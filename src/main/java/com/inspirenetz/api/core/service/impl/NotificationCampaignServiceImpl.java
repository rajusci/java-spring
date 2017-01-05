package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.NotificationCampaign;
import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.NotificationCampaignRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.NotificationCampaignResource;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saneeshci on 27/9/14.
 */
@Service
public class NotificationCampaignServiceImpl extends BaseServiceImpl<NotificationCampaign> implements NotificationCampaignService {


    private static Logger log = LoggerFactory.getLogger(NotificationCampaignServiceImpl.class);


    @Autowired
    NotificationCampaignRepository notificationCampaignRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    SegmentMemberService segmentMemberService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    UserMessagingService userMessagingService;

    @Autowired
    private AuthSessionUtils authSessionUtils;


    @Autowired
    Mapper mapper;

    public NotificationCampaignServiceImpl() {

        super(NotificationCampaign.class);

    }


    @Override
    protected BaseRepository<NotificationCampaign,Long> getDao() {
        return notificationCampaignRepository;
    }



    @Override
    public NotificationCampaign findByNtcId(Long ntcId) throws InspireNetzException {

        // Get the notificationCampaign for the given notificationCampaign id from the repository
        NotificationCampaign notificationCampaign = notificationCampaignRepository.findByNtcId(ntcId);

        // Return the notificationCampaign
        return notificationCampaign;

    }


    @Override
    public Page<NotificationCampaign> findByNtcNameAndNtcMerchantNo(String ntcName,Long ntcMerchantNo,Pageable pageable) {

        Page<NotificationCampaign > notificationCampaigns = notificationCampaignRepository.findByNtcNameLikeAndNtcMerchantNo(ntcName, ntcMerchantNo,pageable);

        return notificationCampaigns;
    }

    @Override
    public Page<NotificationCampaign> findByNtcMerchantNo(Long ntcMerchantNo,Pageable pageable) {

        Page<NotificationCampaign > notificationCampaigns = notificationCampaignRepository.findByNtcMerchantNo(ntcMerchantNo, pageable);

        return notificationCampaigns;
    }

    @Override
    public NotificationCampaign validateAndSaveNotificationCampaign(NotificationCampaignResource notificationCampaignResource) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_SAVE_NOTIFICATION_CAMPAIGN);

        NotificationCampaign notificationCampaign;

        if(notificationCampaignResource.getNtcId()!=null){

            notificationCampaign=findByNtcId(notificationCampaignResource.getNtcId());

            mapper.map(notificationCampaignResource,notificationCampaign);

        }else{

            notificationCampaign=mapper.map(notificationCampaignResource,NotificationCampaign.class);
        }

        //save and return notification campaign
        notificationCampaign =  saveNotificationCampaign(notificationCampaign);

        sendNotification(notificationCampaign);

        return notificationCampaign;
    }

    @Override
    public void sendNotification(NotificationCampaign notificationCampaign) {

        //initialize the list for target sms audience
        List<String> targetSMSAudience = new ArrayList<>();

        //iniitialise the list for target email audience
        List<String> targetEmailAudience = new ArrayList<>();

        //get the target channels
        String[] channels = notificationCampaign.getNtcTargetChannels() != null ?notificationCampaign.getNtcTargetChannels().split(":"):null;

        //check the channel data
        if(channels == null || channels.length == 0){

            return;
        }

        //iterate through the channels and get the target audience
        for(String channel : channels){

            //initialise sms audience list
            List<String> smsAudience = new ArrayList<>();

            //initialise email audience list
            List<String> emailAudience = new ArrayList<>();

            if (channel!=null && channel.length()>0)
            {
            //check channel is sms
            if(Integer.parseInt(channel) == MessageSpielChannel.SMS){

                //get the sms audience
                smsAudience = getSMSChannelAudience(notificationCampaign);

            } else if(Integer.parseInt(channel) == MessageSpielChannel.EMAIL){

                //get the email audience
                emailAudience = getEmailChannelAudience(notificationCampaign);
            }
            }

            //add email audience to the list
            targetEmailAudience.addAll(emailAudience);

            //get the sms audience to the list
            targetSMSAudience.addAll(smsAudience);

        }


        sendEmailNotification(notificationCampaign,targetEmailAudience);

        sendSMSNotification(notificationCampaign,targetSMSAudience);


    }

    private void sendSMSNotification(NotificationCampaign notificationCampaign, List<String> targetSMSAudience){


        for(String mobileNo : targetSMSAudience){

            try{

                MessageWrapper messageWrapper = getMessageWrapperObjectForSMS(notificationCampaign, mobileNo);

                userMessagingService.transmitNotification(messageWrapper);

            }catch (Exception e){


            }

        }
    }

    private MessageWrapper getMessageWrapperObjectForSMS(NotificationCampaign notificationCampaign, String mobileNo) {

        MessageWrapper messageWrapper = new MessageWrapper();

        messageWrapper.setChannel(MessageSpielChannel.SMS);

        messageWrapper.setMerchantNo(notificationCampaign.getNtcMerchantNo());

        messageWrapper.setIsCustomer(IndicatorStatus.NO);

        messageWrapper.setMobile(mobileNo);

        messageWrapper.setMessage(notificationCampaign.getNtcSmsContent());

        messageWrapper.setParams(notificationCampaign.getParams());

        return messageWrapper;

    }

    private void sendEmailNotification(NotificationCampaign notificationCampaign, List<String> targetEmailAudience)  {

        for(String emailId : targetEmailAudience){

            try{

                MessageWrapper messageWrapper = getMessageWrapperObjectForEmail(notificationCampaign, emailId);

                userMessagingService.transmitNotification(messageWrapper);

            } catch(Exception e){


            }

        }
    }

    private MessageWrapper getMessageWrapperObjectForEmail(NotificationCampaign notificationCampaign, String emailId) {


        MessageWrapper messageWrapper = new MessageWrapper();

        messageWrapper.setChannel(MessageSpielChannel.EMAIL);

        messageWrapper.setMerchantNo(notificationCampaign.getNtcMerchantNo());

        messageWrapper.setEmailId(emailId);

        messageWrapper.setIsCustomer(IndicatorStatus.NO);

        messageWrapper.setEmailSubject(notificationCampaign.getNtcEmailSubject());

        messageWrapper.setMessage(notificationCampaign.getNtcEmailContent());

        messageWrapper.setParams(notificationCampaign.getParams());

        return messageWrapper;

    }

    private List<String> getEmailChannelAudience(NotificationCampaign notificationCampaign) {

        //initialise email audience list
        List<String > emailAudienceList = new ArrayList<>();

        //switch for different listeners
        switch (notificationCampaign.getNtcTargetListeners()){

            case NotificationCampaignListeners.ALL_MEMBERS :

                // get all the customers email id
                emailAudienceList = customerService.getEmailInformationForMerchant(notificationCampaign.getNtcMerchantNo());

                break;


            case NotificationCampaignListeners.PUBLIC :

                String targetEmail = notificationCampaign.getNtcTargetEmail();

                //split the public emails separated by comma
                String[] emailList = targetEmail!=null?targetEmail.split(","):null;

                //iterate through the array and add it to email audience list
                for(String email : emailList){

                    if(email.length()>0){

                        //add to audience list
                        emailAudienceList.add(email);

                    }
                }

                break;

            case NotificationCampaignListeners.INDIVIDUAL_CUSTOMERS :

                String targetCustomers = notificationCampaign.getNtcTargetCustomers();

                String[] customerList = targetCustomers!=null?targetCustomers.split(":"):null;

                emailAudienceList = getEmailInformationForCustomers(customerList,notificationCampaign.getNtcMerchantNo());

                break;

            case NotificationCampaignListeners.SEGMENT              :

                String targetSegment = notificationCampaign.getNtcTargetSegments();

                String[] segments = targetSegment!=null?targetSegment.split(":"):null;

                for(String segment : segments){

                    if(segment.length()>0){

                        Page<SegmentMember> segmentMembers= segmentMemberService.findBySgmSegmentId(Long.parseLong(segment),new PageRequest(0,10000));

                        for(SegmentMember segmentMember : segmentMembers){

                            Customer customer = customerService.findByCusCustomerNo(segmentMember.getSgmCustomerNo());

                            if(customer != null){

                                if(customer.getCusEmail() != null && customer.getCusEmail().length() > 0){

                                    emailAudienceList.add(customer.getCusEmail());

                                }

                            }
                        }
                    }
                }

                break;
        }

        return emailAudienceList;

    }

    private List<String> getEmailInformationForCustomers(String[] customerList,Long merchantNo) {

        //initialise the target audience list
        List<String> emailAudienceList = new ArrayList<>();

        //iterate through the customer list
        for(String loyaltyId : customerList){

            //get the customer detail
            Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

            //check whether the customer is having an email id or not
            if(customer != null && customer.getCusEmail() != null && customer.getCusEmail().length() > 0){

                //add the customer email id to the audience list
                emailAudienceList.add(customer.getCusEmail());

            }

        }

        return emailAudienceList;
    }

    private List<String> getSMSChannelAudience(NotificationCampaign notificationCampaign) {

        //initialise email audience list
        List<String > smsAudienceList = new ArrayList<>();

        //switch for different listeners
        switch (notificationCampaign.getNtcTargetListeners()){

            case NotificationCampaignListeners.ALL_MEMBERS :

                // get all the customers mobile no
                smsAudienceList = customerService.getSMSInformationForMerchant(notificationCampaign.getNtcMerchantNo());

                break;


            case NotificationCampaignListeners.PUBLIC :

                String targetSMS = notificationCampaign.getNtcTargetMobile();

                //split the public mobile nos separated by comma
                String[] mobileList = targetSMS!=null?targetSMS.split(","):null;

                for(String mobile : mobileList){

                    if(mobile.length()>0){

                        smsAudienceList.add(mobile);

                    }
                }

                break;

            case NotificationCampaignListeners.INDIVIDUAL_CUSTOMERS :

                String customerList = notificationCampaign.getNtcTargetCustomers();

                //split the target customers separated by colon
                String[] loyaltyIdList = customerList!=null?customerList.split(":"):null;

                //iterate through the array and add it to sms audience list
                for(String loyaltyId : loyaltyIdList){

                    //get the customer information
                    Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,notificationCampaign.getNtcMerchantNo());

                    if(customer != null && customer.getCusMobile() != null && customer.getCusMobile().length() > 0){

                        smsAudienceList.add(customer.getCusMobile());
                    }

                }

                break;

            case NotificationCampaignListeners.SEGMENT              :

                String targetSegment = notificationCampaign.getNtcTargetSegments();

                String[] segments = targetSegment!=null?targetSegment.split(":"):null;

                for(String segment : segments){

                    if(segment.length()>0){

                        Page<SegmentMember> segmentMembers= segmentMemberService.findBySgmSegmentId(Long.parseLong(segment),new PageRequest(0,10000));

                        for(SegmentMember segmentMember : segmentMembers){

                            Customer customer = customerService.findByCusCustomerNo(segmentMember.getSgmCustomerNo());

                            if(customer != null){

                                if(customer != null && customer.getCusMobile() != null && customer.getCusMobile().length() > 0){

                                    smsAudienceList.add(customer.getCusMobile());

                                }

                            }
                        }
                    }
                }

                break;
        }

        return smsAudienceList;
    }

    @Override
    public NotificationCampaign saveNotificationCampaign(NotificationCampaign notificationCampaign) {

        return notificationCampaignRepository.save(notificationCampaign);
    }

    @Override
    public boolean deleteNotificationCampaign(Long ntcId) {

        notificationCampaignRepository.delete(ntcId);

        return true;
    }

    @Override
    public Page<NotificationCampaign> searchNotificationCampaigns(String searchField, String query, Pageable pageable) {

        // Array holding the customer Page
        Page<NotificationCampaign> notificationCampaigns = null ;

        Long ntcMerchantNo=authSessionUtils.getMerchantNo();

        // Check the filter type
        if ( searchField.equals("0") && query.equals("0") ) { 
            notificationCampaigns = findByNtcMerchantNo(ntcMerchantNo, pageable);

        } else if ( searchField.equalsIgnoreCase("name")) {

            notificationCampaigns = findByNtcNameAndNtcMerchantNo("%"+query+"%",ntcMerchantNo,pageable);
        }

        return notificationCampaigns;
    }
}


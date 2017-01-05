package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.loyaltyengine.CatalogueRedemption;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.PartyApprovalRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import com.inspirenetz.api.util.TransferPointUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class PartyApprovalServiceImpl extends BaseServiceImpl<PartyApproval> implements PartyApprovalService,Injectable {


    private static Logger log = LoggerFactory.getLogger(PartyApprovalServiceImpl.class);


    @Autowired
    PartyApprovalRepository partyApprovalRepository;

    @Autowired
    private AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    CustomerService customerService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private LinkRequestService linkRequestService;

    @Autowired
    private AccountLinkingService accountLinkingService;

    @Autowired
    UserMessagingService userMessagingService;

    @Autowired
    LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    RedemptionService redemptionService;

    @Autowired
    CatalogueService catalogueService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    PointTransferRequestService pointTransferRequestService;

    TransferPointService transferPointService;

    // The default approval limit
    private int APPROVAL_TIME_LIMIT = 10;


    public PartyApprovalServiceImpl() {

        super(PartyApproval.class);

    }


    @Override
    protected BaseRepository<PartyApproval,Long> getDao() {
        return partyApprovalRepository;
    }

    @Override
    public PartyApproval findByPapId(Long papId){

        // Get the data from the repository and store in the list
        PartyApproval partyApproval = partyApprovalRepository.findByPapId(papId);

        // Return the list
        return partyApproval;

    }

    @Override
    public PartyApproval getPartyApprovalForLinkRequest(Long papApprover,Long papRequestor,Integer papType) {

        // Get the partyApproval using the partyApproval code and the merchant number
        PartyApproval partyApproval = partyApprovalRepository.findByPapApproverAndPapRequestorAndPapType(papApprover, papRequestor, papType);

        // Return the partyApproval object
        return partyApproval;

    }

    @Override
    public List<PartyApproval> findByPapApproverAndPapType(Long papApprover ,Integer papType) {

        // Get the data from the repository and store in the list
        List<PartyApproval> partyApprovalList = partyApprovalRepository.findByPapApproverAndPapType(papApprover,papType);

        // Return the list
        return partyApprovalList;

    }

    @Override
    public List<PartyApproval > getPartyApproval(Long papApprover, Long papRequestor, Integer papType, Long papRequest){

        //get the party approval list
        List<PartyApproval> partyApprovals = partyApprovalRepository.findByPapApproverAndPapRequestorAndPapTypeAndPapRequest(papApprover, papRequestor, papType, papRequest);

        //return the list
        return partyApprovals;
    }

    @Override
    public Integer sendApproval(Customer requestor, Customer approver, Long requestId, Integer papType, String toLoyaltyId, String reference) throws InspireNetzException {

        PartyApproval partyApproval = getExistingPartyApproval(approver,requestor,papType,requestId);

        int papStaus = 0;

        // If the partyApproval is null, then create a new one
        if ( partyApproval == null ) {

            // Create a new object
            partyApproval = new PartyApproval();

            // Set the fields
            partyApproval.setPapApprover(approver.getCusCustomerNo());

            partyApproval.setPapRequestor(requestor.getCusCustomerNo());

            partyApproval.setPapRequest(requestId);

            partyApproval.setPapType(papType);

            //set product code as reference
            partyApproval.setPapReference(toLoyaltyId);

            partyApproval.setPapStatus(PartyApprovalStatus.QUEUED);

            // Save the object
            partyApproval = savePartyApproval(partyApproval);

            papStaus = PartyApprovalStatus.QUEUED;

            if(partyApproval.getPapId() == null){

                papStaus = PartyApprovalStatus.FAILED;

            }



        } else {

            // Check the current status of the request
            // If it is expired, then we need to requeue again
            if ( partyApproval.getPapStatus().intValue() == PartyApprovalStatus.EXPIRED ) {

                // Set the status to queued
                papStaus =  PartyApprovalStatus.QUEUED;

                //set status to queued
                partyApproval.setPapStatus(papStaus);

                //get current time
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                //set sent time to party approval
                partyApproval.setPapSentDateTime(new Timestamp(timestamp.getTime()));

                //set current link request as papRequest
                partyApproval.setPapRequest(requestId);

                //save the party approval request
                savePartyApproval(partyApproval);

            } else {

                papStaus =  partyApproval.getPapStatus();

            }
        }

        //if the request is queued send notification to approver
        if(papStaus == PartyApprovalStatus.QUEUED){

            // Check if the id has been genreated
            if ( partyApproval.getPapId() != null ) {

                //if approval is not for transfer point call , notification sending method
                if(papType != PartyApprovalType.PARTY_APPROVAL_TRANSFER_POINT_REQUEST){

                    //send notification to approver
                    sendPartyApprovalNotification(papType,approver,requestor,toLoyaltyId,partyApproval.getPapId(),reference);

                } else {

                    /*if approval is for pasa points , we need to add ,points and destLoyatltyId as
                    extra fields send notification for approval*/
                    sendPartyApprovalNotificationForTransferPoint(approver,requestor,reference,toLoyaltyId,partyApproval.getPapId());

                }

            }

        }

        return papStaus;
    }

    private void sendPartyApprovalNotification(Integer papType, Customer approver, Customer requestor, String reference, Long papId,String refField) throws InspireNetzException {

        //create a map for the sms placeholders
        HashMap<String , String > smsParams  = new HashMap<>(0);

        //put the placeholders into the map
        smsParams.put("#min", requestor.getCusLoyaltyId());

        //put transaction id into the map
        smsParams.put("#transId", papId + "");

        //get messageWrapper object
        MessageWrapper messageWrapper = getMessageWrapperObject(approver.getCusLoyaltyId(),smsParams,papId);

        //if partyApproval is link request set appropriate spiel name
        if(papType == PartyApprovalType.PARTY_APPROVAL_LINK_REQUEST){

            messageWrapper.setSpielName(MessageSpielValue.LINK_ACCOUNT_CONFIRM_LINK);

        } else if(papType == PartyApprovalType.PARTY_APPROVAL_UNLINK_REQUEST){

            messageWrapper.setSpielName(MessageSpielValue.LINK_ACCOUNT_CONFIRM_UNLINK);

        } else if(papType == PartyApprovalType.PARTY_APPROVAL_CASHBACK){

            smsParams.put("#amount",refField);

            messageWrapper.setSpielName(MessageSpielValue.LINKED_ACCOUNT_CASHBACK_CONFIRM);

        } else if(papType == PartyApprovalType.PARTY_APPROVAL_REDEMPTION_REQUEST){

            //put transaction id into the map
            smsParams.put("#prdCode",reference);

            messageWrapper.setParams(smsParams);

            messageWrapper.setSpielName(MessageSpielValue.REDEMPTION_REQUEST_CONFIRM_LINK);

        }

        //send notification
        userMessagingService.transmitNotification(messageWrapper);


    }

    private MessageWrapper getMessageWrapperObject(String cusLoyaltyId, HashMap<String, String> smsParams, Long papId) {

        //get message wrapper object
        MessageWrapper messageWrapper = new MessageWrapper();

        //set loyaltyid
        messageWrapper.setLoyaltyId(cusLoyaltyId);

        //set sms params
        messageWrapper.setParams(smsParams);

        //set transaction id
        messageWrapper.setTransId(papId + "");

        //set merchantno
        messageWrapper.setMerchantNo(generalUtils.getDefaultMerchantNo());

        //return messageWrapper
        return messageWrapper;

    }

    private void sendPartyApprovalNotificationForTransferPoint(Customer approver, Customer requestor, String points, String destLoyaltyId, Long papId) throws InspireNetzException{



        //create a map for the sms placeholders
        HashMap<String , String > smsParams  = new HashMap<>(0);

        //put the placeholders into the map
        smsParams.put("#min", requestor.getCusLoyaltyId());

        //add points transferring to sms params
        smsParams.put("#points", generalUtils.getFormattedValue(Double.parseDouble(points)));

        //add transfer to account
        smsParams.put("#destLoyaltyId", destLoyaltyId);

        //put transaction id into the map
        smsParams.put("#transId", papId + "");

        //get message wrapper object
        MessageWrapper messageWrapper = getMessageWrapperObject(approver.getCusLoyaltyId(),smsParams,papId);

        //set spiel name
        messageWrapper.setSpielName(MessageSpielValue.TRANSFER_REQUEST_CONFIRM_LINK);

        //send notification
        userMessagingService.transmitNotification(messageWrapper);
    }

    @Override
    public PartyApproval getExistingPartyApproval(Customer approver, Customer requestor, Integer papType, Long refId) {

        //get the party approval for link request
        if(papType == PartyApprovalType.PARTY_APPROVAL_LINK_REQUEST || papType == PartyApprovalType.PARTY_APPROVAL_UNLINK_REQUEST){

            //return link/unlink type party approvals
            return getPartyApprovalForLinkRequest(approver.getCusCustomerNo(),requestor.getCusCustomerNo(),papType);

        } else if(papType == PartyApprovalType.PARTY_APPROVAL_REDEMPTION_REQUEST || papType == PartyApprovalType.PARTY_APPROVAL_TRANSFER_POINT_REQUEST){

            //get the party approval list
            List<PartyApproval> partyApprovals = getPartyApproval(approver.getCusCustomerNo(),requestor.getCusCustomerNo(),papType,refId);

            //check whether the list is empty , else get the first object
            if(partyApprovals != null && partyApprovals.size()>0){

                //return the first object from the list
                return  partyApprovals.get(0);
            }
        }

        return null;

    }

    private void sendPartyApprovalNotification(Integer papType, Customer approver, Customer requestor) throws InspireNetzException {


        //create a map for the sms placeholders
        HashMap<String , String > smsParams  = new HashMap<>(0);

        //put the placeholders into the map
        smsParams.put("#min", requestor.getCusLoyaltyId());

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("","","","","",approver.getCusMerchantNo(),smsParams,MessageSpielChannel.ALL,IndicatorStatus.NO);

        if(papType == PartyApprovalType.PARTY_APPROVAL_LINK_REQUEST){

            messageWrapper.setSpielName(MessageSpielValue.LINK_ACCOUNT_CONFIRM_LINK);
            messageWrapper.setLoyaltyId(approver.getCusLoyaltyId());
            messageWrapper.setMerchantNo(approver.getCusMerchantNo());
            messageWrapper.setRequestChannel(MessageSpielChannel.ALL);
            messageWrapper.setIsCustomer(IndicatorStatus.YES);

            userMessagingService.transmitNotification(messageWrapper);

        } else if(papType == PartyApprovalType.PARTY_APPROVAL_UNLINK_REQUEST){


            messageWrapper.setSpielName(MessageSpielValue.LINK_ACCOUNT_CONFIRM_UNLINK);
            messageWrapper.setLoyaltyId(approver.getCusLoyaltyId());
            messageWrapper.setMerchantNo(approver.getCusMerchantNo());
            messageWrapper.setRequestChannel(MessageSpielChannel.ALL);
            messageWrapper.setIsCustomer(IndicatorStatus.YES);


            userMessagingService.transmitNotification(messageWrapper);

        } else if(papType == PartyApprovalType.PARTY_APPROVAL_REDEMPTION_REQUEST){

            messageWrapper.setSpielName(MessageSpielValue.REDEMPTION_REQUEST_CONFIRM_LINK);
            messageWrapper.setLoyaltyId(approver.getCusLoyaltyId());
            messageWrapper.setMerchantNo(approver.getCusMerchantNo());
            messageWrapper.setRequestChannel(MessageSpielChannel.ALL);
            messageWrapper.setIsCustomer(IndicatorStatus.YES);

            userMessagingService.transmitNotification(messageWrapper);

        } else if(papType == PartyApprovalType.PARTY_APPROVAL_TRANSFER_POINT_REQUEST){

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_REQUEST_CONFIRM_LINK);
            messageWrapper.setLoyaltyId(approver.getCusLoyaltyId());
            messageWrapper.setMerchantNo(approver.getCusMerchantNo());
            messageWrapper.setRequestChannel(MessageSpielChannel.ALL);
            messageWrapper.setIsCustomer(IndicatorStatus.YES);

            userMessagingService.transmitNotification(messageWrapper);

        }


    }


    @Override
    public PartyApproval savePartyApproval(PartyApproval partyApproval){

        // Save the partyApproval
        return partyApprovalRepository.save(partyApproval);

    }

    @Override
    public boolean deletePartyApproval(Long papId) {

        // Delete the partyApproval
        partyApprovalRepository.delete(papId);

        // return true
        return true;

    }

    @Override
    public boolean isPartyApprovalRequestExpired(PartyApproval partyApproval) {


        // Log the request
        log.info("isPartyApprovalRequestExpired -> Incoming request : " +partyApproval);


        // Check if the type is linkRequest, then we need to set the APPROVAL_LIMIT  to be
        // the data read from the AccountBundlingSetting
        if ( partyApproval.getPapType() == PartyApprovalType.PARTY_APPROVAL_LINK_REQUEST ) {

            // Get the userType
            Integer userType = authSessionUtils.getUserType();

            // Set the merchantNo to null
            Long merchantNo = null;

            // Check the userType and then get the fields
            if ( userType == UserType.MERCHANT_USER ) {

                // Set the merchantNo from the session
                merchantNo = authSessionUtils.getMerchantNo();

            }

            // If the merchantNo is null, use the default MERCHANT_NO
            if ( merchantNo == null ) {

                merchantNo = generalUtils.getDefaultMerchantNo();

            }


            // Log the merchantno
            log.info("isPartyApprovalRequestExpired -> Merchant No : " + merchantNo);


            // Get the default AccoutBundlingSetting
            AccountBundlingSetting accountBundlingSetting =  accountBundlingSettingService.getDefaultAccountBundlingSetting(merchantNo);

            log.info("isPartyApprovalRequestExpired -> AccountBundlingSetting " + accountBundlingSetting);

            // Set the approval limit
            APPROVAL_TIME_LIMIT = accountBundlingSetting.getAbsConfirmationExpiryLimit();


        }



        // Get the current timestamp
        Timestamp reqReceivedTime = new Timestamp(System.currentTimeMillis());

        //party approval request sent is stored in this variable
        Timestamp sentDateTime = partyApproval.getPapSentDateTime();

        //converting the timestamp into time in millis
        long receivedTime = reqReceivedTime.getTime();

        //variable gets the time from timestamp
        long sentTime = sentDateTime.getTime();

        //calendar obj for adding the hour limit to sent time
        Calendar calendarObj = Calendar.getInstance();

        //setting the sent time to calendar object
        calendarObj.setTimeInMillis(sentTime);

        //adding the maximum time availble for approving the request
        calendarObj.add(Calendar.HOUR,APPROVAL_TIME_LIMIT);

        //converting the sent time back to millis
        long expiryTime = calendarObj.getTimeInMillis();

        //checking whether the current time is after the approval time limit
        if(receivedTime < expiryTime) {

            //return false;
            return false;

         } else {

            return true;
        }
    }

    @Override
    public boolean changePartyApprovalForRedemption(Long merchantNo, String requestor, String approver, Integer papType, String papReference, Integer papStatus) throws InspireNetzException {

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",approver,"","","",0L,new HashMap<String,String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

        //getting the requested customer's data by customer number
        Customer papApproverObj = customerService.findByCusLoyaltyIdAndCusMerchantNo(approver, merchantNo);

        //getting the approver customer's data by customer numberapproval
        Customer papRequestorObj = customerService.findByCusLoyaltyIdAndCusMerchantNo(requestor,merchantNo);


        if(papApproverObj == null || papRequestorObj == null){

            //log error
            log.error("changePartyApprovalForRequest : Customer information not found");

            messageWrapper.setMerchantNo(generalUtils.getDefaultMerchantNo());

            messageWrapper.setSpielName(MessageSpielValue.PARTY_APPROVAL_NO_REQUEST_INFORMATION_FOUND); 
            messageWrapper.setMobile(approver);

            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            messageWrapper.setChannel(MessageSpielChannel.SMS);

            //send message to the user
            userMessagingService.transmitNotification(messageWrapper);

            //throw inspirenetz exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }


        messageWrapper.setMerchantNo(papApproverObj.getCusMerchantNo());

        PartyApproval partyApproval = new PartyApproval();

        //get the list of party approvals
        List<PartyApproval> partyApprovals = getPartyApprovalForRedemption(papApproverObj.getCusCustomerNo(), papRequestorObj.getCusCustomerNo(), papType, papReference);

        if(partyApprovals == null || partyApprovals.size() == 0){

            //log error
            log.error("changePartyApprovalForRedemption : No party approval fond ");

            messageWrapper.setSpielName(MessageSpielValue.PARTY_APPROVAL_NO_REQUEST_INFORMATION_FOUND);

            messageWrapper.setMerchantNo(papApproverObj.getCusMerchantNo());

            messageWrapper.setLoyaltyId(papApproverObj.getCusLoyaltyId());

            messageWrapper.setIsCustomer(IndicatorStatus.YES);

            messageWrapper.setChannel(MessageSpielChannel.ALL);

            //send message to the user
            userMessagingService.transmitNotification(messageWrapper);

            //throw error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        } else {

            partyApproval = partyApprovals.get(0);
        }

        // Log the information
        log.info("party approval object"+partyApproval.toString());

        //check whether the request has expired or not
        if(!isPartyApprovalRequestExpired(partyApproval)) {

            //set the status
            partyApproval.setPapStatus(papStatus);

            //update the party approval object
            partyApproval = savePartyApproval(partyApproval);

            // Check if the approval got saved successfully
            if ( partyApproval == null || partyApproval.getPapId() == null ) {

                // Log the information
                log.info("changePartyApprovalForRequest -> Party approval saving failed");

                // Throw exception
                throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

            }

            //get catalogue info
            Catalogue catalogue = catalogueService.findByCatProductCodeAndCatMerchantNo(partyApproval.getPapReference(),merchantNo);

            if (catalogue == null) {

                //log error
                log.error("processPartyApprovalRedemption : No catalogue information found");

                messageWrapper.setSpielName(MessageSpielValue.PARTY_APPROVAL_NO_REQUEST_INFORMATION_FOUND);

                messageWrapper.setMerchantNo(papApproverObj.getCusMerchantNo());

                messageWrapper.setLoyaltyId(papApproverObj.getCusLoyaltyId());

                messageWrapper.setIsCustomer(IndicatorStatus.YES);

                messageWrapper.setChannel(MessageSpielChannel.ALL);

                //send message to the user
                userMessagingService.transmitNotification(messageWrapper);

                //throw exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
            }

            //call method to process redemption
            processPartyApprovalRedemption(partyApproval,merchantNo,papRequestorObj);

            //delete the party approval request
            deletePartyApproval(partyApproval.getPapId());

            //delete the party approval if the request is rejected by the approver
            if(partyApproval.getPapStatus().intValue() == PartyApprovalStatus.REJECTED){

                //log party approval deletion
                log.info("changePartyApprovalForRequest : Approver rejected Request , Deleting party approval :"+partyApproval);

                //delete the party approval request
                deletePartyApproval(partyApproval.getPapId());
            }

            // Return true
            return true;

        } else {

            //set the party approval as expired
           partyApproval.setPapStatus(PartyApprovalStatus.EXPIRED);

           savePartyApproval(partyApproval);


           // Get the LinkRequest information
           Redemption redemption = redemptionService.findByRdmId(partyApproval.getPapRequest());

            //set redemption status to expired
           redemption.setRdmStatus(RedemptionStatus.RDM_STATUS_APPROVAL_REQUEST_EXPIRED);

            //save redemption
           redemptionService.saveRedemption(redemption);

            //log the case
           log.info("changePartyApprovalForRedemption : Request expired , Deleting Party Approval"+partyApproval);

            //delete the party approval
           deletePartyApproval(partyApproval.getPapId());

           // Thrown party approval request expired exception
           throw new InspireNetzException(APIErrorCode.ERR_PARTY_APPROVAL_REQUEST_EXPIRED);

        }

    }

    private void processPartyApprovalRedemption(PartyApproval partyApproval, Long merchantNo ,Customer customer) throws InspireNetzException {

        //get catalogue info
        Catalogue catalogue = catalogueService.findByCatProductCodeAndCatMerchantNo(partyApproval.getPapReference(),merchantNo);

        //get catalogue redemption type
        CatalogueRedemption catalogueRedemption = catalogueService.getCatalogueRedemption(catalogue);

        //create the redemption object
        CatalogueRedemptionItemRequest catalogueRedemptionItemRequest = redemptionService.getRedemptionRequestObject(customer , catalogue);

        //get the redemption object
        Redemption redemption = redemptionService.findByRdmId(partyApproval.getPapRequest());

        //check whether request is pasa rewards , if set destination account details
        String destLoyaltyId  = redemption.getRdmDestLoyaltyId();

        if(!destLoyaltyId.equals("0")){

            //get the recipient account details
            Customer destinationCustomer = customerService.findByCusLoyaltyIdAndCusMerchantNo(destLoyaltyId,merchantNo);

            //set recipient customer no
            catalogueRedemptionItemRequest.setCreditCustomerNo(destinationCustomer.getCusCustomerNo());

            //set recipient loyalty id
            catalogueRedemptionItemRequest.setCreditLoyaltyId(destinationCustomer.getCusLoyaltyId());

            //set recipient loyalty id
            catalogueRedemptionItemRequest.setDestLoyaltyId(destinationCustomer.getCusLoyaltyId());

            //set pasa rewards to true
            catalogueRedemptionItemRequest.setPasaRewards(true);

        } else {

            //set customer loyalty id
            catalogueRedemptionItemRequest.setCreditLoyaltyId(customer.getCusLoyaltyId());

            //set customer
            catalogueRedemptionItemRequest.setCreditCustomerNo(customer.getCusCustomerNo());

            //set pasa rewards to False
            catalogueRedemptionItemRequest.setPasaRewards(false);

        }

        catalogueRedemptionItemRequest.setDebitCustomerNo(partyApproval.getPapRequestor());

        //set redemption id to the request
        catalogueRedemptionItemRequest.setRdmId(redemption.getRdmId());

        //set party approval redemption status to true if request is approved
        if(partyApproval.getPapStatus() == PartyApprovalStatus.ACCEPTED){

            catalogueRedemptionItemRequest.setRedemptionApprovalStatus(true);

        } else {

            catalogueRedemptionItemRequest.setRedemptionApprovalStatus(false);
        }

        try{

            //call redemption method to process redemption
            redemptionService.processCatalogueRedemption(catalogueRedemption, catalogueRedemptionItemRequest);

            //log the activity
            customerActivityService.logActivity(catalogueRedemptionItemRequest.getCreditLoyaltyId(),CustomerActivityType.REDEMPTION,"Redeemed "+redemption.getRdmProductCode()+ " ("+generalUtils.getFormattedValue(catalogue.getCatNumPoints())+" points) ",merchantNo,redemption.getRdmProductCode());

            if(catalogueRedemptionItemRequest.isPasaRewards()){

                //log the activity
                customerActivityService.logActivity(customer.getCusLoyaltyId(),CustomerActivityType.REDEMPTION,"Pasa rewards( "+redemption.getRdmProductCode()+ " ) to "+ catalogueRedemptionItemRequest.getDestLoyaltyId(),merchantNo,redemption.getRdmProductCode());

                //log the activity
                customerActivityService.logActivity(catalogueRedemptionItemRequest.getCreditLoyaltyId(),CustomerActivityType.REDEMPTION,"Pasa rewards( "+redemption.getRdmProductCode()+ " ) from "+ customer.getCusLoyaltyId(),merchantNo,redemption.getRdmProductCode());

            }
        }catch (InspireNetzException e){

            deletePartyApproval(partyApproval.getPapId());

            throw new InspireNetzException(e.getErrorCode());

        }

    }

    @Override
    public boolean validateAndChangePartyApprovalForRequest(Long merchantNo, String requestor, String approver, Integer papType, Integer papStatus) throws InspireNetzException {


        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_PARTY_APPROVAL);

        return changePartyApprovalForRequest(merchantNo,requestor,approver,papType,papStatus);
    }


    /**
     * Function to start the processing of the parent request when we receive a party
     * approval for the process
     *
     * @param partyApproval     - The PartyApproval object indicating the status of approval
     */
    protected void processPartyApprovalParentRequest(PartyApproval partyApproval) throws InspireNetzException {

        if(partyApproval.getPapType() == PartyApprovalType.PARTY_APPROVAL_LINK_REQUEST ||
                partyApproval.getPapType() == PartyApprovalType.PARTY_APPROVAL_UNLINK_REQUEST){

            processPartyApprovalForLinkRequest(partyApproval);

        }

    }


    @Override
    public boolean changePartyApprovalForRequest(Long merchantNo, String approverLoyaltyId, String requestorLoyaltyId, Integer papType, Integer papStatus) throws InspireNetzException {

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",approverLoyaltyId,"","","",0L,new HashMap<String, String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

        //getting the requested customer's data by customer number
        Customer papRequestorObj = customerService.findByCusLoyaltyIdAndCusMerchantNo(requestorLoyaltyId, merchantNo);

        //getting the approver customer's data by customer numberapproval
        Customer papApproverObj = customerService.findByCusLoyaltyIdAndCusMerchantNo(approverLoyaltyId,merchantNo);

        if(papApproverObj == null || papRequestorObj == null){

            //log error
            log.error("changePartyApprovalForRequest : Customer information not found");

            messageWrapper.setMerchantNo(generalUtils.getDefaultMerchantNo());


            messageWrapper.setMobile(papApproverObj.getCusLoyaltyId());

            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            messageWrapper.setChannel(MessageSpielChannel.SMS);

            messageWrapper.setSpielName(MessageSpielValue.PARTY_APPROVAL_NO_REQUEST_INFORMATION_FOUND);

            //send message to the user
            userMessagingService.transmitNotification(messageWrapper);

            //throw inspirenetz exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }

        //getting the party approval object by unique keys
        PartyApproval partyApproval = partyApprovalRepository.findByPapApproverAndPapRequestorAndPapType(papApproverObj.getCusCustomerNo(), papRequestorObj.getCusCustomerNo(), papType);

        messageWrapper.setMerchantNo(papApproverObj.getCusMerchantNo());

        if(partyApproval == null || partyApproval.getPapStatus() != PartyApprovalStatus.QUEUED){

            //log error
            log.error("changePartyApprovalForRequest : No approval request found");

            messageWrapper.setSpielName(MessageSpielValue.PARTY_APPROVAL_NO_REQUEST_INFORMATION_FOUND);

            messageWrapper.setMerchantNo(papApproverObj.getCusMerchantNo());

            messageWrapper.setLoyaltyId(papApproverObj.getCusLoyaltyId());

            messageWrapper.setIsCustomer(IndicatorStatus.YES);

            messageWrapper.setChannel(MessageSpielChannel.ALL);

            //send message to the user
            userMessagingService.transmitNotification(messageWrapper);

            //throw inspirenetz exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }
        // Log the information
        log.info("party approval object"+partyApproval.toString());

        //check whether the request has expired or not
        if(!isPartyApprovalRequestExpired(partyApproval)) {

            //set the status
            partyApproval.setPapStatus(papStatus);

            //update the party approval object
            partyApproval = savePartyApproval(partyApproval);

            // Check if the approval got saved successfully
            if ( partyApproval == null || partyApproval.getPapId() == null ) {

                // Log the information
                log.info("changePartyApprovalForRequest -> Party approval saving failed");

                // Throw exception
                throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

            }

            // Log the information
            log.info("updated party approval" + partyApproval.toString());

            if(partyApproval.getPapStatus() == PartyApprovalStatus.ACCEPTED ){

                //send sms
                //create a map for the sms placeholders
                HashMap<String , String > smsParams  = new HashMap<>(0);

                //put the placeholders into the map
                smsParams.put("#min",papApproverObj.getCusLoyaltyId());

                //if request confirmed is link request , send link request success sms
                if(partyApproval.getPapType().intValue() == PartyApprovalType.PARTY_APPROVAL_UNLINK_REQUEST){

                    messageWrapper.setSpielName(MessageSpielValue.UNLINK_REQUEST_SUCCESS_TO_REQUESTOR);

                    messageWrapper.setParams(smsParams);

                    messageWrapper.setMerchantNo(papRequestorObj.getCusMerchantNo());

                    messageWrapper.setLoyaltyId(papRequestorObj.getCusLoyaltyId());

                    messageWrapper.setIsCustomer(IndicatorStatus.YES);

                    messageWrapper.setChannel(MessageSpielChannel.ALL);

                    //send message to the user
                    userMessagingService.transmitNotification(messageWrapper);

                }

                //send sms
                //create a map for the sms placeholders
                smsParams  = new HashMap<>(0);

                //put the placeholders into the map
                smsParams.put("#min",papRequestorObj.getCusLoyaltyId());

                //if request confirmed is link request , send link request success sms
               if(partyApproval.getPapType().intValue() == PartyApprovalType.PARTY_APPROVAL_UNLINK_REQUEST){

                   messageWrapper.setSpielName(MessageSpielValue.UNLINK_REQUEST_SUCCESS_TO_APPROVER);

                   messageWrapper.setParams(smsParams);

                   messageWrapper.setMerchantNo(papApproverObj.getCusMerchantNo());

                   messageWrapper.setLoyaltyId(papApproverObj.getCusLoyaltyId());

                   messageWrapper.setIsCustomer(IndicatorStatus.YES);

                   messageWrapper.setChannel(MessageSpielChannel.ALL);

                   //send message to the user
                   userMessagingService.transmitNotification(messageWrapper);

                }
            }

            // Process the partyApproval['s parent request
            processPartyApprovalParentRequest(partyApproval);

            //delete the party approval if the request is rejected by the approver
            if(partyApproval.getPapStatus().intValue() == PartyApprovalStatus.REJECTED){

                //log party approval deletion
                log.info("changePartyApprovalForRequest : Approver rejected Request , Deleting party approval :"+partyApproval);

                //delete the party approval request
                deletePartyApproval(partyApproval.getPapId());
            }

            // Return true
            return true;

        } else {

            //set the party approval as expired
            partyApproval.setPapStatus(PartyApprovalStatus.EXPIRED);

            savePartyApproval(partyApproval);


            // Get the LinkRequest information
            LinkRequest linkRequest = linkRequestService.findByLrqId(partyApproval.getPapRequest());

            if(linkRequest.getLrqType() == LinkRequestType.LINK){

                //set the link request status as failed and set remark
                linkRequest.setLrqRemarks("Link request expired");

            } else {

                linkRequest.setLrqRemarks("Unlink request expired");

                // Get the linked loyalty of the request
                LinkedLoyalty linkedLoyalty = linkedLoyaltyService.findByLilChildCustomerNo(linkRequest.getLrqSourceCustomer());

                //set status to active
                linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.ACTIVE);

                //save linked loyalty
                linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);


            }

            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            // Thrown party approval request expired exception
            throw new InspireNetzException(APIErrorCode.ERR_PARTY_APPROVAL_REQUEST_EXPIRED);


        }

    }

    private void processPartyApprovalForLinkRequest(PartyApproval partyApproval) throws InspireNetzException {

        // check the type
        if ( partyApproval.getPapType() == PartyApprovalType.PARTY_APPROVAL_LINK_REQUEST || partyApproval.getPapType() == PartyApprovalType.PARTY_APPROVAL_UNLINK_REQUEST ) {

            // Get the LinkRequest information
            LinkRequest linkRequest = linkRequestService.findByLrqId(partyApproval.getPapRequest());

            // Check if we got the LinkRequest object
            if ( linkRequest == null ) {

                return ;

            }

            Customer customer = null;

            if(linkRequest.getLrqInitiator() ==LinkRequestInitiator.PRIMARY){

                customer = customerService.findByCusCustomerNo(linkRequest.getLrqParentCustomer());
            } else {

                customer =customerService.findByCusCustomerNo(linkRequest.getLrqSourceCustomer());
            }

            //get the primary customer info
            Customer primary = customerService.findByCusCustomerNo(linkRequest.getLrqParentCustomer());

            // Process the linkRequest
            accountLinkingService.processLinkRequest(linkRequest,customer.getCusLoyaltyId() );

            //check linked loyalty status of the primary customer
            linkRequestService.checkAccountLinkingStatus(primary);

        }
    }


    @Override
    public List<PartyApproval> findByPapApprover(Long merchantNo,String loyaltyId) {

        //get the customer details
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        List<PartyApproval> partyApprovals = partyApprovalRepository.findByPapApprover(customer.getCusCustomerNo());

        if(partyApprovals != null && partyApprovals.size()>0) {

            for(PartyApproval partyApproval : partyApprovals) {

                partyApproval.getReqCustomer().getCusCustomerNo();

                partyApproval.setReqCustomer(partyApproval.getReqCustomer());

            }

        }

        return partyApprovals;

    }

    @Override
    public List<PartyApproval> getPendingPartyApproval(Long merchantNo) throws InspireNetzException {

        //get the loyalty id from Session
        String loyaltyId = authSessionUtils.getUserLoginId();

        //call the approval expiry checking method
        expiringLinkRequest(loyaltyId,merchantNo,ApprovalExpiryFilterType.APPROVER);

        //get the pending approvals by approver id
        List<PartyApproval> partyApprovals = findByPapApprover(merchantNo,loyaltyId);

        //return party approvals
        return partyApprovals;

    }

    /**
     * @date:18-12-2014
     * @purpose:expiring the link request based on account bundling settings time (absConfirmationExpiryLimit)
     * @param cusLoyaltyId
     * @param merchantNo
     * @param requestType
     */

    @Override
    public void expiringLinkRequest(String cusLoyaltyId, Long merchantNo, Integer requestType) throws InspireNetzException {

      //find customer number
      Customer customer =customerService.findByCusLoyaltyIdAndCusMerchantNo(cusLoyaltyId, merchantNo);


      List<PartyApproval> partyApprovalList=null;

        //check customer is null or not if customer null log
      if(customer ==null){

          log.info("PartyApprovalServiceImpl::expiringLinkRequest:Customer is null");

          return;

      }


      //check the type of the request
      if(requestType == ApprovalExpiryFilterType.APPROVER){

          partyApprovalList = partyApprovalRepository.findByPapApprover(customer.getCusCustomerNo());

      }else if(requestType == ApprovalExpiryFilterType.REQUESTOR){


          partyApprovalList = partyApprovalRepository.findByPapRequestor(customer.getCusCustomerNo());
      }

      if(partyApprovalList ==null){

          return;
      }

      // Get the default AccoutBundlingSetting
      AccountBundlingSetting accountBundlingSetting =  accountBundlingSettingService.getDefaultAccountBundlingSetting(merchantNo);

      //get the current limit
      int timeLimit = accountBundlingSetting.getAbsConfirmationExpiryLimit()==null?0:accountBundlingSetting.getAbsConfirmationExpiryLimit();

      //find a link request by approver
      for(PartyApproval partyApproval :partyApprovalList){

        //get the send time stamp
        Timestamp timestamp =partyApproval.getPapSentDateTime();

        //check the request is expired or not
        boolean isExpired =isRequestExpired(timestamp, timeLimit);

        //is expired true then update party approval status is expired
        if(isExpired){

            //update the party
            partyApproval.setPapStatus(PartyApprovalStatus.EXPIRED);

            savePartyApproval(partyApproval);

            //update link request status
            updateLinkRequest(partyApproval);


            log.info("PartyApprovalServiceImpl::expiringLinkRequest:with paprequest:"+partyApproval.getPapRequest());


        }


      }

    }

    @Override
    public List<PartyApproval> getPartyApprovalForRedemption(Long approver, Long requestor, Integer papType, String papReference) {

        //get the redemption party approvals for the accounts
        List<PartyApproval > partyApprovals = partyApprovalRepository.findByPapApproverAndPapRequestorAndPapTypeAndPapReferenceOrderByPapIdDesc(approver, requestor, papType, papReference);

        //return the list
        return partyApprovals;

    }

    @Override
    public void changePartyApproval(Long merchantNo, String requestorLoyaltyId, String approverLoyaltyId, Integer requestType, String reference, Integer status, Integer requestType1) throws InspireNetzException {


        if(requestType == PartyApprovalType.PARTY_APPROVAL_REDEMPTION_REQUEST){

            changePartyApprovalForRedemption(merchantNo, requestorLoyaltyId, approverLoyaltyId, requestType, reference, status);

        }else if(requestType == PartyApprovalType.PARTY_APPROVAL_LINK_REQUEST || requestType == PartyApprovalType.PARTY_APPROVAL_UNLINK_REQUEST){

            // change the partyApprovalRequest and set return value to status
            changePartyApprovalForRequest(merchantNo, approverLoyaltyId, requestorLoyaltyId, requestType, status);

        }else {

            //call transfer point method for processing party approval request
            changePartyApprovalForTransferPointRequest(merchantNo,approverLoyaltyId,requestorLoyaltyId,requestType,reference,status);
        }
    }

    @Override
    public boolean changePartyApprovalForTransferPointRequest(Long merchantNo, String approverLoyaltyId, String requestorLoyaltyId, Integer requestType, String reference, Integer status) throws InspireNetzException {

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",approverLoyaltyId,"","","",0L,new HashMap<String, String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

        //getting the requested customer's data by customer number
        Customer papApproverObj = customerService.findByCusLoyaltyIdAndCusMerchantNo(approverLoyaltyId, merchantNo);

        //getting the approver customer's data by customer numberapproval
        Customer papRequestorObj = customerService.findByCusLoyaltyIdAndCusMerchantNo(requestorLoyaltyId,merchantNo);


        if(papApproverObj == null || papRequestorObj == null){

            //log error
            log.error("changePartyApprovalForTransferPointRequest : Customer information not found");

            messageWrapper.setMerchantNo(generalUtils.getDefaultMerchantNo());
            messageWrapper.setSpielName(MessageSpielValue.PARTY_APPROVAL_NO_REQUEST_INFORMATION_FOUND);

            messageWrapper.setMobile(approverLoyaltyId);

            messageWrapper.setChannel(MessageSpielChannel.SMS);

            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            userMessagingService.transmitNotification(messageWrapper);

            //throw inspirenetz exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }

        messageWrapper.setMerchantNo(papApproverObj.getCusMerchantNo());

        PartyApproval partyApproval = new PartyApproval();

        //get the list of party approvals
        List<PartyApproval> partyApprovals = getPartyApprovalForRedemption(papApproverObj.getCusCustomerNo(), papRequestorObj.getCusCustomerNo(), requestType, reference);

        if(partyApprovals == null || partyApprovals.size() == 0){

            //log error
            log.error("changePartyApprovalForTransferPointRequest : No party approval fond ");

            messageWrapper.setSpielName(MessageSpielValue.PARTY_APPROVAL_NO_REQUEST_INFORMATION_FOUND);

            messageWrapper.setMerchantNo(papApproverObj.getCusMerchantNo());

            messageWrapper.setLoyaltyId(papApproverObj.getCusLoyaltyId());

            messageWrapper.setChannel(MessageSpielChannel.ALL);

            messageWrapper.setIsCustomer(IndicatorStatus.YES);

            userMessagingService.transmitNotification(messageWrapper);

            //throw error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        } else {

            partyApproval = partyApprovals.get(0);
        }

        // Log the information
        log.info("changePartyApprovalForTransferPointRequest : Requested party approval: "+partyApproval.toString());

        //check whether the request has expired or not
        if(!isPartyApprovalRequestExpired(partyApproval)) {

            //set the status
            partyApproval.setPapStatus(status);

            //update the party approval object
            partyApproval = savePartyApproval(partyApproval);

            // Check if the approval got saved successfully
            if ( partyApproval == null || partyApproval.getPapId() == null ) {

                // Log the information
                log.info("changePartyApprovalForTransferPointRequest -> Party approval saving failed");

                // Throw exception
                throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

            }

            //get catalogue info
            PointTransferRequest pointTransferRequest = pointTransferRequestService.findByPtrId(partyApproval.getPapRequest());

            if(pointTransferRequest == null){

                //log error
                log.error("changePartyApprovalForTransferPointRequest : No request data found");

                messageWrapper.setSpielName(MessageSpielValue.PARTY_APPROVAL_NO_REQUEST_INFORMATION_FOUND);

                messageWrapper.setMerchantNo(papApproverObj.getCusMerchantNo());

                messageWrapper.setLoyaltyId(papApproverObj.getCusLoyaltyId());

                messageWrapper.setChannel(MessageSpielChannel.ALL);

                messageWrapper.setIsCustomer(IndicatorStatus.YES);

                userMessagingService.transmitNotification(messageWrapper);

                //throw exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
            }

            try{

                    //call method to process redemption
                    processPartyApprovalTransferPoint(pointTransferRequest,status);

                }catch (InspireNetzException e){

                    deletePartyApproval(partyApproval.getPapId());

                    throw new InspireNetzException(e.getErrorCode());

                }

            //delete the party approval request
            deletePartyApproval(partyApproval.getPapId());

            //delete the party approval if the request is rejected by the approver
            if(partyApproval.getPapStatus().intValue() == PartyApprovalStatus.REJECTED){

                //log party approval deletion
                log.info("changePartyApprovalForRequest : Approver rejected Request , Deleting party approval :"+partyApproval);

                //delete the party approval request
                deletePartyApproval(partyApproval.getPapId());
            }

            // Return true
            return true;

        }

        return true;
    }

    private void processPartyApprovalTransferPoint(PointTransferRequest pointTransferRequest,Integer papStatus) throws InspireNetzException {

        TransferPointRequest transferPointRequest = getTransferPointRequestObject(pointTransferRequest);

        //set reference
        transferPointRequest.setPtrId(pointTransferRequest.getPtrId());

        //set party approval redemption status to true if request is approved
        if(papStatus == PartyApprovalStatus.ACCEPTED){

            transferPointRequest.setApproved(true);

        } else {

            transferPointRequest.setApproved(false);
        }

        //process the request
        transferPointService.transferPoints(transferPointRequest);

    }

    private TransferPointRequest getTransferPointRequestObject(PointTransferRequest pointTransferRequest) {

        TransferPointRequest transferPointRequest = new TransferPointRequest();

        //set from loyalty id
        transferPointRequest.setFromLoyaltyId(pointTransferRequest.getPtrSource());

        //set to loyalty id
        transferPointRequest.setToLoyaltyId(pointTransferRequest.getPtrDestination());

        //set from reward currency
        transferPointRequest.setFromRewardCurrency(pointTransferRequest.getPtrSourceCurrency());

        //set to reward currency
        transferPointRequest.setToRewardCurrency(pointTransferRequest.getPtrDestCurrency());

        //set reward qty
        transferPointRequest.setRewardQty(pointTransferRequest.getPtrRewardQty());

        //set merchant no
        transferPointRequest.setMerchantNo(pointTransferRequest.getPtrMerchantNo());

        return transferPointRequest;
    }


    /**
     * @purpose unlink the request
     * @param partyApproval
     * @throws InspireNetzException
     */
    private void updateLinkRequest(PartyApproval partyApproval) throws InspireNetzException {

        // Get the LinkRequest information
        LinkRequest linkRequest = linkRequestService.findByLrqId(partyApproval.getPapRequest());

        //check link request is valid
        if(linkRequest ==null){

            return;
        }

        if(linkRequest.getLrqType() == LinkRequestType.LINK){

            //set the link request status as failed and set remark
            linkRequest.setLrqRemarks("Link request expired");

        } else {



            linkRequest.setLrqRemarks("Unlink request expired");
        }

        linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

        //save the link request
        linkRequestService.saveLinkRequest(linkRequest);

        //if the rejected request is of unlinking, then make linked loyalty as active
        if(linkRequest.getLrqType() == LinkRequestType.UNLINK){


            // Get the linked loyalty of the request
            LinkedLoyalty linkedLoyalty = linkedLoyaltyService.findByLilChildCustomerNo(linkRequest.getLrqSourceCustomer());

            //set status to active
            linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.ACTIVE);

            //save linked loyalty
            linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);

        }
        //log the activity
      //  customerActivityService.logActivity(lrqInitiator,CustomerActivityType.ACCOUNT_LINKING,"Failed , Unlink request saving failed",merchantNo,"");




    }

    private boolean isRequestExpired(Timestamp papSentTime, int timeLimit) {

        // Get the current timestamp
        Timestamp reqReceivedTime = new Timestamp(System.currentTimeMillis());

        //party approval request sent is stored in this variable
        Timestamp sentDateTime = papSentTime;

        //converting the timestamp into time in millis
        long receivedTime = reqReceivedTime.getTime();

        //variable gets the time from timestamp
        long sentTime = sentDateTime.getTime();

        //calendar obj for adding the hour limit to sent time
        Calendar calendarObj = Calendar.getInstance();

        //setting the sent time to calendar object
        calendarObj.setTimeInMillis(sentTime);

        //adding the maximum time availble for approving the request
        calendarObj.add(Calendar.HOUR,timeLimit);

        //converting the sent time back to millis
        long expiryTime = calendarObj.getTimeInMillis();

        //checking whether the current time is after the approval time limit
        if(receivedTime < expiryTime) {

            //return false;
            return false;

        } else {

            return true;
        }


    }

    @Override
    public void inject(TransferPointUtils beansManager) {

        this.transferPointService = beansManager.getTransferPointService();
    }
}

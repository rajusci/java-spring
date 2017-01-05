package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.MerchantSetting;
import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.core.domain.SpielText;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.GeneralUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Created by sandheepgr on 18/9/14.
 */
@Service
public class UserMessagingServiceImpl implements UserMessagingService {


    // Create the Logger
    private static Logger log = LoggerFactory.getLogger(UserMessagingServiceImpl.class);

    @Autowired
    private MessageSpielService messageSpielService;

    @Autowired
    private SpielTextService spielTextService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    SettingService settingService;

    @Autowired
    MerchantSettingService merchantSettingService;

    @Autowired
    private JavaMailSenderImpl mailSender;


    @Value("${sms.provider}")
    private String defSmsProvider;


    @Value("${integration.messaging.sendsms}")
    private String sendSMSUrl;

    /*@Override
    public boolean transmitNotifications(MessageWrapper messageWrapper) throws InspireNetzException {

        //find customer object
        Long merchantNo = messageWrapper.getMerchantNo();

        Customer customer = getCustomerData(merchantNo,messageWrapper);

        //get the message content
        String messageContent = getMessageContents(customer,messageWrapper,MessageSpielChannel.SMS);

       if(messageContent == null ){

           log.error("transmitNotification : No spiel content available SpielName:"+messageWrapper.getSpielName()+" Mobile:"+messageWrapper.getMobile());

           return false;
       }

        // Set the params
        Map<String,String> smsParams = new HashMap<>(0);

        // Set the mobile
        smsParams.put("mobile",messageWrapper.getMobile());

        // Set the message
        smsParams.put("message",messageContent);

        //call the rest method
        APIResponseObject responseObject = transmitSMS( smsParams,messageWrapper.getMerchantNo());

        // Check the response object
        if ( responseObject == null || responseObject.getStatus().equals(APIResponseStatus.failed.name()) ) {

            return false;

        }

        // Finally return true
        return true;
    }
*/
    /*public Customer getCustomerData(Long merchantNo,MessageWrapper messageWrapper){

        Customer customer = new Customer();

        //check referee message

        //if merchant is default set customer location as 0 and merchant no as default
        if(merchantNo == generalUtils.getDefaultMerchantNo()){

            //set merchant no and location
            customer.setCusMerchantNo(merchantNo);
            customer.setCusLocation(0L);


            if(messageWrapper.getLoyaltyId() != null && !messageWrapper.getLoyaltyId().equals("")){

                //find customer object
                Customer exCustomer =customerService.findByCusLoyaltyIdAndCusMerchantNo(messageWrapper.getLoyaltyId(),merchantNo);


                if(exCustomer != null ) {

                    customer = exCustomer;

                    //mobile is not available , get the mobile number from customer data
                    if (messageWrapper.getMobile() == null || messageWrapper.getMobile().length() == 0) {

                        if (customer.getCusMobile() != null) {

                            //set mobile information
                            messageWrapper.setMobile(customer.getCusMobile());

                        } else {

                            //log error
                            log.error("transmitNotification : No mobile number available " + messageWrapper);

                        }


                    }

                }
            }



        } else if(messageWrapper.getIsCustomer() == IndicatorStatus.NO){


            customer.setCusMerchantNo(messageWrapper.getMerchantNo());

            customer.setCusLocation(0L);

            customer.setCusMobile(messageWrapper.getMobile());


        }else {

            //find customer object
            customer =customerService.findByCusLoyaltyIdAndCusMerchantNo(messageWrapper.getLoyaltyId(),merchantNo);

            //mobile is not available , get the mobile number from customer data
            if(messageWrapper.getMobile() == null || messageWrapper.getMobile().length() == 0){

                if(customer.getCusMobile() != null){

                    //set mobile information
                    messageWrapper.setMobile(customer.getCusMobile());

                } else {

                    //log error
                    log.error("transmitNotification : No mobile number available "+messageWrapper);

                }


            }
            //check customer object is null or not
            if(customer ==null){

                // Log the error
                log.error("UserMessagingServiceImpl -> sendSMS -> No valid customer details with mob number : " + messageWrapper.getLoyaltyId());

            }

        }

        return customer;

    }*/

    /*public String getMessgeContent(Customer customer,MessageWrapper messageWrapper) throws InspireNetzException{

        // Get the spielText object
        SpielText spielText = spielTextService.getSpielText(customer, messageWrapper.getSpielName(), MessageSpielChannel.SMS);

        // If the spiel text  is null, return
        if ( spielText == null ) {

            // Log the error
            log.error("UserMessagingServiceImpl -> sendSMS -> No spiel information for name : " + messageWrapper.getSpielName());

            return null;
        }


        // Get the content of the message
        String content = spielText.getSptDescription();

        // If the content is null, return
        if ( content == null ) {

            // Log the erroir
            log.error("UserMessagingServiceImpl -> sendSMS -> No content for spiel : " + messageWrapper.getSpielName());

            return null;

        }

        Map<String , String > fields = messageWrapper.getSmsParams();

        // Go through the field passed and replace the message content
        // with the content of the fields key
        for ( Map.Entry<String,String> field : fields.entrySet() ) {

            // Replace the field.key ( placeholder ) with field.value ( value for the placeholder)
            content = content.replaceAll("(?i)"+field.getKey(),field.getValue());

        }

        return content;

    }*/

    @Override
    public APIResponseObject transmitSMS(Map<String, String> parameters,Long merchantNo) throws InspireNetzException {

        //check whether SMS is enabled for the merchant
        boolean isSmsEnabled = merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_ENABLE_SMS, merchantNo);

        APIResponseObject apiResponseObject = null;

        //continue only if the setting is enabled for the merchant
        if(!isSmsEnabled){

               return null;

        } else {

            //get settings id
            Long settingId = settingService.getSettingsId(AdminSettingsConfiguration.MER_SMS_LINK_STRING);

            //get the link string of the merchant sms configuration
            MerchantSetting merchantSetting = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,settingId);

            Long smsProviderSetting = settingService.getSettingsId(AdminSettingsConfiguration.MER_SMS_PROVIDER);

            MerchantSetting smsProvider = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,smsProviderSetting);

            Long smsCountryCodeSettingId = settingService.getSettingsId(AdminSettingsConfiguration.SMS_COUNTRY_CODE);

            MerchantSetting smsCountryCodeSetting = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,smsCountryCodeSettingId);

            if(merchantSetting == null ){

                return null ;
            }
            
            //get the link string
            String smsLinkString = merchantSetting.getMesValue();

            //if string is not null , continue
            if(smsLinkString != null || smsLinkString.length() > 0){

                String urlEncodedMessage = "";

                String mobile = parameters.get("mobile");

                if(mobile==null ||mobile.equals("")){

                    log.error("transmitSMS : parameter -to -Empty");

                    return null;

                }


                if(smsProvider != null && smsCountryCodeSetting!=null && !smsCountryCodeSetting.getMesValue().equals("")){

                    mobile = smsCountryCodeSetting.getMesValue()+mobile;

                }

                    //replace the parameters in the link string
                smsLinkString = smsLinkString.replace("#to",mobile);

                smsLinkString = smsLinkString.replace("#message",""+parameters.get("message")+"");

                try{


                    apiResponseObject = integrationService.placeRestGetAPICall(smsLinkString, new HashMap<String, String>(0));

                } catch(Exception ex){

                    log.error("transmitSMS : Parameters"+parameters+ "Exception "+ ex);
                    return null;
                }


            } else {

                return null;
            }
        }

        return apiResponseObject;


    }

    /*@Override
    public boolean sendSMS(String spielName, String loyaltyId, HashMap<String, String> fields) throws InspireNetzException {

        //check mobile number null condition
        loyaltyId =(loyaltyId ==null || loyaltyId.equals(""))?"0":loyaltyId;

        //get message wrapper object
        MessageWrapper messageWrapper = new MessageWrapper();

        //set loyaltyid
        messageWrapper.setLoyaltyId(loyaltyId);

        //set smsParams
        messageWrapper.setSmsParams(fields);

        //set spiel name
        messageWrapper.setSpielName(spielName);

        //set channel as sms
        messageWrapper.setChannel(MessageSpielChannel.SMS);

        //set smsparams
        messageWrapper.setSmsParams(fields);

        messageWrapper.setMobile("");

        try{

            //calling transmit sms method
            transmitNotification(messageWrapper);

        }catch(Exception e){

            e.printStackTrace();
        }


        return true;
    }

    @Override
    public boolean sendEmail(String spielName, String mailId, HashMap<String, String> fields) throws InspireNetzException {

        try{

            MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(new InternetAddress("fayizkoyissan@customerinspire.com"));
            message.setFrom(new InternetAddress("fayizkoyissan@gmail.com"));
            message.setSubject("TEST");



            message.setText("<html><body><img src='cid:identifier1234'></br><b>WELCOME Mr FAYIZ KOYISSAN</b></body></html>", true);

            mailSender.send(mimeMessage);

        } catch (AddressException e) {

            e.printStackTrace();

            log.error("sendEmail : Parameters"+fields+ "Exception "+ e);

        } catch (MessagingException e) {

            e.printStackTrace();

            log.error("sendEmail : Parameters"+fields+ "Exception "+ e);
        }



        return true;
    }*/

    @Override
    public boolean transmitNotification(MessageWrapper messageWrapper) throws InspireNetzException {

        //find customer object
        Long merchantNo = messageWrapper.getMerchantNo();

        Customer customer = getCustomerData(merchantNo, messageWrapper);

        if(customer==null){

            log.error("transmitNotification : no customer details: "+ " MerchantNo: "+messageWrapper.getMerchantNo()+" IsCustomer : "+messageWrapper.getIsCustomer()+" LoyaltyId: "+messageWrapper.getLoyaltyId());

            return false;
        }

        if(messageWrapper.getChannel()==0||messageWrapper.getChannel()==MessageSpielChannel.SMS){

            transmitSMSNotifications(customer,messageWrapper);

        }

        if(messageWrapper.getChannel()==0||messageWrapper.getChannel()==MessageSpielChannel.EMAIL){

            transmitMailNotifications(customer,messageWrapper);
        }

        // Finally return true
        return true;
    }


    public Customer getCustomerData(Long merchantNo,MessageWrapper messageWrapper){

        Customer customer=null ;

        //if merchant no is not set in message wrapper,initialize with defaultMerchantNo and
        if(messageWrapper.getMerchantNo()==null||messageWrapper.getMerchantNo().longValue()==0L)
        {

            messageWrapper.setMerchantNo(generalUtils.getDefaultMerchantNo());
            messageWrapper.setIsCustomer(IndicatorStatus.NO);
        }

        //If IsCustomer is false means not a customer in that merchant
        //so need set mobile and email details of customer from  message wrapper
        if(messageWrapper.getIsCustomer()==IndicatorStatus.NO)
        {
            customer=new Customer();
            customer.setCusMerchantNo(messageWrapper.getMerchantNo());
            customer.setCusLocation(0L);
            customer.setCusMobile(messageWrapper.getMobile());
            customer.setCusEmail(messageWrapper.getEmailId());

        }else {

            //Comes here only if customer of merchant specified in message wrapper
            //check whether loyalty id specified or not
            if(messageWrapper.getLoyaltyId() != null && !messageWrapper.getLoyaltyId().equals("")){

                //find customer object
                customer =customerService.findByCusLoyaltyIdAndCusMerchantNo(messageWrapper.getLoyaltyId(),merchantNo);

                //check customer object is null or not
                if(customer ==null){

                    // Log the error
                    log.error("getCustomerDetails -> No valid customer details with loyalty Id : " + messageWrapper.getLoyaltyId()); 

                }else{


                    //mobile is not available in message wrapper , get the mobile number from customer data
                    if (messageWrapper.getMobile() == null || messageWrapper.getMobile().length() == 0) {

                        if (customer.getCusMobile() != null) {

                            //set mobile information
                            messageWrapper.setMobile(customer.getCusMobile());

                        } else {

                            //log error
                            log.info("getCustomerDetails : No mobile number available " + messageWrapper);


                        }


                    }

                    //emailId is not available in message wrapper , get the emailId from customer data
                    if (messageWrapper.getEmailId() == null || messageWrapper.getEmailId().length() == 0) {

                        if (customer.getCusEmail() != null) {

                            //set mobile information
                            messageWrapper.setEmailId(customer.getCusEmail());

                        } else {

                            //log error
                            log.info("getCustomerDetails : No emailId available " + messageWrapper);

                        }


                    }

                }
            }



        }

        return customer;

    }

    public String getMessageContents(Customer customer,MessageWrapper messageWrapper,int channel) throws InspireNetzException{

        if(messageWrapper.getSpielName()==null || messageWrapper.getSpielName().equals(""))
        {
            if(messageWrapper.getMessage()==null||messageWrapper.getMessage().equals("")){

                // Log the error
                log.error("getSpielContent: No spiel and no explicit message content ");

                return null;

            }else{

                String content =messageWrapper.getMessage();

                if(messageWrapper.getParams()!=null){

                    Map<String , String > fields = messageWrapper.getParams();

                    // Go through the field passed and replace the message content
                    // with the content of the fields key
                    for ( Map.Entry<String,String> field : fields.entrySet() ) {

                        // Replace the field.key ( placeholder ) with field.value ( value for the placeholder)
                        content = content.replaceAll("(?i)"+field.getKey(),field.getValue());

                    }
                }

                return content;

            }

        }

        // Get the spielText object
        SpielText spielText = spielTextService.getSpielText(customer,messageWrapper.getSpielName(), channel);

        // If the spiel text  is null, return
        if ( spielText == null ) {

            // Log the error
            log.error("getSpielContent: No spiel with  Name : " + messageWrapper.getSpielName() + " for Channel : " + channel);

            return null;
        }


        // Get the content of the message
        String content = spielText.getSptDescription();

        // If the content is null, return
        if ( content == null ) {

            // Log the erroir
            log.error("getSpielContent -> Content not specified for the spiel : " + messageWrapper.getSpielName()+" for channel : "+channel);

            return null;

        }

        if(messageWrapper.getParams()!=null){

            Map<String , String > fields = messageWrapper.getParams();

            // Go through the field passed and replace the message content
            // with the content of the fields key
            for ( Map.Entry<String,String> field : fields.entrySet() ) {

                // Replace the field.key ( placeholder ) with field.value ( value for the placeholder)
                content = content.replaceAll("(?i)"+field.getKey(),field.getValue());

            }
        }

        return content;

    }

    @Override
    public boolean transmitSMSNotifications(Customer customer,MessageWrapper messageWrapper) throws InspireNetzException{

        Long merchantNo=messageWrapper.getMerchantNo();

        //check whether SMS is enabled for the merchant
        boolean isSmsEnabled = merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_ENABLE_SMS, merchantNo);

        if(isSmsEnabled){

            //get the message content
            String messageContent = getMessageContents(customer,messageWrapper,MessageSpielChannel.SMS);

            if(messageContent == null ){

                log.error("transmitSMSNotifications : No spiel content available SpielName:"+messageWrapper.getSpielName()+" Mobile:"+messageWrapper.getMobile());

                return false;
            }

            // Set the params
            Map<String,String> smsParams = new HashMap<>(0);

            // Set the mobile
            smsParams.put("mobile",messageWrapper.getMobile());

            // Set the message
            smsParams.put("message",messageContent);

            //call the rest method
            APIResponseObject responseObject = transmitSMS( smsParams,messageWrapper.getMerchantNo());

            // Check the response object
            if ( responseObject == null || responseObject.getStatus().equals(APIResponseStatus.failed.name()) ) {

                log.error("transmitSMSNotifications : SMS Sending failed ,"+messageWrapper);

                return false;

            }
        }
        return true;
    }

    @Override
    public boolean transmitMailNotifications(Customer customer,MessageWrapper messageWrapper) throws InspireNetzException{

        Long merchantNo=messageWrapper.getMerchantNo();

        //check whether SMS is enabled for the merchant
        boolean isEmailEnabled = merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_ENABLE_EMAIL, merchantNo);

        if(isEmailEnabled){

            //get the message content
            String messageContent = getMessageContents(customer,messageWrapper,MessageSpielChannel.EMAIL);

            if(messageContent == null ){

                log.error("transmitMailNotifications : No spiel content available SpielName:"+messageWrapper.getSpielName()+" Mobile:"+messageWrapper.getMobile());

                return false;
            }

            String subject="";

            if(messageWrapper.getEmailSubject()==null || messageWrapper.getEmailSubject().equals("")){


                if(messageWrapper.getSpielName()==null||messageWrapper.getSpielName().equals("")){

                    subject="";

                }else{

                    //get the spiel description and put into as subject
                    MessageSpiel messageSpiel = messageSpielService.findByMsiNameAndMsiMerchantNo(messageWrapper.getSpielName(),merchantNo);

                    //check the spiel existing or not
                    if(messageSpiel !=null){

                        //put the description as subject
                        if(messageSpiel.getMsiDescription() ==null || messageSpiel.getMsiDescription().equals("")){

                            subject=messageWrapper.getSpielName().replace("_","");

                        }else {

                            //set spiel description
                            subject = messageSpiel.getMsiDescription();
                        }


                    }else {

                        subject=messageWrapper.getSpielName().replace("_","");

                    }


                }
            }else{

                subject=messageWrapper.getEmailSubject();
            }


            // Set the params
            Map<String,String> emailParams = new HashMap<>(0);

            // Set the mobile
            log.info("Received Mail Id #--------"+messageWrapper.getEmailId());

            if(messageWrapper.getEmailId() !=null && (!messageWrapper.getEmailId().equals(""))){

                emailParams.put("to",messageWrapper.getEmailId());

                emailParams.put("subject",subject);

                // Set the message
                emailParams.put("message",messageContent);

                //call the rest method
                APIResponseObject responseObject = transmitMail(emailParams, messageWrapper.getMerchantNo());

                // Check the response object
                if ( responseObject == null || responseObject.getStatus().equals(APIResponseStatus.failed.name()) ) {

                    log.error("transmitSMSNotifications : SMS Sending failed ,"+messageWrapper);

                    return false;

                }

            }

        }

        return true;
    }

    @Override
    public APIResponseObject transmitMail(Map<String, String> parameters,Long merchantNo) throws InspireNetzException {


        //check whether SMS is enabled for the merchant
        boolean isSmsEnabled = merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_ENABLE_EMAIL, merchantNo);

        APIResponseObject apiResponseObject = new APIResponseObject();

        //continue only if the setting is enabled for the merchant
        if(!isSmsEnabled){

            return null;

        } else {

            String to=parameters.get("to");

            if(to==null ||to.equals("")){

                log.error("transmitMail : parameter -to -Email -Empty");

                return null;

            }


            Properties properties=new Properties();

            MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);



            //get emailHostSettingId
            Long emailHostSettingId = settingService.getSettingsId(AdminSettingsConfiguration.MER_EMAIL_HOST);

            //get the link string of the merchant sms configuration
            MerchantSetting emailHostSetting = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,emailHostSettingId);

            if(emailHostSetting == null ){

                log.error("transmitMail : Mail Sending failed ,settings not defined for "+AdminSettingsConfiguration.MER_EMAIL_HOST);

                return null ;

            }else{

                mailSender.setHost(emailHostSetting.getMesValue());
            }

            //get emailPortSettingsId
            Long emailPortSettingsId = settingService.getSettingsId(AdminSettingsConfiguration.MER_EMAIL_PORT);

            //get the link string of the merchant sms configuration
            MerchantSetting emailPortSettings = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,emailPortSettingsId);

            if(emailPortSettings == null ){

                log.error("transmitMail : Mail Sending failed ,settings not defined for "+AdminSettingsConfiguration.MER_EMAIL_PORT);

                return null ;

            }else{

                mailSender.setPort(Integer.parseInt(emailPortSettings.getMesValue()));

            }

            //get emailUserNameSettingsId
            Long emailUserNameSettingsId = settingService.getSettingsId(AdminSettingsConfiguration.MER_EMAIL_USER_NAME);

            //get the link string of the merchant sms configuration
            MerchantSetting emailUserNameSettings = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,emailUserNameSettingsId);

            if(emailUserNameSettings == null ){

                log.error("transmitMail : Mail Sending failed ,settings not defined for "+AdminSettingsConfiguration.MER_EMAIL_USER_NAME);

                return null ;

            }else{

                mailSender.setUsername(emailUserNameSettings.getMesValue());

            }

            //get emailPasswordSettingsId
            Long emailPasswordSettingsId = settingService.getSettingsId(AdminSettingsConfiguration.MER_EMAIL_PASSWORD);

            //get the link string of the merchant sms configuration
            MerchantSetting emailPasswordSettings = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,emailPasswordSettingsId);

            if(emailPasswordSettings == null ){

                log.error("transmitMail : Mail Sending failed ,settings not defined for "+AdminSettingsConfiguration.MER_EMAIL_PASSWORD);

                return null ;

            }else{

                mailSender.setPassword(emailPasswordSettings.getMesValue());

            }

            //get emailReplyToSettingsId
            Long emailProtocolSettingsId = settingService.getSettingsId(AdminSettingsConfiguration.MER_EMAIL_PROP_PROTOCOL);

            //get the link string of the merchant sms configuration
            MerchantSetting emailProtocolSettings = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,emailProtocolSettingsId);

            if(emailProtocolSettings == null ){

                log.error("transmitMail : Mail Sending failed ,settings not defined for "+AdminSettingsConfiguration.MER_EMAIL_PROP_PROTOCOL);

                return null ;

            }else{

                properties.setProperty("mail.transport.protocol",emailProtocolSettings.getMesValue());
            }

            //get emailReplyToSettingsId
            Long emailSocketFactorySettingsId = settingService.getSettingsId(AdminSettingsConfiguration.MER_EMAIL_PROP_SMTP_SOCKETFACTORY_CLASS);

            //get the link string of the merchant sms configuration
            MerchantSetting emailSocketFactorySettings = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,emailSocketFactorySettingsId);

            if(emailSocketFactorySettings == null ){

                log.error("transmitMail : Mail Sending failed ,settings not defined for "+AdminSettingsConfiguration.MER_EMAIL_PROP_SMTP_SOCKETFACTORY_CLASS);

                return null ;

            }else{

                properties.setProperty("mail.smtp.socketFactory.class",emailSocketFactorySettings.getMesValue());

            }

            //get emailReplyToSettingsId
            Long emailEnableSSLSettingsId = settingService.getSettingsId(AdminSettingsConfiguration.MER_EMAIL_PROP_ENABLE_SSL);

            //get the link string of the merchant sms configuration
            MerchantSetting emailEnableSSLSettings = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,emailEnableSSLSettingsId);

            if(emailEnableSSLSettings == null ){

                log.error("transmitMail : Mail Sending failed ,settings not defined for "+AdminSettingsConfiguration.MER_EMAIL_PROP_ENABLE_SSL);

                return null ;

            }else{

                properties.setProperty("mail.smtp.starttls.enable",emailEnableSSLSettings.getMesValue());
            }

            //get emailReplyToSettingsId
            Long emailDebugEnabledSettingsId = settingService.getSettingsId(AdminSettingsConfiguration.MER_EMAIL_PROP_DEBUG_ENABLE);

            //get the link string of the merchant sms configuration
            MerchantSetting emailDebugEnabledSettings = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,emailDebugEnabledSettingsId);

            if(emailDebugEnabledSettings != null ){

                properties.setProperty("mail.debug",emailDebugEnabledSettings.getMesValue());
            }

            //get emailReplyToSettingsId
            Long emailSmtpAuthSettingsId = settingService.getSettingsId(AdminSettingsConfiguration.MER_EMAIL_PROP_SMTP_ENABLE_AUTH);

            //get the link string of the merchant sms configuration
            MerchantSetting emailSmtpAuthSettings = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,emailSmtpAuthSettingsId);

            if(emailSmtpAuthSettings != null ){

                properties.setProperty("mail.smtp.auth",emailSmtpAuthSettings.getMesValue());;
            }

            try{

                //get emailFromSettingsId
                Long emailFromSettingsId = settingService.getSettingsId(AdminSettingsConfiguration.MER_EMAIL_FROM);

                //get the link string of the merchant sms configuration
                MerchantSetting emailFromSettings = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,emailFromSettingsId);

                if(emailFromSettings != null ){

                    if(emailFromSettings.getMesValue()!=null&&!emailFromSettings.getMesValue().equals("")){

                        String from=emailFromSettings.getMesValue();

                       String fromAddress=from.split(",").length>0?from.split(",")[0]:"";

                        String fromName=from.split(",").length>1?from.split(",")[1]:"";

                        message.setFrom(new InternetAddress(fromAddress,fromName));
                    }


                }


                //get emailReplyToSettingsId
                Long emailReplyToSettingsId = settingService.getSettingsId(AdminSettingsConfiguration.MER_EMAIL_REPLY_TO);

                //get the link string of the merchant sms configuration
                MerchantSetting emailReplyToSettings = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,emailReplyToSettingsId);

                if(emailReplyToSettings != null ){

                    if(emailReplyToSettings.getMesValue()!=null&&!emailReplyToSettings.getMesValue().equals("")){

                        String reply=emailReplyToSettings.getMesValue();

                        String replyAddress=reply.split(",").length>0?reply.split(",")[0]:"";

                        String replyName=reply.split(",").length>1?reply.split(",")[1]:"";

                        message.setReplyTo(new InternetAddress(replyAddress,replyName));
                    }

                    //message.setReplyTo(new InternetAddress(emailReplyToSettings.getMesValue()));

                }

                String mailText=parameters.get("message");

                message.setText(mailText, true); 

                String subject=parameters.get("subject");

                message.setSubject(subject);

                message.setTo(new InternetAddress(to));

                mailSender.send(mimeMessage);

                apiResponseObject.setStatus(APIResponseStatus.success);

            }catch (AddressException e){

                log.error("transmitMail : Mail Sending failed mail id not well format");

                e.printStackTrace();

                return null;

            } catch (MessagingException e) {

                log.error("transmitMail : Mail Sending failed");

                e.printStackTrace();

                return null;

            } catch (UnsupportedEncodingException e) {
                log.error("transmitMail : Mail Sending failed:unsupported format");

                e.printStackTrace();

                return null;

            }catch (Exception e) {
                log.error("transmitMail : Mail Sending failed");

                e.printStackTrace();

                return null;
            }


        }

        return apiResponseObject;



    }

    @Override
    public boolean transmitBulkNotification(Integer channel, String to, String subject,Long merchantNo, String message){

        String[] messageTo=to.split(";");

        MessageWrapper messageWrapper=new MessageWrapper();

        messageWrapper.setIsCustomer(IndicatorStatus.NO);

        messageWrapper.setMessage(message);

        messageWrapper.setMerchantNo(merchantNo);

        messageWrapper.setEmailSubject(subject);

        if(channel==MessageSpielChannel.SMS){

            messageWrapper.setChannel(MessageSpielChannel.SMS);

            for (String toMin : messageTo){

                messageWrapper.setMobile(toMin);



                try{

                    transmitNotification(messageWrapper);

                }catch(InspireNetzException e){

                    log.error("transmitBulkNotification : SMS Sending failed for " +messageWrapper.toString());
                }

            }

        }else if(channel==MessageSpielChannel.EMAIL){

            messageWrapper.setChannel(MessageSpielChannel.EMAIL);

            for (String toEmail : messageTo){

                messageWrapper.setEmailId(toEmail);

                try{

                    transmitNotification(messageWrapper);

                }catch(InspireNetzException e){

                    log.error("transmitBulkNotification : Email Sending failed for " +messageWrapper.toString());

                }

            }

        }

        return true;
    }
}

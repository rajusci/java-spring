package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.OneTimePasswordRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class OneTimePasswordServiceImpl extends BaseServiceImpl<OneTimePassword> implements OneTimePasswordService {


    private static Logger log = LoggerFactory.getLogger(OneTimePasswordServiceImpl.class);


    @Autowired
    OneTimePasswordRepository oneTimePasswordRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserMessagingService userMessagingService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private CardMasterService cardMasterService;

    public OneTimePasswordServiceImpl() {

        super(OneTimePassword.class);

    }


    @Override
    protected BaseRepository<OneTimePassword,Long> getDao() {
        return oneTimePasswordRepository;
    }


    @Override
    public OneTimePassword findByOtpId(Long otpId) {

        // Get the oneTimePassword for the given oneTimePassword id from the repository
        OneTimePassword oneTimePassword = oneTimePasswordRepository.findByOtpId(otpId);

        // Return the oneTimePassword
        return oneTimePassword;


    }

    @Override
    public List<OneTimePassword> getOTPListForType(Long otpMerchantNo, Long otpCustomerNo, Integer otpType) {

        // Get the List of entries from the Repository methods
        List<OneTimePassword> oneTimePasswordList =  oneTimePasswordRepository.getOTPList(otpMerchantNo,otpCustomerNo,otpType);

        // Return the list
        return oneTimePasswordList;

    }

    /*@Override
    public Integer validateOTP(Long otpMerchantNo, Long otpCustomerNo, Integer otpType, String otpCode) {

        // Set the valid flag to false
        boolean isValid = false;

        // Get the list of the OTP's for the customer
        List<OneTimePassword> oneTimePasswordList  = getOTPListForType(otpMerchantNo,otpCustomerNo,otpType);

        // If the list is not there then return false
        if ( oneTimePasswordList == null || oneTimePasswordList.isEmpty() ) {

            // Log the information
            log.info("validateOTP -> There are no OTPs for the customer");

            // Return NO DATA
            return OTPStatus.OTP_NO_DATA;

        }


        // Get the first item ( as it is ordered)
        OneTimePassword oneTimePassword = oneTimePasswordList.get(0);

        // Check if the status of the first item is new
        if ( oneTimePassword.getOtpStatus() != OTPStatus.NEW ) {

            // Log the information
            log.info("validateOTP -> The OTP is not valid");

            // return OTP not valid
            return OTPStatus.OTP_NOT_VALID;

        }


        // Check if the OTP is expired.
        if ( isOTPExpired(oneTimePassword) ) {

            // Log the the information
            log.info("validateOTP - > OTP is expired");

            // Return the status
            return OTPStatus.EXPIRED;

        }

        if(!otpCode.equals(oneTimePassword.getOtpCode())){

            // Log the the information
            log.info("validateOTP - > OTP is invalid");

            // Return the status
            return OTPStatus.OTP_NOT_VALID;

        }

        return OTPStatus.VALIDATED;

    }
*/

    /**
     * Function to check if the expiryTimestamp of the object is more than the
     * current timestamp
     *
     * @param oneTimePassword   - The OneTimePassword which need to be checked for expiry
     * @return
     */
    protected boolean isOTPExpired(OneTimePassword oneTimePassword) {

        // Get the create timestamp for the oneTimePassword
        Timestamp expiryTimestamp = oneTimePassword.getOtpExpiry();

        // Create a timestamp for now
        Timestamp nowTimestamp = new Timestamp(new Date().getTime());

        // Compare the timestamp
        if ( expiryTimestamp.compareTo(nowTimestamp) <= 0 ) {

            return true;

        }  else {

            return false;

        }
    }

    @Override
    public OneTimePassword saveOneTimePassword(OneTimePassword oneTimePassword ){

        // Save the oneTimePassword
        return oneTimePasswordRepository.save(oneTimePassword);

    }

    @Override
    public boolean deleteOneTimePassword(Long otpId) {

        // Delete the oneTimePassword
        oneTimePasswordRepository.delete(otpId);

        // return true
        return true;

    }

    @Override
    public String  generateOTP(Long merchantNo,Long customerNo,Integer otpType) {

        OneTimePassword oneTimePassword = new OneTimePassword();

        //generating a 6 digit random number between 100000 and 999999
        Random random = new Random();

        int randomNumber = 100000 + random.nextInt(900000);

        //get the expiry time of otp
        int  expiryTime = Integer.parseInt(environment.getProperty("one_time_password.expiry.time"));

        //get the timestamp value
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        //variable gets the time from timestamp
        long sentTime = timestamp.getTime();

        //calendar obj for adding the hour limit to sent time
        Calendar calendarObj = Calendar.getInstance();

        //setting the sent time to calendar object
        calendarObj.setTimeInMillis(sentTime);

        //adding the maximum time availble for approving the request
        calendarObj.add(Calendar.SECOND,expiryTime);

        Timestamp otpExiry = new Timestamp(System.currentTimeMillis());

        //set the time to otpExpiry
        otpExiry.setTime(calendarObj.getTimeInMillis());

        //setting generated random number to otpCode string
        String otpCode = randomNumber+"";

        //set the values to the oneTImePassword Object
        oneTimePassword.setOtpCode(otpCode);
        oneTimePassword.setOtpCustomerNo(customerNo);
        oneTimePassword.setOtpMerchantNo(merchantNo);
        oneTimePassword.setOtpType(otpType);
        oneTimePassword.setOtpExpiry(otpExiry);

        //save the oneTimePassword
        oneTimePassword = saveOneTimePassword(oneTimePassword);

        //return the one time password
        return oneTimePassword.getOtpCode();

    }

    @Override
    public String  generateOTP(Long merchantNo,String otpReference, Integer otpRefType,Integer otpType) {

        OneTimePassword oneTimePassword = new OneTimePassword();

        //generating a 6 digit random number between 100000 and 999999
        Random random = new Random();

        int randomNumber = 100000 + random.nextInt(900000);

        //get the expiry time of otp
        int  expiryTime = Integer.parseInt(environment.getProperty("one_time_password.expiry.time"));

        //get the timestamp value
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        //variable gets the time from timestamp
        long sentTime = timestamp.getTime();

        //calendar obj for adding the hour limit to sent time
        Calendar calendarObj = Calendar.getInstance();

        //setting the sent time to calendar object
        calendarObj.setTimeInMillis(sentTime);

        //adding the maximum time availble for approving the request
        calendarObj.add(Calendar.SECOND,expiryTime);

        Timestamp otpExiry = new Timestamp(System.currentTimeMillis());

        //set the time to otpExpiry
        otpExiry.setTime(calendarObj.getTimeInMillis());

        //setting generated random number to otpCode string
        String otpCode = randomNumber+"";

        //set the values to the oneTImePassword Object
        oneTimePassword.setOtpCode(otpCode);
        oneTimePassword.setOtpCustomerNo(0L);
        oneTimePassword.setOtpMerchantNo(merchantNo);
        oneTimePassword.setOtpRefType(otpRefType);
        oneTimePassword.setOtpReference(otpReference);
        oneTimePassword.setOtpType(otpType);
        oneTimePassword.setOtpExpiry(otpExiry);

        //save the oneTimePassword
        oneTimePassword = saveOneTimePassword(oneTimePassword);

        //return the one time password
        return oneTimePassword.getOtpCode();

    }

    @Override
    public boolean generateOTPCompatible(Long merchantNo, String loyaltyId, Integer otpType) throws InspireNetzException{


        if(otpType==OTPType.CHARGE_CARD_PAYMENT||otpType==OTPType.CHARGE_CARD_REFUND){

            CardMaster cardMaster=cardMasterService.findByCrmMerchantNoAndCrmCardNo(merchantNo,loyaltyId);

            if(cardMaster==null || cardMaster.getCrmCardNo()==null){

                // Log the information
                log.info("generateOTPCompatible - No customer information found");

                // Throw the exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

            }

            //generate otp for validation
            boolean isOTPGenerated=generateOTPGeneric(merchantNo, OTPRefType.CARDMASTER, cardMaster.getCrmId().toString(), otpType);

            // return true
            return isOTPGenerated;


        }

        Customer customer=customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        // Make sure the customer exists
        if ( customer == null || customer.getCusCustomerNo() == null ) {

            // Log the information
            log.info("generateOTPCompatible - No customer information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Check if the customer belongs to the merchant by the merchant user
        if ( customer.getCusMerchantNo().longValue() != merchantNo ) {

            // Log the information
            log.info("generateOTPCompatible - Customer does not belong to you");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }
/*

        //generate otp for validation
        String otpCode=generateOTP(merchantNo,customer.getCusCustomerNo(),otpType);

        if(otpCode==null){

            //return false
            return false;
        }

        //create a map for the sms placeholders
        HashMap<String , String > smsParams  = new HashMap<>(0);

        //put the placeholders into the map
        smsParams.put("#otpCode",otpCode);


        //send the otp to the user
        userMessagingService.sendSMS(MessageSpielValue.CHARGE_CARD_PAYMENT_OTP,loyaltyId,smsParams);
*/

        //generate otp for validation
        boolean isOTPGenerated=generateOTPGeneric(merchantNo, OTPRefType.CUSTOMER, customer.getCusCustomerNo().toString(), otpType);

        // return true
        return isOTPGenerated;

    }

    public OneTimePassword findByOtpCode(Long merchantNo, String otpCode) {
        return oneTimePasswordRepository.findByOtpCodeAndOtpMerchantNo(otpCode,merchantNo);

    }

    @Override
    public OneTimePassword findByOtpMerchantNoAndOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(Long otpMerchantNo,Integer otpRefType,String otpReference,Integer otpType) {

        // Get the oneTimePassword for the given oneTimePassword id from the repository
        List<OneTimePassword> oneTimePasswords = oneTimePasswordRepository.findByOtpMerchantNoAndOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(otpMerchantNo, otpRefType, otpReference, otpType);



        // Return the oneTimePassword
        return (oneTimePasswords.isEmpty()?null:oneTimePasswords.get(0));


}
    
    @Override
    public boolean generateOTPGeneric(Long merchantNo, Integer otpRefType, String otpReference, Integer otpType) throws InspireNetzException{

        OneTimePassword oneTimePassword = new OneTimePassword();

        //generating a 6 digit random number between 100000 and 999999
        Random random = new Random();

        int randomNumber = 100000 + random.nextInt(900000);

        //get the expiry time of otp
        int  expiryTime = Integer.parseInt(environment.getProperty("one_time_password.expiry.time"));

        //get the timestamp value
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        //variable gets the time from timestamp
        long sentTime = timestamp.getTime();

        //calendar obj for adding the hour limit to sent time
        Calendar calendarObj = Calendar.getInstance();

        //setting the sent time to calendar object
        calendarObj.setTimeInMillis(sentTime);

        //adding the maximum time available for approving the request
        calendarObj.add(Calendar.SECOND,expiryTime);

        Timestamp otpExpiry = new Timestamp(System.currentTimeMillis());

        //set the time to otpExpiry
        otpExpiry.setTime(calendarObj.getTimeInMillis());

        //setting generated random number to otpCode string
        String otpCode = randomNumber+"";

        //set the values to the oneTImePassword Object
        oneTimePassword.setOtpCode(otpCode);
        oneTimePassword.setOtpMerchantNo(merchantNo);
        oneTimePassword.setOtpType(otpType);
        oneTimePassword.setOtpExpiry(otpExpiry);
        oneTimePassword.setOtpRefType(otpRefType);
        oneTimePassword.setOtpReference(otpReference);
        oneTimePassword.setOtpCustomerNo(0L);

        //save the oneTimePassword
        oneTimePassword = saveOneTimePassword(oneTimePassword);

        // Make sure the customer exists
        if ( oneTimePassword == null || oneTimePassword.getOtpId() == null ) {

            // Log the information
            log.info("generateOTPGeneric - otp generation failed");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
        }

        //Variable to hold target min
        String targetMin="";

        String targetEMail="";

        //check reference type is customer
        if(otpRefType== OTPRefType.CUSTOMER)
        {
            Long customerNo=Long.parseLong(otpReference);

            Customer customer=customerService.findByCusCustomerNo(customerNo);

            // Make sure the customer exists
            if ( customer == null || customer.getCusCustomerNo() == null ) {

                // Log the information
                log.info("generateOTPGeneric - No customer information found");

                // Throw the exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
            }

            // Make sure the customer mobile number exists
            if ( customer.getCusMobile() == null || customer.getCusMobile() == "" ) {

                // Log the information
                log.info("generateOTPGeneric - No customer mobile number found");

                // Throw the exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_MIN_DATA);
            }

            //set target min
            targetMin=customer.getCusMobile();

            targetEMail=customer.getCusEmail();

        }else if(otpRefType==OTPRefType.USER){

            Long userNo=Long.parseLong(otpReference);

            User user=userService.findByUsrUserNo(userNo) ;

            // Make sure the user exists
            if ( user == null || user.getUsrUserNo() == null ) {

                // Log the information
                log.info("generateOTPGeneric - No user information found");

                // Throw the exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
            }

            // Make sure the user mobile number exists
            if ( user.getUsrMobile() == null || user.getUsrMobile() == "" ) {

                // Log the information
                log.info("generateOTPGeneric - No user mobile number found");

                // Throw the exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_MIN_DATA);
            }

            //set targetMin
            targetMin=user.getUsrMobile();

            targetEMail="";

        }else if(otpRefType==OTPRefType.CARDMASTER){

            Long cardMasterId=Long.parseLong(otpReference);

            CardMaster cardMaster=cardMasterService.findByCrmId(cardMasterId) ;

            // Make sure the user exists
            if ( cardMaster == null || cardMaster.getCrmCardNo() == null ) {

                // Log the information
                log.info("generateOTPGeneric - No card information found");

                // Throw the exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
            }

            // Make sure the user mobile number exists
            if ( cardMaster.getCrmMobile() == null || cardMaster.getCrmMobile() == "" ) {

                // Log the information
                log.info("generateOTPGeneric - No mobile number found in card details");

                // Throw the exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_MIN_DATA);
            }

            //set targetMin
            targetMin=cardMaster.getCrmMobile();

            targetEMail=cardMaster.getCrmEmailId();
        }else if(otpRefType==OTPRefType.PUBLIC){

            //set targetMin
            targetMin=otpReference;

            targetEMail="";
        }



        //create a map for the sms placeholders
        HashMap<String , String > smsParams  = new HashMap<>(0);

        //put the placeholders into the map
        smsParams.put("#otpCode",oneTimePassword.getOtpCode());

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",targetMin,targetMin,targetEMail,"",merchantNo,smsParams,MessageSpielChannel.ALL,IndicatorStatus.NO);

        //Send Message Based on otpType
        if(otpType==OTPType.CASH_BACK_REQUEST){

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CASH_BACK_REQUEST_OTP);

            //send the otp to the user
            userMessagingService.transmitNotification(messageWrapper);


        }else if(otpType==OTPType.CHARGE_CARD_PAYMENT){

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CHARGE_CARD_PAYMENT_OTP);

            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            //send the otp to the user
            userMessagingService.transmitNotification(messageWrapper);


        }else if(otpType==OTPType.REGISTER_CUSTOMER){

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CUSTOMER_REGISTRATION_OTP);

            //set is not customer flag is true
            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            //send the otp to the user
            userMessagingService.transmitNotification(messageWrapper);

        }else if(otpType==OTPType.FORGOT_PASSWORD){

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CUSTOMER_FORGOT_PASSWORD_OTP);

            //set spiel is customer to no
            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            //send the otp to the user
            userMessagingService.transmitNotification(messageWrapper);

        }else if(otpType==OTPType.TRANSFER_POINTS){

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINTS_OTP);


            //send the otp to the user
            userMessagingService.transmitNotification(messageWrapper);


        }else if(otpType==OTPType.REDEMPTION){

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.REDEMPTION_OTP);

            //send the otp to the user
            userMessagingService.transmitNotification(messageWrapper);

        }else if(otpType==OTPType.REGISTER_USER){

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CUSTOMER_REGISTRATION_OTP);

            //set is not customer flag is true
            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            //send the otp to the user
            userMessagingService.transmitNotification(messageWrapper);

        }else if(otpType==OTPType.PUBLIC_CARD_REGISTER){

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.PUBLIC_CARD_REGISTER_OTP);

            //set is not customer flag is true
            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            //send the otp to the user
            userMessagingService.transmitNotification(messageWrapper);

        }else if(otpType==OTPType.CHARGE_CARD_BALANCE_OTP){

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CHARGE_CARD_BALANCE_OTP);

            //set is not customer flag is true
            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            //send the otp to the user
            userMessagingService.transmitNotification(messageWrapper);

        }else if(otpType ==OTPType.CHARGE_CARD_AMOUNT_TRANSFER_OTP){


            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CHARGE_CARD_AMOUNT_TRANSFER_OTP);

            //set is not customer flag is true
            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            //send the otp to the user
            userMessagingService.transmitNotification(messageWrapper);

        }else if(otpType == OTPType.PAY_POINTS){

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.PAY_POINTS_OTP);

            //set is Customer flag is false
            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            //send the OTP to the user
            userMessagingService.transmitNotification(messageWrapper);

        }else if(otpType==OTPType.CHARGE_CARD_REFUND){

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CHARGE_CARD_REFUND_OTP);

            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            //send the otp to the user
            userMessagingService.transmitNotification(messageWrapper);


        }

        //return the one time password
        return true;

    }

    @Override
    public Integer validateOTPGeneric(Long otpMerchantNo, Integer otpRefType, String otpReference, Integer otpType, String otpCode) {

        // Set the valid flag to false
        boolean isValid = false;

        // Get the list of the OTP's foOneTimePassword fetchedOneTimePassword = oneTimePasswordRepository.findByOtpMerchantNoAndOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(oneTimePassword.getOtpMerchantNo(),oneTimePassword.getOtpRefType(), oneTimePassword.getOtpReference(), oneTimePassword.getOtpType());

        List<OneTimePassword> oneTimePasswords  = oneTimePasswordRepository.findByOtpMerchantNoAndOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(otpMerchantNo,otpRefType,otpReference,otpType);

        // If the list is not there then return false
        if ( oneTimePasswords == null ||oneTimePasswords.isEmpty() ) {

            // Log the information
            log.info("validateOTP -> There are no OTPs for the customer");

            // Return NO DATA
            return OTPStatus.OTP_NO_DATA;

        }

        OneTimePassword oneTimePassword=oneTimePasswords.get(0);


        // Check if the status of the first item is new
        if ( oneTimePassword.getOtpStatus() != OTPStatus.NEW ) {

            // Log the information
            log.info("validateOTP -> The OTP is not valid");

            // return OTP not valid
            return OTPStatus.OTP_NOT_VALID;

        }


        // Check if the OTP is expired.
        if ( isOTPExpired(oneTimePassword) ) {

            // Log the the information
            log.info("validateOTP - > OTP is expired");

            // Return the status
            return OTPStatus.EXPIRED;

        }

        if(!otpCode.equals(oneTimePassword.getOtpCode())){

            // Log the the information
            log.info("validateOTP - > OTP is invalid");

            // Return the status
            return OTPStatus.OTP_NOT_VALID;

        }

        return OTPStatus.VALIDATED;

    }

    @Override
    public boolean generateOTPForPartnerRequest(Long merchantNo, String mobile, Integer otpType) throws InspireNetzException {

        Customer customer = customerService.findByCusMobileAndCusMerchantNo(mobile,merchantNo);

        if(customer == null){

            //log error
            log.error("generateOTPForPartnerPayRequest : No customer information found");

            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }

        //Generate otpCode
        boolean isGenerated=generateOTPCompatible(merchantNo, customer.getCusMobile(), otpType);

        //return the status
        return isGenerated;

    }


}

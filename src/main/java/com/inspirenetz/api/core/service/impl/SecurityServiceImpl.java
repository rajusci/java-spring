package com.inspirenetz.api.core.service.impl;


import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by saneeshci on 07/10/15.
 */

@Service
public class SecurityServiceImpl implements SecurityService {

    private static Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserMessagingService userMessagingService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    PinFormatValidation pinFormatValidation;

    @Autowired
    MerchantService merchantService;

    public SecurityServiceImpl() {


    }

    @Override
    @CacheEvict(value = {"customer","customers"},allEntries = true)
    public boolean enablePinForCustomer(String loyaltyId,String pin,Long merchantNo,int type) throws InspireNetzException {

        MessageWrapper messageWrapper = getMessageWrapperObject(loyaltyId,merchantNo);

        Merchant merchant = merchantService.findByMerMerchantNo(merchantNo);

        if(merchant.getMerPinEnabled() != null && merchant.getMerPinEnabled() == PinSecurityLevel.DISABLED){

            log.error("enablePinForCustomer : Pin is globally disabled by merchant");

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.PIN_FUNCTIONALITY_NOT_ENABLED_GLOBALLY);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_PIN_FUNCTIONALITY_NOT_ENABLED_GLOBALLY);

        }
        //check the customer details
        User user = validateCustomerDetails(loyaltyId,merchantNo);


        //check if the pin already enabled for the customer
        if(user.getUsrPinEnabled() != null && user.getUsrPinEnabled() == IndicatorStatus.YES){

            //log error
            log.error("enablePinForCustomer : Pin already enabled for the customer");

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.PIN_ALREADY_ENABLED);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_PIN_ALREADY_ENABLED);
        }

        //denotes whether the pin is system generated or customer specified
        boolean isPinGenerated = false;

        //String generatedPin
        String generatedPin = pin;

        //check if the pin is null or invalid
        if(pin == null || pin.length()==0){

         /*   //generate pin for the loyalty id
            pin = generatePinForCustomer();

            //copy the generated pin for sms sending
            generatedPin = pin;

            isPinGenerated = true;*/

            log.error("enablePinForCustomer : Pin is mandatory, please pass pin and try again");

            messageWrapper.setSpielName(MessageSpielValue.PIN_IS_MANDATORY);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

            //throw error
            throw new InspireNetzException(APIErrorCode.ERR_PIN_IS_MANDATORY);

        } else {

            //validate the customer provided pin
            boolean isValid = pinFormatValidation.isPinFormatValid(pin);

            //throw error and send notification is pin is not valid
            if(!isValid){

                //set spiel name
                messageWrapper.setSpielName(MessageSpielValue.PIN_FORMAT_IS_NOT_VALID);

                //send notification
                userMessagingService.transmitNotification(messageWrapper);

                //throw error
                throw new InspireNetzException(APIErrorCode.ERR_INVALID_PIN_FORMAT);

            }

        }


        //encrypt the generated pin
        pin = encryptGeneratedPin(pin);

        //set pin enabled to true
        user.setUsrPinEnabled(IndicatorStatus.YES);

        //set the generated pin to the user
        user.setUsrPin(pin);

        //save the user
        user = userService.saveUser(user);

        if(isPinGenerated){

            //send sms with the generated pin
            messageWrapper.setSpielName(MessageSpielValue.PIN_GENERATION_SUCCESS);

            //sms params
            HashMap<String,String> smsParams = new HashMap<>(0);

            //add pin to the params list
            smsParams.put("#pin",generatedPin);

            //set map as sms params
            messageWrapper.setParams(smsParams);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);


        } else {

            //sms params
            HashMap<String,String> smsParams = new HashMap<>(0);

            //add pin to the params list
            smsParams.put("#pin",generatedPin);

          /*  //set map as sms params
            messageWrapper.setSmsParams(smsParams);*/

            if(type == PinOperationType.ENABLE){

                //send sms for successful pin enbaling
                messageWrapper.setSpielName(MessageSpielValue.PIN_ENABLE_SUCCESS);

            } else {

                //send sms for successful pin enbaling
                messageWrapper.setSpielName(MessageSpielValue.PIN_MODIFY_SUCCESS);

            }

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

        }

        return true;

    }

    @Override
    @CacheEvict(value = {"customer","customers"},allEntries = true)
    public boolean disablePinForCustomer(String loyaltyId,Long merchantNo,int type) throws InspireNetzException {

        MessageWrapper messageWrapper = getMessageWrapperObject(loyaltyId,merchantNo);

        User user = validateCustomerDetails(loyaltyId,merchantNo);



        //check if the pin already enabled for the customer
        if(user.getUsrPinEnabled() == null || user.getUsrPinEnabled() == IndicatorStatus.NO){

            //log error
            log.error("disablePinForCustomer : Pin already disabled for the customer");

            messageWrapper.setSpielName(MessageSpielValue.PIN_ALREADY_DISABLED);

            userMessagingService.transmitNotification(messageWrapper);

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_PIN_ALREADY_DISABLED);
        }

        //set pin enabled to false
        user.setUsrPinEnabled(IndicatorStatus.NO);

        //set pin as null
        user.setUsrPin(null);

        //save user details
        user = userService.saveUser(user);

        if(type == PinOperationType.DISABLE){

            //send sms for successful pin enbaling
            messageWrapper.setSpielName(MessageSpielValue.PIN_RESET_SUCCESS);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

        }

        log.info("disablePinForCustomer : Pin disabled successfully");

        //return true
        return true;
    }

    @Override
    public boolean validatePinForCustomer(String loyaltyId, String pin,Long merchantNo) throws InspireNetzException {

        try{

            //get the user details
            User user = validateCustomerDetails(loyaltyId,merchantNo);

            if(user.getUsrPinEnabled() == null || user.getUsrPinEnabled() == IndicatorStatus.NO){

                MessageWrapper messageWrapper = getMessageWrapperObject(loyaltyId,merchantNo);

                //log error
                log.error("validatePinForCustomer : Pin is not enabled");

                messageWrapper.setSpielName(MessageSpielValue.PIN_ALREADY_DISABLED);

                userMessagingService.transmitNotification(messageWrapper);

                //throw exception
                throw new InspireNetzException(APIErrorCode.ERR_PIN_ALREADY_DISABLED);
            }
            //get the encrypted pin for the provided pin
            String encryptedPin = encryptGeneratedPin(pin);

            //check if the pins are matching
            if(encryptedPin.equals(user.getUsrPin())){

                log.info("validatePinForCustomer : Pin validated successfully");
                return true;
            }

            log.info("validatePinForCustomer : Pin received is invalid , validation failed");

            //return false
            return false;

        } catch(InspireNetzException e){

            throw new InspireNetzException(e.getErrorCode());

        } catch(Exception e){

            return false;
        }

    }

    @Override
    public boolean resetPinForCustomer(String loyaltyId, Long merchantNo) throws InspireNetzException {

       /* //check the user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_RESET_PIN);
*/
        //disable the current pin
        boolean isDisabled = disablePinForCustomer(loyaltyId,merchantNo,PinOperationType.DISABLE);

        //return true
        return isDisabled;

    }

    @Override
    public boolean disablePinWithValidation(String loyaltyId, String pin, Long merchantNo, int type) throws InspireNetzException {

        boolean isPinValid = validatePinForCustomer(loyaltyId,pin,merchantNo);

        MessageWrapper messageWrapper = getMessageWrapperObject(loyaltyId,merchantNo);

        if(isPinValid){

            boolean isDisabled = disablePinForCustomer(loyaltyId,merchantNo,type);

            if(isDisabled){

                log.info("disablePinWithValidation : Pin disabled successfully");
            }

        } else {

            //log error
            log.error("disablePinWithValidation : Pin is invalid");

            messageWrapper.setSpielName(MessageSpielValue.PIN_IS_INVALID);

            userMessagingService.transmitNotification(messageWrapper);

            //throw exception
            throw new InspireNetzException(APIErrorCode.PIN_IS_INVALID);

        }

        return true;
    }

    @Override
    public boolean resetPinWithValidation(String loyaltyId, String oldPin, String newPin, Long merchantNo) throws InspireNetzException {

        boolean isPinValid = validatePinForCustomer(loyaltyId,oldPin,merchantNo);

        MessageWrapper messageWrapper = getMessageWrapperObject(loyaltyId,merchantNo);

        if(isPinValid){

            boolean isGenerated = enablePinForCustomer(loyaltyId,newPin,merchantNo,PinOperationType.RESET);

            if(isGenerated){

                log.info("resetPinWithValidation : Pin generation success");
            }

        } else {

            //log error
            log.error("resetPinWithValidation : Pin is invalid");

            messageWrapper.setSpielName(MessageSpielValue.PIN_IS_INVALID);

            userMessagingService.transmitNotification(messageWrapper);

            //throw exception
            throw new InspireNetzException(APIErrorCode.PIN_IS_INVALID);

        }

        return true;
    }

    private MessageWrapper getMessageWrapperObject(String loyaltyId,Long merchantNo) {

        //get a new object of message wrapper
        MessageWrapper messageWrapper = new MessageWrapper();

        //set the field values
        messageWrapper.setChannel(MessageSpielChannel.SMS);
        messageWrapper.setMerchantNo(merchantNo);
        messageWrapper.setLoyaltyId(loyaltyId);

        return messageWrapper;
    }

    private String encryptGeneratedPin(String pin) {

        // The encodedPassword
        String encPassword = null;


        try {

            // Get the MessageDigest instnace
            MessageDigest messageDigest = null;

            // Get the MD5 instance
            messageDigest = MessageDigest.getInstance("MD5");

            // Create the byteArray
            byte digest[] = messageDigest.digest(pin.getBytes("UTF-8"));

            // Create the string builder with double the size of the digest
            StringBuilder sb = new StringBuilder(2*digest.length);

            // Go through each of the byte and convert to hexadecimal
            for(byte b : digest){

                sb.append(String.format("%02x", b&0xff));

            }

            // Get the encodedPassword from the StringBuilder object
            encPassword =sb.toString();


        } catch (NoSuchAlgorithmException e) {

            // Log as error
            log.info("getEncodedPassword -> No such alogirhtm ");

            // Print stack trace
            e.printStackTrace();

        } catch (UnsupportedEncodingException e) {

            // Log as error
            log.info("getEncodedPassword -> Unsupported encoding exception ");

            // Print stack trace
            e.printStackTrace();

        }


        // Return the encoded password
        return encPassword;

    }

    private String generatePinForCustomer() {

        int pin = (int)(Math.random()*9000)+1000;

        String generatedPin = String.valueOf(pin);

        return generatedPin;
    }

    private User validateCustomerDetails(String loyaltyId,Long merchantNo) throws InspireNetzException {

        //get the customer information
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        //if customer is null or not active , throw error
        if(customer == null || customer.getCusStatus() != CustomerStatus.ACTIVE){

            //log error
            log.error("disablePinForCustomer : Customer is not active ");

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);
        }

        //get the user details for the customer
        User user = userService.findByUsrLoginId(loyaltyId);

        //if user is null or empty throw exception
        if(user == null){

            //log error
            log.error("disablePinForCustomer : User is not active ");

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);
        }

        return user;
    }
}
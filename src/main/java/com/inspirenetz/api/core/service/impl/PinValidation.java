package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.core.service.SecurityService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.core.service.ValidationService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by saneeshci on 09/10/15.
 */
@Service
public class PinValidation implements ValidationService {


    // Create the logger
    private static Logger log = LoggerFactory.getLogger(PinValidation.class);

    @Autowired
    private UserService userService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    SecurityService securityService;

    @Autowired
    MerchantService merchantService;


    // Constructor
    public PinValidation(UserService userService,SecurityService securityService,MerchantService merchantService) {

        this.userService = userService;

        this.securityService = securityService;

        this. merchantService = merchantService;

    }

    // Constructor
    public PinValidation() {

    }


    @Override
    public ValidationResponse isValid(ValidationRequest validationRequest) throws InspireNetzException {

        ValidationResponse validationResponse = new ValidationResponse();

        if(validationRequest.isApprovalRequest()){

            validationResponse.setValid(true);

            return validationResponse;
        }
        //get the merchant details
        Merchant merchant = merchantService.findByMerMerchantNo(validationRequest.getMerchantNo());

        //get the security level for merchant
        int pinSecurityLevel = merchant.getMerPinEnabled() == null ? 0: merchant.getMerPinEnabled();

        if(pinSecurityLevel == PinSecurityLevel.DISABLED){

            if(validationRequest.getPin() != null && validationRequest.getPin().length() > 0){

                validationResponse.setValid(false);

                //set remarks
                validationResponse.setValidationRemarks("Pin is not enabled for user");

                //set error code
                validationResponse.setApiErrorCode(APIErrorCode.ERR_PIN_NOT_ENABLED);

                validationResponse.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_PIN_NOT_ENABLED_GLOBALLY);

                return validationResponse;

            } else {

                //log that the pin is not enabled for the merchant
                log.info("isValid : Pin is not enabled for merchant");

                //set valid to true
                validationResponse.setValid(true);

                //return validtion response
                return validationResponse;
            }


        }

        //get the user details
        User user = userService.findByUsrLoginId(validationRequest.getLoyaltyId());

        //check if the user is valid
        if(user == null ){

            //set request status to false
            validationResponse.setValid(false);

            //set remarks
            validationResponse.setValidationRemarks("User is not active");

            //set error code
            validationResponse.setApiErrorCode(APIErrorCode.ERR_USER_NOT_FOUND);

            //return response
            return validationResponse;
        }

        if(pinSecurityLevel == PinSecurityLevel.ENABLED ){

            if((user.getUsrPinEnabled() == null || user.getUsrPinEnabled() == IndicatorStatus.NO)){

                validationResponse.setValid(false);

                //set remarks
                validationResponse.setValidationRemarks("Please enable pin and retry");

                //set error code
                validationResponse.setApiErrorCode(APIErrorCode.ERR_PIN_ENABLED_GLOBALLY);

                validationResponse.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_PIN_MANDATORY_GLOBAL);

                return validationResponse;

            } else if(user.getUsrPinEnabled() == IndicatorStatus.YES && validationRequest.getPin() == null && validationRequest.getPin().length() == 0){

                //set valid flag as false
                validationResponse.setValid(false);

                //set remarks
                validationResponse.setValidationRemarks("Pin is missing , validation failed");

                //set error code
                validationResponse.setApiErrorCode(APIErrorCode.ERR_PIN_MANDATORY);

                validationResponse.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_PIN_MANDATORY);

                return validationResponse;

            } else {

                validationResponse = validatePinForRequest(validationRequest);

                return validationResponse;
            }

        } else if(pinSecurityLevel == PinSecurityLevel.CUSTOMER_BASED){

            if((user.getUsrPinEnabled() == null || user.getUsrPinEnabled() == IndicatorStatus.NO)){

                validationResponse.setValid(false);

                //set remarks
                validationResponse.setValidationRemarks("Please enable pin and retry");

                //set error code
                validationResponse.setApiErrorCode(APIErrorCode.ERR_PIN_NOT_ENABLED);

                validationResponse.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_PIN_NOT_ENABLED);

                return validationResponse;

            } else if(user.getUsrPinEnabled() == IndicatorStatus.YES && validationRequest.getPin() == null && validationRequest.getPin().length() != 0){

                //set valid flag as false
                validationResponse.setValid(false);

                //set remarks
                validationResponse.setValidationRemarks("Pin is missing , validation failed");

                //set error code
                validationResponse.setApiErrorCode(APIErrorCode.ERR_PIN_MANDATORY);

                validationResponse.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_PIN_MANDATORY);

                return validationResponse;

            } else {

                validationResponse = validatePinForRequest(validationRequest);

                return validationResponse;
            }
        }


        //return validtion response
        return validationResponse;


    }

    private ValidationResponse validatePinForRequest(ValidationRequest validationRequest) throws InspireNetzException {

        //call the security service method for validationg the pin
        boolean isPinValid = securityService.validatePinForCustomer(validationRequest.getLoyaltyId(),validationRequest.getPin(),validationRequest.getMerchantNo());

        ValidationResponse validationResponse = new ValidationResponse();

        //check if the validation is successfull
        if(!isPinValid){

            //set validity as false
            validationResponse.setValid(false);

            //set error remarks
            validationResponse.setValidationRemarks("Pin is not valid , validation failed");

            //set error code
            validationResponse.setApiErrorCode(APIErrorCode.ERR_INCORRECT_PIN);

            validationResponse.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_PIN_INCORRECT);

            return validationResponse;
        }

        //set valid to true
        validationResponse.setValid(true);

        //return validtion response
        return validationResponse;
    }
}

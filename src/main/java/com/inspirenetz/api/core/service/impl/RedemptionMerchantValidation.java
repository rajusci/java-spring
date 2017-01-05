package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.core.service.RedemptionMerchantService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.core.service.ValidationService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by saneeshci on 16/09/15.
 */
@Service
public class RedemptionMerchantValidation implements ValidationService {


    // Create the logger
    private static Logger log = LoggerFactory.getLogger(RedemptionMerchantValidation.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    RedemptionMerchantService redemptionMerchantService;

    // Constructor
    public RedemptionMerchantValidation(RedemptionMerchantService redemptionMerchantService, UserService userService) {

        this.redemptionMerchantService = redemptionMerchantService;

        this.userService = userService;
    }

    //construction
    public RedemptionMerchantValidation(){


    }
    @Override
    public ValidationResponse isValid(ValidationRequest validationRequest) throws InspireNetzException {

        ValidationResponse validationResponse = new ValidationResponse();

        //get the customer details for the request
        Customer customer = validationRequest.getCustomer();

        //check if the identifier value is ava
        if(validationRequest.getMerchantIdentifier() == null){

            //set error fields
            validationResponse = setErrorFields(validationResponse);

            //log the error
            log.error("isValid : No merchant identifier found "+validationRequest);

            //set remark
            validationResponse.setValidationRemarks("Merchant code is mandatory");

            //return the response object
            return validationResponse;
        }

        //get the redemption merchant details with the provided merchant code
        RedemptionMerchant redemptionMerchant = redemptionMerchantService.findByRemCode(validationRequest.getMerchantIdentifier());

        //check if the merchant identifier type is merchant code
        if(!validationRequest.getCashBackType().equals("default")){


            //check if merchant is null or not
            if(redemptionMerchant == null){

                //set error fields
                validationResponse = setErrorFields(validationResponse);

                //set remarks
                validationResponse.setValidationRemarks("No merchant information available");

            } else {

                //set valid to true
                validationResponse.setValid(true);

                //set redemption merchant
                validationResponse.setRedemptionMerchant(redemptionMerchant);

            }

        } else {

            //check if the user is null or is active
            if(redemptionMerchant == null || redemptionMerchant.getRemStatus() != RedemptionMerchantStatus.ACCOUNT_ACTIVE){

                //set validity flag to false
                validationResponse.setValid(false);

                //set api error code to user not valid
                validationResponse.setApiErrorCode(APIErrorCode.ERR_INVALID_REDEMPTION_MERCHANT);

                validationResponse.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_REM_MERCHANT_NOT_VALID);

            }

            if(redemptionMerchant.getRemSettlementLevel().equals(MerchantSettlementLevel.MERCHANT_LEVEL)){

                validationResponse.setValid(true);

                validationResponse.setRedemptionMerchant(redemptionMerchant);

                return validationResponse;

            }

            //Get the redemption user details for the provided cachier mobile
            String merchantLoginId = validationRequest.getRef();

            //get the user details for the login id
            User merchantUser  = userService.findByUsrLoginId(merchantLoginId);

            //check if the user is null or is active
            if(merchantUser == null || merchantUser.getUsrStatus() != UserStatus.ACTIVE){

                //set validity flag to false
                validationResponse.setValid(false);

                //set api error code to user not valid
                validationResponse.setApiErrorCode(APIErrorCode.ERR_REDEMPTION_MERCHANT_USER_NOT_VALID);

                validationResponse.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_REM_MERCHANT_USER_NOT_VALID);

                return validationResponse;
            }

            //get the redemption merchant information for the user
            RedemptionMerchant redemptionMerchantForUser = redemptionMerchantService.findByRemNo(merchantUser.getUsrThirdPartyVendorNo());

            //check if a redemption merchant exists
            if(redemptionMerchantForUser == null){

                //set valid flag to false
                validationResponse.setValid(false);

                //set remarks as merchant information not found
                validationResponse.setValidationRemarks("No redemption merchant information found");

                //set error code
                validationResponse.setApiErrorCode(APIErrorCode.ERR_REDEMPTION_MERCHANT_USER_NOT_VALID);

                validationResponse.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_REM_MERCHANT_USER_NOT_VALID);

                return validationResponse;
            }

            if(redemptionMerchant.getRemNo().longValue() != redemptionMerchantForUser.getRemNo().longValue()){

                //set valid flag to false
                validationResponse.setValid(false);

                //set remarks as merchant information not found
                validationResponse.setValidationRemarks("Merchant identifier not matches with provided reference account");

                //set error code
                validationResponse.setApiErrorCode(APIErrorCode.ERR_REDEMPTION_MERCHANT_USER_NOT_VALID);

                validationResponse.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_REM_MERCHANT_USER_NOT_VALID);

                return validationResponse;
            }

            //set redemption merchant
            validationResponse.setRedemptionMerchant(redemptionMerchant);

            //set validity flag to true
            validationResponse.setValid(true);

        }

        //return response object
        return validationResponse;
    }


    public ValidationResponse setErrorFields(ValidationResponse validationResponse){

        //set api error code to invalid redemption  merchant
        validationResponse.setApiErrorCode(APIErrorCode.ERR_INVALID_REDEMPTION_MERCHANT);

        //set validity flag to false
        validationResponse.setValid(false);

        //return the response object
        return validationResponse;

    }
}

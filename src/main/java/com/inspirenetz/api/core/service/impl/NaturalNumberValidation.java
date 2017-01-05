package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.MessageSpielValue;
import com.inspirenetz.api.core.dictionary.ValidationRequest;
import com.inspirenetz.api.core.dictionary.ValidationResponse;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.core.service.ValidationService;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by saneeshci on 16/09/15.
 */
@Service
public class NaturalNumberValidation implements ValidationService {


    // Create the logger
    private static Logger log = LoggerFactory.getLogger(NaturalNumberValidation.class);

    @Autowired
    private UserService userService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    // Constructor
    public NaturalNumberValidation() {

    }

    @Override
    public ValidationResponse isValid(ValidationRequest validationRequest) {

        ValidationResponse validationResponse = new ValidationResponse();

        Double amount = validationRequest.getAmount();

        //check whether the rwdQty is greater than zero
        //if(amount > 0 && (amount%1 == 0)){
        if(amount > 0){

            //set valid to true
            validationResponse.setValid(true);


        } else {

          //log error
          log.error("isValid : Invalid cashback quantity "+amount);

           validationResponse.setSpielName(MessageSpielValue.CASHBACK_AMOUNT_INVALID);

           validationResponse.setValid(false);

           validationResponse.setApiErrorCode(APIErrorCode.ERR_CASHBACK_AMOUNT_INVALID);

           validationResponse.setValidationRemarks("Invalid cashback quantity");

           return validationResponse;

        }

        //return validtion response
        return validationResponse;


    }
}

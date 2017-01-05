package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.service.ImageService;
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
public class CustomerValidation implements ValidationService {


    // Create the logger
    private static Logger log = LoggerFactory.getLogger(CustomerValidation.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    // Constructor
    public CustomerValidation() {

    }

    @Override
    public ValidationResponse isValid(ValidationRequest validationRequest) {

        ValidationResponse validationResponse = new ValidationResponse();

        //get the customer details for the request
        Customer customer = validationRequest.getCustomer();

        //check if the customer object is null or not
        if(customer == null){

            //log error
            log.error("isValid : Customer information not found "+validationRequest.getLoyaltyId());

            //set request status to false
            validationResponse.setValid(false);

            //set remarks
            validationResponse.setValidationRemarks("Customer information not found");

            validationResponse.setApiErrorCode(APIErrorCode.ERR_NO_LOYALTY_ID);

            validationResponse.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_CUSTOMER_NOT_FOUND);

            return validationResponse;
        }

        //check customer status
        if(customer.getCusStatus() != CustomerStatus.ACTIVE){

            //log error
            log.error("isValid : Customer is not active "+validationRequest.getLoyaltyId());

            //set request status to false
            validationResponse.setValid(false);

            //set remarks
            validationResponse.setValidationRemarks("Customer is not active");

            validationResponse.setApiErrorCode(APIErrorCode.ERR_NO_LOYALTY_ID);

            validationResponse.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_CUSTOMER_NOT_ACTIVE);

            return validationResponse;
        }

        //set valid to true
        validationResponse.setValid(true);

        //return validtion response
        return validationResponse;


    }
}

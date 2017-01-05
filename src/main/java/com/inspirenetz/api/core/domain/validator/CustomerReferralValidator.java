package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.CustomerReferral;
import com.inspirenetz.api.util.DataValidationUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by fayiz on 27/4/15.
 */
public class CustomerReferralValidator implements Validator {

    private DataValidationUtils dataValidationUtils;


    public CustomerReferralValidator(DataValidationUtils dataValidationUtils) {

        this.dataValidationUtils = dataValidationUtils;

    }


    @Override
    public boolean supports(Class clazz) {

        return CustomerReferral.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the Customer
        CustomerReferral customerReferral = (CustomerReferral)target;

        // Check if the customer loyalty id is valid
        if ( customerReferral.getCsrLoyaltyId() ==  null || customerReferral.getCsrLoyaltyId().equals("")){

            // Reject the value
            errors.rejectValue("csrLoyaltyId","customerReferral.csrLoyaltyId.notempty");

        } else if ( customerReferral.getCsrLoyaltyId().length() > 20 ) {

            // Reject the value
            errors.rejectValue("csrLoyaltyId","customerReferral.csrloyaltyid.size");

        }


        // Check if the mobile entered is valid
        if ( customerReferral.getCsrRefMobile() != null && !customerReferral.getCsrRefMobile().trim().equals("") ) {

            // Validate the mobile
            if ( customerReferral.getCsrRefMobile().length() < 10 ) {
                // Reject the value
                errors.rejectValue("csrRefMobile","customerReferral.csrRefMobile.notvalid");

            }

        }

    }

}

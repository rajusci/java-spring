package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.CustomerSubscription;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class CustomerSubscriptionValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return CustomerSubscription.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the CustomerSubscriptionObject
        CustomerSubscription customerSubscription = (CustomerSubscription)target;

        // Check the customer number
        if (  customerSubscription.getCsuCustomerNo() == null | customerSubscription.getCsuCustomerNo() == 0L ) {

            errors.rejectValue("csuCustomerNo","{customersubscription.csucustomerno.notnull}");

        }


        // Check the product code
        if ( customerSubscription.getCsuProductCode() == null || customerSubscription.getCsuProductCode().equals("") ) {

            errors.rejectValue("csuProductCode","{customersubscription.csuproductcode.notempty}");

        }



    }
}

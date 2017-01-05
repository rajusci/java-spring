package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.CustomerRewardActivity;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 29/9/14.
 */
public class CustomerRewardActivityValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return CustomerRewardActivity.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the CustomerRewardActivityObject
        CustomerRewardActivity customerRewardActivity = (CustomerRewardActivity)target;

        if (  customerRewardActivity.getCraCustomerNo() == null  ) {

            errors.rejectValue("craCustomerNo","{customerRewardActivity.craCustomerNo.notnull}");

        }
        if (  customerRewardActivity.getCraType() == null  ) {

            errors.rejectValue("craType","{customerRewardActivity.craType.notnull}");

        }
        if (  customerRewardActivity.getCraActivityRef() == null  ) {


            errors.rejectValue("craActivityRef","{customerRewardActivity.craActivityRef.notnull}");

        }
    }
}

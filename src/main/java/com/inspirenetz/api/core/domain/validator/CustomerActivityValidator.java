package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.CustomerActivity;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 27/9/14.
 */
public class CustomerActivityValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return CustomerActivity.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

            // cast the CustomerActivityObject
        CustomerActivity customerActivity = (CustomerActivity)target;

        // Check the customerActivity activity type
        if (  customerActivity.getCuaActivityType() == null  ) {

            errors.rejectValue("cuaActivityType","{customerActivity.cuaActivityType.notnull}");

        }

        // Check the customerActivity loyalty id
        if (  customerActivity.getCuaLoyaltyId() == null  ) {

            errors.rejectValue("cuaLoyaltyId","{customerActivity.cuaLoyaltyId.notnull}");

        }

    }
}

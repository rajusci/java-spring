package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.CustomerPromotionalEvent;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 25/6/15.
 */
public class CustomerPromotionalEventValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return CustomerPromotionalEvent.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

            // cast the DrawChanceObject
        CustomerPromotionalEvent customerPromotionalEvent = (CustomerPromotionalEvent)target;

        // Check the drawChance customer no
        if (  customerPromotionalEvent.getCpeEventId() == null  ) {

            errors.rejectValue("cpeEventId","{customerPromotionalEvent.cpeEventId.notnull}");

        }

        // Check the drawChance customer no
        if (  customerPromotionalEvent.getCpeLoyaltyId() == null  ) {

            errors.rejectValue("cpeLoyaltyId","{customerPromotionalEvent.cpeLoyaltyId.notnull}");

        }

        // Check the drawChance customer no
        if (  customerPromotionalEvent.getCpeProduct() == null  ) {

            errors.rejectValue("cpeReference","{customerPromotionalEvent.cpeReference.notnull}");

        }

    }
}

package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.DrawChance;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 27/9/14.
 */
public class DrawChanceValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return DrawChance.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

            // cast the DrawChanceObject
        DrawChance drawChance = (DrawChance)target;

        // Check the drawChance customer no
        if (  drawChance.getDrcCustomerNo() == null  ) {

            errors.rejectValue("drcCustomerNo","{drawChance.drcCustomerNo.notnull}");

        }

        // Check the drawChance chances
        if (  drawChance.getDrcChances() == null  ) {

            errors.rejectValue("drcChances","{drawChance.drcChances.notnull}");

        }

        // Check the drawChance draw type
        if (  drawChance.getDrcType() == null  ) {

            errors.rejectValue("drcType","{drawChance.drcType.notnull}");

        }

    }
}

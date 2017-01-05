package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.Purchase;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class PurchaseValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return Purchase.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the PurchaseObject
        Purchase purchase = (Purchase)target;

        // Check the user location
        if (  purchase.getPrcLocation() == -1 ) {

            errors.rejectValue("prcLocation","{purchase.prclocation.notempty}");

        }


        // Check the user number validity
        if ( purchase.getPrcUserNo() == -1 ) {

            errors.rejectValue("prcUserNo","{purchase.prcuserno.notempty}");

        }


        // Check the date and see if its empty
        if ( purchase.getPrcDate() == null ) {

            errors.rejectValue("prcDate","purchase.prcdate.notnull");

        }

    }
}

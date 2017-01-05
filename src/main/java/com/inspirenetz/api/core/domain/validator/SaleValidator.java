package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.Sale;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class SaleValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return Sale.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the SaleObject
        Sale sale = (Sale)target;

        // Check the user location
        if (  sale.getSalLocation() == -1 ) {

            errors.rejectValue("salLocation","{sale.sallocation.notempty}");

        }


        // Check the user number validity
        if ( sale.getSalUserNo() == -1 ) {

            errors.rejectValue("salUserNo","{sale.saluserno.notempty}");

        }


        // Check the date and see if its empty
        if ( sale.getSalDate() == null ) {

            errors.rejectValue("salDate","sale.saldate.notnull");

        }

    }
}

package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.domain.Purchase;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Date;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class LoyaltyProgramValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return Purchase.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {


        // cast the LoyaltyProgramObject
        LoyaltyProgram loyaltyProgram = (LoyaltyProgram)target;

        // Create the today instance
        Date today = new Date(new java.util.Date().getTime());



        // Check the endDate
        if ( loyaltyProgram.getPrgEndDate() == null || loyaltyProgram.getPrgEndDate().compareTo(today) < 0) {

            // Set the error
            errors.rejectValue("prgEndDate","loyaltyprogram.prgenddate.invalid");

        }


        // If the startDate and endDate are not null, then make sure that the startDate
        // is before endDate
        if ( loyaltyProgram.getPrgStartDate() != null && loyaltyProgram.getPrgEndDate() != null ) {

            // Check the startDate is before endDate
            if (  loyaltyProgram.getPrgStartDate().compareTo(loyaltyProgram.getPrgEndDate()) > 0) {

                // Set the error
                errors.rejectValue("prgStartDate","loyaltyprogram.prgstartdate.invalid");

            }

        }

        // Check the ratio deno
        if ( loyaltyProgram.getPrgRatioDeno() == 0.0) {

            // Set the error
            errors.rejectValue("prgRatioDeno","loyaltyprogram.prgratiodeno.invalid");

        }


        // Check if the currency id is specified
        if ( loyaltyProgram.getPrgCurrencyId() == null || loyaltyProgram.getPrgCurrencyId() == 0L ) {

            // Set the error
            errors.rejectValue("prgCurrencyId","loyaltyprogram.prgcurrencyid.invalid");

        }




    }
}

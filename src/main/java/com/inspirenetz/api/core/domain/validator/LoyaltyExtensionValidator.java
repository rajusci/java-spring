package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.LoyaltyExtension;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneesh-ci on 10/9/14.
 */
public class LoyaltyExtensionValidator implements Validator{

    @Override
    public boolean supports(Class clazz) {

        return LoyaltyExtension.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the LoyaltyExtensionObject
        LoyaltyExtension loyaltyExtension = (LoyaltyExtension)target;

        // Check the lex name
        if (  loyaltyExtension.getLexName() == null  ) {

            errors.rejectValue("loyaltyExtension","{loyaltyextension.lexname.notnull}");

        }


        // Check the lex name max length
        if ( loyaltyExtension.getLexName().length() > 50 ) {

            errors.rejectValue("loyaltyExtension","{loyaltyextension.lexname.maxlength}");

        }

        // Check the lex name min length
        if ( loyaltyExtension.getLexName().length() < 5 ) {

            errors.rejectValue("loyaltyExtension","{loyaltyextension.lexname.minlength}");

        }

        // Check the lex description max length
        if ( loyaltyExtension.getLexName().length() >200 ) {

            errors.rejectValue("loyaltyExtension","{loyaltyextension.lexdescription.maxlength}");

        }

        // Check the lex name max length
        if ( loyaltyExtension.getLexName().length() > 45 ) {

            errors.rejectValue("loyaltyExtension","{loyaltyextension.lexfile.maxlength}");

        }

        // Check the lex name min length
        if ( loyaltyExtension.getLexFile().length() < 3 ) {

            errors.rejectValue("loyaltyExtension","{loyaltyextension.lexfile.minlength}");

        }



    }
}

package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.util.DataValidationUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 25/9/14.
 */
public class RedemptionMerchantValidator implements Validator {

    private DataValidationUtils dataValidationUtils;


    public  RedemptionMerchantValidator( DataValidationUtils dataValidationUtils ) {

        this.dataValidationUtils = dataValidationUtils;

    }

    @Override
    public boolean supports(Class clazz) {

        return RedemptionMerchant.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the RedemptionMerchantObject
        RedemptionMerchant redemptionMerchant = (RedemptionMerchant)target;

        // Check the redemptionMerchant name
        if (  redemptionMerchant.getRemName() == null  ) {

            errors.rejectValue("remName","{redemptionMerchant.remName.notnull}");

        }


        // Check the redemptionMerchant name length
        if (  redemptionMerchant.getRemName().length() > 100 ) {

            errors.rejectValue("remName","{redemptionMerchant.remName.size}");

        }

        // Check the redemptionMerchant name length
        if (  redemptionMerchant.getRemCategory() == null ) {

            errors.rejectValue("remCategory","{redemptionMerchant.remCategory.notnull}");

        }

        // Check the redemptionMerchant name length
        if (  redemptionMerchant.getRemAddress() != null && redemptionMerchant.getRemAddress().length() >200) {

            errors.rejectValue("remAddress","{redemptionMerchant.remAddress.size}");

        }

        // Check the redemptionMerchant name length
        if (  redemptionMerchant.getRemContactPerson() != null && redemptionMerchant.getRemContactPerson().length() >100) {

            errors.rejectValue("remContactPerson","{redemptionMerchant.remContactPerson.size}");

        }

        // Check the redemptionMerchant name length
        if (  redemptionMerchant.getRemVoucherPrefix() == null ) {

            errors.rejectValue("remVoucherPrefix","{redemptionMerchant.remVoucherPrefix.null}");

        }
        // Check the redemptionMerchant name length
        if (  redemptionMerchant.getRemVoucherPrefix() != null && redemptionMerchant.getRemVoucherPrefix().length() >3) {

            errors.rejectValue("remVoucherPrefix","{redemptionMerchant.remVoucherPrefix.size}");

        }

        // Check if the email entered is valid
        if ( redemptionMerchant.getRemContactEmail() != null && !redemptionMerchant.getRemContactEmail().trim().equals("") ) {

            // Validate the email
            if ( !dataValidationUtils.isValidEmailAddress(redemptionMerchant.getRemContactEmail()) ) {

                // Reject the value
                errors.rejectValue("remContactEmail","redemptionMerchant.remContactEmail.email");

            }

        }
    }
}

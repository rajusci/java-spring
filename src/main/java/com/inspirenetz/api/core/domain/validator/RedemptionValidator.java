package com.inspirenetz.api.core.domain.validator;


import com.inspirenetz.api.core.domain.Redemption;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 17/2/14.
 */
public class RedemptionValidator implements Validator {


    @Override
    public boolean supports(Class clazz) {

        return Redemption.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // Cast the Redemption object
        Redemption redemption = ( Redemption) target;

        // Check if the loyalty is set
        if ( redemption.getRdmLoyaltyId() == null ) {

            errors.rejectValue("rdmLoyaltyId","ERR_NO_LOYALTY_ID");

        }

        // Check if the redemption type is set
        if ( redemption.getRdmType() == 0 ) {

            errors.rejectValue("rdmType","ERR_NO_REDEMPTION_TYPE");

        }


        // Check if the merchant number is set
        if ( redemption.getRdmMerchantNo() == 0 ) {

            errors.rejectValue("rdmMerchantNo","ERR_NO_MERCHANT_NO");

        }

        // Check if the productCode is set
        if ( redemption.getRdmProductCode() == null ) {

            errors.rejectValue("rdmProductCode","ERR_NO_PRODUCT_CODE");

        }

    }
}

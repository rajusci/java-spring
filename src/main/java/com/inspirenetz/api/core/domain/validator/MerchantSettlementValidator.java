package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.MerchantSettlement;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 27/9/14.
 */
public class MerchantSettlementValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return MerchantSettlement.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

            // cast the MerchantSettlementObject
        MerchantSettlement merchantSettlement = (MerchantSettlement)target;

        // Check the merchantSettlement customer no
        if (  merchantSettlement.getMesLoyaltyId() == null  ) {

            errors.rejectValue("mesLoyaltyId","{merchantSettlement.mesLoyaltyId.notnull}");

        }

        // Check the merchantSettlement chances
        if (  merchantSettlement.getMesVendorNo() == null  ) {

            errors.rejectValue("mesVendorNo","{merchantSettlement.mesVendorNo.notnull}");

        }

        // Check the merchantSettlement draw type
        if (  merchantSettlement.getMesInternalRef() == null  ) {

            errors.rejectValue("mesInternalRef","{merchantSettlement.mesInternalRef.notnull}");

        }

    }
}

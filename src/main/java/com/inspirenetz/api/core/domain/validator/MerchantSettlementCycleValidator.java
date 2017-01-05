package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.MerchantSettlementCycle;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 27/9/14.
 */
public class MerchantSettlementCycleValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return MerchantSettlementCycle.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

            // cast the MerchantSettlementCycleObject
        MerchantSettlementCycle merchantSettlementCycle = (MerchantSettlementCycle)target;

        // Check the merchantSettlementCycle customer no
        if (  merchantSettlementCycle.getMscRedemptionMerchant() == null  ) {

            errors.rejectValue("mscRedemptionMerchant","{merchantSettlementCycle.mscRedemptionMerchant.notnull}");

        }

        // Check the merchantSettlementCycle customer no
        if (  merchantSettlementCycle.getMscMerchantLocation() == null  ) {

            errors.rejectValue("mscMerchantLocation","{merchantSettlementCycle.mscMerchantLocation.notnull}");

        }

        // Check the merchantSettlementCycle customer no
        if (  merchantSettlementCycle.getMscStartDate() == null  ) {

            errors.rejectValue("mscStartDate","{merchantSettlementCycle.mscStartDate.notnull}");

        }

        // Check the merchantSettlementCycle customer no
        if (  merchantSettlementCycle.getMscEndDate() == null  ) {

            errors.rejectValue("mscEndDate","{merchantSettlementCycle.mscEndDate.notnull}");

        }


    }
}

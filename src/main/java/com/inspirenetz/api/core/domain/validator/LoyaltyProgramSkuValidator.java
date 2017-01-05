package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.core.domain.Purchase;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class LoyaltyProgramSkuValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return Purchase.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the LoyaltyProgramSkuObject
        LoyaltyProgramSku loyaltyProgramSku = (LoyaltyProgramSku)target;

        // Check the program id
        if ( loyaltyProgramSku.getLpuProgramId() == null || loyaltyProgramSku.getLpuProgramId() == 0L ){

            errors.rejectValue("lpuProgramId","loyaltyprogramsku.lpuprogramid.notempty");

        }


        // Check if the ratio denom is 0
        if ( loyaltyProgramSku.getLpuPrgRatioDeno() == 0 ) {

            errors.rejectValue("lpuPrgRatioDeno","loyaltyprogramsku.lpuprgratiodeno.range");

        }

    }
}

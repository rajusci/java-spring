package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.VoucherCode;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by alameen on 10/2/15.
 */
public class VoucherCodeValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return VoucherCodeValidator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        //cast the user profile
        VoucherCode voucherCode =(VoucherCode)target;

        if(voucherCode.getVocVoucherCode() ==null){

            // Reject the value
            errors.rejectValue("vocVoucherCode","vouchercode.vocvouchercode.notempty");
        }
    }
}

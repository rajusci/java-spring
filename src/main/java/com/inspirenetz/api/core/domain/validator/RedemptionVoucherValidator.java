package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.RedemptionVoucher;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 25/9/14.
 */
public class RedemptionVoucherValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return RedemptionVoucher.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the RedemptionVoucherObject
        RedemptionVoucher redemptionVoucher = (RedemptionVoucher)target;

        // Check the redemptionVoucher name
        if (  redemptionVoucher.getRvrCustomerNo() == null  ) {

            errors.rejectValue("rvrCustomerNo","{redemptionVoucher.rvrCustomerNo.notnull}");

        }

        // Check the redemptionVoucher redemption merchant
        if (  redemptionVoucher.getRvrMerchant() == null  ) {

            errors.rejectValue("rvrMerchant","{redemptionVoucher.rvrMerchant.notnull}");

        }
        // Check the redemptionVoucher redemption product code
        if (  redemptionVoucher.getRvrProductCode() == null  ) {

            errors.rejectValue("rvrProductCode","{redemptionVoucher.rvrProductCode.notnull}");

        }

        //check redemption voucher code
        if(redemptionVoucher.getRvrVoucherCode() == null){

            errors.rejectValue("rvrProductCode","{redemptionVoucher.rvrProductCode.notnull}");


        }
    }
}

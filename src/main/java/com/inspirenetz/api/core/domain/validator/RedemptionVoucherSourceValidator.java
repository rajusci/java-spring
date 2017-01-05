package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 10/2/15.
 */
public class RedemptionVoucherSourceValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return RedemptionVoucherSource.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the RedemptionVoucherSourceObject
        RedemptionVoucherSource redemptionVoucherSource = (RedemptionVoucherSource)target;

        // Check the RedemptionVoucherSource customer no
        if (  redemptionVoucherSource.getRvsName() == null  ) {

            errors.rejectValue("rvsName","{redemptionVoucherSource.rvsName.notnull}");

        }

        // Check the RedemptionVoucherSource chances
        if (  redemptionVoucherSource.getRvsType() == null  ) {

            errors.rejectValue("rvsType","{redemptionVoucherSource.rvsType.notnull}");

        }

        // Check the RedemptionVoucherSource  type
        if (  redemptionVoucherSource.getRvsType() == 1 &&  (redemptionVoucherSource.getRvsCode() == null || redemptionVoucherSource.getRvsCode().length() == 0)) {

            errors.rejectValue("rvsCode","{redemptionVoucherSource.rvsCode.notnull}");

        }
        // Check the RedemptionVoucherSource draw type
        if (  redemptionVoucherSource.getRvsType() == 2 ){

            if( redemptionVoucherSource.getRvsCodeStart() == null  ) {

                errors.rejectValue("rvsCodeStart","{redemptionVoucherSource.rvsCodeStart.notnull}");

            } else if( redemptionVoucherSource.getRvsCodeEnd() == null ) {

                errors.rejectValue("rvsCodeEnd","{redemptionVoucherSource.rvsCodeEnd.notnull}");

            }

        }

    }
}

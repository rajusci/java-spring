package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.dictionary.CouponDistributionType;
import com.inspirenetz.api.core.domain.CouponDistribution;
import com.inspirenetz.api.util.GeneralUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class CouponDistributionValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return CouponDistribution.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the CouponDistribution
        CouponDistribution couponDistribution = (CouponDistribution)target;

        // Check the type of the distribution and see if the associated informatoin
        // is present in the request
        // Check the type of the distribution
        switch(couponDistribution.getCodDistributionType()) {


            // Public type
            case CouponDistributionType.CUSTOMER_IDS :

                // Check if the customer ids field is filled in
                if ( couponDistribution.getCodCustomerIds() == null || couponDistribution.getCodCustomerIds().equals("")) {

                    errors.rejectValue("codCustomerIds","coupondistribution.codcustomerids.notnull");

                }

                // Break the case
                break;


            // All members type
            case CouponDistributionType.COALITION_IDS :


                // Check if the coalition ids field is filled in
                if ( couponDistribution.getCodCoalitionIds() == null || couponDistribution.getCodCoalitionIds().equals("")) {

                    errors.rejectValue("codCoalitionIds","coupondistribution.codcoalitionids.notnull");

                }

                // Break the case
                break;


            // Selected customer segments
            case CouponDistributionType.CUSTOMER_SEGMENTS :

                // Check if the segment ids field is filled in
                if ( couponDistribution.getCodCustomerSegments() == null || couponDistribution.getCodCustomerSegments().equals("")) {

                    errors.rejectValue("codCustomerSegments","coupondistribution.codcustomersegments.notnull");

                }

                // Break the case
                break;

        }
    }
}

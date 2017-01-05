package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.dictionary.CustomerSegmentComparisonType;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Tier;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class TierValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return Tier.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the TierObject
        Tier tier = (Tier)target;

        // Check if the tier ind is set and value is null
        if (  tier.getTiePointInd() == IndicatorStatus.YES) {


            // Check if the first  value is null
            if ( tier.getTiePointValue1() == null  ) {

                errors.rejectValue("tiePointValue1","{tier.tiepointvalue1.notnull}");

            }



            // Check if type is between
            if ( tier.getTiePointCompType() == CustomerSegmentComparisonType.BETWEEN ) {

                // Check if the second vlaue is
                if ( tier.getTiePointValue2() == null ) {

                    errors.rejectValue("tiePointValue2","{tier.tiepointvalue2.notnull}");

                }


                // Check if the first value is greater than the second value
                if ( tier.getTiePointValue1() > tier.getTiePointValue2() ) {

                    errors.rejectValue("tiePointValue2","{tier.tiepointvalue2.value}");

                }
            }

        }



        // Check if the tier ind is set and value is null
        if ( tier.getTieAmountInd() == IndicatorStatus.YES ) {

            // Check if the first  value is null
            if ( tier.getTieAmountValue1() == null  ) {

                errors.rejectValue("tieAmountValue1","{tier.tieamountvalue1.notnull}");

            }


            // Check if type is between
            if ( tier.getTieAmountCompType() == CustomerSegmentComparisonType.BETWEEN ) {

                // Check if the second vlaue is null
                if ( tier.getTiePointValue2() == null ) {

                    errors.rejectValue("tieAmountValue2","{tier.tieamountvalue2.notnull}");

                }


                // Check if the first value is greater than the second value
                if ( tier.getTieAmountValue1() > tier.getTiePointValue2() ) {

                    errors.rejectValue("tieAmountValue2","{tier.tieamountvalue2.value}");

                }
            }

        }

    }
}

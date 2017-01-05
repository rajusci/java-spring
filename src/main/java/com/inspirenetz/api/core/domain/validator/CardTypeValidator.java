package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.dictionary.CardTypeType;
import com.inspirenetz.api.core.dictionary.PromotionExpiryOption;
import com.inspirenetz.api.core.dictionary.PromotionUserAction;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.core.domain.Promotion;
import com.inspirenetz.api.util.GeneralUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class CardTypeValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return CardType.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the CardType
        CardType cardType = (CardType)target;

        // Check if the card type is set
        if (  cardType.getCrtType() == 0 ) {

            // Reject the value
            errors.rejectValue("crtType","cardtype.crttype.notempty");

            // Return the control
            return;

        }


        // Check the cart type and set the mandatory fields
        if ( cardType.getCrtType() == CardTypeType.FIXED_VALUE ) {

            // Check if the fixed value field is set
            if ( cardType.getCrtFixedValue() == 0 ) {

                // Reject the value
                errors.rejectValue("crtFixedVale","cardtype.crtfixedvalue.notempty");

            }

        } else if ( cardType.getCrtType() == CardTypeType.RECHARGEBLE ) {

            // Check if the crtMinTopupValue is set
            if ( cardType.getCrtMinTopupValue()< 0 ) {

                // Reject the value
                errors.rejectValue("crtMinTopupValue","cardtype.crtmintopupvalue.notempty");

            }


            // Check if the crtMaxCardValue is set
            if ( cardType.getCrtMaxValue() == 0 ) {

                // Reject the value
                errors.rejectValue("crtMaxValue","cardtype.crtmaxvalue.notempty");

            }

        }

    }

}

package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.dictionary.PromotionExpiryOption;
import com.inspirenetz.api.core.dictionary.PromotionUserAction;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.core.domain.Promotion;
import com.inspirenetz.api.core.domain.Purchase;
import com.inspirenetz.api.util.GeneralUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class PromotionValidator implements Validator {

    private GeneralUtils generalUtils;


    public PromotionValidator(GeneralUtils generalUtils) {

        this.generalUtils = generalUtils;

    }

    @Override
    public boolean supports(Class clazz) {

        return Promotion.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the Promotion
        Promotion promotion = (Promotion)target;

        // Check the expiration type and see if the fields are set
        if (    promotion.getPrmExpiryOption() == PromotionExpiryOption.EXPIRY_DATE &&
                promotion.getPrmExpiryDate() == null ){

            errors.rejectValue("prmExpiryDate","promotion.prmexpirydate.notnull");

        }


        if (    promotion.getPrmExpiryOption() == PromotionExpiryOption.NUM_RESPONSES &&
                (promotion.getPrmMaxResponses() == null )) {

            errors.rejectValue("prmNumResponses","promotion.prmnumresponses.notnull");

        }


        // Check if the promotion has got the claim as user action selected, then we need to have
        // the claim expiry days data filled in
        if (    generalUtils.isTokenizedValueExists(promotion.getPrmUserAction(),":", Integer.toString(PromotionUserAction.CLAIM)) &&
                promotion.getPrmClaimExpiryDays() == null ) {

            errors.rejectValue("prmClaimExpiryDays","promotion.prmclaimexpirydays.notnull");

        }
    }
}

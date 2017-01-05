package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.dictionary.CardTypeType;
import com.inspirenetz.api.core.domain.CardMaster;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.util.DataValidationUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class CardMasterValidator implements Validator {

    private DataValidationUtils dataValidationUtils;


    public  CardMasterValidator( DataValidationUtils dataValidationUtils ) {

        this.dataValidationUtils = dataValidationUtils;

    }

    @Override
    public boolean supports(Class clazz) {

        return CardType.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the CardType
        CardMaster cardMaster = (CardMaster)target;

        // Check if the card type is set
        if (  cardMaster.getCrmCardHolderName() == "" ) {

            // Reject the value
            errors.rejectValue("crmCardHolderName","cardMaster.crmCardHolderName.notempty");

            // Return the control
            return;

        }else if ( cardMaster.getCrmCardHolderName() != null && !cardMaster.getCrmCardHolderName().trim().equals("")) {

            if ( !dataValidationUtils.isValidName(cardMaster.getCrmCardHolderName())){

                //Reject the value
                errors.rejectValue("crmCardHolderName","cardmaster.crmCardHolderName.cardHolderName");

            }
        }

        if (  cardMaster.getCrmType() == 0) {

            // Reject the value
            errors.rejectValue("crmType","cardMaster.crmType.notempty");

            // Return the control
            return;

        }


        // Check if the email entered is valid
        if ( cardMaster.getCrmMobile() == null ) {

            // Reject the value
            errors.rejectValue("crmMobile","cardMaster.crmMobile.notempty");

            // Return the control
            return;

        }

        // Check if the email entered is valid
        if ( cardMaster.getCrmMobile() != null && !cardMaster.getCrmMobile().trim().equals("") ) {

            // Validate the email
            if ( !dataValidationUtils.isValidMobile(cardMaster.getCrmMobile()) ) {

                // Reject the value
                errors.rejectValue("crmMobile","cardmaster.crmMobile.mobile");

            }

        }

        // Check if the email entered is valid
        if ( cardMaster.getCrmLoyaltyId() != null && !cardMaster.getCrmLoyaltyId().trim().equals("") ) {

            // Validate the loyaltyid
            if ( !dataValidationUtils.isValidLoyaltyId(cardMaster.getCrmLoyaltyId()) ) {

                // Reject the value
                errors.rejectValue("crmLoyaltyId","cardmaster.crmLoyaltyId.loyaltyId");

            }

        }




        // Check if the email entered is valid
        if ( cardMaster.getCrmEmailId() != null && !cardMaster.getCrmEmailId().trim().equals("") ) {

            // Validate the email
            if ( !dataValidationUtils.isValidEmailAddress(cardMaster.getCrmEmailId()) ) {

                // Reject the value
                errors.rejectValue("crmEmailId","cardMaster.crmEmailId.email");

            }

        }
    }

}

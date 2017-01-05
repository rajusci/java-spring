package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.PartnerCatalogue;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 27/9/14.
 */
public class PartnerCatalogueValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return PartnerCatalogue.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

            // cast the PartnerCatalogueObject
        PartnerCatalogue partnerCatalogue = (PartnerCatalogue)target;

        // Check the partnerCatalogue customer no
        if (  partnerCatalogue.getPacPartnerNo() == null  ) {

            errors.rejectValue("pacPartnerNo","{partnerCatalogue.pacPartnerNo.notnull}");

        }

        // Check the partnerCatalogue chances
        if (  partnerCatalogue.getPacName() == null  ) {

            errors.rejectValue("pacName","{partnerCatalogue.pacName.notnull}");

        }

        // Check the partnerCatalogue chances
        if (  partnerCatalogue.getPacCode() == null  ) {

            errors.rejectValue("pacCode","{partnerCatalogue.pacCode.notnull}");

        }

        // Check the partnerCatalogue chances
        if (  partnerCatalogue.getPacCost() == null  ) {

            errors.rejectValue("pacCost","{partnerCatalogue.pacCost.notnull}");

        }


    }
}

package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.CatalogueDisplayPreference;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 25/9/14.
 */
public class CatalogueDisplayPreferenceValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return CatalogueDisplayPreference.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the RedemptionVoucherObject
        CatalogueDisplayPreference catalogueDisplayPreferences = (CatalogueDisplayPreference)target;
       
        // Check the catalogueDisplayPreferences redemption product code
        if (  catalogueDisplayPreferences.getCdpPreferences() == null  ) {

            errors.rejectValue("cdpPreferences","{catalogueDisplayPreferences.cdpPreferences.notnull}");

        }

    }
}

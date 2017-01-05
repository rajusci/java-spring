package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.SecuritySetting;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 29/9/14.
 */
public class SecuritySettingValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return SecuritySetting.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the SecuritySettingObject
        SecuritySetting securitySetting = (SecuritySetting)target;
    }
}

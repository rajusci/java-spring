package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.Role;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class RoleValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return Role.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the RoleObject
        Role role = (Role)target;

        // Check the role name
        if (  role.getRolName() == null  ) {

            errors.rejectValue("rolName","{role.rolName.notnull}");

        }


        // Check the role name length
        if (  role.getRolName().length() > 100 ) {

            errors.rejectValue("rolName","{role.rolName.notnull}");

        }

        // Check the role user type
        if (  role.getRolUserType() == null || role.getRolUserType() == 0 ) {

            errors.rejectValue("rolName","{role.rolUserType.notnull}");

        }

    }
}

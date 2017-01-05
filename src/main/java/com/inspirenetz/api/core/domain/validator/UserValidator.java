package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return User.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the User
        User user = (User)target;

        // Check if the user login id is set
        if (  user.getUsrLoginId() == null || user.getUsrLoginId().equals("") ) {

            // Reject the value
            errors.rejectValue("usrLoginId","user.usrloginid.notempty");

        }


        // Check if the user password is empty
        if ( user.getUsrPassword() == null || user.getUsrPassword().equals("") ) {

            // Reject the value
            errors.rejectValue("usrPassword","user.usrpassword.notempty");

        }


        // Check if the userFname is not empty
        if ( user.getUsrFName() == null || user.getUsrFName().equals("") ) {

            // Reject the value
            errors.rejectValue("usrFName","user.usrfname.notempty");

        }

    }
}

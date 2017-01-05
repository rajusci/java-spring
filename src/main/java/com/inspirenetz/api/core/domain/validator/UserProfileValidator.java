package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.UserProfile;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by alameen on 24/10/14.
 */
public class UserProfileValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return UserProfileValidator.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the userProfile
        UserProfile userProfile = (UserProfile)target;

        // Check the address field validity
        if ( userProfile.getUspAddress1() != null && userProfile.getUspAddress1().length() > 200  ){

            // Reject the value
            errors.rejectValue("uspAddress2","userprofile.uspAddress1.size");

        }


        // Check the city field validity
        if ( userProfile.getUspAddress2() != null && userProfile.getUspAddress2().length() > 200  ){

            // Reject the value
            errors.rejectValue("uspAddress2","userprofile.uspAddress2.size");

        }

        // Check the city field validity
        if ( userProfile.getUspAddress3() != null && userProfile.getUspAddress3().length() > 200  ){

            // Reject the value
            errors.rejectValue("uspAddress3","userprofile.uspAddress3.size");

        }

        // Check the city field validity
        if ( userProfile.getUspCity() != null && userProfile.getUspCity().length() > 100  ){

            // Reject the value
            errors.rejectValue("uspcity","userprofile.uspcity.size");

        }


        // Check the pincode field validity
        if ( userProfile.getUspPinCode() != null && userProfile.getUspPinCode().length() > 20  ){

            // Reject the value
            errors.rejectValue("uspPincode","userprofile.usppincode.size");

        }


    }

}

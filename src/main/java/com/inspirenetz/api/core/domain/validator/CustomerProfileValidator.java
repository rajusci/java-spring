package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.util.GeneralUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 5/9/14.
 */
public class CustomerProfileValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return CustomerProfile.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the CustomerProfile
        CustomerProfile customerProfile = (CustomerProfile)target;

        // Check the address field validity
        if ( customerProfile.getCspAddress() != null && customerProfile.getCspAddress().length() > 200  ){

            // Reject the value
            errors.rejectValue("cspAddress","customerprofile.cspaddress.size");

        }


        // Check the city field validity
        if ( customerProfile.getCspCity() != null && customerProfile.getCspCity().length() > 100  ){

            // Reject the value
            errors.rejectValue("cspCity","customerprofile.cspcity.size");

        }


        // Check the pincode field validity
        if ( customerProfile.getCspPincode() != null && customerProfile.getCspPincode().length() > 20  ){

            // Reject the value
            errors.rejectValue("cspPincode","customerprofile.csppincode.size");

        }


        // Check the child1 name field validity
        if ( customerProfile.getCspFamilyChild1Name() != null && customerProfile.getCspFamilyChild1Name().length() > 50  ){

            // Reject the value
            errors.rejectValue("cspFamilyChild1Name","customerprofile.cspfamilychild1name.size");

        }


        // Check the child1 name field validity
        if ( customerProfile.getCspFamilyChild2Name() != null && customerProfile.getCspFamilyChild2Name().length() > 50  ){

            // Reject the value
            errors.rejectValue("cspFamilyChild2Name","customerprofile.cspfamilychild2name.size");

        }


        // Check the spouse name field validity
        if ( customerProfile.getCspFamilySpouseName() != null && customerProfile.getCspFamilySpouseName().length() > 50  ){

            // Reject the value
            errors.rejectValue("cspFamilySpouseName","customerprofile.cspfamilyspousename.size");

        }
    }
}

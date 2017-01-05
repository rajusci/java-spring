package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.dictionary.CustomerType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.util.DataValidationUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 26/4/14.
 */
public class CustomerValidator implements Validator {

    private DataValidationUtils dataValidationUtils;


    public  CustomerValidator( DataValidationUtils dataValidationUtils ) {

        this.dataValidationUtils = dataValidationUtils;

    }


    @Override
    public boolean supports(Class clazz) {

        return Customer.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the Customer
        Customer customer = (Customer)target;

        /*// Check if the customer firstname is valid
        if (  customer.getCusFName() == null || customer.getCusFName().equals("") ) {

            // Reject the value
            errors.rejectValue("cusFName","customer.cusfname.notempty");

        } else if ( customer.getCusFName().length() < 3 || customer.getCusFName().length() > 50 ) {

            // Reject the value
            errors.rejectValue("cusFName","customer.cusfname.size");

        }*/
       /* if (  customer.getCusFName() != null && !customer.getCusFName().equals("") ) {

            if ( customer.getCusFName().length() < 3 || customer.getCusFName().length() > 50 ) {

                // Reject the value
                errors.rejectValue("cusFName","customer.cusfname.size");

            }

        }*/



        // Check if the customer loyalty id is valid
        if ( customer.getCusLoyaltyId() ==  null || customer.getCusLoyaltyId().equals("")){

            // Reject the value
            errors.rejectValue("cusLoyaltyId","customer.cusloyaltyid.notempty");

        } else if ( customer.getCusLoyaltyId().length() > 20 ) {

            // Reject the value
            errors.rejectValue("cusLoyaltyId","customer.cusloyaltyid.size");

        } else if ( customer.getCusLoyaltyId() != null && !customer.getCusLoyaltyId().trim().equals("")) {

            if ( !dataValidationUtils.isValidLoyaltyId(customer.getCusLoyaltyId())){

                //Reject the value
                errors.rejectValue("cusLoyaltyId","customer.cusLoyaltyId.loyaltyId");

            }
        }

        // Check if the customer first name  is valid
        if ( customer.getCusFName() ==  null || customer.getCusFName().equals("")){

            // Reject the value
            errors.rejectValue("cusFName","customer.cusfname.notempty");

        } else if ( customer.getCusFName().length() > 100 ) {

            // Reject the value
            errors.rejectValue("cusFName","customer.cusfname.size");

        } else if ( customer.getCusFName() != null && !customer.getCusFName().trim().equals("")) {

            if ( !dataValidationUtils.isValidName(customer.getCusFName())){

                //Reject the value
                errors.rejectValue("cusFName","customer.cusFName.firstName");

            }
        }

        // Check if the size of the Customer lastname is valid
        if ( customer.getCusLName() != null  && customer.getCusLName().length() > 100 ) {

            // Reject the value
            errors.rejectValue("cusLName","customer.cuslname.size");

        }else if ( customer.getCusLName() != null && !customer.getCusLName().trim().equals("")) {

            if ( !dataValidationUtils.isValidName(customer.getCusLName())){

                //Reject the value
                errors.rejectValue("cusLName","customer.cusLName.lastName");

            }
        }




        // Check if the email entered is valid
        if ( customer.getCusEmail() != null && !customer.getCusEmail().trim().equals("") ) {

            // Validate the email
            if ( !dataValidationUtils.isValidEmailAddress(customer.getCusEmail()) ) {

                // Reject the value
                errors.rejectValue("cusEmail","customer.cusemail.email");

            }

        }

        // Check if the mobile entered is valid
        if ( customer.getCusMobile() != null && !customer.getCusMobile().trim().equals("") ) {

            // Validate the email
            if ( !dataValidationUtils.isValidMobile(customer.getCusMobile()) ) {

                // Reject the value
                errors.rejectValue("cusMobile","customer.cusMobile.mobile");

            }

        }



    }

}

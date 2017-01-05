package com.inspirenetz.api.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sandheepgr on 20/4/14.
 */
@Component
public class DataValidationUtils {

    public DataValidationUtils() {}


    // The controller response date format for the fields
    public  final String CONTROLLER_RESPONSE_DATE_FORMAT = "d M Y";

    /**
     * Function to get the validation messages as a string
     *
     * @param result - The BindingResult object from the Controller
     *
     * @return       - The string containing the fields errors messages
     */
    public  String getValidationMessages(BindingResult result) {

        // Variable holding the message
        String message  = "";

        // Go through each of the fields errors and then populate the message fields
        for (FieldError error: result.getFieldErrors()) {


            // Append the message with the data
            message += error.getField() + ": "+error.getDefaultMessage() + ";";

        }

        // Return the message variable
        return message;
    }



    public  String getValidationMessages(BindingResult result,MessageSource messageSource) {

        // Variable holding the message
        String message  = "";

        // Get the locale
        Locale locale = LocaleContextHolder.getLocale();

        // Go through each of the fields errors and then populate the message fields
        for (FieldError error: result.getFieldErrors()) {

            String errorDesc = messageSource.getMessage(error,locale);

            // Append the message with the data
            message += error.getField() + ": "+errorDesc + ";";

        }

        // Return the message variable
        return message;
    }





    public  String getLocalizedAPIErrorCodeDescription(String code,MessageSource messageSource) {

        // Get the current locale
        Locale locale = LocaleContextHolder.getLocale();

        // Get the string
        String message = messageSource.getMessage(code,new Object[0],locale);

        // Return the message
        return message;

    }



    /**
     * Function to validate the email address using regex pattern matching
     *
     * @param email - The email address to be validated
     *
     * @return      - true if its matching
     *                false if not matching
     */
    public boolean isValidEmailAddress(String email) {

        // the pattern for the validation
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

        // Compile the pattern
        Pattern p = Pattern.compile(ePattern);

        // Create the matcher
        Matcher m = p.matcher(email);

        // Return the matches
        return m.matches();

    }

    public boolean isValidMobile(String mobile) {

        // the pattern for the validation
        String ePattern = "^[0-9]+$";

        // Compile the pattern
        Pattern p = Pattern.compile(ePattern);

        // Create the matcher
        Matcher m = p.matcher(mobile);

        // Return the matches
        return m.matches();

    }

    public boolean isValidLoyaltyId(String loyaltyId) {

        // the pattern for the validation
        String ePattern = "^[0-9]+$";

        // Compile the pattern
        Pattern p = Pattern.compile(ePattern);

        // Create the matcher
        Matcher m = p.matcher(loyaltyId);

        // Return the matches
        return m.matches();

    }


    public boolean isValidName(String name) {

        // the pattern for the validation
        String ePattern = "^[a-zA-Z ]+$";

        // Compile the pattern
        Pattern p = Pattern.compile(ePattern);

        // Create the matcher
        Matcher m = p.matcher(name);

        // Return the matches
        return m.matches();

    }


}

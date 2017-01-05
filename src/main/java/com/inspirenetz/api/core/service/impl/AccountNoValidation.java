package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.MessageSpielValue;
import com.inspirenetz.api.core.dictionary.ValidationRequest;
import com.inspirenetz.api.core.dictionary.ValidationResponse;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.core.service.ValidationService;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by saneeshci on 16/09/15.
 */
@Service
public class AccountNoValidation implements ValidationService {


    // Create the logger
    private static Logger log = LoggerFactory.getLogger(AccountNoValidation.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    // Constructor
    public AccountNoValidation() {

    }

    @Override
    public ValidationResponse isValid(ValidationRequest validationRequest) {

         ValidationResponse validationResponse = new ValidationResponse();

        //get the account no
        String accountNo = validationRequest.getRef();

        //get the substring excluding the last digit
        String accNoString  = accountNo.substring(0,accountNo.length()-1);

        //get the last digit as check digit
        String checkDigit = accountNo.substring(accountNo.length()-1);

        //calculate the check digit using algorithm
        String calculatedCheckDigit = getCheckDigit(accNoString);

        //check the values
        if(checkDigit.equals(calculatedCheckDigit)){

            //set valid to true if checkdigit and calculated value are equal
            validationResponse.setValid(true);

        } else {

            //set validity to false
            validationResponse.setValid(false);

            //set remarks
            validationResponse.setValidationRemarks("Account no is not valid");

            //set spiel name
            validationResponse.setSpielName(MessageSpielValue.CASHBACK_ACCOUNTNO_NOT_VALID);

            //set error code
            validationResponse.setApiErrorCode(APIErrorCode.ERR_CASHBACK_ACCOUNT_NO_INVALID);


        }

        //return validaiton response
        return validationResponse;

    }

    /**
     * Method to get the check digit based on the Modulus 11 check digit system
     * @param number    : The number for which the check digit is to be generated
     *                    If we are passing a number which is already having the check digit, then we need
     *                    to pass the 0 to length-1 value.
     * @return          : The check digit of the number.
     *
     */
    public static String getCheckDigit(String number) {

        // Set the initial sum to be -
        int sum = 0;

        // Iterated the numbers
        for (int i = number.length() - 1, multiplier = 2; i >= 0; i--) {
            // Get the sum as the numberic value of the character multiplied with the multiplier
            sum += (int)Character.getNumericValue(number.charAt(i)) * multiplier;

            // If the incremented multiplier is 8, then we reset it to 2
            if (++multiplier == 8) multiplier = 2;

        }


        // Set the checkDigit as 11- modulus 11 of the sum
        String checkDigit = Integer.toString((11 - (sum % 11)));

        // If the checkDigit is 11, then set it to 0
        if (checkDigit.equals("11")) checkDigit = "0";

            // Else if its 10, then set it to X
        else if (checkDigit == "10") checkDigit = "X";

        // Return the checkDigit
        return  checkDigit;

    }

}

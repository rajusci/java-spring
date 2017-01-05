package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by saneeshci on 16/09/15.
 */
@Service
public class PinFormatValidation {


    // Create the logger
    private static Logger log = LoggerFactory.getLogger(PinFormatValidation.class);

    @Autowired
    AuthSessionUtils authSessionUtils;

    // Constructor
    public PinFormatValidation() {

    }

    public boolean isPinFormatValid(String pin){

        //check whether the pin is null or empty
        if(pin == null || pin.equals("")){

            log.error("isPinFormatValid : Pin is empty");

            //return false
            return false;

        }
        //check the pin is 4 digits
        if(pin.length()!= 4){

            log.error("isPinFormatValid : Pin length is not valid");

            return false;

        }

        //make sure that the pin is integer
        try{

            //parse the string pin to integer
            Integer.parseInt(pin);

        } catch(Exception e){

            log.error("isPinFormatValid : Pin must be a 4 digit number , parsing error");

            //if any exception occurs pin is invalid
            return false;
        }

        return true;
    }
   }

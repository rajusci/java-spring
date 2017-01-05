package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.MessageSpiel;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by ameenci on 8/9/14.
 */
public class MessageSpielValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return MessageSpiel.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        MessageSpiel messageSpiel = (MessageSpiel) target;

        //checking msiName is greater than 100
        if(messageSpiel.getMsiName()!=null){

            if(messageSpiel.getMsiName().length()>100 || messageSpiel.getMsiName().length() < 3 ){

                errors.rejectValue("msiname","{messagespiel.msiname.size}");

            }


        }


    }
}

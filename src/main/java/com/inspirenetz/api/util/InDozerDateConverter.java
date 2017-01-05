package com.inspirenetz.api.util;

import org.dozer.CustomConverter;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Created by sandheepgr on 12/8/14.
 *
 *
 * A class that acts a converter for the String to Time and Time to String
 * for the Dozer mapping.
 */
public class InDozerDateConverter implements CustomConverter {


    @Override
    public Object convert(Object destField, Object sourceField, Class<?> destClass, Class<?> sourceClass) {


        // If the source field is not set, then return null
        if ( sourceField == null ){

            return null;

        }


        // Check if the sourceField is an instance of string
        // String --> Time conversion
        // We need to convert the String to a Time object using parsing
        // and return
        //
        // Else if its instance of Time, then we just need to return the toString method
        if ( sourceField instanceof String ){

            // Create the SimpleDateFormat object
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Parse the date
            java.util.Date utilDate = null;

            try {

                utilDate = dateFormat.parse(sourceField.toString());

            } catch (ParseException e) {

                return null;

            }

            // Return the sql date with the date as utilDate
            return  new Date(utilDate.getTime());


        } else if ( sourceField instanceof  Date ) {

            // Return the sourceField.toString method
            return sourceField.toString();

        } else {

            // If nothing matches, return the sourceField itself
            return sourceField;

        }
    }
}

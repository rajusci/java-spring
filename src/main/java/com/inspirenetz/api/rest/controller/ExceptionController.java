package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.DataValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by sandheepgr on 16/2/14.
 */
@ControllerAdvice
public class ExceptionController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private DataValidationUtils dataValidationUtils;


    private final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public APIResponseObject handleIOException(Exception ex) {

        // Log the exception as error
        log.error("Exception", ex);

        // Get the response object
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Set the status to failed
        retData.setStatus(APIResponseStatus.failed);

        // Set the error code
        retData.setErrorCode(APIErrorCode.ERR_EXCEPTION);

        // Set the error description
        retData.setErrorDesc(ex.getMessage());

        // Return the retData
        return retData;

    }


    @ExceptionHandler(BindException.class)
    @ResponseBody
    public APIResponseObject handleBindException(BindException ex) {

        // Log the exception as error
        log.error("Binding Exception", ex);

        // Get the response object
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Set the status to failed
        retData.setStatus(APIResponseStatus.failed);

        // Set the error code
        retData.setErrorCode(APIErrorCode.ERR_INVALID_INPUT);

        // Variable holding the data
        String messages = "";

        // Go through the validation errors and then append the messages
        for (FieldError error : ex.getFieldErrors()) {

            messages +=  error.getField() + ": " + error.getDefaultMessage() + "\n";

        }

        // Set the error description
        retData.setErrorDesc(messages);

        // Return the retData
        return retData;

    }



    @ExceptionHandler(InspireNetzException.class)
    @ResponseBody
    public APIResponseObject handleApplicationException(InspireNetzException ex) {

        // Log the exception as error
        log.info("InspireNetzException", ex);

        // Get the response object
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Set the status to failed
        retData.setStatus(APIResponseStatus.failed);

        // If the bindingResult is not null, then we need to get the results
        if ( ex.getBindingResult() != null ) {

            // Get the messages for the binding result
            String messages = dataValidationUtils.getValidationMessages(ex.getBindingResult(),messageSource);

            // Set the data field as the messages
            retData.setData(messages);

            // Log the message
            log.info("BindingREsult error message: " + messages);

        } else {

            // Set the data field as the message passed to the InspireNetzException
            retData.setData(ex.getExData());

            // Log the message
            log.info("Error Info " + ex.getExData());

        }



        // Set the error code
        retData.setErrorCode(ex.getErrorCode());

        // Set the error description
        retData.setErrorDesc(ex.getErrorCode().getLocalizedDesc(messageSource));

        // Return the retData
        return retData;


    }
}

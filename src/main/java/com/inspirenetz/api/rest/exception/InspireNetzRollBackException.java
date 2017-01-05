package com.inspirenetz.api.rest.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import org.springframework.validation.BindingResult;

import java.io.Serializable;

/**
 * Created by sandheepgr on 16/2/14.
 */
public class InspireNetzRollBackException extends RuntimeException implements Serializable{

    private APIErrorCode errorCode;

    private String exData = "";

    private BindingResult bindingResult;


    public InspireNetzRollBackException(String message)
    {
        super(message);
    }


    public InspireNetzRollBackException(APIErrorCode errorCode) {

        super();

        this.errorCode = errorCode;

    }


    public InspireNetzRollBackException(APIErrorCode errorCode, String exData) {

        super();

        this.errorCode = errorCode;

        this.exData = exData;

    }


    public InspireNetzRollBackException(APIErrorCode errorCode, BindingResult bindingResult) {

        super();

        this.errorCode = errorCode;

        this.bindingResult = bindingResult;


    }

    public InspireNetzRollBackException(Exception e){
        super(e.getMessage());
    }

    @JsonIgnore
    @Override
    public Throwable getCause(){
        return super.getCause();
    }

    @JsonIgnore
    @Override
    public StackTraceElement[] getStackTrace(){
        return super.getStackTrace();
    }


    public APIErrorCode getErrorCode() {
        return errorCode;
    }

    public String getExData() {
        return exData;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}

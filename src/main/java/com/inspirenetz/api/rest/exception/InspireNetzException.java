package com.inspirenetz.api.rest.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import org.springframework.validation.BindingResult;

import java.io.Serializable;

/**
 * Created by sandheepgr on 16/2/14.
 */
public class InspireNetzException extends Exception implements Serializable{

    private APIErrorCode errorCode;

    private String exData = "";

    private BindingResult bindingResult;


    public InspireNetzException(String message)
    {
        super(message);
    }


    public InspireNetzException(APIErrorCode errorCode) {

        super();

        this.errorCode = errorCode;

    }


    public InspireNetzException(APIErrorCode errorCode,String exData ) {

        super();

        this.errorCode = errorCode;

        this.exData = exData;

    }


    public InspireNetzException(APIErrorCode errorCode,BindingResult bindingResult) {

        super();

        this.errorCode = errorCode;

        this.bindingResult = bindingResult;


    }

    public InspireNetzException(Exception e){
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

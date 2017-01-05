package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 11/8/14.
 */
public class RedemptionServiceResponse {

    private String status ="success";

    private String data = "100101";

    private String errorcode = "";


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }
}

package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 11/8/14.
 */
public class RedemptionVoucherOperationResponse {

    private String status;

    private String errorcode;

    private String voucherCode;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
}

package com.inspirenetz.api.rest.resource;

/**
 * Created by alameen on 10/2/15.
 */
public class RedemptionVoucherValidityResource extends  BaseResource {


    private  Long rvrId;

    private String rvrProductCode;


    public Long getRvrId() {
        return rvrId;
    }

    public void setRvrId(Long rvrId) {
        this.rvrId = rvrId;
    }

    public String getRvrProductCode() {
        return rvrProductCode;
    }

    public void setRvrProductCode(String rvrProductCode) {
        this.rvrProductCode = rvrProductCode;
    }
}

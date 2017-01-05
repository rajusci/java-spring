package com.inspirenetz.api.rest.resource;


import java.util.Set;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class RedemptionMerchantLocationResource extends BaseResource {



    private Long rmlId;
    private String rmlLocation;
    private Long rmlMerNo;

    public Long getRmlMerNo() {
        return rmlMerNo;
    }

    public void setRmlMerNo(Long rmlMerNo) {
        this.rmlMerNo = rmlMerNo;
    }

    public String getRmlLocation() {
        return rmlLocation;
    }

    public void setRmlLocation(String rmlLocation) {
        this.rmlLocation = rmlLocation;
    }

    public Long getRmlId() {
        return rmlId;
    }

    public void setRmlId(Long rmlId) {
        this.rmlId = rmlId;
    }

    @Override
    public String toString() {
        return "RedemptionMerchantLocationResource{" +
                "rmlMerNo=" + rmlMerNo +
                ", rmlLocation='" + rmlLocation + '\'' +
                ", rmlId=" + rmlId +
                '}';
    }
}

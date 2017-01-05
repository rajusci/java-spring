package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 15/5/14.
 */
public class MerchantLocationResource extends  BaseResource {


    private Long melId;


    private Long melMerchantNo;


    private String melLocation;


    private String melLatitude;


    private String melLongitude;


    private int melLocationInUse;


    public Long getMelId() {
        return melId;
    }

    public void setMelId(Long melId) {
        this.melId = melId;
    }

    public Long getMelMerchantNo() {
        return melMerchantNo;
    }

    public void setMelMerchantNo(Long melMerchantNo) {
        this.melMerchantNo = melMerchantNo;
    }

    public String getMelLocation() {
        return melLocation;
    }

    public void setMelLocation(String melLocation) {
        this.melLocation = melLocation;
    }

    public String getMelLatitude() {
        return melLatitude;
    }

    public void setMelLatitude(String melLatitude) {
        this.melLatitude = melLatitude;
    }

    public String getMelLongitude() {
        return melLongitude;
    }

    public void setMelLongitude(String melLongitude) {
        this.melLongitude = melLongitude;
    }

    public int getMelLocationInUse() {
        return melLocationInUse;
    }

    public void setMelLocationInUse(int melLocationInUse) {
        this.melLocationInUse = melLocationInUse;
    }
}

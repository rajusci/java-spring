package com.inspirenetz.api.rest.resource;


import java.sql.Date;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class PromotionalEventResource extends BaseResource {

    private Long preId;
    private String preEventCode;
    private String preEventName;
    private String preDescription;
    private Long preLocation;
    private Date preStartDate;
    private Date preEndDate;
    private Long preMerchantNo;
    private Integer preEventType;

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public String getPreEventCode() {
        return preEventCode;
    }

    public void setPreEventCode(String preEventCode) {
        this.preEventCode = preEventCode;
    }

    public String getPreEventName() {
        return preEventName;
    }

    public void setPreEventName(String preEventName) {
        this.preEventName = preEventName;
    }

    public String getPreDescription() {
        return preDescription;
    }

    public void setPreDescription(String preDescription) {
        this.preDescription = preDescription;
    }

    public Long getPreLocation() {
        return preLocation;
    }

    public void setPreLocation(Long preLocation) {
        this.preLocation = preLocation;
    }

    public Date getPreStartDate() {
        return preStartDate;
    }

    public void setPreStartDate(Date preStartDate) {
        this.preStartDate = preStartDate;
    }

    public Date getPreEndDate() {
        return preEndDate;
    }

    public void setPreEndDate(Date preEndDate) {
        this.preEndDate = preEndDate;
    }

    public Long getPreMerchantNo() {
        return preMerchantNo;
    }

    public void setPreMerchantNo(Long preMerchantNo) {
        this.preMerchantNo = preMerchantNo;
    }

    public Integer getPreEventType() {
        return preEventType;
    }

    public void setPreEventType(Integer preEventType) {
        this.preEventType = preEventType;
    }
}

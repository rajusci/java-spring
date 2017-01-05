package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.RedemptionVoucherStatus;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by saneeshci on 30/09/14.
 */
@Entity
@Table(name="PROMOTIONAL_EVENTS")
public class PromotionalEvent extends AuditedEntity {


    @Column(name = "PRE_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preId;

    @Column(name = "PRE_EVENT_CODE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String preEventCode;

    @Column(name = "PRE_EVENT_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String preEventName;

    @Column(name = "PRE_DESCRIPTION" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String preDescription;

    @Column(name = "PRE_LOCATION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long preLocation;

    @Column(name = "PRE_START_DATE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date preStartDate;

    @Column(name = "PRE_END_DATE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date preEndDate;

    @Column(name = "PRE_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long preMerchantNo;


    @Column(name = "PRE_EVENT_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
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

    @Override
    public String toString() {
        return "PromotionalEvent{" +
                "preId=" + preId +
                ", preEventCode='" + preEventCode + '\'' +
                ", preEventName='" + preEventName + '\'' +
                ", preDescription='" + preDescription + '\'' +
                ", preLocation=" + preLocation +
                ", preStartDate=" + preStartDate +
                ", preEndDate=" + preEndDate +
                ", preMerchantNo=" + preMerchantNo +
                ", preEventType=" + preEventType +
                '}';
    }
}

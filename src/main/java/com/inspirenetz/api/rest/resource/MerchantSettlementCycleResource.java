package com.inspirenetz.api.rest.resource;

import java.sql.Date;

/**
 * Created by saneeshci on 21/10/15.
 */
public class MerchantSettlementCycleResource extends BaseResource {

    private Long mscId;
    private Long mscMerchantNo;
    private Long mscRedemptionMerchant;
    private Long mscMerchantLocation;
    private Integer mscStatus;
    private Double mscAmount;
    private String mscReference;
    private Date mscStartDate;
    private Date mscEndDate;
    private String mscSettledBy;
    private Date mscSettledDate;
    private String merchantName;
    private String redemptionMerchantName;

    public Long getMscId() {
        return mscId;
    }

    public void setMscId(Long mscId) {
        this.mscId = mscId;
    }

    public Long getMscMerchantNo() {
        return mscMerchantNo;
    }

    public void setMscMerchantNo(Long mscMerchantNo) {
        this.mscMerchantNo = mscMerchantNo;
    }

    public Long getMscRedemptionMerchant() {
        return mscRedemptionMerchant;
    }

    public void setMscRedemptionMerchant(Long mscRedemptionMerchant) {
        this.mscRedemptionMerchant = mscRedemptionMerchant;
    }

    public Long getMscMerchantLocation() {
        return mscMerchantLocation;
    }

    public void setMscMerchantLocation(Long mscMerchantLocation) {
        this.mscMerchantLocation = mscMerchantLocation;
    }

    public Integer getMscStatus() {
        return mscStatus;
    }

    public void setMscStatus(Integer mscStatus) {
        this.mscStatus = mscStatus;
    }

    public Double getMscAmount() {
        return mscAmount;
    }

    public void setMscAmount(Double mscAmount) {
        this.mscAmount = mscAmount;
    }

    public String getMscReference() {
        return mscReference;
    }

    public void setMscReference(String mscReference) {
        this.mscReference = mscReference;
    }

    public Date getMscStartDate() {
        return mscStartDate;
    }

    public void setMscStartDate(Date mscStartDate) {
        this.mscStartDate = mscStartDate;
    }

    public Date getMscEndDate() {
        return mscEndDate;
    }

    public void setMscEndDate(Date mscEndDate) {
        this.mscEndDate = mscEndDate;
    }

    public String getMscSettledBy() {
        return mscSettledBy;
    }

    public void setMscSettledBy(String mscSettledBy) {
        this.mscSettledBy = mscSettledBy;
    }

    public Date getMscSettledDate() {
        return mscSettledDate;
    }

    public void setMscSettledDate(Date mscSettledDate) {
        this.mscSettledDate = mscSettledDate;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getRedemptionMerchantName() {
        return redemptionMerchantName;
    }

    public void setRedemptionMerchantName(String redemptionMerchantName) {
        this.redemptionMerchantName = redemptionMerchantName;
    }
}

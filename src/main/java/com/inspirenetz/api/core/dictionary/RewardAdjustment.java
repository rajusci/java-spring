package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 11/8/14.
 */
public class RewardAdjustment {

    private String loyaltyId = "";

    private Long merchantNo;

    private Double rwdQty;

    private Long rwdCurrencyId ;

    private boolean isTierAffected = false;

    private String externalReference;

    private String intenalReference;

    private Long programNo;

    private boolean isPointReversed = false;

    private Long locationId;

    private boolean isTransferFailed  = false;

    private int channel;

    private int isRewardAdjustment= IndicatorStatus.NO;



    public String getLoyaltyId() {
        return loyaltyId;
    }

    public void setLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
    }

    public Long getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(Long merchantNo) {
        this.merchantNo = merchantNo;
    }

    public Double getRwdQty() {
        return rwdQty;
    }

    public void setRwdQty(Double rwdQty) {
        this.rwdQty = rwdQty;
    }

    public Long getRwdCurrencyId() {
        return rwdCurrencyId;
    }

    public void setRwdCurrencyId(Long rwdCurrencyId) {
        this.rwdCurrencyId = rwdCurrencyId;
    }

    public boolean isTierAffected() {
        return isTierAffected;
    }

    public void setTierAffected(boolean isTierAffected) {
        this.isTierAffected = isTierAffected;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getIntenalReference() {
        return intenalReference;
    }

    public void setIntenalReference(String intenalReference) {
        this.intenalReference = intenalReference;
    }

    public Long getProgramNo() {
        return programNo;
    }

    public void setProgramNo(Long programNo) {
        this.programNo = programNo;
    }

    public boolean isPointReversed() {
        return isPointReversed;
    }

    public void setPointReversed(boolean isPointReversed) {
        this.isPointReversed = isPointReversed;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long merchantNo) {
        this.locationId = merchantNo;
    }

    public boolean isTransferFailed() {
        return isTransferFailed;
    }

    public void setTransferFailed(boolean isTransferFailed) {
        this.isTransferFailed = isTransferFailed;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getIsRewardAdjustment() {
        return isRewardAdjustment;
    }

    public void setIsRewardAdjustment(int isRewardAdjustment) {
        this.isRewardAdjustment = isRewardAdjustment;
    }
}

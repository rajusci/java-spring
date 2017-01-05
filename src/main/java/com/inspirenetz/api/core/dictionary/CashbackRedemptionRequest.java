package com.inspirenetz.api.core.dictionary;

import com.inspirenetz.api.core.domain.RewardCurrency;

/**
 * Created by sandheepgr on 29/4/14.
 */
public class CashbackRedemptionRequest {




    private Long merchantNo;

    private Long userNo;

    private String loyaltyId;

    private Long userLocation;

    private double amount;

    private String txnRef;

    private Long rewardCurrencyId;

    private RewardCurrency rewardCurrency;

    private String auditDetails;


    public String getAuditDetails() {
        return auditDetails;
    }

    public void setAuditDetails(String auditDetails) {
        this.auditDetails = auditDetails;
    }

    public RewardCurrency getRewardCurrency() {
        return rewardCurrency;
    }

    public void setRewardCurrency(RewardCurrency rewardCurrency) {
        this.rewardCurrency = rewardCurrency;
    }

    public Long getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Long userLocation) {
        this.userLocation = userLocation;
    }

    public Long getRewardCurrencyId() {
        return rewardCurrencyId;
    }

    public void setRewardCurrencyId(Long rewardCurrencyId) {
        this.rewardCurrencyId = rewardCurrencyId;
    }

    public Long getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(Long merchantNo) {
        this.merchantNo = merchantNo;
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public String getLoyaltyId() {
        return loyaltyId;
    }

    public void setLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTxnRef() {
        return txnRef;
    }

    public void setTxnRef(String txnRef) {
        this.txnRef = txnRef;
    }
}

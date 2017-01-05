package com.inspirenetz.api.core.dictionary;

public class RedemptionData {
    private final Long merchantNo;
    private final Long rwdId;
    private final double totalRewardQty;
    private final String prdCode;
    private final String loyaltyId;
    private final double totalCashAmount;

    public RedemptionData(Long merchantNo, Long rwdId, double totalRewardQty, String prdCode, String loyaltyId, double totalCashAmount) {

        this.merchantNo = merchantNo;
        this.rwdId = rwdId;
        this.totalRewardQty = totalRewardQty;
        this.prdCode = prdCode;
        this.loyaltyId = loyaltyId;
        this.totalCashAmount = totalCashAmount;
    }


    public Long getMerchantNo() {
        return merchantNo;
    }

    public Long getRwdId() {
        return rwdId;
    }

    public double getTotalRewardQty() {
        return totalRewardQty;
    }

    public String getPrdCode() {
        return prdCode;
    }

    public String getLoyaltyId() {
        return loyaltyId;
    }

    public double getTotalCashAmount() {
        return totalCashAmount;
    }


}

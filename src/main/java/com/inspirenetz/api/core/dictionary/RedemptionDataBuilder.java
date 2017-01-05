package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 15/3/14.
 */
public class RedemptionDataBuilder {
    private Long merchantNo;
    private Long rwdId;
    private double totalRewardQty;
    private String prdCode;
    private String loyaltyId;
    private double totalCashAmount;

    private RedemptionDataBuilder() {
    }

    public static RedemptionDataBuilder aRedemptionData() {
        return new RedemptionDataBuilder();
    }

    public RedemptionDataBuilder withMerchantNo(Long merchantNo) {
        this.merchantNo = merchantNo;
        return this;
    }

    public RedemptionDataBuilder withRwdId(Long rwdId) {
        this.rwdId = rwdId;
        return this;
    }

    public RedemptionDataBuilder withTotalRewardQty(double totalRewardQty) {
        this.totalRewardQty = totalRewardQty;
        return this;
    }

    public RedemptionDataBuilder withPrdCode(String prdCode) {
        this.prdCode = prdCode;
        return this;
    }

    public RedemptionDataBuilder withLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
        return this;
    }

    public RedemptionDataBuilder withTotalCashAmount(double totalCashAmount) {
        this.totalCashAmount = totalCashAmount;
        return this;
    }

    public RedemptionData build() {
        RedemptionData redemptionData = new RedemptionData(merchantNo, rwdId, totalRewardQty, prdCode, loyaltyId, totalCashAmount);
        return redemptionData;
    }
}

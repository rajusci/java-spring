package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.MerchantRewardSummary;

import java.sql.Date;

/**
 * Created by sandheepgr on 24/8/14.
 */
public class MerchantRewardSummaryBuilder {
    private Long mrsId;
    private Long mrsMerchantNo;
    private Long mrsCurrencyId;
    private Long mrsBranch;
    private Date mrsDate;
    private Double mrsTotalRewarded = 0.0;
    private Double mrsTotalRedeemed = 0.0;
    private Double mrsRewardExpired = 0.0;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private MerchantRewardSummaryBuilder() {
    }

    public static MerchantRewardSummaryBuilder aMerchantRewardSummary() {
        return new MerchantRewardSummaryBuilder();
    }

    public MerchantRewardSummaryBuilder withMrsId(Long mrsId) {
        this.mrsId = mrsId;
        return this;
    }

    public MerchantRewardSummaryBuilder withMrsMerchantNo(Long mrsMerchantNo) {
        this.mrsMerchantNo = mrsMerchantNo;
        return this;
    }

    public MerchantRewardSummaryBuilder withMrsCurrencyId(Long mrsCurrencyId) {
        this.mrsCurrencyId = mrsCurrencyId;
        return this;
    }

    public MerchantRewardSummaryBuilder withMrsBranch(Long mrsBranch) {
        this.mrsBranch = mrsBranch;
        return this;
    }

    public MerchantRewardSummaryBuilder withMrsDate(Date mrsDate) {
        this.mrsDate = mrsDate;
        return this;
    }

    public MerchantRewardSummaryBuilder withMrsTotalRewarded(Double mrsTotalRewarded) {
        this.mrsTotalRewarded = mrsTotalRewarded;
        return this;
    }

    public MerchantRewardSummaryBuilder withMrsTotalRedeemed(Double mrsTotalRedeemed) {
        this.mrsTotalRedeemed = mrsTotalRedeemed;
        return this;
    }

    public MerchantRewardSummaryBuilder withMrsRewardExpired(Double mrsRewardExpired) {
        this.mrsRewardExpired = mrsRewardExpired;
        return this;
    }

    public MerchantRewardSummaryBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public MerchantRewardSummaryBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public MerchantRewardSummaryBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public MerchantRewardSummaryBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public MerchantRewardSummary build() {
        MerchantRewardSummary merchantRewardSummary = new MerchantRewardSummary();
        merchantRewardSummary.setMrsId(mrsId);
        merchantRewardSummary.setMrsMerchantNo(mrsMerchantNo);
        merchantRewardSummary.setMrsCurrencyId(mrsCurrencyId);
        merchantRewardSummary.setMrsBranch(mrsBranch);
        merchantRewardSummary.setMrsDate(mrsDate);
        merchantRewardSummary.setMrsTotalRewarded(mrsTotalRewarded);
        merchantRewardSummary.setMrsTotalRedeemed(mrsTotalRedeemed);
        merchantRewardSummary.setMrsRewardExpired(mrsRewardExpired);
        merchantRewardSummary.setCreatedAt(createdAt);
        merchantRewardSummary.setCreatedBy(createdBy);
        merchantRewardSummary.setUpdatedAt(updatedAt);
        merchantRewardSummary.setUpdatedBy(updatedBy);
        return merchantRewardSummary;
    }
}

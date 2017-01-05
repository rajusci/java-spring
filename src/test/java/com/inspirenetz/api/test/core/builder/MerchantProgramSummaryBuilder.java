package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.MerchantProgramSummary;

import java.util.Date;

/**
 * Created by sandheepgr on 24/8/14.
 */
public class MerchantProgramSummaryBuilder {
    private Long mpsId;
    private Long mpsMerchantNo;
    private Long mpsBranch;
    private Long mpsProgramId;
    private Double mpsTransactionAmount = 0.0;
    private Double mpsRewardCount = 0.0;
    private int mpsTransactionCount = 0;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private MerchantProgramSummaryBuilder() {
    }

    public static MerchantProgramSummaryBuilder aMerchantProgramSummary() {
        return new MerchantProgramSummaryBuilder();
    }

    public MerchantProgramSummaryBuilder withMpsId(Long mpsId) {
        this.mpsId = mpsId;
        return this;
    }

    public MerchantProgramSummaryBuilder withMpsMerchantNo(Long mpsMerchantNo) {
        this.mpsMerchantNo = mpsMerchantNo;
        return this;
    }

    public MerchantProgramSummaryBuilder withMpsBranch(Long mpsBranch) {
        this.mpsBranch = mpsBranch;
        return this;
    }

    public MerchantProgramSummaryBuilder withMpsProgramId(Long mpsProgramId) {
        this.mpsProgramId = mpsProgramId;
        return this;
    }

    public MerchantProgramSummaryBuilder withMpsTransactionAmount(Double mpsTransactionAmount) {
        this.mpsTransactionAmount = mpsTransactionAmount;
        return this;
    }

    public MerchantProgramSummaryBuilder withMpsRewardCount(Double mpsRewardCount) {
        this.mpsRewardCount = mpsRewardCount;
        return this;
    }

    public MerchantProgramSummaryBuilder withMpsTransactionCount(int mpsTransactionCount) {
        this.mpsTransactionCount = mpsTransactionCount;
        return this;
    }

    public MerchantProgramSummaryBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public MerchantProgramSummaryBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public MerchantProgramSummaryBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public MerchantProgramSummaryBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public MerchantProgramSummary build() {
        MerchantProgramSummary merchantProgramSummary = new MerchantProgramSummary();
        merchantProgramSummary.setMpsId(mpsId);
        merchantProgramSummary.setMpsMerchantNo(mpsMerchantNo);
        merchantProgramSummary.setMpsBranch(mpsBranch);
        merchantProgramSummary.setMpsProgramId(mpsProgramId);
        merchantProgramSummary.setMpsTransactionAmount(mpsTransactionAmount);
        merchantProgramSummary.setMpsRewardCount(mpsRewardCount);
        merchantProgramSummary.setMpsTransactionCount(mpsTransactionCount);
        merchantProgramSummary.setCreatedAt(createdAt);
        merchantProgramSummary.setCreatedBy(createdBy);
        merchantProgramSummary.setUpdatedAt(updatedAt);
        merchantProgramSummary.setUpdatedBy(updatedBy);
        return merchantProgramSummary;
    }
}

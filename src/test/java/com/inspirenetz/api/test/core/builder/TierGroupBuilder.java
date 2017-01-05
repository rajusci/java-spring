package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.TierGroup;

import java.util.Date;

/**
 * Created by saneesh-ci on 20/8/14.
 */
public class TierGroupBuilder {

    private Long tigId;
    private String tigName = "";
    private Long tigMerchantNo;
    private Long tigLocation =0L;
    private Long tigRewardCurrency;
    private Long tigTransactionCurrency;
    private String tigApplicableGroup;
    private Integer tigEvaluationPeriodCompType;
    private Integer tigUpgradeCheckPeriod;
    private Integer tigDowngradeCheckPeriod;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private TierGroupBuilder() {
    }

    public static TierGroupBuilder aTierGroup() {
        return new TierGroupBuilder();
    }

    public TierGroupBuilder withTigId(Long tigId) {
        this.tigId = tigId;
        return this;
    }

    public TierGroupBuilder withTigName(String tigName) {
        this.tigName = tigName;
        return this;
    }

    public TierGroupBuilder withTigMerchantNo(Long tigMerchantNo) {
        this.tigMerchantNo = tigMerchantNo;
        return this;
    }

    public TierGroupBuilder withTigLocation(Long tigLocation) {
        this.tigLocation = tigLocation;
        return this;
    }

    public TierGroupBuilder withTigRewardCurrency(Long tigRewardCurrency) {
        this.tigRewardCurrency = tigRewardCurrency;
        return this;
    }

    public TierGroupBuilder withTigTransactionCurrency(Long tigTransactionCurrency) {
        this.tigTransactionCurrency = tigTransactionCurrency;
        return this;
    }

    public TierGroupBuilder withTigApplicableGroup(String tigApplicableGroup) {
        this.tigApplicableGroup = tigApplicableGroup;
        return this;
    }

    public TierGroupBuilder withTigEvaluationPeriodCompType(Integer tigEvaluationPeriodCompType) {
        this.tigEvaluationPeriodCompType = tigEvaluationPeriodCompType;
        return this;
    }

    public TierGroupBuilder withTigUpgradeCheckPeriod(Integer tigUpgradeCheckPeriod) {
        this.tigUpgradeCheckPeriod = tigUpgradeCheckPeriod;
        return this;
    }

    public TierGroupBuilder withTigDowngradeCheckPeriod(Integer tigDowngradeCheckPeriod) {
        this.tigDowngradeCheckPeriod = tigDowngradeCheckPeriod;
        return this;
    }


    public TierGroupBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public TierGroupBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public TierGroupBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public TierGroupBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public TierGroup build() {
        TierGroup tierGroup = new TierGroup();
        tierGroup.setTigId(tigId);
        tierGroup.setTigName(tigName);
        tierGroup.setTigMerchantNo(tigMerchantNo);
        tierGroup.setTigLocation(tigLocation);
        tierGroup.setTigRewardCurrency(tigRewardCurrency);
        tierGroup.setTigTransactionCurrency(tigTransactionCurrency);
        tierGroup.setTigApplicableGroup(tigApplicableGroup);
        tierGroup.setTigEvaluationPeriodCompType(tigEvaluationPeriodCompType);
        tierGroup.setTigUpgradeCheckPeriod(tigUpgradeCheckPeriod);
        tierGroup.setTigDowngradeCheckPeriod(tigDowngradeCheckPeriod);
        tierGroup.setCreatedAt(createdAt);
        tierGroup.setCreatedBy(createdBy);
        tierGroup.setUpdatedAt(updatedAt);
        tierGroup.setUpdatedBy(updatedBy);
        return tierGroup;
    }
}

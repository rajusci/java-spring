package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.MerchantSettlementCycle;

import java.sql.Date;

/**
 * Created by saneesh on 21/10/15.
 */
public class MerchantSettlementCycleBuilder {
    private Date mscSettledDate;
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

    private MerchantSettlementCycleBuilder() {
    }

    public static MerchantSettlementCycleBuilder aMerchantSettlementCycle() {
        return new MerchantSettlementCycleBuilder();
    }

    public MerchantSettlementCycleBuilder withMscSettledDate(Date mscSettledDate) {
        this.mscSettledDate = mscSettledDate;
        return this;
    }

    public MerchantSettlementCycleBuilder withMscId(Long mscId) {
        this.mscId = mscId;
        return this;
    }

    public MerchantSettlementCycleBuilder withMscMerchantNo(Long mscMerchantNo) {
        this.mscMerchantNo = mscMerchantNo;
        return this;
    }

    public MerchantSettlementCycleBuilder withMscRedemptionMerchant(Long mscRedemptionMerchant) {
        this.mscRedemptionMerchant = mscRedemptionMerchant;
        return this;
    }

    public MerchantSettlementCycleBuilder withMscMerchantLocation(Long mscMerchantLocation) {
        this.mscMerchantLocation = mscMerchantLocation;
        return this;
    }

    public MerchantSettlementCycleBuilder withMscStatus(Integer mscStatus) {
        this.mscStatus = mscStatus;
        return this;
    }

    public MerchantSettlementCycleBuilder withMscAmount(Double mscAmount) {
        this.mscAmount = mscAmount;
        return this;
    }

    public MerchantSettlementCycleBuilder withMscReference(String mscReference) {
        this.mscReference = mscReference;
        return this;
    }

    public MerchantSettlementCycleBuilder withMscStartDate(Date mscStartDate) {
        this.mscStartDate = mscStartDate;
        return this;
    }

    public MerchantSettlementCycleBuilder withMscEndDate(Date mscEndDate) {
        this.mscEndDate = mscEndDate;
        return this;
    }

    public MerchantSettlementCycleBuilder withMscSettledBy(String mscSettledBy) {
        this.mscSettledBy = mscSettledBy;
        return this;
    }

    public MerchantSettlementCycleBuilder but() {
        return aMerchantSettlementCycle().withMscSettledDate(mscSettledDate).withMscId(mscId).withMscMerchantNo(mscMerchantNo).withMscRedemptionMerchant(mscRedemptionMerchant).withMscMerchantLocation(mscMerchantLocation).withMscStatus(mscStatus).withMscAmount(mscAmount).withMscReference(mscReference).withMscStartDate(mscStartDate).withMscEndDate(mscEndDate).withMscSettledBy(mscSettledBy);
    }

    public MerchantSettlementCycle build() {
        MerchantSettlementCycle merchantSettlementCycle = new MerchantSettlementCycle();
        merchantSettlementCycle.setMscSettledDate(mscSettledDate);
        merchantSettlementCycle.setMscId(mscId);
        merchantSettlementCycle.setMscMerchantNo(mscMerchantNo);
        merchantSettlementCycle.setMscRedemptionMerchant(mscRedemptionMerchant);
        merchantSettlementCycle.setMscMerchantLocation(mscMerchantLocation);
        merchantSettlementCycle.setMscStatus(mscStatus);
        merchantSettlementCycle.setMscAmount(mscAmount);
        merchantSettlementCycle.setMscReference(mscReference);
        merchantSettlementCycle.setMscStartDate(mscStartDate);
        merchantSettlementCycle.setMscEndDate(mscEndDate);
        merchantSettlementCycle.setMscSettledBy(mscSettledBy);
        return merchantSettlementCycle;
    }
}

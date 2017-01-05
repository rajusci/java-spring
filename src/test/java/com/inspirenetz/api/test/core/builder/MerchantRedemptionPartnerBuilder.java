package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.MerchantRedemptionPartner;

import java.util.Date;

/**
 * Created by ameen on 26/6/15.
 */
public class MerchantRedemptionPartnerBuilder {
    private Long mrpId;
    private Long mrpMerchantNo = 0L;
    private Long mrpRedemptionMerchant = 0L;
    private Integer  mrpEnabled= IndicatorStatus.NO;
    private String redemptionMerchantName;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private MerchantRedemptionPartnerBuilder() {
    }

    public static MerchantRedemptionPartnerBuilder aMerchantRedemptionPartner() {
        return new MerchantRedemptionPartnerBuilder();
    }

    public MerchantRedemptionPartnerBuilder withMrpId(Long mrpId) {
        this.mrpId = mrpId;
        return this;
    }

    public MerchantRedemptionPartnerBuilder withMrpMerchantNo(Long mrpMerchantNo) {
        this.mrpMerchantNo = mrpMerchantNo;
        return this;
    }

    public MerchantRedemptionPartnerBuilder withMrpRedemptionMerchant(Long mrpRedemptionMerchant) {
        this.mrpRedemptionMerchant = mrpRedemptionMerchant;
        return this;
    }

    public MerchantRedemptionPartnerBuilder withMrpEnabled(Integer mrpEnabled) {
        this.mrpEnabled = mrpEnabled;
        return this;
    }

    public MerchantRedemptionPartnerBuilder withRedemptionMerchantName(String redemptionMerchantName) {
        this.redemptionMerchantName = redemptionMerchantName;
        return this;
    }

    public MerchantRedemptionPartnerBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public MerchantRedemptionPartnerBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public MerchantRedemptionPartnerBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public MerchantRedemptionPartnerBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public MerchantRedemptionPartnerBuilder but() {
        return aMerchantRedemptionPartner().withMrpId(mrpId).withMrpMerchantNo(mrpMerchantNo).withMrpRedemptionMerchant(mrpRedemptionMerchant).withMrpEnabled(mrpEnabled).withRedemptionMerchantName(redemptionMerchantName).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public MerchantRedemptionPartner build() {
        MerchantRedemptionPartner merchantRedemptionPartner = new MerchantRedemptionPartner();
        merchantRedemptionPartner.setMrpId(mrpId);
        merchantRedemptionPartner.setMrpMerchantNo(mrpMerchantNo);
        merchantRedemptionPartner.setMrpRedemptionMerchant(mrpRedemptionMerchant);
        merchantRedemptionPartner.setMrpEnabled(mrpEnabled);
        merchantRedemptionPartner.setRedemptionMerchantName(redemptionMerchantName);
        merchantRedemptionPartner.setCreatedAt(createdAt);
        merchantRedemptionPartner.setCreatedBy(createdBy);
        merchantRedemptionPartner.setUpdatedAt(updatedAt);
        merchantRedemptionPartner.setUpdatedBy(updatedBy);
        return merchantRedemptionPartner;
    }
}

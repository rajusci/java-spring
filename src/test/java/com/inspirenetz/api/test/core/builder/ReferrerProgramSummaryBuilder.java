package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.ReferrerProgramSummary;

import java.util.Date;

/**
 * Created by ameen on 8/10/15.
 */
public class ReferrerProgramSummaryBuilder {
    private Long rpsId;
    private Long rpsMerchantNo;
    private String rpsLoyaltyId;
    private Long rpsProgramId;
    private int rpsProgramVisit= 0 ;
    private String rpsRefereeLoyaltyId;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private ReferrerProgramSummaryBuilder() {
    }

    public static ReferrerProgramSummaryBuilder aReferrerProgramSummary() {
        return new ReferrerProgramSummaryBuilder();
    }

    public ReferrerProgramSummaryBuilder withRpsId(Long rpsId) {
        this.rpsId = rpsId;
        return this;
    }

    public ReferrerProgramSummaryBuilder withRpsMerchantNo(Long rpsMerchantNo) {
        this.rpsMerchantNo = rpsMerchantNo;
        return this;
    }

    public ReferrerProgramSummaryBuilder withRpsLoyaltyId(String rpsLoyaltyId) {
        this.rpsLoyaltyId = rpsLoyaltyId;
        return this;
    }

    public ReferrerProgramSummaryBuilder withRpsProgramId(Long rpsProgramId) {
        this.rpsProgramId = rpsProgramId;
        return this;
    }

    public ReferrerProgramSummaryBuilder withRpsProgramVisit(int rpsProgramVisit) {
        this.rpsProgramVisit = rpsProgramVisit;
        return this;
    }

    public ReferrerProgramSummaryBuilder withRpsRefereeLoyaltyId(String rpsRefereeLoyaltyId) {
        this.rpsRefereeLoyaltyId = rpsRefereeLoyaltyId;
        return this;
    }

    public ReferrerProgramSummaryBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ReferrerProgramSummaryBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public ReferrerProgramSummaryBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public ReferrerProgramSummaryBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public ReferrerProgramSummaryBuilder but() {
        return aReferrerProgramSummary().withRpsId(rpsId).withRpsMerchantNo(rpsMerchantNo).withRpsLoyaltyId(rpsLoyaltyId).withRpsProgramId(rpsProgramId).withRpsProgramVisit(rpsProgramVisit).withRpsRefereeLoyaltyId(rpsRefereeLoyaltyId).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public ReferrerProgramSummary build() {
        ReferrerProgramSummary referrerProgramSummary = new ReferrerProgramSummary();
        referrerProgramSummary.setRpsId(rpsId);
        referrerProgramSummary.setRpsMerchantNo(rpsMerchantNo);
        referrerProgramSummary.setRpsLoyaltyId(rpsLoyaltyId);
        referrerProgramSummary.setRpsProgramId(rpsProgramId);
        referrerProgramSummary.setRpsProgramVisit(rpsProgramVisit);
        referrerProgramSummary.setRpsRefereeLoyaltyId(rpsRefereeLoyaltyId);
        referrerProgramSummary.setCreatedAt(createdAt);
        referrerProgramSummary.setCreatedBy(createdBy);
        referrerProgramSummary.setUpdatedAt(updatedAt);
        referrerProgramSummary.setUpdatedBy(updatedBy);
        return referrerProgramSummary;
    }
}

package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.CustomerProgramSummary;

import java.util.Date;

/**
 * Created by sandheepgr on 24/8/14.
 */
public class CustomerProgramSummaryBuilder {
    private Long cpsId;
    private Long cpsMerchantNo;
    private String cpsLoyaltyId;
    private Long cpsProgramId;
    private double cpsAwardCount = 0;
    private double cpsProgramAmount = 0;
    private int cpsProgramQuantity = 0;
    private int cpsProgramVisit= 0 ;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private CustomerProgramSummaryBuilder() {
    }

    public static CustomerProgramSummaryBuilder aCustomerProgramSummary() {
        return new CustomerProgramSummaryBuilder();
    }

    public CustomerProgramSummaryBuilder withCpsId(Long cpsId) {
        this.cpsId = cpsId;
        return this;
    }

    public CustomerProgramSummaryBuilder withCpsMerchantNo(Long cpsMerchantNo) {
        this.cpsMerchantNo = cpsMerchantNo;
        return this;
    }

    public CustomerProgramSummaryBuilder withCpsLoyaltyId(String cpsLoyaltyId) {
        this.cpsLoyaltyId = cpsLoyaltyId;
        return this;
    }

    public CustomerProgramSummaryBuilder withCpsProgramId(Long cpsProgramId) {
        this.cpsProgramId = cpsProgramId;
        return this;
    }

    public CustomerProgramSummaryBuilder withCpsAwardCount(double cpsAwardCount) {
        this.cpsAwardCount = cpsAwardCount;
        return this;
    }

    public CustomerProgramSummaryBuilder withCpsProgramAmount(double cpsProgramAmount) {
        this.cpsProgramAmount = cpsProgramAmount;
        return this;
    }

    public CustomerProgramSummaryBuilder withCpsProgramQuantity(int cpsProgramQuantity) {
        this.cpsProgramQuantity = cpsProgramQuantity;
        return this;
    }

    public CustomerProgramSummaryBuilder withCpsProgramVisit(int cpsProgramVisit) {
        this.cpsProgramVisit = cpsProgramVisit;
        return this;
    }

    public CustomerProgramSummaryBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CustomerProgramSummaryBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CustomerProgramSummaryBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CustomerProgramSummaryBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CustomerProgramSummary build() {
        CustomerProgramSummary customerProgramSummary = new CustomerProgramSummary();
        customerProgramSummary.setCpsId(cpsId);
        customerProgramSummary.setCpsMerchantNo(cpsMerchantNo);
        customerProgramSummary.setCpsLoyaltyId(cpsLoyaltyId);
        customerProgramSummary.setCpsProgramId(cpsProgramId);
        customerProgramSummary.setCpsAwardCount(cpsAwardCount);
        customerProgramSummary.setCpsProgramAmount(cpsProgramAmount);
        customerProgramSummary.setCpsProgramQuantity(cpsProgramQuantity);
        customerProgramSummary.setCpsProgramVisit(cpsProgramVisit);
        customerProgramSummary.setCreatedAt(createdAt);
        customerProgramSummary.setCreatedBy(createdBy);
        customerProgramSummary.setUpdatedAt(updatedAt);
        customerProgramSummary.setUpdatedBy(updatedBy);
        return customerProgramSummary;
    }
}

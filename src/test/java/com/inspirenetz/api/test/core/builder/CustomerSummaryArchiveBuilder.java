package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.CustomerSummaryArchive;

import java.util.Date;

/**
 * Created by sandheepgr on 24/8/14.
 */
public class CustomerSummaryArchiveBuilder {
    private Long csaId;
    private Long csaMerchantNo;
    private String csaLoyaltyId = "0";
    private Long csaLocation = 0L;
    private int csaPeriodYyyy = 0 ;
    private int csaPeriodQq = 0;
    private int csaPeriodMm = 0;
    private int csaVisitCount = 0;
    private int csaQuantity = 0;
    private double csaTxnAmount = 0;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private CustomerSummaryArchiveBuilder() {
    }

    public static CustomerSummaryArchiveBuilder aCustomerSummaryArchive() {
        return new CustomerSummaryArchiveBuilder();
    }

    public CustomerSummaryArchiveBuilder withCsaId(Long csaId) {
        this.csaId = csaId;
        return this;
    }

    public CustomerSummaryArchiveBuilder withCsaMerchantNo(Long csaMerchantNo) {
        this.csaMerchantNo = csaMerchantNo;
        return this;
    }

    public CustomerSummaryArchiveBuilder withCsaLoyaltyId(String csaLoyaltyId) {
        this.csaLoyaltyId = csaLoyaltyId;
        return this;
    }

    public CustomerSummaryArchiveBuilder withCsaLocation(Long csaLocation) {
        this.csaLocation = csaLocation;
        return this;
    }

    public CustomerSummaryArchiveBuilder withCsaPeriodYyyy(int csaPeriodYyyy) {
        this.csaPeriodYyyy = csaPeriodYyyy;
        return this;
    }

    public CustomerSummaryArchiveBuilder withCsaPeriodQq(int csaPeriodQq) {
        this.csaPeriodQq = csaPeriodQq;
        return this;
    }

    public CustomerSummaryArchiveBuilder withCsaPeriodMm(int csaPeriodMm) {
        this.csaPeriodMm = csaPeriodMm;
        return this;
    }

    public CustomerSummaryArchiveBuilder withCsaVisitCount(int csaVisitCount) {
        this.csaVisitCount = csaVisitCount;
        return this;
    }

    public CustomerSummaryArchiveBuilder withCsaQuantity(int csaQuantity) {
        this.csaQuantity = csaQuantity;
        return this;
    }

    public CustomerSummaryArchiveBuilder withCsaTxnAmount(double csaTxnAmount) {
        this.csaTxnAmount = csaTxnAmount;
        return this;
    }

    public CustomerSummaryArchiveBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CustomerSummaryArchiveBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CustomerSummaryArchiveBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CustomerSummaryArchiveBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CustomerSummaryArchive build() {
        CustomerSummaryArchive customerSummaryArchive = new CustomerSummaryArchive();
        customerSummaryArchive.setCsaId(csaId);
        customerSummaryArchive.setCsaMerchantNo(csaMerchantNo);
        customerSummaryArchive.setCsaLoyaltyId(csaLoyaltyId);
        customerSummaryArchive.setCsaLocation(csaLocation);
        customerSummaryArchive.setCsaPeriodYyyy(csaPeriodYyyy);
        customerSummaryArchive.setCsaPeriodQq(csaPeriodQq);
        customerSummaryArchive.setCsaPeriodMm(csaPeriodMm);
        customerSummaryArchive.setCsaVisitCount(csaVisitCount);
        customerSummaryArchive.setCsaQuantity(csaQuantity);
        customerSummaryArchive.setCsaTxnAmount(csaTxnAmount);
        customerSummaryArchive.setCreatedAt(createdAt);
        customerSummaryArchive.setCreatedBy(createdBy);
        customerSummaryArchive.setUpdatedAt(updatedAt);
        customerSummaryArchive.setUpdatedBy(updatedBy);
        return customerSummaryArchive;
    }
}

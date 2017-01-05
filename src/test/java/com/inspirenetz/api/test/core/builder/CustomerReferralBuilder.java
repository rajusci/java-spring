package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.CustomerReferralStatus;
import com.inspirenetz.api.core.domain.CustomerReferral;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ameen on 24/10/15.
 */
public class CustomerReferralBuilder {
    private Long csrId;
    private Long csrMerchantNo = 0L;
    private String csrLoyaltyId ;
    private String csrFName ;
    private String csrRefName ;
    private String csrRefMobile ;
    private String csrRefEmail;
    private Integer csrRefStatus= CustomerReferralStatus.NEW;
    private String csrRefAddress;
    private Timestamp csrRefTimeStamp;
    private Long csrUserNo;
    private String csrProduct;
    private Long csrLocation;
    private String csrRefNo="0";
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private CustomerReferralBuilder() {
    }

    public static CustomerReferralBuilder aCustomerReferral() {
        return new CustomerReferralBuilder();
    }

    public CustomerReferralBuilder withCsrId(Long csrId) {
        this.csrId = csrId;
        return this;
    }

    public CustomerReferralBuilder withCsrMerchantNo(Long csrMerchantNo) {
        this.csrMerchantNo = csrMerchantNo;
        return this;
    }

    public CustomerReferralBuilder withCsrLoyaltyId(String csrLoyaltyId) {
        this.csrLoyaltyId = csrLoyaltyId;
        return this;
    }

    public CustomerReferralBuilder withCsrFName(String csrFName) {
        this.csrFName = csrFName;
        return this;
    }

    public CustomerReferralBuilder withCsrRefName(String csrRefName) {
        this.csrRefName = csrRefName;
        return this;
    }

    public CustomerReferralBuilder withCsrRefMobile(String csrRefMobile) {
        this.csrRefMobile = csrRefMobile;
        return this;
    }

    public CustomerReferralBuilder withCsrRefEmail(String csrRefEmail) {
        this.csrRefEmail = csrRefEmail;
        return this;
    }

    public CustomerReferralBuilder withCsrRefStatus(Integer csrRefStatus) {
        this.csrRefStatus = csrRefStatus;
        return this;
    }

    public CustomerReferralBuilder withCsrRefAddress(String csrRefAddress) {
        this.csrRefAddress = csrRefAddress;
        return this;
    }

    public CustomerReferralBuilder withCsrRefTimeStamp(Timestamp csrRefTimeStamp) {
        this.csrRefTimeStamp = csrRefTimeStamp;
        return this;
    }

    public CustomerReferralBuilder withCsrUserNo(Long csrUserNo) {
        this.csrUserNo = csrUserNo;
        return this;
    }

    public CustomerReferralBuilder withCsrProduct(String csrProduct) {
        this.csrProduct = csrProduct;
        return this;
    }

    public CustomerReferralBuilder withCsrLocation(Long csrLocation) {
        this.csrLocation = csrLocation;
        return this;
    }

    public CustomerReferralBuilder withCsrRefNo(String csrRefNo) {
        this.csrRefNo = csrRefNo;
        return this;
    }

    public CustomerReferralBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CustomerReferralBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CustomerReferralBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CustomerReferralBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CustomerReferralBuilder but() {
        return aCustomerReferral().withCsrId(csrId).withCsrMerchantNo(csrMerchantNo).withCsrLoyaltyId(csrLoyaltyId).withCsrFName(csrFName).withCsrRefName(csrRefName).withCsrRefMobile(csrRefMobile).withCsrRefEmail(csrRefEmail).withCsrRefStatus(csrRefStatus).withCsrRefAddress(csrRefAddress).withCsrRefTimeStamp(csrRefTimeStamp).withCsrUserNo(csrUserNo).withCsrProduct(csrProduct).withCsrLocation(csrLocation).withCsrRefNo(csrRefNo).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public CustomerReferral build() {
        CustomerReferral customerReferral = new CustomerReferral();
        customerReferral.setCsrId(csrId);
        customerReferral.setCsrMerchantNo(csrMerchantNo);
        customerReferral.setCsrLoyaltyId(csrLoyaltyId);
        customerReferral.setCsrFName(csrFName);
        customerReferral.setCsrRefName(csrRefName);
        customerReferral.setCsrRefMobile(csrRefMobile);
        customerReferral.setCsrRefEmail(csrRefEmail);
        customerReferral.setCsrRefStatus(csrRefStatus);
        customerReferral.setCsrRefAddress(csrRefAddress);
        customerReferral.setCsrRefTimeStamp(csrRefTimeStamp);
        customerReferral.setCsrUserNo(csrUserNo);
        customerReferral.setCsrProduct(csrProduct);
        customerReferral.setCsrLocation(csrLocation);
        customerReferral.setCsrRefNo(csrRefNo);
        customerReferral.setCreatedAt(createdAt);
        customerReferral.setCreatedBy(createdBy);
        customerReferral.setUpdatedAt(updatedAt);
        customerReferral.setUpdatedBy(updatedBy);
        return customerReferral;
    }
}

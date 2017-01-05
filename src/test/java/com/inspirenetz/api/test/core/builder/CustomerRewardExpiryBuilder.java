package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.CustomerRewardExpiry;

import java.sql.Date;

/**
 * Created by sandheepgr on 24/8/14.
 */
public class CustomerRewardExpiryBuilder {
    private Long creId;
    private Long creMerchantNo;
    private String creLoyaltyId;
    private Date creExpiryDt;
    private Long creRewardCurrencyId;
    private double creRewardBalance;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private CustomerRewardExpiryBuilder() {
    }

    public static CustomerRewardExpiryBuilder aCustomerRewardExpiry() {
        return new CustomerRewardExpiryBuilder();
    }

    public CustomerRewardExpiryBuilder withCreId(Long creId) {
        this.creId = creId;
        return this;
    }

    public CustomerRewardExpiryBuilder withCreMerchantNo(Long creMerchantNo) {
        this.creMerchantNo = creMerchantNo;
        return this;
    }

    public CustomerRewardExpiryBuilder withCreLoyaltyId(String creLoyaltyId) {
        this.creLoyaltyId = creLoyaltyId;
        return this;
    }

    public CustomerRewardExpiryBuilder withCreExpiryDt(Date creExpiryDt) {
        this.creExpiryDt = creExpiryDt;
        return this;
    }

    public CustomerRewardExpiryBuilder withCreRewardCurrencyId(Long creRewardCurrencyId) {
        this.creRewardCurrencyId = creRewardCurrencyId;
        return this;
    }

    public CustomerRewardExpiryBuilder withCreRewardBalance(double creRewardBalance) {
        this.creRewardBalance = creRewardBalance;
        return this;
    }

    public CustomerRewardExpiryBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CustomerRewardExpiryBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CustomerRewardExpiryBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CustomerRewardExpiryBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CustomerRewardExpiry build() {
        CustomerRewardExpiry customerRewardExpiry = new CustomerRewardExpiry();
        customerRewardExpiry.setCreId(creId);
        customerRewardExpiry.setCreMerchantNo(creMerchantNo);
        customerRewardExpiry.setCreLoyaltyId(creLoyaltyId);
        customerRewardExpiry.setCreExpiryDt(creExpiryDt);
        customerRewardExpiry.setCreRewardCurrencyId(creRewardCurrencyId);
        customerRewardExpiry.setCreRewardBalance(creRewardBalance);
        customerRewardExpiry.setCreatedAt(createdAt);
        customerRewardExpiry.setCreatedBy(createdBy);
        customerRewardExpiry.setUpdatedAt(updatedAt);
        customerRewardExpiry.setUpdatedBy(updatedBy);
        return customerRewardExpiry;
    }
}

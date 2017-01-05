package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.CustomerRewardBalance;

import java.util.Date;

/**
 * Created by sandheepgr on 24/8/14.
 */
public class CustomerRewardBalanceBuilder {
    private Long crbId;
    private Long crbMerchantNo;
    private String crbLoyaltyId;
    private Long crbRewardCurrency;
    private double crbRewardBalance;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private CustomerRewardBalanceBuilder() {
    }

    public static CustomerRewardBalanceBuilder aCustomerRewardBalance() {
        return new CustomerRewardBalanceBuilder();
    }

    public CustomerRewardBalanceBuilder withCrbId(Long crbId) {
        this.crbId = crbId;
        return this;
    }

    public CustomerRewardBalanceBuilder withCrbMerchantNo(Long crbMerchantNo) {
        this.crbMerchantNo = crbMerchantNo;
        return this;
    }

    public CustomerRewardBalanceBuilder withCrbLoyaltyId(String crbLoyaltyId) {
        this.crbLoyaltyId = crbLoyaltyId;
        return this;
    }

    public CustomerRewardBalanceBuilder withCrbRewardCurrency(Long crbRewardCurrency) {
        this.crbRewardCurrency = crbRewardCurrency;
        return this;
    }

    public CustomerRewardBalanceBuilder withCrbRewardBalance(double crbRewardBalance) {
        this.crbRewardBalance = crbRewardBalance;
        return this;
    }

    public CustomerRewardBalanceBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CustomerRewardBalanceBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CustomerRewardBalanceBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CustomerRewardBalanceBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CustomerRewardBalance build() {
        CustomerRewardBalance customerRewardBalance = new CustomerRewardBalance();
        customerRewardBalance.setCrbId(crbId);
        customerRewardBalance.setCrbMerchantNo(crbMerchantNo);
        customerRewardBalance.setCrbLoyaltyId(crbLoyaltyId);
        customerRewardBalance.setCrbRewardCurrency(crbRewardCurrency);
        customerRewardBalance.setCrbRewardBalance(crbRewardBalance);
        customerRewardBalance.setCreatedAt(createdAt);
        customerRewardBalance.setCreatedBy(createdBy);
        customerRewardBalance.setUpdatedAt(updatedAt);
        customerRewardBalance.setUpdatedBy(updatedBy);
        return customerRewardBalance;
    }
}

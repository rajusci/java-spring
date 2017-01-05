package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.LinkedRewardBalance;

/**
 * Created by saneesh-ci on 22/8/14.
 */
public class LinkedRewardBalanceBuilder {
    private Long lrbId;
    private Long lrbMerchantNo;
    private String lrbPrimaryLoyaltyId;
    private Long lrbRewardCurrency;
    private Double lrbRewardBalance;

    private LinkedRewardBalanceBuilder() {
    }

    public static LinkedRewardBalanceBuilder aLinkedRewardBalance() {
        return new LinkedRewardBalanceBuilder();
    }

    public LinkedRewardBalanceBuilder withLrbId(Long lrbId) {
        this.lrbId = lrbId;
        return this;
    }

    public LinkedRewardBalanceBuilder withLrbMerchantNo(Long lrbMerchantNo) {
        this.lrbMerchantNo = lrbMerchantNo;
        return this;
    }

    public LinkedRewardBalanceBuilder withLrbPrimaryLoyaltyId(String lrbPrimaryLoyaltyId) {
        this.lrbPrimaryLoyaltyId = lrbPrimaryLoyaltyId;
        return this;
    }

    public LinkedRewardBalanceBuilder withLrbRewardCurrency(Long lrbRewardCurrency) {
        this.lrbRewardCurrency = lrbRewardCurrency;
        return this;
    }

    public LinkedRewardBalanceBuilder withLrbRewardBalance(Double lrbRewardBalance) {
        this.lrbRewardBalance = lrbRewardBalance;
        return this;
    }

    public LinkedRewardBalance build() {
        LinkedRewardBalance linkedRewardBalance = new LinkedRewardBalance();
        linkedRewardBalance.setLrbId(lrbId);
        linkedRewardBalance.setLrbMerchantNo(lrbMerchantNo);
        linkedRewardBalance.setLrbPrimaryLoyaltyId(lrbPrimaryLoyaltyId);
        linkedRewardBalance.setLrbRewardCurrency(lrbRewardCurrency);
        linkedRewardBalance.setLrbRewardBalance(lrbRewardBalance);
        return linkedRewardBalance;
    }
}

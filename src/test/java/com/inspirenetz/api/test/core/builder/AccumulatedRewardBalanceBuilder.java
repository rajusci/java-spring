package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.AccumulatedRewardBalance;

/**
 * Created by sandheepgr on 23/8/14.
 */
public class AccumulatedRewardBalanceBuilder {
    private Long arbId;
    private Long arbMerchantNo =0l;
    private String arbLoyaltyId = "";
    private Long arbRewardCurrency = 0L;
    private Double arbRewardBalance = 0.0;

    private AccumulatedRewardBalanceBuilder() {
    }

    public static AccumulatedRewardBalanceBuilder anAccumulatedRewardBalance() {
        return new AccumulatedRewardBalanceBuilder();
    }

    public AccumulatedRewardBalanceBuilder withArbId(Long arbId) {
        this.arbId = arbId;
        return this;
    }

    public AccumulatedRewardBalanceBuilder withArbMerchantNo(Long arbMerchantNo) {
        this.arbMerchantNo = arbMerchantNo;
        return this;
    }

    public AccumulatedRewardBalanceBuilder withArbLoyaltyId(String arbLoyaltyId) {
        this.arbLoyaltyId = arbLoyaltyId;
        return this;
    }

    public AccumulatedRewardBalanceBuilder withArbRewardCurrency(Long arbRewardCurrency) {
        this.arbRewardCurrency = arbRewardCurrency;
        return this;
    }

    public AccumulatedRewardBalanceBuilder withArbRewardBalance(Double arbRewardBalance) {
        this.arbRewardBalance = arbRewardBalance;
        return this;
    }

    public AccumulatedRewardBalance build() {
        AccumulatedRewardBalance accumulatedRewardBalance = new AccumulatedRewardBalance();
        accumulatedRewardBalance.setArbId(arbId);
        accumulatedRewardBalance.setArbMerchantNo(arbMerchantNo);
        accumulatedRewardBalance.setArbLoyaltyId(arbLoyaltyId);
        accumulatedRewardBalance.setArbRewardCurrency(arbRewardCurrency);
        accumulatedRewardBalance.setArbRewardBalance(arbRewardBalance);
        return accumulatedRewardBalance;
    }
}

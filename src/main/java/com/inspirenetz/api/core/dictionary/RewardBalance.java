package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 11/8/14.
 */
public class RewardBalance {

    private String loyaltyId = "";

    private Long rwdCurrency = 0L;

    private double rwdBalance = 0;

    private String rwdCurrencyName = "";

    private double rwdCashbackValue = 0;


    public String getLoyaltyId() {
        return loyaltyId;
    }

    public void setLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
    }

    public Long getRwdCurrency() {
        return rwdCurrency;
    }

    public void setRwdCurrency(Long rwdCurrency) {
        this.rwdCurrency = rwdCurrency;
    }

    public double getRwdBalance() {
        return rwdBalance;
    }

    public void setRwdBalance(double rwdBalance) {
        this.rwdBalance = rwdBalance;
    }

    public String getRwdCurrencyName() {
        return rwdCurrencyName;
    }

    public void setRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
    }

    public double getRwdCashbackValue() {
        return rwdCashbackValue;
    }

    public void setRwdCashbackValue(double rwdCashbackValue) {
        this.rwdCashbackValue = rwdCashbackValue;
    }
}


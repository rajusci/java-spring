package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.RewardCurrency;

import java.sql.Date;

/**
 * Created by sandheepgr on 6/5/14.
 */
public class RewardCurrencyBuilder {
    private Long rwdCurrencyId;
    private Long rwdMerchantNo;
    private int rwdRewardUnitType = RewardCurrencyUnitType.POINTS;
    private String rwdCurrencyName = "";
    private String rwdDescription = "";
    private int rwdStatus = RewardCurrencyStatus.ACTIVE;
    private int rwdExpiryOption = RewardCurrencyExpiryOption.NO_EXPIRY;
    private Date rwdExpiryDate;
    private Integer rwdExpiryDays = 0;
    private int rwdCashbackIndicator = IndicatorStatus.NO;
    private Double rwdCashbackRatioDeno = new Double(1);
    private int rwdExclusiveIndicator = IndicatorStatus.NO;
    private int rwdRedemptionMinPoints = 1;
    private Long rwdImage = 1L;
    private Integer rwdAllowDecimal = IndicatorStatus.NO;
    private Integer rwdRoundingMethod= RoundingMethod.ROUND;

    private RewardCurrencyBuilder() {
    }

    public static RewardCurrencyBuilder aRewardCurrency() {
        return new RewardCurrencyBuilder();
    }

    public RewardCurrencyBuilder withRwdCurrencyId(Long rwdCurrencyId) {
        this.rwdCurrencyId = rwdCurrencyId;
        return this;
    }

    public RewardCurrencyBuilder withRwdMerchantNo(Long rwdMerchantNo) {
        this.rwdMerchantNo = rwdMerchantNo;
        return this;
    }

    public RewardCurrencyBuilder withRwdRewardUnitType(int rwdRewardUnitType) {
        this.rwdRewardUnitType = rwdRewardUnitType;
        return this;
    }

    public RewardCurrencyBuilder withRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
        return this;
    }

    public RewardCurrencyBuilder withRwdDescription(String rwdDescription) {
        this.rwdDescription = rwdDescription;
        return this;
    }

    public RewardCurrencyBuilder withRwdStatus(int rwdStatus) {
        this.rwdStatus = rwdStatus;
        return this;
    }

    public RewardCurrencyBuilder withRwdExpiryOption(int rwdExpiryOption) {
        this.rwdExpiryOption = rwdExpiryOption;
        return this;
    }

    public RewardCurrencyBuilder withRwdExpiryDate(Date rwdExpiryDate) {
        this.rwdExpiryDate = rwdExpiryDate;
        return this;
    }

    public RewardCurrencyBuilder withRwdExpiryDays(Integer rwdExpiryDays) {
        this.rwdExpiryDays = rwdExpiryDays;
        return this;
    }

    public RewardCurrencyBuilder withRwdCashbackIndicator(int rwdCashbackIndicator) {
        this.rwdCashbackIndicator = rwdCashbackIndicator;
        return this;
    }

    public RewardCurrencyBuilder withRwdCashbackRatioDeno(Double rwdCashbackRatioDeno) {
        this.rwdCashbackRatioDeno = rwdCashbackRatioDeno;
        return this;
    }

    public RewardCurrencyBuilder withRwdExclusiveIndicator(int rwdExclusiveIndicator) {
        this.rwdExclusiveIndicator = rwdExclusiveIndicator;
        return this;
    }

    public RewardCurrencyBuilder withRwdRedemptionMinPoints(int rwdRedemptionMinPoints) {
        this.rwdRedemptionMinPoints = rwdRedemptionMinPoints;
        return this;
    }

    public RewardCurrencyBuilder withRwdImage(Long rwdImage) {
        this.rwdImage = rwdImage;
        return this;
    }

    public RewardCurrencyBuilder withRwdAllowDecimal(Integer rwdAllowDecimal) {
        this.rwdAllowDecimal = rwdAllowDecimal;
        return this;
    }

    public RewardCurrencyBuilder withRwdRoundingMethod(Integer rwdRoundingMethod) {
        this.rwdRoundingMethod = rwdRoundingMethod;
        return this;
    }


    public RewardCurrency build() {
        RewardCurrency rewardCurrency = new RewardCurrency();
        rewardCurrency.setRwdCurrencyId(rwdCurrencyId);
        rewardCurrency.setRwdMerchantNo(rwdMerchantNo);
        rewardCurrency.setRwdRewardUnitType(rwdRewardUnitType);
        rewardCurrency.setRwdCurrencyName(rwdCurrencyName);
        rewardCurrency.setRwdDescription(rwdDescription);
        rewardCurrency.setRwdStatus(rwdStatus);
        rewardCurrency.setRwdExpiryOption(rwdExpiryOption);
        rewardCurrency.setRwdExpiryDate(rwdExpiryDate);
        rewardCurrency.setRwdExpiryDays(rwdExpiryDays);
        rewardCurrency.setRwdCashbackIndicator(rwdCashbackIndicator);
        rewardCurrency.setRwdCashbackRatioDeno(rwdCashbackRatioDeno);
        rewardCurrency.setRwdExclusiveIndicator(rwdExclusiveIndicator);
        rewardCurrency.setRwdRedemptionMinPoints(rwdRedemptionMinPoints);
        rewardCurrency.setRwdImage(rwdImage);
        rewardCurrency.setRwdAllowDecimal(rwdAllowDecimal);
        rewardCurrency.setRwdRoundingMethod(rwdRoundingMethod);
        return rewardCurrency;
    }
}

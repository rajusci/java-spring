package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.RoundingMethod;

import java.sql.Date;

/**
 * Created by sandheepgr on 6/5/14.
 */
public class RewardCurrencyResource extends BaseResource {

    private Long rwdCurrencyId;

    private Long rwdMerchantNo;

    private String rwdCurrencyName;

    private int rwdRewardUnitType;

    private String rwdDescription;

    private int rwdStatus;

    private int rwdExpiryOption;

    private int rwdExpiryPeriod;

    private Date rwdExpiryDate;

    private Integer rwdExpiryDays ;

    private int rwdCashbackIndicator;

    private Double rwdCashbackRatioDeno;

    private int rwdExclusiveIndicator;

    private int rwdRedemptionMinPoints;

    private Long rwdImage ;

    private Integer rwdAllowDecimal;

    private double rwdCashbackValue = 0;

    private Integer rwdRoundingMethod= RoundingMethod.ROUND;


    public double getRwdCashbackValue() {
        return rwdCashbackValue;
    }

    public void setRwdCashbackValue(double rwdCashbackValue) {
        this.rwdCashbackValue = rwdCashbackValue;
    }

    public Long getRwdCurrencyId() {
        return rwdCurrencyId;
    }

    public void setRwdCurrencyId(Long rwdCurrencyId) {
        this.rwdCurrencyId = rwdCurrencyId;
    }

    public Long getRwdMerchantNo() {
        return rwdMerchantNo;
    }

    public void setRwdMerchantNo(Long rwdMerchantNo) {
        this.rwdMerchantNo = rwdMerchantNo;
    }

    public String getRwdCurrencyName() {
        return rwdCurrencyName;
    }

    public void setRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
    }

    public int getRwdRewardUnitType() {
        return rwdRewardUnitType;
    }

    public void setRwdRewardUnitType(int rwdRewardUnitType) {
        this.rwdRewardUnitType = rwdRewardUnitType;
    }

    public String getRwdDescription() {
        return rwdDescription;
    }

    public void setRwdDescription(String rwdDescription) {
        this.rwdDescription = rwdDescription;
    }

    public int getRwdStatus() {
        return rwdStatus;
    }

    public void setRwdStatus(int rwdStatus) {
        this.rwdStatus = rwdStatus;
    }

    public int getRwdExpiryOption() {
        return rwdExpiryOption;
    }

    public void setRwdExpiryOption(int rwdExpiryOption) {
        this.rwdExpiryOption = rwdExpiryOption;
    }

    public Date getRwdExpiryDate() {
        return rwdExpiryDate;
    }

    public void setRwdExpiryDate(Date rwdExpiryDate) {
        this.rwdExpiryDate = rwdExpiryDate;
    }

    public Integer getRwdExpiryDays() {
        return rwdExpiryDays;
    }

    public void setRwdExpiryDays(Integer rwdExpiryDays) {
        this.rwdExpiryDays = rwdExpiryDays;
    }

    public int getRwdCashbackIndicator() {
        return rwdCashbackIndicator;
    }

    public void setRwdCashbackIndicator(int rwdCashbackIndicator) {
        this.rwdCashbackIndicator = rwdCashbackIndicator;
    }

    public Double getRwdCashbackRatioDeno() {
        return rwdCashbackRatioDeno;
    }

    public void setRwdCashbackRatioDeno(Double rwdCashbackRatioDeno) {
        this.rwdCashbackRatioDeno = rwdCashbackRatioDeno;
    }

    public int getRwdExclusiveIndicator() {
        return rwdExclusiveIndicator;
    }

    public void setRwdExclusiveIndicator(int rwdExclusiveIndicator) {
        this.rwdExclusiveIndicator = rwdExclusiveIndicator;
    }

    public int getRwdRedemptionMinPoints() {
        return rwdRedemptionMinPoints;
    }

    public void setRwdRedemptionMinPoints(int rwdRedemptionMinPoints) {
        this.rwdRedemptionMinPoints = rwdRedemptionMinPoints;
    }

    public Long getRwdImage() {
        return rwdImage;
    }

    public void setRwdImage(Long rwdImage) {
        this.rwdImage = rwdImage;
    }

    public Integer getRwdAllowDecimal() {
        return rwdAllowDecimal;
    }

    public void setRwdAllowDecimal(Integer rwdAllowDecimal) {
        this.rwdAllowDecimal = rwdAllowDecimal;
    }

    public int getRwdExpiryPeriod() {
        return rwdExpiryPeriod;
    }

    public void setRwdExpiryPeriod(int rwdExpiryPeriod) {
        this.rwdExpiryPeriod = rwdExpiryPeriod;
    }

    public Integer getRwdRoundingMethod() {
        return rwdRoundingMethod;
    }

    public void setRwdRoundingMethod(Integer rwdRoundingMethod) {
        this.rwdRoundingMethod = rwdRoundingMethod;
    }

    @Override
    public String toString() {
        return "RewardCurrencyResource{" +
                "rwdCurrencyId=" + rwdCurrencyId +
                ", rwdMerchantNo=" + rwdMerchantNo +
                ", rwdCurrencyName='" + rwdCurrencyName + '\'' +
                ", rwdRewardUnitType=" + rwdRewardUnitType +
                ", rwdDescription='" + rwdDescription + '\'' +
                ", rwdStatus=" + rwdStatus +
                ", rwdExpiryOption=" + rwdExpiryOption +
                ", rwdExpiryPeriod=" + rwdExpiryPeriod +
                ", rwdExpiryDate=" + rwdExpiryDate +
                ", rwdExpiryDays=" + rwdExpiryDays +
                ", rwdCashbackIndicator=" + rwdCashbackIndicator +
                ", rwdCashbackRatioDeno=" + rwdCashbackRatioDeno +
                ", rwdExclusiveIndicator=" + rwdExclusiveIndicator +
                ", rwdRedemptionMinPoints=" + rwdRedemptionMinPoints +
                ", rwdImage=" + rwdImage +
                ", rwdAllowDecimal=" + rwdAllowDecimal +
                ", rwdCashbackValue=" + rwdCashbackValue +
                ", rwdRoundingMethod=" + rwdRoundingMethod +
                '}';
    }
}

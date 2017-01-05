package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.CardType;

import java.sql.Date;

/**
 * Created by ameen on 3/6/16.
 */
public final class CardTypeBuilder {
    private Long crtId;
    private Long crtMerchantNo;
    private String crtName = "";
    private int crtType = CardTypeType.FIXED_VALUE;
    private int crtCurrencyCode = 356;
    private double crtDiscount = 0.0;
    private double crtFixedValue = 0.0;
    private double crtMinTopupValue = 0.0;
    private double crtMaxValue = 0.0;
    private Integer crtAllowPinIndicator = IndicatorStatus.NO;
    private String crtCardNoRangeFrom = "";
    private String crtCardNoRangeTo = "";
    private Integer crtExpiryOption = CardTypeExpiryOption.EXPIRY_DATE;
    private Date crtExpiryDate;
    private Integer crtExpiryDays = 0;
    private Integer crtMaxNumTxns= 0;
    private Integer crtBalanceExpiryOption = CardTypeExpiryOption.EXPIRY_DATE;
    private Integer crtBalExpMaxTxn= 0;
    private Integer crtBalExpiryDays = 0;
    private Integer crtBalTopExpiryDays = 0;
    private String crtBalTopExpiryTime;
    private Date crtBalExpiryDate;
    private Integer crtCardNumberAssignment = CardNumberAssignmentMode.EXPLICIT;
    private Integer crtActivateOption = CardTypeActivateOption.CRT_ACTIVITY_FIXED_DATE;
    private Date crtActivateDate;
    private Integer crtActivateDays = 0;
    private Integer crtPromoIncentive = 0;
    private Integer crtPromoIncentiveType;
    private Double crtIncentiveAmount = 0.0;
    private Double crtIncentiveDiscount = 0.0;
    private Integer crtTier1Upto = 0;
    private Double crtTier1LimitTo = 0.0;
    private Double crtTier1Num = 0.0;
    private Double crtTier1Deno = 0.0;
    private Integer crtTier2Upto = 0;
    private Double crtTier2LimitTo = 0.0;
    private Double crtTier2Num = 0.0;
    private Double crtTier2Deno = 0.0;
    private Integer crtTier3Upto = 0;
    private Double crtTier3LimitTo = 0.0;
    private Double crtTier3Num = 0.0;
    private Double crtTier3Deno = 0.0;
    private Integer crtTier4Upto = 0;
    private Double crtTier4LimitTo = 0.0;
    private Double crtTier4Num = 0.0;
    private Double crtTier4Deno = 0.0;
    private Integer crtTier5Upto = 0;
    private Double crtTier5Num = 0.0;
    private Double crtTier5Deno = 0.0;
    private Boolean isCrtDeductIncentive = false;
    private Integer crtDeductIncentiveType;
    private Double crtDeductIncentiveAmount = 0.0;
    private Double crtDeductIncentivePercentage = 0.0;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private CardTypeBuilder() {
    }

    public static CardTypeBuilder aCardType() {
        return new CardTypeBuilder();
    }

    public CardTypeBuilder withCrtId(Long crtId) {
        this.crtId = crtId;
        return this;
    }

    public CardTypeBuilder withCrtMerchantNo(Long crtMerchantNo) {
        this.crtMerchantNo = crtMerchantNo;
        return this;
    }

    public CardTypeBuilder withCrtName(String crtName) {
        this.crtName = crtName;
        return this;
    }

    public CardTypeBuilder withCrtType(int crtType) {
        this.crtType = crtType;
        return this;
    }

    public CardTypeBuilder withCrtCurrencyCode(int crtCurrencyCode) {
        this.crtCurrencyCode = crtCurrencyCode;
        return this;
    }

    public CardTypeBuilder withCrtDiscount(double crtDiscount) {
        this.crtDiscount = crtDiscount;
        return this;
    }

    public CardTypeBuilder withCrtFixedValue(double crtFixedValue) {
        this.crtFixedValue = crtFixedValue;
        return this;
    }

    public CardTypeBuilder withCrtMinTopupValue(double crtMinTopupValue) {
        this.crtMinTopupValue = crtMinTopupValue;
        return this;
    }

    public CardTypeBuilder withCrtMaxValue(double crtMaxValue) {
        this.crtMaxValue = crtMaxValue;
        return this;
    }

    public CardTypeBuilder withCrtAllowPinIndicator(Integer crtAllowPinIndicator) {
        this.crtAllowPinIndicator = crtAllowPinIndicator;
        return this;
    }

    public CardTypeBuilder withCrtCardNoRangeFrom(String crtCardNoRangeFrom) {
        this.crtCardNoRangeFrom = crtCardNoRangeFrom;
        return this;
    }

    public CardTypeBuilder withCrtCardNoRangeTo(String crtCardNoRangeTo) {
        this.crtCardNoRangeTo = crtCardNoRangeTo;
        return this;
    }

    public CardTypeBuilder withCrtExpiryOption(Integer crtExpiryOption) {
        this.crtExpiryOption = crtExpiryOption;
        return this;
    }

    public CardTypeBuilder withCrtExpiryDate(Date crtExpiryDate) {
        this.crtExpiryDate = crtExpiryDate;
        return this;
    }

    public CardTypeBuilder withCrtExpiryDays(Integer crtExpiryDays) {
        this.crtExpiryDays = crtExpiryDays;
        return this;
    }

    public CardTypeBuilder withCrtMaxNumTxns(Integer crtMaxNumTxns) {
        this.crtMaxNumTxns = crtMaxNumTxns;
        return this;
    }

    public CardTypeBuilder withCrtBalanceExpiryOption(Integer crtBalanceExpiryOption) {
        this.crtBalanceExpiryOption = crtBalanceExpiryOption;
        return this;
    }

    public CardTypeBuilder withCrtBalExpMaxTxn(Integer crtBalExpMaxTxn) {
        this.crtBalExpMaxTxn = crtBalExpMaxTxn;
        return this;
    }

    public CardTypeBuilder withCrtBalExpiryDays(Integer crtBalExpiryDays) {
        this.crtBalExpiryDays = crtBalExpiryDays;
        return this;
    }

    public CardTypeBuilder withCrtBalTopExpiryDays(Integer crtBalTopExpiryDays) {
        this.crtBalTopExpiryDays = crtBalTopExpiryDays;
        return this;
    }

    public CardTypeBuilder withCrtBalTopExpiryTime(String crtBalTopExpiryTime) {
        this.crtBalTopExpiryTime = crtBalTopExpiryTime;
        return this;
    }

    public CardTypeBuilder withCrtBalExpiryDate(Date crtBalExpiryDate) {
        this.crtBalExpiryDate = crtBalExpiryDate;
        return this;
    }

    public CardTypeBuilder withCrtCardNumberAssignment(Integer crtCardNumberAssignment) {
        this.crtCardNumberAssignment = crtCardNumberAssignment;
        return this;
    }

    public CardTypeBuilder withCrtActivateOption(Integer crtActivateOption) {
        this.crtActivateOption = crtActivateOption;
        return this;
    }

    public CardTypeBuilder withCrtActivateDate(Date crtActivateDate) {
        this.crtActivateDate = crtActivateDate;
        return this;
    }

    public CardTypeBuilder withCrtActivateDays(Integer crtActivateDays) {
        this.crtActivateDays = crtActivateDays;
        return this;
    }

    public CardTypeBuilder withCrtPromoIncentive(Integer crtPromoIncentive) {
        this.crtPromoIncentive = crtPromoIncentive;
        return this;
    }

    public CardTypeBuilder withCrtPromoIncentiveType(Integer crtPromoIncentiveType) {
        this.crtPromoIncentiveType = crtPromoIncentiveType;
        return this;
    }

    public CardTypeBuilder withCrtIncentiveAmount(Double crtIncentiveAmount) {
        this.crtIncentiveAmount = crtIncentiveAmount;
        return this;
    }

    public CardTypeBuilder withCrtIncentiveDiscount(Double crtIncentiveDiscount) {
        this.crtIncentiveDiscount = crtIncentiveDiscount;
        return this;
    }

    public CardTypeBuilder withCrtTier1Upto(Integer crtTier1Upto) {
        this.crtTier1Upto = crtTier1Upto;
        return this;
    }

    public CardTypeBuilder withCrtTier1LimitTo(Double crtTier1LimitTo) {
        this.crtTier1LimitTo = crtTier1LimitTo;
        return this;
    }

    public CardTypeBuilder withCrtTier1Num(Double crtTier1Num) {
        this.crtTier1Num = crtTier1Num;
        return this;
    }

    public CardTypeBuilder withCrtTier1Deno(Double crtTier1Deno) {
        this.crtTier1Deno = crtTier1Deno;
        return this;
    }

    public CardTypeBuilder withCrtTier2Upto(Integer crtTier2Upto) {
        this.crtTier2Upto = crtTier2Upto;
        return this;
    }

    public CardTypeBuilder withCrtTier2LimitTo(Double crtTier2LimitTo) {
        this.crtTier2LimitTo = crtTier2LimitTo;
        return this;
    }

    public CardTypeBuilder withCrtTier2Num(Double crtTier2Num) {
        this.crtTier2Num = crtTier2Num;
        return this;
    }

    public CardTypeBuilder withCrtTier2Deno(Double crtTier2Deno) {
        this.crtTier2Deno = crtTier2Deno;
        return this;
    }

    public CardTypeBuilder withCrtTier3Upto(Integer crtTier3Upto) {
        this.crtTier3Upto = crtTier3Upto;
        return this;
    }

    public CardTypeBuilder withCrtTier3LimitTo(Double crtTier3LimitTo) {
        this.crtTier3LimitTo = crtTier3LimitTo;
        return this;
    }

    public CardTypeBuilder withCrtTier3Num(Double crtTier3Num) {
        this.crtTier3Num = crtTier3Num;
        return this;
    }

    public CardTypeBuilder withCrtTier3Deno(Double crtTier3Deno) {
        this.crtTier3Deno = crtTier3Deno;
        return this;
    }

    public CardTypeBuilder withCrtTier4Upto(Integer crtTier4Upto) {
        this.crtTier4Upto = crtTier4Upto;
        return this;
    }

    public CardTypeBuilder withCrtTier4LimitTo(Double crtTier4LimitTo) {
        this.crtTier4LimitTo = crtTier4LimitTo;
        return this;
    }

    public CardTypeBuilder withCrtTier4Num(Double crtTier4Num) {
        this.crtTier4Num = crtTier4Num;
        return this;
    }

    public CardTypeBuilder withCrtTier4Deno(Double crtTier4Deno) {
        this.crtTier4Deno = crtTier4Deno;
        return this;
    }

    public CardTypeBuilder withCrtTier5Upto(Integer crtTier5Upto) {
        this.crtTier5Upto = crtTier5Upto;
        return this;
    }

    public CardTypeBuilder withCrtTier5Num(Double crtTier5Num) {
        this.crtTier5Num = crtTier5Num;
        return this;
    }

    public CardTypeBuilder withCrtTier5Deno(Double crtTier5Deno) {
        this.crtTier5Deno = crtTier5Deno;
        return this;
    }

    public CardTypeBuilder withIsCrtDeductIncentive(Boolean isCrtDeductIncentive) {
        this.isCrtDeductIncentive = isCrtDeductIncentive;
        return this;
    }

    public CardTypeBuilder withCrtDeductIncentiveType(Integer crtDeductIncentiveType) {
        this.crtDeductIncentiveType = crtDeductIncentiveType;
        return this;
    }

    public CardTypeBuilder withCrtDeductIncentiveAmount(Double crtDeductIncentiveAmount) {
        this.crtDeductIncentiveAmount = crtDeductIncentiveAmount;
        return this;
    }

    public CardTypeBuilder withCrtDeductIncentivePercentage(Double crtDeductIncentivePercentage) {
        this.crtDeductIncentivePercentage = crtDeductIncentivePercentage;
        return this;
    }

    public CardTypeBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CardTypeBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CardTypeBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CardTypeBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CardType build() {
        CardType cardType = new CardType();
        cardType.setCrtId(crtId);
        cardType.setCrtMerchantNo(crtMerchantNo);
        cardType.setCrtName(crtName);
        cardType.setCrtType(crtType);
        cardType.setCrtCurrencyCode(crtCurrencyCode);
        cardType.setCrtDiscount(crtDiscount);
        cardType.setCrtFixedValue(crtFixedValue);
        cardType.setCrtMinTopupValue(crtMinTopupValue);
        cardType.setCrtMaxValue(crtMaxValue);
        cardType.setCrtAllowPinIndicator(crtAllowPinIndicator);
        cardType.setCrtCardNoRangeFrom(crtCardNoRangeFrom);
        cardType.setCrtCardNoRangeTo(crtCardNoRangeTo);
        cardType.setCrtExpiryOption(crtExpiryOption);
        cardType.setCrtExpiryDate(crtExpiryDate);
        cardType.setCrtExpiryDays(crtExpiryDays);
        cardType.setCrtMaxNumTxns(crtMaxNumTxns);
        cardType.setCrtBalanceExpiryOption(crtBalanceExpiryOption);
        cardType.setCrtBalExpMaxTxn(crtBalExpMaxTxn);
        cardType.setCrtBalExpiryDays(crtBalExpiryDays);
        cardType.setCrtBalTopExpiryDays(crtBalTopExpiryDays);
        cardType.setCrtBalTopExpiryTime(crtBalTopExpiryTime);
        cardType.setCrtBalExpiryDate(crtBalExpiryDate);
        cardType.setCrtCardNumberAssignment(crtCardNumberAssignment);
        cardType.setCrtActivateOption(crtActivateOption);
        cardType.setCrtActivateDate(crtActivateDate);
        cardType.setCrtActivateDays(crtActivateDays);
        cardType.setCrtPromoIncentive(crtPromoIncentive);
        cardType.setCrtPromoIncentiveType(crtPromoIncentiveType);
        cardType.setCrtIncentiveAmount(crtIncentiveAmount);
        cardType.setCrtIncentiveDiscount(crtIncentiveDiscount);
        cardType.setCrtTier1Upto(crtTier1Upto);
        cardType.setCrtTier1LimitTo(crtTier1LimitTo);
        cardType.setCrtTier1Num(crtTier1Num);
        cardType.setCrtTier1Deno(crtTier1Deno);
        cardType.setCrtTier2Upto(crtTier2Upto);
        cardType.setCrtTier2LimitTo(crtTier2LimitTo);
        cardType.setCrtTier2Num(crtTier2Num);
        cardType.setCrtTier2Deno(crtTier2Deno);
        cardType.setCrtTier3Upto(crtTier3Upto);
        cardType.setCrtTier3LimitTo(crtTier3LimitTo);
        cardType.setCrtTier3Num(crtTier3Num);
        cardType.setCrtTier3Deno(crtTier3Deno);
        cardType.setCrtTier4Upto(crtTier4Upto);
        cardType.setCrtTier4LimitTo(crtTier4LimitTo);
        cardType.setCrtTier4Num(crtTier4Num);
        cardType.setCrtTier4Deno(crtTier4Deno);
        cardType.setCrtTier5Upto(crtTier5Upto);
        cardType.setCrtTier5Num(crtTier5Num);
        cardType.setCrtTier5Deno(crtTier5Deno);
        cardType.setCreatedAt(createdAt);
        cardType.setCreatedBy(createdBy);
        cardType.setUpdatedAt(updatedAt);
        cardType.setUpdatedBy(updatedBy);
        return cardType;
    }
}

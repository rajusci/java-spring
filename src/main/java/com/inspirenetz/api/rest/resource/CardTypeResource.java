package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.*;

import java.sql.Date;

/**
 * Created by sandheepgr on 22/7/14.
 */
public class CardTypeResource extends  BaseResource {

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

    private Integer crtPromoIncentiveType ;

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

    private Integer crtDebitIncentiveType;

    private Integer crtRefundIncentiveType;

    private Double crtRefundIncentivePercentage;

    public Integer getCrtTier1Upto() {
        return crtTier1Upto;
    }

    public void setCrtTier1Upto(Integer crtTier1Upto) {
        this.crtTier1Upto = crtTier1Upto;
    }

    public Double getCrtTier1LimitTo() {
        return crtTier1LimitTo;
    }

    public void setCrtTier1LimitTo(Double crtTier1LimitTo) {
        this.crtTier1LimitTo = crtTier1LimitTo;
    }

    public Double getCrtTier1Num() {
        return crtTier1Num;
    }

    public void setCrtTier1Num(Double crtTier1Num) {
        this.crtTier1Num = crtTier1Num;
    }

    public Double getCrtTier1Deno() {
        return crtTier1Deno;
    }

    public void setCrtTier1Deno(Double crtTier1Deno) {
        this.crtTier1Deno = crtTier1Deno;
    }

    public Integer getCrtTier2Upto() {
        return crtTier2Upto;
    }

    public void setCrtTier2Upto(Integer crtTier2Upto) {
        this.crtTier2Upto = crtTier2Upto;
    }

    public Double getCrtTier2LimitTo() {
        return crtTier2LimitTo;
    }

    public void setCrtTier2LimitTo(Double crtTier2LimitTo) {
        this.crtTier2LimitTo = crtTier2LimitTo;
    }

    public Double getCrtTier2Num() {
        return crtTier2Num;
    }

    public void setCrtTier2Num(Double crtTier2Num) {
        this.crtTier2Num = crtTier2Num;
    }

    public Double getCrtTier2Deno() {
        return crtTier2Deno;
    }

    public void setCrtTier2Deno(Double crtTier2Deno) {
        this.crtTier2Deno = crtTier2Deno;
    }

    public Integer getCrtTier3Upto() {
        return crtTier3Upto;
    }

    public void setCrtTier3Upto(Integer crtTier3Upto) {
        this.crtTier3Upto = crtTier3Upto;
    }

    public Double getCrtTier3LimitTo() {
        return crtTier3LimitTo;
    }

    public void setCrtTier3LimitTo(Double crtTier3LimitTo) {
        this.crtTier3LimitTo = crtTier3LimitTo;
    }

    public Double getCrtTier3Num() {
        return crtTier3Num;
    }

    public void setCrtTier3Num(Double crtTier3Num) {
        this.crtTier3Num = crtTier3Num;
    }

    public Double getCrtTier3Deno() {
        return crtTier3Deno;
    }

    public void setCrtTier3Deno(Double crtTier3Deno) {
        this.crtTier3Deno = crtTier3Deno;
    }

    public Integer getCrtTier4Upto() {
        return crtTier4Upto;
    }

    public void setCrtTier4Upto(Integer crtTier4Upto) {
        this.crtTier4Upto = crtTier4Upto;
    }

    public Double getCrtTier4LimitTo() {
        return crtTier4LimitTo;
    }

    public void setCrtTier4LimitTo(Double crtTier4LimitTo) {
        this.crtTier4LimitTo = crtTier4LimitTo;
    }

    public Double getCrtTier4Num() {
        return crtTier4Num;
    }

    public void setCrtTier4Num(Double crtTier4Num) {
        this.crtTier4Num = crtTier4Num;
    }

    public Double getCrtTier4Deno() {
        return crtTier4Deno;
    }

    public void setCrtTier4Deno(Double crtTier4Deno) {
        this.crtTier4Deno = crtTier4Deno;
    }

    public Double getCrtTier5Num() {
        return crtTier5Num;
    }

    public void setCrtTier5Num(Double crtTier5Num) {
        this.crtTier5Num = crtTier5Num;
    }

    public Double getCrtTier5Deno() {
        return crtTier5Deno;
    }

    public void setCrtTier5Deno(Double crtTier5Deno) {
        this.crtTier5Deno = crtTier5Deno;
    }

    public Long getCrtId() {
        return crtId;
    }

    public void setCrtId(Long crtId) {
        this.crtId = crtId;
    }

    public Long getCrtMerchantNo() {
        return crtMerchantNo;
    }

    public void setCrtMerchantNo(Long crtMerchantNo) {
        this.crtMerchantNo = crtMerchantNo;
    }

    public String getCrtName() {
        return crtName;
    }

    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    public int getCrtType() {
        return crtType;
    }

    public void setCrtType(int crtType) {
        this.crtType = crtType;
    }

    public int getCrtCurrencyCode() {
        return crtCurrencyCode;
    }

    public void setCrtCurrencyCode(int crtCurrencyCode) {
        this.crtCurrencyCode = crtCurrencyCode;
    }

    public double getCrtDiscount() {
        return crtDiscount;
    }

    public void setCrtDiscount(double crtDiscount) {
        this.crtDiscount = crtDiscount;
    }

    public double getCrtFixedValue() {
        return crtFixedValue;
    }

    public void setCrtFixedValue(double crtFixedValue) {
        this.crtFixedValue = crtFixedValue;
    }

    public double getCrtMinTopupValue() {
        return crtMinTopupValue;
    }

    public void setCrtMinTopupValue(double crtMinTopupValue) {
        this.crtMinTopupValue = crtMinTopupValue;
    }

    public double getCrtMaxValue() {
        return crtMaxValue;
    }

    public void setCrtMaxValue(double crtMaxValue) {
        this.crtMaxValue = crtMaxValue;
    }

    public Integer getCrtAllowPinIndicator() {
        return crtAllowPinIndicator;
    }

    public void setCrtAllowPinIndicator(Integer crtAllowPinIndicator) {
        this.crtAllowPinIndicator = crtAllowPinIndicator;
    }

    public String getCrtCardNoRangeFrom() {
        return crtCardNoRangeFrom;
    }

    public void setCrtCardNoRangeFrom(String crtCardNoRangeFrom) {
        this.crtCardNoRangeFrom = crtCardNoRangeFrom;
    }

    public String getCrtCardNoRangeTo() {
        return crtCardNoRangeTo;
    }

    public void setCrtCardNoRangeTo(String crtCardNoRangeTo) {
        this.crtCardNoRangeTo = crtCardNoRangeTo;
    }

    public Integer getCrtExpiryOption() {
        return crtExpiryOption;
    }

    public void setCrtExpiryOption(Integer crtExpiryOption) {
        this.crtExpiryOption = crtExpiryOption;
    }

    public Date getCrtExpiryDate() {
        return crtExpiryDate;
    }

    public void setCrtExpiryDate(Date crtExpiryDate) {
        this.crtExpiryDate = crtExpiryDate;
    }

    public Integer getCrtExpiryDays() {
        return crtExpiryDays;
    }

    public void setCrtExpiryDays(Integer crtExpiryDays) {
        this.crtExpiryDays = crtExpiryDays;
    }

    public Integer getCrtMaxNumTxns() {
        return crtMaxNumTxns;
    }

    public void setCrtMaxNumTxns(Integer crtMaxNumTxns) {
        this.crtMaxNumTxns = crtMaxNumTxns;
    }

    public Integer getCrtBalanceExpiryOption() {
        return crtBalanceExpiryOption;
    }

    public void setCrtBalanceExpiryOption(Integer crtBalanceExpiryOption) {
        this.crtBalanceExpiryOption = crtBalanceExpiryOption;
    }

    public Integer getCrtBalExpMaxTxn() {
        return crtBalExpMaxTxn;
    }

    public void setCrtBalExpMaxTxn(Integer crtBalExpMaxTxn) {
        this.crtBalExpMaxTxn = crtBalExpMaxTxn;
    }

    public Integer getCrtBalExpiryDays() {
        return crtBalExpiryDays;
    }

    public void setCrtBalExpiryDays(Integer crtBalExpiryDays) {
        this.crtBalExpiryDays = crtBalExpiryDays;
    }

    public Integer getCrtBalTopExpiryDays() {
        return crtBalTopExpiryDays;
    }

    public void setCrtBalTopExpiryDays(Integer crtBalTopExpiryDays) {
        this.crtBalTopExpiryDays = crtBalTopExpiryDays;
    }

    public String getCrtBalTopExpiryTime() {
        return crtBalTopExpiryTime;
    }

    public void setCrtBalTopExpiryTime(String crtBalTopExpiryTime) {
        this.crtBalTopExpiryTime = crtBalTopExpiryTime;
    }

    public Date getCrtBalExpiryDate() {
        return crtBalExpiryDate;
    }

    public void setCrtBalExpiryDate(Date crtBalExpiryDate) {
        this.crtBalExpiryDate = crtBalExpiryDate;
    }

    public Integer getCrtCardNumberAssignment() {
        return crtCardNumberAssignment;
    }

    public void setCrtCardNumberAssignment(Integer crtCardNumberAssignment) {
        this.crtCardNumberAssignment = crtCardNumberAssignment;
    }

    public Integer getCrtActivateOption() {
        return crtActivateOption;
    }

    public void setCrtActivateOption(Integer crtActivateOption) {
        this.crtActivateOption = crtActivateOption;
    }

    public Date getCrtActivateDate() {
        return crtActivateDate;
    }

    public void setCrtActivateDate(Date crtActivateDate) {
        this.crtActivateDate = crtActivateDate;
    }

    public Integer getCrtActivateDays() {
        return crtActivateDays;
    }

    public void setCrtActivateDays(Integer crtActivateDays) {
        this.crtActivateDays = crtActivateDays;
    }

    public Integer getCrtPromoIncentive() {
        return crtPromoIncentive;
    }

    public void setCrtPromoIncentive(Integer crtPromoIncentive) {
        this.crtPromoIncentive = crtPromoIncentive;
    }

    public Double getCrtIncentiveAmount() {
        return crtIncentiveAmount;
    }

    public void setCrtIncentiveAmount(Double crtIncentiveAmount) {
        this.crtIncentiveAmount = crtIncentiveAmount;
    }

    public Double getCrtIncentiveDiscount() {
        return crtIncentiveDiscount;
    }

    public void setCrtIncentiveDiscount(Double crtIncentiveDiscount) {
        this.crtIncentiveDiscount = crtIncentiveDiscount;
    }

    public Integer getCrtPromoIncentiveType() {
        return crtPromoIncentiveType;
    }

    public void setCrtPromoIncentiveType(Integer crtPromoIncentiveType) {
        this.crtPromoIncentiveType = crtPromoIncentiveType;
    }

    public Integer getCrtTier5Upto() {
        return crtTier5Upto;
    }

    public void setCrtTier5Upto(Integer crtTier5Upto) {
        this.crtTier5Upto = crtTier5Upto;
    }

    public Integer getCrtDebitIncentiveType() {
        return crtDebitIncentiveType;
    }

    public void setCrtDebitIncentiveType(Integer crtDebitIncentiveType) {
        this.crtDebitIncentiveType = crtDebitIncentiveType;
    }

    public Integer getCrtRefundIncentiveType() {
        return crtRefundIncentiveType;
    }

    public void setCrtRefundIncentiveType(Integer crtRefundIncentiveType) {
        this.crtRefundIncentiveType = crtRefundIncentiveType;
    }

    public Double getCrtRefundIncentivePercentage() {
        return crtRefundIncentivePercentage;
    }

    public void setCrtRefundIncentivePercentage(Double crtRefundIncentivePercentage) {
        this.crtRefundIncentivePercentage = crtRefundIncentivePercentage;
    }
}

package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.LoyaltyComputationSource;
import com.inspirenetz.api.core.dictionary.LoyaltyProgramStatus;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by sandheepgr on 22/5/14.
 */
public class LoyaltyProgramResource extends BaseResource {


    private Long prgProgramNo;

    private Long prgMerchantNo;

    private String prgProgramName = "";

    private String prgProgramDesc = "";

    private Long prgImage = ImagePrimaryId.PRIMARY_LOYALTY_PROGRAM_IMAGE;

    private String prgCustomerDesc = "";

    private int prgTxnCurrency = 356;

    private Long prgCurrencyId = 0L ;

    private Integer prgComputationSource = LoyaltyComputationSource.PROGRAM_CONFIGURATION;

    private Long prgLoyaltyExtension = 0L;

    private Long prgEligibleCusTier = 0L;

    private int prgProgramDriver = 1;

    private int prgRuleType = 1;

    private Double prgFixedValue = 0.0;

    private Double prgRatioDeno = 1.0;

    private Double prgRatioNum = 0.0;

    private Double prgTier1LimitFrom = 0.0;

    private Double prgTier1LimitTo = 0.0;

    private Double prgTier1Deno = 1.0;

    private Double prgTier1Num = 0.0;

    private Double prgTier2LimitFrom = 0.0;

    private Double prgTier2LimitTo = 0.0;

    private Double prgTier2Deno = 1.0;

    private Double prgTier2Num = 0.0;

    private Double prgTier3LimitFrom = 0.0;

    private Double prgTier3LimitTo = 0.0;

    private Double prgTier3Deno = 1.0;

    private Double prgTier3Num = 0.0;

    private Double prgTier4LimitFrom = 0.0;

    private Double prgTier4LimitTo = 0.0;

    private Double prgTier4Deno = 1.0;

    private Double prgTier4Num = 0.0;

    private Double prgTier5LimitFrom = 0.0;

    private Double prgTier5LimitTo = 0.0;

    private Double prgTier5Deno = 1.0;

    private Double prgTier5Num = 0.0;

    private int prgStatus = LoyaltyProgramStatus.NEW;

    private Date prgStartDate;

    private Date prgEndDate;

    private Integer prgMinTxnAmount = 0;

    private Integer prgPaymentModeInd = IndicatorStatus.NO;

    private String prgPaymentModes ="0";

    private Integer prgLocationInd = IndicatorStatus.NO;

    private String prgLocations = "0";

    private Integer prgTxnChannelInd = IndicatorStatus.NO;

    private String prgTxnChannels = "0";

    private Integer prgDaysInd = IndicatorStatus.NO;

    private String prgDays = "0";

    private String prgSpecOcc = "0";

    private Time prgHrsActiveFrom;

    private Time prgHrsActiveTo;

    private Integer prgAwardRestrictInd = IndicatorStatus.NO;

    private Integer prgAwardCustCount = 0;

    private Integer prgEligibleCustType = 1 ;

    private Integer prgEligibleCustSegmentId = 0;

    private Integer prgAwardingTime = 1;

    private Integer prgAwardFreq = 1 ;

    private String rwdCurrencyName = "";

    private Integer prgRole =0;



    public Long getPrgProgramNo() {
        return prgProgramNo;
    }

    public void setPrgProgramNo(Long prgProgramNo) {
        this.prgProgramNo = prgProgramNo;
    }

    public Long getPrgMerchantNo() {
        return prgMerchantNo;
    }

    public void setPrgMerchantNo(Long prgMerchantNo) {
        this.prgMerchantNo = prgMerchantNo;
    }

    public String getPrgProgramName() {
        return prgProgramName;
    }

    public void setPrgProgramName(String prgProgramName) {
        this.prgProgramName = prgProgramName;
    }

    public String getPrgProgramDesc() {
        return prgProgramDesc;
    }

    public void setPrgProgramDesc(String prgProgramDesc) {
        this.prgProgramDesc = prgProgramDesc;
    }

    public Long getPrgImage() {
        return prgImage;
    }

    public void setPrgImage(Long prgImage) {
        this.prgImage = prgImage;
    }

    public String getPrgCustomerDesc() {
        return prgCustomerDesc;
    }

    public void setPrgCustomerDesc(String prgCustomerDesc) {
        this.prgCustomerDesc = prgCustomerDesc;
    }

    public int getPrgTxnCurrency() {
        return prgTxnCurrency;
    }

    public void setPrgTxnCurrency(int prgTxnCurrency) {
        this.prgTxnCurrency = prgTxnCurrency;
    }

    public Long getPrgCurrencyId() {
        return prgCurrencyId;
    }

    public void setPrgCurrencyId(Long prgCurrencyId) {
        this.prgCurrencyId = prgCurrencyId;
    }

    public Integer getPrgComputationSource() {
        return prgComputationSource;
    }

    public void setPrgComputationSource(Integer prgComputationSource) {
        this.prgComputationSource = prgComputationSource;
    }

    public Long getPrgLoyaltyExtension() {
        return prgLoyaltyExtension;
    }

    public void setPrgLoyaltyExtension(Long prgLoyaltyExtension) {
        this.prgLoyaltyExtension = prgLoyaltyExtension;
    }

    public Long getPrgEligibleCusTier() {
        return prgEligibleCusTier;
    }

    public void setPrgEligibleCusTier(Long prgEligibleCusTier) {
        this.prgEligibleCusTier = prgEligibleCusTier;
    }

    public int getPrgProgramDriver() {
        return prgProgramDriver;
    }

    public void setPrgProgramDriver(int prgProgramDriver) {
        this.prgProgramDriver = prgProgramDriver;
    }

    public int getPrgRuleType() {
        return prgRuleType;
    }

    public void setPrgRuleType(int prgRuleType) {
        this.prgRuleType = prgRuleType;
    }

    public Double getPrgFixedValue() {
        return prgFixedValue;
    }

    public void setPrgFixedValue(Double prgFixedValue) {
        this.prgFixedValue = prgFixedValue;
    }

    public Double getPrgRatioDeno() {
        return prgRatioDeno;
    }

    public void setPrgRatioDeno(Double prgRatioDeno) {
        this.prgRatioDeno = prgRatioDeno;
    }

    public Double getPrgRatioNum() {
        return prgRatioNum;
    }

    public void setPrgRatioNum(Double prgRatioNum) {
        this.prgRatioNum = prgRatioNum;
    }

    public Double getPrgTier1LimitFrom() {
        return prgTier1LimitFrom;
    }

    public void setPrgTier1LimitFrom(Double prgTier1LimitFrom) {
        this.prgTier1LimitFrom = prgTier1LimitFrom;
    }

    public Double getPrgTier1LimitTo() {
        return prgTier1LimitTo;
    }

    public void setPrgTier1LimitTo(Double prgTier1LimitTo) {
        this.prgTier1LimitTo = prgTier1LimitTo;
    }

    public Double getPrgTier1Deno() {
        return prgTier1Deno;
    }

    public void setPrgTier1Deno(Double prgTier1Deno) {
        this.prgTier1Deno = prgTier1Deno;
    }

    public Double getPrgTier1Num() {
        return prgTier1Num;
    }

    public void setPrgTier1Num(Double prgTier1Num) {
        this.prgTier1Num = prgTier1Num;
    }

    public Double getPrgTier2LimitFrom() {
        return prgTier2LimitFrom;
    }

    public void setPrgTier2LimitFrom(Double prgTier2LimitFrom) {
        this.prgTier2LimitFrom = prgTier2LimitFrom;
    }

    public Double getPrgTier2LimitTo() {
        return prgTier2LimitTo;
    }

    public void setPrgTier2LimitTo(Double prgTier2LimitTo) {
        this.prgTier2LimitTo = prgTier2LimitTo;
    }

    public Double getPrgTier2Deno() {
        return prgTier2Deno;
    }

    public void setPrgTier2Deno(Double prgTier2Deno) {
        this.prgTier2Deno = prgTier2Deno;
    }

    public Double getPrgTier2Num() {
        return prgTier2Num;
    }

    public void setPrgTier2Num(Double prgTier2Num) {
        this.prgTier2Num = prgTier2Num;
    }

    public Double getPrgTier3LimitFrom() {
        return prgTier3LimitFrom;
    }

    public void setPrgTier3LimitFrom(Double prgTier3LimitFrom) {
        this.prgTier3LimitFrom = prgTier3LimitFrom;
    }

    public Double getPrgTier3LimitTo() {
        return prgTier3LimitTo;
    }

    public void setPrgTier3LimitTo(Double prgTier3LimitTo) {
        this.prgTier3LimitTo = prgTier3LimitTo;
    }

    public Double getPrgTier3Deno() {
        return prgTier3Deno;
    }

    public void setPrgTier3Deno(Double prgTier3Deno) {
        this.prgTier3Deno = prgTier3Deno;
    }

    public Double getPrgTier3Num() {
        return prgTier3Num;
    }

    public void setPrgTier3Num(Double prgTier3Num) {
        this.prgTier3Num = prgTier3Num;
    }

    public Double getPrgTier4LimitFrom() {
        return prgTier4LimitFrom;
    }

    public void setPrgTier4LimitFrom(Double prgTier4LimitFrom) {
        this.prgTier4LimitFrom = prgTier4LimitFrom;
    }

    public Double getPrgTier4LimitTo() {
        return prgTier4LimitTo;
    }

    public void setPrgTier4LimitTo(Double prgTier4LimitTo) {
        this.prgTier4LimitTo = prgTier4LimitTo;
    }

    public Double getPrgTier4Deno() {
        return prgTier4Deno;
    }

    public void setPrgTier4Deno(Double prgTier4Deno) {
        this.prgTier4Deno = prgTier4Deno;
    }

    public Double getPrgTier4Num() {
        return prgTier4Num;
    }

    public void setPrgTier4Num(Double prgTier4Num) {
        this.prgTier4Num = prgTier4Num;
    }

    public Double getPrgTier5LimitFrom() {
        return prgTier5LimitFrom;
    }

    public void setPrgTier5LimitFrom(Double prgTier5LimitFrom) {
        this.prgTier5LimitFrom = prgTier5LimitFrom;
    }

    public Double getPrgTier5LimitTo() {
        return prgTier5LimitTo;
    }

    public void setPrgTier5LimitTo(Double prgTier5LimitTo) {
        this.prgTier5LimitTo = prgTier5LimitTo;
    }

    public Double getPrgTier5Deno() {
        return prgTier5Deno;
    }

    public void setPrgTier5Deno(Double prgTier5Deno) {
        this.prgTier5Deno = prgTier5Deno;
    }

    public Double getPrgTier5Num() {
        return prgTier5Num;
    }

    public void setPrgTier5Num(Double prgTier5Num) {
        this.prgTier5Num = prgTier5Num;
    }

    public int getPrgStatus() {
        return prgStatus;
    }

    public void setPrgStatus(int prgStatus) {
        this.prgStatus = prgStatus;
    }

    public Date getPrgStartDate() {
        return prgStartDate;
    }

    public void setPrgStartDate(Date prgStartDate) {
        this.prgStartDate = prgStartDate;
    }

    public Date getPrgEndDate() {
        return prgEndDate;
    }

    public void setPrgEndDate(Date prgEndDate) {
        this.prgEndDate = prgEndDate;
    }

    public Integer getPrgMinTxnAmount() {
        return prgMinTxnAmount;
    }

    public void setPrgMinTxnAmount(Integer prgMinTxnAmount) {
        this.prgMinTxnAmount = prgMinTxnAmount;
    }

    public Integer getPrgPaymentModeInd() {
        return prgPaymentModeInd;
    }

    public void setPrgPaymentModeInd(Integer prgPaymentModeInd) {
        this.prgPaymentModeInd = prgPaymentModeInd;
    }

    public String getPrgPaymentModes() {
        return prgPaymentModes;
    }

    public void setPrgPaymentModes(String prgPaymentModes) {
        this.prgPaymentModes = prgPaymentModes;
    }

    public Integer getPrgLocationInd() {
        return prgLocationInd;
    }

    public void setPrgLocationInd(Integer prgLocationInd) {
        this.prgLocationInd = prgLocationInd;
    }

    public String getPrgLocations() {
        return prgLocations;
    }

    public void setPrgLocations(String prgLocations) {
        this.prgLocations = prgLocations;
    }

    public Integer getPrgTxnChannelInd() {
        return prgTxnChannelInd;
    }

    public void setPrgTxnChannelInd(Integer prgTxnChannelInd) {
        this.prgTxnChannelInd = prgTxnChannelInd;
    }

    public String getPrgTxnChannels() {
        return prgTxnChannels;
    }

    public void setPrgTxnChannels(String prgTxnChannels) {
        this.prgTxnChannels = prgTxnChannels;
    }

    public Integer getPrgDaysInd() {
        return prgDaysInd;
    }

    public void setPrgDaysInd(Integer prgDaysInd) {
        this.prgDaysInd = prgDaysInd;
    }

    public String getPrgDays() {
        return prgDays;
    }

    public void setPrgDays(String prgDays) {
        this.prgDays = prgDays;
    }

    public String getPrgSpecOcc() {
        return prgSpecOcc;
    }

    public void setPrgSpecOcc(String prgSpecOcc) {
        this.prgSpecOcc = prgSpecOcc;
    }

    public Time getPrgHrsActiveFrom() {
        return prgHrsActiveFrom;
    }

    public void setPrgHrsActiveFrom(Time prgHrsActiveFrom) {
        this.prgHrsActiveFrom = prgHrsActiveFrom;
    }

    public Time getPrgHrsActiveTo() {
        return prgHrsActiveTo;
    }

    public void setPrgHrsActiveTo(Time prgHrsActiveTo) {
        this.prgHrsActiveTo = prgHrsActiveTo;
    }

    public Integer getPrgAwardRestrictInd() {
        return prgAwardRestrictInd;
    }

    public void setPrgAwardRestrictInd(Integer prgAwardRestrictInd) {
        this.prgAwardRestrictInd = prgAwardRestrictInd;
    }

    public Integer getPrgAwardCustCount() {
        return prgAwardCustCount;
    }

    public void setPrgAwardCustCount(Integer prgAwardCustCount) {
        this.prgAwardCustCount = prgAwardCustCount;
    }

    public Integer getPrgEligibleCustType() {
        return prgEligibleCustType;
    }

    public void setPrgEligibleCustType(Integer prgEligibleCustType) {
        this.prgEligibleCustType = prgEligibleCustType;
    }

    public Integer getPrgEligibleCustSegmentId() {
        return prgEligibleCustSegmentId;
    }

    public void setPrgEligibleCustSegmentId(Integer prgEligibleCustSegmentId) {
        this.prgEligibleCustSegmentId = prgEligibleCustSegmentId;
    }

    public Integer getPrgAwardingTime() {
        return prgAwardingTime;
    }

    public void setPrgAwardingTime(Integer prgAwardingTime) {
        this.prgAwardingTime = prgAwardingTime;
    }

    public Integer getPrgAwardFreq() {
        return prgAwardFreq;
    }

    public void setPrgAwardFreq(Integer prgAwardFreq) {
        this.prgAwardFreq = prgAwardFreq;
    }

    public String getRwdCurrencyName() {
        return rwdCurrencyName;
    }

    public void setRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
    }

    public Integer getPrgRole() {
        return prgRole;
    }

    public void setPrgRole(Integer prgRole) {
        this.prgRole = prgRole;
    }
}



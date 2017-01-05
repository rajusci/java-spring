package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by abhilasha on 28/3/16.
 */
public class LoyaltyProgramBuilder {
    private Long prgProgramNo;
    private Long prgMerchantNo;
    private String prgProgramName = "";
    private String prgProgramDesc = "";
    private Long prgImage = ImagePrimaryId.PRIMARY_LOYALTY_PROGRAM_IMAGE;
    private String prgCustomerDesc = "";
    private Integer prgTxnCurrency = 356;
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
    private int prgStatus = LoyaltyProgramStatus.ACTIVE;
    private Date prgStartDate;
    private Date prgEndDate;
    private Integer prgMinTxnAmount = 0;
    private Integer prgPaymentModeInd = IndicatorStatus.NO;
    private String prgPaymentModes ="0";
    private Integer prgLocationInd = IndicatorStatus.NO;
    private String prgLocations = "0";
    private Integer prgTxnChannelInd = IndicatorStatus.NO;
    private String prgTxnChannels = "0";
    private Integer prgDaysInd = LoyaltyProgramActiveDuringInd.ALWAYS;
    private String prgDays = "0";
    private String prgSpecOcc = "0";
    private Time prgHrsActiveFrom;
    private Time prgHrsActiveTo;
    private Integer prgAwardRestrictInd = IndicatorStatus.NO;
    private Integer prgAwardCustCount = 0;
    private Integer prgEligibleCustType = LoyaltyProgramEligibleCustomerType.ALL_CUSTOMERS;
    private Long prgEligibleCustSegmentId = 0L;
    private Integer prgCustomerType = 0;
    private Integer prgAwardingTime = 1;
    private Integer prgAwardFreq = 1 ;
    private Integer prgRole=0;
    private int prgTxnSource = 1;
    private Set<LoyaltyProgramExtension> loyaltyProgramExtensionSet;
    private RewardCurrency rewardCurrency;
    private Set<LoyaltyProgramSku> loyaltyProgramSkuSet = new HashSet<>(0);
    private Image image;
    private AttributeExtendedEntityMap fieldMap;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private LoyaltyProgramBuilder() {
    }

    public static LoyaltyProgramBuilder aLoyaltyProgram() {
        return new LoyaltyProgramBuilder();
    }

    public LoyaltyProgramBuilder withPrgProgramNo(Long prgProgramNo) {
        this.prgProgramNo = prgProgramNo;
        return this;
    }

    public LoyaltyProgramBuilder withPrgMerchantNo(Long prgMerchantNo) {
        this.prgMerchantNo = prgMerchantNo;
        return this;
    }

    public LoyaltyProgramBuilder withPrgProgramName(String prgProgramName) {
        this.prgProgramName = prgProgramName;
        return this;
    }

    public LoyaltyProgramBuilder withPrgProgramDesc(String prgProgramDesc) {
        this.prgProgramDesc = prgProgramDesc;
        return this;
    }

    public LoyaltyProgramBuilder withPrgImage(Long prgImage) {
        this.prgImage = prgImage;
        return this;
    }

    public LoyaltyProgramBuilder withPrgCustomerDesc(String prgCustomerDesc) {
        this.prgCustomerDesc = prgCustomerDesc;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTxnCurrency(Integer prgTxnCurrency) {
        this.prgTxnCurrency = prgTxnCurrency;
        return this;
    }

    public LoyaltyProgramBuilder withPrgCurrencyId(Long prgCurrencyId) {
        this.prgCurrencyId = prgCurrencyId;
        return this;
    }

    public LoyaltyProgramBuilder withPrgComputationSource(Integer prgComputationSource) {
        this.prgComputationSource = prgComputationSource;
        return this;
    }

    public LoyaltyProgramBuilder withPrgLoyaltyExtension(Long prgLoyaltyExtension) {
        this.prgLoyaltyExtension = prgLoyaltyExtension;
        return this;
    }

    public LoyaltyProgramBuilder withPrgEligibleCusTier(Long prgEligibleCusTier) {
        this.prgEligibleCusTier = prgEligibleCusTier;
        return this;
    }

    public LoyaltyProgramBuilder withPrgProgramDriver(int prgProgramDriver) {
        this.prgProgramDriver = prgProgramDriver;
        return this;
    }

    public LoyaltyProgramBuilder withPrgRuleType(int prgRuleType) {
        this.prgRuleType = prgRuleType;
        return this;
    }

    public LoyaltyProgramBuilder withPrgFixedValue(Double prgFixedValue) {
        this.prgFixedValue = prgFixedValue;
        return this;
    }

    public LoyaltyProgramBuilder withPrgRatioDeno(Double prgRatioDeno) {
        this.prgRatioDeno = prgRatioDeno;
        return this;
    }

    public LoyaltyProgramBuilder withPrgRatioNum(Double prgRatioNum) {
        this.prgRatioNum = prgRatioNum;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier1LimitFrom(Double prgTier1LimitFrom) {
        this.prgTier1LimitFrom = prgTier1LimitFrom;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier1LimitTo(Double prgTier1LimitTo) {
        this.prgTier1LimitTo = prgTier1LimitTo;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier1Deno(Double prgTier1Deno) {
        this.prgTier1Deno = prgTier1Deno;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier1Num(Double prgTier1Num) {
        this.prgTier1Num = prgTier1Num;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier2LimitFrom(Double prgTier2LimitFrom) {
        this.prgTier2LimitFrom = prgTier2LimitFrom;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier2LimitTo(Double prgTier2LimitTo) {
        this.prgTier2LimitTo = prgTier2LimitTo;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier2Deno(Double prgTier2Deno) {
        this.prgTier2Deno = prgTier2Deno;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier2Num(Double prgTier2Num) {
        this.prgTier2Num = prgTier2Num;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier3LimitFrom(Double prgTier3LimitFrom) {
        this.prgTier3LimitFrom = prgTier3LimitFrom;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier3LimitTo(Double prgTier3LimitTo) {
        this.prgTier3LimitTo = prgTier3LimitTo;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier3Deno(Double prgTier3Deno) {
        this.prgTier3Deno = prgTier3Deno;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier3Num(Double prgTier3Num) {
        this.prgTier3Num = prgTier3Num;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier4LimitFrom(Double prgTier4LimitFrom) {
        this.prgTier4LimitFrom = prgTier4LimitFrom;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier4LimitTo(Double prgTier4LimitTo) {
        this.prgTier4LimitTo = prgTier4LimitTo;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier4Deno(Double prgTier4Deno) {
        this.prgTier4Deno = prgTier4Deno;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier4Num(Double prgTier4Num) {
        this.prgTier4Num = prgTier4Num;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier5LimitFrom(Double prgTier5LimitFrom) {
        this.prgTier5LimitFrom = prgTier5LimitFrom;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier5LimitTo(Double prgTier5LimitTo) {
        this.prgTier5LimitTo = prgTier5LimitTo;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier5Deno(Double prgTier5Deno) {
        this.prgTier5Deno = prgTier5Deno;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTier5Num(Double prgTier5Num) {
        this.prgTier5Num = prgTier5Num;
        return this;
    }

    public LoyaltyProgramBuilder withPrgStatus(int prgStatus) {
        this.prgStatus = prgStatus;
        return this;
    }

    public LoyaltyProgramBuilder withPrgStartDate(Date prgStartDate) {
        this.prgStartDate = prgStartDate;
        return this;
    }

    public LoyaltyProgramBuilder withPrgEndDate(Date prgEndDate) {
        this.prgEndDate = prgEndDate;
        return this;
    }

    public LoyaltyProgramBuilder withPrgMinTxnAmount(Integer prgMinTxnAmount) {
        this.prgMinTxnAmount = prgMinTxnAmount;
        return this;
    }

    public LoyaltyProgramBuilder withPrgPaymentModeInd(Integer prgPaymentModeInd) {
        this.prgPaymentModeInd = prgPaymentModeInd;
        return this;
    }

    public LoyaltyProgramBuilder withPrgPaymentModes(String prgPaymentModes) {
        this.prgPaymentModes = prgPaymentModes;
        return this;
    }

    public LoyaltyProgramBuilder withPrgLocationInd(Integer prgLocationInd) {
        this.prgLocationInd = prgLocationInd;
        return this;
    }

    public LoyaltyProgramBuilder withPrgLocations(String prgLocations) {
        this.prgLocations = prgLocations;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTxnChannelInd(Integer prgTxnChannelInd) {
        this.prgTxnChannelInd = prgTxnChannelInd;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTxnChannels(String prgTxnChannels) {
        this.prgTxnChannels = prgTxnChannels;
        return this;
    }

    public LoyaltyProgramBuilder withPrgDaysInd(Integer prgDaysInd) {
        this.prgDaysInd = prgDaysInd;
        return this;
    }

    public LoyaltyProgramBuilder withPrgDays(String prgDays) {
        this.prgDays = prgDays;
        return this;
    }

    public LoyaltyProgramBuilder withPrgSpecOcc(String prgSpecOcc) {
        this.prgSpecOcc = prgSpecOcc;
        return this;
    }

    public LoyaltyProgramBuilder withPrgHrsActiveFrom(Time prgHrsActiveFrom) {
        this.prgHrsActiveFrom = prgHrsActiveFrom;
        return this;
    }

    public LoyaltyProgramBuilder withPrgHrsActiveTo(Time prgHrsActiveTo) {
        this.prgHrsActiveTo = prgHrsActiveTo;
        return this;
    }

    public LoyaltyProgramBuilder withPrgAwardRestrictInd(Integer prgAwardRestrictInd) {
        this.prgAwardRestrictInd = prgAwardRestrictInd;
        return this;
    }

    public LoyaltyProgramBuilder withPrgAwardCustCount(Integer prgAwardCustCount) {
        this.prgAwardCustCount = prgAwardCustCount;
        return this;
    }

    public LoyaltyProgramBuilder withPrgEligibleCustType(Integer prgEligibleCustType) {
        this.prgEligibleCustType = prgEligibleCustType;
        return this;
    }

    public LoyaltyProgramBuilder withPrgEligibleCustSegmentId(Long prgEligibleCustSegmentId) {
        this.prgEligibleCustSegmentId = prgEligibleCustSegmentId;
        return this;
    }

    public LoyaltyProgramBuilder withPrgCustomerType(Integer prgCustomerType) {
        this.prgCustomerType = prgCustomerType;
        return this;
    }

    public LoyaltyProgramBuilder withPrgAwardingTime(Integer prgAwardingTime) {
        this.prgAwardingTime = prgAwardingTime;
        return this;
    }

    public LoyaltyProgramBuilder withPrgAwardFreq(Integer prgAwardFreq) {
        this.prgAwardFreq = prgAwardFreq;
        return this;
    }

    public LoyaltyProgramBuilder withPrgRole(Integer prgRole) {
        this.prgRole = prgRole;
        return this;
    }

    public LoyaltyProgramBuilder withPrgTxnSource(int prgTxnSource) {
        this.prgTxnSource = prgTxnSource;
        return this;
    }

    public LoyaltyProgramBuilder withLoyaltyProgramExtensionSet(Set<LoyaltyProgramExtension> loyaltyProgramExtensionSet) {
        this.loyaltyProgramExtensionSet = loyaltyProgramExtensionSet;
        return this;
    }

    public LoyaltyProgramBuilder withRewardCurrency(RewardCurrency rewardCurrency) {
        this.rewardCurrency = rewardCurrency;
        return this;
    }

    public LoyaltyProgramBuilder withLoyaltyProgramSkuSet(Set<LoyaltyProgramSku> loyaltyProgramSkuSet) {
        this.loyaltyProgramSkuSet = loyaltyProgramSkuSet;
        return this;
    }

    public LoyaltyProgramBuilder withImage(Image image) {
        this.image = image;
        return this;
    }

    public LoyaltyProgramBuilder withFieldMap(AttributeExtendedEntityMap fieldMap) {
        this.fieldMap = fieldMap;
        return this;
    }

    public LoyaltyProgramBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LoyaltyProgramBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LoyaltyProgramBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public LoyaltyProgramBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public LoyaltyProgramBuilder but() {
        return aLoyaltyProgram().withPrgProgramNo(prgProgramNo).withPrgMerchantNo(prgMerchantNo).withPrgProgramName(prgProgramName).withPrgProgramDesc(prgProgramDesc).withPrgImage(prgImage).withPrgCustomerDesc(prgCustomerDesc).withPrgTxnCurrency(prgTxnCurrency).withPrgCurrencyId(prgCurrencyId).withPrgComputationSource(prgComputationSource).withPrgLoyaltyExtension(prgLoyaltyExtension).withPrgEligibleCusTier(prgEligibleCusTier).withPrgProgramDriver(prgProgramDriver).withPrgRuleType(prgRuleType).withPrgFixedValue(prgFixedValue).withPrgRatioDeno(prgRatioDeno).withPrgRatioNum(prgRatioNum).withPrgTier1LimitFrom(prgTier1LimitFrom).withPrgTier1LimitTo(prgTier1LimitTo).withPrgTier1Deno(prgTier1Deno).withPrgTier1Num(prgTier1Num).withPrgTier2LimitFrom(prgTier2LimitFrom).withPrgTier2LimitTo(prgTier2LimitTo).withPrgTier2Deno(prgTier2Deno).withPrgTier2Num(prgTier2Num).withPrgTier3LimitFrom(prgTier3LimitFrom).withPrgTier3LimitTo(prgTier3LimitTo).withPrgTier3Deno(prgTier3Deno).withPrgTier3Num(prgTier3Num).withPrgTier4LimitFrom(prgTier4LimitFrom).withPrgTier4LimitTo(prgTier4LimitTo).withPrgTier4Deno(prgTier4Deno).withPrgTier4Num(prgTier4Num).withPrgTier5LimitFrom(prgTier5LimitFrom).withPrgTier5LimitTo(prgTier5LimitTo).withPrgTier5Deno(prgTier5Deno).withPrgTier5Num(prgTier5Num).withPrgStatus(prgStatus).withPrgStartDate(prgStartDate).withPrgEndDate(prgEndDate).withPrgMinTxnAmount(prgMinTxnAmount).withPrgPaymentModeInd(prgPaymentModeInd).withPrgPaymentModes(prgPaymentModes).withPrgLocationInd(prgLocationInd).withPrgLocations(prgLocations).withPrgTxnChannelInd(prgTxnChannelInd).withPrgTxnChannels(prgTxnChannels).withPrgDaysInd(prgDaysInd).withPrgDays(prgDays).withPrgSpecOcc(prgSpecOcc).withPrgHrsActiveFrom(prgHrsActiveFrom).withPrgHrsActiveTo(prgHrsActiveTo).withPrgAwardRestrictInd(prgAwardRestrictInd).withPrgAwardCustCount(prgAwardCustCount).withPrgEligibleCustType(prgEligibleCustType).withPrgEligibleCustSegmentId(prgEligibleCustSegmentId).withPrgCustomerType(prgCustomerType).withPrgAwardingTime(prgAwardingTime).withPrgAwardFreq(prgAwardFreq).withPrgRole(prgRole).withPrgTxnSource(prgTxnSource).withLoyaltyProgramExtensionSet(loyaltyProgramExtensionSet).withRewardCurrency(rewardCurrency).withLoyaltyProgramSkuSet(loyaltyProgramSkuSet).withImage(image).withFieldMap(fieldMap).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public LoyaltyProgram build() {
        LoyaltyProgram loyaltyProgram = new LoyaltyProgram();
        loyaltyProgram.setPrgProgramNo(prgProgramNo);
        loyaltyProgram.setPrgMerchantNo(prgMerchantNo);
        loyaltyProgram.setPrgProgramName(prgProgramName);
        loyaltyProgram.setPrgProgramDesc(prgProgramDesc);
        loyaltyProgram.setPrgImage(prgImage);
        loyaltyProgram.setPrgCustomerDesc(prgCustomerDesc);
        loyaltyProgram.setPrgTxnCurrency(prgTxnCurrency);
        loyaltyProgram.setPrgCurrencyId(prgCurrencyId);
        loyaltyProgram.setPrgComputationSource(prgComputationSource);
        loyaltyProgram.setPrgLoyaltyExtension(prgLoyaltyExtension);
        loyaltyProgram.setPrgEligibleCusTier(prgEligibleCusTier);
        loyaltyProgram.setPrgProgramDriver(prgProgramDriver);
        loyaltyProgram.setPrgRuleType(prgRuleType);
        loyaltyProgram.setPrgFixedValue(prgFixedValue);
        loyaltyProgram.setPrgRatioDeno(prgRatioDeno);
        loyaltyProgram.setPrgRatioNum(prgRatioNum);
        loyaltyProgram.setPrgTier1LimitFrom(prgTier1LimitFrom);
        loyaltyProgram.setPrgTier1LimitTo(prgTier1LimitTo);
        loyaltyProgram.setPrgTier1Deno(prgTier1Deno);
        loyaltyProgram.setPrgTier1Num(prgTier1Num);
        loyaltyProgram.setPrgTier2LimitFrom(prgTier2LimitFrom);
        loyaltyProgram.setPrgTier2LimitTo(prgTier2LimitTo);
        loyaltyProgram.setPrgTier2Deno(prgTier2Deno);
        loyaltyProgram.setPrgTier2Num(prgTier2Num);
        loyaltyProgram.setPrgTier3LimitFrom(prgTier3LimitFrom);
        loyaltyProgram.setPrgTier3LimitTo(prgTier3LimitTo);
        loyaltyProgram.setPrgTier3Deno(prgTier3Deno);
        loyaltyProgram.setPrgTier3Num(prgTier3Num);
        loyaltyProgram.setPrgTier4LimitFrom(prgTier4LimitFrom);
        loyaltyProgram.setPrgTier4LimitTo(prgTier4LimitTo);
        loyaltyProgram.setPrgTier4Deno(prgTier4Deno);
        loyaltyProgram.setPrgTier4Num(prgTier4Num);
        loyaltyProgram.setPrgTier5LimitFrom(prgTier5LimitFrom);
        loyaltyProgram.setPrgTier5LimitTo(prgTier5LimitTo);
        loyaltyProgram.setPrgTier5Deno(prgTier5Deno);
        loyaltyProgram.setPrgTier5Num(prgTier5Num);
        loyaltyProgram.setPrgStatus(prgStatus);
        loyaltyProgram.setPrgStartDate(prgStartDate);
        loyaltyProgram.setPrgEndDate(prgEndDate);
        loyaltyProgram.setPrgMinTxnAmount(prgMinTxnAmount);
        loyaltyProgram.setPrgPaymentModeInd(prgPaymentModeInd);
        loyaltyProgram.setPrgPaymentModes(prgPaymentModes);
        loyaltyProgram.setPrgLocationInd(prgLocationInd);
        loyaltyProgram.setPrgLocations(prgLocations);
        loyaltyProgram.setPrgTxnChannelInd(prgTxnChannelInd);
        loyaltyProgram.setPrgTxnChannels(prgTxnChannels);
        loyaltyProgram.setPrgDaysInd(prgDaysInd);
        loyaltyProgram.setPrgDays(prgDays);
        loyaltyProgram.setPrgSpecOcc(prgSpecOcc);
        loyaltyProgram.setPrgHrsActiveFrom(prgHrsActiveFrom);
        loyaltyProgram.setPrgHrsActiveTo(prgHrsActiveTo);
        loyaltyProgram.setPrgAwardRestrictInd(prgAwardRestrictInd);
        loyaltyProgram.setPrgAwardCustCount(prgAwardCustCount);
        loyaltyProgram.setPrgEligibleCustType(prgEligibleCustType);
        loyaltyProgram.setPrgEligibleCustSegmentId(prgEligibleCustSegmentId);
        loyaltyProgram.setPrgCustomerType(prgCustomerType);
        loyaltyProgram.setPrgAwardingTime(prgAwardingTime);
        loyaltyProgram.setPrgAwardFreq(prgAwardFreq);
        loyaltyProgram.setPrgRole(prgRole);
        loyaltyProgram.setPrgTxnSource(prgTxnSource);
        loyaltyProgram.setLoyaltyProgramExtensionSet(loyaltyProgramExtensionSet);
        loyaltyProgram.setRewardCurrency(rewardCurrency);
        loyaltyProgram.setLoyaltyProgramSkuSet(loyaltyProgramSkuSet);
        loyaltyProgram.setImage(image);
        loyaltyProgram.setFieldMap(fieldMap);
        loyaltyProgram.setCreatedAt(createdAt);
        loyaltyProgram.setCreatedBy(createdBy);
        loyaltyProgram.setUpdatedAt(updatedAt);
        loyaltyProgram.setUpdatedBy(updatedBy);
        return loyaltyProgram;
    }
}

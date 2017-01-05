package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtension;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 21/5/14.
 */
@Entity
@Table(name = "LOYALTY_PROGRAMS")
public class LoyaltyProgram extends AuditedEntity implements AttributeExtension {

    @Id
    @Column(name = "PRG_PROGRAM_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prgProgramNo;

    @Basic
    @Column(name = "PRG_MERCHANT_NO")
    private Long prgMerchantNo;

    @Basic
    @Column(name = "PRG_PROGRAM_NAME")
    @NotNull(message ="{loyaltyprogram.prgprogramname.notnull}")
    @NotEmpty(message ="{loyaltyprogram.prgprogramname.notempty}")
    @Size(max= 100,message ="{loyaltyprogram.prgprogramname.size}")
    private String prgProgramName = "";

    @Basic
    @Column(name = "PRG_PROGRAM_DESC")
    @NotNull(message ="{loyaltyprogram.prgprogramdesc.notnull}")
    @NotEmpty(message ="{loyaltyprogram.prgprogramdesc.notempty}")
    @Size(max= 100,message ="{loyaltyprogram.prgprogramdesc.size}")
    private String prgProgramDesc = "";

    @Basic
    @Column(name = "PRG_IMAGE")
    private Long prgImage = ImagePrimaryId.PRIMARY_LOYALTY_PROGRAM_IMAGE;

    @Basic
    @Column(name = "PRG_CUSTOMER_DESC")
    @Size(max= 500,message ="{loyaltyprogram.prgcustomerdesc.size}")
    private String prgCustomerDesc = "";

    @Basic
    @Column(name = "PRG_TXN_CURRENCY")
    private Integer prgTxnCurrency = 356;

    @Column(name = "PRG_CURRENCY_ID")
    private Long prgCurrencyId = 0L ;

    @Column(name = "PRG_COMPUTATION_SOURCE")
    private Integer prgComputationSource = LoyaltyComputationSource.PROGRAM_CONFIGURATION;

    @Column(name = "PRG_LOYALTY_EXTENSION")
    private Long prgLoyaltyExtension = 0L;

    @Basic
    @Column(name = "PRG_ELIGIBLE_CUS_TIER")
    private Long prgEligibleCusTier = 0L;

    @Basic
    @Column(name = "PRG_PROGRAM_DRIVER")
    private int prgProgramDriver = 1;

    @Basic
    @Column(name = "PRG_RULE_TYPE")
    private int prgRuleType = 1;

    @Basic
    @Column(name = "PRG_FIXED_VALUE",precision = 16,scale = 2)
    private Double prgFixedValue = 0.0;

    @Basic
    @Column(name = "PRG_RATIO_DENO",precision = 16,scale=2)
    private Double prgRatioDeno = 1.0;

    @Basic
    @Column(name = "PRG_RATIO_NUM" ,precision = 16,scale=2)
    private Double prgRatioNum = 0.0;

    @Basic
    @Column(name = "PRG_TIER1_LIMIT_FROM",precision = 16,scale=2)
    private Double prgTier1LimitFrom = 0.0;

    @Basic
    @Column(name = "PRG_TIER1_LIMIT_TO",precision = 16,scale=2)
    private Double prgTier1LimitTo = 0.0;

    @Basic
    @Column(name = "PRG_TIER1_DENO" ,precision = 16,scale=2)
    private Double prgTier1Deno = 1.0;

    @Basic
    @Column(name = "PRG_TIER1_NUM" ,precision = 16,scale=2)
    private Double prgTier1Num = 0.0;

    @Basic
    @Column(name = "PRG_TIER2_LIMIT_FROM" ,precision = 16,scale=2)
    private Double prgTier2LimitFrom = 0.0;

    @Basic
    @Column(name = "PRG_TIER2_LIMIT_TO" ,precision = 16,scale=2)
    private Double prgTier2LimitTo = 0.0;

    @Basic
    @Column(name = "PRG_TIER2_DENO" ,precision = 16,scale=2)
    private Double prgTier2Deno = 1.0;

    @Basic
    @Column(name = "PRG_TIER2_NUM" ,precision = 16,scale=2)
    private Double prgTier2Num = 0.0;

    @Basic
    @Column(name = "PRG_TIER3_LIMIT_FROM",precision = 16,scale=2)
    private Double prgTier3LimitFrom = 0.0;

    @Basic
    @Column(name = "PRG_TIER3_LIMIT_TO",precision = 16,scale=2)
    private Double prgTier3LimitTo = 0.0;

    @Basic
    @Column(name = "PRG_TIER3_DENO",precision = 16,scale=2)
    private Double prgTier3Deno = 1.0;

    @Basic
    @Column(name = "PRG_TIER3_NUM",precision = 16,scale=2)
    private Double prgTier3Num = 0.0;

    @Basic
    @Column(name = "PRG_TIER4_LIMIT_FROM",precision = 16,scale=2)
    private Double prgTier4LimitFrom = 0.0;

    @Basic
    @Column(name = "PRG_TIER4_LIMIT_TO",precision = 16,scale=2)
    private Double prgTier4LimitTo = 0.0;

    @Basic
    @Column(name = "PRG_TIER4_DENO",precision = 16,scale=2)
    private Double prgTier4Deno = 1.0;

    @Basic
    @Column(name = "PRG_TIER4_NUM",precision = 16,scale=2)
    private Double prgTier4Num = 0.0;

    @Basic
    @Column(name = "PRG_TIER5_LIMIT_FROM",precision = 16,scale=2)
    private Double prgTier5LimitFrom = 0.0;

    @Basic
    @Column(name = "PRG_TIER5_LIMIT_TO",precision = 16,scale=2)
    private Double prgTier5LimitTo = 0.0;

    @Basic
    @Column(name = "PRG_TIER5_DENO",precision = 16,scale=2)
    private Double prgTier5Deno = 1.0;

    @Basic
    @Column(name = "PRG_TIER5_NUM",precision = 16,scale=2)
    private Double prgTier5Num = 0.0;

    @Basic
    @Column(name = "PRG_STATUS")
    private int prgStatus = LoyaltyProgramStatus.ACTIVE;

    @Basic
    @Column(name = "PRG_START_DATE")
    private Date prgStartDate;

    @Basic
    @Column(name = "PRG_END_DATE")
    private Date prgEndDate;

    @Basic
    @Column(name = "PRG_MIN_TXN_AMOUNT")
    private Integer prgMinTxnAmount = 0;

    @Basic
    @Column(name = "PRG_PAYMENT_MODE_IND")
    private Integer prgPaymentModeInd = IndicatorStatus.NO;

    @Basic
    @Column(name = "PRG_PAYMENT_MODES")
    private String prgPaymentModes ="0";

    @Basic
    @Column(name = "PRG_LOCATION_IND")
    private Integer prgLocationInd = IndicatorStatus.NO;

    @Basic
    @Column(name = "PRG_LOCATIONS")
    private String prgLocations = "0";

    @Basic
    @Column(name = "PRG_TXN_CHANNEL_IND")
    private Integer prgTxnChannelInd = IndicatorStatus.NO;

    @Basic
    @Column(name = "PRG_TXN_CHANNELS")
    private String prgTxnChannels = "0";

    @Basic
    @Column(name = "PRG_DAYS_IND")
    private Integer prgDaysInd = LoyaltyProgramActiveDuringInd.ALWAYS;

    @Basic
    @Column(name = "PRG_DAYS")
    private String prgDays = "0";

    @Basic
    @Column(name = "PRG_SPEC_OCC")
    private String prgSpecOcc = "0";

    @Basic
    @Column(name = "PRG_HRS_ACTIVE_FROM")
    private Time prgHrsActiveFrom;

    @Basic
    @Column(name = "PRG_HRS_ACTIVE_TO")
    private Time prgHrsActiveTo;

    @Basic
    @Column(name = "PRG_AWARD_RESTRICT_IND")
    private Integer prgAwardRestrictInd = IndicatorStatus.NO;

    @Basic
    @Column(name = "PRG_AWARD_CUST_COUNT")
    private Integer prgAwardCustCount = 0;

    @Basic
    @Column(name = "PRG_ELIGIBLE_CUST_TYPE")
    private Integer prgEligibleCustType = LoyaltyProgramEligibleCustomerType.ALL_CUSTOMERS;

    @Basic
    @Column(name = "PRG_ELIGIBLE_CUST_SEGMENT_ID")
    private Long prgEligibleCustSegmentId = 0L;

    @Basic
    @Column(name = "PRG_CUSTOMER_TYPE",nullable = true)
    private Integer prgCustomerType = 0;

    @Basic
    @Column(name = "PRG_AWARDING_TIME")
    private Integer prgAwardingTime = 1;

    @Basic
    @Column(name = "PRG_AWARD_FREQ")
    private Integer prgAwardFreq = 1 ;

    @Basic
    @Column(name = "PRG_ROLE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer prgRole=0;

    @Basic
    @Column(name = "PRG_TXN_SOURCE")
    private Integer prgTxnSource = LoyaltyProgramDriver.TRANSACTION_AMOUNT;

    @OneToMany(fetch = FetchType.EAGER,cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name="LPE_LOYALTY_PROGRAM_ID",referencedColumnName = "PRG_PROGRAM_NO" )
    private Set<LoyaltyProgramExtension> loyaltyProgramExtensionSet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="PRG_CURRENCY_ID",insertable = false,updatable = false)
    private RewardCurrency rewardCurrency;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="LPU_PROGRAM_ID",referencedColumnName = "PRG_PROGRAM_NO")
    private Set<LoyaltyProgramSku> loyaltyProgramSkuSet = new HashSet<>(0);

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRG_IMAGE",insertable = false,updatable = false)
    private Image image;


    @Transient
    private AttributeExtendedEntityMap fieldMap;




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

    public Integer getPrgTxnCurrency() {
        return prgTxnCurrency;
    }

    public void setPrgTxnCurrency(Integer prgTxnCurrency) {
        this.prgTxnCurrency = prgTxnCurrency;
    }

    public Long getPrgCurrencyId() {
        return prgCurrencyId;
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

    public void setPrgCurrencyId(Long prgCurrencyId) {
        this.prgCurrencyId = prgCurrencyId;
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

    public Long getPrgEligibleCustSegmentId() {
        return prgEligibleCustSegmentId;
    }

    public void setPrgEligibleCustSegmentId(Long prgEligibleCustSegmentId) {
        this.prgEligibleCustSegmentId = prgEligibleCustSegmentId;
    }

    public Integer getPrgCustomerType() {
        return prgCustomerType;
    }

    public void setPrgCustomerType(Integer prgCustomerType) {
        this.prgCustomerType = prgCustomerType;
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

    public Set<LoyaltyProgramExtension> getLoyaltyProgramExtensionSet() {
        return loyaltyProgramExtensionSet;
    }

    public void setLoyaltyProgramExtensionSet(Set<LoyaltyProgramExtension> loyaltyProgramExtensionSet) {
        this.loyaltyProgramExtensionSet = loyaltyProgramExtensionSet;
    }

    public RewardCurrency getRewardCurrency() {
        return rewardCurrency;
    }

    public void setRewardCurrency(RewardCurrency rewardCurrency) {
        this.rewardCurrency = rewardCurrency;
    }

    public AttributeExtendedEntityMap getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(AttributeExtendedEntityMap fieldMap) {
        this.fieldMap = fieldMap;
    }

    public Set<LoyaltyProgramSku> getLoyaltyProgramSkuSet() {
        return loyaltyProgramSkuSet;
    }

    public void setLoyaltyProgramSkuSet(Set<LoyaltyProgramSku> loyaltyProgramSkuSet) {
        this.loyaltyProgramSkuSet = loyaltyProgramSkuSet;
    }

    public Image getImage() {
        return image;
    }


    public Integer getPrgRole() {
        return prgRole;
    }

    public void setPrgRole(Integer prgRole) {
        this.prgRole = prgRole;
    }

    public Integer getPrgTxnSource() {
        return prgTxnSource;
    }

    public void setPrgTxnSource(Integer prgTxnSource) {
        this.prgTxnSource = prgTxnSource;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "LoyaltyProgram{" +
                "prgProgramNo=" + prgProgramNo +
                ", prgMerchantNo=" + prgMerchantNo +
                ", prgProgramName='" + prgProgramName + '\'' +
                ", prgProgramDesc='" + prgProgramDesc + '\'' +
                ", prgImage=" + prgImage +
                ", prgCustomerDesc='" + prgCustomerDesc + '\'' +
                ", prgTxnCurrency=" + prgTxnCurrency +
                ", prgCurrencyId=" + prgCurrencyId +
                ", prgComputationSource=" + prgComputationSource +
                ", prgLoyaltyExtension=" + prgLoyaltyExtension +
                ", prgEligibleCusTier=" + prgEligibleCusTier +
                ", prgProgramDriver=" + prgProgramDriver +
                ", prgRuleType=" + prgRuleType +
                ", prgFixedValue=" + prgFixedValue +
                ", prgRatioDeno=" + prgRatioDeno +
                ", prgRatioNum=" + prgRatioNum +
                ", prgTier1LimitFrom=" + prgTier1LimitFrom +
                ", prgTier1LimitTo=" + prgTier1LimitTo +
                ", prgTier1Deno=" + prgTier1Deno +
                ", prgTier1Num=" + prgTier1Num +
                ", prgTier2LimitFrom=" + prgTier2LimitFrom +
                ", prgTier2LimitTo=" + prgTier2LimitTo +
                ", prgTier2Deno=" + prgTier2Deno +
                ", prgTier2Num=" + prgTier2Num +
                ", prgTier3LimitFrom=" + prgTier3LimitFrom +
                ", prgTier3LimitTo=" + prgTier3LimitTo +
                ", prgTier3Deno=" + prgTier3Deno +
                ", prgTier3Num=" + prgTier3Num +
                ", prgTier4LimitFrom=" + prgTier4LimitFrom +
                ", prgTier4LimitTo=" + prgTier4LimitTo +
                ", prgTier4Deno=" + prgTier4Deno +
                ", prgTier4Num=" + prgTier4Num +
                ", prgTier5LimitFrom=" + prgTier5LimitFrom +
                ", prgTier5LimitTo=" + prgTier5LimitTo +
                ", prgTier5Deno=" + prgTier5Deno +
                ", prgTier5Num=" + prgTier5Num +
                ", prgStatus=" + prgStatus +
                ", prgStartDate=" + prgStartDate +
                ", prgEndDate=" + prgEndDate +
                ", prgMinTxnAmount=" + prgMinTxnAmount +
                ", prgPaymentModeInd=" + prgPaymentModeInd +
                ", prgPaymentModes='" + prgPaymentModes + '\'' +
                ", prgLocationInd=" + prgLocationInd +
                ", prgLocations='" + prgLocations + '\'' +
                ", prgTxnChannelInd=" + prgTxnChannelInd +
                ", prgTxnChannels='" + prgTxnChannels + '\'' +
                ", prgDaysInd=" + prgDaysInd +
                ", prgDays='" + prgDays + '\'' +
                ", prgSpecOcc='" + prgSpecOcc + '\'' +
                ", prgHrsActiveFrom=" + prgHrsActiveFrom +
                ", prgHrsActiveTo=" + prgHrsActiveTo +
                ", prgAwardRestrictInd=" + prgAwardRestrictInd +
                ", prgAwardCustCount=" + prgAwardCustCount +
                ", prgEligibleCustType=" + prgEligibleCustType +
                ", prgEligibleCustSegmentId=" + prgEligibleCustSegmentId +
                ", prgCustomerType=" + prgCustomerType +
                ", prgAwardingTime=" + prgAwardingTime +
                ", prgAwardFreq=" + prgAwardFreq +
                ", prgRole=" + prgRole +
                ", prgTxnSource=" + prgTxnSource +
                ", loyaltyProgramExtensionSet=" + loyaltyProgramExtensionSet +
                ", rewardCurrency=" + rewardCurrency +
                ", loyaltyProgramSkuSet=" + loyaltyProgramSkuSet +
                ", image=" + image +
                ", fieldMap=" + fieldMap +
                '}';
    }
}

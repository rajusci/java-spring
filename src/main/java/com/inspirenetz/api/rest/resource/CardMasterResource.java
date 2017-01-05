package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.*;

import java.sql.Date;
import java.util.List;

/**
 * Created by sandheepgr on 23/7/14.
 */
public class CardMasterResource extends BaseResource {


    private Long crmId;

    private Long crmMerchantNo;

    private Long crmLocation = 0L;

    private String crmCardNo = "";

    private Long crmType = 0L;

    private Integer crmCardStatus = CardMasterStatus.NEW;

    private Double crmCardBalance = 0.0;

    private String crmLoyaltyId = "";

    private String crmCardHolderName ="";

    private Integer crmProfession = 0;

    private String crmMobile = "";

    private String crmEmailId ="";

    private Date crmDob;

    private String crmAddress = "";

    private String crmCity = "";

    private String crmPincode ="";

    private Integer crmState = 0;

    private Integer crmCountry = 356;

    private String crmSecurityQuestion ="";

    private String crmSecurityAnswer ="";

    private String crmNomineeName ="";

    private String crmNomineeRelation="";

    private Date crmNomineeDob;

    private String crmNomineeAddress = "";

    private Date crmExpiryDate ;

    private int crmNumTxns = 0;


    private Integer crtAllowPinIndicator = IndicatorStatus.NO;

    private String cardName = "";

    private boolean expired = false;

    private String expiry ="";

    private String activationPin="";

    private String crtName = "";

    private int crtType = CardTypeType.FIXED_VALUE;

    private double crtFixedValue = 0.0;

    private double crtMinTopupValue = 0.0;

    private double crtMaxValue = 0.0;

    private Integer crtExpiryOption = CardTypeExpiryOption.EXPIRY_DATE;

    private Date crtExpiryDate;

    private Integer crtExpiryDays = 0;

    private Integer crtMaxNumTxns= 0;

    private  Integer crmActivationType = CardActivationType.CARD_TYPE;

    private Integer cusIdType;

    private String cusIdNo="";

    private Long crmTier;

    private Double crmPromoBalance = 0.0;

    private Double totalBalance = 0.0;

    private Boolean isPinRequired = false;



    public List<CardNumberDetails> multipleCardNumber;

    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }

    public Long getCrmMerchantNo() {
        return crmMerchantNo;
    }

    public void setCrmMerchantNo(Long crmMerchantNo) {
        this.crmMerchantNo = crmMerchantNo;
    }

    public Long getCrmLocation() {
        return crmLocation;
    }

    public void setCrmLocation(Long crmLocation) {
        this.crmLocation = crmLocation;
    }

    public String getCrmCardNo() {
        return crmCardNo;
    }

    public void setCrmCardNo(String crmCardNo) {
        this.crmCardNo = crmCardNo;
    }

    public Long getCrmType() {
        return crmType;
    }

    public void setCrmType(Long crmType) {
        this.crmType = crmType;
    }

    public Integer getCrmCardStatus() {
        return crmCardStatus;
    }

    public void setCrmCardStatus(Integer crmCardStatus) {
        this.crmCardStatus = crmCardStatus;
    }

    public Double getCrmCardBalance() {
        return crmCardBalance;
    }

    public void setCrmCardBalance(Double crmCardBalance) {
        this.crmCardBalance = crmCardBalance;
    }

    public String getCrmLoyaltyId() {
        return crmLoyaltyId;
    }

    public void setCrmLoyaltyId(String crmLoyaltyId) {
        this.crmLoyaltyId = crmLoyaltyId;
    }

    public String getCrmCardHolderName() {
        return crmCardHolderName;
    }

    public void setCrmCardHolderName(String crmCardHolderName) {
        this.crmCardHolderName = crmCardHolderName;
    }

    public Integer getCrmProfession() {
        return crmProfession;
    }

    public void setCrmProfession(Integer crmProfession) {
        this.crmProfession = crmProfession;
    }

    public String getCrmMobile() {
        return crmMobile;
    }

    public void setCrmMobile(String crmMobile) {
        this.crmMobile = crmMobile;
    }

    public String getCrmEmailId() {
        return crmEmailId;
    }

    public void setCrmEmailId(String crmEmailId) {
        this.crmEmailId = crmEmailId;
    }

    public Date getCrmDob() {
        return crmDob;
    }

    public void setCrmDob(Date crmDob) {
        this.crmDob = crmDob;
    }

    public String getCrmAddress() {
        return crmAddress;
    }

    public void setCrmAddress(String crmAddress) {
        this.crmAddress = crmAddress;
    }

    public String getCrmCity() {
        return crmCity;
    }

    public void setCrmCity(String crmCity) {
        this.crmCity = crmCity;
    }

    public String getCrmPincode() {
        return crmPincode;
    }

    public void setCrmPincode(String crmPincode) {
        this.crmPincode = crmPincode;
    }

    public Integer getCrmState() {
        return crmState;
    }

    public void setCrmState(Integer crmState) {
        this.crmState = crmState;
    }

    public Integer getCrmCountry() {
        return crmCountry;
    }

    public void setCrmCountry(Integer crmCountry) {
        this.crmCountry = crmCountry;
    }

    public String getCrmSecurityQuestion() {
        return crmSecurityQuestion;
    }

    public void setCrmSecurityQuestion(String crmSecurityQuestion) {
        this.crmSecurityQuestion = crmSecurityQuestion;
    }

    public String getCrmSecurityAnswer() {
        return crmSecurityAnswer;
    }

    public void setCrmSecurityAnswer(String crmSecurityAnswer) {
        this.crmSecurityAnswer = crmSecurityAnswer;
    }

    public String getCrmNomineeName() {
        return crmNomineeName;
    }

    public void setCrmNomineeName(String crmNomineeName) {
        this.crmNomineeName = crmNomineeName;
    }

    public String getCrmNomineeRelation() {
        return crmNomineeRelation;
    }

    public void setCrmNomineeRelation(String crmNomineeRelation) {
        this.crmNomineeRelation = crmNomineeRelation;
    }

    public Date getCrmNomineeDob() {
        return crmNomineeDob;
    }

    public void setCrmNomineeDob(Date crmNomineeDob) {
        this.crmNomineeDob = crmNomineeDob;
    }

    public String getCrmNomineeAddress() {
        return crmNomineeAddress;
    }

    public void setCrmNomineeAddress(String crmNomineeAddress) {
        this.crmNomineeAddress = crmNomineeAddress;
    }

    public Date getCrmExpiryDate() {
        return crmExpiryDate;
    }

    public void setCrmExpiryDate(Date crmExpiryDate) {
        this.crmExpiryDate = crmExpiryDate;
    }

    public int getCrmNumTxns() {
        return crmNumTxns;
    }

    public void setCrmNumTxns(int crmNumTxns) {
        this.crmNumTxns = crmNumTxns;
    }



    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public Integer getCrtAllowPinIndicator() {
        return crtAllowPinIndicator;
    }

    public void setCrtAllowPinIndicator(Integer crtAllowPinIndicator) {
        this.crtAllowPinIndicator = crtAllowPinIndicator;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getActivationPin() {
        return activationPin;
    }

    public void setActivationPin(String activationPin) {
        this.activationPin = activationPin;
    }

    public String getCrtName() {
        return crtName;
    }

    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    public Integer getCrtType() {
        return crtType;
    }

    public void setCrtType(Integer crtType) {
        this.crtType = crtType;
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

    public void setCrtType(int crtType) {
        this.crtType = crtType;
    }


    public Integer getCrmActivationType() {
        return crmActivationType;
    }

    public void setCrmActivationType(Integer crmActivationType) {
        this.crmActivationType = crmActivationType;
    }

    public Integer getCusIdType() {
        return cusIdType;
    }

    public void setCusIdType(Integer cusIdType) {
        this.cusIdType = cusIdType;
    }

    public String getCusIdNo() {
        return cusIdNo;
    }

    public void setCusIdNo(String cusIdNo) {
        this.cusIdNo = cusIdNo;
    }

    public List<CardNumberDetails> getMultipleCardNumber() {
        return multipleCardNumber;
    }

    public void setMultipleCardNumber(List<CardNumberDetails> multipleCardNumber) {
        this.multipleCardNumber = multipleCardNumber;
    }

    public Long getCrmTier() {
        return crmTier;
    }

    public void setCrmTier(Long crmTier) {
        this.crmTier = crmTier;
    }

    public Double getCrmPromoBalance() {
        return crmPromoBalance;
    }

    public void setCrmPromoBalance(Double crmPromoBalance) {
        this.crmPromoBalance = crmPromoBalance;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;

    }

    public Boolean getIsPinRequired() {
        return isPinRequired;
    }

    public void setIsPinRequired(Boolean isPinRequired) {
        this.isPinRequired = isPinRequired;
    }

    @Override
    public String toString() {
        return "CardMasterResource{" +
                "crmId=" + crmId +
                ", crmMerchantNo=" + crmMerchantNo +
                ", crmLocation=" + crmLocation +
                ", crmCardNo='" + crmCardNo + '\'' +
                ", crmType=" + crmType +
                ", crmCardStatus=" + crmCardStatus +
                ", crmCardBalance=" + crmCardBalance +
                ", crmLoyaltyId='" + crmLoyaltyId + '\'' +
                ", crmCardHolderName='" + crmCardHolderName + '\'' +
                ", crmProfession=" + crmProfession +
                ", crmMobile='" + crmMobile + '\'' +
                ", crmEmailId='" + crmEmailId + '\'' +
                ", crmDob=" + crmDob +
                ", crmAddress='" + crmAddress + '\'' +
                ", crmCity='" + crmCity + '\'' +
                ", crmPincode='" + crmPincode + '\'' +
                ", crmState=" + crmState +
                ", crmCountry=" + crmCountry +
                ", crmSecurityQuestion='" + crmSecurityQuestion + '\'' +
                ", crmSecurityAnswer='" + crmSecurityAnswer + '\'' +
                ", crmNomineeName='" + crmNomineeName + '\'' +
                ", crmNomineeRelation='" + crmNomineeRelation + '\'' +
                ", crmNomineeDob=" + crmNomineeDob +
                ", crmNomineeAddress='" + crmNomineeAddress + '\'' +
                ", crmExpiryDate=" + crmExpiryDate +
                ", crmNumTxns=" + crmNumTxns +
                ", crtAllowPinIndicator=" + crtAllowPinIndicator +
                ", cardName='" + cardName + '\'' +
                ", expired=" + expired +
                ", expiry='" + expiry + '\'' +
                ", activationPin='" + activationPin + '\'' +
                ", crtName='" + crtName + '\'' +
                ", crtType=" + crtType +
                ", crtFixedValue=" + crtFixedValue +
                ", crtMinTopupValue=" + crtMinTopupValue +
                ", crtMaxValue=" + crtMaxValue +
                ", crtExpiryOption=" + crtExpiryOption +
                ", crtExpiryDate=" + crtExpiryDate +
                ", crtExpiryDays=" + crtExpiryDays +
                ", crtMaxNumTxns=" + crtMaxNumTxns +
                ", crmActivationType=" + crmActivationType +
                ", cusIdType=" + cusIdType +
                ", cusIdNo='" + cusIdNo + '\'' +
                ", crmTier=" + crmTier +
                ", crmPromoBalance=" + crmPromoBalance +
                ", totalBalance=" + totalBalance +
                ", isPinRequired=" + isPinRequired +
                ", multipleCardNumber=" + multipleCardNumber +
                '}';
    }
}

package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.CardActivationType;
import com.inspirenetz.api.core.dictionary.CardMasterStatus;
import com.inspirenetz.api.core.dictionary.CardNumberDetails;
import com.inspirenetz.api.core.domain.CardMaster;
import com.inspirenetz.api.core.domain.CardType;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ameen on 3/6/16.
 */
public final class CardMasterBuilder {
    public List<CardNumberDetails> multipleCardNumber;
    private Long crmId;
    private Long crmMerchantNo;
    private String crmCardNo = "";
    private Long crmType = 0L;
    private Long crmLocation = 0L;
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
    private String crmPin= "";
    private Date crmIssuedDate;
    private Timestamp crmTopupDate;
    private Integer crmBalExpStatus;
    private Integer crmActivationType = CardActivationType.CARD_TYPE;
    private Double crmPromoBalance = 0.0;
    private CardType cardType;
    private Long crmTier;
    private String activationPin="";
    private Integer cusIdType;
    private String cusIdNo="";
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private CardMasterBuilder() {
    }

    public static CardMasterBuilder aCardMaster() {
        return new CardMasterBuilder();
    }

    public CardMasterBuilder withCrmId(Long crmId) {
        this.crmId = crmId;
        return this;
    }

    public CardMasterBuilder withCrmMerchantNo(Long crmMerchantNo) {
        this.crmMerchantNo = crmMerchantNo;
        return this;
    }

    public CardMasterBuilder withCrmCardNo(String crmCardNo) {
        this.crmCardNo = crmCardNo;
        return this;
    }

    public CardMasterBuilder withCrmType(Long crmType) {
        this.crmType = crmType;
        return this;
    }

    public CardMasterBuilder withCrmLocation(Long crmLocation) {
        this.crmLocation = crmLocation;
        return this;
    }

    public CardMasterBuilder withCrmCardStatus(Integer crmCardStatus) {
        this.crmCardStatus = crmCardStatus;
        return this;
    }

    public CardMasterBuilder withCrmCardBalance(Double crmCardBalance) {
        this.crmCardBalance = crmCardBalance;
        return this;
    }

    public CardMasterBuilder withCrmLoyaltyId(String crmLoyaltyId) {
        this.crmLoyaltyId = crmLoyaltyId;
        return this;
    }

    public CardMasterBuilder withCrmCardHolderName(String crmCardHolderName) {
        this.crmCardHolderName = crmCardHolderName;
        return this;
    }

    public CardMasterBuilder withCrmProfession(Integer crmProfession) {
        this.crmProfession = crmProfession;
        return this;
    }

    public CardMasterBuilder withCrmMobile(String crmMobile) {
        this.crmMobile = crmMobile;
        return this;
    }

    public CardMasterBuilder withCrmEmailId(String crmEmailId) {
        this.crmEmailId = crmEmailId;
        return this;
    }

    public CardMasterBuilder withCrmDob(Date crmDob) {
        this.crmDob = crmDob;
        return this;
    }

    public CardMasterBuilder withCrmAddress(String crmAddress) {
        this.crmAddress = crmAddress;
        return this;
    }

    public CardMasterBuilder withCrmCity(String crmCity) {
        this.crmCity = crmCity;
        return this;
    }

    public CardMasterBuilder withCrmPincode(String crmPincode) {
        this.crmPincode = crmPincode;
        return this;
    }

    public CardMasterBuilder withCrmState(Integer crmState) {
        this.crmState = crmState;
        return this;
    }

    public CardMasterBuilder withCrmCountry(Integer crmCountry) {
        this.crmCountry = crmCountry;
        return this;
    }

    public CardMasterBuilder withCrmSecurityQuestion(String crmSecurityQuestion) {
        this.crmSecurityQuestion = crmSecurityQuestion;
        return this;
    }

    public CardMasterBuilder withCrmSecurityAnswer(String crmSecurityAnswer) {
        this.crmSecurityAnswer = crmSecurityAnswer;
        return this;
    }

    public CardMasterBuilder withCrmNomineeName(String crmNomineeName) {
        this.crmNomineeName = crmNomineeName;
        return this;
    }

    public CardMasterBuilder withCrmNomineeRelation(String crmNomineeRelation) {
        this.crmNomineeRelation = crmNomineeRelation;
        return this;
    }

    public CardMasterBuilder withCrmNomineeDob(Date crmNomineeDob) {
        this.crmNomineeDob = crmNomineeDob;
        return this;
    }

    public CardMasterBuilder withCrmNomineeAddress(String crmNomineeAddress) {
        this.crmNomineeAddress = crmNomineeAddress;
        return this;
    }

    public CardMasterBuilder withCrmExpiryDate(Date crmExpiryDate) {
        this.crmExpiryDate = crmExpiryDate;
        return this;
    }

    public CardMasterBuilder withCrmNumTxns(int crmNumTxns) {
        this.crmNumTxns = crmNumTxns;
        return this;
    }

    public CardMasterBuilder withCrmPin(String crmPin) {
        this.crmPin = crmPin;
        return this;
    }

    public CardMasterBuilder withCrmIssuedDate(Date crmIssuedDate) {
        this.crmIssuedDate = crmIssuedDate;
        return this;
    }

    public CardMasterBuilder withCrmTopupDate(Timestamp crmTopupDate) {
        this.crmTopupDate = crmTopupDate;
        return this;
    }

    public CardMasterBuilder withCrmBalExpStatus(Integer crmBalExpStatus) {
        this.crmBalExpStatus = crmBalExpStatus;
        return this;
    }

    public CardMasterBuilder withCrmActivationType(Integer crmActivationType) {
        this.crmActivationType = crmActivationType;
        return this;
    }

    public CardMasterBuilder withCrmPromoBalance(Double crmPromoBalance) {
        this.crmPromoBalance = crmPromoBalance;
        return this;
    }

    public CardMasterBuilder withCardType(CardType cardType) {
        this.cardType = cardType;
        return this;
    }

    public CardMasterBuilder withCrmTier(Long crmTier) {
        this.crmTier = crmTier;
        return this;
    }

    public CardMasterBuilder withMultipleCardNumber(List<CardNumberDetails> multipleCardNumber) {
        this.multipleCardNumber = multipleCardNumber;
        return this;
    }

    public CardMasterBuilder withActivationPin(String activationPin) {
        this.activationPin = activationPin;
        return this;
    }

    public CardMasterBuilder withCusIdType(Integer cusIdType) {
        this.cusIdType = cusIdType;
        return this;
    }

    public CardMasterBuilder withCusIdNo(String cusIdNo) {
        this.cusIdNo = cusIdNo;
        return this;
    }

    public CardMasterBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CardMasterBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CardMasterBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CardMasterBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CardMaster build() {
        CardMaster cardMaster = new CardMaster();
        cardMaster.setCrmId(crmId);
        cardMaster.setCrmMerchantNo(crmMerchantNo);
        cardMaster.setCrmCardNo(crmCardNo);
        cardMaster.setCrmType(crmType);
        cardMaster.setCrmLocation(crmLocation);
        cardMaster.setCrmCardStatus(crmCardStatus);
        cardMaster.setCrmCardBalance(crmCardBalance);
        cardMaster.setCrmLoyaltyId(crmLoyaltyId);
        cardMaster.setCrmCardHolderName(crmCardHolderName);
        cardMaster.setCrmProfession(crmProfession);
        cardMaster.setCrmMobile(crmMobile);
        cardMaster.setCrmEmailId(crmEmailId);
        cardMaster.setCrmDob(crmDob);
        cardMaster.setCrmAddress(crmAddress);
        cardMaster.setCrmCity(crmCity);
        cardMaster.setCrmPincode(crmPincode);
        cardMaster.setCrmState(crmState);
        cardMaster.setCrmCountry(crmCountry);
        cardMaster.setCrmSecurityQuestion(crmSecurityQuestion);
        cardMaster.setCrmSecurityAnswer(crmSecurityAnswer);
        cardMaster.setCrmNomineeName(crmNomineeName);
        cardMaster.setCrmNomineeRelation(crmNomineeRelation);
        cardMaster.setCrmNomineeDob(crmNomineeDob);
        cardMaster.setCrmNomineeAddress(crmNomineeAddress);
        cardMaster.setCrmExpiryDate(crmExpiryDate);
        cardMaster.setCrmNumTxns(crmNumTxns);
        cardMaster.setCrmPin(crmPin);
        cardMaster.setCrmIssuedDate(crmIssuedDate);
        cardMaster.setCrmTopupDate(crmTopupDate);
        cardMaster.setCrmBalExpStatus(crmBalExpStatus);
        cardMaster.setCrmActivationType(crmActivationType);
        cardMaster.setCrmPromoBalance(crmPromoBalance);
        cardMaster.setCardType(cardType);
        cardMaster.setCrmTier(crmTier);
        cardMaster.setMultipleCardNumber(multipleCardNumber);
        cardMaster.setActivationPin(activationPin);
        cardMaster.setCusIdType(cusIdType);
        cardMaster.setCusIdNo(cusIdNo);
        cardMaster.setCreatedAt(createdAt);
        cardMaster.setCreatedBy(createdBy);
        cardMaster.setUpdatedAt(updatedAt);
        cardMaster.setUpdatedBy(updatedBy);
        return cardMaster;
    }
}

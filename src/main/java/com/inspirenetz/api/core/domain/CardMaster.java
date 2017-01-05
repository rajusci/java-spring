package com.inspirenetz.api.core.domain;


import com.inspirenetz.api.config.IntegrationConfig;
import com.inspirenetz.api.core.dictionary.CardActivationType;
import com.inspirenetz.api.core.dictionary.CardMasterStatus;
import com.inspirenetz.api.core.dictionary.CardNumberDetails;
import com.inspirenetz.api.util.DBUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "CARD_MASTER")
public class CardMaster extends AuditedEntity {


    @Id
    @Column(name = "CRM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long crmId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_MERCHANT_NO", updatable = false)
    private Long crmMerchantNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_CARD_NO")
    @NotNull(message = "{cardmaster.crmcardno.notnull}")
    @NotEmpty(message = "{cardmaster.crmcardno.notempty}")
    @Size(min=3,max=20,message = "{cardmaster.crmcardno.size}")
    private String crmCardNo = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_TYPE")
    private Long crmType = 0L;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_LOCATION")
    private Long crmLocation = 0L;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_CARD_STATUS")
    private Integer crmCardStatus = CardMasterStatus.NEW;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_CARD_BALANCE")
    private Double crmCardBalance = 0.0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_LOYALTY_ID")
    private String crmLoyaltyId = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_CARD_HOLDER_NAME")
    @NotNull(message = "{cardmaster.crmcardholdername.notnull}")
    @NotEmpty(message = "{cardmaster.crmcardholdername.notempty}")
    @Size(min=3,max=100,message = "{cardmaster.crmcardholdername.size}")
    private String crmCardHolderName ="";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_PROFESSION")
    private Integer crmProfession = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_MOBILE")
    @Size(min=0,max=16,message = "{cardmaster.crmobile.size}")
    private String crmMobile = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_EMAIL_ID")
    @Email(message = "{cardmaster.crmemailid.email}")
    private String crmEmailId ="";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_DOB")
    private Date crmDob;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_ADDRESS")
    @Size(max=200,message ="{cardmaster.crmaddress.size}")
    private String crmAddress = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_CITY")
    @Size(max=100,message ="{cardmaster.crmcity.size}")
    private String crmCity = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_PINCODE")
    @Size(max=20,message ="{cardmaster.crmpincode.size}")
    private String crmPincode ="";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_STATE")
    private Integer crmState = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_COUNTRY")
    private Integer crmCountry = 356;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_SECURITY_QUESTION")
    @Size(max=200,message ="{cardmaster.crmsecurityquestion.size}")
    private String crmSecurityQuestion ="";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_SECURITY_ANSWER")
    @Size(max=100,message ="{cardmaster.securityanswer.size}")
    private String crmSecurityAnswer ="";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_NOMINEE_NAME")
    @Size(max=50,message ="{cardmaster.crmnomineename.size}")
    private String crmNomineeName ="";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_NOMINEE_RELATION")
    @Size(max=50,message ="{cardmaster.crmnomineerelation.size}")
    private String crmNomineeRelation="";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_NOMINEE_DOB")
    private Date crmNomineeDob;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_NOMINEE_ADDRESS")
    @Size(max=200,message ="{cardmaster.crmnomineeaddress.size}")
    private String crmNomineeAddress = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_EXPIRY_DATE")
    private Date crmExpiryDate ;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_NUM_TXNS")
    private int crmNumTxns = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_PIN")
    private String crmPin= "";

    @Column(name = "CRM_ISSUED_DATE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Date crmIssuedDate;

    @Column(name = "CRM_TOPUP_DATE")
    @Basic(fetch = FetchType.EAGER)
    private Timestamp crmTopupDate;

    @Column(name = "CRM_BAL_EXP_STATUS")
    @Basic(fetch = FetchType.EAGER)
    private Integer crmBalExpStatus;

    @Column(name = "CRM_ACTIVATION_TYPE")
    @Basic(fetch = FetchType.EAGER)
    private Integer crmActivationType = CardActivationType.CARD_TYPE;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CRM_PROMO_BALANCE")
    private Double crmPromoBalance = 0.0;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CRM_TYPE",insertable = false,updatable = false)
    private CardType cardType;

    @Column(name = "CRM_TIER",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long crmTier;

    @Transient
    public List<CardNumberDetails> multipleCardNumber;

    @Transient
    private String activationPin="";

    @Transient
    private Integer cusIdType;

    @Transient
    private String cusIdNo="";




    @PrePersist
    @PreUpdate
    private void prePopulateFields() {

        // Check if the expirydate field is null, if it is, then we
        // need to prepopulate it with the largest date
        if ( crmExpiryDate == null ) {

            crmExpiryDate = DBUtils.covertToSqlDate("9999-12-31");

        }


        // If the crmPromoBalance is null , then set it to 0
        if ( crmPromoBalance == null ) {

            crmPromoBalance =0.0;

        }

    }


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

    public Long getCrmLocation() {
        return crmLocation;
    }

    public void setCrmLocation(Long crmLocation) {
        this.crmLocation = crmLocation;
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

    public String getCrmPin() {
        return crmPin;
    }

    public void setCrmPin(String crmPin) {
        this.crmPin = crmPin;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Date getCrmIssuedDate() {
        return crmIssuedDate;
    }

    public void setCrmIssuedDate(Date crmIssuedDate) {
        this.crmIssuedDate = crmIssuedDate;
    }

    public Timestamp getCrmTopupDate() {
        return crmTopupDate;
    }

    public void setCrmTopupDate(Timestamp crmTopupDate) {
        this.crmTopupDate = crmTopupDate;
    }

    public Integer getCrmBalExpStatus() {
        return crmBalExpStatus;
    }

    public void setCrmBalExpStatus(Integer crmBalExpStatus) {
        this.crmBalExpStatus = crmBalExpStatus;
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



    public Integer getCrmActivationType() {
        return crmActivationType;
    }

    public void setCrmActivationType(Integer crmActivationType) {
        this.crmActivationType = crmActivationType;
    }

    public List<CardNumberDetails> getMultipleCardNumber() {
        return multipleCardNumber;
    }

    public void setMultipleCardNumber(List<CardNumberDetails> multipleCardNumber) {
        this.multipleCardNumber = multipleCardNumber;
    }

    public String getActivationPin() {
        return activationPin;
    }

    public void setActivationPin(String activationPin) {
        this.activationPin = activationPin;
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

    @Override
    public String toString() {
        return "CardMaster{" +
                "crmId=" + crmId +
                ", crmMerchantNo=" + crmMerchantNo +
                ", crmCardNo='" + crmCardNo + '\'' +
                ", crmType=" + crmType +
                ", crmLocation=" + crmLocation +
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
                ", crmPin='" + crmPin + '\'' +
                ", crmIssuedDate=" + crmIssuedDate +
                ", crmTopupDate=" + crmTopupDate +
                ", crmBalExpStatus=" + crmBalExpStatus +
                ", crmActivationType=" + crmActivationType +
                ", crmPromoBalance=" + crmPromoBalance +
                ", cardType=" + cardType +
                ", crmTier=" + crmTier +
                ", multipleCardNumber=" + multipleCardNumber +
                ", activationPin='" + activationPin + '\'' +
                ", cusIdType=" + cusIdType +
                ", cusIdNo='" + cusIdNo + '\'' +
                '}';
    }
}

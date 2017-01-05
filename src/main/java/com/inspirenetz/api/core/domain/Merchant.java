package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Entity
@Table(name="MERCHANTS")
public class Merchant extends AuditedEntity {

    @Column(name = "MER_MERCHANT_NO",nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long merMerchantNo;

    @Column(name = "MER_MERCHANT_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{merchant.mermerchantname.notempty}")
    @NotNull(message = "{merchant.mermerchantname.notnull}")
    @Size(max=50 , message = "{merchant.mermerchantname.size}")
    private String merMerchantName;

    @Column(name = "MER_URL_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=50 , message = "{merchant.merurlname.size}")
    private String merUrlName;

    @Column(name = "MER_EXCLUSIVE_IND",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int merExclusiveInd = IndicatorStatus.NO;

    @Column(name = "MER_ADDRESS1",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{merchant.meraddress1.notempty}")
    @NotNull(message = "{merchant.meraddress1.notnull}")
    @Size(max=50 , message = "{merchant.meraddress1.size}")
    private String merAddress1 = "";

    @Column(name = "MER_ADDRESS2",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{merchant.meraddress2.notempty}")
    @NotNull(message = "{merchant.meraddress2.notnull}")
    @Size(max=50 , message = "{merchant.meraddress2.size}")
    private String merAddress2 = "";

    @Column(name = "MER_ADDRESS3",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=50 , message = "{merchant.meraddress2.size}")
    private String merAddress3 = "";

    @Column(name = "MER_CITY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{merchant.mercity.notempty}")
    @NotNull(message = "{merchant.mercity.notnull}")
    @Size(max=30 , message = "{merchant.mercity.size}")
    private String merCity;

    @Column(name = "MER_STATE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{merchant.merstate.notempty}")
    @NotNull(message = "{merchant.merstate.notnull}")
    @Size(max=30 , message = "{merchant.merstate.size}")
    private String merState;

    @Column(name = "MER_COUNTRY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int merCountry = 356;

    @Column(name = "MER_POST_CODE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{merchant.merpostcode.notempty}")
    @NotNull(message = "{merchant.merpostcode.notnull}")
    @Size(max=10 , message = "{merchant.merpostcode.size}")
    private String merPostCode;

    @Column(name = "MER_CONTACT_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{merchant.mercontactname.notempty}")
    @NotNull(message = "{merchant.mercontactname.notnull}")
    @Size(max=50 , message = "{merchant.mercontactname.size}")
    private String merContactName;

    @Column(name = "MER_CONTACT_EMAIL",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{merchant.mercontactemail.notempty}")
    @NotNull(message = "{merchant.mercontactemail.notnull}")
    @Size(max=200 , message = "{merchant.mercontactemail.size}")
    @Email( message = "{merchant.mercontactemail.email}" )
    private String merContactEmail;

    @Column(name = "MER_PHONE_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{merchant.merphoneno.notempty}")
    @NotNull(message = "{merchant.merphoneno.notnull}")
    @Size(max=12 , message = "{merchant.merphoneno.size}")
    private String merPhoneNo;

    @Column(name = "MER_EMAIL",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{merchant.meremail.notempty}")
    @NotNull(message = "{merchant.meremail.notnull}")
    @Size(max=200 , message = "{merchant.meremail.size}")
    @Email( message = "{merchant.meremail.email}" )
    private String merEmail;

    @Column(name = "MER_ACTIVATION_DATE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date merActivationDate;

    @Column(name = "MER_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int merStatus = MerchantStatus.ACCOUNT_ACTIVE;

    @Column(name = "MER_SUBSCRIPTION_PLAN",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int merSubscriptionPlan = 1;

    @Column(name = "MER_LOYALTY_ID_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int merLoyaltyIdType = MerchantLoyaltyIdType.GENERATED;

    @Column(name = "MER_LOYALTY_ID_FROM",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String merLoyaltyIdFrom ="0";

    @Column(name = "MER_LOYALTY_ID_TO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String merLoyaltyIdTo = "0";

    @Column(name = "MER_MAX_CUSTOMERS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int merMaxCustomers = 2000;

    @Column(name = "MER_MAX_USERS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int merMaxUsers = 4;

    @Column(name = "MER_SIGNUP_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String merSignupType = "";

    @Column(name = "MER_AUTO_SIGNUP_ENABLE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String merAutoSignupEnable = IndicatorStatusChar.YES;

    @Column(name = "MER_MEMBERSHIP_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=200 , message = "{merchant.mermembershipname.size}")
    private String merMembershipName;

    @Column(name = "MER_PAYMENT_MODES",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String merPaymentModes = "";

    @Column(name = "MER_CUST_ID_TYPES",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String merCustIdTypes = "";

    @Column(name = "MER_MERCHANT_IMAGE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long merMerchantImage = ImagePrimaryId.PRIMARY_MERCHANT_LOGO;

    @Column(name = "MER_COVER_IMAGE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long merCoverImage = ImagePrimaryId.PRIMARY_MERCHANT_COVER_IMAGE;

    @Column(name = "MER_SIGNUP_REWARD_ENABLED_IND",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int merSignupRewardEnabledInd = IndicatorStatus.NO;

    @Column(name = "MER_SIGNUP_REWARD_CURRENCY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int merSignupRewardCurrency =0;

    @Column(name = "MER_SIGNUP_REWARD_POINTS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double merSignupRewardPoints = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MER_MERCHANT_IMAGE",insertable = false,updatable = false)
    private Image imgLogo;

    @Column(name = "MER_PIN_ENABLED",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer merPinEnabled = PinSecurityLevel.ENABLED;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="MEL_MERCHANT_NO")
    public Set<MerchantLocation> merchantLocationSet;
    @Transient
    private Image imgCoverImage;




    public Long getMerMerchantNo() {
        return merMerchantNo;
    }

    public void setMerMerchantNo(Long merMerchantNo) {
        this.merMerchantNo = merMerchantNo;
    }

    public String getMerMerchantName() {
        return merMerchantName;
    }

    public void setMerMerchantName(String merMerchantName) {
        this.merMerchantName = merMerchantName;
    }

    public String getMerUrlName() {
        return merUrlName;
    }

    public void setMerUrlName(String merUrlName) {
        this.merUrlName = merUrlName;
    }

    public int getMerExclusiveInd() {
        return merExclusiveInd;
    }

    public void setMerExclusiveInd(int merExclusiveInd) {
        this.merExclusiveInd = merExclusiveInd;
    }

    public String getMerAddress1() {
        return merAddress1;
    }

    public void setMerAddress1(String merAddress1) {
        this.merAddress1 = merAddress1;
    }

    public String getMerAddress2() {
        return merAddress2;
    }

    public void setMerAddress2(String merAddress2) {
        this.merAddress2 = merAddress2;
    }

    public String getMerAddress3() {
        return merAddress3;
    }

    public void setMerAddress3(String merAddress3) {
        this.merAddress3 = merAddress3;
    }

    public String getMerCity() {
        return merCity;
    }

    public void setMerCity(String merCity) {
        this.merCity = merCity;
    }

    public String getMerState() {
        return merState;
    }

    public void setMerState(String merState) {
        this.merState = merState;
    }

    public int getMerCountry() {
        return merCountry;
    }

    public void setMerCountry(int merCountry) {
        this.merCountry = merCountry;
    }

    public String getMerPostCode() {
        return merPostCode;
    }

    public void setMerPostCode(String merPostCode) {
        this.merPostCode = merPostCode;
    }

    public String getMerContactName() {
        return merContactName;
    }

    public void setMerContactName(String merContactName) {
        this.merContactName = merContactName;
    }

    public String getMerContactEmail() {
        return merContactEmail;
    }

    public void setMerContactEmail(String merContactEmail) {
        this.merContactEmail = merContactEmail;
    }

    public String getMerPhoneNo() {
        return merPhoneNo;
    }

    public void setMerPhoneNo(String merPhoneNo) {
        this.merPhoneNo = merPhoneNo;
    }

    public String getMerEmail() {
        return merEmail;
    }

    public void setMerEmail(String merEmail) {
        this.merEmail = merEmail;
    }

    public Date getMerActivationDate() {
        return merActivationDate;
    }

    public void setMerActivationDate(Date merActivationDate) {
        this.merActivationDate = merActivationDate;
    }

    public int getMerStatus() {
        return merStatus;
    }

    public void setMerStatus(int merStatus) {
        this.merStatus = merStatus;
    }

    public int getMerSubscriptionPlan() {
        return merSubscriptionPlan;
    }

    public void setMerSubscriptionPlan(int merSubscriptionPlan) {
        this.merSubscriptionPlan = merSubscriptionPlan;
    }

    public int getMerLoyaltyIdType() {
        return merLoyaltyIdType;
    }

    public void setMerLoyaltyIdType(int merLoyaltyIdType) {
        this.merLoyaltyIdType = merLoyaltyIdType;
    }

    public String getMerLoyaltyIdFrom() {
        return merLoyaltyIdFrom;
    }

    public void setMerLoyaltyIdFrom(String merLoyaltyIdFrom) {
        this.merLoyaltyIdFrom = merLoyaltyIdFrom;
    }

    public String getMerLoyaltyIdTo() {
        return merLoyaltyIdTo;
    }

    public void setMerLoyaltyIdTo(String merLoyaltyIdTo) {
        this.merLoyaltyIdTo = merLoyaltyIdTo;
    }

    public int getMerMaxCustomers() {
        return merMaxCustomers;
    }

    public void setMerMaxCustomers(int merMaxCustomers) {
        this.merMaxCustomers = merMaxCustomers;
    }

    public int getMerMaxUsers() {
        return merMaxUsers;
    }

    public void setMerMaxUsers(int merMaxUsers) {
        this.merMaxUsers = merMaxUsers;
    }

    public String getMerSignupType() {
        return merSignupType;
    }

    public void setMerSignupType(String merSignupType) {
        this.merSignupType = merSignupType;
    }

    public String getMerAutoSignupEnable() {
        return merAutoSignupEnable;
    }

    public void setMerAutoSignupEnable(String merAutoSignupEnable) {
        this.merAutoSignupEnable = merAutoSignupEnable;
    }

    public String getMerMembershipName() {
        return merMembershipName;
    }

    public void setMerMembershipName(String merMembershipName) {
        this.merMembershipName = merMembershipName;
    }

    public String getMerPaymentModes() {
        return merPaymentModes;
    }

    public void setMerPaymentModes(String merPaymentModes) {
        this.merPaymentModes = merPaymentModes;
    }

    public String getMerCustIdTypes() {
        return merCustIdTypes;
    }

    public void setMerCustIdTypes(String merCustIdTypes) {
        this.merCustIdTypes = merCustIdTypes;
    }

    public int getMerSignupRewardEnabledInd() {
        return merSignupRewardEnabledInd;
    }

    public void setMerSignupRewardEnabledInd(int merSignupRewardEnabledInd) {
        this.merSignupRewardEnabledInd = merSignupRewardEnabledInd;
    }

    public int getMerSignupRewardCurrency() {
        return merSignupRewardCurrency;
    }

    public void setMerSignupRewardCurrency(int merSignupRewardCurrency) {
        this.merSignupRewardCurrency = merSignupRewardCurrency;
    }

    public Long getMerMerchantImage() {
        return merMerchantImage;
    }

    public void setMerMerchantImage(Long merMerchantImage) {
        this.merMerchantImage = merMerchantImage;
    }

    public Long getMerCoverImage() {
        return merCoverImage;
    }

    public void setMerCoverImage(Long merCoverImage) {
        this.merCoverImage = merCoverImage;
    }

    public double getMerSignupRewardPoints() {
        return merSignupRewardPoints;
    }

    public void setMerSignupRewardPoints(double merSignupRewardPoints) {
        this.merSignupRewardPoints = merSignupRewardPoints;
    }

    public Image getImgLogo() {
        return imgLogo;
    }

    public void setImgLogo(Image imgLogo) {
        this.imgLogo = imgLogo;
    }

    public Image getImgCoverImage() {
        return imgCoverImage;
    }

    public void setImgCoverImage(Image imgCoverImage) {
        this.imgCoverImage = imgCoverImage;
    }

    public Set<MerchantLocation> getMerchantLocationSet() {
        return merchantLocationSet;
    }

    public void setMerchantLocationSet(Set<MerchantLocation> merchantLocationSet) {
        this.merchantLocationSet = merchantLocationSet;
    }

    public Integer getMerPinEnabled() {
        return merPinEnabled;
    }

    public void setMerPinEnabled(Integer merPinEnabled) {
        this.merPinEnabled = merPinEnabled;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "merMerchantNo=" + merMerchantNo +
                ", merMerchantName='" + merMerchantName + '\'' +
                ", merUrlName='" + merUrlName + '\'' +
                ", merExclusiveInd=" + merExclusiveInd +
                ", merAddress1='" + merAddress1 + '\'' +
                ", merAddress2='" + merAddress2 + '\'' +
                ", merAddress3='" + merAddress3 + '\'' +
                ", merCity='" + merCity + '\'' +
                ", merState='" + merState + '\'' +
                ", merCountry=" + merCountry +
                ", merPostCode='" + merPostCode + '\'' +
                ", merContactName='" + merContactName + '\'' +
                ", merContactEmail='" + merContactEmail + '\'' +
                ", merPhoneNo='" + merPhoneNo + '\'' +
                ", merEmail='" + merEmail + '\'' +
                ", merActivationDate=" + merActivationDate +
                ", merStatus=" + merStatus +
                ", merSubscriptionPlan=" + merSubscriptionPlan +
                ", merLoyaltyIdType=" + merLoyaltyIdType +
                ", merLoyaltyIdFrom='" + merLoyaltyIdFrom + '\'' +
                ", merLoyaltyIdTo='" + merLoyaltyIdTo + '\'' +
                ", merMaxCustomers=" + merMaxCustomers +
                ", merMaxUsers=" + merMaxUsers +
                ", merSignupType='" + merSignupType + '\'' +
                ", merAutoSignupEnable='" + merAutoSignupEnable + '\'' +
                ", merMembershipName='" + merMembershipName + '\'' +
                ", merPaymentModes='" + merPaymentModes + '\'' +
                ", merCustIdTypes='" + merCustIdTypes + '\'' +
                ", merMerchantImage=" + merMerchantImage +
                ", merCoverImage=" + merCoverImage +
                ", merSignupRewardEnabledInd=" + merSignupRewardEnabledInd +
                ", merSignupRewardCurrency=" + merSignupRewardCurrency +
                ", merSignupRewardPoints=" + merSignupRewardPoints +
                ", imgLogo=" + imgLogo +
                ", merPinEnabled=" + merPinEnabled +
                ", merchantLocationSet=" + merchantLocationSet +
                ", imgCoverImage=" + imgCoverImage +
                '}';
    }
}
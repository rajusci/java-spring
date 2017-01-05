package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.CustomerStatus;
import com.inspirenetz.api.core.dictionary.CustomerType;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by sandheepgr on 16/2/14.
 */
@Entity
@Table(name = "CUSTOMERS")
public class Customer  extends  AuditedEntity {

    @Column(name = "CUS_CUSTOMER_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cusCustomerNo;

    @Column(name = "CUS_MERCHANT_NO",nullable = false , updatable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long cusMerchantNo =0L;

    @Column(name = "CUS_USER_NO",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long cusUserNo = 0L;

    @Column(name = "CUS_ID_TYPE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer cusIdType = 0;

    @Column(name = "CUS_ID_NO",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String cusIdNo = "";

    @Column(name = "CUS_EMAIL",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Email(message = "{customer.cusemail.email}")
    private String cusEmail = "";

    @Column(name = "CUS_MOBILE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String cusMobile  = "";

    @Column(name = "CUS_FNAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String cusFName = "";

    @Column(name = "CUS_LNAME",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String cusLName = "";


    @Column(name = "CUS_LOYALTY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{customer.cusloyaltyid.notempty}")
    @NotNull(message = "{customer.cusloyaltyid.notnull}")
    @Size(min=0,max=20,message = "{customer.cusloyaltyid.size}")
    private String cusLoyaltyId = "";

    @Column(name = "CUS_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int cusStatus = CustomerStatus.ACTIVE;

    @Column(name = "CUS_UNIQUE_LOYALTY_IDENTIFIER",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String cusUniqueLoyaltyIdentifier;

    @Column(name = "CUS_REGISTER_TIMESTAMP",nullable = false,updatable = false)
    @Basic(fetch = FetchType.EAGER)
    private Timestamp cusRegisterTimestamp;

    @Column(name= "CUS_TIER",nullable = true)
    @Basic
    private Long cusTier;

    @Column(name= "CUS_TIER_LAST_EVALUATED",nullable = true)
    @Basic
    private Date cusTierLastEvaluated ;

    @Column(name= "CUS_TYPE",nullable = false)
    @Basic
    private Integer cusType = CustomerType.SUBSCRIBER;


    @Column(name = "CUS_MERCHANT_USER_REGISTERED",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long cusMerchantUserRegistered = 0L;

    @Column(name = "CUS_LOCATION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long cusLocation = 0L ;

    @Column(name = "CUS_RECEIVE_NOTIFICATIONS",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer cusReceiveNotifications = IndicatorStatus.YES ;

    @Column(name = "CUS_IS_WHITE_LISTED",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer cusIsWhiteListed = IndicatorStatus.NO;

    @Column(name = "CUS_REGISTER_STATUS",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer cusRegisterStatus = IndicatorStatus.NO;


    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="CSP_CUSTOMER_NO",referencedColumnName="CUS_CUSTOMER_NO")
    public Set<CustomerProfile> cspProfileRef;



    @Column(name = "CUS_MOBILE_COUNTRY_CODE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String cusMobileCountryCode  = "";

    @Column(name = "CUS_MOBILE_ALTERNATE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String cusMobileAlternate  = "";

    @Transient
    private boolean isPrimary = false;

    @Transient
    private Tier tier;

    @Transient
    private CustomerProfile customerProfile;

    @Transient
    private Merchant merchant;

    @Transient
    private String referralCode="";

    /**
     * Function to update the unique loyalty identifier for the customer
     * when the customer information updated/saved
     *
     * The loyalty identifier is the merchantno+#+loyaltyId
     */
    private void updateCusUniqueLoyaltyIdentifier() {

        // Set the loyatly identified as the merchantantno+#+loyaltyId
      cusUniqueLoyaltyIdentifier = cusMerchantNo.toString() + "#" + cusLoyaltyId;

    }


    /**
     * Function to set the identity fields to empty is they are null of spaces
     */
    private void clearIdentityFieldsFromNull() {

        // Check if the cusLoyaltyId field is null and set the data
        if ( cusLoyaltyId == null ) {

            cusLoyaltyId = "";

        } else {

            cusLoyaltyId = cusLoyaltyId.trim();
        }


        // Check if the cusEmail is nul land set the data
        if ( cusEmail == null ) {

            cusEmail = "";

        } else {

            cusEmail = cusEmail.trim().toLowerCase();

        }


        // Check if the cusMobile is null and set the data as empty string
        if ( cusMobile == null ) {

            cusMobile = "";

        } else {

            cusMobile = cusMobile.trim();
        }


    }


    @PrePersist
    private void populateInsertFields() {

        // Get the current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Set the registerTimestamp to current time
        cusRegisterTimestamp = new Timestamp(timestamp.getTime());

        // Check if the last evaluated tier is not null
        if( cusTierLastEvaluated == null ) {

            // Set it as the current date
            cusTierLastEvaluated = new Date(timestamp.getTime());

        }


        // clear the identity fields from null
        clearIdentityFieldsFromNull();

        // Update the cusUniqueLoyaltyIdentified
        updateCusUniqueLoyaltyIdentifier();

    }


    @PreUpdate
    private void populateUpdateFields() {

        // clear the identity fields from null
        clearIdentityFieldsFromNull();

        // Update the unique loyalty identifier
        updateCusUniqueLoyaltyIdentifier();

    }


    public Long getCusCustomerNo() {
        return cusCustomerNo;
    }

    public void setCusCustomerNo(Long cusCustomerNo) {
        this.cusCustomerNo = cusCustomerNo;
    }

    public Long getCusMerchantNo() {
        return cusMerchantNo;
    }

    public void setCusMerchantNo(Long cusMerchantNo) {
        this.cusMerchantNo = cusMerchantNo;
    }

    public Long getCusUserNo() {
        return cusUserNo;
    }

    public void setCusUserNo(Long cusUserNo) {
        this.cusUserNo = cusUserNo;
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

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusMobile() {
        return cusMobile;
    }

    public void setCusMobile(String cusMobile) {
        this.cusMobile = cusMobile;
    }

    public String getCusFName() {
        return cusFName;
    }

    public void setCusFName(String cusFName) {
        this.cusFName = cusFName;
    }

    public String getCusLName() {
        return cusLName;
    }

    public void setCusLName(String cusLName) {
        this.cusLName = cusLName;
    }

    public String getCusLoyaltyId() {
        return cusLoyaltyId;
    }

    public void setCusLoyaltyId(String cusLoyaltyId) {
        this.cusLoyaltyId = cusLoyaltyId;
    }

    public int getCusStatus() {
        return cusStatus;
    }

    public void setCusStatus(int cusStatus) {
        this.cusStatus = cusStatus;
    }

    public String getCusUniqueLoyaltyIdentifier() {
        return cusUniqueLoyaltyIdentifier;
    }

    public void setCusUniqueLoyaltyIdentifier(String cusUniqueLoyaltyIdentifier) {
        this.cusUniqueLoyaltyIdentifier = cusUniqueLoyaltyIdentifier;
    }

    public Timestamp getCusRegisterTimestamp() {
        return cusRegisterTimestamp;
    }

    public void setCusRegisterTimestamp(Timestamp cusRegisterTimestamp) {
        this.cusRegisterTimestamp = cusRegisterTimestamp;
    }

    public Long getCusTier() {
        return cusTier;
    }

    public void setCusTier(Long cusTier) {
        this.cusTier = cusTier;
    }

    public Date getCusTierLastEvaluated() {
        return cusTierLastEvaluated;
    }

    public void setCusTierLastEvaluated(Date cusTierLastEvaluated) {
        this.cusTierLastEvaluated = cusTierLastEvaluated;
    }

    public Integer getCusType() {
        return cusType;
    }

    public void setCusType(Integer cusType) {
        this.cusType = cusType;
    }

    public Long getCusMerchantUserRegistered() {
        return cusMerchantUserRegistered;
    }

    public void setCusMerchantUserRegistered(Long cusMerchantUserRegistered) {
        this.cusMerchantUserRegistered = cusMerchantUserRegistered;
    }

    public Long getCusLocation() {
        return cusLocation;
    }

    public void setCusLocation(Long cusLocation) {
        this.cusLocation = cusLocation;
    }

    public Integer getCusReceiveNotifications() {
        return cusReceiveNotifications;
    }

    public void setCusReceiveNotifications(Integer cusReceiveNotifications) {
        this.cusReceiveNotifications = cusReceiveNotifications;
    }

    public Integer getCusIsWhiteListed() {
        return cusIsWhiteListed;
    }

    public void setCusIsWhiteListed(Integer cusIsWhiteListed) {
        this.cusIsWhiteListed = cusIsWhiteListed;
    }

    public Integer getCusRegisterStatus() {
        return cusRegisterStatus;
    }

    public void setCusRegisterStatus(Integer cusRegisterStatus) {
        this.cusRegisterStatus = cusRegisterStatus;
    }

    public Set<CustomerProfile> getCspProfileRef() {
        return cspProfileRef;
    }

    public void setCspProfileRef(Set<CustomerProfile> cspProfileRef) {
        this.cspProfileRef = cspProfileRef;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public CustomerProfile getCustomerProfile() {
        return customerProfile;
    }

    public void setCustomerProfile(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getCusMobileCountryCode() {
        return cusMobileCountryCode;
    }

    public void setCusMobileCountryCode(String cusMobileCountryCode) {
        this.cusMobileCountryCode = cusMobileCountryCode;
    }

    public String getCusMobileAlternate() {
        return cusMobileAlternate;
    }

    public void setCusMobileAlternate(String cusMobileAlternate) {
        this.cusMobileAlternate = cusMobileAlternate;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cusCustomerNo=" + cusCustomerNo +
                ", cusMerchantNo=" + cusMerchantNo +
                ", cusUserNo=" + cusUserNo +
                ", cusIdType=" + cusIdType +
                ", cusIdNo='" + cusIdNo + '\'' +
                ", cusEmail='" + cusEmail + '\'' +
                ", cusMobile='" + cusMobile + '\'' +
                ", cusFName='" + cusFName + '\'' +
                ", cusLName='" + cusLName + '\'' +
                ", cusLoyaltyId='" + cusLoyaltyId + '\'' +
                ", cusStatus=" + cusStatus +
                ", cusUniqueLoyaltyIdentifier='" + cusUniqueLoyaltyIdentifier + '\'' +
                ", cusRegisterTimestamp=" + cusRegisterTimestamp +
                ", cusTier=" + cusTier +
                ", cusTierLastEvaluated=" + cusTierLastEvaluated +
                ", cusType=" + cusType +
                ", cusMerchantUserRegistered=" + cusMerchantUserRegistered +
                ", cusLocation=" + cusLocation +
                ", cusReceiveNotifications=" + cusReceiveNotifications +
                ", cusIsWhiteListed=" + cusIsWhiteListed +
                ", cusRegisterStatus=" + cusRegisterStatus +
                ", cspProfileRef=" + cspProfileRef +
                ", cusMobileCountryCode='" + cusMobileCountryCode + '\'' +
                ", cusMobileAlternate='" + cusMobileAlternate + '\'' +
                ", isPrimary=" + isPrimary +
                ", tier=" + tier +
                ", customerProfile=" + customerProfile +
                ", merchant=" + merchant +
                ", referralCode='" + referralCode + '\'' +
                '}';
    }
}

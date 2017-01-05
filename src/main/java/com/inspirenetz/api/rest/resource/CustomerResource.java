package com.inspirenetz.api.rest.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.CustomerSegment;

import java.util.Date;

/**
 * Created by sandheepgr on 16/2/14.
 */
public class CustomerResource extends BaseResource{


    private Long cusCustomerNo;

    private Long cusMerchantNo;

    private String cusLoyaltyId;

    private String cusEmail;

    private String cusMobileCountryCode;

    private String cusMobile;

    private String cusFName;

    private String cusLName;

    private Integer cusIdType;

    private String cusIdNo;

    private int cusStatus = CustomerStatus.ACTIVE;

    private Long cusMerchantUserRegistered;

    private Long cusLocation;

    private Integer cusType = CustomerType.SUBSCRIBER;

    private Long cusTier;





    private java.sql.Date cspCustomerBirthday;

    private Integer cspProfession = 0;

    private Integer cspIncomeRange =0 ;

    private Integer cspAgeGroup = 0;

    private String cspGender = Gender.MALE ;

    private Integer cspFamilyStatus = MaritalStatus.SINGLE;

    private String cspAddress = "";

    private String cspCity = "";

    private String cspPincode = "";

    private Integer cspState = 0;

    private Integer cspCountry = 356;

    private java.sql.Date cspCustomerAnniversary;

    private String cspFamilyChild1Name = "";

    private String cspFamilyChild2Name = "";

    private java.sql.Date cspFamilyChild1Bday;

    private java.sql.Date cspFamilyChild2Bday;

    private String cspFamilySpouseName = "";

    private java.sql.Date cspFamilySpouseBday;

    private int cspPreferredStaff1 = 0;

    private Integer cspCustomerSegmentId1 = 0;

    private int cspIsPrivilegedMember = IndicatorStatus.NO;

    private String cspNomineeName = "";

    private String cspNomineeRelation = "";

    private java.sql.Date cspNomineeDob;

    private String cspNomineeAddress = "";

    private boolean isPrimary = false;



    private String tieName = "";

    private String cusMobileAlternate;

    private String cspAddRef1 = "";


    private String cspAddRef2 = "";

    private String cspAddRef3 = "";

    private String cspAddRef4 = "";

    private String cspAddRef5 = "";

    private String referralCode="";

    public Long getCusCustomerNo() {
        return cusCustomerNo;
    }

    public void setCusCustomerNo(Long cusCustomerNo) {
        this.cusCustomerNo = cusCustomerNo;
    }

    public String getCusLoyaltyId() {
        return cusLoyaltyId;
    }

    public void setCusLoyaltyId(String cusLoyaltyId) {
        this.cusLoyaltyId = cusLoyaltyId;
    }

    public Long getCusMerchantNo() {
        return cusMerchantNo;
    }

    public void setCusMerchantNo(Long cusMerchantNo) {
        this.cusMerchantNo = cusMerchantNo;
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

    public int getCusStatus() {
        return cusStatus;
    }

    public void setCusStatus(int cusStatus) {
        this.cusStatus = cusStatus;
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

    public Integer getCusType() {
        return cusType;
    }

    public void setCusType(Integer cusType) {
        this.cusType = cusType;
    }

    public Long getCusTier() {
        return cusTier;
    }

    public void setCusTier(Long cusTier) {
        this.cusTier = cusTier;
    }

    public java.sql.Date getCspCustomerBirthday() {
        return cspCustomerBirthday;
    }

    public void setCspCustomerBirthday(java.sql.Date cspCustomerBirthday) {
        this.cspCustomerBirthday = cspCustomerBirthday;
    }

    public Integer getCspProfession() {
        return cspProfession;
    }

    public void setCspProfession(Integer cspProfession) {
        this.cspProfession = cspProfession;
    }

    public Integer getCspIncomeRange() {
        return cspIncomeRange;
    }

    public void setCspIncomeRange(Integer cspIncomeRange) {
        this.cspIncomeRange = cspIncomeRange;
    }

    public Integer getCspAgeGroup() {
        return cspAgeGroup;
    }

    public void setCspAgeGroup(Integer cspAgeGroup) {
        this.cspAgeGroup = cspAgeGroup;
    }

    public String getCspGender() {
        return cspGender;
    }

    public void setCspGender(String cspGender) {
        this.cspGender = cspGender;
    }

    public Integer getCspFamilyStatus() {
        return cspFamilyStatus;
    }

    public void setCspFamilyStatus(Integer cspFamilyStatus) {
        this.cspFamilyStatus = cspFamilyStatus;
    }

    public String getCspAddress() {
        return cspAddress;
    }

    public void setCspAddress(String cspAddress) {
        this.cspAddress = cspAddress;
    }

    public String getCspCity() {
        return cspCity;
    }

    public void setCspCity(String cspCity) {
        this.cspCity = cspCity;
    }

    public String getCspPincode() {
        return cspPincode;
    }

    public void setCspPincode(String cspPincode) {
        this.cspPincode = cspPincode;
    }

    public Integer getCspState() {
        return cspState;
    }

    public void setCspState(Integer cspState) {
        this.cspState = cspState;
    }

    public Integer getCspCountry() {
        return cspCountry;
    }

    public void setCspCountry(Integer cspCountry) {
        this.cspCountry = cspCountry;
    }

    public java.sql.Date getCspCustomerAnniversary() {
        return cspCustomerAnniversary;
    }

    public void setCspCustomerAnniversary(java.sql.Date cspCustomerAnniversary) {
        this.cspCustomerAnniversary = cspCustomerAnniversary;
    }

    public String getCspFamilyChild1Name() {
        return cspFamilyChild1Name;
    }

    public void setCspFamilyChild1Name(String cspFamilyChild1Name) {
        this.cspFamilyChild1Name = cspFamilyChild1Name;
    }

    public String getCspFamilyChild2Name() {
        return cspFamilyChild2Name;
    }

    public void setCspFamilyChild2Name(String cspFamilyChild2Name) {
        this.cspFamilyChild2Name = cspFamilyChild2Name;
    }

    public java.sql.Date getCspFamilyChild1Bday() {
        return cspFamilyChild1Bday;
    }

    public void setCspFamilyChild1Bday(java.sql.Date cspFamilyChild1Bday) {
        this.cspFamilyChild1Bday = cspFamilyChild1Bday;
    }

    public java.sql.Date getCspFamilyChild2Bday() {
        return cspFamilyChild2Bday;
    }

    public void setCspFamilyChild2Bday(java.sql.Date cspFamilyChild2Bday) {
        this.cspFamilyChild2Bday = cspFamilyChild2Bday;
    }

    public String getCspFamilySpouseName() {
        return cspFamilySpouseName;
    }

    public void setCspFamilySpouseName(String cspFamilySpouseName) {
        this.cspFamilySpouseName = cspFamilySpouseName;
    }

    public java.sql.Date getCspFamilySpouseBday() {
        return cspFamilySpouseBday;
    }

    public void setCspFamilySpouseBday(java.sql.Date cspFamilySpouseBday) {
        this.cspFamilySpouseBday = cspFamilySpouseBday;
    }

    public int getCspPreferredStaff1() {
        return cspPreferredStaff1;
    }

    public void setCspPreferredStaff1(int cspPreferredStaff1) {
        this.cspPreferredStaff1 = cspPreferredStaff1;
    }

    public Integer getCspCustomerSegmentId1() {
        return cspCustomerSegmentId1;
    }

    public void setCspCustomerSegmentId1(Integer cspCustomerSegmentId1) {
        this.cspCustomerSegmentId1 = cspCustomerSegmentId1;
    }

    public int getCspIsPrivilegedMember() {
        return cspIsPrivilegedMember;
    }

    public void setCspIsPrivilegedMember(int cspIsPrivilegedMember) {
        this.cspIsPrivilegedMember = cspIsPrivilegedMember;
    }

    public String getCspNomineeName() {
        return cspNomineeName;
    }

    public void setCspNomineeName(String cspNomineeName) {
        this.cspNomineeName = cspNomineeName;
    }

    public String getCspNomineeRelation() {
        return cspNomineeRelation;
    }

    public void setCspNomineeRelation(String cspNomineeRelation) {
        this.cspNomineeRelation = cspNomineeRelation;
    }

    public java.sql.Date getCspNomineeDob() {
        return cspNomineeDob;
    }

    public void setCspNomineeDob(java.sql.Date cspNomineeDob) {
        this.cspNomineeDob = cspNomineeDob;
    }

    public String getCspNomineeAddress() {
        return cspNomineeAddress;
    }

    public void setCspNomineeAddress(String cspNomineeAddress) {
        this.cspNomineeAddress = cspNomineeAddress;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getTieName() {
        return tieName;
    }

    public void setTieName(String tieName) {
        this.tieName = tieName;
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

    public String getCspAddRef1() {
        return cspAddRef1;
    }

    public void setCspAddRef1(String cspAddRef1) {
        this.cspAddRef1 = cspAddRef1;
    }

    public String getCspAddRef2() {
        return cspAddRef2;
    }

    public void setCspAddRef2(String cspAddRef2) {
        this.cspAddRef2 = cspAddRef2;
    }

    public String getCspAddRef3() {
        return cspAddRef3;
    }

    public void setCspAddRef3(String cspAddRef3) {
        this.cspAddRef3 = cspAddRef3;
    }

    public String getCspAddRef4() {
        return cspAddRef4;
    }

    public void setCspAddRef4(String cspAddRef4) {
        this.cspAddRef4 = cspAddRef4;
    }

    public String getCspAddRef5() {
        return cspAddRef5;
    }

    public void setCspAddRef5(String cspAddRef5) {
        this.cspAddRef5 = cspAddRef5;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    @Override
    public String toString() {
        return "CustomerResource{" +
                "cusCustomerNo=" + cusCustomerNo +
                ", cusMerchantNo=" + cusMerchantNo +
                ", cusLoyaltyId='" + cusLoyaltyId + '\'' +
                ", cusEmail='" + cusEmail + '\'' +
                ", cusMobileCountryCode='" + cusMobileCountryCode + '\'' +
                ", cusMobile='" + cusMobile + '\'' +
                ", cusFName='" + cusFName + '\'' +
                ", cusLName='" + cusLName + '\'' +
                ", cusIdType=" + cusIdType +
                ", cusIdNo='" + cusIdNo + '\'' +
                ", cusStatus=" + cusStatus +
                ", cusMerchantUserRegistered=" + cusMerchantUserRegistered +
                ", cusLocation=" + cusLocation +
                ", cusType=" + cusType +
                ", cusTier=" + cusTier +
                ", cspCustomerBirthday=" + cspCustomerBirthday +
                ", cspProfession=" + cspProfession +
                ", cspIncomeRange=" + cspIncomeRange +
                ", cspAgeGroup=" + cspAgeGroup +
                ", cspGender='" + cspGender + '\'' +
                ", cspFamilyStatus=" + cspFamilyStatus +
                ", cspAddress='" + cspAddress + '\'' +
                ", cspCity='" + cspCity + '\'' +
                ", cspPincode='" + cspPincode + '\'' +
                ", cspState=" + cspState +
                ", cspCountry=" + cspCountry +
                ", cspCustomerAnniversary=" + cspCustomerAnniversary +
                ", cspFamilyChild1Name='" + cspFamilyChild1Name + '\'' +
                ", cspFamilyChild2Name='" + cspFamilyChild2Name + '\'' +
                ", cspFamilyChild1Bday=" + cspFamilyChild1Bday +
                ", cspFamilyChild2Bday=" + cspFamilyChild2Bday +
                ", cspFamilySpouseName='" + cspFamilySpouseName + '\'' +
                ", cspFamilySpouseBday=" + cspFamilySpouseBday +
                ", cspPreferredStaff1=" + cspPreferredStaff1 +
                ", cspCustomerSegmentId1=" + cspCustomerSegmentId1 +
                ", cspIsPrivilegedMember=" + cspIsPrivilegedMember +
                ", cspNomineeName='" + cspNomineeName + '\'' +
                ", cspNomineeRelation='" + cspNomineeRelation + '\'' +
                ", cspNomineeDob=" + cspNomineeDob +
                ", cspNomineeAddress='" + cspNomineeAddress + '\'' +
                ", isPrimary=" + isPrimary +
                ", tieName='" + tieName + '\'' +
                ", cusMobileAlternate='" + cusMobileAlternate + '\'' +
                ", cspAddRef1='" + cspAddRef1 + '\'' +
                ", cspAddRef2='" + cspAddRef2 + '\'' +
                ", cspAddRef3='" + cspAddRef3 + '\'' +
                ", cspAddRef4='" + cspAddRef4 + '\'' +
                ", cspAddRef5='" + cspAddRef5 + '\'' +
                ", referralCode='" + referralCode + '\'' +
                '}';
    }
}

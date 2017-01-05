package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.Gender;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.MaritalStatus;
import com.inspirenetz.api.core.domain.CustomerProfile;

import java.sql.Date;

/**
 * Created by alameen on 10/12/14.
 */
public class CustomerProfileBuilder {
    private Long cspId;
    private Long cspCustomerNo;
    private Date cspCustomerBirthday;
    private Integer cspProfession = 0;
    private Integer cspIncomeRange =0 ;
    private Integer cspAgeGroup = 0;
    private String cspGender = Gender.MALE ;
    private Integer cspFamilyStatus = MaritalStatus.SINGLE;
    private String cspAddress = "";
    private String cspCity = "";
    private String cspPincode = "";
    private Integer cspState = 0;
    private Integer cspCountry = 608;
    private Date cspCustomerAnniversary;
    private String cspFamilyChild1Name = "";
    private String cspFamilyChild2Name = "";
    private Date cspFamilyChild1Bday;
    private Date cspFamilyChild2Bday;
    private String cspFamilySpouseName = "";
    private Date cspFamilySpouseBday;
    private int cspPreferredStaff1 = 0;
    private Integer cspCustomerSegmentId1 = 0;
    private int cspIsPrivilegedMember = IndicatorStatus.NO;
    private String cspNomineeName = "";
    private String cspNomineeRelation = "";
    private Date cspNomineeDob;
    private String cspNomineeAddress = "";
    private Date cspBirthDayLastAwarded;
    private Date cspAnniversaryLastAwarded;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private CustomerProfileBuilder() {
    }

    public static CustomerProfileBuilder aCustomerProfile() {
        return new CustomerProfileBuilder();
    }

    public CustomerProfileBuilder withCspId(Long cspId) {
        this.cspId = cspId;
        return this;
    }

    public CustomerProfileBuilder withCspCustomerNo(Long cspCustomerNo) {
        this.cspCustomerNo = cspCustomerNo;
        return this;
    }

    public CustomerProfileBuilder withCspCustomerBirthday(Date cspCustomerBirthday) {
        this.cspCustomerBirthday = cspCustomerBirthday;
        return this;
    }

    public CustomerProfileBuilder withCspProfession(Integer cspProfession) {
        this.cspProfession = cspProfession;
        return this;
    }

    public CustomerProfileBuilder withCspIncomeRange(Integer cspIncomeRange) {
        this.cspIncomeRange = cspIncomeRange;
        return this;
    }

    public CustomerProfileBuilder withCspAgeGroup(Integer cspAgeGroup) {
        this.cspAgeGroup = cspAgeGroup;
        return this;
    }

    public CustomerProfileBuilder withCspGender(String cspGender) {
        this.cspGender = cspGender;
        return this;
    }

    public CustomerProfileBuilder withCspFamilyStatus(Integer cspFamilyStatus) {
        this.cspFamilyStatus = cspFamilyStatus;
        return this;
    }

    public CustomerProfileBuilder withCspAddress(String cspAddress) {
        this.cspAddress = cspAddress;
        return this;
    }

    public CustomerProfileBuilder withCspCity(String cspCity) {
        this.cspCity = cspCity;
        return this;
    }

    public CustomerProfileBuilder withCspPincode(String cspPincode) {
        this.cspPincode = cspPincode;
        return this;
    }

    public CustomerProfileBuilder withCspState(Integer cspState) {
        this.cspState = cspState;
        return this;
    }

    public CustomerProfileBuilder withCspCountry(Integer cspCountry) {
        this.cspCountry = cspCountry;
        return this;
    }

    public CustomerProfileBuilder withCspCustomerAnniversary(Date cspCustomerAnniversary) {
        this.cspCustomerAnniversary = cspCustomerAnniversary;
        return this;
    }

    public CustomerProfileBuilder withCspFamilyChild1Name(String cspFamilyChild1Name) {
        this.cspFamilyChild1Name = cspFamilyChild1Name;
        return this;
    }

    public CustomerProfileBuilder withCspFamilyChild2Name(String cspFamilyChild2Name) {
        this.cspFamilyChild2Name = cspFamilyChild2Name;
        return this;
    }

    public CustomerProfileBuilder withCspFamilyChild1Bday(Date cspFamilyChild1Bday) {
        this.cspFamilyChild1Bday = cspFamilyChild1Bday;
        return this;
    }

    public CustomerProfileBuilder withCspFamilyChild2Bday(Date cspFamilyChild2Bday) {
        this.cspFamilyChild2Bday = cspFamilyChild2Bday;
        return this;
    }

    public CustomerProfileBuilder withCspFamilySpouseName(String cspFamilySpouseName) {
        this.cspFamilySpouseName = cspFamilySpouseName;
        return this;
    }

    public CustomerProfileBuilder withCspFamilySpouseBday(Date cspFamilySpouseBday) {
        this.cspFamilySpouseBday = cspFamilySpouseBday;
        return this;
    }

    public CustomerProfileBuilder withCspPreferredStaff1(int cspPreferredStaff1) {
        this.cspPreferredStaff1 = cspPreferredStaff1;
        return this;
    }

    public CustomerProfileBuilder withCspCustomerSegmentId1(Integer cspCustomerSegmentId1) {
        this.cspCustomerSegmentId1 = cspCustomerSegmentId1;
        return this;
    }

    public CustomerProfileBuilder withCspIsPrivilegedMember(int cspIsPrivilegedMember) {
        this.cspIsPrivilegedMember = cspIsPrivilegedMember;
        return this;
    }

    public CustomerProfileBuilder withCspNomineeName(String cspNomineeName) {
        this.cspNomineeName = cspNomineeName;
        return this;
    }

    public CustomerProfileBuilder withCspNomineeRelation(String cspNomineeRelation) {
        this.cspNomineeRelation = cspNomineeRelation;
        return this;
    }

    public CustomerProfileBuilder withCspNomineeDob(Date cspNomineeDob) {
        this.cspNomineeDob = cspNomineeDob;
        return this;
    }

    public CustomerProfileBuilder withCspNomineeAddress(String cspNomineeAddress) {
        this.cspNomineeAddress = cspNomineeAddress;
        return this;
    }

    public CustomerProfileBuilder withCspBirthDayLastAwarded(Date cspBirthDayLastAwarded) {
        this.cspBirthDayLastAwarded = cspBirthDayLastAwarded;
        return this;
    }

    public CustomerProfileBuilder withCspAnniversaryLastAwarded(Date cspAnniversaryLastAwarded) {
        this.cspAnniversaryLastAwarded = cspAnniversaryLastAwarded;
        return this;
    }

    public CustomerProfileBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CustomerProfileBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CustomerProfileBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CustomerProfileBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CustomerProfileBuilder but() {
        return aCustomerProfile().withCspId(cspId).withCspCustomerNo(cspCustomerNo).withCspCustomerBirthday(cspCustomerBirthday).withCspProfession(cspProfession).withCspIncomeRange(cspIncomeRange).withCspAgeGroup(cspAgeGroup).withCspGender(cspGender).withCspFamilyStatus(cspFamilyStatus).withCspAddress(cspAddress).withCspCity(cspCity).withCspPincode(cspPincode).withCspState(cspState).withCspCountry(cspCountry).withCspCustomerAnniversary(cspCustomerAnniversary).withCspFamilyChild1Name(cspFamilyChild1Name).withCspFamilyChild2Name(cspFamilyChild2Name).withCspFamilyChild1Bday(cspFamilyChild1Bday).withCspFamilyChild2Bday(cspFamilyChild2Bday).withCspFamilySpouseName(cspFamilySpouseName).withCspFamilySpouseBday(cspFamilySpouseBday).withCspPreferredStaff1(cspPreferredStaff1).withCspCustomerSegmentId1(cspCustomerSegmentId1).withCspIsPrivilegedMember(cspIsPrivilegedMember).withCspNomineeName(cspNomineeName).withCspNomineeRelation(cspNomineeRelation).withCspNomineeDob(cspNomineeDob).withCspNomineeAddress(cspNomineeAddress).withCspBirthDayLastAwarded(cspBirthDayLastAwarded).withCspAnniversaryLastAwarded(cspAnniversaryLastAwarded).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public CustomerProfile build() {
        CustomerProfile customerProfile = new CustomerProfile();
        customerProfile.setCspId(cspId);
        customerProfile.setCspCustomerNo(cspCustomerNo);
        customerProfile.setCspCustomerBirthday(cspCustomerBirthday);
        customerProfile.setCspProfession(cspProfession);
        customerProfile.setCspIncomeRange(cspIncomeRange);
        customerProfile.setCspAgeGroup(cspAgeGroup);
        customerProfile.setCspGender(cspGender);
        customerProfile.setCspFamilyStatus(cspFamilyStatus);
        customerProfile.setCspAddress(cspAddress);
        customerProfile.setCspCity(cspCity);
        customerProfile.setCspPincode(cspPincode);
        customerProfile.setCspState(cspState);
        customerProfile.setCspCountry(cspCountry);
        customerProfile.setCspCustomerAnniversary(cspCustomerAnniversary);
        customerProfile.setCspFamilyChild1Name(cspFamilyChild1Name);
        customerProfile.setCspFamilyChild2Name(cspFamilyChild2Name);
        customerProfile.setCspFamilyChild1Bday(cspFamilyChild1Bday);
        customerProfile.setCspFamilyChild2Bday(cspFamilyChild2Bday);
        customerProfile.setCspFamilySpouseName(cspFamilySpouseName);
        customerProfile.setCspFamilySpouseBday(cspFamilySpouseBday);
        customerProfile.setCspPreferredStaff1(cspPreferredStaff1);
        customerProfile.setCspCustomerSegmentId1(cspCustomerSegmentId1);
        customerProfile.setCspIsPrivilegedMember(cspIsPrivilegedMember);
        customerProfile.setCspNomineeName(cspNomineeName);
        customerProfile.setCspNomineeRelation(cspNomineeRelation);
        customerProfile.setCspNomineeDob(cspNomineeDob);
        customerProfile.setCspNomineeAddress(cspNomineeAddress);
        customerProfile.setCspBirthDayLastAwarded(cspBirthDayLastAwarded);
        customerProfile.setCspAnniversaryLastAwarded(cspAnniversaryLastAwarded);
//        customerProfile.setCreatedAt(createdAt);
//        customerProfile.setCreatedBy(createdBy);
//        customerProfile.setUpdatedAt(updatedAt);
//        customerProfile.setUpdatedBy(updatedBy);
        return customerProfile;
    }
}

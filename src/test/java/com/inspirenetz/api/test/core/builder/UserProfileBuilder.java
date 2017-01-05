package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.Gender;
import com.inspirenetz.api.core.dictionary.MaritalStatus;
import com.inspirenetz.api.core.domain.UserProfile;

import java.sql.Date;

/**
 * Created by alameen on 28/10/14.
 */
public class UserProfileBuilder {
    private Long uspId;
    private Long uspIdentityType;
    private Long uspIdentityNO;
    private String uspAddress1 = "";
    private String uspAddress2 = "";
    private String uspAddress3 = "";
    private String uspCity = "";
    private Integer uspState = 0;
    private Integer uspCountry = 356;
    private String uspPinCode = "";
    private String uspPhoneNo;
    private Integer uspProfession = 0;
    private Integer uspIncomeRange =0 ;
    private Integer uspAgeGroup = 0;
    private Date uspBirthday;
    private Date uspAnniversary;
    private String uspGender = Gender.MALE ;
    private Integer uspFamilyStatus = MaritalStatus.SINGLE;
    private Integer uspEmailFreq;
    private Integer uspMerchantNo;
    private Long uspUserNo;
    private String usrFName;
    private String usrLName;
    private String usrProfilePicPath;
    private Long usrProfilePic;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private UserProfileBuilder() {
    }

    public static UserProfileBuilder anUserProfile() {
        return new UserProfileBuilder();
    }

    public UserProfileBuilder withUspId(Long uspId) {
        this.uspId = uspId;
        return this;
    }

    public UserProfileBuilder withUspIdentityType(Long uspIdentityType) {
        this.uspIdentityType = uspIdentityType;
        return this;
    }

    public UserProfileBuilder withUspIdentityNO(Long uspIdentityNO) {
        this.uspIdentityNO = uspIdentityNO;
        return this;
    }

    public UserProfileBuilder withUspAddress1(String uspAddress1) {
        this.uspAddress1 = uspAddress1;
        return this;
    }

    public UserProfileBuilder withUspAddress2(String uspAddress2) {
        this.uspAddress2 = uspAddress2;
        return this;
    }

    public UserProfileBuilder withUspAddress3(String uspAddress3) {
        this.uspAddress3 = uspAddress3;
        return this;
    }

    public UserProfileBuilder withUspCity(String uspCity) {
        this.uspCity = uspCity;
        return this;
    }

    public UserProfileBuilder withUspState(Integer uspState) {
        this.uspState = uspState;
        return this;
    }

    public UserProfileBuilder withUspCountry(Integer uspCountry) {
        this.uspCountry = uspCountry;
        return this;
    }

    public UserProfileBuilder withUspPinCode(String uspPinCode) {
        this.uspPinCode = uspPinCode;
        return this;
    }

    public UserProfileBuilder withUspPhoneNo(String uspPhoneNo) {
        this.uspPhoneNo = uspPhoneNo;
        return this;
    }

    public UserProfileBuilder withUspProfession(Integer uspProfession) {
        this.uspProfession = uspProfession;
        return this;
    }

    public UserProfileBuilder withUspIncomeRange(Integer uspIncomeRange) {
        this.uspIncomeRange = uspIncomeRange;
        return this;
    }

    public UserProfileBuilder withUspAgeGroup(Integer uspAgeGroup) {
        this.uspAgeGroup = uspAgeGroup;
        return this;
    }

    public UserProfileBuilder withUspBirthday(Date uspBirthday) {
        this.uspBirthday = uspBirthday;
        return this;
    }

    public UserProfileBuilder withUspAnniversary(Date uspAnniversary) {
        this.uspAnniversary = uspAnniversary;
        return this;
    }

    public UserProfileBuilder withUspGender(String uspGender) {
        this.uspGender = uspGender;
        return this;
    }

    public UserProfileBuilder withUspFamilyStatus(Integer uspFamilyStatus) {
        this.uspFamilyStatus = uspFamilyStatus;
        return this;
    }

    public UserProfileBuilder withUspEmailFreq(Integer uspEmailFreq) {
        this.uspEmailFreq = uspEmailFreq;
        return this;
    }

    public UserProfileBuilder withUspMerchantNo(Integer uspMerchantNo) {
        this.uspMerchantNo = uspMerchantNo;
        return this;
    }

    public UserProfileBuilder withUspUserNo(Long uspUserNo) {
        this.uspUserNo = uspUserNo;
        return this;
    }

    public UserProfileBuilder withUsrFName(String usrFName) {
        this.usrFName = usrFName;
        return this;
    }

    public UserProfileBuilder withUsrLName(String usrLName) {
        this.usrLName = usrLName;
        return this;
    }

    public UserProfileBuilder withUsrProfilePicPath(String usrProfilePicPath) {
        this.usrProfilePicPath = usrProfilePicPath;
        return this;
    }

    public UserProfileBuilder withUsrProfilePic(Long usrProfilePic) {
        this.usrProfilePic = usrProfilePic;
        return this;
    }

    public UserProfileBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserProfileBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public UserProfileBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public UserProfileBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public UserProfileBuilder but() {
        return anUserProfile().withUspId(uspId).withUspIdentityType(uspIdentityType).withUspIdentityNO(uspIdentityNO).withUspAddress1(uspAddress1).withUspAddress2(uspAddress2).withUspAddress3(uspAddress3).withUspCity(uspCity).withUspState(uspState).withUspCountry(uspCountry).withUspPinCode(uspPinCode).withUspPhoneNo(uspPhoneNo).withUspProfession(uspProfession).withUspIncomeRange(uspIncomeRange).withUspAgeGroup(uspAgeGroup).withUspBirthday(uspBirthday).withUspAnniversary(uspAnniversary).withUspGender(uspGender).withUspFamilyStatus(uspFamilyStatus).withUspEmailFreq(uspEmailFreq).withUspMerchantNo(uspMerchantNo).withUspUserNo(uspUserNo).withUsrFName(usrFName).withUsrLName(usrLName).withUsrProfilePicPath(usrProfilePicPath).withUsrProfilePic(usrProfilePic).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public UserProfile build() {
        UserProfile userProfile = new UserProfile();
        userProfile.setUspId(uspId);
        userProfile.setUspIdentityType(uspIdentityType);
        userProfile.setUspIdentityNO(uspIdentityNO);
        userProfile.setUspAddress1(uspAddress1);
        userProfile.setUspAddress2(uspAddress2);
        userProfile.setUspAddress3(uspAddress3);
        userProfile.setUspCity(uspCity);
        userProfile.setUspState(uspState);
        userProfile.setUspCountry(uspCountry);
        userProfile.setUspPinCode(uspPinCode);
        userProfile.setUspPhoneNo(uspPhoneNo);
        userProfile.setUspProfession(uspProfession);
        userProfile.setUspIncomeRange(uspIncomeRange);
        userProfile.setUspAgeGroup(uspAgeGroup);
        userProfile.setUspBirthday(uspBirthday);
        userProfile.setUspAnniversary(uspAnniversary);
        userProfile.setUspGender(uspGender);
        userProfile.setUspFamilyStatus(uspFamilyStatus);
        userProfile.setUspEmailFreq(uspEmailFreq);
        userProfile.setUspMerchantNo(uspMerchantNo);
        userProfile.setUspUserNo(uspUserNo);
        userProfile.setUsrFName(usrFName);
        userProfile.setUsrLName(usrLName);
        userProfile.setUsrProfilePicPath(usrProfilePicPath);
        userProfile.setUsrProfilePic(usrProfilePic);
        userProfile.setCreatedAt(createdAt);
        userProfile.setCreatedBy(createdBy);
        userProfile.setUpdatedAt(updatedAt);
        userProfile.setUpdatedBy(updatedBy);
        return userProfile;
    }
}

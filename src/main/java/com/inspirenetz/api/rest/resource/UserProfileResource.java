package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.Gender;
import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.MaritalStatus;
import com.inspirenetz.api.core.domain.User;

import java.sql.Date;


/**
 * Created by alameen on 24/10/14.
 */
public class
        UserProfileResource extends BaseResource {


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

    public Long getUsrProfilePic() {
        return usrProfilePic;
    }

    public void setUsrProfilePic(Long usrProfilePic) {
        this.usrProfilePic = usrProfilePic;
    }

    public String getUsrProfilePicPath() {
        return usrProfilePicPath;
    }

    public void setUsrProfilePicPath(String usrProfilePicPath) {
        this.usrProfilePicPath = usrProfilePicPath;
    }

    public String getUsrFName() {
        return usrFName;
    }

    public void setUsrFName(String usrFName) {
        this.usrFName = usrFName;
    }

    public String getUsrLName() {
        return usrLName;
    }

    public void setUsrLName(String usrLName) {
        this.usrLName = usrLName;
    }

    public Long getUspId() {
        return uspId;
    }

    public Long getUspUserNo() {
        return uspUserNo;
    }

    public void setUspUserNo(Long uspUserNo) {
        this.uspUserNo = uspUserNo;
    }

    public void setUspId(Long uspId) {
        this.uspId = uspId;
    }

    public Long getUspIdentityType() {
        return uspIdentityType;
    }

    public void setUspIdentityType(Long uspIdentityType) {
        this.uspIdentityType = uspIdentityType;
    }

    public Long getUspIdentityNO() {
        return uspIdentityNO;
    }

    public void setUspIdentityNO(Long uspIdentityNO) {
        this.uspIdentityNO = uspIdentityNO;
    }

    public String getUspAddress1() {
        return uspAddress1;
    }

    public void setUspAddress1(String uspAddress1) {
        this.uspAddress1 = uspAddress1;
    }

    public String getUspAddress2() {
        return uspAddress2;
    }

    public void setUspAddress2(String uspAddress2) {
        this.uspAddress2 = uspAddress2;
    }

    public String getUspAddress3() {
        return uspAddress3;
    }

    public void setUspAddress3(String uspAddress3) {
        this.uspAddress3 = uspAddress3;
    }

    public String getUspCity() {
        return uspCity;
    }

    public void setUspCity(String uspCity) {
        this.uspCity = uspCity;
    }

    public Integer getUspState() {
        return uspState;
    }

    public void setUspState(Integer uspState) {
        this.uspState = uspState;
    }

    public Integer getUspCountry() {
        return uspCountry;
    }

    public void setUspCountry(Integer uspCountry) {
        this.uspCountry = uspCountry;
    }

    public String getUspPinCode() {
        return uspPinCode;
    }

    public void setUspPinCode(String uspPinCode) {
        this.uspPinCode = uspPinCode;
    }

    public String getUspPhoneNo() {
        return uspPhoneNo;
    }

    public void setUspPhoneNo(String uspPhoneNo) {
        this.uspPhoneNo = uspPhoneNo;
    }

    public Integer getUspProfession() {
        return uspProfession;
    }

    public void setUspProfession(Integer uspProfession) {
        this.uspProfession = uspProfession;
    }

    public Integer getUspIncomeRange() {
        return uspIncomeRange;
    }

    public void setUspIncomeRange(Integer uspIncomeRange) {
        this.uspIncomeRange = uspIncomeRange;
    }

    public Integer getUspAgeGroup() {
        return uspAgeGroup;
    }

    public void setUspAgeGroup(Integer uspAgeGroup) {
        this.uspAgeGroup = uspAgeGroup;
    }

    public Date getUspBirthday() {
        return uspBirthday;
    }

    public void setUspBirthday(Date uspBirthday) {
        this.uspBirthday = uspBirthday;
    }

    public Date getUspAnniversary() {
        return uspAnniversary;
    }

    public void setUspAnniversary(Date uspAnniversary) {
        this.uspAnniversary = uspAnniversary;
    }

    public String getUspGender() {
        return uspGender;
    }

    public void setUspGender(String uspGender) {
        this.uspGender = uspGender;
    }

    public Integer getUspFamilyStatus() {
        return uspFamilyStatus;
    }

    public void setUspFamilyStatus(Integer uspFamilyStatus) {
        this.uspFamilyStatus = uspFamilyStatus;
    }

    public Integer getUspEmailFreq() {
        return uspEmailFreq;
    }

    public void setUspEmailFreq(Integer uspEmailFreq) {
        this.uspEmailFreq = uspEmailFreq;
    }

    public Integer getUspMerchantNo() {
        return uspMerchantNo;
    }

    public void setUspMerchantNo(Integer uspMerchantNo) {
        this.uspMerchantNo = uspMerchantNo;
    }


}

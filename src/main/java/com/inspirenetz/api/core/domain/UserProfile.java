package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.Gender;
import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.MaritalStatus;
import com.inspirenetz.api.util.DBUtils;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.text.ParseException;

/**
 * Created by alameen on 24/10/14.
 */
@Entity
@Table(name = "USER_PROFILES")
public class UserProfile  extends  AuditedEntity {

    @Column(name = "USP_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uspId;

    @Basic(fetch = FetchType.EAGER )
    @Column(name= "USP_IDENTITY_TYPE",nullable = true)
    private Long uspIdentityType;

    @Basic(fetch = FetchType.EAGER)
    @Column(name= "USP_IDENTITY_NO",nullable = true)
    private Long uspIdentityNO;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_ADDRESS1",nullable = true)
    @Size(max=200,message ="{userprofile.uspAddress1.size}")
    private String uspAddress1 = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_ADDRESS2",nullable = true)
    @Size(max=200,message ="{userprofile.uspAddress2.size}")
    private String uspAddress2 = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_ADDRESS3",nullable = true)
    @Size(max=200,message ="{userprofile.uspAddress3.size}")
    private String uspAddress3 = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_CITY",nullable = true)
    @Size(max=100,message ="{userprofile.uspcity.size}")
    private String uspCity = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_STATE")
    private Integer uspState = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_COUNTRY")
    private Integer uspCountry = 608;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_PINCODE",nullable = true)
    @Size(max=20,message ="{userprofile.usppincode.size}")
    private String uspPinCode = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_PHONE_NO",nullable = true)
    private String uspPhoneNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_PROFESSION")
    private Integer uspProfession = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_INCOME_RANGE")
    private Integer uspIncomeRange =0 ;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_AGE_GROUP")
    private Integer uspAgeGroup = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_BIRTHDAY",nullable = true)
    private Date uspBirthday;


    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_ANNIVERSARY",nullable = true)
    private Date uspAnniversary;


    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_GENDER")
    private String uspGender = Gender.MALE ;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_FAMILY_STATUS")
    private Integer uspFamilyStatus = MaritalStatus.SINGLE;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_EMAIL_FREQ",nullable = true)
    private Integer uspEmailFreq;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_MERCHANT_NO")
    private Integer uspMerchantNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "USP_USER_NO")
    private Long uspUserNo;

    @Transient
    private String usrFName;

    @Transient
    private String usrLName;

    @Transient
    private String usrProfilePicPath;

    @Transient
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

    public void setUspId(Long uspId) {
        this.uspId = uspId;
    }

    public Long getUspUserNo() {
        return uspUserNo;
    }

    public void setUspUserNo(Long uspUserNo) {
        this.uspUserNo = uspUserNo;
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

    @Override
    public String toString() {
        return "UserProfile{" +
                "uspId=" + uspId +
                ", uspIdentityType=" + uspIdentityType +
                ", uspIdentityNO=" + uspIdentityNO +
                ", uspAddress1='" + uspAddress1 + '\'' +
                ", uspAddress2='" + uspAddress2 + '\'' +
                ", uspAddress3='" + uspAddress3 + '\'' +
                ", uspCity='" + uspCity + '\'' +
                ", uspState=" + uspState +
                ", uspCountry=" + uspCountry +
                ", uspPinCode='" + uspPinCode + '\'' +
                ", uspPhoneNo='" + uspPhoneNo + '\'' +
                ", uspProfession=" + uspProfession +
                ", uspIncomeRange=" + uspIncomeRange +
                ", uspAgeGroup=" + uspAgeGroup +
                ", uspBirthday=" + uspBirthday +
                ", uspAnniversary=" + uspAnniversary +
                ", uspGender='" + uspGender + '\'' +
                ", uspFamilyStatus=" + uspFamilyStatus +
                ", uspEmailFreq=" + uspEmailFreq +
                ", uspMerchantNo=" + uspMerchantNo +
                ", uspUserNo=" + uspUserNo +
                ", usrFName='" + usrFName + '\'' +
                ", usrLName='" + usrLName + '\'' +
                ", usrProfilePicPath='" + usrProfilePicPath + '\'' +
                ", usrProfilePic=" + usrProfilePic +
                '}';
    }
}

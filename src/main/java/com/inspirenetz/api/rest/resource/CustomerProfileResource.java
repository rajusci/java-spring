package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.Gender;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.MaritalStatus;

import java.sql.Date;

/**
 * Created by sandheepgr on 5/9/14.
 */
public class CustomerProfileResource extends BaseResource {

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

    private Integer cspCountry = 356;

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


    private String cspAddRef1 = "";


    private String cspAddRef2 = "";

    private String cspAddRef3 = "";

    private String cspAddRef4 = "";

    private String cspAddRef5 = "";


    public Long getCspId() {
        return cspId;
    }

    public void setCspId(Long cspId) {
        this.cspId = cspId;
    }

    public Long getCspCustomerNo() {
        return cspCustomerNo;
    }

    public void setCspCustomerNo(Long cspCustomerNo) {
        this.cspCustomerNo = cspCustomerNo;
    }

    public Date getCspCustomerBirthday() {
        return cspCustomerBirthday;
    }

    public void setCspCustomerBirthday(Date cspCustomerBirthday) {
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

    public Date getCspCustomerAnniversary() {
        return cspCustomerAnniversary;
    }

    public void setCspCustomerAnniversary(Date cspCustomerAnniversary) {
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

    public Date getCspFamilyChild1Bday() {
        return cspFamilyChild1Bday;
    }

    public void setCspFamilyChild1Bday(Date cspFamilyChild1Bday) {
        this.cspFamilyChild1Bday = cspFamilyChild1Bday;
    }

    public Date getCspFamilyChild2Bday() {
        return cspFamilyChild2Bday;
    }

    public void setCspFamilyChild2Bday(Date cspFamilyChild2Bday) {
        this.cspFamilyChild2Bday = cspFamilyChild2Bday;
    }

    public String getCspFamilySpouseName() {
        return cspFamilySpouseName;
    }

    public void setCspFamilySpouseName(String cspFamilySpouseName) {
        this.cspFamilySpouseName = cspFamilySpouseName;
    }

    public Date getCspFamilySpouseBday() {
        return cspFamilySpouseBday;
    }

    public void setCspFamilySpouseBday(Date cspFamilySpouseBday) {
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

    public Date getCspNomineeDob() {
        return cspNomineeDob;
    }

    public void setCspNomineeDob(Date cspNomineeDob) {
        this.cspNomineeDob = cspNomineeDob;
    }

    public String getCspNomineeAddress() {
        return cspNomineeAddress;
    }

    public void setCspNomineeAddress(String cspNomineeAddress) {
        this.cspNomineeAddress = cspNomineeAddress;
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
}

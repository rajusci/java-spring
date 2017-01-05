package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.Gender;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.MaritalStatus;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "CUSTOMER_PROFILE")
public class CustomerProfile  {


    @Column(name = "CSP_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cspId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name= "CSP_CUSTOMER_NO",nullable = true)
    private Long cspCustomerNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_CUSTOMER_BIRTHDAY")
    private Date cspCustomerBirthday;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_PROFESSION")
    private Integer cspProfession = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_INCOME_RANGE")
    private Integer cspIncomeRange =0 ;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_AGE_GROUP")
    private Integer cspAgeGroup = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_GENDER")
    private String cspGender = Gender.MALE ;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_FAMILY_STATUS")
    @Range(min=1,max=2,message = "{customerprofile.cspfamilystatus.range}")
    private Integer cspFamilyStatus = MaritalStatus.SINGLE;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_ADDRESS")
    @Size(max=200,message ="{customerprofile.cspaddress.size}")
    private String cspAddress = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_CITY")
    @Size(max=100,message ="{customerprofile.cspcity.size}")
    private String cspCity = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_PINCODE")
    @Size(max=20,message ="{customerprofile.csppincode.size}")
    private String cspPincode = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_STATE")
    private Integer cspState = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_COUNTRY")
    private Integer cspCountry = 608;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_CUSTOMER_ANNIVERSARY")
    private Date cspCustomerAnniversary;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_FAMILY_CHILD1_NAME")
    @Size(max=50,message = "{customerprofile.cspfamilychild1name.size}")
    private String cspFamilyChild1Name = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_FAMILY_CHILD2_NAME")
    @Size(max=50,message = "{customerprofile.cspfamilychild2name.size}")
    private String cspFamilyChild2Name = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_FAMILY_CHILD1_BDAY")
    private Date cspFamilyChild1Bday;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_FAMILY_CHILD2_BDAY")
    private Date cspFamilyChild2Bday;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_FAMILY_SPOUSE_NAME")
    @Size(max=50,message = "{customerprofile.cspfamilyspousename.size}")
    private String cspFamilySpouseName = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_FAMILY_SPOUSE_BDAY")
    private Date cspFamilySpouseBday;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_PREFERRED_STAFF1")
    private int cspPreferredStaff1 = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_CUSTOMER_SEGMENT_ID1")
    private Integer cspCustomerSegmentId1 = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_IS_PRIVILEGED_MEMBER")
    private int cspIsPrivilegedMember = IndicatorStatus.NO;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_NOMINEE_NAME")
    private String cspNomineeName = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_NOMINEE_RELATION")
    private String cspNomineeRelation = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_NOMINEE_DOB")
    private Date cspNomineeDob;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_NOMINEE_ADDRESS")
    private String cspNomineeAddress = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_BIRTHDAY_LAST_AWARDED")
    private Date cspBirthDayLastAwarded;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_ANNIVERSARY_LAST_AWARDED")
    private Date cspAnniversaryLastAwarded;



    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_ADD_REF1")
    private String cspAddRef1 = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_ADD_REF2")
    private String cspAddRef2 = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_ADD_REF3")
    private String cspAddRef3 = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_ADD_REF4")
    private String cspAddRef4 = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSP_ADD_REF5")
    private String cspAddRef5 = "";


    /**
     *
     * @date:19-12-2014
     * @purpose:no need for setting default value
     * @modified by:Al Ameen
     */

//    /**
//     * Function to clear the null date fields and to set them to the
//     * default date for the database
//     *
//     * @throws ParseException
//     */
//    private void clearNullDateField() throws ParseException {
//
//
//        // Check if the customerBirthday is set
//        if (cspCustomerBirthday == null ) {
//
//            cspCustomerBirthday = DBUtils.getDefaultDate();
//
//        }
//
//
//        // Check if the customerAnniversary is set
//        if (cspCustomerAnniversary == null ){
//
//            cspCustomerAnniversary = DBUtils.getDefaultDate();
//
//        }
//
//
//        // Check if the cspFamilyChild1Bday is set
//        if ( cspFamilyChild1Bday == null ) {
//
//            cspFamilyChild1Bday = DBUtils.getDefaultDate();
//
//        }
//
//
//        // Check if the cspChild2Bday is set
//        if (cspFamilyChild2Bday == null ) {
//
//            cspFamilyChild2Bday = DBUtils.getDefaultDate();
//
//        }
//
//
//        // Check if the spousebiday is set
//        if (cspFamilySpouseBday == null ) {
//
//            cspFamilySpouseBday = DBUtils.getDefaultDate();
//
//        }
//
//
//        // Check if the nomineeDob is null
//        if ( cspNomineeDob == null ) {
//
//            cspNomineeDob = DBUtils.getDefaultDate();
//
//        }
//
//    }

//
//    @PreUpdate
//    private void preUpdate() throws ParseException {
//
//        clearNullDateField();
//
//    }
//
//
//    @PrePersist
//    private void prePersist() throws ParseException {
//
//        clearNullDateField();
//
//    }


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

    public Date getCspBirthDayLastAwarded() {
        return cspBirthDayLastAwarded;
    }

    public void setCspBirthDayLastAwarded(Date cspBirthDayLastAwarded) {
        this.cspBirthDayLastAwarded = cspBirthDayLastAwarded;
    }

    public Date getCspAnniversaryLastAwarded() {
        return cspAnniversaryLastAwarded;
    }

    public void setCspAnniversaryLastAwarded(Date cspAnniversaryLastAwarded) {
        this.cspAnniversaryLastAwarded = cspAnniversaryLastAwarded;
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

    @Override
    public String toString() {
        return "CustomerProfile{" +
                "cspId=" + cspId +
                ", cspCustomerNo=" + cspCustomerNo +
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
                ", cspBirthDayLastAwarded=" + cspBirthDayLastAwarded +
                ", cspAnniversaryLastAwarded=" + cspAnniversaryLastAwarded +
                ", cspAddRef1='" + cspAddRef1 + '\'' +
                ", cspAddRef2='" + cspAddRef2 + '\'' +
                ", cspAddRef3='" + cspAddRef3 + '\'' +
                ", cspAddRef4='" + cspAddRef4 + '\'' +
                ", cspAddRef5='" + cspAddRef5 + '\'' +
                '}';
    }
}

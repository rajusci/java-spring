package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.CustomerMasterRawdataStatus;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "CUSTOMER_MASTER_RAWDATA")
public class CustomerMasterRawdata {

    @Id
    @Column(name = "CMR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmrId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_BATCH_INDEX")
    private int cmrBatchIndex;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_ROWINDEX")
    private Integer cmrRowindex;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_LOYALTY_ID")
    private String cmrLoyaltyId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_EMAIL")
    private String cmrEmail;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_MOBILE")
    private String cmrMobile;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_FIRSTNAME")
    private String cmrFirstname;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_LASTNAME")
    private String cmrLastname;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_ADDRESS")
    private String cmrAddress;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_CITY")
    private String cmrCity;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_STATE")
    private Integer cmrState;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_PINCODE")
    private String cmrPincode;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_COUNTRY")
    private Integer cmrCountry;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_CUSTOMER_BIRTHDAY")
    private Date cmrCustomerBirthday;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_CUSTOMER_ANNIVERSARY")
    private Date cmrCustomerAnniversary;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_FAMILY_CHILD1_NAME")
    private String cmrFamilyChild1Name;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_FAMILY_CHILD2_NAME")
    private String cmrFamilyChild2Name;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_FAMILY_CHILD1_BDAY")
    private Date cmrFamilyChild1Bday;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_FAMILY_CHILD2_BDAY")
    private Date cmrFamilyChild2Bday;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_FAMILY_SPOUSE_NAME")
    private String cmrFamilySpouseName;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_FAMILY_SPOUSE_BDAY")
    private Date cmrFamilySpouseBday;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_MERCHANT_NO")
    private Long cmrMerchantNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_USER_NO")
    private int cmrUserNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_STATUS")
    private CustomerMasterRawdataStatus cmrStatus;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_PROCESSING_COMMENT")
    private String cmrProcessingComment;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMR_TIMESTAMP")
    private Timestamp cmrTimestamp;


    public Long getCmrId() {
        return cmrId;
    }

    public void setCmrId(Long cmrId) {
        this.cmrId = cmrId;
    }

    public int getCmrBatchIndex() {
        return cmrBatchIndex;
    }

    public void setCmrBatchIndex(int cmrBatchIndex) {
        this.cmrBatchIndex = cmrBatchIndex;
    }

    public Integer getCmrRowindex() {
        return cmrRowindex;
    }

    public void setCmrRowindex(Integer cmrRowindex) {
        this.cmrRowindex = cmrRowindex;
    }

    public String getCmrLoyaltyId() {
        return cmrLoyaltyId;
    }

    public void setCmrLoyaltyId(String cmrLoyaltyId) {
        this.cmrLoyaltyId = cmrLoyaltyId;
    }

    public String getCmrEmail() {
        return cmrEmail;
    }

    public void setCmrEmail(String cmrEmail) {
        this.cmrEmail = cmrEmail;
    }

    public String getCmrMobile() {
        return cmrMobile;
    }

    public void setCmrMobile(String cmrMobile) {
        this.cmrMobile = cmrMobile;
    }

    public String getCmrFirstname() {
        return cmrFirstname;
    }

    public void setCmrFirstname(String cmrFirstname) {
        this.cmrFirstname = cmrFirstname;
    }

    public String getCmrLastname() {
        return cmrLastname;
    }

    public void setCmrLastname(String cmrLastname) {
        this.cmrLastname = cmrLastname;
    }

    public String getCmrAddress() {
        return cmrAddress;
    }

    public void setCmrAddress(String cmrAddress) {
        this.cmrAddress = cmrAddress;
    }

    public String getCmrCity() {
        return cmrCity;
    }

    public void setCmrCity(String cmrCity) {
        this.cmrCity = cmrCity;
    }

    public Integer getCmrState() {
        return cmrState;
    }

    public void setCmrState(Integer cmrState) {
        this.cmrState = cmrState;
    }

    public String getCmrPincode() {
        return cmrPincode;
    }

    public void setCmrPincode(String cmrPincode) {
        this.cmrPincode = cmrPincode;
    }

    public Integer getCmrCountry() {
        return cmrCountry;
    }

    public void setCmrCountry(Integer cmrCountry) {
        this.cmrCountry = cmrCountry;
    }

    public Date getCmrCustomerBirthday() {
        return cmrCustomerBirthday;
    }

    public void setCmrCustomerBirthday(Date cmrCustomerBirthday) {
        this.cmrCustomerBirthday = cmrCustomerBirthday;
    }

    public Date getCmrCustomerAnniversary() {
        return cmrCustomerAnniversary;
    }

    public void setCmrCustomerAnniversary(Date cmrCustomerAnniversary) {
        this.cmrCustomerAnniversary = cmrCustomerAnniversary;
    }

    public String getCmrFamilyChild1Name() {
        return cmrFamilyChild1Name;
    }

    public void setCmrFamilyChild1Name(String cmrFamilyChild1Name) {
        this.cmrFamilyChild1Name = cmrFamilyChild1Name;
    }

    public String getCmrFamilyChild2Name() {
        return cmrFamilyChild2Name;
    }

    public void setCmrFamilyChild2Name(String cmrFamilyChild2Name) {
        this.cmrFamilyChild2Name = cmrFamilyChild2Name;
    }

    public Date getCmrFamilyChild1Bday() {
        return cmrFamilyChild1Bday;
    }

    public void setCmrFamilyChild1Bday(Date cmrFamilyChild1Bday) {
        this.cmrFamilyChild1Bday = cmrFamilyChild1Bday;
    }

    public Date getCmrFamilyChild2Bday() {
        return cmrFamilyChild2Bday;
    }

    public void setCmrFamilyChild2Bday(Date cmrFamilyChild2Bday) {
        this.cmrFamilyChild2Bday = cmrFamilyChild2Bday;
    }

    public String getCmrFamilySpouseName() {
        return cmrFamilySpouseName;
    }

    public void setCmrFamilySpouseName(String cmrFamilySpouseName) {
        this.cmrFamilySpouseName = cmrFamilySpouseName;
    }

    public Date getCmrFamilySpouseBday() {
        return cmrFamilySpouseBday;
    }

    public void setCmrFamilySpouseBday(Date cmrFamilySpouseBday) {
        this.cmrFamilySpouseBday = cmrFamilySpouseBday;
    }

    public Long getCmrMerchantNo() {
        return cmrMerchantNo;
    }

    public void setCmrMerchantNo(Long cmrMerchantNo) {
        this.cmrMerchantNo = cmrMerchantNo;
    }

    public int getCmrUserNo() {
        return cmrUserNo;
    }

    public void setCmrUserNo(int cmrUserNo) {
        this.cmrUserNo = cmrUserNo;
    }

    public CustomerMasterRawdataStatus getCmrStatus() {
        return cmrStatus;
    }

    public void setCmrStatus(CustomerMasterRawdataStatus cmrStatus) {
        this.cmrStatus = cmrStatus;
    }

    public String getCmrProcessingComment() {
        return cmrProcessingComment;
    }

    public void setCmrProcessingComment(String cmrProcessingComment) {
        this.cmrProcessingComment = cmrProcessingComment;
    }

    public Timestamp getCmrTimestamp() {
        return cmrTimestamp;
    }

    public void setCmrTimestamp(Timestamp cmrTimestamp) {
        this.cmrTimestamp = cmrTimestamp;
    }


    @Override
    public String toString() {
        return "CustomerMasterRawdata{" +
                "cmrId=" + cmrId +
                ", cmrBatchIndex=" + cmrBatchIndex +
                ", cmrRowindex=" + cmrRowindex +
                ", cmrLoyaltyId='" + cmrLoyaltyId + '\'' +
                ", cmrEmail='" + cmrEmail + '\'' +
                ", cmrMobile='" + cmrMobile + '\'' +
                ", cmrFirstname='" + cmrFirstname + '\'' +
                ", cmrLastname='" + cmrLastname + '\'' +
                ", cmrAddress='" + cmrAddress + '\'' +
                ", cmrCity='" + cmrCity + '\'' +
                ", cmrState=" + cmrState +
                ", cmrPincode='" + cmrPincode + '\'' +
                ", cmrCountry=" + cmrCountry +
                ", cmrCustomerBirthday=" + cmrCustomerBirthday +
                ", cmrCustomerAnniversary=" + cmrCustomerAnniversary +
                ", cmrFamilyChild1Name='" + cmrFamilyChild1Name + '\'' +
                ", cmrFamilyChild2Name='" + cmrFamilyChild2Name + '\'' +
                ", cmrFamilyChild1Bday=" + cmrFamilyChild1Bday +
                ", cmrFamilyChild2Bday=" + cmrFamilyChild2Bday +
                ", cmrFamilySpouseName='" + cmrFamilySpouseName + '\'' +
                ", cmrFamilySpouseBday=" + cmrFamilySpouseBday +
                ", cmrMerchantNo=" + cmrMerchantNo +
                ", cmrUserNo=" + cmrUserNo +
                ", cmrStatus=" + cmrStatus +
                ", cmrProcessingComment='" + cmrProcessingComment + '\'' +
                ", cmrTimestamp=" + cmrTimestamp +
                '}';
    }
}

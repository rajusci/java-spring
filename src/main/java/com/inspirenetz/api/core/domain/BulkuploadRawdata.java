package com.inspirenetz.api.core.domain;


import com.inspirenetz.api.core.dictionary.BulkUploadRawdataStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "BULKUPLOAD_RAWDATA")
public class BulkuploadRawdata {

    @Id
    @Column(name = "BRD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brdId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_BATCH_INDEX")
    private Long brdBatchIndex;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_LOYALTY_ID")
    private String brdLoyaltyId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_EMAIL")
    private String brdEmail;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_MOBILE")
    private String brdMobile;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_FIRSTNAME")
    private String brdFirstname;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_LASTNAME")
    private String brdLastname;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_ROWINDEX")
    private Integer brdRowindex;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_DATE")
    private Date brdDate;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_TIME")
    private Time brdTime;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_AMOUNT")
    private BigDecimal brdAmount;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_CURRENCY")
    private int brdCurrency;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_PAYMENT_MODE")
    private Integer brdPaymentMode;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_LOCATION")
    private Integer brdLocation;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_TXN_CHANNEL")
    private int brdTxnChannel;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_PAYMENT_REFERENCE")
    private String brdPaymentReference;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_STATUS")
    private BulkUploadRawdataStatus brdStatus;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_QUANTITY")
    private int brdQuantity;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_CUSTOMER_BIRTHDAY")
    private Date brdCustomerBirthday;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_CUSTOMER_ANNIVERSARY")
    private Date brdCustomerAnniversary;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_AGEGROUP")
    private Integer brdAgegroup;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_PROFESSION")
    private Integer brdProfession;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_ADDRESS")
    private String brdAddress;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_CITY")
    private String brdCity;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_STATE")
    private Integer brdState;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_PINCODE")
    private String brdPincode;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_FAMILY_CHILD1_NAME")
    private String brdFamilyChild1Name;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_FAMILY_CHILD2_NAME")
    private String brdFamilyChild2Name;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_FAMILY_CHILD1_BDAY")
    private Date brdFamilyChild1Bday;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_FAMILY_CHILD2_BDAY")
    private Date brdFamilyChild2Bday;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_FAMILY_SPOUSE_NAME")
    private String brdFamilySpouseName;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_FAMILY_SPOUSE_BDAY")
    private Date brdFamilySpouseBday;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_MERCHANT_NO")
    private Long brdMerchantNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_USER_NO")
    private Long brdUserNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "BRD_PROCESSING_COMMENT")
    private String brdProcessingComment;


    public Long getBrdId() {
        return brdId;
    }

    public void setBrdId(Long brdId) {
        this.brdId = brdId;
    }

    public Long getBrdBatchIndex() {
        return brdBatchIndex;
    }

    public void setBrdBatchIndex(Long brdBatchIndex) {
        this.brdBatchIndex = brdBatchIndex;
    }

    public String getBrdLoyaltyId() {
        return brdLoyaltyId;
    }

    public void setBrdLoyaltyId(String brdLoyaltyId) {
        this.brdLoyaltyId = brdLoyaltyId;
    }

    public String getBrdEmail() {
        return brdEmail;
    }

    public void setBrdEmail(String brdEmail) {
        this.brdEmail = brdEmail;
    }

    public String getBrdMobile() {
        return brdMobile;
    }

    public void setBrdMobile(String brdMobile) {
        this.brdMobile = brdMobile;
    }

    public String getBrdFirstname() {
        return brdFirstname;
    }

    public void setBrdFirstname(String brdFirstname) {
        this.brdFirstname = brdFirstname;
    }

    public String getBrdLastname() {
        return brdLastname;
    }

    public void setBrdLastname(String brdLastname) {
        this.brdLastname = brdLastname;
    }

    public Integer getBrdRowindex() {
        return brdRowindex;
    }

    public void setBrdRowindex(Integer brdRowindex) {
        this.brdRowindex = brdRowindex;
    }

    public Date getBrdDate() {
        return brdDate;
    }

    public void setBrdDate(Date brdDate) {
        this.brdDate = brdDate;
    }

    public Time getBrdTime() {
        return brdTime;
    }

    public void setBrdTime(Time brdTime) {
        this.brdTime = brdTime;
    }

    public BigDecimal getBrdAmount() {
        return brdAmount;
    }

    public void setBrdAmount(BigDecimal brdAmount) {
        this.brdAmount = brdAmount;
    }

    public int getBrdCurrency() {
        return brdCurrency;
    }

    public void setBrdCurrency(int brdCurrency) {
        this.brdCurrency = brdCurrency;
    }

    public Integer getBrdPaymentMode() {
        return brdPaymentMode;
    }

    public void setBrdPaymentMode(Integer brdPaymentMode) {
        this.brdPaymentMode = brdPaymentMode;
    }

    public Integer getBrdLocation() {
        return brdLocation;
    }

    public void setBrdLocation(Integer brdLocation) {
        this.brdLocation = brdLocation;
    }

    public int getBrdTxnChannel() {
        return brdTxnChannel;
    }

    public void setBrdTxnChannel(int brdTxnChannel) {
        this.brdTxnChannel = brdTxnChannel;
    }

    public String getBrdPaymentReference() {
        return brdPaymentReference;
    }

    public void setBrdPaymentReference(String brdPaymentReference) {
        this.brdPaymentReference = brdPaymentReference;
    }

    public BulkUploadRawdataStatus getBrdStatus() {
        return brdStatus;
    }

    public void setBrdStatus(BulkUploadRawdataStatus brdStatus) {
        this.brdStatus = brdStatus;
    }

    public int getBrdQuantity() {
        return brdQuantity;
    }

    public void setBrdQuantity(int brdQuantity) {
        this.brdQuantity = brdQuantity;
    }

    public Date getBrdCustomerBirthday() {
        return brdCustomerBirthday;
    }

    public void setBrdCustomerBirthday(Date brdCustomerBirthday) {
        this.brdCustomerBirthday = brdCustomerBirthday;
    }

    public Date getBrdCustomerAnniversary() {
        return brdCustomerAnniversary;
    }

    public void setBrdCustomerAnniversary(Date brdCustomerAnniversary) {
        this.brdCustomerAnniversary = brdCustomerAnniversary;
    }

    public Integer getBrdAgegroup() {
        return brdAgegroup;
    }

    public void setBrdAgegroup(Integer brdAgegroup) {
        this.brdAgegroup = brdAgegroup;
    }

    public Integer getBrdProfession() {
        return brdProfession;
    }

    public void setBrdProfession(Integer brdProfession) {
        this.brdProfession = brdProfession;
    }

    public String getBrdAddress() {
        return brdAddress;
    }

    public void setBrdAddress(String brdAddress) {
        this.brdAddress = brdAddress;
    }

    public String getBrdCity() {
        return brdCity;
    }

    public void setBrdCity(String brdCity) {
        this.brdCity = brdCity;
    }

    public Integer getBrdState() {
        return brdState;
    }

    public void setBrdState(Integer brdState) {
        this.brdState = brdState;
    }

    public String getBrdPincode() {
        return brdPincode;
    }

    public void setBrdPincode(String brdPincode) {
        this.brdPincode = brdPincode;
    }

    public String getBrdFamilyChild1Name() {
        return brdFamilyChild1Name;
    }

    public void setBrdFamilyChild1Name(String brdFamilyChild1Name) {
        this.brdFamilyChild1Name = brdFamilyChild1Name;
    }

    public String getBrdFamilyChild2Name() {
        return brdFamilyChild2Name;
    }

    public void setBrdFamilyChild2Name(String brdFamilyChild2Name) {
        this.brdFamilyChild2Name = brdFamilyChild2Name;
    }

    public Date getBrdFamilyChild1Bday() {
        return brdFamilyChild1Bday;
    }

    public void setBrdFamilyChild1Bday(Date brdFamilyChild1Bday) {
        this.brdFamilyChild1Bday = brdFamilyChild1Bday;
    }

    public Date getBrdFamilyChild2Bday() {
        return brdFamilyChild2Bday;
    }

    public void setBrdFamilyChild2Bday(Date brdFamilyChild2Bday) {
        this.brdFamilyChild2Bday = brdFamilyChild2Bday;
    }

    public String getBrdFamilySpouseName() {
        return brdFamilySpouseName;
    }

    public void setBrdFamilySpouseName(String brdFamilySpouseName) {
        this.brdFamilySpouseName = brdFamilySpouseName;
    }

    public Date getBrdFamilySpouseBday() {
        return brdFamilySpouseBday;
    }

    public void setBrdFamilySpouseBday(Date brdFamilySpouseBday) {
        this.brdFamilySpouseBday = brdFamilySpouseBday;
    }

    public Long getBrdMerchantNo() {
        return brdMerchantNo;
    }

    public void setBrdMerchantNo(Long brdMerchantNo) {
        this.brdMerchantNo = brdMerchantNo;
    }

    public Long getBrdUserNo() {
        return brdUserNo;
    }

    public void setBrdUserNo(Long brdUserNo) {
        this.brdUserNo = brdUserNo;
    }

    public String getBrdProcessingComment() {
        return brdProcessingComment;
    }

    public void setBrdProcessingComment(String brdProcessingComment) {
        this.brdProcessingComment = brdProcessingComment;
    }


    @Override
    public String toString() {
        return "BulkuploadRawdata{" +
                "brdId=" + brdId +
                ", brdBatchIndex=" + brdBatchIndex +
                ", brdLoyaltyId='" + brdLoyaltyId + '\'' +
                ", brdEmail='" + brdEmail + '\'' +
                ", brdMobile='" + brdMobile + '\'' +
                ", brdFirstname='" + brdFirstname + '\'' +
                ", brdLastname='" + brdLastname + '\'' +
                ", brdRowindex=" + brdRowindex +
                ", brdDate=" + brdDate +
                ", brdTime=" + brdTime +
                ", brdAmount=" + brdAmount +
                ", brdCurrency=" + brdCurrency +
                ", brdPaymentMode=" + brdPaymentMode +
                ", brdLocation=" + brdLocation +
                ", brdTxnChannel=" + brdTxnChannel +
                ", brdPaymentReference='" + brdPaymentReference + '\'' +
                ", brdStatus=" + brdStatus +
                ", brdQuantity=" + brdQuantity +
                ", brdCustomerBirthday=" + brdCustomerBirthday +
                ", brdCustomerAnniversary=" + brdCustomerAnniversary +
                ", brdAgegroup=" + brdAgegroup +
                ", brdProfession=" + brdProfession +
                ", brdAddress='" + brdAddress + '\'' +
                ", brdCity='" + brdCity + '\'' +
                ", brdState=" + brdState +
                ", brdPincode='" + brdPincode + '\'' +
                ", brdFamilyChild1Name='" + brdFamilyChild1Name + '\'' +
                ", brdFamilyChild2Name='" + brdFamilyChild2Name + '\'' +
                ", brdFamilyChild1Bday=" + brdFamilyChild1Bday +
                ", brdFamilyChild2Bday=" + brdFamilyChild2Bday +
                ", brdFamilySpouseName='" + brdFamilySpouseName + '\'' +
                ", brdFamilySpouseBday=" + brdFamilySpouseBday +
                ", brdMerchantNo=" + brdMerchantNo +
                ", brdUserNo=" + brdUserNo +
                ", brdProcessingComment='" + brdProcessingComment + '\'' +
                '}';
    }
}

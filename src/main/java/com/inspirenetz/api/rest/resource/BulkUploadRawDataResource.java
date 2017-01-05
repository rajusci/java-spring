package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.BulkUploadRawdataStatus;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by ameen on 14/9/15.
 */
public class BulkUploadRawDataResource extends BaseResource {


    private Long brdId;

    private int brdBatchIndex;

    private String brdLoyaltyId;

    private String brdEmail;

    private String brdMobile;

    private String brdFirstname;

    private String brdLastname;

    private Integer brdRowindex;

    private Date brdDate;

    private Time brdTime;


    private BigDecimal brdAmount;


    private int brdCurrency;


    private Integer brdPaymentMode;


    private Integer brdLocation;
    private int brdTxnChannel;


    private String brdPaymentReference;


    private BulkUploadRawdataStatus brdStatus;


    private int brdQuantity;

    private Date brdCustomerBirthday;

    private Date brdCustomerAnniversary;


    private Integer brdAgegroup;


    private Integer brdProfession;


    private String brdAddress;

    private String brdCity;


    private Integer brdState;

    private String brdPincode;

    private String brdFamilyChild1Name;

    private String brdFamilyChild2Name;


    private Date brdFamilyChild1Bday;

    private Date brdFamilyChild2Bday;

    private String brdFamilySpouseName;

    private Date brdFamilySpouseBday;
    private Long brdMerchantNo;

    private Long brdUserNo;
    private String brdProcessingComment;

    public Long getBrdId() {
        return brdId;
    }

    public void setBrdId(Long brdId) {
        this.brdId = brdId;
    }

    public int getBrdBatchIndex() {
        return brdBatchIndex;
    }

    public void setBrdBatchIndex(int brdBatchIndex) {
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
}

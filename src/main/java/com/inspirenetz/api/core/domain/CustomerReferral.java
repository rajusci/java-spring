package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.CustomerReferralStatus;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.TransferPointSettingCompatibility;
import com.inspirenetz.api.core.dictionary.TransferPointSettingLinkedEligibity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by fayiz on 27/4/15.
 */
@Entity
@Table(name = "CUSTOMER_REFERRALS")
public class CustomerReferral extends AuditedEntity {


    @Id
    @Column(name = "CSR_ID", nullable = false, insertable = true, updatable = false, length = 10, precision = 0)
    @GeneratedValue  ( strategy = GenerationType.IDENTITY)
    private Long csrId;

    @Basic
    @Column(name = "CSR_MERCHANT_NO", nullable = false, insertable = true, updatable = false, length = 10, precision = 0)
    private Long csrMerchantNo = 0L;

    @Basic
    @Column(name = "CSR_LOYALTY_ID", nullable = false, insertable = true, updatable = false)
    private String csrLoyaltyId ;

    @Basic
    @Column(name = "CSR_FNAME", nullable = false, insertable = true, updatable = false)
    private String csrFName ;

    @Basic
    @Column(name = "CSR_REF_NAME", nullable = true, insertable = true, updatable = true)
    private String csrRefName ;

    @Basic
    @Column(name = "CSR_REF_MOBILE", nullable = true, insertable = true, updatable = true)
    private String csrRefMobile ;

    @Basic
    @Column(name = "CSR_REF_EMAIL", nullable = true, insertable = true, updatable = true)
    private String csrRefEmail;

    @Column(name = "CSR_REF_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer csrRefStatus= CustomerReferralStatus.NEW;

    @Basic
    @Column(name = "CSR_REF_ADDRESS", nullable = true, insertable = true, updatable = true)
    private String csrRefAddress;

    @Basic
    @Column(name = "CSR_REF_TIMESTAMP", nullable = false, insertable = true, updatable = false)
    private Timestamp csrRefTimeStamp;

    @Basic
    @Column(name = "CSR_USER_NO", nullable = true, insertable = true, updatable = true)
    private Long csrUserNo;

    @Basic
    @Column(name = "CSR_PRODUCT", nullable = true, insertable = true, updatable = true)
    private String csrProduct;

    @Basic
    @Column(name = "CSR_LOCATION", nullable = true, insertable = true, updatable = true)
    private Long csrLocation;

    @Basic
    @Column(name = "CSR_REF_NO", nullable = true, insertable = true, updatable = true)
    private String csrRefNo="0";

    @Column(name = "CSR_POINTS",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Double csrPoints  = 0.0;

    @Column(name = "CSR_EARNED_STATUS", nullable = true)
    private boolean csrEarnedStatus = false;

    @Column(name = "CSR_REF_MOBILE_COUNTRY_CODE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String csrRefMobileCountryCode  = "";


    @PrePersist
    private void populateInsertFields() {

        // Get the current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Set the registerTimestamp to current time
        csrRefTimeStamp = new Timestamp(timestamp.getTime());

    }

    @PreUpdate
    private void populateUpdateFields() {

        // Get the current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Set the registerTimestamp to current time
        csrRefTimeStamp = new Timestamp(timestamp.getTime());
    }




    public Long getCsrId() {
        return csrId;
    }

    public void setCsrId(Long csrId) {
        this.csrId = csrId;
    }

    public Long getCsrMerchantNo() {
        return csrMerchantNo;
    }

    public void setCsrMerchantNo(Long csrMerchantNo) {
        this.csrMerchantNo = csrMerchantNo;
    }

    public String getCsrLoyaltyId() {
        return csrLoyaltyId;
    }

    public void setCsrLoyaltyId(String csrLoyaltyId) {
        this.csrLoyaltyId = csrLoyaltyId;
    }

    public String getCsrRefName() {
        return csrRefName;
    }

    public void setCsrRefName(String csrRefName) {
        this.csrRefName = csrRefName;
    }

    public String getCsrRefMobile() {
        return csrRefMobile;
    }

    public void setCsrRefMobile(String csrRefMobile) {
        this.csrRefMobile = csrRefMobile;
    }

    public String getCsrRefEmail() {
        return csrRefEmail;
    }

    public void setCsrRefEmail(String csrRefEmail) {
        this.csrRefEmail = csrRefEmail;
    }

    public Integer getCsrRefStatus() {
        return csrRefStatus;
    }

    public void setCsrRefStatus(Integer csrRefStatus) {
        this.csrRefStatus = csrRefStatus;
    }

    public Timestamp getCsrRefTimeStamp() {
        return csrRefTimeStamp;
    }

    public void setCsrRefTimeStamp(Timestamp csrRefTimeStamp) {
        this.csrRefTimeStamp = csrRefTimeStamp;
    }

    public String getCsrRefAddress() {
        return csrRefAddress;
    }

    public void setCsrRefAddress(String csrRefAddress) {
        this.csrRefAddress = csrRefAddress;
    }

    public Long getCsrUserNo() {
        return csrUserNo;
    }

    public void setCsrUserNo(Long csrUserNo) {
        this.csrUserNo = csrUserNo;
    }

    public String getCsrProduct() {
        return csrProduct;
    }

    public void setCsrProduct(String csrProduct) {
        this.csrProduct = csrProduct;
    }

    public Long getCsrLocation() {
        return csrLocation;
    }

    public void setCsrLocation(Long csrLocation) {
        this.csrLocation = csrLocation;
    }

    public String getCsrFName() {
        return csrFName;
    }

    public void setCsrFName(String csrFName) {
        this.csrFName = csrFName;
    }

    public String getCsrRefNo() {
        return csrRefNo;
    }

    public void setCsrRefNo(String csrRefNo) {
        this.csrRefNo = csrRefNo;
    }

    public String getCsrRefMobileCountryCode() {
        return csrRefMobileCountryCode;
    }

    public void setCsrRefMobileCountryCode(String csrRefMobileCountryCode) {
        this.csrRefMobileCountryCode = csrRefMobileCountryCode;
    }

    public boolean isCsrEarnedStatus() {
        return csrEarnedStatus;
    }

    public void setCsrEarnedStatus(boolean csrEarnedStatus) {
        this.csrEarnedStatus = csrEarnedStatus;
    }

    public Double getCsrPoints() {
        return csrPoints;
    }

    public void setCsrPoints(Double csrPoints) {
        this.csrPoints = csrPoints;
    }

    @Override
    public String toString() {
        return "CustomerReferral{" +
                "csrId=" + csrId +
                ", csrMerchantNo=" + csrMerchantNo +
                ", csrLoyaltyId='" + csrLoyaltyId + '\'' +
                ", csrFName='" + csrFName + '\'' +
                ", csrRefName='" + csrRefName + '\'' +
                ", csrRefMobile='" + csrRefMobile + '\'' +
                ", csrRefEmail='" + csrRefEmail + '\'' +
                ", csrRefStatus=" + csrRefStatus +
                ", csrRefAddress='" + csrRefAddress + '\'' +
                ", csrRefTimeStamp=" + csrRefTimeStamp +
                ", csrUserNo=" + csrUserNo +
                ", csrProduct='" + csrProduct + '\'' +
                ", csrLocation=" + csrLocation +
                ", csrRefNo='" + csrRefNo + '\'' +
                ", csrRefMobileCountryCode='" + csrRefMobileCountryCode + '\'' +
                ", csrEarnedStatus=" + csrEarnedStatus +
                '}';
    }
}

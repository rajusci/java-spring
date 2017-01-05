package com.inspirenetz.api.core.domain;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 27/4/14.
 */
@Entity
@Table(name = "SALES_MASTER_RAWDATA")
public class SalesMasterRawdata {

    @Id
    @Column(name = "SMR_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long smrId; // Create the java util Date

    @Basic
    @Column(name = "SMR_BATCH_INDEX", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long smrBatchIndex = new Long(0L);

    @Basic
    @Column(name = "SMR_ROWINDEX", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer smrRowindex = new Integer(0);

    @Basic
    @Column(name = "SMR_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int smrType = 0;

    @Basic
    @Column(name = "SMR_LOYALTY_ID", nullable = true, insertable = true, updatable = true, length = 50, precision = 0)
    @NotEmpty
    @NotNull
    private String smrLoyaltyId;

    @Basic
    @Column(name = "SMR_DATE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Date smrDate;

    @Basic
    @Column(name = "SMR_TIME", nullable = false, insertable = true, updatable = true, length = 8, precision = 0)
    private Time smrTime;

    @Basic
    @Column(name = "SMR_AMOUNT", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    private Double smrAmount = new Double(0);

    @Basic
    @Column(name = "SMR_CURRENCY", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int smrCurrency = 356;

    @Basic
    @Column(name = "SMR_PAYMENT_MODE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer smrPaymentMode = new Integer(1);

    @Basic
    @Column(name = "SMR_LOCATION", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer smrLocation = new Integer(0);

    @Basic
    @Column(name = "SMR_TXN_CHANNEL", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int smrTxnChannel = 1;

    @Basic
    @Column(name = "SMR_ALTER_STATUS", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int smrAlterStatus = 0;

    @Basic
    @Column(name = "SMR_PAYMENT_REFERENCE", nullable = true, insertable = true, updatable = true, length = 300, precision = 0)
    private String smrPaymentReference;

    @Basic
    @Column(name = "SMR_QUANTITY", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int smrQuantity = 1;

    @Basic
    @Column(name = "SMR_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int smrMerchantNo = 0;

    @Basic
    @Column(name = "SMR_USER_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int smrUserNo = 0;

    @Basic
    @Column(name = "SMR_STATUS", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int smrStatus = 1;

    @Basic
    @Column(name = "SMR_PROCESSING_COMMENT", nullable = true, insertable = true, updatable = true, length = 1000, precision = 0)
    private String smrProcessingComment;
    
    @Basic
    @Column(name = "SMR_TIMESTAMP", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    private Timestamp smrTimestamp;


    public Long getSmrId() {
        return smrId;
    }

    public void setSmrId(Long smrId) {
        this.smrId = smrId;
    }

    public Long getSmrBatchIndex() {
        return smrBatchIndex;
    }

    public void setSmrBatchIndex(Long smrBatchIndex) {
        this.smrBatchIndex = smrBatchIndex;
    }

    public Integer getSmrRowindex() {
        return smrRowindex;
    }

    public void setSmrRowindex(Integer smrRowindex) {
        this.smrRowindex = smrRowindex;
    }

    public int getSmrType() {
        return smrType;
    }

    public void setSmrType(int smrType) {
        this.smrType = smrType;
    }

    public String getSmrLoyaltyId() {
        return smrLoyaltyId;
    }

    public void setSmrLoyaltyId(String smrLoyaltyId) {
        this.smrLoyaltyId = smrLoyaltyId;
    }

    public Date getSmrDate() {
        return smrDate;
    }

    public void setSmrDate(Date smrDate) {
        this.smrDate = smrDate;
    }

    public Time getSmrTime() {
        return smrTime;
    }

    public void setSmrTime(Time smrTime) {
        this.smrTime = smrTime;
    }

    public Double getSmrAmount() {
        return smrAmount;
    }

    public void setSmrAmount(Double smrAmount) {
        this.smrAmount = smrAmount;
    }

    public int getSmrCurrency() {
        return smrCurrency;
    }

    public void setSmrCurrency(int smrCurrency) {
        this.smrCurrency = smrCurrency;
    }

    public Integer getSmrPaymentMode() {
        return smrPaymentMode;
    }

    public void setSmrPaymentMode(Integer smrPaymentMode) {
        this.smrPaymentMode = smrPaymentMode;
    }

    public Integer getSmrLocation() {
        return smrLocation;
    }

    public void setSmrLocation(Integer smrLocation) {
        this.smrLocation = smrLocation;
    }

    public int getSmrTxnChannel() {
        return smrTxnChannel;
    }

    public void setSmrTxnChannel(int smrTxnChannel) {
        this.smrTxnChannel = smrTxnChannel;
    }

    public int getSmrAlterStatus() {
        return smrAlterStatus;
    }

    public void setSmrAlterStatus(int smrAlterStatus) {
        this.smrAlterStatus = smrAlterStatus;
    }

    public String getSmrPaymentReference() {
        return smrPaymentReference;
    }

    public void setSmrPaymentReference(String smrPaymentReference) {
        this.smrPaymentReference = smrPaymentReference;
    }

    public int getSmrQuantity() {
        return smrQuantity;
    }

    public void setSmrQuantity(int smrQuantity) {
        this.smrQuantity = smrQuantity;
    }

    public int getSmrMerchantNo() {
        return smrMerchantNo;
    }

    public void setSmrMerchantNo(int smrMerchantNo) {
        this.smrMerchantNo = smrMerchantNo;
    }

    public int getSmrUserNo() {
        return smrUserNo;
    }

    public void setSmrUserNo(int smrUserNo) {
        this.smrUserNo = smrUserNo;
    }

    public int getSmrStatus() {
        return smrStatus;
    }

    public void setSmrStatus(int smrStatus) {
        this.smrStatus = smrStatus;
    }

    public String getSmrProcessingComment() {
        return smrProcessingComment;
    }

    public void setSmrProcessingComment(String smrProcessingComment) {
        this.smrProcessingComment = smrProcessingComment;
    }

    public Timestamp getSmrTimestamp() {
        return smrTimestamp;
    }

    public void setSmrTimestamp(Timestamp smrTimestamp) {
        this.smrTimestamp = smrTimestamp;
    }


    @PrePersist
    protected void populateFields() {

        // Create the java util Date
        java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());

        // check if the date field is empty
        if ( smrDate == null ) {

            // Set the sql date
            smrDate = new Date(utilDate.getTime());

        }


        // Check if prcTime is specified
        if ( smrTime == null ) {

            // Set the time
            smrTime = new Time(utilDate.getTime());

        }


        // Set the timestamp to be the timestamp of current time millis
        if ( smrTimestamp == null ) {

            smrTimestamp = new Timestamp(System.currentTimeMillis());

        }


    }
}

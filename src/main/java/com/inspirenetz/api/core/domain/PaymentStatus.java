package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.PaymentStatusAuthority;
import com.inspirenetz.api.core.dictionary.PaymentStatusMode;
import com.inspirenetz.api.core.dictionary.PaymentStatusStatus;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by sandheepgr on 16/8/14.
 */
@Entity
@Table(name = "PAYMENT_STATUS")
public class PaymentStatus extends AuditedEntity {

    @Id
    @Column(name = "PYS_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pysId;

    @Basic
    @Column(name = "PYS_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long pysMerchantNo =0L;

    @Basic
    @Column(name = "PYS_MODULE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer pysModule = 9;

    @Basic
    @Column(name = "PYS_LOYALTY_ID", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    private String pysLoyaltyId = "";

    @Basic
    @Column(name = "PYS_DATE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Date pysDate ;

    @Basic
    @Column(name = "PYS_TIME", nullable = false, insertable = true, updatable = true, length = 8, precision = 0)
    private Time pysTime;

    @Basic
    @Column(name = "PYS_INTERNAL_REF", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    private String pysInternalRef = "";

    @Basic
    @Column(name = "PYS_AMOUNT", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    private Double pysAmount = 0.0;

    @Basic
    @Column(name = "PYS_PAYMENT_MODE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer pysPaymentMode = PaymentStatusMode.CHARGE_CARD;

    @Basic
    @Column(name = "PYS_AUTHORITY", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer pysAuthority = PaymentStatusAuthority.INSPIRENETZ;

    @Basic
    @Column(name = "PYS_TRAN_RESP_CODE", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    private String pysTranRespCode = "";

    @Basic
    @Column(name = "PYS_TRAN_APPROVAL_CODE", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    private String pysTranApprovalCode = "";

    @Basic
    @Column(name = "PYS_TRAN_RECEIPT_NUMBER", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    private String pysTranReceiptNumber = "";

    @Basic
    @Column(name = "PYS_TRAN_AUTH_ID", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    private String pysTranAuthId = "";

    @Basic
    @Column(name = "PYS_TRAN_BATCH_NUM", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    private String pysTranBatchNum ="";

    @Basic
    @Column(name = "PYS_TRANSACTION_NUMBER", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    private String pysTransactionNumber;

    @Basic
    @Column(name = "PYS_STATUS", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer pysStatus = com.inspirenetz.api.core.dictionary.PaymentStatus.PAYMENT_STATUS_NOT_PAID;

    @Basic
    @Column(name = "PYS_STATUS_TEXT", nullable = true, insertable = true, updatable = true, length = 200, precision = 0)
    private String pysStatusText = "";

    @Basic
    @Column(name = "PYS_CURRENT_STATUS", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer pysCurrentStatus = PaymentStatusStatus.UNKNOWN;

    @Basic
    @Column(name = "PYS_UPDATE_TIMESTAMP", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    private Timestamp pysUpdateTimestamp;

    @Basic
    @Column(name = "PYS_UPDATE_USER", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Long pysUpdateUser;


    @PrePersist
    @PreUpdate
    protected void populateFields() {

        // Create the Calendar object
        Calendar c = Calendar.getInstance();

        // Set the time
        c.setTime(new java.util.Date());



        // Set the timestamp
        if ( pysUpdateTimestamp == null ){

            pysUpdateTimestamp = new Timestamp(System.currentTimeMillis());

        }


        // Set the time if its null
        if ( pysTime == null ) {

            pysTime = new Time(pysUpdateTimestamp.getTime());

        }


        // Set the date if its null
        if ( pysDate == null ) {

            pysDate = new Date(pysUpdateTimestamp.getTime());

        }

    }


    public Long getPysId() {
        return pysId;
    }

    public void setPysId(Long pysId) {
        this.pysId = pysId;
    }

    public Long getPysMerchantNo() {
        return pysMerchantNo;
    }

    public void setPysMerchantNo(Long pysMerchantNo) {
        this.pysMerchantNo = pysMerchantNo;
    }

    public Integer getPysModule() {
        return pysModule;
    }

    public void setPysModule(Integer pysModule) {
        this.pysModule = pysModule;
    }

    public String getPysLoyaltyId() {
        return pysLoyaltyId;
    }

    public void setPysLoyaltyId(String pysLoyaltyId) {
        this.pysLoyaltyId = pysLoyaltyId;
    }

    public Date getPysDate() {
        return pysDate;
    }

    public void setPysDate(Date pysDate) {
        this.pysDate = pysDate;
    }

    public Time getPysTime() {
        return pysTime;
    }

    public void setPysTime(Time pysTime) {
        this.pysTime = pysTime;
    }

    public String getPysInternalRef() {
        return pysInternalRef;
    }

    public void setPysInternalRef(String pysInternalRef) {
        this.pysInternalRef = pysInternalRef;
    }

    public Double getPysAmount() {
        return pysAmount;
    }

    public void setPysAmount(Double pysAmount) {
        this.pysAmount = pysAmount;
    }

    public Integer getPysPaymentMode() {
        return pysPaymentMode;
    }

    public void setPysPaymentMode(Integer pysPaymentMode) {
        this.pysPaymentMode = pysPaymentMode;
    }

    public Integer getPysAuthority() {
        return pysAuthority;
    }

    public void setPysAuthority(Integer pysAuthority) {
        this.pysAuthority = pysAuthority;
    }

    public String getPysTranRespCode() {
        return pysTranRespCode;
    }

    public void setPysTranRespCode(String pysTranRespCode) {
        this.pysTranRespCode = pysTranRespCode;
    }

    public String getPysTranApprovalCode() {
        return pysTranApprovalCode;
    }

    public void setPysTranApprovalCode(String pysTranApprovalCode) {
        this.pysTranApprovalCode = pysTranApprovalCode;
    }

    public String getPysTranReceiptNumber() {
        return pysTranReceiptNumber;
    }

    public void setPysTranReceiptNumber(String pysTranReceiptNumber) {
        this.pysTranReceiptNumber = pysTranReceiptNumber;
    }

    public String getPysTranAuthId() {
        return pysTranAuthId;
    }

    public void setPysTranAuthId(String pysTranAuthId) {
        this.pysTranAuthId = pysTranAuthId;
    }

    public String getPysTranBatchNum() {
        return pysTranBatchNum;
    }

    public void setPysTranBatchNum(String pysTranBatchNum) {
        this.pysTranBatchNum = pysTranBatchNum;
    }

    public String getPysTransactionNumber() {
        return pysTransactionNumber;
    }

    public void setPysTransactionNumber(String pysTransactionNumber) {
        this.pysTransactionNumber = pysTransactionNumber;
    }

    public Integer getPysStatus() {
        return pysStatus;
    }

    public void setPysStatus(Integer pysStatus) {
        this.pysStatus = pysStatus;
    }

    public String getPysStatusText() {
        return pysStatusText;
    }

    public void setPysStatusText(String pysStatusText) {
        this.pysStatusText = pysStatusText;
    }

    public Integer getPysCurrentStatus() {
        return pysCurrentStatus;
    }

    public void setPysCurrentStatus(Integer pysCurrentStatus) {
        this.pysCurrentStatus = pysCurrentStatus;
    }

    public Timestamp getPysUpdateTimestamp() {
        return pysUpdateTimestamp;
    }

    public void setPysUpdateTimestamp(Timestamp pysUpdateTimestamp) {
        this.pysUpdateTimestamp = pysUpdateTimestamp;
    }

    public Long getPysUpdateUser() {
        return pysUpdateUser;
    }

    public void setPysUpdateUser(Long pysUpdateUser) {
        this.pysUpdateUser = pysUpdateUser;
    }

    @Override
    public String toString() {
        return "PaymentStatus{" +
                "pysId=" + pysId +
                ", pysMerchantNo=" + pysMerchantNo +
                ", pysModule=" + pysModule +
                ", pysLoyaltyId='" + pysLoyaltyId + '\'' +
                ", pysDate=" + pysDate +
                ", pysTime=" + pysTime +
                ", pysInternalRef='" + pysInternalRef + '\'' +
                ", pysAmount=" + pysAmount +
                ", pysPaymentMode=" + pysPaymentMode +
                ", pysAuthority=" + pysAuthority +
                ", pysTranRespCode='" + pysTranRespCode + '\'' +
                ", pysTranApprovalCode='" + pysTranApprovalCode + '\'' +
                ", pysTranReceiptNumber='" + pysTranReceiptNumber + '\'' +
                ", pysTranAuthId='" + pysTranAuthId + '\'' +
                ", pysTranBatchNum='" + pysTranBatchNum + '\'' +
                ", pysTransactionNumber='" + pysTransactionNumber + '\'' +
                ", pysStatus=" + pysStatus +
                ", pysStatusText='" + pysStatusText + '\'' +
                ", pysCurrentStatus=" + pysCurrentStatus +
                ", pysUpdateTimestamp=" + pysUpdateTimestamp +
                ", pysUpdateUser=" + pysUpdateUser +
                '}';
    }
}

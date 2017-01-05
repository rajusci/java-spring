package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.OTPRefType;
import com.inspirenetz.api.core.dictionary.OTPStatus;
import com.inspirenetz.api.core.dictionary.OTPType;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 4/9/14.
 */
@Entity
@Table(name = "ONE_TIME_PASSWORD")
public class OneTimePassword extends AuditedEntity {

    @Id
    @Column(name = "OTP_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otpId;

    @Basic
    @Column(name = "OTP_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long otpMerchantNo;

    @Basic
    @Column(name = "OTP_CUSTOMER_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long otpCustomerNo;

    @Basic
    @Column(name = "OTP_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer otpType = OTPType.TRANSFER_POINTS;

    @Basic
    @Column(name = "OTP_CODE", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    private String otpCode;

    @Basic
    @Column(name = "OTP_STATUS", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer otpStatus = OTPStatus.NEW;

    @Basic
    @Column(name = "OTP_EXPIRY", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    private Timestamp otpExpiry;

    @Basic
    @Column(name = "OTP_CREATION_DATE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Date otpCreationDate;

    @Basic
    @Column(name = "OTP_CREATE_TIMESTAMP", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    private Timestamp otpCreateTimestamp;

    @Basic
    @Column(name = "OTP_REF_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer otpRefType = OTPRefType.USER;



    @Basic
    @Column(name = "OTP_REFERENCE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private String otpReference ;

    @PrePersist
    private void populateInsertFields() {

        // Get the current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Set the registerTimestamp to current time
        otpCreateTimestamp = new Timestamp(timestamp.getTime());

    }


    public Long getOtpId() {
        return otpId;
    }

    public void setOtpId(Long otpId) {
        this.otpId = otpId;
    }

    public Long getOtpMerchantNo() {
        return otpMerchantNo;
    }

    public void setOtpMerchantNo(Long otpMerchantNo) {
        this.otpMerchantNo = otpMerchantNo;
    }

    public Long getOtpCustomerNo() {
        return otpCustomerNo;
    }

    public void setOtpCustomerNo(Long otpCustomerNo) {
        this.otpCustomerNo = otpCustomerNo;
    }

    public Integer getOtpType() {
        return otpType;
    }

    public void setOtpType(Integer otpType) {
        this.otpType = otpType;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public Integer getOtpStatus() {
        return otpStatus;
    }

    public void setOtpStatus(Integer otpStatus) {
        this.otpStatus = otpStatus;
    }

    public Timestamp getOtpExpiry() {
        return otpExpiry;
    }

    public void setOtpExpiry(Timestamp otpExpiry) {
        this.otpExpiry = otpExpiry;
    }

    public Date getOtpCreationDate() {
        return otpCreationDate;
    }

    public void setOtpCreationDate(Date otpCreationDate) {
        this.otpCreationDate = otpCreationDate;
    }

    public Timestamp getOtpCreateTimestamp() {
        return otpCreateTimestamp;
    }

    public void setOtpCreateTimestamp(Timestamp otpCreateTimestamp) {
        this.otpCreateTimestamp = otpCreateTimestamp;
    }



    public String getOtpReference() {
        return otpReference;
    }

    public void setOtpReference(String otpReference) {
        this.otpReference = otpReference;
    }

    public Integer getOtpRefType() {
        return otpRefType;
    }

    public void setOtpRefType(Integer otpRefType) {
        this.otpRefType = otpRefType;
    }

    @Override
    public String toString() {
        return "OneTimePassword{" +
                "otpId=" + otpId +
                ", otpMerchantNo=" + otpMerchantNo +
                ", otpCustomerNo=" + otpCustomerNo +
                ", otpType=" + otpType +
                ", otpCode='" + otpCode + '\'' +
                ", otpStatus=" + otpStatus +
                ", otpExpiry=" + otpExpiry +
                ", otpCreationDate=" + otpCreationDate +
                ", otpCreateTimestamp=" + otpCreateTimestamp +
                ", otpRefType=" + otpRefType +
                ", otpReference=" + otpReference +
                '}';
    }
}

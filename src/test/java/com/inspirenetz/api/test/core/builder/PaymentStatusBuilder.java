package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.PaymentStatusAuthority;
import com.inspirenetz.api.core.dictionary.PaymentStatusMode;
import com.inspirenetz.api.core.dictionary.PaymentStatusStatus;
import com.inspirenetz.api.core.domain.PaymentStatus;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 16/8/14.
 */
public class PaymentStatusBuilder {
    private Long pysId;
    private Long pysMerchantNo =0L;
    private Integer pysModule = 9;
    private String pysLoyaltyId = "";
    private Date pysDate ;
    private Time pysTime;
    private String pysInternalRef = "";
    private Double pysAmount = 0.0;
    private Integer pysPaymentMode = PaymentStatusMode.CHARGE_CARD;
    private Integer pysAuthority = PaymentStatusAuthority.INSPIRENETZ;
    private String pysTranRespCode = "";
    private String pysTranApprovalCode = "";
    private String pysTranReceiptNumber = "";
    private String pysTranAuthId = "";
    private String pysTranBatchNum ="";
    private String pysTransactionNumber;
    private Integer pysStatus = com.inspirenetz.api.core.dictionary.PaymentStatus.PAYMENT_STATUS_NOT_PAID;
    private String pysStatusText = "";
    private Integer pysCurrentStatus = PaymentStatusStatus.UNKNOWN;
    private Timestamp pysUpdateTimestamp;
    private Long pysUpdateUser;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private PaymentStatusBuilder() {
    }

    public static PaymentStatusBuilder aPaymentStatus() {
        return new PaymentStatusBuilder();
    }

    public PaymentStatusBuilder withPysId(Long pysId) {
        this.pysId = pysId;
        return this;
    }

    public PaymentStatusBuilder withPysMerchantNo(Long pysMerchantNo) {
        this.pysMerchantNo = pysMerchantNo;
        return this;
    }

    public PaymentStatusBuilder withPysModule(Integer pysModule) {
        this.pysModule = pysModule;
        return this;
    }

    public PaymentStatusBuilder withPysLoyaltyId(String pysLoyaltyId) {
        this.pysLoyaltyId = pysLoyaltyId;
        return this;
    }

    public PaymentStatusBuilder withPysDate(Date pysDate) {
        this.pysDate = pysDate;
        return this;
    }

    public PaymentStatusBuilder withPysTime(Time pysTime) {
        this.pysTime = pysTime;
        return this;
    }

    public PaymentStatusBuilder withPysInternalRef(String pysInternalRef) {
        this.pysInternalRef = pysInternalRef;
        return this;
    }

    public PaymentStatusBuilder withPysAmount(Double pysAmount) {
        this.pysAmount = pysAmount;
        return this;
    }

    public PaymentStatusBuilder withPysPaymentMode(Integer pysPaymentMode) {
        this.pysPaymentMode = pysPaymentMode;
        return this;
    }

    public PaymentStatusBuilder withPysAuthority(Integer pysAuthority) {
        this.pysAuthority = pysAuthority;
        return this;
    }

    public PaymentStatusBuilder withPysTranRespCode(String pysTranRespCode) {
        this.pysTranRespCode = pysTranRespCode;
        return this;
    }

    public PaymentStatusBuilder withPysTranApprovalCode(String pysTranApprovalCode) {
        this.pysTranApprovalCode = pysTranApprovalCode;
        return this;
    }

    public PaymentStatusBuilder withPysTranReceiptNumber(String pysTranReceiptNumber) {
        this.pysTranReceiptNumber = pysTranReceiptNumber;
        return this;
    }

    public PaymentStatusBuilder withPysTranAuthId(String pysTranAuthId) {
        this.pysTranAuthId = pysTranAuthId;
        return this;
    }

    public PaymentStatusBuilder withPysTranBatchNum(String pysTranBatchNum) {
        this.pysTranBatchNum = pysTranBatchNum;
        return this;
    }

    public PaymentStatusBuilder withPysTransactionNumber(String pysTransactionNumber) {
        this.pysTransactionNumber = pysTransactionNumber;
        return this;
    }

    public PaymentStatusBuilder withPysStatus(Integer pysStatus) {
        this.pysStatus = pysStatus;
        return this;
    }

    public PaymentStatusBuilder withPysStatusText(String pysStatusText) {
        this.pysStatusText = pysStatusText;
        return this;
    }

    public PaymentStatusBuilder withPysCurrentStatus(Integer pysCurrentStatus) {
        this.pysCurrentStatus = pysCurrentStatus;
        return this;
    }

    public PaymentStatusBuilder withPysUpdateTimestamp(Timestamp pysUpdateTimestamp) {
        this.pysUpdateTimestamp = pysUpdateTimestamp;
        return this;
    }

    public PaymentStatusBuilder withPysUpdateUser(Long pysUpdateUser) {
        this.pysUpdateUser = pysUpdateUser;
        return this;
    }

    public PaymentStatusBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PaymentStatusBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public PaymentStatusBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public PaymentStatusBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public PaymentStatus build() {
        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setPysId(pysId);
        paymentStatus.setPysMerchantNo(pysMerchantNo);
        paymentStatus.setPysModule(pysModule);
        paymentStatus.setPysLoyaltyId(pysLoyaltyId);
        paymentStatus.setPysDate(pysDate);
        paymentStatus.setPysTime(pysTime);
        paymentStatus.setPysInternalRef(pysInternalRef);
        paymentStatus.setPysAmount(pysAmount);
        paymentStatus.setPysPaymentMode(pysPaymentMode);
        paymentStatus.setPysAuthority(pysAuthority);
        paymentStatus.setPysTranRespCode(pysTranRespCode);
        paymentStatus.setPysTranApprovalCode(pysTranApprovalCode);
        paymentStatus.setPysTranReceiptNumber(pysTranReceiptNumber);
        paymentStatus.setPysTranAuthId(pysTranAuthId);
        paymentStatus.setPysTranBatchNum(pysTranBatchNum);
        paymentStatus.setPysTransactionNumber(pysTransactionNumber);
        paymentStatus.setPysStatus(pysStatus);
        paymentStatus.setPysStatusText(pysStatusText);
        paymentStatus.setPysCurrentStatus(pysCurrentStatus);
        paymentStatus.setPysUpdateTimestamp(pysUpdateTimestamp);
        paymentStatus.setPysUpdateUser(pysUpdateUser);
        paymentStatus.setCreatedAt(createdAt);
        paymentStatus.setCreatedBy(createdBy);
        paymentStatus.setUpdatedAt(updatedAt);
        paymentStatus.setUpdatedBy(updatedBy);
        return paymentStatus;
    }
}

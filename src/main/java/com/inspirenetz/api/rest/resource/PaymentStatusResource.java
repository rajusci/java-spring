package com.inspirenetz.api.rest.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inspirenetz.api.core.dictionary.PaymentStatusAuthority;
import com.inspirenetz.api.core.dictionary.PaymentStatusMode;
import com.inspirenetz.api.core.dictionary.PaymentStatusStatus;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class PaymentStatusResource extends BaseResource {

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

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp pysUpdateTimestamp;


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


    @Override
    public String toString() {
        return "PaymentStatusResource{" +
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
                '}';
    }
}

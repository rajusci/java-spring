package com.inspirenetz.api.rest.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inspirenetz.api.core.dictionary.CardTransactionType;

import java.sql.Timestamp;

/**
 * Created by sandheepgr on 22/7/14.
 */
public class CardTransactionResource extends BaseResource {


    private Long ctxTxnNo;

    private String ctxCardNumber = "";

    private Long ctxCrmId = 0L;

    private Integer ctxTxnType = CardTransactionType.ISSUE;

    private Double ctxTxnAmount = 0.0;

    private String ctxReference =  "";

    private Long ctxTxnTerminal = 0L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    private Timestamp ctxTxnTimestamp;

    private Double ctxCardBalance = 0.0;

    private Long ctxLocation = 0l;

    private Long ctxUserNo = 0l;

    private String merchantName = "";

    private String location = "";


    public Long getCtxTxnNo() {
        return ctxTxnNo;
    }

    public void setCtxTxnNo(Long ctxTxnNo) {
        this.ctxTxnNo = ctxTxnNo;
    }

    public String getCtxCardNumber() {
        return ctxCardNumber;
    }

    public void setCtxCardNumber(String ctxCardNumber) {
        this.ctxCardNumber = ctxCardNumber;
    }

    public Long getCtxCrmId() {
        return ctxCrmId;
    }

    public void setCtxCrmId(Long ctxCrmId) {
        this.ctxCrmId = ctxCrmId;
    }

    public Integer getCtxTxnType() {
        return ctxTxnType;
    }

    public void setCtxTxnType(Integer ctxTxnType) {
        this.ctxTxnType = ctxTxnType;
    }

    public Double getCtxTxnAmount() {
        return ctxTxnAmount;
    }

    public void setCtxTxnAmount(Double ctxTxnAmount) {
        this.ctxTxnAmount = ctxTxnAmount;
    }

    public String getCtxReference() {
        return ctxReference;
    }

    public void setCtxReference(String ctxReference) {
        this.ctxReference = ctxReference;
    }

    public Long getCtxTxnTerminal() {
        return ctxTxnTerminal;
    }

    public void setCtxTxnTerminal(Long ctxTxnTerminal) {
        this.ctxTxnTerminal = ctxTxnTerminal;
    }

    public Timestamp getCtxTxnTimestamp() {
        return ctxTxnTimestamp;
    }

    public void setCtxTxnTimestamp(Timestamp ctxTxnTimestamp) {
        this.ctxTxnTimestamp = ctxTxnTimestamp;
    }

    public Double getCtxCardBalance() {
        return ctxCardBalance;
    }

    public void setCtxCardBalance(Double ctxCardBalance) {
        this.ctxCardBalance = ctxCardBalance;
    }

    public Long getCtxLocation() {
        return ctxLocation;
    }

    public void setCtxLocation(Long ctxLocation) {
        this.ctxLocation = ctxLocation;
    }

    public Long getCtxUserNo() {
        return ctxUserNo;
    }

    public void setCtxUserNo(Long ctxUserNo) {
        this.ctxUserNo = ctxUserNo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "CardTransactionResource{" +
                "ctxTxnNo=" + ctxTxnNo +
                ", ctxCardNumber='" + ctxCardNumber + '\'' +
                ", ctxCrmId=" + ctxCrmId +
                ", ctxTxnType=" + ctxTxnType +
                ", ctxTxnAmount=" + ctxTxnAmount +
                ", ctxReference='" + ctxReference + '\'' +
                ", ctxTxnTerminal=" + ctxTxnTerminal +
                ", ctxTxnTimestamp=" + ctxTxnTimestamp +
                ", ctxCardBalance=" + ctxCardBalance +
                ", ctxLocation=" + ctxLocation +
                ", ctxUserNo=" + ctxUserNo +
                ", merchantName='" + merchantName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

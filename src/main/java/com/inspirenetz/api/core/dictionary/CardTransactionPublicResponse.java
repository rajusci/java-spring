package com.inspirenetz.api.core.dictionary;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * Created by sandheepgr on 25/7/14.
 */
public class CardTransactionPublicResponse {

    private Long ctxTxnNo;

    private String ctxCardNumber = "";

    private Integer ctxTxnType = CardTransactionType.ISSUE;

    private Double ctxTxnAmount = 0.0;

    private String ctxReference =  "";


    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd MMM,yyyy HH:mm")
    private Timestamp ctxTxnTimestamp;

    private Double ctxCardBalance = 0.0;


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
                ", ctxTxnType=" + ctxTxnType +
                ", ctxTxnAmount=" + ctxTxnAmount +
                ", ctxReference='" + ctxReference + '\'' +
                ", ctxTxnTimestamp=" + ctxTxnTimestamp +
                ", ctxCardBalance=" + ctxCardBalance +
                ", merchantName='" + merchantName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

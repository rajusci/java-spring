package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.CardTransactionType;
import com.inspirenetz.api.core.domain.CardTransaction;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by sandheepgr on 22/7/14.
 */
public class CardTransactionBuilder {
    private Long ctxTxnNo;
    private String ctxCardNumber = "";
    private Long ctxCrmId = 0L;
    private Integer ctxTxnType = CardTransactionType.ISSUE;
    private Double ctxTxnAmount = 0.0;
    private String ctxReference =  "";
    private Long ctxTxnTerminal = 0L;
    private Timestamp ctxTxnTimestamp;
    private Double ctxCardBalance = 0.0;
    private Long ctxLocation = 0l;
    private Long ctxUserNo = 0l;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private CardTransactionBuilder() {
    }

    public static CardTransactionBuilder aCardTransaction() {
        return new CardTransactionBuilder();
    }

    public CardTransactionBuilder withCtxTxnNo(Long ctxTxnNo) {
        this.ctxTxnNo = ctxTxnNo;
        return this;
    }

    public CardTransactionBuilder withCtxCardNumber(String ctxCardNumber) {
        this.ctxCardNumber = ctxCardNumber;
        return this;
    }

    public CardTransactionBuilder withCtxCrmId(Long ctxCrmId) {
        this.ctxCrmId = ctxCrmId;
        return this;
    }

    public CardTransactionBuilder withCtxTxnType(Integer ctxTxnType) {
        this.ctxTxnType = ctxTxnType;
        return this;
    }

    public CardTransactionBuilder withCtxTxnAmount(Double ctxTxnAmount) {
        this.ctxTxnAmount = ctxTxnAmount;
        return this;
    }

    public CardTransactionBuilder withCtxReference(String ctxReference) {
        this.ctxReference = ctxReference;
        return this;
    }

    public CardTransactionBuilder withCtxTxnTerminal(Long ctxTxnTerminal) {
        this.ctxTxnTerminal = ctxTxnTerminal;
        return this;
    }

    public CardTransactionBuilder withCtxTxnTimestamp(Timestamp ctxTxnTimestamp) {
        this.ctxTxnTimestamp = ctxTxnTimestamp;
        return this;
    }

    public CardTransactionBuilder withCtxCardBalance(Double ctxCardBalance) {
        this.ctxCardBalance = ctxCardBalance;
        return this;
    }

    public CardTransactionBuilder withCtxLocation(Long ctxLocation) {
        this.ctxLocation = ctxLocation;
        return this;
    }

    public CardTransactionBuilder withCtxUserNo(Long ctxUserNo) {
        this.ctxUserNo = ctxUserNo;
        return this;
    }

    public CardTransactionBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CardTransactionBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CardTransactionBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CardTransactionBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CardTransaction build() {
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setCtxTxnNo(ctxTxnNo);
        cardTransaction.setCtxCardNumber(ctxCardNumber);
        cardTransaction.setCtxCrmId(ctxCrmId);
        cardTransaction.setCtxTxnType(ctxTxnType);
        cardTransaction.setCtxTxnAmount(ctxTxnAmount);
        cardTransaction.setCtxReference(ctxReference);
        cardTransaction.setCtxTxnTerminal(ctxTxnTerminal);
        cardTransaction.setCtxTxnTimestamp(ctxTxnTimestamp);
        cardTransaction.setCtxCardBalance(ctxCardBalance);
        cardTransaction.setCtxLocation(ctxLocation);
        cardTransaction.setCtxUserNo(ctxUserNo);
        cardTransaction.setCreatedAt(createdAt);
        cardTransaction.setCreatedBy(createdBy);
        cardTransaction.setUpdatedAt(updatedAt);
        cardTransaction.setUpdatedBy(updatedBy);
        return cardTransaction;
    }
}

package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.CardTransactionType;
import com.inspirenetz.api.core.dictionary.PaymentMode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "CARD_TRANSACTIONS")
public class CardTransaction extends AuditedEntity {

    @Id
    @Column(name = "CTX_TXN_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ctxTxnNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CTX_CARD_NUMBER")
    @NotEmpty(message = "{cardtransaction.ctxcardnumber.notempty}")
    @Size(min=3,max=20,message = "{cardtransaction.ctxcardnumber.size}")
    private String ctxCardNumber = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CTX_CRM_ID")
    private Long ctxCrmId = 0L;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CTX_TXN_TYPE")
    private Integer ctxTxnType = CardTransactionType.ISSUE;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CTX_TXN_AMOUNT")
    private Double ctxTxnAmount = 0.0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CTX_REFERENCE")
    private String ctxReference =  "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CTX_TXN_TERMINAL")
    private Long ctxTxnTerminal = 0L;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CTX_TXN_TIMESTAMP")
    private Timestamp ctxTxnTimestamp;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CTX_CARD_BALANCE")
    private Double ctxCardBalance = 0.0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CTX_LOCATION")
    private Long ctxLocation = 0l;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CTX_USER_NO")
    private Long ctxUserNo = 0l;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CTX_PAYMENT_MODE")
    private Integer ctxPaymentMode = PaymentMode.CASH;


    @PrePersist
    private void populateInsertFields() {

        // Get the current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Set the registerTimestamp to current time
        ctxTxnTimestamp = new Timestamp(timestamp.getTime());

    }


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

    public Integer getCtxPaymentMode() {
        return ctxPaymentMode;
    }

    public void setCtxPaymentMode(Integer ctxPaymentMode) {
        this.ctxPaymentMode = ctxPaymentMode;
    }

    @Override
    public String toString() {
        return "CardTransaction{" +
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
                ", ctxPaymentMode=" + ctxPaymentMode +
                '}';
    }
}

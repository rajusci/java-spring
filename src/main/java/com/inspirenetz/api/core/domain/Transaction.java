package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.CreditDebitInd;
import com.inspirenetz.api.core.dictionary.RecordStatus;
import com.inspirenetz.api.core.dictionary.TransactionType;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by sandheepgr on 17/2/14.
 */
@Entity
@Table(name="TRANSACTIONS")
public class Transaction extends AuditedEntity {

    @Column(name = "TXN_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long txnId;

    @Column(name = "TXN_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int txnType;

    @Column(name = "TXN_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long txnMerchantNo;

    @Column(name = "TXN_LOYALTY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String txnLoyaltyId;

    @Column(name = "TXN_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int txnStatus;

    @Column(name = "TXN_DATE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date txnDate;

    @Column(name = "TXN_LOCATION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long txnLocation;


    @Column(name = "TXN_INTERNAL_REF",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String txnInternalRef;

    @Column(name = "TXN_EXTERNAL_REF",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String txnExternalRef;

    @Column(name = "TXN_REWARD_CURRENCY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long txnRewardCurrencyId;

    @Column(name = "TXN_CR_DB_IND",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int txnCrDbInd;

    @Column(name = "TXN_REWARD_QTY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double txnRewardQty;

    @Column(name = "TXN_AMOUNT",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double txnAmount;

    @Column(name = "TXN_PROGRAM_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long txnProgramId;

    @Column(name = "TXN_REWARD_PRE_BAL",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double txnRewardPreBal;

    @Column(name = "TXN_REWARD_POST_BAL",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double txnRewardPostBal;

    @Column(name = "TXN_REWARD_EXP_DT",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Date txnRewardExpDt;

    @Column(name = "TXN_RECORD_STATUS",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer txnRecordStatus  = RecordStatus.RECORD_STATUS_ACTIVE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TXN_REWARD_CURRENCY_ID",insertable = false,updatable = false)
    private RewardCurrency rewardCurrency;



    public Long getTxnMerchantNo() {
        return txnMerchantNo;
    }

    public void setTxnMerchantNo(Long txnMerchantNo) {
        this.txnMerchantNo = txnMerchantNo;
    }

    public Long getTxnRewardCurrencyId() {
        return txnRewardCurrencyId;
    }

    public void setTxnRewardCurrencyId(Long txnRewardCurrencyId) {
        this.txnRewardCurrencyId = txnRewardCurrencyId;
    }

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    public int getTxnType() {
        return txnType;
    }

    public void setTxnType(int txnType) {
        this.txnType = txnType;
    }

    public String getTxnLoyaltyId() {
        return txnLoyaltyId;
    }

    public void setTxnLoyaltyId(String txnLoyaltyId) {
        this.txnLoyaltyId = txnLoyaltyId;
    }

    public int getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(int txnStatus) {
        this.txnStatus = txnStatus;
    }

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    public Long getTxnLocation() {
        return txnLocation;
    }

    public void setTxnLocation(Long txnLocation) {
        this.txnLocation = txnLocation;
    }

    public String getTxnInternalRef() {
        return txnInternalRef;
    }

    public void setTxnInternalRef(String txnInternalRef) {
        this.txnInternalRef = txnInternalRef;
    }

    public String getTxnExternalRef() {
        return txnExternalRef;
    }

    public void setTxnExternalRef(String txnExternalRef) {
        this.txnExternalRef = txnExternalRef;
    }

    public int getTxnCrDbInd() {
        return txnCrDbInd;
    }

    public void setTxnCrDbInd(int txnCrDbInd) {
        this.txnCrDbInd = txnCrDbInd;
    }

    public double getTxnRewardQty() {
        return txnRewardQty;
    }

    public void setTxnRewardQty(double txnRewardQty) {
        this.txnRewardQty = txnRewardQty;
    }

    public double getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(double txnAmount) {
        this.txnAmount = txnAmount;
    }

    public Long getTxnProgramId() {
        return txnProgramId;
    }

    public void setTxnProgramId(Long txnProgramId) {
        this.txnProgramId = txnProgramId;
    }

    public double getTxnRewardPreBal() {
        return txnRewardPreBal;
    }

    public void setTxnRewardPreBal(double txnRewardPreBal) {
        this.txnRewardPreBal = txnRewardPreBal;
    }

    public double getTxnRewardPostBal() {
        return txnRewardPostBal;
    }

    public void setTxnRewardPostBal(double txnRewardPostBal) {
        this.txnRewardPostBal = txnRewardPostBal;
    }

    public Date getTxnRewardExpDt() {
        return txnRewardExpDt;
    }

    public void setTxnRewardExpDt(Date txnRewardExpDt) {
        this.txnRewardExpDt = txnRewardExpDt;
    }

    public RewardCurrency getRewardCurrency() {
        return rewardCurrency;
    }

    public void setRewardCurrency(RewardCurrency rewardCurrency) {
        this.rewardCurrency = rewardCurrency;
    }

    public Integer getTxnRecordStatus() {
        return txnRecordStatus;
    }

    public void setTxnRecordStatus(Integer txnRecordStatus) {
        this.txnRecordStatus = txnRecordStatus;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "txnId=" + txnId +
                ", txnType=" + txnType +
                ", txnMerchantNo=" + txnMerchantNo +
                ", txnLoyaltyId='" + txnLoyaltyId + '\'' +
                ", txnStatus=" + txnStatus +
                ", txnDate=" + txnDate +
                ", txnLocation=" + txnLocation +
                ", txnInternalRef='" + txnInternalRef + '\'' +
                ", txnExternalRef='" + txnExternalRef + '\'' +
                ", txnRewardCurrencyId=" + txnRewardCurrencyId +
                ", txnCrDbInd=" + txnCrDbInd +
                ", txnRewardQty=" + txnRewardQty +
                ", txnAmount=" + txnAmount +
                ", txnProgramId=" + txnProgramId +
                ", txnRewardPreBal=" + txnRewardPreBal +
                ", txnRewardPostBal=" + txnRewardPostBal +
                ", txnRewardExpDt=" + txnRewardExpDt +
                '}';
    }
}

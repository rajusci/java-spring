package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.CreditDebitInd;
import com.inspirenetz.api.core.dictionary.TransactionType;
import com.inspirenetz.api.core.domain.Transaction;

import java.sql.Date;

/**
 * Created by sandheepgr on 17/2/14.
 */
public class TransactionBuilder {
    private int txnType;
    private Long txnMerchantNo;
    private String txnLoyaltyId;
    private int txnStatus;
    private Date txnDate;
    private Long txnLocation;
    private String txnInternalRef;
    private String txnExternalRef;
    private Long txnRewardCurrencyId;
    private int txnCrDbInd;
    private double txnRewardQty;
    private double txnAmount;
    private Long txnProgramId;
    private double txnRewardPreBal;
    private double txnRewardPostBal;
    private Date txnRewardExpiryDt;

    private TransactionBuilder() {
    }

    public static TransactionBuilder aTransaction() {
        return new TransactionBuilder();
    }

    public TransactionBuilder withTxnType(int txnType) {
        this.txnType = txnType;
        return this;
    }

    public TransactionBuilder withTxnMerchantNo(Long txnMerchantNo) {
        this.txnMerchantNo = txnMerchantNo;
        return this;
    }

    public TransactionBuilder withTxnLoyaltyId(String txnLoyaltyId) {
        this.txnLoyaltyId = txnLoyaltyId;
        return this;
    }

    public TransactionBuilder withTxnStatus(int txnStatus) {
        this.txnStatus = txnStatus;
        return this;
    }

    public TransactionBuilder withTxnDate(Date txnDate) {
        this.txnDate = txnDate;
        return this;
    }

    public TransactionBuilder withTxnLocation(Long txnLocation) {
        this.txnLocation = txnLocation;
        return this;
    }

    public TransactionBuilder withTxnInternalRef(String txnInternalRef) {
        this.txnInternalRef = txnInternalRef;
        return this;
    }

    public TransactionBuilder withTxnExternalRef(String txnExternalRef) {
        this.txnExternalRef = txnExternalRef;
        return this;
    }

    public TransactionBuilder withTxnRewardCurrencyId(Long txnRewardCurrencyId) {
        this.txnRewardCurrencyId = txnRewardCurrencyId;
        return this;
    }

    public TransactionBuilder withTxnCrDbInd(int txnCrDbInd) {
        this.txnCrDbInd = txnCrDbInd;
        return this;
    }

    public TransactionBuilder withTxnRewardQty(double txnRewardQty) {
        this.txnRewardQty = txnRewardQty;
        return this;
    }

    public TransactionBuilder withTxnAmount(double txnAmount) {
        this.txnAmount = txnAmount;
        return this;
    }

    public TransactionBuilder withTxnProgramId(Long txnProgramId) {
        this.txnProgramId = txnProgramId;
        return this;
    }

    public TransactionBuilder withTxnRewardPreBal(double txnRewardPreBal) {
        this.txnRewardPreBal = txnRewardPreBal;
        return this;
    }

    public TransactionBuilder withTxnRewardPostBal(double txnRewardPostBal) {
        this.txnRewardPostBal = txnRewardPostBal;
        return this;
    }

    public TransactionBuilder withTxnRewardExpiryDt(Date txnRewardExpiryDt) {
        this.txnRewardExpiryDt = txnRewardExpiryDt;
        return this;
    }

    public Transaction build() {
        Transaction transaction = new Transaction();
        transaction.setTxnType(txnType);
        transaction.setTxnMerchantNo(txnMerchantNo);
        transaction.setTxnLoyaltyId(txnLoyaltyId);
        transaction.setTxnStatus(txnStatus);
        transaction.setTxnDate(txnDate);
        transaction.setTxnLocation(txnLocation);
        transaction.setTxnInternalRef(txnInternalRef);
        transaction.setTxnExternalRef(txnExternalRef);
        transaction.setTxnRewardCurrencyId(txnRewardCurrencyId);
        transaction.setTxnCrDbInd(txnCrDbInd);
        transaction.setTxnRewardQty(txnRewardQty);
        transaction.setTxnAmount(txnAmount);
        transaction.setTxnProgramId(txnProgramId);
        transaction.setTxnRewardPreBal(txnRewardPreBal);
        transaction.setTxnRewardPostBal(txnRewardPostBal);
        transaction.setTxnRewardExpDt(txnRewardExpiryDt);
        return transaction;
    }
}

package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 29/4/14.
 */
public class CashbackRedemptionResponse {

    private String errorcode;

    private String status;

    private double balance;

    private String txnRef = "";


    public String getTxnRef() {
        return txnRef;
    }

    public void setTxnRef(String txnRef) {
        this.txnRef = txnRef;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return "CashbackRedemptionResponse{" +
                "errorcode='" + errorcode + '\'' +
                ", status='" + status + '\'' +
                ", balance=" + balance +
                ", txnRef='" + txnRef + '\'' +
                '}';
    }
}

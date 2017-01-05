package com.inspirenetz.api.rest.resource;

/**
 * Created by fayiz-ci on 3/2/15.
 */
public class CashBackCompatibleResource extends BaseResource {

    private Double balance;

    private String txn_ref;

    private Double pointRedeemed;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getTxn_ref() {
        return txn_ref;
    }

    public void setTxn_ref(String txn_ref) {
        this.txn_ref = txn_ref;
    }

    public Double getPointRedeemed() {
        return pointRedeemed;
    }

    public void setPointRedeemed(Double pointRedeemed) {
        this.pointRedeemed = pointRedeemed;
    }

    @Override
    public String toString() {
        return "CashBackCompatibleResource{" +
                "balance=" + balance +
                ", txn_ref='" + txn_ref + '\'' +
                '}';
    }
}

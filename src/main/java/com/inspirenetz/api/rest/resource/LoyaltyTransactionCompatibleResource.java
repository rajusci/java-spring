package com.inspirenetz.api.rest.resource;

import java.util.Date;

/**
 * Created by ameen on 1/3/15.
 */
public class LoyaltyTransactionCompatibleResource extends BaseResource {

    private String txn_type;

    private Date txn_date;

    private Double txn_amount;

    private Double txn_rwd_qty;

    private String txn_user;

    public String getTxn_type() {
        return txn_type;
    }

    public void setTxn_type(String txn_type) {
        this.txn_type = txn_type;
    }

    public Date getTxn_date() {
        return txn_date;
    }

    public void setTxn_date(Date txn_date) {
        this.txn_date = txn_date;
    }

    public Double getTxn_amount() {
        return txn_amount;
    }

    public void setTxn_amount(Double txn_amount) {
        this.txn_amount = txn_amount;
    }

    public Double getTxn_rwd_qty() {
        return txn_rwd_qty;
    }

    public void setTxn_rwd_qty(Double txn_rwd_qty) {
        this.txn_rwd_qty = txn_rwd_qty;
    }

    public String getTxn_user() {
        return txn_user;
    }

    public void setTxn_user(String txn_user) {
        this.txn_user = txn_user;
    }

    @Override
    public String toString() {
        return "LoyaltyTransactionCompatibleResource{" +
                "txn_type='" + txn_type + '\'' +
                ", txn_date=" + txn_date +
                ", txn_amount=" + txn_amount +
                ", txn_rwd_qty=" + txn_rwd_qty +
                ", txn_user='" + txn_user + '\'' +
                '}';
    }
}

package com.inspirenetz.api.rest.resource;

import java.sql.Timestamp;

/**
 * Created by ameen on 28/2/15.
 */
public class TransactionCompatibleResource extends BaseResource {

    private String txn_type;

    private String txn_date;

    private Double txn_amount;

    private Double txn_balance;

    private String txn_user;

    private String txn_card_number;

    private String txn_mer_reference;

    private String txn_card_holder;

    private Integer txn_Id_Type;

    private String txn_Id_No="";

    private long txn_no;


    public String getTxn_type() {
        return txn_type;
    }

    public void setTxn_type(String txn_type) {
        this.txn_type = txn_type;
    }

    public String getTxn_date() {
        return txn_date;
    }

    public void setTxn_date(String txn_date) {
        this.txn_date = txn_date;
    }

    public Double getTxn_amount() {
        return txn_amount;
    }

    public void setTxn_amount(Double txn_amount) {
        this.txn_amount = txn_amount;
    }

    public Double getTxn_balance() {
        return txn_balance;
    }

    public void setTxn_balance(Double txn_balance) {
        this.txn_balance = txn_balance;
    }

    public String getTxn_user() {
        return txn_user;
    }

    public void setTxn_user(String txn_user) {
        this.txn_user = txn_user;
    }

    public String getTxn_card_number() {
        return txn_card_number;
    }

    public void setTxn_card_number(String txn_card_number) {
        this.txn_card_number = txn_card_number;
    }

    public String getTxn_mer_reference() {
        return txn_mer_reference;
    }

    public void setTxn_mer_reference(String txn_mer_reference) {
        this.txn_mer_reference = txn_mer_reference;
    }

    public long getTxn_no() {
        return txn_no;
    }

    public void setTxn_no(long txn_no) {
        this.txn_no = txn_no;
    }

    public String getTxn_card_holder() {
        return txn_card_holder;
    }

    public void setTxn_card_holder(String txn_card_holder) {
        this.txn_card_holder = txn_card_holder;
    }

    public Integer getTxn_Id_Type() {
        return txn_Id_Type;
    }

    public void setTxn_Id_Type(Integer txn_Id_Type) {
        this.txn_Id_Type = txn_Id_Type;
    }

    public String getTxn_Id_No() {
        return txn_Id_No;
    }

    public void setTxn_Id_No(String txn_Id_No) {
        this.txn_Id_No = txn_Id_No;
    }

    @Override
    public String toString() {
        return "TransactionCompatibleResource{" +
                "txn_type='" + txn_type + '\'' +
                ", txn_date='" + txn_date + '\'' +
                ", txn_amount=" + txn_amount +
                ", txn_balance=" + txn_balance +
                ", txn_user='" + txn_user + '\'' +
                ", txn_card_number='" + txn_card_number + '\'' +
                ", txn_mer_reference='" + txn_mer_reference + '\'' +
                ", txn_card_holder='" + txn_card_holder + '\'' +
                ", txn_Id_Type=" + txn_Id_Type +
                ", txn_Id_No='" + txn_Id_No + '\'' +
                ", txn_no=" + txn_no +
                '}';
    }
}

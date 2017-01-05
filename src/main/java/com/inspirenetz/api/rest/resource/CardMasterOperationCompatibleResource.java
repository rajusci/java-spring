package com.inspirenetz.api.rest.resource;

/**
 * Created by ameen on 25/2/15.
 */
public class CardMasterOperationCompatibleResource extends BaseResource {

    private Double balance;

    private String txn_ref;

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

    @Override
    public String toString() {
        return "CardMasterOperationCompatibleResource{" +
                "balance=" + balance +
                ", txn_ref='" + txn_ref + '\'' +
                '}';
    }
}

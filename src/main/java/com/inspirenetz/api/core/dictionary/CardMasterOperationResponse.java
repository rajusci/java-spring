package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 25/7/14.
 */
public class CardMasterOperationResponse {

    private Double balance;

    private String txnref;

    private Double amount;

    private String name;

    private String merchant;

    private String cardtype;

    private String cardnumber;

    private Integer paymentMode;

    private Integer customerIdType;

    private String customerIdNo;

    private Double promoBalance;

    private Double promoTopup;

    private Double promoDebit;

    private Double promoRefund;




    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getTxnref() {
        return txnref;
    }

    public void setTxnref(String txnref) {
        this.txnref = txnref;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getCustomerIdType() {
        return customerIdType;
    }

    public void setCustomerIdType(Integer customerIdType) {
        this.customerIdType = customerIdType;
    }

    public String getCustomerIdNo() {
        return customerIdNo;
    }

    public void setCustomerIdNo(String customerIdNo) {
        this.customerIdNo = customerIdNo;
    }

    public Double getPromoBalance() {
        return promoBalance;
    }

    public void setPromoBalance(Double promoBalance) {
        this.promoBalance = promoBalance;
    }

    public Double getPromoDebit() {
        return promoDebit;
    }

    public void setPromoDebit(Double promoDebit) {
        this.promoDebit = promoDebit;
    }

    public Double getPromoRefund() {
        return promoRefund;
    }

    public void setPromoRefund(Double promoRefund) {
        this.promoRefund = promoRefund;
    }

    public Double getPromoTopup() {
        return promoTopup;
    }

    public void setPromoTopup(Double promoTopup) {
        this.promoTopup = promoTopup;
    }

    @Override
    public String toString() {
        return "CardMasterOperationResponse{" +
                "balance=" + balance +
                ", txnref='" + txnref + '\'' +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                ", merchant='" + merchant + '\'' +
                ", cardtype='" + cardtype + '\'' +
                ", cardnumber='" + cardnumber + '\'' +
                ", paymentMode=" + paymentMode +
                ", customerIdType=" + customerIdType +
                ", customerIdNo='" + customerIdNo + '\'' +
                ", promoBalance=" + promoBalance +
                ", promoTopup=" + promoTopup +
                ", promoDebit=" + promoDebit +
                ", promoRefund=" + promoRefund +
                '}';
    }
}

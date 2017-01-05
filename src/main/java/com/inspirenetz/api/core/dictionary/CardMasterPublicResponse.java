package com.inspirenetz.api.core.dictionary;

import java.util.List;

/**
 * Created by sandheepgr on 25/7/14.
 */
public class CardMasterPublicResponse {

    private String cardNumber;

    private String cardName;

    private String cardHolderName;

    private String merchantName;

    private Double balance;

    private List<CardTransactionPublicResponse> cardTransaction;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<CardTransactionPublicResponse> getCardTransaction() {
        return cardTransaction;
    }

    public void setCardTransaction(List<CardTransactionPublicResponse> cardTransaction) {
        this.cardTransaction = cardTransaction;
    }

    @Override
    public String toString() {
        return "CardMasterPublicResponse{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardName='" + cardName + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", balance=" + balance +
                ", cardTransaction=" + cardTransaction +
                '}';
    }
}

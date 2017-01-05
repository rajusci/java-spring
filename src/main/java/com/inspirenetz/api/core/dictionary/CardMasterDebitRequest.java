package com.inspirenetz.api.core.dictionary;

import com.inspirenetz.api.core.domain.CardMaster;

/**
 * Created by sandheepgr on 25/7/14.
 */
public class CardMasterDebitRequest {

    String cardNo = "";

    Long merchantNo = 0L;

    Long txnLocation = 0L ;

    String txnReference = "";

    String cardPin = "";

    Double debitAmount = 0.0;

    Long userNo =0L;

    Integer paymentMode=1;

    Double mainDebit = 0.0;

    Double promoDebit = 0.0;




    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Long getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(Long merchantNo) {
        this.merchantNo = merchantNo;
    }

    public Long getTxnLocation() {
        return txnLocation;
    }

    public void setTxnLocation(Long txnLocation) {
        this.txnLocation = txnLocation;
    }

    public String getTxnReference() {
        return txnReference;
    }

    public void setTxnReference(String txnReference) {
        this.txnReference = txnReference;
    }

    public String getCardPin() {
        return cardPin;
    }

    public void setCardPin(String cardPin) {
        this.cardPin = cardPin;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Double getMainDebit() {
        return mainDebit;
    }

    public void setMainDebit(Double mainDebit) {
        this.mainDebit = mainDebit;
    }

    public Double getPromoDebit() {
        return promoDebit;
    }

    public void setPromoDebit(Double promoDebit) {
        this.promoDebit = promoDebit;
    }
}

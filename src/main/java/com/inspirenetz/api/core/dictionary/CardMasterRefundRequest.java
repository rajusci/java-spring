package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 25/7/14.
 */
public class CardMasterRefundRequest {


    String cardNo = "";

    Long merchantNo = 0L;

    Long ctxLocation = 0L;

    Long userNo = 0L;

    String reference = "";

    Double refundAmount = 0.0;

    String cardPin = "";

    Integer paymentMode=1;

    Double promoRefund = 0.0;

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

    public Long getCtxLocation() {
        return ctxLocation;
    }

    public void setCtxLocation(Long ctxLocation) {
        this.ctxLocation = ctxLocation;
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getCardPin() {
        return cardPin;
    }

    public void setCardPin(String cardPin) {
        this.cardPin = cardPin;
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Double getPromoRefund() {
        return promoRefund;
    }

    public void setPromoRefund(Double promoRefund) {
        this.promoRefund = promoRefund;
    }

}

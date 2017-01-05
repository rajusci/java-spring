package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 25/7/14.
 */
public class CardMasterTopupRequest {


    String cardNo = "";

    Long merchantNo = 0L;

    Long ctxLocation = 0L;

    Long userNo = 0L;

    Double topupAmount = 0.0;

    String reference = "";

    Integer paymentMode=1;


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

    public Double getTopupAmount() {
        return topupAmount;
    }

    public void setTopupAmount(Double topupAmount) {
        this.topupAmount = topupAmount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    @Override
    public String toString() {
        return "CardMasterTopupRequest{" +
                "cardNo='" + cardNo + '\'' +
                ", merchantNo=" + merchantNo +
                ", ctxLocation=" + ctxLocation +
                ", userNo=" + userNo +
                ", topupAmount=" + topupAmount +
                ", reference='" + reference + '\'' +
                ", paymentMode=" + paymentMode +
                '}';
    }
}

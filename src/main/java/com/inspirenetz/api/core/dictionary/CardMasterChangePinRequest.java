package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 25/7/14.
 */
public class CardMasterChangePinRequest {

    String cardNo = "";

    Long merchantNo = 0L;

    Long ctxLocation = 0L;

    Long userNo = 0L;

    String cardPin = "";


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

    public String getCardPin() {
        return cardPin;
    }

    public void setCardPin(String cardPin) {
        this.cardPin = cardPin;
    }


    @Override
    public String toString() {
        return "CardMasterChangePinRequest{" +
                "cardNo='" + cardNo + '\'' +
                ", merchantNo=" + merchantNo +
                ", ctxLocation=" + ctxLocation +
                ", userNo=" + userNo +
                ", cardPin=" + cardPin +
                '}';
    }
}

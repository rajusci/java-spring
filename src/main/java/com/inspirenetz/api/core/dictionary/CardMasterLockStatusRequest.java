package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 25/7/14.
 */
public class CardMasterLockStatusRequest {

    String cardNo = "";

    Long merchantNo = 0L;

    Long ctxLocation = 0L;

    Long userNo = 0L;

    Integer lockStatus;


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

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }


    @Override
    public String toString() {
        return "CardMasterLockStatusRequest{" +
                "cardNo='" + cardNo + '\'' +
                ", merchantNo=" + merchantNo +
                ", ctxLocation=" + ctxLocation +
                ", userNo=" + userNo +
                ", lockStatus=" + lockStatus +
                '}';
    }
}

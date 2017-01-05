package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 25/7/14.
 */
public class CardMasterTransferRequest {

    String sourceCardNo ="";

    String destCardNo = "";

    Long merchantNo = 0L;

    Long ctxLocation = 0L;

    Long userNo = 0L;

    String reference = "";


    public String getSourceCardNo() {
        return sourceCardNo;
    }

    public void setSourceCardNo(String sourceCardNo) {
        this.sourceCardNo = sourceCardNo;
    }

    public String getDestCardNo() {
        return destCardNo;
    }

    public void setDestCardNo(String destCardNo) {
        this.destCardNo = destCardNo;
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


    @Override
    public String toString() {
        return "CardMasterTransferRequest{" +
                "sourceCardNo='" + sourceCardNo + '\'' +
                ", destCardNo='" + destCardNo + '\'' +
                ", merchantNo=" + merchantNo +
                ", ctxLocation=" + ctxLocation +
                ", userNo=" + userNo +
                ", reference='" + reference + '\'' +
                '}';
    }
}

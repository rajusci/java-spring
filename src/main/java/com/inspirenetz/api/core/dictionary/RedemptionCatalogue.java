package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 28/4/14.
 */
public class RedemptionCatalogue {

    Long catProductNo = 0L;

    String catProductCode ="";

    Integer catQty = 1;

    Long catMerchantNo = 0L;


    public Long getCatProductNo() {
        return catProductNo;
    }

    public void setCatProductNo(Long catProductNo) {
        this.catProductNo = catProductNo;
    }

    public String getCatProductCode() {
        return catProductCode;
    }

    public void setCatProductCode(String catProductCode) {
        this.catProductCode = catProductCode;
    }

    public Integer getCatQty() {
        return catQty;
    }

    public void setCatQty(Integer catQty) {
        this.catQty = catQty;
    }

    public Long getCatMerchantNo() {
        return catMerchantNo;
    }

    public void setCatMerchantNo(Long catMerchantNo) {
        this.catMerchantNo = catMerchantNo;
    }

    @Override
    public String toString() {
        return "RedemptionCatalogue{" +
                "catProductNo='" + catProductNo + '\'' +
                ", catProductCode='" + catProductCode + '\'' +
                ", catQty='" + catQty + '\'' +
                ", catMerchantNo='" + catMerchantNo + '\'' +
                '}';
    }
}

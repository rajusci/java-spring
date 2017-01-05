package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 11/8/14.
 */
public class RedemptionVoucherListItem {

    private String voucherCode = "VOU1001";
    private Long voucherMerchant = 20L;
    private String voucherMerchantName = "Pizza Hut";
    private String voucherPrdCode = "PRD1001";
    private String voucherPrdName = "P10 off for pizza hut";
    private Double voucherQty = 1.0;



    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Long getVoucherMerchant() {
        return voucherMerchant;
    }

    public void setVoucherMerchant(Long voucherMerchant) {
        this.voucherMerchant = voucherMerchant;
    }

    public String getVoucherMerchantName() {
        return voucherMerchantName;
    }

    public void setVoucherMerchantName(String voucherMerchantName) {
        this.voucherMerchantName = voucherMerchantName;
    }

    public String getVoucherPrdCode() {
        return voucherPrdCode;
    }

    public void setVoucherPrdCode(String voucherPrdCode) {
        this.voucherPrdCode = voucherPrdCode;
    }

    public String getVoucherPrdName() {
        return voucherPrdName;
    }

    public void setVoucherPrdName(String voucherPrdName) {
        this.voucherPrdName = voucherPrdName;
    }

    public Double getVoucherQty() {
        return voucherQty;
    }

    public void setVoucherQty(Double voucherQty) {
        this.voucherQty = voucherQty;
    }
}

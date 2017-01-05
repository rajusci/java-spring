package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 18/7/14.
 */
public class PurchaseSKUResource extends BaseResource {

    private Long pkuId;

    private Long pkuPurchaseId = 0L;

    private String pkuProductCode = "";

    private double pkuQty = 1;

    private double pkuPrice = 0;

    private double pkuDiscountPercent = 0;

    private String pkuCategory1 = "";

    private String pkuCategory2 = "";

    private String pkuCategory3 = "";

    private String pkuBrand = "";

    private double pkuRatio = 0;

    private double pkuPoints = 0;

    private String prdName = "";


    public Long getPkuId() {
        return pkuId;
    }

    public void setPkuId(Long pkuId) {
        this.pkuId = pkuId;
    }

    public Long getPkuPurchaseId() {
        return pkuPurchaseId;
    }

    public void setPkuPurchaseId(Long pkuPurchaseId) {
        this.pkuPurchaseId = pkuPurchaseId;
    }

    public String getPkuProductCode() {
        return pkuProductCode;
    }

    public void setPkuProductCode(String pkuProductCode) {
        this.pkuProductCode = pkuProductCode;
    }

    public double getPkuQty() {
        return pkuQty;
    }

    public void setPkuQty(double pkuQty) {
        this.pkuQty = pkuQty;
    }

    public double getPkuPrice() {
        return pkuPrice;
    }

    public void setPkuPrice(double pkuPrice) {
        this.pkuPrice = pkuPrice;
    }

    public double getPkuDiscountPercent() {
        return pkuDiscountPercent;
    }

    public void setPkuDiscountPercent(double pkuDiscountPercent) {
        this.pkuDiscountPercent = pkuDiscountPercent;
    }

    public String getPkuCategory1() {
        return pkuCategory1;
    }

    public void setPkuCategory1(String pkuCategory1) {
        this.pkuCategory1 = pkuCategory1;
    }

    public String getPkuCategory2() {
        return pkuCategory2;
    }

    public void setPkuCategory2(String pkuCategory2) {
        this.pkuCategory2 = pkuCategory2;
    }

    public String getPkuCategory3() {
        return pkuCategory3;
    }

    public void setPkuCategory3(String pkuCategory3) {
        this.pkuCategory3 = pkuCategory3;
    }

    public String getPkuBrand() {
        return pkuBrand;
    }

    public void setPkuBrand(String pkuBrand) {
        this.pkuBrand = pkuBrand;
    }

    public double getPkuRatio() {
        return pkuRatio;
    }

    public void setPkuRatio(double pkuRatio) {
        this.pkuRatio = pkuRatio;
    }

    public double getPkuPoints() {
        return pkuPoints;
    }

    public void setPkuPoints(double pkuPoints) {
        this.pkuPoints = pkuPoints;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }
}

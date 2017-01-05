package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 18/7/14.
 */
public class SaleSKUResource extends BaseResource {

    private Long ssuId;

    private Long ssuSaleId = 0L;

    private String ssuProductCode = "";

    private double ssuQty = 1;

    private double ssuPrice = 0;

    private double ssuDiscountPercent = 0;

    private String ssuCategory1 = "";

    private String ssuCategory2 = "";

    private String ssuCategory3 = "";

    private String ssuBrand = "";

    private double ssuRatio = 0;

    private double ssuPoints = 0;

    private String prdName = "";


    public Long getSsuId() {
        return ssuId;
    }

    public void setSsuId(Long ssuId) {
        this.ssuId = ssuId;
    }

    public Long getSsuSaleId() {
        return ssuSaleId;
    }

    public void setSsuSaleId(Long ssuSaleId) {
        this.ssuSaleId = ssuSaleId;
    }

    public String getSsuProductCode() {
        return ssuProductCode;
    }

    public void setSsuProductCode(String ssuProductCode) {
        this.ssuProductCode = ssuProductCode;
    }

    public double getSsuQty() {
        return ssuQty;
    }

    public void setSsuQty(double ssuQty) {
        this.ssuQty = ssuQty;
    }

    public double getSsuPrice() {
        return ssuPrice;
    }

    public void setSsuPrice(double ssuPrice) {
        this.ssuPrice = ssuPrice;
    }

    public double getSsuDiscountPercent() {
        return ssuDiscountPercent;
    }

    public void setSsuDiscountPercent(double ssuDiscountPercent) {
        this.ssuDiscountPercent = ssuDiscountPercent;
    }

    public String getSsuCategory1() {
        return ssuCategory1;
    }

    public void setSsuCategory1(String ssuCategory1) {
        this.ssuCategory1 = ssuCategory1;
    }

    public String getSsuCategory2() {
        return ssuCategory2;
    }

    public void setSsuCategory2(String ssuCategory2) {
        this.ssuCategory2 = ssuCategory2;
    }

    public String getSsuCategory3() {
        return ssuCategory3;
    }

    public void setSsuCategory3(String ssuCategory3) {
        this.ssuCategory3 = ssuCategory3;
    }

    public String getSsuBrand() {
        return ssuBrand;
    }

    public void setSsuBrand(String ssuBrand) {
        this.ssuBrand = ssuBrand;
    }

    public double getSsuRatio() {
        return ssuRatio;
    }

    public void setSsuRatio(double ssuRatio) {
        this.ssuRatio = ssuRatio;
    }

    public double getSsuPoints() {
        return ssuPoints;
    }

    public void setSsuPoints(double ssuPoints) {
        this.ssuPoints = ssuPoints;
    }


    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }
}

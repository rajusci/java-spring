package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.PurchaseSKU;

/**
 * Created by sandheepgr on 28/5/14.
 */
public class PurchaseSKUBuilder {
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

    private PurchaseSKUBuilder() {
    }

    public static PurchaseSKUBuilder aPurchaseSKU() {
        return new PurchaseSKUBuilder();
    }

    public PurchaseSKUBuilder withPkuId(Long pkuId) {
        this.pkuId = pkuId;
        return this;
    }

    public PurchaseSKUBuilder withPkuPurchaseId(Long pkuPurchaseId) {
        this.pkuPurchaseId = pkuPurchaseId;
        return this;
    }

    public PurchaseSKUBuilder withPkuProductCode(String pkuProductCode) {
        this.pkuProductCode = pkuProductCode;
        return this;
    }

    public PurchaseSKUBuilder withPkuQty(double pkuQty) {
        this.pkuQty = pkuQty;
        return this;
    }

    public PurchaseSKUBuilder withPkuPrice(double pkuPrice) {
        this.pkuPrice = pkuPrice;
        return this;
    }

    public PurchaseSKUBuilder withPkuDiscountPercent(double pkuDiscountPercent) {
        this.pkuDiscountPercent = pkuDiscountPercent;
        return this;
    }

    public PurchaseSKUBuilder withPkuCategory1(String pkuCategory1) {
        this.pkuCategory1 = pkuCategory1;
        return this;
    }

    public PurchaseSKUBuilder withPkuCategory2(String pkuCategory2) {
        this.pkuCategory2 = pkuCategory2;
        return this;
    }

    public PurchaseSKUBuilder withPkuCategory3(String pkuCategory3) {
        this.pkuCategory3 = pkuCategory3;
        return this;
    }

    public PurchaseSKUBuilder withPkuBrand(String pkuBrand) {
        this.pkuBrand = pkuBrand;
        return this;
    }

    public PurchaseSKUBuilder withPkuRatio(double pkuRatio) {
        this.pkuRatio = pkuRatio;
        return this;
    }

    public PurchaseSKUBuilder withPkuPoints(double pkuPoints) {
        this.pkuPoints = pkuPoints;
        return this;
    }

    public PurchaseSKU build() {
        PurchaseSKU purchaseSKU = new PurchaseSKU();
        purchaseSKU.setPkuId(pkuId);
        purchaseSKU.setPkuPurchaseId(pkuPurchaseId);
        purchaseSKU.setPkuProductCode(pkuProductCode);
        purchaseSKU.setPkuQty(pkuQty);
        purchaseSKU.setPkuPrice(pkuPrice);
        purchaseSKU.setPkuDiscountPercent(pkuDiscountPercent);
        purchaseSKU.setPkuCategory1(pkuCategory1);
        purchaseSKU.setPkuCategory2(pkuCategory2);
        purchaseSKU.setPkuCategory3(pkuCategory3);
        purchaseSKU.setPkuBrand(pkuBrand);
        purchaseSKU.setPkuRatio(pkuRatio);
        purchaseSKU.setPkuPoints(pkuPoints);
        return purchaseSKU;
    }
}

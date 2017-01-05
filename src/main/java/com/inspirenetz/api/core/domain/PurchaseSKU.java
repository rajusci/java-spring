package com.inspirenetz.api.core.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Entity
@Table(name="PURCHASE_SKU")
public class PurchaseSKU {

    @Column(name = "PKU_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkuId;

    @Column(name = "PKU_PURCHASE_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long pkuPurchaseId = 0L;

    @Column(name = "PKU_PRODUCT_CODE")
    @Basic(fetch = FetchType.EAGER)
    @NotNull(message = "{purchasesku.pkuproductcode.notnull}")
    @NotEmpty(message = "{purchasesku.pkuproductcode.notempty}")
    @Size(max=100, message = "{purchasesku.pkuproductcode.size}")
    private String pkuProductCode = "";

    @Column(name = "PKU_QTY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double pkuQty = 1;

    @Column(name = "PKU_PRICE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double pkuPrice = 0;

    @Column(name = "PKU_DISCOUNT_PERCENT",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double pkuDiscountPercent = 0;

    @Column(name = "PKU_CATEGORY1",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String pkuCategory1 = "";

    @Column(name = "PKU_CATEGORY2",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String pkuCategory2 = "";

    @Column(name = "PKU_CATEGORY3",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String pkuCategory3 = "";

    @Column(name = "PKU_BRAND",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String pkuBrand = "";

    @Column(name = "PKU_RATIO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double pkuRatio = 0;

    @Column(name = "PKU_POINTS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double pkuPoints = 0;


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



    @Override
    public String toString() {
        return "PurchaseSKU{" +
                "pkuId=" + pkuId +
                ", pkuPurchaseId=" + pkuPurchaseId +
                ", pkuQty=" + pkuQty +
                ", pkuPrice=" + pkuPrice +
                ", pkuDiscountPercent=" + pkuDiscountPercent +
                ", pkuCategory1='" + pkuCategory1 + '\'' +
                ", pkuCategory2='" + pkuCategory2 + '\'' +
                ", pkuCategory3='" + pkuCategory3 + '\'' +
                ", pkuBrand='" + pkuBrand + '\'' +
                ", pkuRatio=" + pkuRatio +
                ", pkuPoints=" + pkuPoints +
                '}';
    }
}

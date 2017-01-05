package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Product;

import java.util.Date;

/**
 * Created by sandheepgr on 13/9/14.
 */
public class ProductBuilder {
    private Long prdId;
    private String prdCode;
    private Long prdMerchantNo;
    private String prdName = "";
    private String prdDescription ="";
    private String prdCategory1 = "";
    private String prdCategory2 = "";
    private String prdCategory3 = "";
    private String prdBrand = "";
    private Integer prdMsfEnabledInd = IndicatorStatus.YES;
    private Double prdMsfValue = 0.0;
    private int prdPurchaseCurrency = 356;
    private double prdPurchasePrice = 0.0;
    private int prdOnSaleInd = IndicatorStatus.YES;
    private int prdSaleCurrency = 356;
    private double prdSalePrice = 0.0;
    private double prdMemberSalePrice = 0.0;
    private int prdPriceEditable = IndicatorStatus.NO;
    private int prdImage = 1;
    private double prdStockQuantity = 0;
    private double prdTaxPerc = 0;
    private double prdServiceTaxPerc = 0;
    private Long prdLocation = 1L;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private ProductBuilder() {
    }

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withPrdId(Long prdId) {
        this.prdId = prdId;
        return this;
    }

    public ProductBuilder withPrdCode(String prdCode) {
        this.prdCode = prdCode;
        return this;
    }

    public ProductBuilder withPrdMerchantNo(Long prdMerchantNo) {
        this.prdMerchantNo = prdMerchantNo;
        return this;
    }

    public ProductBuilder withPrdName(String prdName) {
        this.prdName = prdName;
        return this;
    }

    public ProductBuilder withPrdDescription(String prdDescription) {
        this.prdDescription = prdDescription;
        return this;
    }

    public ProductBuilder withPrdCategory1(String prdCategory1) {
        this.prdCategory1 = prdCategory1;
        return this;
    }

    public ProductBuilder withPrdCategory2(String prdCategory2) {
        this.prdCategory2 = prdCategory2;
        return this;
    }

    public ProductBuilder withPrdCategory3(String prdCategory3) {
        this.prdCategory3 = prdCategory3;
        return this;
    }

    public ProductBuilder withPrdBrand(String prdBrand) {
        this.prdBrand = prdBrand;
        return this;
    }

    public ProductBuilder withPrdMsfEnabledInd(Integer prdMsfEnabledInd) {
        this.prdMsfEnabledInd = prdMsfEnabledInd;
        return this;
    }

    public ProductBuilder withPrdMsfValue(Double prdMsfValue) {
        this.prdMsfValue = prdMsfValue;
        return this;
    }

    public ProductBuilder withPrdPurchaseCurrency(int prdPurchaseCurrency) {
        this.prdPurchaseCurrency = prdPurchaseCurrency;
        return this;
    }

    public ProductBuilder withPrdPurchasePrice(double prdPurchasePrice) {
        this.prdPurchasePrice = prdPurchasePrice;
        return this;
    }

    public ProductBuilder withPrdOnSaleInd(int prdOnSaleInd) {
        this.prdOnSaleInd = prdOnSaleInd;
        return this;
    }

    public ProductBuilder withPrdSaleCurrency(int prdSaleCurrency) {
        this.prdSaleCurrency = prdSaleCurrency;
        return this;
    }

    public ProductBuilder withPrdSalePrice(double prdSalePrice) {
        this.prdSalePrice = prdSalePrice;
        return this;
    }

    public ProductBuilder withPrdMemberSalePrice(double prdMemberSalePrice) {
        this.prdMemberSalePrice = prdMemberSalePrice;
        return this;
    }

    public ProductBuilder withPrdPriceEditable(int prdPriceEditable) {
        this.prdPriceEditable = prdPriceEditable;
        return this;
    }

    public ProductBuilder withPrdImage(int prdImage) {
        this.prdImage = prdImage;
        return this;
    }

    public ProductBuilder withPrdStockQuantity(double prdStockQuantity) {
        this.prdStockQuantity = prdStockQuantity;
        return this;
    }

    public ProductBuilder withPrdTaxPerc(double prdTaxPerc) {
        this.prdTaxPerc = prdTaxPerc;
        return this;
    }

    public ProductBuilder withPrdServiceTaxPerc(double prdServiceTaxPerc) {
        this.prdServiceTaxPerc = prdServiceTaxPerc;
        return this;
    }


    public ProductBuilder withPrdLocation(Long prdLocation) {
        this.prdLocation = prdLocation;
        return this;
    }

    public ProductBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ProductBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public ProductBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public ProductBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Product build() {
        Product product = new Product();
        product.setPrdId(prdId);
        product.setPrdCode(prdCode);
        product.setPrdMerchantNo(prdMerchantNo);
        product.setPrdName(prdName);
        product.setPrdDescription(prdDescription);
        product.setPrdCategory1(prdCategory1);
        product.setPrdCategory2(prdCategory2);
        product.setPrdCategory3(prdCategory3);
        product.setPrdBrand(prdBrand);
        product.setPrdMsfEnabledInd(prdMsfEnabledInd);
        product.setPrdMsfValue(prdMsfValue);
        product.setPrdPurchaseCurrency(prdPurchaseCurrency);
        product.setPrdPurchasePrice(prdPurchasePrice);
        product.setPrdOnSaleInd(prdOnSaleInd);
        product.setPrdSaleCurrency(prdSaleCurrency);
        product.setPrdSalePrice(prdSalePrice);
        product.setPrdMemberSalePrice(prdMemberSalePrice);
        product.setPrdPriceEditable(prdPriceEditable);
        product.setPrdImage(prdImage);
        product.setPrdStockQuantity(prdStockQuantity);
        product.setPrdTaxPerc(prdTaxPerc);
        product.setPrdServiceTaxPerc(prdServiceTaxPerc);
        product.setCreatedAt(createdAt);
        product.setCreatedBy(createdBy);
        product.setUpdatedAt(updatedAt);
        product.setUpdatedBy(updatedBy);
        product.setPrdLocation(prdLocation);
        return product;
    }
}

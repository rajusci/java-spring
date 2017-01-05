package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class ProductResource extends BaseResource {

    private Long prdId;

    private String prdCode;

    private String prdName;

    private double prdSalesPrice;

    private String prdCategory1 = "";

    private String prdCategory2 = "";

    private String prdCategory3 = "";

    private Integer prdMsfEnabledInd;

    private Double prdMsfValue = 0.0;

    private String prdBrand = "";

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

    private String prdDescription;

    private Long prdLocation = 0L;

    public String getPrdDescription() {
        return prdDescription;
    }

    public void setPrdDescription(String prdDescription) {
        this.prdDescription = prdDescription;
    }

    public Long getPrdId() {
        return prdId;
    }

    public void setPrdId(Long prdId) {
        this.prdId = prdId;
    }

    public double getPrdSalesPrice() {
        return prdSalesPrice;
    }

    public void setPrdSalesPrice(double prdSalesPrice) {
        this.prdSalesPrice = prdSalesPrice;
    }

    public String getPrdCategory1() {
        return prdCategory1;
    }

    public void setPrdCategory1(String prdCategory1) {
        this.prdCategory1 = prdCategory1;
    }

    public String getPrdCategory2() {
        return prdCategory2;
    }

    public void setPrdCategory2(String prdCategory2) {
        this.prdCategory2 = prdCategory2;
    }

    public String getPrdCategory3() {
        return prdCategory3;
    }

    public void setPrdCategory3(String prdCategory3) {
        this.prdCategory3 = prdCategory3;
    }

    public Integer getPrdMsfEnabledInd() {
        return prdMsfEnabledInd;
    }

    public void setPrdMsfEnabledInd(Integer prdMsfEnabledInd) {
        this.prdMsfEnabledInd = prdMsfEnabledInd;
    }

    public Double getPrdMsfValue() {
        return prdMsfValue;
    }

    public void setPrdMsfValue(Double prdMsfValue) {
        this.prdMsfValue = prdMsfValue;
    }

    public String getPrdBrand() {
        return prdBrand;
    }

    public void setPrdBrand(String prdBrand) {
        this.prdBrand = prdBrand;
    }

    public int getPrdPurchaseCurrency() {
        return prdPurchaseCurrency;
    }

    public void setPrdPurchaseCurrency(int prdPurchaseCurrency) {
        this.prdPurchaseCurrency = prdPurchaseCurrency;
    }

    public double getPrdPurchasePrice() {
        return prdPurchasePrice;
    }

    public void setPrdPurchasePrice(double prdPurchasePrice) {
        this.prdPurchasePrice = prdPurchasePrice;
    }

    public int getPrdOnSaleInd() {
        return prdOnSaleInd;
    }

    public void setPrdOnSaleInd(int prdOnSaleInd) {
        this.prdOnSaleInd = prdOnSaleInd;
    }

    public int getPrdSaleCurrency() {
        return prdSaleCurrency;
    }

    public void setPrdSaleCurrency(int prdSaleCurrency) {
        this.prdSaleCurrency = prdSaleCurrency;
    }

    public double getPrdSalePrice() {
        return prdSalePrice;
    }

    public void setPrdSalePrice(double prdSalePrice) {
        this.prdSalePrice = prdSalePrice;
    }

    public double getPrdMemberSalePrice() {
        return prdMemberSalePrice;
    }

    public void setPrdMemberSalePrice(double prdMemberSalePrice) {
        this.prdMemberSalePrice = prdMemberSalePrice;
    }

    public int getPrdPriceEditable() {
        return prdPriceEditable;
    }

    public void setPrdPriceEditable(int prdPriceEditable) {
        this.prdPriceEditable = prdPriceEditable;
    }

    public int getPrdImage() {
        return prdImage;
    }

    public void setPrdImage(int prdImage) {
        this.prdImage = prdImage;
    }

    public double getPrdStockQuantity() {
        return prdStockQuantity;
    }

    public void setPrdStockQuantity(double prdStockQuantity) {
        this.prdStockQuantity = prdStockQuantity;
    }

    public double getPrdTaxPerc() {
        return prdTaxPerc;
    }

    public void setPrdTaxPerc(double prdTaxPerc) {
        this.prdTaxPerc = prdTaxPerc;
    }

    public double getPrdServiceTaxPerc() {
        return prdServiceTaxPerc;
    }

    public void setPrdServiceTaxPerc(double prdServiceTaxPerc) {
        this.prdServiceTaxPerc = prdServiceTaxPerc;
    }

    public String getPrdCode() {
        return prdCode;
    }

    public void setPrdCode(String prdCode) {
        this.prdCode = prdCode;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public Long getPrdLocation() {
        return prdLocation;
    }

    public void setPrdLocation(Long prdLocation) {
        this.prdLocation = prdLocation;
    }
}

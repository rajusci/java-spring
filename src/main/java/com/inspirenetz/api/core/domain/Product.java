package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.YesNoInd;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Entity
@Table(name="PRODUCTS")
public class Product extends  AuditedEntity {

    @Column(name = "PRD_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prdId;

    @Column(name = "PRD_CODE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{product.prdcode.notempty}")
    @Size(min=1,max=20,message = "{product.prdcode.size}")
    private String prdCode;

    @Column(name = "PRD_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long prdMerchantNo;

    @Column(name = "PRD_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{product.prdname.notempty}")
    @Size(min=1,max=100,message = "{product.prdname.size}")
    private String prdName = "";

    @Column(name = "PRD_DESCRIPTION",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=300,message = "{product.prddescription.size}")
    private String prdDescription ="";

    @Column(name = "PRD_CATEGORY1",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=100,message = "{product.prdcategory1.size}")
    private String prdCategory1 = "";

    @Column(name = "PRD_CATEGORY2",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=100,message = "{product.prdcategory2.size}")
    private String prdCategory2 = "";

    @Column(name = "PRD_CATEGORY3",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=100,message = "{product.prdcategory3.size}")
    private String prdCategory3 = "";

    @Column(name = "PRD_BRAND",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=100,message = "{product.prdbrand.size}")
    private String prdBrand = "";

    @Column(name = "PRD_MSF_ENABLED_IND",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer prdMsfEnabledInd = IndicatorStatus.YES;

    @Column(name = "PRD_MSF_VALUE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Double prdMsfValue = 0.0;

    @Column(name = "PRD_PURCHASE_CURRENCY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int prdPurchaseCurrency = 356;

    @Column(name = "PRD_PURCHASE_PRICE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double prdPurchasePrice = 0.0;

    @Column(name = "PRD_ON_SALE_IND",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int prdOnSaleInd = IndicatorStatus.YES;

    @Column(name = "PRD_SALE_CURRENCY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int prdSaleCurrency = 356;

    @Column(name = "PRD_SALE_PRICE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double prdSalePrice = 0.0;

    @Column(name = "PRD_MEMBER_SALE_PRICE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double prdMemberSalePrice = 0.0;

    @Column(name = "PRD_PRICE_EDITABLE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int prdPriceEditable = IndicatorStatus.NO;

    @Column(name = "PRD_IMAGE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int prdImage = 1;

    @Column(name = "PRD_STOCK_QUANTITY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double prdStockQuantity = 0;

    @Column(name = "PRD_TAX_PERC",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double prdTaxPerc = 0;

    @Column(name = "PRD_SERVICE_TAX_PERC",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double prdServiceTaxPerc = 0;

    @Column(name = "PRD_LOCATION",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long prdLocation = 1L;


    public Long getPrdId() {
        return prdId;
    }

    public void setPrdId(Long prdId) {
        this.prdId = prdId;
    }

    public String getPrdCode() {
        return prdCode;
    }

    public void setPrdCode(String prdCode) {
        this.prdCode = prdCode;
    }

    public Long getPrdMerchantNo() {
        return prdMerchantNo;
    }

    public void setPrdMerchantNo(Long prdMerchantNo) {
        this.prdMerchantNo = prdMerchantNo;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getPrdDescription() {
        return prdDescription;
    }

    public void setPrdDescription(String prdDescription) {
        this.prdDescription = prdDescription;
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

    public String getPrdBrand() {
        return prdBrand;
    }

    public void setPrdBrand(String prdBrand) {
        this.prdBrand = prdBrand;
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

    public Long getPrdLocation() {
        return prdLocation;
    }

    public void setPrdLocation(Long prdLocation) {
        this.prdLocation = prdLocation;
    }

    @Override
    public String toString() {
        return "Product{" +
                "prdId=" + prdId +
                ", prdCode='" + prdCode + '\'' +
                ", prdMerchantNo=" + prdMerchantNo +
                ", prdName='" + prdName + '\'' +
                ", prdDescription='" + prdDescription + '\'' +
                ", prdCategory1=" + prdCategory1 +
                ", prdCategory2=" + prdCategory2 +
                ", prdCategory3=" + prdCategory3 +
                ", prdBrand=" + prdBrand +
                ", prdPurchaseCurrency=" + prdPurchaseCurrency +
                ", prdPurchasePrice=" + prdPurchasePrice +
                ", prdOnSaleInd=" + prdOnSaleInd +
                ", prdSaleCurrency=" + prdSaleCurrency +
                ", prdSalePrice=" + prdSalePrice +
                ", prdMemberSalePrice=" + prdMemberSalePrice +
                ", prdPriceEditable=" + prdPriceEditable +
                ", prdImage=" + prdImage +
                ", prdStockQuantity=" + prdStockQuantity +
                ", prdTaxPerc=" + prdTaxPerc +
                ", prdLocation=" + prdLocation +
                ", prdServiceTaxPerc=" + prdServiceTaxPerc +
                '}';
    }
}

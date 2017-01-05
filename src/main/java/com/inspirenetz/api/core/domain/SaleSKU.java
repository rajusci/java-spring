package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Entity
@Table(name="SALE_SKU")
public class SaleSKU extends AuditedEntity {

    @Column(name = "SSU_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ssuId;

    @Column(name = "SSU_SALE_ID",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long ssuSaleId;

    @Column(name = "SSU_PRODUCT_CODE")
    @Basic(fetch = FetchType.EAGER)
    @NotNull(message = "{salesku.ssuproductcode.notnull}")
    @NotEmpty(message = "{salesku.ssuproductcode.notempty}")
    @Size(max=100, message = "{salesku.ssuproductcode.size}")
    private String ssuProductCode = "";

    @Column(name = "SSU_TRANSACTION_TYPE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer ssuTransactionType = 0;

    @Column(name = "SSU_QTY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Double ssuQty = 1.0;

    @Column(name = "SSU_PRICE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Double ssuPrice = 0.0;

    @Column(name = "SSU_DISCOUNT_PERCENT",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Double  ssuDiscountPercent  = 0.0;

    @Column(name = "SSU_MSF_VALUE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Double ssuMsfValue = 0.0;

    @Column(name = "SSU_CATEGORY1",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String ssuCategory1 = "";

    @Column(name = "SSU_CATEGORY2",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String ssuCategory2 = "";

    @Column(name = "SSU_CATEGORY3",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String ssuCategory3 = "";

    @Column(name = "SSU_BRAND",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String ssuBrand = "";

    @Column(name = "SSU_RATIO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double ssuRatio = 0;

    @Column(name = "SSU_POINTS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double ssuPoints = 0;

    @Column(name = "SSU_CONTRACTED",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String ssuContracted = "N";

    @OneToMany(fetch = FetchType.EAGER,cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name="SSE_SALE_SKU_ID",referencedColumnName = "SSU_ID" )
    private Set<SaleSKUExtension> saleSKUExtensionSet;

    @Transient
    private Long merchantNo;

    @Transient
    private AttributeExtendedEntityMap fieldMap;

    public SaleSKU(){

    }
    public SaleSKU(String ssuProductCode, Integer ssuTransactionType, Double ssuQty, Double ssuPrice, Double ssuMsfValue,String ssucContracted) {
        this.ssuProductCode = ssuProductCode;
        this.ssuTransactionType = ssuTransactionType;
        this.ssuQty = ssuQty;
        this.ssuPrice = ssuPrice;
        this.ssuMsfValue = ssuMsfValue;
        this.ssuContracted = ssucContracted;
    }
    @PrePersist
    @PreUpdate
    public void clearNullFields() {

        // If brand is null, then set to empty string
        if ( ssuBrand == null ) {

            ssuBrand = "";

        }

        // If category1 is null, then set to empty string
        if ( ssuCategory1 == null ) {

            ssuCategory1 = "";

        }

        // If category2 is null, then set to empty string
        if ( ssuCategory2 == null ) {

            ssuCategory2 = "";

        }

        // If category3 is null, then set to empty string
        if ( ssuCategory3 == null ) {

            ssuCategory3 = "";

        }

        // If ssuDiscountPercent is null, then set to 0
        if ( ssuDiscountPercent == null ) {

            ssuDiscountPercent = 0.0;

        }

        // If ssuMsfValue is null, then set to 0
        if ( ssuMsfValue == null ) {

            ssuMsfValue = 0.0;

        }

    }

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

    public Integer getSsuTransactionType() {
        return ssuTransactionType;
    }

    public void setSsuTransactionType(Integer ssuTransactionType) {
        this.ssuTransactionType = ssuTransactionType;
    }

    public Double getSsuQty() {
        return ssuQty;
    }

    public void setSsuQty(Double ssuQty) {
        this.ssuQty = ssuQty;
    }

    public Double getSsuPrice() {
        return ssuPrice;
    }

    public void setSsuPrice(Double ssuPrice) {
        this.ssuPrice = ssuPrice;
    }

    public Double getSsuMsfValue() {
        return ssuMsfValue;
    }

    public void setSsuMsfValue(Double ssuMsfValue) {
        this.ssuMsfValue = ssuMsfValue;
    }

    public Double getSsuDiscountPercent() {
        return ssuDiscountPercent;
    }

    public void setSsuDiscountPercent(Double ssuDiscountPercent) {
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

    public Set<SaleSKUExtension> getSaleSKUExtensionSet() {
        return saleSKUExtensionSet;
    }

    public void setSaleSKUExtensionSet(Set<SaleSKUExtension> saleSKUExtensionSet) {
        this.saleSKUExtensionSet = saleSKUExtensionSet;
    }

    public AttributeExtendedEntityMap getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(AttributeExtendedEntityMap fieldMap) {
        this.fieldMap = fieldMap;
    }

    public Long getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(Long merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getSsuContracted() {
        return ssuContracted;
    }

    public void setSsuContracted(String ssuContracted) {
        this.ssuContracted = ssuContracted;
    }

    @Override
    public String toString() {
        return "SaleSKU{" +
                "ssuId=" + ssuId +
                ", ssuSaleId=" + ssuSaleId +
                ", ssuProductCode='" + ssuProductCode + '\'' +
                ", ssuTransactionType=" + ssuTransactionType +
                ", ssuQty=" + ssuQty +
                ", ssuPrice=" + ssuPrice +
                ", ssuDiscountPercent=" + ssuDiscountPercent +
                ", ssuMsfValue=" + ssuMsfValue +
                ", ssuCategory1='" + ssuCategory1 + '\'' +
                ", ssuCategory2='" + ssuCategory2 + '\'' +
                ", ssuCategory3='" + ssuCategory3 + '\'' +
                ", ssuBrand='" + ssuBrand + '\'' +
                ", ssuRatio=" + ssuRatio +
                ", ssuPoints=" + ssuPoints +
                ", saleSKUExtensionSet=" + saleSKUExtensionSet +
                ", merchantNo=" + merchantNo +
                ", fieldMap=" + fieldMap +
                '}';
    }
}

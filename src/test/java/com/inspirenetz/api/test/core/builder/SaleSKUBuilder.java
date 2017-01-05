package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.SaleSKU;
import com.inspirenetz.api.core.domain.SaleSKUExtension;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;

import java.util.Date;
import java.util.Set;

/**
 * Created by sandheepgr on 14/9/14.
 */
public class SaleSKUBuilder {
    private Long ssuId;
    private Long ssuSaleId;
    private String ssuProductCode = "";
    private Integer ssuTransactionType = 0;
    private Double ssuQty = 1.0;
    private Double ssuPrice = 0.0;
    private Double  ssuDiscountPercent  = 0.0;
    private Double ssuMsfValue = 0.0;
    private String ssuCategory1 = "";
    private String ssuCategory2 = "";
    private String ssuCategory3 = "";
    private String ssuBrand = "";
    private double ssuRatio = 0;
    private double ssuPoints = 0;
    private Set<SaleSKUExtension> saleSKUExtensionSet;
    private AttributeExtendedEntityMap fieldMap;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private SaleSKUBuilder() {
    }

    public static SaleSKUBuilder aSaleSKU() {
        return new SaleSKUBuilder();
    }

    public SaleSKUBuilder withSsuId(Long ssuId) {
        this.ssuId = ssuId;
        return this;
    }

    public SaleSKUBuilder withSsuSaleId(Long ssuSaleId) {
        this.ssuSaleId = ssuSaleId;
        return this;
    }

    public SaleSKUBuilder withSsuProductCode(String ssuProductCode) {
        this.ssuProductCode = ssuProductCode;
        return this;
    }

    public SaleSKUBuilder withSsuTransactionType(Integer ssuTransactionType) {
        this.ssuTransactionType = ssuTransactionType;
        return this;
    }

    public SaleSKUBuilder withSsuQty(Double ssuQty) {
        this.ssuQty = ssuQty;
        return this;
    }

    public SaleSKUBuilder withSsuPrice(Double ssuPrice) {
        this.ssuPrice = ssuPrice;
        return this;
    }

    public SaleSKUBuilder withSsuDiscountPercent(Double ssuDiscountPercent) {
        this.ssuDiscountPercent = ssuDiscountPercent;
        return this;
    }

    public SaleSKUBuilder withSsuMsfValue(Double ssuMsfValue) {
        this.ssuMsfValue = ssuMsfValue;
        return this;
    }

    public SaleSKUBuilder withSsuCategory1(String ssuCategory1) {
        this.ssuCategory1 = ssuCategory1;
        return this;
    }

    public SaleSKUBuilder withSsuCategory2(String ssuCategory2) {
        this.ssuCategory2 = ssuCategory2;
        return this;
    }

    public SaleSKUBuilder withSsuCategory3(String ssuCategory3) {
        this.ssuCategory3 = ssuCategory3;
        return this;
    }

    public SaleSKUBuilder withSsuBrand(String ssuBrand) {
        this.ssuBrand = ssuBrand;
        return this;
    }

    public SaleSKUBuilder withSsuRatio(double ssuRatio) {
        this.ssuRatio = ssuRatio;
        return this;
    }

    public SaleSKUBuilder withSsuPoints(double ssuPoints) {
        this.ssuPoints = ssuPoints;
        return this;
    }

    public SaleSKUBuilder withSaleSKUExtensionSet(Set<SaleSKUExtension> saleSKUExtensionSet) {
        this.saleSKUExtensionSet = saleSKUExtensionSet;
        return this;
    }

    public SaleSKUBuilder withFieldMap(AttributeExtendedEntityMap fieldMap) {
        this.fieldMap = fieldMap;
        return this;
    }

    public SaleSKUBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SaleSKUBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public SaleSKUBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public SaleSKUBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public SaleSKU build() {
        SaleSKU saleSKU = new SaleSKU();
        saleSKU.setSsuId(ssuId);
        saleSKU.setSsuSaleId(ssuSaleId);
        saleSKU.setSsuProductCode(ssuProductCode);
        saleSKU.setSsuTransactionType(ssuTransactionType);
        saleSKU.setSsuQty(ssuQty);
        saleSKU.setSsuPrice(ssuPrice);
        saleSKU.setSsuDiscountPercent(ssuDiscountPercent);
        saleSKU.setSsuMsfValue(ssuMsfValue);
        saleSKU.setSsuCategory1(ssuCategory1);
        saleSKU.setSsuCategory2(ssuCategory2);
        saleSKU.setSsuCategory3(ssuCategory3);
        saleSKU.setSsuBrand(ssuBrand);
        saleSKU.setSsuRatio(ssuRatio);
        saleSKU.setSsuPoints(ssuPoints);
        saleSKU.setSaleSKUExtensionSet(saleSKUExtensionSet);
        saleSKU.setFieldMap(fieldMap);
        saleSKU.setCreatedAt(createdAt);
        saleSKU.setCreatedBy(createdBy);
        saleSKU.setUpdatedAt(updatedAt);
        saleSKU.setUpdatedBy(updatedBy);
        return saleSKU;
    }
}

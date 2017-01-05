package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.domain.SaleExtension;

import java.util.Date;

/**
 * Created by sandheepgr on 14/8/14.
 */
public class SaleExtensionBuilder {
    private Long saeId;
    private Long saeSaleId;
    private Long attrId;
    private String attrValue;
    private Attribute attribute;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private SaleExtensionBuilder() {
    }

    public static SaleExtensionBuilder aSaleExtension() {
        return new SaleExtensionBuilder();
    }

    public SaleExtensionBuilder withSaeId(Long saeId) {
        this.saeId = saeId;
        return this;
    }

    public SaleExtensionBuilder withSaeSaleId(Long saeSaleId) {
        this.saeSaleId = saeSaleId;
        return this;
    }

    public SaleExtensionBuilder withAttrId(Long attrId) {
        this.attrId = attrId;
        return this;
    }

    public SaleExtensionBuilder withAttrValue(String attrValue) {
        this.attrValue = attrValue;
        return this;
    }

    public SaleExtensionBuilder withAttribute(Attribute attribute) {
        this.attribute = attribute;
        return this;
    }

    public SaleExtensionBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SaleExtensionBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public SaleExtensionBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public SaleExtensionBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public SaleExtension build() {
        SaleExtension saleExtension = new SaleExtension();
        saleExtension.setSaeId(saeId);
        saleExtension.setSaeSaleId(saeSaleId);
        saleExtension.setAttrId(attrId);
        saleExtension.setAttrValue(attrValue);
        saleExtension.setAttribute(attribute);
        saleExtension.setCreatedAt(createdAt);
        saleExtension.setCreatedBy(createdBy);
        saleExtension.setUpdatedAt(updatedAt);
        saleExtension.setUpdatedBy(updatedBy);
        return saleExtension;
    }
}

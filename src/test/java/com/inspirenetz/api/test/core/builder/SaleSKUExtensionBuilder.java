package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.domain.SaleSKUExtension;

import java.util.Date;

/**
 * Created by sandheepgr on 14/8/14.
 */
public class SaleSKUExtensionBuilder {
    private Long sseId;
    private Long sseSaleSkuId;
    private Long attrId;
    private String attrValue;
    private Attribute attribute;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private SaleSKUExtensionBuilder() {
    }

    public static SaleSKUExtensionBuilder aSaleSKUExtension() {
        return new SaleSKUExtensionBuilder();
    }

    public SaleSKUExtensionBuilder withSseId(Long sseId) {
        this.sseId = sseId;
        return this;
    }

    public SaleSKUExtensionBuilder withSseSaleSkuId(Long sseSaleSkuId) {
        this.sseSaleSkuId = sseSaleSkuId;
        return this;
    }

    public SaleSKUExtensionBuilder withAttrId(Long attrId) {
        this.attrId = attrId;
        return this;
    }

    public SaleSKUExtensionBuilder withAttrValue(String attrValue) {
        this.attrValue = attrValue;
        return this;
    }

    public SaleSKUExtensionBuilder withAttribute(Attribute attribute) {
        this.attribute = attribute;
        return this;
    }

    public SaleSKUExtensionBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SaleSKUExtensionBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public SaleSKUExtensionBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public SaleSKUExtensionBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public SaleSKUExtension build() {
        SaleSKUExtension saleSKUExtension = new SaleSKUExtension();
        saleSKUExtension.setSseId(sseId);
        saleSKUExtension.setSseSaleSkuId(sseSaleSkuId);
        saleSKUExtension.setAttrId(attrId);
        saleSKUExtension.setAttrValue(attrValue);
        saleSKUExtension.setAttribute(attribute);
        saleSKUExtension.setCreatedAt(createdAt);
        saleSKUExtension.setCreatedBy(createdBy);
        saleSKUExtension.setUpdatedAt(updatedAt);
        saleSKUExtension.setUpdatedBy(updatedBy);
        return saleSKUExtension;
    }
}

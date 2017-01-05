package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.domain.LoyaltyProgramSkuExtension;

import java.util.Date;

/**
 * Created by sandheepgr on 14/8/14.
 */
public class LoyaltyProgramSkuExtensionBuilder {
    private Long lueId;
    private Long lueLoyaltyProgramSkuId;
    private Long attrId;
    private String attrValue;
    private Attribute attribute;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private LoyaltyProgramSkuExtensionBuilder() {
    }

    public static LoyaltyProgramSkuExtensionBuilder aLoyaltyProgramSkuExtension() {
        return new LoyaltyProgramSkuExtensionBuilder();
    }

    public LoyaltyProgramSkuExtensionBuilder withLueId(Long lueId) {
        this.lueId = lueId;
        return this;
    }

    public LoyaltyProgramSkuExtensionBuilder withLueLoyaltyProgramSkuId(Long lueLoyaltyProgramSkuId) {
        this.lueLoyaltyProgramSkuId = lueLoyaltyProgramSkuId;
        return this;
    }

    public LoyaltyProgramSkuExtensionBuilder withAttrId(Long attrId) {
        this.attrId = attrId;
        return this;
    }

    public LoyaltyProgramSkuExtensionBuilder withAttrValue(String attrValue) {
        this.attrValue = attrValue;
        return this;
    }

    public LoyaltyProgramSkuExtensionBuilder withAttribute(Attribute attribute) {
        this.attribute = attribute;
        return this;
    }

    public LoyaltyProgramSkuExtensionBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LoyaltyProgramSkuExtensionBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LoyaltyProgramSkuExtensionBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public LoyaltyProgramSkuExtensionBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public LoyaltyProgramSkuExtension build() {
        LoyaltyProgramSkuExtension loyaltyProgramSkuExtension = new LoyaltyProgramSkuExtension();
        loyaltyProgramSkuExtension.setLueId(lueId);
        loyaltyProgramSkuExtension.setLueLoyaltyProgramSkuId(lueLoyaltyProgramSkuId);
        loyaltyProgramSkuExtension.setAttrId(attrId);
        loyaltyProgramSkuExtension.setAttrValue(attrValue);
        loyaltyProgramSkuExtension.setAttribute(attribute);
        loyaltyProgramSkuExtension.setCreatedAt(createdAt);
        loyaltyProgramSkuExtension.setCreatedBy(createdBy);
        loyaltyProgramSkuExtension.setUpdatedAt(updatedAt);
        loyaltyProgramSkuExtension.setUpdatedBy(updatedBy);
        return loyaltyProgramSkuExtension;
    }
}

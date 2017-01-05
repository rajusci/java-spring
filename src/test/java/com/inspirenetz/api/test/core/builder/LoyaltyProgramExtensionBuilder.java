package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.domain.LoyaltyProgramExtension;

import java.util.Date;

/**
 * Created by sandheepgr on 14/8/14.
 */
public class LoyaltyProgramExtensionBuilder {
    private Long lpeId;
    private Long lpeLoyaltyProgramId;
    private Long attrId;
    private String attrValue;
    private Attribute attribute;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private LoyaltyProgramExtensionBuilder() {
    }

    public static LoyaltyProgramExtensionBuilder aLoyaltyProgramExtension() {
        return new LoyaltyProgramExtensionBuilder();
    }

    public LoyaltyProgramExtensionBuilder withLpeId(Long lpeId) {
        this.lpeId = lpeId;
        return this;
    }

    public LoyaltyProgramExtensionBuilder withLpeLoyaltyProgramId(Long lpeLoyaltyProgramId) {
        this.lpeLoyaltyProgramId = lpeLoyaltyProgramId;
        return this;
    }

    public LoyaltyProgramExtensionBuilder withAttrId(Long attrId) {
        this.attrId = attrId;
        return this;
    }

    public LoyaltyProgramExtensionBuilder withAttrValue(String attrValue) {
        this.attrValue = attrValue;
        return this;
    }

    public LoyaltyProgramExtensionBuilder withAttribute(Attribute attribute) {
        this.attribute = attribute;
        return this;
    }

    public LoyaltyProgramExtensionBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LoyaltyProgramExtensionBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LoyaltyProgramExtensionBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public LoyaltyProgramExtensionBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public LoyaltyProgramExtension build() {
        LoyaltyProgramExtension loyaltyProgramExtension = new LoyaltyProgramExtension();
        loyaltyProgramExtension.setLpeId(lpeId);
        loyaltyProgramExtension.setLpeLoyaltyProgramId(lpeLoyaltyProgramId);
        loyaltyProgramExtension.setAttrId(attrId);
        loyaltyProgramExtension.setAttrValue(attrValue);
        loyaltyProgramExtension.setAttribute(attribute);
        loyaltyProgramExtension.setCreatedAt(createdAt);
        loyaltyProgramExtension.setCreatedBy(createdBy);
        loyaltyProgramExtension.setUpdatedAt(updatedAt);
        loyaltyProgramExtension.setUpdatedBy(updatedBy);
        return loyaltyProgramExtension;
    }
}

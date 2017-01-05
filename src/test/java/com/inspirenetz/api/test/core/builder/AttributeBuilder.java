package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.Attribute;

/**
 * Created by sandheepgr on 7/8/14.
 */
public class AttributeBuilder {

    private Long atrId;
    private String atrName = "";
    private Integer atrEntity = 0;
    private String atrDesc = "";

    private AttributeBuilder() {
    }

    public static AttributeBuilder anAttribute() {
        return new AttributeBuilder();
    }

    public AttributeBuilder withAtrId(Long atrId) {
        this.atrId = atrId;
        return this;
    }

    public AttributeBuilder withAtrName(String atrName) {
        this.atrName = atrName;
        return this;
    }

    public AttributeBuilder withAtrEntity(Integer atrEntity) {
        this.atrEntity = atrEntity;
        return this;
    }

    public AttributeBuilder withAtrDesc(String atrDesc) {
        this.atrDesc = atrDesc;
        return this;
    }

    public Attribute build() {
        Attribute attribute = new Attribute();
        attribute.setAtrId(atrId);
        attribute.setAtrName(atrName);
        attribute.setAtrEntity(atrEntity);
        attribute.setAtrDesc(atrDesc);
        return attribute;
    }
}

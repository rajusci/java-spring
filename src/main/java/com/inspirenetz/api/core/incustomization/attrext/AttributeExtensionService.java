package com.inspirenetz.api.core.incustomization.attrext;

import com.inspirenetz.api.core.domain.Attribute;

/**
 * Created by sandheepgr on 9/8/14.
 */
public interface AttributeExtensionService {

    public AttributeExtendedEntityMap toAttributeExtensionMap(Object obj, Integer attributeExtensionMapType);
    public Object fromAttributeExtensionMap(Object obj, AttributeExtendedEntityMap attributeExtendedEntityMap, Integer attributeExtensionMapType);

    public void setExtFieldValue(Object obj,Attribute attribute,String value);
    public String getExtFieldValue(Object obj,Attribute attribute);


}

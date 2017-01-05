package com.inspirenetz.api.core.incustomization.attrext;

import java.util.List;

/**
 * Created by sandheepgr on 13/8/14.
 */
public interface AttributeExtendedEntityAssembler<T> {

    public AttributeExtendedEntityMap toAttibuteEntityMap(T obj);
    public List<AttributeExtendedEntityMap> toAttibuteEntityMaps(java.lang.Iterable<? extends T> entities);

}

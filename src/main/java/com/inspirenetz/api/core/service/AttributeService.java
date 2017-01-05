package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.incustomization.attrext.AttributeMap;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface AttributeService extends BaseService<Attribute> {

    public Attribute findByAtrId(Long atrId);
    public List<Attribute> findByAtrEntity(Integer atrEntity);
    public Attribute findByAtrName(String atrName);
    public boolean isDuplicateAttributeExisting(Attribute attribute);
    public AttributeMap getAttributesMapByName(Integer atrEntity);

    public Attribute saveAttribute(Attribute attribute) throws InspireNetzException;
    public Attribute validateAndSaveAttribute(Attribute attribute) throws InspireNetzException;

    public boolean validateAndDeleteAttribute(Long atrId) throws InspireNetzException;
    public boolean deleteAttribute(Long atrId) throws InspireNetzException;

    public Page<Attribute> findAttributeNameLike(String filter,String query,Pageable pageable);

}

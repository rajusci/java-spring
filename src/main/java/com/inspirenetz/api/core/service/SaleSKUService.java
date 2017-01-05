package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.SaleSKU;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtensionService;

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface SaleSKUService extends BaseService<SaleSKU>,AttributeExtensionService {

    public List<SaleSKU> findBySsuSaleId(Long ssuSaleId);
    public SaleSKU findBySsuId(Long ssuId);
    public Set<SaleSKU> getSaleSKUFromParams(List<AttributeExtendedEntityMap> params);

    public SaleSKU saveSaleSku(SaleSKU saleSKU);
    public boolean deleteSaleSku(Long ssuId);

    public void deleteSaleSKUSet(Set<SaleSKU> saleSKUSet);

}

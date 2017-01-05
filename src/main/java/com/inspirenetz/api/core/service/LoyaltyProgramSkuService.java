package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtension;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtensionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface LoyaltyProgramSkuService extends BaseService<LoyaltyProgramSku> , AttributeExtensionService {

    public Page<LoyaltyProgramSku> findByLpuProgramId(Long lpuProgramId, Pageable pageable);
    public LoyaltyProgramSku findByLpuId(Long lpuId);
    public LoyaltyProgramSku findByLpuProgramIdAndLpuItemTypeAndLpuItemCode(Long lpuProgramId,int lpuItemType,String lpuItemCode);
    public boolean isLoyaltyProgramSkuExisting(LoyaltyProgramSku loyaltyProgramSku);
    public List<LoyaltyProgramSku> listRulesForLineItem(Long lpuProgramId,String brand,String category1,String category2,String category3,String product);
    public Set<LoyaltyProgramSku> getLoyaltyProgramSkuFromParams(List<AttributeExtendedEntityMap> params);
    public Set<LoyaltyProgramSku> getRemovedLoyaltySku(Set<LoyaltyProgramSku> dbSet, Set<LoyaltyProgramSku> paramSet);
    public boolean deleteLoyaltyProgramSkus( Set<LoyaltyProgramSku> loyaltyProgramSkuSet );
    public List<LoyaltyProgramSku> findByLpuProgramId(Long lpuProgramId);

    public LoyaltyProgramSku saveLoyaltyProgramSku(LoyaltyProgramSku loyaltyProgramSku);
    public boolean deleteLoyaltyProgramSku(Long lpuId);

}

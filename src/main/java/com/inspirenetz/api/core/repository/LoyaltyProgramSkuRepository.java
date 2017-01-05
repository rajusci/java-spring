package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface LoyaltyProgramSkuRepository extends  BaseRepository<LoyaltyProgramSku,Long> {

    public Page<LoyaltyProgramSku> findByLpuProgramId(Long lpuProgramId, Pageable pageable);
    public List<LoyaltyProgramSku> findByLpuProgramId(Long lpuProgramId);
    public LoyaltyProgramSku findByLpuId(Long lpuId);
    public LoyaltyProgramSku findByLpuProgramIdAndLpuItemTypeAndLpuItemCode(Long lpuProgramId,int lpuItemType,String lpuItemCode);

    @Query("select L from LoyaltyProgramSku L where L.lpuProgramId =?1 and ( (L.lpuItemType = 1 and L.lpuItemCode = ?2) or (L.lpuItemType = 2 and L.lpuItemCode = ?3) or (L.lpuItemType = 2 and L.lpuItemCode = ?4) or (L.lpuItemType = 3 and L.lpuItemCode = ?5) or (L.lpuItemType = 3 and L.lpuItemCode = ?6)) order by L.lpuItemType DESC")
    public List<LoyaltyProgramSku> listRulesForLineItem(Long lpuProgramId,String brand,String category1,String category2,String category3,String product);

}

package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.SaleSKU;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface SaleSKURepository extends  BaseRepository<SaleSKU,Long> {

    public List<SaleSKU> findBySsuSaleId(Long ssuSaleId);
    public SaleSKU findBySsuId(Long ssuId);

}

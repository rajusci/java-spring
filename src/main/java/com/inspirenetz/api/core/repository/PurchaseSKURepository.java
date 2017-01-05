package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.PurchaseSKU;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface PurchaseSKURepository extends  BaseRepository<PurchaseSKU,Long> {

    public List<PurchaseSKU> findByPkuPurchaseId(Long pkuPurchaseId);
    public PurchaseSKU findByPkuId(Long pkuId);

}

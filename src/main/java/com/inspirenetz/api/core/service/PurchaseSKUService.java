package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.PurchaseSKU;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface PurchaseSKUService extends BaseService<PurchaseSKU> {

    public List<PurchaseSKU> findByPkuPurchaseId(Long pkuPurchaseId);
    public PurchaseSKU findByPkuId(Long pkuId);

    public PurchaseSKU savePurchaseSku(PurchaseSKU purchaseSKU);
    public boolean deletePurchaseSku(Long pkuId);

}

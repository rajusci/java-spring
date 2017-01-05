package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.CustomerProfilePK;
import com.inspirenetz.api.core.domain.PurchaseSKU;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.PurchaseSKURepository;
import com.inspirenetz.api.core.service.PurchaseSKUService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class PurchaseSKUServiceImpl extends BaseServiceImpl<PurchaseSKU> implements PurchaseSKUService {

    private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Autowired
    PurchaseSKURepository purchaseSKURepository;


    public PurchaseSKUServiceImpl() {

        super(PurchaseSKU.class);

    }


    @Override
    protected BaseRepository<PurchaseSKU,Long> getDao() {
        return purchaseSKURepository;
    }


    @Override
    public List<PurchaseSKU> findByPkuPurchaseId(Long pkuPurchaseId) {

        // Get the data from the repository and store in the list
        List<PurchaseSKU> purchaseSKUList = purchaseSKURepository.findByPkuPurchaseId(pkuPurchaseId);

        // Return the list
        return purchaseSKUList;

    }


    @Override
    public PurchaseSKU findByPkuId(Long pkuId) {

        // Get the purchaseSKU for the given purchaseSKU id from the repository
        PurchaseSKU purchaseSKU = purchaseSKURepository.findByPkuId(pkuId);

        // Return the purchaseSKU
        return purchaseSKU;

    }

    @Override
    public PurchaseSKU savePurchaseSku(PurchaseSKU purchaseSKU) {

        // Save the PurchaseSku object
        return purchaseSKURepository.save(purchaseSKU);

    }

    @Override
    public boolean deletePurchaseSku(Long pkuId) {

        // Delete the PurchaseSKu
        purchaseSKURepository.delete(pkuId);

        // Return true
        return true;

    }


}

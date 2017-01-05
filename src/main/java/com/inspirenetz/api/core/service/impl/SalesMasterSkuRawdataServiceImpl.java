package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.CustomerProfilePK;
import com.inspirenetz.api.core.domain.SalesMasterSkuRawdata;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.SalesMasterSkuRawdataRepository;
import com.inspirenetz.api.core.service.SalesMasterSkuRawdataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class SalesMasterSkuRawdataServiceImpl extends BaseServiceImpl<SalesMasterSkuRawdata> implements SalesMasterSkuRawdataService {

    private static Logger log = LoggerFactory.getLogger(SalesMasterSkuRawdataServiceImpl.class);


    @Autowired
    SalesMasterSkuRawdataRepository salesMasterSkuRawdataRepository;


    public SalesMasterSkuRawdataServiceImpl() {

        super(SalesMasterSkuRawdata.class);

    }


    @Override
    protected BaseRepository<SalesMasterSkuRawdata,Long> getDao() {

        return salesMasterSkuRawdataRepository;
        
    }



    @Override
    public Page<SalesMasterSkuRawdata> findBySmuParentRowIndexAndSmuParentBatchIndex(int smuParentRowIndex,Long smuParentBatchIndex,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<SalesMasterSkuRawdata> salesMasterSkuRawdataList = salesMasterSkuRawdataRepository.findBySmuParentRowIndexAndSmuParentBatchIndex(smuParentRowIndex, smuParentBatchIndex, pageable);

        // Return the list
        return salesMasterSkuRawdataList;

    }


    @Override
    public SalesMasterSkuRawdata findBySmuId(Long smuId) {

        // Get the salesMasterSkuRawdata for the given salesMasterSkuRawdata id from the repository
        SalesMasterSkuRawdata salesMasterSkuRawdata = salesMasterSkuRawdataRepository.findBySmuId(smuId);

        // Return the salesMasterSkuRawdata
        return salesMasterSkuRawdata;

    }
      


    @Override
    public SalesMasterSkuRawdata saveSalesMasterSkuRawdata(SalesMasterSkuRawdata salesMasterSkuRawdata ){

        // Save the salesMasterSkuRawdata
        return salesMasterSkuRawdataRepository.save(salesMasterSkuRawdata);

    }

    @Override
    public boolean deleteSalesMasterSkuRawdata(Long smuId) {

        // Delete the salesMasterSkuRawdata
        salesMasterSkuRawdataRepository.delete(smuId);

        // return true
        return true;

    }

}

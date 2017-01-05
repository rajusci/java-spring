package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.CustomerProfilePK;
import com.inspirenetz.api.core.domain.SalesMasterRawdata;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.SalesMasterRawdataRepository;
import com.inspirenetz.api.core.service.SalesMasterRawdataService;
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
public class SalesMasterRawdataServiceImpl extends BaseServiceImpl<SalesMasterRawdata> implements SalesMasterRawdataService {

    private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Autowired
    SalesMasterRawdataRepository salesMasterRawdataRepository;


    public SalesMasterRawdataServiceImpl() {

        super(SalesMasterRawdata.class);

    }


    @Override
    protected BaseRepository<SalesMasterRawdata,Long> getDao() {

        return salesMasterRawdataRepository;
        
    }



    @Override
    public Page<SalesMasterRawdata> findBySmrMerchantNoAndSmrBatchIndex(Long smrMerchantNo,Long smrBatchIndex,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<SalesMasterRawdata> salesMasterRawdataList = salesMasterRawdataRepository.findBySmrMerchantNoAndSmrBatchIndex(smrMerchantNo,smrBatchIndex,pageable);

        // Return the list
        return salesMasterRawdataList;

    }


    @Override
    public SalesMasterRawdata findBySmrId(Long smrId) {

        // Get the salesMasterRawdata for the given salesMasterRawdata id from the repository
        SalesMasterRawdata salesMasterRawdata = salesMasterRawdataRepository.findBySmrId(smrId);

        // Return the salesMasterRawdata
        return salesMasterRawdata;


    }

    @Override
    public Long getNextBatchIndex() {

        // Save the SalesMasterRawData and get the next index
        SalesMasterRawdata salesMasterRawdata = new SalesMasterRawdata();

        // Set the loyalty id as 0
        salesMasterRawdata.setSmrLoyaltyId("0");

        // Save the data
        salesMasterRawdata = salesMasterRawdataRepository.save(salesMasterRawdata);

        // If the object is not null, then return the batchIndex, else return0
        if ( salesMasterRawdata != null && salesMasterRawdata.getSmrId() != null ) {

            return salesMasterRawdata.getSmrId();

        }

        return 0L;

    }


    @Override
    public SalesMasterRawdata saveSalesMasterRawdata(SalesMasterRawdata salesMasterRawdata ){

        // Save the salesMasterRawdata
        return salesMasterRawdataRepository.save(salesMasterRawdata);

    }

    @Override
    public boolean deleteSalesMasterRawdata(Long smrId) {

        // Delete the salesMasterRawdata
        salesMasterRawdataRepository.delete(smrId);

        // return true
        return true;

    }

}

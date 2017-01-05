package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.SalesMasterRawdata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface SalesMasterRawdataService extends BaseService<SalesMasterRawdata> {

    public Page<SalesMasterRawdata> findBySmrMerchantNoAndSmrBatchIndex(Long smrMerchantNo,Long smrBatchIndex,Pageable pageable);
    public SalesMasterRawdata findBySmrId(Long smrId);
    public Long getNextBatchIndex();

    public SalesMasterRawdata saveSalesMasterRawdata(SalesMasterRawdata salesMasterRawdata);
    public boolean deleteSalesMasterRawdata(Long smrId);

}

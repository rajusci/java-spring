package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.SalesMasterRawdata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface SalesMasterRawdataRepository extends  BaseRepository<SalesMasterRawdata,Long> {

    public Page<SalesMasterRawdata> findBySmrMerchantNoAndSmrBatchIndex(Long smrMerchantNo,Long smrBatchIndex,Pageable pageable);
    public SalesMasterRawdata findBySmrId(Long smrId);

}

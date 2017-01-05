package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.SalesMasterRawdata;
import com.inspirenetz.api.core.domain.SalesMasterSkuRawdata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface SalesMasterSkuRawdataRepository extends  BaseRepository<SalesMasterSkuRawdata,Long> {

    public Page<SalesMasterSkuRawdata> findBySmuParentRowIndexAndSmuParentBatchIndex(int smuParentRowIndex,Long smuParentBatchIndex,Pageable pageable);
    public SalesMasterSkuRawdata findBySmuId(Long smuId);

}

package com.inspirenetz.api.core.repository;


import com.inspirenetz.api.core.domain.BulkUploadBatchInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;

/**
 * Created by ameen on 12/9/15.
 */
public interface BulkUploadBatchInfoRepository extends BaseRepository<BulkUploadBatchInfo,Long>  {

    public Page<BulkUploadBatchInfo> findByBlkMerchantNo(Long brnMerchantNo,Pageable pageable);
    public Page<BulkUploadBatchInfo> findByBlkMerchantNoAndBlkBatchDateBetween(Long brnMerchantNo,Date fromDate,Date toDate,Pageable pageable);
    public BulkUploadBatchInfo findByBlkBatchIndex(Long brnId);
    public BulkUploadBatchInfo findByBlkMerchantNoAndBlkBatchName(Long brnMerchantNo, String brnCode);
}

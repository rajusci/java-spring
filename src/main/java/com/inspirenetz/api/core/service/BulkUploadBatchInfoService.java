package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.BulkUploadMapping;
import com.inspirenetz.api.core.domain.BulkUploadBatchInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.Set;

/**
 * Created by ameen on 12/9/15.
 */
public interface BulkUploadBatchInfoService {
    public Page<BulkUploadBatchInfo> findByBlkMerchantNo(Long brnMerchantNo,Pageable pageable);
    public BulkUploadBatchInfo findByBlkBatchIndex(Long brnId);
    public BulkUploadBatchInfo findByBrnMerchantNoAndBlkBatchName(Long brnMerchantNo, String brnCode);
    public BulkUploadBatchInfo saveBulkUploadBatchInfo(BulkUploadBatchInfo bulkUploadBatchInfo);
    public Page<BulkUploadBatchInfo> findByBlkMerchantNoAndBlkBatchDateBetween(Long brnMerchantNo,Date fromDate,Date toDate,Pageable pageable);


}
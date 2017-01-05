package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.BulkUploadMapping;
import com.inspirenetz.api.core.domain.BulkUploadBatchInfo;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.BulkUploadBatchInfoRepository;
import com.inspirenetz.api.core.service.BulkUploadBatchInfoService;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.integration.BulkUploadXLSParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ameen on 12/9/15.
 */
@Service
public class BulkUploadBatchInfoServiceImpl extends BaseServiceImpl<BulkUploadBatchInfo> implements BulkUploadBatchInfoService {

    private static Logger log = LoggerFactory.getLogger(BulkUploadBatchInfoServiceImpl.class);

    @Autowired
    BulkUploadBatchInfoRepository bulkUploadBatchInfoRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;




    public BulkUploadBatchInfoServiceImpl() {

        super(BulkUploadBatchInfo.class);

    }

    @Override
    protected BaseRepository<BulkUploadBatchInfo,Long> getDao() {
        return bulkUploadBatchInfoRepository;
    }

    @Override
    public Page<BulkUploadBatchInfo> findByBlkMerchantNo(Long brnMerchantNo, Pageable pageable) {
        return bulkUploadBatchInfoRepository.findByBlkMerchantNo(brnMerchantNo,pageable);
    }

    @Override
    public BulkUploadBatchInfo findByBlkBatchIndex(Long batchIndex) {
        return bulkUploadBatchInfoRepository.findByBlkBatchIndex(batchIndex);
    }

    @Override
    public BulkUploadBatchInfo findByBrnMerchantNoAndBlkBatchName(Long brnMerchantNo, String brnCode) {
        return bulkUploadBatchInfoRepository.findByBlkMerchantNoAndBlkBatchName(brnMerchantNo, brnCode);
    }

    @Override
    public BulkUploadBatchInfo saveBulkUploadBatchInfo(BulkUploadBatchInfo bulkUploadBatchInfo) {
        return bulkUploadBatchInfoRepository.save(bulkUploadBatchInfo);
    }

    @Override
    public Page<BulkUploadBatchInfo> findByBlkMerchantNoAndBlkBatchDateBetween(Long blkMerchantNo, Date fromDate, Date toDate, Pageable pageable) {
        return bulkUploadBatchInfoRepository.findByBlkMerchantNoAndBlkBatchDateBetween(blkMerchantNo,fromDate,toDate,pageable);
    }

}

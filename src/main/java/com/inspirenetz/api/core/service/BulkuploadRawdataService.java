package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.BulkUploadMapping;
import com.inspirenetz.api.core.dictionary.BulkUploadRawdataStatus;
import com.inspirenetz.api.core.domain.BulkuploadRawdata;
import com.inspirenetz.api.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sandheepgr on 1/4/14.
 */
public interface BulkuploadRawdataService extends BaseService<BulkuploadRawdata> {

    public List<BulkuploadRawdata> findByBrdMerchantNoAndBrdBatchIndex(Long brdMerchantNo, int brdBatchIndex);
    public List<BulkuploadRawdata> findByBrdMerchantNoAndBrdBatchIndexAndBrdStatus(Long brdMerchantNo, int brdBatchIndex, BulkUploadRawdataStatus brdStatus);
    public List<BulkuploadRawdata> listBulkUploads(Long brdMerchantNo);
    public HashMap<String,String> getHeaderContent(String fileName);
    public void processingBulkUpload(String fileName, Set<BulkUploadMapping> mappingGrammar,User user);
    public BulkuploadRawdata saveRawData(BulkuploadRawdata bulkuploadRawdata);
    public Page<BulkuploadRawdata> findByBrdMerchantNoAndBrdBatchIndex(Long brdMerchantNo, Long brdBatchIndex,Pageable pageable);
}

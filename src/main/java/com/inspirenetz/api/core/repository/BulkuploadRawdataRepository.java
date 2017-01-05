package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.dictionary.BulkUploadRawdataStatus;
import com.inspirenetz.api.core.domain.BulkuploadRawdata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 1/4/14.
 */
public interface BulkuploadRawdataRepository extends BaseRepository<BulkuploadRawdata,Long> {

    public List<BulkuploadRawdata> findByBrdMerchantNoAndBrdBatchIndex(Long brdMerchantNo, int brdBatchIndex);
    public List<BulkuploadRawdata> findByBrdMerchantNoAndBrdBatchIndexAndBrdStatus(Long brdMerchantNo, int brdBatchIndex, BulkUploadRawdataStatus brdStatus);

    @Query("select b from BulkuploadRawdata b where b.brdMerchantNo = ?1 and b.brdBatchIndex = 0 ")
    public List<BulkuploadRawdata> listBulkUploads(Long brdMerchantNo);

    public Page<BulkuploadRawdata> findByBrdMerchantNoAndBrdBatchIndex(Long brdMerchantNo, Long brdBatchIndex,Pageable pageable);
}

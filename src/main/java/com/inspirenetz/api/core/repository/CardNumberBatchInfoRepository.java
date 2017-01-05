package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CardNumberBatchInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;

/**
 * Created by ameen on 20/10/15.
 */
public interface CardNumberBatchInfoRepository extends BaseRepository<CardNumberBatchInfo,Long>{

    public Page<CardNumberBatchInfo> findByCnbMerchantNoAndCnbDateBetweenAndCnbNameLike(Long cnbMerchantNo,Date fromDate,Date toDate,String cnbName,Pageable pageable);

    public Page<CardNumberBatchInfo> findByCnbMerchantNoAndCnbDateBetween(Long cnbMerchantNo,Date fromDate,Date toDate,Pageable pageable);
    public CardNumberBatchInfo findByCnbMerchantNoAndCnbName(Long cnbMerchantNo,String cnbBatchName);

}

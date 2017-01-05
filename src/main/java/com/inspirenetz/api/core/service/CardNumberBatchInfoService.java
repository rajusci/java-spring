package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.CardNumberBatchInfo;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;

/**
 * Created by ameen on 20/10/15.
 */
public interface CardNumberBatchInfoService extends BaseService<CardNumberBatchInfo>{

    public CardNumberBatchInfo saveBatchInfoInformation(CardNumberBatchInfo cardNumberBatchInfo);
    public Page<CardNumberBatchInfo> searchCardNumberBatchInfo(Long cnbMerchantNo,String filter,String query,Date startDate,Date endDate,Pageable pageable);
    public Page<CardNumberBatchInfo> findByCnbMerchantNoAndCnbDateBetweenAndCnbNameLike(Long cnbMerchantNo,Date fromDate,Date toDate,String cnbName,Pageable pageable);
    public Page<CardNumberBatchInfo> findByCnbMerchantNoAndCnbDateBetween(Long cnbMerchantNo,Date fromDate,Date toDate,Pageable pageable);
    public CardNumberBatchInfo validateAnSaveBatchInfoInformation(CardNumberBatchInfo cardNumberBatchInfo) throws InspireNetzException;
    public boolean isCardNumberBatchNameDuplicate(CardNumberBatchInfo cardNumberBatchInfo);
    public void delete(CardNumberBatchInfo cardNumberBatchInfo);

}

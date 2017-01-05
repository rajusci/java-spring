package com.inspirenetz.api.core.service;


import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.domain.MerchantPartnerTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

/**
 * Created by abhi on 13/7/16.
 */

public interface MerchantPartnerTransactionService extends BaseService<MerchantPartnerTransaction> {

    public MerchantPartnerTransaction findByMptId(Long mptId);
    public Page<MerchantPartnerTransaction> findByMptProductNo(Long mptProductNo,Pageable pageable);
    Page<MerchantPartnerTransaction> findByMptMerchantNoAndMptTxnDateBetween(Long mptMerchantNo, Date startDate, Date endDate, Pageable pageable);

    public Page<MerchantPartnerTransaction> findByMptPartnerNo(Long mptPartnerNo,Pageable pageable);
    public Page<MerchantPartnerTransaction> findByMptProductNoAndMptMerchantNo(Long mptProductNo, Long mptMerchantNo,Pageable pageable);
    public Page<MerchantPartnerTransaction> findByMptProductNoAndMptPartnerNo(Long mptProductNo, Long mptPartnerNo,Pageable pageable);
    public Page<MerchantPartnerTransaction> findByMptProductNoAndMptMerchantNoAndMptTxnDateBetween(Long mptProductNo, Long mptMerchantNo, Date startDate, Date endDate,Pageable pageable);
    public Page<MerchantPartnerTransaction> getMerchantPartnerTransactionsForPartner(Long mptProductNo,Long mptMerchantNo,Long mptPartnerNo, Date startDate, Date endDate,Pageable pageable);
    public Page<MerchantPartnerTransaction> findByMptPartnerNoAndMptProductNoAndMptMerchantNoAndMptTxnDateBetween(Long mptPartnerNo,Long mptProductNo, Long mptMerchantNo, Date startDate, Date endDate,Pageable pageable);
    public MerchantPartnerTransaction saveMerchantPartnerTransaction(MerchantPartnerTransaction merchantPartnerTransaction);


    public void addMerchantPartnerTransaction(Catalogue catalogue);

    public Page<MerchantPartnerTransaction> getMerchantPartnerTransactionsForMerchant(Long mptProductNo, Long mptMerchantNo, Long mptPartnerNo, Date startDate, Date endDate, Pageable pageable);

}

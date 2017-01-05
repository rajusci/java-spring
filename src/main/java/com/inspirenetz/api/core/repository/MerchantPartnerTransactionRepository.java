package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.MerchantPartnerTransaction;
import org.apache.commons.net.ntp.TimeStamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by abhi on 13/7/16.
 */
public interface MerchantPartnerTransactionRepository extends BaseRepository<MerchantPartnerTransaction,Long> {

    public MerchantPartnerTransaction findByMptId(Long mptId);
    public Page<MerchantPartnerTransaction> findByMptProductNo(Long mptProductNo,Pageable pageable);
    public Page<MerchantPartnerTransaction> findByMptMerchantNoAndMptTxnDateBetween(Long mptMerchantNo,Date startDate,Date endDate,Pageable pageable);
    public Page<MerchantPartnerTransaction> findByMptPartnerNo(Long mptPartnerNo,Pageable pageable);
    public Page<MerchantPartnerTransaction> findByMptProductNoAndMptMerchantNo(Long mptProductNo, Long mptMerchantNo,Pageable pageable);
    public Page<MerchantPartnerTransaction> findByMptProductNoAndMptPartnerNo(Long mptProductNo, Long mptPartnerNo,Pageable pageable);
    public Page<MerchantPartnerTransaction> findByMptMerchantNoAndMptPartnerNoAndMptTxnDateBetween(Long mptMerchantNo, Long mptPartnerNo,Date startDate, Date endDate,Pageable pageable);
    public Page<MerchantPartnerTransaction> findByMptProductNoAndMptMerchantNoAndMptTxnDateBetween(Long mptProductNo, Long mptMerchantNo, Date startDate, Date endDate,Pageable pageable);
    public Page<MerchantPartnerTransaction> findByMptPartnerNoAndMptProductNoAndMptMerchantNoAndMptTxnDateBetween(Long mptPartnerNo,Long mptProductNo, Long mptMerchantNo, Date startDate, Date endDate,Pageable pageable);

    public Page<MerchantPartnerTransaction> findByMptProductNoAndMptPartnerNoAndMptTxnDateBetween(Long mptProductNo, Long mptPartnerNo, Date startDate, Date endDate, Pageable pageable);

    public Page<MerchantPartnerTransaction> findByMptPartnerNoAndMptTxnDateBetween(Long mptPartnerNo, Date startDate, Date endDate, Pageable pageable);
}

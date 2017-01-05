package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface TransactionRepository extends BaseRepository<Transaction,Long> {

    public Page<Transaction> findByTxnMerchantNoAndTxnRecordStatusAndTxnDateBetweenOrderByTxnIdDesc(Long txnMerchantNo,Integer txnRecordStatus,Date startDate,Date endDate,Pageable pageable);
    public Page<Transaction> findByTxnMerchantNoAndTxnLoyaltyIdAndTxnRecordStatusAndTxnDateBetweenOrderByTxnIdDesc(Long txnMerchantNo,String txnLoyaltyId,Integer txnRecordStatus,Date startDate,Date endDate,Pageable pageable);
    public Transaction findByTxnMerchantNoAndTxnId(Long txnMerchantNo, Long txnId);

    @Query("select T from Transaction T where T.txnMerchantNo  = ?1 and T.txnLoyaltyId = ?2 and T.txnType = ?3 and T.txnDate between ?4 and ?5 and T.txnRecordStatus = ?6")
    public List<Transaction> searchTransactionByTypeAndDateRange(Long txnMerchantNo,String txnLoyaltyId,Integer txnType,Date startDate,Date endDate,Integer txnRecordStatus);

    @Query("select T from Transaction T where T.txnMerchantNo  = ?1 and T.txnLoyaltyId = ?2  and T.txnRecordStatus = ?3 order by T.txnId desc")
    public List<Transaction> listLastTransactions(Long txnMerchantNo,String txnLoyaltyId,Integer txnRecordStatus);

    public List<Transaction> findByTxnMerchantNoAndTxnLoyaltyIdOrderByTxnIdDesc(Long txnMerchantNo,String txnLoyaltyId);

    public List<Transaction> findByTxnMerchantNoAndTxnLoyaltyIdAndTxnInternalRefAndTxnLocationAndTxnDateOrderByTxnIdDesc(Long txnMerchantNo,String txnLoyaltyId,String txnInternalRef,Long txnLocation, Date txnDate);


    List<Transaction> findByTxnLoyaltyIdAndTxnMerchantNoAndTxnProgramId(String txnLoyaltyId, Long txnMerchantNo, Long txnProgramId);
}

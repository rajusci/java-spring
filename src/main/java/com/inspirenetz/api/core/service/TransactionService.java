package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Transaction;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface TransactionService extends BaseService<Transaction> {

    public Page<Transaction> findByTxnMerchantNoAndTxnDateBetweenOrderByTxnIdDesc(Long txnMerchantNo,Date startDate,Date endDate,Pageable pageable);
    public Page<Transaction> findByTxnMerchantNoAndTxnLoyaltyIdAndTxnDateBetweenOrderByTxnIdDesc(Long txnMerchantNo,String txnLoyaltyId,Date startDate,Date endDate,Pageable pageable);
    public Transaction findByTxnMerchantNoAndTxnId(Long txnMerchantNo, Long txnId);
    public List<Transaction> searchTransactionByTypeAndDateRange(Long txnMerchantNo,String txnLoyaltyId,Integer txnType,Date startDate,Date endDate);
    List<Transaction> listTransactions(Long merchantNo, String loyaltyId);
    public void sendTransactionSMS(String loyaltyId) throws InspireNetzException;
    public Transaction saveTransaction(Transaction transaction);
    public boolean deleteTransaction(Long txnId);
    public Page<Transaction> searchCustomerTransaction(Long txnMerchantNo,Date txnStaDate,Date txnEnDate,Pageable pageable) throws InspireNetzException;
    public List<Transaction> getLastTransactionCompatible(Long merchantNo,String cusLoyaltyId,Integer rowCount);
    public Transaction findByTxnMerchantNoAndTxnLoyaltyIdAndTxnInternalRefAndTxnLocationAndTxnDateOrderByTxnIdDesc(Long txnMerchantNo,String txnLoyaltyId,String txnInternalRef,Long txnLocation, Date txnDate);


    List<Transaction> findByTxnLoyaltyIdAndTxnMerchantNoAndTxnPgmId(String txnLoyaltyId, Long txnMerchantNo, Long txnProgramId);
}

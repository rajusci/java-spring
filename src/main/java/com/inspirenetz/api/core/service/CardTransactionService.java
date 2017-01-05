package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.CardTransactionReport;
import com.inspirenetz.api.core.domain.CardTransaction;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CardTransactionService extends BaseService<CardTransaction> {

    public Page<CardTransaction> findByCtxTxnTerminal(Long ctxTxnTerminal, Pageable pageable);
    public Page<CardTransaction> findByCtxTxnTerminalAndCtxCrmId(Long ctxTxnTerminal,Long ctxCrmId, Pageable pageable);
    public Page<CardTransaction> findByCtxTxnTerminalAndCtxCardNumber(Long ctxTxnTerminal,String ctxCardNumber, Pageable pageable);
    public CardTransaction findByCtxTxnNo(Long ctxTxnNo);
    public Page<CardTransaction> searchCardTransactions(Long ctxTxnTerminal,String ctxCardNumber,Integer ctxTxnType,Timestamp startTimestamp,Timestamp endTimestamp,Pageable pageable);

    public boolean isDuplicateCardTransactionExisting(CardTransaction cardTransaction);

    public CardTransaction saveCardTransaction(CardTransaction cardTransaction);
    public boolean deleteCardTransaction(Long ctxTxnNo);

    public Page<CardTransaction> getCardTransactionList(String filter, String query, Timestamp startTimestamp, Timestamp endTimestamp, Integer txnType, Long merchantNo,Pageable pageable);

    public CardTransaction getCardTransactionInfo(Long ctxTxnNo , Long merchantNo) throws InspireNetzException;

    public List<CardTransaction> getCardTransactionCompatible(String crmCardNo, Long merchantNo,Integer maxItem);

    public Double countTxnAmount(Long ctxTxnTerminal,String ctxCardNumber,Integer ctxTxnType);

    public CardTransactionReport getCardTransactionReport(Long ctxTxnTerminal, String ctxCardNumber);

}

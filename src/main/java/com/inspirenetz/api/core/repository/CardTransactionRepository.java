package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CardTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CardTransactionRepository extends  BaseRepository<CardTransaction,Long> {

    public Page<CardTransaction> findByCtxTxnTerminalOrderByCtxTxnNoDesc(Long ctxTxnTerminal, Pageable pageable);
    public Page<CardTransaction> findByCtxTxnTerminalAndCtxCrmIdOrderByCtxTxnNoDesc(Long ctxTxnTerminal, Long ctxCrmId, Pageable pageable);
    public Page<CardTransaction> findByCtxTxnTerminalAndCtxCardNumberOrderByCtxTxnNoDesc(Long ctxTxnTerminal, String ctxCardNumber, Pageable pageable);
    public CardTransaction findByCtxTxnNo(Long ctxTxnNo);

    @Query("select C from CardTransaction C where C.ctxTxnTerminal = ?1 and C.ctxCardNumber =?2 and ( C.ctxTxnType = ?3  or ?3 = 0 ) and C.ctxTxnTimestamp between ?4 and ?5 order by C.ctxTxnNo desc")
    public Page<CardTransaction> searchCardTransactions(Long ctxTxnTerminal,String ctxCardNumber,Integer ctxTxnType,Timestamp startTimestamp,Timestamp endTimestamp,Pageable pageable);

    @Query("select C from CardTransaction C where C.ctxTxnTerminal = ?1 and C.ctxCrmId = ?2 and C.ctxCardNumber = ?3 and C.ctxTxnType = ?4 and C.ctxTxnAmount = ?5 and C.ctxReference = ?6 and C.ctxLocation = ?7")
    public List<CardTransaction> searchDuplicateTransactions(Long ctxTxnTerminal,Long ctxCrmId,String ctxCardNumber,Integer ctxTxnType,Double ctxTxnAmount,String ctxReference,Long ctxLocation);

    public List<CardTransaction> findByCtxCardNumberOrderByCtxTxnNoDesc(String ctxCardNumber);

    @Query("select SUM (C.ctxTxnAmount) from CardTransaction C where C.ctxTxnTerminal = ?1 and C.ctxCardNumber =?2 and ( C.ctxTxnType = ?3)")
    public Double countTxnAmount(Long ctxTxnTerminal,String ctxCardNumber,Integer ctxTxnType);
}

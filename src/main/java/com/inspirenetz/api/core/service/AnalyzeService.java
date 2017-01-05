package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.CardTransaction;
import com.inspirenetz.api.core.domain.Transaction;

/**
 * Created by sandheepgr on 28/7/16.
 */
public interface AnalyzeService {

    void postTransactionToAnalyze(Transaction transaction);
    void postCardTransactionToAnalyze(CardTransaction transaction);

}

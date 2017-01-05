package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.CreditDebitInd;
import com.inspirenetz.api.core.dictionary.TransactionType;
import com.inspirenetz.api.core.domain.Transaction;
import com.inspirenetz.api.test.core.builder.TransactionBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.util.Date;

/**
 * Created by sandheepgr on 17/2/14.
 */
public class TransactionFixture {

    public static Transaction standardTransaction() {

        Transaction transaction = TransactionBuilder.aTransaction()
                .withTxnMerchantNo(1L)
                .withTxnLoyaltyId("9538828853")
                .withTxnLocation(1L)
                .withTxnAmount(10)
                .withTxnCrDbInd(CreditDebitInd.CREDIT)
                .withTxnDate(DBUtils.covertToSqlDate("9999-12-31"))
                .withTxnProgramId(3L)
                .withTxnRewardCurrencyId(2L)
                .withTxnRewardQty(10)
                .withTxnRewardPreBal(0)
                .withTxnRewardPostBal(10)
                .withTxnStatus(1)
                .withTxnExternalRef("000")
                .withTxnInternalRef("999")
                .withTxnRewardExpiryDt(new java.sql.Date(DBUtils.covertToSqlDate("9999-12-31").getTime()))
                .withTxnType(TransactionType.PURCHASE)
                .build();

        return transaction;

    }
}

package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.CardTransactionType;
import com.inspirenetz.api.core.domain.CardTransaction;
import com.inspirenetz.api.test.core.builder.CardTransactionBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 22/7/14.
 */
public class CardTransactionFixture {

    public static CardTransaction standardCardTransaction() {

        CardTransaction cardTransaction = CardTransactionBuilder.aCardTransaction()
                .withCtxTxnTerminal(1L)
                .withCtxCardNumber("1001")
                .withCtxCrmId(0L)
                .withCtxTxnType(CardTransactionType.DEBIT)
                .withCtxTxnAmount(100.0)
                .withCtxReference("123321")
                .withCtxCardBalance(900.0)
                .withCtxLocation(1L)
                .withCtxUserNo(1L)
                .build();

        return cardTransaction;

    }



    public static CardTransaction updatedStandardCardTransaction(CardTransaction cardTransaction) {

        // Change the reference
        cardTransaction.setCtxReference("12322333");

        return cardTransaction;


    }




    public static Set<CardTransaction> standardCardTransactions() {

        Set<CardTransaction> cardTransactionSet = new HashSet<>(0);


        CardTransaction cardTransaction1 = CardTransactionBuilder.aCardTransaction()
                .withCtxTxnTerminal(1L)
                .withCtxCardNumber("1001")
                .withCtxCrmId(0L)
                .withCtxTxnType(CardTransactionType.DEBIT)
                .withCtxTxnAmount(100.0)
                .withCtxReference("123321")
                .withCtxCardBalance(900.0)
                .withCtxLocation(1L)
                .withCtxUserNo(1L)
                .build();


        cardTransactionSet.add(cardTransaction1);




        CardTransaction cardTransaction2 = CardTransactionBuilder.aCardTransaction()
                .withCtxTxnTerminal(1L)
                .withCtxCardNumber("1001")
                .withCtxCrmId(0L)
                .withCtxTxnType(CardTransactionType.TOPUP)
                .withCtxTxnAmount(1000.0)
                .withCtxReference("123321")
                .withCtxCardBalance(1900.0)
                .withCtxLocation(1L)
                .withCtxUserNo(1L)
                .build();

        cardTransactionSet.add(cardTransaction2);




        return cardTransactionSet;
    }
}

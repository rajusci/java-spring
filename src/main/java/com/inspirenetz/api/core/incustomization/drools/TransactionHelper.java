package com.inspirenetz.api.core.incustomization.drools;

import com.inspirenetz.api.core.domain.Transaction;
import com.inspirenetz.api.core.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sandheepgr on 10/9/14.
 */
@Component
public class TransactionHelper {

    @Autowired
    private TransactionService transactionService;

    public boolean checkTransactionsDone(String salLoyaltyId, Long salMerchantNo, Long prgProgramNo) {

        //Get the transactions
        List<Transaction> transactionList = transactionService.findByTxnLoyaltyIdAndTxnMerchantNoAndTxnPgmId(salLoyaltyId,salMerchantNo,prgProgramNo);

        //check if the transactions is null
        if(transactionList == null || transactionList.size() == 0){

            //Return false
            return false;

        }else{

            return true;

        }
    }
}

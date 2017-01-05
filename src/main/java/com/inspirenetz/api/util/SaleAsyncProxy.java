package com.inspirenetz.api.util;

import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.service.LoyaltyEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * Created by sandheepgr on 29/8/14.
 */
@Component
public class SaleAsyncProxy {


    @Autowired
    private LoyaltyEngineService loyaltyEngineService;



    // Create the logger class
    private static Logger log = LoggerFactory.getLogger(AccountBundlingUtils.class);

    /**
     * Function to process sale transaction
     *
     * @param sale        - Sale object
     *
     * @return                  - Return the object with the fields specified
     */
    @Async
    public Future<Void> processSaleTransactionForLoyalty(Sale sale) {

        if(sale.getSalId() != null ){

            loyaltyEngineService.processTransaction(sale);

        }
        return new AsyncResult<>(null);
    }



}

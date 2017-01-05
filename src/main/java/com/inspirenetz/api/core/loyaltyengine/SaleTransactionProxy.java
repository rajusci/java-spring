package com.inspirenetz.api.core.loyaltyengine;

import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.service.LoyaltyEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by sandheepgr on 27/10/16.
 */
@Component
public class SaleTransactionProxy {

    @Autowired
    private LoyaltyEngineService loyaltyEngineService;

    @Async("threadPoolTaskExecutor")
    public void processSaleTransactionForLoyalty(Sale sale) {

        // If the sale returned is not null, then process the transaction
        if ( sale != null && sale.getSalId() != null ) {

            // Call the processTransaction
            loyaltyEngineService.processTransaction(sale);


        }

    }

}

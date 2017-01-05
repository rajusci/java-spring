package com.inspirenetz.api.core.amqp;

import com.inspirenetz.api.core.dictionary.SaleResource;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.service.SaleService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by sandheepgr on 3/1/15.
 */
@Component
public class RMQSaleDataReceiver {

    @Autowired
    private SaleService saleService;

    @Autowired
    private GeneralUtils generalUtils;


    public void readSale(SaleResource saleResource) {

        // Set the session as the localipuser
        // Set the context
        SecurityContextHolder.getContext().setAuthentication(generalUtils.getLocalUserSession());

        // Log the params
        System.out.println("Received <" + saleResource + ">");

        try {

            // save the sale object
            Sale sale = saleService.processSaleFromQueue(saleResource);

            // If the sale returned is not null, then process the transaction
            if ( sale != null && sale.getSalId() != null ) {

                // Call the loyalty processing for the sales.
                saleService.processSaleTransactionForLoyalty(sale);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

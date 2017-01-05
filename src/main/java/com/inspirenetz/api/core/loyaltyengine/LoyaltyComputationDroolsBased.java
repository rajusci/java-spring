package com.inspirenetz.api.core.loyaltyengine;

import com.inspirenetz.api.core.dictionary.CustomerRewardPoint;
import com.inspirenetz.api.core.dictionary.PointRewardData;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.service.DroolsEngineService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sandheepgr on 10/9/14.
 */
public class LoyaltyComputationDroolsBased implements LoyaltyComputation {

    private DroolsEngineService droolsEngineService;

    // Create the logger
    private static Logger log = LoggerFactory.getLogger(LoyaltyComputationDroolsBased.class);


    public LoyaltyComputationDroolsBased(DroolsEngineService droolsEngineService) {

        this.droolsEngineService = droolsEngineService;

    }

    @Override
    public boolean isProgramValidForTransaction(LoyaltyProgram loyaltyProgram, Sale sale) {

        // Always return true here as we are doing the validation and computation
        // on the calculatePoints
        return true;

    }

    @Override
    public CustomerRewardPoint calculatePoints(LoyaltyProgram loyaltyProgram, Sale sale) {


        // Call the droolsEngine process loyalty txn function
        CustomerRewardPoint customerRewardPoint = null;

        try {

            customerRewardPoint = droolsEngineService.processLoyaltyTransaction(loyaltyProgram,sale);

        } catch (InspireNetzException e) {

            // Log the error
            log.error("Error during drools processing : " + e.getMessage());

            // Print the stack track
            e.printStackTrace();

        }

        // If the pointRewardData is null, return 0
        if ( customerRewardPoint == null ) {

            return null;

        }

        // Print the customer REward point object
        log.info("Drools response : " + customerRewardPoint);

        // Return the reward qty field of the pointRewardData object that hold the
        // point awarded by DroolsService
        return customerRewardPoint;



    }


}

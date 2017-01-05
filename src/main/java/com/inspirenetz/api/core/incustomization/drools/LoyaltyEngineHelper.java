package com.inspirenetz.api.core.incustomization.drools;

import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.service.LoyaltyEngineService;
import com.inspirenetz.api.core.service.TierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sandheepgr on 10/9/14.
 */
@Component
public class LoyaltyEngineHelper {

    @Autowired
    private LoyaltyEngineService loyaltyEngineService;

    @Autowired
    private TierService tierService;


    /**
     * Function to return the tier id for the given tier name
     * This function will find the Tier information and then return
     * the tierId from the tier
     * If the tier is not existing, then we return the 0L
     * @param tieName   - The name of the tier
     *
     * @return          - 0L if the tier is not found
     *                    Id of the tier
     */
    public Long getTierIdForName( String tieName) {

        // Get the tier object for the given tier name
        Tier tier = tierService.findByTieName(tieName);

        // If the tierId is not null, then return the tieId
        if ( tier != null ) {

            return tier.getTieId();

        }


        // Return 0L
        return 0L;

    }






}

package com.inspirenetz.api.core.loyaltyengine;

import com.inspirenetz.api.core.dictionary.CatalogueRedemptionItemRequest;
import com.inspirenetz.api.core.dictionary.CatalogueRedemptionItemResponse;
import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.exception.InspireNetzRollBackException;

/**
 * Created by sandheepgr on 23/9/14.
 */
public interface CatalogueRedemption {

    /**
     * Function to check if the redemption request is valid for the given customer
     * and the Catalogue
     *
     * @param catalogueRedemptionItemRequest  - object contains the redemption details
     * @return          - true if the request is valid
     *                    false if the request is not valid
     */
    public boolean isRequestValid(CatalogueRedemptionItemRequest catalogueRedemptionItemRequest) throws InspireNetzException;


    /**
     * Function to redeem the points for the given customer for the requested catalogue
     * This function should also have the logic to call the appropriate api for external
     * systems involved.
     *
     *
     *
     * @param catalogueRedemptionItemRequest      - catalogue redemption request object contains customer and catalogue details
     * @return              - True if the redemption was successful,
     *                        False if the redemption was not completed successfully
     *
     * @throws InspireNetzException
     */
    public CatalogueRedemptionItemResponse redeemPoints(CatalogueRedemptionItemRequest catalogueRedemptionItemRequest) throws InspireNetzException, InspireNetzRollBackException;
}

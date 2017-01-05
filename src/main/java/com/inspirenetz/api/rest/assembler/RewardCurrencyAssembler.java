package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.service.RewardCurrencyService;
import com.inspirenetz.api.rest.controller.RewardCurrencyController;
import com.inspirenetz.api.rest.resource.RewardCurrencyResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 15/4/14.
 */
@Component
public class RewardCurrencyAssembler extends ResourceAssemblerSupport<RewardCurrency,RewardCurrencyResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;


    public RewardCurrencyAssembler() {

        super(RewardCurrencyController.class,RewardCurrencyResource.class);

    }

    @Override
    public RewardCurrencyResource toResource(RewardCurrency rewardCurrency) {

        // Create the RewardCurrencyResource
        RewardCurrencyResource rewardCurrencyResource = mapper.map(rewardCurrency,RewardCurrencyResource.class);

        // Check if the reward currency is setup for cashback
        if ( rewardCurrency.getRwdCashbackIndicator() == IndicatorStatus.YES ) {

            // Set the cashbackValue
            rewardCurrencyResource.setRwdCashbackValue(rewardCurrencyService.getCashbackValue(rewardCurrency,1));

        }

        // Return the rewardCurrencyResource
        return rewardCurrencyResource;
    }


    /**
     * Function to convert the list of RewardCurrency objects into an equivalent list
     * of RewardCurrencyResource objects
     *
     * @param rewardCurrencyList - The List object for the RewardCurrency objects
     * @return rewardCurrencyResourceList - This list of RewardCurrencyResource objects
     */
    public List<RewardCurrencyResource> toResources(List<RewardCurrency> rewardCurrencyList) {

        // Create the list that will hold the resources
        List<RewardCurrencyResource> rewardCurrencyResourceList = new ArrayList<RewardCurrencyResource>();

        // Create the RewardCurrencyResource object
        RewardCurrencyResource rewardCurrencyResource = null;


        // Go through the rewardCurrencys and then create the rewardCurrency resource
        for(RewardCurrency rewardCurrency : rewardCurrencyList ) {

            // Get the RewardCurrencyResource
            rewardCurrencyResource = mapper.map(rewardCurrency,RewardCurrencyResource.class);

            // Check if the reward currency is setup for cashback
            if ( rewardCurrency.getRwdCashbackIndicator() == IndicatorStatus.YES ) {

                // Set the cashbackValue
                rewardCurrencyResource.setRwdCashbackValue(rewardCurrencyService.getCashbackValue(rewardCurrency,1));

            }

            // Add the resource to the array list
            rewardCurrencyResourceList.add(rewardCurrencyResource);

        }


        // return the rewardCurrencyResoueceList
        return rewardCurrencyResourceList;

    }

}

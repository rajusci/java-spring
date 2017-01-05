package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CustomerRewardExpiry;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.service.RewardCurrencyService;
import com.inspirenetz.api.rest.controller.CustomerRewardExpiryController;
import com.inspirenetz.api.rest.resource.CustomerRewardExpiryResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 15/4/14.
 */
@Component
public class CustomerRewardExpiryAssembler extends ResourceAssemblerSupport<CustomerRewardExpiry,CustomerRewardExpiryResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;


    public CustomerRewardExpiryAssembler() {

        super(CustomerRewardExpiryController.class,CustomerRewardExpiryResource.class);

    }

    @Override
    public CustomerRewardExpiryResource toResource(CustomerRewardExpiry customerRewardExpiry) {

        // Create the CustomerRewardExpiryResource
        CustomerRewardExpiryResource customerRewardExpiryResource = mapper.map(customerRewardExpiry,CustomerRewardExpiryResource.class);

        // Return the customerRewardExpiryResource
        return customerRewardExpiryResource;
    }


    /**
     * Function to convert the list of CustomerRewardExpiry objects into an equivalent list
     * of CustomerRewardExpiryResource objects
     *
     * @param customerRewardExpiryList - The List object for the CustomerRewardExpiry objects
     * @return customerRewardExpiryResourceList - This list of CustomerRewardExpiryResource objects
     */
    public List<CustomerRewardExpiryResource> toResources(List<CustomerRewardExpiry> customerRewardExpiryList,Long merchantNo) {

        // Create the list that will hold the resources
        List<CustomerRewardExpiryResource> customerRewardExpiryResourceList = new ArrayList<CustomerRewardExpiryResource>();

        // Create the CustomerRewardExpiryResource object
        CustomerRewardExpiryResource customerRewardExpiryResource = null;


        // Get the rewardCurrencyHashMap
        HashMap<Long,RewardCurrency> rewardCurrencyHashMap = rewardCurrencyService.getRewardCurrencyKeyMap(merchantNo);


        // Go through the customerRewardExpirys and then create the customerRewardExpiry resource
        for(CustomerRewardExpiry customerRewardExpiry : customerRewardExpiryList ) {

            // Get the RewardCurrency entry
            RewardCurrency rewardCurrency = rewardCurrencyHashMap.get(customerRewardExpiry.getCreRewardCurrencyId());

            // If the rewardCurrency is null, then continue the iteration
            if ( rewardCurrency == null  ) {

                continue;

            }


            // Get the CustomerRewardExpiryResource
            customerRewardExpiryResource = mapper.map(customerRewardExpiry,CustomerRewardExpiryResource.class);


            // Set the reward currency name
            customerRewardExpiryResource.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());

            // Add the resource to the array list
            customerRewardExpiryResourceList.add(customerRewardExpiryResource);

        }


        // return the customerRewardExpiryResoueceList
        return customerRewardExpiryResourceList;

    }

}

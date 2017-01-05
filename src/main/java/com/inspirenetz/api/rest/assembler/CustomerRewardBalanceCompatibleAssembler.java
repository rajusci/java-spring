package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CustomerRewardBalance;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.service.RewardCurrencyService;
import com.inspirenetz.api.rest.controller.CustomerRewardBalanceController;
import com.inspirenetz.api.rest.resource.CustomerRewardBalanceCompatibleResource;
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
public class CustomerRewardBalanceCompatibleAssembler extends ResourceAssemblerSupport<CustomerRewardBalance,CustomerRewardBalanceCompatibleResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;


    public CustomerRewardBalanceCompatibleAssembler() {

        super(CustomerRewardBalanceController.class,CustomerRewardBalanceCompatibleResource.class);

    }

    @Override
    public CustomerRewardBalanceCompatibleResource toResource(CustomerRewardBalance customerRewardBalance) {

        // Create the CustomerRewardBalanceCompatibleResource
        CustomerRewardBalanceCompatibleResource customerRewardBalanceResource = new CustomerRewardBalanceCompatibleResource();


        // Return the customerRewardBalanceResource
        return customerRewardBalanceResource;
    }


    /**
     * Function to convert the list of CustomerRewardBalance objects into an equivalent list
     * of CustomerRewardBalanceCompatibleResource objects
     *
     * @param customerRewardBalanceList - The List object for the CustomerRewardBalance objects
     * @return customerRewardBalanceResourceList - This list of CustomerRewardBalanceCompatibleResource objects
     */
    public List<CustomerRewardBalanceCompatibleResource> toResources(List<CustomerRewardBalance> customerRewardBalanceList,Long merchantNo) {


        // Create the list that will hold the resources
        List<CustomerRewardBalanceCompatibleResource> customerRewardBalanceResourceList = new ArrayList<CustomerRewardBalanceCompatibleResource>();

        // Create the CustomerRewardBalanceCompatibleResource object
        CustomerRewardBalanceCompatibleResource customerRewardBalanceResource ;


        // Get the rewardCurrencyHashMap
        HashMap<Long,RewardCurrency> rewardCurrencyHashMap = rewardCurrencyService.getRewardCurrencyKeyMap(merchantNo);

        // Go through the customerRewardBalances and then create the customerRewardBalance resource
        for(CustomerRewardBalance customerRewardBalance : customerRewardBalanceList ) {




            // Get the RewardCurrency entry
            RewardCurrency rewardCurrency = rewardCurrencyHashMap.get(customerRewardBalance.getCrbRewardCurrency());

            // If the rewardCurrency is null, then continue the iteration
            if ( rewardCurrency == null  ) {

                continue;

            }

            // Get the CustomerRewardBalanceCompatibleResource
            customerRewardBalanceResource = new CustomerRewardBalanceCompatibleResource();

            // Set the reward currencyId
            customerRewardBalanceResource.setRwd_id(rewardCurrency.getRwdCurrencyId());

            // Set the reward currency name
            customerRewardBalanceResource.setRwd_name(rewardCurrency.getRwdCurrencyName());;

            // Set the reward balance
            customerRewardBalanceResource.setRwd_balance(customerRewardBalance.getCrbRewardBalance());

            // Get the cashbackValue
            double cashbackValue = rewardCurrencyService.getCashbackValue(rewardCurrency,customerRewardBalance.getCrbRewardBalance());

            // Set the cashbackValue to the resource
            customerRewardBalanceResource.setRwd_cashback_value(cashbackValue);

            // Add the resource to the array list
            customerRewardBalanceResourceList.add(customerRewardBalanceResource);


        }


        // return the customerRewardBalanceResoueceList
        return customerRewardBalanceResourceList;

    }

}

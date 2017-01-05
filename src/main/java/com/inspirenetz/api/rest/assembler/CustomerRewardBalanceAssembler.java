package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.CustomerRewardBalance;
import com.inspirenetz.api.core.domain.DrawChance;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.service.RewardCurrencyService;
import com.inspirenetz.api.rest.controller.CustomerRewardBalanceController;
import com.inspirenetz.api.rest.resource.CustomerResource;
import com.inspirenetz.api.rest.resource.CustomerRewardBalanceResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by sandheepgr on 15/4/14.
 */
@Component
public class CustomerRewardBalanceAssembler extends ResourceAssemblerSupport<CustomerRewardBalance,CustomerRewardBalanceResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;


    public CustomerRewardBalanceAssembler() {

        super(CustomerRewardBalanceController.class,CustomerRewardBalanceResource.class);

    }

    @Override
    public CustomerRewardBalanceResource toResource(CustomerRewardBalance customerRewardBalance) {

        // Create the CustomerRewardBalanceResource
        CustomerRewardBalanceResource customerRewardBalanceResource = mapper.map(customerRewardBalance,CustomerRewardBalanceResource.class);

        // Return the customerRewardBalanceResource
        return customerRewardBalanceResource;
    }


    /**
     * Function to convert the list of CustomerRewardBalance objects into an equivalent list
     * of CustomerRewardBalanceResource objects
     *
     * @param customerRewardBalanceList - The List object for the CustomerRewardBalance objects
     * @return customerRewardBalanceResourceList - This list of CustomerRewardBalanceResource objects
     */
    public List<CustomerRewardBalanceResource> toResources(List<CustomerRewardBalance> customerRewardBalanceList) {

        // Create the list that will hold the resources
        List<CustomerRewardBalanceResource> customerRewardBalanceResourceList = new ArrayList<CustomerRewardBalanceResource>();

        // Create the CustomerRewardBalanceResource object
        CustomerRewardBalanceResource customerRewardBalanceResource = null;

        // Go through the customerRewardBalances and then create the customerRewardBalance resource
        for(CustomerRewardBalance customerRewardBalance : customerRewardBalanceList ) {



            /*RewardCurrency rewardCurrency= customerRewardBalance.getRewardCurrency();*/

            // Get the rewardcurrency for the item
            RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(customerRewardBalance.getCrbRewardCurrency());

            // Get the CustomerRewardBalanceResource
            customerRewardBalanceResource = mapper.map(customerRewardBalance,CustomerRewardBalanceResource.class);

            // Get the cashbackValue
            double cashbackValue = rewardCurrencyService.getCashbackValue(rewardCurrency,customerRewardBalance.getCrbRewardBalance());

            // Set the cashbackValue
            customerRewardBalanceResource.setRwdCashbackValue(cashbackValue);

            // Set the ratioDeno
            customerRewardBalanceResource.setRwdRatioDeno(rewardCurrency.getRwdCashbackRatioDeno());

            // Set the reward currency name
            customerRewardBalanceResource.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());

            // Add the resource to the array list
            customerRewardBalanceResourceList.add(customerRewardBalanceResource);

        }


        // return the customerRewardBalanceResoueceList
        return customerRewardBalanceResourceList;

    }

    /**
     * Function to convert the list of CustomerRewardBalance objects into an equivalent list
     * of CustomerRewardBalanceResource objects
     *
     * @param customerRewardBalanceList - The List object for the CustomerRewardBalance objects
     * @return customerRewardBalanceResourceList - This list of CustomerRewardBalanceResource objects
     */
    public List<CustomerRewardBalanceResource> toResources(List<CustomerRewardBalance> customerRewardBalanceList,DrawChance drawChance) {

        // Create the list that will hold the resources
        List<CustomerRewardBalanceResource> customerRewardBalanceResourceList = toResources(customerRewardBalanceList);

        CustomerRewardBalanceResource customerRewardBalanceResource = new CustomerRewardBalanceResource();

        //set the Draw chances
        if ( drawChance !=null ){

            Long drawType = drawChance.getDrcType() ==null?0L:drawChance.getDrcType();

            if(drawType == DrawType.RAFFLE_TICKET){

                customerRewardBalanceResource.setRwdCurrencyName("Raffle Ticket");

            }

            customerRewardBalanceResource.setCrbRewardBalance(drawChance.getDrcChances());

            customerRewardBalanceResource.setDrawChance(true);

            customerRewardBalanceResourceList.add(customerRewardBalanceResource);


        }


        // return the customerRewardBalanceResoueceList
        return customerRewardBalanceResourceList;

    }

}

package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.domain.Transaction;
import com.inspirenetz.api.rest.controller.TransactionController;
import com.inspirenetz.api.rest.resource.TransactionResource;
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
public class TransactionAssembler extends ResourceAssemblerSupport<Transaction,TransactionResource> {

    @Autowired
    private Mapper mapper;

    public TransactionAssembler() {

        super(TransactionController.class,TransactionResource.class);

    }

    @Override
    public TransactionResource toResource(Transaction transaction) {

        // Create the TransactionResource
        TransactionResource transactionResource = mapper.map(transaction,TransactionResource.class);

        // Get the rewardCurrency
        RewardCurrency rewardCurrency = transaction.getRewardCurrency();

        // Check if the reward currency is not null
        if ( rewardCurrency != null ) {

            transactionResource.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());

        }


        // Return the transactionResource
        return transactionResource;
    }


    /**
     * Function to convert the list of Transaction objects into an equivalent list
     * of TransactionResource objects
     *
     * @param transactionList - The List object for the Transaction objects
     * @return transactionResourceList - This list of TransactionResource objects
     */
    public List<TransactionResource> toResources(List<Transaction> transactionList) {

        // Create the list that will hold the resources
        List<TransactionResource> transactionResourceList = new ArrayList<TransactionResource>();

        // Create the TransactionResource object
        TransactionResource transactionResource = null;


        // Go through the transactions and then create the transaction resource
        for(Transaction transaction : transactionList ) {

            // Get the TransactionResource
            transactionResource = toResource(transaction);

            // Add the resource to the array list
            transactionResourceList.add(transactionResource);

        }


        // return the transactionResoueceList
        return transactionResourceList;

    }

}

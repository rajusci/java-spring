package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CardTransaction;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.service.MerchantLocationService;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.rest.controller.CardTransactionController;
import com.inspirenetz.api.rest.resource.CardTransactionResource;
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
public class CardTransactionAssembler extends ResourceAssemblerSupport<CardTransaction,CardTransactionResource> {

    @Autowired
    private Mapper mapper;



    public CardTransactionAssembler() {

        super(CardTransactionController.class,CardTransactionResource.class);

    }

    @Override
    public CardTransactionResource toResource(CardTransaction cardTransaction) {

        // Create the CardTransactionResource
        CardTransactionResource cardTransactionResource = mapper.map(cardTransaction,CardTransactionResource.class);


        // Return the cardTransactionResource
        return cardTransactionResource;
    }


    /**
     * Function to convert the list of CardTransaction objects into an equivalent list
     * of CardTransactionResource objects
     *
     * @param cardTransactionList - The List object for the CardTransaction objects
     * @return cardTransactionResourceList - This list of CardTransactionResource objects
     */
    public List<CardTransactionResource> toResources(List<CardTransaction> cardTransactionList) {

        // Create the list that will hold the resources
        List<CardTransactionResource> cardTransactionResourceList = new ArrayList<CardTransactionResource>();

        // Create the CardTransactionResource object
        CardTransactionResource cardTransactionResource = null;


        // Go through the cardTransactions and then create the cardTransaction resource
        for(CardTransaction cardTransaction : cardTransactionList ) {

            // Get the CardTransactionResource
            cardTransactionResource = mapper.map(cardTransaction,CardTransactionResource.class);

            // Add the resource to the array list
            cardTransactionResourceList.add(cardTransactionResource);

        }


        // return the cardTransactionResoueceList
        return cardTransactionResourceList;

    }

}

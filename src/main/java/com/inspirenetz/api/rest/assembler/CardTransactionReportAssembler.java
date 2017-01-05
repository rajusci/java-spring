package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.CardTransactionReport;
import com.inspirenetz.api.rest.controller.CardTransactionController;
import com.inspirenetz.api.rest.resource.CardTransactionReportResource;
import com.inspirenetz.api.rest.resource.CardTransactionResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameen on 7/7/15.
 */
@Component
public class CardTransactionReportAssembler extends ResourceAssemblerSupport<CardTransactionReport,CardTransactionReportResource> {

    @Autowired
    private Mapper mapper;

    public CardTransactionReportAssembler() {

        super(CardTransactionController.class,CardTransactionReportResource.class);

    }

    @Override
    public CardTransactionReportResource toResource(CardTransactionReport cardTransactionReport) {

        // Create the CardTransactionResource
        CardTransactionReportResource cardTransactionReportResource = mapper.map(cardTransactionReport,CardTransactionReportResource.class);


        // Return the cardTransactionResource
        return cardTransactionReportResource;
    }


    /**
     * Function to convert the list of CardTransaction objects into an equivalent list
     * of CardTransactionResource objects
     *
     * @param cardTransactionReportsList - The List object for the CardTransaction objects
     * @return cardTransactionResourceList - This list of CardTransactionResource objects
     */
    public List<CardTransactionReportResource> toResources(List<CardTransactionReport> cardTransactionReportsList) {

        // Create the list that will hold the resources
        List<CardTransactionReportResource> cardTransactionReportResourcesList = new ArrayList<CardTransactionReportResource>();

        // Create the CardTransactionResource object
        CardTransactionReportResource cardTransactionReportResource = null;


        // Go through the cardTransactions and then create the cardTransaction resource
        for(CardTransactionReport cardTransactionReport : cardTransactionReportsList ) {

            // Get the CardTransactionResource
            cardTransactionReportResource= toResource(cardTransactionReport);

            // Add the resource to the array list
            cardTransactionReportResourcesList.add(cardTransactionReportResource);

        }


        // return the cardTransactionResoueceList
        return cardTransactionReportResourcesList;
    }
   
}

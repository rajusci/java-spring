package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.rest.controller.CardTypeController;
import com.inspirenetz.api.rest.resource.CardTypeResource;
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
public class CardTypeAssembler extends ResourceAssemblerSupport<CardType,CardTypeResource> {

    @Autowired
    private Mapper mapper;

    public CardTypeAssembler() {

        super(CardTypeController.class,CardTypeResource.class);

    }

    @Override
    public CardTypeResource toResource(CardType cardType) {

        // Create the CardTypeResource
        CardTypeResource cardTypeResource = mapper.map(cardType,CardTypeResource.class);

        // Return the cardTypeResource
        return cardTypeResource;
    }


    /**
     * Function to convert the list of CardType objects into an equivalent list
     * of CardTypeResource objects
     *
     * @param cardTypeList - The List object for the CardType objects
     * @return cardTypeResourceList - This list of CardTypeResource objects
     */
    public List<CardTypeResource> toResources(List<CardType> cardTypeList) {

        // Create the list that will hold the resources
        List<CardTypeResource> cardTypeResourceList = new ArrayList<CardTypeResource>();

        // Create the CardTypeResource object
        CardTypeResource cardTypeResource = null;


        // Go through the cardTypes and then create the cardType resource
        for(CardType cardType : cardTypeList ) {

            // Get the CardTypeResource
            cardTypeResource = mapper.map(cardType,CardTypeResource.class);

            // Add the resource to the array list
            cardTypeResourceList.add(cardTypeResource);

        }


        // return the cardTypeResoueceList
        return cardTypeResourceList;

    }

}

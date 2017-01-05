package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.LoyaltyProgramProductBasedItem;
import com.inspirenetz.api.rest.controller.LoyaltyProgramController;
import com.inspirenetz.api.rest.controller.LoyaltyProgramController;
import com.inspirenetz.api.rest.resource.LoyaltyProgramProductBasedItemResource;
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
public class LoyaltyProgramProductBasedItemAssembler extends ResourceAssemblerSupport<LoyaltyProgramProductBasedItem,LoyaltyProgramProductBasedItemResource> {

    @Autowired
    private Mapper mapper;

    public LoyaltyProgramProductBasedItemAssembler() {

        super(LoyaltyProgramController.class,LoyaltyProgramProductBasedItemResource.class);

    }

    @Override
    public LoyaltyProgramProductBasedItemResource toResource(LoyaltyProgramProductBasedItem loyaltyProgramProductBasedItem) {

        // Create the LoyaltyProgramProductBasedItemResource
        LoyaltyProgramProductBasedItemResource loyaltyProgramProductBasedItemResource = mapper.map(loyaltyProgramProductBasedItem,LoyaltyProgramProductBasedItemResource.class);

        // Return the loyaltyProgramProductBasedItemResource
        return loyaltyProgramProductBasedItemResource;
    }


    /**
     * Function to convert the list of LoyaltyProgramProductBasedItem objects into an equivalent list
     * of LoyaltyProgramProductBasedItemResource objects
     *
     * @param loyaltyProgramProductBasedItemList - The List object for the LoyaltyProgramProductBasedItem objects
     * @return loyaltyProgramProductBasedItemResourceList - This list of LoyaltyProgramProductBasedItemResource objects
     */
    public List<LoyaltyProgramProductBasedItemResource> toResources(List<LoyaltyProgramProductBasedItem> loyaltyProgramProductBasedItemList) {

        // Create the list that will hold the resources
        List<LoyaltyProgramProductBasedItemResource> loyaltyProgramProductBasedItemResourceList = new ArrayList<LoyaltyProgramProductBasedItemResource>();

        // Create the LoyaltyProgramProductBasedItemResource object
        LoyaltyProgramProductBasedItemResource loyaltyProgramProductBasedItemResource = null;


        // Go through the loyaltyProgramProductBasedItems and then create the loyaltyProgramProductBasedItem resource
        for(LoyaltyProgramProductBasedItem loyaltyProgramProductBasedItem : loyaltyProgramProductBasedItemList ) {

            // Get the LoyaltyProgramProductBasedItemResource
            loyaltyProgramProductBasedItemResource = mapper.map(loyaltyProgramProductBasedItem,LoyaltyProgramProductBasedItemResource.class);

            // Add the resource to the array list
            loyaltyProgramProductBasedItemResourceList.add(loyaltyProgramProductBasedItemResource);

        }


        // return the loyaltyProgramProductBasedItemResoueceList
        return loyaltyProgramProductBasedItemResourceList;

    }

}

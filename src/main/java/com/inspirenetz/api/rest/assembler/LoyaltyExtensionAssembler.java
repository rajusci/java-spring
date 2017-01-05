package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LoyaltyExtension;
import com.inspirenetz.api.rest.controller.LoyaltyExtensionController;
import com.inspirenetz.api.rest.resource.LoyaltyExtensionResource;
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
public class LoyaltyExtensionAssembler extends ResourceAssemblerSupport<LoyaltyExtension,LoyaltyExtensionResource> {

    @Autowired
    private Mapper mapper;

    public LoyaltyExtensionAssembler() {

        super(LoyaltyExtensionController.class,LoyaltyExtensionResource.class);

    }

    @Override
    public LoyaltyExtensionResource toResource(LoyaltyExtension loyaltyExtension) {

        // Create the LoyaltyExtensionResource
        LoyaltyExtensionResource loyaltyExtensionResource = mapper.map(loyaltyExtension,LoyaltyExtensionResource.class);

        // Return the loyaltyExtensionResource
        return loyaltyExtensionResource;
    }


    /**
     * Function to convert the list of LoyaltyExtension objects into an equivalent list
     * of LoyaltyExtensionResource objects
     *
     * @param loyaltyExtensionList - The List object for the LoyaltyExtension objects
     * @return loyaltyExtensionResourceList - This list of LoyaltyExtensionResource objects
     */
    public List<LoyaltyExtensionResource> toResources(List<LoyaltyExtension> loyaltyExtensionList) {

        // Create the list that will hold the resources
        List<LoyaltyExtensionResource> loyaltyExtensionResourceList = new ArrayList<LoyaltyExtensionResource>();

        // Create the LoyaltyExtensionResource object
        LoyaltyExtensionResource loyaltyExtensionResource = null;


        // Go through the loyaltyExtensions and then create the loyaltyExtension resource
        for(LoyaltyExtension loyaltyExtension : loyaltyExtensionList ) {

            // Get the LoyaltyExtensionResource
            loyaltyExtensionResource = toResource(loyaltyExtension);

            // Add the resource to the array list
            loyaltyExtensionResourceList.add(loyaltyExtensionResource);

        }


        // return the loyaltyExtensionResoueceList
        return loyaltyExtensionResourceList;

    }

}

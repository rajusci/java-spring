package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.rest.controller.LinkedLoyaltyController;
import com.inspirenetz.api.rest.resource.LinkedLoyaltyResource;
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
public class LinkedLoyaltyAssembler extends ResourceAssemblerSupport<LinkedLoyalty,LinkedLoyaltyResource> {

    @Autowired
    private Mapper mapper;

    public LinkedLoyaltyAssembler() {

        super(LinkedLoyaltyController.class,LinkedLoyaltyResource.class);

    }

    @Override
    public LinkedLoyaltyResource toResource(LinkedLoyalty linkedLoyalty) {

        // Create the LinkedLoyaltyResource
        LinkedLoyaltyResource linkedLoyaltyResource = mapper.map(linkedLoyalty,LinkedLoyaltyResource.class);

        // Get the Customer object for the associated linked loyalty
        // This customer information is set from the service based on the
        // user requesting information and could be primary of the customer/ secondary itself
        Customer customer = linkedLoyalty.getCustomer();

        // Check if the customer is not null
        if ( customer != null ) {

            // map the customer object to the resource as well
            mapper.map(customer,linkedLoyaltyResource);

        }

        // Return the linkedLoyaltyResource
        return linkedLoyaltyResource;
    }


    /**
     * Function to convert the list of LinkedLoyalty objects into an equivalent list
     * of LinkedLoyaltyResource objects
     *
     * @param linkedLoyaltyList - The List object for the LinkedLoyalty objects
     * @return linkedLoyaltyResourceList - This list of LinkedLoyaltyResource objects
     */
    public List<LinkedLoyaltyResource> toResources(List<LinkedLoyalty> linkedLoyaltyList) {

        // Create the list that will hold the resources
        List<LinkedLoyaltyResource> linkedLoyaltyResourceList = new ArrayList<LinkedLoyaltyResource>();

        // Create the LinkedLoyaltyResource object
        LinkedLoyaltyResource linkedLoyaltyResource = null;


        // Go through the linkedLoyaltys and then create the linkedLoyalty resource
        for(LinkedLoyalty linkedLoyalty : linkedLoyaltyList ) {

            // Get the LinkedLoyaltyResource
            linkedLoyaltyResource = toResource(linkedLoyalty);

            // Add the resource to the array list
            linkedLoyaltyResourceList.add(linkedLoyaltyResource);

        }


        // return the linkedLoyaltyResoueceList
        return linkedLoyaltyResourceList;

    }

}

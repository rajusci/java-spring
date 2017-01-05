package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.PartyApproval;
import com.inspirenetz.api.rest.controller.PartyApprovalController;
import com.inspirenetz.api.rest.resource.PartyApprovalResource;
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
public class PartyApprovalAssembler extends ResourceAssemblerSupport<PartyApproval,PartyApprovalResource> {

    @Autowired
    private Mapper mapper;

    public PartyApprovalAssembler() {

        super(PartyApprovalController.class,PartyApprovalResource.class);

    }

    @Override
    public PartyApprovalResource toResource(PartyApproval partyApproval) {

        // Create the PartyApprovalResource
        PartyApprovalResource partyApprovalResource = mapper.map(partyApproval,PartyApprovalResource.class);

        // Get the custome rinformation
        Customer customer = partyApproval.getReqCustomer();

        // Check if th ecustomer is null
        if ( customer != null ) {

            // Map the customer object to the resource
            mapper.map(customer,partyApprovalResource);

        }

        // Return the partyApprovalResource
        return partyApprovalResource;
    }


    /**
     * Function to convert the list of PartyApproval objects into an equivalent list
     * of PartyApprovalResource objects
     *
     * @param partyApprovalList - The List object for the PartyApproval objects
     * @return partyApprovalResourceList - This list of PartyApprovalResource objects
     */
    public List<PartyApprovalResource> toResources(List<PartyApproval> partyApprovalList) {

        // Create the list that will hold the resources
        List<PartyApprovalResource> partyApprovalResourceList = new ArrayList<PartyApprovalResource>();

        // Create the PartyApprovalResource object
        PartyApprovalResource partyApprovalResource = null;


        // Go through the partyApprovals and then create the partyApproval resource
        for(PartyApproval partyApproval : partyApprovalList ) {

            // Get the PartyApprovalResource
            partyApprovalResource = toResource(partyApproval);

            // Add the resource to the array list
            partyApprovalResourceList.add(partyApprovalResource);

        }


        // return the partyApprovalResoueceList
        return partyApprovalResourceList;

    }

}
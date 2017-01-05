package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkRequest;
import com.inspirenetz.api.rest.controller.LinkRequestController;
import com.inspirenetz.api.rest.resource.LinkRequestResource;
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
public class LinkRequestAssembler extends ResourceAssemblerSupport<LinkRequest,LinkRequestResource> {

    @Autowired
    private Mapper mapper;

    public LinkRequestAssembler() {

        super(LinkRequestController.class,LinkRequestResource.class);

    }

    @Override
    public LinkRequestResource toResource(LinkRequest linkRequest) {

        // Create the LinkRequestResource
        LinkRequestResource linkRequestResource = mapper.map(linkRequest,LinkRequestResource.class);

        // Check if the source customer info is set
        Customer customer = linkRequest.getCustomer();

        // If the customer is not null, set the fields
        if ( customer != null ) {

            mapper.map(customer,linkRequestResource);

        }

        // Return the linkRequestResource
        return linkRequestResource;
    }


    /**
     * Function to convert the list of LinkRequest objects into an equivalent list
     * of LinkRequestResource objects
     *
     * @param linkRequestList - The List object for the LinkRequest objects
     * @return linkRequestResourceList - This list of LinkRequestResource objects
     */
    public List<LinkRequestResource> toResources(List<LinkRequest> linkRequestList) {

        // Create the list that will hold the resources
        List<LinkRequestResource> linkRequestResourceList = new ArrayList<LinkRequestResource>();

        // Create the LinkRequestResource object
        LinkRequestResource linkRequestResource = null;


        // Go through the linkRequests and then create the linkRequest resource
        for(LinkRequest linkRequest : linkRequestList ) {

            // Get the LinkRequestResource
            linkRequestResource = toResource(linkRequest);

            // Add the resource to the array list
            linkRequestResourceList.add(linkRequestResource);

        }


        // return the linkRequestResoueceList
        return linkRequestResourceList;

    }

}

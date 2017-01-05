package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.rest.controller.CustomerSegmentController;
import com.inspirenetz.api.rest.resource.CustomerSegmentResource;
import com.inspirenetz.api.rest.resource.CustomerSegmentResource;
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
public class CustomerSegmentAssembler extends ResourceAssemblerSupport<CustomerSegment,CustomerSegmentResource> {

    @Autowired
    private Mapper mapper;

    public CustomerSegmentAssembler() {

        super(CustomerSegmentController.class,CustomerSegmentResource.class);

    }

    @Override
    public CustomerSegmentResource toResource(CustomerSegment customerSegment) {

        // Create the CustomerSegmentResource
        CustomerSegmentResource customerSegmentResource = mapper.map(customerSegment,CustomerSegmentResource.class);

        // Return the customerSegmentResource
        return customerSegmentResource;
    }


    /**
     * Function to convert the list of CustomerSegment objects into an equivalent list
     * of CustomerSegmentResource objects
     *
     * @param customerSegmentList - The List object for the CustomerSegment objects
     * @return customerSegmentResourceList - This list of CustomerSegmentResource objects
     */
    public List<CustomerSegmentResource> toResources(List<CustomerSegment> customerSegmentList) {

        // Create the list that will hold the resources
        List<CustomerSegmentResource> customerSegmentResourceList = new ArrayList<CustomerSegmentResource>();

        // Create the CustomerSegmentResource object
        CustomerSegmentResource customerSegmentResource = null;


        // Go through the customerSegments and then create the customerSegment resource
        for(CustomerSegment customerSegment : customerSegmentList ) {

            // Get the CustomerSegmentResource
            customerSegmentResource = mapper.map(customerSegment,CustomerSegmentResource.class);

            // Add the resource to the array list
            customerSegmentResourceList.add(customerSegmentResource);

        }


        // return the customerSegmentResoueceList
        return customerSegmentResourceList;

    }

}

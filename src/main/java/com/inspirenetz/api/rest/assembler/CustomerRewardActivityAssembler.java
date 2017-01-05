package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerRewardActivity;
import com.inspirenetz.api.rest.controller.CustomerRewardActivityController;
import com.inspirenetz.api.rest.resource.CustomerRewardActivityResource;
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
public class CustomerRewardActivityAssembler extends ResourceAssemblerSupport<CustomerRewardActivity,CustomerRewardActivityResource> {

    @Autowired
    private Mapper mapper;

    public CustomerRewardActivityAssembler() {

        super(CustomerRewardActivityController.class,CustomerRewardActivityResource.class);

    }

    @Override
    public CustomerRewardActivityResource toResource(CustomerRewardActivity customerRewardActivity) {

        // Create the CustomerRewardActivityResource
        CustomerRewardActivityResource customerRewardActivityResource = mapper.map(customerRewardActivity,CustomerRewardActivityResource.class);

        // If the customer is not null, set the fields
        if ( customerRewardActivity != null ) {

            mapper.map(customerRewardActivity,customerRewardActivityResource);

        }

        // Return the customerRewardActivityResource
        return customerRewardActivityResource;
    }


    /**
     * Function to convert the list of CustomerRewardActivity objects into an equivalent list
     * of CustomerRewardActivityResource objects
     *
     * @param customerRewardActivityList - The List object for the CustomerRewardActivity objects
     * @return customerRewardActivityResourceList - This list of CustomerRewardActivityResource objects
     */
    public List<CustomerRewardActivityResource> toResources(List<CustomerRewardActivity> customerRewardActivityList) {

        // Create the list that will hold the resources
        List<CustomerRewardActivityResource> customerRewardActivityResourceList = new ArrayList<CustomerRewardActivityResource>();

        // Create the CustomerRewardActivityResource object
        CustomerRewardActivityResource customerRewardActivityResource = null;


        // Go through the customerRewardActivitys and then create the customerRewardActivity resource
        for(CustomerRewardActivity customerRewardActivity : customerRewardActivityList ) {

            // Get the CustomerRewardActivityResource
            customerRewardActivityResource = toResource(customerRewardActivity);

            // Add the resource to the array list
            customerRewardActivityResourceList.add(customerRewardActivityResource);

        }


        // return the customerRewardActivityResoueceList
        return customerRewardActivityResourceList;

    }

}

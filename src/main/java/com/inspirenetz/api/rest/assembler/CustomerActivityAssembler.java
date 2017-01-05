package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.rest.controller.CustomerActivityController;
import com.inspirenetz.api.rest.controller.CustomerActivityController;
import com.inspirenetz.api.rest.resource.CustomerActivityResource;
import com.inspirenetz.api.rest.resource.CustomerActivityResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saneeshci on 25/9/14.
 */
@Component
public class CustomerActivityAssembler extends ResourceAssemblerSupport<CustomerActivity,CustomerActivityResource> {


    @Autowired
    private Mapper mapper;

    public CustomerActivityAssembler() {

        super(CustomerActivityController.class,CustomerActivityResource.class);

    }

    @Override
    public CustomerActivityResource toResource(CustomerActivity customerActivity) {

        // Create the CustomerActivityResource
        CustomerActivityResource customerActivityResource = mapper.map(customerActivity,CustomerActivityResource.class);

        // Return the customerActivityResource
        return customerActivityResource;
    }


    /**
     * Function to convert the list of CustomerActivity objects into an equivalent list
     * of CustomerActivityResource objects
     *
     * @param customerActivityList - The List object for the CustomerActivity objects
     * @return customerActivityResourceList - This list of CustomerActivityResource objects
     */
    public List<CustomerActivityResource> toResources(List<CustomerActivity> customerActivityList) {

        // Create the list that will hold the resources
        List<CustomerActivityResource> customerActivityResourceList = new ArrayList<CustomerActivityResource>();

        // Create the CustomerActivityResource object
        CustomerActivityResource customerActivityResource = null;


        // Go through the customerActivitys and then create the customerActivity resource
        for(CustomerActivity customerActivity : customerActivityList ) {

            // Get the CustomerActivityResource
            customerActivityResource = toResource(customerActivity);

            // Add the resource to the array list
            customerActivityResourceList.add(customerActivityResource);

        }


        // return the customerActivityResoueceList
        return customerActivityResourceList;

    }

}

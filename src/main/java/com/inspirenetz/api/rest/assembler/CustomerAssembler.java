package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.rest.controller.CustomerController;
import com.inspirenetz.api.rest.resource.CustomerResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 */
@Component
public class CustomerAssembler extends ResourceAssemblerSupport<Customer, CustomerResource>{
    @Autowired
    private Mapper mapper;


    public CustomerAssembler() {

        super(CustomerController.class, CustomerResource.class);

    }

    @Override
    public CustomerResource toResource(Customer customer) {

        // Get the customerResouce
        CustomerResource customerResource = mapper.map(customer, CustomerResource.class);

        //  Check if the tier is set
        Tier tier = customer.getTier();

        // If the tier is not null , then set the data
        if ( tier != null ) {

            // Map the tier to resource
            mapper.map(tier,customerResource);

        }


        // Return the resource
        return customerResource;

    }




    public CustomerResource toResource(Customer customer,CustomerProfile customerProfile,Tier tier) {

        // Get the customerResource from the mapping of the Customer class
        CustomerResource customerResource = mapper.map(customer,CustomerResource.class);

        // Check if the customer Profile is null, otherwise, map the data
        if ( customerProfile != null ) {

            // Now map the customerResource again to the CustomerProfile class
            mapper.map(customerProfile,customerResource);

        }


        // Check if the tier is null, otherwise, map the data
        if ( tier != null ) {

            // Map the tier object
            mapper.map(tier,customerResource);

        }


        // Return the customerResource
        return customerResource;


    }


    /**
     * Function to convert the Customer List to the CustomerResource list
     *
     * @param customers     - The List of customer objects
     *
     * @return - The customerResourceList
     */
    public List<CustomerResource> toResources(List<Customer> customers) {

        if(customers == null) {

            return new ArrayList<>(0);

        }
        // Create the list that will hold the resources
        List<CustomerResource> customerResourceList = new ArrayList<CustomerResource>();

        // Create the CustomerResource object
        CustomerResource customerResource = null;


        // Go through the customers and then create the customer resource
        for(Customer customer : customers ) {

            // Get the CustomerResource
            customerResource = toResource(customer);

            // Add the resource to the array list
            customerResourceList.add(customerResource);

        }

        // return the customerResoueceList
        return customerResourceList;

    }

}

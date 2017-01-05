package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerSubscription;
import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.rest.controller.CustomerSubscriptionController;
import com.inspirenetz.api.rest.resource.CustomerSubscriptionResource;
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
public class CustomerSubscriptionAssembler extends ResourceAssemblerSupport<CustomerSubscription,CustomerSubscriptionResource> {

    @Autowired
    private Mapper mapper;

    public CustomerSubscriptionAssembler() {

        super(CustomerSubscriptionController.class,CustomerSubscriptionResource.class);

    }

    @Override
    public CustomerSubscriptionResource toResource(CustomerSubscription customerSubscription) {

        // Create the CustomerSubscriptionResource
        CustomerSubscriptionResource customerSubscriptionResource = mapper.map(customerSubscription,CustomerSubscriptionResource.class);



        // Get the customer object
        Customer customer = customerSubscription.getCustomer();

        // Check if the customer is not null
        if ( customer != null ) {

            // Map the fields
            customerSubscriptionResource.setCusFName(customer.getCusFName());;

            customerSubscriptionResource.setCusMobile(customer.getCusMobile());

            customerSubscriptionResource.setCusEmail(customer.getCusEmail());

        }



        // Get the Product object
        Product product = customerSubscription.getProduct();

        // Check if the production is null
        if ( product != null ) {

            // Map the fields
            customerSubscriptionResource.setPrdName(product.getPrdName());

        }

        // Return the customerSubscriptionResource
        return customerSubscriptionResource;
    }


    /**
     * Function to convert the list of CustomerSubscription objects into an equivalent list
     * of CustomerSubscriptionResource objects
     *
     * @param customerSubscriptionList - The List object for the CustomerSubscription objects
     * @return customerSubscriptionResourceList - This list of CustomerSubscriptionResource objects
     */
    public List<CustomerSubscriptionResource> toResources(List<CustomerSubscription> customerSubscriptionList) {

        // Create the list that will hold the resources
        List<CustomerSubscriptionResource> customerSubscriptionResourceList = new ArrayList<CustomerSubscriptionResource>();

        // Create the CustomerSubscriptionResource object
        CustomerSubscriptionResource customerSubscriptionResource = null;


        // Go through the customerSubscriptions and then create the customerSubscription resource
        for(CustomerSubscription customerSubscription : customerSubscriptionList ) {

            // Get the CustomerSubscriptionResource
            customerSubscriptionResource = toResource(customerSubscription);

            // Add the resource to the array list
            customerSubscriptionResourceList.add(customerSubscriptionResource);

        }


        // return the customerSubscriptionResoueceList
        return customerSubscriptionResourceList;

    }

}

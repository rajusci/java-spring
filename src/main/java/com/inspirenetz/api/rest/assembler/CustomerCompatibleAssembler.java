package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.rest.controller.CustomerController;
import com.inspirenetz.api.rest.resource.CustomerCompatibleResource;
import com.inspirenetz.api.rest.resource.CustomerResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 */
@Component
public class CustomerCompatibleAssembler extends ResourceAssemblerSupport<Customer, CustomerCompatibleResource>{
    @Autowired
    private Mapper mapper;


    public CustomerCompatibleAssembler() {

        super(CustomerController.class, CustomerCompatibleResource.class);

    }

    @Override
    public CustomerCompatibleResource toResource(Customer customer) {

        // Get the customerCompatibleResource
        CustomerCompatibleResource customerCompatibleResource = mapper.map(customer, CustomerCompatibleResource.class);

        //  Check if the tier is set
        Tier tier = customer.getTier();

        // Check if the tier is null, otherwise, map the data
        if ( tier != null ) {

            // Map the tier object
            mapper.map(tier,customerCompatibleResource);

        }

        // Return the resource
        return customerCompatibleResource;

    }




    public CustomerCompatibleResource toResource(Customer customer,CustomerProfile customerProfile) {

        // Get the customerCompatibleResource from the mapping of the Customer class
        CustomerCompatibleResource customerCompatibleResource ;


        // Get the CustomerCompatibleResource
        customerCompatibleResource = new CustomerCompatibleResource();

        // Set the customer loyalty id
        customerCompatibleResource.setCustomerno(customer.getCusCustomerNo());

        // Set the customer loyalty id
        customerCompatibleResource.setLoyalty_id(customer.getCusLoyaltyId());

        // Set the customer first name
        customerCompatibleResource.setFirstname(customer.getCusFName());

        // Set the customer last name
        customerCompatibleResource.setLastname(customer.getCusLName());

        // Set the customer email
        customerCompatibleResource.setEmail(customer.getCusEmail());

        // Set the customer mobile
        customerCompatibleResource.setMobile(customer.getCusMobile());

        // Check if the customer Profile is null, otherwise, map the data
        if ( customerProfile != null ) {

            // Set the customer address
            customerCompatibleResource.setAddress(customerProfile.getCspAddress());

            // Set the customer city
            customerCompatibleResource.setCity(customerProfile.getCspCity());

            // Set the customer pincode
            customerCompatibleResource.setPincode(customerProfile.getCspPincode());

            customerCompatibleResource.setBirthday(customerProfile.getCspCustomerBirthday());

            customerCompatibleResource.setAnniversary(customerProfile.getCspCustomerAnniversary());

        }

        // Return the customerCompatibleResource
        return customerCompatibleResource;


    }

}

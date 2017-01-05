package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CustomerReferral;
import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.core.service.ProductService;
import com.inspirenetz.api.rest.controller.CustomerReferralController;
import com.inspirenetz.api.rest.resource.CustomerReferralResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fayiz on 27/4/15.
 */
@Component
public class CustomerReferralAssembler extends ResourceAssemblerSupport<CustomerReferral,CustomerReferralResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ProductService productService;

    public CustomerReferralAssembler() {

        super(CustomerReferralController.class,CustomerReferralResource.class);

    }

    @Override
    public CustomerReferralResource toResource(CustomerReferral customerReferral) {

        CustomerReferralResource customerReferralResource= mapper.map(customerReferral, CustomerReferralResource.class);

        //MAP PRODUCT NAME OTHER if not null
        if(customerReferral !=null){

            //get product information
            Product product=productService.findByPrdMerchantNoAndPrdCode(customerReferral.getCsrMerchantNo(),customerReferral.getCsrProduct()==null?"":customerReferral.getCsrProduct());

            if(product !=null){

                customerReferralResource.setProductName(product.getPrdName());
            }
        }

        return customerReferralResource;

    }

    /**
     * Function to convert the list of CustomerReferral objects into an equivalent list
     * of CustomerReferralResource objects
     *
     * @param  customerReferrals- The List object for the CustomerReferral objects
     * @return customerReferralResources - This list of CustomerReferralResource objects
     */
    public List<CustomerReferralResource> toResources(List<CustomerReferral> customerReferrals) {

        // Create the list that will hold the resources
        List<CustomerReferralResource> customerReferralResources = new ArrayList<CustomerReferralResource>();

        // Create the CustomerReferralResource object
        CustomerReferralResource customerReferralResource = null;


        // Go through the CustomerReferral and then create the CustomerReferralResource
        for(CustomerReferral customerReferral : customerReferrals ) {

            // Get the message spiel resource
            customerReferralResource = toResource(customerReferral);

            // Add the resource to the array list
            customerReferralResources.add(customerReferralResource);

        }


        // return the CustomerReferralResourceList
        return customerReferralResources;

    }
}

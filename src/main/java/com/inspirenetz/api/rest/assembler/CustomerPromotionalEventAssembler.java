package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CustomerPromotionalEvent;
import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.core.service.ProductService;
import com.inspirenetz.api.rest.controller.CustomerPromotionalEventController;
import com.inspirenetz.api.rest.resource.CustomerPromotionalEventResource;
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
public class CustomerPromotionalEventAssembler extends ResourceAssemblerSupport<CustomerPromotionalEvent,CustomerPromotionalEventResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    ProductService productService;

    public CustomerPromotionalEventAssembler() {

        super(CustomerPromotionalEventController.class,CustomerPromotionalEventResource.class);

    }

    @Override
    public CustomerPromotionalEventResource toResource(CustomerPromotionalEvent customerPromotionalEvent) {

        // Create the CustomerPromotionalEventResource
        CustomerPromotionalEventResource customerPromotionalEventResource = mapper.map(customerPromotionalEvent,CustomerPromotionalEventResource.class);

        //get product information
        Product product=productService.findByPrdMerchantNoAndPrdCode(customerPromotionalEventResource.getCpeMerchantNo(),customerPromotionalEventResource.getCpeProduct()==null?"":customerPromotionalEventResource.getCpeProduct());

        if(product !=null){

            customerPromotionalEventResource.setProductName(product.getPrdName());
        }

        // Return the customerPromotionalEventResource
        return customerPromotionalEventResource;
    }


    /**
     * Function to convert the list of CustomerPromotionalEvent objects into an equivalent list
     * of CustomerPromotionalEventResource objects
     *
     * @param customerPromotionalEventList - The List object for the CustomerPromotionalEvent objects
     * @return customerPromotionalEventResourceList - This list of CustomerPromotionalEventResource objects
     */
    public List<CustomerPromotionalEventResource> toResources(List<CustomerPromotionalEvent> customerPromotionalEventList) {

        // Create the list that will hold the resources
        List<CustomerPromotionalEventResource> customerPromotionalEventResourceList = new ArrayList<CustomerPromotionalEventResource>();

        // Create the CustomerPromotionalEventResource object
        CustomerPromotionalEventResource customerPromotionalEventResource = null;


        // Go through the customerPromotionalEvents and then create the customerPromotionalEvent resource
        for(CustomerPromotionalEvent customerPromotionalEvent : customerPromotionalEventList ) {

            // Add the resource to the array list
            customerPromotionalEventResourceList.add(toResource(customerPromotionalEvent));

        }


        // return the customerPromotionalEventResoueceList
        return customerPromotionalEventResourceList;

    }

}

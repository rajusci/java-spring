package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Purchase;
import com.inspirenetz.api.rest.controller.PurchaseController;
import com.inspirenetz.api.rest.resource.PurchaseResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 27/4/14.
 */
@Component
public class PurchaseAssembler extends ResourceAssemblerSupport<Purchase,PurchaseResource> {
    
    @Autowired
    private Mapper mapper;
    
    
    public PurchaseAssembler() {
        
        super(PurchaseController.class,PurchaseResource.class);
        
    }


    @Override
    public PurchaseResource toResource(Purchase purchase) {

        // Create the PurchaseResource
        PurchaseResource purchaseResource = mapper.map(purchase,PurchaseResource.class);

        // Return the purchaseResource
        return purchaseResource;
    }



    /**
     * Function to convert the list of Purchase objects into an equivalent list
     * of PurchaseResource objects
     *
     * @param purchaseList - The List object for the Purchase objects
     * @return purchaseResourceList - This list of PurchaseResource objects
     */
    public List<PurchaseResource> toResources(List<Purchase> purchaseList) {

        // Create the list that will hold the resources
        List<PurchaseResource> purchaseResourceList = new ArrayList<PurchaseResource>();

        // Create the PurchaseResource object
        PurchaseResource purchaseResource = null;


        // Go through the purchases and then create the purchase resource
        for(Purchase purchase : purchaseList ) {

            // Get the PurchaseResource
            purchaseResource = mapper.map(purchase,PurchaseResource.class);

            // Add the resource to the array list
            purchaseResourceList.add(purchaseResource);

        }


        // return the purchaseResoueceList
        return purchaseResourceList;

    }
}

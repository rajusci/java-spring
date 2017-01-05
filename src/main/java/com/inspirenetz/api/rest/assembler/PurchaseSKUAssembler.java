package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.core.domain.PurchaseSKU;
import com.inspirenetz.api.rest.controller.TransactionController;
import com.inspirenetz.api.rest.resource.PurchaseSKUResource;
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
public class PurchaseSKUAssembler extends ResourceAssemblerSupport<PurchaseSKU,PurchaseSKUResource> {

    @Autowired
    private Mapper mapper;

    public PurchaseSKUAssembler() {

        super(TransactionController.class,PurchaseSKUResource.class);

    }

    @Override
    public PurchaseSKUResource toResource(PurchaseSKU purchaseSKU) {

        // Create the PurchaseSKUResource
        PurchaseSKUResource purchaseSKUResource = mapper.map(purchaseSKU,PurchaseSKUResource.class);

        // Return the purchaseSKUResource
        return purchaseSKUResource;
    }


    /**
     * Function to convert the list of PurchaseSKU objects into an equivalent list
     * of PurchaseSKUResource objects
     *
     * @param purchaseSKUList - The List object for the PurchaseSKU objects
     * @return purchaseSKUResourceList - This list of PurchaseSKUResource objects
     */
    public List<PurchaseSKUResource> toResources(List<PurchaseSKU> purchaseSKUList) {

        // Create the list that will hold the resources
        List<PurchaseSKUResource> purchaseSKUResourceList = new ArrayList<PurchaseSKUResource>();

        // Create the PurchaseSKUResource object
        PurchaseSKUResource purchaseSKUResource = null;


        // Go through the purchaseSKUs and then create the purchaseSKU resource
        for(PurchaseSKU purchaseSKU : purchaseSKUList ) {

            // Get the PurchaseSKUResource
            purchaseSKUResource = toResource(purchaseSKU);

            // Add the resource to the array list
            purchaseSKUResourceList.add(purchaseSKUResource);

        }


        // return the purchaseSKUResoueceList
        return purchaseSKUResourceList;

    }

}

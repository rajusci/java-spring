package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.WalletMerchant;
import com.inspirenetz.api.rest.controller.WalletMerchantController;
import com.inspirenetz.api.rest.resource.WalletMerchantResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 21/4/14.
 */
@Component
public class WalletMerchantAssembler extends ResourceAssemblerSupport<WalletMerchant,WalletMerchantResource> {

    @Autowired
    private Mapper mapper;

    public WalletMerchantAssembler() {

        super(WalletMerchantController.class,WalletMerchantResource.class);

    }

    @Override
    public WalletMerchantResource toResource(WalletMerchant brand) {

        // Create the WalletMerchantResource
        WalletMerchantResource brandResource = mapper.map(brand,WalletMerchantResource.class);

        // Return the brandResource
        return brandResource;
    }


    /**
     * Function to convert the list of WalletMerchant objects into an equivalent list
     * of WalletMerchantResource objects
     *
     * @param brandList - The List object for the WalletMerchant objects
     * @return brandResourceList - This list of WalletMerchantResource objects
     */
    public List<WalletMerchantResource> toResources(List<WalletMerchant> brandList) {

        // Create the list that will hold the resources
        List<WalletMerchantResource> brandResourceList = new ArrayList<WalletMerchantResource>();

        // Create the WalletMerchantResource object
        WalletMerchantResource brandResource = null;


        // Go through the brands and then create the brand resource
        for(WalletMerchant brand : brandList ) {

            // Get the WalletMerchantResource
            brandResource = mapper.map(brand,WalletMerchantResource.class);

            // Add the resource to the array list
            brandResourceList.add(brandResource);

        }


        // return the brandResoueceList
        return brandResourceList;

    }

}

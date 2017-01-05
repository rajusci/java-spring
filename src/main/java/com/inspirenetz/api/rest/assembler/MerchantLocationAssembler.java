package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.rest.controller.MerchantLocationController;
import com.inspirenetz.api.rest.resource.MerchantLocationResource;
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
public class MerchantLocationAssembler extends ResourceAssemblerSupport<MerchantLocation,MerchantLocationResource> {

    @Autowired
    private Mapper mapper;

    public MerchantLocationAssembler() {

        super(MerchantLocationController.class,MerchantLocationResource.class);

    }

    @Override
    public MerchantLocationResource toResource(MerchantLocation merchantLocation) {

        // Create the MerchantLocationResource
        MerchantLocationResource merchantLocationResource = mapper.map(merchantLocation,MerchantLocationResource.class);

        // Return the merchantLocationResource
        return merchantLocationResource;
    }


    /**
     * Function to convert the list of MerchantLocation objects into an equivalent list
     * of MerchantLocationResource objects
     *
     * @param merchantLocationList - The List object for the MerchantLocation objects
     * @return merchantLocationResourceList - This list of MerchantLocationResource objects
     */
    public List<MerchantLocationResource> toResources(List<MerchantLocation> merchantLocationList) {

        // Create the list that will hold the resources
        List<MerchantLocationResource> merchantLocationResourceList = new ArrayList<MerchantLocationResource>();

        // Create the MerchantLocationResource object
        MerchantLocationResource merchantLocationResource = null;


        // Go through the merchantLocations and then create the merchantLocation resource
        for(MerchantLocation merchantLocation : merchantLocationList ) {

            // Get the MerchantLocationResource
            merchantLocationResource = mapper.map(merchantLocation,MerchantLocationResource.class);

            // Add the resource to the array list
            merchantLocationResourceList.add(merchantLocationResource);

        }


        // return the merchantLocationResoueceList
        return merchantLocationResourceList;

    }

}

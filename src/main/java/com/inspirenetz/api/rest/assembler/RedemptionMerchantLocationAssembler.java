package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.RedemptionMerchantLocation;
import com.inspirenetz.api.rest.controller.RedemptionMerchantLocationController;
import com.inspirenetz.api.rest.resource.RedemptionMerchantLocationResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saneeshci on 26/9/14.
 */
@Component
public class RedemptionMerchantLocationAssembler extends ResourceAssemblerSupport<RedemptionMerchantLocation,RedemptionMerchantLocationResource> {

    @Autowired
    private Mapper mapper;

    public RedemptionMerchantLocationAssembler() {

        super(RedemptionMerchantLocationController.class,RedemptionMerchantLocationResource.class);

    }

    @Override
    public RedemptionMerchantLocationResource toResource(RedemptionMerchantLocation redemptionMerchantLocation) {

        // Create the RedemptionMerchantLocationResource
        RedemptionMerchantLocationResource redemptionMerchantLocationResource = mapper.map(redemptionMerchantLocation,RedemptionMerchantLocationResource.class);

        // Return the redemptionMerchantLocationResource
        return redemptionMerchantLocationResource;
    }


    /**
     * Function to convert the list of RedemptionMerchantLocation objects into an equivalent list
     * of RedemptionMerchantLocationResource objects
     *
     * @param redemptionMerchantLocationList - The List object for the RedemptionMerchantLocation objects
     * @return redemptionMerchantLocationResourceList - This list of RedemptionMerchantLocationResource objects
     */
    public List<RedemptionMerchantLocationResource> toResources(List<RedemptionMerchantLocation> redemptionMerchantLocationList) {

        // Create the list that will hold the resources
        List<RedemptionMerchantLocationResource> redemptionMerchantLocationResourceList = new ArrayList<RedemptionMerchantLocationResource>();

        // Create the RedemptionMerchantLocationResource object
        RedemptionMerchantLocationResource redemptionMerchantLocationResource = null;


        // Go through the redemptionMerchantLocations and then create the redemptionMerchantLocation resource
        for(RedemptionMerchantLocation redemptionMerchantLocation : redemptionMerchantLocationList ) {

            redemptionMerchantLocationResource = mapper.map(redemptionMerchantLocation,RedemptionMerchantLocationResource.class);

            // Add the resource to the array list
            redemptionMerchantLocationResourceList.add(redemptionMerchantLocationResource);

        }


        // return the redemptionMerchantLocationResoueceList
        return redemptionMerchantLocationResourceList;

    }

}

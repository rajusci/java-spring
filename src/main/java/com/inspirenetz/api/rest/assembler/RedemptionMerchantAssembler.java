package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.rest.controller.RedemptionMerchantController;
import com.inspirenetz.api.rest.resource.RedemptionMerchantResource;
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
public class RedemptionMerchantAssembler extends ResourceAssemblerSupport<RedemptionMerchant,RedemptionMerchantResource> {

    @Autowired
    private Mapper mapper;

    public RedemptionMerchantAssembler() {

        super(RedemptionMerchantController.class,RedemptionMerchantResource.class);

    }

    @Override
    public RedemptionMerchantResource toResource(RedemptionMerchant redemptionMerchant) {

        // Create the RedemptionMerchantResource
        RedemptionMerchantResource redemptionMerchantResource = mapper.map(redemptionMerchant,RedemptionMerchantResource.class);

        // Return the redemptionMerchantResource
        return redemptionMerchantResource;
    }


    /**
     * Function to convert the list of RedemptionMerchant objects into an equivalent list
     * of RedemptionMerchantResource objects
     *
     * @param redemptionMerchantList - The List object for the RedemptionMerchant objects
     * @return redemptionMerchantResourceList - This list of RedemptionMerchantResource objects
     */
    public List<RedemptionMerchantResource> toResources(List<RedemptionMerchant> redemptionMerchantList) {

        // Create the list that will hold the resources
        List<RedemptionMerchantResource> redemptionMerchantResourceList = new ArrayList<RedemptionMerchantResource>();

        // Create the RedemptionMerchantResource object
        RedemptionMerchantResource redemptionMerchantResource = null;


        // Go through the redemptionMerchants and then create the redemptionMerchant resource
        for(RedemptionMerchant redemptionMerchant : redemptionMerchantList ) {

            // Add the resource to the array list
            redemptionMerchantResourceList.add(toResource(redemptionMerchant));

        }


        // return the redemptionMerchantResoueceList
        return redemptionMerchantResourceList;

    }

}

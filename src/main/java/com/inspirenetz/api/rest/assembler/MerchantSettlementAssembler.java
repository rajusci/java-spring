package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.MerchantSettlement;
import com.inspirenetz.api.rest.controller.MerchantSettlementController;
import com.inspirenetz.api.rest.resource.MerchantSettlementResource;
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
public class MerchantSettlementAssembler extends ResourceAssemblerSupport<MerchantSettlement,MerchantSettlementResource> {

    @Autowired
    private Mapper mapper;

    public MerchantSettlementAssembler() {

        super(MerchantSettlementController.class,MerchantSettlementResource.class);

    }

    @Override
    public MerchantSettlementResource toResource(MerchantSettlement merchantSettlement) {

        // Create the MerchantSettlementResource
        MerchantSettlementResource merchantSettlementResource = mapper.map(merchantSettlement,MerchantSettlementResource.class);

        // Return the merchantSettlementResource
        return merchantSettlementResource;
    }


    /**
     * Function to convert the list of MerchantSettlement objects into an equivalent list
     * of MerchantSettlementResource objects
     *
     * @param merchantSettlementList - The List object for the MerchantSettlement objects
     * @return merchantSettlementResourceList - This list of MerchantSettlementResource objects
     */
    public List<MerchantSettlementResource> toResources(List<MerchantSettlement> merchantSettlementList) {

        // Create the list that will hold the resources
        List<MerchantSettlementResource> merchantSettlementResourceList = new ArrayList<MerchantSettlementResource>();

        // Create the MerchantSettlementResource object
        MerchantSettlementResource merchantSettlementResource = null;


        // Go through the merchantSettlements and then create the merchantSettlement resource
        for(MerchantSettlement merchantSettlement : merchantSettlementList ) {

            // Add the resource to the array list
            merchantSettlementResourceList.add(toResource(merchantSettlement));

        }


        // return the merchantSettlementResoueceList
        return merchantSettlementResourceList;

    }

}

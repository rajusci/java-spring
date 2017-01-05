package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.MerchantRedemptionPartner;
import com.inspirenetz.api.rest.controller.MerchantRedemptionPartnerController;
import com.inspirenetz.api.rest.resource.MerchantPublicResource;
import com.inspirenetz.api.rest.resource.MerchantRedemptionPartnerResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameen on 25/6/15.
 */
@Component
public class MerchantRedemptionPartnerAssembler extends ResourceAssemblerSupport<MerchantRedemptionPartner,MerchantRedemptionPartnerResource> {

    @Autowired
    private Mapper mapper;

    public MerchantRedemptionPartnerAssembler() {

        super(MerchantRedemptionPartnerController.class,MerchantRedemptionPartnerResource.class);

    }

    @Override
    public MerchantRedemptionPartnerResource toResource(MerchantRedemptionPartner merchantRedemptionPartner) {

        MerchantRedemptionPartnerResource merchantRedemptionPartnerResource= mapper.map(merchantRedemptionPartner, MerchantRedemptionPartnerResource.class);



        return merchantRedemptionPartnerResource;

    }

    /**
     * Function to convert the list of MerchantRedemptionPartnerl objects into an equivalent list
     * of MerchantRedemptionPartnerlResource objects
     *
     * @param  merchantRedemptionPartners- The List object for the MerchantRedemptionPartnerl objects
     * @return MerchantRedemptionPartnerlList - This list of MerchantRedemptionPartnerlResource objects
     */
    public List<MerchantRedemptionPartnerResource> toResources(List<MerchantRedemptionPartner> merchantRedemptionPartners) {

        // Create the list that will hold the resources
        List<MerchantRedemptionPartnerResource> merchantRedemptionPartnerResourceList = new ArrayList<MerchantRedemptionPartnerResource>();

        // Create the MerchantRedemptionPartnerlResource object
        MerchantRedemptionPartnerResource merchantRedemptionPartnerResource = null;


        // Go through the MerchantRedemptionPartnerl and then create the MerchantRedemptionPartnerl Resource
        for(MerchantRedemptionPartner merchantRedemptionPartner : merchantRedemptionPartners ) {

            // Get the message spiel resource
            merchantRedemptionPartnerResource = toResource(merchantRedemptionPartner);

            // Add the resource to the array list
            merchantRedemptionPartnerResourceList.add(merchantRedemptionPartnerResource);

        }


        // return the MerchantRedemptionPartnerlResourceList
        return merchantRedemptionPartnerResourceList;

    }
}

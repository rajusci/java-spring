package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantSettlementCycle;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.core.service.RedemptionMerchantService;
import com.inspirenetz.api.rest.controller.MerchantSettlementCycleController;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.MerchantSettlementCycleResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saneeshci on 21/10/15.
 */
@Component
public class MerchantSettlementCycleAssembler extends ResourceAssemblerSupport<MerchantSettlementCycle,MerchantSettlementCycleResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    MerchantService merchantService;

    @Autowired
    RedemptionMerchantService redemptionMerchantService;

    public MerchantSettlementCycleAssembler() {

        super(MerchantSettlementCycleController.class,MerchantSettlementCycleResource.class);

    }

    @Override
    public MerchantSettlementCycleResource toResource(MerchantSettlementCycle merchantSettlementCycle) {

        // Create the MerchantSettlementCycleResource
        MerchantSettlementCycleResource merchantSettlementCycleResource = mapper.map(merchantSettlementCycle,MerchantSettlementCycleResource.class);

        Merchant merchant=merchantService.findByMerMerchantNo(merchantSettlementCycleResource.getMscMerchantNo());

        if(merchant!=null){

            merchantSettlementCycleResource.setMerchantName(merchant.getMerMerchantName());
        }

        try{

            RedemptionMerchant redemptionMerchant=redemptionMerchantService.findByRemNo(merchantSettlementCycleResource.getMscRedemptionMerchant());

            merchantSettlementCycleResource.setRedemptionMerchantName(redemptionMerchant.getRemName());

        }catch (InspireNetzException ex){

        }

        // Return the merchantSettlementCycleResource
        return merchantSettlementCycleResource;
    }


    /**
     * Function to convert the list of MerchantSettlementCycle objects into an equivalent list
     * of MerchantSettlementCycleResource objects
     *
     * @param merchantSettlementCycleList - The List object for the MerchantSettlementCycle objects
     * @return merchantSettlementCycleResourceList - This list of MerchantSettlementCycleResource objects
     */
    public List<MerchantSettlementCycleResource> toResources(List<MerchantSettlementCycle> merchantSettlementCycleList) {

        // Create the list that will hold the resources
        List<MerchantSettlementCycleResource> merchantSettlementCycleResourceList = new ArrayList<MerchantSettlementCycleResource>();

        // Create the MerchantSettlementCycleResource object
        MerchantSettlementCycleResource merchantSettlementCycleResource = null;


        // Go through the merchantSettlementCycles and then create the merchantSettlementCycle resource
        for(MerchantSettlementCycle merchantSettlementCycle : merchantSettlementCycleList ) {

            // Add the resource to the array list
            merchantSettlementCycleResourceList.add(toResource(merchantSettlementCycle));

        }


        // return the merchantSettlementCycleResoueceList
        return merchantSettlementCycleResourceList;

    }

}

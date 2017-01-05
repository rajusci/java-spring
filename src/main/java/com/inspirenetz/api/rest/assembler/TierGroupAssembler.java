package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.domain.TierGroup;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.TierGroupController;
import com.inspirenetz.api.rest.resource.TierGroupResource;
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
public class TierGroupAssembler extends ResourceAssemblerSupport<TierGroup,TierGroupResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    ImageService imageService;
    public TierGroupAssembler() {

        super(TierGroupController.class,TierGroupResource.class);

    }

    @Override
    public TierGroupResource toResource(TierGroup tierGroup) {

        for(Tier tier : tierGroup.getTierSet()){

            String path ="";
            if(tier.getTieImage() != null){

                path = imageService.getPathForImage(tier.getImage(), ImagePathType.STANDARD);

            }
            tier.setTieImagePath(path);

        }
        // Create the TierGroupResource
        TierGroupResource tierGroupResource = mapper.map(tierGroup,TierGroupResource.class);

        // Get the RewardCurrency for the TierGroup
        RewardCurrency rewardCurrency = tierGroup.getRewardCurrency();

        // Check if the reward currency is valid
        if ( rewardCurrency != null ) {

            // Set the reward currency name
            tierGroupResource.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());

        }

        // Return the tierGroupResource
        return tierGroupResource;
    }


    /**
     * Function to convert the list of TierGroup objects into an equivalent list
     * of TierGroupResource objects
     *
     * @param tierGroupList - The List object for the TierGroup objects
     * @return tierGroupResourceList - This list of TierGroupResource objects
     */
    public List<TierGroupResource> toResources(List<TierGroup> tierGroupList) {

        // Create the list that will hold the resources
        List<TierGroupResource> tierGroupResourceList = new ArrayList<TierGroupResource>();

        // Create the TierGroupResource object
        TierGroupResource tierGroupResource = null;


        // Go through the tierGroups and then create the tierGroup resource
        for(TierGroup tierGroup : tierGroupList ) {

            // Get the TierGroupResource
            tierGroupResource = toResource(tierGroup);

            // Add the resource to the array list
            tierGroupResourceList.add(tierGroupResource);

        }


        // return the tierGroupResoueceList
        return tierGroupResourceList;

    }

}

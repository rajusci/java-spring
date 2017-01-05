package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.TierController;
import com.inspirenetz.api.rest.resource.TierResource;
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
public class TierAssembler extends ResourceAssemblerSupport<Tier,TierResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    ImageService imageService;

    public TierAssembler() {

        super(TierController.class,TierResource.class);

    }

    @Override
    public TierResource toResource(Tier tier) {

        // Create the TierResource
        TierResource tierResource = mapper.map(tier,TierResource.class);

        // Check if the image exists
        Image image = tier.getImage();

        //If the image is set, then we need to set the image path
        if ( image !=  null ) {

            // Get the image path
            String imagePath  = imageService.getPathForImage(image, ImagePathType.STANDARD);

            // Set the imagePath
            tierResource.setTieImagePath(imagePath);

        }

        // Return the tierResource
        return tierResource;
    }


    /**
     * Function to convert the list of Tier objects into an equivalent list
     * of TierResource objects
     *
     * @param tierList - The List object for the Tier objects
     * @return tierResourceList - This list of TierResource objects
     */
    public List<TierResource> toResources(List<Tier> tierList) {

        // Create the list that will hold the resources
        List<TierResource> tierResourceList = new ArrayList<TierResource>();

        // Create the TierResource object
        TierResource tierResource = null;


        // Go through the tiers and then create the tier resource
        for(Tier tier : tierList ) {

            // Get the TierResource
            tierResource = toResource(tier);

            // Add the resource to the array list
            tierResourceList.add(tierResource);

        }


        // return the tierResoueceList
        return tierResourceList;

    }

}

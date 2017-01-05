package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.Promotion;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.PromotionController;
import com.inspirenetz.api.rest.resource.PromotionResource;
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
public class PromotionAssembler extends ResourceAssemblerSupport<Promotion,PromotionResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ImageService imageService;




    public PromotionAssembler() {

        super(PromotionController.class,PromotionResource.class);

    }

    @Override
    public PromotionResource toResource(Promotion promotion) {

        // Create the PromotionResource
        PromotionResource promotionResource = mapper.map(promotion,PromotionResource.class);

        // Get the image
        Image prmImage = promotion.getImage();

        // If the image it not null, then we need to set the path for the image
        if ( prmImage != null ) {

            // Get the image Path
            String imagePath = imageService.getPathForImage(prmImage, ImagePathType.STANDARD);

            // Set the image path
            promotionResource.setPrmImagePath(imagePath);

        }

        // Return the promotionResource
        return promotionResource;
    }


    /**
     * Function to convert the list of Promotion objects into an equivalent list
     * of PromotionResource objects
     *
     * @param promotionList - The List object for the Promotion objects
     * @return promotionResourceList - This list of PromotionResource objects
     */
    public List<PromotionResource> toResources(List<Promotion> promotionList) {

        // Create the list that will hold the resources
        List<PromotionResource> promotionResourceList = new ArrayList<PromotionResource>();

        // Create the PromotionResource object
        PromotionResource promotionResource = null;


        // Go through the promotions and then create the promotion resource
        for(Promotion promotion : promotionList ) {

            // Get the PromotionResource
            promotionResource = toResource(promotion);

            // Add the resource to the array list
            promotionResourceList.add(promotionResource);

        }


        // return the promotionResoueceList
        return promotionResourceList;

    }

}

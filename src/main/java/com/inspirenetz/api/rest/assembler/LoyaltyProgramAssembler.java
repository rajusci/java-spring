package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.AttributeExtensionMapType;
import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityAssembler;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.core.service.LoyaltyProgramService;
import com.inspirenetz.api.rest.controller.LoyaltyProgramController;
import com.inspirenetz.api.rest.resource.LoyaltyProgramResource;
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
public class LoyaltyProgramAssembler extends ResourceAssemblerSupport<LoyaltyProgram,LoyaltyProgramResource> implements AttributeExtendedEntityAssembler<LoyaltyProgram> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;

    @Autowired
    private ImageService imageService;


    public LoyaltyProgramAssembler() {

        super(LoyaltyProgramController.class,LoyaltyProgramResource.class);

    }


    @Override
    public LoyaltyProgramResource toResource(LoyaltyProgram loyaltyProgram) {

        // Create the LoyaltyProgramResource
        LoyaltyProgramResource loyaltyProgramResource = mapper.map(loyaltyProgram,LoyaltyProgramResource.class);

        // check if the reward currency field is set
        RewardCurrency rewardCurrency = loyaltyProgram.getRewardCurrency();

        // If the reward currency is not null, then we need to set the field
        if ( rewardCurrency != null ) {

            loyaltyProgramResource.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());

        }

        // Return the loyaltyProgramResource
        return loyaltyProgramResource;
    }


    /**
     * Function to convert the list of LoyaltyProgram objects into an equivalent list
     * of LoyaltyProgramResource objects
     *
     * @param loyaltyProgramList - The List object for the LoyaltyProgram objects
     * @return loyaltyProgramResourceList - This list of LoyaltyProgramResource objects
     */
    public List<LoyaltyProgramResource> toResources(List<LoyaltyProgram> loyaltyProgramList) {

        // Create the list that will hold the resources
        List<LoyaltyProgramResource> loyaltyProgramResourceList = new ArrayList<LoyaltyProgramResource>();

        // Create the LoyaltyProgramResource object
        LoyaltyProgramResource loyaltyProgramResource = null;


        // Go through the loyaltyPrograms and then create the loyaltyProgram resource
        for(LoyaltyProgram loyaltyProgram : loyaltyProgramList ) {

            // Get the LoyaltyProgramResource
            loyaltyProgramResource = toResource(loyaltyProgram);

            // Add the resource to the array list
            loyaltyProgramResourceList.add(loyaltyProgramResource);

        }


        // return the loyaltyProgramResoueceList
        return loyaltyProgramResourceList;

    }


    @Override
    public  AttributeExtendedEntityMap toAttibuteEntityMap(LoyaltyProgram loyaltyProgram) {

        // Create the LoyaltyProgramResource
        AttributeExtendedEntityMap loyaltyProgramResource = loyaltyProgramService.toAttributeExtensionMap(loyaltyProgram, AttributeExtensionMapType.ALL);

        // check if the reward currency field is set
        RewardCurrency rewardCurrency = loyaltyProgram.getRewardCurrency();

        // If the reward currency is not null, then we need to set the field
        if ( rewardCurrency != null ) {

            loyaltyProgramResource.put("rwdCurrencyName",rewardCurrency.getRwdCurrencyName());

        }

        // Get the Conver Image
        Image imgCover = loyaltyProgram.getImage();

        // If the cover is not null, then set the page
        if ( imgCover != null ) {

            // Get the imagePath
            String path = imageService.getPathForImage(imgCover, ImagePathType.STANDARD);

            // Set the imagePath
            loyaltyProgramResource.put("prgImagePath", path);

        }

        // Return the loyaltyProgramResource
        return loyaltyProgramResource;

    }

    /**
     * Function to convert the list of LoyaltyProgram objects into an equivalent list
     * of LoyaltyProgramResource objects
     *
     * @param loyaltyProgramList - The List object for the LoyaltyProgram objects
     * @return loyaltyProgramResourceList - This list of LoyaltyProgramResource objects
     */
    @Override
    public List<AttributeExtendedEntityMap> toAttibuteEntityMaps(Iterable<? extends LoyaltyProgram> loyaltyProgramList) {

        // Create the list that will hold the resources
        List<AttributeExtendedEntityMap> attributeExtendedEntityMapList = new ArrayList<AttributeExtendedEntityMap>();

        // Create the LoyaltyProgramResource object
        AttributeExtendedEntityMap attributeExtendedEntityMap = null;


        // Go through the loyaltyPrograms and then create the loyaltyProgram resource
        for(LoyaltyProgram loyaltyProgram : loyaltyProgramList ) {

            // Get the LoyaltyProgramResource
            attributeExtendedEntityMap = toAttibuteEntityMap(loyaltyProgram);

            // Add the resource to the array list
            attributeExtendedEntityMapList.add(attributeExtendedEntityMap);

        }

        // return the loyaltyProgramResoueceList
        return attributeExtendedEntityMapList;

    }
}

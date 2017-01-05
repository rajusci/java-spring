package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.MerchantController;
import com.inspirenetz.api.rest.resource.MerchantProfileResource;
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
public class MerchantProfileAssembler extends ResourceAssemblerSupport<Merchant,MerchantProfileResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ImageService imageService;


    public MerchantProfileAssembler() {

        super(MerchantController.class,MerchantProfileResource.class);

    }

    @Override
    public MerchantProfileResource toResource(Merchant merchant) {

        // Create the MerchantProfileResource
        MerchantProfileResource merchantProfileResource = mapper.map(merchant,MerchantProfileResource.class);

        // Get the Image
        Image imgLogo = merchant.getImgLogo();

        // If the logo is not null, then set the page
        if ( imgLogo != null ) {

            // Get the imagePath
            String path = imageService.getPathForImage(imgLogo, ImagePathType.STANDARD);

            // Set the imagePath
            merchantProfileResource.setMerLogoPath(path);

        }



        // Get the Conver Image
        Image imgCover = merchant.getImgCoverImage();

        // If the cover is not null, then set the page
        if ( imgCover != null ) {

            // Get the imagePath
            String path = imageService.getPathForImage(imgCover, ImagePathType.STANDARD);

            // Set the imagePath
            merchantProfileResource.setMerCoverImagePath(path);

        }




        // Return the merchantProfileResource
        return merchantProfileResource;
    }


    /**
     * Function to convert the list of Merchant objects into an equivalent list
     * of MerchantProfileResource objects
     *
     * @param merchantList - The List object for the Merchant objects
     * @return merchantProfileResourceList - This list of MerchantProfileResource objects
     */
    public List<MerchantProfileResource> toResources(List<Merchant> merchantList) {

        // Create the list that will hold the resources
        List<MerchantProfileResource> merchantProfileResourceList = new ArrayList<MerchantProfileResource>();

        // Create the MerchantProfileResource object
        MerchantProfileResource merchantProfileResource = null;


        // Go through the merchants and then create the merchant resource
        for(Merchant merchant : merchantList ) {

            // Get the MerchantProfileResource
            merchantProfileResource = mapper.map(merchant,MerchantProfileResource.class);

            // Add the resource to the array list
            merchantProfileResourceList.add(merchantProfileResource);

        }


        // return the merchantResoueceList
        return merchantProfileResourceList;

    }

}

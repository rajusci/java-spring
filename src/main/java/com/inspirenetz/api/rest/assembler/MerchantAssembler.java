package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.MerchantController;
import com.inspirenetz.api.rest.resource.MerchantResource;
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
public class MerchantAssembler extends ResourceAssemblerSupport<Merchant,MerchantResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    ImageService imageService;

    public MerchantAssembler() {

        super(MerchantController.class,MerchantResource.class);

    }

    @Override
    public MerchantResource toResource(Merchant merchant) {

        // Create the MerchantResource
        MerchantResource merchantResource = mapper.map(merchant,MerchantResource.class);


        // Get the Image
        Image imgLogo = merchant.getImgLogo();

        // If the logo is not null, then set the page
        if ( imgLogo != null ) {

            // Get the imagePath
            String path = imageService.getPathForImage(imgLogo, ImagePathType.STANDARD);

            // Set the imagePath
            merchantResource.setMerLogoPath(path);

        }



        // Get the Conver Image
        Image imgCover = merchant.getImgCoverImage();

        // If the cover is not null, then set the page
        if ( imgCover != null ) {

            // Get the imagePath
            String path = imageService.getPathForImage(imgCover, ImagePathType.STANDARD);

            // Set the imagePath
            merchantResource.setMerCoverImagePath(path);

        }


        // Return the merchantResource
        return merchantResource;
    }


    /**
     * Function to convert the list of Merchant objects into an equivalent list
     * of MerchantResource objects
     *
     * @param merchantList - The List object for the Merchant objects
     * @return merchantResourceList - This list of MerchantResource objects
     */
    public List<MerchantResource> toResources(List<Merchant> merchantList) {

        // Create the list that will hold the resources
        List<MerchantResource> merchantResourceList = new ArrayList<MerchantResource>();

        // Create the MerchantResource object
        MerchantResource merchantResource = null;


        // Go through the merchants and then create the merchant resource
        for(Merchant merchant : merchantList ) {

            // Get the MerchantResource
            merchantResource = mapper.map(merchant,MerchantResource.class);

            // Add the resource to the array list
            merchantResourceList.add(merchantResource);

        }


        // return the merchantResoueceList
        return merchantResourceList;

    }

}

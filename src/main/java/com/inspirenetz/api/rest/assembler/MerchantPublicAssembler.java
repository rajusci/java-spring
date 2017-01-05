package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.MerchantController;
import com.inspirenetz.api.rest.resource.MerchantPublicResource;
import com.inspirenetz.api.rest.resource.MerchantResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fayiz on 14/5/15.
 */
@Component
public class MerchantPublicAssembler extends ResourceAssemblerSupport<Merchant,MerchantPublicResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ImageService imageService;

    public MerchantPublicAssembler() {

        super(MerchantController.class,MerchantPublicResource.class);

    }

    @Override
    public MerchantPublicResource toResource(Merchant merchant) {

        // Create the MerchantPublicResource
        MerchantPublicResource merchantPublicResource = mapper.map(merchant,MerchantPublicResource.class);

        // Get the Image
        Image imgLogo = merchant.getImgLogo();

        // If the logo is not null, then set the page
        if ( imgLogo != null ) {

            // Get the imagePath
            String path = imageService.getPathForImage(imgLogo, ImagePathType.STANDARD);

            // Set the imagePath
            merchantPublicResource.setMerLogoPath(path);

        }



        // Get the Conver Image
        Image imgCover = merchant.getImgCoverImage();

        // If the cover is not null, then set the page
        if ( imgCover != null ) {

            // Get the imagePath
            String path = imageService.getPathForImage(imgCover, ImagePathType.STANDARD);

            // Set the imagePath
            merchantPublicResource.setMerCoverImagePath(path);

        }
        // Return the merchantPublicResource
        return merchantPublicResource;
    }


    /**
     * Function to convert the list of Merchant objects into an equivalent list
     * of MerchantPublicResource objects
     *
     * @param merchantList - The List object for the Merchant objects
     * @return merchantPublicResourceList - This list of MerchantResource objects
     */
    public List<MerchantPublicResource> toResources(List<Merchant> merchantList) {

        // Create the list that will hold the resources
        List<MerchantPublicResource> merchantPublicResourceList = new ArrayList<MerchantPublicResource>();

        // Create the MerchantPublicResource object
        MerchantPublicResource merchantPublicResource = null;


        // Go through the merchants and then create the merchant public resource
        for(Merchant merchant : merchantList ) {

            // Get the MerchantPublicResource
            merchantPublicResource = mapper.map(merchant,MerchantPublicResource.class);

            // Get the Image
            Image imgLogo = merchant.getImgLogo();

            // If the logo is not null, then set the page
            if ( imgLogo != null ) {

                // Get the imagePath
                String path = imageService.getPathForImage(imgLogo, ImagePathType.STANDARD);

                // Set the imagePath
                merchantPublicResource.setMerLogoPath(path);

            }



            // Get the Conver Image
            Image imgCover = merchant.getImgCoverImage();

            // If the cover is not null, then set the page
            if ( imgCover != null ) {

                // Get the imagePath
                String path = imageService.getPathForImage(imgCover, ImagePathType.STANDARD);

                // Set the imagePath
                merchantPublicResource.setMerCoverImagePath(path);

            }

            // Add the resource to the array list
            merchantPublicResourceList.add(merchantPublicResource);

        }


        // return the merchantPublicResourceList
        return merchantPublicResourceList;

    }

}

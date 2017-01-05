package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.PartnerCatalogue;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.PartnerCatalogueController;
import com.inspirenetz.api.rest.resource.PartnerCatalogueResource;
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
public class PartnerCatalogueAssembler extends ResourceAssemblerSupport<PartnerCatalogue,PartnerCatalogueResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    ImageService imageService;

    public PartnerCatalogueAssembler() {

        super(PartnerCatalogueController.class,PartnerCatalogueResource.class);

    }

    @Override
    public PartnerCatalogueResource toResource(PartnerCatalogue partnerCatalogue) {

        // Create the PartnerCatalogueResource
        PartnerCatalogueResource partnerCatalogueResource = mapper.map(partnerCatalogue,PartnerCatalogueResource.class);

        // Check if the image exists
        Image image = partnerCatalogue.getImage();

        //If the image is set, then we need to set the image path
        if ( image !=  null ) {

            // Get the image path
            String imagePath  = imageService.getPathForImage(image, ImagePathType.MOBILE);

            // Set the imagePath
            partnerCatalogueResource.setPacImagePath(imagePath);


        }
        // Return the partnerCatalogueResource
        return partnerCatalogueResource;
    }


    /**
     * Function to convert the list of PartnerCatalogue objects into an equivalent list
     * of PartnerCatalogueResource objects
     *
     * @param partnerCatalogueList - The List object for the PartnerCatalogue objects
     * @return partnerCatalogueResourceList - This list of PartnerCatalogueResource objects
     */
    public List<PartnerCatalogueResource> toResources(List<PartnerCatalogue> partnerCatalogueList) {

        // Create the list that will hold the resources
        List<PartnerCatalogueResource> partnerCatalogueResourceList = new ArrayList<PartnerCatalogueResource>();

        // Create the PartnerCatalogueResource object
        PartnerCatalogueResource partnerCatalogueResource = null;



        // Go through the partnerCatalogues and then create the partnerCatalogue resource
        for(PartnerCatalogue partnerCatalogue : partnerCatalogueList ) {


            // Add the resource to the array list
            partnerCatalogueResourceList.add(toResource(partnerCatalogue));

        }


        // return the partnerCatalogueResoueceList
        return partnerCatalogueResourceList;

    }

}

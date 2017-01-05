package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.CatalogueController;
import com.inspirenetz.api.rest.resource.CatalogueResource;
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
public class CatalogueAssembler extends ResourceAssemblerSupport<Catalogue,CatalogueResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ImageService imageService;

    public CatalogueAssembler() {

        super(CatalogueController.class,CatalogueResource.class);

    }

    @Override
    public CatalogueResource toResource(Catalogue catalogue) {

        // Create the CatalogueResource
        CatalogueResource catalogueResource = mapper.map(catalogue,CatalogueResource.class);

        // Check if the image exists
        Image image = catalogue.getImage();

        //If the image is set, then we need to set the image path
        if ( image !=  null ) {

            // Get the image path
            String imagePath  = imageService.getPathForImage(image, ImagePathType.MOBILE);

            // Set the imagePath
            catalogueResource.setCatProductImagePath(imagePath);


        }


        // Return the catalogueResource
        return catalogueResource;
    }


    /**
     * Function to convert the list of Catalogue objects into an equivalent list
     * of CatalogueResource objects
     *
     * @param catalogueList - The List object for the Catalogue objects
     * @return catalogueResourceList - This list of CatalogueResource objects
     */
    public List<CatalogueResource> toResources(List<Catalogue> catalogueList) {

        // Create the list that will hold the resources
        List<CatalogueResource> catalogueResourceList = new ArrayList<CatalogueResource>();

        // Create the CatalogueResource object
        CatalogueResource catalogueResource = null;


        // Go through the catalogues and then create the catalogue resource
        for(Catalogue catalogue : catalogueList ) {

            // Get the CatalogueResource
            catalogueResource = toResource(catalogue);

            // Add the resource to the array list
            catalogueResourceList.add(catalogueResource);

        }


        // return the catalogueResoueceList
        return catalogueResourceList;

    }

}

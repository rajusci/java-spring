package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.CatalogueCategory;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.CatalogueCategoryController;
import com.inspirenetz.api.rest.resource.CatalogueCategoryResource;
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
public class CatalogueCategoryAssembler extends ResourceAssemblerSupport<CatalogueCategory,CatalogueCategoryResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ImageService imageService;

    public CatalogueCategoryAssembler() {

        super(CatalogueCategoryController.class,CatalogueCategoryResource.class);

    }

    @Override
    public CatalogueCategoryResource toResource(CatalogueCategory catalogueCategory) {

        // Create the CatalogueCategoryResource
        CatalogueCategoryResource catalogueCategoryResource = mapper.map(catalogueCategory,CatalogueCategoryResource.class);

        // Get the catalogueCategory image
        Image image = catalogueCategory.getImage();

        // Check if the image is not null
        if ( image != null ) {

            // Get the image path
            String imagePath = imageService.getPathForImage(image, ImagePathType.STANDARD);

            // set the image path
            catalogueCategoryResource.setCacImagePath(imagePath);

        }

        // Return the catalogueCategoryResource
        return catalogueCategoryResource;
    }


    /**
     * Function to convert the list of CatalogueCategory objects into an equivalent list
     * of CatalogueCategoryResource objects
     *
     * @param catalogueCategoryList - The List object for the CatalogueCategory objects
     * @return catalogueCategoryResourceList - This list of CatalogueCategoryResource objects
     */
    public List<CatalogueCategoryResource> toResources(List<CatalogueCategory> catalogueCategoryList) {

        // Create the list that will hold the resources
        List<CatalogueCategoryResource> catalogueCategoryResourceList = new ArrayList<CatalogueCategoryResource>();

        // Create the CatalogueCategoryResource object
        CatalogueCategoryResource catalogueCategoryResource = null;


        // Go through the catalogueCategorys and then create the catalogueCategory resource
        for(CatalogueCategory catalogueCategory : catalogueCategoryList ) {

            // Get the CatalogueCategoryResource
            catalogueCategoryResource = mapper.map(catalogueCategory,CatalogueCategoryResource.class);

            // Add the resource to the array list
            catalogueCategoryResourceList.add(catalogueCategoryResource);

        }


        // return the catalogueCategoryResoueceList
        return catalogueCategoryResourceList;

    }

}

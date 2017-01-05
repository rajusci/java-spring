package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CatalogueFavorite;
import com.inspirenetz.api.rest.controller.CatalogueFavoriteController;
import com.inspirenetz.api.rest.resource.CatalogueFavoriteResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alameen on 21/10/14.
 */
@Component
public class CatalogueFavoriteAssembler extends ResourceAssemblerSupport<CatalogueFavorite,CatalogueFavoriteResource> {

    @Autowired
    private Mapper mapper;

    public CatalogueFavoriteAssembler() {

        super(CatalogueFavoriteController.class,CatalogueFavoriteResource.class);

    }

    @Override
    public CatalogueFavoriteResource toResource(CatalogueFavorite catalogueFavorite) {

        // Create the CatalogueFavoriteResource
        CatalogueFavoriteResource catalogueFavoriteResource = mapper.map(catalogueFavorite,CatalogueFavoriteResource.class);

        // Return the brandResource
        return catalogueFavoriteResource;
    }


    /**
     * Function to convert the list of CatalogueFavorite objects into an equivalent list
     * of CatalogueFavoriteResource objects
     *
     * @param catalogueFavoriteList - The List object for the CatalogueFavorite objects
     * @return brandResourceList - This list of CatalogueFavoriteResource objects
     */
    public List<CatalogueFavoriteResource> toResources(List<CatalogueFavorite> catalogueFavoriteList) {

        // Create the list that will hold the resources
        List<CatalogueFavoriteResource> catalogueFavoriteResourceList = new ArrayList<CatalogueFavoriteResource>();

        // Create the CatalogueFavoriteResource object
        CatalogueFavoriteResource catalogueFavoriteResource = null;


        // Go through the brands and then create the brand resource
        for(CatalogueFavorite catalogueFavorite : catalogueFavoriteList ) {

            // Get the CatalogueFavoriteResource
            catalogueFavoriteResource = mapper.map(catalogueFavorite,CatalogueFavoriteResource.class);

            // Add the resource to the array list
            catalogueFavoriteResourceList.add(catalogueFavoriteResource);

        }


        // return the brandResoueceList
        return catalogueFavoriteResourceList;

    }
}

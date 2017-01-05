package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CatalogueDisplayPreference;
import com.inspirenetz.api.rest.controller.CatalogueDisplayPreferenceController;
import com.inspirenetz.api.rest.resource.CatalogueDisplayPreferenceResource;
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
public class CatalogueDisplayPreferenceAssembler extends ResourceAssemblerSupport<CatalogueDisplayPreference,CatalogueDisplayPreferenceResource> {

    @Autowired
    private Mapper mapper;

    public CatalogueDisplayPreferenceAssembler() {

        super(CatalogueDisplayPreferenceController.class,CatalogueDisplayPreferenceResource.class);

    }

    @Override
    public CatalogueDisplayPreferenceResource toResource(CatalogueDisplayPreference catalogueDisplayPreference) {

        // Create the CatalogueDisplayPreferenceResource
        CatalogueDisplayPreferenceResource catalogueDisplayPreferenceResource = mapper.map(catalogueDisplayPreference,CatalogueDisplayPreferenceResource.class);

        // Return the catalogueDisplayPreferenceResource
        return catalogueDisplayPreferenceResource;
    }


    /**
     * Function to convert the list of CatalogueDisplayPreference objects into an equivalent list
     * of CatalogueDisplayPreferenceResource objects
     *
     * @param catalogueDisplayPreferenceList - The List object for the CatalogueDisplayPreference objects
     * @return catalogueDisplayPreferenceResourceList - This list of CatalogueDisplayPreferenceResource objects
     */
    public List<CatalogueDisplayPreferenceResource> toResources(List<CatalogueDisplayPreference> catalogueDisplayPreferenceList) {

        // Create the list that will hold the resources
        List<CatalogueDisplayPreferenceResource> catalogueDisplayPreferenceResourceList = new ArrayList<CatalogueDisplayPreferenceResource>();

        // Create the CatalogueDisplayPreferenceResource object
        CatalogueDisplayPreferenceResource catalogueDisplayPreferenceResource = null;


        // Go through the catalogueDisplayPreferences and then create the catalogueDisplayPreference resource
        for(CatalogueDisplayPreference catalogueDisplayPreference : catalogueDisplayPreferenceList ) {

            // Add the resource to the array list
            catalogueDisplayPreferenceResourceList.add(toResource(catalogueDisplayPreference));

        }


        // return the catalogueDisplayPreferenceResoueceList
        return catalogueDisplayPreferenceResourceList;

    }

}

package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.rest.controller.BrandController;
import com.inspirenetz.api.rest.resource.BrandResource;
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
public class BrandAssembler extends ResourceAssemblerSupport<Brand,BrandResource> {

    @Autowired
    private Mapper mapper;

    public BrandAssembler() {

        super(BrandController.class,BrandResource.class);

    }

    @Override
    public BrandResource toResource(Brand brand) {

        // Create the BrandResource
        BrandResource brandResource = mapper.map(brand,BrandResource.class);

        // Return the brandResource
        return brandResource;
    }


    /**
     * Function to convert the list of Brand objects into an equivalent list
     * of BrandResource objects
     *
     * @param brandList - The List object for the Brand objects
     * @return brandResourceList - This list of BrandResource objects
     */
    public List<BrandResource> toResources(List<Brand> brandList) {

        // Create the list that will hold the resources
        List<BrandResource> brandResourceList = new ArrayList<BrandResource>();

        // Create the BrandResource object
        BrandResource brandResource = null;


        // Go through the brands and then create the brand resource
        for(Brand brand : brandList ) {

            // Get the BrandResource
            brandResource = mapper.map(brand,BrandResource.class);

            // Add the resource to the array list
            brandResourceList.add(brandResource);

        }


        // return the brandResoueceList
        return brandResourceList;

    }

}

package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.ProductCategory;
import com.inspirenetz.api.rest.controller.ProductCategoryController;
import com.inspirenetz.api.rest.resource.ProductCategoryResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 20/4/14.
 */
@Component
public class ProductCategoryAssembler extends ResourceAssemblerSupport<ProductCategory,ProductCategoryResource> {

    @Autowired
    private Mapper mapper;

    public ProductCategoryAssembler() {

        super(ProductCategoryController.class,ProductCategoryResource.class);

    }

    @Override
    public ProductCategoryResource toResource(ProductCategory productCategory) {

        // Create the ProductCategoryResource
        ProductCategoryResource productCategoryResource = mapper.map(productCategory,ProductCategoryResource.class);

        // Return the productCategoryResource
        return productCategoryResource;
    }


    /**
     * Function to convert the list of ProductCategory objects into an equivalent list
     * of ProductCategoryResource objects
     *
     * @param productCategoryList - The List object for the ProductCategory objects
     * @return productCategoryResourceList - This list of ProductCategoryResource objects
     */
    public List<ProductCategoryResource> toResources(List<ProductCategory> productCategoryList) {

        // Create the list that will hold the resources
        List<ProductCategoryResource> productCategoryResourceList = new ArrayList<ProductCategoryResource>();

        // Create the ProductCategoryResource object
        ProductCategoryResource productCategoryResource = null;


        // Go through the productCategorys and then create the productCategory resource
        for(ProductCategory productCategory : productCategoryList ) {

            // Get the ProductCategoryResource
            productCategoryResource = mapper.map(productCategory,ProductCategoryResource.class);

            // Add the resource to the array list
            productCategoryResourceList.add(productCategoryResource);

        }


        // return the productCategoryResoueceList
        return productCategoryResourceList;

    }
}

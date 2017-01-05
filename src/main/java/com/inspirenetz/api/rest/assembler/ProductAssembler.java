package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.rest.controller.ProductController;
import com.inspirenetz.api.rest.resource.ProductResource;
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
public class ProductAssembler extends ResourceAssemblerSupport<Product,ProductResource> {

    @Autowired
    private Mapper mapper;

    public ProductAssembler() {

        super(ProductController.class,ProductResource.class);

    }

    @Override
    public ProductResource toResource(Product product) {

        // Create the ProductResource
        ProductResource productResource = mapper.map(product,ProductResource.class);

        // Return the productResource
        return productResource;
    }


    /**
     * Function to convert the list of Product objects into an equivalent list
     * of ProductResource objects
     *
     * @param productList - The List object for the Product objects
     * @return productResourceList - This list of ProductResource objects
     */
    public List<ProductResource> toResources(List<Product> productList) {

        // Create the list that will hold the resources
        List<ProductResource> productResourceList = new ArrayList<ProductResource>();

        // Create the ProductResource object
        ProductResource productResource = null;


        // Go through the products and then create the product resource
        for(Product product : productList ) {

            // Get the ProductResource
            productResource = mapper.map(product,ProductResource.class);

            // Add the resource to the array list
            productResourceList.add(productResource);

        }


        // return the productResoueceList
        return productResourceList;

    }

}

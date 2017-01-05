package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.OrderCatalogue;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.OrderCatalogueController;
import com.inspirenetz.api.rest.resource.OrderCatalogueResource;
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
public class OrderCatalogueAssembler extends ResourceAssemblerSupport<OrderCatalogue,OrderCatalogueResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ImageService imageService;


    public OrderCatalogueAssembler() {

        super(OrderCatalogueController.class,OrderCatalogueResource.class);

    }


    @Override
    public OrderCatalogueResource toResource(OrderCatalogue orderCatalogue) {

        // Create the OrderCatalogueResource
        OrderCatalogueResource orderCatalogueResource = mapper.map(orderCatalogue,OrderCatalogueResource.class);

        // Get the image
        Image imgPrimaryImage = orderCatalogue.getImgPrimaryImage();

        // Check if the image is valid
        if ( imgPrimaryImage != null ) {

            // Get the image path
            String imagePath = imageService.getPathForImage(imgPrimaryImage, ImagePathType.STANDARD);

            // Set the primary path image
            orderCatalogueResource.setImgPrimaryPath(imagePath);

        }

        // Return the orderCatalogueResource
        return orderCatalogueResource;
    }


    /**
     * Function to convert the list of OrderCatalogue objects into an equivalent list
     * of OrderCatalogueResource objects
     *
     * @param orderCatalogueList - The List object for the OrderCatalogue objects
     * @return orderCatalogueResourceList - This list of OrderCatalogueResource objects
     */
    public List<OrderCatalogueResource> toResources(List<OrderCatalogue> orderCatalogueList) {

        // Create the list that will hold the resources
        List<OrderCatalogueResource> orderCatalogueResourceList = new ArrayList<OrderCatalogueResource>();

        // Create the OrderCatalogueResource object
        OrderCatalogueResource orderCatalogueResource = null;


        // Go through the orderCatalogues and then create the orderCatalogue resource
        for(OrderCatalogue orderCatalogue : orderCatalogueList ) {

            // Get the OrderCatalogueResource
            orderCatalogueResource = toResource(orderCatalogue);

            // Add the resource to the array list
            orderCatalogueResourceList.add(orderCatalogueResource);

        }


        // return the orderCatalogueResoueceList
        return orderCatalogueResourceList;

    }

}

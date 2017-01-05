package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.Vendor;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.VendorController;
import com.inspirenetz.api.rest.resource.VendorResource;
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
public class VendorAssembler extends ResourceAssemblerSupport<Vendor,VendorResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ImageService imageService;

    public VendorAssembler() {

        super(VendorController.class,VendorResource.class);

    }

    @Override
    public VendorResource toResource(Vendor vendor) {

        // Create the VendorResource
        VendorResource vendorResource = mapper.map(vendor,VendorResource.class);

        // Get the vendor image
        Image image = vendor.getImage();

        // Check if the image is not null
        if ( image != null ) {

            // Get the image path
            String imagePath = imageService.getPathForImage(image, ImagePathType.STANDARD);

            // set the image path
            vendorResource.setVenImagePath(imagePath);
        }

        // Return the vendorResource
        return vendorResource;
    }


    /**
     * Function to convert the list of Vendor objects into an equivalent list
     * of VendorResource objects
     *
     * @param vendorList - The List object for the Vendor objects
     * @return vendorResourceList - This list of VendorResource objects
     */
    public List<VendorResource> toResources(List<Vendor> vendorList) {

        // Create the list that will hold the resources
        List<VendorResource> vendorResourceList = new ArrayList<VendorResource>();

        // Create the VendorResource object
        VendorResource vendorResource = null;


        // Go through the vendors and then create the vendor resource
        for(Vendor vendor : vendorList ) {

            // Get the VendorResource
            vendorResource = mapper.map(vendor,VendorResource.class);

            // Add the resource to the array list
            vendorResourceList.add(vendorResource);

        }


        // return the vendorResoueceList
        return vendorResourceList;

    }

}

package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.CustomerController;
import com.inspirenetz.api.rest.resource.CustomerInfoResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;


/**
 * Created by sandheepgr on 15/4/14.
 */
@Component
public class CustomerInfoAssembler extends ResourceAssemblerSupport<Customer,CustomerInfoResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ImageService imageService;

    public CustomerInfoAssembler() {

        super(CustomerController.class,CustomerInfoResource.class);

    }


    /**
     * Function to convert the Customer, Tier objects to CustomerInfoResource
     * object
     *
     * @param customer  - The Customer object
     * @param tier      - The Tier object
     *
     * @return          - The CustomerInfoResource object
     */
    public CustomerInfoResource toResource(Customer customer, Tier tier,Merchant merchant) {

        // Create the CustomerInfoResource
        CustomerInfoResource customerInfoResource = mapper.map(customer,CustomerInfoResource.class);


        // If the tier object is not null, then set the tier fields
        if ( tier != null ) {

            // Now map the tier fields
            customerInfoResource.setTieId(tier.getTieId());

            // Set the tier name
            customerInfoResource.setTieName(tier.getTieName());

            // Check if the image exists
            Image image = tier.getImage();

            //If the image is set, then we need to set the image path
            if ( image !=  null ) {

                // Get the image path
                String imagePath  = imageService.getPathForImage(image, ImagePathType.MOBILE);

                // Set the imagePath
                customerInfoResource.setTieImagePath(imagePath);

            }

            // Set the tieIsTransferPointsAllowedInd
            customerInfoResource.setTieIsTransferPointsAllowedInd(tier.getTieIsTransferPointsAllowedInd());

        }

        if ( merchant != null ) {

            // Now map the merchant name
            customerInfoResource.setMerchantName(merchant.getMerMerchantName());
        }


        // Return the customerInfoResource
        return customerInfoResource;

    }

    @Override
    public CustomerInfoResource toResource(Customer customer) {

        // Create the CustomerinfoResource
        CustomerInfoResource customerInfoResource = mapper.map(customer,CustomerInfoResource.class);

        // Return the object
        return customerInfoResource;

    }

}

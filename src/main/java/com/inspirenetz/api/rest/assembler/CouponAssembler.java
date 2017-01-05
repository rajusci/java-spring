package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Coupon;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.CouponController;
import com.inspirenetz.api.rest.resource.CouponResource;
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
public class CouponAssembler extends ResourceAssemblerSupport<Coupon,CouponResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ImageService imageService;


    public CouponAssembler() {

        super(CouponController.class,CouponResource.class);

    }

    @Override
    public CouponResource toResource(Coupon coupon) {

        // Create the CouponResource
        CouponResource couponResource = mapper.map(coupon,CouponResource.class);

        // Check if the image is set
        Image image = coupon.getImage();

        // Check if the image exists
        if ( image != null ) {

            // Get the image path
            String imagePath = imageService.getPathForImage(image, ImagePathType.STANDARD);

            // Set the image path in the resource
            couponResource.setCpnImagePath(imagePath);

        }

        // Return the couponResource
        return couponResource;
    }


    /**
     * Function to convert the list of Coupon objects into an equivalent list
     * of CouponResource objects
     *
     * @param couponList - The List object for the Coupon objects
     * @return couponResourceList - This list of CouponResource objects
     */
    public List<CouponResource> toResources(List<Coupon> couponList) {

        // Create the list that will hold the resources
        List<CouponResource> couponResourceList = new ArrayList<CouponResource>();

        // Create the CouponResource object
        CouponResource couponResource = null;


        // Go through the coupons and then create the coupon resource
        for(Coupon coupon : couponList ) {

            // Get the CouponResource
            couponResource = toResource(coupon);

            // Add the resource to the array list
            couponResourceList.add(couponResource);

        }


        // return the couponResoueceList
        return couponResourceList;

    }

}

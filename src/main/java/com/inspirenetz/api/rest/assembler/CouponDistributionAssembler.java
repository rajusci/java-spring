package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.CouponDistribution;
import com.inspirenetz.api.rest.controller.CouponDistributionController;
import com.inspirenetz.api.rest.resource.CouponDistributionResource;
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
public class CouponDistributionAssembler extends ResourceAssemblerSupport<CouponDistribution,CouponDistributionResource> {

    @Autowired
    private Mapper mapper;

    public CouponDistributionAssembler() {

        super(CouponDistributionController.class,CouponDistributionResource.class);

    }

    @Override
    public CouponDistributionResource toResource(CouponDistribution couponDistribution) {

        // Create the CouponDistributionResource
        CouponDistributionResource couponDistributionResource = mapper.map(couponDistribution,CouponDistributionResource.class);

        // Return the couponDistributionResource
        return couponDistributionResource;
    }


    /**
     * Function to convert the list of CouponDistribution objects into an equivalent list
     * of CouponDistributionResource objects
     *
     * @param couponDistributionList - The List object for the CouponDistribution objects
     * @return couponDistributionResourceList - This list of CouponDistributionResource objects
     */
    public List<CouponDistributionResource> toResources(List<CouponDistribution> couponDistributionList) {

        // Create the list that will hold the resources
        List<CouponDistributionResource> couponDistributionResourceList = new ArrayList<CouponDistributionResource>();

        // Create the CouponDistributionResource object
        CouponDistributionResource couponDistributionResource = null;


        // Go through the couponDistributions and then create the couponDistribution resource
        for(CouponDistribution couponDistribution : couponDistributionList ) {

            // Get the CouponDistributionResource
            couponDistributionResource = mapper.map(couponDistribution,CouponDistributionResource.class);

            // Add the resource to the array list
            couponDistributionResourceList.add(couponDistributionResource);

        }


        // return the couponDistributionResoueceList
        return couponDistributionResourceList;

    }

}

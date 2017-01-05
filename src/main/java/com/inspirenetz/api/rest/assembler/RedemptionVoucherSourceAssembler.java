package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import com.inspirenetz.api.rest.controller.RedemptionVoucherSourceController;
import com.inspirenetz.api.rest.resource.RedemptionVoucherSourceResource;
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
public class RedemptionVoucherSourceAssembler extends ResourceAssemblerSupport<RedemptionVoucherSource,RedemptionVoucherSourceResource> {

    @Autowired
    private Mapper mapper;

    public RedemptionVoucherSourceAssembler() {

        super(RedemptionVoucherSourceController.class,RedemptionVoucherSourceResource.class);

    }

    @Override
    public RedemptionVoucherSourceResource toResource(RedemptionVoucherSource redemptionVoucherSource) {

        // Create the RedemptionVoucherSourceResource
        RedemptionVoucherSourceResource redemptionVoucherSourceResource = mapper.map(redemptionVoucherSource,RedemptionVoucherSourceResource.class);

        // Return the redemptionVoucherSourceResource
        return redemptionVoucherSourceResource;
    }


    /**
     * Function to convert the list of RedemptionVoucherSource objects into an equivalent list
     * of RedemptionVoucherSourceResource objects
     *
     * @param redemptionVoucherSourceList - The List object for the RedemptionVoucherSource objects
     * @return redemptionVoucherSourceResourceList - This list of RedemptionVoucherSourceResource objects
     */
    public List<RedemptionVoucherSourceResource> toResources(List<RedemptionVoucherSource> redemptionVoucherSourceList) {

        // Create the list that will hold the resources
        List<RedemptionVoucherSourceResource> redemptionVoucherSourceResourceList = new ArrayList<RedemptionVoucherSourceResource>();

        // Create the RedemptionVoucherSourceResource object
        RedemptionVoucherSourceResource redemptionVoucherSourceResource = null;


        // Go through the redemptionVoucherSources and then create the redemptionVoucherSource resource
        for(RedemptionVoucherSource redemptionVoucherSource : redemptionVoucherSourceList ) {

            // Add the resource to the array list
            redemptionVoucherSourceResourceList.add(toResource(redemptionVoucherSource));

        }


        // return the redemptionVoucherSourceResoueceList
        return redemptionVoucherSourceResourceList;

    }

}

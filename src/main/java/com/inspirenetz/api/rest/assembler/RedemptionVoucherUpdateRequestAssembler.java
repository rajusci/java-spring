package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.RedemptionVoucherUpdateRequest;
import com.inspirenetz.api.rest.controller.RedemptionVoucherController;
import com.inspirenetz.api.rest.resource.RedemptionVoucherUpdateRequestResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameen on 5/11/15.
 */
@Component
public class RedemptionVoucherUpdateRequestAssembler extends ResourceAssemblerSupport<RedemptionVoucherUpdateRequest,RedemptionVoucherUpdateRequestResource> {

    @Autowired
    private Mapper mapper;

    public RedemptionVoucherUpdateRequestAssembler() {

        super(RedemptionVoucherController.class,RedemptionVoucherUpdateRequestResource.class);

    }

    @Override
    public RedemptionVoucherUpdateRequestResource toResource(RedemptionVoucherUpdateRequest redemptionVoucherUpdateRequest) {

        // Create the RedemptionVoucherUpdateRequestResource
        RedemptionVoucherUpdateRequestResource redemptionVoucherUpdateRequestResource = mapper.map(redemptionVoucherUpdateRequest,RedemptionVoucherUpdateRequestResource.class);

        // Return the redemptionVoucherUpdateRequestResource
        return redemptionVoucherUpdateRequestResource;
    }


    /**
     * Function to convert the list of RedemptionVoucherUpdateRequest objects into an equivalent list
     * of RedemptionVoucherUpdateRequestResource objects
     *
     * @param redemptionVoucherUpdateRequestList - The List object for the RedemptionVoucherUpdateRequest objects
     * @return redemptionVoucherUpdateRequestResourceList - This list of RedemptionVoucherUpdateRequestResource objects
     */
    public List<RedemptionVoucherUpdateRequestResource> toResources(List<RedemptionVoucherUpdateRequest> redemptionVoucherUpdateRequestList) {

        // Create the list that will hold the resources
        List<RedemptionVoucherUpdateRequestResource> redemptionVoucherUpdateRequestResourceList = new ArrayList<RedemptionVoucherUpdateRequestResource>();

        // Create the RedemptionVoucherUpdateRequestResource object
        RedemptionVoucherUpdateRequestResource redemptionVoucherUpdateRequestResource = null;


        // Go through the redemptionVoucherUpdateRequests and then create the redemptionVoucherUpdateRequest resource
        for(RedemptionVoucherUpdateRequest redemptionVoucherUpdateRequest : redemptionVoucherUpdateRequestList ) {

            // Add the resource to the array list
            redemptionVoucherUpdateRequestResourceList.add(toResource(redemptionVoucherUpdateRequest));

        }


        // return the redemptionVoucherUpdateRequestResoueceList
        return redemptionVoucherUpdateRequestResourceList;

    }
}

package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.PointTransferRequest;
import com.inspirenetz.api.rest.controller.PointTransferRequestController;
import com.inspirenetz.api.rest.resource.PointTransferRequestResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameenci on 16/9/14.
 */
@Component
public class PointTransferRequestAssembler extends ResourceAssemblerSupport<PointTransferRequest,PointTransferRequestResource> {

    @Autowired
    private Mapper mapper;

    public PointTransferRequestAssembler() {

        super(PointTransferRequestController.class,PointTransferRequestResource.class);

    }

    @Override
    public PointTransferRequestResource toResource(PointTransferRequest pointTransferRequest) {

        PointTransferRequestResource pointTransferRequestResource= mapper.map(pointTransferRequest, PointTransferRequestResource.class);

        return pointTransferRequestResource;

    }

    /**
     * Function to convert the list of PointTransferRequestl objects into an equivalent list
     * of PointTransferRequestlResource objects
     *
     * @param  pointTransferRequests- The List object for the PointTransferRequestl objects
     * @return PointTransferRequestlList - This list of PointTransferRequestlResource objects
     */
    public List<PointTransferRequestResource> toResources(List<PointTransferRequest> pointTransferRequests) {

        // Create the list that will hold the resources
        List<PointTransferRequestResource> pointTransferRequestResourceList = new ArrayList<PointTransferRequestResource>();

        // Create the PointTransferRequestlResource object
        PointTransferRequestResource pointTransferRequestResource = null;


        // Go through the PointTransferRequestl and then create the PointTransferRequestl Resource
        for(PointTransferRequest pointTransferRequest : pointTransferRequests ) {

            // Get the message spiel resource
            pointTransferRequestResource = toResource(pointTransferRequest);

            // Add the resource to the array list
            pointTransferRequestResourceList.add(pointTransferRequestResource);

        }


        // return the PointTransferRequestlResourceList
        return pointTransferRequestResourceList;

    }
}

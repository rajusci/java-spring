package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.SegmentMemberDetails;
import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.rest.controller.SegmentMemberController;
import com.inspirenetz.api.rest.resource.SegmentMemberDetailsResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameen on 4/4/15.
 */
@Component
public class SegmentMemberDetailsAssembler extends ResourceAssemblerSupport<SegmentMemberDetails,SegmentMemberDetailsResource> {

    @Autowired
    private Mapper mapper;
    
    public SegmentMemberDetailsAssembler() {
        
        super(SegmentMemberController.class,SegmentMemberDetailsResource.class);
    }

    @Override
    public SegmentMemberDetailsResource toResource(SegmentMemberDetails segmentMemberDetailsDetails) {
        // Create the SegmentMemberResource
        SegmentMemberDetailsResource segmentMemberDetailsDetailsResource = mapper.map(segmentMemberDetailsDetails,SegmentMemberDetailsResource.class);

        // Return the segmentMemberDetailsDetailsResource
        return segmentMemberDetailsDetailsResource;
    }

    /**
     * Function to convert the list of SegmentMember objects into an equivalent list
     * of SegmentMemberResource objects
     *
     * @param segmentMemberDetailsList - The List object for the SegmentMember objects
     * @return segmentMemberDetailsDetailsResourceList - This list of SegmentMemberResource objects
     */
    public List<SegmentMemberDetailsResource> toResources(List<SegmentMember> segmentMemberDetailsList) {

        // Create the list that will hold the resources
        List<SegmentMemberDetailsResource> segmentMemberDetailsDetailsResourceList = new ArrayList<SegmentMemberDetailsResource>();

        // Create the SegmentMemberResource object
        SegmentMemberDetailsResource segmentMemberDetailsDetailsResource = null;


        // Go through the segmentMemberDetailss and then create the segmentMemberDetails resource
        for(SegmentMember segmentMemberDetails : segmentMemberDetailsList ) {

            // Get the SegmentMemberResource
            segmentMemberDetailsDetailsResource = mapper.map(segmentMemberDetails,SegmentMemberDetailsResource.class);

            // Add the resource to the array list
            segmentMemberDetailsDetailsResourceList.add(segmentMemberDetailsDetailsResource);

        }


        // return the segmentMemberDetailsResoueceList
        return segmentMemberDetailsDetailsResourceList;

    }
}

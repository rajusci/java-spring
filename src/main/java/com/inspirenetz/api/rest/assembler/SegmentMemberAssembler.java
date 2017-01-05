package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.rest.controller.SegmentMemberController;
import com.inspirenetz.api.rest.resource.SegmentMemberResource;
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
public class SegmentMemberAssembler extends ResourceAssemblerSupport<SegmentMember,SegmentMemberResource> {

    @Autowired
    private Mapper mapper;

    public SegmentMemberAssembler() {

        super(SegmentMemberController.class,SegmentMemberResource.class);

    }

    @Override
    public SegmentMemberResource toResource(SegmentMember segmentMember) {

        // Create the SegmentMemberResource
        SegmentMemberResource segmentMemberResource = mapper.map(segmentMember,SegmentMemberResource.class);

        // Return the segmentMemberResource
        return segmentMemberResource;
    }


    /**
     * Function to convert the list of SegmentMember objects into an equivalent list
     * of SegmentMemberResource objects
     *
     * @param segmentMemberList - The List object for the SegmentMember objects
     * @return segmentMemberResourceList - This list of SegmentMemberResource objects
     */
    public List<SegmentMemberResource> toResources(List<SegmentMember> segmentMemberList) {

        // Create the list that will hold the resources
        List<SegmentMemberResource> segmentMemberResourceList = new ArrayList<SegmentMemberResource>();

        // Create the SegmentMemberResource object
        SegmentMemberResource segmentMemberResource = null;


        // Go through the segmentMembers and then create the segmentMember resource
        for(SegmentMember segmentMember : segmentMemberList ) {

            // Get the SegmentMemberResource
            segmentMemberResource = mapper.map(segmentMember,SegmentMemberResource.class);

            // Add the resource to the array list
            segmentMemberResourceList.add(segmentMemberResource);

        }


        // return the segmentMemberResoueceList
        return segmentMemberResourceList;

    }

}

package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.BulkUploadBatchInfo;
import com.inspirenetz.api.rest.controller.BulkUploadBatchInfoController;
import com.inspirenetz.api.rest.resource.BulkUploadBatchInfoResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameen on 14/9/15.
 */
@Component
public class BulkUploadBatchInfoAssembler extends ResourceAssemblerSupport<BulkUploadBatchInfo,BulkUploadBatchInfoResource> {
  
    @Autowired
    private Mapper mapper;

    public BulkUploadBatchInfoAssembler() {
        super(BulkUploadBatchInfoController.class, BulkUploadBatchInfoResource.class);
    }

    @Override
    public BulkUploadBatchInfoResource toResource(BulkUploadBatchInfo bulkUploadBatchInfo) {

        // Create the BulkUploadBatchInfoResource
        BulkUploadBatchInfoResource bulkUploadBatchInfoResource = mapper.map(bulkUploadBatchInfo,BulkUploadBatchInfoResource.class);

        // Return the bulkUploadBatchInfodResource
        return bulkUploadBatchInfoResource;
    }

    /**
     * Function to convert the list of BulkUploadBatchInfo objects into an equivalent list
     * of BulkUploadBatchInfoResource objects
     *
     * @param bulkUploadBatchInfodList - The List object for the BulkUploadBatchInfo objects
     * @return bulkUploadBatchInfodResourceList - This list of BulkUploadBatchInfoResource objects
     */
    public List<BulkUploadBatchInfoResource> toResources(List<BulkUploadBatchInfo> bulkUploadBatchInfodList) {

        // Create the list that will hold the resources
        List<BulkUploadBatchInfoResource> bulkUploadBatchInfodResourceList = new ArrayList<BulkUploadBatchInfoResource>();

        // Create the BulkUploadBatchInfoResource object
        BulkUploadBatchInfoResource bulkUploadBatchInfodResource = null;


        // Go through the bulkUploadBatchInfods and then create the bulkUploadBatchInfod resource
        for(BulkUploadBatchInfo bulkUploadBatchInfod : bulkUploadBatchInfodList ) {

            // Get the BulkUploadBatchInfoResource
            bulkUploadBatchInfodResource = mapper.map(bulkUploadBatchInfod,BulkUploadBatchInfoResource.class);

            // Add the resource to the array list
            bulkUploadBatchInfodResourceList.add(bulkUploadBatchInfodResource);

        }


        // return the bulkUploadBatchInfodResoueceList
        return bulkUploadBatchInfodResourceList;

    }
}

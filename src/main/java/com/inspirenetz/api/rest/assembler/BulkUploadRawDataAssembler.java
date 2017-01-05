package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.BulkuploadRawdata;
import com.inspirenetz.api.rest.controller.BulkuploadRawdataController;

import com.inspirenetz.api.rest.resource.BulkUploadRawDataResource;
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
public class BulkUploadRawDataAssembler extends ResourceAssemblerSupport<BulkuploadRawdata,BulkUploadRawDataResource> {
    
    @Autowired
    private Mapper mapper;

    public BulkUploadRawDataAssembler() {
        super(BulkuploadRawdataController.class, BulkUploadRawDataResource.class);
    }

    @Override
    public BulkUploadRawDataResource toResource(BulkuploadRawdata bulkuploadRawdata) {
       
        // Create the BulkuploadRawdataResource
        BulkUploadRawDataResource bulkUploadRawDataResource = mapper.map(bulkuploadRawdata,BulkUploadRawDataResource.class);

        // Return the bulkuploadRawdatadResource
        return bulkUploadRawDataResource;
    }

    /**
     * Function to convert the list of BulkuploadRawdata objects into an equivalent list
     * of BulkuploadRawdataResource objects
     *
     * @param bulkuploadRawdatadList - The List object for the BulkuploadRawdata objects
     * @return bulkuploadRawdatadResourceList - This list of BulkuploadRawdataResource objects
     */
    public List<BulkUploadRawDataResource> toResources(List<BulkuploadRawdata> bulkuploadRawdatadList) {

        // Create the list that will hold the resources
        List<BulkUploadRawDataResource> bulkuploadRawdatadResourceList = new ArrayList<BulkUploadRawDataResource>();

        // Create the BulkuploadRawdataResource object
        BulkUploadRawDataResource bulkuploadRawdatadResource = null;


        // Go through the bulkuploadRawdatads and then create the bulkuploadRawdatad resource
        for(BulkuploadRawdata bulkuploadRawdatad : bulkuploadRawdatadList ) {

            // Get the BulkuploadRawdataResource
            bulkuploadRawdatadResource = mapper.map(bulkuploadRawdatad,BulkUploadRawDataResource.class);

            // Add the resource to the array list
            bulkuploadRawdatadResourceList.add(bulkuploadRawdatadResource);

        }


        // return the bulkuploadRawdatadResoueceList
        return bulkuploadRawdatadResourceList;

    }
}

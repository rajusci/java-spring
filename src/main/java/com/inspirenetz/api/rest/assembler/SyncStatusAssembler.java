package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.SyncStatus;
import com.inspirenetz.api.rest.controller.SyncStatusController;
import com.inspirenetz.api.rest.resource.SyncStatusResource;
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
public class SyncStatusAssembler extends ResourceAssemblerSupport<SyncStatus,SyncStatusResource> {

    @Autowired
    private Mapper mapper;

    public SyncStatusAssembler() {

        super(SyncStatusController.class,SyncStatusResource.class);

    }

    @Override
    public SyncStatusResource toResource(SyncStatus syncStatus) {

        // Create the SyncStatusResource
        SyncStatusResource syncStatusResource = mapper.map(syncStatus,SyncStatusResource.class);

        // Return the syncStatusResource
        return syncStatusResource;
    }


    /**
     * Function to convert the list of SyncStatus objects into an equivalent list
     * of SyncStatusResource objects
     *
     * @param syncStatusList - The List object for the SyncStatus objects
     * @return syncStatusResourceList - This list of SyncStatusResource objects
     */
    public List<SyncStatusResource> toResources(List<SyncStatus> syncStatusList) {

        // Create the list that will hold the resources
        List<SyncStatusResource> syncStatusResourceList = new ArrayList<SyncStatusResource>();

        // Create the BrandResource object
        SyncStatusResource syncStatusResource = null;


        // Go through the syncstatus and then create the syncstatus resource
        for(SyncStatus syncStatus : syncStatusList ) {

            // Get the SyncStatusResource
            syncStatusResource = mapper.map(syncStatus,SyncStatusResource.class);

            String batchFilePath="integration/"+syncStatusResource.getSysMerchantNo()+"/"+syncStatusResource.getSysLocation()+"/"+syncStatusResource.getSysBatchRef();

            syncStatusResource.setBatchFilePath(batchFilePath);

            // Add the resource to the array list
            syncStatusResourceList.add(syncStatusResource);

        }


        // return the syncStatusResourceList
        return syncStatusResourceList;

    }

}

package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.TransferPointSetting;
import com.inspirenetz.api.rest.controller.TransferPointSettingController;
import com.inspirenetz.api.rest.resource.TransferPointSettingResource;
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
public class TransferPointSettingAssembler extends ResourceAssemblerSupport<TransferPointSetting,TransferPointSettingResource> {

    @Autowired
    private Mapper mapper;

    public TransferPointSettingAssembler() {

        super(TransferPointSettingController.class,TransferPointSettingResource.class);

    }

    @Override
    public TransferPointSettingResource toResource(TransferPointSetting transferPointSetting) {

        // Create the TransferPointSettingResource
        TransferPointSettingResource transferPointSettingResource = mapper.map(transferPointSetting,TransferPointSettingResource.class);

        // Return the transferPointSettingResource
        return transferPointSettingResource;
    }


    /**
     * Function to convert the list of TransferPointSetting objects into an equivalent list
     * of TransferPointSettingResource objects
     *
     * @param transferPointSettingList - The List object for the TransferPointSetting objects
     * @return transferPointSettingResourceList - This list of TransferPointSettingResource objects
     */
    public List<TransferPointSettingResource> toResources(List<TransferPointSetting> transferPointSettingList) {

        // Create the list that will hold the resources
        List<TransferPointSettingResource> transferPointSettingResourceList = new ArrayList<TransferPointSettingResource>();

        // Create the TransferPointSettingResource object
        TransferPointSettingResource transferPointSettingResource = null;


        // Go through the transferPointSettings and then create the transferPointSetting resource
        for(TransferPointSetting transferPointSetting : transferPointSettingList ) {

            // Get the TransferPointSettingResource
            transferPointSettingResource = mapper.map(transferPointSetting,TransferPointSettingResource.class);

            // Add the resource to the array list
            transferPointSettingResourceList.add(transferPointSettingResource);

        }


        // return the transferPointSettingResoueceList
        return transferPointSettingResourceList;

    }

}

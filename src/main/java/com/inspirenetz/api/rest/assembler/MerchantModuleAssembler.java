package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.MerchantModule;
import com.inspirenetz.api.rest.controller.MerchantModuleController;
import com.inspirenetz.api.rest.resource.MerchantModuleResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameen on 25/4/15.
 */
@Component
public class MerchantModuleAssembler extends ResourceAssemblerSupport<MerchantModule,MerchantModuleResource> {

    @Autowired
    private Mapper mapper;  

    public MerchantModuleAssembler() {

        super(MerchantModuleController.class,MerchantModuleResource.class);

    }

    @Override
    public MerchantModuleResource toResource(MerchantModule merchantModule) {
    
        // Create the MerchantModuleResource
        MerchantModuleResource merchantModuleResource = mapper.map(merchantModule,MerchantModuleResource.class);

        // Return the merchant module resource
        return merchantModuleResource;
    }


    /**
     * Function to convert the list of MerchantModule objects into an equivalent list
     * of MerchantModuleResource objects
     *
     * @param merchantModuleList - The List object for the MerchantModule objects
     * @return merchantModuleList - This list of MerchantModuleResource objects
     */
    public List<MerchantModuleResource> toResources(List<MerchantModule> merchantModuleList) {

        // Create the list that will hold the resources
        List<MerchantModuleResource> merchantModuleListResource = new ArrayList<MerchantModuleResource>();

        // Create the MerchantModuleResource object
        MerchantModuleResource merchantModuleResource = null;


        // Go through the merchantModules and then create the merchantModule resource
        for(MerchantModule merchantModule : merchantModuleList ) {

            // Get the MerchantModuleResource
            merchantModuleResource = mapper.map(merchantModule,MerchantModuleResource.class);

            // Add the resource to the array list
            merchantModuleListResource.add(merchantModuleResource);

        }


        // return the merchantModuleResoueceList
        return merchantModuleListResource;

    }
}

package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.MerchantSetting;
import com.inspirenetz.api.rest.controller.MerchantSettingController;
import com.inspirenetz.api.rest.resource.MerchantSettingResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameen on 23/4/15.
 */
@Component
public class MerchantSettingAssembler extends ResourceAssemblerSupport<MerchantSetting,MerchantSettingResource> {

    @Autowired
    private Mapper mapper;
    
    public MerchantSettingAssembler() {

        super(MerchantSettingController.class,MerchantSettingResource.class);

    }
    @Override
    public MerchantSettingResource toResource(MerchantSetting merchantSetting) {

        // Create the MerchantSettingResource
        MerchantSettingResource merchantSettingResource = mapper.map(merchantSetting,MerchantSettingResource.class);

        return merchantSettingResource;
    }
    /**
     * Function to convert the list of Merchant objects into an equivalent list
     * of MerchantSettingResource objects
     *
     * @param merchantSettings - The List object for the Merchant objects
     * @return MerchantSettingResourceList - This list of MerchantSettingResource objects
     */
    public List<MerchantSettingResource> toResources(List<MerchantSetting> merchantSettings) {

        // Create the list that will hold the resources
        List<MerchantSettingResource> MerchantSettingResourceList = new ArrayList<MerchantSettingResource>();

        // Create the MerchantSettingResource object
        MerchantSettingResource merchantProfileResource = null;


        // Go through the merchants and then create the merchant resource
        for(MerchantSetting merchantSetting : merchantSettings ) {

            // Get the MerchantSettingResource
            merchantProfileResource = mapper.map(merchantSetting,MerchantSettingResource.class);

            // Add the resource to the array list
            MerchantSettingResourceList.add(merchantProfileResource);

        }


        // return the merchantResoueceList
        return MerchantSettingResourceList;

    }

}

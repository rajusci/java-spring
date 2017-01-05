package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.SecuritySetting;
import com.inspirenetz.api.rest.controller.SecuritySettingController;
import com.inspirenetz.api.rest.resource.SecuritySettingResource;
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
public class SecuritySettingAssembler extends ResourceAssemblerSupport<SecuritySetting,SecuritySettingResource> {

    @Autowired
    private Mapper mapper;

    public SecuritySettingAssembler() {

        super(SecuritySettingController.class,SecuritySettingResource.class);

    }

    @Override
    public SecuritySettingResource toResource(SecuritySetting securitySetting) {

        // Create the SecuritySettingResource
        SecuritySettingResource securitySettingResource = mapper.map(securitySetting,SecuritySettingResource.class);

        // Return the securitySettingResource
        return securitySettingResource;
    }


    /**
     * Function to convert the list of SecuritySetting objects into an equivalent list
     * of SecuritySettingResource objects
     *
     * @param securitySettingList - The List object for the SecuritySetting objects
     * @return securitySettingResourceList - This list of SecuritySettingResource objects
     */
    public List<SecuritySettingResource> toResources(List<SecuritySetting> securitySettingList) {

        // Create the list that will hold the resources
        List<SecuritySettingResource> securitySettingResourceList = new ArrayList<SecuritySettingResource>();

        // Create the SecuritySettingResource object
        SecuritySettingResource securitySettingResource = null;


        // Go through the securitySettings and then create the securitySetting resource
        for(SecuritySetting securitySetting : securitySettingList ) {

            // Add the resource to the array list
            securitySettingResourceList.add(securitySettingResource);

        }


        // return the securitySettingResoueceList
        return securitySettingResourceList;

    }

}

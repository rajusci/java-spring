package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Setting;
import com.inspirenetz.api.rest.controller.SettingController;
import com.inspirenetz.api.rest.resource.SettingResource;
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
public class SettingAssembler extends ResourceAssemblerSupport<Setting,SettingResource> {

    @Autowired
    private Mapper mapper;

    public SettingAssembler() {

        super(SettingController.class,SettingResource.class);

    }

    @Override
    public SettingResource toResource(Setting setting) {

        // Create the SettingResource
        SettingResource settingResource = mapper.map(setting,SettingResource.class);

        // Return the settingResource
        return settingResource;
    }


    /**
     * Function to convert the list of Setting objects into an equivalent list
     * of SettingResource objects
     *
     * @param settingList - The List object for the Setting objects
     * @return settingResourceList - This list of SettingResource objects
     */
    public List<SettingResource> toResources(List<Setting> settingList) {

        // Create the list that will hold the resources
        List<SettingResource> settingResourceList = new ArrayList<SettingResource>();

        // Create the SettingResource object
        SettingResource settingResource = null;


        // Go through the settings and then create the setting resource
        for(Setting setting : settingList ) {

            // Get the SettingResource
            settingResource = mapper.map(setting,SettingResource.class);

            // Add the resource to the array list
            settingResourceList.add(settingResource);

        }


        // return the settingResoueceList
        return settingResourceList;

    }

}

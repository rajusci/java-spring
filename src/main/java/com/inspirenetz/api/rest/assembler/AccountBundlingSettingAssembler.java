package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.AccountBundlingSetting;
import com.inspirenetz.api.rest.controller.AccountBundlingSettingController;
import com.inspirenetz.api.rest.resource.AccountBundlingSettingResource;
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
public class AccountBundlingSettingAssembler extends ResourceAssemblerSupport<AccountBundlingSetting,AccountBundlingSettingResource> {

    @Autowired
    private Mapper mapper;

    public AccountBundlingSettingAssembler() {

        super(AccountBundlingSettingController.class,AccountBundlingSettingResource.class);

    }

    @Override
    public AccountBundlingSettingResource toResource(AccountBundlingSetting accountBundlingSetting) {

        // Create the AccountBundlingSettingResource
        AccountBundlingSettingResource accountBundlingSettingResource = mapper.map(accountBundlingSetting,AccountBundlingSettingResource.class);

        // Return the accountBundlingSettingResource
        return accountBundlingSettingResource;
    }


    /**
     * Function to convert the list of AccountBundlingSetting objects into an equivalent list
     * of AccountBundlingSettingResource objects
     *
     * @param accountBundlingSettingList - The List object for the AccountBundlingSetting objects
     * @return accountBundlingSettingResourceList - This list of AccountBundlingSettingResource objects
     */
    public List<AccountBundlingSettingResource> toResources(List<AccountBundlingSetting> accountBundlingSettingList) {

        // Create the list that will hold the resources
        List<AccountBundlingSettingResource> accountBundlingSettingResourceList = new ArrayList<AccountBundlingSettingResource>();

        // Create the AccountBundlingSettingResource object
        AccountBundlingSettingResource accountBundlingSettingResource = null;


        // Go through the accountBundlingSettings and then create the accountBundlingSetting resource
        for(AccountBundlingSetting accountBundlingSetting : accountBundlingSettingList ) {

            // Get the AccountBundlingSettingResource
            accountBundlingSettingResource = mapper.map(accountBundlingSetting,AccountBundlingSettingResource.class);

            // Add the resource to the array list
            accountBundlingSettingResourceList.add(accountBundlingSettingResource);

        }


        // return the accountBundlingSettingResoueceList
        return accountBundlingSettingResourceList;

    }

}

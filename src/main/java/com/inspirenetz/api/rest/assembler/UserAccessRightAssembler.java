package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.UserAccessRight;
import com.inspirenetz.api.rest.controller.UserAccessRightController;
import com.inspirenetz.api.rest.resource.UserAccessRightResource;
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
public class UserAccessRightAssembler extends ResourceAssemblerSupport<UserAccessRight,UserAccessRightResource> {

    @Autowired
    private Mapper mapper;

    public UserAccessRightAssembler() {

        super(UserAccessRightController.class,UserAccessRightResource.class);

    }

    @Override
    public UserAccessRightResource toResource(UserAccessRight userAccessRight) {

        // Create the UserAccessRightResource
        UserAccessRightResource userAccessRightResource = mapper.map(userAccessRight,UserAccessRightResource.class);

        // Return the userAccessRightResource
        return userAccessRightResource;
    }


    /**
     * Function to convert the list of UserAccessRight objects into an equivalent list
     * of UserAccessRightResource objects
     *
     * @param userAccessRightList - The List object for the UserAccessRight objects
     * @return userAccessRightResourceList - This list of UserAccessRightResource objects
     */
    public List<UserAccessRightResource> toResources(List<UserAccessRight> userAccessRightList) {

        // Create the list that will hold the resources
        List<UserAccessRightResource> userAccessRightResourceList = new ArrayList<UserAccessRightResource>();

        // Create the UserAccessRightResource object
        UserAccessRightResource userAccessRightResource = null;


        // Go through the userAccessRights and then create the userAccessRight resource
        for(UserAccessRight userAccessRight : userAccessRightList ) {

            // Get the UserAccessRightResource
            userAccessRightResource = mapper.map(userAccessRight,UserAccessRightResource.class);

            // Add the resource to the array list
            userAccessRightResourceList.add(userAccessRightResource);

        }


        // return the userAccessRightResoueceList
        return userAccessRightResourceList;

    }

}

package com.inspirenetz.api.rest.assembler;


import com.inspirenetz.api.core.domain.UserRole;
import com.inspirenetz.api.rest.controller.UserRoleController;
import com.inspirenetz.api.rest.resource.MessageSpielResource;
import com.inspirenetz.api.rest.resource.UserRoleResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameenci on 10/9/14.
 */
@Component
public class UserRoleAssembler extends ResourceAssemblerSupport<UserRole,UserRoleResource> {
    @Autowired
    private Mapper mapper;

    public UserRoleAssembler() {

        super(UserRoleController.class,UserRoleResource.class);

    }

    @Override
    public UserRoleResource toResource(UserRole userRole) {

        UserRoleResource userRoleResource= mapper.map(userRole, UserRoleResource.class);

        return userRoleResource;

    }

    /**
     * Function to convert the list of UserRole objects into an equivalent list
     * of UserRole Resource  objects
     *
     * @param  - UserRole The List object for the UserRole objects
     * @return UserRoleList - This list of UserRoleResource objects
     */
    public List<UserRoleResource> toResources(List<UserRole> userRoleList) {

        // Create the list that will hold the resources
        List<UserRoleResource> userRoleListResource = new ArrayList<UserRoleResource>();

        // Create the userRoleResource object
        UserRoleResource userRoleResource = null;


        // Go through the user role  and then create the user role Resource
        for( UserRole userRole : userRoleList ) {

            // Get the user rol resource
            userRoleResource = toResource(userRole);

            // Add the resource to the array list
            userRoleListResource.add(userRoleResource);

        }


        // return the userRoleListResource
        return userRoleListResource;

    }
}

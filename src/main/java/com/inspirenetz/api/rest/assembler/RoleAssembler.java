package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Role;
import com.inspirenetz.api.rest.controller.RoleController;
import com.inspirenetz.api.rest.resource.RoleResource;
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
public class RoleAssembler extends ResourceAssemblerSupport<Role,RoleResource> {

    @Autowired
    private Mapper mapper;

    public RoleAssembler() {

        super(RoleController.class,RoleResource.class);

    }

    @Override
    public RoleResource toResource(Role role) {

        // Create the RoleResource
        RoleResource roleResource = mapper.map(role,RoleResource.class);

        // Return the roleResource
        return roleResource;
    }


    /**
     * Function to convert the list of Role objects into an equivalent list
     * of RoleResource objects
     *
     * @param roleList - The List object for the Role objects
     * @return roleResourceList - This list of RoleResource objects
     */
    public List<RoleResource> toResources(List<Role> roleList) {

        // Create the list that will hold the resources
        List<RoleResource> roleResourceList = new ArrayList<RoleResource>();

        // Create the RoleResource object
        RoleResource roleResource = null;


        // Go through the roles and then create the role resource
        for(Role role : roleList ) {

            // Add the resource to the array list
            roleResourceList.add(toResource(role));

        }


        // return the roleResoueceList
        return roleResourceList;

    }

}

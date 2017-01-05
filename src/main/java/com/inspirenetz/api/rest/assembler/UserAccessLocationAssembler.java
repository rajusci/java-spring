package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.UserAccessLocation;
import com.inspirenetz.api.core.service.UserAccessLocationService;
import com.inspirenetz.api.rest.controller.UserAccessLocationController;
import com.inspirenetz.api.rest.resource.UserAccessLocationResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameenci on 11/9/14.
 */
@Component
public class UserAccessLocationAssembler  extends ResourceAssemblerSupport<UserAccessLocation,UserAccessLocationResource> {

    @Autowired
    private Mapper mapper;

    public UserAccessLocationAssembler() {

        super(UserAccessLocationController.class,UserAccessLocationResource.class);

    }

    @Override
    public UserAccessLocationResource toResource(UserAccessLocation userAccessLocation) {

        UserAccessLocationResource UserAccessLocationResource= mapper.map(userAccessLocation, UserAccessLocationResource.class);

        return UserAccessLocationResource;

    }

    /**
     * Function to convert the list of UserAccessLocation objects into an equivalent list
     * of UserAccessLocationResource objects
     *
     * @param  userAccessLocations- The List object for the UserAccessLocation objects
     * @return UserAccessLocationList - This list of UserAccessLocationResource objects
     */
    public List<UserAccessLocationResource> toResources(List<UserAccessLocation> userAccessLocations) {

        // Create the list that will hold the resources
        List<UserAccessLocationResource> UserAccessLocationResourceList = new ArrayList<UserAccessLocationResource>();

        // Create the UserAccessLocationResource object
        UserAccessLocationResource UserAccessLocationResource = null;


        // Go through the UserAccessLocation and then create the UserAccessLocation Resource
        for(UserAccessLocation userAccessLocation : userAccessLocations ) {

            // Get the message spiel resource
            UserAccessLocationResource = toResource(userAccessLocation);

            // Add the resource to the array list
            UserAccessLocationResourceList.add(UserAccessLocationResource);

        }


        // return the UserAccessLocationResourceList
        return UserAccessLocationResourceList;

    }
}

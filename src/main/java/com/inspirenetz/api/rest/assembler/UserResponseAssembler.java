package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.UserResponse;
import com.inspirenetz.api.rest.controller.UserResponseController;
import com.inspirenetz.api.rest.resource.UserResponseResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alameen on 24/11/14.
 */
@Component
public class UserResponseAssembler extends ResourceAssemblerSupport<UserResponse,UserResponseResource> {

    @Autowired
    private Mapper mapper;


    public UserResponseAssembler() {

        super(UserResponseController.class,UserResponseResource.class);

    }

    @Override
    public UserResponseResource toResource(UserResponse userResponse) {

        UserResponseResource userResponseResource= mapper.map(userResponse, UserResponseResource.class);

        return userResponseResource;

    }

    /**
     * Function to convert the list of userProfile objects into an equivalent list
     * of userProfile Resource  objects
     *
     * @param  - userProfile The List object for the userProfile objects
     * @return userProfileList - This list of userProfileResource objects
     */
    public List<UserResponseResource> toResources(List<UserResponse> userResponseList) {

        // Create the list that will hold the resources
        List<UserResponseResource> userResponseResourcesList = new ArrayList<>();

        // Create the userProfileResource object
        UserResponseResource userResponseResource = null;


        // Go through the user role  and then create the user role Resource
        for( UserResponse userResponse : userResponseList ) {

            // Get the user rol resource
            userResponseResource = toResource(userResponse);

            // Add the resource to the array list
            userResponseResourcesList.add(userResponseResource);

        }


        // return the userProfileListResource
        return userResponseResourcesList;

    }


}

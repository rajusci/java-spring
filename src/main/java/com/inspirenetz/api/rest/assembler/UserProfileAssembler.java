package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.UserProfile;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.rest.controller.UserProfileController;
import com.inspirenetz.api.rest.resource.UserProfileResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alameen on 24/10/14.
 */
@Component
public class UserProfileAssembler extends ResourceAssemblerSupport<UserProfile,UserProfileResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ImageService imageService;

    public UserProfileAssembler() {

        super(UserProfileController.class,UserProfileResource.class);

    }

    @Override
    public UserProfileResource toResource(UserProfile userProfile) {

        UserProfileResource userProfileResource= mapper.map(userProfile, UserProfileResource.class);




        return userProfileResource;

    }

    /**
     * Function to convert the list of userProfile objects into an equivalent list
     * of userProfile Resource  objects
     *
     * @param  - userProfile The List object for the userProfile objects
     * @return userProfileList - This list of userProfileResource objects
     */
    public List<UserProfileResource> toResources(List<UserProfile> userProfileList) {

        // Create the list that will hold the resources
        List<UserProfileResource> userProfileResourceList = new ArrayList<UserProfileResource>();

        // Create the userProfileResource object
        UserProfileResource userProfileResource = null;


        // Go through the user role  and then create the user role Resource
        for( UserProfile userProfile : userProfileList ) {

            // Get the user rol resource
            userProfileResource = toResource(userProfile);

            // Add the resource to the array list
            userProfileResourceList.add(userProfileResource);

        }


        // return the userProfileListResource
        return userProfileResourceList;

    }
}

package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.core.service.MerchantLocationService;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.rest.controller.UserController;
import com.inspirenetz.api.rest.resource.UserResource;
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
public class UserAssembler extends ResourceAssemblerSupport<User,UserResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private MerchantLocationService merchantLocationService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private MerchantService merchantService;


    public UserAssembler() {

        super(UserController.class,UserResource.class);

    }

    @Override
    public UserResource toResource(User user) {

        // Create the UserResource
        UserResource userResource = mapper.map(user,UserResource.class);

        // Get the image path for the user
        Image image = user.getImage();

        // If the image is existing, then we need to find the data
        if( image != null && image.getImgImageId() != null ) {

            // Get the image path
            String imagePath = imageService.getPathForImage(image, ImagePathType.STANDARD);

            // Set the image path for the User object
            userResource.setProfilePic(imagePath);

        }



        // Get the Merchant
        Merchant merchant = user.getMerchant();

        // If the merchant is not null, then set the merchantName
        if ( merchant != null ) {

            //set merchant name
            userResource.setMerchantName(merchant.getMerMerchantName());

            //get merchant logo
            Image merchantImage = merchant.getImgLogo();


            // If the image is existing, then we need to find the data
            if( merchantImage != null && merchantImage.getImgImageId() != null ) {

                // Get the image path
                String imagePath = imageService.getPathForImage(merchantImage, ImagePathType.STANDARD);

                // Set the image path for the User object
                userResource.setMerchantLogo(imagePath);

            }
        }

        // Get the MerchantLocation
        MerchantLocation merchantLocation = user.getMerchantLocation();

        // If the merchantLocation is not null, then set the locationName
        if ( merchantLocation != null ) {

            userResource.setLocationName(merchantLocation.getMelLocation());

        }


        // Set the userPassword to empty string for not exposing the user
        userResource.setUsrPassword("");


        // Return the userResource
        return userResource;
    }

    /**
     * Function to convert the list of User objects into an equivalent list
     * of UserResource objects
     *
     * @param userList - The List object for the User objects
     * @return userResourceList - This list of UserResource objects
     */
    public List<UserResource> toResources(List<User> userList) {

        // Create the list that will hold the resources
        List<UserResource> userResourceList = new ArrayList<UserResource>();

        // Create the UserResource object
        UserResource userResource = null;


        // Go through the users and then create the user resource
        for(User user : userList ) {

            // Get the UserResource
            userResource = toResource(user);

            // Add the resource to the array list
            userResourceList.add(userResource);

        }


        // return the userResoueceList
        return userResourceList;

    }

}

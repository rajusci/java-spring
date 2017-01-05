package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.CustomerStatus;
import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.UserProfile;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.UserProfileRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.core.service.UserProfileService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.util.logging.resources.logging;

import java.util.List;

/**
 * Created by alameen on 24/10/14.
 */
@Service
public class UserProfileServiceImpl extends BaseServiceImpl<UserProfile>implements UserProfileService {

    private static Logger log = LoggerFactory.getLogger(PromotionServiceImpl.class);

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;


    @Autowired
    ImageService imageService;

    public UserProfileServiceImpl() {

        super(UserProfile.class);

    }

    @Override
    protected BaseRepository<UserProfile, Long> getDao() {

        return userProfileRepository;

    }

    @Override
    public UserProfile findByUspUserNo(Long uspUserNo) {

        UserProfile userProfile = null;

        //getting user number from customer
        //Customer customer =customerService.findByCusCustomerNo(uspUserNo);

        //first to get FName and LName from User
        User user =userService.findByUsrUserNo(uspUserNo);

        if(user !=null){

                userProfile =userProfileRepository.findByUspUserNo(user.getUsrUserNo()) ==null?new UserProfile() :userProfileRepository.findByUspUserNo(user.getUsrUserNo());

                userProfile.setUsrFName(user.getUsrFName());

                userProfile.setUsrLName(user.getUsrLName());

                userProfile.setUsrProfilePicPath(getUserImagePath(user));


        }

      return userProfile;

    }

    String getUserImagePath(User user){

        // Get the image path for the user
        Image image = user.getImage();

        // If the image is existing, then we need to find the data
        if( image != null && image.getImgImageId() != null ) {

            // Get the image path
            String imagePath = imageService.getPathForImage(image, ImagePathType.STANDARD);

            return imagePath;

        }

        return null;
    }

    @Override
    public UserProfile saveUserProfile(UserProfile userProfile) throws InspireNetzException {

        //check user number is present if the user number is present update other wise save
        UserProfile userProfile1;

        User user;

        if(userProfile==null){

            log.info("no user profile information");

            return null;
        }

        user=userService.findByUsrUserNo(userProfile.getUspUserNo());

        if(user==null||user.getUsrUserNo()==null){

            log.info("no user data found");

            return null;

        }

        user.setUsrFName(userProfile.getUsrFName());

        user.setUsrLName(userProfile.getUsrLName());

        if(userProfile.getUsrProfilePic() !=null){

            user.setUsrProfilePic(userProfile.getUsrProfilePic());
        }

        //for saving first name and last name in user
        userService.saveFNameAndLName(user);

        List<Customer> customers=customerService.getUserMemberships(0L,user.getUsrUserNo(), CustomerStatus.ACTIVE);

        for(Customer customer:customers){

            customer.setCusFName(userProfile.getUsrFName());

            customer.setCusLName(userProfile.getUsrLName());

            //update first name and last name via customer profile
            customerService.saveCustomer(customer);

        }

        //for searching user number is present if it present update information
        userProfile1 =userProfileRepository.findByUspUserNo(user.getUsrUserNo());

        if(userProfile1 !=null){

            userProfile.setUspId(userProfile1.getUspId());

        }

        return userProfileRepository.save(userProfile);

    }

    @Override
    public boolean delete(UserProfile userProfile) {

        userProfileRepository.delete(userProfile);

        return true;
    }


}

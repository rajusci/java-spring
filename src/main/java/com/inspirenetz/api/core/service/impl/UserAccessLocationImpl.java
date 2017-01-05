package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.UserAccessLocation;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.UserAccessLocationRepository;
import com.inspirenetz.api.core.service.UserAccessLocationService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
@Service
public class UserAccessLocationImpl extends BaseServiceImpl<UserAccessLocation> implements UserAccessLocationService {

    private static Logger log = LoggerFactory.getLogger(UserAccessLocationImpl.class);

    @Autowired
    UserAccessLocationRepository userAccessLocationRepository;


    @Autowired
    private AuthSessionUtils authSessionUtils;

    public UserAccessLocationImpl() {

        super(UserAccessLocation.class);

    }

    @Override
    protected BaseRepository<UserAccessLocation, Long> getDao() {
        return userAccessLocationRepository;
    }

  

    

    @Override
    public UserAccessLocation findByUalId(Long ualId) {

        //get user Access Location    object based by id
        UserAccessLocation userAccessLocation=userAccessLocationRepository.findByUalId(ualId);

        return userAccessLocation;
    }


    @Override
    public List<UserAccessLocation> findByUalUserId(Long ualUserId) {

        // Get all user Access Location    list based on msi function code
        List<UserAccessLocation> userAccessLocationList=userAccessLocationRepository.findByUalUserId(ualUserId);

        return userAccessLocationList;
    }
   
    @Override
    public UserAccessLocation saveUserAccessLocation(UserAccessLocation userAccessLocation) {

        //saving the user Access Location   
        return userAccessLocationRepository.save(userAccessLocation);
    }

    @Override
    public boolean deleteUserAccessLocation(Long ualId) {

        //for deleting the user Access Location   
        userAccessLocationRepository.delete(ualId);

        return true;
    }

    @Override
    public boolean deleteUserAccessLocationSet(Set<UserAccessLocation> userAccessLocations) {

         userAccessLocationRepository.delete(userAccessLocations);

         return true;

    }


    @Override
    public UserAccessLocation validateAndSaveUserAccessLocation(UserAccessLocation userAccessLocation) throws InspireNetzException {



        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        Integer userType = authSessionUtils.getUserType();

        if ( userType != UserType.SUPER_ADMIN  &&  userType != UserType.MERCHANT_ADMIN ) {

            // Log the information
            log.info("Exception in :: validateAndSaveUserAccessLocation UnAuthorised User userType="+userType);

            // Throw not authrorized exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }      


        // If the user Access Location  .getMsiId is  null, then set the created_by, else set the updated_by
        if ( userAccessLocation.getUalId() == null ) {

            userAccessLocation.setCreatedBy(auditDetails);

        } else {

            userAccessLocation.setUpdatedBy(auditDetails);

        }

        // Save the object
        userAccessLocation = saveUserAccessLocation(userAccessLocation);

        // Check if the user Access Location   is saved
        if ( userAccessLocation.getUalId() == null ) {

            // Log the response
            log.info("validateAndSaveUserAccessLocation - Response : Unable to save the user Access Location    information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // return the object
        return userAccessLocation;


    }

    @Override
    public boolean validateAndDeleteUserAccessLocation(Long ualId) throws InspireNetzException {


        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        Integer userType = authSessionUtils.getUserType();

        // for checking authorisation of user
        if ( userType != UserType.SUPER_ADMIN && userType != UserType.ADMIN ) {

            // Log the information
            log.info("Exception in :: validateAndDeleteuser Access Location   UnAuthorised User userType="+userType);

            // Throw not authrorized exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        // Get the user Access Location    information
        UserAccessLocation userAccessLocation = findByUalId(ualId);

        // Check if the user Access Location   is found
        if ( userAccessLocation == null || userAccessLocation.getUalId() == null) {

            // Log the response
            log.info("deleteuser Access Location    - Response : No user Access Location    information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Delete the linkRequest and set the retData fields
        deleteUserAccessLocation(ualId);

        // Return true
        return true;

    }

    @Override
    public boolean updateUserAccessLocation(User user){

        //for getting userRoles Id from the user object
        Set<UserAccessLocation> userLocationList=user.getUserAccessLocationSet();


        log.info("userAccess Location List"+userLocationList);

        //for fetching user access right based by user Id
        List<UserAccessLocation> userLocationBaseList=findByUalUserId(user.getUsrUserNo());

        log.info("user access location base List"+userLocationBaseList);

        //for holding present list into arrayList

        Set<UserAccessLocation> userDeletingList=new HashSet<>();

        if(userLocationList == null){

            userDeletingList.addAll(userLocationBaseList);

        }

        else if(userLocationList!=null && userLocationBaseList!=null){


            for(UserAccessLocation userAccessLocation :userLocationBaseList){

                boolean toDelete = true;

                for(UserAccessLocation userAccessLocation1 : userLocationList){

                    Long ualId = userAccessLocation1.getUalId()==null ?0L:userAccessLocation1.getUalId().longValue();

                    if(ualId !=0L ){

                        if(userAccessLocation1.getUalId().longValue() == userAccessLocation.getUalId().longValue()){

                            toDelete = false;

                            break;

                        }
                    }
                }


                if(toDelete){

                    userDeletingList.add(userAccessLocation);

                }

            }
        }

        // for deleting user access right
        if(!userDeletingList.isEmpty()){

            deleteUserAccessLocationSet(userDeletingList);

            log.info("deleting user Location");
        }



        return true;
    }


}

package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.UserRole;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.UserRoleRepository;
import com.inspirenetz.api.core.service.UserRoleService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService {

    private static Logger log = LoggerFactory.getLogger(UserRoleServiceImpl.class);


    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    private AuthSessionUtils authSessionUtils;


    public UserRoleServiceImpl() {

        super(UserRole.class);

    }


    @Override
    protected BaseRepository<UserRole,Long> getDao() {
        return userRoleRepository;
    }


    @Override
    public List<UserRole> findByUerUserId(Long uerId) {

        // Get the User role list
        List<UserRole> userRolesList = userRoleRepository.findByUerUserId(uerId);

        // Return the UserRoleList
        return userRolesList;

    }

    @Override
    public UserRole findByUerId(Long uarId) {

        // Find the userRole information
        UserRole userRole = userRoleRepository.findByUerId(uarId);

        // Return the user Role
        return userRole;

    }

   @Override
    public UserRole saveUserRole(UserRole userRole ){

        // Save the userRole
        return userRoleRepository.save(userRole);

    }

    @Override
    public boolean deleteUserRole(Long uarId) {

        // Delete the UserRole
        userRoleRepository.delete(uarId);

        // return true
        return true;

    }

    @Override
    public UserRole validateAndSaveUserRole(UserRole userRole) throws InspireNetzException {



        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        Integer userType = authSessionUtils.getUserType();

        if ( userType != UserType.SUPER_ADMIN  &&  userType != UserType.MERCHANT_ADMIN ) {

            // Log the information
            log.info("Exception in :: validateAndSaveUserRole UnAuthorised User userType="+userType);

            // Throw not authrorized exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        // If the roll access right .getMsiId is  null, then set the created_by, else set the updated_by
        if ( userRole.getUerId() == null ) {

            userRole.setCreatedBy(auditDetails);

        } else {

            userRole.setUpdatedBy(auditDetails);

        }

        // Save the object
         userRole = saveUserRole(userRole);

        // Check if the object is saved
        if ( userRole.getUerId() == null ) {

            // Log the response
            log.info("validateAndSaveUserRole - Response : Unable to save the message spiel information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // return the object
        return userRole;


    }
    @Override
    public boolean validateAndDeleteUserRole(Long uerId) throws InspireNetzException {


        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        Integer userType = authSessionUtils.getUserType();

        // for checking authorisation of user
        if ( userType != UserType.SUPER_ADMIN && userType != UserType.MERCHANT_ADMIN ) {

            // Log the information
            log.info("Exception in :: validateAndDeleteUserRole UnAuthorised User userType="+userType);

            // Throw not authrorized exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        // Get the role  information
        UserRole userRole = findByUerId(uerId);

        // Check if the role is found
        if ( userRole == null || userRole.getUerId() == null) {

            // Log the response
            log.info("delete userRole - Response : No roleAccessRight information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Delete the delete user role  and set the retData fields
        deleteUserRole(uerId);

        // Return true
        return true;

    }

    @Override
    public boolean deleteUserRoleSet(Set<UserRole> userRoles) {

        userRoleRepository.delete(userRoles);

        return true;

    }

    @Override
    public boolean updateUserRole(User user){

        //for getting userRoles Id from the user object
        Set<UserRole> userRoleList=user.getUserRoleSet();


        log.info("userRoleList"+userRoleList);

        //for fetching user access right based by user Id
        List<UserRole> userRolesBaseList=findByUerUserId(user.getUsrUserNo());

        log.info("userAccess base List"+userRolesBaseList);

        //for holding present list into arrayList

        Set<UserRole> userDeletingList=new HashSet<>();

        if(userRoleList == null){

            userDeletingList.addAll(userRolesBaseList);

        } else if(userRoleList!=null && userRolesBaseList!=null){


            for(UserRole userRole :userRolesBaseList){

                boolean toDelete = true;

                for(UserRole userRole1 : userRoleList){

                    Long uerId = userRole1.getUerId()==null ?0L:userRole1.getUerId().longValue();

                    if(uerId !=0L){

                        if(userRole1.getUerId().longValue()==userRole.getUerId().longValue()){

                            toDelete = false;

                            break;

                        }
                    }
                }

                // If the flag is true, then add to the userDeletingList
                if(toDelete){

                    userDeletingList.add(userRole);

                }

            }
        }

        // for deleting user access right
        if( !userDeletingList.isEmpty() ){

            deleteUserRoleSet(userDeletingList);

            log.info("deleting user Role");

        }


        return true;
    }

}

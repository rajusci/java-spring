package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.RoleAccessRight;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.UserAccessRight;
import com.inspirenetz.api.core.domain.UserRole;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.UserAccessRightRepository;
import com.inspirenetz.api.core.service.RoleAccessRightService;
import com.inspirenetz.api.core.service.UserAccessRightService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class UserAccessRightServiceImpl extends BaseServiceImpl<UserAccessRight> implements UserAccessRightService {


    private static Logger log = LoggerFactory.getLogger(UserAccessRightServiceImpl.class);


    @Autowired
    UserAccessRightRepository userAccessRightRepository;

    @Autowired
    RoleAccessRightService roleAccessRightService;

    @Autowired
    UserService userService;


    public UserAccessRightServiceImpl() {

        super(UserAccessRight.class);

    }


    @Override
    protected BaseRepository<UserAccessRight,Long> getDao() {
        return userAccessRightRepository;
    }


    @Override
    public List<UserAccessRight> findByUarUserNo(Long uarUserNo) {

       // Get the UserAccessright list
       List<UserAccessRight> userAccessRightList = userAccessRightRepository.findByUarUserNo(uarUserNo);

       // Return the UserAccessRightList
       return userAccessRightList;

    }

    @Override
    public UserAccessRight findByUarUarId(Long uarId) {

        // Find the UserAccessRight information
        UserAccessRight userAccessRight = userAccessRightRepository.findByUarUarId(uarId);

        // Return the UserAccessRight
        return userAccessRight;

    }

    @Override
    public UserAccessRight findByUarUserNoAndUarFunctionCode(Long uarUserNo, Long uarFunctionCode) {

        // Get the UserAccessrigt
        UserAccessRight  userAccessRight = userAccessRightRepository.findByUarUserNoAndUarFunctionCode(uarUserNo,uarFunctionCode);

        // Return the userAccessRight
        return userAccessRight;

    }



    @Override
    public boolean isDuplicateUserAccessRightExisting(UserAccessRight userAccessRight) {

        // Get the userAccessRight information
        UserAccessRight exUserAccessRight = userAccessRightRepository.findByUarUserNoAndUarFunctionCode(userAccessRight.getUarUserNo(), userAccessRight.getUarFunctionCode());

        // If the uarId is 0L, then its a new userAccessRight so we just need to check if there is ano
        // ther userAccessRight code
        if ( userAccessRight.getUarUarId() == null || userAccessRight.getUarUarId() == 0L ) {

            // If the userAccessRight is not null, then return true
            if ( exUserAccessRight != null ) {

                return true;

            }

        } else {

            // Check if the userAccessRight is null
            if ( exUserAccessRight != null && userAccessRight.getUarUarId().longValue() != exUserAccessRight.getUarUarId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;
    }

    @Override
    public HashMap<Long, String> getUarAsMap(Long uarUserNo) throws InspireNetzException {


        // HashMap holding the values
        HashMap<Long,String> uarMap = new HashMap<>(0);

        // Get the UARList
        List<UserAccessRight> accessEnableList = userService.getUserAccessRightsByUserNo(uarUserNo);

        // Iterate through the accessEnableList
        for( UserAccessRight userAccessRight : accessEnableList ) {

            // Now add the key value pair to the map
            uarMap.put(userAccessRight.getUarFunctionCode(),userAccessRight.getUarAccessEnableFlag());

        }


        // Return the uarMap
        return uarMap;

    }


    @Override
    public UserAccessRight saveUserAccessRight(UserAccessRight userAccessRight ){

        // Save the userAccessRight
        return userAccessRightRepository.save(userAccessRight);

    }

    @Override
    public boolean deleteUserAccessRight(Long uarId) {

        // Delete the userAccessRight
        userAccessRightRepository.delete(uarId);

        // return true
        return true;

    }

    @Override
    public boolean deleteUserAccessRightSet(Set<UserAccessRight> userAccessRights) {

        userAccessRightRepository.delete(userAccessRights);

        return true;
    }

    @Override
    public boolean updateUserAccessRights(User user){

        //for getting userRoles Id from the user object
        Set<UserAccessRight> userAccessRights=user.getUserAccessRightsSet();


        log.info("userAccess Rights List"+userAccessRights);

        //for fetching user access right based by user Id
        List<UserAccessRight> userRightsBaseList=findByUarUserNo(user.getUsrUserNo());

        log.info("user access rights base List"+userRightsBaseList);

        //for holding present list into arrayList
        Set<UserAccessRight> userDeletingList =  new HashSet<>();

        // Find the items to delete if not null
        if(userAccessRights!=null && userRightsBaseList != null) {

            // Iterate through the access right list
            for(UserAccessRight userAccessRight :userRightsBaseList){

                boolean toDelete = true;

                for(UserAccessRight userAccessRight1 : userAccessRights){

                    Long uarId = userAccessRight1.getUarUarId() == null ? 0L: userAccessRight1.getUarUarId().longValue();

                    if(uarId !=0L ){

                        if(userAccessRight1.getUarUarId().longValue() == userAccessRight.getUarUarId().longValue()){

                            // Set the flag to false
                            toDelete = false;

                            // Break out of the loop
                            break;

                        }
                    }
                }

                // If the toDelete flag is set, then add to the list for deletion
                if(toDelete){

                    userDeletingList.add(userAccessRight);

                }

            }
        }

        // for deleting user access right
        if( !userDeletingList.isEmpty()  ){

            deleteUserAccessRightSet(userDeletingList);

            log.info("deleting user access right id");
        }


        // Finally return true
        return true;
    }

    @Override
    public Set<UserAccessRight> setUserAccessRights (User user){

        //get the user roles selected by the user
        Set<UserRole> userRoles = user.getUserRoleSet();

        //get the user access rights altered by the user
        Set<UserAccessRight> userAccessRights = user.getUserAccessRights();

        //new object for adding to the map
        UserAccessRight userAccessRight = new UserAccessRight();

        //map storing all the access rights for the currently selected roles
        Map<Long,UserAccessRight> userAccessRightMap = new HashMap<>(0);

        if(null!= userRoles){
            //get all the access rights for the currently selected user roles
            for(UserRole userRole : userRoles){

                //getting the user access rights for the current role
                List<RoleAccessRight> roleAccessRightList = roleAccessRightService.findByRarRole(userRole.getUerRole());

                for(RoleAccessRight roleAccessRight : roleAccessRightList){

                    userAccessRight = new UserAccessRight();

                    userAccessRight.setUarFunctionCode(roleAccessRight.getRarFunctionCode());

                    userAccessRight.setUarAccessEnableFlag("Y");

                    userAccessRight.setUarUserNo(user.getUsrUserNo());

                    userAccessRightMap.put(roleAccessRight.getRarFunctionCode(),userAccessRight);

                }
            }
        }

        if(null != userAccessRights){

            //get the user changed access rights and alter them in the map
            for(UserAccessRight userAccessRightToAlter:userAccessRights ){

                Long functionCode = userAccessRightToAlter.getUarFunctionCode();

                String accessEnableFlag = userAccessRightToAlter.getUarAccessEnableFlag();

                UserAccessRight currentUserAccessRight = userAccessRightMap.get(functionCode);

                if(currentUserAccessRight == null){

                    userAccessRightMap.put(functionCode,userAccessRightToAlter);

                } else {

                    userAccessRightMap.remove(functionCode);

                    currentUserAccessRight.setUarAccessEnableFlag(accessEnableFlag);

                    currentUserAccessRight.setUarAccessOverridenFlag(1);

                    userAccessRightMap.put(functionCode, currentUserAccessRight);

                }

            }
        }

        //converts the map into a set
        Set<UserAccessRight> userAccessRightsToSave = new HashSet<>(userAccessRightMap.values());


        return userAccessRightsToSave;

    }
}

package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.RoleAccessRight;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.RoleAccessRightRepository;
import com.inspirenetz.api.core.service.RoleAccessRightService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import javax.management.relation.Role;
import java.util.List;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */

@Service
public class RoleAccessRightImpl extends BaseServiceImpl<RoleAccessRight> implements RoleAccessRightService {



    private static Logger log = LoggerFactory.getLogger(RoleAccessRightImpl.class);

    @Autowired
    RoleAccessRightRepository roleAccessRightRepository;


    @Autowired
    private AuthSessionUtils authSessionUtils;

    public RoleAccessRightImpl() {

        super(RoleAccessRight.class);

    }
    @Override
    protected BaseRepository<RoleAccessRight, Long> getDao() {
        return roleAccessRightRepository;
    }

    @Override
    public RoleAccessRight findByRarId(Long rarId) {

        // Getting data based by rarId
        RoleAccessRight roleAccessRight=roleAccessRightRepository.findByRarId(rarId);
        return roleAccessRight;
    }

    @Override
    public List<RoleAccessRight> findByRarRole(Long rarRole) {

        // Getting data by rarRole
        List<RoleAccessRight> roleAccessRightList=roleAccessRightRepository.findByRarRole(rarRole);

        return roleAccessRightList;
    }

    @Override
    public List<RoleAccessRight> findByRarRoleAndRarFunctionCode(Long rarRole, Long rarFunctionCode) {


        // Getting data by function code and role
        List<RoleAccessRight> roleAccessRightList=roleAccessRightRepository.findByRarRoleAndRarFunctionCode(rarRole,rarFunctionCode);

        return roleAccessRightList;
    }

    @Override
    public RoleAccessRight saveRoleAccessRight(RoleAccessRight roleAccessRight) {

        //saving the role access right
        return roleAccessRightRepository.save(roleAccessRight);
    }

    @Override
    public boolean deleteRoleAccessRight(Long rarId) {

        //for deleting the roll access right
        roleAccessRightRepository.delete(rarId);

        return true;
    }


    @Override
    public RoleAccessRight validateAndSaveRoleAccessRight(RoleAccessRight roleAccessRight) throws InspireNetzException {



        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        Integer userType = authSessionUtils.getUserType();

        if ( userType != UserType.SUPER_ADMIN  &&  userType != UserType.MERCHANT_ADMIN ) {

            // Log the information
            log.info("Exception in :: validateAndSaveRoleAccessRight UnAuthorised User userType="+userType);

            // Throw not authrorized exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        // If the roll access right .getMsiId is  null, then set the created_by, else set the updated_by
        if ( roleAccessRight.getRarId() == null ) {

            roleAccessRight.setCreatedBy(auditDetails);

        } else {

            roleAccessRight.setUpdatedBy(auditDetails);

        }

        // Save the object
        roleAccessRight = saveRoleAccessRight(roleAccessRight);

        // Check if the messageSpiel is saved
        if ( roleAccessRight.getRarId() == null ) {

            // Log the response
            log.info("validateAndSaveRoleAccessRight - Response : Unable to save the message spiel information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // return the object
        return roleAccessRight;


    }

    @Override
    public boolean validateAndDeleteRoleAccessRight(Long rarId) throws InspireNetzException {


        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        Integer userType = authSessionUtils.getUserType();

        // for checking authorisation of user
        if ( userType != UserType.SUPER_ADMIN && userType != UserType.MERCHANT_ADMIN ) {

            // Log the information
            log.info("Exception in :: validateAndDeleteRoleAccessRight UnAuthorised User userType="+userType);

            // Throw not authrorized exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        // Get the roleAccessRight  information
        RoleAccessRight roleAccessRight = findByRarId(rarId);

        // Check if the roleAccessRight is found
        if ( roleAccessRight == null || roleAccessRight.getRarId() == null) {

            // Log the response
            log.info("deleteMessage spiel - Response : No roleAccessRight information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Delete the deleteRoleAccessRight and set the retData fields
        deleteRoleAccessRight(rarId);

        // Return true
        return true;

    }

    /**
     * modified by :Al Ameen N
     * purpose:delete roleAccessRight
     * @param roleAccessRightSet
     */
    public void deleteRoleAccessRightSet(Set<RoleAccessRight> roleAccessRightSet){

        roleAccessRightRepository.delete(roleAccessRightSet);

    }

}

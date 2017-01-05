package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Function;
import com.inspirenetz.api.core.domain.Role;
import com.inspirenetz.api.core.domain.RoleAccessRight;
import com.inspirenetz.api.core.domain.validator.RoleValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.RoleAccessRightRepository;
import com.inspirenetz.api.core.repository.RoleRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.RoleAccessRightService;
import com.inspirenetz.api.core.service.RoleService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {


    private static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    RoleAccessRightRepository roleAccessRightRepository;

    @Autowired
    private RoleAccessRightService roleAccessRightService;


    @Autowired
    private AuthSessionUtils authSessionUtils;


    public RoleServiceImpl() {

        super(Role.class);

    }


    @Override
    protected BaseRepository<Role,Long> getDao() {
        return roleRepository;
    }



    @Override
    public Role findByRolId(Long rolId) throws InspireNetzException {

        // Get the role for the given role id from the repository
        Role role = roleRepository.findByRolId(rolId);

        // Return the role
        return role;

    }

    @Override
    public Page<Role> searchRoles(String filter,String query,Pageable pageable) {


        Integer userType = authSessionUtils.getUserType();

        Long merchantNo = 0L;

        //Get the roles list
        Page<Role>  rolePage = null;

        if(userType.intValue() == UserType.MERCHANT_ADMIN ){

            merchantNo = authSessionUtils.getMerchantNo();

        }

        if(filter.equals("name")){

            rolePage = roleRepository.findByRolMerchantNoAndRolNameLike(merchantNo, "%" + query + "%", pageable);

        } else {

            rolePage = roleRepository.findByRolMerchantNo(merchantNo, pageable);



        }


        // Return the page
        return rolePage;

    }

    @Override
    public List<Role> getRolesByUserType(Integer rolUserType) throws InspireNetzException {


        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        List<Role> roles = roleRepository.findByRolUserType(rolUserType);

        // return the list
        return roles;


    }




    @Override
    public Role validateAndSaveRole(Role role ) throws InspireNetzException {

        //check user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_ROLE_DEFINITION);

        Integer userType = authSessionUtils.getUserType();

        Long merchantNo = 0L;

        //check whether the user logged in is a merchant admin ,if true set value to merchant no
        //and set user type as merchant user
        if(userType.intValue() == UserType.MERCHANT_ADMIN ){

            merchantNo = authSessionUtils.getMerchantNo();
            //set user type as merchant user
            role.setRolUserType(UserType.MERCHANT_USER);

        }

        //set merchant no
        role.setRolMerchantNo(merchantNo);

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Create the Validator
        RoleValidator validator = new RoleValidator();

        // Create the bindingREsult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(role,"role");

        // Validate the request
        validator.validate(role,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveRole - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }




        // If the role.getLrqId is  null, then set the created_by, else set the updated_by
        if ( role.getRolId() == null ) {

            role.setCreatedBy(auditDetails);

        } else {

            role.setUpdatedBy(auditDetails);

        }




        // Save the object for if roll id is zero for normal saving else delete access rights and update list
        if(role.getRolId()==null){

            role = saveRole(role);

        }else{

            role =updateRole(role);
        }


        // Check if the role is saved
        if ( role.getRolId() == null ) {

            // Log the response
            log.info("validateAndSaveRole - Response : Unable to save the role information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // return the object
        return role;


    }

    @Override
    public Role saveRole(Role role ){

        // Save the role
        return roleRepository.save(role);

    }

    @Override
    public boolean validateAndDeleteRole(Long rolId) throws InspireNetzException {

        //check user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_ROLE_DEFINITION);

        // Get the merchant number
        Long merchantNo = authSessionUtils.getMerchantNo();

        Integer userType = authSessionUtils.getUserType();

        // Get the role information
        Role role = findByRolId(rolId);

        // Check if the role is found
        if ( role == null || role.getRolId() == null) {

            // Log the response
            log.info("deleteRole - Response : No role information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the merchant is valid for deletin
        if ( userType.intValue() == UserType.MERCHANT_ADMIN && role.getRolMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteRole - Response : Not authorized");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the role and set the retData fields
        deleteRole(rolId);

        // Return true
        return true;

    }

    @Override
    public boolean deleteRole(Long rolId) {

        // Delete the role
        roleRepository.delete(rolId);

        // return true
        return true;

    }

    @Override
    public List<Role> getAllRoles() throws InspireNetzException {

        // Get the merchant number
        Long merchantNo = authSessionUtils.getMerchantNo();

        List<Role> roles = roleRepository.getAllRoles(merchantNo);

        return roles;
    }

    // for delete the existing role and update role rights
    public Role updateRole(Role role){

        //for getting roleAccessRights Id from the role object
        Set<RoleAccessRight> roleAccessList=role.getRoleAccessRightSet();

        //intializing delete list
        Set<RoleAccessRight> roleDeleteSet=new HashSet<>();


        log.info("RoleServiceImpl :: updateRole roleAccessList"+roleAccessList);

        //for fetching role access right based by role Id
        List<RoleAccessRight> roleAccessRightsBaseList=roleAccessRightRepository.findByRarRole(role.getRolId());

        log.info("RoleServiceImpl :: updateRole base list from present"+roleAccessRightsBaseList);

        //for holding present list into arrayList

        List<Long> roleDeletingList=new ArrayList<>();



        boolean toDelete=true;

        if(roleAccessList!=null && roleAccessRightsBaseList!=null){


            for(RoleAccessRight roleAccessRight :roleAccessRightsBaseList){



                for(RoleAccessRight roleAccessRight1 : roleAccessList){

                    //for getting value from Access List
                    Long rarId = roleAccessRight1.getRarId()==null ?0L:roleAccessRight1.getRarId().longValue();

                    if(rarId !=0L){

                        if(roleAccessRight.getRarId().longValue()==roleAccessRight1.getRarId().longValue()){



                                toDelete=false;

                                break;

                         }else{

                                toDelete=true;

                         }

                        }else{


                            continue;

                        }


                    }

                    if(toDelete==true){

                        roleDeleteSet.add(roleAccessRight);

                    }


            }
        }

        // for deleting role access right
        if(roleDeleteSet!=null){


            roleAccessRightService.deleteRoleAccessRightSet(roleDeleteSet);

            log.info("delete Access rights");
        }
        // for updating role and role access rights

        role=roleRepository.save(role);

        log.info("role is successfully updated---"+role.getRolId());

        return role;
    }

}


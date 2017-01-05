package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.Role;
import com.inspirenetz.api.core.service.RoleService;
import com.inspirenetz.api.rest.assembler.RoleAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.RoleResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class RoleController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleAssembler roleAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;



    @RequestMapping(value = "/api/0.9/json/merchant/roles/role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveRole(@RequestBody Map<String,Object> params) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveRole - Request Received# "+params.toString());
        log.info("saveRole - "+generalUtils.getLogTextForRequest());

        // convert the LInkRequestResource to object
        Role role = mapper.map(params,Role.class);

        // save the role object and get the result
        role = roleService.validateAndSaveRole(role);

        // Get the role id
        retData.setData(role.getRolId());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveRole - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/roles/role/delete/{rolId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteRole(@PathVariable(value="rolId") Long rolId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("deleteRole - Request Received# Role ID: "+rolId);
        log.info("deleteRole - "+generalUtils.getLogTextForRequest());



        // Delete the role and set the retData fields
        roleService.validateAndDeleteRole(rolId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete lrqId
        retData.setData(rolId);



        // Log the response
        log.info("deleteRole - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/roles/role/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRoles(@PathVariable(value ="filter") String filter,
                                              @PathVariable(value ="query") String query,
                                              Pageable pageable){


        // Log the Request
        log.info("listRoles - Request Received# filter "+ filter +" query :" +query );
        log.info("listRoles - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the Role
        Page<Role> rolePage = roleService.searchRoles(filter,query,pageable);

        // Convert to reosurce
        List<RoleResource> roleResourceList =  roleAssembler.toResources(rolePage);

        // Set the data
        retData.setData(roleResourceList);

        // Log the response
        log.info("listRoles - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/roles/role/{rolId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRoleInfo(@PathVariable(value="rolId") Long rolId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getRoleInfo - "+generalUtils.getLogTextForRequest());

        // Get the Role
        Role role = roleService.findByRolId(rolId);

        // Convert the Role to RoleResource
        RoleResource roleResource = roleAssembler.toResource(role);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the roleResource object
        retData.setData(roleResource);

        // Log the response
        log.info("getRoleInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/roles/allroles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRoles() throws InspireNetzException {


        // Log the Request
        log.info("listRoles - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the Role
        List<Role> roleList = roleService.getAllRoles();

        // Convert to reosurce
        List<RoleResource> roleResourceList =  roleAssembler.toResources(roleList);

        // Set the data
        retData.setData(roleResourceList);

        // Log the response
        log.info("listRoles - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }



}
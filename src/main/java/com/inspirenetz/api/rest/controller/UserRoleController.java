package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.UserRole;
import com.inspirenetz.api.core.service.UserRoleService;
import com.inspirenetz.api.rest.assembler.UserRoleAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.UserRoleResource;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameenci on 10/9/14.
 */
@Controller
public class UserRoleController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(UserRoleController.class);

    @Autowired
    private UserRoleService userRoleService;


    @Autowired
    private UserRoleAssembler userRoleAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/admin/userrole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveUserRole(UserRoleResource userRoleResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveUserRole - Request Received# "+userRoleResource.toString());
        log.info("saveMessageSpiel - "+generalUtils.getLogTextForRequest());


        // convert the User role   to object
        UserRole userRole = mapper.map(userRoleResource,UserRole.class);

        // save the user role  object and get the result
        userRole = userRoleService.validateAndSaveUserRole(userRole);

        // Get the user role  id
        retData.setData(userRole.getUerId());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveMessageSpiel - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/admin/userrole/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listUserRoleByUserId(@PathVariable(value ="userId") Long userId){

       // Log the Request
        log.info("User role  - Request Received# userId "+ userId);
        log.info("messagespiel - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Variable holding the MessageSpiel
        List<UserRoleResource> userRoleResourceList = new ArrayList<>(0);

        List<UserRole> userRoleList=userRoleService.findByUerUserId(userId);

        userRoleResourceList=userRoleAssembler.toResources(userRoleList);

        // Set the data
        retData.setData(userRoleResourceList);

        // Log the response
        log.info("user role  - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/admin/userrole/delete/{roleId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteUserRole(@PathVariable(value ="roleId") Long roleId) throws InspireNetzException {

        // Log the Request
        log.info("messagespiel - Request Received# ::deleteuser role Id roleId="+ roleId);
        log.info("messagespiel - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // for deleting User role
        userRoleService.validateAndDeleteUserRole(roleId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        retData.setData(roleId);

        // Log the response
        log.info("user Role  - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


}

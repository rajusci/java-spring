package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.UserAccessLocation;
import com.inspirenetz.api.core.service.UserAccessLocationService;
import com.inspirenetz.api.rest.assembler.UserAccessLocationAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.UserAccessLocationResource;
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
 * Created by ameenci on 11/9/14.
 */
@Controller
public class UserAccessLocationController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(UserAccessLocationController.class);

    @Autowired
    private UserAccessLocationService userAccessLocationService;


    @Autowired
    private UserAccessLocationAssembler userAccessLocationAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/admin/useraccesslocation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveUserAccessLocation(UserAccessLocationResource userAccessLocationResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveUserAccessLocation - Request Received# "+userAccessLocationResource.toString());
        log.info("saveUserAccessLocation - "+generalUtils.getLogTextForRequest());




        // convert the Messsage spiel  to object
        UserAccessLocation userAccessLocation = mapper.map(userAccessLocationResource,UserAccessLocation.class);

        // save the UserAccessLocation object and get the result
        userAccessLocation = userAccessLocationService.validateAndSaveUserAccessLocation(userAccessLocation);

        // Get the UserAccessLocation id
        retData.setData(userAccessLocation.getUalId());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveUserAccess Location - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/admin/useraccesslocation/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listUserAccessLocation(@PathVariable(value ="userId") Long userId){
                                             


        // Log the Request
        log.info("listUserAccessLocation - Request Received#  userId "+ userId);
        log.info("listUserAccessLocation - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Variable holding the UserAccessLocation
        List<UserAccessLocationResource> userAccessLocationResourceList = new ArrayList<>(0);

        List<UserAccessLocation> userAccessLocations=userAccessLocationService.findByUalUserId(userId);

        userAccessLocationResourceList=userAccessLocationAssembler.toResources(userAccessLocations);

        // Set the data
        retData.setData(userAccessLocationResourceList);

        // Log the response
        log.info("UserAccessLocation - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

   

    @RequestMapping(value = "/api/0.9/json/admin/useraccesslocation/delete/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteUserAccessLocation(@PathVariable(value ="userId") Long userId) throws InspireNetzException {

        // Log the Request
        log.info("deleteUserAccessLocation - Request Received# ::deleteUserAccessLocation userId="+ userId);
        log.info("deleteUserAccessLocation - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // for deleting UserAccessLocation
        userAccessLocationService.validateAndDeleteUserAccessLocation(userId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        retData.setData(userId);

        // Log the response
        log.info("UserAccessLocation - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }
}

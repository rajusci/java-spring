package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.UserProfile;
import com.inspirenetz.api.core.service.UserProfileService;
import com.inspirenetz.api.rest.assembler.UserProfileAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.UserProfileResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by alameen on 24/10/14.
 */
@Controller
public class UserProfileController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(UserProfileController.class);

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserProfileAssembler userProfileAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/customer/profile/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes =MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveUserProfile(UserProfileResource userProfileResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveUserProfile - Request Received# "+userProfileResource.toString());
        log.info("saveUserProfile -  "+generalUtils.getLogTextForRequest());




        // convert the userProfile   to object
        UserProfile userProfile = mapper.map(userProfileResource,UserProfile.class);

        Long userNo=authSessionUtils.getUserNo();

        userProfile.setUspUserNo(userNo);

        // save the UserProfile object and get the result
        userProfile = userProfileService.saveUserProfile(userProfile);

        if(userProfile==null||userProfile.getUspId()==null){


            // Set the status to failed
            retData.setStatus(APIResponseStatus.failed);

        }else{

            // Get the UserProfile id
            retData.setData(userProfile.getUspId());

            // Set the status to success
            retData.setStatus(APIResponseStatus.success);

        }


        // Log the response
        log.info("saveUserProfile - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }
    @RequestMapping(value = "/api/0.9/json/customer/profile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getUserProfile(){

        // Log the Request
        log.info("UserProfile - Request Received# ");
        log.info("UserProfile -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        Long uspUserNo=authSessionUtils.getUserNo();

        // Variable holding the UserProfile
        UserProfile userProfile=userProfileService.findByUspUserNo(uspUserNo);

        // Convert the UserProfile to UserProfileResource
        UserProfileResource userProfileResource = userProfileAssembler.toResource(userProfile);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the UserProfileResource object
        retData.setData(userProfileResource);

        // Log the response
        log.info("UserProfile - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }
    


}

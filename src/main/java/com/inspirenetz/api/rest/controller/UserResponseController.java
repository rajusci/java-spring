package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;

import com.inspirenetz.api.core.domain.UserResponse;
import com.inspirenetz.api.core.service.UserResponseService;
import com.inspirenetz.api.rest.assembler.UserResponseAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import com.inspirenetz.api.rest.resource.UserResponseResource;
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
 * Created by alameen on 24/11/14.
 */
@Controller
public class UserResponseController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(UserResponseController.class);

    @Autowired
    private UserResponseService userResponseService;

    @Autowired
    private UserResponseAssembler userResponseAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/promotionresponse/{urpresponseitemid}/{responseType}/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listUserResponse(@PathVariable(value ="urpresponseitemid") Long  urpResponseItemId,@PathVariable("responseType") Integer  urpResponseType,@PathVariable("filter") String filter,@PathVariable("query") String query,
                                              Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("UserResponse - Request Received# promotionId and response type "+ urpResponseItemId+":and:"+urpResponseType);
        log.info("UserResponse - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Variable holding the UserResponse
        List<UserResponseResource> userResponseResourceList = new ArrayList<>(0);

        Page<UserResponse> userResponsePage=userResponseService.findByUrpResponseItemIdAndUrpResponseType(filter,query,urpResponseItemId, urpResponseType, pageable);

        userResponseResourceList=userResponseAssembler.toResources(userResponsePage);

        // Set the data
        retData.setData(userResponseResourceList);

        // Log the response
        log.info("User Response - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    
}

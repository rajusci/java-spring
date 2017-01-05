package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.core.domain.Notification;
import com.inspirenetz.api.core.service.CustomerActivityService;
import com.inspirenetz.api.core.service.NotificationService;
import com.inspirenetz.api.rest.assembler.CustomerActivityAssembler;
import com.inspirenetz.api.rest.assembler.NotificationAssembler;
import com.inspirenetz.api.rest.resource.CustomerActivityResource;
import com.inspirenetz.api.rest.resource.NotificationResource;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fayizkci on 17/3/15.
 *
 */
@Controller
public class NotificationController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationAssembler notificationAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/customer/notifications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getNotifications(@RequestParam(value ="ntfType") Integer ntfType,
                                              @RequestParam(value ="merchantNo",required = false,defaultValue = "0l") Long merchantNo,
                                              @RequestParam(value ="ntfStatus") Integer ntfStatus){

        //get userType
        Integer userType=authSessionUtils.getUserType();

        //get userNo
        Long userNo=authSessionUtils.getUserNo();

        // Log the Request
        log.info("getNotifications - Request Received # userType :" + userType + " # userNo : "+userNo+" # ntfType : "+ntfType+" #merchantNo : "+merchantNo+" #ntfStatus : "+ntfStatus);

        log.info("getNotifications - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();



        // Get the Notifications
        List<Notification> notificationList = notificationService.getNotifications(userType, userNo, ntfType, merchantNo, ntfStatus);


        // Convert to reosurce
        List<NotificationResource> notificationResourceList =  notificationAssembler.toResources(notificationList);

        // Set the data
        retData.setData(notificationResourceList);

        //Set Status
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getNotifications - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/notifications/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getNotificationCount(
                                              @RequestParam(value ="ntfType") Integer ntfType,
                                              @RequestParam(value ="merchantNo",required = false,defaultValue = "0") Long merchantNo,
                                              @RequestParam(value ="ntfStatus") Integer ntfStatus){

        String loyaltyId = authSessionUtils.getUserLoginId();

        //get userType
        Integer userType=authSessionUtils.getUserType();

        //get userNo
        Long userNo=authSessionUtils.getUserNo();

        // Log the Request
        log.info("getNotificationCount - Request Received # userType :" + userType + " # userNo : "+userNo+" # ntfType : "+ntfType+" #merchantNo : "+merchantNo+" #ntfStatus : "+ntfStatus);

        log.info("getNotificationCount - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the Notification Count
        Integer notificationCount = notificationService.getNotificationsCount(userType, userNo, ntfType, merchantNo, ntfStatus);

        // Set the data
        retData.setData(notificationCount);

        //Set Status
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getNotificationCount - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }



}
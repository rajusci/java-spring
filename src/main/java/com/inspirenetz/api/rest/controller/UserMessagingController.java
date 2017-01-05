package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.Notification;
import com.inspirenetz.api.core.service.NotificationService;
import com.inspirenetz.api.core.service.UserMessagingService;
import com.inspirenetz.api.rest.assembler.NotificationAssembler;
import com.inspirenetz.api.rest.resource.NotificationResource;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by fayizkci on 17/3/15.
 *
 */
@Controller
public class UserMessagingController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(UserMessagingController.class);

    @Autowired
    private UserMessagingService userMessagingService;


    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;



    @RequestMapping(value = "/api/0.9/json/userMessaging/sendNotification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject sendNotification(
            @RequestParam(value ="channel",required = true) String channel,
            @RequestParam(value ="to",required = true) String to,
            @RequestParam(value ="subject",required = false,defaultValue = "") String subject,
            @RequestParam(value ="message",required = true) String message){


        //get userNo
        Long merchantNo=authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("sendNotification - Request Received # channel :" + channel + " # to : "+to+" # subject : "+subject+" #merchantNo : "+merchantNo+" #message : "+message);

        log.info("sendNotification - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the Notification Count
        boolean isMessageSend = userMessagingService.transmitBulkNotification(Integer.parseInt((channel!=null && !channel.equals(""))?channel:"0"), to, subject, merchantNo, message);

        // Set the data
        retData.setData(isMessageSend);

        //Set Status
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("sendNotification - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }



}
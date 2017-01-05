package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.NotificationCampaign;
import com.inspirenetz.api.core.service.NotificationCampaignService;
import com.inspirenetz.api.rest.assembler.NotificationCampaignAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.NotificationCampaignResource;
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

import java.util.List;

/**
 * Created by saneeshci on 25/9/14.
 *
 */
@Controller
public class NotificationCampaignController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(NotificationCampaignController.class);

    @Autowired
    private NotificationCampaignService notificationCampaignService;

    @Autowired
    private NotificationCampaignAssembler notificationCampaignAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/notification/campaign/{ntcId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject retrieveNotificationCampaigns(@PathVariable(value ="ntcId") Long ntcId) throws InspireNetzException {


        // Log the Request
        log.info("retrieveNotificationCampaigns - Request Received# Notifcation Id :" + ntcId);
        log.info("retrieveNotificationCampaigns - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the NotificationCampaign
        NotificationCampaign notificationCampaign = notificationCampaignService.findByNtcId(ntcId);

        // Convert to reosurce
        NotificationCampaignResource notificationCampaignResource = notificationCampaignAssembler.toResource(notificationCampaign);

        // Set the data
        retData.setData(notificationCampaignResource);

        // Log the response
        log.info("retrieveNotificationCampaigns -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }
    @RequestMapping(value = "/api/0.9/json/merchant/notification/campaign/search/{searchField}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject searchNotificationCampaigns(
            @PathVariable(value = "searchField") String searchField,
            @PathVariable(value = "query") String query,
            Pageable pageable
    ) throws InspireNetzException{


        // Log the Request
        log.info("searchNotificationCampaigns - Request Received# search field :" + searchField+" query :"+query);
        log.info("searchNotificationCampaigns - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the NotificationCampaign
        Page<NotificationCampaign> notificationCampaigns = notificationCampaignService.searchNotificationCampaigns(searchField,query,pageable);

        // Convert to reosurce
        List<NotificationCampaignResource> notificationCampaignResources = notificationCampaignAssembler.toResources(notificationCampaigns);

        // Set the pageable params to the retData
        retData.setPageableParams(notificationCampaigns);


        // Set the data
        retData.setData(notificationCampaignResources);
        // Set the status to success
        retData.setStatus(APIResponseStatus.success);


        // Log the response
        log.info("searchNotificationCampaigns -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/notification/campaign/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveNotificationCampaign(NotificationCampaignResource notificationCampaignResource) throws InspireNetzException {


        // Log the Request
        log.info("saveNotificationCampaign - Request Received# Notification Campaign :" + notificationCampaignResource);

        log.info("saveNotificationCampaign - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        notificationCampaignResource.setNtcMerchantNo(authSessionUtils.getMerchantNo());

        // Get the NotificationCampaign
        NotificationCampaign notificationCampaign = notificationCampaignService.validateAndSaveNotificationCampaign(notificationCampaignResource);


        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(notificationCampaign.getNtcId());

        // Log the response
        log.info("saveNotificationCampaign -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/notification/campaign/delete/{ntcId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteNotificationCampaign(@PathVariable(value="ntcId") Long ntcId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteNotificationCampaign - Request Received# Campaign ID: "+ntcId);
        log.info("deleteNotificationCampaign -  "+generalUtils.getLogTextForRequest());

        // Get the brand information
        boolean isDeleted=notificationCampaignService.deleteNotificationCampaign(ntcId);


        if(isDeleted){

            // Set the retData to success
            retData.setStatus(APIResponseStatus.success);

            // Set the data to the delete brnId
            retData.setData(ntcId);

        }else{
            // Set the retData to success
            retData.setStatus(APIResponseStatus.failed);

            // Set the data to the delete brnId
            retData.setData(ntcId);
        }


        // Log the response
        log.info("deleteNotificationCampaign - "+generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }




}
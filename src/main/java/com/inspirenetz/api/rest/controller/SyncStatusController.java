package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.TransactionType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.assembler.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.*;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by fayizkci on 14/9/15.
 *
 */
@Controller
public class SyncStatusController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(SyncStatusController.class);

    @Autowired
    private SyncStatusService syncStatusService;

    @Autowired
    private SyncStatusAssembler syncStatusAssembler;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/admin/sync/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listSyncStatus(
                                                @RequestParam(value = "sysMerchantNo",defaultValue = "0") Long merchantNo,
                                                @RequestParam(value = "sysLocation",defaultValue = "0") Long location,
                                                @RequestParam(value = "sysDate",required = false) Date date

    ){


       log.info("listSyncStatus - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Check if the date is set or not
        // If the  date is not set, then we need to set the date to today
        if ( date == null  ){

            // Get the today Date
           date = new Date(new java.util.Date().getTime());

        }

        // Log the Request
        log.info("listSyncStatus - Request Received# merchant : "+merchantNo+" #location : "+location+" #date : "+date.toString());


        // Call the get sync status method
        List<Map<String,Object>> syncStatusData = syncStatusService.listSyncStatus(merchantNo,location,date);

        retData.setData(syncStatusData);

        // set the pageable params
        retData.setTotalElements(Long.parseLong(syncStatusData.size()+""));

        // Set the retData object status to success
        retData.setStatus(APIResponseStatus.success);

        // Log the information
        log.info("listSyncStatus - " + generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;
    }

    @RequestMapping(value = "/api/0.9/json/admin/sync/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listSyncStatusInfo(
            @RequestParam(value = "sysMerchantNo") Long merchantNo,
            @RequestParam(value = "sysLocation") Long location,
            @RequestParam(value = "sysDate") Date date,
            @RequestParam(value = "sysType") Integer type

    ){


        log.info("listSyncStatusInfo - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("listSyncStatusInfo - Request Received# merchant : "+merchantNo+" #location : "+location+" #type : "+type+" #date : "+date.toString());


        // Call the get sync status method
        List<SyncStatus> syncStatuses = syncStatusService.findBySysMerchantNoAndSysLocationAndSysTypeAndSysDate(merchantNo, location, type, date);

        List<SyncStatusResource>syncStatusResources=syncStatusAssembler.toResources(syncStatuses);

        retData.setData(syncStatusResources);

        // Set the retData object status to success
        retData.setStatus(APIResponseStatus.success);

        // Log the information
        log.info("listSyncStatusInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;
    }

    @RequestMapping(value = "/api/0.9/json/merchant/sync/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantSyncStatus(
            @RequestParam(value = "sysLocation",defaultValue = "0") Long location,
            @RequestParam(value = "sysDate",required = false) Date date

    ){


        log.info("listSyncStatus - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Check if the date is set or not
        // If the  date is not set, then we need to set the date to today
        if ( date == null  ){

            // Get the today Date
            date = new Date(new java.util.Date().getTime());

        }

        Long merchantNo=authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("listSyncStatus - Request Received# merchant : "+merchantNo+" #location : "+location+" #date : "+date.toString());


        // Call the get sync status method
        List<Map<String,Object>> syncStatusData = syncStatusService.listSyncStatus(merchantNo,location,date);

        retData.setData(syncStatusData);

        // set the pageable params
        retData.setTotalElements(Long.parseLong(syncStatusData.size()+""));

        // Set the retData object status to success
        retData.setStatus(APIResponseStatus.success);

        // Log the information
        log.info("listSyncStatus - " + generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;
    }



    @RequestMapping(value = "/api/0.9/json/merchant/sync/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantSyncStatusInfo(
            @RequestParam(value = "sysLocation") Long location,
            @RequestParam(value = "sysDate") Date date,
            @RequestParam(value = "sysType") Integer type

    ){


        log.info("listSyncStatusInfo - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        Long merchantNo=authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("listSyncStatusInfo - Request Received# merchant : "+merchantNo+" #location : "+location+" #type : "+type+" #date : "+date.toString());


        // Call the get sync status method
        List<SyncStatus> syncStatuses = syncStatusService.findBySysMerchantNoAndSysLocationAndSysTypeAndSysDate(merchantNo, location, type, date);

        List<SyncStatusResource>syncStatusResources=syncStatusAssembler.toResources(syncStatuses);

        retData.setData(syncStatusResources);

        // Set the retData object status to success
        retData.setStatus(APIResponseStatus.success);

        // Log the information
        log.info("listSyncStatusInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;
    }


}

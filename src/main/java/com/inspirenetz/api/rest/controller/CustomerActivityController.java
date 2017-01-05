package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.core.service.CustomerActivityService;
import com.inspirenetz.api.rest.assembler.CustomerActivityAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerActivityResource;
import com.inspirenetz.api.util.*;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by saneeshci on 25/9/14.
 *
 */
@Controller
public class CustomerActivityController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CustomerActivityController.class);

    @Autowired
    private CustomerActivityService customerActivityService;

    @Autowired
    private CustomerActivityAssembler customerActivityAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/customer/activities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject searchActivities(@RequestParam(value ="loyaltyId") String  loyaltyId,
                                              @RequestParam(value ="type") Integer type,
                                              @RequestParam(value ="fromDate") Date fromDate,
                                              @RequestParam(value ="endDate") Date endDate,
                                              Pageable pageable){


        // Log the Request
        log.info("searchActivities - Request Received# Loyalty ID :" + loyaltyId + " #type : "+type+" # FromDate : "+fromDate+" #ToDate : "+endDate);
        log.info("searchActivities - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //get the merchant no
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the CustomerActivity
        Page<CustomerActivity> customerActivityPage = customerActivityService.searchCustomerActivities(loyaltyId,type,fromDate,endDate,merchantNo,pageable);

        // Convert to reosurce
        List<CustomerActivityResource> customerActivityResourceList =  customerActivityAssembler.toResources(customerActivityPage);

        // Set the data
        retData.setData(customerActivityResourceList);

        // Log the response
        log.info("searchActivities - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }





    @RequestMapping(value = "/api/0.9/json/customer/activities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerRecentActivities(@RequestParam(value ="merchant_no",required = false,defaultValue = "0") Long  merchantNo){

        String userLoginId = authSessionUtils.getUserLoginId();

        // Log the Request
        log.info("getCustomerRecentActivities - Request Received");
        log.info("getCustomerRecentActivities - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the CustomerActivity
        List<Map<String, String >> customerActivityPage = customerActivityService.getCustomerRecentActivities(userLoginId, merchantNo);

        // Set the data
        retData.setData(customerActivityPage);

        // Log the response
        log.info("getCustomerRecentActivities - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/user/activities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject searchUserActivities(@RequestParam(value ="merchantNo",defaultValue = "0") Long merchantNo,
                                              @RequestParam(value ="type",defaultValue = "0") Integer type,
                                              @RequestParam(value ="fromDate",required = false) Date fromDate,
                                              @RequestParam(value ="endDate",required = false) Date endDate,
                                              @RequestParam(value = "cuaMerchantNo",defaultValue = "0") Long cuaMerchantNo,
                                              Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("searchUserActivities - Request Received# merchantNo :" + merchantNo + " #type : "+type+" # FromDate : "+fromDate+" #ToDate : "+endDate);
        log.info("searchUserActivities - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // If the startDate is null, then  set the date to 0000-00-00
        if ( fromDate == null ) {

            fromDate = DBUtils.covertToSqlDate("0000-00-00");

        }

        // If the endDate is null, then  set the date to 9999-12-31
        if ( endDate == null ) {

            endDate = DBUtils.covertToSqlDate("9999-12-31");

        }

        // Get the CustomerActivity
        Page<CustomerActivity> customerActivityPage = customerActivityService.searchUserActivity(merchantNo, type, fromDate, endDate, cuaMerchantNo, pageable);

        // Convert to reosurce
        List<CustomerActivityResource> customerActivityResourceList =  customerActivityAssembler.toResources(customerActivityPage);

        retData.setPageableParams(customerActivityPage);

        // Set the data
        retData.setData(customerActivityResourceList);



        // Log the response
        log.info("searchUserActivities - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


}
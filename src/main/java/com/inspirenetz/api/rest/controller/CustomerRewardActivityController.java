package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerRewardActivity;
import com.inspirenetz.api.core.service.CustomerRewardActivityService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.rest.assembler.CustomerRewardActivityAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerRewardActivityResource;
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

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class CustomerRewardActivityController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CustomerRewardActivityController.class);

    @Autowired
    private CustomerRewardActivityService customerRewardActivityService;

    @Autowired
    private CustomerRewardActivityAssembler customerRewardActivityAssembler;

    @Autowired
    private Mapper mapper;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    CustomerService customerService;


    @RequestMapping(value = "/api/0.9/json/merchant/customerrewards/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveCustomerRewardActivity( @RequestParam (value = "craCustomerNo") Long craCustomerNo,
                                                         @RequestParam (value = "craType") Integer craType,
                                                         @RequestParam (value = "craActivityRef") String craActivityRef) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveCustomerRewardActivity - Request Received# CustomerNo:" + craCustomerNo + " CraType: " + craType + " CraActivityRef:" + craActivityRef);
        log.info("saveCustomerRewardActivity -  "+generalUtils.getLogTextForRequest());

        CustomerRewardActivity customerRewardActivity = new CustomerRewardActivity();

        // save the customerRewardActivity object and get the result
        customerRewardActivity = customerRewardActivityService.validateAndRegisterCustomerRewardActivity(craCustomerNo,craType,craActivityRef);

        // Get the customerRewardActivity id
        retData.setData(customerRewardActivity.getCraId());

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("RegisterCustomerRewardActivity -  "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/customerrewards/{customerno}/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerRewardActivitiesByCustomerNo(@PathVariable(value ="customerno") Long customerNo,
                                                                     @PathVariable(value ="type") Integer type,
                                        Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("getCustomerRewardActivitiesByCustomerNo - Request Received# filter "+ customerNo );
        log.info("getCustomerRewardActivitiesByCustomerNo -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the CustomerRewardActivity
        Page<CustomerRewardActivity> customerRewardActivityPage = customerRewardActivityService.findByCraCustomerNoAndCraType(customerNo,type, pageable);

        // Convert to reosurce
        List<CustomerRewardActivityResource> customerRewardActivityResourceList =  customerRewardActivityAssembler.toResources(customerRewardActivityPage);

        // Set the data
        retData.setData(customerRewardActivityResourceList);


        // Log the response
        log.info("getCustomerRewardActivitiesByCustomerNo -  "+generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/customerrewards/{customerno}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerRewardActivitiesByCustomerNoAndType(@PathVariable(value ="customerno") Long customerNo,
                                                                     Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("getCustomerRewardActivitiesByCustomerNoAndType - Request Received# filter "+ customerNo );
        log.info("getCustomerRewardActivitiesByCustomerNoAndType -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the CustomerRewardActivity
        Page<CustomerRewardActivity> customerRewardActivityPage = customerRewardActivityService.findByCraCustomerNo(customerNo, pageable);

        // Convert to reosurce
        List<CustomerRewardActivityResource> customerRewardActivityResourceList =  customerRewardActivityAssembler.toResources(customerRewardActivityPage);

        // Set the data
        retData.setData(customerRewardActivityResourceList);


        // Log the response
        log.info("getCustomerRewardActivitiesByCustomerNoAndType -  "+generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/customerrewards/info/{craId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerRewardActivityInfo(@PathVariable(value="craId") Long craId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getCustomerRewardActivityInfo - Request Received# CustomerRewardActivity ID: "+craId);
        log.info("getCustomerRewardActivityInfo -  "+generalUtils.getLogTextForRequest());



        // Get the CustomerRewardActivity
        CustomerRewardActivity customerRewardActivity = customerRewardActivityService.findByCraId(craId);

        // Convert the CustomerRewardActivity to CustomerRewardActivityResource
        CustomerRewardActivityResource customerRewardActivityResource = customerRewardActivityAssembler.toResource(customerRewardActivity);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the customerRewardActivityResource object
        retData.setData(customerRewardActivityResource);




        // Log the response
        log.info("getCustomerRewardActivityInfo - "+generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/customerreward/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveCustomerRewardActivityByLoyalty( @RequestParam (value = "craLoyaltyId") String craLoyaltyId,
                                                                   @RequestParam (value = "craType") Integer craType,
                                                                    @RequestParam (value = "craActivityRef") String craActivityRef) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveCustomerRewardActivity - Request Received# CustomerLoyaltyId:" + craLoyaltyId + " CraType: " + craType + " CraActivityRef:" + craActivityRef);
        log.info("saveCustomerRewardActivity -  "+generalUtils.getLogTextForRequest());

        CustomerRewardActivity customerRewardActivity = new CustomerRewardActivity();

        //fetch merchant number and customer number
        Long merchantNo =authSessionUtils.getMerchantNo();

        // save the customerRewardActivity object and get the result
        customerRewardActivity = customerRewardActivityService.saveCustomerRewardActivityByLoyaltyId(craLoyaltyId,craType,craActivityRef,merchantNo);

        // Get the customerRewardActivity id
        retData.setData(customerRewardActivity.getCraId());

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("RegisterCustomerRewardActivity -  "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }



}

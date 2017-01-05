package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.CustomerSubscription;
import com.inspirenetz.api.core.service.CustomerSubscriptionService;
import com.inspirenetz.api.rest.assembler.CustomerSubscriptionAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerSubscriptionResource;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class CustomerSubscriptionController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CustomerSubscriptionController.class);

    @Autowired
    private CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    private CustomerSubscriptionAssembler customerSubscriptionAssembler;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;



    @RequestMapping(value = "/api/0.9/json/merchant/customer/subscription", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject addCustomerSubscription(CustomerSubscriptionResource customerSubscriptionResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Create the CustomerSubscription object from the resource
        CustomerSubscription customerSubscription = mapper.map(customerSubscriptionResource,CustomerSubscription.class);

        // Log the Request
        log.info("saveCustomerSubscription - Request Received# "+customerSubscription.toString());
        log.info("saveCustomerSubscription -  "+generalUtils.getLogTextForRequest());



        // save the customerSubscription object and get the result
        customerSubscription = customerSubscriptionService.addCustomerSubscription(customerSubscription);

        // Get the customerSubscription id
        retData.setData(customerSubscription.getCsuId());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);



        // Log the response
        log.info("saveCustomerSubscription -" + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseObject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/customer/subscription/delete/{csuId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteCustomerSubscription(@PathVariable(value="csuId") Long csuId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("deleteCustomerSubscription - Request Received# CustomerSubscription ID: "+csuId);
        log.info("deleteCustomerSubscription -  "+generalUtils.getLogTextForRequest());

        // Get the customerSubscription information
        customerSubscriptionService.removeCustomerSubscription(csuId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete csuId
        retData.setData(csuId);


        // Log the response
        log.info("deleteCustomerSubscription -" + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = {"/api/0.9/json/customer/subscriptions/{csuCustomerNo}","/api/0.9/json/customer/subscriptions/{csuCustomerNo}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCustomerSubscriptions(@PathVariable(value = "csuCustomerNo") Long csuCustomerNo) throws InspireNetzException {

        // Log the Request
        log.info("listCustomerSubscriptions - Request Received# ");
        log.info("listCustomerSubscriptions -  "+generalUtils.getLogTextForRequest());



        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the customers
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionService.listSubscriptionsForCustomer(csuCustomerNo);

        // Convert the list to resource
        List<CustomerSubscriptionResource> customerSubscriptionResourceList = customerSubscriptionAssembler.toResources(customerSubscriptionList);

        // Set the data
        retData.setData(customerSubscriptionResourceList);


        // Log the response
        log.info("listCustomerSubscriptions - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/customer/subscription/{csuId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerSubscriptionInfo(@PathVariable(value="csuId") Long csuId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();



        // Log the Request
        log.info("getCustomerSubscriptionInfo - Request Received# CustomerSubscription ID: "+csuId);
        log.info("getCustomerSubscriptionInfo -  "+generalUtils.getLogTextForRequest());



        // Get the customerSubscription object
        CustomerSubscription customerSubscription = customerSubscriptionService.getCustomerSubscription(csuId);

        // Convert the CustomerSubscription to CustomerSubscriptionResource
        CustomerSubscriptionResource customerSubscriptionResource = customerSubscriptionAssembler.toResource(customerSubscription);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the customerSubscriptionResource object
        retData.setData(customerSubscriptionResource);



        // Log the response
        log.info("getCustomerSubscriptionInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }
}

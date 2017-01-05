package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.CustomerPromotionalEventWrapper;
import com.inspirenetz.api.core.domain.AccountBundlingSetting;
import com.inspirenetz.api.core.domain.CustomerPromotionalEvent;
import com.inspirenetz.api.core.service.CustomerPromotionalEventService;
import com.inspirenetz.api.rest.assembler.CustomerPromotionalEventAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.AccountBundlingSettingResource;
import com.inspirenetz.api.rest.resource.CustomerPromotionalEventResource;
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
 * Created by saneeshci on 25/9/14.
 *
 */
@Controller
public class CustomerPromotionalEventController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CustomerPromotionalEventController.class);

    @Autowired
    private CustomerPromotionalEventService customerPromotionalEventService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private CustomerPromotionalEventAssembler customerPromotionalEventAssembler;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/trigger/milestone", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject triggerRewardingForMerchantEvent(CustomerPromotionalEventResource customerPromotionalEventResource) throws InspireNetzException {


        // Log the Request
        log.info("triggerRewardingForMerchantEvent - Request Received# Draw Type :" + customerPromotionalEventResource);
        log.info("triggerRewardingForMerchantEvent - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        CustomerPromotionalEvent customerPromotionalEvent = mapper.map(customerPromotionalEventResource, CustomerPromotionalEvent.class);

        // Get the CustomerPromotionalEvent
        customerPromotionalEventService.triggerAwardingForCustomerPromotionalEvent(customerPromotionalEvent);

        // Set the data
        log.info("triggerRewardingForMerchantEvent -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/events/completed/{loyaltyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCompletedMilestones(@PathVariable (value = "loyaltyId") String loyaltyId) throws InspireNetzException {


        // Log the Request
        log.info("getCompletedMilestones - Request Received# LoyaltyId :" + loyaltyId);
        log.info("getCompletedMilestones - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Get the LinkRequest
        List<CustomerPromotionalEvent> promotionalEventPage = customerPromotionalEventService.findByCpeLoyaltyIdAndCpeMerchantNo(loyaltyId,merchantNo);

        // Convert to reosurce
        List<CustomerPromotionalEventResource> promotionalEventResources =  customerPromotionalEventAssembler.toResources(promotionalEventPage);

        // Set the data
        retData.setData(promotionalEventResources);

        // Log the response
        log.info("getCompletedMilestones - " + generalUtils.getLogTextForResponse(retData));

        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/trigger/milestonelist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject triggerRewardingList(@RequestBody CustomerPromotionalEventWrapper customerPromotionalEventWrapper) throws InspireNetzException {


        // Log the Request
        log.info("triggerRewardingList - Request Received# Draw Type :" + customerPromotionalEventWrapper);
        log.info("triggerRewardingList - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the CustomerPromotionalEvent
        customerPromotionalEventService.triggerAwardingForCustomerPromotionalEventList(customerPromotionalEventWrapper);

        // Set the data
        log.info("triggerRewardingList -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }



}
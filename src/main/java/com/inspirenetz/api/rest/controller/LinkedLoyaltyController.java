package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.rest.assembler.CustomerAssembler;
import com.inspirenetz.api.rest.assembler.LinkedLoyaltyAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerResource;
import com.inspirenetz.api.rest.resource.LinkedLoyaltyResource;
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
public class LinkedLoyaltyController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(LinkedLoyaltyController.class);

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LinkedLoyaltyAssembler linkedLoyaltyAssembler;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/bundling/linkedloyalty/{loyaltyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listLinkedLoyalty(@PathVariable(value ="loyaltyId") String loyaltyId){


        // Log the Request
        log.info("listLinkedLoyaltys - Request Received# LoyaltyId "+ loyaltyId);
        log.info("listLinkedLoyaltys - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchantNo of the merchant logged in
        Long merchantNo = authSessionUtils.getMerchantNo();



        // Get the list of customer
        List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findLinkedAccounts(loyaltyId,merchantNo);

        // Convert to resource list
        List<LinkedLoyaltyResource> linkedLoyaltyResourceList =  linkedLoyaltyAssembler.toResources(linkedLoyaltyList);

        // Set the data
        retData.setData(linkedLoyaltyResourceList);




        // Log the response
        log.info("listLinkedLoyaltys -  " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/bundling/linkedloyalty/{merchantNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listLinkedLoyalty(@PathVariable(value ="merchantNo") Long merchantNo){


        // Log the Request
        log.info("listLinkedLoyaltys - Request Received# merchant No "+ merchantNo);
        log.info("listLinkedLoyaltys - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the loyalty id and store
        String loyaltyId = AuthSession.getUserLoginId();




        // Get the list of customer
        List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findLinkedAccounts(loyaltyId,merchantNo);

        // Convert to resource list
        List<LinkedLoyaltyResource> linkedLoyaltyResourceList =  linkedLoyaltyAssembler.toResources(linkedLoyaltyList);

        // Set the data
        retData.setData(linkedLoyaltyResourceList);




        // Log the response
        log.info("listLinkedLoyaltys -  " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;

    }

}

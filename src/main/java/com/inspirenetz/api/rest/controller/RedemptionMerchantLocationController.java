package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.RedemptionMerchantLocation;
import com.inspirenetz.api.core.service.RedemptionMerchantLocationService;
import com.inspirenetz.api.rest.assembler.RedemptionMerchantLocationAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.RedemptionMerchantLocationResource;
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
import java.util.Map;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class RedemptionMerchantLocationController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(RedemptionMerchantLocationController.class);

    @Autowired
    private RedemptionMerchantLocationService redemptionMerchantLocationService;

    @Autowired
    private RedemptionMerchantLocationAssembler redemptionMerchantLocationAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/redemptionmerchantlocations/{rmlmerno}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRedemptionMerchantLocations(@PathVariable(value ="rmlmerno") Long merchantNo,
                                              Pageable pageable){


        // Log the Request
        log.info("listRedemptionMerchantLocations - Request Received# Merchant No "+ merchantNo);
        log.info("listRedemptionMerchantLocations -   "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the RedemptionMerchantLocation
        List<RedemptionMerchantLocation> redemptionMerchantLocations = redemptionMerchantLocationService.findByRmlMerNo(merchantNo);

        // Convert to reosurce
        List<RedemptionMerchantLocationResource> redemptionMerchantLocationResourceList =  redemptionMerchantLocationAssembler.toResources(redemptionMerchantLocations);

        // Set the data
        retData.setData(redemptionMerchantLocationResourceList);

        // Log the response
        log.info("listRedemptionMerchantLocations - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


}
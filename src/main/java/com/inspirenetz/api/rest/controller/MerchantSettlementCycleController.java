package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.MerchantSettlementCycle;
import com.inspirenetz.api.core.service.MerchantSettlementCycleService;
import com.inspirenetz.api.rest.assembler.MerchantSettlementCycleAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.MerchantSettlementCycleResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * Created by saneeshci on 21/10/15.
 *
 */
@Controller
public class MerchantSettlementCycleController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(MerchantSettlementCycleController.class);

    @Autowired
    private MerchantSettlementCycleService merchantSettlementCycleService;

    @Autowired
    private MerchantSettlementCycleAssembler merchantSettlementCycleAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/settlement/cycle/search/{mscVendorNo}/{mscLocation}/{fromDate}/{endDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject searchSettlementCycles(@PathVariable(value ="mscVendorNo") Long  mscVendorNo,
                                                         @PathVariable(value ="mscLocation") Long mscLocation,
                                                         @PathVariable(value ="fromDate") Date fromDate,
                                                         @PathVariable(value ="endDate") Date endDate,
                                              Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("searchSettlementCycles - Request Received# Redemption Merchant No :" + mscVendorNo+" Location :"+mscLocation+" Start Date : "+fromDate+" End Date: "+endDate);
        log.info("searchSettlementCycles - "+generalUtils.getLogTextForRequest());



        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        Long merchantNo = authSessionUtils.getMerchantNo();

        if(authSessionUtils.getUserType() == UserType.REDEMPTION_MERCHANT_USER){

            mscVendorNo = authSessionUtils.getCurrentUser().getThirdPartyVendorNo();

            mscLocation = authSessionUtils.getCurrentUser().getUserLocation();
        }

        // Get the MerchantSettlementCycle
        List<MerchantSettlementCycle> merchantSettlementCyclePage = merchantSettlementCycleService.searchMerchantSettlementCycle(merchantNo,mscVendorNo, mscLocation, fromDate, endDate);

        // Convert to reosurce
        List<MerchantSettlementCycleResource> merchantSettlementCycleResourceList =  merchantSettlementCycleAssembler.toResources(merchantSettlementCyclePage);

        // Set the data
        retData.setData(merchantSettlementCycleResourceList);

        // Log the response
        log.info("searchSettlementCycles -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/settlement/settle/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject markCycleAsSettled(@RequestParam(value ="mscId") Long mscId) throws InspireNetzException {


        // Log the Request
        log.info("markCycleAsSettled - Request Received# Received Settlement Cycle :" +mscId);
        log.info("markCycleAsSettled - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the MerchantSettlementCycle
        boolean status  = merchantSettlementCycleService.markCycleAsSettled(mscId);

        // Set the data
        retData.setData(status);

        // Log the response
        log.info("retrieveMerchantSettlementCycles -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/settlement/cycle/get/{mscId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getSettlementCycleInfo(@PathVariable(value ="mscId") Long  mscId) throws InspireNetzException {


        // Log the Request
        log.info("getSettlementCycleInfo - Request Received# MscId :"+mscId);
        log.info("getSettlementCycleInfo - "+generalUtils.getLogTextForRequest());



        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the MerchantSettlementCycle
        MerchantSettlementCycle merchantSettlementCycle = merchantSettlementCycleService.findByMscId(mscId);

        // Convert to reosurce
        MerchantSettlementCycleResource merchantSettlementCycleResource =  merchantSettlementCycleAssembler.toResource(merchantSettlementCycle);

        // Set the data
        retData.setData(merchantSettlementCycleResource);

        // Log the response
        log.info("getSettlementCycleInfo -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }



}
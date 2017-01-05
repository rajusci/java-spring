package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.domain.MerchantSettlement;
import com.inspirenetz.api.core.service.MerchantSettlementService;
import com.inspirenetz.api.rest.assembler.MerchantSettlementAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.MerchantSettlementResource;
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
 * Created by saneeshci on 25/9/14.
 *
 */
@Controller
public class MerchantSettlementController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(MerchantSettlementController.class);

    @Autowired
    private MerchantSettlementService merchantSettlementService;

    @Autowired
    private MerchantSettlementAssembler merchantSettlementAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/settlement/search/{mesVendorNo}/{mesLocation}/{fromDate}/{endDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject retrieveMerchantSettlements(@PathVariable(value ="mesVendorNo") Long  mesVendorNo,
                                                         @PathVariable(value ="mesLocation") Long mesLocation,
                                                         @PathVariable(value ="fromDate") Date fromDate,
                                                         @PathVariable(value ="endDate") Date endDate,
                                              Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("retrieveMerchantSettlements - Request Received# Redemption Merchant No :" + mesVendorNo+" Location :"+mesLocation+" Start Date : "+fromDate+" End Date: "+endDate);
        log.info("retrieveMerchantSettlements - "+generalUtils.getLogTextForRequest());



        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the MerchantSettlement
        List<MerchantSettlement> merchantSettlementPage = merchantSettlementService.searchSettlements(mesVendorNo, mesLocation, fromDate, endDate);

        // Convert to reosurce
        List<MerchantSettlementResource> merchantSettlementResourceList =  merchantSettlementAssembler.toResources(merchantSettlementPage);

        // Set the data
        retData.setData(merchantSettlementResourceList);

        // Log the response
        log.info("retrieveMerchantSettlements -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/settlement/settle/1", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject doSettlement(@RequestParam(value ="settlements") String settlements) throws InspireNetzException {


        // Log the Request
        log.info("doSettlement - Request Received# Received settlement ID's :" +settlements);
        log.info("doSettlement - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the MerchantSettlement
        List<MerchantSettlement> merchantSettlementPage = merchantSettlementService.makeSettlement(settlements);

        // Convert to reosurce
        List<MerchantSettlementResource> merchantSettlementResourceList =  merchantSettlementAssembler.toResources(merchantSettlementPage);

        // Set the data
        retData.setData(merchantSettlementResourceList);

        // Log the response
        log.info("retrieveMerchantSettlements -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }




}
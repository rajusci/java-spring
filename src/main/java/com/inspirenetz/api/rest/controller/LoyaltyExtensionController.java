package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.LoyaltyExtension;
import com.inspirenetz.api.core.service.LoyaltyExtensionService;
import com.inspirenetz.api.rest.assembler.LoyaltyExtensionAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.LoyaltyExtensionResource;
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
public class LoyaltyExtensionController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(LoyaltyExtensionController.class);

    @Autowired
    private LoyaltyExtensionService loyaltyExtensionService;

    @Autowired
    private LoyaltyExtensionAssembler loyaltyExtensionAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/merchant/loyalty/extension", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveLoyaltyExtension(LoyaltyExtensionResource loyaltyExtensionResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveLoyaltyExtension - Request Received# "+loyaltyExtensionResource.toString());
        log.info("saveLoyaltyExtension - "+generalUtils.getLogTextForRequest());

        // convert the LoyaltyExtensionResource to object
        LoyaltyExtension loyaltyExtension = mapper.map(loyaltyExtensionResource,LoyaltyExtension.class);

        // save the loyaltyExtension object and get the result
        loyaltyExtension = loyaltyExtensionService.validateAndSaveLoyaltyExtension(loyaltyExtension);

        // Get the loyaltyExtension id
        retData.setData(loyaltyExtension.getLexId());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);




        // Log the response
        log.info("saveLoyaltyExtension -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/loyalty/extension/delete/{lexId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteLoyaltyExtension(@PathVariable(value="lexId") Long lexId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("deleteLoyaltyExtension - Request Received# LoyaltyExtension ID: "+lexId);
        log.info("deleteLoyaltyExtension - "+generalUtils.getLogTextForRequest());



        // Delete the loyaltyExtension and set the retData fields
        loyaltyExtensionService.validateAndDeleteLoyaltyExtension(lexId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete lrqId
        retData.setData(lexId);



        // Log the response
        log.info("deleteLoyaltyExtension -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/loyalty/extensions/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listLoyaltyExtensions(@PathVariable(value ="filter") String filter,
                                              @PathVariable(value ="query") String query,
                                              Pageable pageable){


        // Log the Request
        log.info("listLoyaltyExtensions - Request Received# filter "+ filter +" query :" +query );
        log.info("listLoyaltyExtensions - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the LoyaltyExtension
        Page<LoyaltyExtension> loyaltyExtensionPage = loyaltyExtensionService.searchLoyaltyExtensions(filter,query,pageable);

        // Convert to reosurce
        List<LoyaltyExtensionResource> loyaltyExtensionResourceList =  loyaltyExtensionAssembler.toResources(loyaltyExtensionPage);

        // Set the data
        retData.setData(loyaltyExtensionResourceList);


        // Log the response
        log.info("listLoyaltyExtensions - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/loyalty/extension/{lexId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getLoyaltyExtensionInfo(@PathVariable(value="lexId") Long lexId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getLoyaltyExtensionInfo - "+generalUtils.getLogTextForRequest());

        // Get the LoyaltyExtension
        LoyaltyExtension loyaltyExtension = loyaltyExtensionService.getLoyaltyExtensionInfo(lexId);

        // Convert the LoyaltyExtension to LoyaltyExtensionResource
        LoyaltyExtensionResource loyaltyExtensionResource = loyaltyExtensionAssembler.toResource(loyaltyExtension);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the loyaltyExtensionResource object
        retData.setData(loyaltyExtensionResource);

        // Log the response
        log.info("getLoyaltyExtensionInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }





}
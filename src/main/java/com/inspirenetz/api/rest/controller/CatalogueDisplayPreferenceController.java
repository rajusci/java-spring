package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain. CatalogueDisplayPreference;
import com.inspirenetz.api.core.service. CatalogueDisplayPreferenceService;
import com.inspirenetz.api.rest.assembler. CatalogueDisplayPreferenceAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource. CatalogueDisplayPreferenceResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by saneeshci on 25/9/14.
 *
 */
@Controller
public class CatalogueDisplayPreferenceController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CatalogueDisplayPreferenceController.class);

    @Autowired
    private  CatalogueDisplayPreferenceService  catalogueDisplayPreferenceService;

    @Autowired
    private  CatalogueDisplayPreferenceAssembler  catalogueDisplayPreferenceAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/customer/catalogue/preference/{merchantNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCatalogueDisplayPreference(@PathVariable(value ="merchantNo") Long merchantNo
                                              ) throws InspireNetzException {


        // Log the Request
        log.info("getCatalogueDisplayPreference - Request Received# Merchant No :" +merchantNo );
        log.info("getCatalogueDisplayPreference - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the  CatalogueDisplayPreference
        CatalogueDisplayPreference catalogueDisplayPreference =  catalogueDisplayPreferenceService.getCatalogueDisplayPreference(merchantNo);

        CatalogueDisplayPreferenceResource  catalogueDisplayPreferenceResource =   catalogueDisplayPreferenceAssembler.toResource(catalogueDisplayPreference);

        // Set the data
        retData.setData( catalogueDisplayPreferenceResource);

        // Log the response
        log.info("getCatalogueDisplayPreference - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/cataloguedisplay/preferences", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveCatalogueDisplayPreference(CatalogueDisplayPreferenceResource catalogueDisplayPreferenceResource) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        Long merchantNo = authSessionUtils.getMerchantNo();

        catalogueDisplayPreferenceResource.setCdpMerchantNo(merchantNo);
        // Log the Request
        log.info("saveCatalogueDisplayPreference - "+generalUtils.getLogTextForRequest());
        log.info("saveCatalogueDisplayPreference : Request : "+ catalogueDisplayPreferenceResource);

        // convert the Messsage spiel  to object
        CatalogueDisplayPreference catalogueDisplayPreference = mapper.map(catalogueDisplayPreferenceResource,CatalogueDisplayPreference.class);


        catalogueDisplayPreference =  catalogueDisplayPreferenceService.validateAndSaveCatalogueDisplayPreference(catalogueDisplayPreference);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the roleResource object
        retData.setData( catalogueDisplayPreferenceResource);

        // Log the response
        log.info("get CatalogueDisplayPreferenceInfo - "+generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/catalogue/preference", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCatalogueDisplayPreferenceForMerchant() throws InspireNetzException {

        Long merchantNo = authSessionUtils.getMerchantNo();
        // Log the Request
        log.info("getCatalogueDisplayPreferenceForMerchant - Request Received# Merchant No :" +merchantNo );
        log.info("getCatalogueDisplayPreferenceForMerchant -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the  CatalogueDisplayPreference
        CatalogueDisplayPreference catalogueDisplayPreference =  catalogueDisplayPreferenceService.getCatalogueDisplayPreference(merchantNo);

        CatalogueDisplayPreferenceResource  catalogueDisplayPreferenceResource =   catalogueDisplayPreferenceAssembler.toResource(catalogueDisplayPreference);

        // Set the data
        retData.setData( catalogueDisplayPreferenceResource);

        // Log the response
        log.info("getCatalogueDisplayPreference - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/user/catalogue/preference/{merchantNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getUserCatalogueDisplayPreference(@PathVariable(value ="merchantNo") Long merchantNo

    ) throws InspireNetzException {


        // Log the Request
        log.info("getDefaultCatalogueDisplayPreference - Request Received" );

        //log.info("getDefaultCatalogueDisplayPreference - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the  CatalogueDisplayPreference
        CatalogueDisplayPreference catalogueDisplayPreference =  catalogueDisplayPreferenceService.getUserCatalogueDisplayPreference(merchantNo);

        CatalogueDisplayPreferenceResource  catalogueDisplayPreferenceResource =   catalogueDisplayPreferenceAssembler.toResource(catalogueDisplayPreference);

        // Set the data
        retData.setData( catalogueDisplayPreferenceResource);

        // Log the response
        //log.info("getDefaultCatalogueDisplayPreference - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

}
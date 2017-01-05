package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.CodedValueMap;
import com.inspirenetz.api.core.domain.CodedValue;
import com.inspirenetz.api.core.service.CodedValueService;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class CodedValueController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CodedValueController.class);

    @Autowired
    private CodedValueService codedValueService;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/codedvalues/{cdvIndex}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public APIResponseObject getCodedValuesByIndex(@PathVariable(value = "cdvIndex") Integer cdvIndex){


        // Log the Request
        log.info("searchByCodedValueName - Request Received# "+cdvIndex);
        log.info("searchByCodedValueName - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching codedValues
        HashMap<Integer,String> codedValueMap = codedValueService.getCodedValueMapForIndex(cdvIndex);

        // Set the data
        retData.setData(codedValueMap);

        // Log the response
        log.info("searchByCodedValueName - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/codedvalues/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public APIResponseObject getCodedValuesMap() {


        // Log the Request
        log.info("getCodedValuesMap - Request Received# ");
        log.info("getCodedValuesMap - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching codedValues
        HashMap<Integer,List<CodedValueMap>> codedValueMap = codedValueService.getCodedValueMap();

        // Set the data
        retData.setData(codedValueMap);

        // Log the response
        log.info("getCodedValuesMap - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "api/0.9/json/customer/compatible/categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public APIResponseObject getCompatibleCategory(@RequestParam(value="merchant_no",defaultValue = "1") Long merchantNo) {

        // Log the Request
        log.info("getCompatibleCategory - Request Received# ");
        log.info("getCompatibleCategory - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get compatable format
        List<CodedValue> codedValueList =codedValueService.getCompatableCdvIndex();


        // Set the data
        retData.setData(codedValueList);

        // Log the response
        log.info("getCompatibleCategory - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/codedvalues/map/{cdvIndex}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public APIResponseObject getCodedValuesMapByIndex(@PathVariable(value = "cdvIndex") Integer cdvIndex) {


        // Log the Request
        log.info("getCodedValuesMap - Request Received# ");
        //log.info("getCodedValuesMap - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching codedValues
        HashMap<Integer,List<CodedValueMap>> codedValueMap = codedValueService.getCodedValueMapByIndex(cdvIndex);

        // Set the data
        retData.setData(codedValueMap);

        // Log the response
        //log.info("getCodedValuesMap - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


}

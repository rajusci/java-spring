package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.MerchantModule;
import com.inspirenetz.api.core.service.MerchantModuleService;
import com.inspirenetz.api.rest.assembler.MerchantModuleAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.MerchantModuleResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class MerchantModuleController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(MerchantModuleController.class);

    @Autowired
    private MerchantModuleService merchantModuleService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    MerchantModuleAssembler merchantModuleAssembler;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/admin/merchantmodule", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveMerchantModule(@Valid MerchantModule merchantModule,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveMerchantModule - Request Received# "+merchantModule.toString());
        log.info("saveMerchantModule -  "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveMerchantModule - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }


        // Set the updatedBy field
        merchantModule.setUpdatedBy(auditDetails);


        // save the merchantModule object and get the result
        merchantModule = merchantModuleService.validateAndSaveMerchantModule(merchantModule);

        // Get the merchantModule id
        retData.setData(merchantModule);

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveMerchantModule -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/admin/merchantmodule/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteMerchantModule(MerchantModule reqMerchantModule) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("deleteMerchantModule - Request Received# Product No:"+ reqMerchantModule.toString());
        log.info("deleteMerchantModule -  "+generalUtils.getLogTextForRequest());


        // Get the merchantModule information
        MerchantModule merchantModule = merchantModuleService.findByMemMerchantNoAndMemModuleId(reqMerchantModule.getMemMerchantNo(), reqMerchantModule.getMemModuleId());

        // If no data found, then set error
        if ( merchantModule == null ) {

            // Log the response
            log.info("deleteMerchantModule - Response : No merchantModule information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        } 


        // Delete the merchantModule and set the retData fields
        merchantModuleService.validateAndDeleteMerchantModule(merchantModule);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete catId
        retData.setData(merchantModule);

        // Log the response
        log.info("deleteMerchantModule -  " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/admin/merchantmodules/{mesMerchantNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantModulesForAdmin(@PathVariable("mesMerchantNo") Long mesMerchantNo){


        // Log the Request
        log.info("listMerchantModules - Request Received# " + mesMerchantNo);
        log.info("listMerchantModules -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching merchantModules
        List<MerchantModule> merchantModuleList = merchantModuleService.findByMemMerchantNo(mesMerchantNo);

        // Set the data
        retData.setData(merchantModuleList);

        // Log the response
        log.info("listMerchantModules -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/merchantmodules/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantModulesMapForMerchant(){


        // Log the Request
        log.info("listMerchantModules - Request Received# ");
        log.info("listMerchantModules -  "+generalUtils.getLogTextForRequest());


        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the MerchantModules as HashMap
        HashMap<Long,String> modulesMap = merchantModuleService.getModulesAsMap(merchantNo);

        // Set the data
        retData.setData(modulesMap);

        // Log the response
        log.info("listMerchantModules -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/admin/merchant/merchantmodules/{mesMerchantNo}/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantModulesForMerchant(@PathVariable("mesMerchantNo") Long mesMerchantNo,
                                                            @PathVariable("filter") String filter,
                                                            @PathVariable("query") String query) throws InspireNetzException {


        // Log the Request
        log.info("listMerchantModulesForMerchant - Request Received# " + mesMerchantNo);
        log.info("listMerchantModulesForMerchant -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //create resource list
        List<MerchantModuleResource> merchantModuleResourceList =new ArrayList<>();

        // Get the list of matching merchantModules
        Page<MerchantModule> merchantModulePage = merchantModuleService.getMerchantModule(mesMerchantNo,filter,query);

        //map data
        merchantModuleResourceList =merchantModuleAssembler.toResources(merchantModulePage);

        // Set the data
        retData.setData(merchantModuleResourceList);

        // Log the response
        log.info("listMerchantModulesForMerchant -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

}

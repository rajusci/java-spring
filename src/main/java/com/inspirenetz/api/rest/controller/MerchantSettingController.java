package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.MerchantSetting;
import com.inspirenetz.api.core.service.MerchantSettingService;
import com.inspirenetz.api.rest.assembler.MerchantSettingAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.MerchantSettingResource;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class MerchantSettingController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(MerchantSettingController.class);

    @Autowired
    private MerchantSettingService merchantSettingService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    MerchantSettingAssembler merchantSettingAssembler;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/admin/merchantsetting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveMerchantSetting(@Valid MerchantSetting merchantSetting,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveMerchantSetting - Request Received# "+merchantSetting.toString());
        log.info("saveMerchantSetting -  "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveMerchantSetting - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }


        // Set the updatedBy field
        merchantSetting.setUpdatedBy(auditDetails);


        // save the merchantSetting object and get the result
        merchantSetting = merchantSettingService.validateAndSaveMerchantSetting(merchantSetting);

        // Get the merchantSetting id
        retData.setData(merchantSetting);

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveMerchantSetting -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/admin/merchantsetting/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteMerchantSetting(MerchantSetting reqMerchantSetting) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("deleteMerchantSetting - Request Received# Product No:"+ reqMerchantSetting.toString());
        log.info("deleteMerchantSetting -  "+generalUtils.getLogTextForRequest());


        // Get the merchantSetting information
        MerchantSetting merchantSetting = merchantSettingService.findByMesMerchantNoAndMesSettingId(reqMerchantSetting.getMesMerchantNo(), reqMerchantSetting.getMesSettingId());

        // If no data found, then set error
        if ( merchantSetting == null ) {

            // Log the response
            log.info("deleteMerchantSetting - Response : No merchantSetting information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        } 


        // Delete the merchantSetting and set the retData fields
        merchantSettingService.validateAndDeleteMerchantSetting(merchantSetting);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete catId
        retData.setData(merchantSetting);

        // Log the response
        log.info("deleteMerchantSetting -  " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/admin/merchantsettings/{mesMerchantNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantSettingsForAdmin(@PathVariable("mesMerchantNo") Long mesMerchantNo){


        // Log the Request
        log.info("listMerchantSettings - Request Received# " + mesMerchantNo);
        log.info("listMerchantSettings -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching merchantSettings
        List<MerchantSetting> merchantSettingList = merchantSettingService.findByMesMerchantNo(mesMerchantNo);

        // Set the data
        retData.setData(merchantSettingList);

        // Log the response
        log.info("listMerchantSettings -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/merchantsettings/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantSettingsMapForMerchant(){


        // Log the Request
        log.info("listMerchantSettings - Request Received# ");
        log.info("listMerchantSettings -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the merhcant number from the session
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the MerchantSettingnMap
        HashMap<Long,String>  settingMap = merchantSettingService.getSettingAsMap(merchantNo);

        // Set the data
        retData.setData(settingMap);

        // Log the response
        log.info("listMerchantSettings -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/merchantsettings/valuemap", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantSettingsValuesMapForMerchant(){


        // Log the Request
        log.info("listMerchantSettingsValuesMapForMerchant - Request Received# ");
        log.info("listMerchantSettingsValuesMapForMerchant -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the merhcant number from the session
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the MerchantSettingnMap
        HashMap<String,String>  settingMap = merchantSettingService.getSettingValuesAsMap(merchantNo);

        // Set the data
        retData.setData(settingMap);

        // Log the response
        log.info("listMerchantSettingsValuesMapForMerchant -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/admin/merchantsettings/{mesMerchantNo}/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantSettingsForAdminUser(@PathVariable("mesMerchantNo") Long mesMerchantNo,@PathVariable(value ="filter") String filter,@PathVariable("query") String query,
                                                              Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listMerchantSettingsForAdminUser - Request Received# " + mesMerchantNo);
        log.info("listMerchantSettingsForAdminUser -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //create merchant settings resource list
        List<MerchantSettingResource> merchantSettingResources =new ArrayList<>();

        // Get the list of matching merchantSettings
        Page<MerchantSetting> merchantSettingList = merchantSettingService.getMerchantSettingsForAdmin(mesMerchantNo, filter, query, pageable);

        //map into resource
        merchantSettingResources=merchantSettingAssembler.toResources(merchantSettingList);

        // Set the data
        retData.setData(merchantSettingResources);

        // Log the response
        log.info("listMerchantSettingsForAdminUser -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


}

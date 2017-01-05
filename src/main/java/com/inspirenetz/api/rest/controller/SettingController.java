package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.Setting;
import com.inspirenetz.api.core.service.SettingService;
import com.inspirenetz.api.rest.assembler.SettingAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.SettingResource;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class SettingController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(SettingController.class);

    @Autowired
    private SettingService settingService;

    @Autowired
    private SettingAssembler settingAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/admin/setting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveSetting(@Valid Setting setting,BindingResult result) throws InspireNetzException {


        // Check if the user is valid
        settingService.isUserValidForOperation(authSessionUtils.getCurrentUser());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("saveSetting - Request Received# "+setting.toString());
        log.info("saveSetting - "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveSetting - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }



        // If the setting.getCatId is  null, then set the created_by, else set the updated_by
        if ( setting.getSetId() == null ) {

            setting.setCreatedBy(auditDetails);

        } else {

            setting.setUpdatedBy(auditDetails);

        }


        // save the setting object and get the result
        setting = settingService.validateAndSaveSetting(setting);

        // If the setting object is not null ,then return the success object
        if ( setting.getSetId() != null ) {

            // Get the setting id
            retData.setData(setting.getSetId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveSetting - Response : Unable to save the setting information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveSetting - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/admin/setting/delete/{setId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteSetting(@PathVariable( value = "setId") Long setId) throws InspireNetzException {

        // Check if the user is valid
        settingService.isUserValidForOperation(authSessionUtils.getCurrentUser());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("deleteSetting - Request Received# Product No:"+ setId);
        log.info("deleteSetting - "+generalUtils.getLogTextForRequest());


        // Get the setting information
        Setting setting = settingService.findBySetId(setId);

        // If no data found, then set error
        if ( setting == null ) {

            // Log the response
            log.info("deleteSetting - Response : No setting information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Delete the setting and set the retData fields
        settingService.validateAndDeleteSetting(setId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete setId
        retData.setData(setId);

        // Log the response
        log.info("deleteSetting - " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/admin/settings/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listSettings(  @PathVariable(value="filter") String filter,
                                            @PathVariable(value="query") String query,
                                            Pageable pageable) throws InspireNetzException {


        // Check if the user is valid
        settingService.isUserValidForOperation(authSessionUtils.getCurrentUser());

        // Log the Request
        log.info("listSettings - Request Received# ");
        log.info("listSettings - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching settings
        Page<Setting> settingList = settingService.searchSettings(filter, query, pageable);

        // Get the list of the settingResources
        List<SettingResource> settingResourceList = settingAssembler.toResources(settingList);

        // Set the data
        retData.setData(settingResourceList);


        // Log the response
        log.info("listSettings - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/admin/setting/{setId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getSettingInfo(@PathVariable(value = "setId") Long setId,Pageable pageable) throws InspireNetzException {


        // Check if the user is valid
        settingService.isUserValidForOperation(authSessionUtils.getCurrentUser());
        
        

        // Log the Request
        log.info("getSettingInfo - Request Received# Setting Id : "+setId);
        log.info("getSettingInfo - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        
     
        // Get the information  for the Setting
        Setting setting = settingService.findBySetId(setId);

        // If the setting is not fetched, then show the error
        if ( setting == null ) {

            // Log the information
            log.info("getSettingInfo -> No setting information found");

            // throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // convert the Entity to resource
        SettingResource settingResource = settingAssembler.toResource(setting);

        // Set the retData
        retData.setData(settingResource);



        // Log the response
        log.info("getSettingInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


}

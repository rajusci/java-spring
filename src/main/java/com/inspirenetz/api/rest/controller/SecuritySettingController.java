package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.SecuritySetting;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.SecuritySettingService;
import com.inspirenetz.api.rest.assembler.SecuritySettingAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.SecuritySettingResource;
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
public class SecuritySettingController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(SecuritySettingController.class);

    @Autowired
    private SecuritySettingService securitySettingService;

    @Autowired
    private SecuritySettingAssembler securitySettingAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/merchant/securitysettings/securitysetting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveSecuritySetting(SecuritySettingResource securitySettingResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveSecuritySetting - Request Received# "+securitySettingResource.toString());
        log.info("saveSecuritySetting - "+generalUtils.getLogTextForRequest());

        // convert the LInkRequestResource to object
        SecuritySetting securitySetting = mapper.map(securitySettingResource,SecuritySetting.class);

        // save the securitySetting object and get the result
        securitySetting = securitySettingService.validateAndSaveSecuritySetting(securitySetting);

        // Get the securitySetting id
        retData.setData(securitySetting.getSecId());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveSecuritySetting - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/securitysettings/securitysetting", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getSecuritySettingInfo() throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getSecuritySettingInfo - "+generalUtils.getLogTextForRequest());

        // Get the SecuritySetting
        SecuritySetting securitySetting = securitySettingService.getSecuritySettingsDetails();

        // Convert the SecuritySetting to SecuritySettingResource
        SecuritySettingResource securitySettingResource = securitySettingAssembler.toResource(securitySetting);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the securitySettingResource object
        retData.setData(securitySettingResource);

        // Log the response
        log.info("getSecuritySettingInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }


}
package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.AccountBundlingSetting;
import com.inspirenetz.api.core.service.AccountBundlingSettingService;
import com.inspirenetz.api.rest.assembler.AccountBundlingSettingAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.AccountBundlingSettingResource;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class AccountBundlingSettingController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(AccountBundlingSettingController.class);

    @Autowired
    private AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    private AccountBundlingSettingAssembler accountBundlingSettingAssembler;

    @Autowired
    private Mapper mapper;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/bundling/setting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveAccountBundlingSetting(AccountBundlingSettingResource accountBundlingSettingResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveAccountBundlingSetting - Request Received# "+accountBundlingSettingResource.toString());
        log.info("saveAccountBundlingSetting - "+generalUtils.getLogTextForRequest());

        // Convert the Resource to object
        AccountBundlingSetting accountBundlingSetting = mapper.map(accountBundlingSettingResource,AccountBundlingSetting.class);


        // Save the object
        accountBundlingSetting =  accountBundlingSettingService.saveAccountBundlingSettingForUser(accountBundlingSetting);

        // Get the accountBundlingSetting id
        retData.setData(accountBundlingSetting.getAbsId());

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);



        // Log the response
        log.info("saveAccountBundlingSetting - "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseObject
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/bundling/setting/delete/{absId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteAccountBundlingSetting(@PathVariable(value="absId") Long absId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("deleteAccountBundlingSetting - Request Received# AccountBundlingSetting ID: "+absId);
        log.info("deleteAccountBundlingSetting - "+generalUtils.getLogTextForRequest());



        // Delete the accountBundlingSetting and set the retData fields
        accountBundlingSettingService.removeAccountBundlingSetting(absId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete absId
        retData.setData(absId);



        // Log the response
        log.info("deleteAccountBundlingSetting - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/bundling/settings/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listAccountBundlingSettings(@PathVariable(value ="filter") String filter,
                                        @PathVariable(value ="query") String query,
                                        Pageable pageable){


        // Log the Request
        log.info("listAccountBundlingSettings - Request Received# filter "+ filter +" query :" +query );
        log.info("listAccountBundlingSettings - "+generalUtils.getLogTextForRequest());


        // Get the merchant number
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list
        List<AccountBundlingSetting> accountBundlingSettingList = accountBundlingSettingService.findByAbsMerchantNo(merchantNo);

        // Variable holding the accountBundlingSetting
        List<AccountBundlingSettingResource> accountBundlingSettingResourceList = accountBundlingSettingAssembler.toResources(accountBundlingSettingList);

        // Set the data
        retData.setData(accountBundlingSettingResourceList);

        // Log the response
        log.info("listAccountBundlingSettings - "+generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/bundling/setting", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getAccountBundlingSettingInfo() throws InspireNetzException {



        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getAccountBundlingSettingInfo - Request Received# AccountBundling");
        log.info("getAccountBundlingSettingInfo - "+generalUtils.getLogTextForRequest());



        // Get the AccountBundlingSettingResource
        AccountBundlingSetting accountBundlingSetting = accountBundlingSettingService.getAccountBundlingSettingInfoForUser();

        // Convert the AccountBundlingSetting to AccountBundlingSettingResource
        AccountBundlingSettingResource accountBundlingSettingResource = accountBundlingSettingAssembler.toResource(accountBundlingSetting);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the accountBundlingSettingResource object
        retData.setData(accountBundlingSettingResource);



        // Log the response
        log.info("getAccountBundlingSettingInfo - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;



    }

}

package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.TransferPointSetting;
import com.inspirenetz.api.core.service.TransferPointSettingService;
import com.inspirenetz.api.rest.assembler.TransferPointSettingAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.TransferPointSettingResource;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class TransferPointSettingController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(TransferPointSettingController.class);

    @Autowired
    private TransferPointSettingService transferPointSettingService;

    @Autowired
    private TransferPointSettingAssembler transferPointSettingAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/customer/rewardbalance/transferpoint/setting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveTransferPointSetting(TransferPointSettingResource transferPointSettingResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveTransferPointSetting - Request Received# "+transferPointSettingResource.toString());
        log.info("saveTransferPointSetting - "+generalUtils.getLogTextForRequest());

        // Convert the Resource to object
        TransferPointSetting transferPointSetting = mapper.map(transferPointSettingResource,TransferPointSetting.class);


        // Save the object
        transferPointSetting =  transferPointSettingService.saveTransferPointSettingForUser(transferPointSetting);

        // Get the transferPointSetting id
        retData.setData(transferPointSetting.getTpsId());

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);



        // Log the response
        log.info("saveTransferPointSetting - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseObject
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/customer/rewardbalance/transferpoint/setting/delete/{tpsId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteTransferPointSetting(@PathVariable(value="tpsId") Long tpsId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("deleteTransferPointSetting - Request Received# TransferPointSetting ID: "+tpsId);
        log.info("deleteTransferPointSetting - "+generalUtils.getLogTextForRequest());



        // Delete the transferPointSetting and set the retData fields
        transferPointSettingService.removeTransferPointSetting(tpsId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete tpsId
        retData.setData(tpsId);



        // Log the response
        log.info("deleteTransferPointSetting - " + generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/customer/rewardbalance/transferpoint/settings/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listTransferPointSettings(@PathVariable(value ="filter") String filter,
                                        @PathVariable(value ="query") String query,
                                        Pageable pageable){


        // Log the Request
        log.info("listTransferPointSettings - Request Received# filter "+ filter +" query :" +query );
        log.info("listTransferPointSettings - "+generalUtils.getLogTextForRequest());


        // Get the merchant number
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list
        List<TransferPointSetting> transferPointSettingList = transferPointSettingService.findByTpsMerchantNo(merchantNo);

        // Variable holding the transferPointSetting
        List<TransferPointSettingResource> transferPointSettingResourceList = transferPointSettingAssembler.toResources(transferPointSettingList);

        // Set the data
        retData.setData(transferPointSettingResourceList);

        // Log the response
        log.info("listTransferPointSettings - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/customer/rewardbalance/transferpoint/setting", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getTransferPointSettingInfo() throws InspireNetzException {



        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getTransferPointSettingInfo - Request Received# TransferPoint");
        log.info("getTransferPointSettingInfo - "+generalUtils.getLogTextForRequest());



        // Get the TransferPointSettingResource
        TransferPointSetting transferPointSetting = transferPointSettingService.getTransferPointSettingInfoForUser();

        // Convert the TransferPointSetting to TransferPointSettingResource
        TransferPointSettingResource transferPointSettingResource = transferPointSettingAssembler.toResource(transferPointSetting);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the transferPointSettingResource object
        retData.setData(transferPointSettingResource);



        // Log the response
        log.info("getTransferPointSettingInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;



    }

}

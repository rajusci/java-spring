package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.BulkuploadRawdata;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.BulkuploadRawdataService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.assembler.BulkUploadRawDataAssembler;
import com.inspirenetz.api.rest.resource.BulkUploadMappingResource;
import com.inspirenetz.api.rest.resource.BulkUploadRawDataResource;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 1/4/14.
 */
@Controller
public class BulkuploadRawdataController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(BulkuploadRawdataController.class);

    @Autowired
    private BulkuploadRawdataService bulkuploadRawdataService;

    @Autowired
    private UserService userService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    BulkUploadRawDataAssembler bulkUploadRawDataAssembler;

    @Autowired
    Mapper mapper;

    @RequestMapping(value = "/api/0.8/json/bulkuploadrawdata/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
            params={"mobile"}
    )
    @ResponseBody
    public APIResponseObject findCustomer(@RequestParam(value = "mobile") String mobile){

        long merchantNo = 1;

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // get the list of bulkuploadRawdataService
        List<BulkuploadRawdata> bulkuploadRawdataList = bulkuploadRawdataService.findByBrdMerchantNoAndBrdBatchIndex(merchantNo, 25);
 
        retData.setData(bulkuploadRawdataList);


        // Return the success object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/bulkuploadheader", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public APIResponseObject getHeaderValues(@RequestParam(value = "fileName") String fileName) {


        // Log the Request
        log.info("getHeaderValues - Request Received#  fileName:"+fileName);

        log.info("getHeaderValues - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the header values
        HashMap<String,String> headerValues =bulkuploadRawdataService.getHeaderContent(fileName);

        // Set the data
        retData.setData(headerValues);

        // Log the response
        log.info("getHeaderValues - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;

    }

    @RequestMapping(value = "/api/0.9/json/merchant/processbulkupload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public APIResponseObject bulKUploadProcess(@RequestParam(value = "fileName") String fileName,@RequestBody Map<String,Object> mappingGrammar) {


        // Log the Request
        log.info("bulKUploadProcess - Request Received#  fileName:"+fileName);

        log.info("bulKUploadProcess - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //map set object
        BulkUploadMappingResource bulkUploadMappings =  mapper.map(mappingGrammar, BulkUploadMappingResource.class);

        User user =userService.findByUsrUserNo(authSessionUtils.getUserNo());

        // Get the header values
        bulkuploadRawdataService.processingBulkUpload(fileName, bulkUploadMappings.getData(),user);

        // Set the data
        retData.setData(APIResponseStatus.success);

        // Log the response
        log.info("getHeaderValues - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;

    }

    @RequestMapping(value = "/api/0.9/json/merchant/getrawdatainfo/{brdbatchIndex}", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listBulkUploadRawDataInfo(@PathVariable(value = "brdbatchIndex") Long  brdBatchIndex,Pageable pageable) {


        // Log the Request
        log.info("listBulkUploadRawDataInfo - Request Received#  fileName:"+brdBatchIndex);

        log.info("listBulkUploadRawDataInfo - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //merchant number
        Long merchantNo =authSessionUtils.getMerchantNo();

        //get bulk upload list
        // Variable holding the bulkUploadRawData
        List<BulkUploadRawDataResource> bulkUploadRawDataResourceList = new ArrayList<>(0);


        //get the page
        Page<BulkuploadRawdata> bulkUploadRawDataPage = bulkuploadRawdataService.findByBrdMerchantNoAndBrdBatchIndex(merchantNo, brdBatchIndex, pageable);

        // Convert to Resource
        bulkUploadRawDataResourceList = bulkUploadRawDataAssembler.toResources(bulkUploadRawDataPage);




        // Set the data
        retData.setData(bulkUploadRawDataResourceList);

        // Set the data
        retData.setStatus(APIResponseStatus.success);

        retData.setPageableParams(bulkUploadRawDataPage);

        // Log the response
        log.info("listBulkUploadRawDataInfo - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;

    }
}

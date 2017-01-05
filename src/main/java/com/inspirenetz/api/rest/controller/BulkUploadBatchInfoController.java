package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.BulkUploadBatchInfo;
import com.inspirenetz.api.core.repository.BulkUploadBatchInfoRepository;
import com.inspirenetz.api.core.service.BulkUploadBatchInfoService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.assembler.BulkUploadBatchInfoAssembler;
import com.inspirenetz.api.rest.resource.BulkUploadBatchInfoResource;
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

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ameen on 14/9/15.
 */
@Controller
public class BulkUploadBatchInfoController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(BulkUploadBatchInfoController.class);

    @Autowired
    private BulkUploadBatchInfoService bulkUploadBatchInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    BulkUploadBatchInfoAssembler bulkUploadBatchInfoAssembler;

    @Autowired
    Mapper mapper;

    @RequestMapping(value = "/api/0.9/json/merchant/getbatchinfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getBatchInfo(@RequestParam(value = "fromDate",defaultValue = "1980-01-01") Date fromDate,@RequestParam(value = "toDate",defaultValue = "9999-12-31") Date toDate,Pageable pageable){

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //get merchant info details
        Long merchantNo =authSessionUtils.getMerchantNo();

        // Variable holding the brand
        List<BulkUploadBatchInfoResource> bulkUploadBatchInfoResources = new ArrayList<>(0);

        // get the list of bulkupload RawdataService
        Page<BulkUploadBatchInfo> bulkUploadBatchInfos = bulkUploadBatchInfoService.findByBlkMerchantNoAndBlkBatchDateBetween(merchantNo,fromDate,toDate, pageable);

        bulkUploadBatchInfoResources =bulkUploadBatchInfoAssembler.toResources(bulkUploadBatchInfos);

        retData.setData(bulkUploadBatchInfoResources);

        retData.setPageableParams(bulkUploadBatchInfos);

        retData.setStatus(APIResponseStatus.success);

        // Return the success object
        return retData;
    }
}

package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.PointTransferRequest;
import com.inspirenetz.api.core.service.PointTransferRequestService;
import com.inspirenetz.api.rest.assembler.PointTransferRequestAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.PointTransferRequestResource;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameenci on 16/9/14.
 */
@Controller
public class PointTransferRequestController {


    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(PointTransferRequestController.class);

    @Autowired
    private PointTransferRequestService pointTransferRequestService;

    @Autowired
    private PointTransferRequestAssembler pointTransferRequestAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    private Mapper mapper;


    @RequestMapping(value = "/api/0.9/json/merchant/transferpoint/requests/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listPointTransferRequest( @PathVariable(value ="filter") String filter,
                                                       @PathVariable(value ="query") String query,
                                                       Pageable pageable){
        // Log the Request
        log.info("listPointTransferRequest "+generalUtils.getLogTextForRequest() );

        //Log the Request Parameters
        log.info("listPointTransferRequest - Request Received# filter "+ filter +" query :" +query );

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the PointTransferRequest
        Page<PointTransferRequest> pointTransferRequestPage = pointTransferRequestService.searchPointTransferRequest(filter, query, merchantNo, pageable);

        // Convert to resource
        List<PointTransferRequestResource> pointTransferRequestResourceList = pointTransferRequestAssembler.toResources(pointTransferRequestPage);

        //set pageable
        retData.setPageableParams(pointTransferRequestPage);

        // Set the data
        retData.setData(pointTransferRequestResourceList);

        //Set the status
        retData.setStatus(APIResponseStatus.success);

        //set pageable params
        retData.setPageableParams(pointTransferRequestPage);

        // Log the response
        log.info("listPointTransferRequest - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;
    }


}

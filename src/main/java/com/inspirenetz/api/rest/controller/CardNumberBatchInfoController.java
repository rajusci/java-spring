package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.CardNumberBatchInfo;
import com.inspirenetz.api.core.service.CardNumberBatchInfoService;
import com.inspirenetz.api.rest.assembler.CardNumberBatchInfoAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CardNumberBatchInfoResource;
import com.inspirenetz.api.util.*;
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
import java.util.List;

/**
 * Created by ameen on 21/10/15.
 */
@Controller
public class CardNumberBatchInfoController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CardNumberBatchInfoController.class);

    @Autowired
    private CardNumberBatchInfoService cardNumberBatchInfoService;

    @Autowired
    private CardNumberBatchInfoAssembler cardNumberBatchInfoAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;


    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/merchant/searchcardbatchinfo/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCardBatchInfoInformation(@PathVariable(value ="filter") String filter,@PathVariable("query") String query,
                                                          @RequestParam(value ="fromDate",required = false) Date fromDate,
                                                          @RequestParam(value ="endDate",required = false) Date endDate,

                                                                                                           Pageable pageable){


        // Log the Request
        log.info("listCardBatchInfoInformation - Request Received# filter and query "+ filter+":and:"+query);
        log.info("listCardBatchInfoInformation - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Variable holding the CardNumberBatchInfo
        List<CardNumberBatchInfoResource> cardNumberBatchInfoResourceList = new ArrayList<>(0);

        Long merchantNo =authSessionUtils.getMerchantNo();


        // If the startDate is null, then  set the date to 0000-00-00
        if ( fromDate == null ) {

            fromDate = DBUtils.covertToSqlDate("0000-00-00");

        }

        // If the endDate is null, then  set the date to 9999-12-31
        if ( endDate == null ) {

            endDate = DBUtils.covertToSqlDate("9999-12-31");

        }


        //search batch informations
        Page<CardNumberBatchInfo> cardNumberBatchInfoPage=cardNumberBatchInfoService.searchCardNumberBatchInfo(merchantNo,filter,query,fromDate,endDate,pageable);

        //map resource
        cardNumberBatchInfoResourceList=cardNumberBatchInfoAssembler.toResources(cardNumberBatchInfoPage);

        //set pageable paramter
        retData.setPageableParams(cardNumberBatchInfoPage);

        // Set the data
        retData.setData(cardNumberBatchInfoResourceList);

        // Log the response
        log.info("listCardBatchInfoInformation-   " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }
}

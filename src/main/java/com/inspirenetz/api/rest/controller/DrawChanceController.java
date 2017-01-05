package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.domain.DrawChance;
import com.inspirenetz.api.core.service.DrawChanceService;
import com.inspirenetz.api.rest.assembler.DrawChanceAssembler;
import com.inspirenetz.api.rest.resource.DrawChanceResource;
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
 * Created by saneeshci on 25/9/14.
 *
 */
@Controller
public class DrawChanceController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(DrawChanceController.class);

    @Autowired
    private DrawChanceService drawChanceService;

    @Autowired
    private DrawChanceAssembler drawChanceAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/drawchances/retrive/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject retrieveDrawChances(@PathVariable(value ="type") Integer type,
                                              Pageable pageable){


        // Log the Request
        log.info("retrieveDrawChances - Request Received# Draw Type :" + type);
        log.info("retrieveDrawChances - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the DrawChance
        List<DrawChance> drawChancePage = drawChanceService.findByDrcType(type);

        // Convert to reosurce
        List<DrawChanceResource> drawChanceResourceList =  drawChanceAssembler.toResources(drawChancePage);

        // Set the data
        retData.setData(drawChanceResourceList);

        // Log the response
        log.info("retrieveDrawChances -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }




}
package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.PromotionalEvent;
import com.inspirenetz.api.core.service.PromotionalEventService;
import com.inspirenetz.api.rest.assembler.PromotionalEventAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.PromotionalEventResource;
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

import java.sql.Date;
import java.util.List;

/**
 * Created by saneeshci on 29/9/14.
 *
 */
@Controller
public class PromotionalEventController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(PromotionalEventController.class);

    @Autowired
    private PromotionalEventService promotionalEventService;

    @Autowired
    private PromotionalEventAssembler promotionalEventAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/merchant/promotionalevent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject savePromotionalEvent(PromotionalEventResource promotionalEventResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("savePromotionalEvent - Request Received# "+promotionalEventResource.toString());
        log.info("savePromotionalEvent -  "+generalUtils.getLogTextForRequest());




        // convert the LInkRequestResource to object
        PromotionalEvent promotionalEvent = mapper.map(promotionalEventResource,PromotionalEvent.class);

        // save the promotionalEvent object and get the result
        promotionalEvent = promotionalEventService.validateAndSavePromotionalEvent(promotionalEvent);

        // Get the promotionalEvent id
        retData.setData(promotionalEvent.getPreId());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("savePromotionalEvent - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/promotionalevent/delete/{preId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteEvent(@PathVariable(value="preId") Long preId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("deletePromotionalEvent - Request Received# PromotionalEvent ID: "+preId);
        log.info("deletePromotionalEvent -  "+generalUtils.getLogTextForRequest());



        // Delete the linkRequest and set the retData fields
        promotionalEventService.validateAndDeletePromotionalEvent(preId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete lrqId
        retData.setData(preId);



        // Log the response
        log.info("deletePromotionalEvent - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/promotionalevents/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listPromotionalEvents(@PathVariable(value ="filter") String filter,
                                              @PathVariable(value ="query") String query,
                                              Pageable pageable){


        // Log the Request
        log.info("listPromotionalEvents - Request Received# filter "+ filter +" query :" +query );
        log.info("listPromotionalEvents -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Get the LinkRequest
        Page<PromotionalEvent> promotionalEventPage = promotionalEventService.searchPromotionalEvents(filter, query, merchantNo, pageable);

        // Convert to reosurce
        List<PromotionalEventResource> promotionalEventResources =  promotionalEventAssembler.toResources(promotionalEventPage);

        // Set the data
        retData.setData(promotionalEventResources);


        // Log the response
        log.info("listPromotionalEvents - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/events/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getValidEvents(@PathVariable(value="date") Date date,
                                                Pageable pageable) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getValidPromotionalEvents - Request Received# promotional event Date: "+date);
        log.info("getValidPromotionalEvents -  "+generalUtils.getLogTextForRequest());

        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the LinkRequest
        Page<PromotionalEvent> promotionalEvents = promotionalEventService.getValidEventsByDate(merchantNo,date,pageable);

        // Convert the LinkRequest to LinkRequestResource
        List<PromotionalEventResource> promotionalEventResources = promotionalEventAssembler.toResources(promotionalEvents);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the linkRequestResource object
        retData.setData(promotionalEventResources);




        // Log the response
        log.info("getValidPromotionalEvents - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/promotionalevent/{preId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getEventInfo(@PathVariable(value="preId") Long preId
                                            ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getValidPromotionalEvents - Request Received# promotionalEventID: "+preId);
        log.info("getValidPromotionalEvents -  "+generalUtils.getLogTextForRequest());

        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the LinkRequest
        PromotionalEvent promotionalEvent = promotionalEventService.findByPreId(preId);

        // Convert the LinkRequest to LinkRequestResource
        PromotionalEventResource promotionalEventResource = promotionalEventAssembler.toResource(promotionalEvent);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the linkRequestResource object
        retData.setData(promotionalEventResource);




        // Log the response
        log.info("getValidPromotionalEvents - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/promotionalevents/{preEventType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getMileStoneEvents(@PathVariable(value ="preEventType") Integer preEventType,
                                                                                     Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("getMileStoneEvents - Request Received# EventType "+ preEventType);
        log.info("getMileStoneEvents -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Get the LinkRequest
        Page<PromotionalEvent> promotionalEventPage = promotionalEventService.findByPreMerchantNoAndPreEventType(merchantNo, preEventType, pageable);

        // Convert to reosurce
        List<PromotionalEventResource> promotionalEventResources =  promotionalEventAssembler.toResources(promotionalEventPage);

        // Set the data
        retData.setData(promotionalEventResources);

        // Log the response
        log.info("getMileStoneEvents - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

}
package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.LinkRequest;
import com.inspirenetz.api.core.service.LinkRequestService;
import com.inspirenetz.api.rest.assembler.LinkRequestAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.LinkRequestResource;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class LinkRequestController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(LinkRequestController.class);

    @Autowired
    private LinkRequestService linkRequestService;

    @Autowired
    private LinkRequestAssembler linkRequestAssembler;

    @Autowired
    private Mapper mapper;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/merchant/linking/linkrequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveLinkRequest(LinkRequestResource linkRequestResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveLinkRequest - Request Received# "+linkRequestResource.toString());
        log.info("saveLinkRequest -  "+generalUtils.getLogTextForRequest());




        // convert the LInkRequestResource to object
        LinkRequest linkRequest = mapper.map(linkRequestResource,LinkRequest.class);

        // save the linkRequest object and get the result
        linkRequest = linkRequestService.validateAndSaveLinkRequest(linkRequest);

        // Get the linkRequest id
        retData.setData(linkRequest.getLrqId());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);




        // Log the response
        log.info("saveLinkRequest -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/linking/createlinkrequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject createLinkRequest(@RequestParam(value ="primaryLoyaltyId") String primaryLoyaltyId,
                                               @RequestParam(value="childLoyaltyId") String childLoyaltyId,
                                               @RequestParam(value="lrqInitiator") String lrqInitiator,
                                               @RequestParam(value="lrqSource") Integer lrqSource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("createLinkRequest - Request Received# PrimaryLoyaltyID : "+ primaryLoyaltyId + " : childLoyaltId : " + childLoyaltyId + " Initiator : " + lrqInitiator + " Source : " + lrqSource);
        log.info("createLinkRequest -  "+generalUtils.getLogTextForRequest());



        // convert the LInkRequestResource to object
        LinkRequest linkRequest = linkRequestService.createLinkRequest(authSessionUtils.getMerchantNo(),primaryLoyaltyId,childLoyaltyId,lrqInitiator,lrqSource);

        // Get the linkRequest id
        retData.setData(linkRequest.getLrqId());

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);



        // Log the response
        log.info("saveLinkRequest - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/linking/createlinkrequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject createCustomerLinkRequest(@RequestParam(value ="primaryLoyaltyId") String primaryLoyaltyId,
                                                       @RequestParam(value="childLoyaltyId") String childLoyaltyId,
                                                       @RequestParam(value="lrqInitiator") String lrqInitiator,
                                                       @RequestParam(value="lrqSource") Integer lrqSource,
                                                       @RequestParam(value="merchantNo",defaultValue = "1") Long merchantNo) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("createCustomerLinkRequest - Request Received# PrimaryLoyaltyID : "+ primaryLoyaltyId + " : childLoyaltId : " + childLoyaltyId + " Initiator : " + lrqInitiator + " Source : " + lrqSource);
        log.info("createCustomerLinkRequest -  "+generalUtils.getLogTextForRequest());



        // convert the LInkRequestResource to object
        LinkRequest linkRequest = linkRequestService.createLinkRequest(merchantNo,primaryLoyaltyId,childLoyaltyId,lrqInitiator,lrqSource);

        // Get the linkRequest id
        retData.setData(linkRequest.getLrqId());

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);



        // Log the response
        log.info("saveLinkRequest -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/linking/linkrequest/delete/{lrqId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteLinkRequest(@PathVariable(value="lrqId") Long lrqId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("deleteLinkRequest - Request Received# LinkRequest ID: "+lrqId);
        log.info("deleteLinkRequest -  "+generalUtils.getLogTextForRequest());



        // Delete the linkRequest and set the retData fields
        linkRequestService.validateAndDeleteLinkRequest(lrqId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete lrqId
        retData.setData(lrqId);



        // Log the response
        log.info("deleteLinkRequest -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/linking/linkrequests/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listLinkRequests(@PathVariable(value ="filter") String filter,
                                              @PathVariable(value ="query") String query,
                                        Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listLinkRequests - Request Received# filter "+ filter +" query :" +query );
        log.info("listLinkRequests -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        List<LinkRequestResource> linkRequestResourceList = new ArrayList<>(0);

        // Get the LinkRequest
        Page<LinkRequest> linkRequestPage = linkRequestService.searchLinkRequests(filter,query,merchantNo,pageable);

        // Convert to reosurce
        linkRequestResourceList =  linkRequestAssembler.toResources(linkRequestPage);

        // Set the data
        retData.setData(linkRequestResourceList);

        // Set the pageable params to the retData
        retData.setPageableParams(linkRequestPage);

        // Log the response
        log.info("listLinkRequests -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/linking/linkrequest/{lrqId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getLinkRequestInfo(@PathVariable(value="lrqId") Long lrqId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getLinkRequestInfo - Request Received# LinkRequest ID: "+lrqId);
        log.info("getLinkRequestInfo -  "+generalUtils.getLogTextForRequest());



        // Get the LinkRequest
        LinkRequest linkRequest = linkRequestService.getLinkRequestInfo(lrqId);

        // Convert the LinkRequest to LinkRequestResource
        LinkRequestResource linkRequestResource = linkRequestAssembler.toResource(linkRequest);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the linkRequestResource object
        retData.setData(linkRequestResource);




        // Log the response
        log.info("getLinkRequestInfo -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/bundling/unlink", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject addUnlinkRequest(@RequestParam(value ="primaryLoyaltyId") String primaryLoyaltyId,
                                              @RequestParam(value="childLoyaltyId") String childLoyaltyId,
                                              @RequestParam(value="lrqInitiator") String lrqInitiator,
                                              @RequestParam(value="lrqSource") Integer lrqSource
    ) throws InspireNetzException {

        log.info("addUnlinkRequest -  Request Received: #primaryLoyaltyId :"+primaryLoyaltyId+" #childLoyaltyId :"+childLoyaltyId+" #lrqInitiator : "+lrqInitiator+" #lrqSource:"+lrqSource);
        log.info("addUnlinkRequest -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Place the service call
        Long lrqId= linkRequestService.saveUnlinkingRequest(primaryLoyaltyId, childLoyaltyId,lrqInitiator,lrqSource, merchantNo,false);


        // Check if the data got saved successfully
        if( lrqId != null ) {

            // Set the data
            retData.setData(lrqId);

            // Set the status to success
            retData.setStatus(APIResponseStatus.success);

        }



        // Log the response
        log.info("addUnlinkRequest-   " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/bundling/unlink", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject addUnlinkRequestForCustomer(@RequestParam(value ="primaryLoyaltyId") String primaryLoyaltyId,
                                                         @RequestParam(value="childLoyaltyId") String childLoyaltyId,
                                                         @RequestParam(value="lrqSource") Integer lrqSource,
                                                         @RequestParam(value="merchantNo") Long merchantNo
    ) throws InspireNetzException {

        log.info("addUnlinkRequestForCustomer -  Request Received: #primaryLoyaltyId :"+primaryLoyaltyId+" #childLoyaltyId :"+childLoyaltyId+" #lrqSource:"+lrqSource + " #merchantNo:"+merchantNo);
        log.info("addUnlinkRequestForCustomer -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the initiator as the currently logged in user
        String lrqInitiator = authSessionUtils.getUserLoginId();

        // Place the service call
        Long lrqId= linkRequestService.saveUnlinkingRequest(primaryLoyaltyId, childLoyaltyId,lrqInitiator,lrqSource, merchantNo,false);

        // Check if the data got saved successfully
        if(lrqId != null){

            // Set the data
            retData.setData(lrqId);

            // Set the status
            retData.setStatus(APIResponseStatus.success);

        }

        // Log the response
        log.info("addUnlinkRequestForCustomer -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/bundling/unlink/all", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject addUnlinkAllRequest(@RequestParam(value ="loyaltyId") String loyaltyId,
                                                 @RequestParam(value="merchantNo") Long merchantNo) throws InspireNetzException {

        log.info("addUnlinkAllRequest -  Request Received: #loyaltyId :"+loyaltyId + " #merchantNo:"+merchantNo);
        log.info("addUnlinkAllRequest -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Place the service call
        boolean isUnlinkSuccess= linkRequestService.unlinkAllRequest(loyaltyId, merchantNo,false);

        // Check if the data got saved successfully
        if(isUnlinkSuccess){

            // Set the status
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the status to failed
            retData.setStatus(APIResponseStatus.failed);

        }

        // Log the response
        log.info("addUnlinkAllRequest -   " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }





}

package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.PartyApprovalType;
import com.inspirenetz.api.core.domain.PartyApproval;
import com.inspirenetz.api.core.service.PartyApprovalService;
import com.inspirenetz.api.rest.assembler.PartyApprovalAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.PartyApprovalResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class PartyApprovalController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(PartyApprovalController.class);

    @Autowired
    private PartyApprovalService partyApprovalService;

    @Autowired
    private PartyApprovalAssembler partyApprovalAssembler;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/partyapproval/status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject changePartyApprovalRequestByMerchant(     @RequestParam (value = "approver") String approverLoyaltyId,
                                                                       @RequestParam (value = "requestor") String requestorLoyaltyId,
                                                                       @RequestParam (value = "requestType") Integer requestType,
                                                                       @RequestParam (value = "status") Integer status,
                                                                       @RequestParam (value = "prdCode",defaultValue = "0") String prdCode)

                                                                       throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

              // Log the Request
        log.info("changePartyApprovalRequestByMerchant - Request Received# approver:"+approverLoyaltyId+" requestor:"+requestorLoyaltyId+"type:"+requestType+" status:"+status);
        log.info("changePartyApprovalRequestByMerchant -   "+generalUtils.getLogTextForRequest());



        // Get the merchantNo from session
        Long merchantNo = authSessionUtils.getMerchantNo();

        partyApprovalService.changePartyApproval(merchantNo,requestorLoyaltyId,approverLoyaltyId,requestType,prdCode,status,requestType);

        // set return data as change approval request status
        retData.setData(status);

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("changePartyApprovalRequestByMerchant -   " + generalUtils.getLogTextForResponse(retData));

        // Return  the APIResponseObject
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/customer/partyapproval/status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject changePartyApprovalRequestByCustomer(   @RequestParam (value = "requestor") String requestorLoyaltyId,
                                                                     @RequestParam (value = "requestType") Integer requestType,
                                                                     @RequestParam (value = "status") Integer status,
                                                                     @RequestParam (value = "prdCode",defaultValue = "0") String prdCode,
                                                                     @RequestParam (value = "merchantNo") Long merchantNo)
            throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("changePartyApprovalRequestByMerchant - Request Received# requestor:"+requestorLoyaltyId+"type:"+requestType+" status:"+status);
        log.info("changePartyApprovalRequestByMerchant -   "+generalUtils.getLogTextForRequest());



        // Get the approver loyalty id as the loyalty id from the session
        String approverLoyaltyId = authSessionUtils.getUserLoginId();

        //call the processing method
        partyApprovalService.changePartyApproval(merchantNo,requestorLoyaltyId,approverLoyaltyId,requestType,prdCode,status,requestType);


        // set return data as change approval request status
        retData.setData(status);

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);



        // Log the response
        log.info("changePartyApprovalRequestByMerchant -  " + generalUtils.getLogTextForResponse(retData));

        // Return  the APIResponseObject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/customer/approvals/pending", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getApprovalRequests(   @RequestParam (value = "merchantNo") Long merchantNo)
            throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getApprovalRequests - Request Received# merchantNo:"+merchantNo);
        log.info("getApprovalRequests -   "+generalUtils.getLogTextForRequest());



        // change the partyApprovalRequest and set return value to status
        List<PartyApproval> partyApprovals= partyApprovalService.getPendingPartyApproval(merchantNo);

        // Convert to resources
        List<PartyApprovalResource> partyApprovalResources = partyApprovalAssembler.toResources(partyApprovals);

        // set return data as change approval request status
        retData.setData(partyApprovalResources);


        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getApprovalRequests -   " + generalUtils.getLogTextForResponse(retData));

        // Return  the APIResponseObject
        return retData;


    }

}

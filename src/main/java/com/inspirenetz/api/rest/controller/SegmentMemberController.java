package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.SegmentMemberDetails;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.core.service.CustomerSegmentService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.SegmentMemberService;
import com.inspirenetz.api.rest.assembler.SegmentMemberAssembler;
import com.inspirenetz.api.rest.assembler.SegmentMemberDetailsAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerProfileResource;
import com.inspirenetz.api.rest.resource.CustomerResource;
import com.inspirenetz.api.rest.resource.SegmentMemberDetailsResource;
import com.inspirenetz.api.rest.resource.SegmentMemberResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
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
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class SegmentMemberController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(SegmentMemberController.class);

    @Autowired
    private SegmentMemberService segmentMemberService;

    @Autowired
    private CustomerSegmentService customerSegmentService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    AuthSessionUtils authSessionUtils;
    
    @Autowired
    private SegmentMemberAssembler segmentMemberAssembler;

    @Autowired
    private SegmentMemberDetailsAssembler segmentMemberDetailsAssembler;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/merchant/segmentation/segmentmember", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveSegmentMember(@Valid SegmentMember segmentMember,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();



        // Log the Request
        log.info("saveSegmentMember - Request Received# "+segmentMember.toString());
        log.info("saveSegmentMember - "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveSegmentMember - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }


        // Get the CustomerSegment info
        CustomerSegment customerSegment = customerSegmentService.findByCsgSegmentId(segmentMember.getSgmSegmentId());

        // Check if the customersegment exists and is authorized
        if ( customerSegment == null || customerSegment.getCsgMerchantNo() != merchantNo ) {

            // Log the message
            log.info("saveSegmentMember - Response : You are not authorized for the customer segment");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Get the customer information
        Customer customer = customerService.findByCusCustomerNo(segmentMember.getSgmCustomerNo());

        // Check if the customer is valid and is authorized to be added by the merchant user
        if ( customer == null || customer.getCusMerchantNo() != merchantNo ) {

            // Log the message
            log.info("saveSegmentMember - Response : You are not authorized for the customer");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        // Check if the segmentMember is existing
        boolean isExist = segmentMemberService.isDuplicateSegmentMemberExisting(segmentMember);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveSegmentMember - Response : SegmentMember code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the segmentMember.getSgmId is  null, then set the created_by, else set the updated_by
        if ( segmentMember.getSgmId() == null ) {

            segmentMember.setCreatedBy(auditDetails);

        } else {

            segmentMember.setUpdatedBy(auditDetails);

        }

        //set merchant No
        segmentMember.setSgmMerchantNo(authSessionUtils.getMerchantNo());

        // save the segmentMember object and get the result
        segmentMember = segmentMemberService.validateAndSaveSegmentMember(segmentMember);

        // If the segmentMember object is not null ,then return the success object
        if ( segmentMember.getSgmId() != null ) {

            // Get the segmentMember id
            retData.setData(segmentMember.getSgmId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveSegmentMember - Response : Unable to save the segmentMember information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveSegmentMember - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/segmentation/segmentmember/delete/{sgmId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteSegmentMember(@PathVariable( value = "sgmId") Long sgmId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteSegmentMember - Request Received# Sgm ID No:"+ sgmId);
        log.info("deleteSegmentMember - "+generalUtils.getLogTextForRequest());


        // Get the segmentMember information
        SegmentMember segmentMember = segmentMemberService.findBySgmId(sgmId);

        // If no data found, then set error
        if ( segmentMember == null ) {

            // Log the response
            log.info("deleteSegmentMember - Response : No segmentMember information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Get the CustomerSegmentInformation for the item
        // Get the CustomerSegment info
        CustomerSegment customerSegment = customerSegmentService.findByCsgSegmentId(segmentMember.getSgmSegmentId());

        // Check if the customersegment exists and is authorized
        if ( customerSegment == null || customerSegment.getCsgMerchantNo() != merchantNo ) {

            // Log the message
            log.info("saveSegmentMember - Response : You are not authorized for the customer segment");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the segmentMember and set the retData fields
        segmentMemberService.validateAndDeleteSegmentMember(sgmId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete catId
        retData.setData(sgmId);


        // Log the response
        log.info("deleteSegmentMember - " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/segmentation/segmentmember/segment/{sgmSegmentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getSegmentMembersBySegmentId(@PathVariable(value = "sgmSegmentId") Long sgmSegmentId,Pageable pageable){



        log.info("getSegmentMembersBySegmentId - "+generalUtils.getLogTextForRequest());

        // Log the Request
        log.info("getSegmentMembersBySegmentId - Request Received# "+sgmSegmentId);

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the list of matching segmentMembers
        Page<SegmentMemberDetails> segmentMemberDetailsList = segmentMemberService.getMemberDetails(sgmSegmentId, pageable);

        // Get the list of the segmentMemberResources
        List<SegmentMemberDetailsResource> segmentMemberResourceList = segmentMemberDetailsAssembler.toResources(segmentMemberDetailsList);

        // Set the data
        retData.setData(segmentMemberResourceList);

        // Log the response
        log.info("getSegmentMembersBySegmentId - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/segmentation/segmentmember/customer/{sgmCustomerNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getSegmentMembersByCustomerNo(@PathVariable(value = "sgmCustomerNo") Long sgmCustomerNo){


        // Log the Request
        log.info("getSegmentMembersBySegmentId - Request Received# "+sgmCustomerNo);
        log.info("getSegmentMembersBySegmentId - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the list of matching segmentMembers
        List<SegmentMember> segmentMemberList = segmentMemberService.findBySgmCustomerNo(sgmCustomerNo);

        // Get the list of the segmentMemberResources
        List<SegmentMemberResource> segmentMemberResourceList = segmentMemberAssembler.toResources(segmentMemberList);

        // Set the data
        retData.setData(segmentMemberResourceList);

        // Log the response
        log.info("getSegmentMembersBySegmentId - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/assign/segment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject assignCustomerToSegment(@RequestParam(value = "loyaltyId") String loyaltyId,
                                                     @RequestParam(value = "segmentName") String segmentName) throws InspireNetzException {



        // Create the response object
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("assignCustomerToSegment - Request Received# Loyalty ID : "+loyaltyId + " - segment Name : " + segmentName) ;
        log.info("assignCustomerToSegment - "+generalUtils.getLogTextForRequest());

        Long merchantNo = generalUtils.getDefaultMerchantNo();

        // Call the saveData
        SegmentMember segmentMember = segmentMemberService.assignCustomerToSegment(loyaltyId,segmentName,merchantNo);

        // Set the status as success as otherwise for any error condition, we will
        // have the exception thrown from there
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the customer number
        retData.setData(segmentMember.getSgmId());

        // Log the response
        log.info("assignCustomerToSegment - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData
        return retData;


    }



}

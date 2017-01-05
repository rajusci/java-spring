package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.core.service.CustomerSegmentService;
import com.inspirenetz.api.rest.assembler.CustomerSegmentAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerSegmentResource;
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
import org.springframework.security.access.annotation.Secured;
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
public class CustomerSegmentController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CustomerSegmentController.class);

    @Autowired
    private CustomerSegmentService customerSegmentService;

    @Autowired
    private CustomerSegmentAssembler customerSegmentAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/merchant/customersegment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveCustomerSegment(@Valid CustomerSegment customerSegment,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the customerSegment
        customerSegment.setCsgMerchantNo(merchantNo);


        // Log the Request
        log.info("saveCustomerSegment - Request Received# "+customerSegment.toString());
        log.info("saveCustomerSegment - "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveCustomerSegment - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // Check if the customerSegment is existing
        boolean isExist = customerSegmentService.isDuplicateSegmentNameExisting(customerSegment);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveCustomerSegment - Response : CustomerSegment code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the customerSegment.getCsgSegmentId is  null, then set the created_by, else set the updated_by
        if ( customerSegment.getCsgSegmentId() == null ) {

            customerSegment.setCreatedBy(auditDetails);

        } else {

            customerSegment.setUpdatedBy(auditDetails);

        }


        // save the customerSegment object and get the result
        customerSegment = customerSegmentService.validateAndSaveCustomerSegment(customerSegment);

        // If the customerSegment object is not null ,then return the success object
        if ( customerSegment.getCsgSegmentId() != null ) {

            // Get the customerSegment id
            retData.setData(customerSegment.getCsgSegmentId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveCustomerSegment - Response : Unable to save the customerSegment information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveCustomerSegment - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/customersegment/delete/{csgSegmentId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteCustomerSegment(@PathVariable(value = "csgSegmentId") Long csgSegmentId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteCustomerSegment - Request Received# SegmentId -" +csgSegmentId);
        log.info("deleteCustomerSegment - "+generalUtils.getLogTextForRequest());


        // Get the CustomerSegment information
        CustomerSegment customerSegment = customerSegmentService.findByCsgSegmentId(csgSegmentId);

        // Check f the CustomerSegment exists
        if ( customerSegment == null ) {

            // Log the response
            log.info("deleteCustomerSegment - Response : No segment information found");

            // Throw the NO_DATA_FOUND exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( customerSegment.getCsgMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteCustomerSegment - Response : You are not authorized to delete the customerSegment");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the customerSegment and set the retData fields
        customerSegmentService.validateAndDeleteCustomerSegment(csgSegmentId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete csgId
        retData.setData(csgSegmentId);


        // Log the response
        log.info("deleteCustomerSegment - " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/customersegments/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCustomerSegments(
                                                    @PathVariable(value ="filter") String filter,
                                                    @PathVariable(value ="query") String query,
                                                    Pageable pageable
    ){


        // Log the Request
        log.info("listCustomerSegments - Request Received# filter = "+filter +" query = "+query);
        log.info("listCustomerSegments - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // List of the segment Resource
        List<CustomerSegmentResource> customerSegmentResourceList =  null;

        // Check thefilter
        if ( filter.equals("0") && query.equals("0") ) {

            // Get the list of matching customerSegments
            Page<CustomerSegment> customerSegmentList = customerSegmentService.findByCsgMerchantNo(merchantNo,pageable);

            // Get the list of the customerSegmentResources
            customerSegmentResourceList = customerSegmentAssembler.toResources(customerSegmentList);

            // Set the pageable param in the retDAta
            retData.setPageableParams(customerSegmentList);

        } else if ( filter.equals("name") ) {

            // Get the list of matching segments based on the name
            Page<CustomerSegment> customerSegmentList = customerSegmentService.findByCsgMerchantNoAndCsgSegmentNameLike(merchantNo, "%" + query + "%", pageable);

            // Get the list of Resources
            customerSegmentResourceList = customerSegmentAssembler.toResources(customerSegmentList);

            // Set the pageable param in the retDAta
            retData.setPageableParams(customerSegmentList);

        }




        // Set the status as succes
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(customerSegmentResourceList);


        // Log the response
        log.info("listCustomerSegments - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/customersegment/{csgSegmentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerSegment(
            @PathVariable(value ="csgSegmentId") Long csgSegmentId
    ) throws InspireNetzException {


        // Log the Request
        log.info("getCustomerSegment - Request Received# csgSegmentId "+csgSegmentId.toString());
        log.info("getCustomerSegment - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();



        // check if the customersegment is existing
        CustomerSegment customerSegment = customerSegmentService.findByCsgSegmentId(csgSegmentId);

        // Check if the redemption is found
        if ( customerSegment == null || customerSegment.getCsgSegmentId() == null) {

            // Log the response
            log.info("getCustomerSegment - Response : No customerSegment information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( customerSegment.getCsgMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getCustomerSegment - Response : You are not authorized to view the customerSegment");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Convert the segment to resource
        CustomerSegmentResource customerSegmentResource = customerSegmentAssembler.toResource(customerSegment);


        // Set the status as succes
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(customerSegmentResource);


        // Log the response
        log.info("getCustomerSegment -" + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/customersegment/refresh", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject refreshCustomerSegment() throws InspireNetzException {

        log.info("refreshCustomerSegment - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        boolean customerSegmentRefresh =customerSegmentService.refreshCustomerSegment();

        // Set the status as succes
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(customerSegmentRefresh);

        // Log the response
        log.info("refreshCustomerSegment -" + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }
}

package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.CouponDistribution;
import com.inspirenetz.api.core.domain.validator.CouponDistributionValidator;
import com.inspirenetz.api.core.service.CouponDistributionService;
import com.inspirenetz.api.rest.assembler.CouponDistributionAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CouponDistributionResource;
import com.inspirenetz.api.rest.resource.CouponResource;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class CouponDistributionController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CouponDistributionController.class);

    @Autowired
    private CouponDistributionService couponDistributionService;

    @Autowired
    private CouponDistributionAssembler couponDistributionAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    /*@RequestMapping(value = "/api/0.9/json/merchant/coupondistribution", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveCouponDistribution(@Valid CouponDistribution couponDistribution,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the couponDistribution
        couponDistribution.setCodMerchantNo(merchantNo);


        // Log the Request
        log.info("saveCouponDistribution - Request Received# "+couponDistribution.toString());
        log.info("saveCouponDistribution -  "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() ;



        // Create the Validator
        CouponDistributionValidator validator = new CouponDistributionValidator();

        // Call the validate function on the validator
        validator.validate(couponDistribution,result);

        

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveCouponDistribution - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // Check if the couponDistribution is existing
        boolean isExist = couponDistributionService.isDuplicateCouponDistributionExisting(couponDistribution);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveCouponDistribution - Response : CouponDistribution code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the couponDistribution.getCodId is  null, then set the created_by, else set the updated_by
        if ( couponDistribution.getCodId() == null ) {

            couponDistribution.setCreatedBy(auditDetails);

        } else {

            couponDistribution.setUpdatedBy(auditDetails);

        }


        // save the couponDistribution object and get the result
        couponDistribution = couponDistributionService.validateAndSaveCouponDistribution(couponDistribution);

        // If the couponDistribution object is not null ,then return the success object
        if ( couponDistribution.getCodId() != null ) {

            // Get the couponDistribution id
            retData.setData(couponDistribution.getCodId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveCouponDistribution - Response : Unable to save the couponDistribution information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveCouponDistribution - "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }*/

    @RequestMapping(value = "/api/0.9/json/merchant/coupondistribution", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveCouponDistribution(CouponDistributionResource couponDistributionResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("saveCouponDistribution - Request Received# "+couponDistributionResource.toString());
        log.info("saveCouponDistribution -  "+generalUtils.getLogTextForRequest());

        // save the couponDistribution object and get the result
        CouponDistribution couponDistribution = couponDistributionService.validateAndSaveCouponDistribution(couponDistributionResource);

        // If the couponDistribution object is not null ,then return the success object
        if ( couponDistribution.getCodId() != null ) {

            // Get the couponDistribution id
            retData.setData(couponDistribution.getCodId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveCouponDistribution - Response : Unable to save the couponDistribution information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveCouponDistribution - "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/coupondistribution/delete/{codId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteCouponDistribution(@PathVariable( value = "codId") Long codId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteCouponDistribution - Request Received# Product No:"+ codId);
        log.info("deleteCouponDistribution -  "+generalUtils.getLogTextForRequest());


        // Get the couponDistribution information
        CouponDistribution couponDistribution = couponDistributionService.findByCodId(codId);

        // If no data found, then set error
        if ( couponDistribution == null ) {

            // Log the response
            log.info("deleteCouponDistribution - Response : No couponDistribution information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Check if the passed object's merchant number and the current
        // authenticoded merchant number are same.
        // We need to raise an error if not
        if ( couponDistribution.getCodMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteCouponDistribution - Response : You are not authorized to delete the couponDistribution");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the couponDistribution and set the retData fields
        couponDistributionService.validateAndDeleteCouponDistribution(codId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete codId
        retData.setData(codId);

        // Log the response
        log.info("deleteCouponDistribution - "+generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/coupondistributions/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCouponDistributions(@PathVariable(value = "filter") String filter,
                                                     @PathVariable(value = "query") String query,
                                                     Pageable pageable){


        // Log the Request
        log.info("listCouponDistributions - Request Received# ");
        log.info("listCouponDistributions -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // List holding the resources to be returned
        List<CouponDistributionResource> couponDistributionResourceList =  new ArrayList<>(0);

        // Check the filter and call the appropriate
        if ( filter.equals("couponcode") ) {

            // Get the coupon distribution for the code
            CouponDistribution couponDistribution = couponDistributionService.findByCodMerchantNoAndCodCouponCode(merchantNo,query);

            // Convert to Resource
            CouponDistributionResource couponDistributionResource = couponDistributionAssembler.toResource(couponDistribution);

            // Add to the couponDistributionResourceList
            couponDistributionResourceList.add(couponDistributionResource);

        } else {

            // Get the couponDistribution
            Page<CouponDistribution> couponDistributionPage =  couponDistributionService.findByCodMerchantNo(merchantNo,pageable);

            // Covert the resource to list
            couponDistributionResourceList = couponDistributionAssembler.toResources(couponDistributionPage);

            // Set the pageable params
            retData.setPageableParams(couponDistributionPage);


        }



        // Set the data
        retData.setData(couponDistributionResourceList);

        // Log the response
        log.info("listCouponDistributions - "+generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/coupondistribution/{codId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCouponDistributionInfo(@PathVariable( value = "codId") Long codId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getCouponDistributionInfo - Request Received# Product No:"+ codId);
        log.info("getCouponDistributionInfo -  "+generalUtils.getLogTextForRequest());


        // Get the couponDistribution information
        CouponDistribution couponDistribution = couponDistributionService.findByCodId(codId);

        // If no data found, then set error
        if ( couponDistribution == null ) {

            // Log the response
            log.info("getCouponDistributionInfo - Response : No couponDistribution information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Check if the passed object's merchant number and the current
        // authenticoded merchant number are same.
        // We need to raise an error if not
        if ( couponDistribution.getCodMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getCouponDistributionInfo - Response : You are not authorized to delete the couponDistribution");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // Convert the coupon data to the resource
        CouponDistributionResource couponDistributionResource = couponDistributionAssembler.toResource(couponDistribution);

        // Set the data to the delete codId
        retData.setData(couponDistributionResource);

        // Log the response
        log.info("getCouponDistributionInfo - "+generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }




    @RequestMapping(value = "/api/0.9/json/merchant/coupondistribution/status/{codId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updateCouponDistributionStatus(@PathVariable( value = "codId") Long codId,
                                                            @RequestParam( value = "codStatus") Integer codStatus) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getCouponDistributionInfo - Request Received# Product No:"+ codId);
        log.info("getCouponDistributionInfo -  "+generalUtils.getLogTextForRequest());


        // Call the function to update the status
        boolean updated = couponDistributionService.updateCouponDistributionStatus(codId,codStatus,merchantNo,authSessionUtils.getUserNo());

        // Check if the updated flag is true
        if ( updated ) {

            retData.setStatus(APIResponseStatus.success);

        } else {

            retData.setStatus(APIResponseStatus.failed);

        }


        // Set the data as the codStatus
        retData.setData(Integer.toString(codStatus));

        // Log the response
        log.info("getCouponDistributionInfo - "+generalUtils.getLogTextForResponse(retData));



        // Return the retdata object
        return retData;
    }


}

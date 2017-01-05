package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.CouponService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.assembler.CouponAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
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
import java.util.Map;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class CouponController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponAssembler couponAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/coupon", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveCoupon(@Valid Coupon coupon,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the coupon
        coupon.setCpnMerchantNo(merchantNo);


        // Log the Request
        log.info("saveCoupon - Request Received# "+coupon.toString());
        log.info("saveCoupon - "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveCoupon - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }



        // Check if the coupon is existing with same name
        boolean isExist = couponService.isDuplicateCouponExisting(coupon);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveCoupon - Response : Coupon name is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // Check if the coupon code is valid
        boolean isValid = couponService.isCouponCodeValid(coupon);

        // Check the boolean value
        if ( !isValid ) {

            // Log the response
            log.info("saveCoupon - Response : Coupon code is overlapping with existing coupons");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

        }



        // If the coupon.getCpnCouponId is  null, then set the created_by, else set the updated_by
        if ( coupon.getCpnCouponId() == null ) {

            coupon.setCreatedBy(auditDetails);

        } else {

            coupon.setUpdatedBy(auditDetails);

        }


        // save the coupon object and get the result
        coupon = couponService.validateAndSaveCoupon(coupon);

        // If the coupon object is not null ,then return the success object
        if ( coupon.getCpnCouponId() != null ) {

            // Get the coupon id
            retData.setData(coupon.getCpnCouponId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveCoupon - Response : Unable to save the coupon information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveCoupon - "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/coupon/delete/{cpnCouponId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteCoupon(@PathVariable( value = "cpnCouponId") Long cpnCouponId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteCoupon - Request Received# Product No:"+ cpnCouponId);
        log.info("deleteCoupon - "+generalUtils.getLogTextForRequest());


        // Get the coupon information
        Coupon coupon = couponService.findByCpnCouponId(cpnCouponId);

        // If no data found, then set error
        if ( coupon == null ) {

            // Log the response
            log.info("deleteCoupon - Response : No coupon information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Check if the passed object's merchant number and the current
        // authenticpned merchant number are same.
        // We need to raise an error if not
        if ( coupon.getCpnMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteCoupon - Response : You are not authorized to delete the coupon");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the coupon and set the retData fields
        couponService.validateAndDeleteCoupon(cpnCouponId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete cpnId
        retData.setData(cpnCouponId);

        // Log the response
        log.info("deleteCoupon - "+generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/coupons/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCoupons(   @PathVariable(value = "filter") String filter,
                                            @PathVariable(value = "query")  String query,
                                            Pageable pageable){

        // Log the Request
        log.info("listCoupons - Request Received# ");
        log.info("listCoupons - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the list of matching coupons
        Page<Coupon> couponList;

        // Check the filter and query
        if ( filter.equals("name") ) {

            // Get the couponList
            couponList = couponService.findByCpnMerchantNoAndCpnCouponNameLike(merchantNo, "%" + query + "%", pageable);

        } else {

            // Get the couponList
            couponList = couponService.findByCpnMerchantNo(merchantNo,pageable);

        }


        // Get the list of the couponResources
        List<CouponResource> couponResourceList = couponAssembler.toResources(couponList);

        // Set the data
        retData.setData(couponResourceList);

        // set the pageable params
        retData.setPageableParams(couponList);



        // Log the response
        log.info("listCoupons - "+generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/coupon/validate/{cpnCouponCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject validateCoupon(@PathVariable(value = "cpnCouponCode") String cpnCouponCode,Purchase purchase) throws InspireNetzException {

        // Log the Request
        log.info("validateCoupon - Request Received# CouponCode:"+cpnCouponCode);
        log.info("validateCoupon - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Get the coupon information
        Coupon coupon = couponService.findByCpnMerchantNoAndCpnCouponCode(merchantNo,cpnCouponCode);

        // Check if the information is found
        if ( coupon == null || coupon.getCpnCouponId() == null ) {

            // Log the response
            log.info("validateCoupon - Response : No coupon information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if its valid
        boolean isValid = couponService.validateCoupon(coupon,purchase,new ArrayList<PurchaseSKU>());

        // Check the validity and return the object
        if ( isValid ) {

            // Set the status as success
            retData.setStatus(APIResponseStatus.success);

            // Set the data as valid
            retData.setData("valid");

        } else  {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the data as invalid
            retData.setData("invalid");
        }

        // Log the response
        log.info("validateCoupon - "+generalUtils.getLogTextForResponse(retData));

        // Return the object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/coupon/evaluate/{cpnCouponCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject evaluateCoupon(@PathVariable(value = "cpnCouponCode") String cpnCouponCode,Purchase purchase) throws InspireNetzException {

        // Log the Request
        log.info("evaluateCoupon - Request Received# ");
        log.info("evaluateCoupon - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Get the coupon information
        Coupon coupon = couponService.findByCpnMerchantNoAndCpnCouponCode(merchantNo,cpnCouponCode);

        // Check if the information is found
        if ( coupon == null || coupon.getCpnCouponId() == null ) {

            // Log the response
            log.info("evaluateCoupon - Response : No coupon information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Get the couponValue
        double couponValue = couponService.evaluateCoupon(coupon,purchase,new ArrayList<PurchaseSKU>());



        // Set the status as success
        retData.setStatus(APIResponseStatus.success);

        // Set the data as valid
        retData.setData(couponValue);

        // Log the response
        log.info("evaluateCoupon - "+generalUtils.getLogTextForResponse(retData));

        // Return the object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/coupon/{cpnCouponId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCouponInfo( @PathVariable(value = "cpnCouponId") Long cpnCouponId) throws InspireNetzException {

        // Log the Request
        log.info("getCouponInfo - Request Received# CouponId:"+cpnCouponId);
        log.info("getCouponInfo - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Get the coupon information
        Coupon coupon = couponService.findByCpnCouponId(cpnCouponId);

        // If no data found, then set error
        if ( coupon == null ) {

            // Log the response
            log.info("getCouponInfo - Response : No coupon information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Check if the passed object's merchant number and the current
        // authenticpned merchant number are same.
        // We need to raise an error if not
        if ( coupon.getCpnMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getCouponInfo - Response : You are not authorized to delete the coupon");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // Convert the coupon to CouponResource
        CouponResource couponResource = couponAssembler.toResource(coupon);

        // Set the data in the retData
        retData.setData(couponResource);



        // Log the response
        log.info("getCouponInfo - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/coupons/{merchantNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCouponsForCustomer(@PathVariable(value = "merchantNo") Long merchantNo) throws InspireNetzException {

        // Log the Request
        log.info("getCouponsForCustomer - Request Received# MerchantNo "+merchantNo);
        log.info("getCouponsForCustomer - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the loyaltyId of user
        String  loyaltyId = authSessionUtils.getUserLoginId();

        List<Coupon> coupons = couponService.getCustomerCouponsCompatible(loyaltyId,merchantNo);

        // Convert the coupon to CouponResource
        List<CouponResource> couponResources = couponAssembler.toResources(coupons);

        // Set the data in the retData
        retData.setData(couponResources);

        // Log the response
        log.info("getCouponsForCustomer - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/coupons/compatible", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCouponsForCustomerCompatible(@RequestParam(value = "merchant_no") Long merchantNo) throws InspireNetzException {

        // Log the Request
        log.info("getCouponsForCustomer - Request Received# MerchantNo:"+merchantNo);
        log.info("getCouponsForCustomer - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the loyaltyId of user
        String  loyaltyId = authSessionUtils.getUserLoginId();

        List<Map<String , String>> couponData = couponService.getCouponsCompatible(loyaltyId,merchantNo);

        // Set the data in the retData
        retData.setData(couponData);

        // Log the response
        log.info("getCouponsForCustomer - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/user/coupons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCouponsForUser(@RequestParam(value = "query",defaultValue = "") String query,
                                               @RequestParam(value = "merchantNo",defaultValue = "0") Long merchantNo,Pageable pageable) throws InspireNetzException {

        // Log the Request
        log.info("getCouponsForUser - Request Received# MerchantNo:"+merchantNo+"query :"+query);
        log.info("getCouponsForUser - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the loyaltyId of user
        String  usrLoginId = authSessionUtils.getUserLoginId();

        Page<Coupon> coupons = couponService.getCouponsForUser(usrLoginId,merchantNo);

        // Convert the coupon to CouponResource
        List<CouponResource> couponResources = couponAssembler.toResources(coupons);

        // Set the data in the retData
        retData.setData(couponResources);

        // Log the response
        log.info("getCouponsForUser - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

}

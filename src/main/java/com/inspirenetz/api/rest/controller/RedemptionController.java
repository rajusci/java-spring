package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Redemption;
import com.inspirenetz.api.core.domain.validator.CatalogueRedemptionRequestValidator;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.RedemptionService;
import com.inspirenetz.api.core.service.RewardCurrencyService;
import com.inspirenetz.api.rest.assembler.RedemptionAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CashBackCompatibleResource;
import com.inspirenetz.api.rest.resource.RedemptionResource;
import com.inspirenetz.api.util.*;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 16/2/14.
 */
@Controller
public class RedemptionController {


    @Autowired
    RedemptionService redemptionService;

    @Autowired
    CustomerService customerService;

    @Autowired
    private RedemptionAssembler redemptionAssembler;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(RedemptionController.class);


    @RequestMapping(value = "/api/0.9/json/customer/redemption", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject redeemCatalogue(@RequestParam Map<String,String> params) throws InspireNetzException {


        // Get the loginId of the logged in user
        String usrLoginId = authSessionUtils.getUserLoginId();

        // get the user number of the currently requesting user
        Long userNo = authSessionUtils.getUserNo();

        // Log the Request
        log.info("redeemCatalogue - Request Received# "+params.toString());
        log.info("redeemCatalogue -  "+generalUtils.getLogTextForRequest());


        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

        // Get the data
        List<RedemptionCatalogue> redemptionCatalogueList = redemptionService.getRedemptionCatalogues(params);

        // Create the CatalogueRedemptionRequest object
        CatalogueRedemptionRequest catalogueRedemptionRequest = new CatalogueRedemptionRequest();

        // Set the list
        catalogueRedemptionRequest.setRedemptionCatalogues(redemptionCatalogueList);;

        // Set the Redemption user no
        catalogueRedemptionRequest.setUserNo(userNo);



        // Check if the merchant_no is existing in the request
        if ( params.containsKey("merchant_no") ) {

            // Set the merchantNo
            catalogueRedemptionRequest.setMerchantNo(Long.parseLong(params.get("merchant_no")));

            // We need to get the customer informatoin for the customer
            Customer customer = customerService.findByCusEmailAndCusMerchantNo(usrLoginId,catalogueRedemptionRequest.getMerchantNo());


            // Set the customer loyalty id
            if ( customer == null || customer.getCusLoyaltyId() == null ) {

                catalogueRedemptionRequest.setLoyaltyId("");

            } else {

                catalogueRedemptionRequest.setLoyaltyId(customer.getCusLoyaltyId());

            }

        }



        // Set the delivery_ind
        if ( params.containsKey("delivery_ind")) {

            catalogueRedemptionRequest.setDeliveryInd(Integer.parseInt(params.get("delivery_ind")));

        } else {

            catalogueRedemptionRequest.setDeliveryInd(0);

        }

        // Set the address1
        catalogueRedemptionRequest.setAddress1(params.get("address1"));

        // Set the address2
        catalogueRedemptionRequest.setAddress2(params.get("address2"));

        // Set the address3
        catalogueRedemptionRequest.setAddress3(params.get("address3"));

        // Set the city
        catalogueRedemptionRequest.setCity(params.get("city"));

        // Set the state
        catalogueRedemptionRequest.setState(params.get("state"));

        // Set the pincode
        catalogueRedemptionRequest.setPincode(params.get("pincode"));

        // Set the contact number
        catalogueRedemptionRequest.setContactNumber(params.get("contact"));



        // Create the Validator object
        CatalogueRedemptionRequestValidator validator = new CatalogueRedemptionRequestValidator();

        // Create the
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(catalogueRedemptionRequest,"catalogueRedemptionRequest");

        // Validate
        validator.validate(catalogueRedemptionRequest,result);


        // If the result has errors, then show the message
        if ( result.hasErrors() ) {

            // Log the response
            log.info("redeemCatalogue - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }



        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() ;

        // Set the auditDetails to the request object
        catalogueRedemptionRequest.setAuditDetails(auditDetails);


        // Create the CatalogueRedemptionResponse object
        CatalogueRedemptionResponse catalogueRedemptionResponse;

        // Set the APIResposneObject to be response from the doCatalogueRedemptioncall
        catalogueRedemptionResponse = redemptionService.validateAndDoCatalogueRedemption(catalogueRedemptionRequest);

        // Check the status and return the object
        if (catalogueRedemptionResponse.getStatus().equals("success") ) {

            // Set the status
            retData.setStatus(APIResponseStatus.success);

            // Set the data
            retData.setData(catalogueRedemptionResponse.getCatalogueRedemptionItemResponseList());

            // Log the response object
            log.info("redeemCatalogue  -  " + generalUtils.getLogTextForResponse(retData));

        } else {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the errorcode
            retData.setErrorCode(catalogueRedemptionResponse.getErrorcode());

        }

        // Return the retData object
        return  retData;

    }



    @RequestMapping(value = "/api/0.9/json/merchant/test/redemption/checkout", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject redeemCatalogueMerchant(@RequestParam Map<String,String> params) throws InspireNetzException {



        // get the user number of the currently requesting user
        Long userNo = authSessionUtils.getUserNo();

        // Log the Request
        log.info("redeemCatalogue - Request Received# "+params.toString());
        log.info("redeemCatalogue -  "+generalUtils.getLogTextForRequest());



        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

        // Get the data
        List<RedemptionCatalogue> redemptionCatalogueList = redemptionService.getRedemptionCatalogues(params);

        // Create the CatalogueRedemptionRequest object
        CatalogueRedemptionRequest catalogueRedemptionRequest = new CatalogueRedemptionRequest();

        // Set the list
        catalogueRedemptionRequest.setRedemptionCatalogues(redemptionCatalogueList);;

        // Set the Redemption user no
        catalogueRedemptionRequest.setUserNo(userNo);

        // Set the merchantNo
        catalogueRedemptionRequest.setMerchantNo(authSessionUtils.getMerchantNo());




        // Check if the loyalty  is existing in the request
        if ( params.containsKey("loyalty_id") ) {

            // Get the loyaltyId
            String loyaltyId = params.get("loyalty_id");

            // We need to get the customer informatoin for the customer
            Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId, catalogueRedemptionRequest.getMerchantNo());

            // Set the customer loyalty id
            if ( customer == null || customer.getCusLoyaltyId() == null ) {

                catalogueRedemptionRequest.setLoyaltyId("");

            } else {

                catalogueRedemptionRequest.setLoyaltyId(customer.getCusLoyaltyId());

            }

        } else {

            // Log the error message
            log.info("redeemCatalogueMerchant - No customer information found");

            // Throw the error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);



        }



        // Set the delivery_ind
        if ( params.containsKey("delivery_ind")) {

            catalogueRedemptionRequest.setDeliveryInd(Integer.parseInt(params.get("delivery_ind")));

        } else {

            catalogueRedemptionRequest.setDeliveryInd(0);

        }

        // Set the address1
        catalogueRedemptionRequest.setAddress1(params.get("address1"));

        // Set the address2
        catalogueRedemptionRequest.setAddress2(params.get("address2"));

        // Set the address3
        catalogueRedemptionRequest.setAddress3(params.get("address3"));

        // Set the city
        catalogueRedemptionRequest.setCity(params.get("city"));

        // Set the state
        catalogueRedemptionRequest.setState(params.get("state"));

        // Set the pincode
        catalogueRedemptionRequest.setPincode(params.get("pincode"));

        // Set the contact number
        catalogueRedemptionRequest.setContactNumber(params.get("contact"));



        // Create the Validator object
        CatalogueRedemptionRequestValidator validator = new CatalogueRedemptionRequestValidator();

        // Create the
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(catalogueRedemptionRequest,"catalogueRedemptionRequest");

        // Validate
        validator.validate(catalogueRedemptionRequest,result);


        // If the result has errors, then show the message
        if ( result.hasErrors() ) {

            // Log the response
            log.info("redeemCatalogue - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }



        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() ;

        // Set the auditDetails to the request object
        catalogueRedemptionRequest.setAuditDetails(auditDetails);


        // Create the CatalogueRedemptionResponse object
        CatalogueRedemptionResponse catalogueRedemptionResponse;

        // Set the APIResposneObject to be response from the doCatalogueRedemptioncall
        catalogueRedemptionResponse = redemptionService.validateAndDoCatalogueRedemption(catalogueRedemptionRequest);

        // Check the status and return the object
        if (catalogueRedemptionResponse.getStatus().equals("success") ) {

            // Set the status
            retData.setStatus(APIResponseStatus.success);

            // Set the data
            retData.setData(catalogueRedemptionResponse.getCatalogueRedemptionItemResponseList());

            // Log the response object
            log.info("redeemCatalogue -  " + generalUtils.getLogTextForResponse(retData));

        } else {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the errorcode
            retData.setErrorCode(catalogueRedemptionResponse.getErrorcode());

        }

        // Return the retData object
        return  retData;

    }



//    @RequestMapping(value = "/api/0.9/json/transaction/cashback", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @ResponseBody
//    public HashMap<String,String> doCashbackCompatible(
//                @RequestParam(value="cardnumber") String loyaltyId,
//                @RequestParam(value="rwd_currency_id") Long rwdCurrencyId,
//                @RequestParam(value="purchase_amount",defaultValue = "0") double amount,
//                @RequestParam(value="txnref") String txnRef  ) {
//
//
//
//
//            // Define the HashMap holding the return information
//            HashMap<String,String> retData = new HashMap<>();
//
//            // Create the CashBackRedemptionRequest object
//            CashBackRedemptionRequest cashbackRedemptionRequest = new CashBackRedemptionRequest();
//
//            // Get the merchantNo;
//            Long merchantNo = authSessionUtils.getMerchantNo();
//
//            // Get the user location
//            Long userLocation = authSessionUtils.getUserLocation();
//
//            // get the user number for the currenlty logged in user
//            Long userNo = authSessionUtils.getUserNo();
//
//
//            // Log the Request
//            log.info("doCashbackCompatible - Request Received# "+
//                        "cardnumber="+loyaltyId + " - "+
//                        "rwd_currency_id="+rwdCurrencyId + " - " +
//                        "purchase_amount="+Double.toString(amount) + "-" +
//                        "txnref="+txnRef
//            );
//            log.info("doCashbackCompatible -  "+generalUtils.getLogTextForRequest());
//
//
//
//
//            // Set the fields for the CashBackRedemptionRequest
//            //
//            // Set the merchantNo
//            cashbackRedemptionRequest.setMerchantNo(merchantNo);
//
//            // Set the location
//            cashbackRedemptionRequest.setUserLocation(userLocation);
//
//            // Set the user number to the request
//            cashbackRedemptionRequest.setUserNo(userNo);
//
//
//            // Set the loyaltyid
//            cashbackRedemptionRequest.setLoyaltyId(loyaltyId);
//
//            // Set the reward currency id
//            cashbackRedemptionRequest.setRewardCurrencyId(rwdCurrencyId);
//
//            // set the amount
//            cashbackRedemptionRequest.setAmount(amount);
//
//            // Set the reference
//            cashbackRedemptionRequest.setTxnRef(txnRef);
//
//            // Hold the audit details
//            String auditDetails = authSessionUtils.getUserNo().toString() ;
//
//            // Set the auditDetails
//            cashbackRedemptionRequest.setAuditDetails(auditDetails);
//
//            // Call the doCashbackFunction and then get the response
//            CashBackRedemptionResponse response = redemptionService.doCashbackRedemption(cashbackRedemptionRequest);
//
//
//
//            // Map the values of the resposne to the retData hashmap
//            // Set the status
//            retData.put("status",response.getStatus());
//
//            // Set the error message
//            retData.put("balance", Double.toString(response.getBalance()));
//
//            // Set the error code
//            retData.put("errorcode", response.getErrorcode());
//
//
//            // Log the response
//            log.info("doCashbackCompatible -  Response" +retData);
//
//
//            // Return the retData
//            return retData;
//
//    }


    @RequestMapping(value = "/api/0.9/json/merchant/redemption/paymentstatus/{trackingId}", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updatePaymentStatus(
                                                    @PathVariable(value="trackingId") String trackingId,
                                                    @RequestParam(value="status") Integer status

                                                ) throws InspireNetzException {

        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

        // Get the merchantNo of the currently logged in merchant user
        Long merchantNo = authSessionUtils.getMerchantNo();

        // get the user number of the currently requesting user
        Long userNo = authSessionUtils.getUserNo();


        // Log the Request
        log.info("updatePaymentStatus - Request Received# trackingId="+trackingId + " - status "+ status);
        log.info("updatePaymentStatus -  "+generalUtils.getLogTextForRequest());


        // Get the list of all the Redemptions for the trackingId
        List<Redemption> redemptionList = redemptionService.findByRdmMerchantNoAndRdmUniqueBatchTrackingId(merchantNo,trackingId);

        // Check if data exists
        if ( redemptionList == null || redemptionList.isEmpty() ) {

            // Log the response
            log.info("updatePaymentStatus - Response : No data found for the tracking id");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }



        // Go through each of the items and then update the status and save
        for(Redemption redemption : redemptionList ) {

            // Set the status
            redemption.setRdmCashPaymentStatus(status);

            // Log the informaiton
            log.info("updatePaymentStatus - Updated status for " + redemption.getRdmId());

        }


        // Save the list back
        redemptionService.saveAll(redemptionList);


        // Set the status as success
        retData.setStatus(APIResponseStatus.success);

        // Set the data as updated status
        retData.setData(status);


        // Log the response
        log.info("updatePaymentStatus -  " + generalUtils.getLogTextForResponse(retData));



        // Return the object
        return retData;



    }



    @RequestMapping(value = "/api/0.9/json/merchant/redemption/status/{trackingId}", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updateRedemptionStatus(
            @PathVariable(value="trackingId") String trackingId,
            @RequestParam(value="status") Integer status

    ) throws InspireNetzException {

        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

        // Get the merchantNo of the currently logged in merchant user
        Long merchantNo = authSessionUtils.getMerchantNo();

        // get the user number of the currently requesting user
        Long userNo = authSessionUtils.getUserNo();


        // Log the Request
        log.info("updateRedemptionStatus - Request Received# trackingId="+trackingId + " - status "+ status);
        log.info("updateRedemptionStatus -  "+generalUtils.getLogTextForRequest());


        // Get the list of all the Redemptions for the trackingId
        List<Redemption> redemptionList = redemptionService.findByRdmMerchantNoAndRdmUniqueBatchTrackingId(merchantNo,trackingId);

        // Check if data exists
        if ( redemptionList == null || redemptionList.isEmpty() ) {

            // Log the response
            log.info("updateRedemptionStatus - Response : No data found for the tracking id");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }



        // Go through each of the items and then update the status and save
        for(Redemption redemption : redemptionList ) {

            // Set the status
            redemption.setRdmStatus(status);

            // Log the informaiton
            log.info("updateRedemptionStatus - Updated status for " + redemption.getRdmId());

        }


        // Save the list back
        redemptionService.saveAll(redemptionList);


        // Set the status as success
        retData.setStatus(APIResponseStatus.success);

        // Set the data as updated status
        retData.setData(status);


        // Log the response
        log.info("updateRedemptionStatus -  " + generalUtils.getLogTextForResponse(retData));



        // Return the object
        return retData;



    }



    @RequestMapping(value = "/api/0.9/json/merchant/redemptions/{filter}/{query}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRedemptions(
            @PathVariable(value="filter") String filter,
            @PathVariable(value="query") String query,
            @RequestParam(value="rdmType",defaultValue = "1",required = false) Integer rdmType,
            @RequestParam(value="rdmStartDate",required = false) Date rdmStartDate,
            @RequestParam(value="rdmEndDate", required = false) Date rdmEndDate,
            Pageable pageable
    ) {


        // Get the merchantNo of the currently logged in merchant user
        Long merchantNo = authSessionUtils.getMerchantNo();

        // get the user number of the currently requesting user
        Long userNo = authSessionUtils.getUserNo();


        // If the startDate is null, then  set the date to 0000-00-00
        if ( rdmStartDate == null ) {

            rdmStartDate = DBUtils.covertToSqlDate("0000-00-00");

        }

        // If the endDate is null, then  set the date to 9999-12-31
        if ( rdmEndDate == null ) {

            rdmEndDate = DBUtils.covertToSqlDate("9999-12-31");

        }

        // Log the Request
        log.info("listRedem ptionByType - Request Received# "+
                "filter="+filter + " - "+
                "query="+query + " - "+
                "rdmStartDate="+rdmStartDate.toString() + " - "+
                "rdmEndDate="+rdmEndDate.toString() + " - "
        );
        log.info("listRedemptions -  "+generalUtils.getLogTextForRequest());


        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();


        // Variable holding the resource list
        List<RedemptionResource> redemptionResourceList = new ArrayList<>(0);

        // Check the filter
        if ( filter.equals("loyaltyid") ) {

            // Get the redemption list
            Page<Redemption> redemptionPage = redemptionService.findByRdmMerchantNoAndRdmLoyaltyIdAndRdmTypeAndRdmDateBetween(merchantNo, query, rdmType, rdmStartDate, rdmEndDate, pageable);

            //Convert to resources
            redemptionResourceList = redemptionAssembler.toResources(redemptionPage);

            // Set the pagable params
            retData.setPageableParams(redemptionPage);
        }
            


        // Set the retData status as success
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(redemptionResourceList);



        // Log the response
        log.info("listRedemptions -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return  retData;

    }



    @RequestMapping(value = "/api/0.9/json/merchant/redemption/{rdmId}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRedemptionInfo(
            @PathVariable(value="rdmId") Long rdmId
    ) throws InspireNetzException {


        // Get the merchantNo of the currently logged in merchant user
        Long merchantNo = authSessionUtils.getMerchantNo();

        // get the user number of the currently requesting user
        Long userNo = authSessionUtils.getUserNo();

        // Log the Request
        log.info("getRedemptionInfo - Request Received# rdmId :" +rdmId);
        log.info("getRedemptionInfo -  "+generalUtils.getLogTextForRequest());


        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();


        // Get the information for the Redemption
        Redemption redemption = redemptionService.findByRdmId(rdmId);

        // Check if the redemption is found
        if ( redemption == null || redemption.getRdmId() == null) {

            // Log the response
            log.info("getRedemptionInfo - Response : No redemption information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( redemption.getRdmMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getRedemptionInfo - Response : You are not authorized to view the redemption");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // conver to REdemptionResourc
        RedemptionResource redemptionResource = redemptionAssembler.toResource(redemption);

        // Set the retData status as success
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(redemptionResource);



        // Log the response
        log.info("listRedemptions -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return  retData;

    }



    @RequestMapping(value = "/api/0.9/json/merchant/redemptions/requests/{filter}/{query}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRedemptionsRequests(
            @PathVariable(value="filter") String filter,
            @PathVariable(value="query") String query ,
            @RequestParam(value="status",defaultValue = "0") Integer status,
            Pageable pageable
    ) throws InspireNetzException {


        // Get the merchantNo of the currently logged in merchant user
        Long merchantNo = authSessionUtils.getMerchantNo();

        // get the user number of the currently requesting user
        Long userNo = authSessionUtils.getUserNo();


        // Log the Request
        log.info("listRedemptionRequests - Request Received# filter : "+filter + " query : " + query);
        log.info("listRedemptionRequests -  "+generalUtils.getLogTextForRequest());



        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

        // Get the redemption list
        Page<Redemption> redemptionPage = redemptionService.listRedemptionRequests(merchantNo,filter,query,status,pageable);



        // Check if the redemptionPage is null
        if ( redemptionPage == null ) {

            // Log the response
            log.info("listRedemptionRequests - Response : Please specify all required fields properly");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

        }



        // Convert the response to  resources
        List<RedemptionResource> redemptionResourceList = redemptionAssembler.toResources(redemptionPage);

        // Set the pageable params for the retData
        retData.setPageableParams(redemptionPage);

        // Set the retData status as success
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(redemptionResourceList);

        // Log the response
        log.info("listRedemptionRequests -  " + generalUtils.getLogTextForResponse(retData));



        // Return the retdata object
        return  retData;

    }



    @RequestMapping(value = "/api/0.9/json/merchant/redemptions/request/trackingid/{trackingId}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRedemptionRequestsForTrackingId(
            @PathVariable(value="trackingId") String trackingId
    ) throws InspireNetzException {


        // Get the merchantNo of the currently logged in merchant user
        Long merchantNo = authSessionUtils.getMerchantNo();

        // get the user number of the currently requesting user
        Long userNo = authSessionUtils.getUserNo();


        // Log the Request
        log.info("listRedemptionRequestsForTrackingId - Request Received# "+
                "trackingId="+trackingId + " - "
        );
        log.info("listRedemptionRequestsForTrackingId -  "+generalUtils.getLogTextForRequest());




        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

        List<Redemption> redemptionList =new ArrayList<>();

        // Get the list of redemptions for the given tracking id
        if(!trackingId.equals("0")){

         redemptionList = redemptionService.findByRdmMerchantNoAndRdmUniqueBatchTrackingId(merchantNo, trackingId);

        }


        // If the redemptionList is empty , then we need to show the information as not found
        if ( redemptionList == null || redemptionList.isEmpty() ) {

            // Log the response
            log.info("listRedemptionRequestsForTrackingId - Response : No data found for the tracking id");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);


        }

        // Convert the response to  resources
        List<RedemptionResource> redemptionResourceList = redemptionAssembler.toResources(redemptionList);

        // Set the retData status as success
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(redemptionResourceList);

        // Log the response
        log.info("listRedemptionRequestsForTrackingId -  " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return  retData;

    }

    @RequestMapping(value = "/api/0.9/json/merchant/catalogueredemption", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject redeemCatalogueSingleItemMerchant(@RequestParam(value="loyaltyid") String loyaltyId,
                                                               @RequestParam(value="prdcode") String prdCode,
                                                               @RequestParam(value="channel",defaultValue = "1") Integer rdmChannel,
                                                               @RequestParam(value="destLoyaltyId",defaultValue = "0") String destLoyaltyId,
                                                               @RequestParam(value="quantity") Integer quantity,
                                                               @RequestParam(value="deliveryAddress1", required = false)String rdmDeliveryAddress1,
                                                               @RequestParam(value="deliveryAddress2", required = false)String rdmDeliveryAddress2,
                                                               @RequestParam(value="deliveryAddress3", required = false)String rdmDeliveryAddress3,
                                                               @RequestParam(value="deliveryCity", required = false)String rdmDeliveryCity,
                                                               @RequestParam(value="deliveryState", required = false)String rdmDeliveryState,
                                                               @RequestParam(value="deliveryCountry", required = false)String rdmDeliveryCountry,
                                                               @RequestParam(value="deliveryPostCode", required = false)String rdmDeliveryPostCode,
                                                               @RequestParam(value="contactNo", required= false)String rdmContactNumber

                                                                 ) throws InspireNetzException {

        // get the user number of the currently requesting user
        Long userNo = authSessionUtils.getUserNo();

        Long merchantNo = authSessionUtils.getMerchantNo();

        //Get the catalogue redemption request object
        CatalogueRedemptionItemRequest catalogueRedemptionItemRequest = redemptionService.getCatalogueRedemptionRequestObject(loyaltyId,prdCode,quantity,merchantNo,rdmChannel,destLoyaltyId,rdmDeliveryAddress1, rdmDeliveryAddress2, rdmDeliveryAddress3, rdmDeliveryCity, rdmDeliveryState, rdmDeliveryCountry, rdmDeliveryPostCode, rdmContactNumber);

        // Log the Request
        log.info("redeemCatalogueSingleItemMerchant -  "+generalUtils.getLogTextForRequest());


        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

       CatalogueRedemptionItemResponse catalogueRedemptionItemResponse = new CatalogueRedemptionItemResponse();

        // Set the APIResposneObject to be response from the redeemCatalogueItems
        catalogueRedemptionItemResponse = redemptionService.redeemCatalogueItems(catalogueRedemptionItemRequest);

        // Check the status and return the object
        if (catalogueRedemptionItemResponse != null ) {

            // Set the status
            retData.setStatus(APIResponseStatus.success);

            // Set the data
            retData.setData(catalogueRedemptionItemResponse);

            // Log the response object
            log.info("redeemCatalogue -  " + generalUtils.getLogTextForResponse(retData));

        } else {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.failed);

        }


        // Return the retData object
        return  retData;

    }

    @RequestMapping(value = "/api/0.9/json/customer/catalogueredemption", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject redeemCatalogueSingleItemCustomer(@RequestParam(value="prdcode") String prdCode,
                                                               @RequestParam(value="quantity") Integer quantity,
                                                               @RequestParam(value="channel",defaultValue = "1") Integer rdmChannel,
                                                               @RequestParam(value="destLoyaltyId",defaultValue = "0") String destLoyaltyId,
                                                               @RequestParam(value="merchantno") Long merchantNo,
                                                               @RequestParam(value="deliveryAddress1", required = false)String rdmDeliveryAddress1,
                                                               @RequestParam(value="deliveryAddress2", required = false)String rdmDeliveryAddress2,
                                                               @RequestParam(value="deliveryAddress3", required = false)String rdmDeliveryAddress3,
                                                               @RequestParam(value="deliveryCity", required = false)String rdmDeliveryCity,
                                                               @RequestParam(value="deliveryState", required = false)String rdmDeliveryState,
                                                               @RequestParam(value="deliveryCountry", required = false)String rdmDeliveryCountry,
                                                               @RequestParam(value="deliveryPostCode", required = false)String rdmDeliveryPostCode,
                                                               @RequestParam(value="contactNo", required = false)String rdmContactNumber) throws InspireNetzException {


        // get the user number of the currently requesting user
        Long userNo = authSessionUtils.getUserNo();

        String loyaltyId = authSessionUtils.getUserLoginId();

        //Get the catalogue redemption request object
        CatalogueRedemptionItemRequest catalogueRedemptionItemRequest = redemptionService.getCatalogueRedemptionRequestObject(loyaltyId,prdCode,quantity,merchantNo,rdmChannel,destLoyaltyId,rdmDeliveryAddress1, rdmDeliveryAddress2, rdmDeliveryAddress3, rdmDeliveryCity, rdmDeliveryState, rdmDeliveryCountry, rdmDeliveryPostCode, rdmContactNumber);

        // Log the Request
        log.info("redeemCatalogueSingleItemCustomer - Request Received# User# "+userNo.toString());
        log.info("redeemCatalogueSingleItemCustomer -  "+generalUtils.getLogTextForRequest());

        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

        CatalogueRedemptionItemResponse catalogueRedemptionItemResponse = new CatalogueRedemptionItemResponse();

        // Set the APIResposneObject to be response from the redeemCatalogueItems
        catalogueRedemptionItemResponse = redemptionService.redeemCatalogueItems(catalogueRedemptionItemRequest);

        // Check the status and return the object
        if (catalogueRedemptionItemResponse != null ) {

            // Set the status
            retData.setStatus(APIResponseStatus.success);

            // Set the data
            retData.setData(catalogueRedemptionItemResponse.getCrbRewardBalance());

            // Log the response
            log.info("redeemCatalogueSingleItemCustomer - " + generalUtils.getLogTextForResponse(retData));

        } else {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.failed);

        }


        // Return the retData object
        return  retData;

    }

    @RequestMapping(value = "/api/0.9/json/user/catalogueredemption", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject redeemCatalogueSingleItemUser(@RequestParam(value="prdcode") String prdCode,
                                                               @RequestParam(value="quantity") Integer quantity,
                                                               @RequestParam(value="channel",defaultValue = "1") Integer rdmChannel,
                                                               @RequestParam(value="destLoyaltyId",defaultValue = "0") String destLoyaltyId,
                                                               @RequestParam(value="merchantno") Long merchantNo
                                                               ) throws InspireNetzException {


        // get the user number of the currently requesting user

        String userLoginId = authSessionUtils.getUserLoginId();

        // Log the Request
        log.info("redeemCatalogueSingleItemCustomer - Request Received# User# ");
        log.info("redeemCatalogueSingleItemCustomer -  "+generalUtils.getLogTextForRequest());

        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

        CatalogueRedemptionItemResponse catalogueRedemptionItemResponse = new CatalogueRedemptionItemResponse();

        // Set the APIResposneObject to be response from the redeemCatalogueItems
        catalogueRedemptionItemResponse = redemptionService.redeemCatalogueItemsForUser(userLoginId,prdCode,quantity,merchantNo, rdmChannel, destLoyaltyId);

        // Check the status and return the object
        if (catalogueRedemptionItemResponse != null ) {

            // Set the status
            retData.setStatus(APIResponseStatus.success);

            // Set the data
            retData.setData(catalogueRedemptionItemResponse.getCrbRewardBalance());

            // Log the response
            log.info("redeemCatalogueSingleItemCustomer - " + generalUtils.getLogTextForResponse(retData));

        } else {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.failed);

        }


        // Return the retData object
        return  retData;

    }


    @RequestMapping(value = "/api/0.9/json/customer/redemption/compatible", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject redeemCatalogueCompatible(@RequestParam Map<String,String> params) throws InspireNetzException {


        // Get the loginId of the logged in user
        String loyaltyId = authSessionUtils.getUserLoginId();

        // get the user number of the currently requesting user
        Long userNo = authSessionUtils.getUserNo();

        Long merchantNo = 0L;

        String destLoyaltyId = "0";

        // Log the Request
        log.info("redeemCatalogueCompatible - Request Received# "+params.toString());
        log.info("redeemCatalogueCompatible -  "+generalUtils.getLogTextForRequest());


        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

        // Check if the merchant_no is existing in the request
        if ( params.containsKey("merchant_no") ) {

            // Set the merchantNo
            merchantNo = Long.parseLong(params.get("merchant_no"));


        }

        // Check if the dest_loyalty_id is existing in the request
        if ( params.containsKey("dest_loyalty_id") ) {

            // Set the merchantNo
            destLoyaltyId = params.get("dest_loyalty_id");


        }

        List< Map<String,String>> catalogueRedemptionResponse = redemptionService.redeemCatalogueItemsCompatible(loyaltyId,merchantNo,destLoyaltyId,params);

         // Set the status
         retData.setStatus(APIResponseStatus.success);

         // Set the data
         retData.setData(catalogueRedemptionResponse);

        // Log the response
        log.info("redeemCatalogueCompatible - " + generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return  retData;

    }

    @RequestMapping(value = "/api/0.9/json/transaction/cashback", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public HashMap<String,String> doCashbackCompatibleWpf(
                                        @RequestParam(value="cardnumber") String loyaltyId,
                                        @RequestParam(value="rwd_currency_id") Long rwdCurrencyId,
                                        @RequestParam(value="purchase_amount",defaultValue = "0") double amount,
                                        @RequestParam(value="txnref") String txnRef,
                                        @RequestParam(value="otp_code",defaultValue ="") String otpCode) throws InspireNetzException {


            // Define the HashMap holding the return information
            // Create the APIResponseObject
            APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

            // Create the CashBackRedemptionRequest object
            CashBackRedemptionRequest cashbackRedemptionRequest = new CashBackRedemptionRequest();

            // Get the merchantNo;
            Long merchantNo = authSessionUtils.getMerchantNo();

            // Get the user location
            Long userLocation = authSessionUtils.getUserLocation();

            // get the user number for the currenlty logged in user
            Long userNo = authSessionUtils.getUserNo();


            // Log the Request
            log.info("doCashbackCompatible - Request Received# "+
                    "cardnumber="+loyaltyId + " - "+
                    "rwd_currency_id="+rwdCurrencyId + " - " +
                    "purchase_amount="+Double.toString(amount) + "-" +
                    "txnref="+txnRef
            );
            log.info("doCashbackCompatible -  "+generalUtils.getLogTextForRequest());

            // Set the merchantNo
            cashbackRedemptionRequest.setMerchantNo(merchantNo);

            // Set the location
            cashbackRedemptionRequest.setUserLocation(userLocation);

            // Set the user number to the request
            cashbackRedemptionRequest.setUserNo(userNo);


            // Set the loyaltyid
            cashbackRedemptionRequest.setLoyaltyId(loyaltyId);

            // Set the reward currency id
            cashbackRedemptionRequest.setRewardCurrencyId(rwdCurrencyId);

            // set the amount
            cashbackRedemptionRequest.setAmount(amount);

            // Set the reference
            cashbackRedemptionRequest.setTxnRef(txnRef);

            // Hold the audit details
            String auditDetails = authSessionUtils.getUserNo().toString() ;

            // Set the auditDetails
            cashbackRedemptionRequest.setAuditDetails(auditDetails);

            // Call the doCashbackFunction and then get the response
            CashBackRedemptionResponse response = redemptionService.doCashBackRedemption(cashbackRedemptionRequest, otpCode);

            //getCompatible resource
            CashBackCompatibleResource cashBackCompatibleResource =new CashBackCompatibleResource();

            if(response.getStatus().equals("success") || response.getStatus()=="success"){


                retData.setStatus(APIResponseStatus.success);

                retData.put("errorcode", response.getErrorcode());

                cashBackCompatibleResource.setBalance(response.getBalance());

                cashBackCompatibleResource.setTxn_ref(response.getTxnRef());

                cashBackCompatibleResource.setPointRedeemed(response.getPointRedeemed());

                retData.setBalance(cashBackCompatibleResource);

            }else{


                cashBackCompatibleResource.setBalance(0.0);

                cashBackCompatibleResource.setPointRedeemed(0.0);

                cashBackCompatibleResource.setTxn_ref("");

                retData.setBalance(cashBackCompatibleResource);

                retData.put("status",response.getStatus());

                retData.put("errorcode", response.getErrorcode());


            }

            // Map the values of the resposne to the retData hashmap
            // Set the status



            // Log the response
            log.info("doCashbackCompatible -  Response" +retData);


            // Return the retData
            return retData;

        }

    @RequestMapping(value = "/api/0.9/json/merchant/redemption/cashback", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject doMerchantCashBackRedemption(
            @RequestParam(value="loyaltyId") String loyaltyId,
            @RequestParam(value="rwdCurrencyId") Long rwdCurrencyId,
            @RequestParam(value="purchaseUnit",defaultValue = "0") double amount,
            @RequestParam(value="location",defaultValue = "") String location,
            @RequestParam(value="txnref",defaultValue = "") String txnRef
             ) throws InspireNetzException {


        // Define the HashMap holding the return information
        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        log.info("doMerchantCashBackRedemption - "+generalUtils.getLogTextForRequest());

        log.info("doMerchantCashBackRedemption - Request Received# "+
                "loyaltyId="+loyaltyId + " - "+
                "rwdCurrencyI="+rwdCurrencyId + " - " +
                "purchaseUnit="+Double.toString(amount)

        );

        //do cash back redemption
        CashBackRedemptionResponse cashbackRedemptionResponse = redemptionService.doCashBackRedemptionForMerchant(loyaltyId, amount, rwdCurrencyId,location,txnRef);

        //set Api response
        retData.setData(cashbackRedemptionResponse);

        // Log the response
        log.info("doMerchantCashBackRedemption -  Response"+ generalUtils.getLogTextForResponse(retData));

        // Return the retData
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/partner/catalogue/redemptions/requests/{filter}/{query}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRedemptionsRequestsInPartnerPortal(
            @PathVariable(value="filter") String filter,
            @PathVariable(value="query") String query ,
            @RequestParam(value="status",defaultValue = "0") Integer status,
            Pageable pageable
    ) throws InspireNetzException {


        // Get the logged n user details
        AuthUser user = authSessionUtils.getCurrentUser();

        // Get the redemption partner
        Long rdmPartnerNo = user.getThirdPartyVendorNo();

        //Get the merchant no
        Long merchantNo = user.getMerchantNo();

        // Log the Request
        log.info("listRedemptionRequests - Request Received# filter : "+filter + " query : " + query);
        log.info("listRedemptionRequests -  "+generalUtils.getLogTextForRequest());

        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

        // Get the redemption list
        Page<Redemption> redemptionPage = redemptionService.listRedemptionRequestsInPartnerPortal(rdmPartnerNo,filter,query,status,pageable);


        // Check if the redemptionPage is null
        if ( redemptionPage == null ) {

            // Log the response
            log.info("listRedemptionRequests - Response : Please specify all required fields properly");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

        }

        // Convert the response to  resources
        List<RedemptionResource> redemptionResourceList = redemptionAssembler.toResources(redemptionPage);

        // Set the pageable params for the retData
        retData.setPageableParams(redemptionPage);

        // Set the retData status as success
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(redemptionResourceList);

        // Log the response
        log.info("listRedemptionRequests -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return  retData;

    }

    @RequestMapping(value = "/api/0.9/json/partner/redemption/status/{trackingId}", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updateRedemptionStatusPartnerPortal(
            @PathVariable(value="trackingId") String trackingId,
            @RequestParam(value="status") Integer status

    ) throws InspireNetzException {

        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

        // Get the user details
        AuthUser user = authSessionUtils.getCurrentUser();

        //get the Rdm merchant
        Long rdmPartnerNo = user.getThirdPartyVendorNo();


        // Log the Request
        log.info("updateRedemptionStatusPartnerPortal - Request Received# trackingId="+trackingId + " - status "+ status);
        log.info("updateRedemptionStatusPartnerPortal -  "+generalUtils.getLogTextForRequest());


        // Get the list of all the Redemptions for the trackingId
        List<Redemption> redemptionList = redemptionService.findByRdmPartnerNoAndRdmUniqueBatchTrackingId(rdmPartnerNo, trackingId);

        // Check if data exists
        if ( redemptionList == null || redemptionList.isEmpty() ) {

            // Log the response
            log.info("updateRedemptionStatusPartnerPortal - Response : No data found for the tracking id");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }



        // Go through each of the items and then update the status and save
        for(Redemption redemption : redemptionList ) {

            // Set the status
            redemption.setRdmStatus(status);

            // Log the informaiton
            log.info("updateRedemptionStatus - Updated status for " + redemption.getRdmId());

        }


        // Save the list back
        redemptionService.saveAll(redemptionList);


        // Set the status as success
        retData.setStatus(APIResponseStatus.success);

        // Set the data as updated status
        retData.setData(status);


        // Log the response
        log.info("updateRedemptionStatus -  " + generalUtils.getLogTextForResponse(retData));



        // Return the object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/partner/redemptions/request/trackingid/{trackingId}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRedemptionRequestsForTrackingIdPartnerPortal(
            @PathVariable(value="trackingId") String trackingId
    ) throws InspireNetzException {


        // Get the user details
        AuthUser user = authSessionUtils.getCurrentUser();

        //get the Rdm merchant
        Long rdmPartnerNo = user.getThirdPartyVendorNo();


        // Log the Request
        log.info("listRedemptionRequestsForTrackingIdPartnerPortal - Request Received# "+
                "trackingId="+trackingId + " - "
        );
        log.info("listRedemptionRequestsForTrackingIdPartnerPortal -  "+generalUtils.getLogTextForRequest());




        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

        List<Redemption> redemptionList =new ArrayList<>();

        // Get the list of redemptions for the given tracking id
        if(!trackingId.equals("0")){

            redemptionList = redemptionService.findByRdmPartnerNoAndRdmUniqueBatchTrackingId(rdmPartnerNo, trackingId);

        }


        // If the redemptionList is empty , then we need to show the information as not found
        if ( redemptionList == null || redemptionList.isEmpty() ) {

            // Log the response
            log.info("listRedemptionRequestsForTrackingIdPartnerPortal - Response : No data found for the tracking id");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);


        }

        // Convert the response to  resources
        List<RedemptionResource> redemptionResourceList = redemptionAssembler.toResources(redemptionList);

        // Set the retData status as success
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(redemptionResourceList);

        // Log the response
        log.info("listRedemptionRequestsForTrackingId -  " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return  retData;

    }

    @RequestMapping(value = "/api/0.9/json/customer/redemptions/pay/{merchantNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerTransactions(
            @PathVariable(value = "merchantNo") Long merchantNo,
            Pageable pageable
    ) throws InspireNetzException {


        // Log the Request
        log.info("listTransactions - Request Received# ");
        log.info("listTransactions - "+generalUtils.getLogTextForRequest());

        // Get loggged in user details
        Long userNo = authSessionUtils.getUserNo();

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("searchPurchase - Request params - merchantNo:"+merchantNo

        );

        // Variable holding the MessageSpiel
        List<RedemptionResource> redemptionResourceList = new ArrayList<>(0);


        Page<Redemption> redemptionResourcePage =redemptionService.searchRedemptionsForPay(merchantNo, userNo);

        // Get the list of the transactionResources
        redemptionResourceList = redemptionAssembler.toResources(redemptionResourcePage);

        // Set the data
        retData.setData(redemptionResourceList);

        // Set the retData Pageable fields
        retData.setPageableParams(redemptionResourcePage);


        // Log the response
        log.info("listredemptions - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/partner/redemptions/pay", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getPayTransactionsInPartnerPortal(
            @RequestParam(value = "rdmStartDate", required = false) Date rdmStartDate,
            @RequestParam(value = "rdmEndDate",required = false) Date rdmEndDate,
            Pageable pageable
    ) throws InspireNetzException {


        // Log the Request
        log.info("listTransactions - Request Received# ");
        log.info("listTransactions - "+generalUtils.getLogTextForRequest());

        //Get user details
        AuthUser user = authSessionUtils.getCurrentUser();


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("searchRedemptions - Request params - rdmStartDate:"+rdmStartDate + "And rdmEndDate:" + rdmEndDate);


        // Variable holding the MessageSpiel
        List<RedemptionResource> redemptionResourceList = new ArrayList<>(0);


        Page<Redemption> redemptionResourcePage =redemptionService.listPayRedemptionsInPartnerPortal(user.getThirdPartyVendorNo(),rdmStartDate,rdmEndDate,pageable);

        // Get the list of the transactionResources
        redemptionResourceList = redemptionAssembler.toResources(redemptionResourcePage);

        // Set the data
        retData.setData(redemptionResourceList);

        // Set the retData Pageable fields
        retData.setPageableParams(redemptionResourcePage);


        // Log the response
        log.info("listredemptions - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/partner/redemptions/pay/{rdmId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updateRedemptionStatusInPartnerPortal(
            @PathVariable(value = "rdmId") Long rdmId
    ) throws InspireNetzException {


        // Log the Request
        log.info("listTransactions - Request Received# ");
        log.info("listTransactions - "+generalUtils.getLogTextForRequest());

        // Get the current user
        AuthUser user = authSessionUtils.getCurrentUser();

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("updateRedemptionStatusInPartnerPortal - " +rdmId

        );

        // Update the redemption details

        Boolean isUpdated =redemptionService.voidTransactions(user.getThirdPartyVendorNo(), rdmId);

       //Check if the data is updated
        if(isUpdated){

            // Set the status as success
            retData.setStatus(APIResponseStatus.success);

        }

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/redemption/update/{trackingId}", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updateRedemptionDetails(
            @PathVariable(value="trackingId") String trackingId,
            @RequestParam(value="status") Integer status,
            @RequestParam(value="rdmDeliveryCourierInfo", required = false)String rdmDeliveryCourierInfo,
            @RequestParam(value ="rdmDeliveryCourierTracking" , required = false)String rdmDeliveryCourierTracking

    ) throws InspireNetzException {

        // Call the redeemCatalogue and store the response in the APIResponseObject object
        APIResponseObject retData = new APIResponseObject();

        // Get the merchantNo of the currently logged in merchant user
        Long merchantNo = authSessionUtils.getMerchantNo();

        // get the user number of the currently requesting user
        Long userNo = authSessionUtils.getUserNo();


        // Log the Request
        log.info("updateRedemptionStatus - Request Received# trackingId="+trackingId + " - status "+ status);

        log.info("updateRedemptionStatus -  "+generalUtils.getLogTextForRequest());


        Redemption redemption = redemptionService.updateRedemptionDetails(trackingId,status,rdmDeliveryCourierInfo,rdmDeliveryCourierTracking);

        // Set the status as success
        retData.setStatus(APIResponseStatus.success);

        // Set the data as updated status
        retData.setData(redemption);


        // Log the response
        log.info("updateRedemptionStatus -  " + generalUtils.getLogTextForResponse(retData));

        // Return the object
        return retData;



    }

}

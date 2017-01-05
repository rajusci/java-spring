package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.RedemptionVoucherUpdateRequest;
import com.inspirenetz.api.core.domain.RedemptionVoucher;
import com.inspirenetz.api.core.service.RedemptionVoucherService;
import com.inspirenetz.api.rest.assembler.RedemptionVoucherAssembler;
import com.inspirenetz.api.rest.assembler.RedemptionVoucherValidityAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.RedemptionVoucherResource;
import com.inspirenetz.api.rest.resource.RedemptionVoucherUpdateRequestResource;
import com.inspirenetz.api.rest.resource.RedemptionVoucherValidityResource;
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
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by saneeshci on 25/9/14.
 *
 */
@Controller
public class RedemptionVoucherController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(RedemptionVoucherController.class);

    @Autowired
    private RedemptionVoucherService redemptionVoucherService;

    @Autowired
    private RedemptionVoucherAssembler redemptionVoucherAssembler;

    @Autowired
    private RedemptionVoucherValidityAssembler redemptionVoucherValidityAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/redemptionvoucher/pending/{loyaltyid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRedemptionVoucherForLoyaltyId( @PathVariable(value ="loyaltyid") String loyaltyId,

                                                                @RequestParam(value="channel",defaultValue = "1") Integer channel,

                                                                Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listRedemptionVouchers - Request Received# Loyalty Id :" +loyaltyId );
        log.info("listRedemptionVouchers -   "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the RedemptionVoucher
        Page<RedemptionVoucher> redemptionVoucherPage = redemptionVoucherService.getPendingRedemptionVouchers(loyaltyId, channel,pageable);

        // Convert to reosurce
        List<RedemptionVoucherResource> redemptionVoucherResourceList =  redemptionVoucherAssembler.toResources(redemptionVoucherPage);

        // Set the data
        retData.setData(redemptionVoucherResourceList);

        // Log the response
        log.info("listRedemptionVouchers -  " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/redemptionvoucher/claim", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject redeemRedemptionVoucher( @RequestParam(value="loyaltyid") String loyaltyid,
                                                      @RequestParam(value = "merchantloginid")String merchantLoginId,
                                                      @RequestParam(value = "vouchercode")String voucherCode) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("redeemRedemptionVoucher -   "+generalUtils.getLogTextForRequest());
        log.info("redeemRedemptionVoucher :Received Loyalty Id:"+loyaltyid+" Redemption Merchant User ID:"+merchantLoginId+" Voucher Code"+voucherCode);

        RedemptionVoucher redemptionVoucher = redemptionVoucherService.redeemRedemptionVoucher(loyaltyid,merchantLoginId,voucherCode);

        // Convert the RedemptionVoucher to RedemptionVoucherResource
        RedemptionVoucherResource redemptionVoucherResource = redemptionVoucherAssembler.toResource(redemptionVoucher);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the roleResource object
        retData.setData(redemptionVoucherResource);

        // Log the response
        log.info("redeemRedemptionVoucher -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/redemptionvoucher/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRedemptionVoucherList(@PathVariable(value="filter") String filter,
                                                      @PathVariable(value = "query") String query,
                                                      Pageable pageable) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getRedemptionVoucherList -   "+generalUtils.getLogTextForRequest());

        log.info("getRedemptionVoucherList:Received filter "+filter+" Query:"+query);

        // Variable holding the RedemptionVoucher
        List<RedemptionVoucherResource> redemptionVoucherList = new ArrayList<>(0);

        Page<RedemptionVoucher> redemptionVoucherPage=redemptionVoucherService.searchRedemptionVoucher(filter,query ,pageable);

        redemptionVoucherList = redemptionVoucherAssembler.toResources(redemptionVoucherPage);

        // Set the pageable params for the retData
        retData.setPageableParams(redemptionVoucherPage);

        // Set the data
        retData.setData(redemptionVoucherList);

        // Log the response
        log.info("getRedemptionVoucherList-  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;




    }



    @RequestMapping(value = "/api/0.9/json/merchant/searchredemptionvoucher/{rvrloyaltyid}/{rvrvouchercode}/{rvrstartdate}/{rvrenddate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject searchRedemptionMerchant(@PathVariable(value="rvrloyaltyid") String rvrLoyaltyId,
                                                      @PathVariable(value = "rvrvouchercode") String rvrVoucherCode,
                                                      @PathVariable(value="rvrstartdate") Date rvrStartDate,
                                                      @PathVariable(value = "rvrenddate") Date rvrEndDate,
                                                      Pageable pageable) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("searchRedemptionMerchant -  "+generalUtils.getLogTextForRequest());

        log.info("searchRedemptionMerchant : Received loyaltyId "+rvrLoyaltyId+" voucherCode:"+rvrVoucherCode);

        // Variable holding the RedemptionVoucher
        List<RedemptionVoucherResource> redemptionVoucherList = new ArrayList<>(0);

        Page<RedemptionVoucher> redemptionVoucherPage=redemptionVoucherService.searchRedemptionVoucherForMerchant(rvrVoucherCode,rvrLoyaltyId,rvrStartDate,rvrEndDate,pageable);

        redemptionVoucherList = redemptionVoucherAssembler.toResources(redemptionVoucherPage);

        // Set the data
        retData.setData(redemptionVoucherList);

        // Log the response
        log.info("searchRedemptionMerchant-  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;




    }


    @RequestMapping(value = "/api/0.9/json/merchant/redemptionuservoucher/claim", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject redeemRedemptionUserVoucher( @RequestParam(value="loyaltyid") String loyaltyid,

                                                      @RequestParam(value = "vouchercode")String voucherCode) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("redeemRedemptionUserVoucher -   "+generalUtils.getLogTextForRequest());


        RedemptionVoucher redemptionVoucher = redemptionVoucherService.redeemRedemptionVoucher(loyaltyid,authSessionUtils.getUserLoginId(),voucherCode);

        // Convert the RedemptionVoucher to RedemptionVoucherResource
        RedemptionVoucherResource redemptionVoucherResource = redemptionVoucherAssembler.toResource(redemptionVoucher);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the roleResource object
        retData.setData(redemptionVoucherResource);

        // Log the response
        log.info("redeemRedemptionUserVoucher -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/redemptionvoucher/validate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject redemptionVoucherIsValid(@RequestParam(value = "voucherCode")String rvrVoucherCode  ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        log.info("checking redemption Voucher is valid ::Receiving  vouchercode is:"+rvrVoucherCode);

        //checking the condition redemption voucher is valid
        RedemptionVoucher redemptionVoucherIsValid = redemptionVoucherService.redemptionVoucherIsValid(rvrVoucherCode);

        //if its valid
        if (redemptionVoucherIsValid ==null){

            retData.setStatus(APIResponseStatus.failed);

            retData.setData("false");

            return retData;
        }

        RedemptionVoucherValidityResource redemptionVoucherValidityResource =redemptionVoucherValidityAssembler.toResource(redemptionVoucherIsValid);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        retData.setData(redemptionVoucherValidityResource);

        // Return the return data object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchantuser/redemptionvoucher/claim", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject voucherClaimForRedemptionUser(@RequestParam(value= "voucherCode") String rvrVoucherCode,
                                                           @RequestParam(value = "merchantLocation")String merchantLocation) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("redeemRedemptionUserVoucher -   "+generalUtils.getLogTextForRequest());

        RedemptionVoucher redemptionVoucher = redemptionVoucherService.voucherClaimForMerchantUser(rvrVoucherCode,merchantLocation);

        if(redemptionVoucher ==null){

            retData.setStatus(APIResponseStatus.failed);

            return retData;
        }



        RedemptionVoucherValidityResource redemptionVoucherValidityResource =redemptionVoucherValidityAssembler.toResource(redemptionVoucher);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        retData.setData(redemptionVoucherValidityResource);

        // Log the response
        log.info("redeemRedemptonForSmartUser-  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/user/getredemptionvoucher/{filter}/{query}/{rvrstartdate}/{rvrenddate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRedemptionVoucher(@RequestParam(value= "merchantNo",defaultValue = "0") Long merchantNo,
                                                  @PathVariable(value = "filter") String filter,
                                                  @PathVariable(value = "query") String query,
                                                  @PathVariable(value="rvrstartdate") Date rvrStartDate,
                                                  @PathVariable(value = "rvrenddate") Date rvrEndDate,
                                                  Pageable pageable
                                                           ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getRedemptionVoucher -   "+generalUtils.getLogTextForRequest());


        log.info("getRedemptionVoucherList:Received merchantNo "+merchantNo+" query:"+query+"startDate"+rvrStartDate+"endDate"+rvrEndDate);

        // Variable holding the RedemptionVoucher
        List<RedemptionVoucherResource> redemptionVoucherList = new ArrayList<>(0);

        Page<RedemptionVoucher> redemptionVoucherPage=redemptionVoucherService.searchRedemptionVoucherForCustomer(filter,query,rvrStartDate, rvrEndDate, merchantNo, pageable);

        redemptionVoucherList = redemptionVoucherAssembler.toResources(redemptionVoucherPage);

        // Set the pageable params for the retData
        retData.setPageableParams(redemptionVoucherPage);

        // Set the data
        retData.setData(redemptionVoucherList);

        // Log the response
        log.info("getRedemptionVoucherList-  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/updatevoucher", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject updateVoucher(RedemptionVoucherUpdateRequestResource redemptionVoucherUpdateRequestResource
     ) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("updateVoucher -   "+generalUtils.getLogTextForRequest());

        //map the resource
        RedemptionVoucherUpdateRequest redemptionVoucherUpdateRequest =mapper.map(redemptionVoucherUpdateRequestResource,RedemptionVoucherUpdateRequest.class);

        // Variable holding the RedemptionVoucher
        RedemptionVoucher redemptionVoucher =redemptionVoucherService.updateRedemptionVoucher(redemptionVoucherUpdateRequest);

        // Get the updateVoucher id
        retData.setData(redemptionVoucher.getRvrId());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("updateVoucher - " + generalUtils.getLogTextForResponse(retData));

        // Return the API Response object
        return retData;



    }


    @RequestMapping(value = "/api/0.9/json/merchant/redemptionvoucher/rvrlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRedemptionListIn(@RequestParam(value="rvrList") String rvrList
                          ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getRedemptionListIn -   "+generalUtils.getLogTextForRequest());

        log.info("getRedemptionListIn:Received List "+rvrList);

        // Variable holding the RedemptionVoucher
        List<RedemptionVoucherResource> redemptionVoucherList = new ArrayList<>(0);

        List<RedemptionVoucher>  redemptionVoucherList1=redemptionVoucherService.findByRvrIdIn(rvrList);

        redemptionVoucherList = redemptionVoucherAssembler.toResources(redemptionVoucherList1);

        // Set the data
        retData.setData(redemptionVoucherList);

        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getRedemptionListIn-  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;




    }

    @RequestMapping(value = "/api/0.9/json/merchant/updateRedemptionVoucherList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updateVoucherSpielList(@RequestBody Map<String,Object> params) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("updateVoucherSpielList - Request Received# "+params.toString());

        log.info("updateVoucherSpielList - "+generalUtils.getLogTextForRequest());

        // convert the redemption voucher update request  to object
        RedemptionVoucherUpdateRequest redemptionVoucherUpdateRequest = mapper.map(params,RedemptionVoucherUpdateRequest.class);

        //send notification
        redemptionVoucherService.sendVoucherVoucherNotificationList(redemptionVoucherUpdateRequest);



        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveMessageSpiel -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }



}
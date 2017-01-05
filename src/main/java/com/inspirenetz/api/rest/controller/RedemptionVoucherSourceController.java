package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import com.inspirenetz.api.core.service.RedemptionVoucherSourceService;
import com.inspirenetz.api.rest.assembler.RedemptionVoucherSourceAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.RedemptionVoucherSourceResource;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class RedemptionVoucherSourceController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(RedemptionVoucherSourceController.class);

    @Autowired
    private RedemptionVoucherSourceService redemptionVoucherSourceService;

    @Autowired
    private RedemptionVoucherSourceAssembler redemptionVoucherSourceAssembler;

    @Autowired
    private Mapper mapper;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/merchant/redemption/vouchersource", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveRedemptionVoucherSource(RedemptionVoucherSourceResource redemptionVoucherSourceResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveRedemptionVoucherSource - Request Received# "+redemptionVoucherSourceResource.toString());
        log.info("saveRedemptionVoucherSource -  "+generalUtils.getLogTextForRequest());

        // convert the LInkRequestResource to object
        RedemptionVoucherSource redemptionVoucherSource = mapper.map(redemptionVoucherSourceResource,RedemptionVoucherSource.class);

        //get the merchant no
        //check the user is super admin or web admin
        Integer userType =authSessionUtils.getUserType();

        Long merchantNo =0L;

        if(userType.intValue()== UserType.ADMIN || userType.intValue()==UserType.SUPER_ADMIN){

           //get merchant number from request
           merchantNo =redemptionVoucherSource.getRvsMerchantNo();

        }else {

            //pick merchant number from session
            merchantNo =authSessionUtils.getMerchantNo();
        }


        //set merchant no
        redemptionVoucherSource.setRvsMerchantNo(merchantNo);

        // save the redemptionVoucherSource object and get the result
        redemptionVoucherSource = redemptionVoucherSourceService.validateAndSaveRedemptionVoucherSource(redemptionVoucherSource);

        // Get the redemptionVoucherSource id
        retData.setData(redemptionVoucherSource.getRvsId());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);




        // Log the response
        log.info("saveRedemptionVoucherSource -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/redemption/vouchersource/delete/{rvsId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteRedemptionVoucherSource(@PathVariable(value="rvsId") Long rvsId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("deleteRedemptionVoucherSource - Request Received# RedemptionVoucherSource ID: "+rvsId);
        log.info("deleteRedemptionVoucherSource -  "+generalUtils.getLogTextForRequest());



        // Delete the redemptionVoucherSource and set the retData fields
        redemptionVoucherSourceService.validateAndDeleteRedemptionVoucherSource(rvsId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete lrqId
        retData.setData(rvsId);



        // Log the response
        log.info("deleteRedemptionVoucherSource -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/redemption/vouchersources/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRedemptionVoucherSources(@PathVariable(value ="filter") String filter,
                                              @PathVariable(value ="query") String query,
                                        Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listRedemptionVoucherSources - Request Received# filter "+ filter +" query :" +query );
        log.info("listRedemptionVoucherSources -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // check user type is web admin or super admin
        Integer userType =authSessionUtils.getUserType();

        Long merchantNo =0L;

        if(userType.intValue() ==UserType.ADMIN ||userType.intValue() ==UserType.SUPER_ADMIN){

            //set merchant Number is zero
             merchantNo=0L;

        }else {

            //set merchant number
            merchantNo =authSessionUtils.getMerchantNo();
        }



        List<RedemptionVoucherSourceResource> redemptionVoucherSourceResourceList = new ArrayList<>(0);

        // Get the RedemptionVoucherSource
        Page<RedemptionVoucherSource> redemptionVoucherSourcePage = redemptionVoucherSourceService.searchVoucherSources(filter,query,merchantNo,pageable);

        // Convert to reosurce
        redemptionVoucherSourceResourceList =  redemptionVoucherSourceAssembler.toResources(redemptionVoucherSourcePage);

        // Set the data
        retData.setData(redemptionVoucherSourceResourceList);

        // Set the pageable params to the retData
        retData.setPageableParams(redemptionVoucherSourcePage);

        // Log the response
        log.info("listRedemptionVoucherSources -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/redemption/vouchersources/active", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getActiveVouchers(Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("getActiveVouchers - Request Received# ");
        log.info("getActiveVouchers -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        List<RedemptionVoucherSourceResource> redemptionVoucherSourceResourceList = new ArrayList<>(0);

        // Get the RedemptionVoucherSource
        Page<RedemptionVoucherSource> redemptionVoucherSourcePage = redemptionVoucherSourceService.getActiveVoucherSources(merchantNo,pageable);

        // Convert to reosurce
        redemptionVoucherSourceResourceList =  redemptionVoucherSourceAssembler.toResources(redemptionVoucherSourcePage);

        // Set the data
        retData.setData(redemptionVoucherSourceResourceList);

        // Set the pageable params to the retData
        retData.setPageableParams(redemptionVoucherSourcePage);

        // Log the response
        log.info("getActiveVouchers -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/redemption/vouchersource/{rvsId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRedemptionVoucherSourceInfo(@PathVariable(value="rvsId") Long lrqId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getRedemptionVoucherSourceInfo - Request Received# RedemptionVoucherSource ID: "+lrqId);
        log.info("getRedemptionVoucherSourceInfo -  "+generalUtils.getLogTextForRequest());



        // Get the RedemptionVoucherSource
        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceService.findByRvsId(lrqId);

        // Convert the RedemptionVoucherSource to RedemptionVoucherSourceResource
        RedemptionVoucherSourceResource redemptionVoucherSourceResource = redemptionVoucherSourceAssembler.toResource(redemptionVoucherSource);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the redemptionVoucherSourceResource object
        retData.setData(redemptionVoucherSourceResource);




        // Log the response
        log.info("getRedemptionVoucherSourceInfo -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }


}

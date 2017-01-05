package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthUser;

import com.inspirenetz.api.core.dictionary.APIErrorCode;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.RedemptionMerchantService;
import com.inspirenetz.api.rest.assembler.RedemptionMerchantAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.RedemptionMerchantResource;
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

import java.util.List;
import java.util.Map;

/**
 * Created by saneeshci on 25/9/14.
 *
 */
@Controller
public class RedemptionMerchantController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(RedemptionMerchantController.class);

    @Autowired
    private RedemptionMerchantService redemptionMerchantService;

    @Autowired
    private RedemptionMerchantAssembler redemptionMerchantAssembler;

    @Autowired
    private CustomerService customerService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/merchant/redemptionmerchants/redemptionmerchant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveRedemptionMerchant(@RequestBody Map<String,Object> params) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveRedemptionMerchant - Request Received# "+params.toString());
        log.info("saveRedemptionMerchant -   "+generalUtils.getLogTextForRequest());


        // get redemption merchant object from params list to object
        RedemptionMerchant redemptionMerchant = mapper.map(params,RedemptionMerchant.class);

        // save the redemptionMerchant object and get the result
        redemptionMerchant = redemptionMerchantService.validateAndSaveRedemptionMerchant(redemptionMerchant);

        // Get the redemptionMerchant id
        retData.setData(redemptionMerchant.getRemNo());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveRedemptionMerchant -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/redemptionmerchants/redemptionmerchant/delete/{remNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteRedemptionMerchant(@PathVariable(value="remNo") Long remNo) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("deleteRedemptionMerchant - Request Received# RedemptionMerchant ID: "+remNo);
        log.info("deleteRedemptionMerchant -   "+generalUtils.getLogTextForRequest());



        // Delete the redemptionMerchant and set the retData fields
        redemptionMerchantService.validateAndDeleteRedemptionMerchant(remNo);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete lrqId
        retData.setData(remNo);



        // Log the response
        log.info("deleteRedemptionMerchant -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/redemptionmerchants/redemptionmerchants/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRedemptionMerchants(@PathVariable(value ="filter") String filter,
                                              @PathVariable(value ="query") String query,
                                              Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listRedemptionMerchants - Request Received# filter "+ filter +" query :" +query );
        log.info("listRedemptionMerchants -   "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the RedemptionMerchant
        Page<RedemptionMerchant> redemptionMerchantPage = redemptionMerchantService.searchRedemptionMerchants(filter,query,pageable);

        // Convert to reosurce
        List<RedemptionMerchantResource> redemptionMerchantResourceList =  redemptionMerchantAssembler.toResources(redemptionMerchantPage);

        // Set the data
        retData.setData(redemptionMerchantResourceList);

        // Log the response
        log.info("listRedemptionMerchants -  " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/redemptionmerchants/redemptionmerchant/{remNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRedemptionMerchantInfo(@PathVariable(value="remNo") Long remNo) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getRedemptionMerchantInfo -   "+generalUtils.getLogTextForRequest());

        // Get the RedemptionMerchant
        RedemptionMerchant redemptionMerchant = redemptionMerchantService.findByRemNo(remNo);

        // Convert the RedemptionMerchant to RedemptionMerchantResource
        RedemptionMerchantResource redemptionMerchantResource = redemptionMerchantAssembler.toResource(redemptionMerchant);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the redemptionMerchantResource object
        retData.setData(redemptionMerchantResource);

        // Log the response
        log.info("getRedemptionMerchantInfo -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/redemption/merchant/validate/customer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public APIResponseObject validateCustomerInPartnerPortal(@RequestParam(value = "loyaltyId")String loyaltyId,
                                              @RequestParam(value= "merchantNo")Long merchantNo,
                                              @RequestParam(value="amount")Double amount) throws InspireNetzException{

        // Log the Request
        log.info("validateCustomerInPartnerPortal - Request Received# loyaltyid "+ loyaltyId +" merchantNo :" +merchantNo );
        log.info("validateCustomerInPartnerPortal -   "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Validate the customer by calling service method
        Double balance = redemptionMerchantService.validateCustomer(merchantNo,loyaltyId,amount);

        //Set status as success
        retData.setStatus(APIResponseStatus.success);

        // set the data
        retData.setData(balance);

        // Log the response
        log.info("validateCustomerInPartnerPortal -  " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;





    }


    @RequestMapping(value = "/api/0.9/json/trusted/redemptionmerchants/redemptionmerchant/{remCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRedemptionMerchantInfoForCode(@PathVariable(value = "remCode") String remCode) throws InspireNetzException {

        // log the Request
        log.info("getRedemptionMerchantInfoForCode - Request Received: remCode" + remCode);
        log.info("getRedemptionMerchantInfoForCode -  "+generalUtils.getLogTextForRequest());

        // Check the session and vaildate
        //
        // This need to be inside the controller
        if ( authSessionUtils.getCurrentUser() == null ||
                !authSessionUtils.getCurrentUser().getUserLoginId().equals("localipuser") ) {

            // Log the excception
            log.error("Current user is not authorized for the operation " + authSessionUtils.getCurrentUser());

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Call the service
        RedemptionMerchantResource redemptionMerchantResource = redemptionMerchantService.getRedemptionMerchantWithPartners(remCode);

        // Add to the data
        retData.setData(redemptionMerchantResource);

        // Log the response
        log.info("getCustomerMemberships - Response " + retData);

        // return the object
        return retData;


    }
}
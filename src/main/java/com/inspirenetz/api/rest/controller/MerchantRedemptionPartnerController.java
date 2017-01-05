package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantRedemptionPartner;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.service.MerchantRedemptionPartnerService;
import com.inspirenetz.api.rest.assembler.MerchantAssembler;
import com.inspirenetz.api.rest.assembler.MerchantRedemptionPartnerAssembler;
import com.inspirenetz.api.rest.assembler.RedemptionMerchantAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.MerchantRedemptionPartnerResource;
import com.inspirenetz.api.rest.resource.MerchantResource;
import com.inspirenetz.api.rest.resource.RedemptionMerchantResource;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ameen on 25/6/15.
 */
@Controller
public class MerchantRedemptionPartnerController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(MerchantRedemptionPartnerController.class);

    @Autowired
    private MerchantRedemptionPartnerService merchantRedemptionPartnerService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    MerchantRedemptionPartnerAssembler merchantRedemptionPartnerAssembler;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    MerchantAssembler merchantAssembler;

    @Autowired
    RedemptionMerchantAssembler redemptionMerchantAssembler;


    @RequestMapping(value = "/api/0.9/json/admin/merchantpartner", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveMerchantRedemptionPartner(@Valid MerchantRedemptionPartner merchantRedemptionPartner,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveMerchantRedemptionPartner - Request Received# "+merchantRedemptionPartner.toString());
        log.info("saveMerchantRedemptionPartner -  "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveMerchantRedemptionPartner - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // Set the updatedBy field
        merchantRedemptionPartner.setUpdatedBy(auditDetails);


        // save the merchantRedemptionPartner object and get the result
        merchantRedemptionPartner = merchantRedemptionPartnerService.validateAndSaveMerchantRedemptionPartner(merchantRedemptionPartner);

        // Get the merchantRedemptionPartner id
        retData.setData(merchantRedemptionPartner);

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveMerchantRedemptionPartner -  " + generalUtils.getLogTextForResponse(retData));

        // Return the API Response object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/admin/merchantredemptionpartner/{mrpmerchantno}/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantRedemptionPartnersForAdmin(@PathVariable("mrpmerchantno") Long mrpMerchantNo,@PathVariable("filter") String filter,@PathVariable("query") String query){


        // Log the Request
        log.info("listMerchantRedemptionPartners - Request Received# " + mrpMerchantNo);
        log.info("listMerchantRedemptionPartners -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //create resource list
        List<MerchantRedemptionPartnerResource> redemptionPartnerResources =new ArrayList<>();

        // Get the list of matching merchantRedemptionPartners
        Page<MerchantRedemptionPartner> merchantRedemptionPartnerList = merchantRedemptionPartnerService.getRedemptionPartnerFormAdminFilter(mrpMerchantNo,filter,query);

        redemptionPartnerResources =merchantRedemptionPartnerAssembler.toResources(merchantRedemptionPartnerList);

        // Set the data
        retData.setData(redemptionPartnerResources);

        // Log the response
        log.info("listMerchantRedemptionPartners -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/merchantredemptionpartner", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantRedemptionPartners(){


        // Log the Request
        log.info("listMerchantRedemptionPartners -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //create resource list
        List<MerchantRedemptionPartnerResource> redemptionPartnerResources =new ArrayList<>();


        // Get the list of matching merchantRedemptionPartners
        List<MerchantRedemptionPartner> merchantRedemptionPartnerList = merchantRedemptionPartnerService.findByMrpMerchantNoAndMrpEnabled(authSessionUtils.getMerchantNo());

        redemptionPartnerResources =merchantRedemptionPartnerAssembler.toResources(merchantRedemptionPartnerList);

        // Set the data
        retData.setData(redemptionPartnerResources);

        // Log the response
        log.info("listMerchantRedemptionPartners -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/redemptionmerchant/merchantpartners", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantPartnersForRedemptionMerchant() throws InspireNetzException {


        // Log the Request
        log.info("listMerchantRedemptionPartners -  "+generalUtils.getLogTextForRequest());

        String userLoginId = authSessionUtils.getUserLoginId();

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //create resource list
        List<MerchantResource> merchantResources =new ArrayList<>();


        // Get the list of matching merchantRedemptionPartners
        List<Merchant> merchantList = merchantRedemptionPartnerService.getMerchantsForRedemptionPartner(userLoginId);

        merchantResources =merchantAssembler.toResources(merchantList);

        // Set the data
        retData.setData(merchantResources);

        // Log the response
        log.info("listMerchantRedemptionPartners -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/partners", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRedempionMerchantsForMerchants() throws InspireNetzException {


        // Log the Request
        log.info("listRedempionMerchantsForMerchants -  "+generalUtils.getLogTextForRequest());

        Long merchantNo = authSessionUtils.getMerchantNo();

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //create resource list
        List<RedemptionMerchantResource> merchantResources =new ArrayList<>();


        // Get the list of matching merchantRedemptionPartners
        List<RedemptionMerchant> merchantList = merchantRedemptionPartnerService.getPartnersForMerchant(merchantNo);

        merchantResources =redemptionMerchantAssembler.toResources(merchantList);

        // Set the data
        retData.setData(merchantResources);

        // Log the response
        log.info("listRedempionMerchantsForMerchants -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/merchantpartners/{partnerNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantPartnersByRedemptionMerchantForCustomer(@PathVariable("partnerNo") Long partnerNo) throws InspireNetzException {


        // Log the Request
        log.info("listMerchantRedemptionPartners -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //create resource list
        List<MerchantResource> merchantResources =new ArrayList<>();


        // Get the list of matching merchantRedemptionPartners
        List<Merchant> merchantList = merchantRedemptionPartnerService.getMerchantsListByRedemptionPartner(partnerNo);

        merchantResources =merchantAssembler.toResources(merchantList);

        // Set the data
        retData.setData(merchantResources);

        // Log the response
        log.info("listMerchantRedemptionPartners -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/merchantpartners/code/{partnerCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantPartnersByRedemptionMerchantWithCodeForCustomer(@PathVariable("partnerCode") String partnerCode) throws InspireNetzException {


        // Log the Request
        log.info("listMerchantRedemptionPartners -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //create resource list
        List<MerchantResource> merchantResources =new ArrayList<>();


        // Get the list of matching merchantRedemptionPartners
        List<Merchant> merchantList = merchantRedemptionPartnerService.getMerchantPartnersByPartnerCode(partnerCode);

        merchantResources =merchantAssembler.toResources(merchantList);

        // Set the data
        retData.setData(merchantResources);

        // Log the response
        log.info("listMerchantRedemptionPartners -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/redemptionmerchants/{merchantNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRedempionMerchantsByMerchantsForCustomer(@PathVariable("merchantNo") Long merchantNo) throws InspireNetzException {


        // Log the Request
        log.info("listRedempionMerchantsForMerchants -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //create resource list
        List<RedemptionMerchantResource> merchantResources =new ArrayList<>();

        // Get the list of matching merchantRedemptionPartners
        List<RedemptionMerchant> merchantList = merchantRedemptionPartnerService.getPartnersForMerchant(merchantNo);

        merchantResources =redemptionMerchantAssembler.toResources(merchantList);

        // Set the data
        retData.setData(merchantResources);

        // Log the response
        log.info("listRedempionMerchantsForMerchants -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/trusted/redemptionmerchant/listpartners/{remMerchantNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listPartnersForRedemptionMerchant(@PathVariable(value = "remMerchantNo") Long remMerchantNo) throws InspireNetzException {

        // log the Request
        log.info("listPartnersForRedemptionMerchant - Request Received: remMerchantNo" + remMerchantNo);
        log.info("listPartnersForRedemptionMerchant -  "+generalUtils.getLogTextForRequest());

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
        APIResponseObject response = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Call the service method
        List<MerchantRedemptionPartnerResource> merchantRedemptionPartnerResourceList = merchantRedemptionPartnerService.getMerchantRedemptionPartnerResources(remMerchantNo);

        // Set the data
        response.setData(merchantRedemptionPartnerResourceList);

        // Log the response
        log.info("listPartnersForRedemptionMerchant - Response " + response);

        // Return the object
        return response;

    }

}

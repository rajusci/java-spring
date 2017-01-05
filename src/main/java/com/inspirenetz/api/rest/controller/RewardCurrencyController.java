package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.service.RewardCurrencyService;
import com.inspirenetz.api.rest.assembler.RewardCurrencyAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.RewardCurrencyResource;
import com.inspirenetz.api.util.*;
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
public class RewardCurrencyController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(RewardCurrencyController.class);

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    @Autowired
    private RewardCurrencyAssembler rewardCurrencyAssembler;

    @Autowired
    private DataValidationUtils dataValidationUtils;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/rewardcurrency", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveRewardCurrency(@Valid RewardCurrency rewardCurrency,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the rewardCurrency
        rewardCurrency.setRwdMerchantNo(merchantNo);


        // Log the Request
        log.info("saveRewardCurrency - Request Received# "+rewardCurrency.toString());
        log.info("saveRewardCurrency -  "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Get the validation message
            String messages = dataValidationUtils.getValidationMessages(result);

            // Log the response
            log.info("saveRewardCurrency - Response : Invalid Input - " + messages);

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,messages);

        }

        // Check if the rewardCurrency is existing
        boolean isExist = rewardCurrencyService.isDuplicateRewardCurrencyNameExisting(rewardCurrency);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveRewardCurrency - Response : RewardCurrency code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the rewardCurrency.getRwdCurrencyId is  null, then set the created_by, else set the updated_by
        if ( rewardCurrency.getRwdCurrencyId() == null ) {

            rewardCurrency.setCreatedBy(auditDetails);

        } else {

            rewardCurrency.setUpdatedBy(auditDetails);

        }


        // save the rewardCurrency object and get the result
        rewardCurrency = rewardCurrencyService.validateAndSaveRewardCurrency(rewardCurrency);

        // If the rewardCurrency object is not null ,then return the success object
        if ( rewardCurrency.getRwdCurrencyId() != null ) {

            // Get the rewardCurrency id
            retData.setData(rewardCurrency.getRwdCurrencyId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveRewardCurrency - Response : Unable to save the rewardCurrency information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveRewardCurrency - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/rewardcurrency/delete/{rwdCurrencyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteRewardCurrency(
            @PathVariable(value="rwdCurrencyId") Long rwdCurrencyId
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteRewardCurrency - Request Received#  RewardCurrency Id "+rwdCurrencyId.toString());
        log.info("deleteRewardCurrency -  "+generalUtils.getLogTextForRequest());


        // Get the reward Currency Information
        RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(rwdCurrencyId);

        // Check if rewardCurrency is null, return no data
        if ( rewardCurrency == null ) {

            // Log the response
            log.info("deleteRewardCurrency - Response : No reward currency information foound");

            // Throw InspireNetzException with NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }



        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( rewardCurrency.getRwdMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteRewardCurrency - Response : You are not authorized to delete the rewardCurrency");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the rewardCurrency and set the retData fields
        rewardCurrencyService.validateAndDeleteRewardCurrency(rewardCurrency.getRwdCurrencyId());

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete brnId
        retData.setData(rwdCurrencyId);


        // Log the response
        log.info("deleteRewardCurrency - " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }



    @RequestMapping(value = "/api/0.9/json/merchant/rewardcurrency/{rwdCurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRewardCurrencyInfo(@PathVariable(value="rwdCurrencyId") Long rwdCurrencyId) throws InspireNetzException {


        // Log the Request
        log.info("getRewardCurrencyInfo - Request Received# "+rwdCurrencyId);
        log.info("getRewardCurrencyInfo -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the information for the reward currency
        RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(rwdCurrencyId);

        // Check if the rewardCurrency is found
        if ( rewardCurrency == null || rewardCurrency.getRwdCurrencyId() == null) {

            // Log the response
            log.info("getRewardCurrencyInfo - Response : No brand information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( rewardCurrency.getRwdMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getBrandInfo - Response : You are not authorized to view the brand");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }




        // Convert the RewardCurrency to RewardCurrencyResource
        RewardCurrencyResource rewardCurrencyResource = rewardCurrencyAssembler.toResource(rewardCurrency);

        // Set the data
        retData.setData(rewardCurrencyResource);



        // Log the response
        log.info("searchByRewardCurrencyName - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/rewardcurrencies/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRewardCurrencies(
                                                   @PathVariable(value = "filter") String filter,
                                                   @PathVariable(value = "query") String query,
                                                   Pageable pageable){


        // Log the Request
        log.info("listRewardCurrencys - Request Received# filter - "+filter + " Query : " + query);
        log.info("listRewardCurrencys -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // List holding the reward currency resource
        List<RewardCurrencyResource> rewardCurrencyResourceList = new ArrayList<>(0);


        //check the filter and query
        if ( filter.equals("0") && query.equals("0") ) {

            // Get the page data
            Page<RewardCurrency> rewardCurrencyPage = rewardCurrencyService.findByRwdMerchantNo(merchantNo,pageable);

            // Convert the page to resource
            rewardCurrencyResourceList = rewardCurrencyAssembler.toResources(rewardCurrencyPage);

            // Set the pageable params
            retData.setPageableParams(rewardCurrencyPage);

        } else if ( filter.equalsIgnoreCase("name") ) {

            // Get the page data
            Page<RewardCurrency> rewardCurrencyPage = rewardCurrencyService.findByRwdMerchantNoAndRwdCurrencyNameLike(merchantNo,"%"+query +"%",pageable);

            // Convert the page to resource
            rewardCurrencyResourceList = rewardCurrencyAssembler.toResources(rewardCurrencyPage);

            // Set the pageable params
            retData.setPageableParams(rewardCurrencyPage);

        }



        // Set the data
        retData.setData(rewardCurrencyResourceList);

        // Log the response
        log.info("listRewardCurrencys - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/rewardcurrencies/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRewardCurrenciesForCustomers(
            @PathVariable(value = "filter") String filter,
            @PathVariable(value = "query") String query,
            @RequestParam(value = "merchantno") Long merchantNo,
            Pageable pageable){


        // Log the Request
        log.info("listRewardCurrencys - Request Received# filter - "+filter + " Query : " + query);
        log.info("listRewardCurrencys -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // List holding the reward currency resource
        List<RewardCurrencyResource> rewardCurrencyResourceList = new ArrayList<>(0);


        //check the filter and query
        if ( filter.equals("0") && query.equals("0") ) {

            // Get the page data
            Page<RewardCurrency> rewardCurrencyPage = rewardCurrencyService.findByRwdMerchantNo(merchantNo,pageable);

            // Convert the page to resource
            rewardCurrencyResourceList = rewardCurrencyAssembler.toResources(rewardCurrencyPage);

            // Set the pageable params
            retData.setPageableParams(rewardCurrencyPage);

        } else if ( filter.equalsIgnoreCase("name") ) {

            // Get the page data
            Page<RewardCurrency> rewardCurrencyPage = rewardCurrencyService.findByRwdMerchantNoAndRwdCurrencyNameLike(merchantNo,"%"+query +"%",pageable);

            // Convert the page to resource
            rewardCurrencyResourceList = rewardCurrencyAssembler.toResources(rewardCurrencyPage);

            // Set the pageable params
            retData.setPageableParams(rewardCurrencyPage);

        }



        // Set the data
        retData.setData(rewardCurrencyResourceList);

        // Log the response
        log.info("listRewardCurrencys - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/user/rewardcurrencies/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRewardCurrenciesForUser(
            @PathVariable(value = "filter") String filter,
            @PathVariable(value = "query") String query,
            @RequestParam(value = "merchantno",defaultValue = "0") Long merchantNo,
            Pageable pageable){


        // Log the Request
        log.info("listRewardCurrenciesForUser - Request Received# filter - "+filter + " Query : " + query);
        log.info("listRewardCurrenciesForUser -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // List holding the reward currency resource
        List<RewardCurrencyResource> rewardCurrencyResourceList = new ArrayList<>(0);

        //Get User LoginId
        String usrLoginId=authSessionUtils.getUserLoginId();

        // Get the page data
        List<RewardCurrency> rewardCurrencyPage = rewardCurrencyService.listRewardCurrenciesForUser(usrLoginId, merchantNo, filter, query);

        // Convert the page to resource
        rewardCurrencyResourceList = rewardCurrencyAssembler.toResources(rewardCurrencyPage);

        // Set the data
        retData.setData(rewardCurrencyResourceList);

        // Log the response
        log.info("listRewardCurrenciesForUser - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }
}

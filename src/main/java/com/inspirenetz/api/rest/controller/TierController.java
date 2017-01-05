package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.TierWrapper;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.domain.validator.TierValidator;
import com.inspirenetz.api.core.service.TierService;
import com.inspirenetz.api.rest.assembler.TierAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.TierResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TierController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(TierController.class);

    @Autowired
    private TierService tierService;

    @Autowired
    private TierAssembler tierAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/tiering/tier", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveTier(@Valid Tier tier,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("saveTier - Request Received# "+tier.toString());
        log.info("saveTier -  "+generalUtils.getLogTextForRequest());


        // Check the validity of the tier
        tierService.isTierRequestAuthorized(authSessionUtils.getMerchantNo(), authSessionUtils.getUserLocation(), authSessionUtils.getUserType(), tier.getTieParentGroup());



        // Create the Validation
        TierValidator validator = new TierValidator();

        // Validate the bean
        validator.validate(tier,result);



        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveTier - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }



        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // If the tier.getTieId is  null, then set the created_by, else set the updated_by
        if ( tier.getTieId() == null ) {

            tier.setCreatedBy(auditDetails);

        } else {

            tier.setUpdatedBy(auditDetails);

        }


        // save the tier object and get the result
        tier = tierService.validateAndSaveTier(tier);

        // If the tier object is not null ,then return the success object
        if ( tier.getTieId() != null ) {

            // Get the tier id
            retData.setData(tier.getTieId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveTier - Response : Unable to save the tier information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveTier - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/tiering/tierlist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveTierList(@RequestBody TierWrapper tierWrapper) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("saveTierList - Request Received# "+tierWrapper.toString());
        log.info("saveTierList -  "+generalUtils.getLogTextForRequest());

        // Get the tier
        Tier tier = tierWrapper.get(0);

        // if the tier is null show error
        if( tier == null ) {

            // Log the response
            log.info("saveTierList - No tier information found in the request");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);


        }


        // Check the validity of the tier
        tierService.isTierRequestAuthorized(authSessionUtils.getMerchantNo(), authSessionUtils.getUserLocation(),authSessionUtils.getUserType(),tier.getTieParentGroup());

        // Call the saveTierList
        boolean isSaved = tierService.saveTierList(tierWrapper,authSessionUtils.getUserNo());

        // Check if the tierService isSaved
        if ( !isSaved ) {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the response code as operation failed
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

        } else {

            // Set the status as success
            retData.setStatus(APIResponseStatus.success);

        }




        // Log the response
        log.info("saveTier - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/tiering/tier/delete/{tieId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteTier(@PathVariable(value="tieId") Long tieId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("deleteTier - Request Received# Tier ID: "+tieId);
        log.info("deleteTier -  "+generalUtils.getLogTextForRequest());

        // Get the tier information
        Tier tier = tierService.findByTieId(tieId);

        // Check if the tier is found
        if ( tier == null || tier.getTieId() == null) {

            // Log the response
            log.info("deleteTier - Response : No tier information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check the validity of the tier
        // Check the validity of the tier
        tierService.isTierRequestAuthorized(authSessionUtils.getMerchantNo(), authSessionUtils.getUserLocation(),authSessionUtils.getUserType(),tier.getTieParentGroup());


        // Delete the tier and set the retData fields
        tierService.validateAndDeleteTier(tieId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete tieId
        retData.setData(tieId);


        // Log the response
        log.info("deleteTier - " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/tiering/tiers/{tieParentGroup}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listTiers(@PathVariable(value="tieParentGroup") Long tieParentGroup) throws InspireNetzException {



        // Log the Request
        log.info("listTiers - Request Received# Parent Group " + tieParentGroup );
        log.info("listTiers -  "+generalUtils.getLogTextForRequest());



        // Check the validity of the tier
        tierService.isTierRequestAuthorized(authSessionUtils.getMerchantNo(), authSessionUtils.getUserLocation(),authSessionUtils.getUserType(),tieParentGroup);



        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the tier list
        List<Tier> tierList = tierService.findByTieParentGroup(tieParentGroup);

        // Variable holding the tier
        List<TierResource> tierResourceList = tierAssembler.toResources(tierList);

        // Set the data
        retData.setData(tierResourceList);


        // Log the response
        log.info("listTiers - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/tiering/alltiers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listTiersForGroup() throws InspireNetzException {



        // Log the Request
        log.info("listTiers - Request Received#");
        log.info("listTiers -  "+generalUtils.getLogTextForRequest());


        // Get the merchatnNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the tier list
        List<Tier> tierList = tierService.listTiersForGroup(merchantNo);

        // Variable holding the tier
        List<TierResource> tierResourceList = tierAssembler.toResources(tierList);

        // Set the data
        retData.setData(tierResourceList);


        // Log the response
        log.info("listTiers - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/tiering/tier/{tieId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getTierInfo(@PathVariable(value="tieId") Long tieId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getTierInfo - Request Received# Tier ID: "+tieId);
        log.info("getTierInfo -  "+generalUtils.getLogTextForRequest());

        // Get the tier information
        Tier tier = tierService.findByTieId(tieId);

        // Check if the tier is found
        if ( tier == null || tier.getTieId() == null) {

            // Log the response
            log.info("getTierInfo - Response : No tier information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check the validity of the tier
        // Check the validity of the tier
        tierService.isTierRequestAuthorized(authSessionUtils.getMerchantNo(), authSessionUtils.getUserLocation(),authSessionUtils.getUserType(),tier.getTieParentGroup());


        // Convert the Tier to TierResource
        TierResource tierResource = tierAssembler.toResource(tier);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the tierResource object
        retData.setData(tierResource);




        // Log the response
        log.info("getTierInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/tiering/manual/evaluate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject tierManualEvaluate() throws InspireNetzException {


        // Log the information
        log.info("Running tier manual update");

        // Call the tier evaluation
        tierService.startTierEvaluation(1L);

        // Return the object
        return new APIResponseObject();
    }
}
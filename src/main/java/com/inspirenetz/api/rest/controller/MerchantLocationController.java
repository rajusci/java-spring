package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.MerchantLocationWrapper;
import com.inspirenetz.api.core.dictionary.TierWrapper;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.service.MerchantLocationService;
import com.inspirenetz.api.rest.assembler.MerchantLocationAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.MerchantLocationResource;
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
public class MerchantLocationController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(MerchantLocationController.class);

    @Autowired
    private MerchantLocationService merchantLocationService;

    @Autowired
    private MerchantLocationAssembler merchantLocationAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/location", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveMerchantLocation(@Valid MerchantLocation merchantLocation,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the merchantLocation
        merchantLocation.setMelMerchantNo(merchantNo);


        // Log the Request
        log.info("saveMerchantLocation - Request Received# "+merchantLocation.toString());
        log.info("saveMerchantLocation -  "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveMerchantLocation - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // Check if the merchantLocation is existing
        boolean isExist = merchantLocationService.isMerchantLocationDuplicateExisting(merchantLocation);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveMerchantLocation - Response : MerchantLocation  is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the merchantLocation.getMelId is  null, then set the created_by, else set the updated_by
        if ( merchantLocation.getMelId() == null ) {

            merchantLocation.setCreatedBy(auditDetails);

        } else {

            merchantLocation.setUpdatedBy(auditDetails);

        }


        // save the merchantLocation object and get the result
        merchantLocation = merchantLocationService.validateAndSaveMerchantLocation(merchantLocation);

        // If the merchantLocation object is not null ,then return the success object
        if ( merchantLocation.getMelId() != null ) {

            // Get the merchantLocation id
            retData.setData(merchantLocation.getMelId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveMerchantLocation - Response : Unable to save the merchantLocation information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveMerchantLocation -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/location/delete/{melId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteMerchantLocation(@PathVariable(value="melId") Long melId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteMerchantLocation - Request Received# Mel Id"+melId);
        log.info("deleteMerchantLocation -  "+generalUtils.getLogTextForRequest());


        // Get the merhcant Locatio information for the given merchant location
        MerchantLocation merchantLocation = merchantLocationService.findByMelId(melId);

        // Check if the MerchantLocation is existing
        if ( merchantLocation == null ) {

            // Log the response
            log.info("deleteMerchantLocation - Response : Not merchantLocation information found ");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( merchantLocation.getMelMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteMerchantLocation - Response : You are not authorized to delete the merchantLocation");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the merchantLocation and set the retData fields
        merchantLocationService.validateAndDeleteMerchantLocation(melId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete melId
        retData.setData(melId);

        // Log the response
        log.info("deleteMerchantLocation -  " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }



    @RequestMapping(value = "/api/0.9/json/merchant/location/name/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getMerchantLocationsByName(@PathVariable(value = "query") String query,Pageable pageable){


        // Log the Request
        log.info("searchByMerchantLocationName - Request Received# "+query);
        log.info("searchByMerchantLocationName -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the list of matching merchantLocations
        Page<MerchantLocation> merchantLocationList = merchantLocationService.findByMelMerchantNoAndMelLocationLike(merchantNo, "%" + query + "%", pageable);

        // Get the list of the merchantLocationResources
        List<MerchantLocationResource> merchantLocationResourceList = merchantLocationAssembler.toResources(merchantLocationList);

        // Set the data
        retData.setData(merchantLocationResourceList);

        // Log the response
        log.info("searchByMerchantLocationName -  " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/locations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantLocations(Pageable pageable){


        // Log the Request
        log.info("listMerchantLocations - Request Received# ");
        log.info("listMerchantLocations -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the list of matching merchantLocations
        Page<MerchantLocation> merchantLocationList = merchantLocationService.findByMelMerchantNo(merchantNo,pageable);

        // Get the list of the merchantLocationResources
        List<MerchantLocationResource> merchantLocationResourceList = merchantLocationAssembler.toResources(merchantLocationList);

        // Set the data
        retData.setData(merchantLocationResourceList);


        // Log the response
        log.info("listMerchantLocations -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/locations/{usrMerchantNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantLocationsForSuperAdmin(@PathVariable(value = "usrMerchantNo") Long merchantNo,Pageable pageable){


        // Log the Request
        log.info("listMerchantLocationsForSuperAdmin - Request Received# ");
        log.info("listMerchantLocationsForSuperAdmin -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
       // Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the list of matching merchantLocations
        Page<MerchantLocation> merchantLocationList = merchantLocationService.findByMelMerchantNo(merchantNo,pageable);

        // Get the list of the merchantLocationResources
        List<MerchantLocationResource> merchantLocationResourceList = merchantLocationAssembler.toResources(merchantLocationList);

        // Set the data
        retData.setData(merchantLocationResourceList);


        // Log the response
        log.info("listMerchantLocationsForSuperAdmin -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/locations/allocation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getAllLocation(){


        // Log the Request
        log.info("getAllLocation -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        // Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the list of matching merchantLocations
        List<MerchantLocation> merchantLocationList = merchantLocationService.findAll();

        // Get the list of the merchantLocationResources
        List<MerchantLocationResource> merchantLocationResourceList = merchantLocationAssembler.toResources(merchantLocationList);

        // Set the data
        retData.setData(merchantLocationResourceList);

        // Log the response
        log.info("getAllLocation -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


   /* @RequestMapping(value = "/api/0.9/json/merchant/locations/locationlist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveLocationList(@RequestBody MerchantLocationWrapper locationWrapper) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("saveLocationList - Request Received# "+locationWrapper.toString());
        log.info("saveLocationList - Requested User  # Login Id : "+authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());

        // Call the saveTierList
        boolean isSaved = merchantLocationService.saveLocationList(locationWrapper,authSessionUtils.getUserNo());

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
        log.info("saveTier - Response : " + retData.toString());

        // Return the APIResponseobject
        return retData;


    }*/

}

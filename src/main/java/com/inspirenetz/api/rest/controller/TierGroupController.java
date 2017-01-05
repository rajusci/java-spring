package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.domain.TierGroup;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.core.service.TierGroupService;
import com.inspirenetz.api.rest.assembler.TierGroupAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.TierGroupResource;
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
import java.util.List;

/**
 * Created by saneeshci on 20/8/14.
 *
 */
@Controller
public class TierGroupController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(TierGroupController.class);

    @Autowired
    private TierGroupService tierGroupService;

    @Autowired
    private TierGroupAssembler tierGroupAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    ImageService imageService;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/merchant/tiering/tiergroup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveTierGroup(@Valid TierGroup tierGroup,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the tierGroup
        tierGroup.setTigMerchantNo(merchantNo);


        // Log the Request
        log.info("saveTierGroup - Request Received# "+tierGroup.toString());
        log.info("saveTierGroup - "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveTierGroup - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }



        // If the tierGroup.getTigId is  null, then set the created_by, else set the updated_by
        if ( tierGroup.getTigId() == null ) {

            tierGroup.setCreatedBy(auditDetails);

        } else {

            tierGroup.setUpdatedBy(auditDetails);

        }


        // save the tierGroup object and get the result
        tierGroup = tierGroupService.saveTierGroup(tierGroup);

        // If the tierGroup object is not null ,then return the success object
        if ( tierGroup.getTigId() != null ) {

            // Get the tierGroup id
            retData.setData(tierGroup.getTigId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveTierGroup - Response : Unable to save the tierGroup information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveTierGroup - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/tiering/tiergroup/delete/{tigId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteTierGroup(@PathVariable(value="tigId") Long tigId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteTierGroup - Request Received# TierGroup ID: "+tigId);
        log.info("deleteTierGroup - "+generalUtils.getLogTextForRequest());

        // Get the tierGroup information
        TierGroup tierGroup = tierGroupService.findByTigId(tigId);

        // Check if the tierGroup is found
        if ( tierGroup == null || tierGroup.getTigId() == null) {

            // Log the response
            log.info("deleteTierGroup - Response : No tierGroup information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( tierGroup.getTigMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteTierGroup - Response : You are not authorized to delete the tierGroup");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the tierGroup and set the retData fields
        tierGroupService.deleteTierGroup(tigId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete brnId
        retData.setData(tigId);


        // Log the response
        log.info("deleteTierGroup - " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/tiering/tiergroups/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listTierGroups(@PathVariable(value ="filter") String filter,
                                        @PathVariable(value ="query") String query,
                                        Pageable pageable){


        // Log the Request
        log.info("listTierGroups - Request Received# filter "+ filter +" query :" +query );
        log.info("listTierGroups - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        Long location = authSessionUtils.getUserLocation();

        Integer userType = authSessionUtils.getUserType();

        log.info("merNo:"+merchantNo+ "location:"+location+"userType:"+userType);
        // Variable holding the tierGroup
        List<TierGroupResource> tierGroupResourceList = new ArrayList<>(0);


            // Get the page
        Page<TierGroup> tierGroupPage = tierGroupService.searchTierGroups(merchantNo,location,userType,filter,query, pageable);

        // Convert to Resource
        tierGroupResourceList = tierGroupAssembler.toResources(tierGroupPage);

        // Set the pageable params for the retData
        retData.setPageableParams(tierGroupPage);

        // SEt the data
        retData.setData(tierGroupResourceList);

        // Log the information
        log.info("listTierGroups - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/tiering/tiergroup/{tigId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getTierGroupInfo(@PathVariable(value="tigId") Long tigId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getTierGroupInfo - Request Received# TierGroup ID: "+tigId);
        log.info("getTierGroupInfo - "+generalUtils.getLogTextForRequest());

        // Get the tierGroup information
        TierGroup tierGroup = tierGroupService.findByTigId(tigId);



        // Check if the tierGroup is found
        if ( tierGroup == null || tierGroup.getTigId() == null) {

            // Log the response
            log.info("getTierGroupInfo - Response : No tierGroup information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( tierGroup.getTigMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getTierGroupInfo - Response : You are not authorized to view the tierGroup");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }




        // Convert the TierGroup to TierGroupResource
        TierGroupResource tierGroupResource = tierGroupAssembler.toResource(tierGroup);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the tierGroupResource object
        retData.setData(tierGroupResource);




        // Log the response
        log.info("getTierGroupInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

}

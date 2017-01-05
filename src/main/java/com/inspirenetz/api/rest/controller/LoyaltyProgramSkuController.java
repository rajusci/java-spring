package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.AttributeExtensionMapType;
import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.core.domain.validator.LoyaltyProgramSkuValidator;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.service.LoyaltyProgramSkuService;
import com.inspirenetz.api.rest.assembler.LoyaltyProgramSkuAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.LoyaltyProgramSkuResource;
import com.inspirenetz.api.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class LoyaltyProgramSkuController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(LoyaltyProgramSkuController.class);

    @Autowired
    private LoyaltyProgramSkuService loyaltyProgramSkuService;

    @Autowired
    private LoyaltyProgramSkuAssembler loyaltyProgramSkuAssembler;

    @Autowired
    private AttributeExtensionUtils attributeExtensionUtils;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/loyaltyprogram/sku", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveLoyaltyProgramSku(@RequestParam Map<String,String> params) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();



        // Log the Request
        log.info("saveLoyaltyProgramSku - Request Received# "+params.toString());
        log.info("saveLoyaltyProgramSku - "+generalUtils.getLogTextForRequest());


        // Get the attributeExtendedEntityMap
        AttributeExtendedEntityMap attributeExtendedEntityMap = attributeExtensionUtils.buildAttributeExtendedEntityMapFromMap(params);

        // Create the LoyaltyProgramSku oject
        LoyaltyProgramSku loyaltyProgramSku;

        // Check if the attributeMap has got the id field
        if ( attributeExtendedEntityMap.get("lpuId") != null  && attributeExtendedEntityMap.get("lpuId").equals("")) {

            loyaltyProgramSku = loyaltyProgramSkuService.findByLpuId(Long.parseLong(attributeExtendedEntityMap.get("lpuId").toString()));

        } else {

            loyaltyProgramSku = new LoyaltyProgramSku();

        }


        // Parse the object from map
        loyaltyProgramSku = (LoyaltyProgramSku) loyaltyProgramSkuService.fromAttributeExtensionMap(loyaltyProgramSku,attributeExtendedEntityMap, AttributeExtensionMapType.ALL);



        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Create the validator
        LoyaltyProgramSkuValidator validator = new LoyaltyProgramSkuValidator();

        // Create the BeanPropertyBindingResult to validate the fields
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(loyaltyProgramSku,"loyaltyProgramSku");

        // Validate the input
        validator.validate(loyaltyProgramSku,result);



        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveLoyaltyProgramSku - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        /*
         * TODO - Get the parent program for the given program id and make sure that it belongs
         *        to the same merchant number
         */

        boolean isExist = loyaltyProgramSkuService.isLoyaltyProgramSkuExisting(loyaltyProgramSku);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveLoyaltyProgramSku - Response : LoyaltyProgramSku code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the loyaltyProgramSku.getLpuId is  null, then set the created_by, else set the updated_by
        if ( loyaltyProgramSku.getLpuId() == null ) {

            loyaltyProgramSku.setCreatedBy(auditDetails);

        } else {

            loyaltyProgramSku.setUpdatedBy(auditDetails);

        }


        // save the loyaltyProgramSku object and get the result
        loyaltyProgramSku = loyaltyProgramSkuService.saveLoyaltyProgramSku(loyaltyProgramSku);

        // If the loyaltyProgramSku object is not null ,then return the success object
        if ( loyaltyProgramSku.getLpuId() != null ) {

            // Get the loyaltyProgramSku id
            retData.setData(loyaltyProgramSku.getLpuId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveLoyaltyProgramSku - Response : Unable to save the loyaltyProgramSku information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveLoyaltyProgramSku -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/loyaltyprogram/sku/delete/{lpuId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteLoyaltyProgramSku(@PathVariable( value = "lpuId") Long lpuId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteLoyaltyProgramSku - Request Received# Product No:"+ lpuId);
        log.info("deleteLoyaltyProgramSku - "+generalUtils.getLogTextForRequest());


        // Get the loyaltyProgramSku information
        LoyaltyProgramSku loyaltyProgramSku = loyaltyProgramSkuService.findByLpuId(lpuId);



        // If no data found, then set error
        if ( loyaltyProgramSku == null ) {

            // Log the response
            log.info("deleteLoyaltyProgramSku - Response : No loyaltyProgramSku information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

         /*
         * TODO - Get the parent program for the given program id and make sure that it belongs
         *        to the same merchant number

        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( loyaltyProgramSku.getLpuMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteLoyaltyProgramSku - Response : You are not authorized to delete the loyaltyProgramSku");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }
        */


        // Delete the loyaltyProgramSku and set the retData fields
        loyaltyProgramSkuService.deleteLoyaltyProgramSku(loyaltyProgramSku.getLpuId());

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete catId
        retData.setData(loyaltyProgramSku.getLpuId());



        // Log the response
        log.info("deleteLoyaltyProgramSku -  " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/loyaltyprogram/sku/program/{lpuProgramId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getLoyaltyProgramSkusByProgramId(@PathVariable(value = "lpuProgramId") Long lpuProgramId,Pageable pageable){


        // Log the Request
        log.info("getLoyaltyProgramSkusByProgramId - Request Received# "+lpuProgramId);
        log.info("getLoyaltyProgramSkusByProgramId - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        /*
         * TODO - Get the parent program for the given program id and make sure that it belongs
         *        to the same merchant number
         */
        // Get the list of matching loyaltyProgramSkus
        Page<LoyaltyProgramSku> loyaltyProgramSkuList = loyaltyProgramSkuService.findByLpuProgramId(lpuProgramId,pageable);

        // Get the list of the loyaltyProgramSkuResources
        List<AttributeExtendedEntityMap> loyaltyProgramSkuResourceList = loyaltyProgramSkuAssembler.toAttibuteEntityMaps(loyaltyProgramSkuList);

        // Set the data
        retData.setData(loyaltyProgramSkuResourceList);

        // Log the response
        log.info("getLoyaltyProgramSkusByProgramId -  " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

}

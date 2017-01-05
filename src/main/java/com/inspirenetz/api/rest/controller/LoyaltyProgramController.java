package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.AttributeExtensionMapType;
import com.inspirenetz.api.core.dictionary.LoyaltyProgramProductBasedItem;
import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.core.domain.validator.LoyaltyProgramValidator;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.service.LoyaltyEngineService;
import com.inspirenetz.api.core.service.LoyaltyProgramService;
import com.inspirenetz.api.rest.assembler.LoyaltyProgramAssembler;
import com.inspirenetz.api.rest.assembler.LoyaltyProgramProductBasedItemAssembler;
import com.inspirenetz.api.rest.assembler.LoyaltyProgramSkuAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.LoyaltyProgramProductBasedItemResource;
import com.inspirenetz.api.rest.resource.LoyaltyProgramResource;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class LoyaltyProgramController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(LoyaltyProgramController.class);

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;

    @Autowired
    private LoyaltyProgramAssembler loyaltyProgramAssembler;

    @Autowired
    private LoyaltyProgramProductBasedItemAssembler loyaltyProgramProductBasedItemAssembler;

    @Autowired
    private LoyaltyProgramSkuAssembler loyaltyProgramSkuAssembler;

    @Autowired
    private AttributeExtensionUtils attributeExtensionUtils;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private LoyaltyEngineService loyaltyEngineService;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/merchant/loyaltyprogram", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveLoyaltyProgram(@RequestBody Map<String,Object> params) throws InspireNetzException {

        // Log the information
        log.info("saveLoyaltyProgram : Request received : "+ params);
        log.info("saveLoyaltyProgram - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Try saving the object
        LoyaltyProgram loyaltyProgram = loyaltyProgramService.validateAndSaveLoyaltyProgramDataFromParams(params);

        // Set the retData status to be success
        retData.setStatus(APIResponseStatus.success);

        // Set the retData data to be loyalty program
        retData.setData(loyaltyProgram.getPrgProgramNo());

        // Log the response
        log.info("saveLoyaltyProgram -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/loyaltyprogram/delete/{prgProgramNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteLoyaltyProgram(@PathVariable( value = "prgProgramNo") Long prgProgramNo) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("deleteLoyaltyProgram - Request Received# Program No:"+ prgProgramNo);
        log.info("deleteLoyaltyProgram -  "+generalUtils.getLogTextForRequest());

        loyaltyProgramService.validateAndDeleteLoyaltyProgram(prgProgramNo);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete prgProgramNo
        retData.setData(prgProgramNo);



        // Log the response
        log.info("deleteLoyaltyProgram -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/loyaltyprogram/{prgProgramNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getLoyaltyProgramInfo(@PathVariable(value = "prgProgramNo") Long prgProgramNo) throws InspireNetzException {


        // Log the Request
        log.info("getLoyaltyProgramInfo - Request Received# "+prgProgramNo);
        log.info("getLoyaltyProgramInfo -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the LoyaltyProgram object
        LoyaltyProgram loyaltyProgram = loyaltyProgramService.getLoyaltyProgramInfo(prgProgramNo);

        // Create the LoyaltyProgramResource
        AttributeExtendedEntityMap loyaltyProgramResource = loyaltyProgramAssembler.toAttibuteEntityMap(loyaltyProgram);

        // If the loyaltyProgramSkuSet is not null and empty, then add that also
        // to the resource
        if ( loyaltyProgram.getLoyaltyProgramSkuSet() != null && !loyaltyProgram.getLoyaltyProgramSkuSet().isEmpty() ) {

            // Convert skuSet to resource list
            List<AttributeExtendedEntityMap> loyaltyProgramSkuResourceList = loyaltyProgramSkuAssembler.toAttibuteEntityMaps(loyaltyProgram.getLoyaltyProgramSkuSet());

            // Add to the list
            loyaltyProgramResource.put("loyaltyProgramSkuSet",loyaltyProgramSkuResourceList);

        }

        // Set the retData
        retData.setData(loyaltyProgramResource);



        // Log the response
        log.info("getLoyaltyProgramInfo -  " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/loyaltyprogram/items/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listLoyaltyProgramProductItems(   @PathVariable(value = "filter") String filter,
                                                               @PathVariable(value = "query") String query,
                                                               Pageable pageable){
        

        // Log the Request
        log.info("listLoyaltyProgramProductItems - Request Received# filter "+ filter +" query :" +query );
        log.info("listLoyaltyProgramProductItems -  "+generalUtils.getLogTextForRequest());



        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the items list
        List<LoyaltyProgramProductBasedItem> loyaltyProgramProductBasedItemList = loyaltyProgramService.getLoyaltyProgramProductBasedItems(filter,query,pageable);

        // Get the resources
        List<LoyaltyProgramProductBasedItemResource> loyaltyProgramProductBasedItemResourceList = loyaltyProgramProductBasedItemAssembler.toResources(loyaltyProgramProductBasedItemList);



        // Set the data
        retData.setData(loyaltyProgramProductBasedItemResourceList);

        // Log the response
        log.info("listLoyaltyProgramProductItems -  " + generalUtils.getLogTextForResponse(retData));



        // Return the success object
        return retData;
        
        
    }


    @RequestMapping(value = "/api/0.9/json/merchant/loyaltyprograms/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listLoyaltyPrograms(   @PathVariable(value = "filter") String filter,
                                                    @PathVariable(value = "query") String query,
                                                    Pageable pageable){


        // Log the Request
        log.info("listLoyaltyPrograms - Request Received# filter "+ filter +" query :" +query );
        log.info("listLoyaltyPrograms -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the LoyaltyProgram page
        Page<LoyaltyProgram> loyaltyProgramPage =  loyaltyProgramService.searchLoyaltyPrograms(filter,query,pageable);

        // List holding the return loyaltyProgramList
        List<AttributeExtendedEntityMap> loyaltyProgramResourceList = loyaltyProgramAssembler.toAttibuteEntityMaps(loyaltyProgramPage);

        // Set the data
        retData.setData(loyaltyProgramResourceList);

        // Set the pageable params
        retData.setPageableParams(loyaltyProgramPage);

        // Log the response
        log.info("listLoyaltyPrograms -  " + generalUtils.getLogTextForResponse(retData));




        // Return the success object
        return retData;


    }




    @RequestMapping(value = "/api/0.9/json/merchant/loyaltyprogram/status/{prgProgramNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updateLoyaltyProgramStatus(    @PathVariable(value="prgProgramNo") Long prgProgramNo,
                                                            @RequestParam(value = "prgStatus") Integer prgStatus) throws InspireNetzException {


        // Log the Request
        log.info("updateLoyaltyProgramStatus - Request Received# Program No"+prgProgramNo + " status : " + prgStatus);
        log.info("updateLoyaltyProgramStatus -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();




        // Call the updateProgramStatus
        boolean updated = loyaltyProgramService.updateLoyaltyProgramStatus(prgProgramNo,prgStatus,merchantNo,authSessionUtils.getUserNo());

        // if the updated is true, then set the data
        if ( updated ) {

            // set the retData to success
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.failed);

        }


        // Set the data to the status which was set
        retData.setData(prgStatus);

        // Log the response
        log.info("updateLoyaltyProgramStatus -  " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    // THIS IS A TEST ONLY CONTROLLER, NEED TO BE CLEARED ON PRODUCTION
    @RequestMapping(value = "/api/0.9/json/merchant/loyaltyprogram/datetriggered/start", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject doDateTriggered( ) throws InspireNetzException {

        log.info("LoyaltyProgram -> Requesting to start date triggered processing");

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // call the datetriggered
        loyaltyEngineService.runScheduledProcessing();

        // Return the success object
        return retData;

    }

    @RequestMapping(value = "/api/0.9/json/customer/loyaltyprograms/{merchantNo}/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listLoyaltyProgramsForCustomer(@PathVariable(value = "merchantNo") Long merchantNo,
                                                    @PathVariable(value = "filter") String filter,
                                                    @PathVariable(value = "query") String query,
                                                    Pageable pageable){


        // Log the Request
        log.info("listLoyaltyProgramsForCustomer - Request Received# merchant :"+merchantNo+" filter "+ filter +" query :" +query );
        log.info("listLoyaltyProgramsForCustomer -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        String usrLoginId=authSessionUtils.getUserLoginId();

        // Get the LoyaltyProgram page
        List<LoyaltyProgram> loyaltyPrograms =  loyaltyProgramService.searchLoyaltyProgramsForCustomer(usrLoginId,merchantNo,filter,query);

        // List holding the return loyaltyProgramList
        List<AttributeExtendedEntityMap> loyaltyProgramResourceList = loyaltyProgramAssembler.toAttibuteEntityMaps(loyaltyPrograms);

        // Set the data
        retData.setData(loyaltyProgramResourceList);


        // Log the response
        log.info("listLoyaltyProgramsForCustomer -  " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

}
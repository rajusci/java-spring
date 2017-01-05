package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.core.domain.validator.CardTypeValidator;
import com.inspirenetz.api.core.service.CardTypeService;
import com.inspirenetz.api.rest.assembler.CardTypeAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CardTypeResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
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
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class CardTypeController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CardTypeController.class);

    @Autowired
    private CardTypeService cardTypeService;

    @Autowired
    private CardTypeAssembler cardTypeAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;




    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardtype", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveCardType(@Valid CardType cardType,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the cardType
        cardType.setCrtMerchantNo(merchantNo);


        // Log the Request
        log.info("saveCardType - Request Received# "+cardType.toString());
        log.info("saveCardType - Requested User  # Login Id : "+authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());

        // Check the data with the validator as well
        CardTypeValidator validator = new CardTypeValidator();

        // Validate the cardType
        validator.validate(cardType,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveCardType - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // save the cardType object and get the result
        cardType = cardTypeService.validateAndSaveCardType(cardType);

        // If the cardType object is not null ,then return the success object
        if ( cardType.getCrtId() != null ) {

            // Get the cardType id
            retData.setData(cardType.getCrtId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveCardType - Response : Unable to save the cardType information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveCardType - Response : " + retData.toString());

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardtype/delete/{crtId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteCardType(@PathVariable(value="crtId") Long crtId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteCardType - Request Received# CardType ID: "+crtId);
        log.info("deleteCardType - Requested User  # Login Id : "+authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());



        // Delete the cardType and set the retData fields
        cardTypeService.validateAndDeleteCardType(crtId,merchantNo);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete crtId
        retData.setData(crtId);


        // Log the response
        log.info("deleteCardType - Response : " + retData.toString());


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardtypes/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCardTypes(@PathVariable(value ="filter") String filter,
                                        @PathVariable(value ="query") String query,
                                        Pageable pageable){


        // Log the Request
        log.info("listCardTypes - Request Received# filter "+ filter +" query :" +query );
        log.info("listCardTypes - Requested User  # Login Id : "+authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Variable holding the cardType
        List<CardTypeResource> cardTypeResourceList = new ArrayList<>(0);

        //call the search method
        Page<CardType> cardTypePage = cardTypeService.searchCardTypes(filter,query,merchantNo,pageable);

        // Convert to Resource
        cardTypeResourceList = cardTypeAssembler.toResources(cardTypePage);

        // Set the pageable params for the retData
        retData.setPageableParams(cardTypePage);

        // Set the data
        retData.setData(cardTypeResourceList);

        // Log the response
        log.info("listCardTypes - Response : " + retData.toString());

        // Return the success object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardtype/{crtId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCardTypeInfo(@PathVariable(value="crtId") Long crtId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getCardTypeInfo - Request Received# CardType ID: "+crtId);
        log.info("getCardTypeInfo - Requested User  # Login Id : "+authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());

        // Get the cardType information
        CardType cardType = cardTypeService.getCardTypeInfo(crtId,merchantNo);

        // Convert the CardType to CardTypeResource
        CardTypeResource cardTypeResource = cardTypeAssembler.toResource(cardType);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the cardTypeResource object
        retData.setData(cardTypeResource);

        // Log the response
        log.info("getCardTypeInfo - Response : " + retData.toString());

        // Return the retdata object
        return retData;


    }

}

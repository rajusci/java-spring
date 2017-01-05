package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.CardNumberInfoRequest;
import com.inspirenetz.api.core.domain.CardNumberInfo;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.core.service.CardNumberInfoService;
import com.inspirenetz.api.core.service.CardTypeService;
import com.inspirenetz.api.rest.assembler.CardNumberInfoAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CardNumberInfoRequestResource;
import com.inspirenetz.api.rest.resource.CardNumberInfoResource;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ameen on 21/10/15.
 */
@Controller
public class CardNumberInfoController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CardNumberInfoController.class);

    @Autowired
    private CardNumberInfoService cardNumberInfoService;

    @Autowired
    private CardTypeService cardTypeService;

    @Autowired
    private CardNumberInfoAssembler cardNumberInfoAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/card/cardnumberinfobatch",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject processCardFile(CardNumberInfoRequestResource cardNumberInfoRequestResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("processCardFile - Request Received# "+cardNumberInfoRequestResource.toString());
        log.info("processCardFile -  "+generalUtils.getLogTextForRequest());

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        //map the request
        CardNumberInfoRequest cardNumberInfoRequest = mapper.map(cardNumberInfoRequestResource,CardNumberInfoRequest.class);

        //then set the merchant number
        cardNumberInfoRequest.setCniMerchantNo(merchantNo);

        //process file operation
        cardNumberInfoService.processBatchFile(cardNumberInfoRequest);

        //set the status
        retData.setStatus(APIResponseStatus.success);
        // Log the response
        log.info("processCardFile - "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponse object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/cardnumberbatch/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCardBatchInformation(@PathVariable(value ="filter") String filter,@PathVariable("query") String query,
                                              @RequestParam(value="batchIndex",defaultValue = "0") Long batchIndex,
                                              Pageable pageable){


        // Log the Request
        log.info("listCardBatchInformation - Request Received# filter and query "+ filter+":and:"+query);
        log.info("listCardBatchInformation - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Variable holding the CardNumberInfo
        List<CardNumberInfoResource> cardNumberInfoResourceList = new ArrayList<>(0);

        Long merchantNo =authSessionUtils.getMerchantNo();

        Page<CardNumberInfo> cardNumberInfoPage=cardNumberInfoService.searchCardNumberInfo(merchantNo,batchIndex,filter,query ,pageable);

        cardNumberInfoResourceList=cardNumberInfoAssembler.toResources(cardNumberInfoPage);


        retData.setPageableParams(cardNumberInfoPage);

        // Set the data
        retData.setData(cardNumberInfoResourceList);


        // Log the response
        log.info("listCardBatchInformation-   " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/cardnumber/info",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getValidatedCardDetails(@RequestParam(value = "cardNumber") String cardNumber) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getValidatedCardDetails - Request Received #cardNumber "+cardNumber);
        log.info("getValidatedCardDetails -  "+generalUtils.getLogTextForRequest());

        CardNumberInfo cardNumberInfo=cardNumberInfoService.getValidatedCardDetails(cardNumber,"");

        if(cardNumberInfo!=null){

            CardNumberInfoResource cardNumberInfoResource=cardNumberInfoAssembler.toResource(cardNumberInfo);

            retData.setData(cardNumberInfoResource);
            retData.setStatus(APIResponseStatus.success);


        }else{

            retData.setData(null);
            retData.setErrorCode(APIErrorCode.ERR_INVALID_CARD);
            retData.setStatus(APIResponseStatus.failed);

        }

        // Log the response
        log.info("getValidatedCardDetails - "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponse object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/public/cardnumber/validate",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getValidatedCardDetailsForPublic(@RequestParam(value = "cardNumber") String cardNumber,@RequestParam(value = "mobile") String mobile,@RequestParam(value = "pin",defaultValue = "") String pin,@RequestParam(value = "merchantNo",defaultValue = "0") Long merchantNo) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getValidatedCardDetailsForPublic - Request Received #cardNumber "+cardNumber+" #mobile "+mobile);

        log.info("getValidatedCardDetailsForPublic -  from public with mobile number "+mobile);

        CardNumberInfo cardNumberInfo=cardNumberInfoService.getValidatedCardDetailsForPublic(cardNumber,mobile,pin,merchantNo);

        if(cardNumberInfo!=null){

            CardNumberInfoResource cardNumberInfoResource=cardNumberInfoAssembler.toResource(cardNumberInfo);

            retData.setData(cardNumberInfoResource);

            retData.setStatus(APIResponseStatus.success);


        }else{

            retData.setData(null);

            retData.setErrorCode(APIErrorCode.ERR_INVALID_CARD);

            retData.setStatus(APIResponseStatus.failed);

        }

        // Log the response
        log.info("getValidatedCardDetailsForPublic - "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponse object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/cardnumber/search/{filter}/{cardnumber}",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject searchValidatedCardDetails( @PathVariable(value = "filter") String filter,@PathVariable(value = "cardnumber") String cardnumber) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("searchValidatedCardDetails - Request Received #cardnumber "+cardnumber);
        log.info("getValidatedCardDetails -  "+generalUtils.getLogTextForRequest());


        CardNumberInfo cardNumberInfo=cardNumberInfoService.getValidatedCardDetails(cardnumber,"");

        if(cardNumberInfo!=null){

                CardNumberInfoResource cardNumberInfoResource=cardNumberInfoAssembler.toResource(cardNumberInfo);

                retData.setData(cardNumberInfoResource);
                retData.setStatus(APIResponseStatus.success);


        }else{

                retData.setData(null);
                retData.setErrorCode(APIErrorCode.ERR_INVALID_CARD);
                retData.setStatus(APIResponseStatus.failed);

        }


        // Log the response
        log.info("getValidatedCardDetails - "+generalUtils.getLogTextForResponse(retData));
        // Return the APIResponse object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/cardnumber/next/{cardtype}",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getNextAvailableCard(@PathVariable(value = "cardtype") Long cardType) {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the next object
        CardNumberInfo cardNumberInfo = cardNumberInfoService.getAvailableCardNumber(authSessionUtils.getMerchantNo(),cardType);

        // Set the object in the retData
        retData.setData(cardNumberInfo);

        // LOg the response
        log.info("getNextAvailableCard - Response: " +retData);

        // return the object
        return retData;

    }
}

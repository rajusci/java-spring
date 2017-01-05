package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.CardMaster;
import com.inspirenetz.api.core.service.CardMasterService;
import com.inspirenetz.api.rest.assembler.CardMasterAssembler;
import com.inspirenetz.api.rest.assembler.CardMasterCompatibleAssembler;
import com.inspirenetz.api.rest.assembler.CardMasterOperationCompatibleAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CardMasterCompatibleResource;
import com.inspirenetz.api.rest.resource.CardMasterOperationCompatibleResource;
import com.inspirenetz.api.rest.resource.CardMasterResource;
import com.inspirenetz.api.util.*;
import org.dozer.Mapper;
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
import java.sql.Date;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class CardMasterController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CardMasterController.class);

    @Autowired
    private CardMasterService cardMasterService;

    @Autowired
    private CardMasterAssembler cardMasterAssembler;

    @Autowired
    private CardMasterOperationCompatibleAssembler cardMasterOperationCompatibleAssembler;

    @Autowired
    private CardMasterCompatibleAssembler cardMasterCompatibleAssembler;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;




    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveCardMaster(CardMasterResource cardMasterResource,@RequestParam(value="reissue",defaultValue = "0") boolean isReissue,@RequestParam(value="overridestatus",defaultValue = "0") Integer overrideStatus ,@RequestParam(value="location",defaultValue = "") String location) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the cardMaster
        cardMasterResource.setCrmMerchantNo(merchantNo);

        cardMasterResource.setCrmLocation(authSessionUtils.getUserLocation());

        // Log the Request
        log.info("saveCardMaster - Request Received# "+cardMasterResource.toString());

        log.info("saveCardMaster - Requested -"+generalUtils.getLogTextForRequest() );

        //card master object
        CardMasterOperationResponse cardMasterOperationResponse =new CardMasterOperationResponse();

        // save the cardMaster object and get the result
        if(cardMasterResource.getCrmId() ==null||cardMasterResource.getCrmId().longValue()==0 || (cardMasterResource.getCrmId() !=null && isReissue)){

            //card master operation
             cardMasterOperationResponse = cardMasterService.validateAndIssueCardFromMerchant(cardMasterResource, authSessionUtils.getUserNo(), authSessionUtils.getUserLoginId(), isReissue,overrideStatus,location);

        }else {

            //update card master information
            if(overrideStatus.intValue() !=0){

                cardMasterResource.setCrmCardStatus(overrideStatus);

            }

            CardMaster cardMaster1 =cardMasterService.validateAndSaveCardMaster(cardMasterResource);

            cardMasterResource =cardMasterAssembler.toResource(cardMaster1);

            retData.setStatus(APIResponseStatus.success);

            retData.setData(cardMasterResource);

            log.info("saveCardMaster - Response : " + generalUtils.getLogTextForResponse(retData));

            return  retData;
        }


        // If the cardMaster object is not null ,then return the success object
        if ( cardMasterOperationResponse != null ) {

            // Set the cardMasterOperationResponse
            retData.setData(cardMasterOperationResponse);

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);


        } else {

            // Log the response
            log.info("saveCardMaster - Response : Unable to save the cardMaster information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Log the response
        log.info("saveCardMaster - Response : " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmaster/delete/{crmId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteCardMaster(@PathVariable(value="crmId") Long crmId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteCardMaster - Request Received# CardMaster ID: "+crmId);

        log.info("deleteCardMaster - Requested -"+generalUtils.getLogTextForRequest() );


        // Delete the cardMaster and set the retData fields
        cardMasterService.validateAndDeleteCardMaster(crmId,merchantNo);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete crmId
        retData.setData(crmId);


        // Log the response
        log.info("deleteCardMaster - Response : " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmasters/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCardMasters(@PathVariable(value ="filter") String filter,
                                        @PathVariable(value ="query") String query,
                                        Pageable pageable){


        // Log the Request
        log.info("listCardMasters - Request Received# filter "+ filter +" query :" +query );

        log.info("listCardMasters - Requested -"+generalUtils.getLogTextForRequest() );


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Variable holding the cardMaster
        List<CardMasterResource> cardMasterResourceList = new ArrayList<>(0);

        Page<CardMaster> cardMasterPage = cardMasterService.searchCardMasters(filter,query,merchantNo,pageable);

        // Convert to Resource
        cardMasterResourceList = cardMasterAssembler.toResources(cardMasterPage);

        // Set the pageable params for the retData
        retData.setPageableParams(cardMasterPage);

        // Set the data
        retData.setData(cardMasterResourceList);


        // Log the response
        log.info("listCardMasters - Response : " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmaster/{crmId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCardMasterInfo(@PathVariable(value="crmId") Long crmId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getCardMasterInfo - Request Received# CardMaster ID: "+crmId);

        log.info("getCardMasterInfo - Requested -"+generalUtils.getLogTextForRequest() );

        //get card master info
        CardMaster cardMaster = cardMasterService.getCardMasterInfo(crmId,merchantNo);

        // Convert the CardMaster to CardMasterResource
        CardMasterResource cardMasterResource = cardMasterAssembler.toResource(cardMaster);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the cardMasterResource object
        retData.setData(cardMasterResource);

        // Log the response
        log.info("getCardMasterInfo - Response : " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/storedcard/cardmaster/{crmloyaltyid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCardMasterInfoByCrmLoyaltyId(@PathVariable(value="crmloyaltyid") String crmLoyaltyId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getCardMasterInfoByCrmLoyaltyId - Request Received# CrmLoyaltyId: "+crmLoyaltyId);

        log.info("getCardMasterInfoByCrmLoyaltyId - Requested -"+generalUtils.getLogTextForRequest() );

        //get card master info
        CardMaster cardMaster = cardMasterService.getCardMasterByCrmLoyaltyId(merchantNo,crmLoyaltyId);

        // Convert the CardMaster to CardMasterResource
        CardMasterResource cardMasterResource = cardMasterAssembler.toResource(cardMaster);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the cardMasterResource object
        retData.setData(cardMasterResource);

        // Log the response
        log.info("getCardMasterInfo - Response : " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/storedcard/cardmaster/manual/cardbalancyexpiry", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject processCardBalanceExpiry() throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("processCardBalanceExpiry - Request Received# ");

        log.info("processCardBalanceExpiry - Requested -"+generalUtils.getLogTextForRequest() );

        //processCardBalance Expiry
        cardMasterService.startCardBalanceExpiry();

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("processCardBalanceExpiry - Response : " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmaster/security/{status}/{cardNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject changeCardLockStatus(
                                        @PathVariable(value="status") String status,
                                        @PathVariable(value="cardNo") String cardNo
                                     ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("changeCardLockStatus - Request Received# CardNo: "+cardNo + " status : "+status);

        log.info("changeCardLockStatus - Requested -"+generalUtils.getLogTextForRequest() );


        // Variable holding the status
        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.changeCardLockStatus(cardNo,status,merchantNo);

        // Check the response
        if ( cardMasterOperationResponse != null ) {

            // Set the retData to success
            retData.setStatus(APIResponseStatus.success);

            // Set the data
            retData.setData(cardMasterOperationResponse);

        } else {

            // Set the retData to failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the errorcode to operation failed
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // Log the response
        log.info("changeCardLockStatus - Response : " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmaster/pinchange/{cardNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject changeCardPin(
            @PathVariable(value="cardNo") String cardNo,
            @RequestParam(value="pin") String crmPin
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("changeCardPin - Request Received# Card Number ID: "+cardNo);

        log.info("changeCardPin - Requested -"+generalUtils.getLogTextForRequest() );

        // Call the service methods for changing
        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.changeCardPin(cardNo,crmPin,merchantNo);


        // Check the response
        if ( cardMasterOperationResponse != null ) {

            // Set the retData to success
            retData.setStatus(APIResponseStatus.success);

            // Set the retData
            retData.setData(cardMasterOperationResponse);

        } else {

            // Set the retData to failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the errorcode to operation failed
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // Log the response
        log.info("changeCardPin - Response : " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmaster/topup/{cardNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject topupCard(
            @PathVariable(value="cardNo") String cardNo,
            @RequestParam(value="amount") Double amount,
            @RequestParam(value = "isLoyaltyPoint",defaultValue = "0") Integer isLoyaltyPoint,
            @RequestParam(value = "rwdCurrency",defaultValue = "0") Long rwdCurrencyId,
            @RequestParam(value = "reference",defaultValue = "") String reference,
            @RequestParam(value = "paymentMode",defaultValue = "1") Integer paymentMode,
            @RequestParam(value = "location",defaultValue = "") String location,
            @RequestParam(value = "awardIncentive", defaultValue = "1") boolean awardIncentive,
            @RequestParam(value = "incentiveAmount", defaultValue = "0.0") Double incentiveAmount
    ) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("topupCard - Request Received# Card Number: "+cardNo);

        log.info("topupCard - Requested -"+generalUtils.getLogTextForRequest() );

        // Call the topup method from CardService
        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.topupCard(cardNo,amount,merchantNo,isLoyaltyPoint,rwdCurrencyId,reference,paymentMode,location,awardIncentive,incentiveAmount);

        // Check the response
        if ( cardMasterOperationResponse != null ) {

            // Set the retData to success
            retData.setStatus(APIResponseStatus.success);

            // Set the data
            retData.setData(cardMasterOperationResponse);

        } else {

            // Set the retData to failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the errorcode to operation failed
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Log the response
        log.info("topupCard - Response : " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmaster/refund/{cardNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject refundCard(
            @PathVariable(value="cardNo") String cardNo,
            @RequestParam(value="reference") String reference,
            @RequestParam(value="amount") Double amount,
            @RequestParam(value = "paymentMode",defaultValue = "1") Integer paymentMode,
            @RequestParam(value="location",defaultValue = "") String location,
            @RequestParam(value = "promoRefund", defaultValue = "0") Double promoRefund
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("refundCard - Request Received# Card Number: "+cardNo + "reference "+reference);

        log.info("refundCard - Requested -"+generalUtils.getLogTextForRequest() );



        // Call the topup method from CardService
        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.refundCard(cardNo,amount,reference,merchantNo,paymentMode,location,promoRefund);


        // Check the response
        if ( cardMasterOperationResponse != null ) {


            //check card response operation
            String cardReference =cardMasterOperationResponse.getTxnref() ==null?"":cardMasterOperationResponse.getTxnref();

            if(cardReference.equals("ERR_CARD_BALANCE_EXPIRED")){

                retData.setStatus(APIResponseStatus.failed);

                retData.setErrorCode(APIErrorCode.ERR_CARD_BALANCE_EXPIRED);

            }else {


                // Set the retData to success
                retData.setStatus(APIResponseStatus.success);

                // Set the data
                retData.setData(cardMasterOperationResponse);

            }


        } else {

            // Set the retData to failed
            retData.setStatus(APIResponseStatus.failed);

            retData.setData(new CardMasterOperationResponse());

            // Set the errorcode to operation failed
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Log the response
        log.info("refundCard - Response : " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;

    }

    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmaster/refundwithotp/{cardNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject refundCardWithOTP(
            @PathVariable(value="cardNo") String cardNo,
            @RequestParam(value="reference") String reference,
            @RequestParam(value="amount") Double amount,
            @RequestParam(value="pin",defaultValue = "",required = false) String pin,
            @RequestParam(value = "paymentMode",defaultValue = "1") Integer paymentMode,
            @RequestParam(value="location",defaultValue = "") String location,
            @RequestParam(value = "otpCode",defaultValue = "") String otpCode,
            @RequestParam(value = "promoRefund", defaultValue = "0") Double promoRefund
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("refundCard - Request Received# Card Number: "+cardNo + "reference "+reference);

        log.info("refundCard - Requested -"+generalUtils.getLogTextForRequest() );



        // Call the topup method from CardService
        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.refundCardWithOTP(cardNo,amount,reference,pin,merchantNo,otpCode,paymentMode,location,promoRefund);


        // Check the response
        if ( cardMasterOperationResponse != null ) {


            //check card response operation
            String cardReference =cardMasterOperationResponse.getTxnref() ==null?"":cardMasterOperationResponse.getTxnref();

            if(cardReference.equals("ERR_CARD_BALANCE_EXPIRED")){

                retData.setStatus(APIResponseStatus.failed);

                retData.setErrorCode(APIErrorCode.ERR_CARD_BALANCE_EXPIRED);

            }else {


                // Set the retData to success
                retData.setStatus(APIResponseStatus.success);

                // Set the data
                retData.setData(cardMasterOperationResponse);

            }


        } else {

            // Set the retData to failed
            retData.setStatus(APIResponseStatus.failed);

            retData.setData(new CardMasterOperationResponse());

            // Set the errorcode to operation failed
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Log the response
        log.info("refundCard - Response : " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;

    }

    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmaster/return/{cardNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject returnCard(
            @PathVariable(value="cardNo") String cardNo,
            @RequestParam(value="reference") String reference,
            @RequestParam(value = "paymentMode",defaultValue = "1") Integer paymentMode,
            @RequestParam(value="location",defaultValue = "") String location
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("refundCard - Request Received# Card Number: "+cardNo + "reference "+reference);

        log.info("refundCard - Requested -"+generalUtils.getLogTextForRequest() );



        // Call the topup method from CardService
        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.returnCard(cardNo,reference,merchantNo,paymentMode,location);


        // Check the response
        if ( cardMasterOperationResponse != null ) {


            //check card response operation
            String cardReference =cardMasterOperationResponse.getTxnref() ==null?"":cardMasterOperationResponse.getTxnref();

            if(cardReference.equals("ERR_CARD_BALANCE_EXPIRED")){

                retData.setStatus(APIResponseStatus.failed);

                retData.setErrorCode(APIErrorCode.ERR_CARD_BALANCE_EXPIRED);

            }else {


                // Set the retData to success
                retData.setStatus(APIResponseStatus.success);

                // Set the data
                retData.setData(cardMasterOperationResponse);

            }


        } else {

            // Set the retData to failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the errorcode to operation failed
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Log the response
        log.info("refundCard - Response : " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;

    }



    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmaster/transfer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject transferCard(
            @RequestParam(value="sourceCardNo") String sourceCardNo,
            @RequestParam(value="destCardNo") String destCardNo,
            @RequestParam(value="location",defaultValue = "") String location
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("transferCard - Request Received# Source Card Number: "+sourceCardNo + " - Dest Card Number : "+destCardNo );

        log.info("transferCard - Requested -"+generalUtils.getLogTextForRequest() );

       // Call the topup method from CardService
        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.transferCard(sourceCardNo,destCardNo,merchantNo,location);

        // Check the response
        if ( cardMasterOperationResponse != null ) {

            //check card response operation
            String cardReference =cardMasterOperationResponse.getTxnref() ==null?"":cardMasterOperationResponse.getTxnref();

            if(cardReference.equals("ERR_CARD_BALANCE_EXPIRED")){

                retData.setStatus(APIResponseStatus.failed);

                retData.setErrorCode(APIErrorCode.ERR_CARD_BALANCE_EXPIRED);

            }else {


                // Set the retData to success
                retData.setStatus(APIResponseStatus.success);

                // Set the data
                retData.setData(cardMasterOperationResponse);

            }


        } else {

            // Set the retData to failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the errorcode to operation failed
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("transferCard - Response : " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmaster/transferamount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject transferAmount(
            @RequestParam(value="sourceCardNo") String sourceCardNo,
            @RequestParam(value="destCardNo") String destCardNo,
            @RequestParam(value="pin",defaultValue = "") String pin,
            @RequestParam(value = "reference",defaultValue = "") String reference,
            @RequestParam(value = "amount") Double amount,
            @RequestParam(value = "authtype",defaultValue = "2") Integer authType,
            @RequestParam(value = "otpCode",defaultValue = "") String otpCode,
            @RequestParam(value="location",defaultValue = "") String location

    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("transferAmount - Request Received# Source Card Number: "+sourceCardNo + " - Dest Card Number : "+destCardNo );

        log.info("transferAmount - Requested -"+generalUtils.getLogTextForRequest() );

        // Call the topup method from CardService
        boolean cardMasterOperationResponse = cardMasterService.transferAmount(sourceCardNo,destCardNo,merchantNo,amount,pin,reference,authType,otpCode,location);

        //Check the response
        if(cardMasterOperationResponse){

            retData.setStatus(APIResponseStatus.success);

        }else {

            retData.setStatus(APIResponseStatus.failed);
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);
        }


        // Log the response
        log.info("transferCard - Response : " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmaster/debit/{cardNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject debitCard(
            @PathVariable(value="cardNo") String cardNo,
            @RequestParam(value="reference",defaultValue = "") String reference,
            @RequestParam(value="pin",defaultValue = "",required = false) String pin,
            @RequestParam(value="amount") Double amount,
            @RequestParam(value = "paymentMode",defaultValue = "1") Integer paymentMode,
            @RequestParam(value="location",defaultValue = "") String location
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("debitCard - Request Received# Source Card Number: "+cardNo + " - reference : "+reference + " - pin "+ pin.toString() + " - amount " + amount.toString() );

        log.info("debitCard - Requested -"+generalUtils.getLogTextForRequest() );

        //set card authentication request
        CardTransferAuthenticationRequest cardTransferAuthenticationRequest =new CardTransferAuthenticationRequest();
        cardTransferAuthenticationRequest.setAuthenticationType(CardAuthType.NOT_APPLICABLE);

        // Call the topup method from CardService
        CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.debitCard(cardNo,reference,pin,amount,merchantNo,cardTransferAuthenticationRequest,paymentMode,location);


        // Check the response
        if ( cardMasterOperationResponse != null ) {


            //check card response operation
            String cardReference =cardMasterOperationResponse.getTxnref() ==null?"":cardMasterOperationResponse.getTxnref();

            if(cardReference.equals("ERR_CARD_BALANCE_EXPIRED")){

                retData.setStatus(APIResponseStatus.failed);

                retData.setErrorCode(APIErrorCode.ERR_CARD_BALANCE_EXPIRED);

            }else {


                // Set the retData to success
                retData.setStatus(APIResponseStatus.success);

                // Set the data
                retData.setData(cardMasterOperationResponse);

            }

        } else {

            // Set the retData to failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the errorcode to operation failed
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Log the response
        log.info("debitCard - Response : " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardmaster/info/{crmCardNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCardMasterByCardNo(@PathVariable(value="crmCardNo") String crmCardNo) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getCardMasterInfo - Request Received# CardMaster CardNo: "+crmCardNo);

        log.info("getCardMasterInfo - Requested -"+generalUtils.getLogTextForRequest() );

        //get card master info
        CardMaster cardMaster = cardMasterService.findByCrmMerchantNoAndCrmCardNo(merchantNo, crmCardNo);

        if(cardMaster != null){

            // Convert the CardMaster to CardMasterResource
            CardMasterResource cardMasterResource = cardMasterAssembler.toResource(cardMaster);

            // Set the retData to success
            retData.setStatus(APIResponseStatus.success);

            // Set the data to the cardMasterResource object
            retData.setData(cardMasterResource);

            // Log the response
            log.info("getCardMasterByCardNo - Response : " + generalUtils.getLogTextForResponse(retData));


        }


        // Return the retdata object
        return retData;


    }

    /**
     * @purpose:get compatible API regarding for card balance based on card number
     * @param crmCardNo
     * @return
     * @throws InspireNetzException
     */

    @RequestMapping(value = "/api/0.9/json/transaction/cardbalance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCompatibleCardBalance(@RequestParam(value="cardnumber") String crmCardNo) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getCompatibleCardBalance - Request Received# CardMaster CardNo: "+crmCardNo);

        log.info("getCompatibleCardBalance - Requested -"+generalUtils.getLogTextForRequest() );


        //get card master info
        CardMaster cardMaster = cardMasterService.findByCrmMerchantNoAndCrmCardNo(merchantNo, crmCardNo);

        if(cardMaster != null){

            // Convert the CardMaster to CardMasterResource
            CardMasterCompatibleResource cardMasterResource = cardMasterCompatibleAssembler.toResource(cardMaster);

            // Set the retData to success
            retData.setStatus(APIResponseStatus.success);

            // Set the data to the cardMasterResource object
            retData.setBalance(cardMasterResource);

            // Log the response
            log.info("getCardCompatible - Response : " + retData.toString());

        }else{

            //set failed status
            retData.setStatus(APIResponseStatus.failed);

            //set zerot
            retData.setBalance(0);

            //set error code
            retData.setErrorCode(APIErrorCode.ERR_NOT_FOUND);

            log.info("getCardCompatible - Response : " + generalUtils.getLogTextForResponse(retData));

        }


        // Return the retdata object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/transaction/cardtopup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject makeChargeCardTopupCompatible(@RequestParam(value="cardnumber") String cardNo,@RequestParam(value="purchase_amount",defaultValue = "0.0") String amount,

                                                        @RequestParam(value="reference",defaultValue = "0") String reference,@RequestParam(value="payment_mode",defaultValue = "1") Integer payment_mode,@RequestParam(value="location",defaultValue = "") String location,@RequestParam(value ="awardIncentive",defaultValue = "true") boolean awardIncentive,@RequestParam(value = "incentiveAmount", defaultValue = "0.0") Double incentiveAmount
                                    ) throws InspireNetzException{


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("makeChargeCardTopupCompatible - Request Received# Card Number: "+cardNo);


        log.info("makeChargeCardTopupCompatible - Requested -"+generalUtils.getLogTextForRequest() );

        CardMasterOperationResponse cardMasterOperationResponse=null;

        try{

            // Call the topup method from CardService
            cardMasterOperationResponse = cardMasterService.topupCard(cardNo,Double.parseDouble(amount),merchantNo,reference,payment_mode,location,awardIncentive,false,incentiveAmount);

            // Check the response
            if ( cardMasterOperationResponse != null ) {

                CardMasterOperationCompatibleResource cardMasterOperationCompatibleResource =cardMasterOperationCompatibleAssembler.toResource(cardMasterOperationResponse);

                // Set the retData to success
                retData.setStatus(APIResponseStatus.success);

                // Set the data
                retData.setBalance(cardMasterOperationCompatibleResource);

                //set the exact data
                retData.setData(cardMasterOperationResponse);

            } else {

                // Set the retData to failed
                retData.setStatus(APIResponseStatus.failed);

                // Set the errorcode to operation failed
                retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

                cardMasterOperationResponse.setBalance(0.00);

                cardMasterOperationResponse.setTxnref("");

                CardMasterOperationCompatibleResource cardMasterOperationCompatibleResource =cardMasterOperationCompatibleAssembler.toResource(cardMasterOperationResponse);

                retData.setBalance(cardMasterOperationCompatibleResource);


            }


        }catch (InspireNetzException ex){

            // Set the retData to failed
            cardMasterOperationResponse =new CardMasterOperationResponse();

            cardMasterOperationResponse.setBalance(0.00);

            cardMasterOperationResponse.setTxnref("");

            CardMasterOperationCompatibleResource cardMasterOperationCompatibleResource =cardMasterOperationCompatibleAssembler.toResource(cardMasterOperationResponse);

            retData.setStatus(APIResponseStatus.failed);

            retData.setBalance(cardMasterOperationCompatibleResource);

            // Set the error code to operation failed
            retData.setErrorCode(ex.getErrorCode());

            log.info("makeChargeCardTopupCompatible - Response : " + generalUtils.getLogTextForResponse(retData));

            return retData;

        }

        // Log the response
        log.info("makeChargeCardTopupCompatible - Response : " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/transaction/chargecard", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject makeChargeCardPaymentCompatible(@RequestParam(value="cardnumber") String cardNo,@RequestParam(value="purchase_amount",defaultValue = "0.0") Double amount,@RequestParam(value = "pin",defaultValue = "") String pin,

                                                             @RequestParam(value="txnref",defaultValue = "") String reference,@RequestParam(value="payment_mode",defaultValue = "1") Integer payment_mode,@RequestParam(value = "otp_code",defaultValue = "") String otpCode,@RequestParam(value="location",defaultValue = "") String location) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("makeChargeCardPaymentCompatible - Request Received# Card Number: "+cardNo);
        log.info("makeChargeCardPaymentCompatible -"+generalUtils.getLogTextForRequest());


        try{

            // Call the topup method from CardService
            CardMasterOperationResponse cardMasterOperationResponse = cardMasterService.debitCardCompatible(cardNo, reference, pin, amount, merchantNo, otpCode,payment_mode,location);

            // Check the response
            if ( cardMasterOperationResponse != null ) {


                String cardReference =cardMasterOperationResponse.getTxnref() ==null?"":cardMasterOperationResponse.getTxnref();

                //check card response operation
                if(cardReference.equals("ERR_CARD_BALANCE_EXPIRED")){

                    // Set the retData to failed
                    retData.setStatus(APIResponseStatus.failed);

                    retData.setErrorCode(APIErrorCode.ERR_CARD_BALANCE_EXPIRED);

                    cardMasterOperationResponse =new CardMasterOperationResponse();

                    cardMasterOperationResponse.setBalance(0.00);

                    cardMasterOperationResponse.setTxnref("");

                    CardMasterOperationCompatibleResource cardMasterOperationCompatibleResource =cardMasterOperationCompatibleAssembler.toResource(cardMasterOperationResponse);

                    retData.setBalance(cardMasterOperationCompatibleResource);

                    //set the exact data
                    retData.setData(cardMasterOperationResponse);

                }else {

                    CardMasterOperationCompatibleResource cardMasterOperationCompatibleResource =cardMasterOperationCompatibleAssembler.toResource(cardMasterOperationResponse);

                    // Set the retData to success
                    retData.setStatus(APIResponseStatus.success);

                    // Set the data
                    retData.setBalance(cardMasterOperationCompatibleResource);

                    //set the exact data
                    retData.setData(cardMasterOperationResponse);

                }

            } else {

                // Set the retData to failed
                retData.setStatus(APIResponseStatus.failed);

                // Set the errorcode to operation failed
                retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

                cardMasterOperationResponse =new CardMasterOperationResponse();

                cardMasterOperationResponse.setBalance(0.00);

                cardMasterOperationResponse.setTxnref("");

                CardMasterOperationCompatibleResource cardMasterOperationCompatibleResource =cardMasterOperationCompatibleAssembler.toResource(cardMasterOperationResponse);

                retData.setBalance(cardMasterOperationCompatibleResource);

            }


        }catch (InspireNetzException ex){

            //set error code
            if(ex.getErrorCode()==APIErrorCode.ERR_INSUFFICIENT_BALANCE){

                retData.setErrorCode(APIErrorCode.ERR_NO_BALANCE);

            }else{

                retData.setErrorCode(ex.getErrorCode());
            }

            CardMasterOperationResponse cardMasterOperationResponse =new CardMasterOperationResponse();


            //set balance field to zero
            cardMasterOperationResponse.setBalance(0.00);

            cardMasterOperationResponse.setTxnref("");

            CardMasterOperationCompatibleResource cardMasterOperationCompatibleResource =cardMasterOperationCompatibleAssembler.toResource(cardMasterOperationResponse);

            retData.setBalance(cardMasterOperationCompatibleResource);


            retData.setStatus(APIResponseStatus.failed);
        }

        // Log the response
        log.info("makeChargeCardPaymentCompatible - Response : " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }


    @RequestMapping(value="/api/0.9/json/public/card/issue",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject issueCardFromPublic(@RequestParam(value = "cardNumber") String cardNumber,
                                                 @RequestParam(value = "pin") String pin,
                                                 @RequestParam(value = "mobile") String mobile,
                                                 @RequestParam(value = "cardHolderName") String cardHolderName,
                                                 @RequestParam(value = "email",defaultValue = "") String email,
                                                 @RequestParam(value = "dob",defaultValue = "") String dob,
                                                 @RequestParam(value = "merchantNo",defaultValue = "0") Long merchantNo,
                                                 @RequestParam(value = "otpCode") String otpCode) throws InspireNetzException{

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveCardMaster - Request Received #cardNumber = "+cardNumber+" #pin = "+pin+" #mobile = "+mobile+" #cardHolderName = "+cardHolderName+" #email = "+email+" #dob = "+dob+" #merchantNo = "+merchantNo+" #otpCode = "+otpCode);


        //card master object
        CardMasterPublicResponse cardMasterPublicResponse =new CardMasterPublicResponse();

        cardMasterPublicResponse = cardMasterService.issueCardFromPublic(cardNumber,pin,mobile,cardHolderName,email,dob,merchantNo,otpCode,0);



        // If the cardMaster object is not null ,then return the success object
        if ( cardMasterPublicResponse != null ) {

            // Set the cardMasterOperationResponse
            retData.setData(cardMasterPublicResponse);

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);


        } else {

            // Log the response
            log.info("saveCardMaster - Response : Unable to save the cardMaster information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Log the response
        log.info("saveCardMaster - Response : " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;
    }

    @RequestMapping(value="/api/0.9/json/public/card/getbalanceotp",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCardBalanceOTP(@RequestParam(value = "cardNumber") String cardNumber,
                                                 @RequestParam(value = "merchantNo",defaultValue = "0") Long merchantNo) throws InspireNetzException{

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getCardBalancePublic - Request Received #cardNumber = "+cardNumber+" #merchantNo = "+merchantNo);


        boolean isOtpGenerated = cardMasterService.getCardBalanceOTP(cardNumber,merchantNo);



        // If the cardMaster object is not null ,then return the success object
        if ( isOtpGenerated) {


            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);


        } else {

            // Set the status to failed
            retData.setStatus(APIResponseStatus.failed);

        }



        // Log the response
        log.info("getCardBalancePublic - Response : " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;
    }

    @RequestMapping(value="/api/0.9/json/public/card/getbalance",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCardBalancePublic(@RequestParam(value = "cardNumber") String cardNumber,
                                               @RequestParam(value = "merchantNo",defaultValue = "0") Long merchantNo,
                                               @RequestParam(value = "otpCode") String otpCode) throws InspireNetzException{

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getCardBalancePublic - Request Received #cardNumber = "+cardNumber+" #merchantNo = "+merchantNo);

        CardMasterPublicResponse cardMasterPublicResponse=new CardMasterPublicResponse();

        cardMasterPublicResponse = cardMasterService.getCardBalancePublic(cardNumber, merchantNo, otpCode);



        // If the cardMaster object is not null ,then return the success object
        if ( cardMasterPublicResponse != null ) {

            // Set the cardMasterOperationResponse
            retData.setData(cardMasterPublicResponse);

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);


        } else {

            // Set the cardMasterOperationResponse
            retData.setData(null);

            // Set the status to succes
            retData.setStatus(APIResponseStatus.failed);

        }



        // Log the response
        log.info("getCardBalancePublic - Response : " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;
    }


    @RequestMapping(value="/api/0.9/json/merchant/cardtransfer/genertaotp",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCardTransferOtp(@RequestParam(value = "crmCardNumber") String cardNumber
                                                ) throws InspireNetzException{

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //get merchant number
        Long merchantNo =authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getCardTransferOtp - Request Received #cardnumber = "+cardNumber+" #merchantNo = "+merchantNo);

        boolean transferAmountOtp =cardMasterService.generateTransferAmountOtp(cardNumber,merchantNo);

        if(transferAmountOtp){

            retData.setStatus(APIResponseStatus.success);

        }else {
            retData.setStatus(APIResponseStatus.failed);
        }

        // Log the response
        log.info("getCardTransferOtp - Response : " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;
    }

    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/multiplecard/issue", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject multipleCardIssue(@RequestBody Map<String,Object> params) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        //get card master object
        CardMaster cardMaster  =mapper.map(params,CardMaster.class);

        //set merchant number
        cardMaster.setCrmMerchantNo(merchantNo);

        // Log the Request
        log.info("Multiple Card - Request Received# "+cardMaster.toString());

        log.info("Multiple Card - Requested -"+generalUtils.getLogTextForRequest() );

        //card master object
        CardMasterOperationResponse cardMasterOperationResponse =new CardMasterOperationResponse();

        // save the cardMaster object and get the result
        cardMasterService.issueMultipleCard(cardMaster,authSessionUtils.getUserNo(),authSessionUtils.getUserLoginId(),false,0);

        retData.setStatus(APIResponseStatus.success);
        //retData.setData(cardMasterOperationResponse);

        // Log the response
        log.info("saveCardMaster - Response : " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/updatetier", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updateTier(@RequestParam(value = "crmCardNumber") String crmCardNumber,
                                        @RequestParam(value = "tierId") Long tierId,
                                        @RequestParam(value = "updateCardExpiry",defaultValue = "0") Boolean updateCardExpiry,
                                        @RequestParam(value = "cardExpiryDate",required = false)Date cardExpiryDate) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        log.info("updateTier ->Received Request crmCardNumber #"+crmCardNumber +"tierId  #"+tierId);


        //update card master information
        CardMaster cardMaster = cardMasterService.updateTier(crmCardNumber,tierId,merchantNo,updateCardExpiry,cardExpiryDate);

        // Convert the cardMaster into  to cardmaster resource
        CardMasterResource  cardMasterResource = cardMasterAssembler.toResource(cardMaster);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the roleResource object
        retData.setData(cardMasterResource);

        // Log the response
        log.info("Update Tier -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

    @RequestMapping(value="/api/0.9/json/merchant/calculate/refundable/balance/{cardNo}/{amount}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRefundableBalance(@PathVariable(value = "cardNo") String crmCardNumber,
                                                  @PathVariable(value = "amount") Double amount) throws InspireNetzException{


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //Calll the service method to get the refundable balance
        Map<String,Double> refundableBalance= cardMasterService.getRefundableBalance(amount,crmCardNumber);

        //Check if the balance is null
        if(refundableBalance != null){

            //set the data
            retData.setData(refundableBalance);

            //set status as success
            retData.setStatus(APIResponseStatus.success);
        }

        return retData;
    }

}

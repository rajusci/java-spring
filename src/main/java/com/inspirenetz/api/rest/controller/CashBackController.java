package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.CashBackRequest;
import com.inspirenetz.api.core.dictionary.CashBackResponse;
import com.inspirenetz.api.core.dictionary.RequestChannel;
import com.inspirenetz.api.core.service.CashBackService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by saneeshci on 28/9/15.
 *
 */
@Controller
public class CashBackController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CashBackController.class);

    @Autowired
    private CashBackService cashBackService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/cashback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject doCashBack(@RequestParam(value ="loyaltyId") String loyaltyId,
                                        @RequestParam(value ="merchantIdentifier") String merchantIdentifier,
                                        @RequestParam(value ="amount") String amount,
                                        @RequestParam(value ="pin") String pin,
                                        @RequestParam(value ="channel",required = false,defaultValue = RequestChannel.RDM_CHANNEL_SMS+"") Integer channel,
                                        @RequestParam(value ="ref") String ref) throws InspireNetzException {

        // Log the Request
        log.info("doCashBack - Request Received# Cashback request :Loyalty Id : " +loyaltyId+" MerchantIdentifier: "+merchantIdentifier+" Amount :"+ amount+" Ref :"+ref );
        log.info("doCashBack - "+generalUtils.getLogTextForRequest());

        CashBackRequest cashBackRequest = getCashBackRequestObjectFromMap(loyaltyId,merchantIdentifier,ref,amount,pin,channel);

        cashBackRequest.setMerchantNo(authSessionUtils.getMerchantNo());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the CashBack
        CashBackResponse cashBackResponse = cashBackService.doCashBack(cashBackRequest);

        // Set the data
        retData.setData(cashBackResponse);

        // Log the response
        log.info("doCashBack -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/cashback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject doCashBackForCustomer(@RequestParam(value ="merchantNo") Long merchantNo,
                                        @RequestParam(value ="merchantIdentifier") String merchantIdentifier,
                                        @RequestParam(value ="amount") String amount,
                                        @RequestParam(value ="pin") String pin,
                                        @RequestParam(value ="channel",required = false,defaultValue = RequestChannel.RDM_CHANNEL_SMS+"") Integer channel,
                                        @RequestParam(value ="ref") String ref,
                                        @RequestParam(value="otpCode") String otpCode) throws InspireNetzException {

        String loyaltyId = authSessionUtils.getUserLoginId();

        // Log the Request
        log.info("doCashBackForCustomer - Request Received# Cashback request :LoyaltyId: " + loyaltyId + " MerchantIdentifier: " + merchantIdentifier + " Amount :" + amount + " Ref :" + ref);
        log.info("doCashBackForCustomer - "+generalUtils.getLogTextForRequest());

        CashBackRequest cashBackRequest = getCashBackRequestObjectFromMap(loyaltyId,merchantIdentifier,ref,amount,pin,channel);

        cashBackRequest.setMerchantNo(merchantNo);

        cashBackRequest.setOtpCode(otpCode);

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the CashBack
        CashBackResponse cashBackResponse = cashBackService.doCashBack(cashBackRequest);

        // Set the data
        retData.setData(cashBackResponse);

        // Log the response
        log.info("doCashBackForCustomer -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    private CashBackRequest getCashBackRequestObjectFromMap(String loyaltyId, String merchantIdentifier, String ref, String amount, String pin,Integer channel) {

        CashBackRequest cashBackRequest = new CashBackRequest();

        cashBackRequest.setPin(pin);

        cashBackRequest.setRef(ref);

        cashBackRequest.setAmount(Double.parseDouble(amount));

        cashBackRequest.setMerchantIdentifier(merchantIdentifier);

        cashBackRequest.setChannel(1);

        cashBackRequest.setLoyaltyId(loyaltyId);

        cashBackRequest.setChannel(channel);

        return cashBackRequest;
    }

    @RequestMapping(value = "/api/0.9/json/merchant/partner/cashback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject doCashBackFromPartner(@RequestParam(value ="mobile") String mobile,
                                        @RequestParam(value ="merchantNo") Long merchantNo,
                                        @RequestParam(value ="amount") String amount,
                                        @RequestParam(value ="channel",required = false,defaultValue = RequestChannel.RDM_CHANNEL_SMS+"") Integer channel,
                                        @RequestParam(value ="reference") String reference,
                                        @RequestParam(value = "otpCode") String otpCode) throws InspireNetzException {

        //Get the user id of the merchant
        String userLoginId = authSessionUtils.getUserLoginId();

        // Log the Request
        log.info("doCashBackFromPartner - Request Received# Cashback request :Mobile : " + mobile + " Partner UserID: " + userLoginId + " Amount :" + amount + " Reference :" + reference + "otpCode : " + otpCode);
        log.info("doCashBackFromPartner - " + generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        CashBackResponse cashBackResponse = cashBackService.doCashbackFromPartner(mobile,userLoginId,merchantNo,amount,channel,reference,otpCode);

        // Set the data
        retData.setData(cashBackResponse);

        // Log the response
        log.info("doCashBackFromPartner -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/trusted/cashback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject doCashbackFromTrusted(@RequestParam(value ="merchantNo") Long merchantNo,
                                                   @RequestParam(value ="mobile") String mobile,
                                                   @RequestParam(value ="merchantIdentifier") String merchantIdentifier,
                                                   @RequestParam(value ="amount") String amount,
                                                   @RequestParam(value ="pin") String pin,
                                                   @RequestParam(value ="channel",required = false,defaultValue = RequestChannel.RDM_CHANNEL_SMS+"") Integer channel,
                                                   @RequestParam(value ="ref") String ref,
                                                   @RequestParam(value="otpCode") String otpCode) throws InspireNetzException {



        // Log the Request
        log.info("doCashBackForCustomer - Request Received# Cashback request :MObile: " + mobile + " MerchantIdentifier: " + merchantIdentifier + " Amount :" + amount + " Ref :" + ref);
        log.info("doCashBackForCustomer - "+generalUtils.getLogTextForRequest());

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

        // Check the cashbackRequest object
        CashBackRequest cashBackRequest = getCashBackRequestObjectFromMap(mobile,merchantIdentifier,ref,amount,pin,channel);

        // set the merchant number
        cashBackRequest.setMerchantNo(merchantNo);

        // Set the otpCode
        cashBackRequest.setOtpCode(otpCode);

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the CashBack
        CashBackResponse cashBackResponse = cashBackService.doCashBack(cashBackRequest);

        // Set the data
        retData.setData(cashBackResponse);

        // Log the response
        log.info("doCashBackForCustomer -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


}
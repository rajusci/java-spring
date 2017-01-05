package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.service.OneTimePasswordService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fayizkci on 16/2/14.
 */
@Controller
public class OneTimePasswordController {

    @Autowired
    private OneTimePasswordService oneTimePasswordService;



    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;


    // Create the logger
    private static Logger log = LoggerFactory.getLogger(OneTimePasswordController.class);

    @RequestMapping(value = "/api/0.9/json/merchant/otp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject generateOTPCompatible(
                                                @RequestParam(value = "card_number",required = true) String loyaltyId,
                                                @RequestParam(value = "otp_type",required = false,defaultValue = "0") Integer otpType
                                             ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        long merchantNo = authSessionUtils.getMerchantNo();


        // Log the Request
        log.info("generateOTPCompatible - Request Received# card_number = " +loyaltyId + " : otp_type = " +otpType );

        log.info("generateOTPCompatible -  "+generalUtils.getLogTextForRequest());

        //Generate otpCode
       boolean isGenerated=oneTimePasswordService.generateOTPCompatible(merchantNo, loyaltyId, otpType);

        if(isGenerated){

            retData.setStatus(APIResponseStatus.success);

        }else {

            retData.setStatus(APIResponseStatus.failed);

        }

        // Log the Request
        log.info("generateOTPCompatible -  "+generalUtils.getLogTextForResponse(retData));

        //return response
        return retData;

    }

    @RequestMapping(value = "/api/0.9/json/merchant/pay/otp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject generateOtpForPartnerRequest(
                                            @RequestParam(value = "mobile") String mobile,
                                            @RequestParam(value = "merchantNo")Long merchantNo,
                                            @RequestParam(value = "otpType")Integer otpType
                                        ) throws InspireNetzException{

        //create APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        //log the request
        log.info("Generate OTP ---> Request received for Mobile = "+ mobile +  "Merchant No = " + merchantNo + "OTP Type =" +otpType );
        log.info("Generate OTP --->" + generalUtils.getLogTextForRequest());

        // Get the userType
        Integer userType = authSessionUtils.getUserType();

        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( userType != UserType.REDEMPTION_MERCHANT_USER ) {

            // Log the response
            log.info("generateOtpForPartnerRequest - Response : You are not authorized");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        boolean isOtpGenerated = oneTimePasswordService.generateOTPForPartnerRequest(merchantNo,mobile,otpType);

        if (isOtpGenerated){

            retData.setStatus(APIResponseStatus.success);

        }else{

            retData.setStatus(APIResponseStatus.failed);

        }

        //log the response
        log.info("Generate OTP Compatible --> " + generalUtils.getLogTextForResponse(retData));

        //return the response
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/trusted/pay/otp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject generateOtpForTrustedPartner(
            @RequestParam(value = "mobile") String mobile,
            @RequestParam(value = "merchantNo")Long merchantNo,
            @RequestParam(value = "otpType")Integer otpType
    ) throws InspireNetzException{

        //create APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //log the request
        log.info("generateOtpForTrustedPartner ---> Request received for Mobile = "+ mobile +  "Merchant No = " + merchantNo + "OTP Type =" +otpType );
        log.info("generateOtpForTrustedPartner --->" + generalUtils.getLogTextForRequest());

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

        // Check if the otp is generated
        boolean isOtpGenerated = oneTimePasswordService.generateOTPForPartnerRequest(merchantNo,mobile,otpType);

        // If the otp is generated, set the status as success
        if (isOtpGenerated){

            retData.setStatus(APIResponseStatus.success);

        } else{

            retData.setStatus(APIResponseStatus.failed);

        }

        //log the response
        log.info("generateOtpForTrustedPartner --> " + generalUtils.getLogTextForResponse(retData));

        //return the response
        return retData;

    }

}

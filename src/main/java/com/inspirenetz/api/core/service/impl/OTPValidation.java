package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.OneTimePassword;
import com.inspirenetz.api.core.service.OneTimePasswordService;
import com.inspirenetz.api.core.service.ValidationService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by abhilasha on 4/4/16.
 */
@Service
public class OTPValidation implements ValidationService {

    // Create the logger
    private static Logger log = LoggerFactory.getLogger(OTPValidation.class);


    private OneTimePasswordService oneTimePasswordService;

    public OTPValidation(){}



    public OTPValidation(OneTimePasswordService oneTimePasswordService) {

        this.oneTimePasswordService = oneTimePasswordService;
    }


    @Override
    public ValidationResponse isValid(ValidationRequest validationRequest) throws InspireNetzException{

        //create a validationResponse object
        ValidationResponse validationResponse = new ValidationResponse();

        //get the OTP merchant no
        Long otpMerchantNo = validationRequest.getMerchantNo();

        //get the Otp reference type
        Integer otpReferenceType = OTPRefType.CUSTOMER;

        //get the otpReference
        String otpReference = validationRequest.getCustomer().getCusCustomerNo().toString();

        //get the otp Type
        Integer otpType = OTPType.PAY_POINTS;

        //get the otpCode
        String otpCode = validationRequest.getOtpCode();


        //call the method for checking the validity of OTP
        Integer otpStatus= oneTimePasswordService.validateOTPGeneric(otpMerchantNo,otpReferenceType,otpReference,otpType,otpCode);

        //check the otp is validated or not
        if (otpStatus == OTPStatus.VALIDATED){

            validationResponse.setValid(true);

        }
        else{
            validationResponse.setValid(false);
            validationResponse.setValidationRemarks("Pay with points failed, invalid OTP");
            validationResponse.setSpielName(MessageSpielValue.PAY_POINTS_OTP_INVALID);
            validationResponse.setApiErrorCode(APIErrorCode.ERR_OTP_VALIDATION_FAILED);
        }

        //return the response
        return validationResponse;
    }
}

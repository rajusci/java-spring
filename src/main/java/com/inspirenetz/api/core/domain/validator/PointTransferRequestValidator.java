package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.PointTransferRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 25/9/14.
 */
public class PointTransferRequestValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return PointTransferRequest.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the PointTransferRequestObject
        PointTransferRequest pointTransferRequest = (PointTransferRequest)target;

        // Check the pointTransferRequest name
        if (  pointTransferRequest.getPtrSource() == null  ) {

            errors.rejectValue("ptrSource","{pointTransferRequest.ptrSource.notnull}");

        }

        // Check the pointTransferRequest redemption merchant
        if (  pointTransferRequest.getPtrDestination() == null  ) {

            errors.rejectValue("ptrDestination","{pointTransferRequest.ptrDestination.notnull}");

        }
        // Check the pointTransferRequest redemption product code
        if (  pointTransferRequest.getPtrApprover() == null  ) {

            errors.rejectValue("ptrApprover","{pointTransferRequest.ptrApprover.notnull}");

        }

        //check redemption voucher code
        if(pointTransferRequest.getPtrRewardQty() == null){

            errors.rejectValue("ptrRewardQty","{pointTransferRequest.ptrRewardQty.notnull}");


        }
        //check redemption voucher code
        if(pointTransferRequest.getPtrDestCurrency() == null){

            errors.rejectValue("ptrDestCurrency","{pointTransferRequest.ptrDestCurrency.notnull}");

        }

        //check redemption voucher code
        if(pointTransferRequest.getPtrSourceCurrency() == null){

            errors.rejectValue("ptrSourceCurrency","{pointTransferRequest.ptrSourceCurrency.notnull}");

        }



    }
}

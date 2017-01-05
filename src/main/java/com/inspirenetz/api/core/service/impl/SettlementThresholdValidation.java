package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.MerchantRedemptionPartner;
import com.inspirenetz.api.core.domain.MerchantSettlement;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by saneeshci on 05/08/16.
 */
@Service
public class SettlementThresholdValidation implements ValidationService {


    // Create the logger
    private static Logger log = LoggerFactory.getLogger(SettlementThresholdValidation.class);

    MerchantSettlementService merchantSettlementService;

    MerchantRedemptionPartnerService merchantRedemptionPartnerService;

    // Constructor
    public SettlementThresholdValidation(MerchantSettlementService merchantSettlementService,MerchantRedemptionPartnerService merchantRedemptionPartnerService) {

        this.merchantSettlementService = merchantSettlementService;
        this.merchantRedemptionPartnerService = merchantRedemptionPartnerService;
    }

    public SettlementThresholdValidation(){

    }

    @Override
    public ValidationResponse isValid(ValidationRequest validationRequest) {

        ValidationResponse validationResponse = new ValidationResponse();


        //initialize variable to store the unsettled amount
        Double totalUnsettled = 0.0;

        //get merchant redemption partner object
        MerchantRedemptionPartner merchantRedemptionPartner = merchantRedemptionPartnerService.findByMrpMerchantNoAndMrpRedemptionMerchant(validationRequest.getMerchantNo(),validationRequest.getRedemptionMerchantNo());


        if(merchantRedemptionPartner.getMrpThresholdUnsettled() != null && merchantRedemptionPartner.getMrpThresholdUnsettled() > 0){

            //get the list of all unsettled entries
            List<MerchantSettlement> unsettledEntries = merchantSettlementService.findByMesMerchantNoAndMesVendorNoAndMesIsSettled(validationRequest.getMerchantNo(),validationRequest.getRedemptionMerchantNo(), MerchantSettlementStatus.NOT_SETTLED);

            //get the maximum allowed unsettled amount
            Double thresholdUnsettled = merchantRedemptionPartner.getMrpThresholdUnsettled();

            //get the total unsettled amount
            for(MerchantSettlement merchantSettlement  : unsettledEntries){

                //add the settlement amount to total
                totalUnsettled += merchantSettlement.getMesAmount();

            }


            //check if threshold is reached or not
            if(totalUnsettled >= thresholdUnsettled){

                //log the error
                log.error("SettlementThreshold validation : Cashback cannot be processed , settlement pending");

                //set spiel name
                validationResponse.setSpielName(MessageSpielValue.CASHBACK_FAILED_CONTACT_MERCHANT);

                //set request invalid
                validationResponse.setValid(false);

                //set error code
                validationResponse.setApiErrorCode(APIErrorCode.ERR_CASHBACK_FAILED_PENDING_SETTLEMENT);

                //set remarks
                validationResponse.setValidationRemarks("Cashback failed , unable to perform operation");

                //return response
                return validationResponse;
            }

        }

        validationResponse.setValid(true);

        //return validtion response
        return validationResponse;


    }
}

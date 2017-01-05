package com.inspirenetz.api.core.loyaltyengine;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.domain.Redemption;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.domain.RedemptionVoucher;
import com.inspirenetz.api.core.service.CatalogueService;
import com.inspirenetz.api.core.service.MerchantSettingService;
import com.inspirenetz.api.core.service.RedemptionService;
import com.inspirenetz.api.core.service.UserMessagingService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.exception.InspireNetzRollBackException;
import com.inspirenetz.api.util.GeneralUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;


/**
 * Created by abhi on 7/7/16.
 */

@Component
public class CatalogueRedemptionProduct implements CatalogueRedemption {

    private RedemptionService redemptionService;

    private UserMessagingService userMessagingService;

    private GeneralUtils generalUtils;

    private CatalogueService catalogueService;

    private MerchantSettingService merchantSettingService;

    private Logger log = LoggerFactory.getLogger(CatalogueRedemptionProduct.class);

    public CatalogueRedemptionProduct(){

    }

    public CatalogueRedemptionProduct(RedemptionService redemptionService,UserMessagingService userMessagingService,CatalogueService catalogueService,
                                      GeneralUtils generalUtils){

        this.redemptionService = redemptionService;

        this.userMessagingService = userMessagingService;

        this.catalogueService =catalogueService;

        this.generalUtils = generalUtils;


    }


    @Override
    public boolean isRequestValid(CatalogueRedemptionItemRequest catalogueRedemptionItemRequest) throws InspireNetzException {

        return true;
    }

    @Override
    public CatalogueRedemptionItemResponse redeemPoints(CatalogueRedemptionItemRequest catalogueRedemptionItemRequest) throws InspireNetzException, InspireNetzRollBackException {

        CatalogueRedemptionItemResponse catalogueRedemptionItemResponse = redemptionService.redeemSingleCatalogueItem(catalogueRedemptionItemRequest);

        //check redemption is success or not
        if (catalogueRedemptionItemResponse.getStatus().equals("success")) {

            //Get the tracking id from the response
            String trackingId = catalogueRedemptionItemResponse.getTracking_id();


            //find catalogue
            Catalogue catalogue = catalogueService.findByCatProductNo(catalogueRedemptionItemRequest.getCatProductNo());

            Redemption redemption = redemptionService.findByRdmId(catalogueRedemptionItemResponse.getRdmId());

            //set redemption status to processed successfully
            redemption.setRdmStatus(RedemptionStatus.RDM_STATUS_PROCESSED_SUCCESSFULLY);

            //save the redemption object
            redemptionService.saveRedemption(redemption);

            //send the success message to the customer
            //create a map for the sms placeholders
            HashMap<String, String> smsParams = new HashMap<>(0);

            // Set the cataloguename
            smsParams.put("#catalogueName", catalogueRedemptionItemRequest.getCatDescription());

            // Set the dti number
            smsParams.put("#dti", catalogueRedemptionItemRequest.getCatDtiNumber());

            // Set the points formatted
            smsParams.put("#points", generalUtils.getFormattedValue(catalogueRedemptionItemRequest.getCatNumPoints()));

            // Set the reference as the tracking id
            smsParams.put("#reference", catalogueRedemptionItemResponse.getTracking_id());

            //add min to the map
            smsParams.put("#min", catalogueRedemptionItemRequest.getCreditLoyaltyId());

            //if pasa rewards send success message to initiator
            if (catalogueRedemptionItemRequest.isPasaRewards()) {

                //create a map for the sms placeholders
                HashMap<String, String> pasaRewardParams = new HashMap<>(0);

                // Set the cataloguename
                pasaRewardParams.put("#catalogueName", catalogueRedemptionItemRequest.getCatDescription());

                // Set the dti number
                pasaRewardParams.put("#dti", catalogueRedemptionItemRequest.getCatDtiNumber());

                // Set the points formatted
                pasaRewardParams.put("#points", generalUtils.getFormattedValue(catalogueRedemptionItemRequest.getCatNumPoints()));

                // Set the reference as the tracking id
                pasaRewardParams.put("#reference", catalogueRedemptionItemResponse.getTracking_id());

                //add min to the map
                pasaRewardParams.put("#min", catalogueRedemptionItemRequest.getCreditLoyaltyId());

                MessageWrapper messageWrapper  = generalUtils.getMessageWrapperObject(MessageSpielValue.PASA_REWARD_SUCCESS_TO_INITIATOR,catalogueRedemptionItemRequest.getLoyaltyId(),"","","",0L,pasaRewardParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

                messageWrapper.setMerchantNo(redemption.getRdmMerchantNo());

                userMessagingService.transmitNotification(messageWrapper);

                //replace receipt min by sender
                smsParams.put("#min", catalogueRedemptionItemRequest.getLoyaltyId());

                MessageWrapper messageWrapper2  = generalUtils.getMessageWrapperObject(MessageSpielValue.PASA_REWARD_SUCCESS_VOUCHER,catalogueRedemptionItemRequest.getCreditLoyaltyId(),"","","",0L,smsParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

                messageWrapper2.setMerchantNo(redemption.getRdmMerchantNo());

                userMessagingService.transmitNotification(messageWrapper2);

            } else {

                MessageWrapper messageWrapper3  = generalUtils.getMessageWrapperObject(catalogue.getCatMessageSpiel(),catalogueRedemptionItemRequest.getCreditLoyaltyId(),"","","",0L,smsParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

                messageWrapper3.setMerchantNo(redemption.getRdmMerchantNo());

                userMessagingService.transmitNotification(messageWrapper3);

            }

            //set the status to success
            catalogueRedemptionItemResponse.setRdmStatus(CatalogueRedemptionStatus.CAT_RDM_STATUS_SUCCESS);

        } else {

            //if redemption is failed set
            catalogueRedemptionItemResponse.setRdmStatus(CatalogueRedemptionStatus.CAT_RDM_REDEEM_FAILED);

        }
        //return the object
        return catalogueRedemptionItemResponse;



    }
}

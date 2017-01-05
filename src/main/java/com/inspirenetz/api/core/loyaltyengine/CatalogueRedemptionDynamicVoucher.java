package com.inspirenetz.api.core.loyaltyengine;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.domain.Redemption;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.domain.RedemptionVoucher;
import com.inspirenetz.api.core.service.*;
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
 * Created by saneesh-ci on 12/02/2014.
 */
@Component
public class CatalogueRedemptionDynamicVoucher implements CatalogueRedemption {

    private RedemptionService redemptionService;

    private RedemptionMerchantService redemptionMerchantService;

    private RedemptionVoucherService redemptionVoucherService;

    private UserMessagingService userMessagingService;

    private CatalogueService catalogueService;

    private GeneralUtils generalUtils;

    private RedemptionVoucherSourceService redemptionVoucherSourceService;

    private Logger log = LoggerFactory.getLogger(CatalogueRedemptionDynamicVoucher.class);

    public CatalogueRedemptionDynamicVoucher(){

    }
    public CatalogueRedemptionDynamicVoucher(RedemptionService redemptionService, RedemptionMerchantService redemptionMerchantService,
                                             RedemptionVoucherService redemptionVoucherService, UserMessagingService userMessagingService, CatalogueService catalogueService,
                                             GeneralUtils generalUtils,RedemptionVoucherSourceService redemptionVoucherSourceService){

        this.redemptionService = redemptionService;

        this.redemptionMerchantService = redemptionMerchantService;

        this.redemptionVoucherService = redemptionVoucherService;

        this.userMessagingService = userMessagingService;

        this.catalogueService =catalogueService;

        this.generalUtils = generalUtils;

        this.redemptionVoucherSourceService = redemptionVoucherSourceService;

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

            RedemptionMerchant redemptionMerchant = redemptionMerchantService.findByRemNo(catalogueRedemptionItemRequest.getCatRedemptionMerchant());


            //find catalogue
            Catalogue catalogue = catalogueService.findByCatProductNo(catalogueRedemptionItemRequest.getCatProductNo());

            //Generate the Voucher Code
            String voucherCode = redemptionVoucherSourceService.getVoucherCode(catalogue.getCatVoucherSource());

            //create the Redemption Voucher Object
            RedemptionVoucher redemptionVoucher = new RedemptionVoucher();

            //set values to the redemption voucher
            redemptionVoucher.setRvrCustomerNo(catalogueRedemptionItemRequest.getCreditCustomerNo());
            redemptionVoucher.setRvrProductCode(catalogueRedemptionItemRequest.getCatProductCode());
            redemptionVoucher.setRvrMerchant(redemptionMerchant.getRemNo());
            redemptionVoucher.setRvrVoucherCode(voucherCode);
            redemptionVoucher.setRvrStatus(RedemptionVoucherStatus.NEW);
            redemptionVoucher.setRvrLoyaltyId(catalogueRedemptionItemRequest.getCreditLoyaltyId());
            redemptionVoucher.setRvrMerchantNo(catalogueRedemptionItemRequest.getMerchantNo());
            redemptionVoucher.setRvrUniqueBatchId(trackingId);
            redemptionVoucher.setRvrAssignedStatus(VoucherUpdateStatus.ASSIGNED);

            //for setting created date
            // create a java calendar instance
            Calendar calendar = Calendar.getInstance();

            // get a java date (java.util.Date) from the Calendar instance.
            // this java date will represent the current date, or "now".
            java.util.Date currentDate = calendar.getTime();

            // now, create a java.sql.Date from the java.util.Date
            Date date = new Date(currentDate.getTime());

            redemptionVoucher.setRvrCreateDate(date);

            redemptionVoucher.setRvrVoucherType(RedemptionVoucherType.DYNAMIC_VOUCHER);

            //find voucher expiry date
            Date voucherExpiryDate = catalogueService.getExpiryDateForVoucher(catalogue, redemptionVoucher.getRvrCreateDate());

            redemptionVoucher.setRvrExpiryDate(voucherExpiryDate);

            //save the redemption voucher
            redemptionVoucher = redemptionVoucherService.validateAndSaveRedemptionVoucher(redemptionVoucher);

            if (redemptionVoucher != null && redemptionVoucher.getRvrId() != null) {

                log.info("Generated Redemption Voucher " + redemptionVoucher);

            } else {

                //set the status to success
                catalogueRedemptionItemResponse.setRdmStatus(CatalogueRedemptionStatus.CAT_RDM_REDEEM_FAILED);

                //return the object
                return catalogueRedemptionItemResponse;
            }


            Redemption redemption = redemptionService.findByRdmId(catalogueRedemptionItemResponse.getRdmId());

            //set redemption status to processed successfully
            redemption.setRdmStatus(RedemptionStatus.RDM_STATUS_PROCESSED_SUCCESSFULLY);

            //save the redemption object
            redemptionService.saveRedemption(redemption);

            //send the success message to the customer
            //create a map for the sms placeholders
            HashMap<String, String> smsParams = new HashMap<>(0);

            //put the placeholders into the map
            smsParams.put("#voucherCode", voucherCode);

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

                //put the placeholders into the map
                pasaRewardParams.put("#voucherCode", voucherCode);

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

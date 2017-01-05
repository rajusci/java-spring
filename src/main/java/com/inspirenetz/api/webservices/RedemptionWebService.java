package com.inspirenetz.api.webservices;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.service.RedemptionService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by sandheepgr on 27/7/14.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class RedemptionWebService extends SpringBeanAutowiringSupport{

    @Autowired
    private RedemptionService redemptionService;

    @Autowired
    private GeneralUtils generalUtils;

    // Create the logger
    private static Logger log = LoggerFactory.getLogger(RedemptionWebService.class);

    @WebMethod(operationName = "redeemCatalogue")
    public String redeemCatalogue(
                                                       @WebParam(name="loyaltyid")String loyaltyId,
                                                       @WebParam(name="prdcode")String prdCode,
                                                       @WebParam(name="qty") Integer qty,
                                                       @WebParam(name="channel") Integer channel,
                                                       @WebParam(name="destLoyaltyId") String destLoyaltyId)  {

        // Get the merchantNO
        Long merchantNo = generalUtils.getDefaultMerchantNo();

        // Call the webservice
        CatalogueRedemptionItemResponse catalogueRedemptionItemResponse = null;

        // Create a catalogue redemption item request object
        CatalogueRedemptionItemRequest catalogueRedemptionItemRequest = new CatalogueRedemptionItemRequest();

        catalogueRedemptionItemRequest.setLoyaltyId(loyaltyId);
        catalogueRedemptionItemRequest.setMerchantNo(merchantNo);
        catalogueRedemptionItemRequest.setChannel(channel);
        catalogueRedemptionItemRequest.setQty(qty);
        catalogueRedemptionItemRequest.setDestLoyaltyId(destLoyaltyId);
        catalogueRedemptionItemRequest.setPrdCode(prdCode);

        try {

            // Get the response object
            catalogueRedemptionItemResponse = redemptionService.redeemCatalogueItems(catalogueRedemptionItemRequest);

            // Log the response
            log.info("RedemptionWebService ->  " + catalogueRedemptionItemResponse);

        } catch (Exception e) {

            // Log the error
            log.error("RedemptionWebService -> Error processing request " + e.getMessage());

            // Print the stack trace
            e.printStackTrace();

        }

        // Check if the response is not null
        if ( catalogueRedemptionItemResponse != null ) {

            return "success";

        } else {

            return "failed";

        }

    }


    /*

    @WebMethod
    public RedemptionServiceResponse doCashback(
                                                       @WebParam(name="loyaltyId") String loyaltyId,
                                                       @WebParam(name="rwdId") Long rwdId,
                                                       @WebParam(name="amount") Double amount,
                                                       @WebParam(name="txnref")String txnRef) {


        // Get the merchantNo;
        Long merchantNo = AuthSession.getMerchantNo();

        // Get the user location
        Long userLocation = AuthSession.getUserLocation();

        // get the user number for the currenlty logged in user
        Long userNo = AuthSession.getUserNo();


        // Create the CashBackRedemptionRequest object
        CashBackRedemptionRequest cashbackRedemptionRequest = new CashBackRedemptionRequest();


        // Set the fields for the CashBackRedemptionRequest
        //
        // Set the merchantNo
        cashbackRedemptionRequest.setMerchantNo(merchantNo);

        // Set the location
        cashbackRedemptionRequest.setUserLocation(userLocation);

        // Set the user number to the request
        cashbackRedemptionRequest.setUserNo(userNo);


        // Set the loyaltyid
        cashbackRedemptionRequest.setLoyaltyId(loyaltyId);

        // Set the reward currency id
        cashbackRedemptionRequest.setRewardCurrencyId(rwdId);

        // set the amount
        cashbackRedemptionRequest.setAmount(amount);

        // Set the reference
        cashbackRedemptionRequest.setTxnRef(txnRef);

        // Hold the audit details
        String auditDetails = AuthSession.getUserNo().toString() ;

        // Set the auditDetails
        cashbackRedemptionRequest.setAuditDetails(auditDetails);





        // Call the doCashbackFunction and then get the response
        CashBackRedemptionResponse response = redemptionService.doCashbackRedemption(cashbackRedemptionRequest);

        // Create the RedemptonSErvice response
        RedemptionServiceResponse redemptionServiceResponse = new RedemptionServiceResponse();

        redemptionServiceResponse.setStatus(response.getStatus());

        redemptionServiceResponse.setErrorcode(response.getErrorcode());

        redemptionServiceResponse.setData(response.getTxnRef());

        // Return the response
        return redemptionServiceResponse;


    }



    @WebMethod
    public RedemptionVoucherListItem[] listPendingRedemptionVouchers(@WebParam(name="loyaltyId") String loyaltyId) {

        RedemptionVoucherListItem redemptionVoucherListItem[] = new RedemptionVoucherListItem[1];
        redemptionVoucherListItem[0] = new RedemptionVoucherListItem();

        return redemptionVoucherListItem;

    }




    @WebMethod
    public RedemptionServiceResponse applyRedemptionVoucher(
            @WebParam(name="mMin") String mMin,
            @WebParam(name = "loyaltyId") String loyaltyId,
            @WebParam(name = "voucherCode") String voucherCode
    ) {

        // Create the RedemptonSErvice response
        RedemptionServiceResponse redemptionServiceResponse = new RedemptionServiceResponse();

        redemptionServiceResponse.setStatus("success");

        redemptionServiceResponse.setErrorcode("");

        redemptionServiceResponse.setData(voucherCode);

        // Return the response
        return redemptionServiceResponse;

        /**
         * error codes
         *
         * ERR_INVALID_COUPON - RedemptionVoucher is not valid ( expired / not available for customer
         * ERR_NOT_APPLICABLE - RedemptionVoucher is not applicable to the customer
         * ERR_COUPON_EXPIRED - RedemptionVoucher has expired
         *



    }

    */

}

package com.inspirenetz.api.webservices;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.SalesSKU;
import com.inspirenetz.api.core.domain.Purchase;
import com.inspirenetz.api.core.domain.PurchaseSKU;
import com.inspirenetz.api.core.service.PurchaseSKUService;
import com.inspirenetz.api.core.service.PurchaseService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 27/7/14.
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class SalesWebService extends SpringBeanAutowiringSupport {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseSKUService purchaseSKUService;



    @WebMethod
    public String addSales(@WebParam(name="loyaltyId") String loyaltyId,
                            @WebParam(name="date")String date,
                            @WebParam(name="time")String time,
                            @WebParam(name="amount") Double amount,
                            @WebParam(name="paymentMode")Integer paymentMode,
                            @WebParam(name="txnref")String txnRef,
                            @WebParam(name="txnCurrency") String txnCurrency,
                            @WebParam(name="txnChannel")Integer txnChannel,
                            @WebParam(name="sku") SalesSKU salesSku[]) {


        // Get the merchant number and store
        Long merchantNo = AuthSession.getMerchantNo();

        // Get the location
        Long userLocation = AuthSession.getUserLocation();

        // Get the userNumbPer
        Long userNo = AuthSession.getUserNo();


        // Create the Purchase object
        Purchase purchase= new Purchase();

        // Set the merchantNo
        purchase.setPrcMerchantNo(merchantNo);

        // Set the user location
        purchase.setPrcLocation(userLocation);

        // Set the user number
        purchase.setPrcUserNo(userNo);

        // Set the loyalty id
        purchase.setPrcLoyaltyId(loyaltyId);

        // Set the amount
        purchase.setPrcAmount(amount);

        // SEt the paymentMode
        purchase.setPrcPaymentMode(paymentMode);

        // Set the date
        purchase.setPrcDate(DBUtils.covertToSqlDate(date));

        // Set the time
        purchase.setPrcTime(DBUtils.convertToSqlTime(time));

        // Set the qty
        purchase.setPrcQuantity(1);

        // Set the payment reference
        purchase.setPrcPaymentReference(txnRef);

        // Set the transacton channel
        purchase.setPrcTxnChannel(txnChannel);;

        // Set the currency
        purchase.setPrcCurrency(0);

        // Hold the audit details
        String auditDetails = AuthSession.getUserNo().toString() + "#" + AuthSession.getUserLoginId();

        // Set the created by field
        purchase.setCreatedBy(auditDetails);

        // Save the purchase object
        try {

            purchase = purchaseService.savePurchase(purchase);

        } catch (InspireNetzException e) {

            e.printStackTrace();

            // Return the error code
            return e.getErrorCode().name();

        }


        // Check if the salesKUs are enabled
        if ( salesSku != null && salesSku.length > 0 ) {

            // List holding the purchaseskus
            List<PurchaseSKU> purchaseSKUList = new ArrayList<>(0);

            // Go through each of the salesSKUS and convert them to purchaseSKU
            for(int i = 0; i < salesSku.length ; i++ ) {

                // Create the PurchaseSKU
                PurchaseSKU purchaseSKU = new PurchaseSKU();

                // Set the item code
                purchaseSKU.setPkuProductCode(salesSku[i].getItemCode());

                // Set the purchaseId
                purchaseSKU.setPkuPurchaseId(purchase.getPrcId());

                // Set the qty
                purchaseSKU.setPkuQty(salesSku[i].getQty());

                // Set the price
                purchaseSKU.setPkuPrice(salesSku[i].getPrice());

                // Add to the list
                purchaseSKUList.add(purchaseSKU);

            }


            // Save the purchaseSKU list
            purchaseSKUService.saveAll(purchaseSKUList);

        }




        // Return success
        return "success";

    }


}

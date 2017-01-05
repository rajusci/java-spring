package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.TransactionType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.assembler.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.*;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class TransactionController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PurchaseSKUService purchaseSKUService;

    @Autowired
    private SaleSKUService saleSKUService;

    @Autowired
    private TransactionCompatibleAssembler transactionCompatibleAssembler;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private TransactionAssembler transactionAssembler;

    @Autowired
    private LoyaltyTransactionCompatibleAssembler loyaltyTransactionCompatibleAssembler;

    @Autowired
    private SaleSKUAssembler saleSKUAssembler;

    @Autowired
    private PurchaseSKUAssembler purchaseSKUAssembler;

    @Autowired
    private CardTransactionService cardTransactionService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/customer/transactions/{txnLoyaltyId}/{txnStartDate}/{txnEndDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listTransactions(
                                                @PathVariable(value = "txnLoyaltyId") String txnLoyaltyId,
                                                @PathVariable(value = "txnStartDate") Date txnStartDate,
                                                @PathVariable(value = "txnEndDate"  ) Date txnEndDate,
                                                Pageable pageable
    ){


        // Log the Request
        log.info("listTransactions - Request Received# ");
        log.info("listTransactions - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = AuthSession.getMerchantNo();


        // Log the Request
        log.info("searchPurchase - Request params - LoyaltyId:"+txnLoyaltyId +
                " - txnStartDate:"+txnStartDate.toString()+
                " - txnEndDate:"+txnEndDate.toString()
        );

        // Check if the txnStartDate is set or not
        // If the start date is not set, then we need to set the date to the minimum value
        if ( txnStartDate == null  ){

            // Create the calendar object
            Calendar cal = Calendar.getInstance();

            // set Date portion to January 1, 1970
            cal.set( cal.YEAR, 1970 );
            cal.set( cal.MONTH, cal.JANUARY );
            cal.set( cal.DATE, 1 );

            txnStartDate = new Date(cal.getTime().getTime());

        }


        // Check if the endDate is set, if not then we need to
        // set the date to the largest possible date
        if ( txnEndDate == null ) {

            // Create the calendar object
            Calendar cal = Calendar.getInstance();

            // set Date portion to December 31, 9999
            cal.set( cal.YEAR, 9999 );
            cal.set( cal.MONTH, cal.DECEMBER );
            cal.set( cal.DATE, 31 );

            txnEndDate = new Date(cal.getTime().getTime());

        }


        // Create the Page<Transasction> object
        Page<Transaction> transactionPage ;

        // Check if the transactionPage exists
        if ( txnLoyaltyId != null && !txnLoyaltyId.equals("0") ) {

            transactionPage = transactionService.findByTxnMerchantNoAndTxnLoyaltyIdAndTxnDateBetweenOrderByTxnIdDesc(merchantNo,txnLoyaltyId,txnStartDate,txnEndDate,pageable);

        } else {

            transactionPage = transactionService.findByTxnMerchantNoAndTxnDateBetweenOrderByTxnIdDesc(merchantNo,txnStartDate,txnEndDate,pageable);

        }


        // Get the list of the transactionResources
        List<TransactionResource> transactionResourceList = transactionAssembler.toResources(transactionPage);

        // Set the data
        retData.setData(transactionResourceList);

        // Set the retData Pageable fields
        retData.setPageableParams(transactionPage);



        // Log the response
        log.info("listTransactions - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }




    @RequestMapping(value = "/api/0.9/json/merchant/customer/transaction/{txnId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getTranscationInfo(
            @PathVariable(value = "txnId") Long txnId
    ) throws InspireNetzException {


        // Log the Request
        log.info("getTranscationInfo - Request Received# Txn ID; " + txnId);
        log.info("getTranscationInfo - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = AuthSession.getMerchantNo();


        // Get the information for the transaction
        Transaction transaction = transactionService.findByTxnMerchantNoAndTxnId(merchantNo,txnId);


        // Check if the transaction exists
        if ( transaction == null || transaction.getTxnId() == null ) {

            // Log the response
            log.info("getTranscationInfo - Response : No transaction information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Convert Tranasction to TransactionResource
        TransactionResource transactionResource = transactionAssembler.toResource(transaction);


        // Check the transaction type
        if ( transaction.getTxnType() == TransactionType.PURCHASE ) {

            // Get the purchase sku information for the purchase id referenced by the
            // internal reference
            List<SaleSKU> saleSKUList = saleSKUService.findBySsuSaleId(Long.parseLong(transaction.getTxnInternalRef()));

            // If the purchaseSKUList is not empty , then we need to convert it into resource
            // and add to the transactionResrouce object
            if ( saleSKUList != null ) {

                // Convert the list to resource
                List<SaleSKUResource> saleSKUResourceList = saleSKUAssembler.toResources(saleSKUList,merchantNo);

                // Set the resources to the transactionResource
                transactionResource.setSaleSKUResources(saleSKUResourceList);

            }
        }


        // Get the information for the merchant
        Merchant merchant = merchantService.findByMerMerchantNo(transaction.getTxnMerchantNo());

        // If the merchant object is not null, the set the merchant name
        if ( merchant != null ) {

            // Set the merchant name
            transactionResource.setMerchantName(merchant.getMerMerchantName());

        }


        // Set the data
        retData.setData(transactionResource);

        // SEt the status to be succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("listTransactions - " + generalUtils.getLogTextForResponse(retData));



        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/customer/transactions/{merchantno}/{txnStartDate}/{txnEndDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerTransactionsInfo(
            @PathVariable(value = "merchantno") Long merchantNo,
            @PathVariable(value = "txnStartDate") Date txnStartDate,
            @PathVariable(value = "txnEndDate"  ) Date txnEndDate,
            Pageable pageable
    ) throws InspireNetzException {


        // Log the Request
        log.info("listTransactions - Request Received# ");
        log.info("listTransactions - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("searchPurchase - Request params - merchant No:"+merchantNo +
                " - txnStartDate:"+txnStartDate.toString()+
                " - txnEndDate:"+txnEndDate.toString()
        );

        // Variable holding the MessageSpiel
        List<TransactionResource> transactionResourceList = new ArrayList<>(0);


        Page<Transaction> transactionResourcePage =transactionService.searchCustomerTransaction(merchantNo, txnStartDate, txnEndDate, pageable);

        // Get the list of the transactionResources
        transactionResourceList = transactionAssembler.toResources(transactionResourcePage);

        // Set the data
        retData.setData(transactionResourceList);

        // Set the retData Pageable fields
        retData.setPageableParams(transactionResourcePage);


        // Log the response
        log.info("listTransactions - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/customer/transactions/sms/{loyaltyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject sendTransactionListSMS(
            @PathVariable(value = "loyaltyId") String loyaltyId
    ) throws InspireNetzException {


        // Log the Request
        log.info("sendTransactionListSMS - Request Received# ");
        log.info("sendTransactionListSMS - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("sendTransactionListSMS - Request params - Loyalty Id:" + loyaltyId);

        // Call the method
        transactionService.sendTransactionSMS(loyaltyId);

        // Set the data
        retData.setData("");

        // Log the response
        log.info("listTransactions - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/customer/transaction/{txnId}/{merchantno}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerTransaction(
            @PathVariable(value = "txnId") Long txnId,
            @PathVariable(value="merchantno") Long merchantNo
    ) throws InspireNetzException {


        // Log the Request
        log.info("getTranscationInfo - Request Received# Txn ID; " + txnId);
        log.info("getTranscationInfo - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the information for the transaction
        Transaction transaction = transactionService.findByTxnMerchantNoAndTxnId(merchantNo,txnId);

        // Check if the transaction exists
        if ( transaction == null || transaction.getTxnId() == null ) {

            // Log the response
            log.info("getTranscationInfo - Response : No transaction information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Convert Tranasction to TransactionResource
        TransactionResource transactionResource = transactionAssembler.toResource(transaction);


        // Check the transaction type
        if ( transaction.getTxnType() == TransactionType.PURCHASE ) {

            // Get the purchase sku information for the purchase id referenced by the
            // internal reference
            List<SaleSKU> saleSKUList = saleSKUService.findBySsuSaleId(Long.parseLong(transaction.getTxnInternalRef()));


            // If the purchaseSKUList is not empty , then we need to convert it into resource
            // and add to the transactionResrouce object
            if ( saleSKUList != null ) {

                // Convert the list to resource
                List<SaleSKUResource> saleSKUResourceList = saleSKUAssembler.toResources(saleSKUList,merchantNo);

                // Set the resources to the transactionResource
                transactionResource.setSaleSKUResources(saleSKUResourceList);

            }
        }


        // Get the information for the merchant
        Merchant merchant = merchantService.findByMerMerchantNo(transaction.getTxnMerchantNo());

        // If the merchant object is not null, the set the merchant name
        if ( merchant != null ) {

            // Set the merchant name
            transactionResource.setMerchantName(merchant.getMerMerchantName());

        }


        // Set the data
        retData.setData(transactionResource);

        // SEt the status to be succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("listTransactions - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    /**
     *
     * @param txnType
     * @param txnType
     * @param maxItem
     * @return
     * @throws InspireNetzException
     */

    @RequestMapping(value = "/api/0.9/json/transaction/transactionlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getTransactionListCompatibleWpf(
                                        @RequestParam(value = "cardnumber") String crmCardNo,
                                        @RequestParam(value="list_type",defaultValue = "0") Integer txnType,
                                        @RequestParam(value = "max_items",defaultValue = "5") Integer maxItem
                                ) throws InspireNetzException {


        // Log the Request
        log.info("getTransactionListCompatibleWpf - Request Received# cardNumber; " + crmCardNo);
        log.info("getTransactionListCompatibleWpf - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        List<CardTransaction> cardTransactionList =null;

        //check Transaction is card Transaction or loyaltyTransaction if type is 1 loyalty transaction else card transaction
        if(txnType ==2){

            //return card transactionList
            cardTransactionList = cardTransactionService.getCardTransactionCompatible(crmCardNo,merchantNo,maxItem);

            if(cardTransactionList ==null){

                retData.setStatus(APIResponseStatus.failed);

                retData.setErrorCode(APIErrorCode.ERR_NO_DATA);

            }else {

                log.info("listTransactions Compatible Api - " + generalUtils.getLogTextForResponse(retData));

                List<TransactionCompatibleResource> transactionCompatibleResource = transactionCompatibleAssembler.toResources(cardTransactionList);

                retData.setData(transactionCompatibleResource);

                retData.setStatus(APIResponseStatus.success);

                return retData;
            }



        }else if(txnType ==1){

           //get last 5 transaction
          List<Transaction> transactionList =transactionService.getLastTransactionCompatible(merchantNo,crmCardNo,maxItem);

          if(transactionList !=null){

              List<LoyaltyTransactionCompatibleResource> loyaltyTransactionCompatibleResourceList =loyaltyTransactionCompatibleAssembler.toResources(transactionList);

              retData.setStatus(APIResponseStatus.success);

              retData.setData(loyaltyTransactionCompatibleResourceList);

              log.info("list Loyalty Transactions Compatible Api - " + generalUtils.getLogTextForResponse(retData));


              return retData;

          }else{

              retData.setStatus(APIResponseStatus.failed);

              retData.setErrorCode(APIErrorCode.ERR_NO_DATA);
          }


        }

        // Log the response
        log.info("listTransactions Compatible Api - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }



}

package com.inspirenetz.api.rest.controller;

import com.google.common.io.CountingOutputStream;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.SaleType;
import com.inspirenetz.api.core.domain.Purchase;
import com.inspirenetz.api.core.domain.PurchaseSKU;
import com.inspirenetz.api.core.domain.validator.PurchaseValidator;
import com.inspirenetz.api.core.service.PurchaseSKUService;
import com.inspirenetz.api.core.service.PurchaseService;
import com.inspirenetz.api.core.service.SalesMasterRawdataService;
import com.inspirenetz.api.core.service.SalesMasterSkuRawdataService;
import com.inspirenetz.api.rest.assembler.PurchaseAssembler;
import com.inspirenetz.api.rest.assembler.PurchaseSKUAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.PurchaseResource;
import com.inspirenetz.api.rest.resource.PurchaseSKUResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import com.inspirenetz.api.util.integration.IntegrationUtils;
import com.inspirenetz.api.util.integration.SalesDataXMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sandheepgr on 26/4/14.
 */
@Controller
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseSKUService purchaseSKUService;

    @Autowired
    private PurchaseAssembler purchaseAssembler;

    @Autowired
    private PurchaseSKUAssembler purchaseSKUAssembler;

    @Autowired
    private SalesMasterRawdataService salesMasterRawdataService;

    @Autowired
    private SalesMasterSkuRawdataService salesMasterSkuRawdataService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    IntegrationUtils integrationUtils;

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(PurchaseController.class);

    @RequestMapping(value = "/api/0.9/json/transaction/sales", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject savePurchase(@Valid Purchase purchase,BindingResult result) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the PurchaseValidator
        PurchaseValidator validator = new PurchaseValidator();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the location
        Long userLocation = authSessionUtils.getUserLocation();

        // Get the userNumber
        Long userNo = authSessionUtils.getUserNo();


        // Log the Request
        log.info("savePurchase - Request Received# "+purchase.toString());
        log.info("savePurchase -  "+generalUtils.getLogTextForRequest());



        // Set the merchantNo
        purchase.setPrcMerchantNo(merchantNo);

        // Set the user location
        purchase.setPrcLocation(userLocation);

        // Set the user number
        purchase.setPrcUserNo(userNo);


        // Validate using the PurchaseValidator object
        validator.validate(purchase, result);

        // Check if there are validation errors for the purchse object
        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("savePurchase - Response : Invalid Input ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }


        // Check if the purchase data is already existing
        boolean isExist = purchaseService.isDuplicatePurchase(
                                                                merchantNo,
                                                                purchase.getPrcLoyaltyId(),
                                                                purchase.getPrcPaymentReference(),
                                                                purchase.getPrcDate(),
                                                                purchase.getPrcAmount()
                                                             );

        // Check the boolean value and if the entry already exists, then we need to show
        // the message as already exists
        if ( isExist ) {

            // Log the response
            log.info("savePurchase - Response : Purchase entry already exists");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }


        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();


        // Check the purchase
        if (purchase.getPrcId() == null ) {

            purchase.setCreatedBy(auditDetails);

        } else {

            purchase.setUpdatedBy(auditDetails);;

        }

        // Save the purchase object
        purchase = purchaseService.savePurchase(purchase);

        // Check if a auto id was generated
        if ( purchase.getPrcId() != null ) {

            // Set the purchase id as data
            retData.setData(purchase.getPrcId());;

            // Set the status to success
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the data to be the purchase object
            retData.setData(purchase);

            // Log the response
            log.info("savePurchase - Response : Unable to save the purchase information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // Log the response
        log.info("savePurchase - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseObject
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/transaction/addpurchase", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject savePurchaseCompatible(
                                                        @RequestParam(value = "cardnumber") String cardNumber,
                                                        @RequestParam(value = "purchase_amount") Double amount,
                                                        @RequestParam(value = "payment_mode",defaultValue = "1",required = false) Integer paymentMode,
                                                        @RequestParam(value = "date") Date date,
                                                        @RequestParam(value = "time",required = false ,defaultValue = "00:00:00")Time time,
                                                        @RequestParam(value = "qty",defaultValue = "1",required = false) Double qty,
                                                        @RequestParam(value = "txnref",defaultValue = "",required = false) String txnref,
                                                        @RequestParam(value = "txn_channel",defaultValue = "1",required = false) Integer txnChannel,
                                                        @RequestParam(value = "currency",defaultValue = "356",required = false) String txnCurrency) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the location
        Long userLocation = authSessionUtils.getUserLocation();

        // Get the userNumbPer
        Long userNo = authSessionUtils.getUserNo();

        // Log the Request
        log.info("savePurchaseCompatible - Request Received# "+
                        "cardnumber:"+cardNumber +
                        " - amount:"+amount.toString() +
                        " -  payment_mode:"+paymentMode.toString() +
                        " -  date:"+date.toString()+
                        " -  time:"+time.toString() +
                        " -  qty:"+qty.toString() +
                        " -  txnref:"+txnref.toString() +
                        " -  txnchannel:"+txnChannel.toString() +
                        " -  currency:"+txnCurrency.toString()
                );
        log.info("savePurchaseCompatible - "+generalUtils.getLogTextForRequest());


        // Create the Purchase object
        Purchase purchase= new Purchase();

        // Set the merchantNo
        purchase.setPrcMerchantNo(merchantNo);

        // Set the user location
        purchase.setPrcLocation(userLocation);

        // Set the user number
        purchase.setPrcUserNo(userNo);

        // Set the loyalty id
        purchase.setPrcLoyaltyId(cardNumber);

        // Set the amount
        purchase.setPrcAmount(amount);;

        // SEt the paymentMode
        purchase.setPrcPaymentMode(paymentMode);

        // Set the date
        purchase.setPrcDate(date);

        // Set the time
        purchase.setPrcTime(time);

        // Set the qty
        purchase.setPrcQuantity(qty);

        // Set the payment reference
        purchase.setPrcPaymentReference(txnref);

        // Set the transacton channel
        purchase.setPrcTxnChannel(txnChannel);;

        // Set the currency
        purchase.setPrcCurrency(0);



        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();


        // Check the purchase
        if (purchase.getPrcId() == null ) {

            // Set the created by field
            purchase.setCreatedBy(auditDetails);

        } else {

            // Set the updated by field
            purchase.setUpdatedBy(auditDetails);;

        }


        // Save the purchase object
        purchase = purchaseService.savePurchase(purchase);

        // Check if a auto id was generated
        if ( purchase.getPrcId() != null ) {

            // Set the purchase id as data
            retData.setData(purchase.getPrcId());;

            // Set the status to success
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("savePurchaseCompatible - Response : Unable to save the purchase information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
        }


        // Log the response
        log.info("savePurchaseCompatible -" + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseObject
        return retData;

    }




    @RequestMapping(value = "/api/0.9/json/merchant/customer/sales/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deletePurchase(@RequestParam(value="prcId") Long prcId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Log the Request
        log.info("deletePurchase - Request Received# - PurchaseId:"+prcId.toString());
        log.info("deletePurchase -  "+generalUtils.getLogTextForRequest());


        // Get the purchase object
        Purchase purchase = purchaseService.findByPrcId(prcId);

        // Check if the purchaseExists
        if ( purchase == null || purchase.getPrcId() == null ) {

            // Set the log
            log.info("deletePurchase - No data found");

            // throw the exception
            throw  new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }



        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( purchase.getPrcMerchantNo().longValue() != merchantNo ) {

            // Set the log
            log.info("deletePurchase - Not authorized");

            // throw the exception
            throw  new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the purchase and set the retData fields
        purchaseService.deletePurchase(prcId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete brnId
        retData.setData(prcId);


        // Set the response
        log.info("deletePurchase - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/sales/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject searchPurchase(
                                            @PathVariable(value="filter") String filter,
                                            @PathVariable(value="query") String query,
                                            @RequestParam(value="prcStartDate",required = false) Date prcStartDate,
                                            @RequestParam(value="prcEndDate",required = false) Date prcEndDate,
                                            Pageable pageable){

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Log the Request
        log.info("searchPurchase - Request Received#");
        log.info("searchPurchase -  "+generalUtils.getLogTextForRequest());


        // Check if the prcStartDate is set or not
        // If the start date is not set, then we need to set the date to the minimum value
        if ( prcStartDate == null ){

            // Create the calendar object
            Calendar cal = Calendar.getInstance();

            // set Date portion to January 1, 1970
            cal.set( cal.YEAR, 1970 );
            cal.set( cal.MONTH, cal.JANUARY );
            cal.set( cal.DATE, 1 );

            prcStartDate = new Date(cal.getTime().getTime());

        }


        // Check if the endDate is set, if not then we need to
        // set the date to the largest possible date
        if ( prcEndDate == null ) {

            // Create the calendar object
            Calendar cal = Calendar.getInstance();

            // set Date portion to December 31, 9999
            cal.set( cal.YEAR, 9999 );
            cal.set( cal.MONTH, cal.DECEMBER );
            cal.set( cal.DATE, 31 );

            prcEndDate = new Date(cal.getTime().getTime());

        }


        // Log the Request
        log.info("searchPurchase - Request params Filter - "+filter +" : - Query:"+query.toString() +
                " - prcStartDate:"+prcStartDate.toString()+
                " - prcEndDate:"+prcEndDate.toString()
        );


        // Resource list
        List<PurchaseResource> purchaseResourceList = new ArrayList<>(0);

        // Check the filter and query
        if ( filter.equals("loyaltyid") ) {

            // Get the list of purchases
            Page<Purchase> purchaseList = purchaseService.findByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateBetween(merchantNo,query,prcStartDate,prcEndDate,pageable);

            // convert to resource list
            purchaseResourceList = purchaseAssembler.toResources(purchaseList);

            // Set the pagable
            retData.setPageableParams(purchaseList);

        }




        // Store the response as data to the retData object
        retData.setData(purchaseResourceList);

        // Set the data to success
        retData.setStatus(APIResponseStatus.success);


        log.info("searchPurchase - " + generalUtils.getLogTextForResponse(retData));

        // Return the retData
        return retData;

    }


    @RequestMapping(value = "/api/0.9/xml/sku/salemaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
    @ResponseBody
    public APIResponseObject readPurchaseXML(InputStream inputStream) throws InspireNetzException {

        // Create the APIResponseObject

        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the location
        Long userLocation = authSessionUtils.getUserLocation();

        // Log the Request
        log.info("readPurchaseXML - Request Received# ");
        log.info("readPurchaseXML -  "+generalUtils.getLogTextForRequest());


        try {

            // Set the read value to be 0
            int read = 0;

            // Get the integration filename
            String filename = integrationUtils.getIntegrationFileName(merchantNo,userLocation,"salesmaster","xml");

            // Create the FileOutputSteam object with the integration filename
            FileOutputStream fos = new FileOutputStream(filename);

            // Create the counting output stream
            CountingOutputStream out = new CountingOutputStream(fos);

            // Create the byteArray of 100 KB
            byte[] bytes = new byte[102400];

            // Read from the inputstream and set to the bytes till the
            // there is no data to read
            while ((read = inputStream.read(bytes)) != -1) {

                // Write the bytes to the output stream
                out.write(bytes, 0, read);

            }

            // Flush the output stream
            out.flush();

            // Close the outputstream
            out.close();

            // Parse the xml
            SalesDataXMLParser parser = new SalesDataXMLParser(filename,salesMasterRawdataService,salesMasterSkuRawdataService);

            // Set the data to be successful
            retData.setStatus(APIResponseStatus.success);


        } catch (Exception e) {

            // TODO throw!
            e.printStackTrace();

            // Log the file
            log.info("readPurchaseXML - Error reading the stream");

            // throw
            throw new InspireNetzException(APIErrorCode.ERR_STREAM_ERROR);

        }

        log.info("readPurchaseXML - File read successfully");

        // Return the retData object
        return new APIResponseObject();

    }


    @RequestMapping(value = "/api/0.9/json/merchant/sale/{prcId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getPurchaseInfo(
            @PathVariable(value="prcId") Long prcId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Log the Request
        log.info("getPurchaseInfo - Request Received# Purhcase ID "+prcId);
        log.info("getPurchaseInfo -  "+generalUtils.getLogTextForRequest());


        // Get the purhcase Infor
        Purchase purchase = purchaseService.findByPrcId(prcId);

        // Check if the purchase is found
        if ( purchase == null || purchase.getPrcId() == null) {

            // Log the response
            log.info("getPurchaseInfo - Response : No purchase information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( purchase.getPrcMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getPurchaseInfo - Response : You are not authorized to view the purchase");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Convert to resource
        PurchaseResource purchaseResource = purchaseAssembler.toResource(purchase);


        // Check the type of the purchase
        // If the its sku level, then get the purchase sku information
        if ( purchase.getPrcType() == SaleType.ITEM_BASED_PURCHASE) {

            // Get the purchase sku information for the purchase id referenced by the
            // internal reference
            List<PurchaseSKU> purchaseSKUList = purchaseSKUService.findByPkuPurchaseId(prcId);


            // If the purchaseSKUList is not empty , then we need to convert it into resource
            // and add to the transactionResrouce object
            if ( purchaseSKUList != null ) {

                // Convert the list to resource
                List<PurchaseSKUResource> purchaseSKUResourceList = purchaseSKUAssembler.toResources(purchaseSKUList);

                // Set the resources to the purchaseResource
                purchaseResource.setPurchaseSKUList(purchaseSKUResourceList);

            }

        }




        // Store the response as data to the retData object
        retData.setData(purchaseResource);

        // Set the data to success
        retData.setStatus(APIResponseStatus.success);


        log.info("getPurchaseInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retData
        return retData;

    }
}




package com.inspirenetz.api.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CountingOutputStream;
import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.SaleType;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.domain.SaleSKU;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.service.SaleSKUService;
import com.inspirenetz.api.core.service.SaleService;
import com.inspirenetz.api.core.service.SalesMasterRawdataService;
import com.inspirenetz.api.core.service.SalesMasterSkuRawdataService;
import com.inspirenetz.api.rest.assembler.SaleAssembler;
import com.inspirenetz.api.rest.assembler.SaleSKUAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerResource;
import com.inspirenetz.api.rest.resource.EBillResource;
import com.inspirenetz.api.util.*;
import com.inspirenetz.api.util.integration.IntegrationUtils;
import com.inspirenetz.api.util.integration.SalesDataXMLParser;
import com.inspirenetz.api.util.integration.SalesMasterXMLParser;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sandheepgr on 26/4/14.
 */
@Controller
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleAssembler saleAssembler;

    @Autowired
    private AttributeExtensionUtils attributeExtensionUtils;

    @Autowired
    private SalesMasterRawdataService salesMasterRawdataService;

    @Autowired
    private SalesMasterSkuRawdataService salesMasterSkuRawdataService;

    @Autowired
    private SaleSKUService saleSKUService;

    @Autowired
    private SaleSKUAssembler saleSKUAssembler;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    IntegrationUtils integrationUtils;

    @Autowired
    SalesMasterXMLParser salesMasterXMLParser;

    @Autowired
    Mapper mapper;



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(SaleController.class);

    @RequestMapping(value = "/api/0.9/json/transaction/sale", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveSale( @RequestBody Map<String,Object> params) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("saveSale - Request Received# "+params);
        log.info("saveSale - Requested User  # Login Id : "+authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());



        // Save the sale object
        Sale sale = saleService.saveSaleDataFromParams(params);


        // Call the loyalty processing for the sales.
        saleService.processSaleTransactionForLoyalty(sale);

        // Set the data as the sale id
        retData.setData(sale.getSalId());

        // Set the status as success
        retData.setStatus(APIResponseStatus.success);



        // Log the response
        log.info("saveSale - Response : " + retData.toString());

        // Return the APIResponseObject
        return retData;

    }



    @RequestMapping(value = "/api/0.9/json/transaction/purchase", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveSaleCompatible(
                                                        @RequestParam(value = "cardnumber") String cardNumber,
                                                        @RequestParam(value = "purchase_amount") Double amount,
                                                        @RequestParam(value = "payment_mode",defaultValue = "1",required = false) Integer paymentMode,
                                                        @RequestParam(value = "date") Date date,
                                                        @RequestParam(value = "time",required = false ,defaultValue = "00:00:00")String timeString,
                                                        @RequestParam(value = "qty",defaultValue = "1",required = false) Double qty,
                                                        @RequestParam(value = "txnref",defaultValue = "",required = false) String txnref,
                                                        @RequestParam(value = "txn_channel",defaultValue = "1",required = false) Integer txnChannel,
                                                        @RequestParam(value = "currency",defaultValue = "356",required = false) String txnCurrency) throws InspireNetzException, ParseException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = AuthSession.getMerchantNo();

        // Get the location
        Long userLocation = AuthSession.getUserLocation();

        // Get the userNumbPer
        Long userNo = AuthSession.getUserNo();

        //convert string to sql time
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        java.util.Date date1 = parseFormat.parse(timeString);

        Time time =new Time(date1.getTime());


        // Log the Request
        log.info("saveSaleCompatible - Request Received# "+
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
        log.info("saveSaleCompatible - Requested User  # Login Id : "+AuthSession.getUserLoginId() + " - User No: "+AuthSession.getUserNo() + " - IP Address : " + AuthSession.getUserIpAddress());


        // Create the Sale object
        Sale sale= new Sale();

        // Set the merchantNo
        sale.setSalMerchantNo(merchantNo);

        // Set the user location
        sale.setSalLocation(userLocation);

        // Set the user number
        sale.setSalUserNo(userNo);

        // Set the loyalty id
        sale.setSalLoyaltyId(cardNumber);

        // Set the amount
        sale.setSalAmount(amount);

        // SEt the paymentMode
        sale.setSalPaymentMode(paymentMode);

        // Set the date
        sale.setSalDate(date);

        // Set the time
        sale.setSalTime(time);

        // Set the qty
        sale.setSalQuantity(qty);

        // Set the payment reference
        sale.setSalPaymentReference(txnref);

        // Set the transacton channel
        sale.setSalTxnChannel(txnChannel);;

        // Set the currency
        sale.setSalCurrency(0);

        // Hold the audit details
        String auditDetails = AuthSession.getUserNo().toString() + "#" + AuthSession.getUserLoginId();


        // Check the sale
        if (sale.getSalId() == null ) {

            // Set the created by field
            sale.setCreatedBy(auditDetails);

        } else {

            // Set the updated by field
            sale.setUpdatedBy(auditDetails);;

        }

        // Save the sale object
        sale = saleService.saveSale(sale);

        // Check if a auto id was generated
        if ( sale != null && sale.getSalId() != null ) {

            // Call the loyalty processing for the sales.
            saleService.processSaleTransactionForLoyalty(sale);


            // Set the sale id as data
            retData.setData(sale.getSalId());

            //return data
            retData.setBalance(0);

            // Set the status to success
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveSaleCompatible - Response : Unable to save the sale information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
        }


        // Log the response
        log.info("saveSaleCompatible - Response : " + retData.toString());

        // Return the APIResponseObject
        return retData;

    }


/*

    @RequestMapping(value = "/api/0.9/json/merchant/customer/sales/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteSale(@RequestParam(value="salId") Long salId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = AuthSession.getMerchantNo();


        // Log the Request
        log.info("deleteSale - Request Received# - SaleId:"+salId.toString());
        log.info("deleteSale - Requested User  # Login Id : "+AuthSession.getUserLoginId() + " - User No: "+AuthSession.getUserNo() + " - IP Address : " + AuthSession.getUserIpAddress());


        // Get the sale object
        Sale sale = saleService.findBySalId(salId);

        // Check if the saleExists
        if ( sale == null || sale.getSalId() == null ) {

            // Set the log
            log.info("deleteSale - No data found");

            // throw the exception
            throw  new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }



        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( sale.getSalMerchantNo().longValue() != merchantNo ) {

            // Set the log
            log.info("deleteSale - Not authorized");

            // throw the exception
            throw  new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the sale and set the retData fields
        saleService.deleteSale(salId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete brnId
        retData.setData(salId);


        // Set the response
        log.info("deleteSale - Response "+retData.toString());

        // Return the retdata object
        return retData;
    }

    */
    @RequestMapping(value = "/api/0.9/json/merchant/sales/ontest/{filter}/{query}/{prcStartDate}/{prcEndDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject searchSale(
                                            @PathVariable(value="filter") String filter,
                                            @PathVariable(value="query") String query,
                                            @PathVariable(value="prcStartDate") Date salStartDate,
                                            @PathVariable(value="prcEndDate") Date salEndDate,
                                            Pageable pageable) throws ParseException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("searchSale - Request Received#");
        log.info("searchSale - Requested User  # Login Id : "+AuthSession.getUserLoginId() + " - User No: "+AuthSession.getUserNo() + " - IP Address : " + AuthSession.getUserIpAddress());

        //convert start and end date to sql date
//        SimpleDateFormat startDate = new SimpleDateFormat("yyyy-MM-dd");
//        Date salStartDate = (Date) startDate.parse(prcStartDate);
//
//        SimpleDateFormat enddate = new SimpleDateFormat("yyyy-MM-dd");
//        Date salEndDate = (Date) enddate.parse(prcEndDate);




        // Get the Page
        Page<Sale> salePage = saleService.searchSales(filter,query,salStartDate,salEndDate,pageable);

        // Resource list
        List<AttributeExtendedEntityMap> saleResourceList = saleAssembler.toAttibuteEntityMaps(salePage);



        // Set the pageable params
        retData.setPageableParams(salePage);

        // Store the response as data to the retData object
        retData.setData(saleResourceList);

        // Set the data to success
        retData.setStatus(APIResponseStatus.success);


        log.info("searchSale - Response: "+retData.toString());

        // Return the retData
        return retData;

    }




    @RequestMapping(value = "/api/0.9/json/merchant/sale/ontest/{salId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getSaleInfo(@PathVariable(value="salId") Long salId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = AuthSession.getMerchantNo();


        // Log the Request
        log.info("getSaleInfo - Request Received# Purhcase ID "+salId);
        log.info("getSaleInfo - Requested User  # Login Id : "+AuthSession.getUserLoginId() + " - User No: "+AuthSession.getUserNo() + " - IP Address : " + AuthSession.getUserIpAddress());



        // Get the Sale object
        Sale sale = saleService.getSaleInfo(salId);

        // Convert to EntityMap
        AttributeExtendedEntityMap saleResource = saleAssembler.toAttibuteEntityMap(sale);


        // Check the type of the sale
        // If the its sku level, then get the sale sku information
        if ( sale.getSalType() == SaleType.ITEM_BASED_PURCHASE) {

            // Get the sale sku information for the sale id referenced by the
            // internal reference
            Set<SaleSKU>  saleSKUSet = sale.getSaleSKUSet();

            // If the saleSKUSet is not empty , then we need to convert it into resource
            // and add to the transactionResrouce object
            if ( saleSKUSet != null ) {

                // Convert the list to resource
                List<AttributeExtendedEntityMap> saleSKUResourceList = saleSKUAssembler.toAttibuteEntityMaps(saleSKUSet);

                // Add the item to the set
                saleResource.put("saleSKUList",saleSKUResourceList);

            }
        }


        // Store the response as data to the retData object
        retData.setData(saleResource);

        // Set the data to success
        retData.setStatus(APIResponseStatus.success);


        log.info("getSaleInfo - Response: "+retData.toString());

        // Return the retData
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/sale/ontest/save", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject test(@RequestBody Map<String,Object> params) throws InspireNetzException {

        log.info("params" + params.toString());

        List<AttributeExtendedEntityMap> data = (List<AttributeExtendedEntityMap>) params.get("skudata");

        log.info("data "+data);

        return new APIResponseObject();

    }

    @RequestMapping(value = "/api/0.9/xml/sku/salesmaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject readSaleXML(InputStream inputStream) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchantNo
        Long merchantNo = AuthSession.getMerchantNo();

        // Get the location
        Long userLocation = AuthSession.getUserLocation();

        // Log the Request
        log.info("readSaleXML - Request Received# ");
        log.info("readSaleXML - "+generalUtils.getLogTextForRequest());



        try {

            // Set the read value to be 0
            int read = 0;

            // Get the integration filename
            String filename = integrationUtils.getIntegrationFileName(merchantNo, userLocation, "salesmaster", "xml");

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
            salesMasterXMLParser.SalesMasterXMLParser(filename);

            // Set the data to be successful
            retData.setStatus(APIResponseStatus.success);


        } catch(NullPointerException e){

            // TODO throw!
            e.printStackTrace();

            // Set the data to be successful
            retData.setStatus(APIResponseStatus.failed);

            // throw
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

        }
        catch (Exception e) {

            // TODO throw!
            e.printStackTrace();

            // Set the data to be successful
            retData.setStatus(APIResponseStatus.failed);

            // Log the file
            log.info("readSaleXML - Error reading the stream");

            // throw
            throw new InspireNetzException(APIErrorCode.ERR_STREAM_ERROR);

        }

        log.info("readSaleXML - File read successfully");

        // Return the retData object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/transaction/sale/sendebill", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject sendSalesEBill(@RequestBody EBillResource eBillResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("sendSalesEBill - Request Received# "+eBillResource.toString());
        log.info("sendSalesEBill - Requested User  # Login Id : "+authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());

        // Save the sale object
        saleService.sendSalesEBill(eBillResource.getCustomerResource(),eBillResource.getSaleResource(),eBillResource.getAdditionalParams());


        // Set the status as success
        retData.setStatus(APIResponseStatus.success);



        // Log the response
        log.info("sendSalesEBill - Response : " + retData.toString());

        // Return the APIResponseObject
        return retData;

    }

}


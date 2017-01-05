package com.inspirenetz.api.util.integration;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by fayizkci on 4/3/15.
 */
@Component
public class SalesMasterXMLParser extends DefaultHandler {

    @Autowired
    SaleService saleService;

    @Autowired
    SaleSKUService saleSKUService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    SyncStatusService syncStatusService;

    @Autowired
    MerchantLocationService merchantLocationService;

    @Autowired
    private Environment environment;

    // List holding the Sale
    List<Sale> salesData;

    // List holding the Sale for Update
    List<Sale> salesUpdateData;

    // Set for holding the SaleSKU objects
    Set<SaleSKU> saleSKUSet;

    // Variable holding the tmpValue
    String tmpValue;

    // Temporary Sale object
    Sale sale;

    // Temporary SaleSKU object
    SaleSKU saleSKU;

    // Variable holding the xml filename
    String xmlFileName;

    String fileName;


    // no of item per Batch for the current batch of xml being processed
    Integer noPerBatch;

    // Row index for the current processing item
    int rowIndex = 1;

    //Temporary alter status field
    boolean alterStatus=false;

    // create an instance of the logger
    private static Logger log = LoggerFactory.getLogger(SalesMasterXMLParser.class);

    //Variable holding merchant no
    Long merchantNo;

    //Variable holding user no
    Long userNo;

    //Variable holding user location
    Long userLocation;

    //Variable holding audit details
    String auditDetails;;

    public void SalesMasterXMLParser(String xmlFileName) {

        // Set the filename
        this.xmlFileName = xmlFileName;

        //set save sale batch limit
        this.noPerBatch=Integer.parseInt(environment.getProperty("sales.xml.batchSize"));

        //set alterStatus false
        this.alterStatus=false;

        //set merchantNo
        this.merchantNo=authSessionUtils.getMerchantNo();

        //set userNo
        this.userNo=authSessionUtils.getUserNo();

        //set userLocation
        this.userLocation=authSessionUtils.getUserLocation();

        // set audit details
        this.auditDetails = authSessionUtils.getUserNo()+""+authSessionUtils.getUserLoginId();

        // Initialise the salesData list
        salesData = new ArrayList<Sale>();

        // Initialise the salesUpdateData list
        salesUpdateData = new ArrayList<Sale>();

        // Initialise the salesDataSku list
        saleSKUSet = new HashSet<SaleSKU>();

        // call the parse document
        parseDocument();

    }


    /**
     * Function to parse the document
     * Function will create the parser object , get the batch index for the current batch
     * and the call the parse on the document referenced by the xmlFileName
     *
     */
    public void parseDocument() {

        // Create a SAXParserFactory instance
        SAXParserFactory factory = SAXParserFactory.newInstance();

        // Get the today Date
        Date today = new Date(new java.util.Date().getTime());

        File file=new File(this.xmlFileName);
        this.fileName=file.getName();

        //Create Sync Status Object
        SyncStatus syncStatus= new SyncStatus();
        syncStatus.setSysMerchantNo(this.merchantNo);
        syncStatus.setSysLocation(this.userLocation);

        Long lastBatchIndex=syncStatusService.getLastBatchIndex(this.merchantNo, this.userLocation,SyncType.SALES, today);

        syncStatus.setSysBatch(lastBatchIndex!=null?lastBatchIndex+1L:1L);
        syncStatus.setSysDate(today);
        syncStatus.setSysBatchRef(this.fileName);
        syncStatus.setSysType(SyncType.SALES);
        syncStatus.setSysStatus(SyncProcessStatus.ONGOING);
        syncStatus=syncStatusService.saveSyncStatus(syncStatus);

        try {



            // Get the parser
            SAXParser parser = factory.newSAXParser();

            // Call the parse on the parser for the filename
            parser.parse(xmlFileName,this);

            //get size of salesData
            Integer noSalesData=salesData.size();

            //Sub list of salesData List for Batch split
            List<Sale> saleList;

            //To set batchEndIndex for subList
            int batchStartIndex=0;

            //To set batchEndIndex for subList
            int batchEndIndex=0;


            boolean isSaveStatus=true;

            Integer savedCount=0;

            //process new sales
            for(batchStartIndex=0;batchStartIndex<noSalesData;batchStartIndex=batchStartIndex+noPerBatch){

                //set batchEndIndex
                batchEndIndex=(batchStartIndex+noPerBatch);

                //set batchEndIndex as size of saleData if batchEndIndex is greater than its size
                batchEndIndex=batchEndIndex>noSalesData?noSalesData:batchEndIndex;

                saleList=salesData.subList(batchStartIndex,batchEndIndex);

                try{

                    // Save the Sale
                    saleService.saveSalesAll(saleList, auditDetails);

                    savedCount++;

                }catch(InspireNetzException ex){

                    isSaveStatus=false;

                    log.info("SalesMasterXMLParser:- Save  :-batch "+syncStatus.getSysBatch()+" : "+batchStartIndex+" failed");

                    ex.printStackTrace();
                }catch(Exception e){

                    log.info("SalesMasterXMLParser:- Save  :-batch  sale:"+e);

                    e.printStackTrace();

                }


            }

            if(isSaveStatus){

                syncStatus.setSysStatus(SyncProcessStatus.COMPLETED);

            }else if(!isSaveStatus && savedCount>0){

                syncStatus.setSysStatus(SyncProcessStatus.PARTIALLY_COMPLETED);

            }else{

                syncStatus.setSysStatus(SyncProcessStatus.FAILED);

            }

            syncStatus=syncStatusService.saveSyncStatus(syncStatus);

        }catch(NullPointerException e){

            syncStatus.setSysStatus(SyncProcessStatus.FAILED);

            syncStatus=syncStatusService.saveSyncStatus(syncStatus);

            log.error("Parser Config Error " + xmlFileName);


            e.printStackTrace();

            throw e;
        }
        catch (ParserConfigurationException e) {

            syncStatus.setSysStatus(SyncProcessStatus.FAILED);

            syncStatus=syncStatusService.saveSyncStatus(syncStatus);

            log.error("Parser Config Error " + xmlFileName);
            e.printStackTrace();

        } catch (SAXException e) {

            syncStatus.setSysStatus(SyncProcessStatus.FAILED);

            syncStatus=syncStatusService.saveSyncStatus(syncStatus);

            log.error("SAX Exception : XML not well formed " + xmlFileName);
            e.printStackTrace();

        } catch (IOException e) {

            syncStatus.setSysStatus(SyncProcessStatus.FAILED);

            syncStatus=syncStatusService.saveSyncStatus(syncStatus);

            log.error("IO Error " + xmlFileName);
            e.printStackTrace();

        }catch(Exception e){

            syncStatus.setSysStatus(SyncProcessStatus.FAILED);

            syncStatus=syncStatusService.saveSyncStatus(syncStatus);

            log.error(e.toString() + xmlFileName);
            e.printStackTrace();

        }

    }


    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {


        // If the starting element is SALE tag then we need to
        // create a Sale object
        if (elementName.equalsIgnoreCase("sale")) {

            //make alter status false
            alterStatus=false;

            // Create the object
            sale = new Sale();

            saleSKUSet=new HashSet<SaleSKU>();

            //get merchantNo
            Long merchantNo=this.merchantNo;

            //get userNo
            Long userNo=this.userNo;

            //get userLocation
            Long userLocation=this.userLocation;

            // Hold the audit details
            String auditDetails = this.auditDetails;

            try{

                // Set the alter status
                alterStatus=(Integer.parseInt(attributes.getValue("ALTER_STATUS")))==0?false:true;

            }catch(Exception ex){

                alterStatus=false;
            }

            // Set the merchantNo
            sale.setSalMerchantNo(merchantNo);

            // Set the user location
            sale.setSalLocation(userLocation);

            // Set the user number
            sale.setSalUserNo(userNo);

            if(alterStatus){

                //Set the updated by field
                sale.setUpdatedBy(auditDetails);

            }else{

                // Set the created by field
                sale.setCreatedBy(auditDetails);

            }

            // Set the loyatly id
            sale.setSalLoyaltyId(attributes.getValue("LOYALTY_ID")==null?"":attributes.getValue("LOYALTY_ID"));


            try{

                // Set the sale date
                sale.setSalDate(DBUtils.covertToSqlDate(attributes.getValue("DATE")));

            }catch(Exception ex){

                // Log the file
                log.info("SalesMasterXMLParser - No DATE attribute");

                throw new NullPointerException();

            }

            try{

                // Set the sale time
                sale.setSalTime(DBUtils.convertToSqlTime(attributes.getValue("TIME")));

            }catch(Exception ex){

                // Log the file
                log.info("SalesMasterXMLParser - No TIME attribute");


            }

            try{

                // Set the sale amount
                sale.setSalAmount(Double.parseDouble(attributes.getValue("TOTAL_AMOUNT")));

            }catch(Exception ex){


                // Log the file
                log.info("SalesMasterXMLParser - No TOTAL_AMOUNT attribute");

                sale.setSalAmount(0.0);

                //throw new NullPointerException();
            }

            try{

                // Set the reference
                sale.setSalPaymentReference(attributes.getValue("REFERENCE"));


            }catch(Exception ex){

                // Log the file
                log.info("SalesMasterXMLParser - No REFERENCE attribute");

                throw new NullPointerException();
            }

            try{

                // Set the reference
                String location = attributes.getValue("PURCHASE_LOCATION") ==null?"":attributes.getValue("PURCHASE_LOCATION");

                if(!location.equals("")){

                    Long customerLocation = getLocation(location.trim());

                    if(customerLocation.longValue() !=0L){

                        sale.setSalLocation(customerLocation);

                    }else {

                        sale.setSalLocation(userLocation);
                    }

                }else {

                    sale.setSalLocation(userLocation);
                }


            }catch(Exception ex){

                // Log the file
                log.info("SalesMasterXMLParser - Pue");

                sale.setSalLocation(userLocation);

            }

            String paymentMode=attributes.getValue("PAYMENT_MODE")==null?"":attributes.getValue("PAYMENT_MODE");

            // Set the Payment Mode
            switch (paymentMode){

                case "CHARGE_CARD"  :

                    sale.setSalPaymentMode(PaymentStatusMode.CHARGE_CARD);

                    break;

                case "CASHBACK_POINTS"  :

                    sale.setSalPaymentMode(PaymentStatusMode.CASHBACK_POINTS);

                    break;

                case "CREDIT_CARD"  :

                    sale.setSalPaymentMode(PaymentStatusMode.CREDIT_CARD);

                    break;

                case "DEBIT_CARD"   :

                    sale.setSalPaymentMode(PaymentStatusMode.DEBIT_CARD);

                    break;

                case "NET_BANKING"  :

                    sale.setSalPaymentMode(PaymentStatusMode.NET_BANKING);

                    break;

                case "CASH" :

                    sale.setSalPaymentMode(PaymentStatusMode.CASH);

                    break;
                default:
                    break;
            }

        }


        // Check if the element is starting with SALE_ITEM, then its sku value
        if ( elementName.equalsIgnoreCase("SALE_ITEM")) {

            // Set the type of the sale to sku based
            sale.setSalType(SaleType.ITEM_BASED_PURCHASE);

            // Create the object
            saleSKU = new SaleSKU();

        }
        tmpValue="";

    }

    private Long getLocation(String tmpValue) {

        MerchantLocation merchantLocation =merchantLocationService.findByMelMerchantNoAndMelLocation(merchantNo,tmpValue==null?"":tmpValue);

        if(merchantLocation !=null){

            return merchantLocation.getMelId();

        }

        return 0L;
    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
        // If the element /SALE
        // if end of sale element add to list
        if (element.equalsIgnoreCase("sale")) {

            //Add SaleSKU to Sale object
            sale.setSaleSKUSet(saleSKUSet);

            //check alterStatus and choose list

            /*if(alterStatus){

                // Add to the list
                salesUpdateData.add(sale);

            }else{*/

                // Add to the list

               //check the sale location null or zero
               try {

                   if(sale !=null && (sale.getSalLocation() ==null || sale.getSalLocation().longValue() ==0L)){

                       sale.setSalLocation(userLocation);
                   }
               }catch (Exception e){

                   log.info("Exception"+e);

               }


                salesData.add(sale);
/*
            }*/

            saleSKUSet=null;
        }


        // Check if the element is starting with the ITEM_CODE
        if ( element.equalsIgnoreCase("ITEM_CODE")) {

            // Set the value read
            saleSKU.setSsuProductCode(tmpValue);

        }

        // Check if the element is starting with the ITEM_QTY
        if ( element.equalsIgnoreCase("ITEM_QTY")) {

            // Set the value read
            saleSKU.setSsuQty(Double.parseDouble(tmpValue.equals("")?"0":tmpValue));

        }


        // Check if the element is starting with the ITEM_PRICE
        if ( element.equalsIgnoreCase("ITEM_PRICE")) {

            // Set the value read
            saleSKU.setSsuPrice(Double.parseDouble(tmpValue.equals("")?"0":tmpValue));

        }

        // Check if the element is starting with the ITEM_DISCOUNT
        if ( element.equalsIgnoreCase("ITEM_DISCOUNT")) {

            // Set the value read
            saleSKU.setSsuDiscountPercent(Double.parseDouble(tmpValue.equals("")?"0":tmpValue));

        }




        // If the ending elenment is SALE_ITEM, then we need to add the saleSKU to the list
        if ( element.equalsIgnoreCase("SALE_ITEM")) {

            saleSKUSet.add(saleSKU);

            saleSKU=null;

        }



    }


    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {

        tmpValue = new String(ac, i, j);

    }

}

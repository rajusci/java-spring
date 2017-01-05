package com.inspirenetz.api.util.integration;

import com.inspirenetz.api.core.dictionary.SaleType;
import com.inspirenetz.api.core.domain.SalesMasterRawdata;
import com.inspirenetz.api.core.domain.SalesMasterSkuRawdata;
import com.inspirenetz.api.core.service.SalesMasterRawdataService;
import com.inspirenetz.api.core.service.SalesMasterSkuRawdataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 27/4/14.
 */
public class SalesDataXMLParser extends DefaultHandler {

    // List holding the SalesMasterRawData
    List<SalesMasterRawdata> salesData;

    // List holding the SalesMasterSkuRawdata
    List<SalesMasterSkuRawdata> salesDataSku;

    // Variable holding the tmpValue
    String tmpValue;

    // Temporary SalesMasterRawdata object
    SalesMasterRawdata salesMasterRawdata;

    // Temporary SalesMasterSkuRawdata object
    SalesMasterSkuRawdata salesMasterSkuRawdata;

    // Variable holding the xml filename
    String xmlFileName;

    // BatchIndex for the current batch of xml being processed
    Long batchIndex;

    // Row index for the current processing item
    int rowIndex = 1;

    // create an instance of the logger
    private static Logger log = LoggerFactory.getLogger(SalesDataXMLParser.class);

    // Define the date format
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Define the time format
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    // The SalesMasterRawDataService
    SalesMasterRawdataService salesMasterRawdataService;

    // The SalesMasterSkuRawdataService
    SalesMasterSkuRawdataService salesMasterSkuRawdataService;




    public SalesDataXMLParser(String xmlFileName,SalesMasterRawdataService salesMasterRawdataService,SalesMasterSkuRawdataService salesMasterSkuRawdataService ) {

        // Set the filename
        this.xmlFileName = xmlFileName;

        // Initialise the salesData list
        salesData = new ArrayList<SalesMasterRawdata>();

        // Initialise the salesDataSku list
        salesDataSku = new ArrayList<SalesMasterSkuRawdata>();

        // set the SalesMasterRawdataService informaiton
        this.salesMasterRawdataService = salesMasterRawdataService;

        // Set the SalesMasterSkuRawdataService information
        this.salesMasterSkuRawdataService = salesMasterSkuRawdataService;

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

        try {

            // Get the batchIndex
            batchIndex = salesMasterRawdataService.getNextBatchIndex();

            // If the batchIndex is null, then we need to log the error
            // and exit
            if ( batchIndex == null || batchIndex == 0L) {

                // Log the error
                log.error("Batch Index generation failed : " + xmlFileName);

                // return the control
                return;
            }

            // Get the parser
            SAXParser parser = factory.newSAXParser();

            // Call the parse on the parser for the filename
            parser.parse(xmlFileName,this);

            // Save the SalesMasterRawData
            salesMasterRawdataService.saveAll(salesData);

            // Save the SalesMasterSkuRawdata
            salesMasterSkuRawdataService.saveAll(salesDataSku);


        } catch (ParserConfigurationException e) {

            log.error("Parser Config Error " + xmlFileName);
            e.printStackTrace();

        } catch (SAXException e) {

            log.error("SAX Exception : XML not well formed " + xmlFileName);
            e.printStackTrace();

        } catch (IOException e) {

            log.error("IO Error " + xmlFileName);
            e.printStackTrace();

        }

    }


    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {


        // If the starting element is SALE tag then we need to
        // create a SalesMasterRawdata object
        if (elementName.equalsIgnoreCase("sale")) {

            // Create the object
            salesMasterRawdata = new SalesMasterRawdata();

            // set the batchIndex
            salesMasterRawdata.setSmrBatchIndex(batchIndex);

            // Set the rowIndex
            salesMasterRawdata.setSmrRowindex(rowIndex);

            // Set the loyatly id
            salesMasterRawdata.setSmrLoyaltyId(attributes.getValue("LOYALTY_ID"));

            // Set the amount
            salesMasterRawdata.setSmrAmount(Double.parseDouble(attributes.getValue("TOTAL_AMOUNT")));

            // Set the reference
            salesMasterRawdata.setSmrPaymentReference(attributes.getValue("REFERENCE"));

            // Set the alter status
            salesMasterRawdata.setSmrAlterStatus(Integer.parseInt(attributes.getValue("ALTER_STATUS")));

            // Set the date
            try {

                // Get the data in java.util.date
                java.util.Date utilDate = dateFormat.parse(attributes.getValue("DATE"));

                // Set the sql date
                salesMasterRawdata.setSmrDate(new Date(utilDate.getTime()));

            } catch (ParseException e) {

                // On exception we need to report the error
                // TODO
            }


            // Set the time
            try {

                // Get the data in java.util.date
                java.util.Date utilTime = timeFormat.parse(attributes.getValue("TIME"));

                // Set the sql date
                salesMasterRawdata.setSmrTime(new Time(utilTime.getTime()));

            } catch (ParseException e) {

                // On exception we need to report the error
                // TODO
            }

        }


        // Check if the element is starting with SALE_ITEM, then its sku value
        if ( elementName.equalsIgnoreCase("SALE_ITEM")) {

            // Set the type of the salesMasterRawData to sku based
            salesMasterRawdata.setSmrType(SaleType.ITEM_BASED_PURCHASE);

            // Create the object
            salesMasterSkuRawdata = new SalesMasterSkuRawdata();

            // Set the batchIndex
            salesMasterSkuRawdata.setSmuParentBatchIndex(batchIndex);

            // Set the rowIndex
            salesMasterSkuRawdata.setSmuParentRowIndex(rowIndex);

        }


    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
// If the elemne
        // if end of sale element add to list
        if (element.equalsIgnoreCase("sale")) {

            // Add to the list
            salesData.add(salesMasterRawdata);

            // Increment the rowIndex
            rowIndex++;

        }


        // Check if the element is starting with the ITEM_CODE
        if ( element.equalsIgnoreCase("ITEM_CODE")) {

            // Set the value read
            salesMasterSkuRawdata.setSmuItemCode(tmpValue);

        }

        // Check if the element is starting with the ITEM_QTY
        if ( element.equalsIgnoreCase("ITEM_QTY")) {

            // Set the value read
            salesMasterSkuRawdata.setSmuItemQty(Double.parseDouble(tmpValue));

        }


        // Check if the element is starting with the ITEM_PRICE
        if ( element.equalsIgnoreCase("ITEM_PRICE")) {

            // Set the value read
            salesMasterSkuRawdata.setSmuItemPrice(Double.parseDouble(tmpValue));

        }

        // Check if the element is starting with the ITEM_DISCOUNT
        if ( element.equalsIgnoreCase("ITEM_DISCOUNT")) {

            // Set the value read
            salesMasterSkuRawdata.setSmuItemDiscountPercent(Double.parseDouble(tmpValue));

        }


        // If the ending elenment is SALE_ITEM, then we need to add the salesmasterskurawdata to the list
        if ( element.equalsIgnoreCase("SALE_ITEM")) {

            salesDataSku.add(salesMasterSkuRawdata);

        }



    }


    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {

        tmpValue = new String(ac, i, j);

    }

}

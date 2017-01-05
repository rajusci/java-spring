package com.inspirenetz.api.util.integration;

import com.inspirenetz.api.core.dictionary.SyncProcessStatus;
import com.inspirenetz.api.core.dictionary.SyncType;
import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.core.domain.ProductCategory;
import com.inspirenetz.api.core.domain.SyncStatus;
import com.inspirenetz.api.core.service.BrandService;
import com.inspirenetz.api.core.service.ProductCategoryService;
import com.inspirenetz.api.core.service.ProductService;
import com.inspirenetz.api.core.service.SyncStatusService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fayizkci on 4/3/15.
 */
@Component
public class ItemMasterXMLParser extends DefaultHandler {

    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;
    
    @Autowired
    SyncStatusService syncStatusService;

    @Autowired
    private Environment environment;

    // List holding the Product
    List<Product> productList;

    // List holding the Brand
    List<Brand> brandList;

    // List holding the Category
    List<ProductCategory> productCategoryList;



    // Variable holding the tmpValue
    String tmpValue;

    // Temporary Product object
    Product product;

    // Temporary Brand object
    Brand brand;

    // Temporary Product Category object
    ProductCategory productCategory;


    // Variable holding the xml filename
    String xmlFileName;

    String fileName;

    // Variable holding the item type
    String itemType;

    //Variable holding merchant no
    Long merchantNo;

    //Variable holding user no
    Long userNo;

    //Variable holding user location
    Long userLocation;

    //Variable holding audit details
    String auditDetails;


    // no of item per Batch for the current batch of xml being processed
    Integer noPerBatch;

    // Row index for the current processing item
    int rowIndex = 1;

    //Temporary alter status field
    boolean alterStatus=false;

    // create an instance of the logger
    private static Logger log = LoggerFactory.getLogger(ItemMasterXMLParser.class);


    public void ItemMasterXMLParser(String xmlFileName) {

        // Set the filename
        this.xmlFileName = xmlFileName;

        //set save product batch limit
        this.noPerBatch=Integer.parseInt(environment.getProperty("item.xml.batchSize"));

        //set alterStatus false
        this.alterStatus=false;

        //set merchantNo
        this.merchantNo=authSessionUtils.getMerchantNo();

        //set userNo
        this.userNo=authSessionUtils.getUserNo();

        //set userLocation
        this.userLocation=authSessionUtils.getUserLocation();

        // set audit details
        this.auditDetails = authSessionUtils.getUserNo().toString();

        // Initialise the productList list
        productList = new ArrayList<Product>();

        // Initialise the brandList list
        brandList = new ArrayList<Brand>();

        // Initialise the productCategoryList list
        productCategoryList = new ArrayList<ProductCategory>();

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

        Long lastBatchIndex=syncStatusService.getLastBatchIndex(this.merchantNo, this.userLocation, SyncType.ITEMS, today);

        syncStatus.setSysBatch(lastBatchIndex!=null?lastBatchIndex+1L:1L);
        syncStatus.setSysDate(today);
        syncStatus.setSysBatchRef(this.fileName);
        syncStatus.setSysType(SyncType.ITEMS);
        syncStatus.setSysStatus(SyncProcessStatus.ONGOING);
        syncStatus=syncStatusService.saveSyncStatus(syncStatus);


        try {

            // Get the parser
            SAXParser parser = factory.newSAXParser();

            // Call the parse on the parser for the filename
            parser.parse(xmlFileName,this);

            Integer noProductData=0;

            //To set batchEndIndex for subList
            int batchStartIndex=0;

            //To set batchEndIndex for subList
            int batchEndIndex=0;

            //get size of brandList
            noProductData=brandList.size();

            //Sub list of productList List for Batch split
            List<Brand> brandSubList;

            boolean isSaveStatus=true;

            Integer savedCount=0;


            //process new sales
            for(batchStartIndex=0;batchStartIndex<noProductData;batchStartIndex=batchStartIndex+noPerBatch){

                //set batchEndIndex
                batchEndIndex=(batchStartIndex+noPerBatch);

                //set batchEndIndex as size of productList if batchEndIndex is greater than its size
                batchEndIndex=batchEndIndex>noProductData?noProductData:batchEndIndex;

                brandSubList=brandList.subList(batchStartIndex,batchEndIndex);


                for(Brand brandToSave : brandSubList){

                    try{

                        brandToSave=brandService.saveBrand(brandToSave);

                        savedCount++;

                    }catch(DataIntegrityViolationException ex){

                        savedCount++;

                        log.info("ItemMasterXMLParser:- Brand Save  :-batch "+syncStatus.getSysBatch()+" : "+batchStartIndex+" failed");


                        log.info("ItemMasterXMLParser:-Brand Save Failed for "+brandToSave);

                        ex.printStackTrace();

                    }catch (Exception e){

                        isSaveStatus=false;

                        log.info("ItemMasterXMLParser:- Brand Save  :-batch "+syncStatus.getSysBatch()+" : "+batchStartIndex+" failed");


                        log.info("ItemMasterXMLParser:-Brand Save Failed for "+brandToSave);

                        e.printStackTrace();
                    }
                }


            }

            //get size of brandList
            noProductData=productCategoryList.size();

            //Sub list of productList List for Batch split
            List<ProductCategory> productCategorySubList;

            //process new sales
            for(batchStartIndex=0;batchStartIndex<noProductData;batchStartIndex=batchStartIndex+noPerBatch){

                //set batchEndIndex
                batchEndIndex=(batchStartIndex+noPerBatch);

                //set batchEndIndex as size of productList if batchEndIndex is greater than its size
                batchEndIndex=batchEndIndex>noProductData?noProductData:batchEndIndex;

                productCategorySubList=productCategoryList.subList(batchStartIndex,batchEndIndex);

                for(ProductCategory categoryToSave : productCategorySubList){

                    try{

                        categoryToSave=productCategoryService.saveProductCategory(categoryToSave);

                        savedCount++;

                    }catch(DataIntegrityViolationException ex){

                        savedCount++;

                        log.info("ItemMasterXMLParser:- Product Category Save  :-batch "+syncStatus.getSysBatch()+" : "+batchStartIndex+" failed");


                        log.info("ItemMasterXMLParser:-Product Category Save Failed for "+categoryToSave);

                        ex.printStackTrace();

                    }catch (Exception e){

                        isSaveStatus=false;

                        log.info("ItemMasterXMLParser:- Product Category Save  :-batch "+syncStatus.getSysBatch()+" : "+batchStartIndex+" failed");


                        log.info("ItemMasterXMLParser:-Product Category Save Failed for "+categoryToSave);

                        e.printStackTrace();
                    }
                }

            }

            //get size of productList
            noProductData=productList.size();

            //Sub list of productList List for Batch split
            List<Product> productSubList;

            //process new sales
            for(batchStartIndex=0;batchStartIndex<noProductData;batchStartIndex=batchStartIndex+noPerBatch){

                //set batchEndIndex
                batchEndIndex=(batchStartIndex+noPerBatch);

                //set batchEndIndex as size of productList if batchEndIndex is greater than its size
                batchEndIndex=batchEndIndex>noProductData?noProductData:batchEndIndex;

                productSubList=productList.subList(batchStartIndex,batchEndIndex);



                for(Product productToSave : productSubList){

                    try{

                        productToSave=productService.saveProduct(productToSave);
                        savedCount++;

                    }catch(DataIntegrityViolationException ex){

                        savedCount++;

                        log.info("ItemMasterXMLParser:- Product Save  :-batch "+syncStatus.getSysBatch()+" : "+batchStartIndex+" failed");


                        log.info("ItemMasterXMLParser:-Product  Save Failed for "+productToSave);

                        ex.printStackTrace();

                    }catch (Exception e){

                        isSaveStatus=false;

                        log.info("ItemMasterXMLParser:- Product Save  :-batch "+syncStatus.getSysBatch()+" : "+batchStartIndex+" failed");


                        log.info("ItemMasterXMLParser:-Product Save Failed for "+productToSave);

                        e.printStackTrace();
                    }
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






        } catch (ParserConfigurationException e) {

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

        }

    }


    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {


        // If the starting element is ITEM tag then we need to
        // create a Product object
        if (elementName.equalsIgnoreCase("item")) {

            //Set itemType empty
            itemType="";

        }

        tmpValue="";

    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
        // If the element is ITEM end tag
        // if end of item element add to list
        if (element.equalsIgnoreCase("item")) {


            //if Product object not null add to product list
            if(product!=null){

                //set merchant no
                product.setPrdMerchantNo(merchantNo);

                //set location
                product.setPrdLocation(userLocation);

                //set created user details
                product.setCreatedBy(auditDetails);

                //Add to product list
                productList.add(product);

                product=null;

            }

            //if brand not null add to brandList
            if (brand!=null){

                //set merchant no
                brand.setBrnMerchantNo(merchantNo);

                //set created user details
                brand.setCreatedBy(auditDetails);

                //Add to brandList
                brandList.add(brand);

                brand=null;

            }

            //if product category not null add to productCategoryList
            if(productCategory!=null){

                //set merchant no
                productCategory.setPcyMerchantNo(merchantNo);

                //set created user details
                productCategory.setCreatedBy(auditDetails);

                //Add to productCategoryList
                productCategoryList.add(productCategory);

                productCategory=null;

            }


        }


        // Check if the element is starting with the TYPE
        if ( element.equalsIgnoreCase("TYPE")) {

            //check if type PRODUCT
            if(tmpValue.equalsIgnoreCase("PRODUCT")){

                //create Product object
                product = new Product();


            }else if(tmpValue.equalsIgnoreCase("BRAND")){

                //Create Brand object
                brand=new Brand();

            }else if (tmpValue.equalsIgnoreCase("CATEGORY")){

                //Create ProductCategory object
                productCategory = new ProductCategory();

            }

            itemType=tmpValue;

        }

        // Check if the element is starting with the CODE
        if ( element.equalsIgnoreCase("CODE")) {

            SetItemPropertyByType(itemType,element.toUpperCase(),tmpValue);

        }
        // Check if the element is starting with the NAME
        if ( element.equalsIgnoreCase("NAME")) {

            // Set the value read
            SetItemPropertyByType(itemType, element.toUpperCase(), tmpValue);

        }
        // Check if the element is starting with the DESCRIPTION
        if ( element.equalsIgnoreCase("DESCRIPTION")) {

            // Set the value read
            SetItemPropertyByType(itemType, element.toUpperCase(), tmpValue);

        }

        // Check if the element is starting with the BRAND_CODE
        if ( element.equalsIgnoreCase("BRAND_CODE")) {

            // Set the value read
            SetItemPropertyByType(itemType, element.toUpperCase(), tmpValue);

        }
        // Check if the element is starting with the CATEGORY1_CODE
        if ( element.equalsIgnoreCase("CATEGORY1_CODE")) {

            // Set the value read
            SetItemPropertyByType(itemType, element.toUpperCase(), tmpValue);

        }

        // Check if the element is starting with the CATEGORY2_CODE
        if ( element.equalsIgnoreCase("CATEGORY2_CODE")) {

            // Set the value read
            SetItemPropertyByType(itemType, element.toUpperCase(), tmpValue);

        }

        // Check if the element is starting with the CATEGORY3_CODE
        if ( element.equalsIgnoreCase("CATEGORY3_CODE")) {

            // Set the value read
            SetItemPropertyByType(itemType, element.toUpperCase(), tmpValue);

        }



        // Check if the element is starting with the PURCHASE_PRICE
        if ( element.equalsIgnoreCase("PURCHASE_PRICE")) {

            // Set the value read
            SetItemPropertyByType(itemType, element.toUpperCase(), tmpValue);

        }

        // Check if the element is starting with the SALE_PRICE
        if ( element.equalsIgnoreCase("SALE_PRICE")) {

            // Set the value read
            SetItemPropertyByType(itemType, element.toUpperCase(), tmpValue);

        }


        // If the ending elenment is SALE_ITEM, then we need to add the salesmasterskurawdata to the list
        if ( element.equalsIgnoreCase("STOCK_QUANTITY")) {

            //salesDataSku.add(salesMasterSkuRawdata);
            SetItemPropertyByType(itemType, element.toUpperCase(), tmpValue);

        }


    }

    public void SetItemPropertyByType(String itemType,String property,String value ){

        //Property set for Product object
        if(itemType.equalsIgnoreCase("PRODUCT")){

            switch (property){

                case "CODE" :

                    product.setPrdCode(value);

                    break;

                case "NAME" :

                    product.setPrdName(value);

                    break;

                case "DESCRIPTION" :

                    product.setPrdDescription(value);

                    break;

                case "BRAND_CODE" :

                    product.setPrdBrand(value);

                    break;

                case "CATEGORY1_CODE" :

                    product.setPrdCategory1(value);

                    break;

                case "CATEGORY2_CODE" :

                    product.setPrdCategory2(value);

                    break;

                case "CATEGORY3_CODE" :

                    product.setPrdCategory3(value);

                    break;

                case "PURCHASE_PRICE" :

                    product.setPrdPurchasePrice(Double.parseDouble(value.equals("")?"0":value));

                    break;

                case "SALE_PRICE" :

                    product.setPrdSalePrice(Double.parseDouble(value.equals("")?"0":value));

                    break;

                case "STOCK_QUANTITY" :

                    product.setPrdStockQuantity(Double.parseDouble(value.equals("")?"0":value));

                    break;



            }



        }else if(itemType.equalsIgnoreCase("BRAND")){

            switch (property){

                case "CODE" :

                    brand.setBrnCode(value);

                    break;

                case "NAME" :

                    brand.setBrnName(value);

                    break;

                case "DESCRIPTION" :

                    brand.setBrnDescription(value);

                    break;

            }


        }else if(itemType.equalsIgnoreCase("CATEGORY")){

            switch (property){

                case "CODE" :

                    productCategory.setPcyCode(value);

                    break;

                case "NAME" :

                    productCategory.setPcyName(value);

                    break;

                case "DESCRIPTION" :

                    productCategory.setPcyDescription(value);

                    break;

            }

        }
    }


    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {

        tmpValue = new String(ac, i, j);

    }

}

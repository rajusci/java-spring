package com.inspirenetz.api.rest.controller;

import com.google.common.io.CountingOutputStream;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.core.service.ProductService;
import com.inspirenetz.api.rest.assembler.ProductAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.ProductResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import com.inspirenetz.api.util.integration.IntegrationUtils;
import com.inspirenetz.api.util.integration.ItemMasterXMLParser;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductAssembler productAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    IntegrationUtils integrationUtils;

    @Autowired
    ItemMasterXMLParser itemMasterXMLParser;


    @RequestMapping(value = "/api/0.9/json/merchant/sku/product", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveProduct(@Valid Product product,BindingResult result) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the product
        product.setPrdMerchantNo(merchantNo);


        // Log the Request
        log.info("saveProduct - Request Received# "+product.toString());
        log.info("saveProduct - "+generalUtils.getLogTextForRequest());



        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveProduct - Response : Invalid Input");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

        }

        // Check if the product is existing
        boolean isExist = productService.isProductCodeDuplicateExisting(product);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveProduct - Response : Product code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }


        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // If the product.getPrdId is  null, then set the created_by, else set the updated_by
        if ( product.getPrdId() == null ) {

            product.setCreatedBy(auditDetails);

        } else {

            product.setUpdatedBy(auditDetails);

        }

        // save the product object and get the result
        product = productService.validateAndSaveProduct(product);

        // If the product object is not null ,then return the success object
        if ( product.getPrdId() != null ) {

            // Get the product id
            retData.setData(product.getPrdId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveProduct - Response : Unable to save the product information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // Log the response
        log.info("saveProduct -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/sku/product/delete/{prdId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteProduct(@PathVariable(value="prdId") Long prdId) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteProduct - Request Received# Product ID: "+prdId);
        log.info("deleteProduct - "+generalUtils.getLogTextForRequest());

        // Get the product information
        Product product = productService.findByPrdId(prdId);

        // Check if the product is found
        if ( product == null || product.getPrdId() == null) {

            // Log the response
            log.info("deleteProduct - Response : No product information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( product.getPrdMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteProduct - Response : You are not authorized to delete the product");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the product and set the retData fields
        productService.validateAndDeleteProduct(prdId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete prdId
        retData.setData(prdId);


        // Log the response
        log.info("deleteProduct -  " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;



    }



    @RequestMapping(value = "/api/0.9/json/merchant/sku/products/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listProducts(
                                            @PathVariable(value ="filter") String filter,
                                            @PathVariable(value ="query") String query,
                                            Pageable pageable){


        // Log the Request
        log.info("listProducts - Request Received filter "+ filter +" query :" +query);
        log.info("listProducts - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        //get the user location
        Long userLocation = authSessionUtils.getUserLocation();

        //if user is merchant admin then set location as 0
        if(authSessionUtils.getUserType() == UserType.MERCHANT_ADMIN){

            userLocation = 0L;
        }

        // Variable storing the product ResourceList
        List<ProductResource> productResourceList = new ArrayList<>(0);

        // Check the filter type
        if ( filter.equals("0") && query.equals("0") ) {

            // Get the page
            Page<Product> productPage = productService.findByPrdMerchantNoAndPrdLocation(merchantNo,userLocation, pageable);

            // Convert to Resource
            productResourceList = productAssembler.toResources(productPage);

            // Set the pageable params for the retData
            retData.setPageableParams(productPage);


        } else if ( filter.equalsIgnoreCase("code") ) {

            Page<Product> productPage = productService.findByPrdMerchantNoAndPrdLocationAndPrdCodeLike(merchantNo,userLocation,"%" +query+"%",pageable);

            // Get the resource list
            productResourceList = productAssembler.toResources(productPage);

            // Set the pageable params for the retData
            retData.setPageableParams(productPage);


        } else if ( filter.equalsIgnoreCase("name")) {

            // Get the productPage
            Page<Product> productPage = productService.findByPrdMerchantNoAndPrdLocationAndPrdNameLike(merchantNo, userLocation, "%" + query + "%", pageable);

            // Get the resource list
            productResourceList = productAssembler.toResources(productPage);

            // Set the pageable params for the retData
            retData.setPageableParams(productPage);

        }




        // Set the data
        retData.setData(productResourceList);

        // Log the response
        log.info("listProducts -  " + generalUtils.getLogTextForResponse(retData));



        // Return the success object
        return retData;


    }

    
    @RequestMapping(value = "/api/0.9/json/merchant/sku/product/{prdId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getProductInfo(@PathVariable(value="prdId") Long prdId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getProductInfo - Request Received# Product ID: "+prdId);
        log.info("getProductInfo - "+generalUtils.getLogTextForRequest());

        // Get the product information
        Product product = productService.findByPrdId(prdId);

        // Check if the product is found
        if ( product == null || product.getPrdId() == null) {

            // Log the response
            log.info("getProductInfo - Response : No product information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( product.getPrdMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getProductInfo - Response : You are not authorized to view the product");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }




        // Convert the Product to ProductResource
        ProductResource productResource = productAssembler.toResource(product);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the productResource object
        retData.setData(productResource);




        // Log the response
        log.info("getProductInfo -  " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/xml/sku/itemmaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject readItemXML(InputStream inputStream) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the location
        Long userLocation = authSessionUtils.getUserLocation();

        // Log the Request
        log.info("readItemXML - Request Received# ");
        log.info("readItemXML - "+generalUtils.getLogTextForRequest());



        try {

            // Set the read value to be 0
            int read = 0;

            // Get the integration filename
            String filename = integrationUtils.getIntegrationFileName(merchantNo, userLocation, "itemmaster", "xml");

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
            itemMasterXMLParser.ItemMasterXMLParser(filename);

            // Set the data to be successful
            retData.setStatus(APIResponseStatus.success);


        } catch (Exception e) {

            // TODO throw!
            e.printStackTrace();

            // Log the file
            log.info("readItemXML - Error reading the stream");

            // throw
            throw new InspireNetzException(APIErrorCode.ERR_STREAM_ERROR);

        }

        log.info("readItemXML - File read successfully");

        // Return the retData object
        return retData;

    }

    /**
     * @purpost:to get the list of product skip pagination  its helpful for display product in select box
     * @return
     */
    @RequestMapping(value = "/api/0.9/json/merchant/sku/getlistproducts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getProductListInDropDown(){


        // Log the Request
        log.info("getProductListInDropDown - Request Received filter ");
        log.info("getProductListInDropDown - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        //get the user location
        Long userLocation = authSessionUtils.getUserLocation();

        //if user is merchant admin then set location as 0
        if(authSessionUtils.getUserType() == UserType.MERCHANT_ADMIN){

            userLocation = 0L;
        }

        // Variable storing the product ResourceList
        List<ProductResource> productResourceList = new ArrayList<>(0);

        // Get the page
        List<Product> productPage = productService.findByPrdMerchantNoAndPrdLocation(merchantNo,userLocation);
        // Convert to Resource
        productResourceList = productAssembler.toResources(productPage);

        // Set the data
        retData.setData(productResourceList);

        // Log the response
        log.info("getProductListInDropDown -  " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/customer/sku/products/{merchantno}/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getProductDetails(
            @PathVariable(value ="merchantno") Long merchantNo,@PathVariable("filter")

        String filter,@PathVariable("query") String query,Pageable pageable) throws InspireNetzException {

        // Log the Request
        log.info("getProductDetails - Request Received merchant No "+merchantNo);
        log.info("getProductDetails - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Variable storing the product ResourceList
        List<ProductResource> productResourceList = new ArrayList<>(0);

        // Get the page
        Page<Product> productPage = productService.listProductInCustomer(merchantNo,filter,query,pageable);

        // Convert to Resource
        productResourceList = productAssembler.toResources(productPage);

        //set return data
        retData.setStatus(APIResponseStatus.success);

        //set data
        retData.setData(productResourceList);


        // Log the response
        log.info("getProductDetails -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


}

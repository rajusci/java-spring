package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.ProductCategory;
import com.inspirenetz.api.core.service.ProductCategoryService;
import com.inspirenetz.api.rest.assembler.ProductCategoryAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.ProductCategoryResource;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 */
@Controller
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductCategoryAssembler productCategoryAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(BrandController.class);



    @RequestMapping(value = "/api/0.9/json/merchant/sku/productcategory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveProductCategory(@Valid ProductCategory productCategory,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the productCategory
        productCategory.setPcyMerchantNo(merchantNo);

        // Log the Request
        log.info("saveProductCategory - Request Received# "+productCategory.toString());
        log.info("saveProductCategory -  "+generalUtils.getLogTextForRequest());



        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveProductCategory - Response : Invalid Input");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

        }

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the productCategory is existing
        boolean isExist = productCategoryService.isProductCategoryCodeDuplicateExisting(productCategory);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveProductCategory - Response : Product Category code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }



        // If the brand.getPcyId is  null, then set the created_by, else set the updated_by
        if ( productCategory.getPcyId() == null ) {

            productCategory.setCreatedBy(auditDetails);

        } else {

            productCategory.setUpdatedBy(auditDetails);

        }


        // save the productCategory object and get the result
        productCategory = productCategoryService.validateAndSaveProductCategory(productCategory);

        // If the productCategory object is not null ,then return the success object
        if ( productCategory.getPcyId() != null ) {

            // Get the productCategory id
            retData.setData(productCategory.getPcyId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

            // Log the response
            log.info("saveProductCategory -  " + generalUtils.getLogTextForResponse(retData));


        } else {

            // Log the response
            log.info("saveProductCategory - Response : Unable to save the product category information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/sku/productcategory/delete/{pcyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteProductCategory(@PathVariable(value = "pcyId") Long pcyId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Log the Request
        log.info("deleteProductCategory - Request Received# Pcy ID : " + pcyId);
        log.info("deleteProductCategory -  "+generalUtils.getLogTextForRequest());



        // Get the ProductCategory Information
        ProductCategory productCategory = productCategoryService.findByPcyId(pcyId);

        // Check if the productCategory exists
        if ( productCategory == null || productCategory.getPcyId() == null ) {

            // Log the response
            log.info("deleteProductCategory - Response : Not product category information found ");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( productCategory.getPcyMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteProductCategory - Response : You are not authorized to delete the product category");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // Delete the productCategory and set the retData fields
        productCategoryService.validateAndDeleteProductCategory(pcyId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete pcyId
        retData.setData(pcyId);

        // Log the response
        log.info("deleteProductCategory -  " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;

    }



    @RequestMapping(value = "/api/0.9/json/merchant/sku/productcategories/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listProductCategories(
                                                    @PathVariable(value ="filter") String filter,
                                                    @PathVariable(value ="query") String query,
                                                    Pageable pageable){


        // Log the Request
        log.info("listProductCategories - Request Received# filter "+ filter +" query :" +query );
        log.info("listProductCategories -   "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Variable holding the categories
        List<ProductCategoryResource> productCategoryResourceList = new ArrayList<>(0);

        // Get the page
        Page<ProductCategory> productCategoryPage = productCategoryService.searchProductCategories(filter,query,merchantNo,pageable);

        // Convert to resource
        productCategoryResourceList = productCategoryAssembler.toResources(productCategoryPage);

        // Set the pageable params for the retData
        retData.setPageableParams(productCategoryPage);

        // Set the data
        retData.setData(productCategoryResourceList);

        // Log the response
        log.info("listProductCategories -  " + generalUtils.getLogTextForResponse(retData));

        // Return the data
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/sku/productcategory/{pcyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getProductCategoryInfo(@PathVariable(value="pcyId") Long pcyId) throws InspireNetzException {

        // Log the Request
        log.info("getProductCategoryInfo - Request Received# brnid "+ pcyId );
        log.info("getProductCategoryInfo -   "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();



        // Get the information for the pcyId
        ProductCategory productCategory = productCategoryService.findByPcyId(pcyId);

        // Check if the information exists
        if ( productCategory == null || productCategory.getPcyId() == null ) {

            // Log the response
            log.info("getProductCategoryInfo - Response : Not product category information found ");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( productCategory.getPcyMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getProductCategoryInfo - Response : You are not authorized to delete the product category");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // Convert the ProductCategory to resource
        ProductCategoryResource productCategoryResource = productCategoryAssembler.toResource(productCategory);

        // Set the data
        retData.setData(productCategoryResource);;

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);


        // Set the log file
        log.info("getProductCategoryInfo -   " + generalUtils.getLogTextForResponse(retData));




        // Return the object
        return retData;

    }


}

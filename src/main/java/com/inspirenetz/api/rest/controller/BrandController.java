package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.service.BrandService;
import com.inspirenetz.api.rest.assembler.BrandAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.BrandResource;
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
 *
 */
@Controller
public class BrandController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandAssembler brandAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;



    @RequestMapping(value = "/api/0.9/json/merchant/sku/brand", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveBrand(@Valid Brand brand,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the brand
        brand.setBrnMerchantNo(merchantNo);


        // Log the Request
        log.info("saveBrand - Request Received# "+brand.toString());
        log.info("saveBrand -  "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveBrand - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // Check if the brand is existing
        boolean isExist = brandService.isBrandCodeDuplicateExisting(brand);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveBrand - Response : Brand code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the brand.getBrnId is  null, then set the created_by, else set the updated_by
        if ( brand.getBrnId() == null ) {

            brand.setCreatedBy(auditDetails);

        } else {

            brand.setUpdatedBy(auditDetails);

        }


        // save the brand object and get the result
        brand = brandService.validateAndSaveBrand(brand);

        // If the brand object is not null ,then return the success object
        if ( brand.getBrnId() != null ) {

            // Get the brand id
            retData.setData(brand.getBrnId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveBrand - Response : Unable to save the brand information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveBrand - "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/sku/brand/delete/{brnId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteBrand(@PathVariable(value="brnId") Long brnId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteBrand - Request Received# Brand ID: "+brnId);
        log.info("deleteBrand -  "+generalUtils.getLogTextForRequest());

        // Get the brand information
        Brand brand = brandService.findByBrnId(brnId);

        // Check if the brand is found
        if ( brand == null || brand.getBrnId() == null) {

            // Log the response
            log.info("deleteBrand - Response : No brand information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( brand.getBrnMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteBrand - Response : You are not authorized to delete the brand");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the brand and set the retData fields
        brandService.validateAndDeleteBrand(brnId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete brnId
        retData.setData(brnId);


        // Log the response
        log.info("deleteBrand - "+generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/sku/brands/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listBrands(@PathVariable(value ="filter") String filter,
                                        @PathVariable(value ="query") String query,
                                        Pageable pageable){


        // Log the Request
        log.info("listBrands - Request Received# filter "+ filter +" query :" +query );
        log.info("listBrands -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Variable holding the brand
        List<BrandResource> brandResourceList = new ArrayList<>(0);

        Page<Brand> brandPage = brandService.searchBrands(filter,query,merchantNo,pageable);

         // Convert to Resource
         brandResourceList = brandAssembler.toResources(brandPage);

         // Set the pageable params for the retData
         retData.setPageableParams(brandPage);

        // Set the data
        retData.setData(brandResourceList);


        // Log the response
        log.info("listBrands - "+generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/sku/brand/{brnId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getBrandInfo(@PathVariable(value="brnId") Long brnId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getBrandInfo - Request Received# Brand ID: "+brnId);
        log.info("getBrandInfo -  "+generalUtils.getLogTextForRequest());

        // Get the brand information
        Brand brand = brandService.findByBrnId(brnId);

        // Check if the brand is found
        if ( brand == null || brand.getBrnId() == null) {

            // Log the response
            log.info("getBrandInfo - Response : No brand information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( brand.getBrnMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getBrandInfo - Response : You are not authorized to view the brand");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }




        // Convert the Brand to BrandResource
        BrandResource brandResource = brandAssembler.toResource(brand);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the brandResource object
        retData.setData(brandResource);




        // Log the response
        log.info("getBrandInfo - "+generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

}

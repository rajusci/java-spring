package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.CatalogueCategory;
import com.inspirenetz.api.core.service.CatalogueCategoryService;
import com.inspirenetz.api.rest.assembler.CatalogueCategoryAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CatalogueCategoryResource;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class CatalogueCategoryController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CatalogueCategoryController.class);

    @Autowired
    private CatalogueCategoryService catalogueCategoryService;

    @Autowired
    private CatalogueCategoryAssembler catalogueCategoryAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/admin/cataloguecategory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveCatalogueCategory(@Valid CatalogueCategory catalogueCategory,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveCatalogueCategory - Request Received# "+catalogueCategory.toString());
        log.info("saveCatalogueCategory -  "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveCatalogueCategory - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }


        // If the catalogueCategory.getCacId is  null, then set the created_by, else set the updated_by
        if ( catalogueCategory.getCacId() == null ) {

            catalogueCategory.setCreatedBy(auditDetails);

        } else {

            catalogueCategory.setUpdatedBy(auditDetails);

        }


        // save the catalogueCategory object and get the result
        catalogueCategory = catalogueCategoryService.validateAndSaveCatalogueCategory(catalogueCategory);

        // If the catalogueCategory object is not null ,then return the success object
        if ( catalogueCategory.getCacId() != null ) {

            // Get the catalogueCategory id
            retData.setData(catalogueCategory.getCacId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveCatalogueCategory - Response : Unable to save the catalogueCategory information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveCatalogueCategory - "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/admin/cataloguecategory/delete/{cacId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteCatalogueCategory(@PathVariable(value="cacId") Long cacId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteCatalogueCategory - Request Received# CatalogueCategory ID: "+cacId);
        log.info("deleteCatalogueCategory -  "+generalUtils.getLogTextForRequest());

        // Get the catalogueCategory information
        CatalogueCategory catalogueCategory = catalogueCategoryService.findByCacId(cacId);

        // Check if the catalogueCategory is found
        if ( catalogueCategory == null || catalogueCategory.getCacId() == null) {

            // Log the response
            log.info("deleteCatalogueCategory - Response : No catalogueCategory information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Delete the catalogueCategory and set the retData fields
        catalogueCategoryService.validateAndDeleteCatalogueCategory(cacId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete cacId
        retData.setData(cacId);


        // Log the response
        log.info("deleteCatalogueCategory - "+generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/admin/cataloguecategories/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCatalogueCategorys(@PathVariable(value ="filter") String filter,
                                                    @PathVariable(value ="query") String query,
                                                    Pageable pageable){


        // Log the Request
        log.info("listCatalogueCategorys - Request Received# filter "+ filter +" query :" +query );
        log.info("listCatalogueCategorys -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Variable holding the catalogueCategory
        List<CatalogueCategoryResource> catalogueCategoryResourceList;

        // Get the CatalogueCategoryPage
        Page<CatalogueCategory> catalogueCategoryPage = catalogueCategoryService.searchCatalogueCategories(filter, query, pageable);

        // Convert the catalogueCategoryPage to CatalogueCategoryResource List
        catalogueCategoryResourceList = catalogueCategoryAssembler.toResources(catalogueCategoryPage);

        // Set the data
        retData.setData(catalogueCategoryResourceList);


        // Log the response
        log.info("listCatalogueCategorys - "+generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/admin/cataloguecategory/{cacId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCatalogueCategoryInfo(@PathVariable(value="cacId") Long cacId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getCatalogueCategoryInfo - Request Received# CatalogueCategory ID: "+cacId);
        log.info("getCatalogueCategoryInfo -  "+generalUtils.getLogTextForRequest());

        // Get the catalogueCategory information
        CatalogueCategory catalogueCategory = catalogueCategoryService.findByCacId(cacId);

        // Check if the catalogueCategory is found
        if ( catalogueCategory == null || catalogueCategory.getCacId() == null) {

            // Log the response
            log.info("getCatalogueCategoryInfo - Response : No catalogueCategory information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

          
        }


        // Convert the CatalogueCategory to CatalogueCategoryResource
        CatalogueCategoryResource catalogueCategoryResource = catalogueCategoryAssembler.toResource(catalogueCategory);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the catalogueCategoryResource object
        retData.setData(catalogueCategoryResource);




        // Log the response
        log.info("getCatalogueCategoryInfo - "+generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/cataloguecategories/parentgroup/{parentCategoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCatalogueCategoriesForParentGroup(@PathVariable(value="parentCategoryId") Integer parentCategoryId) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getCatalogueCategoriesForParentGroup - Request Received# parentCategoryId: "+parentCategoryId);
        log.info("getCatalogueCategoriesForParentGroup -  "+generalUtils.getLogTextForRequest());


        // Get the List of categories
        List<CatalogueCategory> catalogueCategoryList = catalogueCategoryService.findByCacParentGroup(parentCategoryId);

        // Convert to resource
        List<CatalogueCategoryResource> catalogueCategoryResourceList = catalogueCategoryAssembler.toResources(catalogueCategoryList);



        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the catalogueCategoryResource object
        retData.setData(catalogueCategoryResourceList);



        // Log the response
        log.info("getCatalogueCategoriesForParentGroup - "+generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/cataloguecategories/firstlevel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getFirstLevelCatalogueCategories() throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("getFirstLevelCatalogueCategories - Request Received");
        log.info("getFirstLevelCatalogueCategories -  "+generalUtils.getLogTextForRequest());


        // Get the List of categories
        List<CatalogueCategory> catalogueCategoryList = catalogueCategoryService.findFirstLevelCategories();

        // Convert to resource
        List<CatalogueCategoryResource> catalogueCategoryResourceList = catalogueCategoryAssembler.toResources(catalogueCategoryList);



        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the catalogueCategoryResource object
        retData.setData(catalogueCategoryResourceList);


        // Log the response
        log.info("getFirstLevelCatalogueCategories - "+generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;

    }

}

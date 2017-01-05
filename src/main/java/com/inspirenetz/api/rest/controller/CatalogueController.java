package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.CatalogueType;
import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.PartnerCatalogue;
import com.inspirenetz.api.core.service.CatalogueService;
import com.inspirenetz.api.core.service.MerchantPartnerTransactionService;
import com.inspirenetz.api.core.service.MerchantSettlementService;
import com.inspirenetz.api.core.service.PartnerCatalogueService;
import com.inspirenetz.api.rest.assembler.CatalogueAssembler;
import com.inspirenetz.api.rest.assembler.CatalogueCompatableAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CatalogueCompatableResource;
import com.inspirenetz.api.rest.resource.CatalogueResource;
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
public class CatalogueController {


    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CatalogueController.class);

    @Autowired
    private CatalogueService catalogueService;

    @Autowired
    private CatalogueAssembler catalogueAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    CatalogueCompatableAssembler catalogueCompatableAssembler;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    PartnerCatalogueService partnerCatalogueService;

    @Autowired
    MerchantPartnerTransactionService merchantPartnerTransactionService;

    @Autowired
    MerchantSettlementService merchantSettlementService;

    @RequestMapping(value = "/api/0.9/json/merchant/catalogue", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveCatalogue(@Valid Catalogue catalogue,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the catalogue
        catalogue.setCatMerchantNo(merchantNo);


        // Log the Request
        log.info("saveCatalogue - Request Received# "+catalogue.toString());
        log.info("saveCatalogue - "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveCatalogue - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // Check if the catalogue is existing
        boolean isExist = catalogueService.isDuplicateCatalogueExisting(catalogue);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveCatalogue - Response : Catalogue code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the catalogue.getCatProductNo is  null, then set the created_by, else set the updated_by
        if ( catalogue.getCatProductNo() == null ) {

            catalogue.setCreatedBy(auditDetails);

        } else {

            catalogue.setUpdatedBy(auditDetails);

        }

        PartnerCatalogue partnerCatalogue = new PartnerCatalogue();

        if(catalogue.getCatPartnerProduct() != null && catalogue.getCatPartnerProduct() != 0 ){

            //check catalogue stock
            //get partner catalogue details
            partnerCatalogue = partnerCatalogueService.findByPacId(catalogue.getCatPartnerProduct());

            if(partnerCatalogue == null){

                log.error("saveCatalogue : No parnter catalogue found for partner catalogue id : "+catalogue.getCatPartnerProduct() );

                throw new InspireNetzException(APIErrorCode.ERR_INVALID_PARTNER_CATALOGUE);

            } else if(catalogue.getCatAvailableStock() > partnerCatalogue.getPacStock()){

                log.error("saveCatalogue : Not enough stock available for partner catalogue :Available stock :"+partnerCatalogue.getPacStock()+"Requested "+catalogue.getCatAvailableStock());

                throw new InspireNetzException(APIErrorCode.ERR_INVALID_CATALOGUE_STOCK);
            }

        }

        boolean isStoreCatalogue = false;

        if(catalogue.getCatProductNo() == null && catalogue.getCatType() == CatalogueType.PARTNER_CATALOGUE){

            isStoreCatalogue = true;

        }
        // save the catalogue object and get the result
        catalogue = catalogueService.validateAndSaveCatalogue(catalogue);

        if(isStoreCatalogue){

            merchantPartnerTransactionService.addMerchantPartnerTransaction(catalogue);

            merchantSettlementService.addSettlementEntryForPartnerTransaction(catalogue,partnerCatalogue);
        }
        partnerCatalogueService.deductStockFromPartnerCatalogue(catalogue.getCatPartnerProduct(),catalogue.getCatAvailableStock());


        // If the catalogue object is not null ,then return the success object
        if ( catalogue.getCatProductNo() != null ) {

            // Get the catalogue id
            retData.setData(catalogue.getCatProductNo());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveCatalogue - Response : Unable to save the catalogue information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveCatalogue - "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/catalogue/delete/{catProductNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteCatalogue(@PathVariable( value = "catProductNo") Long catProductNo) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteCatalogue - Request Received# Product No:"+ catProductNo);
        log.info("deleteCatalogue - "+generalUtils.getLogTextForRequest());


        // Get the catalogue information
        Catalogue catalogue = catalogueService.findByCatProductNo(catProductNo);

        // If no data found, then set error
        if ( catalogue == null ) {

            // Log the response
            log.info("deleteCatalogue - Response : No catalogue information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( catalogue.getCatMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteCatalogue - Response : You are not authorized to delete the catalogue");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the catalogue and set the retData fields
        catalogueService.validateAndDeleteCatalogue(catProductNo);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete catId
        retData.setData(catProductNo);

        // Log the response
        log.info("deleteCatalogue - "+generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/catalogue/{catProductNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCatalogueInfo(@PathVariable(value = "catProductNo") Long catProductNo) throws InspireNetzException {


        // Log the Request
        log.info("getCatalogueInfo - Request Received# "+catProductNo);
        log.info("getCatalogueInfo - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Get the Catalogue information
        Catalogue catalogue = catalogueService.findByCatProductNo(catProductNo);

        // Check if the catalogue is found
        if ( catalogue == null || catalogue.getCatProductNo() == null) {

            // Log the response
            log.info("getCatalogueInfo - Response : No catalogue information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( catalogue.getCatMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getCatalogueInfo - Response : You are not authorized to view the catalogue");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // Convert the Catalogue to CatalogueResource
        CatalogueResource catalogueResource = catalogueAssembler.toResource(catalogue);

        // Set the data
        retData.setData(catalogueResource);



        // Log the response
        log.info("getCatalogueInfo - "+generalUtils.getLogTextForResponse(retData));




        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/catalogues/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCataloguesMerchant(
                                            @PathVariable(value = "filter") String filter,
                                            @PathVariable(value = "query") String query,
                                            @RequestParam(value = "rewardcurrency",defaultValue = "0") Long rewardCurrencyId,
                                            @RequestParam(value = "loyaltyId",defaultValue = "0") String loyaltyId,
                                            @RequestParam(value = "channel",defaultValue = "2") Integer channel,
                                            Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listCatalogues - Request Received# filter : " +filter + " Query : " + query);
        log.info("listCatalogues - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Variable holding the response resource list
        List<CatalogueResource> catalogueResourceList = new ArrayList<>(0);


        // Check if the filter and query are specified
        if ( filter.equals("0") && query.equals("0") ) {

            // Get the data for all the catalogues
            Page<Catalogue> cataloguePage = catalogueService.findByCatMerchantNo(merchantNo,pageable);

            // Convert to cataloguResourcelist
            catalogueResourceList = catalogueAssembler.toResources(cataloguePage);

            // Set the pagenable params
            retData.setPageableParams(cataloguePage);

        } else if ( filter.equalsIgnoreCase("name") ) {


            // Get the data for all the catalogues
            Page<Catalogue> cataloguePage = catalogueService.findByCatMerchantNoAndCatDescriptionLike(merchantNo, "%" + query + "%", pageable);

            // Convert to cataloguResourcelist
            catalogueResourceList = catalogueAssembler.toResources(cataloguePage);

            // Set the pagenable params
            retData.setPageableParams(cataloguePage);


        } else if ( filter.equalsIgnoreCase("code") ) {

            // Get the Catalogue matching the product code
            Catalogue catalogue = catalogueService.findByCatProductCodeAndCatMerchantNo(query,merchantNo);

            // Convert to CatalogueResource
            CatalogueResource catalogueResource = catalogueAssembler.toResource(catalogue);

            // Add the resource to the list
            catalogueResourceList.add(catalogueResource);

        } else if ( filter.equals("category") ) {

            // Get the data for all the catalogues
            Page<Catalogue> cataloguePage = catalogueService.searchCatalogueByCurrencyAndCategory(merchantNo, rewardCurrencyId, Integer.parseInt(query),loyaltyId, channel, pageable);
            
            // Convert to cataloguResourcelist
            catalogueResourceList = catalogueAssembler.toResources(cataloguePage);

            // Set the pagenable params
            retData.setPageableParams(cataloguePage);

        }




        // Set the data
        retData.setData(catalogueResourceList);

        // Log the response
        log.info("listCatalogues - "+generalUtils.getLogTextForResponse(retData));




        // Return the success object
        return retData;


    }
    @RequestMapping(value = "/api/0.9/json/customer/catalogues/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCataloguesCustomer(
            @PathVariable(value = "filter") String filter,
            @PathVariable(value = "query") String query,
            @RequestParam(value = "merchantno") Long merchantNo,
            @RequestParam(value = "rewardcurrency",defaultValue = "0") Long rewardCurrencyId,
            @RequestParam(value = "channel",defaultValue = "3") Integer channel,
            Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listCataloguesCustomer - Request Received# filter : " +filter + " Query : " + query);
        log.info("listCataloguesCustomer - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


         // Variable holding the response resource list
        List<CatalogueResource> catalogueResourceList = new ArrayList<>(0);

        Page<Catalogue> cataloguePage = catalogueService.searchCatalogueByCurrencyAndCategoryCustomerPortal(merchantNo, rewardCurrencyId,filter, query,channel, pageable);


        // Convert to cataloguResourcelist
        catalogueResourceList = catalogueAssembler.toResources(cataloguePage);

        // Set the pagenable params
        retData.setPageableParams(cataloguePage);

        // Set the data
        retData.setData(catalogueResourceList);

        // Log the response
        log.info("listCatalogues - "+generalUtils.getLogTextForResponse(retData));




        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/catalogues/compatible", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCataloguesCustomerCompatible(
            @RequestParam(value = "query",defaultValue = "0") String query,
            @RequestParam(value = "merchant_no",defaultValue = "1") Long merchantNo,
            @RequestParam(value = "prd_category",defaultValue = "0") String prdCategory,
            Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listCataloguesCustomerCompatible - Request Received# Product Category :" + query);
        log.info("listCataloguesCustomerCompatible - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        String  loyaltyId = authSessionUtils.getUserLoginId();

        //call the service method for retrieving the catalogue list
        List<Catalogue > catalogues = catalogueService.getCatalogueListCompatible(prdCategory,loyaltyId,merchantNo,query);

        // Variable holding the response resource list
        List<CatalogueResource> catalogueResourceList = new ArrayList<>(0);

        //for mapping rosource
        List<CatalogueCompatableResource> catalogueCompatibleResourcesList =new ArrayList<>(0);

        catalogueCompatibleResourcesList =catalogueCompatableAssembler.toResources(catalogues,merchantNo,loyaltyId);

        // Set the data
        retData.setData(catalogueCompatibleResourcesList);

        // Log the response
        log.info("listCatalogues - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/catalogue/list/favourites/{merchantNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCustomerFavourites(@PathVariable(value = "merchantNo") Long merchantNo) throws InspireNetzException {


        // Log the Request
        log.info("listCustomerFavourites - Request Received# MerchantNo :"+merchantNo);

        // Log the Request
        log.info("listCustomerFavourites - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Variable holding the response resource list
        List<CatalogueResource> catalogueResourceList = new ArrayList<>(0);

        String loyaltyId = authSessionUtils.getUserLoginId();


        List<Catalogue> catalogueList = catalogueService.getCatalogueFavourites(loyaltyId,merchantNo);

        // Convert to cataloguResourcelist
        catalogueResourceList = catalogueAssembler.toResources(catalogueList);

        // Set the data
        retData.setData(catalogueResourceList);

        // Log the response
        log.info("listCatalogues - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/public/catalogues", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getPublicCatalogues(
            @RequestParam(value = "query",defaultValue = "") String query,
            @RequestParam(value = "merchantNo",defaultValue = "0") Long catMerchantNo,
            @RequestParam(value = "category",defaultValue = "0") Integer catCategory,
            @RequestParam(value = "channel",defaultValue = "0") Integer catChannel,
            Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("getPublicCatalogues - Request Received# merchantNo :" + catMerchantNo+"category :"+catCategory+"channel :"+catChannel+"query :"+query);
        //log.info("getPublicCatalogues - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //get the catalogues for public
        Page<Catalogue > catalogues = catalogueService.getPublicCatalogues(catMerchantNo,catCategory, catChannel, query, pageable);

        // Variable holding the response resource list
        List<CatalogueResource> catalogueResourceList = new ArrayList<>(0);


        catalogueResourceList =catalogueAssembler.toResources(catalogues);

        // Set the data
        retData.setData(catalogueResourceList);

        // Log the response
        //log.info("getPublicCatalogues - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/user/catalogues", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCataloguesUser(
            @RequestParam(value = "filter",defaultValue = "0") String filter,
            @RequestParam(value = "query",defaultValue = "0") String query,
            @RequestParam(value = "categoryFilters",defaultValue = "") String categories,
            @RequestParam(value = "merchantFilters",defaultValue = "0") String merchants,
            @RequestParam(value = "catMerchantNo",defaultValue = "0") Long catMerchantNo,
            @RequestParam(value = "channel",defaultValue = "0") Integer channel,
            Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listCataloguesUser - Request Received# filter : "+filter+" query : " +query + " categories : " + categories+ " merchants : " + merchants+ " channel : " + channel);

        log.info("listCataloguesUser - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Variable holding the response resource list
        List<CatalogueResource> catalogueResourceList = new ArrayList<>(0);

        String usrLoginId = authSessionUtils.getUserLoginId();

        // Get the data for all the catalogues
        Page<Catalogue> catalogues = catalogueService.listCataloguesUser(catMerchantNo,usrLoginId,filter,query,categories,merchants,channel, pageable);



        catalogueResourceList=catalogueAssembler.toResources(catalogues);

        // Set the data
        retData.setData(catalogueResourceList);

        // Log the response
        log.info("listCatalogues - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/user/catalogues/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCataloguesForCustomerPortal(
            @RequestParam(value = "filter",defaultValue = "0") String filter,
            @RequestParam(value = "query",defaultValue = "") String query,
            @RequestParam(value = "catCategory",defaultValue = "0") Integer catCategory,
            @RequestParam(value = "catMerchantNo",defaultValue = "0") Long catMerchantNo,
            @RequestParam(value = "sortOption",defaultValue = "0") Integer sortOption,
            @RequestParam(value = "channel",defaultValue = "0") Integer channel,
            Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listCataloguesUser - Request Received# filter : "+filter+" query : " +query + " catCategory : " + catCategory+ " catMerchantNo : " + catMerchantNo+" sort option :"+sortOption+ " channel : " + channel);

        log.info("listCataloguesUser - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Variable holding the response resource list
        List<CatalogueResource> catalogueResourceList = new ArrayList<>(0);

        String usrLoginId = authSessionUtils.getUserLoginId();

        // Get the data for all the catalogues
        Page<Catalogue> catalogues = catalogueService.searchCatalogueByCatMerchantNoAndCatCategoryAndSortOption(usrLoginId,catMerchantNo,catCategory,filter,query,sortOption,channel,pageable);



        catalogueResourceList=catalogueAssembler.toResources(catalogues);

        // Set the pageable params to the retData
        retData.setPageableParams(catalogues);

        // Set the data
        retData.setData(catalogueResourceList);

        // Log the response
        log.info("listCatalogues - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/user/catalogue/list/favourites", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCatalogueFavouritesForUser(@RequestParam(value = "merchantNo",defaultValue = "0") Long merchantNo,@RequestParam(value = "query",defaultValue = "") String query) throws InspireNetzException {


        // Log the Request
        log.info("getCatalogueFavouritesForUser - Request Received# MerchantNo :"+merchantNo);

        // Log the Request
        log.info("getCatalogueFavouritesForUser - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Variable holding the response resource list
        List<CatalogueResource> catalogueResourceList = new ArrayList<>(0);

        String usrLoginId = authSessionUtils.getUserLoginId();


        List<Catalogue> catalogueList = catalogueService.getCatalogueFavouritesForUser(usrLoginId,merchantNo,query);

        // Convert to cataloguResourcelist
        catalogueResourceList = catalogueAssembler.toResources(catalogueList);

        // Set the data
        retData.setData(catalogueResourceList);

        // Log the response
        log.info("getCatalogueFavouritesForUser - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/partner/myproducts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getMerchantStoreProducts(Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("getMerchantStpreProducts - Request Received# ");

        // Log the Request
        log.info("getMerchantStpreProducts - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        Long merchantNo = authSessionUtils.getMerchantNo();

        // Variable holding the response resource list
        List<CatalogueResource> catalogueResourceList = new ArrayList<>(0);

        Page<Catalogue> catalogueList = catalogueService.findByCatTypeAndCatMerchantNo(CatalogueType.PARTNER_CATALOGUE, merchantNo, pageable);

        // Convert to cataloguResourcelist
        catalogueResourceList = catalogueAssembler.toResources(catalogueList);

        // Set the data
        retData.setData(catalogueResourceList);

        // Log the response
        log.info("getCatalogueFavouritesForUser - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/catalogue/updatestock", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updateCatalogueStockOfPartnerProduct(@RequestParam( value = "stock") Long stock,
                                                                  @RequestParam( value = "pacId") Long pacId ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("updateCatalogueStockOfPartnerProduct - Request Received# PacId:"+ pacId+" Stock : "+stock);
        log.info("updateCatalogueStockOfPartnerProduct - "+generalUtils.getLogTextForRequest());

        // Get the catalogue information
        boolean isUpdated = catalogueService.updateCatalogueStockOfPartnerProduct(stock, pacId, merchantNo);

        // If no data found, then set error
        if ( !isUpdated ) {

            // Log the response
            log.info("updateCatalogueStockOfPartnerProduct - Stock updation failed");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete catId
        retData.setData(isUpdated);

        // Log the response
        log.info("updateCatalogueStockOfPartnerProduct - "+generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }
}

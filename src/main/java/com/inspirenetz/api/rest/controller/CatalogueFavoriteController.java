package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.CatalogueFavorite;
import com.inspirenetz.api.core.service.CatalogueFavoriteService;
import com.inspirenetz.api.rest.assembler.CatalogueFavoriteAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CatalogueFavoriteResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alameen on 21/10/14.
 */
@Controller
public class CatalogueFavoriteController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CatalogueFavoriteController.class);

    @Autowired
    private CatalogueFavoriteService catalogueFavoriteService;


    @Autowired
    private CatalogueFavoriteAssembler catalogueFavoriteAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    private Mapper mapper;

    @RequestMapping(value = "/api/0.9/json/customer/favorite", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveCatalogueFavorite(CatalogueFavoriteResource catalogueFavoriteResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveCatalogueFavorite - Request Received# "+catalogueFavoriteResource.toString());
        log.info("saveCatalogueFavorite - "+generalUtils.getLogTextForRequest());




        // convert thecatalogueFavorite  to object
        CatalogueFavorite catalogueFavorite = mapper.map(catalogueFavoriteResource,CatalogueFavorite.class);

        // save the CataLogueFavorite object and get the result
        catalogueFavorite = catalogueFavoriteService.saveCatalogueFavorite(catalogueFavorite);

        // Get the CataLogueFavorite id
        retData.setData(catalogueFavorite.getCafId());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveCatalogueFavorite -  "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/catalogue/favorite/{loyaltyid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCatalogueFavorites(@PathVariable(value ="loyaltyid") String loyaltyId){

        // Log the Request
        log.info("getCatalogueFavorites - Request Received# ::loyaltyId loyaltyId="+ loyaltyId);
        log.info("getCatalogueFavorites - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Variable holding the CatalogueFavorite
        List<CatalogueFavoriteResource> catalogueFavoriteList = new ArrayList<>(0);
        
        // Variable holding the CataLogueFavorite 
        List<CatalogueFavorite> catalogueFavorite=catalogueFavoriteService.findByCafLoyaltyId(loyaltyId);
        
        
        // Convert the CataLogueFavorite to CataLogueFavorite Resource
        catalogueFavoriteList = catalogueFavoriteAssembler.toResources(catalogueFavorite);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the CataLogueFavorite Resource object
        retData.setData(catalogueFavoriteList);

        // Log the response
        log.info("getCatalogueFavorites -  "+generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/user/favorite", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveCatalogueFavoriteForUser(@RequestParam(value="productCode") Long cafProductNo,@RequestParam(value="favoriteFlag") Integer cafFavoriteFlag) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveCatalogueFavoriteForUser - Request Received# productCode: "+cafProductNo+" favoriteflag :"+cafFavoriteFlag);
        log.info("saveCatalogueFavoriteForUser - "+generalUtils.getLogTextForRequest());

        //get user login Id
        String usrLoginId=authSessionUtils.getUserLoginId();

        // save the catalogueFavorite object and get the result
        CatalogueFavorite catalogueFavorite = catalogueFavoriteService.saveCatalogueFavoriteForUser(usrLoginId,cafProductNo,cafFavoriteFlag);

        // Get the catalogueFavorite id
        retData.setData(catalogueFavorite.getCafId());

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveCatalogueFavorite -  "+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseObject
        return retData;


    }


}

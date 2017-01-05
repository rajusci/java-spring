package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.PartnerCatalogue;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.PartnerCatalogueService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.assembler.PartnerCatalogueAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerProfileResource;
import com.inspirenetz.api.rest.resource.CustomerResource;
import com.inspirenetz.api.rest.resource.PartnerCatalogueResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by saneeshci on 13/7/16.
 *
 */
@Controller
public class PartnerCatalogueController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(PartnerCatalogueController.class);

    @Autowired
    private PartnerCatalogueService partnerCatalogueService;

    @Autowired
    private PartnerCatalogueAssembler partnerCatalogueAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/0.9/json/partner/catalogues/search/{productNo}/{category}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject searchPartnerCatalogue(@PathVariable(value ="productNo") Long productNo,
                                                    @PathVariable(value ="category") Integer category,
                                              Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("retrievePartnerCatalogues - Request Received# ProductNo :" + productNo + " Category : " + category);
        log.info("retrievePartnerCatalogues - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        String userNo = authSessionUtils.getUserLoginId();

        // Get the PartnerCatalogue
        Page<PartnerCatalogue> PartnerCataloguePage = partnerCatalogueService.searchPartnerCatalogues(category, productNo, userNo, pageable);

        // Convert to reosurce
        List<PartnerCatalogueResource> PartnerCatalogueResourceList =  partnerCatalogueAssembler.toResources(PartnerCataloguePage);

        // Set the data
        retData.setData(PartnerCatalogueResourceList);

        // Log the response
        log.info("retrievePartnerCatalogues -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/partner/catalogues/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject searchPartnerCatalogue(@PathVariable(value ="filter") String filter,
                                                    @PathVariable(value ="query") String query,
                                                    Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("retrievePartnerCatalogues - Request Received# Filter :" + filter + " Query : " + query);
        log.info("retrievePartnerCatalogues - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        String userLoginId = authSessionUtils.getUserLoginId();

        // Get the PartnerCatalogue
        Page<PartnerCatalogue> partnerCataloguePage = partnerCatalogueService.getPartnerCatalogue(filter, query, userLoginId, pageable);

        List<PartnerCatalogueResource> partnerCatalogueResourceList =  new ArrayList<>();

        if(partnerCataloguePage != null){

            partnerCatalogueResourceList = partnerCatalogueAssembler.toResources(partnerCataloguePage);

        }
        // Convert to reosurce

        // Set the data
        retData.setData(partnerCatalogueResourceList);

        // Log the response
        log.info("retrievePartnerCatalogues -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/partner/catalogue", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject savePartnerCatalogue(@RequestBody Map<String,Object> params) throws InspireNetzException {



        // Create the response object
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        PartnerCatalogue partnerCatalogue = new PartnerCatalogue();

        mapper.map(params,partnerCatalogue);

        // Log the Request
        log.info("savePartnerCatalogue - Request Received# PartnerCatalogue : "+params) ;
        log.info("savePartnerCatalogue - "+generalUtils.getLogTextForRequest());


        // Call the saveData
        partnerCatalogue = partnerCatalogueService.validateAndSavePartnerCatalogue(partnerCatalogue);

        // Set the status as success as otherwise for any error condition, we will
        // have the exception thrown from there
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the customer number
        retData.setData(partnerCatalogue.getPacId());

        // Log the response
        log.info("savePartnerCatalogue - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/partner/catalogue/status/{pacId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updatePartnerCatalogueStatus(    @PathVariable(value="pacId") Long pacId,
                                                            @RequestParam(value = "pacStatus") Integer pacStatus) throws InspireNetzException {


        // Log the Request
        log.info("updatePartnerCatalogueStatus - Request Received# Program No"+pacId + " status : " + pacId);
        log.info("updatePartnerCatalogueStatus -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the logged in user details
        Long userNo = authSessionUtils.getUserNo();
        
        // Get the user details
        User user = userService.findByUsrUserNo(userNo);
        
        // Get the partner no
        Long partnerNo = user.getUsrThirdPartyVendorNo();

        // Call the updateProgramStatus
        boolean updated = partnerCatalogueService.updatePartnerCatalogueStatus(pacId,pacStatus,partnerNo,authSessionUtils.getUserNo());

        // if the updated is true, then set the data
        if ( updated ) {

            // set the retData to success
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.failed);

        }

        // Set the data to the status which was set
        retData.setData(pacStatus);

        // Log the response
        log.info("updatePartnerCatalogueStatus -  " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/partner/catalogue/{pacId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCatalogueInfo(@PathVariable(value = "pacId") Long pacId) throws InspireNetzException {


        // Log the Request
        log.info("getCatalogueInfo - Request Received# "+pacId);
        log.info("getCatalogueInfo - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the Catalogue information
        PartnerCatalogue catalogue = partnerCatalogueService.findByPacId(pacId);

        // Check if the catalogue is found
        if ( catalogue == null || catalogue.getPacId() == null) {

            // Log the response
            log.info("getCatalogueInfo - Response : No catalogue information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Convert the Catalogue to CatalogueResource
        PartnerCatalogueResource catalogueResource = partnerCatalogueAssembler.toResource(catalogue);

        // Set the data
        retData.setData(catalogueResource);

        // Log the response
        log.info("getCatalogueInfo - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/partner/prodcuts/{category}/{partnerNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject searchPartnerCatalogueForMerchant(@PathVariable(value ="category") Integer category,
                                                    @PathVariable(value ="partnerNo") Long partnerNo,
                                                    Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("retrievePartnerCatalogues - Request Received# Category :" + category + " Partner : " + partnerNo);
        log.info("retrievePartnerCatalogues - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        String userLoginId = authSessionUtils.getUserLoginId();

        // Get the PartnerCatalogue
        Page<PartnerCatalogue> partnerCataloguePage = partnerCatalogueService.searchPartnerCatalogueForMerchant(category, partnerNo, pageable);

        List<PartnerCatalogueResource> partnerCatalogueResourceList =  new ArrayList<>();

        if(partnerCataloguePage != null){

            partnerCatalogueResourceList = partnerCatalogueAssembler.toResources(partnerCataloguePage);

        }
        // Convert to reosurce

        // Set the data
        retData.setData(partnerCatalogueResourceList);

        // Log the response
        log.info("retrievePartnerCatalogues -" + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

}
package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Module;
import com.inspirenetz.api.core.service.ModuleService;
import com.inspirenetz.api.rest.assembler.ModuleAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.ModuleResource;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class ModuleController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(ModuleController.class);

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ModuleAssembler moduleAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/admin/module", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveModule(@Valid Module module,BindingResult result) throws InspireNetzException {

        // Check if the user is valid
        moduleService.isUserValidForOperation(authSessionUtils.getCurrentUser());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("saveModule - Request Received# "+module.toString());
        log.info("saveModule -  "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveModule - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // Check if the module is existing
        boolean isExist = moduleService.isDuplicateModuleExisting(module);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveModule - Response : Module code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the module.getCatId is  null, then mdl the created_by, else mdl the updated_by
        if ( module.getMdlId() == null ) {

            module.setCreatedBy(auditDetails);

        } else {

            module.setUpdatedBy(auditDetails);

        }


        // save the module object and get the result
        module = moduleService.validateAndSaveModule(module);

        // If the module object is not null ,then return the success object
        if ( module.getMdlId() != null ) {

            // Get the module id
            retData.setData(module.getMdlId());

            // Mdl the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveModule - Response : Unable to save the module information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveModule - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/admin/module/delete/{mdlId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteModule(@PathVariable( value = "mdlId") Long mdlId) throws InspireNetzException {


        // Check if the user is valid
        moduleService.isUserValidForOperation(authSessionUtils.getCurrentUser());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("deleteModule - Request Received# Product No:"+ mdlId);
        log.info("deleteModule -  "+generalUtils.getLogTextForRequest());


        // Get the module information
        Module module = moduleService.findByMdlId(mdlId);

        // If no data found, then mdl error
        if ( module == null ) {

            // Log the response
            log.info("deleteModule - Response : No module information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Delete the module and mdl the retData fields
        moduleService.validateAndDeleteModule(mdlId);

        // Mdl the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Mdl the data to the delete mdlId
        retData.setData(mdlId);

        // Log the response
        log.info("deleteModule - " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/admin/modules/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject searchModules( @PathVariable(value = "filter") String filter,
                                            @PathVariable(value = "query") String query,
                                            Pageable pageable) throws InspireNetzException {

        // Check if the user is valid
        moduleService.isUserValidForOperation(authSessionUtils.getCurrentUser());

        // Log the Request
        log.info("searchModules - Request Received# filter : "+filter + "query: "+query);
        log.info("searchModules -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching modules
        Page<Module> moduleList = moduleService.searchModules(filter,query,pageable);

        // Get the list of the moduleResources
        List<ModuleResource> moduleResourceList = moduleAssembler.toResources(moduleList);

        // Mdl the data
        retData.setData(moduleResourceList);

        // Log the response
        log.info("searchByModuleName - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/admin/module/{mdlId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getModuleInfo(@PathVariable(value="mdlId") Long mdlId) throws InspireNetzException {


        // Check if the user is valid
        moduleService.isUserValidForOperation(authSessionUtils.getCurrentUser());

        // Log the Request
        log.info("getModuleInfo - Request Received# mdlId "+mdlId);
        log.info("getModuleInfo -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the matching data
        Module module =  moduleService.findByMdlId(mdlId);

        // If the module is null, throw error as no information found
        if ( module == null ) {

            // Log the message
            log.info("getModuleInfo - No information found for the module ");

            // throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }



        // Convert the module to ModuleResource
        ModuleResource moduleResource = moduleAssembler.toResource(module);

        // Set the data in the retData
        retData.setData(moduleResource);;

        // Set te status to success
        retData.setStatus(APIResponseStatus.success);


        // Log the response
        log.info("getModuleInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }



}

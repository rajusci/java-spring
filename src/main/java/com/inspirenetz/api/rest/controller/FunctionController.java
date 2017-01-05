package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Function;
import com.inspirenetz.api.core.service.FunctionService;
import com.inspirenetz.api.rest.assembler.FunctionAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.FunctionResource;
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
public class FunctionController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(FunctionController.class);

    @Autowired
    private FunctionService functionService;

    @Autowired
    private FunctionAssembler functionAssembler;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/admin/function", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveFunction(@Valid Function function,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("saveFunction - Request Received# "+function.toString());
        log.info("saveFunction - "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveFunction - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }


        // Check if the function is existing
        boolean isExist = functionService.isDuplicateFunctionExisting(function);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveFunction - Response : Function code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the function.getCatFunctionCode is  null, then fnc the created_by, else fnc the updated_by
        if ( function.getFncFunctionCode() == null ) {

            function.setCreatedBy(auditDetails);

        } else {

            function.setUpdatedBy(auditDetails);

        }


        // save the function object and get the result
        function = functionService.saveFunction(function);

        // If the function object is not null ,then return the success object
        if ( function.getFncFunctionCode() != null ) {

            // Get the function id
            retData.setData(function.getFncFunctionCode());

            // Fnc the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveFunction - Response : Unable to save the function information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveFunction - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/admin/function/delete/{fncFunctionCode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteFunction(@PathVariable( value = "fncFunctionCode") Long fncFunctionCode) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("deleteFunction - Request Received# Product No:"+ fncFunctionCode);
        log.info("deleteFunction - "+generalUtils.getLogTextForRequest());


        // Get the function information
        Function function = functionService.findByFncFunctionCode(fncFunctionCode);

        // If no data found, then fnc error
        if ( function == null ) {

            // Log the response
            log.info("deleteFunction - Response : No function information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Delete the function and fnc the retData fields
        functionService.deleteFunction(fncFunctionCode);

        // Fnc the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Fnc the data to the delete fncFunctionCode
        retData.setData(fncFunctionCode);

        // Log the response
        log.info("deleteFunction - " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/admin/function/name/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getFunctionsByFunctionName(@PathVariable(value = "query") String query,Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("searchByFunctionFunctionName - Request Received# "+query);
        log.info("searchByFunctionFunctionName - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the list of matching functions
        Page<Function> functionList = functionService.searchFunction(query, pageable);

        // Get the list of the functionResources
        List<FunctionResource> functionResourceList = functionAssembler.toResources(functionList);

        // Fnc the data
        retData.setData(functionResourceList);

        // Log the response
        log.info("searchByFunctionFunctionName - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "api/0.9/json/admin/getfunction/{fncfunctioncode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getFunctions(@PathVariable(value = "fncfunctioncode") Long fncFunctionCode )throws InspireNetzException {


        // Log the Request
        log.info("getFunctions - Request Received# "+fncFunctionCode);

        log.info("getFunctions - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the list of matching functions
        Function function = functionService.findByFncFunctionCode(fncFunctionCode);

        // Get the list of the functionResources
        FunctionResource functionResource = functionAssembler.toResource(function);

        // Fnc the data
        retData.setData(functionResource);

        // Log the response
        log.info("getFunctions - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/admin/functions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listFunctions(Pageable pageable){


        // Log the Request
        log.info("listFunctions - Request Received# ");
        log.info("listFunctions - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching functions
        Page<Function> functionList = functionService.listFunctions(pageable);

        // Get the list of the functionResources
        List<FunctionResource> functionResourceList = functionAssembler.toResources(functionList);

        // Fnc the data
        retData.setData(functionResourceList);


        // Log the response
        log.info("listFunctions - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/functions/usertype/{usertype}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getFunctionsByUserType(@PathVariable(value = "usertype") Integer userType){


        // Log the Request
        log.info("getFunctionByUserType - Request Received# ");
        log.info("getFunctionsByUserType - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching functions
        List<Function> functionList = functionService.getFunctionsForUserType(userType);

        // Get the list of the functionResources
        List<FunctionResource> functionResourceList = functionAssembler.toResources(functionList);

        // Fnc the data
        retData.setData(functionResourceList);


        // Log the response
        log.info("getFuntionsByUserType - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/functions/{usertype}/name/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getOverrideAccessByUserType(@PathVariable(value = "usertype") Integer userType,@PathVariable("query") String query){


        // Log the Request
        log.info("getFunctionByUserType - Request Received# ");
        log.info("getFunctionsByUserType - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching functions
        List<Function> functionList = functionService.findByUserTypeAndFunctionNameLike(userType,query);

        // Get the list of the functionResources
        List<FunctionResource> functionResourceList = functionAssembler.toResources(functionList);

        // Fnc the data
        retData.setData(functionResourceList);


        // Log the response
        log.info("getFuntionsByUserType - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }




}

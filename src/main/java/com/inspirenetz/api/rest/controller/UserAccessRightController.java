package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.UserAccessRight;
import com.inspirenetz.api.core.service.UserAccessRightService;
import com.inspirenetz.api.rest.assembler.UserAccessRightAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.UserAccessRightResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserAccessRightController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(UserAccessRightController.class);

    @Autowired
    private UserAccessRightService userAccessRightService;

    @Autowired
    private UserAccessRightAssembler userAccessRightAssembler;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/admin/uar/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveUserAccessRight(@Valid UserAccessRight userAccessRight,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveUserAccessRight - Request Received# "+userAccessRight.toString());
        log.info("saveUserAccessRight - "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = AuthSession.getUserNo().toString() + "#" + AuthSession.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveUserAccessRight - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // Check if the userAccessRight is existing
        boolean isExist = userAccessRightService.isDuplicateUserAccessRightExisting(userAccessRight);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveUserAccessRight - Response : UserAccessRight code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the userAccessRight.getUarUarId is  null, then set the created_by, else set the updated_by
        if ( userAccessRight.getUarUarId() == null ) {

            userAccessRight.setCreatedBy(auditDetails);

        } else {

            userAccessRight.setUpdatedBy(auditDetails);

        }


        // save the userAccessRight object and get the result
        userAccessRight = userAccessRightService.saveUserAccessRight(userAccessRight);

        // If the userAccessRight object is not null ,then return the success object
        if ( userAccessRight.getUarUarId() != null ) {

            // Get the userAccessRight id
            retData.setData(userAccessRight.getUarUarId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveUserAccessRight - Response : Unable to save the userAccessRight information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveUserAccessRight - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/user/uars/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listUserAccessRightsMap() throws InspireNetzException {


        // Log the Request
        log.info("listUserAccessRights - Request Received# ");
        log.info("listUserAccessRights - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the user number and store
        Long userNo = AuthSession.getUserNo();

        // Get the UserAccessRight Map
        HashMap<Long,String> uarMap  = userAccessRightService.getUarAsMap(userNo);

        // Set the data
        retData.setData(uarMap);

        // Log the response
        log.info("listUserAccessRights - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

}

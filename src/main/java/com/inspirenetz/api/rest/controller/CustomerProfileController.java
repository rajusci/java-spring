package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.service.CustomerProfileService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by sandheepgr on 3/5/14.
 */
@Controller
public class CustomerProfileController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CustomerProfileController.class);

    // Autowire the Customerserivce dependency
    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;



    @RequestMapping(value = "/api/0.9/json/merchant/customer/profile/{cspId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerProfile(@PathVariable(value = "cspId") Long cspId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Log the Request
        log.info("getCustomerProfile - Request Received# - cspId "+cspId);
        log.info("getCustomerProfile - "+generalUtils.getLogTextForRequest());


        //  Get the CustomerProfile information for the given loyaltyid and
        //  merchantNumber of the logged int user
        CustomerProfile customerProfile = customerProfileService.findByCspId(cspId);

        // Check if the customerProfile is null
        if ( customerProfile == null ) {

            // Log the response
            log.info("getCusotmerProfile - Response - No data found");

            // throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Get the Customer object
        Customer customer = customerService.findByCusCustomerNo(customerProfile.getCspCustomerNo());

        // Check if the customer is valid
        if ( customer == null || customer.getCusMerchantNo() != merchantNo ) {

            // Log
            log.info("getCustomerProfile  -> You are not authorized to view the content");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        // Set the data
        retData.setData(customerProfile);;

        // Set the status
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getCusotmerProfile -  "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;

    }
}

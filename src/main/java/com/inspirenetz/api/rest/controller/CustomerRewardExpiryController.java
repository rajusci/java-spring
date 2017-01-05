package com.inspirenetz.api.rest.controller;
import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.CustomerRewardExpiry;
import com.inspirenetz.api.core.service.CustomerRewardExpiryService;
import com.inspirenetz.api.rest.assembler.CustomerRewardExpiryAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerRewardExpiryResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by sandheepgr on 16/2/14.
 */
@Controller
public class CustomerRewardExpiryController {

    @Autowired
    private CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    private CustomerRewardExpiryAssembler customerRewardExpiryAssembler;

    // Create the logger
    private static Logger log = LoggerFactory.getLogger(CustomerRewardExpiryController.class);

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = {"/api/0.9/json/merchant/customer/rewardexpiry/{loyaltyId}/{rwdCurrencyId}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRewardExpiry(
            @PathVariable(value = "loyaltyId") String loyaltyId,
            @PathVariable(value = "rwdCurrencyId") long rwdCurrencyId
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        long merchantNo = authSessionUtils.getMerchantNo();

        log.info("getRewardExpiry - "+generalUtils.getLogTextForRequest());

        // Get the list of CustomerRewardExpiry
        List<CustomerRewardExpiry> customerRewardExpiryList = new ArrayList<CustomerRewardExpiry>();

        // Check if the rwdCurrencyId is specified, if yes, then we need to
        // get the data filtered for the reward currency id
        if ( rwdCurrencyId != 0 ) {

            customerRewardExpiryList = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyId(merchantNo, loyaltyId, rwdCurrencyId);

        } else {

            customerRewardExpiryList = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyId(merchantNo, loyaltyId);
        }

        // If the list is empty, then we don't have any result
        if ( customerRewardExpiryList.isEmpty() ) {

            // Log the response
            log.info("getRewardExpiry - Response : No data for the request " );

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);
        } else {

             // convert the reward balance list to CustomerRewardExpiryResource
             List<CustomerRewardExpiryResource> customerRewardExpiryResourceList = customerRewardExpiryAssembler.toResources(customerRewardExpiryList,merchantNo);

             // Set the list
             retData.setData(customerRewardExpiryResourceList);

             // Set the status as success
             retData.setStatus(APIResponseStatus.success);

             // Log the response
            log.info("getRewardExpiry - " + generalUtils.getLogTextForResponse(retData));
        }

        // Return the retData object
        return retData;

    }


    @RequestMapping(value = {"/api/0.9/json/customer/rewardexpiry/{merchantNo}/{rwdCurrencyId}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRewardExpiryForCustomer(
            @PathVariable(value = "merchantNo") long merchantNo,
            @PathVariable(value = "rwdCurrencyId") long rwdCurrencyId
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        String loyaltyId = authSessionUtils.getUserLoginId();

        // Log the Request
        log.info("getRewardExpiryForCustomer - "+generalUtils.getLogTextForRequest());

        // Get the list of CustomerRewardExpiry
        List<CustomerRewardExpiry> customerRewardExpiryList = new ArrayList<CustomerRewardExpiry>();

        // Check if the rwdCurrencyId is specified, if yes, then we need to
        // get the data filtered for the reward currency id
        if ( rwdCurrencyId != 0 ) {

            customerRewardExpiryList = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyId(merchantNo, loyaltyId, rwdCurrencyId);

        } else {

            customerRewardExpiryList = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyId(merchantNo, loyaltyId);

        }

        // If the list is empty, then we don't have any result
        if ( customerRewardExpiryList.isEmpty() ) {

            // Log the response
            log.info("getRewardExpiry - Response : No data for the request " );

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);
        } else {

            // convert the reward balance list to CustomerRewardExpiryResource
            List<CustomerRewardExpiryResource> customerRewardExpiryResourceList = customerRewardExpiryAssembler.toResources(customerRewardExpiryList,merchantNo);

            // Set the list
            retData.setData(customerRewardExpiryResourceList);

            // Set the status as success
            retData.setStatus(APIResponseStatus.success);

            // Log the response
            log.info("getRewardExpiryForCustomer - " + generalUtils.getLogTextForResponse(retData));

        }

        // Return the retData object
        return retData;
    }
}

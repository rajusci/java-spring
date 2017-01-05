package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.CustomerReferral;
import com.inspirenetz.api.core.service.CustomerReferralService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.rest.assembler.CustomerReferralAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerReferralResource;
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

/**
 * Created by fayiz on 27/4/15.
 */
@Controller
public class CustomerReferralController {


    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CustomerReferralController.class);

    @Autowired
    private CustomerReferralService customerReferralService;

    @Autowired
    private CustomerReferralAssembler customerReferralAssembler;

    @Autowired
    CustomerService customerService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    private Mapper mapper;


    @RequestMapping(value = "/api/0.9/json/customer/referral/request", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveCustomerReferral(CustomerReferralResource customerReferralResource)throws InspireNetzException {

        // Log the Request
        log.info("saveCustomerReferral "+generalUtils.getLogTextForRequest() );

        //Log the Request Parameters
        log.info("saveCustomerReferral - Request Received# "+customerReferralResource);

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //map the referral object
        CustomerReferral customerReferral1=mapper.map(customerReferralResource,CustomerReferral.class);


        // save CustomerReferral
        CustomerReferral customerReferral = customerReferralService.validateCustomerReferralThroughCustomer(customerReferral1);

        //check customer referral  is null or not
        if(customerReferral !=null){

            //set status is success
            retData.setStatus(APIResponseStatus.success);

            //convert to resource object
            CustomerReferralResource customerReferralResource1 =customerReferralAssembler.toResource(customerReferral);

            //set data
            retData.setData(customerReferralResource1);

        }else{

            retData.setStatus(APIResponseStatus.failed);

            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);
        }

        // Log the response
        log.info("saveCustomerReferral - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;
    }

    @RequestMapping(value = "/api/0.9/json/merchant/customer/referral/request", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveCustomerReferralThroughMerchantPortal(CustomerReferralResource customerReferralResource)throws InspireNetzException {

        // Log the Request
        log.info("saveCustomerReferral "+generalUtils.getLogTextForRequest() );

        //Log the Request Parameters
        log.info("saveCustomerReferral - Request Received# "+customerReferralResource);

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();




        // save CustomerReferral
        CustomerReferral customerReferral = customerReferralService.saveCustomerReferralThroughMerchantPortal(customerReferralResource);


        //check customer referral  is null or not
        if(customerReferral !=null){

            //set status is success
            retData.setStatus(APIResponseStatus.success);

            //convert to resource object
            CustomerReferralResource customerReferralResource1 =customerReferralAssembler.toResource(customerReferral);

            //set data
            retData.setData(customerReferralResource1);

        }else{

            retData.setStatus(APIResponseStatus.failed);

            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);
        }

        // Log the response
        log.info("saveCustomerReferral - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;
    }

    @RequestMapping(value = "/api/0.9/json/merchant/customer/referral/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCustomerReferral(@PathVariable("filter") String filter,@PathVariable("query") String query,Pageable pageable)throws InspireNetzException {

        // Log the Request
        log.info("listCustomerReferral "+generalUtils.getLogTextForRequest() );

        //Log the Request Parameters
        log.info("listCustomerReferral - Request Received# filter:"+filter+"query:"+query);

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //customer referral list
        List<CustomerReferralResource> customerReferralResourceList =new ArrayList<>();

        //create pageable list in customer referral
        Page<CustomerReferral> customerReferralPage=customerReferralService.searchReferral(filter,query, pageable);

        customerReferralResourceList=customerReferralAssembler.toResources(customerReferralPage);

        //set data field
        retData.setData(customerReferralResourceList);

        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveCustomerReferral - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/customer/getreferral/{csrId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerReferralDetails(@PathVariable("csrId") Long csrId)throws InspireNetzException {

        // Log the Request
        log.info("getCustomerReferralDetails "+generalUtils.getLogTextForRequest() );

        //Log the Request Parameters
        log.info("getCustomerReferralDetails - Request Received# csrId:"+csrId);

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //customer referral list
        CustomerReferralResource customerReferralResource =new CustomerReferralResource();

        //create pageable list in customer referral
        CustomerReferral customerReferral=customerReferralService.findByCsrId(csrId);

        customerReferralResource=customerReferralAssembler.toResource(customerReferral);

        //set data field
        retData.setData(customerReferralResource);

        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getCustomerReferralDetails - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;
    }



    @RequestMapping(value = "/api/0.9/json/customer/referral/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCustomerReferralForCustomerPortal(@PathVariable("filter") String filter,@PathVariable("query") String query,@RequestParam(value ="merchantno",defaultValue = "0") Long merchantNo,Pageable pageable)throws InspireNetzException {

        // Log the Request
        log.info("listCustomerReferralForCustomerPortal "+generalUtils.getLogTextForRequest() );

        //Log the Request Parameters
        log.info("listCustomerReferralForCustomerPortal - Request Received# filter:"+filter+"query:"+query);

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //customer referral list
        List<CustomerReferralResource> customerReferralResourceList =new ArrayList<>();

        //create pageable list in customer referral
        Page<CustomerReferral> customerReferralPage=customerReferralService.searchReferralForCustomerPortal(filter,query,merchantNo,pageable);

        customerReferralResourceList=customerReferralAssembler.toResources(customerReferralPage);

        // Set the pageable params for the retData
        retData.setPageableParams(customerReferralPage);

        //set data field
        retData.setData(customerReferralResourceList);

        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveCustomerReferral - "+generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;
    }

}

package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.PaymentStatus;
import com.inspirenetz.api.core.service.PaymentStatusService;
import com.inspirenetz.api.rest.assembler.PaymentStatusAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.PaymentStatusResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class PaymentStatusController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(PaymentStatusController.class);

    @Autowired
    private PaymentStatusService paymentStatusService;

    @Autowired
    private PaymentStatusAssembler paymentStatusAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;


    @RequestMapping(value = "/api/0.9/json/merchant/paymentstatus/list/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listPaymentStatus( @PathVariable(value ="filter") String filter,
                                                @PathVariable(value ="query") String query,
                                                @RequestParam(value="date") Date date,
                                                @RequestParam(value="module") Integer module,
                                                Pageable pageable){


        // Log the Request
        log.info("listPaymentStatuss - Request Received# filter "+ filter +" query :" +query );
        log.info("listPaymentStatuss - Requested User  # Login Id : "+authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Get the paymentStatus page
        Page<PaymentStatus> paymentStatusPage =  paymentStatusService.searchPaymentStatus(merchantNo,date,module,filter,query,pageable);

        // Variable holding the paymentStatusResource
        List<PaymentStatusResource> paymentStatusResourceList = paymentStatusAssembler.toResources(paymentStatusPage);



        // Set the data
        retData.setData(paymentStatusResourceList);

        // Log the response
        log.info("listPaymentStatuss - Response : " + retData.toString());


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/paymentstatus/{pysId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getPaymentStatusInfo(@PathVariable(value="pysId") Long pysId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getPaymentStatusInfo - Request Received# PaymentStatus ID: "+pysId);
        log.info("getPaymentStatusInfo - Requested User  # Login Id : "+authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());

        // Get the paymentStatus information
        PaymentStatus paymentStatus = paymentStatusService.findByPysId(pysId);

        // Check if the paymentStatus is found
        if ( paymentStatus == null || paymentStatus.getPysId() == null) {

            // Log the response
            log.info("getPaymentStatusInfo - Response : No paymentStatus information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( paymentStatus.getPysMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getPaymentStatusInfo - Response : You are not authorized to view the paymentStatus");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }




        // Convert the PaymentStatus to PaymentStatusResource
        PaymentStatusResource paymentStatusResource = paymentStatusAssembler.toResource(paymentStatus);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the paymentStatusResource object
        retData.setData(paymentStatusResource);




        // Log the response
        log.info("getPaymentStatusInfo - Response : " + retData.toString());

        // Return the retdata object
        return retData;


    }

}

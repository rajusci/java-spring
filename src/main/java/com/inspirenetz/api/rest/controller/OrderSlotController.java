package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.OrderSlot;
import com.inspirenetz.api.core.service.OrderSlotService;
import com.inspirenetz.api.rest.assembler.OrderSlotAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.OrderSlotResource;
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
public class OrderSlotController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(OrderSlotController.class);

    @Autowired
    private OrderSlotService orderSlotService;

    @Autowired
    private OrderSlotAssembler orderSlotAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/order/orderslot", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveOrderSlot(@Valid OrderSlot orderSlot,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the orderSlot
        orderSlot.setOrtMerchantNo(merchantNo);


        // Log the Request
        log.info("saveOrderSlot - Request Received# "+orderSlot.toString());
        log.info("saveOrderSlot -  "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveOrderSlot - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }


        // If the orderSlot.getOrtId is  null, then set the created_by, else set the updated_by
        if ( orderSlot.getOrtId() == null ) {

            orderSlot.setCreatedBy(auditDetails);

        } else {

            orderSlot.setUpdatedBy(auditDetails);

        }


        // save the orderSlot object and get the result
        orderSlot = orderSlotService.validateAndSaveOrderSlot(orderSlot);

        // If the orderSlot object is not null ,then return the success object
        if ( orderSlot.getOrtId() != null ) {

            // Get the orderSlot id
            retData.setData(orderSlot.getOrtId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveOrderSlot - Response : Unable to save the orderSlot information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveOrderSlot -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/order/orderslot/delete/{ortId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteOrderSlot(@PathVariable(value="ortId") Long ortId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteOrderSlot - Request Received# OrderSlot ID: "+ortId);
        log.info("deleteOrderSlot -  "+generalUtils.getLogTextForRequest());

        // Get the orderSlot information
        OrderSlot orderSlot = orderSlotService.findByOrtId(ortId);

        // Check if the orderSlot is found
        if ( orderSlot == null || orderSlot.getOrtId() == null) {

            // Log the response
            log.info("deleteOrderSlot - Response : No orderSlot information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( orderSlot.getOrtMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteOrderSlot - Response : You are not authorized to delete the orderSlot");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the orderSlot and set the retData fields
        orderSlotService.validateAndDeleteOrderSlot(ortId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete ortId
        retData.setData(ortId);


        // Log the response
        log.info("deleteOrderSlot -  " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/order/orderslots", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listOrderSlots(@RequestParam(value ="ortType",defaultValue = "1") Integer ortType,
                                            @RequestParam(value ="ortSession",defaultValue = "1") Integer ortSession,
                                            Pageable pageable){


        // Log the Request
        log.info("listOrderSlots - Request Received# ortType "+ ortType +" ortSession :" +ortSession );
        log.info("listOrderSlots -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Variable holding the orderSlot
        List<OrderSlotResource> orderSlotResourceList = new ArrayList<>(0);

        // Get the list of the orderslts
        Page<OrderSlot> orderSlotPage = orderSlotService.findByOrtMerchantNoAndOrtLocationAndOrtTypeAndOrtSessionOrderByOrtStartingTime(merchantNo,authSessionUtils.getUserLocation(),ortType,ortSession,pageable);

        // convert to resource
        orderSlotResourceList = orderSlotAssembler.toResources(orderSlotPage);



        // Set the data
        retData.setData(orderSlotResourceList);

        // Log the response
        log.info("listOrderSlots -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/order/orderslot/{ortId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getOrderSlotInfo(@PathVariable(value="ortId") Long ortId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getOrderSlotInfo - Request Received# OrderSlot ID: "+ortId);
        log.info("getOrderSlotInfo -  "+generalUtils.getLogTextForRequest());

        // Get the orderSlot information
        OrderSlot orderSlot = orderSlotService.findByOrtId(ortId);

        // Check if the orderSlot is found
        if ( orderSlot == null || orderSlot.getOrtId() == null) {

            // Log the response
            log.info("getOrderSlotInfo - Response : No orderSlot information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( orderSlot.getOrtMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getOrderSlotInfo - Response : You are not authorized to view the orderSlot");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }




        // Convert the OrderSlot to OrderSlotResource
        OrderSlotResource orderSlotResource = orderSlotAssembler.toResource(orderSlot);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the orderSlotResource object
        retData.setData(orderSlotResource);




        // Log the response
        log.info("getOrderSlotInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

}

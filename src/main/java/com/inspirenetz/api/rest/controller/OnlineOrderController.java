package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.OnlineOrder;
import com.inspirenetz.api.core.service.OnlineOrderService;
import com.inspirenetz.api.rest.assembler.OnlineOrderAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.OnlineOrderResource;
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
public class OnlineOrderController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(OnlineOrderController.class);

    @Autowired
    private OnlineOrderService onlineOrderService;

    @Autowired
    private OnlineOrderAssembler onlineOrderAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/merchant/order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveOnlineOrder(@Valid OnlineOrder onlineOrder,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the onlineOrder
        onlineOrder.setOrdMerchantNo(merchantNo);


        // Log the Request
        log.info("saveOnlineOrder - Request Received# "+onlineOrder.toString());
        log.info("saveOnlineOrder - "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveOnlineOrder - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }



        // If the onlineOrder.getOrdId is  null, then set the created_by, else set the updated_by
        if ( onlineOrder.getOrdId() == null ) {

            onlineOrder.setCreatedBy(auditDetails);

        } else {

            onlineOrder.setUpdatedBy(auditDetails);

        }


        // save the onlineOrder object and get the result
        onlineOrder = onlineOrderService.validateAndSaveOnlineOrder(onlineOrder);

        // If the onlineOrder object is not null ,then return the success object
        if ( onlineOrder.getOrdId() != null ) {

            // Get the onlineOrder id
            retData.setData(onlineOrder.getOrdId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveOnlineOrder - Response : Unable to save the onlineOrder information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveOnlineOrder - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/order/delete/{ordId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteOnlineOrder(@PathVariable(value="ordId") Long ordId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteOnlineOrder - Request Received# OnlineOrder ID: "+ordId);
        log.info("deleteOnlineOrder - "+generalUtils.getLogTextForRequest());

        // Get the onlineOrder information
        OnlineOrder onlineOrder = onlineOrderService.findByOrdId(ordId);

        // Check if the onlineOrder is found
        if ( onlineOrder == null || onlineOrder.getOrdId() == null) {

            // Log the response
            log.info("deleteOnlineOrder - Response : No onlineOrder information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( onlineOrder.getOrdMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteOnlineOrder - Response : You are not authorized to delete the onlineOrder");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the onlineOrder and set the retData fields
        onlineOrderService.validateAndDeleteOnlineOrder(ordId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete ordId
        retData.setData(ordId);


        // Log the response
        log.info("deleteOnlineOrder - " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/orders/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listOnlineOrders(@PathVariable(value ="filter") String filter,
                                              @PathVariable(value ="query") String query,
                                              @RequestParam(value ="ordSlot",defaultValue = "0")  Long ordSlot,
                                              @RequestParam(value ="ordStatus",defaultValue = "0")  Integer ordStatus,
                                              Pageable pageable){


        // Log the Request
        log.info("listOnlineOrders - Request Received# filter "+ filter +" query :" +query );
        log.info("listOnlineOrders - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Variable holding the onlineOrder
        List<OnlineOrderResource> onlineOrderResourceList;

        // Get the page
        Page<OnlineOrder> onlineOrderPage = onlineOrderService.searchOnlineOrders(merchantNo,authSessionUtils.getUserLocation(),ordSlot,ordStatus,filter,query,pageable);

        // Convert to resource
        onlineOrderResourceList = onlineOrderAssembler.toResources(onlineOrderPage);

        // Set the data
        retData.setData(onlineOrderResourceList);

        // Log the response
        log.info("listOnlineOrders - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/order/{ordId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getOnlineOrderInfo(@PathVariable(value="ordId") Long ordId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getOnlineOrderInfo - Request Received# OnlineOrder ID: "+ordId);
        log.info("getOnlineOrderInfo - "+generalUtils.getLogTextForRequest());

        // Get the onlineOrder information
        OnlineOrder onlineOrder = onlineOrderService.findByOrdId(ordId);

        // Check if the onlineOrder is found
        if ( onlineOrder == null || onlineOrder.getOrdId() == null) {

            // Log the response
            log.info("getOnlineOrderInfo - Response : No onlineOrder information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( onlineOrder.getOrdMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getOnlineOrderInfo - Response : You are not authorized to view the onlineOrder");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }




        // Convert the OnlineOrder to OnlineOrderResource
        OnlineOrderResource onlineOrderResource = onlineOrderAssembler.toResource(onlineOrder);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the onlineOrderResource object
        retData.setData(onlineOrderResource);




        // Log the response
        log.info("getOnlineOrderInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }




    @RequestMapping(value = "/api/0.9/json/merchant/order/trackingid/{trackingId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getOnlineOrderInfoForTrackingId(@PathVariable(value="trackingId") Integer trackingId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getOnlineOrderInfoForTrackingId - Request Received# OnlineOrder ID: "+trackingId);
        log.info("getOnlineOrderInfoForTrackingId - "+generalUtils.getLogTextForRequest());



        // GEt the list
        List<OnlineOrder> onlineOrderList = onlineOrderService.findByOrdMerchantNoAndOrdUniqueBatchTrackingId(merchantNo,trackingId);

        // Convert to resource list
        List<OnlineOrderResource> onlineOrderResourceList = onlineOrderAssembler.toResources(onlineOrderList);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the onlineOrderResource object
        retData.setData(onlineOrderResourceList);




        // Log the response
        log.info("getOnlineOrderInfoForTrackingId - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/order/paymentstatus/{trackingId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updatePaymentStatus(
                        @PathVariable(value="trackingId") Integer trackingId,
                        @RequestParam(value="status")  Integer status
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("updateOnlineOrderPaymentStatus - Request Received# Tracking ID: "+trackingId + " - status : " + status) ;
        log.info("updateOnlineOrderPaymentStatus - "+generalUtils.getLogTextForRequest());



        // GEt the list
        List<OnlineOrder> onlineOrderList = onlineOrderService.findByOrdMerchantNoAndOrdUniqueBatchTrackingId(merchantNo,trackingId);


        // Check if data exists
        if ( onlineOrderList == null || onlineOrderList.isEmpty() ) {

            // Log the response
            log.info("updatePaymentStatus - Response : No data found for the tracking id");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Go through each of the list item and then update the status
        for(OnlineOrder onlineOrder : onlineOrderList ) {

            // Set the status
            onlineOrder.setOrdCashPaymentStatus(status);

            // Log the informaiton
            log.info("updatePaymentStatus - Updated status for " + onlineOrder.getOrdId());

        }



        // Save the list
        onlineOrderService.saveAll(onlineOrderList);


        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the status to which the online order is updated
        retData.setData(status);


        // Log the response
        log.info("updatePaymentStatus - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/order/status/{trackingId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updateOnlineOrderStatus(
            @PathVariable(value="trackingId") Integer trackingId,
            @RequestParam(value="status")  Integer status
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("updateOnlineOrderStatus - Request Received# Tracking ID: "+trackingId + " - status : " + status) ;
        log.info("updateOnlineOrderStatus - "+generalUtils.getLogTextForRequest());



        // GEt the list
        List<OnlineOrder> onlineOrderList = onlineOrderService.findByOrdMerchantNoAndOrdUniqueBatchTrackingId(merchantNo,trackingId);


        // Check if data exists
        if ( onlineOrderList == null || onlineOrderList.isEmpty() ) {

            // Log the response
            log.info("updateOnlineOrderStatus - Response : No data found for the tracking id");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Go through each of the list item and then update the status
        for(OnlineOrder onlineOrder : onlineOrderList ) {

            // Set the status
            onlineOrder.setOrdStatus(status);

            // Log the informaiton
            log.info("updateOnlineOrderStatus - Updated status for " + onlineOrder.getOrdId());

        }



        // Save the list
        onlineOrderService.saveAll(onlineOrderList);


        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the status to which the online order is updated
        retData.setData(status);


        // Log the response
        log.info("updatePaymentStatus - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }
}

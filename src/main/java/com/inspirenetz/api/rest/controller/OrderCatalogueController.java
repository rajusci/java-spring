package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.ImagePathType;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.OrderCatalogue;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.core.service.OrderCatalogueService;
import com.inspirenetz.api.rest.assembler.OrderCatalogueAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.OrderCatalogueResource;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class OrderCatalogueController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(OrderCatalogueController.class);

    @Autowired
    private OrderCatalogueService orderCatalogueService;

    @Autowired
    private OrderCatalogueAssembler orderCatalogueAssembler;

    @Autowired
    private ImageService imageService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;


    @RequestMapping(value = "/api/0.9/json/merchant/order/catalogue", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveOrderCatalogue(@Valid OrderCatalogue orderCatalogue,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the orderCatalogue
        orderCatalogue.setOrcMerchantNo(merchantNo);


        // Log the Request
        log.info("saveOrderCatalogue - Request Received# "+orderCatalogue.toString());
        log.info("saveOrderCatalogue -  "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveOrderCatalogue - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }



        // If the orderCatalogue.getOrcProductNo is  null, then set the created_by, else set the updated_by
        if ( orderCatalogue.getOrcProductNo() == null ) {

            orderCatalogue.setCreatedBy(auditDetails);

        } else {

            orderCatalogue.setUpdatedBy(auditDetails);

        }


        // save the orderCatalogue object and get the result
        orderCatalogue = orderCatalogueService.validateAndSaveOrderCatalogue(orderCatalogue);

        // If the orderCatalogue object is not null ,then return the success object
        if ( orderCatalogue.getOrcProductNo() != null ) {

            // Get the orderCatalogue id
            retData.setData(orderCatalogue.getOrcProductNo());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveOrderCatalogue - Response : Unable to save the orderCatalogue information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveOrderCatalogue - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/order/catalogue/delete/{orcProductNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteOrderCatalogue(@PathVariable(value="orcProductNo") Long orcProductNo) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteOrderCatalogue - Request Received# OrderCatalogue ID: "+orcProductNo);
        log.info("deleteOrderCatalogue -  "+generalUtils.getLogTextForRequest());

        // Get the orderCatalogue information
        OrderCatalogue orderCatalogue = orderCatalogueService.findByOrcProductNo(orcProductNo);

        // Check if the orderCatalogue is found
        if ( orderCatalogue == null || orderCatalogue.getOrcProductNo() == null) {

            // Log the response
            log.info("deleteOrderCatalogue - Response : No orderCatalogue information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( orderCatalogue.getOrcMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteOrderCatalogue - Response : You are not authorized to delete the orderCatalogue");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the orderCatalogue and set the retData fields
        orderCatalogueService.validateAndDeleteOrderCatalogue(orcProductNo);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete orcProductNo
        retData.setData(orcProductNo);


        // Log the response
        log.info("deleteOrderCatalogue - " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/order/catalogues/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listOrderCatalogues(@PathVariable(value ="filter") String filter,
                                        @PathVariable(value ="query") String query,
                                        Pageable pageable){


        // Log the Request
        log.info("listOrderCatalogues - Request Received# filter "+ filter +" query :" +query );
        log.info("listOrderCatalogues -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Variable holding the orderCatalogue
        List<OrderCatalogueResource> orderCatalogueResourceList = new ArrayList<>(0);

        // Get the details
        Page<OrderCatalogue> orderCataloguePage = orderCatalogueService.searchOrderCatalogues(merchantNo,authSessionUtils.getUserLocation(),filter,query,pageable);

        // Convert to resrouce
        orderCatalogueResourceList = orderCatalogueAssembler.toResources(orderCataloguePage);

        // Set the data
        retData.setData(orderCatalogueResourceList);


        // Log the response
        log.info("listOrderCatalogues - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/order/catalogue/{orcProductNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getOrderCatalogueInfo(@PathVariable(value="orcProductNo") Long orcProductNo) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getOrderCatalogueInfo - Request Received# OrderCatalogue ID: "+orcProductNo);
        log.info("getOrderCatalogueInfo -  "+generalUtils.getLogTextForRequest());

        // Get the orderCatalogue information
        OrderCatalogue orderCatalogue = orderCatalogueService.findByOrcProductNo(orcProductNo);

        // Check if the orderCatalogue is found
        if ( orderCatalogue == null || orderCatalogue.getOrcProductNo() == null) {

            // Log the response
            log.info("getOrderCatalogueInfo - Response : No orderCatalogue information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( orderCatalogue.getOrcMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getOrderCatalogueInfo - Response : You are not authorized to view the orderCatalogue");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // Convert the OrderCatalogue to OrderCatalogueResource
        OrderCatalogueResource orderCatalogueResource = orderCatalogueAssembler.toResource(orderCatalogue);

        // Get the image for the secondary image
        Image image = imageService.findByImgImageId(orderCatalogue.getOrcSecondaryProductImage());

        // If the image is not null, set the path
        if ( image != null ) {

            // Set the secondardImagePath
            orderCatalogueResource.setImgSecondaryPath(imageService.getPathForImage(image, ImagePathType.STANDARD));

        }



        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the orderCatalogueResource object
        retData.setData(orderCatalogueResource);




        // Log the response
        log.info("getOrderCatalogueInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

}

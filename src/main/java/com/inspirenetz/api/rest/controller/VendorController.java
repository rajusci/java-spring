package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.Vendor;
import com.inspirenetz.api.core.service.VendorService;
import com.inspirenetz.api.rest.assembler.VendorAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.VendorResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
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
public class VendorController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorAssembler vendorAssembler;

    @Autowired
    GeneralUtils generalUtils;



    @RequestMapping(value = "/api/0.9/json/merchant/vendor", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveVendor(@Valid Vendor vendor,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = AuthSession.getMerchantNo();

        // Set the merchantNo to the vendor
        vendor.setVenMerchantNo(merchantNo);


        // Log the Request
        log.info("saveVendor - Request Received# "+vendor.toString());
        log.info("saveVendor - "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = AuthSession.getUserNo().toString() + "#" + AuthSession.getUserLoginId();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveVendor - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }


        // If the vendor.getVenId is  null, then set the created_by, else set the updated_by
        if ( vendor.getVenId() == null ) {

            vendor.setCreatedBy(auditDetails);

        } else {

            vendor.setUpdatedBy(auditDetails);

        }


        // save the vendor object and get the result
        vendor = vendorService.saveVendor(vendor);

        // If the vendor object is not null ,then return the success object
        if ( vendor.getVenId() != null ) {

            // Get the vendor id
            retData.setData(vendor.getVenId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveVendor - Response : Unable to save the vendor information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveVendor - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/vendor/delete/{venId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteVendor(@PathVariable(value="venId") Long venId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = AuthSession.getMerchantNo();

        // Log the Request
        log.info("deleteVendor - Request Received# Vendor ID: "+venId);
        log.info("deleteVendor - "+generalUtils.getLogTextForRequest());

        // Get the vendor information
        Vendor vendor = vendorService.findByVenId(venId);

        // Check if the vendor is found
        if ( vendor == null || vendor.getVenId() == null) {

            // Log the response
            log.info("deleteVendor - Response : No vendor information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( vendor.getVenMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteVendor - Response : You are not authorized to delete the vendor");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the vendor and set the retData fields
        vendorService.deleteVendor(venId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete venId
        retData.setData(venId);


        // Log the response
        log.info("deleteVendor - " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/merchant/vendors/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listVendors(@PathVariable(value ="filter") String filter,
                                        @PathVariable(value ="query") String query,
                                        Pageable pageable){


        // Log the Request
        log.info("listVendors - Request Received# filter "+ filter +" query :" +query );
        log.info("listVendors - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = AuthSession.getMerchantNo();

        // Variable holding the vendor
        List<VendorResource> vendorResourceList;

        // Get the VendorPage
        Page<Vendor> vendorPage = vendorService.searchVendors(merchantNo,filter,query,pageable);

        // Convert the vendorPage to VendorResource List
        vendorResourceList = vendorAssembler.toResources(vendorPage);

        // Set the data
        retData.setData(vendorResourceList);


        // Log the response
        log.info("listVendors - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/vendor/{venId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getVendorInfo(@PathVariable(value="venId") Long venId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = AuthSession.getMerchantNo();

        // Log the Request
        log.info("getVendorInfo - Request Received# Vendor ID: "+venId);
        log.info("getVendorInfo - "+generalUtils.getLogTextForRequest());

        // Get the vendor information
        Vendor vendor = vendorService.findByVenId(venId);

        // Check if the vendor is found
        if ( vendor == null || vendor.getVenId() == null) {

            // Log the response
            log.info("getVendorInfo - Response : No vendor information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( vendor.getVenMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getVendorInfo - Response : You are not authorized to view the vendor");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }




        // Convert the Vendor to VendorResource
        VendorResource vendorResource = vendorAssembler.toResource(vendor);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the vendorResource object
        retData.setData(vendorResource);




        // Log the response
        log.info("getVendorInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }

}

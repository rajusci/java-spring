package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.WalletMerchant;
import com.inspirenetz.api.core.service.WalletMerchantService;
import com.inspirenetz.api.rest.assembler.WalletMerchantAssembler;
import com.inspirenetz.api.rest.resource.WalletMerchantResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.DataValidationUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by sandheepgr on 21/4/14.
 */
@Controller
public class WalletMerchantController {

    @Autowired
    WalletMerchantService walletMerchantService;

    @Autowired
    WalletMerchantAssembler walletMerchantAssembler;

    @Autowired
    private DataValidationUtils dataValidationUtils;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.8/json/walletmerchant/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public APIResponseObject saveWalletMerchant(@Valid WalletMerchant walletMerchant,BindingResult result){

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Set the status to be failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the error messages
            retData.setData(dataValidationUtils.getValidationMessages(result));

            // Set the errorcode to be invalid input
            retData.setErrorCode(APIErrorCode.ERR_INVALID_INPUT);

            // Return the retData object
            return retData;

        }

        // Check if the wallet merchant is existing
        boolean isExist = walletMerchantService.isWalletMerchantExisting(walletMerchant);

        // Check the boolean value
        if ( isExist ) {

            // Set the status to be failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the error messages
            retData.setData("Wallet Merchant already exists");

            // Set the errorcode to be invalid input
            retData.setErrorCode(APIErrorCode.ERR_INVALID_INPUT);

            // Return the retData object
            return retData;

        }

        // save the WalletMerhcant object and get the result
        walletMerchant = walletMerchantService.saveWalletMerchant(walletMerchant);

        // If the WalletMerchant object is not null ,then return the success object
        if ( walletMerchant.getWmtMerchantNo() != null ) {

            // Get the merchant no
            retData.setData(walletMerchant.getWmtMerchantNo());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the data to be WalletMerchant object
            retData.setData(walletMerchant);

            // Set the status to failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the errorcode to ERR_OPERATION_FAILED
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Return the APIResponseobject
        return retData;


    }



    @RequestMapping(value = "/api/0.8/json/walletmerchant/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteWalletMerchant(@Valid WalletMerchant walletMerchant,BindingResult result){

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Set the status to be failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the error messages
            retData.setData(dataValidationUtils.getValidationMessages(result));

            // Set the errorcode to be invalid input
            retData.setErrorCode(APIErrorCode.ERR_INVALID_INPUT);

            // Return the retData object
            return retData;

        }


        // Get the wallet merchant information for given wallet merchant no
        WalletMerchant delWalletMerchant = walletMerchantService.findByWmtMerchantNo(walletMerchant.getWmtMerchantNo());

        // Check if the wmtMerchantNo is not null
        if ( walletMerchant.getWmtMerchantNo() != null) {

            // Delete the wallet merchant and set the retData fields
            walletMerchantService.deleteWalletMerchant(walletMerchant.getWmtMerchantNo());

            // Set the retData to success
            retData.setStatus(APIResponseStatus.success);

            // Set the data to the deleted merchantno
            retData.setData(delWalletMerchant.getWmtMerchantNo());

        } else {

            // Set the retData to failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the data to the error message
            retData.setData("No walletMerchant found with the given walletMerchant code");

        }


        // Return the retdata object
        return retData;
    }



    @RequestMapping(value = "/api/0.8/json/walletmerchant/search/name", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
            params={"query"}
    )
    @ResponseBody
    @Secured({"ROLE_ADMIN"})
    public APIResponseObject getWalletMerchantByName(@RequestParam(value = "query") String query,Pageable pageable){


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching the names
        Page<WalletMerchant> walletMerchantList = walletMerchantService.findByWmtNameLike("%" + query + "%",pageable);

        // Get the list of the walletMerchantResource
        List<WalletMerchantResource> walletMerchantResourceList = walletMerchantAssembler.toResources(walletMerchantList);

        // Set the data
        retData.setData(walletMerchantResourceList);

        // Return the success object
        return retData;

    }



    @RequestMapping(value = "/api/0.8/json/walletmerchant/search/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
            params={"query"}
    )
    @ResponseBody
    @Secured({"ROLE_ADMIN"})
    public APIResponseObject getWalletMerchantByLocation(@RequestParam(value = "query") int query,Pageable pageable){


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching the names
        Page<WalletMerchant> walletMerchantList = walletMerchantService.findByWmtLocation(query,pageable);

        // Get the list of the walletMerchantResource
        List<WalletMerchantResource> walletMerchantResourceList = walletMerchantAssembler.toResources(walletMerchantList);

        // Set the data
        retData.setData(walletMerchantResourceList);

        // Return the success object
        return retData;

    }



    @RequestMapping(value = "/api/0.8/json/walletmerchant/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    @Secured({"ROLE_ADMIN"})
    public APIResponseObject listWalletMerchants(Pageable pageable){


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching the names
        Page<WalletMerchant> walletMerchantList = walletMerchantService.findAll(pageable);

        // Get the list of the walletMerchantResource
        List<WalletMerchantResource> walletMerchantResourceList = walletMerchantAssembler.toResources(walletMerchantList);

        // Set the data
        retData.setData(walletMerchantResourceList);

        // Return the success object
        return retData;

    }

}

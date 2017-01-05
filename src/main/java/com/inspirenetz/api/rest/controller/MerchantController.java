package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.Role;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.rest.assembler.MerchantAssembler;
import com.inspirenetz.api.rest.assembler.MerchantProfileAssembler;
import com.inspirenetz.api.rest.assembler.MerchantPublicAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.MerchantProfileResource;
import com.inspirenetz.api.rest.resource.MerchantPublicResource;
import com.inspirenetz.api.rest.resource.MerchantResource;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class MerchantController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(MerchantController.class);

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantAssembler merchantAssembler;

    @Autowired
    private MerchantPublicAssembler merchantPublicAssembler;

    @Autowired
    private MerchantProfileAssembler merchantProfileAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    private Mapper mapper;

    @RequestMapping(value = {"/api/0.9/json/admin/merchant"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveMerchant(@Valid Merchant merchant,BindingResult result) throws InspireNetzException {

        // As this is a admin function, check if the user requesting is admin
        merchantService.checkAdminRequestValid(authSessionUtils.getUserType());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("saveMerchant - Request Received# "+merchant.toString());
        log.info("saveMerchant - "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveMerchant - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // Check if the merchant is existing
        boolean isExist = merchantService.isDuplicateMerchantExist(merchant);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveMerchant - Response : Merchant already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the merchant.getMerMerchantNo is  null, then set the created_by, else set the updated_by
        if ( merchant.getMerMerchantNo() == null ) {

            merchant.setCreatedBy(auditDetails);

        } else {

            merchant.setUpdatedBy(auditDetails);

        }


        // save the merchant object and get the result
        merchant = merchantService.validateAndSaveMerchant(merchant);

        // If the merchant object is not null ,then return the success object
        if ( merchant.getMerMerchantNo() != null ) {

            // Get the merchant id
            retData.setData(merchant.getMerMerchantNo());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveMerchant - Response : Unable to save the merchant information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveMerchant - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }

    /*

    @RequestMapping(value = "/api/0.9/json/admin/merchant/delete/{merMerchantNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteMerchant(
            @PathVariable("merMerchantNo") Long merMerchantNo
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deleteMerchant - Request Received# "+merMerchantNo);
        log.info("deleteMerchant - Requested User  # Login Id : "+authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());



        // Get the merchant information for the given merchantCode and merhcantNo
        Merchant delMerchant = merchantService.findByMerMerchantNo(merMerchantNo);

        // Check if the merMerchantNo is not null
        if ( delMerchant.getMerMerchantNo() != null) {

            // Delete the merchant and set the retData fields
            merchantService.deleteMerchant(delMerchant.getMerMerchantNo());

            // Set the retData to success
            retData.setStatus(APIResponseStatus.success);

            // Set the data to the delete merMerchantNo
            retData.setData(delMerchant.getMerMerchantNo());

        } else {

            // Log the response
            log.info("deleteMerchant - Response : Not merchant information found ");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Log the response
        log.info("deleteMerchant - Response : " + retData.toString());


        // Return the retdata object
        return retData;
    }

    */




    @RequestMapping(value = "/api/0.9/json/admin/merchants/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchants( @PathVariable(value = "filter") String filter,
                                            @PathVariable(value = "query")  String query,
                                            Pageable pageable) throws InspireNetzException {


        // As this is a admin function, check if the user requesting is admin
        merchantService.checkAdminRequestValid(authSessionUtils.getUserType());


        // Log the Request
        log.info("listMerchants - Request Received# ");
        log.info("listMerchants - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching merchants
        Page<Merchant> merchantList = merchantService.searchMerchants(filter,query,pageable);

        // Get the list of the merchantResources
        List<MerchantResource> merchantResourceList = merchantAssembler.toResources(merchantList);

        // Set the data
        retData.setData(merchantResourceList);

        // Log the response
        log.info("listMerchants - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/admin/merchant/{merMerchantNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getMerchantInfo(@PathVariable(value = "merMerchantNo") Long merMerchantNo) throws InspireNetzException {

        // As this is a admin function, check if the user requesting is admin
        merchantService.checkAdminRequestValid(authSessionUtils.getUserType());


        // Log the Request
        log.info("getMerchantInfo - Request Received# Merchant No :"+merMerchantNo);
        log.info("getMerchantInfo - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant information
        Merchant merchant = merchantService.findByMerMerchantNo(merMerchantNo);

        // check if the merchant information was fetched
        if ( merchant == null ) {

            // Log the response
            log.info("getMerchantInfo - Response : Not merchant information found ");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }



        // Convert the Entity to Resource
        MerchantResource merchantResource = merchantAssembler.toResource(merchant);

        // Set the data as MerchantResource object
        retData.setData(merchantResource);

        // Log the response
        log.info("getMerchantInfo - " + generalUtils.getLogTextForResponse(retData));




        // Return the success object
        return retData;

    }


    @RequestMapping(value = {"/api/0.9/json/merchant/profile"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getMerchantProfile() throws InspireNetzException {

        // Log the Request
        log.info("getMerchantProfile - Request Received# ");
        log.info("getMerchantProfile - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the profile for the currently logged in merchantno
        Merchant merchant = merchantService.findByMerMerchantNo(merchantNo);


        // Check the merchant information
        if ( merchant == null ) {

            // Log the response
            log.info("getMerchantProfile - Merchant Information not found");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Convert the merchant MerchantProfileResource
        // this is to exclude the admin specific fields and show only thhe merchant
        // editable fields
        MerchantProfileResource merchantProfileResource = merchantProfileAssembler.toResource(merchant);

        // Set the data
        retData.setData(merchantProfileResource);;

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);



        // Log the response
        log.info("getMerchantProfile - " + generalUtils.getLogTextForResponse(retData));

        // Return the retData
        return retData;

    }


    @RequestMapping(value = {"/api/0.9/json/merchant/profile"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveMerchantProfile(@RequestBody Map<String,Object> params) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Log the Request
        log.info("saveMerchantProfile - Request Received# "+params.toString());
        log.info("saveMerchantProfile - "+generalUtils.getLogTextForRequest());


        // Get the merchantNumber of the merchant user requesting the information
        Long merchantNo = authSessionUtils.getMerchantNo();

        Merchant merchant = mapper.map(params,Merchant.class);

        // Get the information for the merchant
        Merchant baseMerchant = merchantService.findByMerMerchantNo(merchantNo);


        // Check the merchant information
        if ( baseMerchant == null ) {

            // Log the response
            log.info("saveMerchantProfile - Merchant Information not found");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Copy the merchant editable fields from merchant to baseMerchant
        baseMerchant.setMerMerchantName(merchant.getMerMerchantName());

        baseMerchant.setMerAddress1(merchant.getMerAddress1());

        baseMerchant.setMerAddress2(merchant.getMerAddress2());

        baseMerchant.setMerAddress3(merchant.getMerAddress3());

        baseMerchant.setMerCity(merchant.getMerCity());

        baseMerchant.setMerState(merchant.getMerState());

        baseMerchant.setMerCountry(merchant.getMerCountry());

        baseMerchant.setMerPostCode(merchant.getMerPostCode());

        baseMerchant.setMerContactName(merchant.getMerContactName());

        baseMerchant.setMerContactEmail(merchant.getMerContactEmail());

        baseMerchant.setMerPhoneNo(merchant.getMerPhoneNo());

        baseMerchant.setMerEmail(merchant.getMerEmail());

        baseMerchant.setMerSignupType(merchant.getMerSignupType());

        baseMerchant.setMerAutoSignupEnable(merchant.getMerAutoSignupEnable());

        baseMerchant.setMerMembershipName(merchant.getMerMembershipName());

        baseMerchant.setMerPaymentModes(merchant.getMerPaymentModes());

        baseMerchant.setMerCustIdTypes(merchant.getMerCustIdTypes());

        baseMerchant.setMerMerchantImage(merchant.getMerMerchantImage());

        baseMerchant.setMerCoverImage(merchant.getMerCoverImage());

        baseMerchant.setMerSignupRewardEnabledInd(merchant.getMerSignupRewardEnabledInd());

        baseMerchant.setMerSignupRewardCurrency(merchant.getMerSignupRewardCurrency());

        baseMerchant.setMerSignupRewardPoints(merchant.getMerSignupRewardPoints());

        baseMerchant.setMerchantLocationSet(merchant.getMerchantLocationSet());


        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Set the updatedBy field value
        baseMerchant.setUpdatedBy(auditDetails);


        // Create the bindingREsult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(merchant,"merchant");

       // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveMerchantProfile - Response : Invalid Input");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // save the merchant object and get the result
        baseMerchant = merchantService.saveMerchant(baseMerchant);

        // If the merchant object is not null ,then return the success object
        if ( baseMerchant.getMerMerchantNo() != null ) {

            // Get the merchant id
            retData.setData(baseMerchant.getMerMerchantNo());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("saveMerchant - Response : Unable to save the merchant information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("saveMerchant - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }

    @RequestMapping(value = {"/api/0.9/json/merchant/admin/save"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveMerchantForAdmin(@RequestBody Map<String,Object> params) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveMerchantForAdmin - Request Received# "+params.toString());
        log.info("saveMerchantForAdmin - "+generalUtils.getLogTextForRequest());


        //map merchant object
        Merchant merchant = mapper.map(params,Merchant.class);

        //check duplicate
        // Check if the merchant is existing
        boolean isExist = merchantService.isDuplicateMerchantExist(merchant);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveMerchantForAdmin - Response : Merchant already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }

        merchant=merchantService.validateAndSaveMerchant(merchant);

        // Log the response
        log.info("saveMerchant - " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/public/merchants", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getActiveMerchants( @RequestParam(value = "query",defaultValue = "") String query,
                                                 @RequestParam(value = "merchantNo",defaultValue = "0") Long merchantNo,
                                            Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listMerchants - Request Received #query :"+query);
        //log.info("listMerchants - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the list of matching merchants
        Page<Merchant> merchantPage = merchantService.getActiveMerchants(merchantNo,query, pageable);

        // Get the list of the merchantPublicResource
        List<MerchantPublicResource> merchantPublicResourceList = merchantPublicAssembler.toResources(merchantPage);

        // Set the data
        retData.setData(merchantPublicResourceList);

        // Log the response
        log.info("listMerchants - Response" + retData);


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/user/userconnect/merchants", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getConnectedMerchant(@RequestParam(value="merchantno",defaultValue ="0") Long merchantNo) throws InspireNetzException {



        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        log.info("getConnectedMerchant - "+generalUtils.getLogTextForRequest());

        // Get the list of matching merchants
        List<Merchant> ListConnectedMerchant = merchantService.getConnectedMerchant(merchantNo);

        // Get the list of the merchantPublicResource
        List<MerchantPublicResource> merchantPublicResourceList = merchantPublicAssembler.toResources(ListConnectedMerchant);

        // Set the data
        retData.setData(merchantPublicResourceList);

        // Log the response
        log.info("listMerchants - Response" + retData);


        // Return the success object
        return retData;


    }

    @RequestMapping(value = {"/api/0.9/json/customer/merchantprofile/{merchantNo}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getMerchantProfileInfo(@PathVariable("merchantNo") Long merchantNo) throws InspireNetzException {

        // Log the Request
        log.info("getMerchantProfileInfo - Request Received# merchantNo :"+merchantNo);
        log.info("getMerchantProfileInfo - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the profile for the currently logged in merchantno
        Merchant merchant = merchantService.findActiveMerchantsByMerMerchantNo(merchantNo);


        // Check the merchant information
        if ( merchant == null ) {

            // Log the response
            log.info("getMerchantProfileInfo - Merchant Information not found");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Convert the merchant MerchantProfileResource
        // this is to exclude the admin specific fields and show only thhe merchant
        // editable fields
        MerchantProfileResource merchantProfileResource = merchantProfileAssembler.toResource(merchant);

        // Set the data
        retData.setData(merchantProfileResource);;

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);



        // Log the response
        log.info("getMerchantProfileInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retData
        return retData;

    }

    @RequestMapping(value = {"/api/0.9/json/customer/merchant/profile/{merUrlName}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getMerchantDetailsByUrl(@PathVariable("merUrlName") String merUrlName) throws InspireNetzException {

        // Log the Request
        log.info("getMerchantDetailsByUrl - Request Received# Merchant Url Name :"+merUrlName);
        //log.info("getMerchantDetailsByUrl - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the profile for the currently logged in merchantno
        Merchant merchant = merchantService.findByMerUrlName(merUrlName);


        // Check the merchant information
        if ( merchant == null ) {

            // Log the response
            log.info("getMerchantDetailsByUrl - Merchant Information not found");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Convert the merchant MerchantProfileResource
        // this is to exclude the admin specific fields and show only thhe merchant
        // editable fields
        MerchantResource merchantResource = merchantAssembler.toResource(merchant);

        // Set the data
        retData.setData(merchantResource);

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);



        // Log the response
        log.info("getMerchantDetailsByUrl - " + generalUtils.getLogTextForResponse(retData));

        // Return the retData
        return retData;

    }

}

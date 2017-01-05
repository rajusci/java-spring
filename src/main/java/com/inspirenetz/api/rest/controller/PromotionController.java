package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.Promotion;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.UserResponse;
import com.inspirenetz.api.core.domain.validator.PromotionValidator;
import com.inspirenetz.api.core.service.PromotionService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.assembler.PromotionAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.PromotionResource;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class PromotionController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(PromotionController.class);

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PromotionAssembler promotionAssembler;

    @Autowired
    private UserService userService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;


   /* @RequestMapping(value = "/api/0.9/json/merchant/promotion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject savePromotion(@Valid Promotion promotion,BindingResult result) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the promotion
        promotion.setPrmMerchantNo(merchantNo);


        // Log the Request
        log.info("savePromotion - Request Received# "+promotion.toString());
        log.info("savePromotion -  "+generalUtils.getLogTextForRequest());

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Create the PromotionValidator object
        PromotionValidator validator = new PromotionValidator(generalUtils);

        // Validate the result
        validator.validate(promotion,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("savePromotion - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // Check if the promotion is existing
        boolean isExist = promotionService.isDuplicatePromotionExisting(promotion);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("savePromotion - Response : Promotion name is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the promotion.getPrmId is  null, then set the created_by, else set the updated_by
        if ( promotion.getPrmId() == null ) {

            promotion.setCreatedBy(auditDetails);

        } else {

            promotion.setUpdatedBy(auditDetails);

        }


        // save the promotion object and get the result
        promotion = promotionService.validateAndSavePromotion(promotion);

        // If the promotion object is not null ,then return the success object
        if ( promotion.getPrmId() != null ) {

            // Get the promotion id
            retData.setData(promotion.getPrmId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("savePromotion - Response : Unable to save the promotion information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("savePromotion -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }
*/

    @RequestMapping(value = "/api/0.9/json/merchant/promotion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject savePromotion(PromotionResource promotionResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("savePromotion - Request Received# "+promotionResource.toString());
        log.info("savePromotion -  "+generalUtils.getLogTextForRequest());

        Promotion promotion=promotionService.validateAndSavePromotion(promotionResource);



        // If the promotion object is not null ,then return the success object
        if ( promotion.getPrmId() != null ) {

            // Get the promotion id
            retData.setData(promotion.getPrmId());

            // Set the status to succes
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Log the response
            log.info("savePromotion - Response : Unable to save the promotion information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the response
        log.info("savePromotion -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/promotion/delete/{prmId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deletePromotion(@PathVariable( value = "prmId") Long prmId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("deletePromotion - Request Received# Promotion Id:"+ prmId);
        log.info("deletePromotion -  "+generalUtils.getLogTextForRequest());


        // Get the promotion information
        Promotion promotion = promotionService.findByPrmId(prmId);

        // If no data found, then set error
        if ( promotion == null ) {

            // Log the response
            log.info("deletePromotion - Response : No promotion information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Check if the passed object's merchant number and the current
        // authentiprmed merchant number are same.
        // We need to raise an error if not
        if ( promotion.getPrmMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deletePromotion - Response : You are not authorized to delete the promotion");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // Delete the promotion and set the retData fields
        promotionService.validateAndDeletePromotion(prmId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete prmId
        retData.setData(prmId);


        // Log the response
        log.info("deletePromotion -  " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }



    @RequestMapping(value = "/api/0.9/json/merchant/promotions/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listPromotions(@PathVariable(value ="filter") String filter,
                                            @PathVariable(value ="query") String query,
                                            Pageable pageable){


        // Log the Request
        log.info("listPromotions - Request Received# ");
        log.info("listPromotions -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        Page<Promotion> promotionList = null;


        // Check the filter and query
        if ( filter.equals("name") ) {

            // Get the list of matching promotions
            promotionList = promotionService.findByPrmMerchantNoAndPrmNameLike(merchantNo, "%" + query + "%", pageable);


        } else {

            // Get the list of matching promotions
            promotionList = promotionService.findByPrmMerchantNo(merchantNo,pageable);


        }

        // Get the list of the promotionResources
        List<PromotionResource> promotionResourceList = promotionAssembler.toResources(promotionList);

        // Set the data
        retData.setData(promotionResourceList);

        // set the pageableparams
        retData.setPageableParams(promotionList);



        // Log the response
        log.info("listPromotions -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/customer/promotions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listPromotionsForCustomer(Pageable pageable){


        // Log the Request
        log.info("listPromotions - Request Received# ");
        log.info("listPromotions -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the user number
        Long userNo = authSessionUtils.getUserNo();

        // Get the user information for the current user
        User user = userService.findByUsrUserNo(userNo);

        // Get the promotion list for the user
        List<Promotion> promotionList = promotionService.getPromotionsForUser(user);

        // Get the list of the promotionResources
        List<PromotionResource> promotionResourceList = promotionAssembler.toResources(promotionList);

        // Set the data
        retData.setData(promotionResourceList);


        // Log the response
        log.info("listPromotions -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/promotion/{prmId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getPromotionInfo(@PathVariable( value = "prmId") Long prmId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Log the Request
        log.info("getPromotionInfo - Request Received# Promotion Id:"+ prmId);
        log.info("getPromotionInfo -  "+generalUtils.getLogTextForRequest());


        // Get the promotion information
        Promotion promotion = promotionService.findByPrmId(prmId);

        // If no data found, then set error
        if ( promotion == null ) {

            // Log the response
            log.info("getPromotionInfo - Response : No promotion information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Check if the passed object's merchant number and the current
        // authentiprmed merchant number are same.
        // We need to raise an error if not
        if ( promotion.getPrmMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getPromotionInfo - Response : You are not authorized to delete the promotion");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // Convert the Promotion to PromotionResource
        PromotionResource promotionResource = promotionAssembler.toResource(promotion);

        // Set the resource in the retData
        retData.setData(promotionResource);


        // Log the response
        log.info("getPromotionInfo -  " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/customer/promotionview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject savePromotionView(@RequestBody Map<String,Object> params) throws InspireNetzException {


        // Log the Request
        log.info("saveMerchantUser - Request Received# ");
        log.info("saveMerchantUser -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();



        // convert the promotion map  to object
        UserResponse userResponse = mapper.map(params,UserResponse.class);

        Promotion promotion =promotionService.updatePromotionView(userResponse);

        // Get the CataLogueFavorite id
        retData.setData(promotion.getPrmId());
        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("savePromotionView-  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/customer/promotionResponse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject savePromotionResponse(@RequestBody Map<String,Object> params) throws InspireNetzException {


        // Log the Request
        log.info("saveMerchantUser - Request Received# ");
        log.info("saveMerchantUser -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();



        // convert the promotion map  to object
        UserResponse userResponse = mapper.map(params,UserResponse.class);

        Promotion promotion =promotionService.updatePromotionResponse(userResponse);

        // Get the CataLogueFavorite id
        retData.setData(promotion.getPrmId());
        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("savePromotionView-  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }

    @RequestMapping(value = "api/0.9/json/customer/compatible/promotion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public APIResponseObject getCompatiblePromotion(@RequestParam(value="merchant_no",defaultValue = "1") Long merchantNo) throws InspireNetzException {


        // Log the Request
        log.info("getCompatiblePromotion - Request Received# MerchantNo: "+merchantNo);
        log.info("getCompatiblePromotion -  "+generalUtils.getLogTextForRequest());



        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        List<Promotion> promotionList =promotionService.getComptablePromotion(merchantNo);


        // Get the CataLogueFavorite id
        retData.setData(promotionList);
        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getCompatiblePromotion :  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/public/promotions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public APIResponseObject getPublicPromotion(
            @RequestParam(value = "query",defaultValue = "") String query,
            @RequestParam(value="prmMerchantNo",defaultValue = "0") Long prmMerchantNo,Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("getPublicPromotion - Request Received# MerchantNo: "+prmMerchantNo);
        //log.info("getPublicPromotion -  "+generalUtils.getLogTextForRequest());



        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Variable holding the response resource list
        List<PromotionResource> promotionResourceList = new ArrayList<>(0);

        // Get the data for all the public promotions
        Page<Promotion> promotionPage =promotionService.getPublicPromotions(prmMerchantNo,query,pageable);

        // Convert to promotionResourceList
        promotionResourceList = promotionAssembler.toResources(promotionPage);


        // Set the data
        retData.setData(promotionResourceList);

        // Set the status to success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        //log.info("getPublicPromotion :  " + generalUtils.getLogTextForResponse(retData));
        log.info("getPublicPromotion - Response: "+retData);

        // Return the APIResponseobject
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/user/promotions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listPromotionsForUser(
                                                   @RequestParam(value = "query",defaultValue = "") String query,
                                                   @RequestParam(value="merchantNo",defaultValue = "0") Long merchantNo,Pageable pageable){


        // Log the Request
        log.info("listPromotions - Request Received# ");
        log.info("listPromotions -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the user number
        String usrLoginId = authSessionUtils.getUserLoginId();


        // Get the promotion list for the user
        Page<Promotion> promotions = promotionService.getPromotionsForUser(usrLoginId,merchantNo,query,pageable);

        // Get the list of the promotionResources
        List<PromotionResource> promotionResourceList = promotionAssembler.toResources(promotions);

        // Set the data
        retData.setData(promotionResourceList);


        // Log the response
        log.info("listPromotions -  " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


}

package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Role;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.UserResponse;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.assembler.UserAssembler;
import com.inspirenetz.api.rest.assembler.UserDetailsAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.UserDetailsResource;
import com.inspirenetz.api.rest.resource.UserResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import javassist.bytecode.stackmap.BasicBlock;
import org.dozer.Mapper;
import org.drools.lang.dsl.DSLMapParser;
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
public class UserController {

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private UserDetailsAssembler userDetailsAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    CustomerService customerService;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @RequestMapping(value = "/api/0.9/json/user/authenticate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject authenticateUser(){


        // Log the Request
        log.info("authenticateUser - Request Received# ");
        log.info("authenticateUser -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the current user
        Long userNo = authSessionUtils.getUserNo();

        // Get the user information
        User user = userService.findByUsrUserNo(userNo);



        // Get the user as resoruce
        UserResource userResource = userAssembler.toResource(user);

        // Set the response status as successful
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(userResource);


        // Log the response
        log.info("authenticateUser - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveMerchantUser(@RequestBody Map<String,Object> params,@RequestParam(value = "usrMerchantNo",defaultValue = "0") Long usrMerchantNo) throws InspireNetzException {


        // Log the Request
        log.info("saveMerchantUser - Request Received# ");
        log.info("saveMerchantUser -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //merchantNumber
        Long merchantNo=0L;

        // Get the merchant number of the merchant admin
        if(authSessionUtils.getUserType()==UserType.SUPER_ADMIN ||authSessionUtils.getUserType()==UserType.ADMIN){

            //set merchant number
            merchantNo =usrMerchantNo;

        }else {

            merchantNo =authSessionUtils.getMerchantNo();

        }


        // Get the current user type
        Integer userType = authSessionUtils.getUserType();

        // convert the LInkRequestResource to object
        User user = mapper.map(params,User.class);

//        // If the userType is not merchant admin, then we need to throw unauthorized error
//        if ( userType != UserType.MERCHANT_ADMIN ) {
//
//            // Log the response
//            log.info("saveMerchantUser - Response : You are not authorized to create a merchant user");
//
//            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
//            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);
//
//        }


        // Create the bindingREsult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(user,"user");
        // If there are validation errors, show the errors
        if ( result.hasErrors() ) {

            // Log the response
            log.info("saveMerchantUser - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }



        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // Set the user merchantNo to the merchant number of the admin user
        user.setUsrMerchantNo(merchantNo);

        //if the save request is for user type redemption merchant set the user type
        if(user.getUsrUserType() == UserType.REDEMPTION_MERCHANT_USER){

            user.setUsrUserType(UserType.REDEMPTION_MERCHANT_USER);

            //user.setUsrThirdPartyVendorNo(au);

        }

        // Set the auditDetails based on the type of the request
        if ( user.getUsrUserNo() == null || user.getUsrUserNo() == 0L ) {

            user.setCreatedBy(auditDetails);

        } else {

            user.setUpdatedBy(auditDetails);

        }

        // Save the user
        user = userService.validateAndSaveUser(user);

        // Check if the user is null
        if ( user  == null || user.getUsrUserNo() == null ) {

            // Log the response
            log.info("saveMerchantUser - Response : Unable to save the user  information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Set the response status as successful
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(user.getUsrUserNo());


        // Log the response
        log.info("saveMerchantUser - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/merchant/user/delete/{usrUserNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteMerchantUser(@PathVariable(value="usrUserNo") Long usrUserNo) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the user Type
        Integer userType = authSessionUtils.getUserType();


        // Log the Request
        log.info("deleteMerchantUser - Request Received# User ID: "+usrUserNo);
        log.info("deleteMerchantUser -  "+generalUtils.getLogTextForRequest());

        // Get the user information
        User user = userService.findByUsrUserNo(usrUserNo);

        // Check if the user is found
        if ( user == null || user.getUsrUserNo() == null) {

            // Log the response
            log.info("deleteMerchantUser - Response : No user information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
//        if ( user.getUsrMerchantNo().longValue() != merchantNo || userType != UserType.MERCHANT_ADMIN ) {
//
//            // Log the response
//            log.info("deleteMerchantUser - Response : You are not authorized to delete the user");
//
//            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
//            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);
//
//        }


        // Delete the user and set the retData fields
        userService.validateAndDeleteUser(user);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the delete usrUserNo
        retData.setData(usrUserNo);



        // Log the response
        log.info("deleteUser - " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }



    @RequestMapping(value = "/api/0.9/json/merchant/users/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantUsers( @PathVariable(value ="filter") String filter,
                                                @PathVariable(value ="query") String query,
                                                Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listMerchantUsers - Request Received# filter "+ filter +" query :" +query );
        log.info("listMerchantUsers -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the userType
        Integer userType = authSessionUtils.getUserType();

        //check access rights permission
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_VIEW_MERCHANT_USERS);

        // Get the UserPage
        Page<User> userPage = userService.searchMerchantUsers(merchantNo, UserType.MERCHANT_USER, filter, query, pageable);

        // Convert the User to UserResouce
        List<UserResource> userResourceList = userAssembler.toResources(userPage);

        // Set the data
        retData.setData(userResourceList);

        // Log the response
        log.info("listMerchantUsers - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/redemptionusers/{usrthirdpartyvendorno}/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listRedemptionMerchantUsers( @PathVariable(value = "usrthirdpartyvendorno") Long usrThirdPartyVendorNo,@PathVariable(value ="filter") String filter,
                                                          @PathVariable(value ="query") String query,
                                                          Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listRedemptionMerchantUsers - Request Received# filter "+ filter +" query :" +query );
        log.info("listRedemptionMerchantUsers -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the userType
        Integer userType = authSessionUtils.getUserType();

        // Get the UserPage
        Page<User> userPage = userService.searchRedemptionMerchantUsers(merchantNo,usrThirdPartyVendorNo, filter, query, pageable);

        // Convert the User to UserResouce
        List<UserResource> userResourceList = userAssembler.toResources(userPage);




        // Set the data
        retData.setData(userResourceList);

        // Log the response
        log.info("listRedemptionMerchantUsers - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/user/{usrLoginId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getMerchantUserInfo(@PathVariable(value="usrLoginId") String usrLoginId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the userType
        Integer userType = authSessionUtils.getUserType();


        // Log the Request
        log.info("getMerchantUserInfo - Request Received# User ID: "+usrLoginId);
        log.info("getMerchantUserInfo -  "+generalUtils.getLogTextForRequest());

        // Get the user information
        User user = userService.findByUsrLoginId(usrLoginId);

        // Check if the user is found
        if ( user == null || user.getUsrUserNo() == null) {

            // Log the response
            log.info("getMerchantUserInfo - Response : No user information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Convert the User to UserResource
        UserResource userResource = userAssembler.toResource(user);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the userResource object
        retData.setData(userResource);

        // Log the response
        log.info("getMerchantUserInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/user/details/{usrUserNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getMerchantUserWithAccessData(@PathVariable(value="usrUserNo") Long usrUserNo) throws InspireNetzException {


        //check the user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_MERCHANT_USER);

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the userType
        Integer userType = authSessionUtils.getUserType();


        // Log the Request
        log.info("getMerchantUserInfo - Request Received# User ID: "+usrUserNo);
        log.info("getMerchantUserInfo -  "+generalUtils.getLogTextForRequest());

        // Get the user information
        User user = userService.getUserDataByUsrUserNo(usrUserNo);

        // Check if the user is found
        if ( user == null || user.getUsrUserNo() == null) {

            // Log the response
            log.info("getMerchantUserInfo - Response : No user information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Convert the User to userDetailsresource
        UserDetailsResource userDetailsResource = userDetailsAssembler.toResource(user);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the userDetails Resource object
        retData.setData(userDetailsResource);




        // Log the response
        log.info("getMerchantUserInfo - " + generalUtils.getLogTextForResponse(retData));

        // Return the retdata object
        return retData;


    }
    @RequestMapping(value = "/api/0.9/json/merchant/redemptionusers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRedemptionMerchantUsers(Pageable pageable) throws InspireNetzException {


        // Log the Request
        log.info("listRedemptionMerchantUsers -  "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the userType
        Integer userType = authSessionUtils.getUserType();


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( userType != UserType.MERCHANT_ADMIN ) {

            // Log the response
            log.info("listRedemptionMerchantUsers - Response : You are not authorized to get the redemption merchant users");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Get the UserPage
        Page<User> userPage = userService.getRedemptionMerchantUsers(pageable);

        // Convert the User to UserResouce
        List<UserResource> userResourceList = userAssembler.toResources(userPage);

        // Set the data
        retData.setData(userResourceList);

        // Log the response
        log.info("listRedemptionMerchantUsers - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/customer/forgetpassword",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updateCustomerPassword(@RequestParam(value = "loyaltyid") String usrLoginId, @RequestParam(value = "password") String newPassword,@RequestParam(value = "otpcode") String otpCode) throws InspireNetzException {


        // Log the Request
        log.info("updateCustomerPassword - Request Received# ");
        /*log.info("updateCustomerPassword -  "+generalUtils.getLogTextForRequest());*/

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //call update userPassword
        User user =userService.forgetPasswordUpdation(usrLoginId,newPassword,otpCode);

        // Set the response status as successful
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(user.getUsrUserNo());

        // Log the response
        log.info("updateCustomerPassword- " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;

    }
    @RequestMapping(value = "/api/0.9/json/user/registrationstatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRegistrationStatus(@RequestParam(value = "loyaltyId") String loyaltyId) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the UserPage
        User user = userService.getRegistrationStatus(loyaltyId);

        // Convert the User to UserResouce
        UserResource userResource = userAssembler.toResource(user);

        // Set the data
        retData.setData(userResource);

        // Log the response
        log.info("getRegistrationStatus - " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/forgetpassword/generateotp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject generateOtp(@RequestParam(value = "loyaltyid") String usrLoginId) throws InspireNetzException {

        // Log the Request
        log.info("generateotp - Request Received# ");

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //call update userPassword
        boolean isGenerate =userService.generateOtp(usrLoginId);

        // Set the response status as successful
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(isGenerate);

        // Log the response
        log.info("generateotp : " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;

    }

    @RequestMapping(value = "/api/0.9/json/merchant/authentication", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject authenticateUserCompatible(){


        // Log the Request
        log.info("authenticateUser - Request Received# ");
        log.info("authenticateUser -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the current user
        Long userNo = authSessionUtils.getUserNo();

        // Get the user information
        User user = userService.findByUsrUserNo(userNo);

        // Get the user as resoruce
        UserResource userResource = userAssembler.toResource(user);


        // Set the response status as successful
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(userResource);


        // Log the response
        log.info("authenticateUser - " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value={"/api/0.9/json/user/register"},method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject registerUser(@RequestParam(value = "firstName",defaultValue = "") String firstName,
                                          @RequestParam(value = "lastName",defaultValue = "") String lastName,
                                          @RequestParam(value = "mobile") String mobile ,
                                          @RequestParam(value = "email",defaultValue = "") String email,
                                          @RequestParam(value = "password",defaultValue = "") String password,
                                          @RequestParam(value = "channel",defaultValue = "2") Integer channel,
                                          @RequestParam(value = "merchantNo",defaultValue = "0") Long merchantNo
    )

            throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("registerUser - Request Received# first name: " + firstName + " - last name : " + lastName +" mobile: "+mobile+" email:"+email);

        // Call the register user method
        boolean isRegistered = userService.registerUser(firstName, lastName ,mobile,email,password,channel,merchantNo);

        //check whether the registration is success
        if(isRegistered){

            // Set the retData object status to success
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the retData object
            retData.setStatus(APIResponseStatus.failed);

        }

        // Log the information
        log.info("registerCustomer - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/user/register/generateotp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject generateUserRegistrationOtp(@RequestParam(value = "userLoginId") String usrLoginId,@RequestParam(value = "merchantNo",defaultValue = "0") Long merchantNo) throws InspireNetzException {

        // Log the Request
        log.info("generateUserRegistrationOtp - Request Received# ");

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        try{
            //call update userPassword
            boolean isGenerate =userService.generateUserRegistrationOtp(usrLoginId,merchantNo);

            // Set the response status as successful
            retData.setStatus(APIResponseStatus.success);

            // Set the data
            retData.setData(isGenerate);
        }
        catch(InspireNetzException ex){

            // Set the response status as failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the data
            retData.setData(false);

            retData.setErrorCode(ex.getErrorCode());

        }

        // Log the response
        log.info("generateUserRegistrationOtp : " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;

    }


    @RequestMapping(value={"/api/0.9/json/user/register/validate"},method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject confirmUserRegister(@RequestParam(value = "userLoginId") String usrLoginId,
                                                 @RequestParam(value = "otpCode") String otpCode,
                                                 @RequestParam(value = "channel",defaultValue = "0") Integer channel,
                                                 @RequestParam(value = "merchantNo",defaultValue = "0") Long merchantNo)
            throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("confirmUserRegister - Request Received# usrLoginId : " + usrLoginId +" OtpCode: "+otpCode+"merchantNo"+merchantNo);

        // Call the register user method
        boolean isRegistered = userService.confirmUserRegistration(usrLoginId, otpCode,channel,merchantNo);

        //check whether the registration is success
        if(isRegistered){

            // Set the retData object status to success
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the retData object
            retData.setStatus(APIResponseStatus.failed);

            //set error code
            retData.setErrorCode(APIErrorCode.ERR_INVALID_OTP);

        }

        // Log the information
        log.info("confirmUserRegister - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/user/forgotpassword/generateotp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject generateForgetUserPasswordOtp(@RequestParam(value = "userLoginId") String usrLoginId,@RequestParam(value = "merchantNo",defaultValue = "0") Long merchantNo) throws InspireNetzException {

        // Log the Request
        log.info("generateForgetPasswordOtp - Request Received# ");

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        try{
            //call update userPassword
            boolean isGenerate =userService.generateForgetPasswordOtp(usrLoginId,merchantNo);

            // Set the response status as successful
            retData.setStatus(APIResponseStatus.success);

            // Set the data
            retData.setData(isGenerate);
        }
        catch(InspireNetzException ex){

            // Set the response status as failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the data
            retData.setData(false);

            retData.setErrorCode(ex.getErrorCode());

        }

        // Log the response
        log.info("generateForgetPasswordOtp : " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;

    }

    @RequestMapping(value = "/api/0.9/json/user/forgotpassword/validate",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject forgetUserPasswordOtpValidation(@RequestParam(value = "userLoginId") String usrLoginId, @RequestParam(value = "password") String newPassword,@RequestParam(value = "otpcode") String otpCode,@RequestParam(value = "merchantNo",defaultValue = "0") Long merchantNo) throws InspireNetzException {


        // Log the Request
        log.info("forgetUserPasswordOtpValidation - Request Received# ");
        /*log.info("updateCustomerPassword -  "+generalUtils.getLogTextForRequest());*/


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        try{

            //call update userPassword
            User user =userService.forgetUserPasswordOtpValidation(usrLoginId,newPassword,otpCode,merchantNo);

            // Set the response status as successful
            retData.setStatus(APIResponseStatus.success);

            // Set the data
            retData.setData(user.getUsrUserNo());

        }catch(InspireNetzException ex){

            // Set the response status as failed
            retData.setStatus(APIResponseStatus.failed);

            // Set the data
            retData.setData(ex.getErrorCode());

        }

        // Log the response
        log.info("forgetUserPasswordOtpValidation- " + generalUtils.getLogTextForResponse(retData));

        // Return the success object
        return retData;


    }

    @RequestMapping(value={"/api/0.9/json/user/memberships/{merchantNo}/{filter}/{query}"},method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getUserMemberShips(@PathVariable(value = "merchantNo") Long merchantNo,
                                                @PathVariable(value = "filter") String filter,
                                                @PathVariable(value = "query") String query)throws InspireNetzException {


        log.info("getUserMemberShips - Request Received" );
        log.info("getUserMemberShips - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        String userLoginId = authSessionUtils.getUserLoginId();

        // Call the get user membership method
        List<Map<String,Object>> membershipData = userService.getUserMemberships(merchantNo,userLoginId,filter,query);

        retData.setData(membershipData);

        // Set the retData object status to success
        retData.setStatus(APIResponseStatus.success);

        // Log the information
        log.info("getUserMemberShips - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/accountinformation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject changeAccountInformation(UserResource userResource) throws InspireNetzException {


        // Log the Request
        log.info("changeAccountInformation - Request Received# ");

        log.info("changeAccountInformation -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //map the user resource
        UserResource userResource1 =mapper.map(userResource, UserResource.class);

        User user =userService.changeAccountInformation(userResource1);

        // Log the response
        log.info("changeAccountInformation - " + generalUtils.getLogTextForResponse(retData));

        //set the status
        retData.setStatus(APIResponseStatus.success);

        //set the user id if not null
        if(user!=null){

            retData.setData(user.getUsrUserNo());
        }

        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/getaccountinformation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getUserAccountInformation() throws InspireNetzException {


        // Log the Request
        log.info("getUserAccountInformation - Request Received# ");

        log.info("getUserAccountInformation -  "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        //find the user details using login id
        User user =userService.findByUsrLoginId(authSessionUtils.getUserLoginId());

        //pick the user details
        UserDetailsResource userDetailsResource =userDetailsAssembler.toResource(user);

        //set the user details
        retData.setData(userDetailsResource);

        //set the response status
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getUserAccountInformation - " + generalUtils.getLogTextForResponse(retData));

        return retData;
    }

    @RequestMapping(value = "/api/0.9/json/customer/otp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject generateOTPForCustomer(
            @RequestParam(value = "merchantNo",required = false,defaultValue = "0") Long merchantNo,
            @RequestParam(value = "userLoginId",required = true) String usrLoginId,
            @RequestParam(value = "otpType",required = false,defaultValue = "0") Integer otpType,
            @RequestParam(value = "otpRefType",required = false,defaultValue = "1") Integer otpRefType
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("generateOTPForCustomer - Request Received# userLoginId = " +usrLoginId + " : otpType = " +otpType + " : otpRefType = " +otpRefType );

        //Generate otpCode
        boolean isGenerated=userService.generateOTPForCustomer(merchantNo,usrLoginId, otpRefType, otpType);

        if(isGenerated){

            retData.setStatus(APIResponseStatus.success);

        }else {

            retData.setStatus(APIResponseStatus.failed);

        }

        // Log the Request
        log.info("generateOTPForCustomer -  "+generalUtils.getLogTextForResponse(retData));

        //return response
        return retData;

    }


    @RequestMapping(value="/api/0.9/json/user/unregister",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject unRegisterUser() throws InspireNetzException {

        // Create the APIResponse Object
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("unRegisterUser Loyalty - Request Received# userNo : " + authSessionUtils.getUserNo());
        log.info("unRegisterUser Loyalty - "+generalUtils.getLogTextForRequest());

        //process unRegister for user
        userService.userOptOutOperation(authSessionUtils.getUserNo());

        // Set the retData object
        retData.setStatus(APIResponseStatus.success);
        // Log the information
        log.info("registerForCustomer Loyalty  - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }

    @RequestMapping(value={"/api/0.9/json/user/register/compatible"},method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject userRegisterCompatible(@RequestParam(value = "username") String loyaltyId,
                                                    @RequestParam(value = "firstname") String firstName,
                                                    @RequestParam(value = "lastname",defaultValue = "") String lastName ,
                                                    @RequestParam(value = "email",defaultValue = "") String email,
                                                    @RequestParam(value = "password") String password ,
                                                    @RequestParam(value = "reg_key") String regKey ,
                                                    @RequestParam(value = "merchantNo",defaultValue = "0")Long merchantNo ) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("userRegisterCompatible - Request Received# Src Loyalty Id : " + loyaltyId + " - Dest loyalty id : " + loyaltyId +" FirstName: "+firstName + " LastName : "+ lastName);

        //check whehter the registration key is valid
        boolean isAuthenticated = customerService.isRegistrationKeyValid(loyaltyId,password,regKey);

        if(isAuthenticated){

            //call the customer registration method
            boolean isRegistered = userService.registerUser(firstName, lastName, loyaltyId, email,password, RequestChannel.RDM_MOBILE_APP,merchantNo);

            //if registration is successful , set status to success
            if(isRegistered){

                retData.setStatus(APIResponseStatus.success);
            }

        } else {

            // Set the retData object
            retData.setStatus(APIResponseStatus.failed);

        }

        // Log the information
        log.info("userRegisterCompatible - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;

    }

    @RequestMapping(value = "/api/0.9/json/merchant/user/getusercode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getUsrUserCode() throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        Long usrUserNo=authSessionUtils.getUserNo();


        // Log the Request
        log.info("getUsrUserCode - Request Received");

        log.info("getUsrUserCode -  "+generalUtils.getLogTextForRequest());

        try{

            // Get the user information
            String usrUserCode = userService.getUsrUserCode(usrUserNo);

            // Set the retData to success
            retData.setStatus(APIResponseStatus.success);

            // Set the data to the delete usrUserCode
            retData.setData(usrUserCode);

        }catch (InspireNetzException ex){

            // Set the retData to success
            retData.setStatus(APIResponseStatus.failed);

            // Set the data to the delete usrUserNo
            retData.setErrorCode(ex.getErrorCode());

        }catch (Exception ex){

            // Set the retData to success
            retData.setStatus(APIResponseStatus.failed);

            // Set the data to the delete usrUserNo
            retData.setErrorCode(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // Log the response
        log.info("getUsrUserCode - " + generalUtils.getLogTextForResponse(retData));


        // Return the retdata object
        return retData;
    }
}
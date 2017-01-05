package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Function;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.FunctionRepository;
import com.inspirenetz.api.core.service.FunctionService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class FunctionServiceImpl extends BaseServiceImpl<Function> implements FunctionService {


   private static Logger log = LoggerFactory.getLogger(FunctionServiceImpl.class);


    @Autowired
    FunctionRepository functionRepository;

    @Autowired
    private AuthSessionUtils authSessionUtils;


    public FunctionServiceImpl() {

        super(Function.class);

    }


    @Override
    protected BaseRepository<Function,Long> getDao() {
        return functionRepository;
    }


    @Override
    public Function findByFncFunctionCode(Long fncFunctionCode) {

        // Get the Function for the given Function id from the repository
        Function function = functionRepository.findByFncFunctionCode(fncFunctionCode);

        // Return the Function
        return function;


    }

    @Override
    public Function findByFncFunctionName(String fncFunctionName) {

        // Get the functions
        Function function = functionRepository.findByFncFunctionName(fncFunctionName);

        // Return the function
        return function;

    }

    @Override
    public Page<Function> findByFncFunctionNameLike(String fncFunctionName, Pageable pageable) {

        // Get the function page
        Page<Function> functionPage = functionRepository.findByFncFunctionNameLike(fncFunctionName,pageable);

        // return the functionpage
        return functionPage;

    }

    @Override
    public Page<Function> listFunctions(Pageable pageable) {

        // Get the functions
        Page<Function> functionPage = functionRepository.findAll(pageable);

        // Return the page
        return functionPage;

    }


    @Override
    public boolean isDuplicateFunctionExisting(Function function) {

        // Get the Function information
        Function exFunction = functionRepository.findByFncFunctionName(function.getFncFunctionName());

        // If the fncFunctionCode is 0L, then its a new Function so we just need to check if there is ano
        // ther Function code
        if ( function.getFncFunctionCode() == null || function.getFncFunctionCode() == 0L ) {

            // If the Function is not null, then return true
            if ( exFunction != null ) {

                return true;

            }

        } else {

            // Check if the Function is null
            if ( exFunction != null && function.getFncFunctionCode().longValue() != exFunction.getFncFunctionCode().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }


    @Override
    public List<Function> getFunctionsForUserType(Integer userType) {

        //params for the repository method
        String merchantAdminEnabled ;
        String merchantUserEnabled ;
        String customerEnabled ;
        String adminEnabled ;
        String vendorEnabled ;


        log.info("Request recieved from usertype :"+userType);

        //check whether the logged in user is merchant admin
        //if so sent the merchant user enabled functions
        if(authSessionUtils.getUserType() == UserType.MERCHANT_ADMIN){

            // Get the userType
            userType = UserType.MERCHANT_USER;

            // Log the information
            log.info("Request recieved from usertype Merchant Admin");

        }

        //if merchant admin set merchant admin enabled as True
        if(userType.intValue() == UserType.MERCHANT_ADMIN) {

            merchantAdminEnabled = "Y";
            merchantUserEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            customerEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            adminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            vendorEnabled=DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;

        } else if(userType.intValue() == UserType.MERCHANT_USER) {

            merchantAdminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            merchantUserEnabled = "Y";
            customerEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            adminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            vendorEnabled=DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;

        }  else if(userType.intValue() == UserType.ADMIN) {

            merchantAdminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            merchantUserEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            customerEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            adminEnabled = "Y";
            vendorEnabled=DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;

        } else if(userType.intValue() == UserType.REDEMPTION_MERCHANT_USER){

            merchantAdminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            merchantUserEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            customerEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            adminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            vendorEnabled="Y";

        } else {

            merchantAdminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            merchantUserEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            customerEnabled = "Y";
            adminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            vendorEnabled=DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;

        }

        //call the repository method for getting the functions
        List<Function> functions = functionRepository.getFunctionsForUserType(adminEnabled,merchantAdminEnabled,merchantUserEnabled,customerEnabled,vendorEnabled);

        //return the functions
        return functions;
    }


    @Override
    public Function saveFunction(Function Function ){

        // Save the Function
        return functionRepository.save(Function);

    }

    @Override
    public boolean deleteFunction(Long fncFunctionCode) {

        // Delete the Function
        functionRepository.delete(fncFunctionCode);

        // return true
        return true;

    }

    @Override
    public List<Function> findByUserTypeAndFunctionNameLike(Integer userType, String fncFunctionName) {

        //params for the repository method
        String merchantAdminEnabled ;
        String merchantUserEnabled ;
        String customerEnabled ;
        String adminEnabled ;

        log.info("Request recieved from usertype :"+userType);

        //check whether the logged in user is merchant admin
        //if so sent the merchant user enabled functions
        if(authSessionUtils.getUserType() == UserType.MERCHANT_ADMIN){

            // Get the userType
            userType = UserType.MERCHANT_USER;

            // Log the information
            log.info("Request recieved from usertype Merchant Admin");

        }else if(authSessionUtils.getUserType() ==UserType.SUPER_ADMIN){

            //set user type is super admin
            userType =UserType.SUPER_ADMIN;
        }

        //if user type then get all the user access
        if(userType.intValue() ==UserType.SUPER_ADMIN){

            merchantAdminEnabled = "Y";
            merchantUserEnabled = "Y";
            customerEnabled ="Y";
            adminEnabled ="Y";

        }else if(userType.intValue() == UserType.MERCHANT_ADMIN) {

            merchantAdminEnabled = "Y";
            merchantUserEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            customerEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            adminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;

        } else if(userType.intValue() == UserType.MERCHANT_USER) {

           merchantAdminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
           merchantUserEnabled = "Y";
           customerEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
           adminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;

        }  else if(userType.intValue() == UserType.ADMIN) {

            merchantAdminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            merchantUserEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            customerEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            adminEnabled = "Y";

        } else {

            merchantAdminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            merchantUserEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;
            customerEnabled = "Y";
            adminEnabled = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;

        }

        //call the repository method for getting the functions
        List<Function> functions = functionRepository.findByUserTypeAndFunctionNameLike(adminEnabled,merchantAdminEnabled,merchantUserEnabled,customerEnabled,"%"+fncFunctionName+"%");

        //return the functions
        return functions;
    }

    @Override
    public Page<Function> searchFunction(String fncName, Pageable pageable) throws InspireNetzException {

        Page<Function> functionPage=null;

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_VIEW_FUNCTION);

        //check authorization its only alllowed super admin
        if(authSessionUtils.getUserType() !=UserType.SUPER_ADMIN){

            //throw error user does't have access
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);
        }

        //check the fncname is zero then pick all value
        if(fncName.equals("0")){

         //get the functional list
         List<Function> functionList =findAll();

         //convert into page
         functionPage =new PageImpl<>(functionList);

        }else {

            functionPage =findByFncFunctionNameLike("%"+fncName+"%",pageable);
        }

        return functionPage;
    }

    @Override
    public Function validateAndSave(Function function) throws InspireNetzException {

        //check access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_FUNCTION);

        //save function
        Function function1 =saveFunction(function);

        //return functions
        return  function1;
    }

}

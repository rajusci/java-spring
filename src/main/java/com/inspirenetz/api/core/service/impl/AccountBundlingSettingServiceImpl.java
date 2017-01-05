package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.AccountBundlingSetting;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.AccountBundlingSettingRepository;
import com.inspirenetz.api.core.service.AccountBundlingSettingService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class AccountBundlingSettingServiceImpl extends BaseServiceImpl<AccountBundlingSetting> implements AccountBundlingSettingService {


    private static Logger log = LoggerFactory.getLogger(AccountBundlingSettingServiceImpl.class);


    @Autowired
    AccountBundlingSettingRepository accountBundlingSettingRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    public AccountBundlingSettingServiceImpl() {

        super(AccountBundlingSetting.class);

    }

    @Override
    protected BaseRepository<AccountBundlingSetting,Long> getDao() {
        return accountBundlingSettingRepository;
    }




    @Override
    public AccountBundlingSetting findByAbsId(Long absId) {

        // Get the accountBundlingSetting for the given accountBundlingSetting id from the repository
        AccountBundlingSetting accountBundlingSetting = accountBundlingSettingRepository.findByAbsId(absId);

        // Return the accountBundlingSetting
        return accountBundlingSetting;


    }

    @Override
    public List<AccountBundlingSetting> findByAbsMerchantNo(Long absMerchantNo) {

        // GEt the List
        List<AccountBundlingSetting> accountBundlingSettingList = accountBundlingSettingRepository.findByAbsMerchantNo(absMerchantNo);

        // REturn the list
        return accountBundlingSettingList;

    }

    @Override
    public AccountBundlingSetting findByAbsMerchantNoAndAbsLocation(Long absMerchantNo, Long absLocation) {

        // Get the accountBundlingSetting using the accountBundlingSetting code and the merchant number
        AccountBundlingSetting accountBundlingSetting = accountBundlingSettingRepository.findByAbsMerchantNoAndAbsLocation(absMerchantNo, absLocation);

        // Return the accountBundlingSetting object
        return accountBundlingSetting;

    }

    @Override
    public AccountBundlingSetting getDefaultAccountBundlingSetting(Long absMerchantNo) {

        // Get the global settings ( created by the merchant admin with the location as 0L
        AccountBundlingSetting accountBundlingSetting =  accountBundlingSettingRepository.findByAbsMerchantNoAndAbsLocation(absMerchantNo,0L);

        // Return the object
        return accountBundlingSetting;

    }

    @Override
    public AccountBundlingSetting getAccountBundlingSettingInfoForUser( ) throws InspireNetzException {


        // Check the userType
        Integer userType = authSessionUtils.getUserType();

        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the merchantLocation
        Long merchantLocation =authSessionUtils.getUserLocation();



        // If the userType is admin, set the location to 0
        if ( userType == UserType.MERCHANT_ADMIN ) {

            merchantLocation = 0L;

        }



        // Get the accoutn bundling for the merchantNo and location
        AccountBundlingSetting accountBundlingSetting =  accountBundlingSettingRepository.findByAbsMerchantNoAndAbsLocation(merchantNo,merchantLocation);

        // If no data is found, then throw no data found exceptoin
        if ( accountBundlingSetting == null ) {

            // Log the response
            log.info("getAccountBundlingSettingInfoForUser - Response : No accountBundlingSetting information found");

            // Throw not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }



        // Return the object
        return accountBundlingSetting;


    }





    @Override
    public AccountBundlingSetting saveAccountBundlingSettingForUser(AccountBundlingSetting accountBundlingSetting ) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_LINK_CONFIGURATION);

        // Get the merchant number of the merchant
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the location
        Long location = authSessionUtils.getUserLocation();

        // Get the userType
        Integer userType = authSessionUtils.getUserType();


        // If the userType is admin, then check if the location is set, then
        // we will use the location, otherwise, set the location to 0
        if ( userType == UserType.MERCHANT_ADMIN ) {

            // Check if the location is null
            if ( accountBundlingSetting.getAbsLocation() == null ) {

                // Set the location to 0
                accountBundlingSetting.setAbsLocation(0l);

            }

        } else if ( userType ==UserType.MERCHANT_USER ) {

            // Set the location as the lcoation for th euser
            accountBundlingSetting.setAbsLocation(location);

        }


        // Set the merchantNo to the merchant number of the merchant admin
        accountBundlingSetting.setAbsMerchantNo(merchantNo);



        // Get the auditDetails
        String auditDetails = authSessionUtils.getUserNo().toString();

        // If the accountBundlingSetting.getAbsId is  null, then set the created_by, else set the updated_by
        if ( accountBundlingSetting.getAbsId() == null ) {

            accountBundlingSetting.setCreatedBy(auditDetails);

        } else {

            accountBundlingSetting.setUpdatedBy(auditDetails);

        }



        // save the accountBundlingSetting object and get the result
        accountBundlingSetting = saveAccountBundlingSetting(accountBundlingSetting);


        // Check if the accountBundling setting is saved
        if ( accountBundlingSetting.getAbsId() == null ) {

            // Log the response
            log.info("saveAccountBundlingSetting - Response : Unable to save the accountBundlingSetting information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Return the object
        return accountBundlingSetting;

    }

    @Override
    public AccountBundlingSetting saveAccountBundlingSetting(AccountBundlingSetting accountBundlingSetting) {

        return accountBundlingSettingRepository.save(accountBundlingSetting);

    }

    @Override
    public boolean removeAccountBundlingSetting(Long absId) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_LINK_CONFIGURATION);

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the accountBundlingSetting information
        AccountBundlingSetting accountBundlingSetting = findByAbsId(absId);



        // Check if the accountBundlingSetting is found
        if ( accountBundlingSetting == null || accountBundlingSetting.getAbsId() == null) {

            // Log the response
            log.info("deleteAccountBundlingSetting - Response : No accountBundlingSetting information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( accountBundlingSetting.getAbsMerchantNo().longValue() != merchantNo || authSessionUtils.getUserType() != UserType.MERCHANT_ADMIN ) {

            // Log the response
            log.info("deleteAccountBundlingSetting - Response : You are not authorized to delete the accountBundlingSetting");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // Delete the accountBundlingSetting
        deleteAccountBundlingSetting(absId);

        // return true
        return true;

    }

    @Override
    public boolean deleteAccountBundlingSetting(Long absId) {

        // Delete the item
        accountBundlingSettingRepository.delete(absId);

        // Return true
        return true;



    }

    @Override
    public boolean checkPrimaryAccountIsReachedLinkedLimit(String cusLoyaltyId,Long cusMerchantNo) throws InspireNetzException {

        //find the customer number

       Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(cusLoyaltyId,cusMerchantNo);

       AccountBundlingSetting accountBundlingSetting = getAccountBundlingSetting();

       //check linked account is exceed account bundling settings
       if(customer !=null && accountBundlingSetting !=null){

         Long linkedAccountCount = linkedLoyaltyService.findByCountLilParentCustomerNo(customer.getCusCustomerNo())==null?0:linkedLoyaltyService.findByCountLilParentCustomerNo(customer.getCusCustomerNo());

         Integer linkingLimitSettings =accountBundlingSetting.getAbsLinkedAccountLimit()==null?0:accountBundlingSetting.getAbsLinkedAccountLimit();

         if(linkedAccountCount > linkingLimitSettings){

             return true;
         }
      }


        return false;
    }


    public AccountBundlingSetting getAccountBundlingSetting() throws InspireNetzException {


        List<AccountBundlingSetting> accountBundlingSettingList = accountBundlingSettingRepository.findAll();

        if(accountBundlingSettingList == null){

            log.error("getAccount Bundling setting Info : No Setting Found");

            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }
        else{

            //return the security setting
            return accountBundlingSettingList.get(0);

        }

    }
}

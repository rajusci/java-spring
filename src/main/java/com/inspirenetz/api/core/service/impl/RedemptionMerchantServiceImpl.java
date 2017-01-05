package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.domain.validator.RedemptionMerchantValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.RedemptionMerchantLocationRepository;
import com.inspirenetz.api.core.repository.RedemptionMerchantRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.MerchantRedemptionPartnerResource;
import com.inspirenetz.api.rest.resource.RedemptionMerchantResource;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DataValidationUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.*;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class RedemptionMerchantServiceImpl extends BaseServiceImpl<RedemptionMerchant> implements RedemptionMerchantService {


    private static Logger log = LoggerFactory.getLogger(RedemptionMerchantServiceImpl.class);


    @Autowired
    RedemptionMerchantRepository redemptionMerchantRepository;

    @Autowired
    RedemptionMerchantLocationRepository redemptionMerchantLocationRepository;

    @Autowired
    RedemptionMerchantLocationService redemptionMerchantLocationService;

    @Autowired
    UserService userService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private MerchantSettlementCycleService merchantSettlementCycleService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private DataValidationUtils dataValidationUtils;

    @Autowired
    private MerchantRedemptionPartnerService merchantRedemptionPartnerService;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    CustomerService customerService;

    public RedemptionMerchantServiceImpl() {

        super(RedemptionMerchant.class);

    }


    @Override
    protected BaseRepository<RedemptionMerchant,Long> getDao() {
        return redemptionMerchantRepository;
    }

    @Override
    public RedemptionMerchant findByRemNo(Long remNo) throws InspireNetzException {

        // Get the redemptionMerchant for the given redemptionMerchant id from the repository
        RedemptionMerchant redemptionMerchant = redemptionMerchantRepository.findByRemNo(remNo);

        // Return the redemptionMerchant
        return redemptionMerchant;

    }

    @Override
    public Page<RedemptionMerchant> searchRedemptionMerchants(String filter,String query,Pageable pageable) throws InspireNetzException {


        Page<RedemptionMerchant> redemptionMerchantPage = null;

        if(filter.equals("name")){

            redemptionMerchantPage = redemptionMerchantRepository.findByRemNameLike("%" + query + "%", pageable);

        }else if(filter.equals("code")){

            redemptionMerchantPage = redemptionMerchantRepository.findByRemCodeLike("%"+query+"%",pageable);

        } else {

            redemptionMerchantPage = redemptionMerchantRepository.findAll(pageable);

        }

        // Return the page
        return redemptionMerchantPage;

    }

    @Override
    public RedemptionMerchant validateAndSaveRedemptionMerchant(RedemptionMerchant redemptionMerchant ) throws InspireNetzException {

        //check the user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_REDEMPTION_MERCHANT);

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Create the Validator
        RedemptionMerchantValidator validator = new RedemptionMerchantValidator(dataValidationUtils);

        // Create the bindingREsult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(redemptionMerchant,"redemptionMerchant");

        // Validate the request
        validator.validate(redemptionMerchant,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveRedemption Merchant - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // Check if the merchant name  is already existing
        boolean isExists = isDuplicateMerchantNameExisting(redemptionMerchant);

        // If duplicate is existing, then, we need to throw exception
        if ( isExists ) {

            // Log the information
            log.info("Exception in validate merchant name :: duplicate Entry Exist");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }





        // If the redemptionMerchant.getLrqId is  null, then set the created_by, else set the updated_by
        if ( redemptionMerchant.getRemNo() == null ) {

            redemptionMerchant.setCreatedBy(auditDetails);

        } else {

            redemptionMerchant.setUpdatedBy(auditDetails);

        }


        // Save the object for if roll id is zero for normal saving else delete access rights and update list
        if(redemptionMerchant.getRemNo()==null){

            redemptionMerchant = saveRedemptionMerchant(redemptionMerchant);

        }else{

            redemptionMerchant =updateRedemptionMerchant(redemptionMerchant);
        }


        // Check if the redemptionMerchant is saved
        if ( redemptionMerchant.getRemNo() == null ) {

            // Log the response
            log.error("validateAndSaveRedemptionMerchant - Response : Unable to save the redemptionMerchant information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        //for checking redemption merchant Active or inactive
        //if redemption merchant is inactive then all user is set to inactive
        if(redemptionMerchant.getRemStatus() == RedemptionMerchantStatus.DEACTIVATED){

            //deactivate redemption user
            deActiveRedemptionUser(redemptionMerchant);
        }



        // return the object
        return redemptionMerchant;


    }

    /**
     * @purpose: deactive redemption user
     * @param redemptionMerchant
     * @date:10-12-2014
     */
    private void deActiveRedemptionUser(RedemptionMerchant redemptionMerchant) {

       //find user by third party vendor number
       List<User> userList =userService.findByUsrThirdPartyVendorNo(redemptionMerchant.getRemNo());

       if(userList !=null){

           for(User user:userList){

               //set redemption user is de active
               user.setUsrStatus(UserStatus.DEACTIVATED);

               //save the user status
               userService.saveUser(user);

           }

       }


    }

    @Override
    public RedemptionMerchant saveRedemptionMerchant(RedemptionMerchant redemptionMerchant ){

        // Save the redemptionMerchant
        return redemptionMerchantRepository.save(redemptionMerchant);

    }

    @Override
    public boolean validateAndDeleteRedemptionMerchant(Long remNo) throws InspireNetzException {

        //check the user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_VIEW_REDEMPTION_MERCHANT);

        // Get the redemptionMerchant information
        RedemptionMerchant redemptionMerchant = findByRemNo(remNo);

        // Check if the redemptionMerchant is found
        if ( redemptionMerchant == null || redemptionMerchant.getRemNo() == null) {

            // Log the response
            log.info("deleteRedemptionMerchant - Response : No redemptionMerchant information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Delete the redemptionMerchant and set the retData fields
        deleteRedemptionMerchant(remNo);

        // Return true
        return true;

    }

    @Override
    public boolean deleteRedemptionMerchant(Long remNo) {

        // Delete the redemptionMerchant
        redemptionMerchantRepository.delete(remNo);

        // return true
        return true;

    }

    @Override
    public List<RedemptionMerchant> searchRedemptionMerchants(String query) {
        return redemptionMerchantRepository.findByRemNameLike(query);
    }

    @Override
    public RedemptionMerchant findByRemVendorUrl(String remVenUrl) {

        RedemptionMerchant redemptionMerchant = redemptionMerchantRepository.findByRemVenUrl(remVenUrl);

        return redemptionMerchant;

    }

    @Override
    public Double validateCustomer(Long merchantNo, String loyaltyId,Double amount) throws InspireNetzException {

        //Get the current user
        AuthUser user = authSessionUtils.getCurrentUser();

        //Get redemption merchant
        Long rdmPartnerNo = user.getThirdPartyVendorNo();

        //Variable storing the required rwd qty
        Double rwdQty =0.0;

        // Create a customer reward balance object
        CustomerRewardBalance customerRewardBalance = new CustomerRewardBalance();

        //Get the merchant redemption partner
        MerchantRedemptionPartner merchantRedemptionPartner = merchantRedemptionPartnerService.findByMrpMerchantNoAndMrpRedemptionMerchant(merchantNo,rdmPartnerNo);

        // Check if the merchantRedemptionPartner is not null
        if(merchantRedemptionPartner == null || merchantRedemptionPartner.getMrpId() == 0){

            // lof the error
            log.info("validateCustomer--->No information found");

            //Throw an exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_MERCHANT);
        }

        // Get the customer
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        // Check if the customer exists
        if(customer == null || customer.getCusCustomerNo() == 0 || customer.getCusStatus() != CustomerStatus.ACTIVE){

            //log the error
            log.info("validateCustomer---> Customer is invalid");

            // Throw an exception
            throw new InspireNetzException(APIErrorCode.ERR_CUSTOMER_NOT_ACTIVE);

        }

        // Get the customer rewars balance
        customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(loyaltyId,merchantNo,merchantRedemptionPartner.getMrpRewardCurrency());

        if(customerRewardBalance == null || customerRewardBalance.getCrbId() == 0){

            //log the issue
            log.info("validateCustomer --> No Customer reward balance");

            // Throw an exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }
        // Calculate the required points
        rwdQty = merchantRedemptionPartnerService.getCashbackQtyForAmount(merchantRedemptionPartner.getMrpCashbackRatioDeno(),amount);

        // Check if the enough points are available
        if(rwdQty > customerRewardBalance.getCrbRewardBalance()){

            //Log the message
            log.info("validateCustomer --> Insufficient point balance");

            //throw an exception
            throw  new InspireNetzException(APIErrorCode.ERR_INSUFFICIENT_POINT_BALANCE);

        }

        return customerRewardBalance.getCrbRewardBalance();
    }

    @Override
    public RedemptionMerchant findByRemCode(String remCode) {

        RedemptionMerchant redemptionMerchant = redemptionMerchantRepository.findByRemCode(remCode);



        return redemptionMerchant;
    }

    @Override
    public RedemptionMerchantResource getRedemptionMerchantWithPartners(String remCode) {

        // Find the redemption merchant with code
        RedemptionMerchant redemptionMerchant = findByRemCode(remCode);

        // Check if data is found
        if ( redemptionMerchant == null ) {

            // Log the information
            log.info("Redemption merchant object not found");

            // return null
            return null;

        }


        // Convert the object to resource
        RedemptionMerchantResource redemptionMerchantResource = mapper.map(redemptionMerchant,RedemptionMerchantResource.class);

        // Set the secretKey field
        redemptionMerchantResource.setSecretKey(redemptionMerchant.getRemSecretKey());

        // get the merchant partner object list
        List<MerchantRedemptionPartnerResource> merchantRedemptionPartnerResourceList = merchantRedemptionPartnerService.getMerchantRedemptionPartnerResources(redemptionMerchant.getRemNo());

        // Add to the list
        redemptionMerchantResource.setMerchantRedemptionPartnerResources(merchantRedemptionPartnerResourceList);

        // Return the object
        return redemptionMerchantResource;

    }

    // for delete the existing redemptionMerchant and update redemptionMerchant rights
    public RedemptionMerchant updateRedemptionMerchant(RedemptionMerchant redemptionMerchant) throws InspireNetzException {

        //for getting redemptionMerchantAccessRights Id from the redemptionMerchant object
        Set<RedemptionMerchantLocation> redemptionMerchantLocations=redemptionMerchant.getRedemptionMerchantLocations();

        //intializing delete list
        Set<RedemptionMerchantLocation> redemptionMerchantLocationDeleteSet=new HashSet<>();

        log.info("RedemptionMerchantServiceImpl :: update redemption merchants redemptionMerchantLocationList"+redemptionMerchantLocations);

        //for fetching redemptionMerchant locations based by redemptionMerchant Id
        List<RedemptionMerchantLocation> redemptionMerchantLocationsBaseList=redemptionMerchantLocationRepository.findByRmlMerNo(redemptionMerchant.getRemNo());

        log.info("RedemptionMerchantServiceImpl :: update redemption merchant base list (Present In DB)"+redemptionMerchantLocationsBaseList);



        boolean toDelete=true;

        if(redemptionMerchantLocations == null){

            redemptionMerchantLocationDeleteSet.addAll(redemptionMerchantLocationsBaseList);

        }

        if(redemptionMerchantLocationsBaseList!=null && redemptionMerchantLocations!=null){

            // Iterate through the based list and check
            for(RedemptionMerchantLocation redemptionMerchantLocation :redemptionMerchantLocationsBaseList){

                // Reset the isDelete to true
                toDelete = true;

                // Iterate through the params items and check if the data is there
                for(RedemptionMerchantLocation redemptionMerchantLocation1 : redemptionMerchantLocations){

                    //for getting value from Access List
                    Long rmlId = redemptionMerchantLocation1.getRmlId()==null ?0L:redemptionMerchantLocation1.getRmlId().longValue();

                    if(rmlId !=0L){

                        if(redemptionMerchantLocation1.getRmlId().longValue()==redemptionMerchantLocation.getRmlId().longValue()){

                            // Set the flag to false
                            toDelete=false;

                            // Break from the loop as no further checking is required
                            break;

                         }
                    }

                }

                // If the flag is set, then we need to add it to the list for deleting
                if(toDelete){

                    redemptionMerchantLocationDeleteSet.add(redemptionMerchantLocation);

                }
            }
        }

        // for deleting redemptionMerchant access right
        if(!redemptionMerchantLocationDeleteSet.isEmpty()){

            // Delete the set
            redemptionMerchantLocationService.deleteRedemptionMerchantLocationSet(redemptionMerchantLocationDeleteSet);

            // Log the information
            log.info("Deleting Merchant Locations");

        }


        // for updating redemptionMerchant and redemptionMerchant access rights
        redemptionMerchant = redemptionMerchantRepository.save(redemptionMerchant);

        // Check if the redemptionMerchant is not null and is saved
        if ( redemptionMerchant == null || redemptionMerchant.getRemNo() == null ) {

            // Log information
            log.error("Redemption merchant saving failed");

            // throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        log.info("Redemption Merchant is updated Successfully.."+redemptionMerchant.getRemNo());

        return redemptionMerchant;
    }


    public boolean isDuplicateMerchantNameExisting(RedemptionMerchant redemptionMerchant) {

        // Get the redemption merchant information
        RedemptionMerchant exMerchant = redemptionMerchantRepository.findByRemName(redemptionMerchant.getRemName());

        // If the brnId is 0L, then its a new merchant so we just need to check if there is ano
        // ther merchant name
        if ( redemptionMerchant.getRemNo() == null ||redemptionMerchant.getRemNo() == 0L ) {

            // If the catalogue is not null, then return true
            if ( exMerchant != null ) {

                return true;

            }

        } else {

            // Check if the catalogue is null
            if ( exMerchant != null && redemptionMerchant.getRemNo().longValue() != exMerchant.getRemNo().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

}


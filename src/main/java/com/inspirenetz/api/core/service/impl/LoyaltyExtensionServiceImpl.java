package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LoyaltyExtension;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.domain.validator.LoyaltyExtensionValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.LoyaltyExtensionRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LoyaltyExtensionService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

/**
 * Created by saneesh-ci on 10/9/14.
 */
@Service
public class LoyaltyExtensionServiceImpl extends BaseServiceImpl<LoyaltyExtension> implements LoyaltyExtensionService {


    private static Logger log = LoggerFactory.getLogger(LoyaltyExtensionServiceImpl.class);


    @Autowired
    LoyaltyExtensionRepository loyaltyExtensionRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    LinkedLoyaltyService linkedLoyaltyService;


    @Autowired
    private AuthSessionUtils authSessionUtils;


    public LoyaltyExtensionServiceImpl() {

        super(LoyaltyExtension.class);

    }


    @Override
    protected BaseRepository<LoyaltyExtension,Long> getDao() {
        return loyaltyExtensionRepository;
    }



    @Override
    public LoyaltyExtension findByLexId(Long lexId) {

        // Get the loyaltyExtension for the given loyaltyExtension id from the repository
        LoyaltyExtension loyaltyExtension = loyaltyExtensionRepository.findByLexId(lexId);

        // Return the loyaltyExtension
        return loyaltyExtension;

    }

    @Override
    public Page<LoyaltyExtension> searchLoyaltyExtensions(String filter,String query,Pageable pageable) {

        Long merchantNo = authSessionUtils.getMerchantNo();

        Page<LoyaltyExtension>  loyaltyExtensionPage = null;

        if(filter.equals("name")) {

               loyaltyExtensionPage = loyaltyExtensionRepository.findByLexMerchantNoAndLexNameLike(merchantNo,"%"+query+"%",pageable);

        } else {

            loyaltyExtensionPage = loyaltyExtensionRepository.findByLexMerchantNo(merchantNo, pageable);

        }

        // Return the page
        return loyaltyExtensionPage;

    }

    @Override
    public LoyaltyExtension getLoyaltyExtensionInfo(Long lexId) throws InspireNetzException {


        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the loyaltyExtension information
        LoyaltyExtension loyaltyExtension = findByLexId(lexId);

        // Check if the loyaltyExtension is found
        if ( loyaltyExtension == null || loyaltyExtension.getLexId() == null) {

            // Log the response
            log.info("getLoyaltyExtensionInfo - Response : No loyaltyExtension information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the merchant is valid for deletion
        if ( loyaltyExtension.getLexMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteLoyaltyExtension - Response : No loyaltyExtension information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // return the object
        return loyaltyExtension;


    }




    @Override
    public LoyaltyExtension validateAndSaveLoyaltyExtension(LoyaltyExtension loyaltyExtension ) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_LOYALTY_EXTENSION);
        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo
        loyaltyExtension.setLexMerchantNo(merchantNo);


        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();



        // Create the Validator
        LoyaltyExtensionValidator validator = new LoyaltyExtensionValidator();

        // Create the bindingREsult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(loyaltyExtension,"loyaltyExtension");

        // Validate the request
        validator.validate(loyaltyExtension,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveLoyaltyExtension - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }




        // If the loyaltyExtension.getLexId is  null, then set the created_by, else set the updated_by
        if ( loyaltyExtension.getLexId() == null ) {

            loyaltyExtension.setCreatedBy(auditDetails);

        } else {

            loyaltyExtension.setUpdatedBy(auditDetails);

        }




        // Save the object
        loyaltyExtension = saveLoyaltyExtension(loyaltyExtension);

        // Check if the loyaltyExtension is saved
        if ( loyaltyExtension.getLexId() == null ) {

            // Log the response
            log.info("validateAndSaveLoyaltyExtension - Response : Unable to save the loyaltyExtension information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // return the object
        return loyaltyExtension;


    }

    @Override
    public LoyaltyExtension saveLoyaltyExtension(LoyaltyExtension loyaltyExtension ){

        // Save the loyaltyExtension
        return loyaltyExtensionRepository.save(loyaltyExtension);

    }

    @Override
    public boolean validateAndDeleteLoyaltyExtension(Long lexId) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_LOYALTY_EXTENSION);

        // Get the merchant number
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Get the loyaltyExtension information
        LoyaltyExtension loyaltyExtension = findByLexId(lexId);

        // Check if the loyaltyExtension is found
        if ( loyaltyExtension == null || loyaltyExtension.getLexId() == null) {

            // Log the response
            log.info("deleteLoyaltyExtension - Response : No loyaltyExtension information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the merchant is valid for deletin
        if ( loyaltyExtension.getLexMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteLoyaltyExtension - Response : No loyaltyExtension information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the loyaltyExtension and set the retData fields
        deleteLoyaltyExtension(lexId);

        // Return true
        return true;

    }

    @Override
    public boolean deleteLoyaltyExtension(Long lrqId) {

        // Delete the loyaltyExtension
        loyaltyExtensionRepository.delete(lrqId);

        // return true
        return true;

    }



   /* @Override
    public LoyaltyExtension addLoyaltyUnlinkingRequest(String primaryLoyaltyId, String childLoyaltyId,Long merchantNo) throws InspireNetzException {


    }*/


}


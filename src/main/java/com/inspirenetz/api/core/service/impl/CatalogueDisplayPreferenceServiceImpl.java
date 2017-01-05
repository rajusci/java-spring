package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.RedemptionVoucherStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CatalogueDisplayPreference;
import com.inspirenetz.api.core.domain.validator.CatalogueDisplayPreferenceValidator;
import com.inspirenetz.api.core.domain.validator.RedemptionVoucherValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CatalogueDisplayPreferenceRepository;
import com.inspirenetz.api.core.repository.RedemptionVoucherRepository;
import com.inspirenetz.api.core.service.CatalogueDisplayPreferenceService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.RedemptionVoucherService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

/**
 * Created by saneeshci on 25/9/14.
 */
@Service
public class CatalogueDisplayPreferenceServiceImpl extends BaseServiceImpl<CatalogueDisplayPreference> implements CatalogueDisplayPreferenceService {


    private static Logger log = LoggerFactory.getLogger(CatalogueDisplayPreferenceServiceImpl.class);


    @Autowired
    CatalogueDisplayPreferenceRepository catalogueDisplayPreferenceRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    private AuthSessionUtils authSessionUtils;


    public CatalogueDisplayPreferenceServiceImpl() {

        super(CatalogueDisplayPreference.class);

    }


    @Override
    protected BaseRepository<CatalogueDisplayPreference,Long> getDao() {
        return catalogueDisplayPreferenceRepository;
    }



    @Override
    public CatalogueDisplayPreference findByCdpId(Long cdpId) throws InspireNetzException {

        // Get the catalogueDisplayPreference for the given catalogueDisplayPreference id from the repository
        CatalogueDisplayPreference catalogueDisplayPreference = catalogueDisplayPreferenceRepository.findByCdpId(cdpId);

        // Return the catalogueDisplayPreference
        return catalogueDisplayPreference;

    }

    @Override
    public CatalogueDisplayPreference getCatalogueDisplayPreference(Long cdpMerchantNo) {

       //calling the repository method to get all pending vouchers( having voucher status as new)
       CatalogueDisplayPreference catalogueDisplayPreference = catalogueDisplayPreferenceRepository.findByCdpMerchantNo(cdpMerchantNo);

       return catalogueDisplayPreference;

    }


    @Override
    public CatalogueDisplayPreference validateAndSaveCatalogueDisplayPreference(CatalogueDisplayPreference catalogueDisplayPreference ) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_CATALOGUE_DISPLAY_PREFERENCE);
        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Create the Validator
        CatalogueDisplayPreferenceValidator validator = new CatalogueDisplayPreferenceValidator();

        // Create the bindingREsult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(catalogueDisplayPreference,"catalogueDisplayPreference");

        // Validate the request
        validator.validate(catalogueDisplayPreference,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveCatalogueDisplayPreference - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_CATALOGUE_PREFERENCE_NULL,result);

        }

        // If the catalogueDisplayPreference.getLrqId is  null, then set the created_by, else set the updated_by
        if ( catalogueDisplayPreference.getCdpId() == null ) {

            catalogueDisplayPreference.setCreatedBy(auditDetails);

        } else {

            catalogueDisplayPreference.setUpdatedBy(auditDetails);

        }

        catalogueDisplayPreference = saveDisplayPreference(catalogueDisplayPreference);

        // Check if the catalogueDisplayPreference is saved
        if ( catalogueDisplayPreference.getCdpId() == null ) {

            // Log the response
            log.info("validateAndSaveCatalogueDisplayPreference - Response : Unable to save the catalogueDisplayPreference information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // return the object
        return catalogueDisplayPreference;


    }

    @Override
    public CatalogueDisplayPreference saveDisplayPreference(CatalogueDisplayPreference catalogueDisplayPreference ){

        // Save the catalogueDisplayPreference
        return catalogueDisplayPreferenceRepository.save(catalogueDisplayPreference);

    }

    @Override
    public boolean deleteDisplayPreference(Long rolId) {

        // Delete the catalogueDisplayPreference
        catalogueDisplayPreferenceRepository.delete(rolId);

        // return true
        return true;

    }

    @Override
    public CatalogueDisplayPreference getDefaultCatalogueDisplayPreference() {

        //Default Merchant
        Long cdpMerchantNo=generalUtils.getDefaultMerchantNo();

        //calling the repository method to get all pending vouchers( having voucher status as new)
        CatalogueDisplayPreference catalogueDisplayPreference = catalogueDisplayPreferenceRepository.findByCdpMerchantNo(cdpMerchantNo);

        return catalogueDisplayPreference;

    }

    @Override
    public CatalogueDisplayPreference getUserCatalogueDisplayPreference(Long cdpMerchantNo) {

        if(cdpMerchantNo==null||cdpMerchantNo.longValue()==0L){

            //Default Merchant
            cdpMerchantNo=generalUtils.getDefaultMerchantNo();
        }


        //calling the repository method to get all pending vouchers( having voucher status as new)
        CatalogueDisplayPreference catalogueDisplayPreference = catalogueDisplayPreferenceRepository.findByCdpMerchantNo(cdpMerchantNo);

        return catalogueDisplayPreference;

    }

}


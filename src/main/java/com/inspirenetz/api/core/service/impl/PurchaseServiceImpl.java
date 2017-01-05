package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.domain.Purchase;
import com.inspirenetz.api.core.domain.validator.PurchaseValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.PurchaseRepository;
import com.inspirenetz.api.core.service.PurchaseService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DataValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.sql.Date;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class PurchaseServiceImpl extends BaseServiceImpl<Purchase> implements PurchaseService {

    private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    private DataValidationUtils dataValidationUtils;

    @Autowired
    AuthSessionUtils authSessionUtils;



    public PurchaseServiceImpl() {

        super(Purchase.class);

    }


    @Override
    protected BaseRepository<Purchase,Long> getDao() {
        return purchaseRepository;
    }

    @Override
    public Purchase findByPrcId(Long prcId) {

        // Get the purchase for the given purchase id from the repository
        Purchase purchase = purchaseRepository.findByPrcId(prcId);

        // Return the purchase
        return purchase;

    }

    @Override
    public Page<Purchase> findByPrcMerchantNoAndPrcDate(Long prcMerchantNo, Date prcDate,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<Purchase> purchaseList = purchaseRepository.findByPrcMerchantNoAndPrcDate(prcMerchantNo, prcDate, pageable);

        // Return the list
        return purchaseList;

    } 

    @Override
    public Page<Purchase> findByPrcMerchantNoAndPrcDateBetween(Long prcMerchantNo,Date prcStartDate,Date prcEndDate,Pageable pageable) {

        // Get the purchase using the purchase code and the merchant number
        Page<Purchase> purchaseList = purchaseRepository.findByPrcMerchantNoAndPrcDateBetween(prcMerchantNo,prcStartDate,prcEndDate,pageable);

        // Return the purchase object
        return purchaseList;

    }


    @Override
    public Page<Purchase> findByPrcMerchantNoAndPrcLoyaltyId(Long prcMerchantNo,String prcLoyaltyId,Pageable pageable) {

        // Get the purchase using the purchase code and the merchant number
        Page<Purchase> purchaseList = purchaseRepository.findByPrcMerchantNoAndPrcLoyaltyId(prcMerchantNo, prcLoyaltyId,pageable);

        // Return the purchase object
        return purchaseList;

    }


    @Override
    public Page<Purchase> findByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateBetween(Long prcMerchantNo,String prcLoyaltyId,Date prcStartDate, Date prcEndDate,Pageable pageable) {

        // Get the purchase using the purchase code and the merchant number
        Page<Purchase> purchaseList = purchaseRepository.findByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateBetween(prcMerchantNo, prcLoyaltyId, prcStartDate, prcEndDate,pageable);

        // Return the purchase object
        return purchaseList;
    }

    @Override
    public boolean isDuplicatePurchase(Long prcMerchantNo, String prcLoyaltyId, String prcPaymentReference, Date prcDate, double prcAmount) {
 // Get the PurchaseValidator
        PurchaseValidator validator = new PurchaseValidator();


        // Check if there is purchae information existing for the passed values
        Purchase purchase = purchaseRepository.findByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateAndPrcAmountAndPrcPaymentReference(prcMerchantNo,prcLoyaltyId,prcDate,prcAmount,prcPaymentReference);

        // If the purchase object is valid and if there is a valid purchase id, then we have
        // a purchase entry already existing
        if ( purchase != null && purchase.getPrcId() != null ) {

            return true;

        }

        // Else return false
        return false;

    }





    @Override
    public Purchase savePurchase(Purchase purchase ) throws InspireNetzException {

        // Create the BindingResult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(purchase,"purchase");

        // Create the PurchaseValidator
        PurchaseValidator validator = new PurchaseValidator();


        // Validate using the PurchaseValidator object
        validator.validate(purchase, result);

        // Check if there are validation errors for the purchse object
        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Get the validation message
            String messages = dataValidationUtils.getValidationMessages(result);

            // Log the response
            log.info("savePurchase - Response : Invalid Input - " + messages);

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,messages);

        }


        // Check if the purchase data is already existing
        boolean isExist = isDuplicatePurchase(
                purchase.getPrcMerchantNo(),
                purchase.getPrcLoyaltyId(),
                purchase.getPrcPaymentReference(),
                purchase.getPrcDate(),
                purchase.getPrcAmount()
        );


        // Check the boolean value and if the entry already exists, then we need to show
        // the message as already exists
        if ( isExist ) {

            // Log the response
            log.info("savePurchase - Response : Purchase entry already exists");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }


        // Save the purchase
        return purchaseRepository.save(purchase);

    }

    @Override
    public boolean deletePurchase(Long prcId) {

        // Delete the purchase
        purchaseRepository.delete(prcId);

        // return true
        return true;

    }


}

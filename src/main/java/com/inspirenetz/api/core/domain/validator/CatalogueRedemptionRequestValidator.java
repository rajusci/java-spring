package com.inspirenetz.api.core.domain.validator;


import com.inspirenetz.api.core.dictionary.CatalogueRedemptionRequest;
import com.inspirenetz.api.core.domain.Redemption;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by sandheepgr on 17/2/14.
 */
public class CatalogueRedemptionRequestValidator implements Validator {


    @Override
    public boolean supports(Class clazz) {

        return CatalogueRedemptionRequest.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // Cast the CatalogueRedemptionRequest object
        CatalogueRedemptionRequest catalogueRedemptionRequest = ( CatalogueRedemptionRequest) target;

        // Check if the merchantNo is present
        if ( catalogueRedemptionRequest.getMerchantNo() == null || catalogueRedemptionRequest.getMerchantNo().longValue() == 0L ) {

            errors.rejectValue("merchantNo","ERR_NO_MERCHANT_NUMBER");

        }

        // Check if the loyaltyId is present
        if ( catalogueRedemptionRequest.getLoyaltyId() == null || catalogueRedemptionRequest.getLoyaltyId().isEmpty() ) {

            errors.rejectValue("loyaltyId","ERR_NO_LOYALTY_ID");

        }

        // Check if the redemptionCatalogues list is empty
        if ( catalogueRedemptionRequest.getRedemptionCatalogues().isEmpty() ) {

            errors.rejectValue("redemptionCatalogues","ERR_NO_CATALOGUES");

        }

        // if the deliveryInd is 1, then check the value
        if ( catalogueRedemptionRequest.getDeliveryInd() == 1 ) {

            // Check the address1 is existing
            if ( catalogueRedemptionRequest.getAddress1() == null || catalogueRedemptionRequest.getAddress1().isEmpty() ) {

                errors.rejectValue("address1","ERR_NO_ADDRESS1");

            }

            // Check the address2 is existing
            if ( catalogueRedemptionRequest.getAddress2() == null || catalogueRedemptionRequest.getAddress2().isEmpty() ) {

                errors.rejectValue("address2","ERR_NO_ADDRESS1");

            }


            // Check the city is existing
            if ( catalogueRedemptionRequest.getCity() == null || catalogueRedemptionRequest.getCity().isEmpty() ) {

                errors.rejectValue("city","ERR_NO_CITY");

            }


            // Check the state is existing
            if ( catalogueRedemptionRequest.getState() == null || catalogueRedemptionRequest.getState().isEmpty() ) {

                errors.rejectValue("state","ERR_NO_STATE");

            }


            // Check the country is existing
            if ( catalogueRedemptionRequest.getState() == null || catalogueRedemptionRequest.getState().isEmpty() ) {

                errors.rejectValue("state","ERR_NO_STATE");

            }


            // Check the pincode is existing
            if ( catalogueRedemptionRequest.getPincode() == null || catalogueRedemptionRequest.getPincode().isEmpty() ) {

                errors.rejectValue("pincode","ERR_NO_PINCODE");

            }

        }


        // Check the contactNumber is existing
        if ( catalogueRedemptionRequest.getContactNumber() == null || catalogueRedemptionRequest.getContactNumber().isEmpty() ) {

            errors.rejectValue("contactNumber","ERR_NO_CONTACT_NUMBER");

        }

    }
}

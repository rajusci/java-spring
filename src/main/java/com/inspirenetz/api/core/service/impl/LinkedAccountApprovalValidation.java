package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.AccountBundlingSettingRedemption;
import com.inspirenetz.api.core.dictionary.RequestEligibilityStatus;
import com.inspirenetz.api.core.dictionary.ValidationRequest;
import com.inspirenetz.api.core.dictionary.ValidationResponse;
import com.inspirenetz.api.core.domain.AccountBundlingSetting;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AccountBundlingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by saneeshci on 16/09/15.
 */
@Service
public class LinkedAccountApprovalValidation implements ValidationService {

    // Create the logger
    private static Logger log = LoggerFactory.getLogger(LinkedAccountApprovalValidation.class);

    @Autowired
    LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    RewardCurrencyService rewardCurrencyService;

    @Autowired
    AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    AccountBundlingUtils accountBundlingUtils;

    public LinkedAccountApprovalValidation(){


    }
    // Constructor
    public LinkedAccountApprovalValidation(LinkedLoyaltyService linkedLoyaltyService, RewardCurrencyService rewardCurrencyService, CustomerRewardBalanceService customerRewardBalanceService
            , AccountBundlingSettingService accountBundlingSettingService, AccountBundlingUtils accountBundlingUtils) {

        this.linkedLoyaltyService = linkedLoyaltyService;
        this.rewardCurrencyService =rewardCurrencyService;
        this.customerRewardBalanceService = customerRewardBalanceService;
        this.accountBundlingSettingService = accountBundlingSettingService;
        this.accountBundlingUtils = accountBundlingUtils;
    }

    @Override
    public ValidationResponse isValid(ValidationRequest validationRequest) throws InspireNetzException {

        ValidationResponse validationResponse = new ValidationResponse();

        validationRequest = getCashBackValidity(validationRequest,validationResponse);

        if(validationRequest.getEligibilityStatus() == RequestEligibilityStatus.INELIGIBLE){

            log.info("isValid : Customer not eligle for redemption");

            validationResponse.setValid(false);

            validationResponse = setValidationResponseFields(validationRequest,validationResponse);

            validationResponse.setEligibilityStatus(RequestEligibilityStatus.INELIGIBLE);

            validationResponse.setValidationRemarks("Customer is not eligible for redemption");

        } else if(validationRequest.getEligibilityStatus() == RequestEligibilityStatus.APPROVAL_NEEDED){

            log.info("isValid : Customer eligible for redemption , approval needed");

            log.info("isValid : Customer not eligle for redemption");

            validationResponse = setValidationResponseFields(validationRequest,validationResponse);

            validationResponse.setValid(true);

            validationResponse.setEligibilityStatus(RequestEligibilityStatus.APPROVAL_NEEDED);

            validationResponse.setValidationRemarks("Approval needed for cashback");


        } else if(validationRequest.getEligibilityStatus() == RequestEligibilityStatus.ELIGIBLE){

            validationResponse = setValidationResponseFields(validationRequest,validationResponse);

            validationResponse.setValid(true);

            validationResponse.setEligibilityStatus(RequestEligibilityStatus.ELIGIBLE);

            validationResponse.setValidationRemarks("Customer eligible to do cashback");

        }

        return validationResponse;

    }


    private ValidationRequest getCashBackValidity(ValidationRequest validationRequest,ValidationResponse validationResponse) throws InspireNetzException {

        Customer customer  = validationRequest.getCustomer();

        //check whether the customer is linked
        List<LinkedLoyalty> linkedLoyalties = linkedLoyaltyService.getAllLinkedAccounts(customer.getCusCustomerNo());

        if(linkedLoyalties == null || linkedLoyalties.size() == 0){

            //if not linked to any account , then customer is eligible to redeem
            validationRequest.setEligibilityStatus(RequestEligibilityStatus.ELIGIBLE);

            //set debit loyalty id
            validationRequest.setDebitLoyaltyId(validationRequest.getLoyaltyId());

            log.info("getCashBackValidity : Account is not linked , eligible for redemption"+ validationRequest);

            return validationRequest;

        } else {

            //check the account bundling settings
            AccountBundlingSetting accountBundlingSetting = accountBundlingSettingService.getAccountBundlingSetting();

            //check whether the customer is primary
            validationRequest = isCustomerPrimary(linkedLoyalties,validationRequest);

            //check redemption settings
            switch(accountBundlingSetting.getAbsBundlingRedemption()){

                case AccountBundlingSettingRedemption.NO_AUTHORIZATION:

                    validationRequest.setEligibilityStatus(RequestEligibilityStatus.ELIGIBLE);

                    if(validationRequest.isCustomerPrimary()){

                        //set debit loyalty id
                        validationRequest.setDebitLoyaltyId(validationRequest.getLoyaltyId());

                        log.info("getCashBackValidity : Requested customer is primary , points will be debited from "+validationRequest.getLoyaltyId());


                    } else {

                        Customer primary = accountBundlingUtils.getPrimaryCustomerForCustomer(validationRequest.getMerchantNo(),validationRequest.getLoyaltyId());

                        //set debit loyalty id
                        validationRequest.setDebitLoyaltyId(primary.getCusLoyaltyId());

                        log.info("getCashBackValidity : Requested customer is secondary , points will be debited from "+ primary.getCusLoyaltyId());

                    }

                    return validationRequest;

                case AccountBundlingSettingRedemption.PRIMARY_ONLY:

                    log.info("getRedemptionValidity : Redemption allowed only for primary");

                    if(validationRequest.isCustomerPrimary()){

                        log.info("getRedemptionValidity : Requested customer is primary");

                        //set eligibility status as eligible
                        validationRequest.setEligibilityStatus(RequestEligibilityStatus.ELIGIBLE);

                        log.info("getCashBackValidity : Requested customer is primary , points will be debited from "+validationRequest.getLoyaltyId());

                        //set debit loyalty id
                        validationRequest.setDebitLoyaltyId(validationRequest.getLoyaltyId());

                    } else {


                        log.info("getCashBackValidity : Redemption failed , requested customer is not primary");

                        //set ellgibility status to ineligible
                        validationRequest.setEligibilityStatus(RequestEligibilityStatus.INELIGIBLE);
                    }

                    return validationRequest;

                case AccountBundlingSettingRedemption.ANY_ACCOUNT_WITH_AUTHORIZATION:

                    log.info("getCashBackValidity : Redemption allowed for any account with authorization from other account");

                    //set status as eligible
                    validationRequest.setEligibilityStatus(RequestEligibilityStatus.APPROVAL_NEEDED);

                    if(validationRequest.isCustomerPrimary()){

                        //set debit loyalty id
                        validationRequest.setDebitLoyaltyId(validationRequest.getLoyaltyId());

                        log.info("getCashBackValidity : Requested customer is primary , points will be debited from "+validationRequest.getLoyaltyId());


                    } else {

                        Customer primary = accountBundlingUtils.getPrimaryCustomerForCustomer(validationRequest.getMerchantNo(),validationRequest.getLoyaltyId());

                        //set debit loyalty id
                        validationRequest.setDebitLoyaltyId(primary.getCusLoyaltyId());

                        validationRequest.setDebitCustomerNo(primary.getCusCustomerNo());

                        log.info("getCashBackValidity : Requested customer is secondary , points will be debited from "+ primary.getCusLoyaltyId());

                    }

                    return validationRequest;

                case AccountBundlingSettingRedemption.SECONDARY_WITH_AUTHORIZATION:

                    log.info("getCashBackValidity : Redemption settings : Secondary can redeeom with authorization from primary");

                    if(validationRequest.isCustomerPrimary()){

                        //set eligibility status as eligible
                        validationRequest.setEligibilityStatus(RequestEligibilityStatus.ELIGIBLE);

                        log.info("getCashBackValidity : Requested customer is primary , points will be debited from "+validationRequest.getLoyaltyId());

                        //set debit loyalty id
                        validationRequest.setDebitLoyaltyId(validationRequest.getLoyaltyId());


                    } else {

                        //set eligibility status as eligible
                        validationRequest.setEligibilityStatus(RequestEligibilityStatus.APPROVAL_NEEDED);

                        Customer primary = accountBundlingUtils.getPrimaryCustomerForCustomer(validationRequest.getMerchantNo(),validationRequest.getLoyaltyId());

                        //set debit loyalty id
                        validationRequest.setDebitLoyaltyId(primary.getCusLoyaltyId());

                        validationRequest.setDebitCustomerNo(primary.getCusCustomerNo());

                        log.info("getRedemptionValidity : Requested customer is secondary , points will be debited from "+primary.getCusLoyaltyId());

                    }

                    //set debit loyalty id
                    validationRequest.setDebitLoyaltyId(validationRequest.getDebitLoyaltyId());


                    return  validationRequest;

            }

            return validationRequest;
        }

    }

    public ValidationRequest isCustomerPrimary(List<LinkedLoyalty> linkedLoyalties , ValidationRequest validationRequest){

        Customer customer = validationRequest.getCustomer();

        //iterate through the linked loyalties
        for(LinkedLoyalty linkedLoyalty : linkedLoyalties){

            //if the customer is primary , approval is needed from secondary
            if(linkedLoyalty.getLilParentCustomerNo().longValue() == customer.getCusCustomerNo()){

                //set customer primary as true
                validationRequest.setCustomerPrimary(true);

                //set approver customer no
                validationRequest.setApproverCustomerNo(linkedLoyalty.getLilChildCustomerNo());

            } else {

                //set customer primary as true
                validationRequest.setCustomerPrimary(false);

                //set approver customer no
                validationRequest.setApproverCustomerNo(linkedLoyalty.getLilParentCustomerNo());

            }

        }

        //return redemption request object
        return validationRequest;

    }


    private ValidationResponse setValidationResponseFields(ValidationRequest validationRequest,ValidationResponse validationResponse){

        validationResponse.setApproverCustomerNo(validationRequest.getApproverCustomerNo());

        validationResponse.setDebitCustomerNo(validationRequest.getDebitCustomerNo());

        validationResponse.setDebitLoyaltyId(validationRequest.getDebitLoyaltyId());


        return validationResponse;
    }
}

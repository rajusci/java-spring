package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AccountBundlingUtils;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by saneeshci on 16/09/15..
 */
@Service
public class BalanceValidation implements ValidationService {


    // Create the logger
    private static Logger log = LoggerFactory.getLogger(BalanceValidation.class);


    AuthSessionUtils authSessionUtils;


    LinkedLoyaltyService linkedLoyaltyService;

    CustomerRewardBalanceService customerRewardBalanceService;


    RewardCurrencyService rewardCurrencyService;


    AccountBundlingSettingService accountBundlingSettingService;


    AccountBundlingUtils accountBundlingUtils;


    CustomerService customerService;

    MerchantSettingService merchantSettingService;

    CardMasterService cardMasterService;

    public BalanceValidation(){


    }
    // Constructor
    public BalanceValidation(LinkedLoyaltyService linkedLoyaltyService, RewardCurrencyService rewardCurrencyService, CustomerRewardBalanceService customerRewardBalanceService
            , AccountBundlingSettingService accountBundlingSettingService, AccountBundlingUtils accountBundlingUtils, CustomerService customerService, MerchantSettingService merchantSettingService,CardMasterService cardMasterService) {

        this.linkedLoyaltyService = linkedLoyaltyService;
        this.rewardCurrencyService =rewardCurrencyService;
        this.customerRewardBalanceService = customerRewardBalanceService;
        this.accountBundlingSettingService = accountBundlingSettingService;
        this.accountBundlingUtils = accountBundlingUtils;
        this.customerService = customerService;
        this.merchantSettingService = merchantSettingService;
        this.cardMasterService = cardMasterService;

    }

    @Override
    public ValidationResponse isValid(ValidationRequest validationRequest) throws InspireNetzException {

        //get customer details
        Customer customer  = validationRequest.getCustomer();

        //check whether the customer is secondary in any linked loyalty
        List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findLinkedAccounts(customer.getCusLoyaltyId(), customer.getCusMerchantNo());

        //Validation Response object
        ValidationResponse validationResponse = new ValidationResponse();

        RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(validationRequest.getRwdCurrencyId());

        //Account from which balance to be deducted
        Customer debitCustomer = customer;

        if(linkedLoyaltyList == null || linkedLoyaltyList.size() == 0){

            //log
            log.info("isValid :Account is not linked");

        } else {

            //get the parent customer no
            Long parentCustomer = linkedLoyaltyList.get(0).getLilParentCustomerNo();

            //get the parent customer details
            debitCustomer = customerService.findByCusCustomerNo(parentCustomer);

        }

        //get the required reward balance for the customer
        double pointsRequired = validationRequest.getRedeemQty();

        //get the account balance of the debit customer
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(debitCustomer.getCusLoyaltyId(), validationRequest.getMerchantNo(),rewardCurrency.getRwdCurrencyId());

        //check pay eith wallet balance enabled
        boolean isPayWithBalanceEnabled = merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_ENABLE_PAY_WITH_WALLET_BALANCE,validationRequest.getMerchantNo());

        CardMaster cardMaster = new CardMaster();

        // Check if the pay with balance enabled
        if(isPayWithBalanceEnabled){

            //Get the card balance
            List<CardMaster> cardMasterList = cardMasterService.findByCrmMerchantNoAndCrmLoyaltyIdOrderByCrmIdDesc(validationRequest.getMerchantNo(),validationRequest.getLoyaltyId());

            if(cardMasterList != null && cardMasterList.size() != 0){

                //Get the card
                cardMaster = cardMasterList.get(0);

                //Get the total balance
                double points = rewardCurrencyService.getCashbackQtyForAmount(rewardCurrency,cardMaster.getCrmCardBalance());

                if(customerRewardBalance == null && points < pointsRequired){

                    //log error
                    log.error("isValid : Customer doesn't have balance to do cashback  Loyalty ID:"+validationRequest.getLoyaltyId());

                    //set validity flag to false
                    validationResponse.setValid(false);

                    //set validation remarks
                    validationResponse.setValidationRemarks("Customer doesn't have balance to do cashback");

                    //set error code
                    validationResponse.setApiErrorCode(APIErrorCode.ERR_NO_BALANCE);

                    validationResponse.setSpielName(MessageSpielValue.REDEMPTION_FAILED_NOT_ENOUGH_POINTS);
                    //return response
                    return validationResponse;

                } else if(customerRewardBalance != null){

                    double totalPoints = points + customerRewardBalance.getCrbRewardBalance();

                    //Check if total points are enough for cashback
                    //check if the customer has enough points balance for cashback
                    if(totalPoints < pointsRequired){

                        //log error
                        log.error("isValid : Insufficient points balance:"+validationRequest.getLoyaltyId());

                        //set validity flag to false
                        validationResponse.setValid(false);

                        //set validation remarks
                        validationResponse.setValidationRemarks("Customer doesn't have balance to do cashback");

                        //set error code
                        validationResponse.setApiErrorCode(APIErrorCode.ERR_INSUFFICIENT_POINT_BALANCE);

                        validationResponse.setValidationRemarks("Insufficient balance for cashback");

                        validationResponse.setSpielName(MessageSpielValue.CASHBACK_FAILED_NOT_ENOUGH_POINTS);

                        //return response
                        return validationResponse;

                    }

                }

            }

            //set validity flag to false
            validationResponse.setValid(true);

            return validationResponse;


        } else {

            if(customerRewardBalance == null){

                //log error
                log.error("isValid : Customer doesn't have balance to do cashback  Loyalty ID:"+validationRequest.getLoyaltyId());

                //set validity flag to false
                validationResponse.setValid(false);

                //set validation remarks
                validationResponse.setValidationRemarks("Customer doesn't have balance to do cashback");

                //set error code
                validationResponse.setApiErrorCode(APIErrorCode.ERR_NO_BALANCE);

                validationResponse.setSpielName(MessageSpielValue.REDEMPTION_FAILED_NOT_ENOUGH_POINTS);
                //return response
                return validationResponse;
            }

            //check if the customer has enough points balance for cashback
            if(customerRewardBalance.getCrbRewardBalance() < pointsRequired){

                //log error
                log.error("isValid : Insufficient points balance:"+validationRequest.getLoyaltyId());

                //set validity flag to false
                validationResponse.setValid(false);

                //set validation remarks
                validationResponse.setValidationRemarks("Customer doesn't have balance to do cashback");

                //set error code
                validationResponse.setApiErrorCode(APIErrorCode.ERR_INSUFFICIENT_POINT_BALANCE);

                validationResponse.setValidationRemarks("Insufficient balance for cashback");

                validationResponse.setSpielName(MessageSpielValue.CASHBACK_FAILED_NOT_ENOUGH_POINTS);

                //return response
                return validationResponse;

            }


            //set validity flag to false
            validationResponse.setValid(true);

            return validationResponse;
        }


    }

}

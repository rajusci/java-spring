package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class TransferPointServiceImpl implements TransferPointService,Injectable {


    private static Logger log = LoggerFactory.getLogger(TransferPointServiceImpl.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserMessagingService userMessagingService;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    @Autowired
    private TransferPointSettingService transferPointSettingService;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private AccountBundlingUtils accountBundlingUtils;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TierService tierService;

    @Autowired
    private LoyaltyEngineService loyaltyEngineService;

    @Autowired
    private LoyaltyEngineUtils loyaltyEngineUtils;

    @Autowired
    LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    PointTransferRequestService pointTransferRequestService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    CustomerActivityService customerActivityService;

    PartyApprovalService partyApprovalService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean transferPoints(TransferPointRequest transferPointRequest) throws InspireNetzException {

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",transferPointRequest.getFromLoyaltyId(),"","","",transferPointRequest.getMerchantNo(),new HashMap<String, String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);


        APIErrorCode apiErrorCode = null;

        // Get the customer information for sourceACcount
        Customer fromAccount = customerService.findByCusLoyaltyIdAndCusMerchantNo(transferPointRequest.getFromLoyaltyId(),transferPointRequest.getMerchantNo());

        // If the fromAccount is null, then show message
        if ( fromAccount == null ) {

            // Log the information
            log.info("transferPoints -> No from account information found");

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINT_NO_FROM_CUSTOMER);
            messageWrapper.setMobile(transferPointRequest.getFromLoyaltyId());
            messageWrapper.setMerchantNo(transferPointRequest.getMerchantNo());
            messageWrapper.setChannel(MessageSpielChannel.SMS);
            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(transferPointRequest.getToLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , source account is invalid :"+transferPointRequest.getFromLoyaltyId(),transferPointRequest.getMerchantNo(),"");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        //set the source account to transfer point request
        transferPointRequest.setFromCustomer(fromAccount);

        // Get the customer information for the toAccount
        Customer toAccount = customerService.findByCusLoyaltyIdAndCusMerchantNo(transferPointRequest.getToLoyaltyId(),transferPointRequest.getMerchantNo());

        // Check if the toAccout is null, then show message
        if ( toAccount == null ) {

            // Log the information
            log.info("transferPoints -> No to account information found");

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINT_NO_TO_CUSTOMER);
            messageWrapper.setLoyaltyId(fromAccount.getCusLoyaltyId());
            messageWrapper.setMerchantNo(fromAccount.getCusMerchantNo());
            messageWrapper.setChannel(MessageSpielChannel.ALL);
            messageWrapper.setIsCustomer(IndicatorStatus.YES);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(transferPointRequest.getFromLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , destination account is invalid :"+transferPointRequest.getToLoyaltyId(),transferPointRequest.getMerchantNo(),"");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        //set destination account info to the transfer point request
        transferPointRequest.setToCustomer(toAccount);

        // Get the fromRewardCurrency
        RewardCurrency fromRewardCurrency = rewardCurrencyService.findByRwdCurrencyId(transferPointRequest.getFromRewardCurrency());

        // Check if the fromRewardCurrency is null, then show message
        if ( fromRewardCurrency == null ) {

            // Log the information
            log.info("transferPoints -> No to from reward currency information found");

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINT_NO_FROM_CURRENCY);

            messageWrapper.setLoyaltyId(fromAccount.getCusLoyaltyId());
            messageWrapper.setMerchantNo(fromAccount.getCusMerchantNo());
            messageWrapper.setChannel(MessageSpielChannel.ALL);
            messageWrapper.setIsCustomer(IndicatorStatus.YES);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(transferPointRequest.getFromLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , no from reward currency found ",transferPointRequest.getMerchantNo(),"");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        //set source reward currency info
        transferPointRequest.setFromRewardCurrencyObj(fromRewardCurrency);

        // Get the toRewardCurrency
        RewardCurrency toRewardCurrency = rewardCurrencyService.findByRwdCurrencyId(transferPointRequest.getToRewardCurrency());

        // Check if the toRewardCurrency is null, then show message
        if ( toRewardCurrency == null ) {

            // Log the information
            log.info("transferPoints -> No to to reward currency information found");

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINT_NO_TO_CURRENCY);
            messageWrapper.setLoyaltyId(fromAccount.getCusLoyaltyId());
            messageWrapper.setMerchantNo(fromAccount.getCusMerchantNo());
            messageWrapper.setChannel(MessageSpielChannel.ALL);
            messageWrapper.setIsCustomer(IndicatorStatus.YES);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(transferPointRequest.getFromLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , no to reward currency found ",transferPointRequest.getMerchantNo(),"");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        //set destination reward currency info
        transferPointRequest.setToRewardCurrencyObj(toRewardCurrency);

        // Get the default TransferPointSetting
        TransferPointSetting transferPointSetting   = transferPointSettingService.getDefaultTransferPointSetting(transferPointRequest.getMerchantNo());

        //set transfer point setting to request
        transferPointRequest.setTransferPointSetting(transferPointSetting);

        // Check the validity
        isTransferRequestValid(fromAccount, toAccount, fromRewardCurrency, toRewardCurrency, transferPointSetting);

        //check transfer eligiblity
        transferPointRequest = checkTransferRequestEligibility(transferPointRequest);

        if(transferPointRequest.getEligibilityStatus() == RequestEligibilityStatus.INELIGIBLE){

            //log the error
            log.error("transferPoints : Transfer not allowed for user");


            //log the activity
            customerActivityService.logActivity(transferPointRequest.getFromLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , pasa point not allowed for customer ",transferPointRequest.getMerchantNo(),"");

            //throw error
            throw new InspireNetzException(APIErrorCode.ERR_TRANSFER_NOT_ALLOWED_FOR_SECONDARY);


        } else if(transferPointRequest.getEligibilityStatus() == RequestEligibilityStatus.APPROVAL_NEEDED){

            //log the error
            log.info("transferPoints : Approval needed for points transfer");

            //check whether a request alread exists
            if(transferPointRequest.getPtrId() == null){

                List<PointTransferRequest> pointTransferRequests = pointTransferRequestService.findByPtrMerchantNoAndPtrSourceAndPtrDestinationAndPtrStatus(transferPointRequest.getMerchantNo(),transferPointRequest.getFromLoyaltyId(),transferPointRequest.getToLoyaltyId(),TransferRequestStatus.NEW);

                if(pointTransferRequests.size() > 0){

                    //log error
                    log.error("transferPoints : Request already existing between the parties");

                    //log the activity
                    customerActivityService.logActivity(transferPointRequest.getFromLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , transfer request already exists ",transferPointRequest.getMerchantNo(),"");

                    //throw error
                    throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_TRANSFER_REQUEST);


                }
            }


            transferPointRequest = processTransferApproval(transferPointRequest);

            //if transfer is not allowed , return
            if(!transferPointRequest.isTransferAllowed()){


                //if the request is not approved return
                if(!transferPointRequest.isApproved()){

                    //log error
                    log.info("redeemSingleCatalogueItem: Transfer request rejected by linked account");

                    //set redemption status to approval rejected equest
                    pointTransferRequestService.updateTransferRequestStatus(transferPointRequest.getPtrId(), TransferRequestStatus.REJECTED);

                    //log the activity
                    customerActivityService.logActivity(transferPointRequest.getFromLoyaltyId(), CustomerActivityType.TRANSFER_POINT, "Failed , transfer request rejected by approver ", transferPointRequest.getMerchantNo(), "");

                    messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINTS_REQUEST_REJECTED);
                    messageWrapper.setLoyaltyId(fromAccount.getCusLoyaltyId());
                    messageWrapper.setMerchantNo(fromAccount.getCusMerchantNo());
                    messageWrapper.setChannel(MessageSpielChannel.ALL);
                    messageWrapper.setIsCustomer(IndicatorStatus.YES);

                    userMessagingService.transmitNotification(messageWrapper);

                    //throw error
                    throw new InspireNetzException(APIErrorCode.ERR_TRANSFER_REQUEST_REJECTED);

                } else {

                    //log error
                    log.info("redeemSingleCatalogueItem: Transfer request added , waiting for approval from linked account");

                    //log the activity
                    customerActivityService.logActivity(transferPointRequest.getFromLoyaltyId(), CustomerActivityType.TRANSFER_POINT, "Transfer request added, approval needed from linked account", transferPointRequest.getMerchantNo(), "");

                    //set redemption status to approval rejected equest
                    pointTransferRequestService.updateTransferRequestStatus(transferPointRequest.getPtrId(), TransferRequestStatus.NEW);


                    messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINT_WAITING_FOR_APPROVAL);
                    messageWrapper.setLoyaltyId(fromAccount.getCusLoyaltyId());
                    messageWrapper.setMerchantNo(fromAccount.getCusMerchantNo());
                    messageWrapper.setChannel(MessageSpielChannel.ALL);
                    messageWrapper.setIsCustomer(IndicatorStatus.YES);

                    userMessagingService.transmitNotification(messageWrapper);

                    //throw error
                    throw new InspireNetzException(APIErrorCode.ERR_TRANSFER_REQUEST_WAITING);
                }
            }

        }

        // Get the PointDeductData object
        PointDeductData pointDeductData = getPointDeductData(fromAccount,toAccount,fromRewardCurrency,transferPointRequest.getRewardQty());

        // Log the information
        log.info("transferPoints -> Debiting the source account " + pointDeductData.toString());

        boolean isDebit = false;


        try {

            // Now debit the points from source account
            isDebit = loyaltyEngineService.deductPoints(pointDeductData);

        }catch (InspireNetzException ex){

            apiErrorCode = ex.getErrorCode();
        }

        // Check if the debit is successful
        if( !isDebit ) {

            // Log the information
            log.info(" transferPoints -> Debiting failed ");

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINTS_DEBIT_FAILED);
            messageWrapper.setLoyaltyId(fromAccount.getCusLoyaltyId());
            messageWrapper.setMerchantNo(fromAccount.getCusMerchantNo());
            messageWrapper.setChannel(MessageSpielChannel.ALL);
            messageWrapper.setIsCustomer(IndicatorStatus.YES);

            userMessagingService.transmitNotification(messageWrapper);

            //if exception occured is insufficient point , log activity
            if(apiErrorCode.equals(APIErrorCode.ERR_INSUFFICIENT_POINT_BALANCE)){

                //log the activity
                customerActivityService.logActivity(transferPointRequest.getFromLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , insufficient point balance ",transferPointRequest.getMerchantNo(),"");

            } else {

                //log the activity
                customerActivityService.logActivity(transferPointRequest.getFromLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , point debiting failed ",transferPointRequest.getMerchantNo(),"");

            }

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }




        // If the debit was successful, then we need to create the credit
        // Get the PointRewardData
        PointRewardData pointRewardData = getPointRewardData(fromAccount,toAccount,fromRewardCurrency,toRewardCurrency,transferPointRequest.getRewardQty(),transferPointSetting);

        // Get the prebalance
        double preBalance = getPreBalanceForToAccount(toAccount,toRewardCurrency);

        // Get the Transaction object for the pointRewardData
        Transaction transaction = loyaltyEngineUtils.getTransactionForPointRewardData(pointRewardData,preBalance,fromAccount.getCusLoyaltyId());

        // Log the information
        log.info("transferPoints -> Crediting in destination account ->  " + pointRewardData.toString());


        boolean isCredit = false;

        try{

            // Call the awardPoints function
            isCredit = loyaltyEngineService.awardPoints(pointRewardData,transaction);

        }catch (InspireNetzException ex){


        }

        // Check if the credit is successful
        if ( !isCredit ) {

            // Log the information
            log.info(" transferPoints -> Crediting failed ");

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINTS_CREDIT_FAILED);
            messageWrapper.setLoyaltyId(fromAccount.getCusLoyaltyId());
            messageWrapper.setMerchantNo(fromAccount.getCusMerchantNo());
            messageWrapper.setChannel(MessageSpielChannel.ALL);
            messageWrapper.setIsCustomer(IndicatorStatus.YES);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(transferPointRequest.getFromLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , point crediting failed ",transferPointRequest.getMerchantNo(),"");

            //if crediting is failed make reward adjustment to the source account
            //create reward adjustment object
            RewardAdjustment rewardAdjustment = createRewardAdjustmentObject(transferPointRequest.getMerchantNo(),transferPointRequest.getFromLoyaltyId(),transferPointRequest.getFromRewardCurrency(),transferPointRequest.getRewardQty(),false,0L,transferPointRequest.getToLoyaltyId(),"Point reversal for transfer point failure");

            //reverse the points redeemed
            customerRewardBalanceService.awardPointsForRewardAdjustment(rewardAdjustment);

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        if(transferPointRequest.getPtrId() != null && transferPointRequest.getPtrId() != 0){

            pointTransferRequestService.updateTransferRequestStatus(transferPointRequest.getPtrId(),TransferRequestStatus.PROCESSED);
        }

        // Send the notification for debit
        sendTransferFromNotificationSMS(fromAccount,toAccount,transferPointRequest.getRewardQty(),fromRewardCurrency,messageWrapper);

        messageWrapper.setMobile(null);
        // Send the notification for credit
        sendTransferToNotificationSMS(fromAccount,toAccount,pointRewardData.getRewardQty(),toRewardCurrency,messageWrapper);


        // Finally return true
        return true;

    }


    private TransferPointRequest processTransferApproval(TransferPointRequest transferPointRequest) throws InspireNetzException {

        //get the requested customer
        Customer requestor = transferPointRequest.getFromCustomer();

        Customer approver = customerService.findByCusCustomerNo(transferPointRequest.getApproverCustomerNo());

        //if a new request , send approval request and save request
        if(transferPointRequest.getPtrId() == null || transferPointRequest.getPtrId() == 0){

            // Log the information
            log.info("transferPoints -> New request for point transfer received(Approval needed)");

            //add a new transfer point request
            transferPointRequest = addTransferPointRequest(transferPointRequest);

            log.info("transferPoints: Sending approval request to approver");

            //send approval request to approver
            partyApprovalService.sendApproval(requestor,approver,transferPointRequest.getPtrId(),PartyApprovalType.PARTY_APPROVAL_TRANSFER_POINT_REQUEST,transferPointRequest.getToLoyaltyId(),transferPointRequest.getRewardQty()+"");

            //set transfer allowed to false
            transferPointRequest.setTransferAllowed(false);

        } else {

            //CHECK whether approver has accepted the request
            boolean isTransferAllowed = isPartyApprovedTransfer(transferPointRequest);

            if(isTransferAllowed){

                log.info("transferPoints : Transfer is approved by the linked account");

                //set redemption allowed to true
                transferPointRequest.setTransferAllowed(true);

            } else {

                log.info("transferPoints : Transfer request rejected by linked account");

                //set redemption allowed to false
                transferPointRequest.setTransferAllowed(false);


            }
        }

        return transferPointRequest;
    }

    private boolean isPartyApprovedTransfer(TransferPointRequest transferPointRequest) {

        //get the requested customer
        Customer requestor = transferPointRequest.getFromCustomer();

        Customer approver = customerService.findByCusCustomerNo(transferPointRequest.getApproverCustomerNo());

        // Check if there is a entry in the LinkingApproval table
        PartyApproval partyApproval = partyApprovalService.getExistingPartyApproval(approver, requestor, PartyApprovalType.PARTY_APPROVAL_TRANSFER_POINT_REQUEST, transferPointRequest.getPtrId());

        // If the partyApproval is not found, then return false
        if ( partyApproval == null) {

            // Log the information
            log.info("isPartyApproved -> Party has not approved linking");

            // return false
            return false;

        } else {

            return transferPointRequest.isApproved();
        }

    }

    private TransferPointRequest addTransferPointRequest(TransferPointRequest transferPointRequest) throws InspireNetzException {

        //get a new point transfer request object
        PointTransferRequest pointTransferRequest = new PointTransferRequest();

        //set merchant no
        pointTransferRequest.setPtrMerchantNo(transferPointRequest.getMerchantNo());

        //set from loyalty id
        pointTransferRequest.setPtrSource(transferPointRequest.getFromLoyaltyId());

        //set to loyalty id
        pointTransferRequest.setPtrDestination(transferPointRequest.getToLoyaltyId());

        //set qty
        pointTransferRequest.setPtrRewardQty(transferPointRequest.getRewardQty());

        //set source reward currency
        pointTransferRequest.setPtrSourceCurrency(transferPointRequest.getFromRewardCurrency());

        //set destination reward currency
        pointTransferRequest.setPtrDestCurrency(transferPointRequest.getToRewardCurrency());

        //set merchant no
        pointTransferRequest.setPtrMerchantNo(transferPointRequest.getMerchantNo());

        //set requestor customer no
        pointTransferRequest.setPtrSourceCusNo(transferPointRequest.getRequestorCustomerNo());

        //set approver customer no
        pointTransferRequest.setPtrApproverCusNo(transferPointRequest.getApproverCustomerNo());

        //save the request
        pointTransferRequest = pointTransferRequestService.savePointTransferRequest(pointTransferRequest);

        //set the ptrId
        transferPointRequest.setPtrId(pointTransferRequest.getPtrId());

        return transferPointRequest;

    }

    private TransferPointRequest checkTransferRequestEligibility(TransferPointRequest transferPointRequest) {

        //get the source account
        Customer fromAccount = transferPointRequest.getFromCustomer();

        //get destination account
        Customer toAccount = transferPointRequest.getToCustomer();

        //check if account is linked
        transferPointRequest = isAccountLinked(transferPointRequest);

        //set request customer no as  from account customer no
        transferPointRequest.setRequestorCustomerNo(fromAccount.getCusCustomerNo());

        //if accounts are not linked , requestor is eligible for transfer
        if(!transferPointRequest.isAccountLinked()){

            //set transfer eligibility to ELIGIBLE
           transferPointRequest.setEligibilityStatus(RequestEligibilityStatus.ELIGIBLE);

        } else {

            //if linked , get the transfer point settings
            TransferPointSetting transferPointSetting = transferPointRequest.getTransferPointSetting();

            //check redemption settings
            switch(transferPointSetting.getTpsLinkedEligibilty()){

                case TransferPointSettingLinkedEligibity.NO_AUTHORIZATION:

                    //if authorization is not needed set eligibity to ELIGIBLE
                    transferPointRequest.setEligibilityStatus(RequestEligibilityStatus.ELIGIBLE);

                    return transferPointRequest;

                case TransferPointSettingLinkedEligibity.PRIMARY_ONLY:

                    //check the requestor is primary
                    if(!transferPointRequest.isCustomerPrimary()){

                        //if not primary , then set eligibility to INELIGIBLE
                        transferPointRequest.setEligibilityStatus(RequestEligibilityStatus.INELIGIBLE);

                    } else {

                        transferPointRequest.setEligibilityStatus(RequestEligibilityStatus.ELIGIBLE);
                    }

                    return transferPointRequest;

                case TransferPointSettingLinkedEligibity.SECONDARY_WITH_AUTHORIZATION:

                    //if customer is secondary , set eligibility to APRROVAL_NEEDED and set approver
                    //and requestor customer no's
                    if(!transferPointRequest.isCustomerPrimary()){

                        //set eligibility status as approval needed
                        transferPointRequest.setEligibilityStatus(RequestEligibilityStatus.APPROVAL_NEEDED);

                        //set approver customer no
                        transferPointRequest.setApproverCustomerNo(transferPointRequest.getParentCustomerNo());

                    } else {

                        //set eligibility to eligible
                        transferPointRequest.setEligibilityStatus(RequestEligibilityStatus.ELIGIBLE);

                    }

                    return transferPointRequest;

                case TransferPointSettingLinkedEligibity.ANY_ACCOUNT_WITH_AUTHORIZATION:

                    //set eligibility to approval needed
                    transferPointRequest.setEligibilityStatus(RequestEligibilityStatus.APPROVAL_NEEDED);

                    if(transferPointRequest.isCustomerPrimary()){

                        //set approver customer no
                        transferPointRequest.setApproverCustomerNo(transferPointRequest.getChildCustomerNo());

                    } else {

                        //set approver customer no
                        transferPointRequest.setApproverCustomerNo(transferPointRequest.getParentCustomerNo());

                    }

                    return transferPointRequest;

            }
        }

        return transferPointRequest;

    }

    public TransferPointRequest isCustomerPrimary(List<LinkedLoyalty> linkedLoyalties , TransferPointRequest transferPointRequest){

        //get the customer
        Customer customer = transferPointRequest.getFromCustomer();

        //iterate through the linked loyalties
        for(LinkedLoyalty linkedLoyalty : linkedLoyalties){

            //if the customer is primary , approval is needed from secondary
            if(linkedLoyalty.getLilParentCustomerNo().longValue() == customer.getCusCustomerNo()){

                //set customer primary as true
                transferPointRequest.setCustomerPrimary(true);

                //set approver customer no
                transferPointRequest.setParentCustomerNo(linkedLoyalty.getLilParentCustomerNo());

                transferPointRequest.setChildCustomerNo(linkedLoyalty.getLilChildCustomerNo());

            } else {

                //set customer primary as true
                transferPointRequest.setCustomerPrimary(false);

                transferPointRequest.setParentCustomerNo(linkedLoyalty.getLilParentCustomerNo());

                //set approver customer no
                transferPointRequest.setChildCustomerNo(linkedLoyalty.getLilChildCustomerNo());

            }

        }

        //return redemption request object
        return transferPointRequest;

    }

    private TransferPointRequest isAccountLinked(TransferPointRequest transferPointRequest) {

        //get the requested customer
        Customer fromCustomer = transferPointRequest.getFromCustomer();

        //check whether the customer is linked
        List<LinkedLoyalty> linkedLoyalties = linkedLoyaltyService.getAllLinkedAccounts(fromCustomer.getCusCustomerNo());

        //if account is not linked , set account linked as false
        if(linkedLoyalties == null || linkedLoyalties.size() == 0){

            transferPointRequest.setAccountLinked(false);

        } else {

            //if linked , get the primary customer info
            transferPointRequest = isCustomerPrimary(linkedLoyalties,transferPointRequest);

            //set accountLinked as true
            transferPointRequest.setAccountLinked(true);
        }

        return transferPointRequest;
    }


    /**
     * Function to check if the transfer request is valid,
     * This will check for the conditions based on the TransferPointSetting for the merchant
     *
     * @param fromCustomer          - The fromCustomer object
     * @param toCustomer            - The customer object to which points are transfferred
     * @param fromRewardCurrency    - The source reward currency
     * @param toRewardCurrency      - The destination reward currency
     * @param transferPointSetting  - The TransferPointSetting object
     * @return                      - true if the request is valid after checking all the conditions
     *                                false if the request is not valid
     * @throws InspireNetzException
     */
    public boolean isTransferRequestValid(Customer fromCustomer,Customer toCustomer,RewardCurrency fromRewardCurrency, RewardCurrency toRewardCurrency,TransferPointSetting transferPointSetting) throws InspireNetzException {

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",fromCustomer.getCusLoyaltyId(),"","","",fromCustomer.getCusMerchantNo(),new HashMap<String, String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

        // Check if the customer status is active
        if ( fromCustomer.getCusStatus() != CustomerStatus.ACTIVE ) {

            // Log the information
            log.info("transferPoints -> isTransferRequestValid -> The fromCustomer account is not active");

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINT_FROM_CUSTOMER_INACTIVE);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(fromCustomer.getCusLoyaltyId(), CustomerActivityType.TRANSFER_POINT, "Failed , source customer not active", fromCustomer.getCusMerchantNo(), "");

            // throw exception
            throw new InspireNetzException(APIErrorCode.ERR_PARTY_NOT_ACTIVE);

        }


        // Check if the toCustomer is active
        if ( toCustomer.getCusStatus() != CustomerStatus.ACTIVE ) {

            // Log the information
            log.info("transferPoints -> isTransferRequestValid -> The toCustomer account is not active");

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINT_TO_CUSTOMER_INACTIVE);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(fromCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , destination customer not active",fromCustomer.getCusMerchantNo(),"");

            // throw exception
            throw new InspireNetzException(APIErrorCode.ERR_PARTY_NOT_ACTIVE);

        }


        // Check if the both fromCustomer and toCustomer are same
        if ( fromCustomer.getCusCustomerNo().longValue() == toCustomer.getCusCustomerNo().longValue() ) {

            // Log the information
            log.info("transferPoints -> isTransferRequestValid -> sourceCustomer and destCustomer cannot be same");

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINT_CUSTOMERS_SAME);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(fromCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , source and destination customers are same",fromCustomer.getCusMerchantNo(),"");

            // throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }


        // Check if the transferPointSetting is valid
        if ( transferPointSetting == null ) {

            // Log the information
            log.info("transferPoints -> isTransferRequestValid ->  No TransferPointSetting object found");

            messageWrapper.setSpielName(MessageSpielValue.GENERAL_ERROR_MESSAGE);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(fromCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , no transfer point setting found",fromCustomer.getCusMerchantNo(),"");

            // throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // Check the validity for the location compatibility
        if ( fromCustomer.getCusLocation().longValue() != toCustomer.getCusLocation().longValue() &&
             transferPointSetting.getTpsTransferCompatibility() != TransferPointSettingCompatibility.ACROSS_LOCATIONS ) {

            // Log the information
            log.info("transferPoints -> isTransferRequestValid -> Transfer request across locations are not allowed");

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINT_ACROSS_LOCATION);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(fromCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , transfer request across mega brands not allowed",fromCustomer.getCusMerchantNo(),"");

            // return false
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);


        }


        // check if the customer tier is valid
        boolean isTierValid = isCustomerTierValid(fromCustomer);

        // If not valid, then throw exception
        if ( !isTierValid ) {

            // Log the information
            log.info("transferPoints -> isTransferRequestValid -> Customer tier does not allow transfer");

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINT_CUSTOMER_TIER_NOT_VALID);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(fromCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , customer tier doesn't allow transfer ",fromCustomer.getCusMerchantNo(),"");

            // return false
            throw new InspireNetzException(APIErrorCode.ERR_TRANSFER_REQUEST_TIER_NOT_VALID);

        }




        // Check if the customer has reached max transfer limit
        boolean isMaxTransferValid = isMaxTransfersValid(fromCustomer,transferPointSetting);

        // Check if the max transfer condition is valid
        if ( !isMaxTransferValid ) {

            // Log the information
            log.info("transferPoints -> isTransferRequestValid -> Customer exceeded maximum limit");

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINT_MAX_TRANSFER_LIMIT_REACHED);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(fromCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , customer exceeded maximum transfer limit ",fromCustomer.getCusMerchantNo(),"");

            // return false
            throw new InspireNetzException(APIErrorCode.ERR_MAX_TRANSFER_POINT_COUNT_EXCEEDED);

        }


        // Check if the parties involved are part of a linking
        boolean isPartOfLinking = isCustomersLinked(fromCustomer, toCustomer);

        // Check if the customers are part of linking
        if ( isPartOfLinking ) {

            // Log the information
            log.info("transferPoints -> isTransferRequestValid -> From customer and to customer are linked");

            messageWrapper.setSpielName(MessageSpielValue.TRANSFER_POINT_CUSTOMER_LINKED);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(fromCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , source and destination customers are linked ",fromCustomer.getCusMerchantNo(),"");

            // return false
            throw new InspireNetzException(APIErrorCode.ERR_TRANSFER_PARTIES_LINKED);

        }


        // Check if the customer
        return true;

    }


    /**
     * Function to check if the tier is valid for the Customer requesting the information
     * has got the tier allowed for transfer points
     *
     * @param fromCustomer  - The Customer object that needs to be checked
     * @return              - true if the customer is valid
     *                        false if tier is not defined or is not allowed
     */
    public boolean isCustomerTierValid(Customer fromCustomer) {

        // Get the tier for the customer
        Long tieId = fromCustomer.getCusTier();

        // Check if the tieId is null
        if ( tieId == null ) {

            // Log the information
            log.info("TransferPoints isCustomerTierValid -> Customer does not have a tier");

            // Return false
            return false;

        }




        // Get the Tier for the customer
        Tier tier = tierService.findByTieId(tieId);

        // Check if the tier is null
        if ( tier == null ) {

            // Log the information
            log.info("TransferPoints isCustomerTierValid -> Customer does not have a tier");

            // Return false
            return false;

        }



        // Check if the tier is transfer enabled
        if ( tier.getTieIsTransferPointsAllowedInd() != IndicatorStatus.YES ) {

            // Log the information
            log.info("TransferPoints isCustomerTierValid -> Customer tier does not allow transfer of points");

            // Return false
            return false;

        }



        // Finally return true
        return true;

    }


    /**
     * Function to check if the customer has done maximum number of transfers from the current loyalty id
     *
     * @param fromAccount           - The Customer account that need to be checked
     * @param transferPointSetting  - The TransferPointSetting object
     *
     * @return                      - Return true if the customer has not crossed the limit
     *                                Return false otherwise
     */
    public boolean isMaxTransfersValid(Customer fromAccount , TransferPointSetting transferPointSetting ) {


        // Get the firstDateForMonth
        Date firstDateForMonth = new Date(generalUtils.getFirstDateForCurrentMonth().getTime());

        // Get the lastDateForMonth
        Date lastDateForMonth = new Date(generalUtils.getLastDateForCurrentMonth().getTime());


        // Get the transactions list
        List<Transaction>  transactionList = transactionService.searchTransactionByTypeAndDateRange(fromAccount.getCusMerchantNo(),fromAccount.getCusLoyaltyId(), TransactionType.TRANSFER_POINT_TO,firstDateForMonth,lastDateForMonth);

        // If list is null, then return true
        if ( transactionList == null || transactionList.isEmpty() ) {

            // Log the information
            log.info("TransferPoints -> isMaxTransfersValid -> No transfer transactions found for the customer for current month ");

            // return true
            return true;

        }


        // Check if the list size is less than the setting
        if ( transactionList.size() >= transferPointSetting.getTpsMaxTransfers() ) {

            // Log the information
            log.info("TransferPoints -> isMaxTransfersValid -> Customer has done maximum number of transfers");

            // return false
            return false;

        }

        // finally return true;
        return true;

    }


    /**
     * Function to check if the source and destination accounts are linked to each other
     * in any way
     *
     * @param fromAccount       - The source Customer object
     * @param toAccount         - The destination Customer object
     * @return                  - true if the customers are linked
     *                             false otherwise
     */
    public boolean isCustomersLinked(Customer fromAccount, Customer toAccount) {

        // Get the  LinkedLoyalty for the fromAccount
        LinkedLoyalty fromLinkedLoyalty = accountBundlingUtils.getCustomerLinkedLoyalty(fromAccount.getCusMerchantNo(),fromAccount.getCusLoyaltyId());

        // Get the LinkedLoyalty for the toAccount
        LinkedLoyalty toLinkedLoyalty   = accountBundlingUtils.getCustomerLinkedLoyalty(toAccount.getCusMerchantNo(),toAccount.getCusLoyaltyId());



        // If both linked loyaltys are null, none of them are child
        if ( fromLinkedLoyalty == null && toLinkedLoyalty == null ){

            return false;

        }


        // If both of thme are not null , then check if they have  a common parent
        if ( fromLinkedLoyalty != null && toLinkedLoyalty != null ) {

            // Check if the parents for the linked loyalties are different
            if ( fromLinkedLoyalty.getLilParentCustomerNo().longValue() == toLinkedLoyalty.getLilParentCustomerNo().longValue() ) {

                // Log the information
                log.info("transferPoints -> isCustomersLinked -> From and two accounts are linked and having same parent");

                // return true
                return true;

            }

        }



        // If the fromLinkedLoyalty is not null, then kk if toACcount is parent
        if ( fromLinkedLoyalty != null ) {

            // Check if the from account's parent is toAccount
            if ( fromLinkedLoyalty.getLilParentCustomerNo().longValue() == toAccount.getCusCustomerNo().longValue() ) {

                // Log the information
                log.info("transferPoints -> isCustomersLinked -> To account is parent of from account");

                // return true
                return true;


            }

        }



        // If toLinkedLoyalty is not null, then check if the fromAccount is parent
        if ( toLinkedLoyalty != null ) {

            // Check if the fromAccount is parent for toAccount
            if ( toLinkedLoyalty.getLilParentCustomerNo().longValue() == fromAccount.getCusCustomerNo().longValue() ) {

                // Log the information
                log.info("transferPoints -> isCustomersLinked -> From account is parent of to account");

                // return true
                return true;

            }

        }



        // Finally return false indicating that they are not linked
        return false;

    }


    /**
     * Function to get the expiry date for the points based on the TransferPointSetting object
     *
     * @param transferPointSetting      - The TranfserPointSetting object
     *
     * @return                          - Date object representing the expiry date
     */
    public Date getExpiryDateForPoints(TransferPointSetting transferPointSetting ) {

        // Get the number of days validity
        int numDays = transferPointSetting.getTpsTransferredPointValidity();

        // Return the date
        return new Date(generalUtils.addDaysToToday(numDays).getTime());


    }


    /**
     * Function to get the  PointRewardData object from the Transfer Request params
     *
     * @param fromAccount           - Source account
     * @param toAccount             - Destination account
     * @param fromRewardCurrency    - Source reward currency
     * @param toRewardCurrency      - Destination reward currency
     * @param rewardQty             - Quantity to be transferred
     * @param transferPointSetting  - Setting object
     *
     * @return                      - Return the pointRewardData object
     */
    public PointRewardData getPointRewardData(Customer fromAccount, Customer toAccount, RewardCurrency fromRewardCurrency, RewardCurrency toRewardCurrency,double rewardQty, TransferPointSetting transferPointSetting ) {

        // Create the PointRewardData object
        PointRewardData pointRewardData =  new PointRewardData();


        // Set the fields
        pointRewardData.setMerchantNo(toAccount.getCusMerchantNo());

        pointRewardData.setLoyaltyId(toAccount.getCusLoyaltyId());


        pointRewardData.setRwdCurrencyId(toRewardCurrency.getRwdCurrencyId());

        pointRewardData.setRwdCurrencyName(toRewardCurrency.getRwdCurrencyName());



        // Get the creditRewardQty
        double creditRewardQty = getCreditRewardQty(fromRewardCurrency,toRewardCurrency,rewardQty);

        pointRewardData.setRewardQty(creditRewardQty);

        pointRewardData.setTxnType(TransactionType.TRANSFER_POINT_TO);



        pointRewardData.setTxnLocation(toAccount.getCusLocation());

        pointRewardData.setTxnDate(new Date(new java.util.Date().getTime()));

        pointRewardData.setExpiryDt(getExpiryDateForPoints(transferPointSetting));


        // Check if tierAffected field is set
        if ( transferPointSetting.getTpsIsTierAffected() == IndicatorStatus.YES ) {

            pointRewardData.setAddToAccumulatedBalance(true);

        } else{

            pointRewardData.setAddToAccumulatedBalance(false);

        }


        // Return the object
        return pointRewardData;
    }


    /**
     *
     * Function to get the PointDeductData object for the tranfer information
     *
     * @param fromAccount           - Source account
     * @param toAccount             - Destination account
     * @param fromRewardCurrency    - Source reward currency
     * @param rewardQty             - Quantity to be transferred
     *
     *
     * @return                      - Return PointDeductData object created from the passed params
     */
    public PointDeductData getPointDeductData(Customer fromAccount, Customer toAccount, RewardCurrency fromRewardCurrency,double rewardQty) {

        // Create the PointDeductData object
        PointDeductData pointDeductData = new PointDeductData();


        // Set the fields
        pointDeductData.setMerchantNo(fromAccount.getCusMerchantNo());


        pointDeductData.setRedeemQty(rewardQty);


        pointDeductData.setRwdCurrencyId(fromRewardCurrency.getRwdCurrencyId());

        pointDeductData.setRwdCurrencyName(fromRewardCurrency.getRwdCurrencyName());

        pointDeductData.setLoyaltyId(fromAccount.getCusLoyaltyId());

        pointDeductData.setTxnLocation(fromAccount.getCusLocation());



        pointDeductData.setTxnDate(new Date(new java.util.Date().getTime()));

        pointDeductData.setTxnType(TransactionType.TRANSFER_POINT_FROM);

        pointDeductData.setExternalRef(toAccount.getCusLoyaltyId());


        // return the object
        return pointDeductData;


    }


    /**
     * Function to get the prebalance for the destination customer currency
     *
     * @param toAccount         - The destination account
     * @param toRewardCurrency  - The destination reward currency
     *
     *
     * @return                  - 0 if there is no entry
     *                            balance if the entry exists
     */
    public double getPreBalanceForToAccount(Customer toAccount,RewardCurrency toRewardCurrency) {

        // Get the CustomerRewardBalance object
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(toAccount.getCusLoyaltyId(),toAccount.getCusMerchantNo(),toRewardCurrency.getRwdCurrencyId());


        // If the reward balance object is null, return 0
        if ( customerRewardBalance == null ) {

            return 0.0;

        }


        // Return balance
        return customerRewardBalance.getCrbRewardBalance();

    }


    /**
     * Function to get the credit reward qty
     *
     * @param fromRewardCurrency        - Source reward currency
     * @param toRewardCurrency          - destination reward currency
     * @param rewardQty                 - source reward qty
     *
     * @return                          - Return the creditQty in dest reward currency
     *
     * 
     */
    public double getCreditRewardQty(RewardCurrency fromRewardCurrency, RewardCurrency toRewardCurrency, double rewardQty ) {


        // Check if the ratioDeno is set for fromRewardCurrency, else set it to 1
        if ( fromRewardCurrency.getRwdCashbackRatioDeno() == null || fromRewardCurrency.getRwdCashbackRatioDeno().doubleValue() == 0.0 ) {

            fromRewardCurrency.setRwdCashbackRatioDeno(1.0);

        }


        // Check if the ratioDeno is set for toRewardCurrency, else set it to 1
        if ( toRewardCurrency.getRwdCashbackRatioDeno() == null || toRewardCurrency.getRwdCashbackRatioDeno().doubleValue() == 0.0 ) {

            toRewardCurrency.setRwdCashbackRatioDeno(1.0);

        }




        // Convert the rewardQty from source to amount
        double amount = ( 1 * rewardQty )/ fromRewardCurrency.getRwdCashbackRatioDeno();

        // Now covert the amount to quantity in toRewardcurrency
        double creditQty = amount * toRewardCurrency.getRwdCashbackRatioDeno();



        // Return the creditQty
        return creditQty;

    }


    @Async
    public boolean sendTransferFromNotificationSMS(Customer source, Customer dest, Double rewardQty, RewardCurrency rewardCurrency, MessageWrapper messageWrapper) throws InspireNetzException {

        // Create the Map with the data for the spiel
        HashMap<String,String> fields = new HashMap<>(0);

        // Add the qty
        fields.put("#qty",generalUtils.getFormattedValue(rewardQty));

        // Add the currencyname
        fields.put("#currencyname",rewardCurrency.getRwdCurrencyName());

        // Add the recepient name
        fields.put("#receipientname",dest.getCusLoyaltyId());

       /* // Get the reward balance
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(source.getCusLoyaltyId(),source.getCusMerchantNo(),rewardCurrency.getRwdCurrencyId());
*/
        //get the reward balance
        List<CustomerRewardBalance> customerRewardBalances = customerRewardBalanceService.searchBalances(source.getCusMerchantNo(),source.getCusLoyaltyId(),rewardCurrency.getRwdCurrencyId());

        //if the list contains any entries set reward balance to the placeholder
        if(customerRewardBalances != null && customerRewardBalances.size()>0){

            // Send the points formatted
            fields.put("#points",generalUtils.getFormattedValue(customerRewardBalances.get(0).getCrbRewardBalance()));

        }

        // Set the date
        String toDay = generalUtils.convertDateToFormat(new Date(System.currentTimeMillis()),"dd MMM yyyy");

        // Set the value for #date placeholder
        fields.put("#date", toDay);

        //log the activity
        customerActivityService.logActivity(source.getCusLoyaltyId(), CustomerActivityType.TRANSFER_POINT, "Transferred " + generalUtils.getFormattedValue(rewardQty) + " points to :" + dest.getCusLoyaltyId(), source.getCusMerchantNo(), "");

        messageWrapper.setParams(fields);

        messageWrapper.setSpielName(MessageSpielValue.SPIEL_TRANSFER_POINT_FROM);


        messageWrapper.setMerchantNo(source.getCusMerchantNo());

        messageWrapper.setLoyaltyId(source.getCusLoyaltyId());

        messageWrapper.setChannel(MessageSpielChannel.ALL);

        messageWrapper.setIsCustomer(IndicatorStatus.YES);

        return  userMessagingService.transmitNotification(messageWrapper);

    }


    @Async
    public boolean sendTransferToNotificationSMS(Customer source, Customer dest, Double rewardQty, RewardCurrency rewardCurrency, MessageWrapper messageWrapper) throws InspireNetzException {

        // Create the Map with the data for the spiel
        HashMap<String,String> fields = new HashMap<>(0);

        // Add the qty
        fields.put("#qty",generalUtils.getFormattedValue(rewardQty));

        // Add the currencyname
        fields.put("#currencyname",rewardCurrency.getRwdCurrencyName());

        // Add the sender name
        fields.put("#sender",source.getCusLoyaltyId());

        /*// Get the reward balance
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(dest.getCusLoyaltyId(),dest.getCusMerchantNo(),rewardCurrency.getRwdCurrencyId());*/

        //get the reward balance
        List<CustomerRewardBalance> customerRewardBalances = customerRewardBalanceService.searchBalances(dest.getCusMerchantNo(),dest.getCusLoyaltyId(),rewardCurrency.getRwdCurrencyId());

        //if the list contains any entries set reward balance to the placeholder
        if(customerRewardBalances != null && customerRewardBalances.size()>0){

            // Send the points formatted
            fields.put("#points",generalUtils.getFormattedValue(customerRewardBalances.get(0).getCrbRewardBalance()));

        }


        // Set the date
        String toDay = generalUtils.convertDateToFormat(new Date(System.currentTimeMillis()),"dd MMM yyyy");

        // Replace the date
        fields.put("#date", toDay);

        //log the activity
        customerActivityService.logActivity(dest.getCusLoyaltyId(), CustomerActivityType.TRANSFER_POINT, "Received " + generalUtils.getFormattedValue(rewardQty) + " points from :" + source.getCusLoyaltyId(), source.getCusMerchantNo(), "");

        messageWrapper.setParams(fields);

        messageWrapper.setSpielName(MessageSpielValue.SPIEL_TRANSFER_POINT_TO);

        messageWrapper.setLoyaltyId(dest.getCusLoyaltyId());
        messageWrapper.setMerchantNo(dest.getCusMerchantNo());
        messageWrapper.setChannel(MessageSpielChannel.ALL);
        messageWrapper.setIsCustomer(IndicatorStatus.YES);

        return  userMessagingService.transmitNotification(messageWrapper);

    }

    private RewardAdjustment createRewardAdjustmentObject(Long merchantNo, String loyaltyId, Long adjCurrencyId, Double adjPoints, boolean isTierAffected, Long adjProgramNo, String adjIntReference,String adExtReference) {

        RewardAdjustment rewardAdjustment = new RewardAdjustment();

        //set values to the object
        rewardAdjustment.setLoyaltyId(loyaltyId);
        rewardAdjustment.setRwdCurrencyId(adjCurrencyId);
        rewardAdjustment.setRwdQty(adjPoints);
        rewardAdjustment.setMerchantNo(merchantNo);
        rewardAdjustment.setExternalReference(adExtReference);
        rewardAdjustment.setIntenalReference(adjIntReference);
        rewardAdjustment.setProgramNo(adjProgramNo);
        rewardAdjustment.setTierAffected(isTierAffected);
        rewardAdjustment.setPointReversed(true);

        //return the reward adjustment object
        return rewardAdjustment;
    }

    @Override
    public void inject(TransferPointUtils beansManager) {

        this.partyApprovalService = beansManager.getPartyApprovalService();

    }
}

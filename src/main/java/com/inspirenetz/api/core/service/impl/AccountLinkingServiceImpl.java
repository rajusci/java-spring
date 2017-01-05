package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AccountBundlingUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/8/14.
 */
@Service
@Transactional
public class AccountLinkingServiceImpl  implements AccountLinkingService {

    // Initialize the logger
    private static Logger log = LoggerFactory.getLogger(LoyaltyEngineServiceImpl.class);



    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    private LinkRequestService linkRequestService;

    @Autowired
    private PrimaryLoyaltyService primaryLoyaltyService;

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    private PartyApprovalService partyApprovalService;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;

    @Autowired
    private LinkedRewardBalanceService linkedRewardBalanceService;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private AccountBundlingUtils accountBundlingUtils;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    UserMessagingService userMessagingService;

    @Autowired
    TierGroupService tierGroupService;

    @Autowired
    TierService tierService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    CustomerActivityService customerActivityService;

    public boolean processLinkRequest(LinkRequest linkRequest, String loyaltyId) throws InspireNetzException {

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",loyaltyId,"","","",linkRequest.getLrqMerchantNo(),new HashMap<String ,String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

        // Log the information
        log.info("processLinkRequest -> Staring processing for " + linkRequest);

        // Check if the linkRequest is not null
        if ( linkRequest == null ) {

            // Log the information
            log.info("processLinkRequest -> Request is null ");

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , request is null",linkRequest.getLrqMerchantNo(),"");

            // Return
            return false;

        }



        // Read the AccountBundlingSetting
        AccountBundlingSetting accountBundlingSetting = accountBundlingSettingService.getDefaultAccountBundlingSetting(linkRequest.getLrqMerchantNo());

        // If no bundling setting is found, then return false
        if  ( accountBundlingSetting == null ) {

            // Log the information
            log.info("processLinkRequest -> AccountBundlingSetting is null ");

            messageWrapper.setSpielName(MessageSpielValue.GENERAL_ERROR_MESSAGE);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , no account linking setting found",linkRequest.getLrqMerchantNo(),"");

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("AccountBundlingSetting is null");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            // Return
            return false;

        }

        // Get the Source Customer
        Customer secondaryCustomer = customerService.findByCusCustomerNo(linkRequest.getLrqSourceCustomer());

        // Log the information
        log.info("processLinkRequest -> Source customer information " + secondaryCustomer);


        // Get the primary customer
        Customer primaryCustomer = customerService.findByCusCustomerNo(linkRequest.getLrqParentCustomer());

        // Log the information
        log.info("processLinkRequest -> Primary customer information " + primaryCustomer);

        //check if the initiator is valid
        boolean isInitiatorValid = isInititatorValid(primaryCustomer.getCusLoyaltyId(),secondaryCustomer.getCusLoyaltyId(),loyaltyId);


        if( !isInitiatorValid){


            messageWrapper.setSpielName(MessageSpielValue.LINK_REQUEST_INVALID_INITIATOR);

            userMessagingService.transmitNotification(messageWrapper);

            // Update the status to failed
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED) ;

            //set remarks as the approver has rejected the request
            linkRequest.setLrqRemarks("Invalid Request initiator");

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , invalid request initiator",linkRequest.getLrqMerchantNo(),"");

            return false;
        }

        // check if the linkRequest is valid
        boolean isValid = isLinkRequestValid(linkRequest,accountBundlingSetting,loyaltyId);


        // check if the link request is valid
        if ( !isValid ) {

            // Log the information
            log.info("processLinkRequest -> Link request is not valid , ending processing ");

            messageWrapper.setSpielName(MessageSpielValue.LINK_REQUEST_NOT_VALID);

            userMessagingService.transmitNotification(messageWrapper);

            // return  the control;
            return false;

        }

        // Set the isValid to false
        isValid  = false;

        // Now based on the linkRequestType, call the validation method for the parties
        if ( linkRequest.getLrqType() == LinkRequestType.LINK ) {

            // Check if the linkRequest is valid for the parties
            isValid = isLinkingRequestValidForParties(secondaryCustomer, primaryCustomer, accountBundlingSetting,loyaltyId,linkRequest);

        } else {

            // Check if the unlink request is valid for the parties
            // Check if the linkRequest is valid for the parties
            isValid = isUnlinkingRequestValidForParties(secondaryCustomer, primaryCustomer, accountBundlingSetting,linkRequest,loyaltyId);

        }



        // check if the linking parties are valid
        if ( !isValid ) {

            // Log the information
            log.info("processLinkRequest -> Linking parties are not valid");

            // return  the control;
            return false;

        }





        // If the request is valid, then check if the linking is confirmed by the parties
        // as per the settings
        boolean isConfirmed = isRequestConfirmed(linkRequest, primaryCustomer, secondaryCustomer, accountBundlingSetting);

        // If the linking is not yet confirmed, then we need to send the approval and return
        if ( !isConfirmed ) {

            // Log the information
            log.info("processLinkRequest ->  Request not confirmed, adding entries to LinkingApproval");

            // Get the status
            int status = processApprovalForParty(linkRequest, primaryCustomer, secondaryCustomer, accountBundlingSetting);

            // Check the status
            if ( status == PartyApprovalStatus.REJECTED )   {

                // Log the information
                log.info("processLinkRequest -> Party has rejected the  request.. Aborting");

                // Update the status to failed
                linkRequest.setLrqStatus(LinkRequestStatus.FAILED) ;

                //set remarks as the approver has rejected the request
                linkRequest.setLrqRemarks("Approver rejected the request");

                // Save the data
                linkRequestService.saveLinkRequest(linkRequest);

                //log the activity
                customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed ,approver rejected request",linkRequest.getLrqMerchantNo(),"");

                //if the rejected request is of unlinking, then make linked loyalty as active
                if(linkRequest.getLrqType() == LinkRequestType.UNLINK){


                    // Get the linked loyalty of the request
                    LinkedLoyalty linkedLoyalty = linkedLoyaltyService.findByLilChildCustomerNo(secondaryCustomer.getCusCustomerNo());

                    //set status to active
                    linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.ACTIVE);

                    //save linked loyalty
                    linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);

                }

                //create a map for the sms placeholders
                HashMap<String , String > smsParams  = new HashMap<>(0);

                //put the placeholders into the map
                smsParams.put("#min",primaryCustomer.getCusLoyaltyId());

                messageWrapper.setParams(smsParams);

                messageWrapper.setSpielName(MessageSpielValue.PRIMARY_REJECTED_LINKING);

                userMessagingService.transmitNotification(messageWrapper);

                // Return the control
                return false;

            } else if(status == PartyApprovalStatus.QUEUED){

                // Update the status to failed
                linkRequest.setLrqStatus(LinkRequestStatus.PENDING) ;

                //set remark for linking type requests
                if(linkRequest.getLrqType() == LinkRequestType.LINK){

                    //set remarks as request has sent for confirmation
                    linkRequest.setLrqRemarks("Request sent for linking confirmation");

                } else {

                    //set remarks as request sent for confirmation
                    linkRequest.setLrqRemarks("Request sent for unlinking confirmation");

                }


                // Save the data
                linkRequestService.saveLinkRequest(linkRequest);

                //log the activity
                customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,linkRequest.getLrqRemarks(),linkRequest.getLrqMerchantNo(),"");

            }


            // Return false
            return false;

        }



        // Update the linking tables
        boolean isUpdateLinkingTables = updateLinkingTables(primaryCustomer,secondaryCustomer,linkRequest.getLrqType());

        // Check if the update is success
        if ( !isUpdateLinkingTables ) {

            // Log the information
            log.info("processLinkRequest -> Updating of the linked tables failed");

            messageWrapper.setSpielName(MessageSpielValue.GENERAL_ERROR_MESSAGE);

            userMessagingService.transmitNotification(messageWrapper);

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("Updating of the linked tables failed");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Updating of the linked tables failed",linkRequest.getLrqMerchantNo(),"");

            // return the control
            return false;

        }



        // Update the balance tables
        boolean isUpdateBalanceTables = updateBalanceTables(primaryCustomer,secondaryCustomer,linkRequest.getLrqType(),accountBundlingSetting);

        // Check if the update is success
        if ( !isUpdateBalanceTables ) {

            // Log the information
            log.info("processLinkRequest -> Updating of the balance tables failed");

            messageWrapper.setSpielName(MessageSpielValue.GENERAL_ERROR_MESSAGE);

            userMessagingService.transmitNotification(messageWrapper);

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("Updating of the balance tables failed");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Updating of the balance tables failed",linkRequest.getLrqMerchantNo(),"");

            // return the control
            return false;

        }



        if ( linkRequest.getLrqType() == LinkRequestType.LINK ) {

            // Update the status to failed
            linkRequest.setLrqStatus(LinkRequestStatus.LINKED) ;

            //set remarks for the link request
            linkRequest.setLrqRemarks("Accounts Linked Successfully");

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Accounts linked successfully",linkRequest.getLrqMerchantNo(),"");

        } else {

            // Update the status to failed
            linkRequest.setLrqStatus(LinkRequestStatus.UNLINKED) ;

            //set remarks for the link request
            linkRequest.setLrqRemarks("Accounts Unlinked Successfully");

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Accounts unlinked successfully",linkRequest.getLrqMerchantNo(),"");
        }


        // Save the data
        linkRequestService.saveLinkRequest(linkRequest);

        // Finally update the status as linked
        // Log the information
        log.info("processLinkRequest -> Processing successful " + linkRequest);

        if(isConfirmed && linkRequest.getLrqStatus() == LinkRequestStatus.LINKED){


            //create a map for the sms placeholders
            HashMap<String , String > smsParams  = new HashMap<>(0);

            smsParams.put("#linkingAccount",secondaryCustomer.getCusLoyaltyId());


            messageWrapper.setSpielName(MessageSpielValue.LINK_SUCCESS_SUBSCRIBER_CONFIRMED);

            messageWrapper.setLoyaltyId(primaryCustomer.getCusLoyaltyId());

            messageWrapper.setParams(smsParams);

            userMessagingService.transmitNotification(messageWrapper);

            //create a map for the sms placeholders
            smsParams  = new HashMap<>(0);

            smsParams.put("#linkingAccount",primaryCustomer.getCusLoyaltyId());

            messageWrapper.setSpielName(MessageSpielValue.LINK_SUCCESS_SUBSCRIBER_CONFIRMED);

            messageWrapper.setLoyaltyId(secondaryCustomer.getCusLoyaltyId());

            messageWrapper.setParams(smsParams);

            userMessagingService.transmitNotification(messageWrapper);

        }

        //update the tiers of the primary and secondary
        evaluateTiersOfAccounts(primaryCustomer,secondaryCustomer);

        // Finally return true
        return true;

    }

    @Async
    private void evaluateTiersOfAccounts(Customer primaryCustomer, Customer secondaryCustomer) {

        // Get the tierGroupList for the merchant
        List<TierGroup> tierGroupList = tierGroupService.findByTigMerchantNo(primaryCustomer.getCusMerchantNo());

        // Call the evaulate tier method for primary customer
        tierService.evaluateTierForCustomer(primaryCustomer,tierGroupList);

        //call the evaluate tier method for secondary customer
        tierService.evaluateTierForCustomer(secondaryCustomer, tierGroupList);

    }

    private boolean isInititatorValid(String primaryLoyaltyId, String secondaryLoyaltyId , String loyaltyId) {

        //check whether the request is receieved from child or primary , if not throw error
        if(!primaryLoyaltyId.equals(loyaltyId) && !secondaryLoyaltyId.equals(loyaltyId)){

            //log error
            log.error("createLinkRequest : Link requested initiated neither by primary nor child");

            //return false;
             return false;
        }

        return true;
    }


    // General validation check
    protected boolean isLinkRequestValid(LinkRequest linkRequest, AccountBundlingSetting accountBundlingSetting, String loyaltyId) throws InspireNetzException {

        if(linkRequest.isUnregisterRequest()){

            return true;
        }

        // Check the status of the request
        if ( linkRequest.getLrqStatus() != LinkRequestStatus.PENDING ) {

            // Log the information
            log.info("isLinkRequestValid -> Request status is not pending ");

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("Request status is not pending");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed ,request is not pending",linkRequest.getLrqMerchantNo(),"");

            // Return false
            return false;

        }


        // If the initiator is not primary and account settings does not allow any account
        // initiation, then we need to return false
        if ( linkRequest.getLrqInitiator() != LinkRequestInitiator.PRIMARY &&
                accountBundlingSetting.getAbsBundlingActionInitiation() != AccountBundlingSettingInitiation.ANY_ACCOUNT ) {

            // Log the information
            log.info("isLinkRequestValid -> Request initiation by secondary is not allowed ");

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("Request initiation by secondary is not allowed");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed ,request initiation by secondary is not allowed",linkRequest.getLrqMerchantNo(),"");

            // Return false
            return false;

        }


        // Return true
        return true;

    }

    // Unlink validation check
    protected boolean isUnlinkingRequestValidForParties(Customer secondary, Customer primary, AccountBundlingSetting accountBundlingSetting, LinkRequest linkRequest,String loyaltyId) throws InspireNetzException {

        if(linkRequest.isUnregisterRequest()){

            return true;
        }
        // Make sure secondary is not null
        if ( secondary == null  || secondary.getCusStatus() != CustomerStatus.ACTIVE ){

            // Log the information
            log.info("isUnlinkingRequestValidForParties -> Secondary information not found or is not active");

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("Secondary information not found or is not active");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , secondary is not active",linkRequest.getLrqMerchantNo(),"");

            // Return false
            return false;

        }



        // Make sure primary is not null
        if ( primary == null || primary.getCusStatus() != CustomerStatus.ACTIVE ){

            // Log the information
            log.info("isUnlinkingRequestValidForParties -> Primary informtion not found or is not active");

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("Primary informtion not found or is not active");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            //log the activity
            customerActivityService.logActivity(loyaltyId, CustomerActivityType.ACCOUNT_LINKING, "Failed , primary is not active", linkRequest.getLrqMerchantNo(), "");

            // Return false
            return false;

        }


        // Make sure that the secondary is currently linked to primary in LinkedLoyalty table
        LinkedLoyalty linkedLoyalty = linkedLoyaltyService.findByLilChildCustomerNo(secondary.getCusCustomerNo());

        // If the LinkedLoyalty has entry valid, then the secondary is already linked to another primary
        if ( linkedLoyalty == null || linkedLoyalty.getLilParentCustomerNo().longValue() != primary.getCusCustomerNo().longValue() ) {

            // Log the information
            log.info("isUnlinkingRequestValidForParties -> Secondary is not linked with primary: " + linkedLoyalty);

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("Secondary is not linked with primary");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            //log the activity
            customerActivityService.logActivity(loyaltyId, CustomerActivityType.ACCOUNT_LINKING, "Failed , accounts are not linked", linkRequest.getLrqMerchantNo(), "");

            // Return false
            return false;

        }



        // Finally return true
        return true;

    }

    // Linking validation checks
    protected boolean isLinkingRequestValidForParties(Customer secondary, Customer primary, AccountBundlingSetting accountBundlingSetting, String loyaltyId, LinkRequest linkRequest) throws InspireNetzException {

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",loyaltyId,"","","",linkRequest.getLrqMerchantNo(),new HashMap<String,String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

        // Check if the secondary is valid
        boolean isValid = isSecondaryValidForLinking(secondary,accountBundlingSetting,loyaltyId,linkRequest);

        // If the secondary is not valid, then return false
        if ( !isValid ) {

            // Log the information
            log.info("isLinkingRequestValidForParties -> Secondary is not valid for linking");


            // Return false
            return false;

        }


        // Check if the primary is valid
        isValid = isPrimaryValidForLinking(primary,accountBundlingSetting,loyaltyId,linkRequest);

        // If the secondary is not valid, then return false
        if ( !isValid ) {

            // Log the information
            log.info("isLinkingRequestValidForParties -> Primary is not valid for linking");

            // Return false
            return false;

        }


        // Check if source and primary are same
        if ( primary.getCusCustomerNo().longValue() == secondary.getCusCustomerNo().longValue() ) {

            // Log the information
            log.info("isLinkingRequestValidForParties -> Primary and secondary are same");

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("Primary and secondary are same");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            messageWrapper.setSpielName(MessageSpielValue.LINK_ERROR_PRIMARY_SECONDARY_SAME);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , primary and Secondary are same",linkRequest.getLrqMerchantNo(),"");

            // Return false
            return false;

        }

        // If both are valid, check if they are location valid
        if ( accountBundlingSetting.getAbsLinkingEligibility() == AccountBundlingLinkingEligibility.SAME_LOCATION_ONLY ) {

            // Check if both primary and secondary are of same location
            if ( primary.getCusLocation().longValue() != secondary.getCusLocation().longValue() ) {

                // Log the information
                log.info("isLinkingRequestValidForParties -> Primary and secondary are of different locations");

                //set the link request status to failed and save the request
                linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

                linkRequest.setLrqRemarks("Primary and secondary are of different locations");

                //save the link request
                linkRequestService.saveLinkRequest(linkRequest);

                messageWrapper.setSpielName(MessageSpielValue.LINK_ERROR_DIFFERENT_MEGA_BRANDS);

                userMessagingService.transmitNotification(messageWrapper);

                //log the activity
                customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , Linking across megabrands is not allowed",linkRequest.getLrqMerchantNo(),"");

                // Return false
                return false;

            }
        }


        // Check if the primary is valid
        return true;


    }

    protected boolean isSecondaryValidForLinking(Customer customer, AccountBundlingSetting accountBundlingSetting, String loyaltyId, LinkRequest linkRequest) throws InspireNetzException {

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",loyaltyId,"","","",linkRequest.getLrqMerchantNo(),new HashMap<String,String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

        // Check if the customer is valid
        if ( customer == null ){

            // Log the information
            log.info("isSecondaryValidForLinking -> No secondary information found");

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("No secondary information found");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , no secondary information found",linkRequest.getLrqMerchantNo(),"");

            // Return false
            return false;

        }


        // Check the status of the customer
        if ( customer.getCusStatus() != CustomerStatus.ACTIVE ) {

            // Log the information
            log.info("isSecondaryValidForLinking -> Secondary is not active");

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("Secondary is not active");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            messageWrapper.setSpielName(MessageSpielValue.LINK_ERROR_CHILD_NOT_ACTIVE);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , secondary is not active",linkRequest.getLrqMerchantNo(),"");

            // Return false
            return false;

        }


        // Check if the secondary is already primary for any other account
        PrimaryLoyalty primaryLoyalty =  primaryLoyaltyService.findByPllCustomerNo(customer.getCusCustomerNo());

        // If the PrimaryLoyalty is not null then, we have a error
        if ( primaryLoyalty != null && primaryLoyalty.getPllId() != null ) {

            // Log the information
            log.info("isSecondaryValidForLinking -> Secondary is primary for another account : " + primaryLoyalty.toString());

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("Secondary is primary for another account");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            //create a map for the sms placeholders
            HashMap<String , String > smsParams  = new HashMap<>(0);

            messageWrapper.setSpielName(MessageSpielValue.LINK_ERROR_SUBSCRIBER_IS_A_CHILD);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , secondary is primary to another account",linkRequest.getLrqMerchantNo(),"");

            // Return false
            return false;

        }


        // Check if the customer has entry in LinkedLoyalty
        LinkedLoyalty linkedLoyalty = linkedLoyaltyService.findByLilChildCustomerNo(customer.getCusCustomerNo());

        // If the LinkedLoyalty has entry valid, then the secondary is already linked to another primary
        if ( linkedLoyalty != null && linkedLoyalty.getLilId() != null ) {

            // Log the information
            log.info("isSecondaryValidForLinking -> Secondary is already linked to another account : " + linkedLoyalty.toString());


            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("Secondary is already linked to another account");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            //create a map for the sms placeholders
            HashMap<String , String > smsParams  = new HashMap<>(0);

            messageWrapper.setSpielName(MessageSpielValue.LINK_ERROR_SUBSCRIBER_IS_ALREADY_LINKED);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , secondary is already linked to another account",linkRequest.getLrqMerchantNo(),"");

            // Return false
            return false;

        }



        // TODO - Check if the last entry in the linking approval and see if its
        // less than set period



        // If all the conditions are valid
        return true;


    }

    protected boolean isPrimaryValidForLinking(Customer customer, AccountBundlingSetting accountBundlingSetting, String loyaltyId, LinkRequest linkRequest) throws InspireNetzException {

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",loyaltyId,"","","",linkRequest.getLrqMerchantNo(),new HashMap<String,String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

        // Check if the customer is valid
        if ( customer == null ){

            // Log the information
            log.info("isPrimaryValidForLinking -> No primary information found");

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            linkRequest.setLrqRemarks("No primary information found");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , no primary information found",linkRequest.getLrqMerchantNo(),"");

            // Return false
            return false;

        }


        // Check the status of the customer
        if ( customer.getCusStatus() != CustomerStatus.ACTIVE ) {

            // Log the information
            log.info("isPrimaryValidForLinking -> Primary is not active");

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            //set the error as remarks
            linkRequest.setLrqRemarks("Primary is not active");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            messageWrapper.setSpielName(MessageSpielValue.LINK_ERROR_PRIMARY_NOT_ACTIVE);

            userMessagingService.transmitNotification(messageWrapper);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , primary is not active",linkRequest.getLrqMerchantNo(),"");

            // Return false
            return false;

        }


        // Check if the customer has entry in LinkedLoyalty
        LinkedLoyalty linkedLoyalty = linkedLoyaltyService.findByLilChildCustomerNo(customer.getCusCustomerNo());

        // If the LinkedLoyalty has entry valid, then the secondary is already linked to another primary
        if ( linkedLoyalty != null && linkedLoyalty.getLilId() != null ) {

            // Log the information
            log.info("isPrimaryValidForLinking -> Primary is already linked to another account : " + linkedLoyalty.toString());

            //set the link request status to failed and save the request
            linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

            //set the error as remarks
            linkRequest.setLrqRemarks("Primary is already linked to another account");

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , primary is already linked to another account",linkRequest.getLrqMerchantNo(),"");

            //save the link request
            linkRequestService.saveLinkRequest(linkRequest);

            messageWrapper.setSpielName(MessageSpielValue.LINK_ERROR_PRIMARY_IS_ALREADY_LINKED);

            userMessagingService.transmitNotification(messageWrapper);

            // Return false
            return false;

        }

        // Check the setting for primary account eligibility
        if ( accountBundlingSetting.getAbsPrimaryAccountEligibility() == AccountBundlingPrimaryAccountEligibility.SPECIFIC_CATEGORY ) {

            // Get the category list for the primary
            List<CustomerSubscription> customerSubscriptionList = customerSubscriptionService.findByCsuCustomerNo(customer.getCusCustomerNo());

            // Flag showing if the cateogyr is valid
            boolean isCategoryValid = false;

            // Go throug the CustomerSubscription and see if the list is valid
            for (CustomerSubscription customerSubscription : customerSubscriptionList ) {

                // Get the Product
                Product product = customerSubscription.getProduct();

                // If the product is null, continue to next iteration
                if ( product == null ) {

                    continue;
                }


                // Check if the product categories are matching the category in the setting
                if ( product.getPrdCategory1() != null &&  product.getPrdCategory1().equals(accountBundlingSetting.getAbsPrimaryAccountCategory()) ||
                     product.getPrdCategory2() != null &&  product.getPrdCategory2().equals(accountBundlingSetting.getAbsPrimaryAccountCategory()) ||
                     product.getPrdCategory3() != null &&  product.getPrdCategory3().equals(accountBundlingSetting.getAbsPrimaryAccountCategory()) ) {

                    // Set the flag to true
                    isCategoryValid = true;

                    // Break the loop
                    break;

                }

            }


            //  If the category is not valid, then retunn false
            if ( ! isCategoryValid ) {

                // Log the information
                log.info("isPrimaryValidForLinking -> Primary eligibility category is not valid");

                //set the link request status to failed and save the request
                linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

                //set the error as remarks
                linkRequest.setLrqRemarks("Primary eligibility category is not valid");

                //save the link request
                linkRequestService.saveLinkRequest(linkRequest);

                messageWrapper.setSpielName(MessageSpielValue.LINK_ERROR_PRIMARY_CATEGORY_INVALID);

                userMessagingService.transmitNotification(messageWrapper);
                //log the activity
                customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , primary eligibility category is not valid",linkRequest.getLrqMerchantNo(),"");

                // Return false
                return false;

            }

        }


        // Get all the link requests for the primary
        List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findByLilParentCustomerNo(customer.getCusCustomerNo());

        // Check if the count is greater
        if ( linkedLoyaltyList != null && !linkedLoyaltyList.isEmpty() ) {

            // Check if the max count is reached
            if ( linkedLoyaltyList.size() >=  accountBundlingSetting.getAbsLinkedAccountLimit() ) {

                // Log the information
                log.info("isPrimaryValidForLinking -> Primary has reached the maximum linked accounts");

                //set the link request status to failed and save the request
                linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

                //set the error as remarks
                linkRequest.setLrqRemarks("Primary has reached the maximum linked accounts");

                //save the link request
                linkRequestService.saveLinkRequest(linkRequest);

                messageWrapper.setSpielName(MessageSpielValue.LINK_ERROR_PRIMARY_LINK_LIMIT_REACHED);

                userMessagingService.transmitNotification(messageWrapper);

                //log the activity
                customerActivityService.logActivity(loyaltyId,CustomerActivityType.ACCOUNT_LINKING,"Failed , primary has reached maximum linked accounts",linkRequest.getLrqMerchantNo(),"");

                // Return false
                return false;

            }

        }



        // Return true
        return true;

    }


    // Linking tables update
    protected boolean updateLinkingTables(Customer primary , Customer secondary , Integer linkRequestType ) {

        // If the linkRequest type is LINK, then we need to check if primary is in PrimaryLoyaltyTable
        if ( linkRequestType == LinkRequestType.LINK ) {

            // Add the primary to PrimaryLoyaltyTable
            primaryLoyaltyService.addCustomerAsPrimary(primary);

            // Add the secondary to the LinkedLoyalty
            LinkedLoyalty linkedLoyalty = linkedLoyaltyService.linkCustomers(primary,secondary);

            // Check if the data has been added successfully
            if ( linkedLoyalty == null ) {

                // Log the information
                log.info("updateLinkingTables -> Unable to create entry for LinkedLoyalty" );

                // return false
                return false;

            }

        } else if ( linkRequestType == LinkRequestType.UNLINK ) {

            // Get the LinkedLoyalty
            LinkedLoyalty linkedLoyalty = linkedLoyaltyService.findByLilChildCustomerNo(secondary.getCusCustomerNo());

            // Check if the entry exists
            if ( linkedLoyalty == null ) {

                // Log the information
                log.info("updateLinkingTables -> Unable to find entry for LinkedLoyalty" );

                // return false
                return false;

            }


            // Delete the entry
            linkedLoyaltyService.deleteLinkedLoyalty(linkedLoyalty.getLilId());


        }


        // Return
        return true;

    }

    protected boolean updateBalanceTables(Customer primary, Customer secondary, Integer linkType,AccountBundlingSetting accountBundlingSetting ) {

        // Update AccumulatedRewardBalance
        boolean isUpdateAccumulatedRewardBalance = updateAccumulatedRewardBalance(primary,secondary,linkType,accountBundlingSetting);

        // Check if the isUpdateAccumulatedRewardBalance is successful
        if ( !isUpdateAccumulatedRewardBalance  ) {

            // Log the informatoin
            log.info("updateBalanceTables -> Update of accumulated reward balance failed");

            // return false
            return false;

        }



        boolean isUpdateLinkedRewardBalance = updateLinkedRewardBalance(primary,secondary,linkType,accountBundlingSetting);

        // Check if the  isUpdateLinkedRewardBalance is successful
        if ( !isUpdateLinkedRewardBalance ) {

            // Log the informatoin
            log.info("updateBalanceTables -> Update of linked reward balance failed");

            // return false
            return false;

        }



        // If the linkType is LINK and the accountBundlingsettingLinkingTYpe is TRANSFER_TO , then
        // call the moveCustomerRewardExpiryToPrimary function
        if ( linkType == LinkRequestType.LINK &&
             accountBundlingSetting.getAbsLinkingType() == AccountBundlingLinkingType.TRANSFER_TO_PRIMARY ) {

            // Call the function to move the reward expiry to primary
            boolean isMoved = accountBundlingUtils.moveCustomerRewardExpiryToPrimary(primary,secondary);

            // Check if the data has been moved
            if ( !isMoved ) {

                // Log the information
                log.info("updateBalanceTables -> moving of customer reward expiry failed");

                // return false
                return false;

            }




            // Call the function to move the reward balance to primary
            isMoved = accountBundlingUtils.moveCustomerRewardBalanceToPrimary(primary,secondary);

            // Check if the data has been moved
            if ( !isMoved ) {

                // Log the information
                log.info("updateBalanceTables -> moving of customer reward balance failed");

                // return false
                return false;

            }

        }

        // finally return true
        return true;

    }

    protected boolean updateAccumulatedRewardBalance(Customer primary, Customer secondary, Integer linkType,AccountBundlingSetting accountBundlingSetting) {


        // Get the AccumulateRewardBalance for Secondary
        List<AccumulatedRewardBalance> secondaryBalance = accumulatedRewardBalanceService.findByArbMerchantNoAndArbLoyaltyId(secondary.getCusMerchantNo(),secondary.getCusLoyaltyId());

        // If secondary does not have balance, then return true as we don't need to update anything
        if ( secondaryBalance == null ) {

            // Log the information
            log.info("updateAccumulatedRewardBalance -> Secondary does not have balance");

            // Return true
            return true;

        }



        // Get the AccumulatedRewardBalance for Primary
        List<AccumulatedRewardBalance> primaryBalance = accumulatedRewardBalanceService.findByArbMerchantNoAndArbLoyaltyId(primary.getCusMerchantNo(),primary.getCusLoyaltyId());

        // Check if primary has balance
        if ( primaryBalance == null ) {

           // Assign the balance to a new Arraylist
           primaryBalance = new ArrayList<>(0);

        }



        // Go through the SecondaryBalance list and check
        for (AccumulatedRewardBalance secAccumulatedRewardBalance :secondaryBalance ) {

            // Flag showing if the rewardbalance exists for the primary
            boolean isBalanceExists  = false;

            // Go through the list of Primary reward balances and check if it exists
            for(AccumulatedRewardBalance priAccumulatedRewardBalance : primaryBalance ) {

                // Check if both the reward currencies are same
                if ( secAccumulatedRewardBalance.getArbRewardCurrency().longValue() == priAccumulatedRewardBalance.getArbRewardCurrency().longValue() ) {

                    // Set the flag to true
                    isBalanceExists = true;

                    // Update the balance
                    // If linkType is LINK, then add the balance
                    //
                    // Else reduce the balance
                    if ( linkType == LinkRequestType.LINK ) {

                        priAccumulatedRewardBalance.setArbRewardBalance(priAccumulatedRewardBalance.getArbRewardBalance() + secAccumulatedRewardBalance.getArbRewardBalance());

                    } else {

                        priAccumulatedRewardBalance.setArbRewardBalance(priAccumulatedRewardBalance.getArbRewardBalance() - secAccumulatedRewardBalance.getArbRewardBalance());

                    }

                    // break the loop
                    break;

                }

            }


            // If the balance does not exists,
            // then we need to create one in case of linking
            if ( !isBalanceExists && linkType == LinkRequestType.LINK ) {

                // Create the balance object
                AccumulatedRewardBalance accumulatedRewardBalance = accountBundlingUtils.createAccumulatedRewardBalance(primary.getCusMerchantNo(),primary.getCusLoyaltyId(),secAccumulatedRewardBalance.getArbRewardCurrency(),secAccumulatedRewardBalance.getArbRewardBalance());

                // Add teh item to the list
                primaryBalance.add(accumulatedRewardBalance);

            }

        }


        // Save the primaryRewardBalance list
        accumulatedRewardBalanceService.saveAll(primaryBalance);

        // Return false
        return true;

    }

    protected boolean updateLinkedRewardBalance(Customer primary, Customer secondary, Integer linkType,AccountBundlingSetting accountBundlingSetting) {


        // Get the AccumulateRewardBalance for Secondary
        List<CustomerRewardBalance> secondaryBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(secondary.getCusLoyaltyId(), secondary.getCusMerchantNo());

        // If secondary does not have balance, then return true as we don't need to update anything
        if ( secondaryBalance == null ) {

            // Log the information
            log.info("updateLinkedRewardBalance -> Secondary does not have balance");

            // Return true
            return true;

        }



        // Get the LinkedRewardBalance for Primary
        List<LinkedRewardBalance> primaryBalance = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(primary.getCusLoyaltyId(), primary.getCusMerchantNo());

        // Check if primary has balance
        if ( primaryBalance == null ) {

            // Assign the balance to a new Arraylist
            primaryBalance = new ArrayList<>(0);

        }


        // Variable holding the preBalance
        double preBalance = 0.0;


        // Go through the SecondaryBalance list and check
        for (CustomerRewardBalance secCustomerRewardBalance :secondaryBalance ) {

            // Flag showing if the rewardbalance exists for the primary
            boolean isBalanceExists  = false;

            // Go through the list of Primary reward balances and check if it exists
            for(LinkedRewardBalance priLinkedRewardBalance : primaryBalance ) {

                // Set the preBalance
                preBalance = priLinkedRewardBalance.getLrbRewardBalance();


                // Check if both the reward currencies are same
                if ( secCustomerRewardBalance.getCrbRewardCurrency().longValue() == priLinkedRewardBalance.getLrbRewardCurrency().longValue() ) {

                    // Set the flag to true
                    isBalanceExists = true;

                    // Update the balance
                    // If linkType is  , then add the balance
                    //
                    // Else reduce the balance
                    if ( linkType == LinkRequestType.LINK ) {

                        priLinkedRewardBalance.setLrbRewardBalance(priLinkedRewardBalance.getLrbRewardBalance() + secCustomerRewardBalance.getCrbRewardBalance());


                    } else if ( linkType == LinkRequestType.UNLINK && accountBundlingSetting.getAbsLinkingType() == AccountBundlingLinkingType.CONSOLIDATE_TO_PRIMARY) {

                        priLinkedRewardBalance.setLrbRewardBalance(priLinkedRewardBalance.getLrbRewardBalance() - secCustomerRewardBalance.getCrbRewardBalance());

                    }

                    // break the loop
                    break;

                }

            }


            // If the balance does not exists,
            // then we need to create one in case of linking
            if ( !isBalanceExists && linkType == LinkRequestType.LINK ) {

                // Create the balance object
                LinkedRewardBalance linkedRewardBalance = accountBundlingUtils.createLinkedRewardBalance(primary.getCusMerchantNo(), primary.getCusLoyaltyId(), secCustomerRewardBalance.getCrbRewardCurrency(), secCustomerRewardBalance.getCrbRewardBalance());

                // Add teh item to the list
                primaryBalance.add(linkedRewardBalance);

            }



            // Check the linkType and add the transaction
            if ( linkType == LinkRequestType.LINK ) {

                // Add a transaction for crediting the secondary balance to primary
                Transaction creditTxn = accountBundlingUtils.createTransactionForLinkingTransfer(
                        secCustomerRewardBalance.getCrbMerchantNo(),
                        primary.getCusLoyaltyId(),
                        secCustomerRewardBalance.getCrbRewardCurrency(),
                        secCustomerRewardBalance.getCrbRewardBalance(),
                        secCustomerRewardBalance.getCrbLoyaltyId(),
                        secondary.getCusLocation(),
                        preBalance,
                        CreditDebitInd.CREDIT,
                        TransactionType.LINKING_TRANSFER_FROM);

                // Add the transaction
                transactionService.saveTransaction(creditTxn);



                // If the accountBundlingSetting linking type is transfer, then set the balance to 0 for secondary
                if ( accountBundlingSetting.getAbsLinkingType() == AccountBundlingLinkingType.TRANSFER_TO_PRIMARY  ) {


                    // Add a transaction for the debit of point from the secondary to primary
                    Transaction debitTxn = accountBundlingUtils.createTransactionForLinkingTransfer(
                            secCustomerRewardBalance.getCrbMerchantNo(),
                            secCustomerRewardBalance.getCrbLoyaltyId(),
                            secCustomerRewardBalance.getCrbRewardCurrency(),
                            secCustomerRewardBalance.getCrbRewardBalance(),
                            primary.getCusLoyaltyId(),
                            secondary.getCusLocation(),
                            preBalance,
                            CreditDebitInd.DEBIT,
                            TransactionType.LINKING_TRANSFER_TO);

                    // save the transaction
                    transactionService.saveTransaction(debitTxn);

                }

            } else if ( linkType == LinkRequestType.UNLINK && accountBundlingSetting.getAbsLinkingType() == AccountBundlingLinkingType.CONSOLIDATE_TO_PRIMARY ) {


                // Add a transaction for the debit from the linked reward balance for primary
                Transaction debitTxn = accountBundlingUtils.createTransactionForLinkingTransfer(
                        primary.getCusMerchantNo(),
                        primary.getCusLoyaltyId(),
                        secCustomerRewardBalance.getCrbRewardCurrency(),
                        secCustomerRewardBalance.getCrbRewardBalance(),
                        secondary.getCusLoyaltyId(),
                        secondary.getCusLocation(),
                        preBalance,
                        CreditDebitInd.DEBIT,
                        TransactionType.LINKING_TRANSFER_TO);


                // save the transaction
                transactionService.saveTransaction(debitTxn);

            }

        }


        // Save the primaryRewardBalance list
        linkedRewardBalanceService.saveAll(primaryBalance);

        // Return false
        return true;

    }


    // Request approval methods ( common for linking and delinking)
    protected boolean isRequestConfirmed(LinkRequest linkRequest, Customer primary, Customer secondary, AccountBundlingSetting accountBundlingSetting) {


        //check if the request is initiated after a customer deactivation request
        if(linkRequest.isUnregisterRequest()){

            //return true
            return true;

        }
        // Check the different conditions based on the confirmation type in the settings
        switch( accountBundlingSetting.getAbsBundlingConfirmationType().intValue()) {

            // If type is notification only, then return true always
            case AccountBundlingSettingConfirmationType.NOTIFICATION_ONLY:

                return true;

            // If the confirmation is required only if initiated by secondary, then
            // check if the secondary has given approval
            case AccountBundlingSettingConfirmationType.CONFIRMATION_IF_INITIATED_BY_SECONDARY:

                // Check the intiator
                if ( linkRequest.getLrqInitiator() == LinkRequestInitiator.CHILD ) {

                    // Check if the request is confirmed by the primary
                    boolean isConfirmedByPrimary = isPartyApproved(linkRequest,secondary,primary);

                    // Return the return value
                    return isConfirmedByPrimary;


                } else {

                    return true;
                }

            // Check if the setting is give confirmation when initiated by primary,
            // Check if primary has given approval
            case AccountBundlingSettingConfirmationType.CONFIRMATION_IF_INITIATED_BY_PRIMARY:

                // Check if the initiator is primary
                if ( linkRequest.getLrqInitiator() == LinkRequestInitiator.PRIMARY ) {

                    // Check if the request is confirmed by the secondary
                    boolean isConfirmedBySecondary = isPartyApproved(linkRequest,primary,secondary);

                    // Return the flag vlaue
                    return isConfirmedBySecondary;

                } else {

                    return true;

                }


            // If the confirmation type is from secondary, then check if the
            // secondary has approved
            case AccountBundlingSettingConfirmationType.CONFIRMATION_FROM_OPPOSITE_PARTY:

                // Check if the initiator is primary
                if ( linkRequest.getLrqInitiator() == LinkRequestInitiator.PRIMARY ) {

                    // Check if the request is confirmed by the secondary
                    boolean isConfirmedBySecondary = isPartyApproved(linkRequest,primary,secondary);

                    // Return the flag vlaue
                    return isConfirmedBySecondary;

                } else {

                    // Check if the request is confirmed by the secondary
                    boolean isConfirmedByPrimary = isPartyApproved(linkRequest,secondary,primary);

                    // Return the flag vlaue
                    return isConfirmedByPrimary;

                }

        }


        // Return false
        return false;

    }

    protected boolean isPartyApproved(LinkRequest linkRequest ,Customer requestor,Customer approver) {

        //get the party approval type
        int partyApprovalType = linkRequest.getLrqType().intValue() == LinkRequestType.LINK ?PartyApprovalType.PARTY_APPROVAL_LINK_REQUEST:PartyApprovalType.PARTY_APPROVAL_UNLINK_REQUEST;

        // Check if there is a entry in the LinkingApproval table
        PartyApproval partyApproval = partyApprovalService.getPartyApprovalForLinkRequest(approver.getCusCustomerNo(), requestor.getCusCustomerNo(), partyApprovalType);

        // If the partyApproval is not found, then return false
        if ( partyApproval == null || partyApproval.getPapStatus() != PartyApprovalStatus.ACCEPTED ) {

            // Log the information
            log.info("isPartyApproved -> Party has not approved linking");

            //if a request already exists in a status other than accepted , use this request to send an approval
            if(partyApproval != null && partyApproval.getPapStatus() == PartyApprovalStatus.PROCESSED){

                partyApproval.setPapStatus(PartyApprovalStatus.QUEUED);

                partyApproval.setPapRequest(linkRequest.getLrqId());

                // Get the current timestamp
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                partyApproval.setPapSentDateTime(new Timestamp(timestamp.getTime()));

                partyApprovalService.savePartyApproval(partyApproval);

            }

            //create a map for the sms placeholders
            HashMap<String , String > smsParams  = new HashMap<>(0);

         /*   smsParams.put("#approverId",approver.getCusLoyaltyId());

            userMessagingService.sendSMS(MessageSpielValue.PARTY_APPROVAL_REQUEST_REJECTED,requestor.getCusLoyaltyId(),smsParams);
*/
            // return false
            return false;

        } else {

            partyApproval.setPapStatus(PartyApprovalStatus.PROCESSED);

            partyApprovalService.savePartyApproval(partyApproval);
        }

        // Return true
        return true;

    }

    protected Integer processApprovalForParty(LinkRequest linkRequest, Customer primary, Customer secondary, AccountBundlingSetting accountBundlingSetting) throws InspireNetzException {

        // The approver
        Customer approver ;
        // The requester
        Customer requestor;


        // Check the different conditions based on the confirmation type in the settings
        switch( accountBundlingSetting.getAbsBundlingConfirmationType().intValue()) {

            // If the confirmation is required only if initiated by secondary, then
            // check if the secondary has given approval
            case AccountBundlingSettingConfirmationType.CONFIRMATION_IF_INITIATED_BY_SECONDARY:

                // Set the approver to primary
                approver = primary;

                // Set the requestor to secondary
                requestor = secondary;

                // break
                break;

            // Check if the setting is give confirmation when initiated by primary,
            // Check if the confirmation is from secondary
            case AccountBundlingSettingConfirmationType.CONFIRMATION_IF_INITIATED_BY_PRIMARY:

                // Set the secondary as the approver
                approver = secondary;

                // Set the requestor as primary
                requestor = primary;

                // Break
                break;

            case AccountBundlingSettingConfirmationType.CONFIRMATION_FROM_OPPOSITE_PARTY:

                if ( linkRequest.getLrqInitiator() == LinkRequestInitiator.PRIMARY ) {

                    // Set the secondary as the approver
                    approver = secondary;

                    // Set the requestor as primary
                    requestor = primary;

                } else {

                    // Set the approver to primary
                    approver = primary;

                    // Set the requestor to secondary
                    requestor = secondary;

                }


                // Break
                break;

            default :

                return PartyApprovalStatus.FAILED;

        }

        //this variable holds the party approval type
        int papType =0;

        //if the party approval type is for linking set papType as LinkRequest , else as Unlink Request
        if(linkRequest.getLrqType() == LinkRequestType.LINK){

            papType = PartyApprovalType.PARTY_APPROVAL_LINK_REQUEST;

        } else{

            papType = PartyApprovalType.PARTY_APPROVAL_UNLINK_REQUEST;

        }

        //set the link request status to failed and save the request
        linkRequest.setLrqStatus(LinkRequestStatus.FAILED);

        // Call the partyApprovalService for sending the linking approval request
        Integer status = partyApprovalService.sendApproval(requestor, approver, linkRequest.getLrqId(), papType, "", "");

        // Return the status
        return status;
    }


}

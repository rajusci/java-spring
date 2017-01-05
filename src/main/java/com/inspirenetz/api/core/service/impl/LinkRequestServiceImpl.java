package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.domain.validator.LinkRequestValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.LinkRequestRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AccountBundlingUtils;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class LinkRequestServiceImpl extends BaseServiceImpl<LinkRequest> implements LinkRequestService {


    private static Logger log = LoggerFactory.getLogger(LinkRequestServiceImpl.class);


    @Autowired
    LinkRequestRepository linkRequestRepository;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private AccountLinkingService accountLinkingService;

    @Autowired
    UserMessagingService userMessagingService;

    @Autowired
    PrimaryLoyaltyService primaryLoyaltyService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    PartyApprovalService partyApprovalService;

    @Autowired
    LinkedRewardBalanceService linkedRewardBalanceService;

    @Autowired
    AccountBundlingUtils accountBundlingUtils;

    @Autowired
    GeneralUtils generalUtils;

    public LinkRequestServiceImpl() {

        super(LinkRequest.class);

    }


    @Override
    protected BaseRepository<LinkRequest,Long> getDao() {
        return linkRequestRepository;
    }



    @Override
    public LinkRequest findByLrqId(Long lrqId) {

        // Get the linkRequest for the given linkRequest id from the repository
        LinkRequest linkRequest = linkRequestRepository.findByLrqId(lrqId);

        // Return the linkRequest
        return linkRequest;

    }

    @Override
    public Page<LinkRequest> findByLrqSourceCustomerLrqMerchantNo(Long lrqSourceCustomer, Long lrqMerchantNo,Pageable pageable) {

        return linkRequestRepository.findByLrqSourceCustomerAndLrqMerchantNo(lrqSourceCustomer,lrqMerchantNo,pageable);

    }

    @Override
    public Page<LinkRequest> searchLinkRequests(String filter,String query,Long merchantNo,Pageable pageable) throws InspireNetzException {

        // Get the linkRequest
        Page<LinkRequest>  linkRequestPage = null;

        //if filter and query is 0 , then return all items
        if(filter.equals("0") && query.equals("0")){

            linkRequestPage = linkRequestRepository.findByLrqMerchantNo(merchantNo,pageable);

        } else if(filter.equals("loyaltyid")){

            Customer customer= customerService.findByCusLoyaltyIdAndCusMerchantNo(query, merchantNo);

            //check if the customer exists
            if(customer == null){

                //log the error
                log.error("No customer information found");

                throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);

            } else {

                //call the link request expiration method for party approval
                partyApprovalService.expiringLinkRequest(customer.getCusLoyaltyId(),merchantNo,ApprovalExpiryFilterType.REQUESTOR);

                //get the link request for the customer
                linkRequestPage = findByLrqSourceCustomerLrqMerchantNo(customer.getCusCustomerNo(),merchantNo,pageable);

            }

            return linkRequestPage;
        }


        // Return the page
        return linkRequestPage;

    }

    @Override
    public LinkRequest getLinkRequestInfo(Long lrqId) throws InspireNetzException {


        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the linkRequest information
        LinkRequest linkRequest = findByLrqId(lrqId);

        // Check if the linkRequest is found
        if ( linkRequest == null || linkRequest.getLrqId() == null) {

            // Log the response
            log.info("getLinkRequestInfo - Response : No linkRequest information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the merchant is valid for deletin
        if ( linkRequest.getLrqMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteLinkRequest - Response : No linkRequest information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // return the object
        return linkRequest;


    }


    @Override
    public Long saveUnlinkingRequest(String primaryLoyaltyId, String childLoyaltyId,String lrqInitiator,Integer lrqSource, Long merchantNo,boolean isUnregisterRequest) throws InspireNetzException {

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",lrqInitiator,lrqInitiator,"","",generalUtils.getDefaultMerchantNo(),new HashMap<String, String>(),MessageSpielChannel.ALL,IndicatorStatus.YES);

        //getting the initiator customer data
        Customer initiator=customerService.findByCusLoyaltyIdAndCusMerchantNo(lrqInitiator,merchantNo);

        //getting the primary customer data
        Customer primary = customerService.findByCusLoyaltyIdAndCusMerchantNo(primaryLoyaltyId,merchantNo);

        //getting the child customer data
        Customer child = customerService.findByCusLoyaltyIdAndCusMerchantNo(childLoyaltyId,merchantNo);

        //if child or primary is null then throw exception
        if( child == null || primary == null) {

            // Log the information
            log.info("saveUnlinkingRequest : Child or primary is null : chlid - " + child + " : primary - " + primary);

            if(primary == null ){

                messageWrapper.setSpielName(MessageSpielValue.UNLINK_ERROR_PRIMARY_NOT_ACTIVE);

            } else {

                messageWrapper.setSpielName(MessageSpielValue.UNLINK_ERROR_CHILD_NOT_ACTIVE);

            }

            if(initiator==null||initiator.getCusCustomerNo()==null){

                messageWrapper.setMerchantNo(merchantNo);

                messageWrapper.setMobile(lrqInitiator);

                messageWrapper.setIsCustomer(IndicatorStatus.NO);

                messageWrapper.setChannel(MessageSpielChannel.SMS);

            }else{

                messageWrapper.setMerchantNo(initiator.getCusMerchantNo());

                messageWrapper.setLoyaltyId(initiator.getCusLoyaltyId());

                messageWrapper.setMobile(initiator.getCusMobile());

                messageWrapper.setEmailId(initiator.getCusEmail());

                messageWrapper.setIsCustomer(IndicatorStatus.YES);

                messageWrapper.setChannel(MessageSpielChannel.ALL);
            }

            userMessagingService.transmitNotification(messageWrapper);

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the initiator is matching to one of the parties
        if ( !lrqInitiator.equals(primaryLoyaltyId) && !lrqInitiator.equals(childLoyaltyId) ) {

            // Log the information
            log.info("saveUnlinkingRequest -> Initiator is neither primary nor child ");

            //log the activity
            customerActivityService.logActivity(lrqInitiator, CustomerActivityType.ACCOUNT_LINKING, "Failed , Initiator is neither primary nor child", merchantNo, "");

            messageWrapper.setSpielName(MessageSpielValue.UNLINK_REQUEST_INVALID_INITIATOR);

            if(initiator==null||initiator.getCusCustomerNo()==null){

                messageWrapper.setMerchantNo(generalUtils.getDefaultMerchantNo());

                messageWrapper.setMobile(lrqInitiator);

                messageWrapper.setIsCustomer(IndicatorStatus.NO);

                messageWrapper.setChannel(MessageSpielChannel.SMS);

            }else{

                messageWrapper.setMerchantNo(initiator.getCusMerchantNo());

                messageWrapper.setLoyaltyId(initiator.getCusLoyaltyId());

                messageWrapper.setMobile(initiator.getCusMobile());

                messageWrapper.setEmailId(initiator.getCusEmail());

                messageWrapper.setIsCustomer(IndicatorStatus.YES);

                messageWrapper.setChannel(MessageSpielChannel.ALL);
            }

            userMessagingService.transmitNotification(messageWrapper);

            // Throw operation not allowed exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }


        //retrieving the linked loyalty for the two customers
        LinkedLoyalty linkedLoyalty = linkedLoyaltyService.findByLilChildCustomerNo(child.getCusCustomerNo());

        //if no linked loyalty is found then throw exception
        if(linkedLoyalty == null){

            // log information
            log.info("saveUnlinkingRequest -> No linked loyalty found");

            //log the activity
            customerActivityService.logActivity(lrqInitiator, CustomerActivityType.ACCOUNT_LINKING, "Failed , No Linked Loyalty Found", merchantNo, "");

            messageWrapper.setSpielName(MessageSpielValue.UNLINK_REQUEST_NO_LINKING_FOUND);

            if(initiator==null||initiator.getCusCustomerNo()==null){

                messageWrapper.setMerchantNo(merchantNo);

                messageWrapper.setMobile(lrqInitiator);

                messageWrapper.setIsCustomer(IndicatorStatus.NO);

                messageWrapper.setChannel(MessageSpielChannel.SMS);

            }else{

                messageWrapper.setMerchantNo(initiator.getCusMerchantNo());

                messageWrapper.setLoyaltyId(initiator.getCusLoyaltyId());

                messageWrapper.setMobile(initiator.getCusMobile());

                messageWrapper.setEmailId(initiator.getCusEmail());

                messageWrapper.setIsCustomer(IndicatorStatus.YES);

                messageWrapper.setChannel(MessageSpielChannel.ALL);
            }

            userMessagingService.transmitNotification(messageWrapper);

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        } else {

            // Check if the linkedLoyaltyParent is the given primary ,
            // otherwise thow operation not allowed error
            if(linkedLoyalty.getLilParentCustomerNo() .equals(primary.getCusCustomerNo() ) ) {

                // Create the object
                LinkRequest linkRequest = new LinkRequest();


                // check if the initiator and set the value
                if(lrqInitiator.equals(primaryLoyaltyId)) {

                    linkRequest.setLrqInitiator(LinkRequestInitiator.PRIMARY);

                } else {

                    linkRequest.setLrqInitiator(LinkRequestInitiator.CHILD);

                }

                // Set the fields
                linkRequest.setLrqMerchantNo(merchantNo);
                linkRequest.setLrqParentCustomer(primary.getCusCustomerNo());
                linkRequest.setLrqSourceCustomer(child.getCusCustomerNo());
                linkRequest.setLrqRequestSource(lrqSource);
                linkRequest.setLrqType(LinkRequestType.UNLINK);
                linkRequest.setLrqStatus(LinkRequestStatus.PENDING);
                linkRequest.setLrqRequestDate(new Date(System.currentTimeMillis()));
                linkRequest.setLrqRequestSourceRef(lrqInitiator);

                // Save the request
                linkRequest = saveLinkRequest(linkRequest);

                // Check if the request is saved
                if ( linkRequest.getLrqId() == null ) {

                    // Log the information
                    log.info("saveUnlinkingRequest -> Unlinking request save unsuccessful");

                    //log the activity
                    customerActivityService.logActivity(lrqInitiator,CustomerActivityType.ACCOUNT_LINKING,"Failed , Unlink request saving failed",merchantNo,"");

                    // throw exception
                    throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

                }



                // Update the LinkedLoyalty entry as UNLINK_REQUESTED
                linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.UNLINK_REQUESTED);

                // Save the linkedLoyalty
                linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);


                //if unlink request is for account deactivation set link request's isUnregisterRequest to true
                if(isUnregisterRequest){

                    linkRequest.setUnregisterRequest(true);

                }
                // Call the process link request
                // this will check for the validity and then process it.
                accountLinkingService.processLinkRequest(linkRequest,lrqInitiator );

                //check  primary loyatly's linked accounts , if no accounts found remove primary from
                //primary loyatly
                checkAccountLinkingStatus(primary);

               /* //SEND SMS to the user
                //create a map for the sms placeholders
                HashMap<String , String > smsParams  = new HashMap<>(0);
                smsParams.put("#unlinkAccount",primaryLoyaltyId);

                userMessagingService.sendSMS(MessageSpielValue.LINK_REQUEST_UNLINK_ACCOUNT,childLoyaltyId,smsParams);
*/
                //get the link request
                linkRequest = findByLrqId(linkRequest.getLrqId());

                // return the status
                return linkRequest.getLrqStatus().longValue();

            } else {

                // log information
                log.info("saveUnlinkingRequest -> primary is not valid");

                //log the activity
                customerActivityService.logActivity(lrqInitiator,CustomerActivityType.ACCOUNT_LINKING,"Failed , Primary is not valid",merchantNo,"");

                messageWrapper.setSpielName(MessageSpielValue.UNLINK_REQUEST_PRIMARY_NOT_VALID);

                if(initiator==null||initiator.getCusCustomerNo()==null){

                    messageWrapper.setMerchantNo(merchantNo);

                    messageWrapper.setMobile(lrqInitiator);

                    messageWrapper.setIsCustomer(IndicatorStatus.NO);

                    messageWrapper.setChannel(MessageSpielChannel.SMS);

                }else{

                    messageWrapper.setMerchantNo(initiator.getCusMerchantNo());

                    messageWrapper.setLoyaltyId(initiator.getCusLoyaltyId());

                    messageWrapper.setMobile(initiator.getCusMobile());

                    messageWrapper.setEmailId(initiator.getCusEmail());

                    messageWrapper.setIsCustomer(IndicatorStatus.YES);

                    messageWrapper.setChannel(MessageSpielChannel.ALL);
                }

                userMessagingService.transmitNotification(messageWrapper);

                // Throw exception
                throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

            }

        }

    }

    @Override
    public void checkAccountLinkingStatus(Customer primary) {

        //check whether the user is currently connected to any accounts
        List<LinkedLoyalty> linkedLoyalties = linkedLoyaltyService.findByLilParentCustomerNo(primary.getCusCustomerNo());

        //if linked loyalties is null , the primary customer is now not parent to any
        //account, if so remove entry from primary loyatly
        if(linkedLoyalties == null || linkedLoyalties.size() == 0){

            //remove the entry from primary loyalty if exists
            PrimaryLoyalty primaryLoyalty = primaryLoyaltyService.findByPllLoyaltyId(primary.getCusLoyaltyId());

            if(primaryLoyalty != null){

                //remove the entry from primary loyalty
                primaryLoyaltyService.deletePrimaryLoyalty(primaryLoyalty.getPllId());

                //delete all linked reward balances of the customer
                List<LinkedRewardBalance> linkedRewardBalances = linkedRewardBalanceService.findByLrbPrimaryLoyaltyId(primary.getCusLoyaltyId());

                //delete all records
                for(LinkedRewardBalance linkedRewardBalance : linkedRewardBalances){

                    //delete the entry
                    linkedRewardBalanceService.deleteLinkedRewardBalance(linkedRewardBalance.getLrbId());
                }

            }

        }
    }


    /**
     * Function to create a link request
     *
     * @param merchantNo        - The merchant number of the merchant
     * @param primaryLoyaltyId  - the primary loyalty id
     * @param childLoyaltyId    - The child loyalty id
     * @param lrqInitiator      - The initiator loyalty id
     * @param lrqSource         - The source of request
     *
     * @return                  - Return the LinkRequest object
     *
     * @throws InspireNetzException
     */
    public LinkRequest createLinkRequest(Long merchantNo, String primaryLoyaltyId, String childLoyaltyId, String lrqInitiator, Integer lrqSource) throws InspireNetzException {

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",lrqInitiator,lrqInitiator,"","",0L,new HashMap<String, String>(0),MessageSpielChannel.ALL,IndicatorStatus.NO);

        //getting the initiator
        Customer initiator=customerService.findByCusLoyaltyIdAndCusMerchantNo(lrqInitiator,merchantNo);

        //getting the primary customer data
        Customer primary = customerService.findByCusLoyaltyIdAndCusMerchantNo(primaryLoyaltyId,merchantNo);

        //getting the child customer data
        Customer child = customerService.findByCusLoyaltyIdAndCusMerchantNo(childLoyaltyId,merchantNo);

        // If primary of child is null, then we need to show the message
        if ( primary == null || child == null ) {

            // Log the information
            log.info("Primary is : "+ primary + " : Secondary is  :" + child);

            if(primary == null ){


                messageWrapper.setSpielName(MessageSpielValue.LINK_ERROR_PRIMARY_NOT_ACTIVE);



            } else {

                messageWrapper.setSpielName(MessageSpielValue.LINK_ERROR_CHILD_NOT_ACTIVE);

            }

            if(initiator==null||initiator.getCusCustomerNo()==null){

                messageWrapper.setMerchantNo(merchantNo);

                messageWrapper.setMobile(lrqInitiator);

                messageWrapper.setIsCustomer(IndicatorStatus.NO);

                messageWrapper.setChannel(MessageSpielChannel.SMS);

            }else{

                messageWrapper.setMerchantNo(initiator.getCusMerchantNo());

                messageWrapper.setLoyaltyId(initiator.getCusLoyaltyId());

                messageWrapper.setMobile(initiator.getCusMobile());

                messageWrapper.setEmailId(initiator.getCusEmail());

                messageWrapper.setIsCustomer(IndicatorStatus.YES);

                messageWrapper.setChannel(MessageSpielChannel.ALL);
            }

            // Send the user messaging service
            userMessagingService.transmitNotification(messageWrapper);

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }


        // Create the object
        LinkRequest linkRequest = new LinkRequest();


        // check if the initiator and set the value
        if(lrqInitiator.equals(primaryLoyaltyId)) {

            linkRequest.setLrqInitiator(LinkRequestInitiator.PRIMARY);

        } else {

            linkRequest.setLrqInitiator(LinkRequestInitiator.CHILD);

        }

        // Set the fields
        linkRequest.setLrqMerchantNo(merchantNo);
        linkRequest.setLrqParentCustomer(primary.getCusCustomerNo());
        linkRequest.setLrqSourceCustomer(child.getCusCustomerNo());
        linkRequest.setLrqRequestSource(lrqSource);
        linkRequest.setLrqType(LinkRequestType.LINK);
        linkRequest.setLrqStatus(LinkRequestStatus.PENDING);
        linkRequest.setLrqRequestDate(new Date(System.currentTimeMillis()));
        linkRequest.setLrqRequestSourceRef(lrqInitiator);
        //check if a link request exists with pending status for the customers,
        if(isPendingLinkRequestExists(linkRequest)){

            //log error
            log.error("createLinkRequest : Link Request already exists for the accounts");


            messageWrapper.setSpielName(MessageSpielValue.LINK_REQUEST_ALREADY_EXISTS);

            if(initiator==null||initiator.getCusCustomerNo()==null){

                messageWrapper.setMerchantNo(merchantNo);

                messageWrapper.setMobile(lrqInitiator);

                messageWrapper.setIsCustomer(IndicatorStatus.NO);

                messageWrapper.setChannel(MessageSpielChannel.SMS);

            }else{

                messageWrapper.setMerchantNo(initiator.getCusMerchantNo());

                messageWrapper.setLoyaltyId(initiator.getCusLoyaltyId());

                messageWrapper.setMobile(initiator.getCusMobile());

                messageWrapper.setEmailId(initiator.getCusEmail());

                messageWrapper.setIsCustomer(IndicatorStatus.YES);

                messageWrapper.setChannel(MessageSpielChannel.ALL);
            }

            userMessagingService.transmitNotification(messageWrapper);

            //throw an exception
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_LINK_REQUEST);

        }
        // Save the request
        linkRequest = saveLinkRequest(linkRequest);

        // Check if the request is saved
        if ( linkRequest.getLrqId() == null ) {

            // Log the information
            log.info("saveUnlinkingRequest -> Unlinking request save unsuccessful");

            // throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        messageWrapper.setSpielName(MessageSpielValue.LINK_REQUEST_LINK_ACCOUNT);

        if(initiator==null||initiator.getCusCustomerNo()==null){

            messageWrapper.setMerchantNo(merchantNo);

            messageWrapper.setMobile(lrqInitiator);

            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            messageWrapper.setChannel(MessageSpielChannel.SMS);

        }else{

            messageWrapper.setMerchantNo(initiator.getCusMerchantNo());

            messageWrapper.setLoyaltyId(initiator.getCusLoyaltyId());

            messageWrapper.setMobile(initiator.getCusMobile());

            messageWrapper.setEmailId(initiator.getCusEmail());

            messageWrapper.setIsCustomer(IndicatorStatus.YES);

            messageWrapper.setChannel(MessageSpielChannel.ALL);
        }

        // Send the user messaging service
        userMessagingService.transmitNotification(messageWrapper);

        // Call the process link request
        // this will check for the validity and then process it.
        accountLinkingService.processLinkRequest(linkRequest, lrqInitiator);

        // Return the link request
        return linkRequest;

    }

    @Override
    public LinkRequest validateAndSaveLinkRequest(LinkRequest linkRequest ) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_LINK_REQUESTS);

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo
        linkRequest.setLrqMerchantNo(merchantNo);

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Create the Validator
        LinkRequestValidator validator = new LinkRequestValidator();

        // Create the bindingREsult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(linkRequest,"linkRequest");

        // Validate the request
        validator.validate(linkRequest,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveLinkRequest - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }




        // If the linkRequest.getLrqId is  null, then set the created_by, else set the updated_by
        if ( linkRequest.getLrqId() == null ) {

            linkRequest.setCreatedBy(auditDetails);

        } else {

            linkRequest.setUpdatedBy(auditDetails);

        }

        //check if a link request exists with pending status for the customers,
        if(isPendingLinkRequestExists(linkRequest)){

            //log error
            log.error("validateAndSaveLinkRequest : Link Request already exists for the accounts");

            //throw an exception
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_LINK_REQUEST);

        }


        // Save the object
        linkRequest = saveLinkRequest(linkRequest);

        // Check if the linkRequest is saved
        if ( linkRequest.getLrqId() == null ) {

            // Log the response
            log.info("validateAndSaveLinkRequest - Response : Unable to save the linkRequest information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        Customer customer = null;

        if(linkRequest.getLrqInitiator() ==LinkRequestInitiator.PRIMARY){

            customer = customerService.findByCusCustomerNo(linkRequest.getLrqParentCustomer());
        } else {

            customer =customerService.findByCusCustomerNo(linkRequest.getLrqSourceCustomer());
        }

        // Call the process linking option for the link request
        accountLinkingService.processLinkRequest(linkRequest, customer.getCusLoyaltyId());


        // return the object
        return linkRequest;


    }

    private boolean isPendingLinkRequestExists(LinkRequest linkRequest) {

        //get the link request for the primary and secondary
        Page<LinkRequest> linkRequests = findByLrqSourceCustomerLrqMerchantNo(linkRequest.getLrqSourceCustomer(), linkRequest.getLrqMerchantNo(), new PageRequest(0, 100));

        //iterate through each link request and check whether an identical exists
        for(LinkRequest linkRequest1 : linkRequests){

            //check if a similiar request exists
            if(linkRequest1.getLrqStatus().intValue() == LinkRequestStatus.PENDING &&
                    linkRequest1.getLrqType().intValue() == LinkRequestType.LINK &&
                    linkRequest1.getLrqSourceCustomer().longValue() == linkRequest.getLrqSourceCustomer().longValue() &&
                    linkRequest1.getLrqParentCustomer().longValue() == linkRequest.getLrqParentCustomer()){

                //if exists return true
                return true;
            }

        }

        //return false if no request found
        return false;

    }

    @Override
    public LinkRequest saveLinkRequest(LinkRequest linkRequest ){

        // Save the linkRequest
        return linkRequestRepository.save(linkRequest);

    }

    @Override
    public boolean validateAndDeleteLinkRequest(Long lrqId) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_LINK_REQUESTS);

        // Get the merchant number
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Get the linkRequest information
        LinkRequest linkRequest = findByLrqId(lrqId);

        // Check if the linkRequest is found
        if ( linkRequest == null || linkRequest.getLrqId() == null) {

            // Log the response
            log.info("deleteLinkRequest - Response : No linkRequest information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the merchant is valid for deletin
        if ( linkRequest.getLrqMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteLinkRequest - Response : No linkRequest information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the linkRequest and set the retData fields
        deleteLinkRequest(lrqId);

        // Return true
        return true;

    }

    @Override
    public boolean deleteLinkRequest(Long lrqId) {

        // Delete the linkRequest
        linkRequestRepository.delete(lrqId);

        // return true
        return true;

    }

    @Override
    public boolean unlinkAllRequest(String loyaltyId, Long merchantNo,boolean isUnregisterRequest) throws InspireNetzException {

        MessageWrapper messageWrapper=generalUtils.getMessageWrapperObject("",loyaltyId,"","","",merchantNo,new HashMap<String, String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

        //get the customer details
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId, merchantNo);

        // check if the customer is null
        if ( customer == null || customer.getCusCustomerNo() == null ) {

            // Log the information
            log.error("unlinkAllRequest -> No customer information found");

            messageWrapper.setSpielName(MessageSpielValue.GENERAL_ERROR_MESSAGE);
            messageWrapper.setMerchantNo(merchantNo);
            messageWrapper.setMobile(loyaltyId);
            messageWrapper.setChannel(MessageSpielChannel.SMS);
            messageWrapper.setIsCustomer(IndicatorStatus.NO);

            userMessagingService.transmitNotification(messageWrapper);

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        //get all the linked accounts of the customer
        List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findLinkedAccounts(customer.getCusLoyaltyId(), customer.getCusMerchantNo());

        //check if customer is primary to any account
        if(linkedLoyaltyList == null || linkedLoyaltyList.size() == 0){

            //log error
            log.error("Customer is not primary to any account");

            messageWrapper.setSpielName(MessageSpielValue.UNLINK_REQUEST_CUSTOMER_NOT_PRIMARY);
            messageWrapper.setMerchantNo(merchantNo);
            messageWrapper.setLoyaltyId(customer.getCusLoyaltyId());
            messageWrapper.setChannel(MessageSpielChannel.ALL);
            messageWrapper.setIsCustomer(IndicatorStatus.YES);

            userMessagingService.transmitNotification(messageWrapper);


        }
        Customer childCustomer = null;

        //iterate through all the requests sand add unlink request
        for(LinkedLoyalty linkedLoyalty : linkedLoyaltyList){

            //get child customer details
            childCustomer = customerService.findByCusCustomerNo(linkedLoyalty.getLilChildCustomerNo());

            //add unlink request
            saveUnlinkingRequest(loyaltyId,childCustomer.getCusLoyaltyId(),loyaltyId,LinkRequestSource.SMS,merchantNo,isUnregisterRequest);

        }

        checkAccountLinkingStatus(customer);

        //send notification
        messageWrapper.setSpielName(MessageSpielValue.DELINK_ALL_REQUEST_SMS);
        messageWrapper.setMerchantNo(merchantNo);
        messageWrapper.setLoyaltyId(customer.getCusLoyaltyId());
        messageWrapper.setChannel(MessageSpielChannel.ALL);
        messageWrapper.setIsCustomer(IndicatorStatus.YES);

        userMessagingService.transmitNotification(messageWrapper);


        return true;
    }

    @Override
    public boolean unlinkCustomerAccounts(Customer customer) throws InspireNetzException {

        //get the customers linked accounts
       List<LinkedLoyalty> linkedLoyalties = linkedLoyaltyService.findLinkedAccounts(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

        //if no linking is found , do nothing and return
        if(linkedLoyalties == null){

            return true;
        }

        String childLoyaltyId = customer.getCusLoyaltyId();

        String parentLoyaltyId = customer.getCusLoyaltyId();

        Customer linkedCustomer = null;

        //iterate through all accounts and unlink them
        for(LinkedLoyalty linkedLoyalty :linkedLoyalties){

            //get the primary account's customer no
            Long parentCustomerNo = linkedLoyalty.getLilParentCustomerNo();

            //check if customer is parent
            if(parentCustomerNo.longValue() == customer.getCusCustomerNo().longValue()){

               //get the child account details
                linkedCustomer = customerService.findByCusCustomerNo(linkedLoyalty.getLilChildCustomerNo());

                //get child's loyalty id
                childLoyaltyId = linkedCustomer.getCusLoyaltyId();

            } else {

                //get the primary account details
                linkedCustomer = customerService.findByCusCustomerNo(linkedLoyalty.getLilParentCustomerNo());

                //get the parent loyalty id
                parentLoyaltyId = linkedCustomer.getCusLoyaltyId();
            }

            //post a request for account unlinking
            saveUnlinkingRequest(parentLoyaltyId,childLoyaltyId,customer.getCusLoyaltyId(),LinkRequestSource.ONLINE,customer.getCusMerchantNo(),true);
        }

        return true;
    }


}

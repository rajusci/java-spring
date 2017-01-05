package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerRewardActivity;
import com.inspirenetz.api.core.domain.validator.CustomerRewardActivityValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CustomerRewardActivityRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.CustomerRewardUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.HashMap;


/**
 * Created by saneeshci on 29/9/14.
 */

@Service
public class CustomerRewardActivityServiceImpl extends BaseServiceImpl<CustomerRewardActivity> implements CustomerRewardActivityService,InjectableReward {



    private static Logger log = LoggerFactory.getLogger(CustomerRewardActivityServiceImpl.class);

    @Autowired
    CustomerRewardActivityRepository customerRewardActivityRepository;

    @Autowired
    LoyaltyEngineService loyaltyEngineService;

    @Autowired
    CustomerService customerService ;

    @Autowired
    UserMessagingService userMessagingService;

    @Autowired
    CustomerActivityService customerActivityService;
    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    public CustomerRewardActivityServiceImpl() {

        super(CustomerRewardActivity.class);

    }
    @Override
    protected BaseRepository<CustomerRewardActivity, Long> getDao() {
        return customerRewardActivityRepository;
    }

    @Override
    public CustomerRewardActivity findByCraId(Long craId) {

        // Getting data based by secid
        CustomerRewardActivity customerRewardActivity=customerRewardActivityRepository.findByCraId(craId);
        return customerRewardActivity;
    }

    @Override
    public Page<CustomerRewardActivity> findByCraCustomerNo(Long craCustomerNo,Pageable pageable) {

        // Getting data based by secid
        Page<CustomerRewardActivity> customerRewardActivityPage=customerRewardActivityRepository.findByCraCustomerNo(craCustomerNo, pageable);
        return customerRewardActivityPage;
    }
    @Override
    public Page<CustomerRewardActivity> findByCraCustomerNoAndCraType(Long craCustomerNo,Integer craType,Pageable pageable) {

        // Getting data based by secid
        Page<CustomerRewardActivity> customerRewardActivityPage=customerRewardActivityRepository.findByCraCustomerNoAndCraType(craCustomerNo, craType, pageable);
        return customerRewardActivityPage;
    }

    @Override
    public CustomerRewardActivity saveCustomerRewardActivity(CustomerRewardActivity customerRewardActivity) throws InspireNetzException {

        //saving the role access right
        customerRewardActivity =  customerRewardActivityRepository.save(customerRewardActivity);


        if(customerRewardActivity != null && customerRewardActivity.getCraId() != null){

            //get the customer details
            Customer customer = customerService.findByCusCustomerNo(customerRewardActivity.getCraCustomerNo());

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.EVENT_REGISTRATION_SUCCESS_SMS,customer.getCusLoyaltyId(),customer.getCusMobile(),customer.getCusEmail(),"",customer.getCusMerchantNo(),new HashMap<String,String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

            userMessagingService.transmitNotification(messageWrapper);
        }

        return customerRewardActivity;
    }

    @Override
    public boolean deleteCustomerRewardActivity(CustomerRewardActivity customerRewardActivity) {

        //for deleting the roll access right
        customerRewardActivityRepository.delete(customerRewardActivity);

        return true;
    }

    @Override
    public boolean isDuplicateActivityExisting(Integer craType, Long craCustomerNo, String craActivityRef) {

        //get the data by the unique key
        CustomerRewardActivity customerRewardActivity = customerRewardActivityRepository.findByCraTypeAndCraCustomerNoAndCraActivityRef(craType,craCustomerNo,craActivityRef);

        //if return data is not null , duplicate is existing
        if(customerRewardActivity != null ){

            return true;

        }

       return false;

    }

    @Async
    @Override
    public void startRewardActivityProcessing(CustomerRewardActivity customerRewardActivity) throws InspireNetzException {

        //call the loyaltyEngineService method to start processing
        loyaltyEngineService.doCustomerRewardActivityProcessing(customerRewardActivity);

    }

    /**
     *
      * @param craLoyaltyId
     * @param craType
     * @param craActivityRef
     * @param merchantNo
     * @return CustomerRewardActivity
     * @throws InspireNetzException
     * @purpose:register customer reward activity by loyaltyId
     */
    @Override
    public CustomerRewardActivity saveCustomerRewardActivityByLoyaltyId(String craLoyaltyId, Integer craType, String craActivityRef,Long merchantNo) throws InspireNetzException {


        //find customer object based on loyaltyId
        Customer customer=customerService.findByCusLoyaltyIdAndCusMerchantNo(craLoyaltyId,merchantNo);

        //check customer is null or not
        if(customer ==null){

            log.info("CustomerRewardActivityServiceImpl::saveCustomerRewardActivityByLoyaltyId ErrorNoLoyaltyId"+craLoyaltyId);

            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);
        }

        // save the customerRewardActivity object and get the result
        CustomerRewardActivity customerRewardActivity =validateAndRegisterCustomerRewardActivity(customer.getCusCustomerNo(),craType,craActivityRef);

        //return customer reward activity
        return  customerRewardActivity;
    }


    @Override
    public CustomerRewardActivity validateAndRegisterCustomerRewardActivity(Long craCustomerNo,Integer craType,String craActivityRef) throws InspireNetzException {

        //authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_CUSTOMER_REWARD_ACTIVITY);

        // Hold the audit details
       // String auditDetails = authSessionUtils.getUserNo().toString();

       // Integer userType = authSessionUtils.getUserType();

        CustomerRewardActivity customerRewardActivity = new CustomerRewardActivity();

        customerRewardActivity.setCraType(craType);
        customerRewardActivity.setCraActivityRef(craActivityRef);
        customerRewardActivity.setCraCustomerNo(craCustomerNo);
        customerRewardActivity.setCraStatus(CustomerRewardActivityStatus.NEW);

        // Create the Validator
        CustomerRewardActivityValidator validator = new CustomerRewardActivityValidator();

        // Create the bindingREsult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(customerRewardActivity,"linkRequest");

        // Validate the request
        validator.validate(customerRewardActivity,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveCustomerRewardActivity - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }



        // If the roll access right .getMsiId is  null, then set the created_by, else set the updated_by
//        if ( customerRewardActivity.getCraId() == null ) {
//
//            customerRewardActivity.setCreatedBy(auditDetails);
//
//        } else {
//
//            customerRewardActivity.setUpdatedBy(auditDetails);
//
//        }

        if(isDuplicateActivityExisting(craType,craCustomerNo,craActivityRef)){

            log.error("registerCustomerRewardActivity : Customer already registered for the activity type");

            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }

        // Save the object
        customerRewardActivity = saveCustomerRewardActivity(customerRewardActivity);

        //check if the activity type is event registration, if true log activity
        if(customerRewardActivity.getCraType() == CustomerRewardingType.EVENT_REGISTRATION){

            //get the customer details
            Customer customer = customerService.findByCusCustomerNo(customerRewardActivity.getCraCustomerNo());

            //log the activity
            customerActivityService.logActivity(customer.getCusLoyaltyId(),CustomerActivityType.EVENT_REGISTER,customerRewardActivity.getCraActivityRef(),customer.getCusMerchantNo(),"");

        }
        // Check if the messageSpiel is saved
        if ( customerRewardActivity.getCraId() == null ) {

            // Log the response
            log.info("validateAndSaveCustomerRewardActivity - Response : Unable to save the message spiel information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        //start the reward activity processing
        startRewardActivityProcessing(customerRewardActivity);

        // return the object
        return customerRewardActivity;


    }

    @Override
    public void inject(CustomerRewardUtils beansManager) {
        this.customerService = beansManager.getCustomerService();
    }




}

package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.CustomerRewardActivityStatus;
import com.inspirenetz.api.core.dictionary.CustomerRewardingType;
import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerPromotionalEvent;
import com.inspirenetz.api.core.domain.CustomerPromotionalEvent;
import com.inspirenetz.api.core.domain.CustomerRewardActivity;
import com.inspirenetz.api.core.domain.validator.CustomerPromotionalEventValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CustomerPromotionalEventRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.List;

/**
 * Created by saneeshci on 27/9/14.
 */
@Service
public class CustomerPromotionalEventServiceImpl extends BaseServiceImpl<CustomerPromotionalEvent> implements CustomerPromotionalEventService {


    private static Logger log = LoggerFactory.getLogger(CustomerPromotionalEventServiceImpl.class);


    @Autowired
    CustomerPromotionalEventRepository customerPromotionalEventRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    LoyaltyEngineService loyaltyEngineService;

    @Autowired
    private AuthSessionUtils authSessionUtils;


    public CustomerPromotionalEventServiceImpl() {

        super(CustomerPromotionalEvent.class);

    }


    @Override
    protected BaseRepository<CustomerPromotionalEvent,Long> getDao() {
        return customerPromotionalEventRepository;
    }



    @Override
    public CustomerPromotionalEvent findByCpeId(Long cpeId)   {

        // Get the customerPromotionalEvent for the given customerPromotionalEvent id from the repository
        CustomerPromotionalEvent customerPromotionalEvent = customerPromotionalEventRepository.findByCpeId(cpeId);

        // Return the customerPromotionalEvent
        return customerPromotionalEvent;

    }

    @Override
    public List<CustomerPromotionalEvent> findByCpeLoyaltyIdAndCpeMerchantNo(String cpeLoyaltyId,Long cpeMerchantNo) {


        // Get the customerPromotionalEvent for the given customerPromotionalEvent id from the repository
        List<CustomerPromotionalEvent> customerPromotionalEvents = customerPromotionalEventRepository.findByCpeLoyaltyIdAndCpeMerchantNo(cpeLoyaltyId,cpeMerchantNo);



        //return the object
        return customerPromotionalEvents;

    }

    @Override
    public CustomerPromotionalEvent findByCpeLoyaltyIdAndCpeEventIdCpeMerchantNo(String cpeLoyaltyId, Long cpeEventId,Long cpeMerchantNo) {

        CustomerPromotionalEvent customerPromotionalEvent = customerPromotionalEventRepository.findByCpeLoyaltyIdAndCpeEventIdAndCpeMerchantNo(cpeLoyaltyId, cpeEventId, cpeMerchantNo);

        return customerPromotionalEvent;
    }


    @Override
    public CustomerPromotionalEvent validateAndSaveCustomerPromotionalEvent(CustomerPromotionalEvent customerPromotionalEvent ) throws InspireNetzException {

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Create the Validator
        CustomerPromotionalEventValidator validator = new CustomerPromotionalEventValidator();

        // Create the bindingResult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(customerPromotionalEvent,"customerPromotionalEvent");

        // Validate the request
        validator.validate(customerPromotionalEvent,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveCustomerPromotionalEvent - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }
        
        //check duplicate existing
        if(isDuplicate(customerPromotionalEvent)){

            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);
        }

        // If the customerPromotionalEvent.getLrqId is  null, then set the created_by, else set the updated_by
        if ( customerPromotionalEvent.getCpeId() == null ) {

            customerPromotionalEvent.setCreatedBy(auditDetails);

        } else {

            customerPromotionalEvent.setUpdatedBy(auditDetails);

        }

        customerPromotionalEvent = saveCustomerPromotionalEvent(customerPromotionalEvent);

        // Check if the customerPromotionalEvent is saved
        if ( customerPromotionalEvent.getCpeId() == null ) {

            // Log the response
            log.info("validateAndSaveCustomerPromotionalEvent - Response : Unable to save the customerPromotionalEvent information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // return the object
        return customerPromotionalEvent;


    }

    @Override
    public boolean validateAndDeleteCustomerPromotionalEvent(CustomerPromotionalEvent customerPromotionalEvent) {

        return deleteCustomerPromotionalEvent(customerPromotionalEvent.getCpeId());
    }

    @Override
    public CustomerPromotionalEvent saveCustomerPromotionalEvent(CustomerPromotionalEvent customerPromotionalEvent ){

        // Save the customerPromotionalEvent
        return customerPromotionalEventRepository.save(customerPromotionalEvent);

    }


    @Override
    public boolean deleteCustomerPromotionalEvent(Long rolId) {

        // Delete the customerPromotionalEvent
        customerPromotionalEventRepository.delete(rolId);

        // return true
        return true;

    }

    @Override
    public void triggerAwardingForCustomerPromotionalEvent(CustomerPromotionalEvent customerPromotionalEvent) throws InspireNetzException {

        if(customerPromotionalEvent != null && customerPromotionalEvent.getCpeId() == null){

            customerPromotionalEvent = validateAndSaveCustomerPromotionalEvent(customerPromotionalEvent);
        }

        CustomerRewardActivity customerRewardActivity = getCustomerRewardActivityObject(customerPromotionalEvent);

        loyaltyEngineService.doEventBasedAwarding(customerRewardActivity);


    }

    @Override
    public void triggerAwardingForCustomerPromotionalEventList(List<CustomerPromotionalEvent> customerPromotionalEvents) throws InspireNetzException {

        //check wrapper is null or size is zero
        if(customerPromotionalEvents ==null || customerPromotionalEvents.size() ==0){

            //throw new inspirenetz exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);
        }

        Long merchantNo =authSessionUtils.getMerchantNo();

        //save the data
        for (CustomerPromotionalEvent customerPromotionalEvents1:customerPromotionalEvents){


            customerPromotionalEvents1.setCpeMerchantNo(merchantNo);

            //trigger awarding
            triggerAwardingForCustomerPromotionalEvent(customerPromotionalEvents1);
        }

    }

    @Override
    public boolean isDuplicate(CustomerPromotionalEvent customerPromotionalEvent) {

        // Get the event information
        CustomerPromotionalEvent exCustomerPromotionEvent =findByCpeEventIdAndCpeProductAndCpeMerchantNoAndCpeLoyaltyId(customerPromotionalEvent.getCpeEventId(),customerPromotionalEvent.getCpeProduct(),customerPromotionalEvent.getCpeMerchantNo(),customerPromotionalEvent.getCpeLoyaltyId());

        // If the cpeId is 0L, then its a new customerPromotionEvent so we just need to check if there is ano
        // ther customerPromotionEvent code
        if ( customerPromotionalEvent.getCpeId() == null || customerPromotionalEvent.getCpeId() == 0L ) {

            // If the customerPromotionEvent is not null, then return true
            if ( exCustomerPromotionEvent != null ) {

                return true;

            }

        } else {

            // Check if the customerPromotionEvent is null
            if ( exCustomerPromotionEvent != null && exCustomerPromotionEvent.getCpeId().longValue() != exCustomerPromotionEvent.getCpeId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;
    }

    @Override
    public CustomerPromotionalEvent findByCpeEventIdAndCpeProductAndCpeMerchantNoAndCpeLoyaltyId(Long eventId, String productCode, Long cpeMerchantNo, String cpeLoyaltyId) {
        return customerPromotionalEventRepository.findByCpeEventIdAndCpeProductAndCpeMerchantNoAndCpeLoyaltyId(eventId,productCode,cpeMerchantNo,cpeLoyaltyId);
    }

    private CustomerRewardActivity getCustomerRewardActivityObject(CustomerPromotionalEvent customerPromotionalEvent) {

        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(customerPromotionalEvent.getCpeLoyaltyId(),customerPromotionalEvent.getCpeMerchantNo());

        CustomerRewardActivity customerRewardActivity = new CustomerRewardActivity();

        customerRewardActivity.setCraActivityRef(customerPromotionalEvent.getCpeId()+"");

        customerRewardActivity.setCraType(CustomerRewardingType.MERCHANT_EVENT);

        customerRewardActivity.setCraCustomerNo(customer.getCusCustomerNo());

        customerRewardActivity.setCraStatus(CustomerRewardActivityStatus.NEW);

        customerRewardActivity.setCraActivityTimeStamp(customerPromotionalEvent.getCpeTimeStamp());

        return customerRewardActivity;
    }

}


package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerRewardActivity;
import com.inspirenetz.api.core.domain.CustomerSubscription;
import com.inspirenetz.api.core.domain.validator.CustomerSubscriptionValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CustomerSubscriptionRepository;
import com.inspirenetz.api.core.service.CustomerRewardActivityService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.CustomerSubscriptionService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
@Transactional
public class CustomerSubscriptionServiceImpl extends BaseServiceImpl<CustomerSubscription> implements CustomerSubscriptionService {


    private static Logger log = LoggerFactory.getLogger(CustomerSubscriptionServiceImpl.class);


    @Autowired
    CustomerSubscriptionRepository customerSubscriptionRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    CustomerRewardActivityService customerRewardActivityService;

    public CustomerSubscriptionServiceImpl() {

        super(CustomerSubscription.class);

    }

    @Override
    protected BaseRepository<CustomerSubscription,Long> getDao() {
        return customerSubscriptionRepository;
    }




    @Override
    public CustomerSubscription getCustomerSubscription(Long csuId) throws InspireNetzException {

        // Get the CustomerSubscription object
        CustomerSubscription customerSubscription = customerSubscriptionRepository.findByCsuId(csuId);

        // Check if the customer subscription is existing
        if ( customerSubscription == null ) {

            // Log the information
            log.info("listSubscriptionsForCustomer -> Customer does not exist");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }




        // GEt the userType
        Integer userType = authSessionUtils.getUserType();

        // Flag holding if the request is authroized
        boolean isAuthorized = true;

        // if the user type is customer, check if the customer.cusUserNo is the user number
        // in the session
        if ( userType == UserType.CUSTOMER ) {

            // Get the customer information
            Customer customer = customerService.findByCusCustomerNo(customerSubscription.getCsuCustomerNo());

            // If the customer does not exist or, customer.cusUserNo is not authsession user no,
            // then we need to set the field to false
            if ( customer == null || customer.getCusCustomerNo().longValue() != authSessionUtils.getUserNo().longValue() ) {

                // Set the flag to false
                isAuthorized = false;

            }
        }


        // If the userType is merchant, check if the merchant number of the customer is same as the
        // merchantno of the currently logged in user, otherwise set the flag as false
        if ( userType == UserType.MERCHANT_USER &&
             customerSubscription.getCsuMerchantNo().longValue() != authSessionUtils.getMerchantNo().longValue()) {

            // Set the flag to false
            isAuthorized = false;

        }


        // If the isAuthorized is false, then we need to show the error message
        if ( !isAuthorized ) {

            // Log the information
            log.info("listSubscriptionsForCustomer -> You are not authorized ");

            // throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);


        }

        // Call the getCustomer to fetch the customer information lazily
        // IMPORTANT: Need to call the function of lazily loaded object
        // If just getcustomer is called, then customer instance is not getting loaded
        customerSubscription.getCustomer().getCusCustomerNo();

        // Call the getProduction to fetch the product information lazily
        customerSubscription.getProduct().getPrdId();

        // Return the customerSubscription
        return customerSubscription;

    }

    @Override
    public List<CustomerSubscription> listSubscriptionsForCustomer(Long csuCustomerNo) throws InspireNetzException {

        // Get the customer information
        Customer customer = customerService.findByCusCustomerNo(csuCustomerNo);

        // check if the customer exists
        if ( customer == null ) {

            // Log the information
            log.info("listSubscriptionsForCustomer -> Customer does not exist");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // GEt the userType
        Integer userType = authSessionUtils.getUserType();

        // Flag holding if the request is authroized
        boolean isAuthorized = true;

        // if the user type is customer, check if the customer.cusUserNo is the user number
        // in the session
        if ( userType == UserType.CUSTOMER &&
                (customer.getCusUserNo().longValue() != authSessionUtils.getUserNo().longValue())) {

            // Set the flag to false
            isAuthorized = false;

        }


        // If the userType is merchant, check if the merchant number of the customer is same as the
        // merchantno of the currently logged in user, otherwise set the flag as false
        if ( userType == UserType.MERCHANT_USER &&
             customer.getCusMerchantNo().longValue() != authSessionUtils.getMerchantNo().longValue()) {

            // Set the flag to false
            isAuthorized = false;

        }


        // If the isAuthorized is false, then we need to show the error message
        if ( !isAuthorized ) {

            // Log the information
            log.info("listSubscriptionsForCustomer -> You are not authorized ");

            // throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);


        }


        // Get the List
        List<CustomerSubscription> customerSubscriptionList =  customerSubscriptionRepository.findByCsuCustomerNo(csuCustomerNo);

        // go through each of the CustomerSubscription object and then call the getting
        // for customer and
        for(CustomerSubscription customerSubscription : customerSubscriptionList ) {

            // Get the customer
            customerSubscription.getCustomer();

            // Get the Product
            customerSubscription.getProduct();

        }

        // Return the list
        return  customerSubscriptionList;

    }

    @Override
    public List<CustomerSubscription> findByCsuCustomerNo(Long csuCustomerNo) {

        // Get the List
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findByCsuCustomerNo(csuCustomerNo);

        // Return the list
        return customerSubscriptionList;

    }

    @Override
    public CustomerSubscription findByCsuCustomerNoAndCsuProductCode(Long csuCustomerNo, String csuProductCode) {

        // Get the CustomerSubscription
        CustomerSubscription customerSubscription = customerSubscriptionRepository.findByCsuCustomerNoAndCsuProductCode(csuCustomerNo,csuProductCode);

        // Return the object
        return customerSubscription;

    }

    @Override
    public boolean isDuplicateProductCodeExistingForCustomer(CustomerSubscription customerSubscription) {

        // Get the customerSubscription information
        CustomerSubscription exCustomerSubscription = customerSubscriptionRepository.findByCsuCustomerNoAndCsuProductCode(customerSubscription.getCsuCustomerNo(), customerSubscription.getCsuProductCode());

        // If the csuId is 0L, then its a new customerSubscription so we just need to check if there is ano
        // ther customerSubscription code
        if ( customerSubscription.getCsuId() == null || customerSubscription.getCsuId() == 0L ) {

            // If the customerSubscription is not null, then return true
            if ( exCustomerSubscription != null ) {

                return true;

            }

        } else {

            // Check if the customerSubscription is null
            if ( exCustomerSubscription != null && customerSubscription.getCsuId().longValue() != exCustomerSubscription.getCsuId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;
    }

    @Override
    public String getCustomerSubscriptionProductCode(Customer customer) {

        // Get the list of subscriptions for customer
        List<CustomerSubscription> subscriptionList = findByCsuCustomerNo(customer.getCusCustomerNo());

        // Check if the list is null or empty, then return empty string
        if ( subscriptionList == null || subscriptionList.isEmpty() ) {

            return "";

        }


        // If we have data, the return the product code of the first entry in the list
        return subscriptionList.get(0).getCsuProductCode();

    }



    @Override
    public boolean removeCustomerSubscription(Long csuId) throws InspireNetzException {


        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the customerSubscription information
        CustomerSubscription customerSubscription = getCustomerSubscription(csuId);

        // Check if the customerSubscription is found
        if ( customerSubscription == null || customerSubscription.getCsuId() == null) {

            // Log the response
            log.info("removeCustomerSubscription - Response : No customerSubscription information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( customerSubscription.getCsuMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("removeCustomerSubscription - Response : You are not authorized to delete the customerSubscription");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Delete the customerSubscription and set the retData fields
        deleteCustomerSubscription(csuId);


        // Return true
        return true;

    }

    @Override
    public CustomerSubscription addCustomerSubscription(CustomerSubscription customerSubscription ) throws InspireNetzException {

        // Check if the customer is existing
        Customer customer = customerService.findByCusCustomerNo(customerSubscription.getCsuCustomerNo());


        // If no customer exists, then throw error
        if ( customer == null ) {

            // Log the information
            log.info("addCustomerSubscription -> Customer does not exists");

            // throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the customer subscription already exists
        boolean isExists =  isDuplicateProductCodeExistingForCustomer(customerSubscription);

        // If exists, then throw error
        if ( isExists ) {

            // Log the information
            log.info("addCustomerSubscription --> Duplicate entry exists for the same product code" );

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }



        // Get the currently logged in merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Set the merchantNo to the customerSubscription
        customerSubscription.setCsuMerchantNo(merchantNo);

        // Update the audit details
        authSessionUtils.updateAuditDetails(customerSubscription);



        // Create the BindingResult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(customerSubscription,"customerSubscription");

        // Create the Validator
        CustomerSubscriptionValidator validator= new CustomerSubscriptionValidator();

        // Validate
        validator.validate(customerSubscription,result);

        // Check if the result has got errors
        if ( result.hasErrors() ) {

            // Log the information
            log.info("addCustomerSubscription --> Invalid input in the request");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // Save the customerSubscription
        customerSubscription = saveCustomerSubscription(customerSubscription);


        // Check if the customerSubscription is saved
        if ( customerSubscription == null || customerSubscription.getCsuId() == null ) {

            // Log the response
            log.info("addCustomerSubscription - Response : Unable to save the customerSubscription information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        //add customer reward data
        CustomerRewardActivity customerRewardActivity = new CustomerRewardActivity();

        //set values to customer reward activity
        customerRewardActivity.setCraCustomerNo(customerSubscription.getCsuCustomerNo());
        customerRewardActivity.setCraActivityRef(customerSubscription.getCsuId().toString());
        customerRewardActivity.setCraType(CustomerRewardingType.PRODUCT_SUBSCRIPTION);
        customerRewardActivity.setCraStatus(CustomerRewardActivityStatus.PROCESSED);

        //save the customer reward activity
        customerRewardActivityService.saveCustomerRewardActivity(customerRewardActivity);

        // Return the object
        return customerSubscription;
    }

    @Override
    public CustomerSubscription saveCustomerSubscription(CustomerSubscription customerSubscription )  {

        return customerSubscriptionRepository.save(customerSubscription);

    }

    @Override
    public boolean deleteCustomerSubscription(Long csuId) {

        // Delete the customerSubscription
        customerSubscriptionRepository.delete(csuId);

        // return true
        return true;

    }

}

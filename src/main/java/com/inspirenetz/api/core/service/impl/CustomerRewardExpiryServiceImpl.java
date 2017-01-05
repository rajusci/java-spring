package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CustomerRewardExpiryRepository;
import com.inspirenetz.api.core.service.AccumulatedRewardBalanceService;
import com.inspirenetz.api.core.service.CustomerRewardExpiryService;
import com.inspirenetz.api.core.service.LoyaltyEngineService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.apache.commons.beanutils.BeanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
@Service
public class CustomerRewardExpiryServiceImpl extends BaseServiceImpl<CustomerRewardExpiry> implements CustomerRewardExpiryService {

    private static Logger log = LoggerFactory.getLogger(CustomerRewardExpiryServiceImpl.class);


    @Autowired
    private CustomerRewardExpiryRepository customerRewardExpiryRepository;

    @Autowired
    private LoyaltyEngineService loyaltyEngineService;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;



    public CustomerRewardExpiryServiceImpl() {

        super(CustomerRewardExpiry.class);

    }

    @Override
    protected BaseRepository<CustomerRewardExpiry,Long> getDao() {
        return customerRewardExpiryRepository;
    }


    @Override
    public CustomerRewardExpiry findByCreId(Long creId) {

        // Get the CustomerRewardExpiry
        CustomerRewardExpiry customerRewardExpiry = customerRewardExpiryRepository.findByCreId(creId);

        // return the object
        return customerRewardExpiry;

    }

    @Override
    public List<CustomerRewardExpiry> findByCreLoyaltyId(String creLoyaltyId) {

        // Get the list of expiries for the loyalty id
        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryRepository.findByCreLoyaltyId(creLoyaltyId);

        // Return the list
        return customerRewardExpiryList;

    }

    @Override
    public List<CustomerRewardExpiry> findByCreMerchantNoAndCreLoyaltyId(Long creMerchantNo, String creLoyaltyId) {

        // Get the list of expiries for the loyalty id
        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryRepository.findByCreMerchantNoAndCreLoyaltyId(creMerchantNo,creLoyaltyId);

        // Return the list
        return customerRewardExpiryList;

    }

    @Override
    public List<CustomerRewardExpiry> findByCreLoyaltyIdAndCreRewardCurrencyId(String creLoyaltyId,Long creRewardCurrencyId) {

        // Get the list of expiries for the loyalty id and reward currency
        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryRepository.findByCreLoyaltyIdAndCreRewardCurrencyId(creLoyaltyId, creRewardCurrencyId);

        // Return the list
        return customerRewardExpiryList;

    }

    @Override
    public List<CustomerRewardExpiry> getFIFOCustomerExpiryList(Long creMerchantNo, String creLoyaltyId, Long creRewardCurrency ) {

        // Get the list of items by the fields
        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryRepository.findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyId(creMerchantNo,creLoyaltyId,creRewardCurrency);

        // If the list is null,then we  need to set \it to empty
        if ( customerRewardExpiryList == null ) {

            customerRewardExpiryList = new ArrayList<CustomerRewardExpiry>();

        }

        // Before we return the List, we need to sort the CustomerRewardExpiry objects using the
        // expiry date field of the CRE table
        //
        // Create the BeanComparator with the field as creExpiryDt
        BeanComparator fieldComparator = new BeanComparator("creExpiryDt");

        // Sort the List
        Collections.sort(customerRewardExpiryList, fieldComparator);

        // Return the list
        return customerRewardExpiryList;

    }

    @Override
    public List<CustomerRewardExpiry> findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyId(Long creMerchantNo, String creLoyaltyId, Long creRewardCurrencyId) {

        // Get the list of expiries for the loyalty id and reward currency
        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryRepository.findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyId(creMerchantNo, creLoyaltyId, creRewardCurrencyId);

        // Return the list
        return customerRewardExpiryList;

    }

    @Override
    public CustomerRewardExpiry findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyIdAndCreExpiryDt(Long creMerchantNo, String creLoyaltyId, Long creRewardCurrencyId, Date creExpiryDt) {

        // Get the CustomerRewardExpiry
        CustomerRewardExpiry customerRewardExpiry = customerRewardExpiryRepository.findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyIdAndCreExpiryDt(creMerchantNo,creLoyaltyId,creRewardCurrencyId,creExpiryDt);

        // Return the customerRewardExpiry
        return customerRewardExpiry;

    }

    @Override
    public Page<CustomerRewardExpiry> getExpiredCustomerRewardExpiry(Long creMerchantNo,Pageable pageable) {

        // Get the today Date
        Date today = new Date(new java.util.Date().getTime());

        // Return the list
        return customerRewardExpiryRepository.getCustomerRewardExpiryExpiredByDate(creMerchantNo, today , pageable);

    }



    @Override
    public CustomerRewardExpiry saveCustomerRewardExpiry(CustomerRewardExpiry customerRewardExpiry) {

        // Return the CustomerRewardExpiry after saving
        return customerRewardExpiryRepository.save(customerRewardExpiry);

    }

    @Override
    public boolean deleteCustomerRewardExpiry(CustomerRewardExpiry customerRewardExpiry) {

        // Delete the object
        customerRewardExpiryRepository.delete(customerRewardExpiry);;

        // Return true;
        return true;

    }


    /**
     * Function called when a customer is being deactivated and we need to expire
     * all the remanining balance for the cusotmer
     *
     * Function will first expire the balance and also we need to clear the ARB
     * for clearing the tier calcuation
     *
     * @param customer  - The Customer object to be cleared
     * @return          - true if the data was cleared successfully
     *
     */
    @Override
    public boolean expireBalanceForCustomer(Customer customer) {

        // Get the CustomerRewardExpiry entries for the Customer
        List<CustomerRewardExpiry> customerRewardExpiryList = findByCreMerchantNoAndCreLoyaltyId(customer.getCusMerchantNo(),customer.getCusLoyaltyId());

        // If the list is empty, then return true
        if ( customerRewardExpiryList == null || customerRewardExpiryList.isEmpty() ) {

            // Log the information
            log.info("expireBalanceForCustomer -> No reward expiry entries found");

            // return true
            return true;

        }



        // Iterate throught list and call the expire balance method of the LoyaltEngine
        for(CustomerRewardExpiry customerRewardExpiry : customerRewardExpiryList ) {

            // Call the method to clear the balance
            try {

                //set account deactivation to true
                customerRewardExpiry.setAccountDeactivation(true);

                //expire the balance
                loyaltyEngineService.clearExpiredBalance(customerRewardExpiry);

            } catch (InspireNetzException e) {

                // Log the informaton
                log.error("expireBalanceForCustomer -> Error during deletion");

                // print the stack trace
                e.printStackTrace();

                // return false
                return false;

            }

        }


        // On successfully clearing the reward balance , we need to clear the
        // accumulated reward balance
        accumulatedRewardBalanceService.clearAccumulatedRewardBalance(customer);


        // finally return true
        return true;

    }


}

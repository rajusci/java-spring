package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerSubscription;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CustomerSubscriptionService extends BaseService<CustomerSubscription> {

    public CustomerSubscription getCustomerSubscription(Long csuId) throws InspireNetzException;
    public List<CustomerSubscription> listSubscriptionsForCustomer(Long csuCustomerNo) throws InspireNetzException;
    public List<CustomerSubscription> findByCsuCustomerNo(Long csuCustomerNo);
    public CustomerSubscription findByCsuCustomerNoAndCsuProductCode(Long csuCustomerNo, String csuProductCode);
    public boolean isDuplicateProductCodeExistingForCustomer(CustomerSubscription customerSubscription);
    public String getCustomerSubscriptionProductCode(Customer customer);


    public CustomerSubscription addCustomerSubscription(CustomerSubscription customerSubscription ) throws InspireNetzException;
    public CustomerSubscription saveCustomerSubscription(CustomerSubscription customerSubscription);
    public boolean removeCustomerSubscription(Long csuId) throws InspireNetzException;
    public boolean deleteCustomerSubscription(Long csuId);

}

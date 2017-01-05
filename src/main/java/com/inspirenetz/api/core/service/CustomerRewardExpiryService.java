package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerRewardExpiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface CustomerRewardExpiryService extends BaseService<CustomerRewardExpiry> {

    public CustomerRewardExpiry findByCreId(Long creId);
    public List<CustomerRewardExpiry> findByCreLoyaltyId(String creLoyaltyId);
    public boolean expireBalanceForCustomer(Customer customer);
    public List<CustomerRewardExpiry> findByCreMerchantNoAndCreLoyaltyId(Long creMerchantNo,String creLoyaltyId);
    public List<CustomerRewardExpiry> findByCreLoyaltyIdAndCreRewardCurrencyId(String creLoyaltyId, Long creRewardCurrencyId);
    public List<CustomerRewardExpiry> getFIFOCustomerExpiryList(Long creMerchantNo, String creLoyaltyId, Long creRewardCurrency );
    public List<CustomerRewardExpiry> findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyId(Long creMerchantNo,String creLoyaltyId, Long creRewardCurrencyId);
    public CustomerRewardExpiry findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyIdAndCreExpiryDt(Long creMerchantNo,String creLoyaltyId, Long creRewardCurrencyId,Date creExpiryDt);
    public Page<CustomerRewardExpiry> getExpiredCustomerRewardExpiry(Long creMerchantNo,Pageable pageable);


    public CustomerRewardExpiry saveCustomerRewardExpiry(CustomerRewardExpiry customerRewardExpiry);
    public boolean deleteCustomerRewardExpiry(CustomerRewardExpiry customerRewardExpiry);


}

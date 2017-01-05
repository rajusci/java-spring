package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CustomerRewardExpiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface CustomerRewardExpiryRepository extends BaseRepository<CustomerRewardExpiry,Long> {

    public CustomerRewardExpiry findByCreId(Long creId);
    public List<CustomerRewardExpiry> findByCreLoyaltyId(String creLoyaltyId);
    public List<CustomerRewardExpiry> findByCreMerchantNoAndCreLoyaltyId(Long creMerchantNo,String creLoyaltyId);
    public List<CustomerRewardExpiry> findByCreLoyaltyIdAndCreRewardCurrencyId(String creLoyaltyId, Long creRewardCurrencyId);
    public List<CustomerRewardExpiry> findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyId(Long creMerchantNo,String creLoyaltyId, Long creRewardCurrencyId);
    public CustomerRewardExpiry findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyIdAndCreExpiryDt(Long creMerchantNo,String creLoyaltyId, Long creRewardCurrencyId,Date creExpiryDt);

    @Query("select C from CustomerRewardExpiry C where C.creMerchantNo = ?1 and C.creExpiryDt <= ?2 and C.creRewardBalance > 0 ")
    public Page<CustomerRewardExpiry> getCustomerRewardExpiryExpiredByDate(Long creMerchantNo,Date today,Pageable pageable);

}

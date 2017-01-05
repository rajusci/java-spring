package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CustomerRewardBalance;

import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface CustomerRewardBalanceRepository extends BaseRepository<CustomerRewardBalance,Long> {

    public CustomerRewardBalance findByCrbId(Long crbId);
    public List<CustomerRewardBalance> findByCrbLoyaltyIdAndCrbMerchantNo(String crbLoyaltyId, Long crbMerchantNo);
    public CustomerRewardBalance findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(String crbLoyaltyId, Long crbMerchantNo, Long crbRewardCurrency);

}

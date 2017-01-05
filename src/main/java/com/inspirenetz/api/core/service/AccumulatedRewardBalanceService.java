package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.AccumulatedRewardBalance;
import com.inspirenetz.api.core.domain.Customer;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface AccumulatedRewardBalanceService extends BaseService<AccumulatedRewardBalance> {

    public AccumulatedRewardBalance findByArbId(Long arbId);
    public AccumulatedRewardBalance findByArbMerchantNoAndArbLoyaltyIdAndArbRewardCurrency(Long arbMerchantNo, String arbLoyaltyId, Long arbRewardCurrency);
    public boolean isAccumulatedRewardBalanceCodeDuplicateExisting(AccumulatedRewardBalance accumulatedRewardBalance);
    public List<AccumulatedRewardBalance> findByArbMerchantNoAndArbLoyaltyId(Long arbMerchantNo,String arbLoyaltyId);
    public boolean clearAccumulatedRewardBalance(Customer customer);

    public AccumulatedRewardBalance saveAccumulatedRewardBalance(AccumulatedRewardBalance accumulatedRewardBalance);
    public boolean deleteAccumulatedRewardBalance(Long arbId);

}

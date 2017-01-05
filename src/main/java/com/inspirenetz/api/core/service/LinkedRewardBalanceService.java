package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedRewardBalance;
import java.util.List;

/**
 * Created by sandheepgr on 10/3/14.
 */
public interface LinkedRewardBalanceService extends  BaseService<LinkedRewardBalance> {

    public boolean exportPrimaryBalanceToLinkedRewardBalance(Customer primary);
    public List<LinkedRewardBalance> findByLrbPrimaryLoyaltyId(String lrbPrimaryLoyaltyId);
    public List<LinkedRewardBalance> findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(String lrbPrimaryLoyaltyId, Long lrbMerchantNo);
    public LinkedRewardBalance findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(String lrbPrimaryLoyaltyId, Long lrbMerchantNo, Long lrbRewardCurrency);

    public LinkedRewardBalance saveLinkedRewardBalance(LinkedRewardBalance linkedRewardBalance);
    public boolean deleteLinkedRewardBalance(Long lrbId);

}

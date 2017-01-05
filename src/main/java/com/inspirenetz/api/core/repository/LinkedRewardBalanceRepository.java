package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.LinkedRewardBalance;
import java.util.List;

/**
 * Created by sandheepgr on 10/3/14.
 */
public interface LinkedRewardBalanceRepository extends BaseRepository<LinkedRewardBalance,Long> {

    public LinkedRewardBalance findByLrbId(Long lrbId);
    public List<LinkedRewardBalance> findByLrbPrimaryLoyaltyId(String lrbPrimaryLoyaltyId);
    public List<LinkedRewardBalance> findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(String lrbPrimaryLoyaltyId, Long lrbMerchantNo);
    public LinkedRewardBalance findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(String lrbPrimaryLoyaltyId, Long lrbMerchantNo, Long lrbRewardCurrency);

}

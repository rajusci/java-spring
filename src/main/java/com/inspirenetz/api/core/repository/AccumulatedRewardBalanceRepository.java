package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.AccumulatedRewardBalance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface AccumulatedRewardBalanceRepository extends  BaseRepository<AccumulatedRewardBalance,Long> {

    public AccumulatedRewardBalance findByArbId(Long arbId);
    public List<AccumulatedRewardBalance> findByArbMerchantNoAndArbLoyaltyId(Long arbMerchantNo,String arbLoyaltyId);
    public AccumulatedRewardBalance findByArbMerchantNoAndArbLoyaltyIdAndArbRewardCurrency(Long arbMerchantNo, String arbLoyaltyId, Long arbRewardCurrency);

}

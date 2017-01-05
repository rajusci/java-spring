package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.RewardCurrency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface RewardCurrencyRepository extends  BaseRepository<RewardCurrency,Long> {

    public Page<RewardCurrency> findByRwdMerchantNo(Long rwdMerchantNo, Pageable pageable);
    public List<RewardCurrency> findByRwdMerchantNo(Long rwdMerchantNo);
    public RewardCurrency findByRwdCurrencyId(Long rwdCurrencyId);
    public Page<RewardCurrency> findByRwdMerchantNoAndRwdCurrencyNameLike(Long rwdMerchantNo, String rwdName, Pageable pageable);
    public List<RewardCurrency> findByRwdMerchantNoAndRwdCurrencyNameLike(Long rwdMerchantNo, String rwdName);
    public RewardCurrency findByRwdMerchantNoAndRwdCurrencyName(Long rwdMerchantNo, String rwdName);

}

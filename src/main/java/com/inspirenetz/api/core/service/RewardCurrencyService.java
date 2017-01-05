package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface RewardCurrencyService extends BaseService<RewardCurrency> {

    public Page<RewardCurrency> findByRwdMerchantNo(Long rwdMerchantNo, Pageable pageable);
    public List<RewardCurrency> findByRwdMerchantNo(Long rwdMerchantNo);
    public RewardCurrency findByRwdCurrencyId(Long rwdCurrencyId);    
    public Page<RewardCurrency> findByRwdMerchantNoAndRwdCurrencyNameLike(Long rwdMerchantNo, String rwdName, Pageable pageable);
    public RewardCurrency findByRwdMerchantNoAndRwdCurrencyName(Long rwdMerchantNo, String rwdName);
    public boolean isDuplicateRewardCurrencyNameExisting(RewardCurrency rewardCurrency);
    public HashMap<Long,RewardCurrency> getRewardCurrencyKeyMap(Long rwdMerchantNo);
    public double getCashbackValue(RewardCurrency rewardCurrency,double rwdQty);
    public double getCashbackQtyForAmount(RewardCurrency rewardCurrency,double amount);
    public Date getRewardExpiryDate(RewardCurrency rewardCurrency,Date rewardDate);


    public RewardCurrency saveRewardCurrency(RewardCurrency rewardCurrency) throws InspireNetzException;
    public boolean deleteRewardCurrency(Long rwdCurrencyId) throws InspireNetzException;

    public RewardCurrency validateAndSaveRewardCurrency(RewardCurrency rewardCurrency) throws InspireNetzException;
    public boolean validateAndDeleteRewardCurrency(Long rwdCurrencyId) throws InspireNetzException;

    public List<RewardCurrency> listRewardCurrenciesForUser(String usrLoginId,Long rwdMerchantNo, String filter,String query);

    public List<RewardCurrency> findByRwdMerchantNoAndRwdCurrencyNameLike(Long rwdMerchantNo, String rwdName);



}

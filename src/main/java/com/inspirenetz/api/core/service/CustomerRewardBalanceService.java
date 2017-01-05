package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.RewardAdjustment;
import com.inspirenetz.api.core.dictionary.TransferPointRequest;
import com.inspirenetz.api.core.domain.CustomerRewardBalance;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface CustomerRewardBalanceService extends  BaseService<CustomerRewardBalance> {

    public CustomerRewardBalance findByCrbId(Long crbId);
    public List<CustomerRewardBalance> getBalanceList(Long merchantNo,String loyaltyId);
    public List<CustomerRewardBalance> getBalance(Long merchantNo,String loyaltyId,Long rwdId);
    public List<CustomerRewardBalance> searchBalances(Long merchantNo,String loyaltyId,Long rwdId);
    public void sendRewardBalanceSMS(Long merchantNo, String loyaltyId,Long rwdId ) throws InspireNetzException;

    public boolean transferPointsForMerchantUser(String srcLoyaltyId, String destLoyaltyId,Long srcCurrencyId,Long destCurrencyId,Double rwdQty) throws InspireNetzException;
    public boolean transferPointsForCustomerUser(Long merchantNo, String destLoyaltyId,Long srcCurrencyId,Long destCurrencyId,Double rwdQty) throws InspireNetzException;

    public List<CustomerRewardBalance> findByCrbLoyaltyIdAndCrbMerchantNo(String crbLoyaltyId,Long crbMerchantNo);
    public CustomerRewardBalance findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(String crbLoyaltyId,Long crbMerchantNo,Long lrbRewardCurrency);
    public CustomerRewardBalance buyPoints(String loyaltyId,Long merchantNo,Long crbRewardCurrency,Long crbNumPoints) throws InspireNetzException;

    public CustomerRewardBalance awardPointsForRewardAdjustment(RewardAdjustment rewardAdjustment) throws InspireNetzException;
    public CustomerRewardBalance deductPointsForRewardAdjustment(Long merchantNo, String loyaltyId,Long rwdCurrencyId,Double rwdQty,String reference) throws InspireNetzException;

    public CustomerRewardBalance doRewardAdjustment(Long merchantNo,String loyaltyId,Integer adjType,Long adjCurrencyId,Double adjPoints,boolean isTierAffected,Long adjProgramNo,String reference) throws InspireNetzException;
    public CustomerRewardBalance saveCustomerRewardBalance(CustomerRewardBalance customerRewardBalance);
    public boolean deleteCustomerRewardBalance(CustomerRewardBalance customerRewardBalance);

    public List<CustomerRewardBalance> GetUserRewardBalances(Long merchantNo,Long rwdCurrencyId)throws InspireNetzException;

    public List<CustomerRewardBalance> getUserRewardBalancesForUser(Long merchantNo)throws InspireNetzException;



}

package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.domain.TierGroup;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface TierService extends BaseService<Tier> {


    public Tier findByTieId(Long tieId);
    public boolean startTierEvaluation(Long merchantNo);
    public boolean isTierListValid(List<Tier> tierList);
    public List<Tier> orderTierList(List<Tier> tierList);
    public boolean isByCalendarTimeValid(Integer period);
    public boolean isByCustomerSpecificDateValid(Customer customer,Integer period);
    public boolean isTierCodeDuplicateExisting(Tier tier);
    public List<Tier> listTiersForGroup(Long merchantNo);
    public Tier findByTieName(String tieName);

    public Tier getNextLowerTier( Tier currTier,List<Tier> tierList );

    public List<Tier> findByTieParentGroup(Long tieParentGroup);
    public boolean updateCustomerTier(Customer customer, Tier tier);
    public boolean resetAccumulatedRewardBalance(Customer customer, Long rwdCurrencyId);
    public Tier getCurrentTierForCustomer(Customer customer, List<Tier> tierList );
    public Tier findByTieParentGroupAndTieName(Long tieParentGroup, String tieName);
    public void evaluateTierForCustomer(Customer customer, List<TierGroup> tierGroupList);
    public boolean isTierValidByAmount(Customer customer, TierGroup tierGroup, Tier tier );
    public Integer getTierEvaluationResult(Tier currTier, Tier calcTier, List<Tier> tierList );
    public boolean isTierValidByRewardBalance(Customer customer,TierGroup tierGroup, Tier tier );
    public Tier getCalculatedTierForCustomer(Customer customer, List<Tier> tierList,TierGroup tierGroup );
    public TierGroup getApplicableTierGroupForCustomer(Customer customer,List<TierGroup> tierGroupList );
    public boolean isTierRequestAuthorized(Long merchantNo, Long location, Integer userType, Long tieParentGroup) throws InspireNetzException;
    public boolean saveTierList(List<Tier> tierList, Long userNo) throws InspireNetzException;
    public boolean isTierValidByApplicableGroup(Customer customer, String tigApplicableGroup );

    public void runTierEvaluation();

    public Tier saveTier(Tier tier) throws InspireNetzException;
    public boolean deleteTier(Long tieId) throws InspireNetzException;

    public Tier validateAndSaveTier(Tier tier) throws InspireNetzException;
    public boolean validateAndDeleteTier(Long tieId) throws InspireNetzException;




}

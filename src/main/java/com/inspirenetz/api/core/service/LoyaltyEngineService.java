package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.PointDeductData;
import com.inspirenetz.api.core.dictionary.PointRewardData;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.loyaltyengine.PointTransaction;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.List;

/**
 * Created by sandheepgr on 23/5/14.
 */
public interface LoyaltyEngineService extends PointTransaction {


    public boolean processTransaction(Sale sale);
    public boolean updateCustomerRewardBalance(PointRewardData pointRewardData);
    public boolean updateCustomerRewardExpiry(PointRewardData pointRewardData);
    public boolean updateMerchantProgramSummary(PointRewardData pointRewardData);
    public boolean updateMerchantRewardSummary(PointRewardData pointRewardData);
    public boolean updateCustomerProgramSummary(PointRewardData pointRewardData);
    public boolean updateCustomerSummaryArchive(PointRewardData pointRewardData);
    public boolean updateAccumulatedRewardBalance(PointRewardData pointRewardData);
    public boolean updateLinkedRewardBalance(PointRewardData pointRewardData);

    public boolean updateReferrerProgramSummary(PointRewardData pointRewardData);

    public void runScheduledProcessing() throws InspireNetzException;

    List<CustomerRewardExpiry> getCustomerRewardExpiryList(Customer customer, Long rwdId);

    public void runRewardExpiryProcessing();
    public void doCustomerRewardActivityProcessing(CustomerRewardActivity customerRewardActivity ) throws InspireNetzException;
    public boolean clearExpiredBalance( CustomerRewardExpiry customerRewardExpiry ) throws InspireNetzException;
    public boolean processDTLoyaltyProgramsForCustomer(LoyaltyProgram loyaltyProgram,Customer customer) throws InspireNetzException;


    public boolean isCustomerActive(String loyaltyId,Long merchantNo);


    public boolean updateLinkedBalance( PointRewardData pointRewardData, LinkedLoyalty linkedLoyalty,AccountBundlingSetting accountBundlingSetting );
    public boolean awardPoints(PointRewardData pointRewardData,Transaction transaction) throws InspireNetzException;
    public boolean awardPointsProxy(PointRewardData pointRewardData,Transaction transaction) throws InspireNetzException;
    public boolean processProgramRewarding(LoyaltyProgram loyaltyProgram,Sale sale,PointRewardData pointRewardData) throws InspireNetzException;
    public boolean isProgramGeneralRulesValid(LoyaltyProgram loyaltyProgram,Sale sale);
    public PointRewardData getPointRewardDataObjectForTransaction(Sale  sale);

    public boolean deductPoints(PointDeductData pointDeductData) throws InspireNetzException;
    public boolean deductPointsProxy(PointDeductData pointDeductData) throws InspireNetzException;

    public void doEventBasedAwarding(CustomerRewardActivity customerPromotionalEvent) throws InspireNetzException;


}

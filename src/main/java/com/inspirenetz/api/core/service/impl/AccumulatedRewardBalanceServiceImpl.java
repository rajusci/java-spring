package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.AccumulatedRewardBalance;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.AccumulatedRewardBalanceRepository;
import com.inspirenetz.api.core.service.AccumulatedRewardBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class AccumulatedRewardBalanceServiceImpl extends BaseServiceImpl<AccumulatedRewardBalance> implements AccumulatedRewardBalanceService {


    private static Logger log = LoggerFactory.getLogger(AccumulatedRewardBalanceServiceImpl.class);


    @Autowired
    AccumulatedRewardBalanceRepository accumulatedRewardBalanceRepository;


    public AccumulatedRewardBalanceServiceImpl() {

        super(AccumulatedRewardBalance.class);

    }


    @Override
    protected BaseRepository<AccumulatedRewardBalance,Long> getDao() {
        return accumulatedRewardBalanceRepository;
    }


    @Override
    public AccumulatedRewardBalance findByArbId(Long arbId) {

        // Get the AccumulatedRewardBalance
        AccumulatedRewardBalance accumulatedRewardBalance = accumulatedRewardBalanceRepository.findByArbId(arbId);

        // Return the object
        return accumulatedRewardBalance;

    }

    @Override
    public AccumulatedRewardBalance findByArbMerchantNoAndArbLoyaltyIdAndArbRewardCurrency(Long arbMerchantNo, String arbLoyaltyId, Long arbRewardCurrency) {

        // Get the AccumulatedRewardBalance
        AccumulatedRewardBalance accumulatedRewardBalance = accumulatedRewardBalanceRepository.findByArbMerchantNoAndArbLoyaltyIdAndArbRewardCurrency(arbMerchantNo,arbLoyaltyId,arbRewardCurrency);

        // Return the object
        return accumulatedRewardBalance;

    }




    @Override
    public boolean isAccumulatedRewardBalanceCodeDuplicateExisting(AccumulatedRewardBalance accumulatedRewardBalance) {

        // Get the accumulatedRewardBalance information
        AccumulatedRewardBalance exAccumulatedRewardBalance = accumulatedRewardBalanceRepository.findByArbMerchantNoAndArbLoyaltyIdAndArbRewardCurrency(accumulatedRewardBalance.getArbMerchantNo(), accumulatedRewardBalance.getArbLoyaltyId(), accumulatedRewardBalance.getArbRewardCurrency());

        // If the arbId is 0L, then its a new accumulatedRewardBalance so we just need to check if there is ano
        // ther accumulatedRewardBalance code
        if ( accumulatedRewardBalance.getArbId() == null || accumulatedRewardBalance.getArbId() == 0L ) {

            // If the accumulatedRewardBalance is not null, then return true
            if ( exAccumulatedRewardBalance != null ) {

                return true;

            }

        } else {

            // Check if the accumulatedRewardBalance is null
            if ( exAccumulatedRewardBalance != null && accumulatedRewardBalance.getArbId().longValue() != exAccumulatedRewardBalance.getArbId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public List<AccumulatedRewardBalance> findByArbMerchantNoAndArbLoyaltyId(Long arbMerchantNo, String arbLoyaltyId) {

        // Get the list
        List<AccumulatedRewardBalance> accumulatedRewardBalanceList = accumulatedRewardBalanceRepository.findByArbMerchantNoAndArbLoyaltyId(arbMerchantNo,arbLoyaltyId);

        // return the list
        return accumulatedRewardBalanceList;

    }

    @Override
    public AccumulatedRewardBalance saveAccumulatedRewardBalance(AccumulatedRewardBalance accumulatedRewardBalance ){

        // Save the accumulatedRewardBalance
        return accumulatedRewardBalanceRepository.save(accumulatedRewardBalance);

    }

    @Override
    public boolean deleteAccumulatedRewardBalance(Long arbId) {

        // Delete the accumulatedRewardBalance
        accumulatedRewardBalanceRepository.delete(arbId);

        // return true
        return true;

    }


    @Override
    public boolean clearAccumulatedRewardBalance(Customer customer) {

        // Get the ARB for the Customer
        List<AccumulatedRewardBalance> accumulatedRewardBalanceList = accumulatedRewardBalanceRepository.findByArbMerchantNoAndArbLoyaltyId(customer.getCusMerchantNo(),customer.getCusLoyaltyId());

        // Delete the list
        accumulatedRewardBalanceRepository.delete(accumulatedRewardBalanceList);

        // Return true
        return true;

    }

}

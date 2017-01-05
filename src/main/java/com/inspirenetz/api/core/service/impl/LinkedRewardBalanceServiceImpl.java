package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerRewardBalance;
import com.inspirenetz.api.core.domain.LinkedRewardBalance;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.LinkedRewardBalanceRepository;
import com.inspirenetz.api.core.service.CustomerRewardBalanceService;
import com.inspirenetz.api.core.service.LinkedRewardBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 10/3/14.
 */
@Service
public class LinkedRewardBalanceServiceImpl extends BaseServiceImpl<LinkedRewardBalance> implements LinkedRewardBalanceService {

    private static Logger log = LoggerFactory.getLogger(LinkedRewardBalanceServiceImpl.class);

    @Autowired
    private LinkedRewardBalanceRepository linkedRewardBalanceRepository;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    public LinkedRewardBalanceServiceImpl() {

        super(LinkedRewardBalance.class);

    }

    @Override
    protected BaseRepository<LinkedRewardBalance, Long> getDao() {
        return linkedRewardBalanceRepository;
    }



    @Override
    public List<LinkedRewardBalance> findByLrbPrimaryLoyaltyId(String lrbPrimaryLoyaltyId) {

        // Get the list of balance
        List<LinkedRewardBalance> linkedRewardBalanceList = linkedRewardBalanceRepository.findByLrbPrimaryLoyaltyId(lrbPrimaryLoyaltyId);

        // Return the reward balance
        return linkedRewardBalanceList;

    }

    @Override
    public List<LinkedRewardBalance> findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(String lrbPrimaryLoyaltyId,Long lrbMerchantNo) {

        // Get the list of balance
        List<LinkedRewardBalance> linkedRewardBalanceList = linkedRewardBalanceRepository.findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(lrbPrimaryLoyaltyId, lrbMerchantNo);

        // Return the reward balance
        return linkedRewardBalanceList;

    }


    /**
     * Function to export the customer reward balance entries to the LInkedRewardBalance entry for the
     * customer.
     * This function is normally called when the customer is made as primary for the first time.
     *
     * @param primary - The Customer object for the primary
     * @return        - True if exported successfully , false otherwise
     */
    @Override
    public boolean exportPrimaryBalanceToLinkedRewardBalance(Customer primary) {

        // Get the CustomerRewardBalance for the customer
        List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(primary.getCusLoyaltyId(),primary.getCusMerchantNo());


        // If the list null or empty, then return true
        if ( customerRewardBalanceList == null || customerRewardBalanceList.isEmpty() ) {

            // Log the information
            log.info("exportPrimaryBalanceToLinkedRewardBalance -> No reward balance information found for the primary ");

            // return true
            return true;

        }



        // List holding the LinkedRewardBalance
        List<LinkedRewardBalance> linkedRewardBalanceList = new ArrayList<>(0);

        // Go through the CustomerRewardBalance list and then add to the linkedREwardBalnaceList
        for(CustomerRewardBalance customerRewardBalance : customerRewardBalanceList ) {

            // Create the LinkedRewardBalance object
            LinkedRewardBalance linkedRewardBalance  = new LinkedRewardBalance();

            // Set the fields
            linkedRewardBalance.setLrbMerchantNo(primary.getCusMerchantNo());

            linkedRewardBalance.setLrbPrimaryLoyaltyId(primary.getCusLoyaltyId());

            linkedRewardBalance.setLrbRewardCurrency(customerRewardBalance.getCrbRewardCurrency());

            linkedRewardBalance.setLrbRewardBalance(customerRewardBalance.getCrbRewardBalance());


            // Add to the list
            linkedRewardBalanceList.add(linkedRewardBalance);

        }

        // Save the linkedRewardBalnaceList
        saveAll(linkedRewardBalanceList);

        // Return true
        return true;

    }

    @Override
    public LinkedRewardBalance findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(String lrbPrimaryLoyaltyId,Long lrbMerchantNo,Long lrbRewardCurrency) {

        // Get the list of balance
        LinkedRewardBalance linkedRewardBalance = linkedRewardBalanceRepository.findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(lrbPrimaryLoyaltyId, lrbMerchantNo, lrbRewardCurrency);

        // Return the reward balance
        return linkedRewardBalance;

    }

    @Override
    public LinkedRewardBalance saveLinkedRewardBalance(LinkedRewardBalance linkedRewardBalance ){

        // Save the linked reward balance
        return linkedRewardBalanceRepository.save(linkedRewardBalance);

    }

    @Override
    public boolean deleteLinkedRewardBalance(Long lrbId) {

        // Delete the brand
        linkedRewardBalanceRepository.delete(lrbId);

        // return true
        return true;

    }

}

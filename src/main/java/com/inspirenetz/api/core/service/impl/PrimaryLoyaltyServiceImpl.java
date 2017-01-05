package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.PrimaryLoyaltyStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.PrimaryLoyalty;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.PrimaryLoyaltyRepository;
import com.inspirenetz.api.core.service.LinkedRewardBalanceService;
import com.inspirenetz.api.core.service.PrimaryLoyaltyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by saneeshci on 28/8/14.
 */
@Service
public class PrimaryLoyaltyServiceImpl extends BaseServiceImpl<PrimaryLoyalty> implements PrimaryLoyaltyService {


    private static Logger log = LoggerFactory.getLogger(PrimaryLoyaltyServiceImpl.class);


    @Autowired
    PrimaryLoyaltyRepository primaryLoyaltyRepository;

    @Autowired
    private LinkedRewardBalanceService linkedRewardBalanceService;


    public PrimaryLoyaltyServiceImpl() {

        super(PrimaryLoyalty.class);

    }


    @Override
    protected BaseRepository<PrimaryLoyalty,Long> getDao() {
        return primaryLoyaltyRepository;
    }

    @Override
    public PrimaryLoyalty findByPllId(Long pllId) {

        // Get the data from the repository and store in the object
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyRepository.findByPllId(pllId);

        // Return the list
        return primaryLoyalty;

    }

    @Override
    public PrimaryLoyalty findByPllCustomerNo(Long pllCustomerNo) {

        // Get the primaryLoyalty for the given primaryLoyalty id from the repository
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyRepository.findByPllCustomerNo(pllCustomerNo);

        // Return the primaryLoyalty
        return primaryLoyalty;


    }

    @Override
    public PrimaryLoyalty findByPllLoyaltyId(String pllLoyaltyId) {

        // Get the primaryLoyalty for the given primaryLoyalty id from the repository
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyRepository.findByPllLoyaltyId(pllLoyaltyId);

        // Return the primaryLoyalty
        return primaryLoyalty;


    }

    @Override
    public boolean addCustomerAsPrimary(Customer customer) {

        // Check if customer is already a primary
        PrimaryLoyalty primaryLoyalty = findByPllCustomerNo(customer.getCusCustomerNo());

        // If the primaryLoyalty is null, then add the customer
        if ( primaryLoyalty == null ) {

            // Create new object
            primaryLoyalty = new PrimaryLoyalty();


            // Set the fields
            primaryLoyalty.setPllCustomerNo(customer.getCusCustomerNo());;

            primaryLoyalty.setPllFName(customer.getCusFName());

            primaryLoyalty.setPllLName(customer.getCusLName());

            primaryLoyalty.setPllLocation(customer.getCusLocation());

            primaryLoyalty.setPllLoyaltyId(customer.getCusLoyaltyId());

            primaryLoyalty.setPllStatus(PrimaryLoyaltyStatus.ACTIVE);


            // Save the PrimaryLoyalty object
            primaryLoyalty = savePrimaryLoyalty(primaryLoyalty);


            // If the primaryLoyalty is saved successfully, then update the linked reward balance
            // from the customer reward balance for the customer as the primary is being converted
            // to primary for the first time and the LInkedREwardBalance tables would be empty
            // for the customer
            if ( primaryLoyalty != null && primaryLoyalty.getPllId() != null ) {

                // Export the reward balance to linked reward balance as
                linkedRewardBalanceService.exportPrimaryBalanceToLinkedRewardBalance(customer);

            }
        }


        // Check if the primaryLoyalty id is null
        if (primaryLoyalty.getPllId() == null ) {

            return false ;

        } else {

            return true;

        }

    }


    @Override
    public boolean isPrimaryLoyaltyExisting(PrimaryLoyalty primaryLoyalty) {

        // Get the primaryLoyalty information
        PrimaryLoyalty exPrimaryLoyalty = primaryLoyaltyRepository.findByPllCustomerNo(primaryLoyalty.getPllCustomerNo());

        // If the pllid is 0L, then its a new primaryLoyalty so we just need to check if there is any
        // ther primaryLoyalty
        if ( primaryLoyalty.getPllId() == null || primaryLoyalty.getPllId() == 0L ) {

            // If the primaryLoyalty is not null, then return true
            if ( exPrimaryLoyalty != null ) {

                return true;

            }

        } else {

            // Check if the primaryLoyalty is null
            if ( exPrimaryLoyalty != null  && primaryLoyalty.getPllId().longValue() != exPrimaryLoyalty.getPllId().longValue()) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public PrimaryLoyalty savePrimaryLoyalty(PrimaryLoyalty primaryLoyalty ){

        // Save the primaryLoyalty
        return primaryLoyaltyRepository.save(primaryLoyalty);

    }

    @Override
    public boolean deletePrimaryLoyalty(Long pllId) {

        // Delete the primaryLoyalty
        primaryLoyaltyRepository.delete(pllId);

        // return true
        return true;

    }

}

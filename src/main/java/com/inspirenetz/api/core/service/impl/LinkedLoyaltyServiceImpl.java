package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.LinkedLoyaltyStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.LinkedLoyaltyRepository;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.util.AccountBundlingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 11/3/14.
 */
@Service
public class LinkedLoyaltyServiceImpl extends BaseServiceImpl<LinkedLoyalty> implements LinkedLoyaltyService {

    private static Logger log = LoggerFactory.getLogger(LinkedLoyaltyServiceImpl.class);

    @Autowired
    private LinkedLoyaltyRepository linkedLoyaltyRepository;

    @Autowired
    private AccountBundlingUtils accountBundlingUtils;

    public LinkedLoyaltyServiceImpl() {

        super(LinkedLoyalty.class);

    }

    @Override
    protected BaseRepository<LinkedLoyalty, Long> getDao() {
        return linkedLoyaltyRepository;
    }


    @Override
    public LinkedLoyalty findByLilId(Long lilId) {

        LinkedLoyalty linkedLoyalty = linkedLoyaltyRepository.findByLilId(lilId);

        return linkedLoyalty;
    }

    @Override
    public LinkedLoyalty findByLilChildCustomerNo(Long lilChildCustomerNo) {

        // Get the LinkedLoyalty for the give child customerno
        LinkedLoyalty linkedLoyalty = linkedLoyaltyRepository.findByLilChildCustomerNo(lilChildCustomerNo);

        // Return the linkedLoyalty
        return linkedLoyalty;

    }

    @Override
    public List<LinkedLoyalty> findByLilParentCustomerNo(Long lilParentCustomerNo) {

        // Get the List
        List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyRepository.findByLilParentCustomerNo(lilParentCustomerNo);

        // Return the list
        return linkedLoyaltyList;

    }

    public LinkedLoyalty linkCustomers(Customer primary,Customer secondary) {

        // Create the LinkedLoyalty
        LinkedLoyalty linkedLoyalty = new LinkedLoyalty();

        // Add the fields
        linkedLoyalty.setLilParentCustomerNo(primary.getCusCustomerNo());

        linkedLoyalty.setLilChildCustomerNo(secondary.getCusCustomerNo());

        linkedLoyalty.setLilLocation(primary.getCusLocation());

        linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.ACTIVE);


        // Save the object
        linkedLoyalty = saveLinkedLoyalty(linkedLoyalty);

        // Return the object
        return linkedLoyalty;

    }


    @Override
    public LinkedLoyalty saveLinkedLoyalty(LinkedLoyalty linkedLoyalty ){

        // Save the linkedLoyalty
        return linkedLoyaltyRepository.save(linkedLoyalty);

    }

    @Override
    public boolean deleteLinkedLoyalty(Long brnId) {

        // Delete the linkedLoyalty
        linkedLoyaltyRepository.delete(brnId);

        // return true
        return true;

    }


    @Override
    public List<LinkedLoyalty> findLinkedAccounts(String loyaltyId, Long merchantNo) {

        // Data to return
        List<LinkedLoyalty> linkedLoyaltyList = new ArrayList<>(0);

        // Set the info
        log.info("received loyalty id : "+loyaltyId + " merchantno : " +merchantNo);

        //check if the account is primary or not , if primary find all the linked accounts
        //else get the primary for linked accounts
        Customer primaryCustomer = accountBundlingUtils.getPrimaryCustomerForCustomer(merchantNo,loyaltyId);

        // Variable holding the linked customer information
        Customer linkedCustomer;

        //check if primaryCustomer is null ,
        if( primaryCustomer == null ) {

            // Log the information
            log.info("findLinkedAccounts-> No primary information found");

            // Return the empty list
            return linkedLoyaltyList;

        }


        // Log the information
        log.info("primary customer"+primaryCustomer);

        //check customer itself the primary account
        if(primaryCustomer.getCusLoyaltyId().equals(loyaltyId)) {

            //return the linked accounts of the customer
            //List stores the linked accounts
            linkedLoyaltyList = findByLilParentCustomerNo(primaryCustomer.getCusCustomerNo());

            //getting the customer data for each linked loyalty
            for (LinkedLoyalty linkedLoyalty : linkedLoyaltyList) {

                linkedLoyalty.getChildCustomer().getCusCustomerNo();

                linkedCustomer = linkedLoyalty.getChildCustomer();

                linkedCustomer.setPrimary(false);

                linkedLoyalty.setCustomer(linkedCustomer);

                log.info("now sending linked accounts data");

            }

        } else {

            // Get the LinkedLoyalty object for the data
            LinkedLoyalty linkedLoyalty =  accountBundlingUtils.getCustomerLinkedLoyalty(merchantNo,loyaltyId);

            // If the linkedLoyalty is null, return emtpy list
            if ( linkedLoyalty == null ) {

                // Log the information
                log.info("No LinkedLoyalty information found for the customer");

                // Return the empty list
                return linkedLoyaltyList;

            }

            // Set the primary to true
            primaryCustomer.setPrimary(true);

            // Add the customer as the primary customer
            // This is only to have the customer information to be processed
            // by the Assembler
            linkedLoyalty.setCustomer(primaryCustomer);

            // Add the linkedLoyalty to the list
            linkedLoyaltyList.add(linkedLoyalty);

            // Sending the primary data
            log.info("now sending primary data");

        }


        // Return the linkedLoyatylist object
        return linkedLoyaltyList;
    }

    @Override
    public Long findByCountLilParentCustomerNo(Long cusCustomerNo){


        return linkedLoyaltyRepository.findByCountLilParentCustomerNo(cusCustomerNo);

    }

    @Override
    public List<LinkedLoyalty> getAllLinkedAccounts(Long customerNo) {

        //call repository method to get the linked loyalties
        List<LinkedLoyalty> linkedLoyalties = linkedLoyaltyRepository.getLinkedAccounts(customerNo);

        //return the list
        return linkedLoyalties;
    }
}

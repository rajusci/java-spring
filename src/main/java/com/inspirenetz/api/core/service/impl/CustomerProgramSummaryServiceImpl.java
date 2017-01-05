package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.CustomerProgramSummary;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CustomerProgramSummaryRepository;
import com.inspirenetz.api.core.service.CustomerProgramSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class CustomerProgramSummaryServiceImpl extends BaseServiceImpl<CustomerProgramSummary> implements CustomerProgramSummaryService {

    private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Autowired
    CustomerProgramSummaryRepository customerProgramSummaryRepository;


    public CustomerProgramSummaryServiceImpl() {

        super(CustomerProgramSummary.class);

    }


    @Override
    protected BaseRepository<CustomerProgramSummary,Long> getDao() {
        return customerProgramSummaryRepository;
    }


    @Override
    public CustomerProgramSummary findByCpsId(Long cpsId) {

        // Get the CustomerProgramSummary object
        CustomerProgramSummary customerProgramSummary = customerProgramSummaryRepository.findByCpsId(cpsId);

        // Return the object
        return customerProgramSummary;

    }

    @Override
    public List<CustomerProgramSummary> findByCpsMerchantNoAndCpsLoyaltyId(Long cpsMerchantNo, String cpsLoyaltyId) {

        // Get the list
        List<CustomerProgramSummary> customerProgramSummaryList = customerProgramSummaryRepository.findByCpsMerchantNoAndCpsLoyaltyId(cpsMerchantNo,cpsLoyaltyId);

        // Return the list
        return customerProgramSummaryList;

    }

    @Override
    public CustomerProgramSummary findByCpsMerchantNoAndCpsLoyaltyIdAndCpsProgramId(Long cpsMerchantNo, String cpsLoyaltyId, Long cpsProgramId) {

        // Get the CustomerProgramSummary
        CustomerProgramSummary customerProgramSummary = customerProgramSummaryRepository.findByCpsMerchantNoAndCpsLoyaltyIdAndCpsProgramId(cpsMerchantNo,cpsLoyaltyId,cpsProgramId);

        // Return the customerProgramSummary
        return customerProgramSummary;

    }

    @Override
    public CustomerProgramSummary saveCustomerProgramSummary(CustomerProgramSummary customerProgramSummary) {

        // Save the CustomerProgramsummary
        customerProgramSummary = customerProgramSummaryRepository.save(customerProgramSummary);

        // Return the CustomerProgramSummary
        return customerProgramSummary;

    }

    @Override
    public boolean deleteCustomerProgramSummary(CustomerProgramSummary customerProgramSummary) {

        // Delete the CustomerProgramSummary
        customerProgramSummaryRepository.delete(customerProgramSummary);

        // Return true
        return true;

    }


}

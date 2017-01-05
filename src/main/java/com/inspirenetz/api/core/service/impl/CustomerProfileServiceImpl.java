package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.CustomerProfilePK;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CustomerProfileRepository;
import com.inspirenetz.api.core.service.CustomerProfileService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class CustomerProfileServiceImpl extends BaseServiceImpl<CustomerProfile> implements CustomerProfileService {

    private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    CustomerProfileRepository customerProfileRepository;


    public CustomerProfileServiceImpl() {

        super(CustomerProfile.class);

    }


    @Override
    protected BaseRepository<CustomerProfile, Long> getDao() {
        return customerProfileRepository;
    }


    @Override
    public CustomerProfile findByCspCustomerNo(Long cspCustomerNo) {

        // Get the CustomerProfile
        CustomerProfile customerProfile = customerProfileRepository.findByCspCustomerNo(cspCustomerNo);

        // Return the customerProfile
        return customerProfile;

    }

    @Override
    public CustomerProfile findByCspId(Long cspId) {

        // Get the CustomerProfile
        CustomerProfile customerProfile = customerProfileRepository.findByCspId(cspId);

        // Return the CustomerProfile
        return customerProfile;

    }

    @Override
    public CustomerProfile saveCustomerProfile(CustomerProfile customerProfile) {

        // Save the CustomerProfile object
        customerProfile = customerProfileRepository.save(customerProfile);

        // Return the object
        return customerProfile;

    }

    @Override
    public boolean deleteCustomerProfile(CustomerProfile customerProfile) {

        // Delete the customer profile
        customerProfileRepository.delete(customerProfile);

        // Return true
        return true;

    }


}

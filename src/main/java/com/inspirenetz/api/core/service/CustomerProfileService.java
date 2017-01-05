package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.CustomerProfilePK;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface
        CustomerProfileService extends BaseService<CustomerProfile> {

    public CustomerProfile findByCspCustomerNo(Long cspCustomerNo);
    public CustomerProfile findByCspId( Long cspId);
    public CustomerProfile saveCustomerProfile(CustomerProfile customerProfile);
    public boolean deleteCustomerProfile(CustomerProfile customerProfile);



}

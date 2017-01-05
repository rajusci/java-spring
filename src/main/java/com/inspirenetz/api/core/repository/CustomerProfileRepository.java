package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.CustomerProfilePK;


/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CustomerProfileRepository extends  BaseRepository<CustomerProfile,Long> {

    public CustomerProfile findByCspId(Long cspId);
    public CustomerProfile findByCspCustomerNo(Long cspCustomerNo);

}

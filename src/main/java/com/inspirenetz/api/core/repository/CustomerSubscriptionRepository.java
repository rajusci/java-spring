package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CustomerSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CustomerSubscriptionRepository extends  BaseRepository<CustomerSubscription,Long> {

    public CustomerSubscription findByCsuId(Long csuId);
    public List<CustomerSubscription> findByCsuCustomerNo(Long csuCustomerNo);
    public CustomerSubscription findByCsuCustomerNoAndCsuProductCode(Long csuCustomerNo, String csuProductCode);


}

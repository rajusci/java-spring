package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CustomerRewardActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by saneeshci on 29/9/14.
 */
public interface CustomerRewardActivityRepository extends BaseRepository<CustomerRewardActivity,Long>{

    public CustomerRewardActivity findByCraId(Long craId);
    public CustomerRewardActivity findByCraTypeAndCraCustomerNoAndCraActivityRef(Integer craType, Long craCustomerNo,String craActivityRef);
    public Page<CustomerRewardActivity> findByCraCustomerNo(Long craId,Pageable pageable);
    public Page<CustomerRewardActivity> findByCraCustomerNoAndCraType(Long craId,Integer craType,Pageable pageable);



}

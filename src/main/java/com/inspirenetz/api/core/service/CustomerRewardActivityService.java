package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.CustomerRewardActivity;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Created by saneeshci on 29/9/14.
 */
public interface CustomerRewardActivityService extends BaseService<CustomerRewardActivity>{

    CustomerRewardActivity  findByCraId(Long secId);

    Page<CustomerRewardActivity> findByCraCustomerNo(Long craCustomerNo,Pageable pageable) throws InspireNetzException;
    Page<CustomerRewardActivity> findByCraCustomerNoAndCraType(Long craCustomerNo,Integer craType,Pageable pageable) throws InspireNetzException;

    public CustomerRewardActivity validateAndRegisterCustomerRewardActivity(Long craCustomerNo,Integer craType,String craActivityRef) throws InspireNetzException;
    public CustomerRewardActivity saveCustomerRewardActivity(CustomerRewardActivity customerRewardActivity) throws InspireNetzException;
    public boolean deleteCustomerRewardActivity(CustomerRewardActivity customerRewardActivity);
    public boolean isDuplicateActivityExisting(Integer craType,Long craCustomerNo,String craActivityRef);
    public void startRewardActivityProcessing(CustomerRewardActivity customerRewardActivity) throws InspireNetzException;
    public CustomerRewardActivity saveCustomerRewardActivityByLoyaltyId(String cuaLoyaltyId,Integer craType,String craActivityRef,Long merchantNo) throws InspireNetzException;
}

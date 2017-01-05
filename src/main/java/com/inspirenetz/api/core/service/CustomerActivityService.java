package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by saneesh-ci on 9/9/14.
 */
public interface CustomerActivityService extends BaseService<CustomerActivity> {


    public CustomerActivity findByCuaId(Long cuaId) throws InspireNetzException;

    public Page<CustomerActivity> searchCustomerActivities(String cuaLoyaltyId,Integer cuaActivityType,Date fromDate, Date toDate,Long cuaMerchantNo,Pageable pageable);

    public CustomerActivity saveCustomerActivity(CustomerActivity drawChance);
    public boolean deleteCustomerActivity(Long rvrId);

    public CustomerActivity logActivity(String loyaltyId,Integer activityType,String remarks,Long merchantNo,String params) throws InspireNetzException;

    public List<Map<String, String >> getCustomerRecentActivities(String usrLoginId, Long merchantNo);

    List<CustomerActivity > findByCuaMerchantNoAndCuaLoyaltyId(Long merchantNo, String loyaltyId);

    public Page<CustomerActivity> searchUserActivity(Long merchantNo,Integer cuaActivityType,Date fromDate, Date toDate,Long cuaMerchantNo,Pageable pageable) throws InspireNetzException;
}

package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.DrawChance;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.List;


/**
 * Created by saneesh-ci on 9/9/14.
 */
public interface DrawChanceService extends BaseService<DrawChance> {


    public DrawChance findByDrcId(Long drcId) throws InspireNetzException;
    public DrawChance getCustomerDrawChances(Long drcCustomerNo,Integer drcDrawType) throws InspireNetzException;
    public List<DrawChance> findByDrcType(Integer drcType);

    public DrawChance validateAndSaveDrawChance(DrawChance drawChance) throws InspireNetzException;
    public DrawChance saveDrawChance(DrawChance drawChance);
    public boolean deleteDrawChance(Long rvrId);

    public DrawChance getDrawChancesByLoyaltyId(String loyaltyId,Integer drcDrawType);

    public void expiringDrawChance(Customer customer) throws InspireNetzException;


}

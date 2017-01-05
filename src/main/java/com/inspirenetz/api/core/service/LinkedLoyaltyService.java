package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import java.util.List;

/**
 * Created by sandheepgr on 11/3/14.
 */
public interface LinkedLoyaltyService extends BaseService<LinkedLoyalty> {

    public LinkedLoyalty findByLilId(Long lilId);
    public LinkedLoyalty findByLilChildCustomerNo(Long lilChildCustomerNo);
    public LinkedLoyalty linkCustomers(Customer primary,Customer secondary);
    public List<LinkedLoyalty> findByLilParentCustomerNo(Long lilParentCustomerNo);

    public LinkedLoyalty saveLinkedLoyalty(LinkedLoyalty linkedLoyalty);
    public boolean deleteLinkedLoyalty(Long lilId);
    public List<LinkedLoyalty> findLinkedAccounts(String loyaltyId, Long merchantNo);

    public Long findByCountLilParentCustomerNo(Long cusCustomerNo);

    public List<LinkedLoyalty> getAllLinkedAccounts(Long customerNo);


}

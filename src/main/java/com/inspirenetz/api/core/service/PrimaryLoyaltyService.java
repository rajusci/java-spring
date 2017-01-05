package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.PrimaryLoyalty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by saneesh on 28/8/14.
 */
public interface PrimaryLoyaltyService extends BaseService<PrimaryLoyalty> {

    public PrimaryLoyalty findByPllId(Long pllId);
    public boolean addCustomerAsPrimary(Customer customer);
    public PrimaryLoyalty findByPllCustomerNo(Long pllCustomerNo);
    public PrimaryLoyalty findByPllLoyaltyId(String pllLoyaltyId);
    public boolean isPrimaryLoyaltyExisting(PrimaryLoyalty primaryLoyalty);

    public PrimaryLoyalty savePrimaryLoyalty(PrimaryLoyalty primaryLoyalty);
    public boolean deletePrimaryLoyalty(Long pllId);




}

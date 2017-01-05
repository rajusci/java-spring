package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.PrimaryLoyalty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by saneeshci on 28/3/14.
 */
public interface PrimaryLoyaltyRepository extends  BaseRepository<PrimaryLoyalty,Long> {

    public PrimaryLoyalty findByPllId(Long pllId);
    public PrimaryLoyalty findByPllLoyaltyId(String pllLoyaltyId);
    public PrimaryLoyalty findByPllCustomerNo(Long pllCustomerNo);

}

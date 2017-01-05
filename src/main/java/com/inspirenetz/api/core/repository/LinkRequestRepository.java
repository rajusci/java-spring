package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.LinkRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface LinkRequestRepository extends  BaseRepository<LinkRequest,Long> {

    public LinkRequest findByLrqId(Long lrqId);
    public Page<LinkRequest> findByLrqSourceCustomerAndLrqMerchantNo(Long lrqSourceCustomer,Long lrqMerchantNo,Pageable pageable);
    public Page<LinkRequest> findAll(Pageable pageable);
    public Page<LinkRequest> findByLrqMerchantNo(Long lrqMerchantNo,Pageable pageable);

}

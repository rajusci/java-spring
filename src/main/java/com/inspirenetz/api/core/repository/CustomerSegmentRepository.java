package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CustomerSegment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CustomerSegmentRepository extends  BaseRepository<CustomerSegment,Long> {

    public Page<CustomerSegment> findByCsgMerchantNo(Long csgMerchantNo, Pageable pageable);
    public List<CustomerSegment> findByCsgMerchantNo(Long csgMerchantNo);
    public CustomerSegment findByCsgSegmentId(Long csgSegmentId);
    public CustomerSegment findByCsgMerchantNoAndCsgSegmentName(Long csgMerchantNo, String csgSegmentName);
    public Page<CustomerSegment> findByCsgMerchantNoAndCsgSegmentNameLike(Long csgMerchantNo, String query, Pageable pageable);


}

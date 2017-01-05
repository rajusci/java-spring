package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by saneesh-ci on 25/9/14.
 */
public interface RedemptionVoucherSourceRepository extends  BaseRepository<RedemptionVoucherSource,Long> {

    public RedemptionVoucherSource findByRvsId(Long rvsId);
    public Page<RedemptionVoucherSource> findByRvsMerchantNoAndRvsNameLike(Long rvsMerchantNo, String rvsName,Pageable pageable);
    public Page<RedemptionVoucherSource> findByRvsMerchantNo(Long rvsMerchantNo, Pageable pageable);
    public Page<RedemptionVoucherSource> findByRvsMerchantNoAndRvsStatus(Long rvsMerchantNo, Integer rvsStatus,Pageable pageable);
    public Page<RedemptionVoucherSource> findAll( Pageable pageable);
    public Page<RedemptionVoucherSource> findByRvsNameLike(String rvsName,Pageable pageable);


}

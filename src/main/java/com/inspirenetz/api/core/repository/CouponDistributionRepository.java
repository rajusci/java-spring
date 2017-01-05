package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CouponDistribution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CouponDistributionRepository extends  BaseRepository<CouponDistribution,Long> {

    public Page<CouponDistribution> findByCodMerchantNo(Long codMerchantNo, Pageable pageable);
    public List<CouponDistribution> findByCodMerchantNo(Long codMerchantNo);
    public CouponDistribution findByCodId(Long codId);
    public CouponDistribution findByCodMerchantNoAndCodCouponCode(Long codMerchantNo, String codCouponCode);

}

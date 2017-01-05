package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.CouponDistribution;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CouponDistributionResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CouponDistributionService extends BaseService<CouponDistribution> {

    public Page<CouponDistribution> findByCodMerchantNo(Long codMerchantNo, Pageable pageable);
    public CouponDistribution findByCodId(Long codId);
    public CouponDistribution findByCodMerchantNoAndCodCouponCode(Long codMerchantNo, String codCouponCode);
    public boolean isDuplicateCouponDistributionExisting(CouponDistribution couponDistribution);
    public List<CouponDistribution> getCouponDistributionForCustomers(Customer customer);
    public boolean updateCouponDistributionStatus(Long codId, Integer codStatus,Long merchantNo, Long userNo) throws InspireNetzException;


    public CouponDistribution saveCouponDistribution(CouponDistribution couponDistribution) throws InspireNetzException;
    public boolean deleteCouponDistribution(Long codId) throws InspireNetzException;

    public CouponDistribution validateAndSaveCouponDistribution(CouponDistributionResource couponDistributionResource) throws InspireNetzException;
    public boolean validateAndDeleteCouponDistribution(Long codId) throws InspireNetzException;


}

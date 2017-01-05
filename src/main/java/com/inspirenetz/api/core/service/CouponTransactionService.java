package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.CouponTransaction;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CouponTransactionService extends BaseService<CouponTransaction> {

    public Page<CouponTransaction> findByCptMerchantNo(Long cptMerchantNo,Pageable pageable);
    public Page<CouponTransaction> findByCptMerchantNoAndCptCouponCode(Long cptMerchantNo,String cptCouponCode,Pageable pageable);
    public Page<CouponTransaction> findByCptMerchantNoAndCptLoyaltyId(Long cptMerchantNo, String cptLoyaltyId,Pageable pageable);
    public Page<CouponTransaction> findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyId(Long cptMerhantNo,String cptCouponCode,String cptLoyaltyId,Pageable pageable);
    public CouponTransaction findByCptId(Long cptId);
    public CouponTransaction findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(Long cptMerchantNo,String cptCouponCode,String cptLoyaltyId,Long cptPurchaseId);
    public Map<Integer,Integer> getCouponTransactionCount(CouponTransaction couponTransaction);
    public boolean recordCouponAccept(CouponTransaction couponTransaction) throws InspireNetzException;
    public boolean revertCouponAccept(CouponTransaction couponTransaction) throws InspireNetzException;

    public CouponTransaction saveCouponTransaction(CouponTransaction couponTransaction);
    public boolean deleteCouponTransaction(Long cptId);

}

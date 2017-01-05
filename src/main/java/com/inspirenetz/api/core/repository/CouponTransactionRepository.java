package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.CouponTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CouponTransactionRepository extends  BaseRepository<CouponTransaction,Long> {


    public Page<CouponTransaction> findByCptMerchantNo(Long cptMerchantNo,Pageable pageable);
    public Page<CouponTransaction> findByCptMerchantNoAndCptCouponCode(Long cptMerchantNo,String cptCouponCode,Pageable pageable);
    public Page<CouponTransaction> findByCptMerchantNoAndCptLoyaltyId(Long cptMerchantNo, String cptLoyaltyId,Pageable pageable);
    public Page<CouponTransaction> findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyId(Long cptMerhantNo,String cptCouponCode,String cptLoyaltyId,Pageable pageable);
    public CouponTransaction findByCptId(Long cptId);
    public CouponTransaction findByCptMerchantNoAndCptCouponCodeAndCptLoyaltyIdAndCptPurchaseId(Long cptMerchantNo,String cptCouponCode,String cptLoyaltyId,Long cptPurchaseId);


}

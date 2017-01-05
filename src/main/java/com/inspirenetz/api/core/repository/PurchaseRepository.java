package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;


/**
 * Created by sandheepgr on 28/3/14.
 */
public interface PurchaseRepository extends  BaseRepository<Purchase,Long> {
    
    public Purchase findByPrcId(Long prcId);
    public Page<Purchase> findByPrcMerchantNoAndPrcDate(Long prcMerchantNo, Date prcDate,Pageable pageable);
    public Page<Purchase> findByPrcMerchantNoAndPrcDateBetween(Long prcMerchantNo, Date prcStartDate, Date prcEndDate,Pageable pageable);
    public Page<Purchase> findByPrcMerchantNoAndPrcLoyaltyId(Long prcMerchantNo, String prcLoyaltyId,Pageable pageable);
    public Page<Purchase> findByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateBetween(Long prcMerchantNo, String prcLoyaltyId, Date prcStartDate, Date prcEndDate,Pageable pageable);
    public Purchase findByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateAndPrcAmountAndPrcPaymentReference(Long prcMerchantNo, String prcLoyaltyId,Date prcDate,double prcAmount,String prcPaymentReference);

}

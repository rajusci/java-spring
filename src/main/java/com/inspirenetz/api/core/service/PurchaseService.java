package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Purchase;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;


/**
 * Created by sandheepgr on 28/3/14.
 */
public interface PurchaseService extends BaseService<Purchase> {

    public Purchase findByPrcId(Long prcId);
    public Page<Purchase> findByPrcMerchantNoAndPrcDate(Long prcMerchantNo, Date prcDate,Pageable pageable);
    public Page<Purchase> findByPrcMerchantNoAndPrcDateBetween(Long prcMerchantNo, Date prcStartDate, Date prcEndDate,Pageable pageable);
    public Page<Purchase> findByPrcMerchantNoAndPrcLoyaltyId(Long prcMerchantNo, String prcLoyaltyId,Pageable pageable);
    public Page<Purchase> findByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateBetween(Long prcMerchantNo, String prcLoyaltyId, Date prcStartDate, Date prcEndDate,Pageable pageable);

    public boolean isDuplicatePurchase(Long prcMerchantNo, String prcLoyaltyId, String prcPaymentReference,Date prcDate,double prcAmount );
    public Purchase savePurchase(Purchase purchase) throws InspireNetzException;
    public boolean deletePurchase(Long prcId);
}

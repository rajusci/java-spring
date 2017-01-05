package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface SaleRepository extends  BaseRepository<Sale,Long> {

    public Sale findBySalId(Long salId);
    public Page<Sale> findBySalMerchantNoAndSalDate(Long salMerchantNo, Date salDate,Pageable pageable);
    public Page<Sale> findBySalMerchantNoAndSalDateBetween(Long salMerchantNo, Date salStartDate, Date salEndDate,Pageable pageable);
    public Page<Sale> findBySalMerchantNoAndSalLoyaltyId(Long salMerchantNo, String salLoyaltyId,Pageable pageable);
    public Page<Sale> findBySalMerchantNoAndSalLoyaltyIdAndSalDateBetween(Long salMerchantNo, String salLoyaltyId, Date salStartDate, Date salEndDate,Pageable pageable);
    public Sale findBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalAmountAndSalPaymentReference(Long salMerchantNo, String salLoyaltyId,Date salDate,double salAmount,String salPaymentReference);
    public List<Sale> findBySalMerchantNoAndSalLoyaltyIdAndSalDateBetween(Long salMerchantNo, String salLoyaltyId, Date salStartDate, Date salEndDate);
    public List<Sale> findBySalMerchantNoAndSalLoyaltyIdAndSalDateAndSalPaymentReference(Long salMerchantNo,String salLoyaltyId,Date salDate,String salPaymentReference);
    public List<Sale> findBySalMerchantNoAndSalLocationAndSalDateAndSalPaymentReference(Long salMerchantNo,Long salLocation,Date salDate,String salPaymentReference);
}

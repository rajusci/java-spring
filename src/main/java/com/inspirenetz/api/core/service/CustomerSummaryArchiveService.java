package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.CustomerSummaryArchive;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CustomerSummaryArchiveService extends BaseService<CustomerSummaryArchive> {

    public CustomerSummaryArchive findByCsaId(Long csaId);
    public List<CustomerSummaryArchive> findByCsaMerchantNoAndCsaLoyaltyId(Long csaMerchantNo,String csaLoyaltyId);
    public List<CustomerSummaryArchive> findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocation(Long csaMerchantNo,String csaLoyaltyId,Long csaLocation);
    public List<CustomerSummaryArchive> findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyy(Long csaMerchantNo,String csaLoyaltyId,Long csaLocation,int csaPeriodYyyy);
    public List<CustomerSummaryArchive> findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQq(Long csaMerchantNo,String csaLoyaltyId,Long csaLocation,int csaPeriodYyyy,int csaPeriodQq);
    public CustomerSummaryArchive findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQqAndCsaPeriodMm(Long csaMerchantNo,String csaLoyaltyId,Long csaLocation,int csaPeriodYyyy,int csaPeriodQq,int csaPeriodMm);

    public CustomerSummaryArchive saveCustomerSummaryArchive(CustomerSummaryArchive customerSummaryArchive);
    public boolean deleteCustomerSummaryArchive(CustomerSummaryArchive customerSummaryArchive);

}

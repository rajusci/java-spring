package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CustomerSummaryArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CustomerSummaryArchiveRepository extends  BaseRepository<CustomerSummaryArchive,Long> {

    public CustomerSummaryArchive findByCsaId(Long csaId);
    public List<CustomerSummaryArchive> findByCsaMerchantNoAndCsaLoyaltyId(Long csaMerchantNo,String csaLoyaltyId);
    public List<CustomerSummaryArchive> findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocation(Long csaMerchantNo,String csaLoyaltyId,Long csaLocation);
    public List<CustomerSummaryArchive> findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyy(Long csaMerchantNo,String csaLoyaltyId,Long csaLocation,int csaPeriodYyyy);
    public List<CustomerSummaryArchive> findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQq(Long csaMerchantNo,String csaLoyaltyId,Long csaLocation,int csaPeriodYyyy,int csaPeriodQq);
    public CustomerSummaryArchive findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQqAndCsaPeriodMm(Long csaMerchantNo,String csaLoyaltyId,Long csaLocation,int csaPeriodYyyy,int csaPeriodQq,int csaPeriodMm);

}

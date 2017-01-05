package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.MerchantSettlement;

import java.sql.Date;
import java.util.List;

/**
 * Created by saneesh-ci on 25/9/14.
 */
public interface MerchantSettlementRepository extends BaseRepository<MerchantSettlement,Long> {

    public MerchantSettlement findByMesId(Long drcId);
    public List<MerchantSettlement> findByMesVendorNo(Long mesVendorNo);
    public List<MerchantSettlement> findByMesVendorNoAndMesLocation(Long mesVendorNo, Long mesLocation);
    public List<MerchantSettlement> findByMesVendorNoAndMesIsSettled(Long mesVendorNo, Integer mesIsSettled);
    public List<MerchantSettlement> findByMesVendorNoAndMesLocationAndMesDateBetween(Long mesVendorNo, Long mesLocation, Date startDate, Date endDate);
    public List<MerchantSettlement> findByMesVendorNoAndMesDateBetween(Long mesVendorNo, Date startDate, Date endDate);
    public List<MerchantSettlement> findByMesVendorNoAndMesLocationAndMesSettlementTypeAndMesIsSettledAndMesDateAfter(Long mesVendorNo, Long mesLocation, Integer mesSettlementType, Integer mesIsSettled, Date startDate);
    public List<MerchantSettlement> findByMesVendorNoAndMesLocationAndMesSettlementTypeAndMesIsSettledAndMesDateBetween(Long mesVendorNo, Long mesLocation, Integer mesSettlementType, Integer mesIsSettled, Date startDate, Date endDate);

    public List<MerchantSettlement> findByMesMerchantNoAndMesVendorNoAndMesIsSettled(Long mesMerchantNo,Long mesVendorNo,  Integer mesIsSettled);

    public List<MerchantSettlement> findByMesMerchantNoAndMesVendorNoAndMesLocationAndMesSettlementTypeAndMesIsSettledAndMesDateAfter(Long mesMerchantNo,Long mesVendorNo, Long mesLocation, Integer mesSettlementType, Integer mesIsSettled, Date startDate);

    public MerchantSettlement findByMesMerchantNoAndMesVendorNoAndMesDateAndMesInternalRefAndMesLoyaltyId(Long mesMerchantNo, Long mesVendorNo, Date mesDate,String mesInternalRef, String mesLoyaltyId);
}

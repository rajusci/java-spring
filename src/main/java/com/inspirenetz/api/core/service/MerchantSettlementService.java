package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.EventReactor;
import com.inspirenetz.api.core.dictionary.EventReactorCommand;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import reactor.spring.annotation.Selector;

import java.sql.Date;
import java.util.List;


/**
 * Created by saneesh-ci on 9/9/14.
 */
public interface MerchantSettlementService extends BaseService<MerchantSettlement> {

    public MerchantSettlement findByMesId(Long mesId) throws InspireNetzException;
    public List<MerchantSettlement> findByMesVendorNo(Long mesVendorNo);
    public List<MerchantSettlement> findByMesVendorNoAndMesIsSettled(Long mesVendorNo, Integer mesIsSettled);

    public MerchantSettlement validateAndSaveMerchantSettlement(MerchantSettlement merchantSettlement) throws InspireNetzException;
    public MerchantSettlement saveMerchantSettlement(MerchantSettlement merchantSettlement);
    public boolean deleteMerchantSettlement(Long mesId);
    public List<MerchantSettlement> findByMesVendorNoAndMesLocation(Long mesVendorNo, Long mesLocation);
    public List<MerchantSettlement> findByMesVendorNoMesLocationAndMesDateBetween(Long mesVendorNo, Long mesLocation, Date startDate, Date endDate);

    public List<MerchantSettlement> searchSettlements(Long mesVendorNo, Long mesLocation, Date fromDate, Date endDate) throws InspireNetzException;
    public List<MerchantSettlement> findByMesVendorNoAndMesDateBetween(Long mesVendorNo, Date startDate, Date endDate);

    public List<MerchantSettlement> findByMesVendorNoAndMesLocationAndAndMesSettlementTypeAndMesIsSettledAndMesDateAfter(Long mesVendorNo, Long mesLocation, Integer mesSettlementType, Integer mesIsSettled, Date startDate);

    public List<MerchantSettlement> findByMesMerchantNoAndMesVendorNoAndMesLocationAndAndMesSettlementTypeAndMesIsSettledAndMesDateAfter(Long mesMerchantNo,Long mesVendorNo, Long mesLocation, Integer mesSettlementType, Integer mesIsSettled, Date startDate);

    public MerchantSettlement  findByMesMerchantNoAndMesVendorNoAndMesDateAndMesInternalRefAndMesLoyaltyId(Long mesMerchantNo, Long mesVendorNo, Date mesDate, String mesInternalRef, String mesLoyaltyId);

    public List<MerchantSettlement> findByMesMerchantNoAndMesVendorNoAndMesIsSettled(Long mesMerchantNo,Long mesVendorNo,  Integer mesIsSettled);


    public List<MerchantSettlement> findByMesVendorNoAndMesLocationAndAndMesSettlementTypeAndMesIsSettledAndMesDateBetween(Long mesVendorNo, Long mesLocation, Integer mesSettlementType, Integer mesIsSettled, Date startDate, Date endDate);

    public List<MerchantSettlement> makeSettlement(String settlements) throws InspireNetzException;

    List<MerchantSettlement> sortSettlementsBasedOnDate(List<MerchantSettlement> merchantSettlements);

    @Selector(value= EventReactorCommand.ERC_MARK_AS_SETTLED,reactor = EventReactor.REACTOR_NAME)
    void markBatchAsSettled(List<MerchantSettlement> merchantSettlements);

    void addSettlementEntryForPartnerTransaction(Catalogue catalogue, PartnerCatalogue partnerCatalogue);

    boolean deleteMerchantSettlementEntry(Redemption redemption,RedemptionMerchant redemptionMerchant);
}

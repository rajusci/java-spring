package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.MerchantSettlementCycle;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.Date;
import java.util.List;


/**
 * Created by saneesh-ci on 20/10/15.
 */
public interface MerchantSettlementCycleService extends BaseService<MerchantSettlementCycle> {


    public MerchantSettlementCycle findByMscId(Long mscId) throws InspireNetzException;
    public List<MerchantSettlementCycle> findByMscMerchantNoAndMscRedemptionMerchantAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(Long mscMerchantNo, Long mscRedemptionMerchant, Long mscMerchantLocation, java.sql.Date mscStartDate, java.sql.Date mscEndDate);

    List<MerchantSettlementCycle> findByMscMerchantNoAndMscRedemptionMerchantAndMscStartDateAfterAndMscEndDateBefore(Long mscMerchantNo, Long mscRedemptionMerchant, java.sql.Date mscStartDate, java.sql.Date mscEndDate);

    public List<MerchantSettlementCycle> findByMscRedemptionMerchantAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(Long mscRedemptionMerchant, Long mscMerchantLocation, java.sql.Date mscStartDate, java.sql.Date mscEndDate);

    List<MerchantSettlementCycle> findByMscRedemptionMerchantAndMscStartDateAfterAndMscEndDateBefore(Long mscRedemptionMerchant, java.sql.Date mscStartDate, java.sql.Date mscEndDate);

    public List<MerchantSettlementCycle> findByMscMerchantNoAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(Long mscMerchantNo, Long mscMerchantLocation, java.sql.Date mscStartDate, java.sql.Date mscEndDate);


    List<MerchantSettlementCycle> findByMscMerchantNoAndMscStartDateAfterAndMscEndDateBefore(Long mscMerchantNo, java.sql.Date mscStartDate, java.sql.Date mscEndDate);

    public MerchantSettlementCycle validateAndSaveMerchantSettlementCycle(MerchantSettlementCycle merchantSettlementCycle) throws InspireNetzException;
    public MerchantSettlementCycle saveMerchantSettlementCycle(MerchantSettlementCycle merchantSettlementCycle);
    public boolean deleteMerchantSettlementCycle(Long rvrId);

    public Date getLastCycleGeneratedDate(Long mscMerchantNo, Long mscRedemptionMerchant, Long mscMerchantLocation);

    public boolean generateMerchantSettlementCycle(RedemptionMerchant redemptionMerchant, Long merchantNo);

    public boolean generateMerchantSettlementCycleFromMerchant(Long merchantNo,Long redemptionMerchantNo) throws InspireNetzException;

    public List<MerchantSettlementCycle> searchMerchantSettlementCycle(Long mscMerchantNo, Long mscRedemptionMerchant, Long mscMerchantLocation, java.sql.Date mscStartDate, java.sql.Date mscEndDate);

    boolean markCycleAsSettled(Long mscId) throws InspireNetzException;
}

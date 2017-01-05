package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.MerchantSettlementCycle;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 * Created by saneesh-ci on 25/9/14.
 */
public interface MerchantSettlementCycleRepository extends BaseRepository<MerchantSettlementCycle,Long> {

    public MerchantSettlementCycle findByMscId(Long mscId);
    public List<MerchantSettlementCycle> findByMscMerchantNoAndMscRedemptionMerchantAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(Long mscMerchantNo, Long mscRedemptionMerchant, Long mscMerchantLocation, Date mscStartDate, Date mscEndDate);

    public List<MerchantSettlementCycle> findByMscRedemptionMerchantAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore( Long mscRedemptionMerchant, Long mscMerchantLocation, Date mscStartDate, Date mscEndDate);

    public List<MerchantSettlementCycle> findByMscMerchantNoAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(Long mscMerchantNo, Long mscMerchantLocation, Date mscStartDate, Date mscEndDate);


    @Query("select max(m.mscEndDate) from MerchantSettlementCycle m where m.mscMerchantNo=?1 and m.mscRedemptionMerchant=?2 and m.mscMerchantLocation=?3")
    public Date findLastGeneratedSettlementCycle(Long mscMerchantNo, Long mscRedemptionMerchant, Long mscMerchantLocation);

    List<MerchantSettlementCycle> findByMscRedemptionMerchantAndMscStartDateAfterAndMscEndDateBefore(Long mscRedemptionMerchant, Date mscStartDate, Date mscEndDate);

    List<MerchantSettlementCycle> findByMscMerchantNoAndMscStartDateAfterAndMscEndDateBefore(Long mscMerchantNo, Date mscStartDate, Date mscEndDate);

    List<MerchantSettlementCycle> findByMscMerchantNoAndMscRedemptionMerchantAndMscStartDateAfterAndMscEndDateBefore(Long mscMerchantNo, Long mscRedemptionMerchant, Date mscStartDate, Date mscEndDate);
}

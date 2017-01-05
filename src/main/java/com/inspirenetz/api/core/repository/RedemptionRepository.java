package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Redemption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 */
public interface RedemptionRepository extends BaseRepository<Redemption,Long> {

    public List<Redemption> findByRdmMerchantNoAndRdmUniqueBatchTrackingId(Long rdmMerchantNo,String rdmUniqueBatchTrackingId);
    public Redemption findByRdmId(Long rdmId);
    public Page<Redemption> findByRdmMerchantNoAndRdmLoyaltyIdAndRdmRecordStatusAndRdmDateBetween(Long rdmMerchantNo,String rdmLoyaltyId,Integer rdmRecordStatus,Date rdmStartDate,Date rdmEndDate, Pageable pageable);
    public Page<Redemption> findByRdmMerchantNoAndRdmLoyaltyIdAndRdmTypeAndRdmRecordStatusAndRdmDateBetween(Long rdmMerchantNo,String rdmLoyaltyId,Integer rdmType,Integer rdmRecordStatus,Date rdmStartDate,Date rdmEndDate, Pageable pageable);
    public Page<Redemption> findByRdmMerchantNoAndRdmTypeAndRdmRecordStatusAndRdmDateBetween(Long rdmMerchantNo, int rdmType,Integer rdmRecordStatus,Date rdmStartDate, Date rdmEndDate,Pageable pageable);
    public Page<Redemption> findByRdmMerchantNoAndRdmLoyaltyIdAndRdmStatusAndRdmRecordStatus(Long rdmMerchantNo,String rdmLoyaltyId,Integer rdmStatus,Integer recordStatus,Pageable pageable);
    public Page<Redemption> findByRdmMerchantNoAndRdmLoyaltyIdAndRdmRecordStatus(Long rdmMerchantNo,String rdmLoyaltyId,Integer recordStatus,Pageable pageable);
    public Page<Redemption> findByRdmMerchantNoAndRdmUniqueBatchTrackingIdAndRdmStatusAndRdmRecordStatus(Long rdmMerchantNo,String rdmTrackingId,Integer rdmStatus,Integer recordStatus,Pageable pageable);
    public Page<Redemption> findByRdmMerchantNoAndRdmUniqueBatchTrackingIdAndRdmRecordStatus(Long rdmMerchantNo,String rdmTrackingId,Integer recordStatus,Pageable pageable);

    public Page<Redemption> findByRdmMerchantNoAndRdmProductCodeAndRdmStatusAndRdmRecordStatus(Long rdmMerchantNo,String rdmProductCode,Integer rdmStatus,Integer recordStatus,Pageable pageable);
    public Page<Redemption> findByRdmMerchantNoAndRdmProductCodeAndRdmRecordStatus(Long rdmMerchantNo,String rdmProductCode,Integer recordStatus,Pageable pageable);
    public Page<Redemption> findByRdmMerchantNoAndRdmStatusAndRdmRecordStatus(Long rdmMerchantNo,Integer rdmStatus,Integer recordStatus,Pageable pageable);
    public Page<Redemption> findByRdmMerchantNoAndRdmRecordStatus(Long rdmMerchantNo,Integer recordStatus,Pageable pageable);

    public Page<Redemption> findByRdmPartnerNoAndRdmRecordStatus(Long rdmPartnerNo, Integer recordStatus, Pageable pageable);
    public Page<Redemption> findByRdmPartnerNoAndRdmStatusAndRdmRecordStatus(Long rdmPartnerNo,Integer rdmStatus,Integer recordStatus,Pageable pageable);
    public Page<Redemption> findByRdmPartnerNoAndRdmMerchantNoAndRdmRecordStatus(Long rdmPartnerNo, Long rdmMerchantNo,Integer recordStatus,Pageable pageable);
    public Page<Redemption> findByRdmPartnerNoAndRdmMerchantNoAndRdmStatusAndRdmRecordStatus(Long rdmPartnerNo, Long rdmMerchantNo,Integer rdmStatus, Integer recordStatus,Pageable pageable);
    public Page<Redemption> findByRdmPartnerNoAndRdmProductCodeAndRdmRecordStatus(Long rdmPartnerNo,String rdmProductCode,Integer recordStatus,Pageable pageable);
    public Page<Redemption> findByRdmPartnerNoAndRdmProductCodeAndRdmStatusAndRdmRecordStatus(Long rdmPartnerNo,String rdmProductCode,Integer rdmStatus,Integer recordStatus,Pageable pageable);

    @Query("select R from Redemption R where (R.rdmMerchantNo = ?1 and (?2  = 0 or R.rdmStatus = ?2 ) and R.rdmRecordStatus =?3) ")
    public Page<Redemption> listRedemptionRequestsByMerchantNoAndStatusAndRdmRecordStatus(Long rdmMerchantNo,Integer status,Integer rdmRecordStatus,Pageable pageable);

    @Query("select R from Redemption R where R.rdmMerchantNo = ?1 and R.rdmLoyaltyId = ?2  and ( ?3  = 0 or R.rdmStatus = ?3 )  and R.rdmRecordStatus =?4 ")
    public Page<Redemption> listRedemptionRequestsByMerchantNoAndLoyaltyIdAndStatus(Long rdmMerchantNo, String rdmLoyaltyId,Integer status,Integer rdmRecordStatus,Pageable pageable);

    @Query("select R from Redemption R where R.rdmMerchantNo = ?1 and R.rdmUniqueBatchTrackingId = ?2  and ( ?3  = 0 or R.rdmStatus = ?3 )  and R.rdmRecordStatus =?4 ")
    public Page<Redemption> listRedemptionRequestsByMerchantNoAndTrackingIdAndStatus(Long rdmMerchantNo, String rdmUniqueBatchTrackingId,Integer status,Integer rdmRecordStatus,Pageable pageable);

    @Query("select R from Redemption R where R.rdmMerchantNo = ?1 and R.rdmProductCode = ?2  and ( ?3  = 0 or R.rdmStatus = ?3 ) and R.rdmRecordStatus =?4 ")
    public Page<Redemption> listRedemptionRequestsByMerchantNoAndProductCodeAndStatus(Long rdmMerchantNo, String rdmProductCode,Integer status, Integer rdmRecordStatus,Pageable pageable);

    public List<Redemption> findByRdmLoyaltyIdAndRdmMerchantNoAndRdmRecordStatus(String rdmLoyaltyId,Long rdmMerchantNo,Integer rdmRecordStatus);


    public List<Redemption> findByRdmPartnerNoAndRdmUniqueBatchTrackingId(Long rdmPartnerNo, String UniqueBatchTrackingId);

    public List<Redemption> findByRdmMerchantNoAndRdmLoyaltyIdAndRdmTypeAndRdmRecordStatus(Long rdmMerchantNo, String rdmLoyaltyId, Integer rdmType,Integer rdmRecordStatus);

    public Page<Redemption> findByRdmProductCodeAndRdmTypeAndRdmDateBetween(String rdmProductCode, Integer rdmType,Date rdmStartDate,Date rdmEndDate, Pageable pageable);
}

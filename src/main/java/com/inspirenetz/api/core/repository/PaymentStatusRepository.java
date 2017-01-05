package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface PaymentStatusRepository extends  BaseRepository<PaymentStatus,Long> {

    public PaymentStatus findByPysId(Long pysId);
    public Page<PaymentStatus> findByPysMerchantNo(Long pysMerchantNo, Pageable pageable);


    public Page<PaymentStatus> findByPysMerchantNoAndPysDateAndPysModuleAndPysInternalRef(Long pysMerchantNo,Date pysDate,Integer pysModule,String pysInternalRef, Pageable pageable);
    public Page<PaymentStatus> findByPysMerchantNoAndPysDateAndPysModuleAndPysLoyaltyId(Long pysMerchantNo,Date pysDate,Integer pysModule,String pysLoyaltyId, Pageable pageable);
    public Page<PaymentStatus> findByPysMerchantNoAndPysDateAndPysModuleAndPysTransactionNumber(Long pysMerchantNo,Date pysDate,Integer pysModule,String pysTransactionNumber, Pageable pageable);
    public Page<PaymentStatus> findByPysMerchantNoAndPysDateAndPysModuleAndPysTranApprovalCode(Long pysMerchantNo,Date pysDate,Integer pysModule,String pysTranApprovalCode, Pageable pageable);
    public Page<PaymentStatus> findByPysMerchantNoAndPysDateAndPysModuleAndPysTranReceiptNumber(Long pysMerchantNo,Date pysDate,Integer pysModule,String pysTranReceiptNumber, Pageable pageable);
    public Page<PaymentStatus> findByPysMerchantNoAndPysDateAndPysModuleAndPysTranAuthId(Long pysMerchantNo,Date pysDate,Integer pysModule,String pysTranAuthId, Pageable pageable);

}

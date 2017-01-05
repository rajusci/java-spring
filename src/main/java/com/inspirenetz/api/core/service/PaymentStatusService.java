package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;


/**
 * Created by sandheepgr on 28/3/14.
 */
public interface PaymentStatusService extends BaseService<PaymentStatus> {

    public PaymentStatus findByPysId(Long pysId);
    public Page<PaymentStatus> findByPysMerchantNo(Long pysMerchantNo, Pageable pageable);
    public Page<PaymentStatus> searchPaymentStatus(Long pysMerchantNo, Date pysDate,Integer pysModule,String filter, String query, Pageable pageable);

    public PaymentStatus savePaymentStatus(PaymentStatus paymentStatus);
    public boolean deletePaymentStatus(Long pysId);

}

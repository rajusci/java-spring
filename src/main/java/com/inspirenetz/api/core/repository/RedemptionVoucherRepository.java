package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.domain.RedemptionVoucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 * Created by saneesh-ci on 25/9/14.
 */
public interface RedemptionVoucherRepository extends  BaseRepository<RedemptionVoucher,Long> {

    public RedemptionVoucher findByRvrId(Long rvrId);
    public Page<RedemptionVoucher> findByRvrMerchantNoAndRvrCustomerNoAndRvrStatus(Long rvrMerchantNo,Long rvrCustomerNo,Integer rvrStatus, Pageable pageable);
    public RedemptionVoucher findByRvrMerchantNoAndRvrCustomerNoAndRvrMerchantAndRvrVoucherCodeAndRvrStatus(Long rvrMerchantNo,Long rvrCustomerNo,Long rvrMerchant,String rvrVoucherCode,Integer rvrStatus);

    public Page<RedemptionVoucher> findByRvrMerchantNoAndRvrVoucherCodeLikeOrderByRvrIdDesc(Long merchantNo,String rvrVoucherCode,Pageable pageable);
    public Page<RedemptionVoucher> findByRvrMerchantNoAndRvrProductCodeLikeOrderByRvrIdDesc(Long merchantNo,String rvrProductCode,Pageable pageable);
    public Page<RedemptionVoucher> findByRvrMerchantNoAndRvrLoyaltyIdLikeOrderByRvrIdDesc(Long merchantNo,String rvrLoyaltyId,Pageable pageable);
    public Page<RedemptionVoucher> findAll(Pageable pageable);
    public Page<RedemptionVoucher> findByRvrMerchantNo(Long merchantNo,Pageable pageable);

    public Page<RedemptionVoucher> findByRvrMerchantNoOrderByRvrIdDesc(Long merchantNo,Pageable pageable);

    @Query("select R from RedemptionVoucher R where R.rvrMerchant = ?1 and R.rvrMerchantNo=?2 and ( ?3 = '0' or R.rvrVoucherCode = ?3 ) and ( ?4 = '0' or R.rvrLoyaltyId = ?4) and R.rvrCreateDate between ?5 and ?6")
    public Page<RedemptionVoucher> searchForRedemptionVoucher(Long rvrMerchant,Long rvrMerchantNo,String rvrVoucherCode,String rvrLoyaltyId,Date startDate,Date endDate,Pageable pageable);

    @Query("select R from RedemptionVoucher R where R.rvrMerchant = ?1 and ( ?2 = '0' or R.rvrVoucherCode = ?2 ) and ( ?3 = '0' or R.rvrLoyaltyId = ?3) and R.rvrCreateDate between ?4 and ?5")
    public Page<RedemptionVoucher> searchForRedemptionVoucher(Long rvrMerchant,String rvrVoucherCode,String rvrLoyaltyId,Date startDate,Date endDate,Pageable pageable);

    public RedemptionVoucher findByRvrVoucherCode(String rvrVoucherCode);
    public RedemptionVoucher findByRvrMerchantAndRvrVoucherCodeAndRvrStatus(Long rvrMerchant,String rvrVoucherCode,int rvrStatus);

    public List<RedemptionVoucher> findByRvrMerchantNoAndRvrVoucherCodeLikeAndRvrLoyaltyIdAndCreatedAtBetweenOrderByRvrIdDesc(Long rvrMerchantNo,String rvrVoucherCode,String rvrLoyaltyId,Date startDate,Date endDate);
    public List<RedemptionVoucher> findByRvrMerchantNoAndRvrLoyaltyIdAndCreatedAtBetweenOrderByRvrIdDesc(Long rvrMerchantNo,String rvrLoyaltyId,Date statDate,Date endDate);
    public List<RedemptionVoucher> findByRvrMerchantNoAndRvrProductCodeLikeAndRvrLoyaltyIdAndCreatedAtBetweenOrderByRvrIdDesc(Long rvrMerchantNo,String rvrProductCode,String rvrLoyaltyId,Date startDate,Date endDate);

    public RedemptionVoucher findByRvrUniqueBatchId(String rvrUniqueBatchId);
    public List<RedemptionVoucher> findByRvrIdIn(List<Long> rvrId);

    public List<RedemptionVoucher> findByRvrUniqueBatchIdAndRvrMerchantNo(String rvrUniqueBatchId,Long rvrMerchantNo);



}

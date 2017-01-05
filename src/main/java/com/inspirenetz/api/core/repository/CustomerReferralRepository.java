package com.inspirenetz.api.core.repository;


import com.inspirenetz.api.core.domain.CustomerReferral;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by fayiz on 27/4/15.
 */
public interface CustomerReferralRepository extends  BaseRepository<CustomerReferral,Long> {

    public CustomerReferral findByCsrId(Long csrId);
    public Page<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdOrderByCsrRefTimeStampDesc(Long csrMerchantNo, String csrLoyaltyId,Pageable pageable);
    public List<CustomerReferral> findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc(Long csrMerchantNo, String csrRefMobile,Integer csrRefStatus);
    public CustomerReferral findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobile(Long csrMerchantNo, String csrLoyaltyId,String csrRefMobile);
    public Page<CustomerReferral> findByCsrMerchantNoAndCsrRefMobileLike(Long csrMerchantNo, String csrRefMobile,Pageable pageable);
    public Page<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdLike(Long csrMerchantNo, String csrLoyaltyId,Pageable pageable);
    public Page<CustomerReferral> findByCsrMerchantNoAndCsrUserNoAndCsrLoyaltyIdLike(Long csrMerchantNo, Long csrUserNo, String csrLoyaltyId,Pageable pageable);
    public Page<CustomerReferral> findByCsrMerchantNoAndCsrUserNoAndCsrRefMobileLike(Long csrMerchantNo, Long usrUserNo,String csrRefMobile,Pageable pageable);
    public Page<CustomerReferral> findByCsrMerchantNo(Long csrMerchantNo,Pageable pageable);
    public Page<CustomerReferral> findByCsrMerchantNoAndCsrUserNo(Long csrMerchantNo,Long csrUserNo,Pageable pageable);
    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdOrderByCsrRefTimeStampDesc(Long csrMerchantNo, String csrLoyaltyId);
    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefNameLike(Long csrMerchantNo, String csrLoyaltyId,String csrRefName);
    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobileLike(Long csrMerchantNo,String csrLoyaltyId, String csrRefMobile);
    public CustomerReferral findByCsrMerchantNoAndCsrRefMobile(Long csrMerchantNo,String csrRefMobile);
    public List<CustomerReferral>  findByCsrMerchantNoAndCsrRefMobileOrderByCsrRefTimeStampAsc(Long csrMerchantNo,String csrRefMobile);




    @Query("select CR from CustomerReferral CR where CR.csrMerchantNo = ?1 and CR.csrLoyaltyId =?2 and CR.csrRefTimeStamp between ?3 and?4 and  CR.csrRefStatus=2 ")
    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefTimeStampBetween(Long csrMerchantNo,String csrLoyaltyId,Timestamp fromDate,Timestamp toDate);


    public CustomerReferral findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobileAndCsrRefStatusNot(Long csrMerchantNo, String csrLoyaltyId,String csrRefMobile,Integer csrRefStatus);

    public List<CustomerReferral> findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusNotOrderByCsrRefTimeStampAsc(Long csrMerchantNo, String csrRefMobile,Integer csrRefStatus);

    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdAndCsrEarnedStatus(Long csrMerchantNo, String csrRefMobile,boolean earnedStatus);



}

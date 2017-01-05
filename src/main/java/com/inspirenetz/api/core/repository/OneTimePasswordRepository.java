package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.OneTimePassword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface OneTimePasswordRepository extends  BaseRepository<OneTimePassword,Long> {


    public OneTimePassword findByOtpId(Long otpId);

    @Query("select O from OneTimePassword O where O.otpMerchantNo = ?1 and O.otpCustomerNo = ?2 and O.otpType = ?3 order by O.otpCreateTimestamp DESC ")
    public List<OneTimePassword> getOTPList(Long otpMerchantNo, Long otpCustomerNo,Integer otpType);

    public  OneTimePassword findByOtpCodeAndOtpMerchantNo(String otpCode,Long merchantNo);

    public  OneTimePassword findByOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(Integer otpRefType,String otpReference,Integer otpType);

    public  List<OneTimePassword> findByOtpMerchantNoAndOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(Long otpMerchantNo,Integer otpRefType,String otpReference,Integer otpType);



}

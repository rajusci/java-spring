package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.OneTimePassword;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public interface OneTimePasswordService extends BaseService<OneTimePassword> {

    public OneTimePassword findByOtpId(Long otpId);
    public List<OneTimePassword> getOTPListForType(Long otpMerchantNo, Long otpCustomerNo,Integer otpType);
    //public Integer validateOTP(Long otpMerchantNo, Long otpCustomerNo, Integer otpType, String otpCode);

    public OneTimePassword saveOneTimePassword(OneTimePassword oneTimePassword);
    public boolean deleteOneTimePassword(Long otpId);

    String generateOTP(Long merchantNo,Long customerNo,Integer otpType);

    String generateOTP(Long merchantNo,String otpReference, Integer otpRefType,Integer otpType);

    boolean generateOTPCompatible(Long merchantNo,String loyaltyId,Integer otpType) throws InspireNetzException;
    public OneTimePassword findByOtpCode(Long merchantNo,String otpCode);

    public OneTimePassword findByOtpMerchantNoAndOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(Long otpMerchantNo,Integer otpRefType,String otpReference,Integer otpType);

    boolean generateOTPGeneric(Long merchantNo,Integer otpRefType,String otpReference,Integer otpType)throws InspireNetzException;

    public Integer validateOTPGeneric(Long otpMerchantNo, Integer otpRefType,String otpReference, Integer otpType, String otpCode);

    boolean generateOTPForPartnerRequest(Long merchantNo, String mobile, Integer otpType) throws InspireNetzException;
}

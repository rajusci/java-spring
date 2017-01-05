package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.domain.Coupon;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CouponService extends BaseService<Coupon> {

    public Page<Coupon> findByCpnMerchantNo(Long cpnMerchantNo, Pageable pageable);
    public Coupon findByCpnCouponId(Long cpnCouponId);
    public Coupon findByCpnMerchantNoAndCpnCouponCode(Long cpnMerchantNo, String cpnCode);
    public Coupon findByCpnMerchantNoAndCpnCouponName(Long cpnMerchantNo,String cpnCouponName);
    public Page<Coupon> findByCpnMerchantNoAndCpnCouponNameLike(Long cpnMerchantNo, String cpnName, Pageable pageable);
    public double calculateCouponValue(Coupon coupon,Purchase purchase);
    public List<Coupon> getCustomerCoupons(Customer customer);
    public boolean isCouponGeneralRulesValid(Coupon coupon, Purchase purchase);
    public boolean validateCoupon(Coupon coupon, Purchase purchase,List<PurchaseSKU> purchaseSKUList);
    public double evaluateCoupon(Coupon coupon,Purchase purchase, List<PurchaseSKU> purchaseSKUList );

    public boolean isDuplicateCouponExisting(Coupon coupon);
    public boolean isCouponCodeValid(Coupon coupon);


    public Coupon saveCoupon(Coupon coupon) throws InspireNetzException;
    public boolean deleteCoupon(Long cpnId) throws InspireNetzException;

    public Coupon validateAndSaveCoupon(Coupon coupon) throws InspireNetzException;
    public boolean validateAndDeleteCoupon(Long cpnId) throws InspireNetzException;

    List<Coupon> getCustomerCouponsCompatible(String loyaltyId , Long merchantNo) throws InspireNetzException;

    List<Map<String ,String >> getCouponsCompatible(String loyaltyId, Long merchantNo) throws InspireNetzException;

    public Page<Coupon> getCouponsForUser(String usrLoginId , Long merchantNo) throws InspireNetzException;
}

package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.CouponCodeType;
import com.inspirenetz.api.core.dictionary.CouponValueType;
import com.inspirenetz.api.core.domain.Coupon;
import com.inspirenetz.api.test.core.builder.CouponBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 17/6/14.
 */
public class CouponFixture {

    public static Coupon standardCoupon() {

        Coupon coupon = CouponBuilder.aCoupon()
                .withCpnMerchantNo(1L)
                .withCpnCouponName("Test Coupon Name")
                .withCpnPromoName("Test Promotion Name")
                .withCpnCouponText("Test Coupon Text")
                .withCpnCouponCodeType(CouponCodeType.FIXED)
                .withCpnCouponCode("CPN10001")
                .withCpnValueType(CouponValueType.AMOUNT)
                .withCpnValue(100.0)
                .withCpnExpiryDt(DBUtils.covertToSqlDate("2014-06-31"))
                .build();

        return coupon;

    }



    public static Coupon updatedStandardCoupon(Coupon coupon) {

        coupon.setCpnCouponName("Test Coupon Name 2");
        return coupon;

    }



    public static Set<Coupon> standardCoupons() {

        Set<Coupon> couponSet = new HashSet<>(0);


        Coupon coupon1 = CouponBuilder.aCoupon()
                .withCpnMerchantNo(1L)
                .withCpnCouponName("Test Coupon Name")
                .withCpnPromoName("Test Promotion Name")
                .withCpnCouponText("Test Coupon Text")
                .withCpnCouponCodeType(CouponCodeType.FIXED)
                .withCpnCouponCode("CPN10001")
                .withCpnValueType(CouponValueType.AMOUNT)
                .withCpnValue(100.0)
                .withCpnExpiryDt(DBUtils.covertToSqlDate("2014-06-31"))
                .build();


        couponSet.add(coupon1);



        Coupon coupon2 = CouponBuilder.aCoupon()
                .withCpnMerchantNo(1L)
                .withCpnCouponName("Test Coupon Name 2")
                .withCpnPromoName("Test Promotion Name")
                .withCpnCouponText("Test Coupon Text")
                .withCpnCouponCodeType(CouponCodeType.RANGE)
                .withCpnCouponCodeFrom("CPN10001")
                .withCpnCouponCodeTo("CPN20001")
                .withCpnValueType(CouponValueType.AMOUNT)
                .withCpnValue(100.0)
                .withCpnExpiryDt(DBUtils.covertToSqlDate("2014-06-31"))
                .build();


        couponSet.add(coupon2);



        return couponSet;

    }
}

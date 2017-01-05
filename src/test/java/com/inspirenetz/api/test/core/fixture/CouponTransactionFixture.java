package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.CouponTransaction;
import com.inspirenetz.api.test.core.builder.CouponTransactionBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 18/6/14.
 */
public class CouponTransactionFixture {

    public static CouponTransaction standardCouponTransaction() {

        CouponTransaction couponTransaction = CouponTransactionBuilder.aCouponTransaction()
                .withCptCouponCode("CPN10001")
                .withCptMerchantNo(1L)
                .withCptLoyaltyId("9999888877776661")
                .withCptPurchaseId(1L)
                .withCptCouponCount(1)
                .build();

        return couponTransaction;

    }



    public static CouponTransaction updatedStandardCouponTransaction(CouponTransaction couponTransaction) {

        couponTransaction.setCptCouponCount(2);;

        return couponTransaction;
    }


    public static Set<CouponTransaction> standardCouponTransactions() {

        Set<CouponTransaction> couponTransactionSet =  new HashSet<>(0);

        CouponTransaction couponTransaction = CouponTransactionBuilder.aCouponTransaction()
                .withCptCouponCode("CPN10001")
                .withCptMerchantNo(1L)
                .withCptLoyaltyId("9999888877776661")
                .withCptPurchaseId(1L)
                .withCptCouponCount(1)
                .build();

        couponTransactionSet.add(couponTransaction);


        return couponTransactionSet;


    }

}

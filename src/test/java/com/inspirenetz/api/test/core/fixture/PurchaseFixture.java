package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.PurchaseStatus;
import com.inspirenetz.api.core.dictionary.SaleType;
import com.inspirenetz.api.core.domain.Purchase;
import com.inspirenetz.api.test.core.builder.PurchaseBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 6/5/14.
 */
public class PurchaseFixture {

    public static Purchase standardPurchase() {

        Purchase purchase = PurchaseBuilder.aPurchase()
                .withPrcMerchantNo(1L)
                .withPrcType(SaleType.STANDARD_PURCHASE)
                .withPrcDate(DBUtils.covertToSqlDate("2014-05-03"))
                .withPrcTime(DBUtils.convertToSqlTime("12:00:03"))
                .withPrcLoyaltyId("9999888877776661")
                .withPrcAmount(100.0)
                .withPrcPaymentReference("c/223093")
                .withPrcStatus(PurchaseStatus.PURCHASE_LOG) // to avoid computation due to trigger
                .build();


        return purchase;


    }


    public static Purchase updatedStandardPurchase(Purchase purchase) {

        purchase.setPrcAmount(200.00);
        purchase.setPrcPaymentReference("s/9203948");

        // Return the purchsae object
        return purchase;

    }



    public static Set<Purchase> standardPurchases() {

        Set<Purchase> purchases = new HashSet<>();

        Purchase purchase1 = PurchaseBuilder.aPurchase()
                .withPrcMerchantNo(1L)
                .withPrcType(SaleType.STANDARD_PURCHASE)
                .withPrcDate(DBUtils.covertToSqlDate("2014-05-03"))
                .withPrcTime(DBUtils.convertToSqlTime("12:00:03"))
                .withPrcLoyaltyId("9999888877776661")
                .withPrcAmount(100.0)
                .withPrcPaymentReference("c/223093")
                .withPrcStatus(PurchaseStatus.PURCHASE_LOG) // to avoid computation due to trigger
                .build();

        purchases.add(purchase1);



        Purchase purchase2 = PurchaseBuilder.aPurchase()
                .withPrcMerchantNo(1L)
                .withPrcType(SaleType.STANDARD_PURCHASE)
                .withPrcDate(DBUtils.covertToSqlDate("2014-05-03"))
                .withPrcTime(DBUtils.convertToSqlTime("12:00:03"))
                .withPrcLoyaltyId("9999888877776661")
                .withPrcAmount(1300.0)
                .withPrcPaymentReference("c/22309399")
                .withPrcStatus(PurchaseStatus.PURCHASE_LOG) // to avoid computation due to trigger
                .build();

        purchases.add(purchase2);



        Purchase purchase3 = PurchaseBuilder.aPurchase()
                .withPrcMerchantNo(1L)
                .withPrcType(SaleType.STANDARD_PURCHASE)
                .withPrcDate(DBUtils.covertToSqlDate("2014-05-03"))
                .withPrcTime(DBUtils.convertToSqlTime("12:00:03"))
                .withPrcLoyaltyId("9999888877776662")
                .withPrcAmount(1300.0)
                .withPrcPaymentReference("c/22309539")
                .withPrcStatus(PurchaseStatus.PURCHASE_LOG) // to avoid computation due to trigger
                .build();

        purchases.add(purchase3);



        return purchases;


    }

}

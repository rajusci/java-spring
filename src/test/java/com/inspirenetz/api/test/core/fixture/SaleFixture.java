package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.PurchaseStatus;
import com.inspirenetz.api.core.dictionary.SaleType;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.test.core.builder.SaleBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 7/8/14.
 */
public class SaleFixture {

    public static Sale standardSale() {

        Sale purchase = SaleBuilder.aSale()
                .withSalMerchantNo(1L)
                .withSalDate(DBUtils.covertToSqlDate("2015-03-05"))
                .withSalTime(DBUtils.convertToSqlTime("12:00:03"))
                .withSalLoyaltyId("9999888877776661")
                .withSalCurrency(356)
                .withSalAmount(100.0)
                .withSalPaymentReference("c/223093")
                .withSalStatus(PurchaseStatus.PURCHASE_LOG) // to avoid computation due to trigger
                .withSalLocation(56L)
                .withCreatedBy("56")
                .build();


        return purchase;


    }


    public static Sale updatedStandardSale(Sale purchase) {

        purchase.setSalAmount(200.00);
        purchase.setSalPaymentReference("s/9203948");

        // Return the purchsae object
        return purchase;

    }



    public static Set<Sale> standardSales() {

        Set<Sale> purchases = new HashSet<>();

        Sale purchase1 = SaleBuilder.aSale()
                .withSalMerchantNo(1L)
                .withSalType(SaleType.STANDARD_PURCHASE)
                .withSalDate(DBUtils.covertToSqlDate("2014-05-03"))
                .withSalTime(DBUtils.convertToSqlTime("12:00:03"))
                .withSalLoyaltyId("9999888877776661")
                .withSalAmount(100.0)
                .withSalPaymentReference("c/223093")
                .withSalStatus(PurchaseStatus.PURCHASE_LOG) // to avoid computation due to trigger
                .build();

        purchases.add(purchase1);



        Sale purchase2 = SaleBuilder.aSale()
                .withSalMerchantNo(1L)
                .withSalType(SaleType.STANDARD_PURCHASE)
                .withSalDate(DBUtils.covertToSqlDate("2014-05-03"))
                .withSalTime(DBUtils.convertToSqlTime("12:00:03"))
                .withSalLoyaltyId("9999888877776661")
                .withSalAmount(1300.0)
                .withSalPaymentReference("c/22309399")
                .withSalStatus(PurchaseStatus.PURCHASE_LOG) // to avoid computation due to trigger
                .build();

        purchases.add(purchase2);



        Sale purchase3 = SaleBuilder.aSale()
                .withSalMerchantNo(1L)
                .withSalType(SaleType.STANDARD_PURCHASE)
                .withSalDate(DBUtils.covertToSqlDate("2014-05-03"))
                .withSalTime(DBUtils.convertToSqlTime("12:00:03"))
                .withSalLoyaltyId("9999888877776662")
                .withSalAmount(1300.0)
                .withSalPaymentReference("c/22309539")
                .withSalStatus(PurchaseStatus.PURCHASE_LOG) // to avoid computation due to trigger
                .build();

        purchases.add(purchase3);



        return purchases;


    }

}

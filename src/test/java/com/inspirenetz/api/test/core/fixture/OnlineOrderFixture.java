package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.OnlineOrder;
import com.inspirenetz.api.test.core.builder.OnlineOrderBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.security.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 30/7/14.
 */
public class OnlineOrderFixture {

    public static OnlineOrder standardOnlineOrder() {

java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());

        OnlineOrder onlineOrder = OnlineOrderBuilder.anOnlineOrder()
                .withOrdMerchantNo(1L)
                .withOrdProductCode("PRDTEST1001")
                .withOrdLoyaltyId("9999888877776661")
                .withOrdOrderLocation(0L)
                .withOrdOrderSlot(1L)
                .withOrdDeliveryInd(IndicatorStatus.NO)
                .withOrdUniqueBatchTrackingId(1000001)
                .withOrdTimestamp(timestamp)
                .build();


        return onlineOrder;

    }



    public static OnlineOrder updatedStandardOnlineOrder(OnlineOrder onlineOrder) {

        onlineOrder.setOrdRef("MY REFERENCE");

        return onlineOrder;

    }



    public static Set<OnlineOrder> standardOnlineOrders() {

        Set<OnlineOrder> onlineOrderSet = new HashSet<>(0);

        OnlineOrder onlineOrder1 = OnlineOrderBuilder.anOnlineOrder()
                .withOrdMerchantNo(1L)
                .withOrdProductCode("PRDTEST1001")
                .withOrdLoyaltyId("9999888877776661")
                .withOrdOrderLocation(0L)
                .withOrdOrderSlot(1L)
                .withOrdDeliveryInd(IndicatorStatus.NO)
                .withOrdUniqueBatchTrackingId(1000001)
                .build();

        onlineOrderSet.add(onlineOrder1);


        OnlineOrder onlineOrder2 = OnlineOrderBuilder.anOnlineOrder()
                .withOrdMerchantNo(1L)
                .withOrdProductCode("PRDTEST1002")
                .withOrdLoyaltyId("9999888877776661")
                .withOrdOrderLocation(0L)
                .withOrdOrderSlot(1L)
                .withOrdDeliveryInd(IndicatorStatus.NO)
                .withOrdUniqueBatchTrackingId(1000001)
                .build();

        onlineOrderSet.add(onlineOrder2);


        return onlineOrderSet;

    }
}

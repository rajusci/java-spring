package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.OrderSlotSession;
import com.inspirenetz.api.core.dictionary.OrderSlotType;
import com.inspirenetz.api.core.domain.OrderSlot;
import com.inspirenetz.api.test.core.builder.OrderSlotBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 30/7/14.
 */
public class OrderSlotFixture {

    public static OrderSlot standardOrderSlot() {

        OrderSlot orderSlot = OrderSlotBuilder.anOrderSlot()
                .withOrtMerchantNo(1L)
                .withOrtLocation(1L)
                .withOrtType(OrderSlotType.ORT_TYPE_PICKUP)
                .withOrtSession(OrderSlotSession.ORT_SESSION_BREAKFAST)
                .withOrtDisplayTitle("06:00 am - 06:30 am")
                .withOrtStartingTime(DBUtils.convertToSqlTime("06:00:00"))
                .build();


        return orderSlot;

    }



    public  static OrderSlot standardUpdatedOrderSlot(OrderSlot orderSlot) {

        orderSlot.setOrtDisplayTitle("06:30 am - 07:00 am");

        return orderSlot;

    }



    public static Set<OrderSlot> standardOrderSlots() {

        Set<OrderSlot> orderSlotSet = new HashSet<>(0);

        OrderSlot orderSlot1 = OrderSlotBuilder.anOrderSlot()
                .withOrtMerchantNo(1L)
                .withOrtLocation(1L)
                .withOrtType(OrderSlotType.ORT_TYPE_PICKUP)
                .withOrtSession(OrderSlotSession.ORT_SESSION_BREAKFAST)
                .withOrtDisplayTitle("06:00 am - 06:30 am")
                .withOrtStartingTime(DBUtils.convertToSqlTime("06:00:00"))
                .build();


        orderSlotSet.add(orderSlot1);



        OrderSlot orderSlot2 = OrderSlotBuilder.anOrderSlot()
                .withOrtMerchantNo(1L)
                .withOrtLocation(1L)
                .withOrtType(OrderSlotType.ORT_TYPE_PICKUP)
                .withOrtSession(OrderSlotSession.ORT_SESSION_LUNCH)
                .withOrtDisplayTitle("12:00 pm - 12:30 am")
                .withOrtStartingTime(DBUtils.convertToSqlTime("12:00:00"))
                .build();

        orderSlotSet.add(orderSlot2);


        return  orderSlotSet;

    }
}

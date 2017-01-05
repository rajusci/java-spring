package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.PromotionalEvent;
import com.inspirenetz.api.test.core.builder.PromotionalEventBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneeshci on 29/9/14.
 */
public class PromotionalEventFixture {

    public static PromotionalEvent standardPromotionalEvent(){

        PromotionalEvent promotionalEvent = PromotionalEventBuilder.aPromotionalEvent()

                .withPreEventCode("PRE1001")
                .withPreEventName("EVENT A")
                .withPreLocation(1L)
                .withPreMerchantNo(1L)
                .withPreEndDate(DBUtils.covertToSqlDate("2014-12-31"))
                .withPreStartDate(DBUtils.covertToSqlDate("2014-6-31"))
                .build();


        return promotionalEvent;


    }


    public static PromotionalEvent updatedStandPromotionalEvent(PromotionalEvent promotionalEvent) {

        promotionalEvent.setPreEventName("Event C");


        return promotionalEvent;

    }


    public static Set<PromotionalEvent> standardPromotionalEvents() {

        Set<PromotionalEvent> promotionalEvents = new HashSet<PromotionalEvent>(0);

        PromotionalEvent promotionalEvent  = PromotionalEventBuilder.aPromotionalEvent()
                .withPreEventCode("PRE1001")
                .withPreEventName("EVENT A")
                .withPreLocation(1L)
                .withPreMerchantNo(1L)
                .withPreEndDate(DBUtils.covertToSqlDate("2014-12-31"))
                .withPreStartDate(DBUtils.covertToSqlDate("2014-6-31"))
                .build();

        promotionalEvents.add(promotionalEvent);

        PromotionalEvent promotionalEvent1 = PromotionalEventBuilder.aPromotionalEvent()
                .withPreEventCode("PRE1002")
                .withPreEventName("EVENT B")
                .withPreLocation(1L)
                .withPreMerchantNo(1L)
                .withPreEndDate(DBUtils.covertToSqlDate("2014-12-31"))
                .withPreStartDate(DBUtils.covertToSqlDate("2014-6-31"))
                .build();

        promotionalEvents.add(promotionalEvent1);



        return promotionalEvents;



    }
}

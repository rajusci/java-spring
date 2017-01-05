package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.CardNumberInfo;
import com.inspirenetz.api.test.core.builder.CardNumberInfoBuilder;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameen on 20/10/15.
 */
public class CardNumberInfoFixture {

    public static CardNumberInfo standardCardNumberInfo() {

        CardNumberInfo cardNumberInfo = CardNumberInfoBuilder.aCardNumberInfo()
                .withCniMerchantNo(1L)
                .withCniCardNumber("123456")
                .withCniCardType(1L)
                .withCniBatchId(1L)
                .withCniPin("123")
                .withCniCardStatus(IndicatorStatus.NO)
                .build();


        return cardNumberInfo;


    }

    public static Set<CardNumberInfo> standardCardNumberInfos() {

        Set<CardNumberInfo> cardNumberInfoSet = new HashSet<>(0);

        CardNumberInfo cardNumberInfo1 = CardNumberInfoBuilder.aCardNumberInfo()
                .withCniMerchantNo(1L)
                .withCniCardNumber("123456")
                .withCniCardType(1L)
                .withCniBatchId(1L)
                .withCniPin("123")
                .build();



        cardNumberInfoSet.add(cardNumberInfo1);



        CardNumberInfo cardNumberInfo2 = CardNumberInfoBuilder.aCardNumberInfo()
                .withCniMerchantNo(1L)
                .withCniCardNumber("1234567")
                .withCniCardType(1L)
                .withCniBatchId(1L)
                .withCniPin("124")
                .build();
        cardNumberInfoSet.add(cardNumberInfo2);



        return cardNumberInfoSet;


    }
}

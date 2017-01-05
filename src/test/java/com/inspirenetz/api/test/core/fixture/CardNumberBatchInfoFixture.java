package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.BulkUploadBatchInfoStatus;
import com.inspirenetz.api.core.domain.BulkUploadBatchInfo;
import com.inspirenetz.api.core.domain.CardNumberBatchInfo;
import com.inspirenetz.api.test.core.builder.CardNumberBatchInfoBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameen on 20/10/15.
 */
public class CardNumberBatchInfoFixture {

    public static CardNumberBatchInfo standardCardNumberBatchInfo() {

        CardNumberBatchInfo cardNumberBatchInfo = CardNumberBatchInfoBuilder.aCardNumberBatchInfo()
                .withCnbMerchantNo(1L)
                .withCnbName("Batch1")
                .withCnbDate(new java.sql.Date(new Date().getTime()))
                .withCnbTime(new Time(new java.util.Date().getTime()))
                .withCnbProcessStatus(BulkUploadBatchInfoStatus.PROCESSING)
                .build();


        return cardNumberBatchInfo;


    }



    public static CardNumberBatchInfo updatedStandardCardNumberBatchInfo(CardNumberBatchInfo cardNumberBatchInfo) {

        // update the balance
        cardNumberBatchInfo.setCnbProcessStatus(BulkUploadBatchInfoStatus.COMPLETED);

        return cardNumberBatchInfo;


    }



    public static Set<CardNumberBatchInfo> standardCardNumberBatchInfos() {

        Set<CardNumberBatchInfo> cardNumberBatchInfoSet = new HashSet<>(0);

        CardNumberBatchInfo cardNumberBatchInfo1 = CardNumberBatchInfoBuilder.aCardNumberBatchInfo()
                .withCnbMerchantNo(1L)
                .withCnbName("Batch1")
                .withCnbDate(new java.sql.Date(new Date().getTime()))
                .withCnbTime(new Time(new java.util.Date().getTime()))
                .withCnbProcessStatus(BulkUploadBatchInfoStatus.PROCESSING)
                .build();


        cardNumberBatchInfoSet.add(cardNumberBatchInfo1);



        CardNumberBatchInfo cardNumberBatchInfo2 = CardNumberBatchInfoBuilder.aCardNumberBatchInfo()
                .withCnbMerchantNo(1L)
                .withCnbName("Batch2")
                .withCnbDate(new java.sql.Date(new Date().getTime()))
                .withCnbTime(new Time(new java.util.Date().getTime()))
                .withCnbProcessStatus(BulkUploadBatchInfoStatus.PROCESSING)
                .build();
        cardNumberBatchInfoSet.add(cardNumberBatchInfo2);



        return cardNumberBatchInfoSet;


    }
}

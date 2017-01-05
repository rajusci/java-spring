package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.CardMasterStatus;
import com.inspirenetz.api.core.domain.CardMaster;
import com.inspirenetz.api.test.core.builder.CardMasterBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 23/7/14.
 */
public class CardMasterFixture {

    public static CardMaster standardCardMaster() {

        CardMaster cardMaster = CardMasterBuilder.aCardMaster()
                .withCrmMerchantNo(1L)
                .withCrmType(1L)
                .withCrmCardStatus(CardMasterStatus.ACTIVE)
                .withCrmCardBalance(1000.0)
                .withCrmPromoBalance(100.0)
                .withCrmCardNo("1001")
                .withCrmLoyaltyId("")
                .withCrmCardHolderName("Test Card")
                .withCrmMobile("9999888871")
                .withCrmExpiryDate(DBUtils.covertToSqlDate("9999-12-31"))
                .build();


        return cardMaster;


    }



    public static CardMaster updatedStandardCardMaster(CardMaster cardMaster) {

        // update the balance
        cardMaster.setCrmCardBalance(90.0);

        return cardMaster;


    }



    public static Set<CardMaster> standardCardMasters() {

        Set<CardMaster> cardMasterSet = new HashSet<>(0);

        CardMaster cardMaster1 = CardMasterBuilder.aCardMaster()
                .withCrmMerchantNo(1L)
                .withCrmType(1L)
                .withCrmCardStatus(CardMasterStatus.ACTIVE)
                .withCrmCardBalance(100.0)
                .withCrmCardNo("1001")
                .withCrmLoyaltyId("1001")
                .withCrmCardHolderName("Test Card")
                .withCrmMobile("9999888871")
                .build();


        cardMasterSet.add(cardMaster1);



        CardMaster cardMaster2 = CardMasterBuilder.aCardMaster()
                .withCrmMerchantNo(1L)
                .withCrmType(1L)
                .withCrmCardStatus(CardMasterStatus.ACTIVE)
                .withCrmCardBalance(100.0)
                .withCrmCardNo("1002")
                .withCrmLoyaltyId("1002")
                .withCrmCardHolderName("Test Card2")
                .withCrmMobile("9999888872")
                .build();

        cardMasterSet.add(cardMaster2);



        return cardMasterSet;


    }
}

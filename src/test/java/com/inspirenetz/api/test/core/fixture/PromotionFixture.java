package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.MessageSpielChannel;
import com.inspirenetz.api.core.dictionary.PromotionExpiryOption;
import com.inspirenetz.api.core.dictionary.PromotionTargetOption;
import com.inspirenetz.api.core.dictionary.PromotionUserAction;
import com.inspirenetz.api.core.domain.Promotion;
import com.inspirenetz.api.test.core.builder.PromotionBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 17/6/14.
 */
public class PromotionFixture {

    public static Promotion standardPromotion() {

        Promotion promotion = PromotionBuilder.aPromotion()
                .withPrmMerchantNo(1L)
                .withPrmName("Test Promotion")
                .withPrmShortDescription("This is a test promo")
                .withPrmLongDescription("This is a test promo")
                .withPrmExpiryDate(DBUtils.covertToSqlDate("2015-08-31"))
                .withPrmUserAction(Integer.toString(PromotionUserAction.CLAIM))
                .withPrmTargetedOption(PromotionTargetOption.CUSTOMER_SEGMENT)
                .withPrmBroadcastOption(MessageSpielChannel.SMS+":"+MessageSpielChannel.EMAIL)
                .withPrmSmsContent("TEST")
                .withPrmEmailSubject("TEST")
                .withPrmEmailContent("TEST")
                .build();

        return promotion;

    }



    public static Promotion updatedStandardPromotion(Promotion promotion) {

        promotion.setPrmName("Test Promotion updated");
        promotion.setPrmShortDescription("Test promo desc updated");

        return promotion;

    }


    public static Set<Promotion> standardPromotions() {

        Set<Promotion> promotionSet = new HashSet<>(0);

        Promotion promotion1 = PromotionBuilder.aPromotion()
                .withPrmMerchantNo(1L)
                .withPrmName("Test Promotion")
                .withPrmShortDescription("This is a test promo")
                .withPrmLongDescription("This is a test promo")
                .withPrmExpiryDate(DBUtils.covertToSqlDate("2014-06-18"))
                .withPrmUserAction(Integer.toString(PromotionUserAction.CLAIM))
                .build();

        promotionSet.add(promotion1);


        Promotion promotion2 = PromotionBuilder.aPromotion()
                .withPrmMerchantNo(1L)
                .withPrmName("Test Promotion2")
                .withPrmShortDescription("This is a test promo2")
                .withPrmLongDescription("This is a test promo2")
                .withPrmExpiryOption(PromotionExpiryOption.NUM_RESPONSES)
                .withPrmMaxResponses(100)
                .withPrmUserAction(Integer.toString(PromotionUserAction.CLAIM))
                .build();

        promotionSet.add(promotion2);


        return promotionSet;

    }
}

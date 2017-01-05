package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.CardTypeActivateOption;
import com.inspirenetz.api.core.dictionary.CardTypeExpiryOption;
import com.inspirenetz.api.core.dictionary.CardTypeType;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.test.core.builder.CardTypeBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 22/7/14.
 */
public class CardTypeFixture {

    public static CardType standardCardType() {

        CardType cardType = CardTypeBuilder.aCardType()
                .withCrtMerchantNo(1L)
                .withCrtName("TESTVal")
                .withCrtType(CardTypeType.RECHARGEBLE)
                .withCrtFixedValue(10.0)
                .withCrtDeductIncentiveAmount(10.0)
                .withCrtCardNoRangeFrom("1000")
                .withCrtCardNoRangeTo("2000")
                .withCrtExpiryOption(CardTypeExpiryOption.EXPIRY_DATE)
                .withCrtExpiryDate(DBUtils.covertToSqlDate("9999-12-31"))
                .withCrtActivateOption(CardTypeActivateOption.CRT_ACTIVITY_DAYS)
                .withCrtActivateDays(0)
                .build();

        return cardType;

    }



    public static CardType standardUpdatedCardType(CardType cardType) {

        // Set the value
        cardType.setCrtFixedValue(20.0);

        return cardType;

    }




    public static Set<CardType> standardCardTypes() {

        Set<CardType> cardTypeSet = new HashSet<>(0);

        CardType cardType1 = CardTypeBuilder.aCardType()
                .withCrtMerchantNo(1L)
                .withCrtName("TEST val")
                .withCrtType(CardTypeType.FIXED_VALUE)
                .withCrtFixedValue(10.0)
                .withCrtCardNoRangeFrom("1000")
                .withCrtCardNoRangeTo("2000")
                .withCrtActivateOption(CardTypeActivateOption.CRT_ACTIVITY_DAYS)
                .withCrtActivateDays(0)
                .withCrtExpiryOption(CardTypeExpiryOption.EXPIRY_DATE)
                .withCrtExpiryDate(DBUtils.covertToSqlDate("9999-12-31"))
                .build();


        cardTypeSet.add(cardType1);




        CardType cardType2 = CardTypeBuilder.aCardType()
                .withCrtMerchantNo(1L)
                .withCrtName("TEST RECHARBEBLE")
                .withCrtType(CardTypeType.RECHARGEBLE)
                .withCrtMinTopupValue(100.0)
                .withCrtMaxValue(2000.0)
                .withCrtCardNoRangeFrom("3000")
                .withCrtCardNoRangeTo("4000")
                .withCrtExpiryOption(CardTypeExpiryOption.EXPIRY_DATE)
                .withCrtExpiryDate(DBUtils.covertToSqlDate("9999-12-31"))
                .build();


        cardTypeSet.add(cardType2);


        return cardTypeSet;

    }

}

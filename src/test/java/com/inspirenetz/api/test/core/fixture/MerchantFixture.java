package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.MerchantLoyaltyIdType;
import com.inspirenetz.api.core.dictionary.MerchantSignupType;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.test.core.builder.MerchantBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 14/5/14.
 */
public class MerchantFixture {

    public static Merchant standardMerchant() {

        Merchant merchant = MerchantBuilder.aMerchant()
                .withMerMerchantName("Standard Merchant")
                .withMerUrlName("standard")
                .withMerAddress1("#20, Lancherster street")
                .withMerAddress2("2nd Cross")
                .withMerAddress3("Bangalore")
                .withMerCity("Bangalore")
                .withMerState("Karnataka")
                .withMerPostCode("560017")
                .withMerContactName("Sandeep")
                .withMerContactEmail("sandeep1@inspirenetz.com")
                .withMerPhoneNo("9538828853")
                .withMerEmail("merchant@inspirenetz.com")
                .withMerActivationDate(DBUtils.covertToSqlDate("2014-05-12"))
                .withMerLoyaltyIdType(MerchantLoyaltyIdType.GENERATED)
                .withMerSignupType(MerchantSignupType.INSTORE+"#"+MerchantSignupType.ONLINE)
                .withMerMembershipName("Standard Loyal")
                .build();

        return merchant;


    }


    public static Merchant updatedStandardMerchant(Merchant merchant) {

        merchant.setMerMerchantName("New Merchant");
        merchant.setMerAddress1("#20,Sussex");

        return merchant;

    }



    public static Set<Merchant> standardMerchants() {

        Set<Merchant> merchantSet = new HashSet<>();

        Merchant merchant1 = MerchantBuilder.aMerchant()
                .withMerMerchantName("Standard Merchant")
                .withMerUrlName("standard")
                .withMerAddress1("#20, Lancherster street")
                .withMerAddress2("2nd Cross")
                .withMerAddress3("Bangalore")
                .withMerCity("Bangalore")
                .withMerState("Karnataka")
                .withMerPostCode("560017")
                .withMerContactName("Sandeep")
                .withMerContactEmail("sandeep1@inspirenetz.com")
                .withMerPhoneNo("9538828853")
                .withMerEmail("merchant@inspirenetz.com")
                .withMerActivationDate(DBUtils.covertToSqlDate("2014-05-12"))
                .withMerLoyaltyIdType(MerchantLoyaltyIdType.GENERATED)
                .withMerSignupType(MerchantSignupType.INSTORE+"#"+MerchantSignupType.ONLINE)
                .withMerMembershipName("Standard Loyal")
                .build();

        merchantSet.add(merchant1);


        Merchant merchant2 = MerchantBuilder.aMerchant()
                .withMerMerchantName("New Merchant")
                .withMerUrlName("newmerchant")
                .withMerAddress1("#20, Test street")
                .withMerAddress2("2nd Cross")
                .withMerAddress3("Chennai")
                .withMerCity("Chennai")
                .withMerState("Tamilnadu")
                .withMerPostCode("560017")
                .withMerContactName("Sandeep")
                .withMerContactEmail("sandeep2@inspirenetz.com")
                .withMerPhoneNo("9538828853")
                .withMerEmail("merchant3@inspirenetz.com")
                .withMerActivationDate(DBUtils.covertToSqlDate("2014-05-12"))
                .withMerLoyaltyIdType(MerchantLoyaltyIdType.GENERATED)
                .withMerSignupType(MerchantSignupType.INSTORE+"#"+MerchantSignupType.ONLINE)
                .withMerMembershipName("Standard Loyal")
                .build();

        merchantSet.add(merchant2);


        return merchantSet;

    }
}

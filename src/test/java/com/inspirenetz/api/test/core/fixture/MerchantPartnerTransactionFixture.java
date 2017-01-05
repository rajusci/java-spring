package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.MerchantPartnerTransactionSearchType;
import com.inspirenetz.api.core.domain.MerchantPartnerTransaction;
import com.inspirenetz.api.test.core.builder.MerchantPartnerTransactionBuilder;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by abhi on 14/7/16.
 */
public class MerchantPartnerTransactionFixture {

    public static MerchantPartnerTransaction standardMerchantPartnerTransaction(){

        MerchantPartnerTransaction merchantPartnerTransaction = MerchantPartnerTransactionBuilder.aMerchantPartnerTransaction()
                .withMptId(1l)
                .withMptMerchantNo(1L)
                .withMptPartnerNo(1L)
                .withMptPrice(200.00)
                .withMptProductNo(1000L)
                .withMptQuantity(2)
                .withMptTxnDate((Date.valueOf("2016-07-08")))
                .build();


        return merchantPartnerTransaction;

    }

    public static Set<MerchantPartnerTransaction> standardMerchantPartnerTransactionSet(){

        Set<MerchantPartnerTransaction> merchantPartnerTransactionSet = new HashSet<MerchantPartnerTransaction>();

        MerchantPartnerTransaction merchantPartnerTransaction1 = MerchantPartnerTransactionBuilder.aMerchantPartnerTransaction()
                .withMptId(1l)
                .withMptMerchantNo(1L)
                .withMptPartnerNo(1L)
                .withMptPrice(200.00)
                .withMptProductNo(1000L)
                .withMptQuantity(2)
                .withMptSearchType(MerchantPartnerTransactionSearchType.MERCHANT)
                .build();


        merchantPartnerTransactionSet.add(merchantPartnerTransaction1);



        MerchantPartnerTransaction merchantPartnerTransaction2 = MerchantPartnerTransactionBuilder.aMerchantPartnerTransaction()
                .withMptId(2l)
                .withMptMerchantNo(1L)
                .withMptPartnerNo(1L)
                .withMptPrice(300.00)
                .withMptProductNo(6000L)
                .withMptQuantity(56)
                .build();



        merchantPartnerTransactionSet.add(merchantPartnerTransaction2);



        return merchantPartnerTransactionSet;
    }


}

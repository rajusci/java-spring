package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.RedemptionVoucherStatus;
import com.inspirenetz.api.core.domain.RedemptionVoucher;
import com.inspirenetz.api.test.core.builder.RedemptionVoucherBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 9/9/14.
 */
public class RedemptionVoucherFixture {




    public static RedemptionVoucher standardRedemptionVoucher() {


        RedemptionVoucher redemptionVoucher = RedemptionVoucherBuilder.aRedemptionVoucher()
                .withRvrCustomerNo(100L)
                .withRvrMerchant(104L)
                .withRvrProductCode("PRD1001")
                .withRvrVoucherCode("EXT10001")
                .withRvrStatus(RedemptionVoucherStatus.NEW)
                .withRvrLoyaltyId("9999888888")

                .build();


        return redemptionVoucher;


    }


    public static RedemptionVoucher updatedStandardRedemptionVoucher(RedemptionVoucher redemptionVoucher) {

        redemptionVoucher.setRvrProductCode("PRD1002");
        redemptionVoucher.setRvrStatus(RedemptionVoucherStatus.REDEEMED);

        return redemptionVoucher;

    }


    public static Set<RedemptionVoucher> standardRedemptionVouchers() {

        Set<RedemptionVoucher> redemptionVouchers = new HashSet<RedemptionVoucher>(0);

        RedemptionVoucher voucherA  = RedemptionVoucherBuilder.aRedemptionVoucher()
                .withRvrCustomerNo(100L)
                .withRvrMerchant(100L)
                .withRvrProductCode("PRD1001")
                .withRvrVoucherCode("EXT10001")
                .withRvrStatus(RedemptionVoucherStatus.NEW)
                .build();

        redemptionVouchers.add(voucherA);



        RedemptionVoucher voucherB = RedemptionVoucherBuilder.aRedemptionVoucher()
                .withRvrCustomerNo(1001L)
                .withRvrMerchant(1001L)
                .withRvrProductCode("PRD1002")
                .withRvrVoucherCode("EXT10002")
                .withRvrStatus(RedemptionVoucherStatus.NEW)
                .build();

        redemptionVouchers.add(voucherB);



        return redemptionVouchers;



    }
}

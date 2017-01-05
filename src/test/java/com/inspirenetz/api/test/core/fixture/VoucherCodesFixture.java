package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.VoucherCode;
import com.inspirenetz.api.test.core.builder.VoucherCodeBuilder;

/**
 * Created by alameen on 10/2/15.
 */
public class VoucherCodesFixture {

    public static VoucherCode stVoucherCodes(){

        VoucherCode voucherCode = VoucherCodeBuilder.aVoucherCode()

                .withVocVoucherCode("llllllll")
                .withVocVoucherSource(3L)
                .withVocIndex(1L)
                .withVocMerchantNo(1L)
                .build();


        return voucherCode;


    }


    public static VoucherCode updatedStandRoleAccessRight(VoucherCode voucherCode) {

        voucherCode.setVocVoucherCode("asd");

        return voucherCode;

    }
}

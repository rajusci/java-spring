package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.PaymentStatusStatus;
import com.inspirenetz.api.core.domain.PaymentStatus;
import com.inspirenetz.api.test.core.builder.PaymentStatusBuilder;

/**
 * Created by sandheepgr on 16/8/14.
 */
public class PaymentStatusFixture {

    public static PaymentStatus standardPaymentStatus() {

        PaymentStatus paymentStatus = PaymentStatusBuilder.aPaymentStatus()
                .withPysMerchantNo(1L)
                .withPysLoyaltyId("9999888877776661")
                .withPysInternalRef("100010")
                .withPysAmount(100.00)
                .withPysTranApprovalCode("1")
                .withPysTranReceiptNumber("11111")
                .withPysTranAuthId("1212")
                .withPysTransactionNumber("1212121212")
                .build();


        return paymentStatus;

    }


    public static PaymentStatus updatedStandardPaymentStatus(PaymentStatus paymentStatus ) {

        paymentStatus.setPysCurrentStatus(PaymentStatusStatus.APPROVED);

        return  paymentStatus;

    }


}

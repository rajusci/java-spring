package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.OTPRefType;
import com.inspirenetz.api.core.dictionary.OTPStatus;
import com.inspirenetz.api.core.dictionary.OTPType;
import com.inspirenetz.api.core.domain.CustomerRewardBalance;
import com.inspirenetz.api.core.domain.OneTimePassword;
import com.inspirenetz.api.test.core.builder.CustomerRewardBalanceBuilder;
import com.inspirenetz.api.test.core.builder.OneTimePasswordBuilder;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
public class OneTimePasswordFixture {

    public static OneTimePassword standardOneTimePassword() {

        OneTimePassword oneTimePassword = OneTimePasswordBuilder.anOneTimePassword()
                .withOtpMerchantNo(1L)
                .withOtpCustomerNo(1L)
                .withOtpType(OTPType.CHARGE_CARD_PAYMENT)
                .withOtpCode("123456")
                .withOtpStatus(OTPStatus.NEW)
                .withOtpExpiry(new Timestamp(System.currentTimeMillis()))
                .withOtpRefType(OTPRefType.CUSTOMER)
                .withOtpReference("1")
                .build();

        return oneTimePassword;


    }


    public static OneTimePassword updatedStandardOneTimePassword(OneTimePassword oneTimePassword) {

        oneTimePassword.setOtpType(OTPType.REDEMPTION);
        return oneTimePassword;
    }


    public static Set<OneTimePassword> standardOneTimePasswords() {

        Set<OneTimePassword> oneTimePasswordSet = new HashSet<>();


        OneTimePassword oneTimePassword1 = OneTimePasswordBuilder.anOneTimePassword()
                .withOtpMerchantNo(1L)
                .withOtpCustomerNo(1L)
                .withOtpType(OTPType.CHARGE_CARD_PAYMENT)
                .withOtpCode("123456")
                .withOtpStatus(OTPStatus.NEW)
                .withOtpExpiry(new Timestamp(System.currentTimeMillis()))
                .withOtpRefType(OTPRefType.CUSTOMER)
                .withOtpReference("1")
                .build();

        oneTimePasswordSet.add(oneTimePassword1);


        OneTimePassword oneTimePassword2 = OneTimePasswordBuilder.anOneTimePassword()
                .withOtpMerchantNo(1L)
                .withOtpCustomerNo(1L)
                .withOtpType(OTPType.CASH_BACK_REQUEST)
                .withOtpCode("34567")
                .withOtpStatus(OTPStatus.NEW)
                .withOtpExpiry(new Timestamp(System.currentTimeMillis()))
                .withOtpRefType(OTPRefType.CUSTOMER)
                .withOtpReference("1")
                .build();

        oneTimePasswordSet.add(oneTimePassword2);

        return oneTimePasswordSet;

    }
}

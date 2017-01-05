package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.CustomerReferralStatus;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.CustomerReferral;
import com.inspirenetz.api.test.core.builder.CustomerReferralBuilder;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fayiz on 27/4/15.
 */
public class CustomerReferralFixture {




    public static CustomerReferral standardCustomerReferral() {


        CustomerReferral customerReferral = CustomerReferralBuilder.aCustomerReferral()
                .withCsrLoyaltyId("9400651677")
                .withCsrFName("FAYIZ")
                .withCsrMerchantNo(1L)
                .withCsrRefName("test")
                .withCsrRefMobile("9999999999")
                .withCsrRefEmail("test@g.com")
                .withCsrRefTimeStamp(new Timestamp(System.currentTimeMillis()))
                .withCsrRefStatus(IndicatorStatus.NO)
                .build();


        return customerReferral;


    }


    public static CustomerReferral updatedStandardCustomerReferral(CustomerReferral customerReferral) {

        customerReferral.setCsrRefEmail("test2@g.com");

        return customerReferral;

    }


    public static Set<CustomerReferral> standardCustomerReferrals() {

        Set<CustomerReferral> customerReferrals = new HashSet<CustomerReferral>(0);

        CustomerReferral customerReferralA = CustomerReferralBuilder.aCustomerReferral()
                .withCsrLoyaltyId("9400651677")
                .withCsrMerchantNo(1L)
                .withCsrRefName("test")
                .withCsrRefMobile("9999999999")
                .withCsrRefEmail("test@g.com")
                .withCsrFName("asd")
                .withCsrRefTimeStamp(new Timestamp(System.currentTimeMillis()))
                .withCsrRefStatus(CustomerReferralStatus.PROCESSED)
                .build();

        customerReferrals.add(customerReferralA);



        CustomerReferral customerReferralB = CustomerReferralBuilder.aCustomerReferral()
                .withCsrLoyaltyId("8888888888")
                .withCsrMerchantNo(1L)
                .withCsrFName("hjj")
                .withCsrRefName("test3")
                .withCsrRefMobile("9999999999")
                .withCsrRefEmail("test3@g.com")
                .withCsrRefTimeStamp(new Timestamp(System.currentTimeMillis()))
                .withCsrRefStatus(CustomerReferralStatus.PROCESSED)
                .build();

        customerReferrals.add(customerReferralB);



        return customerReferrals;



    }
}

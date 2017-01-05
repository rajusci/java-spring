package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.CustomerSummaryArchive;
import com.inspirenetz.api.test.core.builder.CustomerSummaryArchiveBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 20/5/14.
 */
public class CustomerSummaryArchiveFixture {

    public static CustomerSummaryArchive standardCustomerSummaryArchive() {

        CustomerSummaryArchive customerSummaryArchive = CustomerSummaryArchiveBuilder.aCustomerSummaryArchive()
                .withCsaMerchantNo(1L)
                .withCsaLoyaltyId("9999888877776661")
                .withCsaLocation(1L)
                .withCsaPeriodYyyy(1990)
                .withCsaPeriodQq(4)
                .withCsaPeriodMm(12)
                .withCsaVisitCount(12)
                .withCsaQuantity(23)
                .withCsaTxnAmount(3000)
                .build();

        return customerSummaryArchive;


    }



    public static CustomerSummaryArchive updatedStandardCustomerSummaryArchive(CustomerSummaryArchive customerSummaryArchive) {

        customerSummaryArchive.setCsaVisitCount(13);
        customerSummaryArchive.setCsaQuantity(24);
        customerSummaryArchive.setCsaTxnAmount(3200);

        return customerSummaryArchive;

    }


    public static Set<CustomerSummaryArchive> standardCustomerSummaryArchives() {

        Set<CustomerSummaryArchive> customerSummaryArchiveSet = new HashSet<>();

        CustomerSummaryArchive customerSummaryArchive = CustomerSummaryArchiveBuilder.aCustomerSummaryArchive()
                .withCsaMerchantNo(1L)
                .withCsaLoyaltyId("9999888877776661")
                .withCsaLocation(1L)
                .withCsaPeriodYyyy(1990)
                .withCsaPeriodQq(4)
                .withCsaPeriodMm(12)
                .withCsaVisitCount(12)
                .withCsaQuantity(23)
                .withCsaTxnAmount(3000)
                .build();

        customerSummaryArchiveSet.add(customerSummaryArchive);


        CustomerSummaryArchive customerSummaryArchive2 = CustomerSummaryArchiveBuilder.aCustomerSummaryArchive()
                .withCsaMerchantNo(1L)
                .withCsaLoyaltyId("9999888877776661")
                .withCsaLocation(2L)
                .withCsaPeriodYyyy(1990)
                .withCsaPeriodQq(4)
                .withCsaPeriodMm(12)
                .withCsaVisitCount(12)
                .withCsaQuantity(23)
                .withCsaTxnAmount(3000)
                .build();

        customerSummaryArchiveSet.add(customerSummaryArchive2);

        return customerSummaryArchiveSet;

    }
}

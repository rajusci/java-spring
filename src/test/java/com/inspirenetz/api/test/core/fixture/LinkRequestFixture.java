package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.LinkRequestStatus;
import com.inspirenetz.api.core.domain.LinkRequest;
import com.inspirenetz.api.test.core.builder.LinkRequestBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 30/4/14.
 */
public class LinkRequestFixture {

    public static LinkRequest standardLinkRequest() {

        LinkRequest linkRequest = LinkRequestBuilder.aLinkRequest()
                .withLrqMerchantNo(1L)
                .withLrqSourceCustomer(6434L)
                .withLrqParentCustomer(6435L)
                .withLrqRequestSource(3)
                .withLrqInitiator(1)
                .withLrqStatus(LinkRequestStatus.PENDING)
                .withLrqRequestSourceRef("Link1")
                .build();


        return linkRequest;


    }


    public static LinkRequest updatedStandardLinkRequests(LinkRequest linkRequest) {

        linkRequest.setLrqRequestSourceRef("updated redference");
        linkRequest.setLrqStatus(2);

        return linkRequest;

    }


    public static Set<LinkRequest> standardLinkRequestss() {

        Set<LinkRequest> linkRequests = new HashSet<LinkRequest>(0);

        LinkRequest lrq1  = LinkRequestBuilder.aLinkRequest()
                .withLrqMerchantNo(1L)
                .withLrqSourceCustomer(1L)
                .withLrqParentCustomer(2L)
                .withLrqRequestSource(3)
                .withLrqStatus(5)
                .withLrqRequestSourceRef("Link1")
                .build();
        linkRequests.add(lrq1);



        LinkRequest lrq2 = LinkRequestBuilder.aLinkRequest()
                .withLrqMerchantNo(1L)
                .withLrqSourceCustomer(1L)
                .withLrqParentCustomer(2L)
                .withLrqRequestSource(3)
                .withLrqStatus(5)
                .withLrqRequestSourceRef("Link2")
                .build();
        linkRequests.add(lrq2);



        return linkRequests;



    }
}

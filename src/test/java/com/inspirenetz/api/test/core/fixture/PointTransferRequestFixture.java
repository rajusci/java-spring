package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.dictionary.TransferRequestStatus;
import com.inspirenetz.api.core.domain.PointTransferRequest;
import com.inspirenetz.api.test.core.builder.PointTransferRequestBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 27/9/14.
 */
public class PointTransferRequestFixture {




    public static PointTransferRequest standardPointTransferRequest() {


        PointTransferRequest pointTransferRequest = PointTransferRequestBuilder.aPointTransferRequest()
                .withPtrApprover("9400651688")
                .withPtrSource("9742375894")
                .withPtrDestination("8792684047")
                .withPtrApproverCusNo(10000L)
                .withPtrSourceCusNo(100001L)
                .withPtrSourceCurrency(1L)
                .withPtrDestCurrency(1L)
                .withPtrRewardQty(100.0)
                .withPtrStatus(TransferRequestStatus.NEW)
                .withPtrMerchantNo(1l)
                .build();


        return pointTransferRequest;


    }


    public static PointTransferRequest updatedStandardPointTransferRequest(PointTransferRequest pointTransferRequest) {

        pointTransferRequest.setPtrApprover("9495951688");

        return pointTransferRequest;

    }


    public static Set<PointTransferRequest> standardPointTransferRequests() {

        Set<PointTransferRequest> pointTransferRequests = new HashSet<PointTransferRequest>(0);

        PointTransferRequest pointTransferRequestA  = PointTransferRequestBuilder.aPointTransferRequest()
                .withPtrApprover("9400651688")
                .withPtrSource("9742375894")
                .withPtrDestination("8792684047")
                .withPtrApproverCusNo(10000L)
                .withPtrSourceCusNo(100001L)
                .withPtrSourceCurrency(1L)
                .withPtrDestCurrency(1L)
                .withPtrRewardQty(100.0)
                .withPtrStatus(TransferRequestStatus.NEW)
                .withPtrMerchantNo(1l)
                .build();

        pointTransferRequests.add(pointTransferRequestA);



        PointTransferRequest pointTransferRequestB = PointTransferRequestBuilder.aPointTransferRequest()
                .withPtrApprover("9400651688")
                .withPtrSource("9742375894")
                .withPtrDestination("8792684047")
                .withPtrApproverCusNo(10000L)
                .withPtrSourceCusNo(100001L)
                .withPtrSourceCurrency(1L)
                .withPtrDestCurrency(1L)
                .withPtrRewardQty(100.0)
                .withPtrStatus(TransferRequestStatus.NEW)
                .withPtrMerchantNo(1l)
                .build();
        pointTransferRequests.add(pointTransferRequestB);



        return pointTransferRequests;



    }
}

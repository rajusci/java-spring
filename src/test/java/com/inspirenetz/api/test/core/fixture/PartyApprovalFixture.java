package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.PartyApproval;
import com.inspirenetz.api.test.core.builder.PartyApprovalBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneeshci on 29/8/14.
 */
public class PartyApprovalFixture {

    public static PartyApproval standardPartyApproval() {

        PartyApproval partyApproval = PartyApprovalBuilder.aPartyApproval()
                .withPapApprover(1L)
                .withPapRequest(1L)
                .withPapStatus(1)
                .withPapRequestor(1L)
                .withPapType(1)
                .withPapSentDateTime(new Timestamp(System.currentTimeMillis()))
                .build();


        return partyApproval;


    }


    public static PartyApproval updatedStandardPartyApproval(PartyApproval partyApproval) {

        partyApproval.setPapStatus(2);
        return partyApproval;

    }


    public static Set<PartyApproval> standardPartyApprovals() {

        Set<PartyApproval> partyApprovals = new HashSet<PartyApproval>(0);

        PartyApproval approvalA  = PartyApprovalBuilder.aPartyApproval()
                .withPapApprover(1L)
                .withPapRequest(1L)
                .withPapStatus(1)
                .withPapRequestor(1L)
                .withPapType(1)
                .build();

        partyApprovals.add(approvalA);



        PartyApproval approvalB = PartyApprovalBuilder.aPartyApproval()
                .withPapApprover(2L)
                .withPapRequest(2L)
                .withPapStatus(1)
                .withPapRequestor(1L)
                .withPapType(1)
                .build();
        partyApprovals.add(approvalB);



        return partyApprovals;



    }
}

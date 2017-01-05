package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.PartyApproval;

import java.sql.Timestamp;

/**
 * Created by saneesh-ci on 29/8/14.
 */
public class PartyApprovalBuilder {
    private Timestamp papSentDateTime;
    private Long papId;
    private Integer papType;
    private Long papRequest;
    private Long papApprover;
    private Long papRequestor;
    private Integer papStatus;

    private PartyApprovalBuilder() {
    }

    public static PartyApprovalBuilder aPartyApproval() {
        return new PartyApprovalBuilder();
    }

    public PartyApprovalBuilder withPapSentDateTime(Timestamp papSentDateTime) {
        this.papSentDateTime = papSentDateTime;
        return this;
    }

    public PartyApprovalBuilder withPapId(Long papId) {
        this.papId = papId;
        return this;
    }

    public PartyApprovalBuilder withPapType(Integer papType) {
        this.papType = papType;
        return this;
    }

    public PartyApprovalBuilder withPapRequest(Long papRequest) {
        this.papRequest = papRequest;
        return this;
    }

    public PartyApprovalBuilder withPapApprover(Long papApprover) {
        this.papApprover = papApprover;
        return this;
    }

    public PartyApprovalBuilder withPapRequestor(Long papRequestor) {
        this.papRequestor = papRequestor;
        return this;
    }

    public PartyApprovalBuilder withPapStatus(Integer papStatus) {
        this.papStatus = papStatus;
        return this;
    }

    public PartyApproval build() {
        PartyApproval partyApproval = new PartyApproval();
        partyApproval.setPapSentDateTime(papSentDateTime);
        partyApproval.setPapId(papId);
        partyApproval.setPapType(papType);
        partyApproval.setPapRequest(papRequest);
        partyApproval.setPapApprover(papApprover);
        partyApproval.setPapRequestor(papRequestor);
        partyApproval.setPapStatus(papStatus);
        return partyApproval;
    }
}

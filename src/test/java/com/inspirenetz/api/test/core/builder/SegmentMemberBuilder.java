package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.SegmentMember;

import java.util.Date;

/**
 * Created by sandheepgr on 29/5/14.
 */
public class SegmentMemberBuilder {
    private Long sgmId =  0L;
    private Long sgmSegmentId = 0L;
    private Long sgmCustomerNo = 0L;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private SegmentMemberBuilder() {
    }

    public static SegmentMemberBuilder aSegmentMember() {
        return new SegmentMemberBuilder();
    }

    public SegmentMemberBuilder withSgmId(Long sgmId) {
        this.sgmId = sgmId;
        return this;
    }

    public SegmentMemberBuilder withSgmSegmentId(Long sgmSegmentId) {
        this.sgmSegmentId = sgmSegmentId;
        return this;
    }

    public SegmentMemberBuilder withSgmCustomerNo(Long sgmCustomerNo) {
        this.sgmCustomerNo = sgmCustomerNo;
        return this;
    }

    public SegmentMemberBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SegmentMemberBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public SegmentMemberBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public SegmentMemberBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public SegmentMember build() {
        SegmentMember segmentMember = new SegmentMember();
        segmentMember.setSgmId(sgmId);
        segmentMember.setSgmSegmentId(sgmSegmentId);
        segmentMember.setSgmCustomerNo(sgmCustomerNo);
        segmentMember.setCreatedAt(createdAt);
        segmentMember.setCreatedBy(createdBy);
        segmentMember.setUpdatedAt(updatedAt);
        segmentMember.setUpdatedBy(updatedBy);
        return segmentMember;
    }
}

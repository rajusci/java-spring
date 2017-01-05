package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 29/5/14.
 */
public class SegmentMemberResource extends BaseResource {

    private Long sgmId;

    private Long sgmSegmentId;

    private Long sgmCustomerNo;


    public Long getSgmId() {
        return sgmId;
    }

    public void setSgmId(Long sgmId) {
        this.sgmId = sgmId;
    }

    public Long getSgmSegmentId() {
        return sgmSegmentId;
    }

    public void setSgmSegmentId(Long sgmSegmentId) {
        this.sgmSegmentId = sgmSegmentId;
    }

    public Long getSgmCustomerNo() {
        return sgmCustomerNo;
    }

    public void setSgmCustomerNo(Long sgmCustomerNo) {
        this.sgmCustomerNo = sgmCustomerNo;
    }


    @Override
    public String toString() {
        return "SegmentMemberResource{" +
                "sgmId=" + sgmId +
                ", sgmSegmentId=" + sgmSegmentId +
                ", sgmCustomerNo=" + sgmCustomerNo +
                '}';
    }
}

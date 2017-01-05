package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import javax.swing.text.Segment;

/**
 * Created by sandheepgr on 29/5/14.
 */
@Entity
@Table(name = "SEGMENT_MEMBER")
public class SegmentMember extends AuditedEntity {

    @Id
    @Column(name = "SGM_ID", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sgmId =  0L;

    @Basic
    @Column(name = "SGM_SEGMENT_ID", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    private Long sgmSegmentId = 0L;

    @Basic
    @Column(name = "SGM_CUSTOMER_NO", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    private Long sgmCustomerNo = 0L;

    @Basic
    @Column(name = "SGM_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    private Long sgmMerchantNo;




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


    public Long getSgmMerchantNo() {
        return sgmMerchantNo;
    }

    public void setSgmMerchantNo(Long sgmMerchantNo) {
        this.sgmMerchantNo = sgmMerchantNo;
    }

    @Override
    public String toString() {
        return "SegmentMember{" +
                "sgmId=" + sgmId +
                ", sgmSegmentId=" + sgmSegmentId +
                ", sgmCustomerNo=" + sgmCustomerNo +
                ", sgmMerchantNo=" + sgmMerchantNo +
                '}';
    }
}

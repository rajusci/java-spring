package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "CUSTOMER_MILESTONES")
@IdClass(CustomerMilestonePK.class)
public class CustomerMilestone {
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMI_MERCHANT_NO")
    private Long cmiMerchantNo;
    
    @Id
    @Column(name = "CMI_LOYALTY_ID")
    private String cmiLoyaltyId;
    
    @Id
    @Column(name = "CMI_MILESTONE_ID")
    private int cmiMilestoneId;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMI_CLAIM_STATUS")
    private int cmiClaimStatus;


    public Long getCmiMerchantNo() {
        return cmiMerchantNo;
    }

    public void setCmiMerchantNo(Long cmiMerchantNo) {
        this.cmiMerchantNo = cmiMerchantNo;
    }

    public String getCmiLoyaltyId() {
        return cmiLoyaltyId;
    }

    public void setCmiLoyaltyId(String cmiLoyaltyId) {
        this.cmiLoyaltyId = cmiLoyaltyId;
    }

    public int getCmiMilestoneId() {
        return cmiMilestoneId;
    }

    public void setCmiMilestoneId(int cmiMilestoneId) {
        this.cmiMilestoneId = cmiMilestoneId;
    }

    public int getCmiClaimStatus() {
        return cmiClaimStatus;
    }

    public void setCmiClaimStatus(int cmiClaimStatus) {
        this.cmiClaimStatus = cmiClaimStatus;
    }


    @Override
    public String toString() {
        return "CustomerMilestone{" +
                "cmiMerchantNo=" + cmiMerchantNo +
                ", cmiLoyaltyId='" + cmiLoyaltyId + '\'' +
                ", cmiMilestoneId=" + cmiMilestoneId +
                ", cmiClaimStatus=" + cmiClaimStatus +
                '}';
    }
}

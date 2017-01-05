package com.inspirenetz.api.core.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by sandheepgr on 29/3/14.
 */
public class CustomerMilestonePK implements Serializable {

    @Column(name = "CMI_LOYALTY_ID")
    @Id
    private String cmiLoyaltyId;

    @Column(name = "CMI_MILESTONE_ID")
    @Id
    private int cmiMilestoneId;

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



}

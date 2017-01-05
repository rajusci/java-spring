package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by ameen on 8/10/15.
 */
@Entity
@Table(name = "REFERRER_PROGRAM_SUMMARY")
public class ReferrerProgramSummary extends AuditedEntity{

    @Id
    @Column(name = "RPS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rpsId;

    @Column(name = "CPS_MERCHANT_NO")
    private Long rpsMerchantNo;

    @Column(name = "RPS_LOYALTY_ID")
    private String rpsLoyaltyId;

    @Column(name = "RPS_PROGRAM_ID")
    private Long rpsProgramId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "RPS_PROGRAM_VISIT")
    private int rpsProgramVisit= 0 ;

    @Column(name = "RPS_REFEREE_LOYALTY_ID")
    private String rpsRefereeLoyaltyId;

    public Long getRpsId() {
        return rpsId;
    }

    public void setRpsId(Long rpsId) {
        this.rpsId = rpsId;
    }

    public Long getRpsMerchantNo() {
        return rpsMerchantNo;
    }

    public void setRpsMerchantNo(Long rpsMerchantNo) {
        this.rpsMerchantNo = rpsMerchantNo;
    }

    public String getRpsLoyaltyId() {
        return rpsLoyaltyId;
    }

    public void setRpsLoyaltyId(String rpsLoyaltyId) {
        this.rpsLoyaltyId = rpsLoyaltyId;
    }

    public Long getRpsProgramId() {
        return rpsProgramId;
    }

    public void setRpsProgramId(Long rpsProgramId) {
        this.rpsProgramId = rpsProgramId;
    }



    public int getRpsProgramVisit() {
        return rpsProgramVisit;
    }

    public void setRpsProgramVisit(int rpsProgramVisit) {
        this.rpsProgramVisit = rpsProgramVisit;
    }

    public String getRpsRefereeLoyaltyId() {
        return rpsRefereeLoyaltyId;
    }

    public void setRpsRefereeLoyaltyId(String rpsRefereeLoyaltyId) {
        this.rpsRefereeLoyaltyId = rpsRefereeLoyaltyId;
    }

    @Override
    public String toString() {
        return "ReferrerProgramSummary{" +
                "rpsId=" + rpsId +
                ", rpsMerchantNo=" + rpsMerchantNo +
                ", rpsLoyaltyId='" + rpsLoyaltyId + '\'' +
                ", rpsProgramId=" + rpsProgramId +
                ", rpsProgramVisit=" + rpsProgramVisit +
                ", rpsRefereeLoyaltyId='" + rpsRefereeLoyaltyId + '\'' +
                '}';
    }
}

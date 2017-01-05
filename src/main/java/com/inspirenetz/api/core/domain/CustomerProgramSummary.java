package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "CUSTOMER_PROGRAM_SUMMARY")
public class CustomerProgramSummary extends AuditedEntity {

    @Id
    @Column(name = "CPS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cpsId;

    @Column(name = "CPS_MERCHANT_NO")
    private Long cpsMerchantNo;

    @Column(name = "CPS_LOYALTY_ID")
    private String cpsLoyaltyId;

    @Column(name = "CPS_PROGRAM_ID")
    private Long cpsProgramId;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPS_AWARD_COUNT")
    private double cpsAwardCount = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPS_PROGRAM_AMOUNT")
    private double cpsProgramAmount = 0;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPS_PROGRAM_QUANTITY")
    private int cpsProgramQuantity = 0;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPS_PROGRAM_VISIT")
    private int cpsProgramVisit= 0 ;




    public Long getCpsId() {
        return cpsId;
    }

    public void setCpsId(Long cpsId) {
        this.cpsId = cpsId;
    }

    public Long getCpsProgramId() {
        return cpsProgramId;
    }

    public void setCpsProgramId(Long cpsProgramId) {
        this.cpsProgramId = cpsProgramId;
    }

    public Long getCpsMerchantNo() {
        return cpsMerchantNo;
    }

    public void setCpsMerchantNo(Long cpsMerchantNo) {
        this.cpsMerchantNo = cpsMerchantNo;
    }

    public String getCpsLoyaltyId() {
        return cpsLoyaltyId;
    }

    public void setCpsLoyaltyId(String cpsLoyaltyId) {
        this.cpsLoyaltyId = cpsLoyaltyId;
    }

    public double getCpsAwardCount() {
        return cpsAwardCount;
    }

    public void setCpsAwardCount(double cpsAwardCount) {
        this.cpsAwardCount = cpsAwardCount;
    }

    public double getCpsProgramAmount() {
        return cpsProgramAmount;
    }

    public void setCpsProgramAmount(double cpsProgramAmount) {
        this.cpsProgramAmount = cpsProgramAmount;
    }

    public int getCpsProgramQuantity() {
        return cpsProgramQuantity;
    }

    public void setCpsProgramQuantity(int cpsProgramQuantity) {
        this.cpsProgramQuantity = cpsProgramQuantity;
    }

    public int getCpsProgramVisit() {
        return cpsProgramVisit;
    }

    public void setCpsProgramVisit(int cpsProgramVisit) {
        this.cpsProgramVisit = cpsProgramVisit;
    }


    @Override
    public String toString() {
        return "CustomerProgramSummary{" +
                "cpsId=" + cpsId +
                ", cpsMerchantNo=" + cpsMerchantNo +
                ", cpsLoyaltyId='" + cpsLoyaltyId + '\'' +
                ", cpsProgramId=" + cpsProgramId +
                ", cpsAwardCount=" + cpsAwardCount +
                ", cpsProgramAmount=" + cpsProgramAmount +
                ", cpsProgramQuantity=" + cpsProgramQuantity +
                ", cpsProgramVisit=" + cpsProgramVisit +
                '}';
    }
}

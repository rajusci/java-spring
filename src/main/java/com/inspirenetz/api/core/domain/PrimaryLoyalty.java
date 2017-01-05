package com.inspirenetz.api.core.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Entity
@Table(name="PRIMAY_LOYALTY")
public class PrimaryLoyalty extends AuditedEntity implements Serializable {

    @Column(name = "PLL_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pllId;


    @Column(name = "PLL_CUSTOMER_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long pllCustomerNo;

    @Column(name = "PLL_LOYALTY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String pllLoyaltyId;


    @Column(name = "PLL_FNAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String pllFName;


    @Column(name = "PLL_LNAME",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String pllLName;

    @Column(name = "PLL_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer pllStatus;


    @Column(name = "PLL_LOCATION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long pllLocation;

    public Long getPllId() {
        return pllId;
    }

    public void setPllId(Long pllId) {
        this.pllId = pllId;
    }

    public Long getPllCustomerNo() {
        return pllCustomerNo;
    }

    public void setPllCustomerNo(Long pllCustomerNo) {
        this.pllCustomerNo = pllCustomerNo;
    }

    public String getPllLoyaltyId() {
        return pllLoyaltyId;
    }

    public void setPllLoyaltyId(String pllLoyaltyId) {
        this.pllLoyaltyId = pllLoyaltyId;
    }

    public String getPllFName() {
        return pllFName;
    }

    public void setPllFName(String pllFName) {
        this.pllFName = pllFName;
    }

    public String getPllLName() {
        return pllLName;
    }

    public void setPllLName(String pllLName) {
        this.pllLName = pllLName;
    }

    public Integer getPllStatus() {
        return pllStatus;
    }

    public void setPllStatus(Integer pllStatus) {
        this.pllStatus = pllStatus;
    }

    public Long getPllLocation() {
        return pllLocation;
    }

    public void setPllLocation(Long pllLocation) {
        this.pllLocation = pllLocation;
    }

    @Override
    public String toString() {
        return "PrimaryLoyalty{" +
                "pllId=" + pllId +
                ", pllCustomerNo=" + pllCustomerNo +
                ", pllLoyaltyId='" + pllLoyaltyId + '\'' +
                ", pllFName='" + pllFName + '\'' +
                ", pllLName='" + pllLName + '\'' +
                ", pllStatus=" + pllStatus +
                ", pllLocation='" + pllLocation + '\'' +
                '}';
    }
}

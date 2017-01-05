package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 11/3/14.
 */
@Entity
@Table(name="LINKED_LOYALTY")
public class LinkedLoyalty extends AuditedEntity {

    @Column(name = "LIL_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lilId;

    @Column(name = "LIL_PARENT_CUSTOMER_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long lilParentCustomerNo;

    @Column(name = "LIL_CHILD_CUSTOMER_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long lilChildCustomerNo;

    @Column(name = "LIL_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer lilStatus;

    @Column(name = "LIL_LOCATION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long lilLocation;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="LIL_CHILD_CUSTOMER_NO",insertable = false,updatable = false)
    private Customer childCustomer;

    @Transient
    private Customer customer;


    public Long getLilId() {
        return lilId;
    }

    public void setLilId(Long lilId) {
        this.lilId = lilId;
    }

    public Long getLilParentCustomerNo() {
        return lilParentCustomerNo;
    }

    public void setLilParentCustomerNo(Long lilParentCustomerNo) {
        this.lilParentCustomerNo = lilParentCustomerNo;
    }

    public Long getLilChildCustomerNo() {
        return lilChildCustomerNo;
    }

    public void setLilChildCustomerNo(Long lilChildCustomerNo) {
        this.lilChildCustomerNo = lilChildCustomerNo;
    }

    public Integer getLilStatus() {
        return lilStatus;
    }

    public void setLilStatus(Integer lilStatus) {
        this.lilStatus = lilStatus;
    }

    public Long getLilLocation() {
        return lilLocation;
    }

    public void setLilLocation(Long lilLocation) {
        this.lilLocation = lilLocation;
    }

    public Customer getChildCustomer() {
        return childCustomer;
    }

    public void setChildCustomer(Customer childCustomer) {
        this.childCustomer = childCustomer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "LinkedLoyalty{" +
                "lilId=" + lilId +
                ", lilParentCustomerNo=" + lilParentCustomerNo +
                ", lilChildCustomerNo='" + lilChildCustomerNo + '\'' +
                ", lilStatus=" + lilStatus +
                ", lilLocation=" + lilLocation +
                '}';
    }
}

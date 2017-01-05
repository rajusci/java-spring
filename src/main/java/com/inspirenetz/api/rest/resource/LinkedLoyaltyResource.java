package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 6/9/14.
 */
public class LinkedLoyaltyResource extends BaseResource {


    private Long lilId;

    private Long lilParentCustomerNo;

    private Long lilChildCustomerNo;

    private Integer lilStatus;

    private Long lilLocation;

    private String cusFName;

    private String cusLName;

    private String cusLoyaltyId;

    private Long cusCustomerNo;

    private boolean isPrimary =false;


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

    public String getCusFName() {
        return cusFName;
    }

    public void setCusFName(String cusFName) {
        this.cusFName = cusFName;
    }

    public String getCusLName() {
        return cusLName;
    }

    public void setCusLName(String cusLName) {
        this.cusLName = cusLName;
    }

    public String getCusLoyaltyId() {
        return cusLoyaltyId;
    }

    public void setCusLoyaltyId(String cusLoyaltyId) {
        this.cusLoyaltyId = cusLoyaltyId;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }


    @Override
    public String toString() {
        return "LinkedLoyaltyResource{" +
                "lilId=" + lilId +
                ", lilParentCustomerNo=" + lilParentCustomerNo +
                ", lilChildCustomerNo=" + lilChildCustomerNo +
                ", lilStatus=" + lilStatus +
                ", lilLocation=" + lilLocation +
                ", cusFName='" + cusFName + '\'' +
                ", cusLName='" + cusLName + '\'' +
                ", cusLoyaltyId='" + cusLoyaltyId + '\'' +
                ", isPrimary=" + isPrimary +
                '}';
    }
}

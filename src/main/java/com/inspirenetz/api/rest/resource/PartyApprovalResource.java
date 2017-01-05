package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class PartyApprovalResource extends BaseResource {

    private Long papId;

    private Long papApprover;

    private Long papRequestor;

    private Long papRequest;

    private Integer papType;

    private Integer papStatus;

    private String cusFName;

    private String cusLName;

    private String cusLoyaltyId;

    private String cusCustomerNo;

    private String papReference;

    public Long getPapId() {
        return papId;
    }

    public void setPapId(Long papId) {
        this.papId = papId;
    }

    public Long getPapApprover() {
        return papApprover;
    }

    public void setPapApprover(Long papApprover) {
        this.papApprover = papApprover;
    }

    public Long getPapRequestor() {
        return papRequestor;
    }

    public void setPapRequestor(Long papRequestor) {
        this.papRequestor = papRequestor;
    }

    public Long getPapRequest() {
        return papRequest;
    }

    public void setPapRequest(Long papRequest) {
        this.papRequest = papRequest;
    }

    public Integer getPapType() {
        return papType;
    }

    public void setPapType(Integer papType) {
        this.papType = papType;
    }

    public Integer getPapStatus() {
        return papStatus;
    }

    public void setPapStatus(Integer papStatus) {
        this.papStatus = papStatus;
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

    public String getCusCustomerNo() {
        return cusCustomerNo;
    }

    public void setCusCustomerNo(String cusCustomerNo) {
        this.cusCustomerNo = cusCustomerNo;
    }

    public String getPapReference() {
        return papReference;
    }

    public void setPapReference(String papReference) {
        this.papReference = papReference;
    }

    @Override
    public String toString() {
        return "PartyApprovalResource{" +
                "papId=" + papId +
                ", papApprover=" + papApprover +
                ", papRequestor=" + papRequestor +
                ", papRequest=" + papRequest +
                ", papType=" + papType +
                ", papStatus=" + papStatus +
                ", cusFName='" + cusFName + '\'' +
                ", cusLName='" + cusLName + '\'' +
                ", cusLoyaltyId='" + cusLoyaltyId + '\'' +
                ", cusCustomerNo='" + cusCustomerNo + '\'' +
                ", papReference='" + papReference + '\'' +
                '}';
    }
}
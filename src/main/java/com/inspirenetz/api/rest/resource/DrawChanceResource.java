package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class DrawChanceResource extends BaseResource {



    private Long drcId;
    private Long drcCustomerNo;
    private Long drcChances;
    private Integer drcType;
    private Integer drcStatus;

    public Long getDrcId() {
        return drcId;
    }

    public void setDrcId(Long drcId) {
        this.drcId = drcId;
    }

    public Long getDrcCustomerNo() {
        return drcCustomerNo;
    }

    public void setDrcCustomerNo(Long drcCustomerNo) {
        this.drcCustomerNo = drcCustomerNo;
    }

    public Long getDrcChances() {
        return drcChances;
    }

    public void setDrcChances(Long drcChances) {
        this.drcChances = drcChances;
    }

    public Integer getDrcType() {
        return drcType;
    }

    public void setDrcType(Integer drcType) {
        this.drcType = drcType;
    }

    public Integer getDrcStatus() {
        return drcStatus;
    }

    public void setDrcStatus(Integer drcStatus) {
        this.drcStatus = drcStatus;
    }

    @Override
    public String toString() {
        return "DrawChanceResource{" +
                "drcId=" + drcId +
                ", drcCustomerNo=" + drcCustomerNo +
                ", drcChances=" + drcChances +
                ", drcType=" + drcType +
                ", drcStatus=" + drcStatus +
                '}';
    }
}

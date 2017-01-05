package com.inspirenetz.api.rest.resource;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by saneeshci on 30/09/14.
 */
public class CustomerRewardActivityResource extends BaseResource {

    private Long craId;
    private Long craCustomerNo;
    private Integer craType;
    private String craActivityRef;
    private Integer craStatus;
    private Timestamp craActivityTimeStamp ;

    public Timestamp getCraActivityTimeStamp() {
        return craActivityTimeStamp;
    }

    public void setCraActivityTimeStamp(Timestamp craActivityTimeStamp) {
        this.craActivityTimeStamp = craActivityTimeStamp;
    }

    public Long getCraId() {

        return craId;
    }

    public void setCraId(Long craId) {
        this.craId = craId;
    }

    public Long getCraCustomerNo() {
        return craCustomerNo;
    }

    public void setCraCustomerNo(Long craCustomerNo) {
        this.craCustomerNo = craCustomerNo;
    }

    public Integer getCraType() {
        return craType;
    }

    public void setCraType(Integer craType) {
        this.craType = craType;
    }

    public String getCraActivityRef() {
        return craActivityRef;
    }

    public void setCraActivityRef(String craActivityRef) {
        this.craActivityRef = craActivityRef;
    }

    public Integer getCraStatus() {
        return craStatus;
    }

    public void setCraStatus(Integer craStatus) {
        this.craStatus = craStatus;
    }

    @Override
    public String toString() {
        return "CustomerRewardActivityResource{" +
                "craId=" + craId +
                ", craCustomerNo=" + craCustomerNo +
                ", craType=" + craType +
                ", craActivityRef='" + craActivityRef + '\'' +
                ", craStatus=" + craStatus +
                ", craActivityTimeStamp=" + craActivityTimeStamp +
                '}';
    }
}

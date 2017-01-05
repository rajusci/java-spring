package com.inspirenetz.api.rest.resource;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by ameen on 21/10/15.
 */
public class CardNumberBatchInfoResource extends BaseResource {

    private Long cnbId;

    private Long cnbMerchantNo;

    private String cnbName;

    private Date cnbDate;

    private Time cnbTime;
    private Integer cnbProcessStatus;

    private String cnbCardName ="";

    public Long getCnbId() {
        return cnbId;
    }

    public void setCnbId(Long cnbId) {
        this.cnbId = cnbId;
    }

    public Long getCnbMerchantNo() {
        return cnbMerchantNo;
    }

    public void setCnbMerchantNo(Long cnbMerchantNo) {
        this.cnbMerchantNo = cnbMerchantNo;
    }

    public String getCnbName() {
        return cnbName;
    }

    public void setCnbName(String cnbName) {
        this.cnbName = cnbName;
    }

    public Date getCnbDate() {
        return cnbDate;
    }

    public void setCnbDate(Date cnbDate) {
        this.cnbDate = cnbDate;
    }

    public Time getCnbTime() {
        return cnbTime;
    }

    public void setCnbTime(Time cnbTime) {
        this.cnbTime = cnbTime;
    }

    public Integer getCnbProcessStatus() {
        return cnbProcessStatus;
    }

    public void setCnbProcessStatus(Integer cnbProcessStatus) {
        this.cnbProcessStatus = cnbProcessStatus;
    }

    public String getCnbCardName() {
        return cnbCardName;
    }

    public void setCnbCardName(String cnbCardName) {
        this.cnbCardName = cnbCardName;
    }
}

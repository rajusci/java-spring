package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.OrderSlotSession;
import com.inspirenetz.api.core.dictionary.OrderSlotType;

import java.sql.Time;

/**
 * Created by sandheepgr on 30/7/14.
 */
public class OrderSlotResource extends BaseResource {

    private Long ortId;

    private Integer ortType = OrderSlotType.ORT_TYPE_PICKUP;

    private Long ortMerchantNo = 0L;

    private Long ortLocation = 0L;

    private Integer ortSession = OrderSlotSession.ORT_SESSION_BREAKFAST;

    private Time ortStartingTime;

    private String ortDisplayTitle ="";


    public Long getOrtId() {
        return ortId;
    }

    public void setOrtId(Long ortId) {
        this.ortId = ortId;
    }

    public Integer getOrtType() {
        return ortType;
    }

    public void setOrtType(Integer ortType) {
        this.ortType = ortType;
    }

    public Long getOrtMerchantNo() {
        return ortMerchantNo;
    }

    public void setOrtMerchantNo(Long ortMerchantNo) {
        this.ortMerchantNo = ortMerchantNo;
    }

    public Long getOrtLocation() {
        return ortLocation;
    }

    public void setOrtLocation(Long ortLocation) {
        this.ortLocation = ortLocation;
    }

    public Integer getOrtSession() {
        return ortSession;
    }

    public void setOrtSession(Integer ortSession) {
        this.ortSession = ortSession;
    }

    public Time getOrtStartingTime() {
        return ortStartingTime;
    }

    public void setOrtStartingTime(Time ortStartingTime) {
        this.ortStartingTime = ortStartingTime;
    }

    public String getOrtDisplayTitle() {
        return ortDisplayTitle;
    }

    public void setOrtDisplayTitle(String ortDisplayTitle) {
        this.ortDisplayTitle = ortDisplayTitle;
    }
}

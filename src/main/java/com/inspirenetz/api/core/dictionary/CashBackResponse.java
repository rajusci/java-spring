package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 25/7/14.
 */
public class CashBackResponse {

    Integer status = IndicatorStatus.NO;

    Long reference = 0L;

    String trackingId = "";

    Double points = 0.0;

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;

    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "CashBackResponse{" +
                "status=" + status +
                ", reference=" + reference +
                ", trackingId='" + trackingId + '\'' +
                ", points=" + points +
                '}';
    }
}

package com.inspirenetz.api.core.dictionary;

/**
 * Created by ameen on 4/4/15.
 */
public class SegmentMemberDetails {

    private String smLoyaltyId;

    private String smFName;

    private String smLName;

    private String smMobileNo;

    private String smEmail;

    private Integer smActive;

    public String getSmLoyaltyId() {
        return smLoyaltyId;
    }

    public void setSmLoyaltyId(String smLoyaltyId) {
        this.smLoyaltyId = smLoyaltyId;
    }

    public String getSmFName() {
        return smFName;
    }

    public void setSmFName(String smFName) {
        this.smFName = smFName;
    }

    public String getSmLName() {
        return smLName;
    }

    public void setSmLName(String smLName) {
        this.smLName = smLName;
    }

    public String getSmMobileNo() {
        return smMobileNo;
    }

    public void setSmMobileNo(String smMobileNo) {
        this.smMobileNo = smMobileNo;
    }

    public String getSmEmail() {
        return smEmail;
    }

    public void setSmEmail(String smEmail) {
        this.smEmail = smEmail;
    }

    public Integer getSmActive() {
        return smActive;
    }

    public void setSmActive(Integer smActive) {
        this.smActive = smActive;
    }

    @Override
    public String toString() {
        return "SegmentMemberDetails{" +
                "smLoyaltyId='" + smLoyaltyId + '\'' +
                ", smFName='" + smFName + '\'' +
                ", smLName='" + smLName + '\'' +
                ", smMobileNo='" + smMobileNo + '\'' +
                ", smEmail='" + smEmail + '\'' +
                ", smActive=" + smActive +
                '}';
    }
}

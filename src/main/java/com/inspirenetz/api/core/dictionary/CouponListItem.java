package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 10/8/14.
 */
public class CouponListItem {

    private String cpnCouponName = "";
    private String cpnPromoName = "";
    private String cpnCouponText = "";
    private String cpnCouponCode = "";
    private String cpnImagePath = "";
    private String cpnValue = "";
    private Integer cpnType = CouponValueType.AMOUNT;


    public String getCpnCouponName() {
        return cpnCouponName;
    }

    public void setCpnCouponName(String cpnCouponName) {
        this.cpnCouponName = cpnCouponName;
    }

    public String getCpnPromoName() {
        return cpnPromoName;
    }

    public void setCpnPromoName(String cpnPromoName) {
        this.cpnPromoName = cpnPromoName;
    }

    public String getCpnCouponText() {
        return cpnCouponText;
    }

    public void setCpnCouponText(String cpnCouponText) {
        this.cpnCouponText = cpnCouponText;
    }

    public String getCpnCouponCode() {
        return cpnCouponCode;
    }

    public void setCpnCouponCode(String cpnCouponCode) {
        this.cpnCouponCode = cpnCouponCode;
    }

    public String getCpnImagePath() {
        return cpnImagePath;
    }

    public void setCpnImagePath(String cpnImagePath) {
        this.cpnImagePath = cpnImagePath;
    }

    public String getCpnValue() {
        return cpnValue;
    }

    public void setCpnValue(String cpnValue) {
        this.cpnValue = cpnValue;
    }

    public Integer getCpnType() {
        return cpnType;
    }

    public void setCpnType(Integer cpnType) {
        this.cpnType = cpnType;
    }
}

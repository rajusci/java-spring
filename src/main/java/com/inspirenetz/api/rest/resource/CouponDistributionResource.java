package com.inspirenetz.api.rest.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inspirenetz.api.core.dictionary.CouponDistributionStatus;
import com.inspirenetz.api.core.dictionary.CouponDistributionType;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by sandheepgr on 18/6/14.
 */
public class CouponDistributionResource extends BaseResource {

    private Long codId;
    private Long codMerchantNo;
    private String codCouponCode = "";
    private Integer codDistributionType = CouponDistributionType.MEMBERS;
    private String codCustomerSegments = "";
    private String codCustomerIds = "";
    private String codCoalitionIds = "";
    private Integer codStatus = CouponDistributionStatus.ACTIVE;
    private String codBroadCastType;
    private String codSmsContent = "";
    private String codEmailSubject = "";
    private String codEmailContent = "";

    public String getCodBroadCastType() {
        return codBroadCastType;
    }

    public void setCodBroadCastType(String codBroadCastType) {
        this.codBroadCastType = codBroadCastType;
    }

    public Long getCodId() {
        return codId;
    }

    public void setCodId(Long codId) {
        this.codId = codId;
    }

    public Long getCodMerchantNo() {
        return codMerchantNo;
    }

    public void setCodMerchantNo(Long codMerchantNo) {
        this.codMerchantNo = codMerchantNo;
    }

    public String getCodCouponCode() {
        return codCouponCode;
    }

    public void setCodCouponCode(String codCouponCode) {
        this.codCouponCode = codCouponCode;
    }

    public Integer getCodDistributionType() {
        return codDistributionType;
    }

    public void setCodDistributionType(Integer codDistributionType) {
        this.codDistributionType = codDistributionType;
    }

    public String getCodCustomerSegments() {
        return codCustomerSegments;
    }

    public void setCodCustomerSegments(String codCustomerSegments) {
        this.codCustomerSegments = codCustomerSegments;
    }

    public String getCodCustomerIds() {
        return codCustomerIds;
    }

    public void setCodCustomerIds(String codCustomerIds) {
        this.codCustomerIds = codCustomerIds;
    }

    public String getCodCoalitionIds() {
        return codCoalitionIds;
    }

    public void setCodCoalitionIds(String codCoalitionIds) {
        this.codCoalitionIds = codCoalitionIds;
    }

    public Integer getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(Integer codStatus) {
        this.codStatus = codStatus;
    }

    public String getCodSmsContent() {
        return codSmsContent;
    }

    public void setCodSmsContent(String codSmsContent) {
        this.codSmsContent = codSmsContent;
    }

    public String getCodEmailSubject() {
        return codEmailSubject;
    }

    public void setCodEmailSubject(String codEmailSubject) {
        this.codEmailSubject = codEmailSubject;
    }

    public String getCodEmailContent() {
        return codEmailContent;
    }

    public void setCodEmailContent(String codEmailContent) {
        this.codEmailContent = codEmailContent;
    }
}

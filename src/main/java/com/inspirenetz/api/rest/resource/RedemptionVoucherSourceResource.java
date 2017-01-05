package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.VoucherSourceType;
import com.inspirenetz.api.core.dictionary.VoucherStatus;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class RedemptionVoucherSourceResource extends BaseResource {

    private Long rvsId;
    private String rvsName;
    private Integer rvsType;
    private String rvsPrefix;
    private Long rvsCodeStart;
    private String rvsCode;
    private Long rvsCodeEnd;
    private Long rvsIndex;
    private Long rvsMerchantNo;
    private Integer rvsStatus= VoucherStatus.ACTIVE;
    private String filePath;

    public Long getRvsId() {
        return rvsId;
    }

    public void setRvsId(Long rvsId) {
        this.rvsId = rvsId;
    }

    public String getRvsName() {
        return rvsName;
    }

    public void setRvsName(String rvsName) {
        this.rvsName = rvsName;
    }

    public Integer getRvsType() {
        return rvsType;
    }

    public void setRvsType(Integer rvsType) {
        this.rvsType = rvsType;
    }

    public String getRvsPrefix() {
        return rvsPrefix;
    }

    public void setRvsPrefix(String rvsPrefix) {
        this.rvsPrefix = rvsPrefix;
    }

    public Long getRvsCodeStart() {
        return rvsCodeStart;
    }

    public void setRvsCodeStart(Long rvsCodeStart) {
        this.rvsCodeStart = rvsCodeStart;
    }

    public String getRvsCode() {
        return rvsCode;
    }

    public void setRvsCode(String rvsCode) {
        this.rvsCode = rvsCode;
    }

    public Long getRvsCodeEnd() {
        return rvsCodeEnd;
    }

    public void setRvsCodeEnd(Long rvsCodeEnd) {
        this.rvsCodeEnd = rvsCodeEnd;
    }

    public Long getRvsIndex() {
        return rvsIndex;
    }

    public void setRvsIndex(Long rvsIndex) {
        this.rvsIndex = rvsIndex;
    }

    public Long getRvsMerchantNo() {
        return rvsMerchantNo;
    }

    public void setRvsMerchantNo(Long rvsMerchantNo) {
        this.rvsMerchantNo = rvsMerchantNo;
    }

    public Integer getRvsStatus() {
        return rvsStatus;
    }

    public void setRvsStatus(Integer rvsStatus) {
        this.rvsStatus = rvsStatus;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "RedemptionVoucherSourceResource{" +
                "rvsId=" + rvsId +
                ", rvsName='" + rvsName + '\'' +
                ", rvsType=" + rvsType +
                ", rvsPrefix='" + rvsPrefix + '\'' +
                ", rvsCodeStart=" + rvsCodeStart +
                ", rvsCode='" + rvsCode + '\'' +
                ", rvsCodeEnd=" + rvsCodeEnd +
                ", rvsIndex=" + rvsIndex +
                ", rvsMerchantNo=" + rvsMerchantNo +
                '}';
    }
}

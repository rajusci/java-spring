package com.inspirenetz.api.rest.resource;

/**
 * Created by ameen on 25/4/15.
 */
public class MerchantModuleResource extends BaseResource {

    private Long memId;

    private Long memMerchantNo;

    private Long memModuleId = 0L;

    private Integer memEnabledInd;

    private String memModuleName;

    public Long getMemId() {
        return memId;
    }

    public Long getMemMerchantNo() {
        return memMerchantNo;
    }

    public void setMemMerchantNo(Long memMerchantNo) {
        this.memMerchantNo = memMerchantNo;
    }

    public Long getMemModuleId() {
        return memModuleId;
    }

    public void setMemModuleId(Long memModuleId) {
        this.memModuleId = memModuleId;
    }

    public Integer getMemEnabledInd() {
        return memEnabledInd;
    }

    public void setMemEnabledInd(Integer memEnabledInd) {
        this.memEnabledInd = memEnabledInd;
    }

    public String getMemModuleName() {
        return memModuleName;
    }

    public void setMemModuleName(String memModuleName) {
        this.memModuleName = memModuleName;
    }

    public void setMemId(Long memId) {
        this.memId = memId;
    }
}

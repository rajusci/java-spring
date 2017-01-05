package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;

import javax.persistence.*;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "MERCHANT_MODULES")
public class MerchantModule extends AuditedEntity {

    @Column(name = "MEM_ID")
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memId;

    @Column(name = "MEM_MERCHANT_NO")
    private Long memMerchantNo;

    @Column(name = "MEM_MODULE_ID")
    private Long memModuleId = 0L;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "MEM_ENABLED_IND")
    private Integer memEnabledInd = IndicatorStatus.NO;

    @Transient
    private String memModuleName;


    public Long getMemId() {
        return memId;
    }

    public void setMemId(Long memId) {
        this.memId = memId;
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

    @Override
    public String toString() {
        return "MerchantModule{" +
                "memId=" + memId +
                ", memMerchantNo=" + memMerchantNo +
                ", memModuleId=" + memModuleId +
                ", memEnabledInd=" + memEnabledInd +
                ", memModuleName='" + memModuleName + '\'' +
                '}';
    }
}

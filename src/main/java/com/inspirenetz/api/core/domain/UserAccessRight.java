package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.UserAccessRightAccessEnableFlag;

import javax.persistence.*;

/**
 * Created by sandheepgr on 26/6/14.
 */
@Entity
@Table(name = "USER_ACCESS_RIGHTS")
public class UserAccessRight extends  AuditedEntity {


    @Id
    @Column(name = "UAR_UARID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uarUarId;

    @Basic
    @Column(name = "UAR_USER_NO", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Long uarUserNo =  0L;

    @Basic
    @Column(name = "UAR_FUNCTION_CODE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long uarFunctionCode = 0L;

    @Basic
    @Column(name = "UAR_ACCESS_ENABLE_FLAG", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    private String uarAccessEnableFlag = UserAccessRightAccessEnableFlag.DISABLED;

    @Basic
    @Column(name = "UAR_ACCESS_OVERRIDEN_FLAG", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    private Integer uarAccessOverridenFlag = 0;

    @Transient
    public boolean isAddedTolList = false;


    public Long getUarUarId() {
        return uarUarId;
    }

    public void setUarUarId(Long uarUarId) {
        this.uarUarId = uarUarId;
    }

    public Long getUarUserNo() {
        return uarUserNo;
    }

    public void setUarUserNo(Long uarUserNo) {
        this.uarUserNo = uarUserNo;
    }

    public Long getUarFunctionCode() {
        return uarFunctionCode;
    }

    public void setUarFunctionCode(Long uarFunctionCode) {
        this.uarFunctionCode = uarFunctionCode;
    }

    public String getUarAccessEnableFlag() {
        return uarAccessEnableFlag;
    }

    public void setUarAccessEnableFlag(String uarAccessEnableFlag) {
        this.uarAccessEnableFlag = uarAccessEnableFlag;
    }

    public Integer getUarAccessOverridenFlag() {
        return uarAccessOverridenFlag;
    }

    public void setUarAccessOverridenFlag(Integer uarAccessOverridenFlag) {
        this.uarAccessOverridenFlag = uarAccessOverridenFlag;
    }

    public boolean isAddedTolList() {
        return isAddedTolList;
    }

    public void setAddedTolList(boolean isAddedTolList) {
        this.isAddedTolList = isAddedTolList;
    }

    @Override
    public String toString() {
        return "UserAccessRight{" +
                "uarUarId=" + uarUarId +
                ", uarUserNo=" + uarUserNo +
                ", uarFunctionCode=" + uarFunctionCode +
                ", uarAccessEnableFlag='" + uarAccessEnableFlag + '\'' +
                ", uarAccessOverridenFlag=" + uarAccessOverridenFlag +
                '}';
    }
}

package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.domain.RoleAccessRight;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class RoleResource extends BaseResource {



    private Long rolId;
    private String rolName;
    private String rolDescription;
    private Long rolMerchantNo;
    private Integer rolUserType;
    private Integer rolIsDefaultRole = 0;
    public Set<RoleAccessRight> roleAccessRightSet;

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public String getRolDescription() {
        return rolDescription;
    }

    public void setRolDescription(String rolDescription) {
        this.rolDescription = rolDescription;
    }

    public Long getRolMerchantNo() {
        return rolMerchantNo;
    }

    public void setRolMerchantNo(Long rolMerchantNo) {
        this.rolMerchantNo = rolMerchantNo;
    }

    public Integer getRolUserType() {
        return rolUserType;
    }

    public void setRolUserType(Integer rolUserType) {
        this.rolUserType = rolUserType;
    }

    public Integer getRolIsDefaultRole() {
        return rolIsDefaultRole;
    }

    public void setRolIsDefaultRole(Integer rolIsDefaultRole) {
        this.rolIsDefaultRole = rolIsDefaultRole;
    }

    public Set<RoleAccessRight> getRoleAccessRightSet() {
        return roleAccessRightSet;
    }

    public void setRoleAccessRightSet(Set<RoleAccessRight> roleAccessRightSet) {
        this.roleAccessRightSet = roleAccessRightSet;
    }

    @Override
    public String toString() {
        return "RoleResource{" +
                "rolId=" + rolId +
                ", rolName='" + rolName + '\'' +
                ", rolDescription='" + rolDescription + '\'' +
                ", rolMerchantNo=" + rolMerchantNo +
                ", rolUserType=" + rolUserType +
                ", rolIsDefaultRole=" + rolIsDefaultRole +
                ", roleAccessRightSet=" + roleAccessRightSet +
                '}';
    }


}

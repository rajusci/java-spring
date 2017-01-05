package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.Role;
import com.inspirenetz.api.core.domain.RoleAccessRight;

import java.util.Date;
import java.util.Set;

/**
 * Created by ameenci on 20/9/14.
 */
public class RoleBuilder {
    public Set<RoleAccessRight> roleAccessRightList;
    private Long rolId;
    private String rolName;
    private String rolDescription;
    private Long rolMerchantNo;
    private Integer rolUserType;
    private Integer rolIsDefaultRole = 0;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private RoleBuilder() {
    }

    public static RoleBuilder aRole() {
        return new RoleBuilder();
    }

    public RoleBuilder withRolId(Long rolId) {
        this.rolId = rolId;
        return this;
    }

    public RoleBuilder withRolName(String rolName) {
        this.rolName = rolName;
        return this;
    }

    public RoleBuilder withRolDescription(String rolDescription) {
        this.rolDescription = rolDescription;
        return this;
    }

    public RoleBuilder withRolMerchantNo(Long rolMerchantNo) {
        this.rolMerchantNo = rolMerchantNo;
        return this;
    }

    public RoleBuilder withRolUserType(Integer rolUserType) {
        this.rolUserType = rolUserType;
        return this;
    }

    public RoleBuilder withRolIsDefaultRole(Integer rolIsDefaultRole) {
        this.rolIsDefaultRole = rolIsDefaultRole;
        return this;
    }

    public RoleBuilder withRoleAccessRightList(Set<RoleAccessRight> roleAccessRightList) {
        this.roleAccessRightList = roleAccessRightList;
        return this;
    }

    public RoleBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public RoleBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public RoleBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public RoleBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Role build() {
        Role role = new Role();
        role.setRolId(rolId);
        role.setRolName(rolName);
        role.setRolDescription(rolDescription);
        role.setRolMerchantNo(rolMerchantNo);
        role.setRolUserType(rolUserType);
        role.setRolIsDefaultRole(rolIsDefaultRole);
        role.setRoleAccessRightSet(roleAccessRightList);
        role.setCreatedAt(createdAt);
        role.setCreatedBy(createdBy);
        role.setUpdatedAt(updatedAt);
        role.setUpdatedBy(updatedBy);
        return role;
    }
}

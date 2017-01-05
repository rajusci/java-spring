package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.RoleAccessRight;

/**
 * Created by ameenci on 10/9/14.
 */
public class RoleAccessRightBuilder {
    private Long rarId;
    private Long rarRole;
    private Long rarFunctionCode;

    private RoleAccessRightBuilder() {
    }

    public static RoleAccessRightBuilder aRoleAccessRight() {
        return new RoleAccessRightBuilder();
    }

    public RoleAccessRightBuilder withRarId(Long rarId) {
        this.rarId = rarId;
        return this;
    }

    public RoleAccessRightBuilder withRarRole(Long rarRole) {
        this.rarRole = rarRole;
        return this;
    }

    public RoleAccessRightBuilder withRarFunctionCode(Long rarFunctionCode) {
        this.rarFunctionCode = rarFunctionCode;
        return this;
    }

    public RoleAccessRight build() {
        RoleAccessRight roleAccessRight = new RoleAccessRight();
        roleAccessRight.setRarId(rarId);
        roleAccessRight.setRarRole(rarRole);
        roleAccessRight.setRarFunctionCode(rarFunctionCode);
        return roleAccessRight;
    }
}

package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.UserRole;

import java.util.Date;

/**
 * Created by ameenci on 10/9/14.
 */
public class UserRoleBuilder {
    private Long uerId;
    private Long uerRole;
    private Long uerUserId;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private UserRoleBuilder() {
    }

    public static UserRoleBuilder anUserRole() {
        return new UserRoleBuilder();
    }

    public UserRoleBuilder withUerId(Long uerId) {
        this.uerId = uerId;
        return this;
    }

    public UserRoleBuilder withUerRole(Long uerRole) {
        this.uerRole = uerRole;
        return this;
    }

    public UserRoleBuilder withUerUserId(Long uerUserId) {
        this.uerUserId = uerUserId;
        return this;
    }

    public UserRoleBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserRoleBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public UserRoleBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public UserRoleBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public UserRole build() {
        UserRole userRole = new UserRole();
        userRole.setUerId(uerId);
        userRole.setUerRole(uerRole);
        userRole.setUerUserId(uerUserId);
        userRole.setCreatedAt(createdAt);
        userRole.setCreatedBy(createdBy);
        userRole.setUpdatedAt(updatedAt);
        userRole.setUpdatedBy(updatedBy);
        return userRole;
    }
}

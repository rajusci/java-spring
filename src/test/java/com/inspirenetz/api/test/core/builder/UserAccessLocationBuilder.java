package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.UserAccessLocation;

import java.util.Date;

/**
 * Created by ameenci on 10/9/14.
 */
public class UserAccessLocationBuilder {
    private Long ualId;
    private Long  ualUserId;
    private Long ualLocation;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private UserAccessLocationBuilder() {
    }

    public static UserAccessLocationBuilder anUserAccessLocation() {
        return new UserAccessLocationBuilder();
    }

    public UserAccessLocationBuilder withUalId(Long ualId) {
        this.ualId = ualId;
        return this;
    }

    public UserAccessLocationBuilder withUalUserId(Long ualUserId) {
        this.ualUserId = ualUserId;
        return this;
    }

    public UserAccessLocationBuilder withUalLocation(Long ualLocation) {
        this.ualLocation = ualLocation;
        return this;
    }

    public UserAccessLocationBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserAccessLocationBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public UserAccessLocationBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public UserAccessLocationBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public UserAccessLocation build() {
        UserAccessLocation userAccessLocation = new UserAccessLocation();
        userAccessLocation.setUalId(ualId);
        userAccessLocation.setUalUserId(ualUserId);
        userAccessLocation.setUalLocation(ualLocation);
        userAccessLocation.setCreatedAt(createdAt);
        userAccessLocation.setCreatedBy(createdBy);
        userAccessLocation.setUpdatedAt(updatedAt);
        userAccessLocation.setUpdatedBy(updatedBy);
        return userAccessLocation;
    }
}

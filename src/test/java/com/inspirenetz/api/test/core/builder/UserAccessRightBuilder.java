package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.UserAccessRightAccessEnableFlag;
import com.inspirenetz.api.core.domain.UserAccessRight;

import java.util.Date;

/**
 * Created by sandheepgr on 26/6/14.
 */
public class UserAccessRightBuilder {
    private Long uarUarId;
    private Long uarUserNo =  0L;
    private Long uarFunctionCode = 0L;
    private String uarAccessEnableFlag = UserAccessRightAccessEnableFlag.DISABLED;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private UserAccessRightBuilder() {
    }

    public static UserAccessRightBuilder anUserAccessRight() {
        return new UserAccessRightBuilder();
    }

    public UserAccessRightBuilder withUarUarId(Long uarUarId) {
        this.uarUarId = uarUarId;
        return this;
    }

    public UserAccessRightBuilder withUarUserNo(Long uarUserNo) {
        this.uarUserNo = uarUserNo;
        return this;
    }

    public UserAccessRightBuilder withUarFunctionCode(Long uarFunctionCode) {
        this.uarFunctionCode = uarFunctionCode;
        return this;
    }

    public UserAccessRightBuilder withUarAccessEnableFlag(String uarAccessEnableFlag) {
        this.uarAccessEnableFlag = uarAccessEnableFlag;
        return this;
    }

    public UserAccessRightBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserAccessRightBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public UserAccessRightBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public UserAccessRightBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public UserAccessRight build() {
        UserAccessRight userAccessRight = new UserAccessRight();
        userAccessRight.setUarUarId(uarUarId);
        userAccessRight.setUarUserNo(uarUserNo);
        userAccessRight.setUarFunctionCode(uarFunctionCode);
        userAccessRight.setUarAccessEnableFlag(uarAccessEnableFlag);
        userAccessRight.setCreatedAt(createdAt);
        userAccessRight.setCreatedBy(createdBy);
        userAccessRight.setUpdatedAt(updatedAt);
        userAccessRight.setUpdatedBy(updatedBy);
        return userAccessRight;
    }
}

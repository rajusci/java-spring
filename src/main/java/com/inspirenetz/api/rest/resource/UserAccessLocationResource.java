package com.inspirenetz.api.rest.resource;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by ameenci on 11/9/14.
 */
public class UserAccessLocationResource extends ResourceSupport {


    private Long ualId;
    private Long  ualUserId;
    private Long ualLocation;


    public Long getUalId() {
        return ualId;
    }

    public void setUalId(Long ualId) {
        this.ualId = ualId;
    }

    public Long getUalUserId() {
        return ualUserId;
    }

    public void setUalUserId(Long ualUserId) {
        this.ualUserId = ualUserId;
    }

    public Long getUalLocation() {
        return ualLocation;
    }

    public void setUalLocation(Long ualLocation) {
        this.ualLocation = ualLocation;
    }

    @Override
    public String toString() {
        return "UserAccessLocationResource{" +
                "ualId=" + ualId +
                ", ualUserId=" + ualUserId +
                ", ualLocation=" + ualLocation +
                '}';
    }
}

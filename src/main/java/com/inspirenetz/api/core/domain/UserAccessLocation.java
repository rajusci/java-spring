package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ameenci on 10/9/14.
 */
@Entity
@Table(name = "USER_ACCESS_LOCATION")
public class UserAccessLocation extends AuditedEntity implements Serializable {



    @Column(name = "UAL_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ualId;

    @Column(name = "UAL_USER_ID",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long  ualUserId;

    @Column(name = "UAL_LOCATION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
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
        return "UserAccessLocation{" +
                "ualId=" + ualId +
                ", ualUserId=" + ualUserId +
                ", ualLocation=" + ualLocation +
                '}';
    }
}






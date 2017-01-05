package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ameenci on 10/9/14.
 */
@Entity
@Table(name="USER_ROLE")
public class UserRole extends AuditedEntity implements Serializable {

    @Column(name = "UER_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uerId;

    @Column(name = "UER_ROLE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long uerRole;

    @Column(name = "UER_USER_ID",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long uerUserId;

    public Long getUerUserId() {
        return uerUserId;
    }

    public void setUerUserId(Long uerUserId) {
        this.uerUserId = uerUserId;
    }

    public Long getUerRole() {
        return uerRole;
    }

    public void setUerRole(Long uerRole) {
        this.uerRole = uerRole;
    }



    public Long getUerId() {
        return uerId;
    }

    public void setUerId(Long uerId) {
        this.uerId = uerId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "uerId=" + uerId +
                ", uerRole=" + uerRole +
                ", uerUserId=" + uerUserId +
                '}';
    }
}

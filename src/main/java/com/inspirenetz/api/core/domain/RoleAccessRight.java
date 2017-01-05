package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.NotificationRecepientType;
import com.inspirenetz.api.core.dictionary.NotificationType;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by ameenci on 9/9/14.
 */
@Entity
@Table(name="ROLE_ACCESS_RIGHTS")
public class RoleAccessRight  extends AuditedEntity implements Serializable {

    @Column(name = "RAR_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rarId;

    @Column(name = "RAR_ROLE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long rarRole;

    @Column(name = "RAR_FUNCTION_CODE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long rarFunctionCode;

    public Long getRarId() {
        return rarId;
    }

    public void setRarId(Long rarId) {
        this.rarId = rarId;
    }

    public Long getRarRole() {
        return rarRole;
    }


    public void setRarRole(Long rarRole) {
        this.rarRole = rarRole;
    }

    public Long getRarFunctionCode() {
        return rarFunctionCode;
    }

    public void setRarFunctionCode(Long rarFunctionCode) {
        this.rarFunctionCode = rarFunctionCode;
    }

    @Override
    public String toString() {
        return "RoleAccessRight{" +
                "rarId=" + rarId +
                ", rarRole=" + rarRole +
                ", rarFunctionCode=" + rarFunctionCode +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleAccessRight that = (RoleAccessRight) o;

        if (!rarFunctionCode.equals(that.rarFunctionCode)) return false;
        if (rarId != null ? !rarId.equals(that.rarId) : that.rarId != null) return false;
        if (rarRole != null ? !rarRole.equals(that.rarRole) : that.rarRole != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rarId != null ? rarId.hashCode() : 0;
        result = 31 * result + (rarRole != null ? rarRole.hashCode() : 0);
        result = 31 * result + rarFunctionCode.hashCode();
        return result;
    }
}

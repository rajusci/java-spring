package com.inspirenetz.api.core.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by ameenci on 9/10/14.
 */
@Entity
@Table(name="PASSWORD_HISTORY")
public class PasswordHistory extends AuditedEntity {

    @Column(name = "PAS_HISTORY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pasHistoryId;

    @Column(name = "PAS_HISTORY_USER_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long pasHistoryUserNo;

    @Column(name = "PAS_HISTORY_PASSWORD",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String pasHistoryPassword;

    @Column(name = "PAS_CHANGED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pasChangedAt;


    public Long getPasHistoryId() {
        return pasHistoryId;
    }

    public void setPasHistoryId(Long pasHistoryId) {
        this.pasHistoryId = pasHistoryId;
    }

    public Long getPasHistoryUserNo() {
        return pasHistoryUserNo;
    }

    public void setPasHistoryUserNo(Long pasHistoryUserNo) {
        this.pasHistoryUserNo = pasHistoryUserNo;
    }

    public String getPasHistoryPassword() {
        return pasHistoryPassword;
    }

    public void setPasHistoryPassword(String pasHistoryPassword) {
        this.pasHistoryPassword = pasHistoryPassword;
    }

    public Date getPasChangedAt() {
        return pasChangedAt;
    }

    public void setPasChangedAt(Date pasChangedAt) {
        this.pasChangedAt = pasChangedAt;
    }


    @Override
    public String toString() {
        return "PasswordHistory{" +
                "pasHistoryId=" + pasHistoryId +
                ", pasHistoryUserNo=" + pasHistoryUserNo +
                ", pasHistoryPassword='" + pasHistoryPassword + '\'' +
                ", pasChangedAt=" + pasChangedAt +
                '}';
    }
}

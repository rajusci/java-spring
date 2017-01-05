package com.inspirenetz.api.core.domain;

/**
 * Created by sandheepgr on 16/2/14.
 */

import com.inspirenetz.api.core.dictionary.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * User: ffl
 * Date: 8/11/13
 * Time: 3:32 PM
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    protected Long id;

    @Column(name = "version")
    @Version
    private Long version;

    @Column(name = "status", nullable = false, columnDefinition = "ENUM('ACTIVE','INACTIVE') default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    @Basic(fetch = FetchType.EAGER)
    private Status status;

    @Column(name = "uid", unique = true, nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    @PrePersist
    public void init() {
        this.uid = UUID.randomUUID().toString();
        this.status = Status.ACTIVE;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;

        BaseEntity other = (BaseEntity) object;
        if (this.getId() != other.getId() && (this.getId() == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", version=" + version +
                ", status=" + status +
                ", uid='" + uid + '\'' +
                "} " + super.toString();
    }
}
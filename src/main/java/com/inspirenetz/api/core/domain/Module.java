package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;

import javax.persistence.*;

/**
 * Created by sandheepgr on 26/6/14.
 */
@Entity
@Table(name = "MODULES")
public class Module extends AuditedEntity{


    @Id
    @Column(name = "MDL_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mdlId;

    @Basic
    @Column(name = "MDL_NAME", nullable = false, insertable = true, updatable = true, length = 200, precision = 0)
    private String mdlName = "";

    @Basic
    @Column(name = "MDL_DEFAULT_ENABLED", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer mdlDefaultEnabled = IndicatorStatus.NO;

    @Basic
    @Column(name = "MDL_COMMENT", nullable = true, insertable = true, updatable = true, length = 1000, precision = 0)
    private String mdlComment = "";



    public Long getMdlId() {
        return mdlId;
    }

    public void setMdlId(Long mdlId) {
        this.mdlId = mdlId;
    }

    public String getMdlName() {
        return mdlName;
    }

    public void setMdlName(String mdlName) {
        this.mdlName = mdlName;
    }

    public Integer getMdlDefaultEnabled() {
        return mdlDefaultEnabled;
    }

    public void setMdlDefaultEnabled(Integer mdlDefaultEnabled) {
        this.mdlDefaultEnabled = mdlDefaultEnabled;
    }

    public String getMdlComment() {
        return mdlComment;
    }

    public void setMdlComment(String mdlComment) {
        this.mdlComment = mdlComment;
    }



    @Override
    public String toString() {
        return "Module{" +
                "mdlId=" + mdlId +
                ", mdlName='" + mdlName + '\'' +
                ", mdlDefaultEnabled=" + mdlDefaultEnabled +
                ", mdlComment='" + mdlComment + '\'' +
                '}';
    }
}

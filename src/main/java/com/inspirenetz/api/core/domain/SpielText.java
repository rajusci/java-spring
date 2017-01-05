package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by alameen on 2/2/15.
 */
@Entity
@Table(name="SPIEL_TEXT")
public class SpielText extends AuditedEntity implements Serializable {

    @Column(name = "SPT_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sptId;

    @Column(name = "SPT_REF",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long sptRef;

    @Column(name = "SPT_CHANNEL",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer  sptChannel;

    @Column(name = "SPT_LOCATION",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long sptLocation ;

    @Column(name = "SPT_DESCRIPTION",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String sptDescription;

    public Long getSptId() {
        return sptId;
    }

    public void setSptId(Long sptId) {
        this.sptId = sptId;
    }

    public Long getSptRef() {
        return sptRef;
    }

    public void setSptRef(Long sptRef) {
        this.sptRef = sptRef;
    }

    public Integer getSptChannel() {
        return sptChannel;
    }

    public void setSptChannel(Integer sptChannel) {
        this.sptChannel = sptChannel;
    }

    public Long getSptLocation() {
        return sptLocation;
    }

    public void setSptLocation(Long sptLocation) {
        this.sptLocation = sptLocation;
    }

    public String getSptDescription() {
        return sptDescription;
    }

    public void setSptDescription(String sptDescription) {
        this.sptDescription = sptDescription;
    }

    @Override
    public String toString() {
        return "SpielText{" +
                "sptId=" + sptId +
                ", sptRef=" + sptRef +
                ", sptChannel=" + sptChannel +
                ", sptLocation=" + sptLocation +
                ", sptDescription='" + sptDescription + '\'' +
                '}';
    }
}

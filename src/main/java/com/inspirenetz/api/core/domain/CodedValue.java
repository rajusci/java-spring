package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "CODED_VALUES")
public class CodedValue  {

    @Id
    @Column(name = "CDV_CODEID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdvCodeId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CDV_INDEX")
    private int cdvIndex;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CDV_CODE_VALUE")
    private int cdvCodeValue;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CDV_CODE_LABEL")
    @Size(max=100, message = "{codedvalue.cdvcodelabel.size}")
    private String cdvCodeLabel;


    public Long getCdvCodeId() {
        return cdvCodeId;
    }

    public void setCdvCodeId(Long cdvCodeId) {
        this.cdvCodeId = cdvCodeId;
    }

    public int getCdvIndex() {
        return cdvIndex;
    }

    public void setCdvIndex(int cdvIndex) {
        this.cdvIndex = cdvIndex;
    }

    public int getCdvCodeValue() {
        return cdvCodeValue;
    }

    public void setCdvCodeValue(int cdvCodeValue) {
        this.cdvCodeValue = cdvCodeValue;
    }

    public String getCdvCodeLabel() {
        return cdvCodeLabel;
    }

    public void setCdvCodeLabel(String cdvCodeLabel) {
        this.cdvCodeLabel = cdvCodeLabel;
    }


    @Override
    public String toString() {
        return "CodedValue{" +
                "cdvCodeId=" + cdvCodeId +
                ", cdvIndex=" + cdvIndex +
                ", cdvCodeValue=" + cdvCodeValue +
                ", cdvCodeLabel='" + cdvCodeLabel + '\'' +
                '}';
    }
}

package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "COALITIONS")
public class Coalition {


    @Id
    @Column(name = "COA_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int coaId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "COA_NAME")
    private String coaName;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "COA_LOCATION")
    private int coaLocation;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "COA_DESCRIPTION")
    private String coaDescription;



    public int getCoaId() {
        return coaId;
    }

    public void setCoaId(int coaId) {
        this.coaId = coaId;
    }

    public String getCoaName() {
        return coaName;
    }

    public void setCoaName(String coaName) {
        this.coaName = coaName;
    }

    public int getCoaLocation() {
        return coaLocation;
    }

    public void setCoaLocation(int coaLocation) {
        this.coaLocation = coaLocation;
    }

    public String getCoaDescription() {
        return coaDescription;
    }

    public void setCoaDescription(String coaDescription) {
        this.coaDescription = coaDescription;
    }


    @Override
    public String toString() {
        return "Coalition{" +
                "coaId=" + coaId +
                ", coaName='" + coaName + '\'' +
                ", coaLocation=" + coaLocation +
                ", coaDescription='" + coaDescription + '\'' +
                '}';
    }
}

package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "COALITION_LOCATIONS")
public class CoalitionLocation {
    @Id
    @Column(name = "COL_ID")
    private int colId;
    @Basic
    @Column(name = "COL_TYPE")
    private int colType;
    @Basic
    @Column(name = "COL_NAME")
    private String colName;
    @Basic
    @Column(name = "COL_DESCRIPTION")
    private String colDescription;

    public int getColId() {
        return colId;
    }

    public void setColId(int colId) {
        this.colId = colId;
    }

    public int getColType() {
        return colType;
    }

    public void setColType(int colType) {
        this.colType = colType;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColDescription() {
        return colDescription;
    }

    public void setColDescription(String colDescription) {
        this.colDescription = colDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoalitionLocation that = (CoalitionLocation) o;

        if (colId != that.colId) return false;
        if (colType != that.colType) return false;
        if (colDescription != null ? !colDescription.equals(that.colDescription) : that.colDescription != null)
            return false;
        if (colName != null ? !colName.equals(that.colName) : that.colName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = colId;
        result = 31 * result + colType;
        result = 31 * result + (colName != null ? colName.hashCode() : 0);
        result = 31 * result + (colDescription != null ? colDescription.hashCode() : 0);
        return result;
    }
}

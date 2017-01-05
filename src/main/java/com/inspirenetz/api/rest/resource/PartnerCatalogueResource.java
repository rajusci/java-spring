package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.domain.PropertyGroup;

import java.util.Set;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class PartnerCatalogueResource extends BaseResource {



    private Long pacId;
    private Long pacImage;
    private Long pacPartnerNo;
    private String pacName;
    private String pacCode;
    private String pacDescription;
    private Double pacCost;
    private int pacCategory;
    private int pacFulfilmentType;
    private int pacStatus;
    private Double pacStock;
    private String pacImagePath;
    private Set<PropertyGroup> propertyGroups;

    public Long getPacId() {
        return pacId;
    }

    public void setPacId(Long pacId) {
        this.pacId = pacId;
    }

    public Long getPacImage() {
        return pacImage;
    }

    public void setPacImage(Long pacImage) {
        this.pacImage = pacImage;
    }

    public Long getPacPartnerNo() {
        return pacPartnerNo;
    }

    public void setPacPartnerNo(Long pacPartnerNo) {
        this.pacPartnerNo = pacPartnerNo;
    }

    public String getPacName() {
        return pacName;
    }

    public void setPacName(String pacName) {
        this.pacName = pacName;
    }

    public String getPacCode() {
        return pacCode;
    }

    public void setPacCode(String pacCode) {
        this.pacCode = pacCode;
    }

    public String getPacDescription() {
        return pacDescription;
    }

    public void setPacDescription(String pacDescription) {
        this.pacDescription = pacDescription;
    }

    public Double getPacCost() {
        return pacCost;
    }

    public void setPacCost(Double pacCost) {
        this.pacCost = pacCost;
    }

    public int getPacCategory() {
        return pacCategory;
    }

    public void setPacCategory(int pacCategory) {
        this.pacCategory = pacCategory;
    }

    public int getPacFulfilmentType() {
        return pacFulfilmentType;
    }

    public void setPacFulfilmentType(int pacFulfilmentType) {
        this.pacFulfilmentType = pacFulfilmentType;
    }

    public int getPacStatus() {
        return pacStatus;
    }

    public void setPacStatus(int pacStatus) {
        this.pacStatus = pacStatus;
    }

    public Set<PropertyGroup> getPropertyGroups() {
        return propertyGroups;
    }

    public void setPropertyGroups(Set<PropertyGroup> propertyGroups) {
        this.propertyGroups = propertyGroups;
    }

    public Double getPacStock() {
        return pacStock;
    }

    public void setPacStock(Double pacStock) {
        this.pacStock = pacStock;
    }

    public String getPacImagePath() {
        return pacImagePath;
    }

    public void setPacImagePath(String pacImagePath) {
        this.pacImagePath = pacImagePath;
    }
}

package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.FulfilmentType;
import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.PartnerCatalogueStatus;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneeshci on 13/07/16.
 */
@Entity
@Table(name="PARTNER_CATALOGUE")
public class PartnerCatalogue extends AuditedEntity {


    @Column(name = "PAC_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pacId;

    @Column(name = "PAC_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String pacName;

    @Column(name = "PAC_CODE" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String pacCode;

    @Column(name = "PAC_DESCRIPTION" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String pacDescription;

    @Column(name = "PAC_COST" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Double pacCost;

    @Column(name = "PAC_STOCK" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Double pacStock;

    @Column(name = "PAC_IMAGE" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long pacImage =  ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE;

    @Column(name = "PAC_PARTNER_NO" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long pacPartnerNo;

    @Column(name = "PAC_CATEGORY" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private int pacCategory;

    @Column(name = "PAC_FULFILMENT_TYPE" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private int pacFulfilmentType = FulfilmentType.MERCHANT_FULFILLED;

    @Column(name = "PAC_STATUS" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int pacStatus = PartnerCatalogueStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PAC_IMAGE",insertable = false,updatable = false)
    private Image image;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "PAC_ID")
    @Fetch(value = FetchMode.JOIN)
    Set<PropertyGroup> propertyGroups = new HashSet<>(0);

    @Column(name = "PAC_ADDED_USER" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String pacAddedUser;

    public Long getPacId() {
        return pacId;
    }

    public void setPacId(Long pacId) {
        this.pacId = pacId;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public String getPacAddedUser() {
        return pacAddedUser;
    }

    public void setPacAddedUser(String pacAddedUser) {
        this.pacAddedUser = pacAddedUser;
    }

    @Override
    public String toString() {
        return "PartnerCatalogue{" +
                "pacId=" + pacId +
                ", pacName='" + pacName + '\'' +
                ", pacCode='" + pacCode + '\'' +
                ", pacDescription='" + pacDescription + '\'' +
                ", pacCost=" + pacCost +
                ", pacImage=" + pacImage +
                ", pacPartnerNo=" + pacPartnerNo +
                ", pacCategory=" + pacCategory +
                ", pacFulfilmentType=" + pacFulfilmentType +
                ", pacStatus=" + pacStatus +
                '}';
    }
}

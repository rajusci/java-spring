package com.inspirenetz.api.core.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Entity
@Table(name="PRODUCT_CATEGORIES")
public class ProductCategory extends AuditedEntity {

    @Column(name = "PCY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pcyId;


    @Column(name = "PCY_CODE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{productcategory.pcycode.notempty}")
    @Size(min=1,max=20,message = "{productcategory.pcycode.size}")
    private String pcyCode;


    @Column(name = "PCY_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long pcyMerchantNo;


    @Column(name = "PCY_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{productcategory.pcyname.notempty}")
    @Size(min=1,max=100,message = "{productcategory.pcyname.size}")
    private String pcyName;

    @Column(name = "PCY_DESCRIPTION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=200,message = "{productcategory.pcydescription.size}")
    private String pcyDescription;


    public Long getPcyId() {
        return pcyId;
    }

    public void setPcyId(Long pcyId) {
        this.pcyId = pcyId;
    }

    public String getPcyCode() {
        return pcyCode;
    }

    public void setPcyCode(String pcyCode) {
        this.pcyCode = pcyCode;
    }

    public Long getPcyMerchantNo() {
        return pcyMerchantNo;
    }

    public void setPcyMerchantNo(Long pcyMerchantNo) {
        this.pcyMerchantNo = pcyMerchantNo;
    }

    public String getPcyName() {
        return pcyName;
    }

    public void setPcyName(String pcyName) {
        this.pcyName = pcyName;
    }

    public String getPcyDescription() {
        return pcyDescription;
    }

    public void setPcyDescription(String pcyDescription) {
        this.pcyDescription = pcyDescription;
    }


    @Override
    public String toString() {
        return "ProductCategory{" +
                "pcyId=" + pcyId +
                ", pcyCode='" + pcyCode + '\'' +
                ", pcyMerchantNo=" + pcyMerchantNo +
                ", pcyName='" + pcyName + '\'' +
                ", pcyDescription='" + pcyDescription + '\'' +
                '}';
    }
}


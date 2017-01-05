package com.inspirenetz.api.core.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Entity
@Table(name="BRANDS")
public class Brand extends AuditedEntity implements Serializable {

    @Column(name = "BRN_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brnId;


    @Column(name = "BRN_CODE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{brand.brncode.notempty}")
    @Size(min=1,max=20,message = "{brand.brncode.size}")
    private String brnCode;


    @Column(name = "BRN_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long brnMerchantNo;


    @Column(name = "BRN_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{brand.brnname.notempty}")
    @Size(min=1,max=100,message = "{brand.brnname.size}")
    private String brnName;

    @Column(name = "BRN_DESCRIPTION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=200,message = "{brand.brndescription.size}")
    private String brnDescription;


    public Long getBrnId() {
        return brnId;
    }

    public void setBrnId(Long brnId) {
        this.brnId = brnId;
    }

    public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }

    public Long getBrnMerchantNo() {
        return brnMerchantNo;
    }

    public void setBrnMerchantNo(Long brnMerchantNo) {
        this.brnMerchantNo = brnMerchantNo;
    }

    public String getBrnName() {
        return brnName;
    }

    public void setBrnName(String brnName) {
        this.brnName = brnName;
    }

    public String getBrnDescription() {
        return brnDescription;
    }

    public void setBrnDescription(String brnDescription) {
        this.brnDescription = brnDescription;
    }


    @Override
    public String toString() {
        return "Brand{" +
                "brnId=" + brnId +
                ", brnCode='" + brnCode + '\'' +
                ", brnMerchantNo=" + brnMerchantNo +
                ", brnName='" + brnName + '\'' +
                ", brnDescription='" + brnDescription + '\'' +
                '}';
    }
}

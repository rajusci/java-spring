package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by ameenci on 8/9/14.
 */
@Entity
@Table (name = "MESSAGE_SPIEL")
public class MessageSpiel extends AuditedEntity implements Serializable {

    @Column(name = "MSI_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msiId;

    @Column(name = "MSI_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String msiName;

    @Column(name = "MSI_TARIFF_CLASS",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String msiTariffClass;

    @Column(name = "MSI_DESCRIPTION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String msiDescription;

    @Column(name = "MSI_MERCHANT_NO",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long msiMerchantNo;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="SPT_REF")
    public Set<SpielText> spielTexts;

    public Long getMsiId() {
        return msiId;
    }

    public void setMsiId(Long msiId) {
        this.msiId = msiId;
    }

    public String getMsiName() {
        return msiName;
    }

    public void setMsiName(String msiName) {
        this.msiName = msiName;
    }

    public String getMsiTariffClass() {
        return msiTariffClass;
    }

    public void setMsiTariffClass(String msiTariffClass) {
        this.msiTariffClass = msiTariffClass;
    }

    public Set<SpielText> getSpielTexts() {
        return spielTexts;
    }

    public void setSpielTexts(Set<SpielText> spielTexts) {
        this.spielTexts = spielTexts;
    }

    public Long getMsiMerchantNo() {
        return msiMerchantNo;
    }

    public void setMsiMerchantNo(Long msiMerchantNo) {
        this.msiMerchantNo = msiMerchantNo;
    }

    public String getMsiDescription() {
        return msiDescription;
    }

    public void setMsiDescription(String msiDescription) {
        this.msiDescription = msiDescription;
    }

    @Override
    public String toString() {
        return "MessageSpiel{" +
                "msiId=" + msiId +
                ", msiName='" + msiName + '\'' +
                ", msiTariffClass='" + msiTariffClass + '\'' +
                ", msiDescription='" + msiDescription + '\'' +
                ", msiMerchantNo=" + msiMerchantNo +
                ", spielTexts=" + spielTexts +
                '}';
    }
}

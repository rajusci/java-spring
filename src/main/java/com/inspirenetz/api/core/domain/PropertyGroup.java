package com.inspirenetz.api.core.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneeshci on 27/09/14.
 */
@Entity
@Table(name="PROPERTY_GROUP")
public class PropertyGroup extends AuditedEntity {


    @Column(name = "PPG_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ppgId;

    @Column(name = "PPG_NAME" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String ppgName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "PPG_ID")
    @Fetch(value = FetchMode.JOIN)
    Set<ProductProperties> productProperties = new HashSet<>(0);

    public Long getPpgId() {
        return ppgId;
    }

    public void setPpgId(Long ppgId) {
        this.ppgId = ppgId;
    }

    public String getPpgName() {
        return ppgName;
    }

    public void setPpgName(String ppgName) {
        this.ppgName = ppgName;
    }

    public Set<ProductProperties> getProductProperties() {
        return productProperties;
    }

    public void setProductProperties(Set<ProductProperties> productProperties) {
        this.productProperties = productProperties;
    }

    @Override
    public String toString() {
        return "PropertyGroup{" +
                "ppgId=" + ppgId +
                ", ppgName=" + ppgName +
                '}';
    }
}

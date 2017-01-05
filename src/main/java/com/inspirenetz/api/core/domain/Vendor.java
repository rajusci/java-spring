package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by sandheepgr on 31/7/14.
 */
@Entity
@Table(name = "VENDORS")
public class Vendor extends AuditedEntity {

    @Id
    @Column(name = "VEN_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long venId;

    @Basic
    @Column(name = "VEN_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long venMerchantNo = 1L ;

    @Basic
    @Column(name = "VEN_NAME", nullable = false, insertable = true, updatable = true, length = 200, precision = 0)
    @NotEmpty(message = "{vendor.venname.notempty}")
    @Size(min=1,max=50,message = "{vendor.venname.size}")
    private String venName = "";

    @Basic
    @Column(name = "VEN_DESCRIPTION", nullable = false, insertable = true, updatable = true, length = 500, precision = 0)
    @Size(max=100,message = "{vendor.vendescription.size}")
    private String venDescription = "";

    @Basic
    @Column(name = "VEN_CATEGORY", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long venCategory = 1L;

    @Basic
    @Column(name = "VEN_IMAGE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long venImage = ImagePrimaryId.PRIMARY_MERCHANT_VENDOR_IMAGE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VEN_IMAGE",insertable = false,updatable = false)
    private Image image;


    public Long getVenId() {
        return venId;
    }

    public void setVenId(Long venId) {
        this.venId = venId;
    }

    public Long getVenMerchantNo() {
        return venMerchantNo;
    }

    public void setVenMerchantNo(Long venMerchantNo) {
        this.venMerchantNo = venMerchantNo;
    }

    public String getVenName() {
        return venName;
    }

    public void setVenName(String venName) {
        this.venName = venName;
    }

    public String getVenDescription() {
        return venDescription;
    }

    public void setVenDescription(String venDescription) {
        this.venDescription = venDescription;
    }

    public Long getVenCategory() {
        return venCategory;
    }

    public void setVenCategory(Long venCategory) {
        this.venCategory = venCategory;
    }

    public Long getVenImage() {
        return venImage;
    }

    public void setVenImage(Long venImage) {
        this.venImage = venImage;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}

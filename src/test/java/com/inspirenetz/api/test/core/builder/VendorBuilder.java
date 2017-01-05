package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.Vendor;

import java.util.Date;

/**
 * Created by sandheepgr on 31/7/14.
 */
public class VendorBuilder {
    private Long venId;
    private Long venMerchantNo = 1L ;
    private String venName = "";
    private String venDescription = "";
    private Long venCategory = 1L;
    private Long venImage = ImagePrimaryId.PRIMARY_MERCHANT_VENDOR_IMAGE;
    private Image image;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private VendorBuilder() {
    }

    public static VendorBuilder aVendor() {
        return new VendorBuilder();
    }

    public VendorBuilder withVenId(Long venId) {
        this.venId = venId;
        return this;
    }

    public VendorBuilder withVenMerchantNo(Long venMerchantNo) {
        this.venMerchantNo = venMerchantNo;
        return this;
    }

    public VendorBuilder withVenName(String venName) {
        this.venName = venName;
        return this;
    }

    public VendorBuilder withVenDescription(String venDescription) {
        this.venDescription = venDescription;
        return this;
    }

    public VendorBuilder withVenCategory(Long venCategory) {
        this.venCategory = venCategory;
        return this;
    }

    public VendorBuilder withVenImage(Long venImage) {
        this.venImage = venImage;
        return this;
    }

    public VendorBuilder withImage(Image image) {
        this.image = image;
        return this;
    }

    public VendorBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public VendorBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public VendorBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public VendorBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Vendor build() {
        Vendor vendor = new Vendor();
        vendor.setVenId(venId);
        vendor.setVenMerchantNo(venMerchantNo);
        vendor.setVenName(venName);
        vendor.setVenDescription(venDescription);
        vendor.setVenCategory(venCategory);
        vendor.setVenImage(venImage);
        vendor.setImage(image);
        vendor.setCreatedAt(createdAt);
        vendor.setCreatedBy(createdBy);
        vendor.setUpdatedAt(updatedAt);
        vendor.setUpdatedBy(updatedBy);
        return vendor;
    }
}

package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;

/**
 * Created by sandheepgr on 1/8/14.
 */
public class VendorResource  extends BaseResource {

    private Long venId;

    private Long venMerchantNo = 1L ;

    private String venName = "";

    private String venDescription = "";

    private Long venCategory = 1L;

    private Long venImage = ImagePrimaryId.PRIMARY_MERCHANT_VENDOR_IMAGE;

    private String venImagePath = "";



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

    public String getVenImagePath() {
        return venImagePath;
    }

    public void setVenImagePath(String venImagePath) {
        this.venImagePath = venImagePath;
    }
}

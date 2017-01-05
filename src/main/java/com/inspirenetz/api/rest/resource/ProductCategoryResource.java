package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 20/4/14.
 */
public class ProductCategoryResource extends BaseResource {

    private Long pcyId;

    private String pcyCode;

    private String pcyName;

    private String pcyDescription;


    public Long getPcyId() {
        return pcyId;
    }

    public void setPcyId(Long pcyId) {
        this.pcyId = pcyId;
    }

    public String getPcyDescription() {
        return pcyDescription;
    }

    public void setPcyDescription(String pcyDescription) {
        this.pcyDescription = pcyDescription;
    }

    public String getPcyCode() {
        return pcyCode;
    }

    public void setPcyCode(String pcyCode) {
        this.pcyCode = pcyCode;
    }

    public String getPcyName() {
        return pcyName;
    }

    public void setPcyName(String pcyName) {
        this.pcyName = pcyName;
    }
}

package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class BrandResource extends BaseResource {

    private Long brnId;

    private String brnCode;

    private String brnName;

    private String brnDescription;




    public Long getBrnId() {
        return brnId;
    }

    public void setBrnId(Long brnId) {
        this.brnId = brnId;
    }

    public String getBrnDescription() {
        return brnDescription;
    }

    public void setBrnDescription(String brnDescription) {
        this.brnDescription = brnDescription;
    }

    public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }

    public String getBrnName() {
        return brnName;
    }

    public void setBrnName(String brnName) {
        this.brnName = brnName;
    }
}

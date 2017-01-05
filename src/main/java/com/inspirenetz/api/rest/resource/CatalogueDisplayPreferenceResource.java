package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class CatalogueDisplayPreferenceResource extends BaseResource {



    private Long cdpId;
    private Long cdpMerchantNo;
    private String cdpPreferences;


    public Long getCdpId() {
        return cdpId;
    }

    public void setCdpId(Long cdpId) {
        this.cdpId = cdpId;
    }

    public Long getCdpMerchantNo() {
        return cdpMerchantNo;
    }

    public void setCdpMerchantNo(Long cdpMerchantNo) {
        this.cdpMerchantNo = cdpMerchantNo;
    }

    public String getCdpPreferences() {
        return cdpPreferences;
    }

    public void setCdpPreferences(String cdpPreference) {
        this.cdpPreferences = cdpPreference;
    }

    @Override
    public String toString() {
        return "CatalogueDisplayPreferenceResource{" +
                "cdpId=" + cdpId +
                ", cdpMerchantNo=" + cdpMerchantNo +
                ", cdpPreference='" + cdpPreferences + '\'' +
                '}';
    }
}

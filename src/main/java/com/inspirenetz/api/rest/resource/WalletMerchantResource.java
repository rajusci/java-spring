package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 21/4/14.
 */
public class WalletMerchantResource extends  BaseResource {

    private Long wmtMerchantNo;

    private String wmtName;

    private String wmtLocation;

    private String wmtImagePath;


    public Long getWmtMerchantNo() {
        return wmtMerchantNo;
    }

    public void setWmtMerchantNo(Long wmtMerchantNo) {
        this.wmtMerchantNo = wmtMerchantNo;
    }

    public String getWmtName() {
        return wmtName;
    }

    public void setWmtName(String wmtName) {
        this.wmtName = wmtName;
    }

    public String getWmtLocation() {
        return wmtLocation;
    }

    public void setWmtLocation(String wmtLocation) {
        this.wmtLocation = wmtLocation;
    }

    public String getWmtImagePath() {
        return wmtImagePath;
    }

    public void setWmtImagePath(String wmtImagePath) {
        this.wmtImagePath = wmtImagePath;
    }
}

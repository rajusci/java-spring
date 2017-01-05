package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 1/9/14.
 */
public class CustomerInfoResource extends BaseResource {


    private Long tieId;

    private String tieName;

    private Integer tieIsTransferPointsAllowedInd;

    private String tieImagePath;


    private String cusCustomerNo;

    private String cusLoyaltyId;

    private Long cusMerchantNo;

    private boolean isPrimary;

    private String merchantName;



    public Long getTieId() {
        return tieId;
    }

    public void setTieId(Long tieId) {
        this.tieId = tieId;
    }

    public String getTieName() {
        return tieName;
    }

    public void setTieName(String tieName) {
        this.tieName = tieName;
    }

    public Integer getTieIsTransferPointsAllowedInd() {
        return tieIsTransferPointsAllowedInd;
    }

    public void setTieIsTransferPointsAllowedInd(Integer tieIsTransferPointsAllowedInd) {
        this.tieIsTransferPointsAllowedInd = tieIsTransferPointsAllowedInd;
    }


    public String getCusCustomerNo() {
        return cusCustomerNo;
    }

    public void setCusCustomerNo(String cusCustomerNo) {
        this.cusCustomerNo = cusCustomerNo;
    }

    public String getCusLoyaltyId() {
        return cusLoyaltyId;
    }

    public void setCusLoyaltyId(String cusLoyaltyId) {
        this.cusLoyaltyId = cusLoyaltyId;
    }

    public Long getCusMerchantNo() {
        return cusMerchantNo;
    }

    public void setCusMerchantNo(Long cusMerchantNo) {
        this.cusMerchantNo = cusMerchantNo;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTieImagePath() {
        return tieImagePath;
    }

    public void setTieImagePath(String tieImagePath) {
        this.tieImagePath = tieImagePath;
    }

    @Override
    public String toString() {
        return "CustomerInfoResource{" +
                "tieId=" + tieId +
                ", tieName='" + tieName + '\'' +
                ", tieIsTransferPointsAllowedInd=" + tieIsTransferPointsAllowedInd +
                ", tieImagePath='" + tieImagePath + '\'' +
                ", cusCustomerNo='" + cusCustomerNo + '\'' +
                ", cusLoyaltyId='" + cusLoyaltyId + '\'' +
                ", cusMerchantNo=" + cusMerchantNo +
                ", isPrimary=" + isPrimary +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}

package com.inspirenetz.api.rest.resource;

/**
 * Created by ameen on 21/10/15.
 */
public class CardNumberInfoRequestResource extends BaseResource {

    private Long cniMerchantNo;

    private Long cniCardType;

    private String cniBatchName;

    private String filePath;

    public Long getCniMerchantNo() {
        return cniMerchantNo;
    }

    public void setCniMerchantNo(Long cniMerchantNo) {
        this.cniMerchantNo = cniMerchantNo;
    }

    public Long getCniCardType() {
        return cniCardType;
    }

    public void setCniCardType(Long cniCardType) {
        this.cniCardType = cniCardType;
    }

    public String getCniBatchName() {
        return cniBatchName;
    }

    public void setCniBatchName(String cniBatchName) {
        this.cniBatchName = cniBatchName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "CardNumberInfoResource{" +
                "cniMerchantNo=" + cniMerchantNo +
                ", cniCardType=" + cniCardType +
                ", cniBatchName='" + cniBatchName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}

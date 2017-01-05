package com.inspirenetz.api.core.dictionary;

import java.util.List;

/**
 * Created by sandheepgr on 12/5/14.
 */
public class CatalogueRedemptionResponse {

    private String status;

    private APIErrorCode errorcode;

    private List<CatalogueRedemptionItemResponse> catalogueRedemptionItemResponseList;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public APIErrorCode getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(APIErrorCode errorcode) {
        this.errorcode = errorcode;
    }

    public List<CatalogueRedemptionItemResponse> getCatalogueRedemptionItemResponseList() {
        return catalogueRedemptionItemResponseList;
    }

    public void setCatalogueRedemptionItemResponseList(List<CatalogueRedemptionItemResponse> catalogueRedemptionItemResponseList) {
        this.catalogueRedemptionItemResponseList = catalogueRedemptionItemResponseList;
    }


    @Override
    public String toString() {
        return "CatalogueRedemptionResponse{" +
                "status='" + status + '\'' +
                ", errorcode=" + errorcode +
                ", catalogueRedemptionItemResponseList=" + catalogueRedemptionItemResponseList +
                '}';
    }
}

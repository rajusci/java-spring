package com.inspirenetz.api.rest.resource;

/**
 * Created by alameen on 21/10/14.
 */
public class CatalogueFavoriteResource extends BaseResource{


    private Long cafId;

    private Long cafProductNo;

    private String cafLoyaltyId;

    private Integer cafFavoriteFlag;

    public Long getCafId() {
        return cafId;
    }

    public void setCafId(Long cafId) {
        this.cafId = cafId;
    }

    public Long getCafProductNo() {
        return cafProductNo;
    }

    public void setCafProductNo(Long cafProductNo) {
        this.cafProductNo = cafProductNo;
    }

    public String getCafLoyaltyId() {
        return cafLoyaltyId;
    }

    public void setCafLoyaltyId(String cafLoyaltyId) {
        this.cafLoyaltyId = cafLoyaltyId;
    }

    public Integer getCafFavoriteFlag() {
        return cafFavoriteFlag;
    }

    public void setCafFavoriteFlag(Integer cafFavoriteFlag) {
        this.cafFavoriteFlag = cafFavoriteFlag;
    }
}

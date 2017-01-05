package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.CatalogueFavorite;

/**
 * Created by alameen on 21/10/14.
 */
public class CatalogueFavoriteBuilder {
    private Long cafId;
    private Long cafProductNo;
    private String cafLoyaltyId;
    private Integer cafFavoriteFlag;

    private CatalogueFavoriteBuilder() {
    }

    public static CatalogueFavoriteBuilder aCatalogueFavorite() {
        return new CatalogueFavoriteBuilder();
    }

    public CatalogueFavoriteBuilder withCafId(Long cafId) {
        this.cafId = cafId;
        return this;
    }

    public CatalogueFavoriteBuilder withCafProductNo(Long cafProductNo) {
        this.cafProductNo = cafProductNo;
        return this;
    }

    public CatalogueFavoriteBuilder withCafLoyaltyId(String cafLoyaltyId) {
        this.cafLoyaltyId = cafLoyaltyId;
        return this;
    }

    public CatalogueFavoriteBuilder withCafFavoriteFlag(Integer cafFavoriteFlag) {
        this.cafFavoriteFlag = cafFavoriteFlag;
        return this;
    }

    public CatalogueFavoriteBuilder but() {
        return aCatalogueFavorite().withCafId(cafId).withCafProductNo(cafProductNo).withCafLoyaltyId(cafLoyaltyId).withCafFavoriteFlag(cafFavoriteFlag);
    }

    public CatalogueFavorite build() {
        CatalogueFavorite catalogueFavorite = new CatalogueFavorite();
        catalogueFavorite.setCafId(cafId);
        catalogueFavorite.setCafProductNo(cafProductNo);
        catalogueFavorite.setCafLoyaltyId(cafLoyaltyId);
        catalogueFavorite.setCafFavoriteFlag(cafFavoriteFlag);
        return catalogueFavorite;
    }
}

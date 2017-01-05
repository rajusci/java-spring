package com.inspirenetz.api.test.core.fixture;



import com.inspirenetz.api.core.domain.CatalogueFavorite;
import com.inspirenetz.api.test.core.builder.CatalogueFavoriteBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alameen on 21/10/14.
 */
public class CatalogueFavoriteFixture {


    public static CatalogueFavorite standardCatalogueFavorite() {

        CatalogueFavorite catalogueFavorite = CatalogueFavoriteBuilder.aCatalogueFavorite()
                .withCafFavoriteFlag(1)
                .withCafLoyaltyId("123456789123")
                .withCafProductNo(12L)
                .build();


            return catalogueFavorite;


    }


    public static CatalogueFavorite updatedStandardCatalogueFavorite(CatalogueFavorite catalogueFavorite) {

        catalogueFavorite.setCafFavoriteFlag(0);

        return catalogueFavorite;

    }


    public static Set<CatalogueFavorite> standardCatalogueFavorites() {

        Set<CatalogueFavorite> catalogueFavorites = new HashSet<CatalogueFavorite>(0);

        CatalogueFavorite favorite  = CatalogueFavoriteBuilder.aCatalogueFavorite()
                .withCafFavoriteFlag(1)
                .withCafLoyaltyId("1234567891")
                .withCafProductNo(12L)
                .build();


        catalogueFavorites.add(favorite);



        CatalogueFavorite unFavorite = CatalogueFavoriteBuilder.aCatalogueFavorite()
                .withCafFavoriteFlag(0)
                .withCafLoyaltyId("1234567895")
                .withCafProductNo(12L)
                .build();

        catalogueFavorites.add(unFavorite);



        return catalogueFavorites;



    }
}

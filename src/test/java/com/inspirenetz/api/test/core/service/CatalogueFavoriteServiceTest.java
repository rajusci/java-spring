package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.domain.CatalogueFavorite;
import com.inspirenetz.api.core.repository.CatalogueCategoryRepository;
import com.inspirenetz.api.core.repository.CatalogueFavoriteRepository;
import com.inspirenetz.api.core.service.CatalogueCategoryService;
import com.inspirenetz.api.core.service.CatalogueFavoriteService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CatalogueFavoriteFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by alameen on 21/10/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class CatalogueFavoriteServiceTest {

    private static Logger log = LoggerFactory.getLogger(CatalogueCategoryServiceTest.class);

    @Autowired
    private CatalogueFavoriteService catalogueFavoriteService;

    @Autowired
    private CatalogueFavoriteRepository catalogueFavoriteRepository;

    Set<CatalogueFavorite> tempSet = new HashSet<>(0);
    @Before
    public void setUp() {}

    @Test
    public void test1Create(){


        CatalogueFavorite catalogueFavorite = catalogueFavoriteService.saveCatalogueFavorite(CatalogueFavoriteFixture.standardCatalogueFavorite());
        log.info(catalogueFavorite.toString());
        Assert.assertNotNull(catalogueFavorite.getCafId());

        tempSet.add(catalogueFavorite);


    }

    @Test
    public void test2findByCafLoyaltyId() {

        CatalogueFavorite catalogueFavorite = CatalogueFavoriteFixture.standardCatalogueFavorite();
        catalogueFavorite = catalogueFavoriteService.saveCatalogueFavorite(catalogueFavorite);
        log.info("Original Catalogue favorite " + catalogueFavorite.toString());

        tempSet.add(catalogueFavorite);

        List<CatalogueFavorite> catalogueFavoriteList = catalogueFavoriteService.findByCafLoyaltyId(catalogueFavorite.getCafLoyaltyId());
        Assert.assertNotNull(catalogueFavoriteList);
        log.info("ListCatalogue favorite "+ catalogueFavoriteList.toString());


    }

    @Test
    public void test3findByCafLoyaltyIdAndCafProductNo() {

        CatalogueFavorite catalogueFavorite = CatalogueFavoriteFixture.standardCatalogueFavorite();
        catalogueFavorite = catalogueFavoriteService.saveCatalogueFavorite(catalogueFavorite);
        log.info("Original Catalogue favorite " + catalogueFavorite.toString());

        tempSet.add(catalogueFavorite);

        CatalogueFavorite catalogueFavoriteList = catalogueFavoriteService.findByCafLoyaltyIdAndCafProductNo(catalogueFavorite.getCafLoyaltyId(),catalogueFavorite.getCafProductNo());
        Assert.assertNotNull(catalogueFavoriteList);
        log.info("favorite List "+ catalogueFavoriteList.toString());
    }


    @org.junit.After
    public void tearDown() {

        for ( CatalogueFavorite catalogueFavorite : tempSet ) {

            catalogueFavoriteService.delete(catalogueFavorite);

        }
    }
}

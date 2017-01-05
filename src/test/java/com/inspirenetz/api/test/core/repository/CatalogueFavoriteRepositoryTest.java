package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.CatalogueFavorite;
import com.inspirenetz.api.core.repository.CatalogueFavoriteRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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

import java.util.List;
import java.util.Set;

/**
 * Created by alameen on 21/10/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CatalogueFavoriteRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CatalogueFavoriteRepositoryTest.class);

    @Autowired
    private CatalogueFavoriteRepository catalogueFavoriteRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        CatalogueFavorite catalogueFavorite = catalogueFavoriteRepository.save(CatalogueFavoriteFixture.standardCatalogueFavorite());
        log.info(catalogueFavorite.toString());
        Assert.assertNotNull(catalogueFavorite.getCafId());


    }

    @Test
    public void test2Update() {

        CatalogueFavorite catalogueFavorite = CatalogueFavoriteFixture.standardCatalogueFavorite();
        catalogueFavorite = catalogueFavoriteRepository.save(catalogueFavorite);
        log.info("Original Catalogue favorite " + catalogueFavorite.toString());

        CatalogueFavorite updateCatalogueFavorite = CatalogueFavoriteFixture.updatedStandardCatalogueFavorite(catalogueFavorite);
        updateCatalogueFavorite = catalogueFavoriteRepository.save(updateCatalogueFavorite);
        log.info("Updated Catalogue favorite "+ updateCatalogueFavorite.toString());
    }

    @Test
    public void test2findByLoyaltyId() {

        CatalogueFavorite catalogueFavorite = CatalogueFavoriteFixture.standardCatalogueFavorite();
        catalogueFavorite = catalogueFavoriteRepository.save(catalogueFavorite);
        log.info("Original Catalogue favorite " + catalogueFavorite.toString());

        List<CatalogueFavorite> catalogueFavoriteList = catalogueFavoriteRepository.findByCafLoyaltyId(catalogueFavorite.getCafLoyaltyId());
        Assert.assertNotNull(catalogueFavoriteList);
        log.info("favorite List "+ catalogueFavoriteList.toString());
    }




}

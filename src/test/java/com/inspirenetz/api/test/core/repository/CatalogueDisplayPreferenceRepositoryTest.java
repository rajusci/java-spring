package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.CatalogueDisplayPreference;
import com.inspirenetz.api.core.repository.CatalogueDisplayPreferenceRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CatalogueDisplayPreferenceFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CatalogueDisplayPreferenceRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CatalogueDisplayPreferenceRepositoryTest.class);

    @Autowired
    private CatalogueDisplayPreferenceRepository catalogueDisplayPreferenceRepository;

    Set<CatalogueDisplayPreference> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        CatalogueDisplayPreferenceFixture catalogueDisplayPreferenceFixture=new CatalogueDisplayPreferenceFixture();

        CatalogueDisplayPreference catalogueDisplayPreference = catalogueDisplayPreferenceRepository.save(CatalogueDisplayPreferenceFixture.standardCataloguDisplayPreference());

        // Add to the tempSet
        tempSet.add(catalogueDisplayPreference);

        log.info(catalogueDisplayPreference.toString());
        Assert.assertNotNull(catalogueDisplayPreference.getCdpId());

    }

    @Test
    public void test2Update() {

        CatalogueDisplayPreferenceFixture catalogueDisplayPreferenceFixture=new CatalogueDisplayPreferenceFixture();

        CatalogueDisplayPreference catalogueDisplayPreference = CatalogueDisplayPreferenceFixture.standardCataloguDisplayPreference();
        catalogueDisplayPreference = catalogueDisplayPreferenceRepository.save(catalogueDisplayPreference);
        log.info("Original CatalogueDisplayPreference " + catalogueDisplayPreference.toString());

        // Add to the tempSet
        tempSet.add(catalogueDisplayPreference);


        CatalogueDisplayPreference updatedCatalogueDisplayPreference = CatalogueDisplayPreferenceFixture.updatedStandardCataloguDisplayPreference(catalogueDisplayPreference);
        updatedCatalogueDisplayPreference = catalogueDisplayPreferenceRepository.save(updatedCatalogueDisplayPreference);
        log.info("Updated CatalogueDisplayPreference "+ updatedCatalogueDisplayPreference.toString());

    }

    @Test
    public void test3FindByCpdId() {

        CatalogueDisplayPreferenceFixture catalogueDisplayPreferenceFixture=new CatalogueDisplayPreferenceFixture();

        CatalogueDisplayPreference catalogueDisplayPreference = catalogueDisplayPreferenceFixture.standardCataloguDisplayPreference();
        catalogueDisplayPreference = catalogueDisplayPreferenceRepository.save(catalogueDisplayPreference);
        log.info("Original CatalogueDisplayPreference " + catalogueDisplayPreference.toString());


        // Add to the tempSet
        tempSet.add(catalogueDisplayPreference);

        // Get the data
        CatalogueDisplayPreference searchcatalogueDisplayPreference = catalogueDisplayPreferenceRepository.findByCdpId(catalogueDisplayPreference.getCdpId());
        Assert.assertNotNull(searchcatalogueDisplayPreference);
        Assert.assertTrue(catalogueDisplayPreference.getCdpId().longValue() ==  searchcatalogueDisplayPreference.getCdpId().longValue());;
        log.info("Searched CatalogueDisplayPreference : " + searchcatalogueDisplayPreference.toString());


    }


    @Test
    public void test6FindByCpdMerchantNo(){

        CatalogueDisplayPreferenceFixture catalogueDisplayPreferenceFixture=new CatalogueDisplayPreferenceFixture();

        CatalogueDisplayPreference catalogueDisplayPreference = CatalogueDisplayPreferenceFixture.standardCataloguDisplayPreference();
        catalogueDisplayPreference = catalogueDisplayPreferenceRepository.save(catalogueDisplayPreference);
        log.info("Original CatalogueDisplayPreference " + catalogueDisplayPreference.toString());


        // Add to the tempSet
        tempSet.add(catalogueDisplayPreference);

        // Get the data
        CatalogueDisplayPreference searchCatalogueDisplayPreference = catalogueDisplayPreferenceRepository.findByCdpMerchantNo(catalogueDisplayPreference.getCdpMerchantNo());
        Assert.assertTrue(searchCatalogueDisplayPreference != null);
        log.info("Searched CatalogueDisplayPreference : " + searchCatalogueDisplayPreference.toString());

    }




    @After
    public void tearDown() {

        for(CatalogueDisplayPreference catalogueDisplayPreference : tempSet ) {

            catalogueDisplayPreferenceRepository.delete(catalogueDisplayPreference);

        }

    }




}

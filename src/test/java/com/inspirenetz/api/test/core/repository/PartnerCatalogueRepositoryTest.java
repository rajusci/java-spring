package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.PartnerCatalogue;
import com.inspirenetz.api.core.repository.PartnerCatalogueRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.PartnerCatalogueFixture;
import org.junit.*;
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
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class PartnerCatalogueRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(PartnerCatalogueRepositoryTest.class);

    @Autowired
    private PartnerCatalogueRepository partnerCatalogueRepository;

    Set<PartnerCatalogue> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        PartnerCatalogueFixture partnerCatalogueFixture=new PartnerCatalogueFixture();

        PartnerCatalogue partnerCatalogue = partnerCatalogueRepository.save(partnerCatalogueFixture.standardPartnerCatalogue());

        // Add to the tempSet
        tempSet.add(partnerCatalogue);

        log.info(partnerCatalogue.toString());
        Assert.assertNotNull(partnerCatalogue.getPacId());

    }

    @Test
    public void test2Update() {

        PartnerCatalogueFixture partnerCatalogueFixture=new PartnerCatalogueFixture();

        PartnerCatalogue partnerCatalogue = partnerCatalogueFixture.standardPartnerCatalogue();
        partnerCatalogue = partnerCatalogueRepository.save(partnerCatalogue);
        log.info("Original PartnerCatalogue " + partnerCatalogue.toString());

        // Add to the tempSet
        tempSet.add(partnerCatalogue);


        PartnerCatalogue updatedPartnerCatalogue = PartnerCatalogueFixture.updatedStandardPartnerCatalogue(partnerCatalogue);
        updatedPartnerCatalogue = partnerCatalogueRepository.save(updatedPartnerCatalogue);
        log.info("Updated PartnerCatalogue "+ updatedPartnerCatalogue.toString());

    }

    @Test
    public void test3FindByDrcId() {

        PartnerCatalogueFixture partnerCatalogueFixture=new PartnerCatalogueFixture();

        PartnerCatalogue partnerCatalogue = partnerCatalogueFixture.standardPartnerCatalogue();
        partnerCatalogue = partnerCatalogueRepository.save(partnerCatalogue);
        log.info("Original PartnerCatalogue " + partnerCatalogue.toString());


        // Add to the tempSet
        tempSet.add(partnerCatalogue);

        // Get the data
        PartnerCatalogue searchpartnerCatalogue = partnerCatalogueRepository.findByPacId(partnerCatalogue.getPacId());
        Assert.assertNotNull(searchpartnerCatalogue);
        Assert.assertTrue(partnerCatalogue.getPacId().longValue() ==  searchpartnerCatalogue.getPacId().longValue());;
        log.info("Searched PartnerCatalogue : " + searchpartnerCatalogue.toString());


    }



    @After
    public void tearDown() {

        for(PartnerCatalogue partnerCatalogue : tempSet ) {

            partnerCatalogueRepository.delete(partnerCatalogue);

        }

    }



}

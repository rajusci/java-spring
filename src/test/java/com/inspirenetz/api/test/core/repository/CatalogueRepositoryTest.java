package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.repository.CatalogueRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CatalogueFixture;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/5/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CatalogueRepositoryTest {


    //  Create the logger
    private static Logger log = LoggerFactory.getLogger(CatalogueRepositoryTest.class);

    @Autowired
    private CatalogueRepository catalogueRepository;


    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

        Set<Catalogue> catalogues = CatalogueFixture.standardCatalogues();

        for(Catalogue catalogue: catalogues) {

            Catalogue delCatalogue = catalogueRepository.findByCatProductCodeAndCatMerchantNo(catalogue.getCatProductCode(),catalogue.getCatMerchantNo());

            if ( delCatalogue != null ) {
                catalogueRepository.delete(delCatalogue);
            }

        }
    }


    @Test
    public void test1Create(){


        Catalogue catalogue = catalogueRepository.save(CatalogueFixture.standardCatalogue());
        log.info(catalogue.toString());
        Assert.assertNotNull(catalogue.getCatProductNo());

    }

    @Test
    public void test2Update() {

        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogue = catalogueRepository.save(catalogue);
        log.info("Original Catalogue " + catalogue.toString());

        Catalogue updatedCatalogue = CatalogueFixture.updatedStandardCatalogue(catalogue);
        updatedCatalogue = catalogueRepository.save(updatedCatalogue);
        log.info("Updated Catalogue "+ updatedCatalogue.toString());

    }

    @Test
    public void testFindByCatProductNo() throws Exception {

        // Create the prodcut
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogue = catalogueRepository.save(catalogue);

        Catalogue searchCatalogue = catalogueRepository.findByCatProductNo(catalogue.getCatProductNo());
        Assert.assertNotNull(searchCatalogue);
        log.info("Catalogue Information" + searchCatalogue.toString());
    }

    @Test
    public void testFindByCatMerchantNo() throws Exception {

        Set<Catalogue> catalogues = CatalogueFixture.standardCatalogues();
        catalogueRepository.save(catalogues);

        Catalogue catalogue = CatalogueFixture.standardCatalogue();

        Page<Catalogue>  cataloguePage = catalogueRepository.findByCatMerchantNo(catalogue.getCatMerchantNo(),constructPageSpecification(0));
        Assert.assertTrue(cataloguePage.hasContent());
        List<Catalogue> catalogueList = Lists.newArrayList((Iterable<Catalogue>)cataloguePage);
        log.info("Catalogue List" + catalogueList);

    }

    @Test
    public void testFindByCatProductCodeAndCatMerchantNo() throws Exception {

        // Create the prodcut
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueRepository.save(catalogue);

        Catalogue searchCatalogue = catalogueRepository.findByCatProductCodeAndCatMerchantNo(catalogue.getCatProductCode(),catalogue.getCatMerchantNo());
        Assert.assertNotNull(searchCatalogue);
        log.info("Catalogue information " + searchCatalogue.toString());

    }



    @Test
    public void testFindByCatMerchantNoAndCatDescriptionLike() {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueRepository.save(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");

        // Check the catalogue name
        Page<Catalogue> catalogues = catalogueRepository.findByCatMerchantNoAndCatDescriptionLike(catalogue.getCatMerchantNo(),"%item%",constructPageSpecification(0));
        Assert.assertTrue(catalogues.hasContent());
        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) catalogues);
        log.info("catalogue list "+catalogueSet.toString());


    }
    @Test
    public void searchCatalogueByCategoryAndCatDescription() {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueRepository.save(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");

        // Check the catalogue name
        List<Catalogue> catalogues = catalogueRepository.searchCatalogueByCategoryAndCatDescription(catalogue.getCatMerchantNo(), catalogue.getCatCategory(), "%" + catalogue.getCatDescription() +"%");
        log.info("catalogue list "+catalogues);


    }




    @Test
    public void searchCatalogueByCurrencyAndCategory() {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueRepository.save(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");



        // Check the catalogue name
        Page<Catalogue> catalogues = catalogueRepository.searchCatalogueByCurrencyAndCategory(catalogue.getCatMerchantNo(),1L,0,constructPageSpecification(0));
        Assert.assertTrue(catalogues.hasContent());
        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) catalogues);
        log.info("catalogue list "+catalogueSet.toString());

    }

    @Test
    public void testFindByCatDescriptionLike() {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueRepository.save(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");

        // Check the catalogue name
        Page<Catalogue> catalogues = catalogueRepository.findByCatDescriptionLike("%item%",constructPageSpecification(0));
        Assert.assertTrue(catalogues.hasContent());
        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) catalogues);
        log.info("catalogue list "+catalogueSet.toString());


    }

    @Test
    public void testFindByCatCategoryAndCatDescriptionLike() {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueRepository.save(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");

        // Check the catalogue name
        Page<Catalogue> catalogues = catalogueRepository.findByCatCategoryAndCatDescriptionLike(catalogue.getCatCategory(),"%item%",constructPageSpecification(0));
        Assert.assertTrue(catalogues.hasContent());
        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) catalogues);
        log.info("catalogue list "+catalogueSet.toString());


    }

    @Test
    public void testFindByCatMerchantNoAndCatCategoryAndCatDescriptionLike() {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueRepository.save(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");

        // Check the catalogue name
        Page<Catalogue> catalogues = catalogueRepository.findByCatMerchantNoAndCatCategoryAndCatDescriptionLike(catalogue.getCatMerchantNo(),catalogue.getCatCategory(),"%item%",constructPageSpecification(0));
        Assert.assertTrue(catalogues.hasContent());
        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) catalogues);
        log.info("catalogue list "+catalogueSet.toString());


    }

    @Test
    public void testListCataloguesByCatDescriptionAndCatCategoriesAndCatMerchants() {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueRepository.save(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");

        List<Integer> categories=Lists.newArrayList(catalogue.getCatCategory());
        List<Long> merchants=Lists.newArrayList(catalogue.getCatMerchantNo());

        // Check the catalogue name
        Page<Catalogue> catalogues = catalogueRepository.listCataloguesByCatDescriptionAndCatCategoriesAndCatMerchants("%item%", categories, merchants,constructPageSpecification(0));
        Assert.assertFalse(catalogues.hasContent());

        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) catalogues);
        log.info("catalogue list "+catalogueSet.toString());


    }

    @Test
    public void testListCataloguesByCatDescriptionAndCatCategories() {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueRepository.save(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");

        List<Integer> categories=Lists.newArrayList(catalogue.getCatCategory());
        List<Long> merchants=Lists.newArrayList(catalogue.getCatMerchantNo());

        // Check the catalogue name
        List<Catalogue> catalogues = catalogueRepository.listCataloguesByCatDescriptionAndCatCategories("%item%", categories);
        Assert.assertFalse(catalogues.isEmpty());

        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) catalogues);
        log.info("catalogue list "+catalogueSet.toString());


    }

    @Test
    public void testListCataloguesByCatDescriptionAndCatMerchants() {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueRepository.save(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");

        List<Integer> categories=Lists.newArrayList(catalogue.getCatCategory());
        List<Long> merchants=Lists.newArrayList(catalogue.getCatMerchantNo());

        // Check the catalogue name
        List<Catalogue> catalogues = catalogueRepository.listCataloguesByCatDescriptionAndCatMerchants("%item%", merchants);
        Assert.assertFalse(catalogues.isEmpty());
        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) catalogues);
        log.info("catalogue list "+catalogueSet.toString());


    }
    @Test
    public void testListCatalogueByCatMerchantNoAndCatCategoryAndCatDescriptionLike() {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueRepository.save(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");


        // Check the catalogue name
        List<Catalogue> catalogues = catalogueRepository.listCatalogueByCatMerchantNoAndCatCategoryAndCatDescriptionLike(catalogue.getCatMerchantNo(),0,"%"+catalogue.getCatDescription()+"%");
        Assert.assertFalse(catalogues.isEmpty());
        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) catalogues);
        log.info("catalogue list "+catalogueSet.toString());


    }

    @Test
    public void testListCatalogueByCatCategoryAndCatDescriptionLike() {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogueRepository.save(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");


        // Check the catalogue name
        List<Catalogue> catalogues = catalogueRepository.listCatalogueByCatCategoryAndCatDescriptionLike(0,"%"+catalogue.getCatDescription()+"%");
        Assert.assertFalse(catalogues.isEmpty());
        Set<Catalogue> catalogueSet = Sets.newHashSet((Iterable<Catalogue>) catalogues);
        log.info("catalogue list "+catalogueSet.toString());


    }

    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10, sortByLastNameAsc());
        return pageSpecification;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "catProductNo");
    }
}

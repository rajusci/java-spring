package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.CatalogueCategory;
import com.inspirenetz.api.core.repository.CatalogueCategoryRepository;
import com.inspirenetz.api.core.service.CatalogueCategoryService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CatalogueCategoryFixture;
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
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class CatalogueCategoryServiceTest {


    private static Logger log = LoggerFactory.getLogger(CatalogueCategoryServiceTest.class);

    @Autowired
    private CatalogueCategoryService catalogueCategoryService;

    @Autowired
    private CatalogueCategoryRepository catalogueCategoryRepository;


    @Before
    public void setUp() {}


    @Test
    public void test1Create() throws InspireNetzException {


        CatalogueCategory catalogueCategory = catalogueCategoryService.saveCatalogueCategory(CatalogueCategoryFixture.standardCatalogueCategory());
        log.info(catalogueCategory.toString());
        Assert.assertNotNull(catalogueCategory.getCacId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        CatalogueCategory catalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategory = catalogueCategoryService.saveCatalogueCategory(catalogueCategory);
        log.info("Original CatalogueCategory " + catalogueCategory.toString());

        CatalogueCategory updatedCatalogueCategory = CatalogueCategoryFixture.updatedStandardCatalogueCategory(catalogueCategory);
        updatedCatalogueCategory = catalogueCategoryService.saveCatalogueCategory(updatedCatalogueCategory);
        log.info("Updated CatalogueCategory "+ updatedCatalogueCategory.toString());

    }
      


    @Test
    public void test3SearchCatalogueCategories() throws InspireNetzException {

        // Create the catalogueCategory
        CatalogueCategory catalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategory = catalogueCategoryService.saveCatalogueCategory(catalogueCategory);
        Assert.assertNotNull(catalogueCategory.getCacId());
        log.info("CatalogueCategory created");

        // Get the cacodr list
        Page<CatalogueCategory> catalogueCategoryPage = catalogueCategoryService.searchCatalogueCategories("0", "0", constructPageSpecification(0));
        Assert.assertNotNull(catalogueCategoryPage);
        Assert.assertTrue(catalogueCategoryPage.hasContent());
        List<CatalogueCategory> catalogueCategoryList = Lists.newArrayList((Iterable<CatalogueCategory>)catalogueCategoryPage);
        log.info("CatalogueCategory list " + catalogueCategoryList.toString());

    }



    @Test
    public void test4IsCatalogueCategoryCodeExisting() throws InspireNetzException {

        // Create the catalogueCategory
        CatalogueCategory catalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategory = catalogueCategoryService.saveCatalogueCategory(catalogueCategory);
        Assert.assertNotNull(catalogueCategory.getCacId());
        log.info("CatalogueCategory created");

        // Create a new catalogueCategory
        CatalogueCategory newCatalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        boolean exists = catalogueCategoryService.isDuplicateCatalogueCategoryExisting(newCatalogueCategory);
        Assert.assertTrue(exists);
        log.info("CatalogueCategory exists");


    }


    @Test
    public void test5FindByCacName() throws InspireNetzException {

        // Create the catalogueCategory
        CatalogueCategory catalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategory = catalogueCategoryService.saveCatalogueCategory(catalogueCategory);
        Assert.assertNotNull(catalogueCategory.getCacId());
        log.info("CatalogueCategory created");


        CatalogueCategory searchCatalogueCategory = catalogueCategoryService.findByCacName(catalogueCategory.getCacName());
        Assert.assertNotNull(searchCatalogueCategory);
        log.info("CatalogueCategory INformation : " + searchCatalogueCategory.toString());

    }


    @Test
    public void test6FindByCacId() throws InspireNetzException {

        // Create the catalogueCategory
        CatalogueCategory catalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategory = catalogueCategoryService.saveCatalogueCategory(catalogueCategory);
        Assert.assertNotNull(catalogueCategory.getCacId());
        log.info("CatalogueCategory created");


        CatalogueCategory searchCatalogueCategory = catalogueCategoryService.findByCacId(catalogueCategory.getCacId());
        Assert.assertNotNull(searchCatalogueCategory);
        log.info("CatalogueCategory INformation : " + searchCatalogueCategory.toString());

    }


    @Test
    public void test7DeleteCatalogueCategory() throws InspireNetzException {

        // Create the catalogueCategory
        CatalogueCategory catalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategory = catalogueCategoryService.saveCatalogueCategory(catalogueCategory);
        Assert.assertNotNull(catalogueCategory.getCacId());
        log.info("CatalogueCategory created");

        // call the delete catalogueCategory
        catalogueCategoryService.deleteCatalogueCategory(catalogueCategory.getCacId());

        // Try searching for the catalogueCategory
        CatalogueCategory checkCatalogueCategory  = catalogueCategoryService.findByCacName(catalogueCategory.getCacName());
        Assert.assertNull(checkCatalogueCategory);
        log.info("catalogueCategory deleted");

    }



    @Test
    public void test8FindByCacParentGroup() throws InspireNetzException {

        // Create the CatalogueCategory
        CatalogueCategory catalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategoryService.saveCatalogueCategory(catalogueCategory);
        Assert.assertNotNull(catalogueCategory.getCacId());
        log.info("CatalogueCategory created");


        // Get the list
        List<CatalogueCategory> catalogueCategoryList =  catalogueCategoryService.findByCacParentGroup(catalogueCategory.getCacParentGroup());
        Assert.assertNotNull(catalogueCategoryList);;
        Assert.assertTrue(!catalogueCategoryList.isEmpty());
        log.info("CatalogueCategory List : " + catalogueCategoryList.toString());

    }



    @Test
    public void test9FindFirstLevelCategories() throws InspireNetzException {

        // Create the CatalogueCategory
        CatalogueCategory catalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategoryService.saveCatalogueCategory(catalogueCategory);
        Assert.assertNotNull(catalogueCategory.getCacId());
        log.info("CatalogueCategory created");


        // Get the list
        List<CatalogueCategory> catalogueCategoryList =  catalogueCategoryService.findFirstLevelCategories();
        Assert.assertNotNull(catalogueCategoryList);;
        Assert.assertTrue(!catalogueCategoryList.isEmpty());
        log.info("CatalogueCategory List : " + catalogueCategoryList.toString());

    }



    @After
    public void tearDown() {

        Set<CatalogueCategory> catalogueCategorys = CatalogueCategoryFixture.standardCatalogueCategories();

        for(CatalogueCategory catalogueCategory: catalogueCategorys) {

            CatalogueCategory delCatalogueCategory = catalogueCategoryRepository.findByCacName(catalogueCategory.getCacName());

            if ( delCatalogueCategory != null ) {
                catalogueCategoryRepository.delete(delCatalogueCategory);
            }

        }
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
        return new Sort(Sort.Direction.ASC, "cacId");
    }

}

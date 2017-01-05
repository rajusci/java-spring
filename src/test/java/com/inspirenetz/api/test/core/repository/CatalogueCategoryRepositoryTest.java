package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.CatalogueCategory;
import com.inspirenetz.api.core.repository.CatalogueCategoryRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CatalogueCategoryRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CatalogueCategoryRepositoryTest.class);

    @Autowired
    private CatalogueCategoryRepository catalogueCategoryRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        CatalogueCategory CatalogueCategory = catalogueCategoryRepository.save(CatalogueCategoryFixture.standardCatalogueCategory());
        log.info(CatalogueCategory.toString());
        Assert.assertNotNull(CatalogueCategory.getCacId());

    }

    @Test
    public void test2Update() {

        CatalogueCategory CatalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        CatalogueCategory = catalogueCategoryRepository.save(CatalogueCategory);
        log.info("Original CatalogueCategory " + CatalogueCategory.toString());

        CatalogueCategory updatedCatalogueCategory = CatalogueCategoryFixture.updatedStandardCatalogueCategory(CatalogueCategory);
        updatedCatalogueCategory = catalogueCategoryRepository.save(updatedCatalogueCategory);
        log.info("Updated CatalogueCategory "+ updatedCatalogueCategory.toString());

    }



    @Test
    public void test3FindByCacName() {

        // Create the CatalogueCategory
        CatalogueCategory CatalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategoryRepository.save(CatalogueCategory);
        Assert.assertNotNull(CatalogueCategory.getCacId());
        log.info("CatalogueCategory created");


        //  Get the CatalogueCategory information for the id
        CatalogueCategory searchCatalogueCategory = catalogueCategoryRepository.findByCacName(CatalogueCategory.getCacName());
        Assert.assertNotNull(searchCatalogueCategory);
        log.info("Search CatalogueCategory : " + searchCatalogueCategory.toString());

    }


    @Test
    public void test4FindByCacParentGroup() {

        // Create the CatalogueCategory
        CatalogueCategory catalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategoryRepository.save(catalogueCategory);
        Assert.assertNotNull(catalogueCategory.getCacId());
        log.info("CatalogueCategory created");


        // Get the list
        List<CatalogueCategory> catalogueCategoryList =  catalogueCategoryRepository.findByCacParentGroup(catalogueCategory.getCacParentGroup());
        Assert.assertNotNull(catalogueCategoryList);;
        Assert.assertTrue(!catalogueCategoryList.isEmpty());
        log.info("CatalogueCategory List : " + catalogueCategoryList.toString());

    }



    @Test
    public void test5FindFirstLevelCategories() {

        // Create the CatalogueCategory
        CatalogueCategory catalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategoryRepository.save(catalogueCategory);
        Assert.assertNotNull(catalogueCategory.getCacId());
        log.info("CatalogueCategory created");


        // Get the list
        List<CatalogueCategory> catalogueCategoryList =  catalogueCategoryRepository.findFirstLevelCategories();
        Assert.assertNotNull(catalogueCategoryList);;
        Assert.assertTrue(!catalogueCategoryList.isEmpty());
        log.info("CatalogueCategory List : " + catalogueCategoryList.toString());

    }


    @Test
    public void test6FindByCacNameLike() {

        // Create the CatalogueCategory
        CatalogueCategory catalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategoryRepository.save(catalogueCategory);
        Assert.assertNotNull(catalogueCategory.getCacId());
        log.info("CatalogueCategory created");

        // Check the CatalogueCategory name
        Page<CatalogueCategory> CatalogueCategorys = catalogueCategoryRepository.findByCacMerchantNoAndCacNameLike(1L,"%test%", constructPageSpecification(0));
        Assert.assertTrue(CatalogueCategorys.hasContent());
        Set<CatalogueCategory> CatalogueCategorySet = Sets.newHashSet((Iterable<CatalogueCategory>)CatalogueCategorys);
        log.info("CatalogueCategory list "+CatalogueCategorySet.toString());


    }


    @Test
    public void test7FindByCacId() {

        // Create the CatalogueCategory
        CatalogueCategory CatalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategoryRepository.save(CatalogueCategory);
        Assert.assertNotNull(CatalogueCategory.getCacId());
        log.info("CatalogueCategory created");


        //  Get the CatalogueCategory information for the id
        CatalogueCategory searchCatalogueCategory = catalogueCategoryRepository.findByCacId(CatalogueCategory.getCacId());
        Assert.assertNotNull(searchCatalogueCategory);
        log.info("Search CatalogueCategory : " + searchCatalogueCategory.toString());

    }


    @Test
    public void test8FindAll() {

        // Create the CatalogueCategory
        CatalogueCategory catalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();
        catalogueCategoryRepository.save(catalogueCategory);
        Assert.assertNotNull(catalogueCategory.getCacId());
        log.info("CatalogueCategory created");

        // Check the CatalogueCategory name
        Page<CatalogueCategory> CatalogueCategorys = catalogueCategoryRepository.findAll(constructPageSpecification(0));
        Assert.assertTrue(CatalogueCategorys.hasContent());
        Set<CatalogueCategory> CatalogueCategorySet = Sets.newHashSet((Iterable<CatalogueCategory>)CatalogueCategorys);
        log.info("CatalogueCategory list "+CatalogueCategorySet.toString());


    } 

    @After
    public void tearDown() {

        Set<CatalogueCategory> CatalogueCategorys = CatalogueCategoryFixture.standardCatalogueCategories();

        for(CatalogueCategory CatalogueCategory: CatalogueCategorys) {

            CatalogueCategory delCatalogueCategory = catalogueCategoryRepository.findByCacName(CatalogueCategory.getCacName());

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

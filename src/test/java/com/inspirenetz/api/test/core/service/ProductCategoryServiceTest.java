package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.ProductCategory;
import com.inspirenetz.api.core.repository.ProductCategoryRepository;
import com.inspirenetz.api.core.service.ProductCategoryService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.ProductCategoryFixture;
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

import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class ProductCategoryServiceTest {


    private static Logger log = LoggerFactory.getLogger(ProductCategoryServiceTest.class);

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    @Before
    public void setUp() {}



    @Test
    public void test1FindByMerchantNo() {

        // Get the standard productCategory
        ProductCategory productCategory = ProductCategoryFixture.standardProductCategory();

        Page<ProductCategory> productCategorys = productCategoryService.findByPcyMerchantNo(productCategory.getPcyMerchantNo(),constructPageSpecification(1));
        log.info("productCategorys by merchant no " + productCategorys.toString());
        Set<ProductCategory> productCategorySet = Sets.newHashSet((Iterable<ProductCategory>) productCategorys);
        log.info("productCategory list "+productCategorySet.toString());

    }



    @Test
    public void test2FindByPcyMerchantNoAndPcyCode() throws InspireNetzException {

        // Create the productCategory
        ProductCategory productCategory = ProductCategoryFixture.standardProductCategory();
        productCategoryService.saveProductCategory(productCategory);
        Assert.assertNotNull(productCategory.getPcyId());
        log.info("ProductCategory created");

        ProductCategory fetchProductCategory = productCategoryService.findByPcyMerchantNoAndPcyCode(productCategory.getPcyMerchantNo(),productCategory.getPcyCode());
        Assert.assertNotNull(fetchProductCategory);
        log.info("Fetched productCategory info" + productCategory.toString());

    }



    @Test
    public void test3FindByPcyMerchantNoAndPcyNameLike() throws InspireNetzException {

        // Create the productCategory
        ProductCategory productCategory = ProductCategoryFixture.standardProductCategory();
        productCategoryService.saveProductCategory(productCategory);
        Assert.assertNotNull(productCategory.getPcyId());
        log.info("ProductCategory created");

        // Check the productCategory name
        Page<ProductCategory> productCategorys = productCategoryService.findByPcyMerchantNoAndPcyNameLike(productCategory.getPcyMerchantNo(),"%grocery%",constructPageSpecification(0));
        Assert.assertTrue(productCategorys.hasContent());
        Set<ProductCategory> productCategorySet = Sets.newHashSet((Iterable<ProductCategory>)productCategorys);
        log.info("productCategory list "+productCategorySet.toString());


    }


    @Test
    public void test4IsProductCategoryCodeExisting() throws InspireNetzException {

        // Create the productCategory
        ProductCategory productCategory = ProductCategoryFixture.standardProductCategory();
        productCategory = productCategoryService.saveProductCategory(productCategory);
        Assert.assertNotNull(productCategory.getPcyId());
        log.info("ProductCategory created");

        // Create a new productCategory
        ProductCategory newProductCategory = ProductCategoryFixture.standardProductCategory();
        boolean exists = productCategoryService.isProductCategoryCodeDuplicateExisting(newProductCategory);
        Assert.assertTrue(exists);
        log.info("ProductCategory exists");


    }


    @Test
    public void test5DeleteProductCategory() throws InspireNetzException {

        // Create the productCategory
        ProductCategory productCategory = ProductCategoryFixture.standardProductCategory();
        productCategory = productCategoryService.saveProductCategory(productCategory);
        Assert.assertNotNull(productCategory.getPcyId());
        log.info("ProductCategory created");

        // call the delete productCategory
        productCategoryService.deleteProductCategory(productCategory.getPcyId());

        // Try searching for the productCategory
        ProductCategory checkProductCategory  = productCategoryService.findByPcyMerchantNoAndPcyCode(productCategory.getPcyMerchantNo(),productCategory.getPcyCode());

        Assert.assertNull(checkProductCategory);

        log.info("productCategory deleted");

    }





    @After
    public void tearDown() {

        Set<ProductCategory> productCategorys = ProductCategoryFixture.standardProductCategories();

        for(ProductCategory productCategory: productCategorys) {

            ProductCategory delProductCategory = productCategoryRepository.findByPcyMerchantNoAndPcyCode(productCategory.getPcyMerchantNo(),productCategory.getPcyCode());

            if ( delProductCategory != null ) {
                productCategoryRepository.delete(delProductCategory);
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
        return new Sort(Sort.Direction.ASC, "pcyName");
    }

}

package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.core.repository.ProductRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.ProductFixture;
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
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class ProductRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(ProductRepositoryTest.class);

    @Autowired
    private ProductRepository productRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        Product product = productRepository.save(ProductFixture.standardProduct());
        log.info(product.toString());
        Assert.assertNotNull(product.getPrdId());

    }

    @Test
    public void test2Update() {

        Product product = ProductFixture.standardProduct();
        product = productRepository.save(product);
        log.info("Original Product " + product.toString());

        Product updatedProduct = ProductFixture.updatedStandardProduct(product);
        updatedProduct = productRepository.save(updatedProduct);
        log.info("Updated Product "+ updatedProduct.toString());

    }



    @Test
    public void test3FindByPrdMerchantNo() {

        // Get the standard product
        Product product = ProductFixture.standardProduct();
        product = productRepository.save(product);
        Page<Product> products = productRepository.findByPrdMerchantNoAndPrdLocation(product.getPrdMerchantNo(),product.getPrdLocation(),constructPageSpecification(1));
        log.info("products by merchant no " + products.toString());
        Set<Product> productSet = Sets.newHashSet((Iterable<Product>)products);
        log.info("product list "+productSet.toString());

    }

    @Test
    public void test4FindByPrdMerchantNoAndPrdCode() {

        // Create the product
        Product product = ProductFixture.standardProduct();
        productRepository.save(product);
        Assert.assertNotNull(product.getPrdId());
        log.info("Product created");

        Product fetchProduct = productRepository.findByPrdMerchantNoAndPrdCode(product.getPrdMerchantNo(), product.getPrdCode());
        Assert.assertNotNull(fetchProduct);
        log.info("Fetched product info" + product.toString());

    }


    @Test
    public void test5FindByPrdMerchantNoAndPrdCodeLike() {

        // Create the product
        Product product = ProductFixture.standardProduct();
        productRepository.save(product);
        Assert.assertNotNull(product.getPrdId());
        log.info("Product created");

        // Check the product name
        Page<Product> products = productRepository.findByPrdMerchantNoAndPrdLocationAndPrdCodeLike(product.getPrdMerchantNo(),product.getPrdLocation(),"%PRD%",constructPageSpecification(0));
        Assert.assertTrue(products.hasContent());
        Set<Product> productSet = Sets.newHashSet((Iterable<Product>)products);
        log.info("product list "+productSet.toString());


    }

    @After
    public void tearDown() {

        Set<Product> products = ProductFixture.standardProducts();

        for(Product product: products) {

            Product delProduct = productRepository.findByPrdMerchantNoAndPrdCode(product.getPrdMerchantNo(), product.getPrdCode());

            if ( delProduct != null ) {
                productRepository.delete(delProduct);
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
        return new Sort(Sort.Direction.ASC, "prdName");
    }


}

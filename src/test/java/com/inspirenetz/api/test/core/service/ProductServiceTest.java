package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.core.repository.ProductRepository;
import com.inspirenetz.api.core.service.ProductService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
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
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class ProductServiceTest {


    private static Logger log = LoggerFactory.getLogger(ProductServiceTest.class);


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;



    @Before
    public void setUp() {}

    @Test
    public void test1FindByMerchantNo() {

        // Get the standard product
        Product product = ProductFixture.standardProduct();

        Page<Product> products = productService.findByPrdMerchantNoAndPrdLocation(product.getPrdMerchantNo(), product.getPrdLocation(), constructPageSpecification(1));
        log.info("products by merchant no " + products.toString());
        Set<Product> productSet = Sets.newHashSet((Iterable<Product>) products);
        log.info("product list "+productSet.toString());

    }

    @Test
    public void test2FindByPrdMerchantNoAndPrdCode() throws InspireNetzException {

        // Create the product
        Product product = ProductFixture.standardProduct();
        productService.saveProduct(product);
        Assert.assertNotNull(product.getPrdId());
        log.info("Product created");

        Product fetchProduct = productService.findByPrdMerchantNoAndPrdCode(product.getPrdMerchantNo(), product.getPrdCode());
        Assert.assertNotNull(fetchProduct);
        log.info("Fetched product info" + product.toString());

    }

    @Test
    public void test3FindByPrdMerchantNoAndPrdNameLike() throws InspireNetzException {

        // Create the product
        Product product = ProductFixture.standardProduct();
        productService.saveProduct(product);
        Assert.assertNotNull(product.getPrdId());
        log.info("Product created");

        // Check the product name
        Page<Product> products = productService.findByPrdMerchantNoAndPrdLocationAndPrdNameLike(product.getPrdMerchantNo(), product.getPrdLocation(), "%pep%", constructPageSpecification(0));
        Assert.assertTrue(products.hasContent());
        Set<Product> productSet = Sets.newHashSet((Iterable<Product>)products);
        log.info("product list "+productSet.toString());


    }

    @Test
    public void test4IsProductCodeExisting() throws InspireNetzException {

        // Create the product
        Product product = ProductFixture.standardProduct();
        product = productService.saveProduct(product);
        Assert.assertNotNull(product.getPrdId());
        log.info("Product created");

        // Create a new product
        Product newProduct = ProductFixture.standardProduct();
        boolean exists = productService.isProductCodeDuplicateExisting(newProduct);
        Assert.assertTrue(exists);
        log.info("Product exists");


    }

    @Test
    public void test5DeleteProduct() throws InspireNetzException {

        // Create the product
        Product product = ProductFixture.standardProduct();
        product = productService.saveProduct(product);
        Assert.assertNotNull(product.getPrdId());
        log.info("Product created");

        // call the delete product
        productService.deleteProduct(product.getPrdId());

        // Try searching for the product
        Product checkProduct  = productService.findByPrdMerchantNoAndPrdCode(product.getPrdMerchantNo(), product.getPrdCode());

        Assert.assertNull(checkProduct);

        log.info("product deleted");

    }

    @After
    public void tearDown() throws InspireNetzException {

        Set<Product> products = ProductFixture.standardProducts();

        for(Product product: products) {

            Product delProduct = productService.findByPrdMerchantNoAndPrdCode(product.getPrdMerchantNo(), product.getPrdCode());

            if ( delProduct != null ) {
                productService.deleteProduct(delProduct.getPrdId());
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

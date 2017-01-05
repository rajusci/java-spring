package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.core.domain.ProductCategory;
import com.inspirenetz.api.core.service.BrandService;
import com.inspirenetz.api.core.service.ProductCategoryService;
import com.inspirenetz.api.core.service.ProductService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.ProductFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.DBUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class ProductControllerTest {


    private static Logger log = LoggerFactory.getLogger(ProductControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // Product object
    private Product product;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    // tempSet for product
    Set<Product> tempProductSet = new HashSet<>(0);

    // tempSet for brand
    Set<Brand> tempBrandSet = new HashSet<>(0);

    // tempSet for product category
    Set<ProductCategory> tempProductCategorySet = new HashSet<>(0);




    @Before
    public void setUp()
    {
        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID,userDetailsService);

            // Create the Session
            session = new MockHttpSession();


            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    new ControllerTestUtils.MockSecurityContext(principal));

            //mockMvc = webAppContextSetup(this.wac).build();
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(this.wac)
                    .addFilters(this.springSecurityFilterChain)
                    .build();

            init();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();

        // Create the product
        product = ProductFixture.standardProduct();


    }



    @Test
    public void saveProduct() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/sku/product")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("prdId", "52856")
                                                .param("prdCode", product.getPrdCode())
                                                .param("prdName", product.getPrdName())
                                                .param("prdDescription", product.getPrdDescription())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("ProductResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Product created");


    } 
    
    @Test
    public void deleteProduct() throws  Exception {

        // Create the product
        product = productService.saveProduct(product);
        Assert.assertNotNull(product.getPrdId());
        log.info("Product created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/sku/product/delete/" + product.getPrdId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("ProductResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Product deleted");


    }

    @Test
    public void listProducts() throws Exception  {


        // Save the product
        product = productService.saveProduct(product);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/sku/products/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("ProductResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getProductInfo() throws Exception  {


        // Save the product
        product = productService.saveProduct(product);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/sku/product/"+product.getPrdId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("ProductResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void readItemXML() throws Exception{

        //Initialize file object for xml
        File file=new File("/media/fayiz-ci/B474B6EA74B6AE8C/Source/ITEMS/categories.txt");

        FileInputStream fileInputStream=new FileInputStream(file);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/xml/sku/itemmaster")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(ByteStreams.toByteArray(fileInputStream))

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("SaleResponse: " + response);

        /*//fetch product for teardown
        Product product=productService.findByPrdMerchantNoAndPrdCode(1L,"PR1002");

        Assert.assertNotNull(product);

        tempProductSet.add(product);

        //fetch brand for teardown
        Brand brand=brandService.findByBrnMerchantNoAndBrnCode(1L, "BR2003");

        Assert.assertNotNull(brand);

        tempBrandSet.add(brand);

        //fetch product category for teardown
        ProductCategory productCategory=productCategoryService.findByPcyMerchantNoAndPcyCode(1L, "PC1002");

        Assert.assertNotNull(productCategory);

        tempProductCategorySet.add(productCategory);

*/

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

        //tear down product from tempProductSet
        for(Product product: tempProductSet) {

            if ( product != null ) {

                productService.deleteProduct(product.getPrdId());
            }

        }

        //tear down brand from tempBrandSet
        for(Brand brand: tempBrandSet) {

            if ( brand != null ) {

                brandService.deleteBrand(brand.getBrnId());
            }

        }

        //tear down product category from tempProductCategorySet
        for(ProductCategory productCategory: tempProductCategorySet) {

            if ( productCategory != null ) {

                productCategoryService.deleteProductCategory(productCategory.getPcyId());
            }

        }
    }

}

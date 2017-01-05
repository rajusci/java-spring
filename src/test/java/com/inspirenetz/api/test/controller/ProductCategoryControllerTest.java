package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.domain.ProductCategory;
import com.inspirenetz.api.core.service.ProductCategoryService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.ProductCategoryFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
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
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class ProductCategoryControllerTest {


    private static Logger log = LoggerFactory.getLogger(ProductCategoryControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // ProductCategory object
    private ProductCategory productCategory;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();




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

        // Create the productCategory
        productCategory = ProductCategoryFixture.standardProductCategory();


    }



    @Test
    public void saveProductCategory() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/sku/productcategory")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("pcyCode",productCategory.getPcyCode())
                                                .param("pcyName",productCategory.getPcyName())
                                                .param("pcyDescription", productCategory.getPcyDescription())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("ProductCategoryResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("ProductCategory created");


    } 
    
    @Test
    public void deleteProductCategory() throws  Exception {

        // Create the productCategory
        productCategory = productCategoryService.saveProductCategory(productCategory);
        Assert.assertNotNull(productCategory.getPcyId());
        log.info("ProductCategory created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/sku/productcategory/delete/" + productCategory.getPcyId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("ProductCategoryResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("ProductCategory deleted");


    }

    @Test
    public void listProductCategorys() throws Exception  {

        // Save from the product category
        productCategory = productCategoryService.saveProductCategory(productCategory);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/sku/productcategories/name/pre")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("ProductCategoryResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getProductCategoryInfo() throws Exception  {

        // Save from the product category
        productCategory = productCategoryService.saveProductCategory(productCategory);

        // Place the  request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/sku/productcategory/"+productCategory.getPcyId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("ProductCategoryResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }





    @After
    public void tearDown() throws InspireNetzException {

        Set<ProductCategory> productCategorys = ProductCategoryFixture.standardProductCategories();

        for(ProductCategory productCategory: productCategorys) {

            ProductCategory delProductCategory = productCategoryService.findByPcyMerchantNoAndPcyCode(productCategory.getPcyMerchantNo(),productCategory.getPcyCode());

            if ( delProductCategory != null ) {
                productCategoryService.deleteProductCategory(delProductCategory.getPcyId());
            }

        }
    }

}

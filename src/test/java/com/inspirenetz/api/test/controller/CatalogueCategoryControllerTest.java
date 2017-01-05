package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.CatalogueCategory;
import com.inspirenetz.api.core.service.CatalogueCategoryService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CatalogueCategoryFixture;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class CatalogueCategoryControllerTest {


    private static Logger log = LoggerFactory.getLogger(CatalogueCategoryControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private CatalogueCategoryService catalogueCategoryService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // CatalogueCategory object
    private CatalogueCategory catalogueCategory;

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

        // Create the catalogueCategory
        catalogueCategory = CatalogueCategoryFixture.standardCatalogueCategory();


    }




    @Test
    public void saveCatalogueCategory() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/cataloguecategory")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("cacName", catalogueCategory.getCacName())
                                                .param("cacDescription", catalogueCategory.getCacDescription())
                                                .param("cacParentGroup",catalogueCategory.getCacParentGroup().toString())
                                                .param("cacFirstLevelInd",catalogueCategory.getCacFirstLevelInd().toString())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CatalogueCategoryResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("CatalogueCategory created");


    }

    @Test
    public void deleteCatalogueCategory() throws  Exception {

        // Create the catalogueCategory
        catalogueCategory = catalogueCategoryService.saveCatalogueCategory(catalogueCategory);
        Assert.assertNotNull(catalogueCategory.getCacId());
        log.info("CatalogueCategory created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/cataloguecategory/delete/" + catalogueCategory.getCacId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CatalogueCategoryResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("CatalogueCategory deleted");


    }

    @Test
    public void listCatalogueCategories() throws Exception  {

        //Add the data
        catalogueCategory = catalogueCategoryService.saveCatalogueCategory(catalogueCategory);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/admin/cataloguecategories/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CatalogueCategoryResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getCatalogueCategoryInfo()   throws Exception  {

        //Add the data
        catalogueCategory = catalogueCategoryService.saveCatalogueCategory(catalogueCategory);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/admin/cataloguecategory/"+catalogueCategory.getCacId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CatalogueCategoryResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void getCatalogueCategoriesForParentGroup()   throws Exception  {

        //Add the data
        catalogueCategory = catalogueCategoryService.saveCatalogueCategory(catalogueCategory);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/cataloguecategories/parentgroup/1")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CatalogueCategoryResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void getFirstLevelCatalogueCategories()   throws Exception  {

        //Add the data
        catalogueCategory = catalogueCategoryService.saveCatalogueCategory(catalogueCategory);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/cataloguecategories/firstlevel")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CatalogueCategoryResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }




    @After
    public void tearDown() throws InspireNetzException {

        Set<CatalogueCategory> catalogueCategorys = CatalogueCategoryFixture.standardCatalogueCategories();

        for(CatalogueCategory catalogueCategory: catalogueCategorys) {

            CatalogueCategory delCatalogueCategory = catalogueCategoryService.findByCacName(catalogueCategory.getCacName());

            if ( delCatalogueCategory != null ) {
                catalogueCategoryService.deleteCatalogueCategory(delCatalogueCategory.getCacId());
            }

        }
    }

}

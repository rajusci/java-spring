package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.dictionary.CatalogueSortOptionCodedValues;
import com.inspirenetz.api.core.dictionary.RequestChannel;
import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.service.CatalogueService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CatalogueFixture;
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
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class CatalogueControllerTest {


    private static Logger log = LoggerFactory.getLogger(CatalogueControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private CatalogueService catalogueService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // Catalogue object
    private Catalogue catalogue;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();




    @Before
    public void setUp()
    {
        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_CUSTOMER_LOGINID,userDetailsService);

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

    private void useCustomerSession() {

        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_CUSTOMER_LOGINID,userDetailsService);

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

        // Create the catalogue
        catalogue = CatalogueFixture.standardCatalogue();


    }


    @Test
    public void saveCatalogue() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/catalogue")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("catProductCode",catalogue.getCatProductCode())
                                                .param("catDescription",catalogue.getCatDescription())
                                                .param("catNumPoints", catalogue.getCatNumPoints().toString())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CatalogueResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Catalogue created");


    } 
    
    @Test
    public void deleteCatalogue() throws  Exception {

        // Create the catalogue
        catalogue = catalogueService.saveCatalogue(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/catalogue/delete/" + catalogue.getCatProductNo())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CatalogueResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Catalogue deleted");


    }

    @Test
    public void getCatalogueInfo() throws Exception  {

        // Save catalogue
        catalogue.setCatCustomerTier("1,2");
        catalogue = catalogueService.saveCatalogue(catalogue);



        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/catalogue/"+catalogue.getCatProductNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CatalogueResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listCatalogues() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/catalogues/category/1")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("rewardcurrency","5")

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CatalogueResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listCatalogueCustomer() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/catalogues/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("rewardcurrency","0")
                .param("merchantno","1")

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CatalogueResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @Test
    public void listCataloguesCustomerCompatible() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/catalogues/compatible")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CatalogueList for Mobile: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listCatalogueFavourites() throws Exception{

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/catalogue/list/favourites/1")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();


    }

    @Test
    public void getPublicCatalogues() throws Exception  {

        // Create the catalogue
        Catalogue catalogue = CatalogueFixture.standardCatalogue();

        catalogue.setCatCustomerType(4);
        catalogue.setCatStartDate((DBUtils.covertToSqlDate("2015-05-01")));
        catalogue.setCatEndDate((DBUtils.covertToSqlDate("2015-05-20")));
        catalogue.setCatChannelValues("2");
        catalogue.setCatAvailableStock(10L);
        catalogueService.saveCatalogue(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/public/catalogues")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("query","%meen%")
                .param("merchantNo","1")//catalogue.getCatMerchantNo().toString())
                .param("channel", "2")

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("getPublicCatalogues Response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listCataloguesUser() throws Exception  {

        useCustomerSession();

        // Create the catalogue
        /*Catalogue catalogue = CatalogueFixture.standardCatalogue();

        catalogue.setCatCustomerType(4);
        catalogue.setCatStartDate((DBUtils.covertToSqlDate("2015-05-01")));
        catalogue.setCatEndDate((DBUtils.covertToSqlDate("2015-05-25")));
        catalogue.setCatChannelValues("2");
        catalogue.setCatAvailableStock(10L);
        catalogueService.saveCatalogue(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");*/

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/user/catalogues")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("channel", "2")
                .param("catMerchantNo", "1")

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("listCataloguesUser Response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listCataloguesForCustomerPortal() throws Exception  {

        useCustomerSession();

        // Create the catalogue
        /*Catalogue catalogue = CatalogueFixture.standardCatalogue();

        catalogue.setCatCustomerType(4);
        catalogue.setCatStartDate((DBUtils.covertToSqlDate("2015-05-01")));
        catalogue.setCatEndDate((DBUtils.covertToSqlDate("2015-05-25")));
        catalogue.setCatChannelValues("2");
        catalogue.setCatAvailableStock(10L);
        catalogueService.saveCatalogue(catalogue);
        Assert.assertNotNull(catalogue.getCatProductNo());
        log.info("Catalogue created");*/

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/user/catalogues/search")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("catCategory", "0")
                .param("catMerchantNo", "2")
                .param("sortOption", CatalogueSortOptionCodedValues.POINT_HIGH_TO_LOW+"")
                .param("channel", RequestChannel.RDM_WEB+"")

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("listCataloguesForCustomerPortal Response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getCatalogueFavouritesForUser() throws Exception{

        useCustomerSession();

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/user/catalogue/list/favourites")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();


    }




    @After
    public void tearDown() throws Exception {

        Set<Catalogue> catalogues = CatalogueFixture.standardCatalogues();

        for(Catalogue catalogue: catalogues) {

            Catalogue delCatalogue = catalogueService.findByCatProductCodeAndCatMerchantNo(catalogue.getCatProductCode(),catalogue.getCatMerchantNo());

            if ( delCatalogue != null ) {
                catalogueService.deleteCatalogue(delCatalogue.getCatProductNo());
            }

        }
    }

}

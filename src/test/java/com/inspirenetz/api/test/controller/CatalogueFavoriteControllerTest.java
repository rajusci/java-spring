package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.CatalogueFavorite;
import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.core.service.CatalogueFavoriteService;
import com.inspirenetz.api.core.service.MessageSpielService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CatalogueFavoriteFixture;
import com.inspirenetz.api.test.core.fixture.MessageSpielFixture;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by alameen on 21/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class CatalogueFavoriteControllerTest {

    private static Logger log = LoggerFactory.getLogger(CatalogueFavoriteControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    Set<CatalogueFavorite> tempSet = new HashSet<>(0);

    @Autowired
    private CatalogueFavoriteService catalogueFavoriteService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    MockHttpSession session;

    private CatalogueFavorite catalogueFavorite;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    @Before
    public void setup() {



        try{
            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

            // Set the context
            SecurityContextHolder.getContext().setAuthentication(principal);

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
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();

        // Create the linkRequest
        catalogueFavorite = CatalogueFavoriteFixture.standardCatalogueFavorite();


    }

    @Test
    public void saveCatalogueFavorite() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/favorite")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cafLoyaltyId", catalogueFavorite.getCafLoyaltyId().toString())
                .param("cafProductNo", catalogueFavorite.getCafProductNo().toString())
                .param("cafFavoriteFlag", catalogueFavorite.getCafFavoriteFlag().toString())

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("catalogueFavorite: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));

        // Add to tempSet
        tempSet.add(catalogueFavorite);

        log.info("catalogueFavorite created");


    }

    @Test
    public void listCatalogueFavorite() throws Exception  {

        //Add the data
        catalogueFavorite = catalogueFavoriteService.saveCatalogueFavorite(catalogueFavorite);

        tempSet.add(catalogueFavorite);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/catalogue/favorite/"+catalogueFavorite.getCafLoyaltyId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("Catalogue Favorite: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));

    }

    @After
    public void tearDown() {


        for ( CatalogueFavorite catalogueFavorite : tempSet ) {

            catalogueFavoriteService.delete(catalogueFavorite);

        }

    }


}

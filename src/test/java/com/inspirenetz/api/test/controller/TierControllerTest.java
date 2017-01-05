package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.domain.TierGroup;
import com.inspirenetz.api.core.service.TierGroupService;
import com.inspirenetz.api.core.service.TierService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.TierFixture;
import com.inspirenetz.api.test.core.fixture.TierGroupFixture;
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
public class TierControllerTest {


    private static Logger log = LoggerFactory.getLogger(TierControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private TierService tierService;

    @Autowired
    private TierGroupService tierGroupService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // Tier object
    private Tier tier;

    // TierGroup object
    private TierGroup tierGroup;


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

        // Create the tier
        tier = TierFixture.standardTier();

        // Create the TierGroup
        tierGroup = TierGroupFixture.standardTierGroup();


    }




    @Test
    public void saveTier() throws Exception {


        // Save the tierGroup
        tierGroup = tierGroupService.saveTierGroup(tierGroup);

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/tiering/tier")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("tieName",tier.getTieName())
                                                .param("tieParentGroup",tierGroup.getTigId().toString())
                                                .param("tiePointInd", tier.getTiePointInd().toString())
                                                .param("tiePointValue1",tier.getTieAmountValue1().toString())
                                                .param("tiePointValue2",tier.getTieAmountValue1().toString())
                                                .param("tiePointCompType",tier.getTieAmountCompType().toString())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TierResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Tier created");


    }

    @Test
    public void saveTierList() throws Exception {


        // Save the tierGroup
        tierGroup = tierGroupService.saveTierGroup(tierGroup);
        tier.setTieParentGroup(tierGroup.getTigId());

        Tier tier2 = TierFixture.standardTier();
        tier2.setTieName("Test tier");
        tier2.setTieParentGroup(tierGroup.getTigId());

        // Create the tierList array
        List<Tier> tierList = new ArrayList<>(0);

        // Add the items to the list
        tierList.add(tier);
        tierList.add(tier2);


        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(tierList);
        log.info("JSOn value is : " + data);

        // Add the


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/tiering/tierlist")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(data)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TierResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Tier created");


    }


    @Test
    public void deleteTier() throws  Exception {

        // Save the tierGroup
        tierGroup = tierGroupService.saveTierGroup(tierGroup);

        // Set the tier
        tier.setTieParentGroup(tierGroup.getTigId());

        // Create the tier
        tier = tierService.saveTier(tier);
        Assert.assertNotNull(tier.getTieId());
        log.info("Tier created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/tiering/tier/delete/" + tier.getTieId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TierResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Tier deleted");


        // SEt the tierid to null
        tier.setTieId(null);

    }

    @Test
    public void listTiers() throws Exception  {

        // Save the tierGroup
        tierGroup = tierGroupService.saveTierGroup(tierGroup);

        // Set the tier
        tier.setTieParentGroup(tierGroup.getTigId());

        // Create the tier
        tier = tierService.saveTier(tier);
        Assert.assertNotNull(tier.getTieId());
        log.info("Tier created");

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/tiering/tiers/"+tierGroup.getTigId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TierResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @Test
    public void listAllTiers() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/tiering/alltiers")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TierResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getTierInfo()   throws Exception  {

      /*  // Save the tierGroup
        tierGroup = tierGroupService.saveTierGroup(tierGroup);

        // Set the tier9
        tier.setTieParentGroup(tierGroup.getTigId());

        // Create the tier
        tier = tierService.saveTier(tier);
        Assert.assertNotNull(tier.getTieId());
        log.info("Tier created");
*/


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/tiering/tier/"+1011)
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TierResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }




    @Test
    public void tierManualEvaluate() throws Exception  {


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/tiering/manual/evaluate")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TierResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }





    @After
    public void tearDown() throws InspireNetzException {

        if ( tier.getTieId() != null )
            // Delete the tier
            tierService.deleteTier(tier.getTieId());

        if ( tierGroup.getTigId() != null )
            // Delete the tier group
            tierGroupService.deleteTierGroup(tierGroup.getTigId());


    }

}

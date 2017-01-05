package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.domain.TierGroup;
import com.inspirenetz.api.core.service.RewardCurrencyService;
import com.inspirenetz.api.core.service.TierGroupService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.RewardCurrencyFixture;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by saneeshci on 20/8/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class TierGroupControllerTest {


    private static Logger log = LoggerFactory.getLogger(TierGroupControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private TierGroupService tierGroupService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    // TierGroup object
    private TierGroup tierGroup;

    // TierGroup object
    private Set<TierGroup> tierGroup1;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<RewardCurrency> rewardCurrencySet = new HashSet<>(0);



    @Before
    public void setUp()
    {
        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID,userDetailsService);

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

        // Create the tierGroup
        tierGroup = TierGroupFixture.standardTierGroup();

        tierGroup1 = TierGroupFixture.standardTierGroups();


    }




    @Test
    public void saveTierGroup() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/tiering/tiergroup")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("tigName", tierGroup.getTigName())
                                                .param("tigLocation",tierGroup.getTigLocation().toString())
                                                .param("tigRewardCurrency", tierGroup.getTigRewardCurrency().toString())
                                                .param("tigTransactionCurrency", tierGroup.getTigTransactionCurrency().toString())
                                                .param("tigApplicableGroup", tierGroup.getTigApplicableGroup().toString())
                                                .param("tigUpgradeCheckPeriod", tierGroup.getTigUpgradeCheckPeriod().toString())
                                                .param("tigEvaluationPeriodCompType", tierGroup.getTigEvaluationPeriodCompType().toString())
                                                .param("tigDowngradeCheckPeriod", tierGroup.getTigDowngradeCheckPeriod().toString())
        )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TierGroupResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("TierGroup created");


    }

    @Test
    public void deleteTierGroup() throws  Exception {

        // Create the tierGroup
        tierGroup = tierGroupService.saveTierGroup(tierGroup);
        Assert.assertNotNull(tierGroup.getTigId());
        log.info("TierGroup created" + tierGroup.getTigId());


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/tiering/tiergroup/delete/" + tierGroup.getTigId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TierGroupResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("TierGroup deleted");


    }

    @Test
    public void listTierGroups() throws Exception  {

        // Create the RewardCurrency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);

        // Add to the set
        rewardCurrencySet.add(rewardCurrency);


        for(TierGroup tierGroup : tierGroup1) {

            // Set the reward currency
            tierGroup.setTigRewardCurrency(rewardCurrency.getRwdCurrencyId());

            //Add the data
            tierGroup = tierGroupService.saveTierGroup(tierGroup);

            log.info("TG created id"+ tierGroup.toString());

            log.info("TG created id"+ tierGroup.getTigId());

        }


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/tiering/tiergroups/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TierGroupResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getTierGroupInfo()   throws Exception  {

        //Add the data
       /* tierGroup = tierGroupService.saveTierGroup(tierGroup);*/

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/tiering/tiergroup/4")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TierGroupResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }




    @After
    public void tearDown() throws InspireNetzException {

        Set<TierGroup> tierGroups = TierGroupFixture.standardTierGroups();

        for(TierGroup tierGroup: tierGroups) {

            TierGroup delTierGroup = tierGroupService.findByTigMerchantNoAndTigName(tierGroup.getTigMerchantNo(),tierGroup.getTigName());

            if ( delTierGroup != null ) {
                tierGroupService.deleteTierGroup(delTierGroup.getTigId());
            }

        }


        for(RewardCurrency rewardCurrency: rewardCurrencySet ) {

            rewardCurrencyService.deleteRewardCurrency(rewardCurrency.getRwdCurrencyId());

        }
    }

}

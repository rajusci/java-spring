package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.dictionary.LoyaltyProgramSkuType;
import com.inspirenetz.api.core.dictionary.LoyaltyProgramStatus;
import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.core.repository.LoyaltyProgramRepository;
import com.inspirenetz.api.core.service.LoyaltyProgramService;
import com.inspirenetz.api.core.service.LoyaltyProgramSkuService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.LoyaltyProgramFixture;
import com.inspirenetz.api.test.core.fixture.LoyaltyProgramSkuFixture;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class LoyaltyProgramControllerTest {


    private static Logger log = LoggerFactory.getLogger(LoyaltyProgramControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;

    @Autowired
    private LoyaltyProgramSkuService loyaltyProgramSkuService;

    @Autowired
    private LoyaltyProgramRepository loyaltyProgramRepository;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // LoyaltyProgram object
    private LoyaltyProgram loyaltyProgram;

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

            // set the principal for the service
            SecurityContextHolder.getContext().setAuthentication(principal);

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

        // Create the loyaltyProgram
        loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();


    }



    @Test
    public void createLoyaltyProgram() throws Exception {

        // Create the LoyaltyProgram
        LoyaltyProgram loyaltyProgram =  LoyaltyProgramFixture.standardLoyaltyProgram();

        // Create the LoyaltyProgramSku1
        LoyaltyProgramSku loyaltyProgramSku1 = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku1.setLpuProgramId(null);

        LoyaltyProgramSku loyaltyProgramSku2 = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku2.setLpuProgramId(null);
        loyaltyProgramSku2.setLpuItemType(LoyaltyProgramSkuType.BRAND);

        // Create a set
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = new HashSet<>(0);
        loyaltyProgramSkuSet.add(loyaltyProgramSku1);
        loyaltyProgramSkuSet.add(loyaltyProgramSku2);


        // Add the set to the loyaltyProgram
        loyaltyProgram.setLoyaltyProgramSkuSet(loyaltyProgramSkuSet);


        // Convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String loyaltyProgramData = objectMapper.writeValueAsString(loyaltyProgram);
        log.info("JSON string : " + loyaltyProgramData);



        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/loyaltyprogram")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(loyaltyProgramData)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyProgramResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("LoyaltyProgram created");


    }


    @Test
    public void updateLoyaltyProgram() throws Exception {

        // Create the LoyaltyProgram
        LoyaltyProgram loyaltyProgram =  LoyaltyProgramFixture.standardLoyaltyProgram();

        // Create the LoyaltyProgramSku1
        LoyaltyProgramSku loyaltyProgramSku1 = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku1.setLpuProgramId(null);

        LoyaltyProgramSku loyaltyProgramSku2 = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku2.setLpuProgramId(null);
        loyaltyProgramSku2.setLpuItemType(LoyaltyProgramSkuType.BRAND);

        // Create a set
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = new HashSet<>(0);
        loyaltyProgramSkuSet.add(loyaltyProgramSku1);
        loyaltyProgramSkuSet.add(loyaltyProgramSku2);


        // Add the set to the loyaltyProgram
        loyaltyProgram.setLoyaltyProgramSkuSet(loyaltyProgramSkuSet);


        // Save the loyaltyProgram
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);

        loyaltyProgram = loyaltyProgramService.getLoyaltyProgramInfo(loyaltyProgram.getPrgProgramNo());

        int index = 1;

        // Now remove one loyalty program sku from the set
        for(LoyaltyProgramSku loyaltyProgramSku : loyaltyProgram.getLoyaltyProgramSkuSet()) {

            loyaltyProgram.getLoyaltyProgramSkuSet().remove(loyaltyProgramSku);
            break;

        }



        // Convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String loyaltyProgramData = objectMapper.writeValueAsString(loyaltyProgram);
        loyaltyProgramData = loyaltyProgramData.replace(loyaltyProgramSku1.getLpuItemCode(),"SLMDF");
        log.info("JSON string : " + loyaltyProgramData);



        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/loyaltyprogram")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(loyaltyProgramData)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyProgramResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("LoyaltyProgram created");


    }

    @Test
    public void deleteLoyaltyProgram() throws  Exception {

        // Create the loyaltyProgram
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());
        log.info("LoyaltyProgram created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/loyaltyprogram/delete/" + loyaltyProgram.getPrgProgramNo())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyProgramResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("LoyaltyProgram deleted");


    }

    @Test
    public void listLoyaltyPrograms() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/loyaltyprograms/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyProgramResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @Test
    public void listLoyaltyProgramProductBasedItems() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/loyaltyprogram/items/brand/t")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyProgramResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getLoyaltyProgramInfo() throws Exception  {

        //SAve the loyaltyprogram
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/loyaltyprogram/"+loyaltyProgram.getPrgProgramNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyProgramResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @Test
    public void updateLoyatlyProgramStatus() throws Exception  {

        //SAve the loyaltyprogram
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/loyaltyprogram/status/"+loyaltyProgram.getPrgProgramNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("prgStatus",Integer.toString(LoyaltyProgramStatus.EXPIRED))

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyProgramResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @Test
    public void startDateTriggered() throws Exception  {


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/loyaltyprogram/datetriggered/start")
                .principal(principal)
                .session(session)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyProgramResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listLoyaltyProgramsForCustomer() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/loyaltyprograms/0/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyProgramResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @After
    public void tearDown() throws Exception {

        Set<LoyaltyProgram> loyaltyPrograms = LoyaltyProgramFixture.standardLoyaltyPrograms();

        for(LoyaltyProgram loyaltyProgram: loyaltyPrograms) {

            LoyaltyProgram delLoyaltyProgram = loyaltyProgramRepository.findByPrgMerchantNoAndPrgProgramName(loyaltyProgram.getPrgMerchantNo(),loyaltyProgram.getPrgProgramName());

            if ( delLoyaltyProgram != null ) {

                /*
                List<LoyaltyProgramSku> loyaltyProgramSkuList =  loyaltyProgramSkuService.findByLpuProgramId(loyaltyProgram.getPrgProgramNo(),constructPageSpecification(0)).getContent();

                for (LoyaltyProgramSku loyaltyProgramSku : loyaltyProgramSkuList ) {

                    loyaltyProgramSkuService.deleteLoyaltyProgramSku(loyaltyProgramSku.getLpuId());

                }*/

                loyaltyProgramService.deleteLoyaltyProgram(delLoyaltyProgram.getPrgProgramNo());
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
        return new Sort(Sort.Direction.ASC, "prgProgramNo");
    }


}

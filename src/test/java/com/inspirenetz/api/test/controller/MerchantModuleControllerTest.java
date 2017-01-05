package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.MerchantModule;
import com.inspirenetz.api.core.domain.Module;
import com.inspirenetz.api.core.service.MerchantModuleService;
import com.inspirenetz.api.core.service.ModuleService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.MerchantModuleFixture;
import com.inspirenetz.api.test.core.fixture.ModuleFixture;
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
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class MerchantModuleControllerTest {


    private static Logger log = LoggerFactory.getLogger(MerchantModuleControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private MerchantModuleService merchantModuleService;

    @Autowired
    ModuleService moduleService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // MerchantModule object
    private MerchantModule merchantModule;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<Module> temModule =new HashSet<>();

    Set<MerchantModule> tempMerchantModule =new HashSet<>();




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

        // Create the merchantModule
        merchantModule = MerchantModuleFixture.standardMerchantModule();


    }



    @Test
    public void saveMerchantModule() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/merchantmodule")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("memMerchantNo",merchantModule.getMemMerchantNo().toString())
                                                .param("memModuleId",merchantModule.getMemModuleId().toString())
                                                .param("memEnabledInd", merchantModule.getMemEnabledInd().toString())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantModuleResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));

        log.info("MerchantModule created");


    } 
    
    @Test
    public void deleteMerchantModule() throws  Exception {

        // Create the merchantModule
        merchantModule = merchantModuleService.saveMerchantModule(merchantModule);
        log.info("MerchantModule created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/merchantmodule/delete")
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                    .param("memMerchantNo", merchantModule.getMemMerchantNo().toString())
                                                    .param("memModuleId",merchantModule.getMemModuleId().toString())
                                                    .param("memEnabledInd", merchantModule.getMemEnabledInd().toString())
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantModuleResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


        log.info("MerchantModule deleted");


    }

    @Test
    public void listMerchantModulesForAdmin() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/admin/merchantmodules/"+merchantModule.getMemMerchantNo().toString())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantModuleResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listMerchantModulesMapForMerchant() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/merchantmodules/map")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantModuleResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listMerchantModulesForMerchant() throws Exception  {

        //Save the moduel
        Module module = ModuleFixture.standardModule();
        module.setMdlName("testR");
        module = moduleService.saveModule(module);

        // Create the merchantModule
        MerchantModule merchantModule = MerchantModuleFixture.standardMerchantModule();
        merchantModule.setMemMerchantNo(1L);
        merchantModule.setMemModuleId(module.getMdlId());
        merchantModule.setMemEnabledInd(IndicatorStatus.YES);
        merchantModuleService.saveMerchantModule(merchantModule);
        log.info("MerchantModule created");

        temModule.add(module);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/admin/merchant/merchantmodules/"+merchantModule.getMemMerchantNo()+"/"+"name"+module.getMdlName())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        log.info("MerchantModuleResponse: " + response);



    }





    @After
    public void tearDown() throws Exception {

        Set<MerchantModule> merchantModules = MerchantModuleFixture.standardMerchantModules();

        for(MerchantModule merchantModule: merchantModules) {

            MerchantModule delMerchantModule = merchantModuleService.findByMemMerchantNoAndMemModuleId(merchantModule.getMemMerchantNo(),merchantModule.getMemModuleId());

            if ( delMerchantModule != null ) {
                merchantModuleService.deleteMerchantModule(delMerchantModule);
            }

        }

        for (Module module:temModule){

            moduleService.deleteModule(module.getMdlId());
        }
    }

}
